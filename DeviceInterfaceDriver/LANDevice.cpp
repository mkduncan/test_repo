#include "LANDevice.hpp"
#include <boost/optional.hpp>
#include <regex>
namespace Cognitive
{
LANDevice::LANDevice() : Service{}, Interface{Service}, ErrorCode{},
Timeout{getDefaultTimeout()}, Address{getDefaultAddress()}, Port{getDefaultPort()}
{
}
LANDevice::~LANDevice()
{
Close();
Reset();
}
LANDevice::LANDevice(LANDevice&&moveValue) : Service{}, Interface{Service}, ErrorCode{moveValue.ErrorCode},
Timeout{std::exchange(moveValue.Timeout, getDefaultTimeout())}, Address{std::move(moveValue.Address)},
Port{std::exchange(moveValue.Port, getDefaultPort())}
{
moveValue.ErrorCode.clear();
if(moveValue.Interface.is_open())
{
if(Interface.is_open())
{
Interface.close(ErrorCode);
}
Address=std::move(moveValue.Address);
Port=moveValue.Port;
if(!Open())
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
}
moveValue.Interface.close();
}
}
LANDevice& LANDevice::operator=(LANDevice&&moveValue)
{
if(this!=&moveValue)
{
Close();
ErrorCode=moveValue.ErrorCode;
Timeout=std::exchange(moveValue.Timeout, getDefaultTimeout());
Address=std::move(moveValue.Address);
Port=std::exchange(moveValue.Port, getDefaultPort());
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
bool LANDevice::Close()
{
ErrorCode.clear();
if(!Interface.is_open())
{
return true;
}
Interface.cancel(ErrorCode);
const bool success=ExecuteWithTimeout(Timeout, [&]()
{
Interface.close(ErrorCode);
});
return success&&!ErrorCode;
}
bool LANDevice::Flush()
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
std::size_t available=getAvailableBytes();
if(available > 0)
{
std::string buffer(available, '\0');
boost::system::error_code tempError;
Interface.read_some(boost::asio::buffer(buffer), tempError);
}
return true;
}
bool LANDevice::Open()
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
Interface=boost::asio::ip::tcp::socket(Service);
boost::asio::ip::address address;
try
{
address=boost::asio::ip::make_address(Address=="localhost" ? "127.0.0.1" : Address);
}
catch(const boost::system::system_error& errorStatus)
{
ErrorCode=errorStatus.code();
return false;
}
boost::asio::ip::tcp::endpoint endpoint(address, Port);
if(Timeout==std::chrono::milliseconds::zero())
{
Interface.connect(endpoint, ErrorCode);
if(ErrorCode)
{
Interface.close();
return false;
}
}
else
{
boost::optional<boost::system::error_code> timerResult, connectResult;
boost::asio::steady_timer timer(Service, Timeout);
timer.async_wait([&](const boost::system::error_code& state)
{
timerResult.reset(state);
if(!state&&!connectResult)
{
Interface.cancel();
}
});
Interface.async_connect(endpoint, [&](const boost::system::error_code& state)
{
connectResult.reset(state);
timer.cancel();
});
Service.restart();
Service.run();
if(!connectResult||*connectResult)
{
ErrorCode=connectResult ? *connectResult
: boost::system::errc::make_error_code(boost::system::errc::timed_out);
Interface.close();
return false;
}
}
return true;
}
bool LANDevice::Reopen()
{
ErrorCode.clear();
if(isOpened()&&!Close())
{
return false;
}
return Open();
}
bool LANDevice::Reset()
{
ErrorCode.clear();
if(Interface.is_open())
{
Interface.close(ErrorCode);
}
Interface=boost::asio::ip::tcp::socket(Service);
Timeout=getDefaultTimeout();
Address.assign(getDefaultAddress());
Port=getDefaultPort();
return !ErrorCode;
}
bool LANDevice::Read(std::string& readData)
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
Clear(readData);
const std::size_t availableBytes=getAvailableBytes();
if(availableBytes > 0)
{
const std::size_t bytesToRead=std::min(availableBytes, requestedSize);
std::string buffer(bytesToRead, '\0');
std::size_t bytesRead=0;
try
{
bytesRead=Interface.read_some(boost::asio::buffer(buffer), ErrorCode);
if(ErrorCode)
{
return false;
}
Resize(buffer, bytesRead);
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
std::string buffer(remainingBytes, '\0');
if(Timeout==std::chrono::milliseconds::zero())
{
try
{
std::size_t bytesRead=boost::asio::read(Interface, boost::asio::buffer(buffer),
boost::asio::transfer_at_least(remainingBytes), ErrorCode);
if(ErrorCode&&ErrorCode!=boost::asio::error::eof)
{
return !readData.empty();  
}
buffer.resize(bytesRead);
readData.append(buffer);
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return !readData.empty();  
}
}
else
{
boost::optional<boost::system::error_code> timerResult, readResult;
boost::asio::steady_timer timer(Service, Timeout);
std::size_t bytesRead=0;
timer.expires_after(Timeout);
timer.async_wait([&](const boost::system::error_code& state)
{
timerResult.reset(state);
});
boost::asio::async_read(Interface, boost::asio::buffer(buffer, remainingBytes),
boost::asio::transfer_at_least(1), [&](const boost::system::error_code& state, std::size_t bytes)
{
readResult.reset(state);
bytesRead=bytes;
});
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
bool LANDevice::Read(std::string& readData, char readUntil)
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
try
{
std::size_t bytesRead=Interface.read_some(boost::asio::buffer(buffer), ErrorCode);
if(ErrorCode)
{
return false;
}
Resize(buffer, bytesRead);
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
try
{
char singleChar;
size_t bytesRead=boost::asio::read(Interface, boost::asio::buffer(&singleChar, 1), ErrorCode);
if(ErrorCode)
{
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
boost::asio::steady_timer timer(Service, Timeout);
bool timeoutOccurred=false;
timer.expires_after(Timeout);
timer.async_wait([&](const boost::system::error_code& state)
{
if(!state)
{
timeoutOccurred=true;
Interface.cancel(ErrorCode);
}
});
while(!timeoutOccurred)
{
boost::optional<boost::system::error_code> readResult;
char singleChar;
bool charRead=false;
Interface.async_read_some(boost::asio::buffer(&singleChar, 1),
[&](const boost::system::error_code& state, std::size_t bytes)
{
readResult.reset(state);
charRead=(bytes > 0);
});
Service.restart();
Service.run_one();
if(!readResult||*readResult)
{
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
bool LANDevice::Write(const std::string& writeData)
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
size_t bytesWritten=0;
try
{
if(Timeout==std::chrono::milliseconds::zero())
{
bytesWritten=boost::asio::write(Interface, boost::asio::buffer(writeData), ErrorCode);
if(ErrorCode)
{
return false;
}
}
else
{
boost::asio::steady_timer timer(Service, Timeout);
bool writeCompleted=false;
boost::asio::async_write(Interface, boost::asio::buffer(writeData),
[&](const boost::system::error_code& state, size_t bytes)
{
ErrorCode=state;
bytesWritten=bytes;
writeCompleted=true;
timer.cancel();
});
timer.async_wait([&](const boost::system::error_code& state)
{
if(!writeCompleted&&!state)
{
Interface.cancel(ErrorCode);
}
});
Service.restart();
Service.run();
if(!writeCompleted||ErrorCode)
{
return false;
}
}
}
catch(...)
{
ErrorCode=boost::system::errc::make_error_code(boost::system::errc::io_error);
return false;
}
return bytesWritten==writeData.size();
}
bool LANDevice::WriteRead(const std::string& writeData, std::string& readData,
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
bool LANDevice::WriteRead(const std::string& writeData, std::string& readData, char readUntil,
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
std::size_t LANDevice::getAvailableBytes() const
{
if(Interface.is_open())
{
boost::asio::socket_base::bytes_readable command;
boost::system::error_code tempError;
const_cast<boost::asio::ip::tcp::socket&>(Interface).io_control(command, tempError);
if(!tempError)
{
return command.get();
}
}
return 0;
}
std::string LANDevice::getAddress() const
{
return Address;
}
std::uint16_t LANDevice::getPort() const
{
return Port;
}
bool LANDevice::getEndpoint(std::string& address, std::uint16_t& port) const
{
address.assign(Address);
port=Port;
return true;
}
bool LANDevice::getEndpoint(std::uint16_t& port, std::string& address) const
{
address.assign(Address);
port=Port;
return true;
}
boost::system::error_code LANDevice::getErrorCode() const
{
return ErrorCode;
}
std::chrono::milliseconds LANDevice::getTimeout() const
{
return Timeout;
}
bool LANDevice::setAddress(const std::string& address)
{
ErrorCode.clear();
if(!isValidAddress(address))
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
Address.assign(address=="localhost" ? "127.0.0.1" : address);
return true;
}
bool LANDevice::setPort(const std::uint16_t port)
{
ErrorCode.clear();
if(!isValidPort(port))
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
Port=port;
return true;
}
bool LANDevice::setEndpoint(std::uint16_t port, const std::string& address)
{
return setEndpoint(address, port);
}
bool LANDevice::setEndpoint(const std::string& address, std::uint16_t port)
{
ErrorCode.clear();
if(!isValidEndpoint(address, port))
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
Address.assign(address=="localhost" ? "127.0.0.1" : address);
Port=port;
return true;
}
bool LANDevice::setTimeout(std::chrono::milliseconds waitTimeMs)
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
bool LANDevice::setTimeout(std::chrono::milliseconds::rep waitTimeMs)
{
return setTimeout(std::chrono::milliseconds(waitTimeMs));
}
std::string LANDevice::toString() const
{
if(ErrorCode)
{
return Format("LAN device: \"{}:{}\" with timeout of {} ms. with error code \"{}\"",
Address, Port, Timeout.count(), ErrorCode.message());
}
return Format("LAN device: \"{}:{}\" with timeout of {} ms. without an error code",
Address, Port, Timeout.count());
}
bool LANDevice::isClosed() const
{
return !Interface.is_open();
}
bool LANDevice::isOpened() const
{
return Interface.is_open();
}
bool LANDevice::isValid() const
{
return isValidTimeout(Timeout)&&isValidAddress(Address)&&isValidPort(Port)&&!ErrorCode;
}
std::vector<std::string> LANDevice::getActiveSystemInterfaces()
{
std::vector<std::string> interfaces;
return interfaces;
}
std::string LANDevice::getDefaultAddress()
{
return std::string{};
}
std::uint16_t LANDevice::getDefaultPort()
{
return 9100;  
}
bool LANDevice::getDefaultEndpoint(std::string& address, std::uint16_t& port)
{
address=getDefaultAddress();
port=getDefaultPort();
return true;
}
bool LANDevice::getDefaultEndpoint(std::uint16_t& port, std::string& address)
{
address=getDefaultAddress();
port=getDefaultPort();
return true;
}
std::chrono::milliseconds LANDevice::getDefaultTimeout()
{
return std::chrono::milliseconds::zero();
}
bool LANDevice::isValidAddress(const std::string& address)
{
if(address.empty())
{
return false;
}
if(address=="localhost")
{
return true;
}
return std::regex_match(address, std::regex(R"(^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?)"
R"([0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$)"));
}
bool LANDevice::isValidPort(std::uint16_t port)
{
return port <=65535;  
}
bool LANDevice::isValidEndpoint(const std::string& address, std::uint16_t port)
{
return isValidAddress(address)&&isValidPort(port);
}
bool LANDevice::isValidEndpoint(std::uint16_t port, const std::string& address)
{
return isValidAddress(address)&&isValidPort(port);
}
bool LANDevice::isValidTimeout(std::chrono::milliseconds durationMs)
{
return durationMs >=std::chrono::milliseconds::zero();
}
bool LANDevice::isValidTimeout(std::chrono::milliseconds::rep durationMs)
{
return durationMs >=0;
}
}
