#ifndef USBDevice_H
#define USBDevice_H
#include <boost/asio.hpp>
#include "Device.hpp"
#include "Utility.hpp"
namespace Cognitive
{
class USBDevice : public Device
{
public:
USBDevice();
~USBDevice();
USBDevice(USBDevice&&moveValue);
USBDevice& operator=(USBDevice&&moveValue);
USBDevice(const USBDevice&)=delete;
USBDevice& operator=(const USBDevice&)=delete;
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
const std::string& getDevicePath() const;
boost::system::error_code getErrorCode() const;
std::chrono::milliseconds getTimeout() const;
bool setDevicePath(const std::string& devicePath);
bool setTimeout(std::chrono::milliseconds waitTimeMs);
bool setTimeout(std::chrono::milliseconds::rep waitTimeMs);
bool isClosed() const;
bool isOpened() const;
bool isValid() const;
std::string toString() const;
static std::vector<std::string> getActiveSystemDevices();
static std::vector<std::string> getActiveSystemDevices(uint16_t vendorId, uint16_t productId);
static std::string getDefaultDevicePath();
static std::chrono::milliseconds getDefaultTimeout();
static bool isValidDevicePath(const std::string& devicePath);
static bool isValidTimeout(std::chrono::milliseconds durationMs);
static bool isValidTimeout(std::chrono::milliseconds::rep durationMs);
public:
std::size_t Interface;
boost::system::error_code ErrorCode;
std::chrono::milliseconds Timeout;
std::string DevicePath;
};
}
#endif
