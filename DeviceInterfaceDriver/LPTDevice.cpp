#include "LPTDevice.hpp"
#include <boost/optional.hpp>
#include <regex>
#include <windows.h>
namespace Cognitive
{
LPTDevice::LPTDevice() :
Service{},
Interface{Service},
ErrorCode{},
Timeout{getDefaultTimeout()},
PortName{getDefaultPortName()}
{
}
LPTDevice::~LPTDevice()
{
Close();
Reset();
}
LPTDevice::LPTDevice(LPTDevice&&moveValue) :
Service{},
Interface{Service},
ErrorCode{moveValue.ErrorCode},
Timeout{std::exchange(moveValue.Timeout, getDefaultTimeout())},
PortName{std::move(moveValue.PortName)}
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
LPTDevice& LPTDevice::operator=(LPTDevice&&moveValue)
{
if(this!=&moveValue)
{
Close();
ErrorCode=moveValue.ErrorCode;
Timeout=std::exchange(moveValue.Timeout, getDefaultTimeout());
PortName=std::move(moveValue.PortName);
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
bool LPTDevice::Close()
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
bool LPTDevice::Flush()
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
std::string newline="\n";
if(!Write(newline))
{
return false;
}
return true;
}
bool LPTDevice::Open()
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
const std::string prefix("\\\\.\\");
const std::string portName(PortName.starts_with(prefix) ? PortName : (prefix + PortName));
HANDLE nativePortHandle=CreateFile(
portName.c_str(),
GENERIC_READ | GENERIC_WRITE,
FILE_SHARE_READ | FILE_SHARE_WRITE,
nullptr,
OPEN_EXISTING,
FILE_FLAG_OVERLAPPED,
nullptr
);
if(nativePortHandle==INVALID_HANDLE_VALUE)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::no_such_device);
return false;
}
if(Interface.assign(nativePortHandle, ErrorCode))
{
CloseHandle(nativePortHandle);
return false;
}
return true;
}
bool LPTDevice::Reopen()
{
ErrorCode.clear();
if(isOpened()&&!Close())
{
return false;
}
return Open();
}
bool LPTDevice::Reset()
{
ErrorCode.clear();
if(Interface.is_open())
{
Interface.close(ErrorCode);
}
Timeout=getDefaultTimeout();
PortName.assign(getDefaultPortName());
return !ErrorCode;
}
bool LPTDevice::Read(std::string& readData)
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
return true;
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
bool LPTDevice::Read(std::string& readData, char readUntil)
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
bool LPTDevice::Write(const std::string& writeData)
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
bool LPTDevice::WriteRead(const std::string& writeData, std::string& readData,
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
bool LPTDevice::WriteRead(const std::string& writeData, std::string& readData, char readUntil,
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
std::size_t LPTDevice::getAvailableBytes() const
{
return 0;
}
std::string LPTDevice::getPortName() const
{
return PortName;
}
boost::system::error_code LPTDevice::getErrorCode() const
{
return ErrorCode;
}
std::chrono::milliseconds LPTDevice::getTimeout() const
{
return Timeout;
}
bool LPTDevice::setPortName(const std::string& portName)
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
bool LPTDevice::setTimeout(std::chrono::milliseconds waitTimeMs)
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
bool LPTDevice::setTimeout(std::chrono::milliseconds::rep waitTimeMs)
{
return setTimeout(std::chrono::milliseconds(waitTimeMs));
}
bool LPTDevice::isClosed() const
{
return !Interface.is_open();
}
bool LPTDevice::isOpened() const
{
return Interface.is_open();
}
bool LPTDevice::isValid() const
{
return isValidTimeout(Timeout)&&isValidPortName(PortName)&&!ErrorCode;
}
std::string LPTDevice::toString() const
{
if(ErrorCode)
{
return Format("LPT device: \"{}\" with timeout of {} ms. and error code \"{}\"",
PortName, Timeout.count(), ErrorCode.message());
}
return Format("LPT device: \"{}\" with timeout of {} ms. without an error code",
PortName, Timeout.count());
}
std::vector<std::string> LPTDevice::getActiveSystemPorts()
{
std::vector<std::string> validPorts;
std::string nameBuffer(0x3FFF, '\0'), validName;
for(std::size_t index=1; index <=256; ++index)
{
validName.assign("LPT" + std::to_string(index));
if(QueryDosDevice(validName.c_str(), nameBuffer.data(), static_cast<DWORD>(nameBuffer.size())))
{
validPorts.emplace_back(std::move(validName));
}
}
Clear(nameBuffer);
Clear(validName);
return validPorts;
}
std::string LPTDevice::getDefaultPortName()
{
return std::string{};
}
std::chrono::milliseconds LPTDevice::getDefaultTimeout()
{
return std::chrono::milliseconds::zero();
}
bool LPTDevice::isValidPortName(const std::string& portName)
{
if(portName.empty())
{
return false;
}
return std::regex_match(portName, std::regex("^(\\\\\\\\[.]\\\\)?LPT([1-9]|[1-8][0-9]"
"|9[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", std::regex_constants::icase));
}
bool LPTDevice::isValidTimeout(std::chrono::milliseconds durationMs)
{
return durationMs >=std::chrono::milliseconds::zero();
}
bool LPTDevice::isValidTimeout(std::chrono::milliseconds::rep durationMs)
{
return durationMs >=0;
}
}
