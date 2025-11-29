#ifndef LPTDevice_H
#define LPTDevice_H
#include <boost/asio.hpp>
#include "Device.hpp"
#include "Utility.hpp"
namespace Cognitive
{
class LPTDevice : public Device
{
public:
LPTDevice();
~LPTDevice();
LPTDevice(LPTDevice&&moveValue);
LPTDevice& operator=(LPTDevice&&moveValue);
LPTDevice(const LPTDevice&)=delete;
LPTDevice& operator=(const LPTDevice&)=delete;
bool Close();
bool Flush();
bool Open();
bool Reopen();
bool Reset();
bool Read(std::string& readData);
bool Read(std::string& readData, char readUntil);
bool Write(const std::string& writeData);
bool WriteRead(const std::string& writeData, std::string& readData,
const std::chrono::milliseconds pauseDuration=std::chrono::milliseconds{50});
bool WriteRead(const std::string& writeData, std::string& readData, char readUntil,
const std::chrono::milliseconds pauseDuration=std::chrono::milliseconds{50});
std::size_t getAvailableBytes() const;
std::string getPortName() const;
boost::system::error_code getErrorCode() const;
std::chrono::milliseconds getTimeout() const;
bool setPortName(const std::string& portName);
bool setTimeout(std::chrono::milliseconds waitTimeMs);
bool setTimeout(std::chrono::milliseconds::rep waitTimeMs);
bool isClosed() const;
bool isOpened() const;
bool isValid() const;
std::string toString() const;
static std::vector<std::string> getActiveSystemPorts();
static std::string getDefaultPortName();
static std::chrono::milliseconds getDefaultTimeout();
static bool isValidPortName(const std::string& portName);
static bool isValidTimeout(std::chrono::milliseconds durationMs);
static bool isValidTimeout(std::chrono::milliseconds::rep durationMs);
private:
boost::asio::io_context Service;
boost::asio::windows::stream_handle Interface;
boost::system::error_code ErrorCode;
std::chrono::milliseconds Timeout;
std::string PortName;
};
}
#endif
