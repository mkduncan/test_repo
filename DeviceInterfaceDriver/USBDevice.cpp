#include "USBDevice.hpp"
#include <guiddef.h>
#include <windows.h>
#include <setupapi.h>
#include <shlwapi.h>
#include <algorithm>
#include <array>
#include <atomic>
#include <format>
#include <map>
#include <memory>
#include <mutex>
#include <regex>
#include <span>
#include <string_view>
#include <thread>
#define _PRTCLS_
#define _SPOOLER_MOD_
namespace Cognitive
{
#if defined _PRTCLS_
static constexpr const GUID GUID_CLASS_TPG_BULK{0xC12127C7,
0x8258, 0x4929, {0x85, 0x74, 0x42, 0x78, 0xB9, 0x46, 0xCD, 0x69}};
static constexpr const GUID GUID_CLASS_PTR_BULK{0x28D78FAD,
0x5A12, 0x11D1, {0xAE, 0x5B, 0x00, 0x00, 0xF8, 0x03, 0xA8, 0xC2}};
#else
static constexpr const GUID GUID_CLASS_COM_BULK{0xC12127C7,
0x8258, 0x4929, {0x85, 0x74, 0x42, 0x78, 0xB9, 0x46, 0xCD, 0x69}};
#endif
enum class USBDeviceType
{
NativeType,
PrinterClassType
};
static constexpr std::size_t USB_MAX_READ{4096};
class CircularBuffer
{
public:
CircularBuffer(std::size_t size=12);
~CircularBuffer()=default;
CircularBuffer(const CircularBuffer&)=delete;
CircularBuffer(CircularBuffer&&) noexcept;
CircularBuffer& operator=(const CircularBuffer&)=delete;
CircularBuffer& operator=(CircularBuffer&&) noexcept;
std::size_t PutBytes(std::span<const std::byte> data);
std::size_t GetBytes(std::span<std::byte> destination);
std::size_t ReadyBytes() const noexcept;
private:
void PutByte(std::byte byte);
std::byte GetByte();
std::unique_ptr<std::byte[]> Buffer;
std::mutex Mutex;
std::size_t DataLength{0}, MaxLength{0}, NextInputLength{0}, NextOutputLength{0};
};
#ifdef _SPOOLER_MOD_
class SpoolerManager
{
public:
SpoolerManager()=default;
~SpoolerManager();
SpoolerManager(const SpoolerManager&)=delete;
SpoolerManager(SpoolerManager&&)=delete;
SpoolerManager& operator=(const SpoolerManager&)=delete;
SpoolerManager& operator=(SpoolerManager&&)=delete;
bool StopSpooler();
bool StartSpooler();
private:
SC_HANDLE SpoolerService{nullptr};
SERVICE_STATUS ServiceStatus{};
};
#endif
class USBCommunicator
{
public:
USBCommunicator()=default;
~USBCommunicator();
USBCommunicator(const USBCommunicator&)=delete;
USBCommunicator& operator=(const USBCommunicator&)=delete;
USBCommunicator(USBCommunicator&&) noexcept;
USBCommunicator& operator=(USBCommunicator&&) noexcept;
bool Initialize(std::int32_t deviceType=-1);
bool Close();
std::size_t ReadyBytes() const;
#if defined _PRTCLS_
bool getUSBDeviceFileName(std::int32_t index, std::string& outName);
HANDLE OpenDevice(LPGUID pGuid, std::string& outName);
HANDLE OpenDevice(
HDEVINFO hardwareDeviceInfo, PSP_INTERFACE_DEVICE_DATA deviceInfoData, std::string& outName);
HANDLE OpenFile(std::int32_t index, std::string_view filename);
bool ReceiveData();
bool getData(std::span<std::byte> buffer, std::size_t& bytesRead, std::chrono::milliseconds timeout);
bool getPowerStatus() const noexcept;
bool Reopen();
#else
bool getUSBDeviceFileName(std::string& outName);
HANDLE OpenDevice(LPGUID pGuid, std::string& outName);
HANDLE OpenDevice(HDEVINFO hardwareDeviceInfo, PSP_INTERFACE_DEVICE_DATA deviceInfoData, std::string& outName);
HANDLE OpenFile(std::string_view filename);
#endif
#ifdef _SPOOLER_MOD_
bool StartSpooler();
bool StopSpooler();
#endif
public:
std::atomic_bool FirstConnectFlag{false};
std::string DeviceName{};
std::int32_t DeviceType{-1};
#if defined _PRTCLS_
private:
std::atomic_bool ReadThreadActive{false}, ContinueFlag{false}, PowerStatusFlag{false}, HandlesValid{false};
HANDLE ReadHandle{INVALID_HANDLE_VALUE}, WriteHandle{INVALID_HANDLE_VALUE},
QuitEvent{nullptr}, DataEvent{nullptr}, ReadThreadEnded{nullptr};
std::jthread WorkerThread;
std::unique_ptr<CircularBuffer> Buffer;
#ifdef _SPOOLER_MOD_
SpoolerManager Spooler;
#endif
void receiverThread(std::stop_token stopToken);
#endif
friend class DeviceManager;
};
static std::int32_t ProbeDevice(std::string& result);
class DeviceManager
{
public:
static DeviceManager& Instance();
std::size_t AddDevice(const std::string& path);
bool RemoveDevice(std::size_t deviceId);
bool Read(std::size_t deviceId, std::span<std::byte> buffer, std::size_t& bytesRead,
std::chrono::milliseconds timeout=std::chrono::milliseconds(100));
bool Write(std::size_t deviceId, std::span<const std::byte> data,
std::chrono::milliseconds timeout=std::chrono::milliseconds(100));
std::size_t ReadyBytes(std::size_t deviceId);
std::vector<std::string> getActiveDevicePaths() const;
private:
DeviceManager()=default;
~DeviceManager()=default;
DeviceManager(const DeviceManager&)=delete;
DeviceManager(DeviceManager&&)=delete;
DeviceManager& operator=(const DeviceManager&)=delete;
DeviceManager& operator=(DeviceManager&&)=delete;
std::mutex Mutex;
std::map<std::size_t, std::unique_ptr<USBCommunicator>>Devices;
std::size_t NextId{1};
};
CircularBuffer::CircularBuffer(std::size_t size)
{
size=std::clamp<std::size_t>(size, 6, 16);
MaxLength=(1<<size) - 1;
Buffer=std::make_unique<std::byte[]>(MaxLength + 1);
}
CircularBuffer::CircularBuffer(CircularBuffer&&other) noexcept : Buffer(std::move(other.Buffer)),
DataLength(std::exchange(other.DataLength, 0)), MaxLength(std::exchange(other.MaxLength, 0)),
NextInputLength(std::exchange(other.NextInputLength, 0)), NextOutputLength(std::exchange(other.NextOutputLength, 0))
{
}
CircularBuffer& CircularBuffer::operator=(CircularBuffer&&other) noexcept
{
if(this!=&other)
{
std::scoped_lock lock(Mutex, other.Mutex);
Buffer=std::move(other.Buffer);
DataLength=std::exchange(other.DataLength, 0);
MaxLength=std::exchange(other.MaxLength, 0);
NextInputLength=std::exchange(other.NextInputLength, 0);
NextOutputLength=std::exchange(other.NextOutputLength, 0);
}
return *this;
}
std::size_t CircularBuffer::PutBytes(std::span<const std::byte> data)
{
std::lock_guard<std::mutex> lock(Mutex);
std::size_t bytesToPut=std::min(data.size(), (MaxLength + 1) - DataLength);
for(std::size_t index=0; index < bytesToPut; ++index)
{
PutByte(data[index]);
}
return bytesToPut;
}
std::size_t CircularBuffer::GetBytes(std::span<std::byte> destination)
{
std::lock_guard<std::mutex> lock(Mutex);
std::size_t bytesToGet=std::min(destination.size(), DataLength);
for(std::size_t index=0; index < bytesToGet; ++index)
{
destination[index]=GetByte();
}
return bytesToGet;
}
std::size_t CircularBuffer::ReadyBytes() const noexcept
{
return DataLength;
}
void CircularBuffer::PutByte(std::byte byte)
{
if(DataLength < MaxLength + 1)
{
Buffer[NextInputLength++]=byte;
NextInputLength &=MaxLength;
DataLength++;
}
}
std::byte CircularBuffer::GetByte()
{
std::byte byte{0};
if(DataLength > 0)
{
byte=Buffer[NextOutputLength++];
NextOutputLength &=MaxLength;
DataLength--;
}
return byte;
}
#ifdef _SPOOLER_MOD_
SpoolerManager::~SpoolerManager()
{
if(SpoolerService!=nullptr)
{
CloseServiceHandle(SpoolerService);
}
}
bool SpoolerManager::StopSpooler()
{
SC_HANDLE schSCManager=OpenSCManager(nullptr, nullptr, SC_MANAGER_ALL_ACCESS);
if(!schSCManager)
{
return false;
}
SpoolerService=OpenService(schSCManager, "spooler", SERVICE_ALL_ACCESS);
CloseServiceHandle(schSCManager);
if(!SpoolerService)
{
return false;
}
if(!ControlService(SpoolerService, SERVICE_CONTROL_STOP, &ServiceStatus))
{
return false;
}
constexpr std::int32_t MAX_WAIT_COUNT=60;
std::int32_t waitCount=0;
while(QueryServiceStatus(SpoolerService, &ServiceStatus)&&waitCount < MAX_WAIT_COUNT)
{
if(ServiceStatus.dwCurrentState==SERVICE_STOP_PENDING)
{
BlockThread(1000);
waitCount++;
}
else
{
break;
}
}
return (ServiceStatus.dwCurrentState==SERVICE_STOPPED);
}
bool SpoolerManager::StartSpooler()
{
if(!SpoolerService)
{
return false;
}
if(!StartService(SpoolerService, 0, nullptr))
{
return false;
}
BlockThread(1000);
while(QueryServiceStatus(SpoolerService, &ServiceStatus))
{
if(ServiceStatus.dwCurrentState!=SERVICE_RUNNING)
{
BlockThread(1000);
}
else
{
break;
}
}
return (ServiceStatus.dwCurrentState==SERVICE_RUNNING);
}
#endif  
USBCommunicator::~USBCommunicator()
{
Close();
}
USBCommunicator::USBCommunicator(USBCommunicator&&other) noexcept : FirstConnectFlag(other.FirstConnectFlag.load()),
DeviceName(std::move(other.DeviceName)), DeviceType(std::exchange(other.DeviceType, -1))
{
#if defined _PRTCLS_
ReadThreadActive=other.ReadThreadActive.load();
ContinueFlag=other.ContinueFlag.load();
PowerStatusFlag=other.PowerStatusFlag.load();
HandlesValid=other.HandlesValid.load();
ReadHandle=std::exchange(other.ReadHandle, INVALID_HANDLE_VALUE);
WriteHandle=std::exchange(other.WriteHandle, INVALID_HANDLE_VALUE);
QuitEvent=std::exchange(other.QuitEvent, nullptr);
DataEvent=std::exchange(other.DataEvent, nullptr);
ReadThreadEnded=std::exchange(other.ReadThreadEnded, nullptr);
Buffer=std::move(other.Buffer);
#endif
}
USBCommunicator& USBCommunicator::operator=(USBCommunicator&&other) noexcept
{
if(this!=&other)
{
Close();
DeviceType=std::exchange(other.DeviceType, -1);
FirstConnectFlag.store(other.FirstConnectFlag.load());
DeviceName=std::move(other.DeviceName);
#if defined _PRTCLS_
ReadThreadActive.store(other.ReadThreadActive.load());
ContinueFlag.store(other.ContinueFlag.load());
PowerStatusFlag.store(other.PowerStatusFlag.load());
HandlesValid.store(other.HandlesValid.load());
ReadHandle=std::exchange(other.ReadHandle, INVALID_HANDLE_VALUE);
WriteHandle=std::exchange(other.WriteHandle, INVALID_HANDLE_VALUE);
QuitEvent=std::exchange(other.QuitEvent, nullptr);
DataEvent=std::exchange(other.DataEvent, nullptr);
ReadThreadEnded=std::exchange(other.ReadThreadEnded, nullptr);
Buffer=std::move(other.Buffer);
if(other.WorkerThread.joinable())
{
WorkerThread=std::move(other.WorkerThread);
}
#endif
}
return *this;
}
bool USBCommunicator::Initialize(std::int32_t deviceType)
{
Close();
#if defined _PRTCLS_
if(deviceType < 0)
{
std::string deviceInfo;
DeviceType=ProbeDevice(deviceInfo);
}
else
{
DeviceType=deviceType;
}
Buffer=std::make_unique<CircularBuffer>();
QuitEvent=CreateEvent(nullptr, TRUE, FALSE, nullptr);
if(!QuitEvent)
return false;
ReadThreadEnded=CreateEvent(nullptr, FALSE, FALSE, nullptr);
if(!ReadThreadEnded)
{
CloseHandle(QuitEvent);
QuitEvent=nullptr;
return false;
}
DataEvent=CreateEvent(nullptr, TRUE, FALSE, nullptr);
if(!DataEvent)
{
CloseHandle(QuitEvent);
CloseHandle(ReadThreadEnded);
QuitEvent=nullptr;
ReadThreadEnded=nullptr;
return false;
}
std::string outName;
if(!getUSBDeviceFileName(DeviceType, outName))
{
Close();
return false;
}
DeviceName=outName;
const std::string readPath=DeviceName + "\\PIPE00";
ReadHandle=CreateFile(readPath.c_str(), GENERIC_READ | GENERIC_WRITE,
FILE_SHARE_READ | FILE_SHARE_WRITE, nullptr, OPEN_EXISTING, FILE_FLAG_OVERLAPPED, nullptr);
if(ReadHandle==INVALID_HANDLE_VALUE)
{
Close();
return false;
}
const std::string writePath=DeviceName + "\\PIPE01";
WriteHandle=CreateFile(writePath.c_str(), GENERIC_READ | GENERIC_WRITE,
FILE_SHARE_READ | FILE_SHARE_WRITE, nullptr, OPEN_EXISTING, 0, nullptr);
if(WriteHandle==INVALID_HANDLE_VALUE)
{
Close();
return false;
}
ContinueFlag=true;
WorkerThread=std::jthread([this](std::stop_token stopToken) { receiverThread(std::move(stopToken)); });
HandlesValid=true;
return WorkerThread.joinable();
#else
std::string outName;
if(!getUSBDeviceFileName(outName))
{
return false;
}
DeviceName=outName;
DeviceType=NativeType;
return true;
#endif
}
bool USBCommunicator::Close()
{
#if defined _PRTCLS_
if(QuitEvent)
{
SetEvent(QuitEvent);
}
if(WorkerThread.joinable())
{
WorkerThread.request_stop();
if(ReadThreadEnded)
{
WaitForSingleObject(ReadThreadEnded, INFINITE);
}
WorkerThread.join();
}
if(ReadHandle!=INVALID_HANDLE_VALUE)
{
CloseHandle(ReadHandle);
ReadHandle=INVALID_HANDLE_VALUE;
}
if(WriteHandle!=INVALID_HANDLE_VALUE)
{
CloseHandle(WriteHandle);
WriteHandle=INVALID_HANDLE_VALUE;
}
if(QuitEvent)
{
CloseHandle(QuitEvent);
QuitEvent=nullptr;
}
if(DataEvent)
{
CloseHandle(DataEvent);
DataEvent=nullptr;
}
if(ReadThreadEnded)
{
CloseHandle(ReadThreadEnded);
ReadThreadEnded=nullptr;
}
Buffer.reset();
HandlesValid=false;
ReadThreadActive=false;
#endif
return true;
}
std::size_t USBCommunicator::ReadyBytes() const
{
#if defined _PRTCLS_
return Buffer ? Buffer->ReadyBytes() : 0;
#else
return 0;
#endif
}
#if defined _PRTCLS_
bool USBCommunicator::getUSBDeviceFileName(std::int32_t index, std::string& outName)
{
LPGUID pGuid=nullptr;
switch(index)
{
case 0:
default:
{
pGuid=(LPGUID)&GUID_CLASS_TPG_BULK;
break;
}
case 1:
{
pGuid=(LPGUID)&GUID_CLASS_PTR_BULK;
break;
}
}
HANDLE hDev=OpenDevice(pGuid, outName);
if(hDev!=INVALID_HANDLE_VALUE&&hDev!=nullptr)
{
CloseHandle(hDev);
return true;
}
return false;
}
HANDLE USBCommunicator::OpenDevice(LPGUID pGuid, std::string& outName)
{
HANDLE hOut=nullptr;
HDEVINFO hardwareDeviceInfo=SetupDiGetClassDevs(pGuid, nullptr, nullptr, DIGCF_PRESENT | DIGCF_INTERFACEDEVICE);
if(hardwareDeviceInfo==INVALID_HANDLE_VALUE)
{
return (HANDLE)1;
}
SP_INTERFACE_DEVICE_DATA deviceInfoData{ .cbSize=sizeof(SP_INTERFACE_DEVICE_DATA) };
for(DWORD index=0;; ++index)
{
if(!SetupDiEnumDeviceInterfaces(hardwareDeviceInfo, nullptr, pGuid, index, &deviceInfoData))
{
if(GetLastError()==ERROR_NO_MORE_ITEMS)
{
break;
}
continue;
}
hOut=OpenDevice(hardwareDeviceInfo, &deviceInfoData, outName);
if(hOut!=INVALID_HANDLE_VALUE)
{
if(StrStrI(outName.c_str(), "VID_1840&PID_0100")!=nullptr)
{
SetupDiDestroyDeviceInfoList(hardwareDeviceInfo);
return hOut;
}
if(hOut!=nullptr&&hOut!=INVALID_HANDLE_VALUE)
{
CloseHandle(hOut);
hOut=nullptr;
}
Clear(outName);
}
}
SetupDiDestroyDeviceInfoList(hardwareDeviceInfo);
return hOut;
}
HANDLE USBCommunicator::OpenDevice(HDEVINFO hardwareDeviceInfo,
PSP_INTERFACE_DEVICE_DATA deviceInfoData, std::string& outName)
{
DWORD requiredLength=0;
SetupDiGetInterfaceDeviceDetail(hardwareDeviceInfo, deviceInfoData, nullptr,
0, &requiredLength, nullptr);
std::unique_ptr<BYTE[]> buffer=std::make_unique<BYTE[]>(requiredLength);
PSP_INTERFACE_DEVICE_DETAIL_DATA detailData=reinterpret_cast<PSP_INTERFACE_DEVICE_DETAIL_DATA>(buffer.get());
if(!detailData)
{
return INVALID_HANDLE_VALUE;
}
detailData->cbSize=sizeof(SP_INTERFACE_DEVICE_DETAIL_DATA);
if(!SetupDiGetInterfaceDeviceDetail(hardwareDeviceInfo, deviceInfoData,
detailData, requiredLength, &requiredLength, nullptr))
{
return INVALID_HANDLE_VALUE;
}
outName=detailData->DevicePath;
return CreateFile(detailData->DevicePath, GENERIC_READ | GENERIC_WRITE,
FILE_SHARE_READ | FILE_SHARE_WRITE, nullptr, OPEN_EXISTING, 0, nullptr);
}
HANDLE USBCommunicator::OpenFile(std::int32_t index, std::string_view filename)
{
std::string deviceName;
if(!getUSBDeviceFileName(index, deviceName))
{
return INVALID_HANDLE_VALUE;
}
std::string fullPath=deviceName + "\\" + std::string(filename);
return CreateFile(fullPath.c_str(), GENERIC_READ | GENERIC_WRITE,
FILE_SHARE_READ | FILE_SHARE_WRITE, nullptr, OPEN_EXISTING, 0, nullptr);
}
bool USBCommunicator::ReceiveData()
{
if(ReadHandle==INVALID_HANDLE_VALUE||!Buffer)
{
return false;
}
return true;
}
void USBCommunicator::receiverThread(std::stop_token stopToken)
{
ReadThreadActive=true;
PowerStatusFlag=true;
HANDLE overlappedEvent=CreateEvent(nullptr, TRUE, FALSE, nullptr);
if(!overlappedEvent)
{
ReadThreadActive=false;
PowerStatusFlag=false;
return;
}
std::array<std::byte, USB_MAX_READ> inBuffer{};
std::stop_callback stopCallback(stopToken, [this]()
{
ContinueFlag=false;
if(QuitEvent)
{
SetEvent(QuitEvent);
}
if(ReadHandle!=INVALID_HANDLE_VALUE)
{
CancelIo(ReadHandle);
}
});
OVERLAPPED overlapped{.hEvent=overlappedEvent};
const std::array<HANDLE, 2> waitHandles={overlappedEvent, QuitEvent};
ContinueFlag=true;
while(ContinueFlag&&!stopToken.stop_requested())
{
overlapped.Offset=overlapped.OffsetHigh=0;
ResetEvent(overlappedEvent);
DWORD bytesRead=0;
BOOL result=ReadFile(ReadHandle, inBuffer.data(), static_cast<DWORD>(inBuffer.size()), &bytesRead, &overlapped);
if(!result)
{
const DWORD error=GetLastError();
if(error==ERROR_IO_PENDING)
{
DWORD waitResult=WaitForMultipleObjects(static_cast<DWORD>(waitHandles.size()),
waitHandles.data(), FALSE, INFINITE);
if(waitResult==WAIT_OBJECT_0)
{
if(GetOverlappedResult(ReadHandle, &overlapped, &bytesRead, FALSE)&&bytesRead > 0)
{
Buffer->PutBytes({inBuffer.data(), bytesRead});
SetEvent(DataEvent);
}
}
else if(waitResult==WAIT_OBJECT_0 + 1)
{
ContinueFlag=false;
CancelIo(ReadHandle);
SetEvent(ReadThreadEnded);
break;
}
}
else if(error==ERROR_ACCESS_DENIED||error==ERROR_INVALID_HANDLE)
{
PowerStatusFlag=false;
if(Reopen())
{
PowerStatusFlag=true;
}
else
{
if(WaitForSingleObject(QuitEvent, 500)==WAIT_OBJECT_0)
{
ContinueFlag=false;
CancelIo(ReadHandle);
SetEvent(ReadThreadEnded);
break;
}
}
}
}
else if(bytesRead > 0)
{
Buffer->PutBytes({inBuffer.data(), bytesRead});
SetEvent(DataEvent);
}
if(ContinueFlag)
{
BlockThread(25);
}
}
CloseHandle(overlappedEvent);
ReadThreadActive=false;
PowerStatusFlag=false;
SetEvent(ReadThreadEnded);
}
bool USBCommunicator::getData(std::span<std::byte> buffer, std::size_t& bytesRead, std::chrono::milliseconds timeout)
{
bytesRead=0;
if(!Buffer||buffer.empty())
{
return false;
}
ResetEvent(DataEvent);
bytesRead=Buffer->GetBytes(buffer);
if(bytesRead > 0)
{
return true;
}
const std::array<HANDLE, 2> waitHandles={DataEvent, QuitEvent};
DWORD waitResult=WaitForMultipleObjects(static_cast<DWORD>(waitHandles.size()),
waitHandles.data(), FALSE, static_cast<DWORD>(timeout.count()));
if(waitResult==WAIT_OBJECT_0)
{
bytesRead=Buffer->GetBytes(buffer);
return bytesRead > 0;
}
else if(waitResult==WAIT_OBJECT_0 + 1)
{
SetEvent(ReadThreadEnded);
ContinueFlag=false;
return false;
}
else if(waitResult==WAIT_TIMEOUT)
{
return false;
}
return false;
}
bool USBCommunicator::getPowerStatus() const noexcept
{
return PowerStatusFlag;
}
bool USBCommunicator::Reopen()
{
if(ReadHandle!=INVALID_HANDLE_VALUE)
{
CloseHandle(ReadHandle);
ReadHandle=INVALID_HANDLE_VALUE;
}
if(WriteHandle!=INVALID_HANDLE_VALUE)
{
CloseHandle(WriteHandle);
WriteHandle=INVALID_HANDLE_VALUE;
}
HandlesValid=false;
std::string deviceName;
if(!getUSBDeviceFileName(DeviceType, deviceName))
{
return false;
}
DeviceName=deviceName;
const std::string readPath=DeviceName + "\\PIPE00";
ReadHandle=CreateFile(readPath.c_str(), GENERIC_READ | GENERIC_WRITE,
FILE_SHARE_READ | FILE_SHARE_WRITE, nullptr, OPEN_EXISTING, FILE_FLAG_OVERLAPPED, nullptr);
if(ReadHandle==INVALID_HANDLE_VALUE)
{
return false;
}
const std::string writePath=DeviceName + "\\PIPE01";
WriteHandle=CreateFile(writePath.c_str(), GENERIC_READ | GENERIC_WRITE,
FILE_SHARE_READ | FILE_SHARE_WRITE, nullptr, OPEN_EXISTING, 0, nullptr);
if(WriteHandle==INVALID_HANDLE_VALUE)
{
CloseHandle(ReadHandle);
ReadHandle=INVALID_HANDLE_VALUE;
return false;
}
PurgeComm(WriteHandle, PURGE_TXCLEAR);
PurgeComm(WriteHandle, PURGE_RXCLEAR);
HandlesValid=true;
return true;
}
#else
bool USBCommunicator::getUSBDeviceFileName(std::string& outName)
{
HANDLE hDev=OpenDevice((LPGUID)&GUID_CLASS_COM_BULK, outName);
if(hDev!=INVALID_HANDLE_VALUE)
{
CloseHandle(hDev);
return true;
}
return false;
}
HANDLE USBCommunicator::OpenDevice(LPGUID pGuid, std::string& outName)
{
HANDLE hOut=INVALID_HANDLE_VALUE;
HDEVINFO hardwareDeviceInfo=SetupDiGetClassDevs(pGuid, nullptr, nullptr, DIGCF_PRESENT | DIGCF_INTERFACEDEVICE);
if(hardwareDeviceInfo==INVALID_HANDLE_VALUE)
{
return (HANDLE)1;
}
SP_INTERFACE_DEVICE_DATA deviceInfoData{};
deviceInfoData.cbSize=sizeof(SP_INTERFACE_DEVICE_DATA);
for(DWORD index=0;; ++index)
{
if(!SetupDiEnumDeviceInterfaces(hardwareDeviceInfo, nullptr, pGuid, index, &deviceInfoData))
{
if(GetLastError()==ERROR_NO_MORE_ITEMS)
{
break;
}
continue;
}
hOut=OpenDevice(hardwareDeviceInfo, &deviceInfoData, outName);
if(hOut!=INVALID_HANDLE_VALUE)
{
break;
}
}
SetupDiDestroyDeviceInfoList(hardwareDeviceInfo);
return hOut;
}
HANDLE USBCommunicator::OpenDevice(HDEVINFO hardwareDeviceInfo,
PSP_INTERFACE_DEVICE_DATA deviceInfoData, std::string& outName)
{
DWORD requiredLength=0;
SetupDiGetInterfaceDeviceDetail(hardwareDeviceInfo, deviceInfoData, nullptr, 0, &requiredLength, nullptr);
std::unique_ptr<BYTE[]> buffer=std::make_unique<BYTE[]>(requiredLength);
PSP_INTERFACE_DEVICE_DETAIL_DATA detailData=reinterpret_cast<PSP_INTERFACE_DEVICE_DETAIL_DATA>(buffer.GetBytes());
if(!detailData)
{
return INVALID_HANDLE_VALUE;
}
detailData->cbSize=sizeof(SP_INTERFACE_DEVICE_DETAIL_DATA);
if(!SetupDiGetInterfaceDeviceDetail(hardwareDeviceInfo,
deviceInfoData, detailData, requiredLength, &requiredLength, nullptr))
{
return INVALID_HANDLE_VALUE;
}
outName=detailData->DevicePath;
return CreateFile(detailData->DevicePath, GENERIC_READ | GENERIC_WRITE,
FILE_SHARE_READ | FILE_SHARE_WRITE, nullptr, OPEN_EXISTING, 0, nullptr);
}
HANDLE USBCommunicator::OpenFile(std::string_view filename)
{
std::string deviceName;
if(!getUSBDeviceFileName(deviceName))
{
return INVALID_HANDLE_VALUE;
}
std::string fullPath=deviceName + "\\" + std::string(filename);
return CreateFile(fullPath.c_str(), GENERIC_READ | GENERIC_WRITE,
FILE_SHARE_READ | FILE_SHARE_WRITE, nullptr, OPEN_EXISTING, 0, nullptr);
}
#endif  
#ifdef _SPOOLER_MOD_
bool USBCommunicator::StopSpooler()
{
#if defined _PRTCLS_
return Spooler.StopSpooler();
#else
return false;
#endif
}
bool USBCommunicator::StartSpooler()
{
#if defined _PRTCLS_
return Spooler.StartSpooler();
#else
return false;
#endif
}
#endif  
std::int32_t ProbeDevice(std::string& result)
{
USBCommunicator usbComm;
std::int32_t retVal=-1;
HANDLE hUsb=nullptr;
#if defined _PRTCLS_
{
std::string devicePath;
if(usbComm.getUSBDeviceFileName(0, devicePath))
{
hUsb=usbComm.OpenFile(0, "PIPE00");
if(hUsb!=INVALID_HANDLE_VALUE)
{
result="Native USB";
retVal=static_cast<std::int32_t>(USBDeviceType::NativeType);
CloseHandle(hUsb);
return retVal;
}
}
}
{
std::string devicePath;
if(usbComm.getUSBDeviceFileName(1, devicePath))
{
hUsb=usbComm.OpenFile(1, "PIPE00");
if(hUsb!=INVALID_HANDLE_VALUE)
{
result="Printer Class USB";
retVal=static_cast<std::int32_t>(USBDeviceType::PrinterClassType);
CloseHandle(hUsb);
return retVal;
}
}
}
#else
{
std::string devicePath;
if(usbComm.getUSBDeviceFileName(devicePath))
{
hUsb=usbComm.OpenFile("PIPE00");
if(hUsb!=INVALID_HANDLE_VALUE)
{
result="Native USB";
retVal=NativeType;
CloseHandle(hUsb);
return retVal;
}
}
}
#endif
result="Unknown USB type";
return retVal;
}
DeviceManager& DeviceManager::Instance()
{
static DeviceManager Instance;
return Instance;
}
std::size_t DeviceManager::AddDevice(const std::string& path)
{
std::unique_lock<std::mutex> lock(Mutex);
for(const auto& [id, device] : Devices)
{
if(device->DeviceName==path)
{
return id;
}
}
auto device=std::make_unique<USBCommunicator>();
if(!device)
{
return 0;
}
if(!device->Initialize())
{
return 0;
}
std::size_t id=NextId++;
Devices[id]=std::move(device);
return id;
}
bool DeviceManager::RemoveDevice(std::size_t deviceId)
{
std::unique_lock<std::mutex> lock(Mutex);
auto it=Devices.find(deviceId);
if(it==Devices.end())
{
return false;
}
it->second->Close();
Devices.erase(it);
return true;
}
bool DeviceManager::Read(
std::size_t deviceId, std::span<std::byte> buffer, std::size_t& bytesRead, std::chrono::milliseconds timeout)
{
std::unique_lock<std::mutex> lock(Mutex);
auto it=Devices.find(deviceId);
if(it==Devices.end())
{
return false;
}
#if defined _PRTCLS_
return it->second->getData(buffer, bytesRead, timeout);  
#else
bytesRead=0;
return false;
#endif
}
bool DeviceManager::Write(std::size_t deviceId, std::span<const std::byte> data, std::chrono::milliseconds timeout)
{
std::unique_lock lock(Mutex);
auto it=Devices.find(deviceId);
if(it==Devices.end())
{
return false;
}
if(it->second->WriteHandle==INVALID_HANDLE_VALUE)
{
return false;
}
OVERLAPPED overlapped{};
HANDLE overlappedEvent=CreateEvent(nullptr, TRUE, FALSE, nullptr);
if(overlappedEvent==nullptr)
{
return false;
}
overlapped.hEvent=overlappedEvent;
DWORD bytesWritten=0;
bool success=false;
if(WriteFile(it->second->WriteHandle, data.data(), static_cast<DWORD>(data.size()), &bytesWritten, &overlapped))
{
success=(bytesWritten==data.size());
}
else if(GetLastError()==ERROR_IO_PENDING)
{
DWORD waitResult=WaitForSingleObject(overlappedEvent, static_cast<DWORD>(timeout.count()));
if(waitResult==WAIT_OBJECT_0)
{
if(GetOverlappedResult(it->second->WriteHandle, &overlapped, &bytesWritten, FALSE))
{
success=(bytesWritten==data.size());
}
}
}
CloseHandle(overlappedEvent);
return success;
}
std::size_t DeviceManager::ReadyBytes(std::size_t deviceId)
{
std::unique_lock lock(Mutex);
auto it=Devices.find(deviceId);
if(it==Devices.end())
{
return 0;
}
return it->second->ReadyBytes();
}
std::vector<std::string> DeviceManager::getActiveDevicePaths() const
{
const std::array<const GUID*, 2> guidsToCheck{&GUID_CLASS_PTR_BULK, &GUID_CLASS_TPG_BULK};
std::vector<std::string> paths;
for(const GUID* pGuid : guidsToCheck)
{
HDEVINFO hardwareDeviceInfo=SetupDiGetClassDevs(pGuid, nullptr, nullptr, DIGCF_PRESENT | DIGCF_INTERFACEDEVICE);
if(hardwareDeviceInfo==INVALID_HANDLE_VALUE)
continue;
SP_INTERFACE_DEVICE_DATA deviceInfoData{.cbSize=sizeof(SP_INTERFACE_DEVICE_DATA)};
for(DWORD index=0;; ++index)
{
if(!SetupDiEnumDeviceInterfaces(hardwareDeviceInfo, nullptr, pGuid, index, &deviceInfoData))
{
if(GetLastError()==ERROR_NO_MORE_ITEMS)
break;
else
continue;
}
DWORD requiredSize=0;
SetupDiGetInterfaceDeviceDetail(hardwareDeviceInfo, &deviceInfoData, nullptr, 0, &requiredSize, nullptr);
if(requiredSize==0)
continue;
std::unique_ptr<uint8_t[]> buffer=std::make_unique<uint8_t[]>(requiredSize);
PSP_INTERFACE_DEVICE_DETAIL_DATA detailData=reinterpret_cast<PSP_INTERFACE_DEVICE_DETAIL_DATA>(buffer.get());
if(detailData==nullptr)
continue;
detailData->cbSize=sizeof(SP_INTERFACE_DEVICE_DETAIL_DATA);
if(!SetupDiGetInterfaceDeviceDetail(hardwareDeviceInfo,
&deviceInfoData, detailData, requiredSize, nullptr, nullptr))
{
continue;
}
HANDLE testHandle=CreateFile(detailData->DevicePath, GENERIC_READ | GENERIC_WRITE,
FILE_SHARE_READ | FILE_SHARE_WRITE, nullptr, OPEN_EXISTING, 0, nullptr);
if(testHandle!=INVALID_HANDLE_VALUE)
{
CloseHandle(testHandle);
paths.push_back(detailData->DevicePath);
}
}
SetupDiDestroyDeviceInfoList(hardwareDeviceInfo);
}
return paths;
}
USBDevice::USBDevice() : Interface(0), ErrorCode(), Timeout(getDefaultTimeout()), DevicePath(getDefaultDevicePath())
{
}
USBDevice::~USBDevice()
{
Close();
Reset();
}
USBDevice::USBDevice(USBDevice&&moveValue) : Interface(std::exchange(moveValue.Interface, 0)),
ErrorCode(moveValue.ErrorCode), Timeout(std::exchange(moveValue.Timeout, getDefaultTimeout())),
DevicePath(std::move(moveValue.DevicePath))
{
moveValue.ErrorCode.clear();
}
USBDevice& USBDevice::operator=(USBDevice&&moveValue)
{
if(this!=&moveValue)
{
if(!Close())
{
}
else
{
ErrorCode=moveValue.ErrorCode;
moveValue.ErrorCode.clear();
}
Interface=std::exchange(moveValue.Interface, 0);
Timeout=std::exchange(moveValue.Timeout, getDefaultTimeout());
DevicePath=std::move(moveValue.DevicePath);
}
return *this;
}
bool USBDevice::Close()
{
if(!isOpened())
return true;  
bool result=DeviceManager::Instance().RemoveDevice(Interface);
if(!result)
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return result;
}
bool USBDevice::Flush()
{
if(!isOpened()&&!Open())
return false;
std::string buffer(getAvailableBytes(), '\0');
if(buffer.empty())
return true;
std::size_t bytesRead=0;
bool success=DeviceManager::Instance().Read(Interface, StringToByteSpan(buffer), bytesRead);
if(!success)
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return success;
}
bool USBDevice::Open()
{
ErrorCode.clear();
if(!isValid())
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
if(isOpened())
return true;
Interface=DeviceManager::Instance().AddDevice(DevicePath);
if(Interface==0)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
return false;
}
return true;
}
bool USBDevice::Reopen()
{
ErrorCode.clear();
if(isOpened()&&!Close())
return false;
return Open();
}
bool USBDevice::Reset()
{
Interface=0;
ErrorCode.clear();
Timeout=getDefaultTimeout();
DevicePath=getDefaultDevicePath();
return true;
}
bool USBDevice::Read(std::string& readData)
{
ErrorCode.clear();  
if(!isOpened()&&!Open())
{
if(!ErrorCode)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
}
return false;
}
if(readData.empty())
{
std::size_t available=getAvailableBytes();
if(available==0)
{
ErrorCode=boost::system::errc::make_error_code(
boost::system::errc::resource_unavailable_try_again);
return false;
}
Resize(readData, available);
}
std::size_t bytesRead=0;
bool success=DeviceManager::Instance().Read(Interface, StringToByteSpan(readData), bytesRead, Timeout);
if(success&&bytesRead > 0)
{
Resize(readData, bytesRead);  
return true;
}
Clear(readData);
if(bytesRead==0)
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::resource_unavailable_try_again);
else if(bytesRead!=0)
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return false;
}
bool USBDevice::Read(std::string& readData, char readUntil)
{
ErrorCode.clear();
if(!isOpened()&&!Open())
{
if(!ErrorCode)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
}
return false;
}
Clear(readData);
bool delimiterFound=false;
while(!delimiterFound)
{
if(getAvailableBytes()==0)
{
if(!readData.empty())
{
return true;
}
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::resource_unavailable_try_again);
return false;
}
std::string singleByte(1, '\0');
std::size_t bytesRead=0;
bool success=DeviceManager::Instance().Read(Interface, StringToByteSpan(singleByte), bytesRead, Timeout);
if(!success)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return !readData.empty();
}
if(bytesRead==0)
{
return !readData.empty();
}
readData.push_back(singleByte[0]);
if(singleByte[0]==readUntil)
{
delimiterFound=true;
break;
}
}
return true;
}
bool USBDevice::Write(const std::string& writeData)
{
ErrorCode.clear();  
if(writeData.empty())
return true;  
if(!isOpened()&&!Open())
{
if(!ErrorCode)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
}
return false;
}
bool success=DeviceManager::Instance().Write(Interface, StringToConstByteSpan(writeData), Timeout);
if(!success)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
}
return success;
}
bool USBDevice::WriteRead(const std::string& writeData, std::string& readData,
char readUntil, const std::chrono::milliseconds pauseDuration)
{
ErrorCode.clear();  
if(!Write(writeData))
{
return false;
}
BlockThread(pauseDuration);
if(!Write("\n"))
{
return false;
}
BlockThread(pauseDuration);
return Read(readData, readUntil);
}
bool USBDevice::WriteRead(const std::string& writeData,
std::string& readData, const std::chrono::milliseconds pauseDuration)
{
ErrorCode.clear();  
if(!Write(writeData))
{
return false;
}
BlockThread(pauseDuration);
if(!Write("\n"))
{
return false;
}
BlockThread(pauseDuration);
return Read(readData);
}
std::size_t USBDevice::getAvailableBytes() const
{
if(!isOpened())
return 0;
return DeviceManager::Instance().ReadyBytes(Interface);
}
const std::string& USBDevice::getDevicePath() const
{
return DevicePath;
}
boost::system::error_code USBDevice::getErrorCode() const
{
return ErrorCode;
}
std::chrono::milliseconds USBDevice::getTimeout() const
{
return Timeout;
}
bool USBDevice::setDevicePath(const std::string& devicePath)
{
ErrorCode.clear();  
if(!isValidDevicePath(devicePath))
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
if(isOpened())
{
if(!Close())
{
return false;
}
}
DevicePath=devicePath;
return true;
}
bool USBDevice::setTimeout(std::chrono::milliseconds waitTimeMs)
{
ErrorCode.clear();  
if(!isValidTimeout(waitTimeMs))
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
Timeout=waitTimeMs;
return true;
}
bool USBDevice::setTimeout(std::chrono::milliseconds::rep waitTimeMs)
{
return setTimeout(std::chrono::milliseconds(waitTimeMs));
}
bool USBDevice::isClosed() const
{
return !isOpened();
}
bool USBDevice::isOpened() const
{
if(Interface==0)
return false;
return DeviceManager::Instance().ReadyBytes(Interface) >=0;
}
bool USBDevice::isValid() const
{
if(!isValidDevicePath(DevicePath)||!isValidTimeout(Timeout))
{
return false;
}
return !ErrorCode;
}
std::string USBDevice::toString() const
{
if(ErrorCode)
{
return Format("USB device: \"{}\" with timeout of {} ms. and error code of \"{}\"",
DevicePath, Timeout.count(), ErrorCode.message());
}
return Format("USB device: \"{}\" with timeout of {} ms. and without error code", DevicePath, Timeout.count());
}
std::vector<std::string> USBDevice::getActiveSystemDevices()
{
return DeviceManager::Instance().getActiveDevicePaths();
}
std::vector<std::string> USBDevice::getActiveSystemDevices(uint16_t vendorId, uint16_t productId)
{
std::vector<std::string> allDevices{getActiveSystemDevices()};
if(vendorId==0&&productId==0)
return allDevices;
std::vector<std::string> matchingDevices;
std::string vidPidPattern=Format("VID_{:04X}&PID_{:04X}", vendorId, productId);
for(const std::string& device : allDevices)
{
if(isValidDevicePath(device))
{
std::string deviceUpper=device;
std::transform(deviceUpper.begin(), deviceUpper.end(), deviceUpper.begin(),::toupper);
std::string patternUpper=vidPidPattern;
std::transform(patternUpper.begin(), patternUpper.end(), patternUpper.begin(),::toupper);
if(deviceUpper.find(patternUpper)!=std::string::npos)
{
matchingDevices.push_back(device);
}
}
}
return matchingDevices;
}
std::string USBDevice::getDefaultDevicePath()
{
return std::string{};
}
std::chrono::milliseconds USBDevice::getDefaultTimeout()
{
return std::chrono::milliseconds::zero();
}
bool USBDevice::isValidDevicePath(const std::string& devicePath)
{
if(devicePath.size() < 54)
return false;
return std::regex_match(devicePath, std::regex("^\\\\\\\\\\?\\\\USB#VID_[0-9A-F]{4}&PID_[0-9A-F]{4}#.*#"
"\\{[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}\\}$", std::regex_constants::icase));
}
bool USBDevice::isValidTimeout(std::chrono::milliseconds durationMs)
{
return durationMs.count() >=0;
}
bool USBDevice::isValidTimeout(std::chrono::milliseconds::rep durationMs)
{
return durationMs >=0;
}
}
