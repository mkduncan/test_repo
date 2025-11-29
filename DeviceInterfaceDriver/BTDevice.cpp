#include "BTDevice.hpp"
#include <boost/optional.hpp>
#include <memory>
#include <regex>
#include <thread>
#include <windows.h>
namespace Cognitive
{
static bool connectBLEDevice(SimpleBLE::Peripheral& peripheral, const std::string& address);
static bool readBLECharacteristic(SimpleBLE::Peripheral& peripheral, std::string& data);
static bool writeBLECharacteristic(SimpleBLE::Peripheral& peripheral, const std::string& data);
static size_t getBLEAvailableBytes(SimpleBLE::Peripheral& peripheral);
BTDevice::BTDevice() :
Service{},
BLEInterface{},
COMInterface{Service},
ErrorCode{},
Timeout{getDefaultTimeout()},
DevicePath{getDefaultDevicePath()}
{
}
BTDevice::~BTDevice()
{
Close();
Reset();
}
BTDevice::BTDevice(BTDevice&&moveValue) :
Service{},
BLEInterface{std::move(moveValue.BLEInterface)},
COMInterface{Service},
ErrorCode{moveValue.ErrorCode},
Timeout{std::exchange(moveValue.Timeout, getDefaultTimeout())},
DevicePath{std::move(moveValue.DevicePath)}
{
moveValue.ErrorCode.clear();
if(moveValue.COMInterface.is_open())
{
if(COMInterface.is_open())
{
COMInterface.close(ErrorCode);
}
DevicePath=std::move(moveValue.DevicePath);
if(!Open())
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
}
moveValue.COMInterface.close();
}
}
BTDevice& BTDevice::operator=(BTDevice&&moveValue)
{
if(this!=&moveValue)
{
Close();
ErrorCode=moveValue.ErrorCode;
Timeout=std::exchange(moveValue.Timeout, getDefaultTimeout());
DevicePath=std::move(moveValue.DevicePath);
BLEInterface=std::move(moveValue.BLEInterface);
moveValue.ErrorCode.clear();
if(moveValue.COMInterface.is_open())
{
COMInterface=boost::asio::serial_port(Service);
if(!Open())
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
}
moveValue.COMInterface.close();
}
}
return *this;
}
bool BTDevice::Close()
{
if(!isOpened())
{
return true;
}
ErrorCode.clear();
if(COMInterface.is_open())
{
COMInterface.cancel(ErrorCode);
auto closeLambda=[&]()
{
COMInterface.close(ErrorCode);
};
return ExecuteWithTimeout(Timeout, closeLambda)&&!ErrorCode;
}
else
{
auto disconnectLambda=[&]()
{
try
{
if(BLEInterface.is_connected())
{
BLEInterface.disconnect();
}
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
}
};
return ExecuteWithTimeout(Timeout, disconnectLambda)&&!ErrorCode;
}
}
bool BTDevice::Flush()
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
if(COMInterface.is_open())
{
const BOOL result=PurgeComm(reinterpret_cast<HANDLE>(COMInterface.native_handle()),
PURGE_RXABORT | PURGE_TXABORT | PURGE_RXCLEAR | PURGE_TXCLEAR);
return result==TRUE;
}
return true;
}
bool BTDevice::Open()
{
ErrorCode.clear();
if(!isValid())
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
if(isOpened())
{
return true;
}
if(DevicePath.find("COM")!=std::string::npos)
{
if(COMInterface.is_open())
{
COMInterface.close(ErrorCode);
if(ErrorCode)
{
return false;
}
}
COMInterface=boost::asio::serial_port(Service);
bool attemptOpen=false;
auto openLambda=[&]()
{
COMInterface.open(DevicePath, ErrorCode);
};
attemptOpen=ExecuteWithTimeout(Timeout, openLambda);
if(!attemptOpen||ErrorCode||!COMInterface.is_open())
{
Close();
return false;
}
boost::asio::serial_port_base::baud_rate baudRate(9600);
boost::asio::serial_port_base::character_size charSize(8);
boost::asio::serial_port_base::flow_control flowControl(boost::asio::serial_port_base::flow_control::none);
boost::asio::serial_port_base::parity parity(boost::asio::serial_port_base::parity::none);
boost::asio::serial_port_base::stop_bits stopBits(boost::asio::serial_port_base::stop_bits::one);
COMInterface.set_option(baudRate, ErrorCode);
if(ErrorCode)
{
Close(); return false;
}
COMInterface.set_option(charSize, ErrorCode);
if(ErrorCode)
{
Close(); return false;
}
COMInterface.set_option(flowControl, ErrorCode);
if(ErrorCode)
{
Close(); return false;
}
COMInterface.set_option(parity, ErrorCode);
if(ErrorCode)
{
Close(); return false;
}
COMInterface.set_option(stopBits, ErrorCode);
if(ErrorCode)
{
Close(); return false;
}
return true;
}
else
{
try
{
return connectBLEDevice(BLEInterface, DevicePath);
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return false;
}
}
}
bool BTDevice::Reopen()
{
ErrorCode.clear();
if(isOpened()&&!Close())
{
return false;
}
return Open();
}
bool BTDevice::Reset()
{
ErrorCode.clear();
if(isOpened())
{
Close();
}
BLEInterface=SimpleBLE::Peripheral();
COMInterface=boost::asio::serial_port(Service);
ErrorCode.clear();
Timeout=getDefaultTimeout();
DevicePath=getDefaultDevicePath();
return true;
}
bool BTDevice::Read(std::string& readData)
{
ErrorCode.clear();
if(!(isOpened()||(!isOpened()&&Open())))
{
if(!ErrorCode)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
}
return false;
}
if(COMInterface.is_open())
{
const std::size_t requestedSize=readData.size();
if(requestedSize==0)
{
std::size_t available=getAvailableBytes();
if(available > 0)
{
readData.resize(available);
}
else
{
return true;
}
}
readData.clear();
const std::size_t availableBytes=getAvailableBytes();
if(availableBytes > 0)
{
const std::size_t bytesToRead=std::min(availableBytes, requestedSize);
std::string buffer(bytesToRead, '\0');
boost::system::error_code errorCode;
try
{
std::size_t bytesRead=COMInterface.read_some(boost::asio::buffer(buffer), errorCode);
if(errorCode)
{
ErrorCode=errorCode;
return false;
}
buffer.resize(bytesRead);
readData.append(buffer);
if(readData.size() >=requestedSize)
{
return true;
}
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return false;
}
}
const std::size_t remainingBytes=requestedSize - readData.size();
if(remainingBytes > 0)
{
if(Timeout==std::chrono::milliseconds::zero())
{
std::string buffer(remainingBytes, '\0');
boost::system::error_code errorCode;
try
{
std::size_t bytesRead=boost::asio::read(COMInterface, boost::asio::buffer(buffer),
boost::asio::transfer_at_least(remainingBytes), errorCode);
if(errorCode&&errorCode!=boost::asio::error::eof)
{
ErrorCode=errorCode;
return !readData.empty();  
}
buffer.resize(bytesRead);
readData.append(buffer);
return !readData.empty();
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return !readData.empty();  
}
}
else
{
boost::asio::steady_timer timer(Service);
std::string buffer(remainingBytes, '\0');
boost::optional<boost::system::error_code> timerResult, readResult;
std::size_t bytesRead=0;
auto waitLambda=[&](const boost::system::error_code& state)
{
timerResult.reset(state);
};
auto readLambda=[&](const boost::system::error_code& state, std::size_t bytes)
{
readResult.reset(state);
bytesRead=bytes;
};
timer.expires_after(Timeout);
timer.async_wait(waitLambda);
boost::asio::async_read(COMInterface, boost::asio::buffer(buffer, remainingBytes),
boost::asio::transfer_at_least(1), readLambda);
Service.restart();
while(Service.run_one())
{
if(readResult)
{
timer.cancel();
}
else if(timerResult)
{
COMInterface.cancel(ErrorCode);
if(ErrorCode)
{
return !readData.empty();  
}
}
}
if(readResult&&*readResult&&*readResult!=boost::asio::error::eof)
{
ErrorCode=*readResult;
return !readData.empty();  
}
if(bytesRead > 0)
{
buffer.resize(bytesRead);
readData.append(buffer);
}
}
}
return !readData.empty()||requestedSize==0;
}
else
{
try
{
return readBLECharacteristic(BLEInterface, readData);
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return false;
}
}
}
bool BTDevice::Read(std::string& readData, char readUntil)
{
ErrorCode.clear();
if(!(isOpened()||(!isOpened()&&Open())))
{
if(!ErrorCode)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
}
return false;
}
readData.clear();
if(COMInterface.is_open())
{
const std::size_t availableBytes=getAvailableBytes();
if(availableBytes > 0)
{
std::string buffer(availableBytes, '\0');
boost::system::error_code errorCode;
try
{
std::size_t bytesRead=COMInterface.read_some(boost::asio::buffer(buffer), errorCode);
if(errorCode)
{
ErrorCode=errorCode;
return false;
}
buffer.resize(bytesRead);
size_t terminatorPos=buffer.find(readUntil);
if(terminatorPos!=std::string::npos)
{
readData.append(buffer, 0, terminatorPos + 1);
return true;
}
readData.append(buffer);
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return false;
}
}
if(Timeout==std::chrono::milliseconds::zero())
{
while(true)
{
boost::system::error_code errorCode;
char singleChar;
try
{
size_t bytesRead=boost::asio::read(COMInterface, boost::asio::buffer(&singleChar, 1), errorCode);
if(errorCode)
{
ErrorCode=errorCode;
return !readData.empty();  
}
if(bytesRead > 0)
{
readData.push_back(singleChar);
if(singleChar==readUntil)
{
return true;
}
}
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return !readData.empty();  
}
}
}
else
{
boost::asio::steady_timer timer(Service);
bool timeoutOccurred=false;
auto waitLambda=[&](const boost::system::error_code& state)
{
if(!state)
{
timeoutOccurred=true;
COMInterface.cancel(ErrorCode);
}
};
timer.expires_after(Timeout);
timer.async_wait(waitLambda);
while(!timeoutOccurred)
{
boost::optional<boost::system::error_code> readResult;
char singleChar;
bool charRead=false;
auto readLambda=[&](const boost::system::error_code& state, std::size_t bytes)
{
readResult.reset(state);
charRead=(bytes > 0);
};
COMInterface.async_read_some(boost::asio::buffer(&singleChar, 1), readLambda);
Service.restart();
Service.run_one();
if(!readResult||*readResult)
{
if(readResult)
{
ErrorCode=*readResult;
}
break;
}
if(charRead)
{
readData.push_back(singleChar);
if(singleChar==readUntil)
{
timer.cancel();
return true;
}
}
}
}
return !readData.empty();
}
else
{
if(readBLECharacteristic(BLEInterface, readData))
{
size_t terminatorPos=readData.find(readUntil);
if(terminatorPos!=std::string::npos)
{
readData.resize(terminatorPos + 1);
}
return !readData.empty();
}
return false;
}
}
bool BTDevice::Write(const std::string& writeData)
{
ErrorCode.clear();
if(writeData.empty())
{
return true;
}
if(!(isOpened()||(!isOpened()&&Open())))
{
if(!ErrorCode)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
}
return false;
}
if(COMInterface.is_open())
{
if(Timeout==std::chrono::milliseconds::zero())
{
try
{
size_t bytesWritten=boost::asio::write(COMInterface, boost::asio::buffer(writeData), ErrorCode);
if(ErrorCode)
{
return false;
}
return bytesWritten==writeData.size();
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return false;
}
}
else
{
boost::asio::steady_timer timer(Service);
boost::optional<boost::system::error_code> timerResult, writeResult;
size_t bytesWritten=0;
timer.expires_after(Timeout);
auto waitLambda=[&](const boost::system::error_code& state)
{
timerResult.reset(state);
};
auto writeLambda=[&](const boost::system::error_code& state, size_t bytes)
{
writeResult.reset(state);
bytesWritten=bytes;
};
timer.async_wait(waitLambda);
boost::asio::async_write(COMInterface, boost::asio::buffer(writeData), writeLambda);
Service.restart();
Service.run();
if(writeResult&&*writeResult)
{
ErrorCode=*writeResult;
return false;
}
if(timerResult&&!writeResult)
{
COMInterface.cancel(ErrorCode);
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::timed_out);
return false;
}
return bytesWritten==writeData.size();
}
}
else
{
try
{
bool success=true;
auto writeLambda=[&]()
{
success=writeBLECharacteristic(BLEInterface, writeData);
if(!success)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
}
};
return ExecuteWithTimeout(Timeout, writeLambda)&&success;
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return false;
}
}
}
bool BTDevice::WriteRead(const std::string& writeData, std::string& readData,
const std::chrono::milliseconds pauseDuration)
{
ErrorCode.clear();
if(!Write(writeData))
{
return false;
}
std::this_thread::sleep_for(pauseDuration);
if(!Write("\n"))
{
return false;
}
std::this_thread::sleep_for(pauseDuration);
return Read(readData);
}
bool BTDevice::WriteRead(const std::string& writeData, std::string& readData, char readUntil,
const std::chrono::milliseconds pauseDuration)
{
ErrorCode.clear();
if(!Write(writeData))
{
return false;
}
std::this_thread::sleep_for(pauseDuration);
if(!Write("\n"))
{
return false;
}
std::this_thread::sleep_for(pauseDuration);
return Read(readData, readUntil);
}
std::size_t BTDevice::getAvailableBytes() const
{
if(!isOpened())
{
return 0;
}
if(COMInterface.is_open())
{
COMSTAT status{};
DWORD errors=0;
auto handle=const_cast<boost::asio::serial_port&>(COMInterface).native_handle();
if(ClearCommError(reinterpret_cast<HANDLE>(handle), &errors, &status))
{
return status.cbInQue;
}
}
else
{
try
{
SimpleBLE::Peripheral& nonConstInterface=const_cast<SimpleBLE::Peripheral&>(BLEInterface);
if(nonConstInterface.is_connected())
{
return getBLEAvailableBytes(nonConstInterface);
}
}
catch(...)
{
}
}
return 0;
}
std::string BTDevice::getDevicePath() const
{
return DevicePath;
}
boost::system::error_code BTDevice::getErrorCode() const
{
return ErrorCode;
}
std::chrono::milliseconds BTDevice::getTimeout() const
{
return Timeout;
}
bool BTDevice::setDevicePath(const std::string& devicePath)
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
bool BTDevice::setTimeout(std::chrono::milliseconds waitTimeMs)
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
bool BTDevice::setTimeout(std::chrono::milliseconds::rep waitTimeMs)
{
return setTimeout(std::chrono::milliseconds(waitTimeMs));
}
bool BTDevice::isClosed() const
{
return !isOpened();
}
bool BTDevice::isLowEnergy() const
{
return DevicePath.find("COM")==std::string::npos;
}
bool BTDevice::isOpened() const
{
bool comOpen=COMInterface.is_open();
bool bleOpen=false;
try
{
SimpleBLE::Peripheral& nonConstInterface=const_cast<SimpleBLE::Peripheral&>(BLEInterface);
bleOpen=nonConstInterface.is_connected();
}
catch(...)
{  
}
return comOpen||bleOpen;
}
bool BTDevice::isValid() const
{
return isValidDevicePath(DevicePath)&&isValidTimeout(Timeout)&&!ErrorCode;
}
std::string BTDevice::toString() const
{
if(ErrorCode)
{
return Format("Bluetooth device: \"{}\" with timeout of {} ms. with error code \"{}\"",
DevicePath, Timeout.count(), ErrorCode.message());
}
return Format("Bluetooth device: \"{}\" with timeout of {} ms. without an error code",
DevicePath, Timeout.count());
}
std::vector<std::string> BTDevice::getActiveSystemDevices()
{
std::vector<std::string> devices;
std::string nameBuffer(0x3FFF, '\0');
for(std::size_t index=1; index <=255; ++index)
{
std::string portName="COM" + std::to_string(index);
if(QueryDosDevice(portName.c_str(), &nameBuffer[0], static_cast<DWORD>(nameBuffer.size())))
{
const std::string keyPath("HARDWARE\\DEVICEMAP\\SERIALCOMM");
HKEY registryKey;
if(RegOpenKeyEx(HKEY_LOCAL_MACHINE, keyPath.c_str(), 0, KEY_READ, &registryKey)==ERROR_SUCCESS)
{
DWORD totalValues=0;
if(RegQueryInfoKey(registryKey, nullptr, nullptr, nullptr, nullptr, nullptr, nullptr,
&totalValues, nullptr, nullptr, nullptr, nullptr)==ERROR_SUCCESS)
{
for(DWORD i=0; i < totalValues; ++i)
{
std::string value(0x3FFF, '\0'), data(0x3FFF, '\0');
DWORD valueLength=static_cast<DWORD>(value.size());
DWORD dataLength=static_cast<DWORD>(data.size());
if(RegEnumValue(registryKey, i, &value[0], &valueLength, nullptr, nullptr,
reinterpret_cast<LPBYTE>(&data[0]), &dataLength)==ERROR_SUCCESS)
{
value.resize(valueLength);
data.resize(dataLength - 1);  
if(value.find("BTHMODE")!=std::string::npos&&data==portName)
{
devices.push_back(portName);
break;
}
}
}
}
RegCloseKey(registryKey);
}
}
}
try
{
auto adapters=SimpleBLE::Adapter::get_adapters();
if(!adapters.empty())
{
auto& adapter=adapters[0];
std::function<void(SimpleBLE::Peripheral)> scanCallback=[&devices](SimpleBLE::Peripheral peripheral)
{
devices.push_back(peripheral.address());
};
adapter.set_callback_on_scan_found(scanCallback);
adapter.scan_for(1000);  
}
}
catch(...)
{
}
return devices;
}
std::string BTDevice::getDefaultDevicePath()
{
return std::string{};
}
std::chrono::milliseconds BTDevice::getDefaultTimeout()
{
return std::chrono::milliseconds::zero();
}
bool BTDevice::isValidDevicePath(const std::string& devicePath)
{
if(devicePath.empty())
{
return false;
}
if(devicePath.find("COM")!=std::string::npos)
{
try
{
static const std::regex comRegex("^COM([1-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
std::regex::icase);
return std::regex_match(devicePath, comRegex);
}
catch(...)
{
return devicePath.substr(0, 3)=="COM"&&devicePath.size() > 3;
}
}
try
{
static const std::regex bleRegex("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
return std::regex_match(devicePath, bleRegex);
}
catch(...)
{
return devicePath.size()==17;  
}
}
bool BTDevice::isValidTimeout(std::chrono::milliseconds durationMs)
{
return durationMs >=std::chrono::milliseconds::zero();
}
bool BTDevice::isValidTimeout(std::chrono::milliseconds::rep durationMs)
{
return durationMs >=0;
}
bool connectBLEDevice(SimpleBLE::Peripheral& peripheral, const std::string& address)
{
try
{
auto adapters=SimpleBLE::Adapter::get_adapters();
if(adapters.empty())
{
return false;
}
auto& adapter=adapters[0];
bool deviceFound=false;
SimpleBLE::Peripheral targetPeripheral;
std::function<void(SimpleBLE::Peripheral)> scanCallback=[&deviceFound, &targetPeripheral, &address](SimpleBLE::Peripheral p)
{
if(p.address()==address)
{
deviceFound=true;
targetPeripheral=p;
}
};
adapter.set_callback_on_scan_found(scanCallback);
adapter.scan_for(5000);  
adapter.scan_stop();
if(!deviceFound)
{
return false;
}
targetPeripheral.connect();
if(!targetPeripheral.is_connected())
{
return false;
}
peripheral=std::move(targetPeripheral);
return true;
}
catch(...)
{
return false;
}
}
bool writeBLECharacteristic(SimpleBLE::Peripheral& peripheral, const std::string& data)
{
if(!peripheral.is_connected())
{
return false;
}
try
{
auto services=peripheral.services();
if(services.empty())
{
return false;
}
auto& service=services[0];
auto characteristics=service.characteristics();
if(characteristics.empty())
{
return false;
}
for(auto& characteristic : characteristics)
{
try
{
std::string bytes(data.begin(), data.end());
peripheral.write_request(service.uuid(), characteristic.uuid(), bytes);
return true;
}
catch(...)
{
continue;
}
}
return false;
}
catch(...)
{
return false;
}
}
bool readBLECharacteristic(SimpleBLE::Peripheral& peripheral, std::string& data)
{
if(!peripheral.is_connected())
{
return false;
}
try
{
auto services=peripheral.services();
if(services.empty())
{
return false;
}
auto& service=services[0];
auto characteristics=service.characteristics();
if(characteristics.empty())
{
return false;
}
for(auto& characteristic : characteristics)
{
try
{
std::string bytes=peripheral.read(service.uuid(), characteristic.uuid());
data=bytes;
return true;
}
catch(...)
{
continue;
}
}
return false;
}
catch(...)
{
return false;
}
}
size_t getBLEAvailableBytes(SimpleBLE::Peripheral& peripheral)
{
if(!peripheral.is_connected())
{
return 0;
}
try
{
auto services=peripheral.services();
if(services.empty())
{
return 0;
}
auto& service=services[0];
auto characteristics=service.characteristics();
if(characteristics.empty())
{
return 0;
}
for(auto& characteristic : characteristics)
{
try
{
auto bytes=peripheral.read(service.uuid(), characteristic.uuid());
return bytes.size();
}
catch(...)
{
continue;
}
}
}
catch(...)
{
}
return 0;
}
}
