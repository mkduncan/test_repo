#include "COMDevice.hpp"
#include <boost/optional.hpp>
#include <regex>
#include <shlwapi.h>
namespace Cognitive
{
COMDevice::COMDevice() : Service{}, Interface{Service}, ErrorCode{}, Timeout{getDefaultTimeout()},
PortName{getDefaultPortName()}, BaudRate{getDefaultBaudRate()}, CharSize{getDefaultCharSize()},
FlowControl{getDefaultFlowControl()}, Parity{getDefaultParity()}, StopBits{getDefaultStopBits()}
{
}
COMDevice::~COMDevice()
{
Close();
Reset();
}
COMDevice::COMDevice(COMDevice&&moveValue) : Service{}, Interface{Service}, ErrorCode{moveValue.ErrorCode},
Timeout{std::exchange(moveValue.Timeout, getDefaultTimeout())}, PortName{std::move(moveValue.PortName)},
BaudRate{std::exchange(moveValue.BaudRate, getDefaultBaudRate())},
CharSize{std::exchange(moveValue.CharSize, getDefaultCharSize())},
FlowControl{std::exchange(moveValue.FlowControl, getDefaultFlowControl())},
Parity{std::exchange(moveValue.Parity, getDefaultParity())},
StopBits{std::exchange(moveValue.StopBits, getDefaultStopBits())}
{
moveValue.ErrorCode.clear();
if(moveValue.Interface.is_open())
{
if(Interface.is_open())
{
Interface.close(ErrorCode);
}
PortName=std::move(moveValue.PortName);
if(!Open())
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
}
moveValue.Interface.close();
}
}
COMDevice& COMDevice::operator=(COMDevice&&moveValue)
{
if(this!=&moveValue)
{
Close();
Interface=std::move(moveValue.Interface);
ErrorCode=moveValue.ErrorCode;
Timeout=std::exchange(moveValue.Timeout, getDefaultTimeout());
PortName=std::move(moveValue.PortName);
BaudRate=std::exchange(moveValue.BaudRate, getDefaultBaudRate());
CharSize=std::exchange(moveValue.CharSize, getDefaultCharSize());
FlowControl=std::exchange(moveValue.FlowControl, getDefaultFlowControl());
Parity=std::exchange(moveValue.Parity, getDefaultParity());
StopBits=std::exchange(moveValue.StopBits, getDefaultStopBits());
moveValue.ErrorCode.clear();
if(moveValue.Interface.is_open())
{
if(!Open())
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
}
moveValue.Interface.close();
}
}
return *this;
}
bool COMDevice::Close()
{
ErrorCode.clear();
if(!Interface.is_open())
{
return true;
}
Interface.cancel(ErrorCode);
auto closeLambda=[&]()
{
Interface.close(ErrorCode);
};
const bool success=ExecuteWithTimeout(Timeout, closeLambda);
return success&&!ErrorCode;
}
bool COMDevice::Flush()
{
ErrorCode.clear();
if(!Interface.is_open()&&!Open())
{
if(!ErrorCode)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
}
return false;
}
const BOOL result=PurgeComm(reinterpret_cast<HANDLE>(Interface.native_handle()),
PURGE_RXABORT | PURGE_TXABORT | PURGE_RXCLEAR | PURGE_TXCLEAR);
return result==TRUE;
}
bool COMDevice::Open()
{
ErrorCode.clear();
if(!isValid())
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
if(Interface.is_open())
{
return true;
}
if(Interface.is_open())
{
Interface.close(ErrorCode);
if(ErrorCode)
{
return false;
}
}
Interface=boost::asio::serial_port(Service);
const bool attemptOpen=ExecuteWithTimeout(Timeout, [&]()
{
Interface.open(PortName, ErrorCode);
});
if(!attemptOpen||ErrorCode||!Interface.is_open())
{
Close();
return false;
}
if(Interface.set_option(BaudRate, ErrorCode)||Interface.set_option(CharSize, ErrorCode)||Interface.set_option(FlowControl, ErrorCode)||Interface.set_option(Parity, ErrorCode)||Interface.set_option(StopBits, ErrorCode))
{
Close();
return false;
}
return true;
}
bool COMDevice::Reopen()
{
ErrorCode.clear();
if(isOpened()&&!Close())
{
return false;
}
return Open();
}
bool COMDevice::Reset()
{
ErrorCode.clear();
if(Interface.is_open())
{
Interface.close(ErrorCode);
}
Interface=boost::asio::serial_port(Service);
Timeout=getDefaultTimeout();
PortName.assign(getDefaultPortName());
BaudRate=getDefaultBaudRate();
CharSize=getDefaultCharSize();
FlowControl=getDefaultFlowControl();
Parity=getDefaultParity();
StopBits=getDefaultStopBits();
return !ErrorCode;
}
bool COMDevice::Read(std::string& readData)
{
ErrorCode.clear();
if(!(Interface.is_open()||(!Interface.is_open()&&Open())))
{
if(!ErrorCode)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
}
return false;
}
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
std::size_t bytesRead=Interface.read_some(boost::asio::buffer(buffer), errorCode);
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
const std::size_t remainingBytes=requestedSize - readData.size();
std::string buffer(remainingBytes, '\0');
boost::system::error_code errorCode;
try
{
std::size_t bytesRead=boost::asio::read(Interface, boost::asio::buffer(buffer),
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
boost::asio::async_read(Interface, boost::asio::buffer(buffer, remainingBytes),
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
Interface.cancel(ErrorCode);
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
bool COMDevice::Read(std::string& readData, char readUntil)
{
ErrorCode.clear();
if(!(Interface.is_open()||(!Interface.is_open()&&Open())))
{
if(!ErrorCode)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
}
return false;
}
Clear(readData);
const std::size_t availableBytes=getAvailableBytes();
if(availableBytes > 0)
{
std::string buffer(availableBytes, '\0');
boost::system::error_code errorCode;
try
{
std::size_t bytesRead=Interface.read_some(boost::asio::buffer(buffer), errorCode);
if(errorCode)
{
ErrorCode=errorCode;
return false;
}
size_t terminatorPos=Resize(buffer, bytesRead).find(readUntil);
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
size_t bytesRead=boost::asio::read(Interface, boost::asio::buffer(&singleChar, 1), errorCode);
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
Interface.cancel(ErrorCode);
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
Interface.async_read_some(boost::asio::buffer(&singleChar, 1), readLambda);
Service.restart();
Service.run_one();
if(!readResult||*readResult)
{
ErrorCode=*readResult;
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
bool COMDevice::WriteRead(const std::string& writeData, std::string& readData,
const std::chrono::milliseconds pauseDuration)
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
bool COMDevice::WriteRead(const std::string& writeData, std::string& readData, char readUntil,
const std::chrono::milliseconds pauseDuration)
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
bool COMDevice::Write(const std::string& writeData)
{
ErrorCode.clear();
if(writeData.empty())
{
return true;
}
if(!(Interface.is_open()||(!Interface.is_open()&&Open())))
{
if(!ErrorCode)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
}
return false;
}
if(Timeout==std::chrono::milliseconds::zero())
{
try
{
size_t bytesWritten=boost::asio::write(Interface, boost::asio::buffer(writeData), ErrorCode);
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
boost::asio::async_write(Interface, boost::asio::buffer(writeData), writeLambda);
Service.restart();
Service.run();
if(writeResult&&*writeResult)
{
ErrorCode=*writeResult;
return false;
}
if(timerResult&&!writeResult)
{
Interface.cancel(ErrorCode);
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::timed_out);
return false;
}
return bytesWritten==writeData.size();
}
}
std::size_t COMDevice::getAvailableBytes() const
{
if(Interface.is_open())
{
COMSTAT status{};
DWORD errors=0;
auto handle=const_cast<boost::asio::serial_port&>(Interface).native_handle();
if(ClearCommError(reinterpret_cast<HANDLE>(handle), &errors, &status))
{
return status.cbInQue;
}
}
return 0;
}
std::string COMDevice::getPortName() const
{
return PortName;
}
COMDevice::Option::baud_rate COMDevice::getBaudRate() const
{
return BaudRate;
}
COMDevice::Option::character_size COMDevice::getCharSize() const
{
return CharSize;
}
COMDevice::Option::flow_control COMDevice::getFlowControl() const
{
return FlowControl;
}
COMDevice::Option::parity COMDevice::getParity() const
{
return Parity;
}
COMDevice::Option::stop_bits COMDevice::getStopBits() const
{
return StopBits;
}
boost::system::error_code COMDevice::getErrorCode() const
{
return ErrorCode;
}
std::chrono::milliseconds COMDevice::getTimeout() const
{
return Timeout;
}
bool COMDevice::setPortName(const std::string& portName)
{
ErrorCode.clear();
if(!isValidPortName(portName))
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
PortName.assign(portName);
return true;
}
bool COMDevice::setBaudRate(Option::baud_rate baudRate)
{
ErrorCode.clear();
if(!isValidBaudRate(baudRate))
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
BaudRate=baudRate;
if(Interface.is_open())
{
Interface.set_option(BaudRate, ErrorCode);
return !ErrorCode;
}
return true;
}
bool COMDevice::setBaudRate(std::uint32_t baudRate)
{
return setBaudRate(Option::baud_rate(baudRate));
}
bool COMDevice::setCharSize(Option::character_size charSize)
{
ErrorCode.clear();
if(!isValidCharSize(charSize))
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
CharSize=charSize;
if(Interface.is_open())
{
Interface.set_option(CharSize, ErrorCode);
return !ErrorCode;
}
return true;
}
bool COMDevice::setCharSize(std::uint8_t charSize)
{
return setCharSize(Option::character_size(charSize));
}
bool COMDevice::setFlowControl(Option::flow_control flowControl)
{
ErrorCode.clear();
if(!isValidFlowControl(flowControl))
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
FlowControl=flowControl;
if(Interface.is_open())
{
Interface.set_option(FlowControl, ErrorCode);
return !ErrorCode;
}
return true;
}
bool COMDevice::setParity(Option::parity parity)
{
ErrorCode.clear();
if(!isValidParity(parity))
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
Parity=parity;
if(Interface.is_open())
{
Interface.set_option(Parity, ErrorCode);
return !ErrorCode;
}
return true;
}
bool COMDevice::setStopBits(Option::stop_bits stopBits)
{
ErrorCode.clear();
if(!isValidStopBits(stopBits))
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
StopBits=stopBits;
if(Interface.is_open())
{
Interface.set_option(StopBits, ErrorCode);
return !ErrorCode;
}
return true;
}
bool COMDevice::setTimeout(std::chrono::milliseconds durationMs)
{
ErrorCode.clear();
if(!isValidTimeout(durationMs))
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::invalid_argument);
return false;
}
Timeout=durationMs;
return true;
}
bool COMDevice::setTimeout(std::chrono::milliseconds::rep durationMs)
{
return setTimeout(std::chrono::milliseconds(durationMs));
}
std::string COMDevice::toString() const
{
constexpr std::array<std::string_view, 4> flowControlStrings={"no", "hardware", "software", "unknown"},
parityStrings={"no", "even", "odd", "unknown"}, stopBitsStrings={"one", "1.5", "two", "unknown"};
const std::string flowControl{flowControlStrings.at(static_cast<std::size_t>(FlowControl.value()))},
parity{parityStrings.at(static_cast<std::size_t>(Parity.value()))},
stopBits{stopBitsStrings.at(static_cast<std::size_t>(StopBits.value()))};
if(ErrorCode)
{
return Format("COM device: \"{}\" with {} baud rate, {} character bits, {} flow control, {} parity, "
"{} stop bits, and a timeout of {} ms. with error code \"{}\"", PortName, BaudRate.value(),
CharSize.value(), flowControl, parity, stopBits, Timeout.count(), ErrorCode.message());
}
return Format("COM device: \"{}\" with {} baud rate, {} character bits, {} flow control, {} parity, "
"{} stop bits, and a timeout of {} ms. without an error code", PortName, BaudRate.value(),
CharSize.value(), flowControl, parity, stopBits, Timeout.count());
}
bool COMDevice::isClosed() const
{
return !Interface.is_open();
}
bool COMDevice::isOpened() const
{
return Interface.is_open();
}
bool COMDevice::isValid() const
{
return isValidTimeout(Timeout)&&isValidBaudRate(BaudRate)&&isValidCharSize(CharSize)&&isValidParity(Parity)&&isValidFlowControl(FlowControl)&&isValidPortName(PortName)&&isValidStopBits(StopBits)&&!ErrorCode;
}
std::vector<std::string> COMDevice::getActiveSystemPorts()
{
std::vector<std::string> validPorts, resultPorts;
std::string nameBuffer(0x3FFF, '\0'), validName;
for(std::size_t index=1; index <=256; ++index)
{
validName.assign("COM" + std::to_string(index));
if(QueryDosDevice(validName.c_str(), nameBuffer.data(), static_cast<DWORD>(nameBuffer.size())))
validPorts.emplace_back(std::move(validName));
}
Clear(nameBuffer);
Clear(validName);
if(!validPorts.empty())
{
const std::string keyPath("HARDWARE\\DEVICEMAP\\SERIALCOMM");
std::size_t resultPortsCount=validPorts.size();
DWORD totalValues=0, errorCode=0;
HKEY registryKey;
if(RegOpenKeyEx(HKEY_LOCAL_MACHINE, keyPath.data(), 0, KEY_READ, &registryKey)==ERROR_SUCCESS)
{
errorCode=RegQueryInfoKey(registryKey, nullptr, nullptr, nullptr, nullptr,
nullptr, nullptr, &totalValues, nullptr, nullptr, nullptr, nullptr);
if(errorCode==ERROR_SUCCESS)
{
for(DWORD index=0; index < totalValues&&resultPortsCount > 0; ++index)
{
std::string value(0x3FFF, '\0'), data(value);
DWORD valueLength=static_cast<DWORD>(value.size()), dataLength=static_cast<DWORD>(data.size());
if(RegEnumValue(registryKey, index, value.data(), &valueLength, nullptr, nullptr,
reinterpret_cast<LPBYTE>(data.data()), &dataLength)==ERROR_SUCCESS&&dataLength > 0)
{
Resize(data, dataLength - 1);
Resize(value, valueLength);
if(StrStrI(value.data(), "BTHMODE")!=nullptr)
{
for(std::size_t port=0; port < validPorts.size()&&resultPortsCount > 0; ++port)
{
if(data==validPorts.at(port))
{
Clear(validPorts.at(port));
--resultPortsCount;
}
}
}
}
}
}
RegCloseKey(registryKey);
}
if(resultPortsCount==validPorts.size())
{
return validPorts;
}
if(resultPortsCount > 0)
{
resultPorts.reserve(resultPortsCount);
for(std::string& port : validPorts)
{
if(!port.empty())
{
resultPorts.emplace_back(std::move(port));
}
}
}
}
return resultPorts;
}
COMDevice::Option::baud_rate COMDevice::getDefaultBaudRate()
{
return COMDevice::Option::baud_rate(115200);
}
COMDevice::Option::character_size COMDevice::getDefaultCharSize()
{
return COMDevice::Option::character_size(8);
}
COMDevice::Option::flow_control COMDevice::getDefaultFlowControl()
{
return COMDevice::Option::flow_control(COMDevice::Option::flow_control::none);
}
COMDevice::Option::parity COMDevice::getDefaultParity()
{
return COMDevice::Option::parity(COMDevice::Option::parity::none);
}
std::string COMDevice::getDefaultPortName()
{
return std::string{};
}
COMDevice::Option::stop_bits COMDevice::getDefaultStopBits()
{
return COMDevice::Option::stop_bits(COMDevice::Option::stop_bits::one);
}
std::chrono::milliseconds COMDevice::getDefaultTimeout()
{
return std::chrono::milliseconds::zero();
}
bool COMDevice::isValidBaudRate(Option::baud_rate baudRate)
{
return isValidBaudRate(baudRate.value());
}
bool COMDevice::isValidBaudRate(std::uint32_t baudRate)
{
return baudRate > 0&&baudRate <=921600;
}
bool COMDevice::isValidCharSize(Option::character_size charSize)
{
return isValidCharSize(charSize.value());
}
bool COMDevice::isValidCharSize(std::uint8_t charSize)
{
return charSize >=5&&charSize <=8;
}
bool COMDevice::isValidFlowControl(Option::flow_control flowControl)
{
return (flowControl.value()==Option::flow_control::none)||(flowControl.value()==Option::flow_control::software)||(flowControl.value()==Option::flow_control::hardware);
}
bool COMDevice::isValidParity(Option::parity parity)
{
return (parity.value()==Option::parity::none)||(parity.value()==Option::parity::odd)||(parity.value()==Option::parity::even);
}
bool COMDevice::isValidPortName(const std::string& portName)
{
if(portName.size() < 4)
{
return false;
}
return std::regex_match(portName, std::regex("^(\\\\\\\\[.]\\\\)?COM([1-9]|[1-8][0-9]"
"|9[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", std::regex_constants::icase));
}
bool COMDevice::isValidStopBits(Option::stop_bits stopBits)
{
return (stopBits.value()==Option::stop_bits::one)||(stopBits.value()==Option::stop_bits::onepointfive)||(stopBits.value()==Option::stop_bits::two);
}
bool COMDevice::isValidTimeout(std::chrono::milliseconds durationMs)
{
return durationMs >=std::chrono::milliseconds::zero();
}
bool COMDevice::isValidTimeout(std::chrono::milliseconds::rep durationMs)
{
return durationMs >=0;
}
}
