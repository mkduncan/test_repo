#ifndef LANDevice_H
#define LANDevice_H
#include <boost/asio.hpp>
#include "Device.hpp"
#include "Utility.hpp"
namespace Cognitive
{
class LANDevice : public Device
{
public:
LANDevice();
~LANDevice();
LANDevice(LANDevice&&moveValue);
LANDevice& operator=(LANDevice&&moveValue);
LANDevice(const LANDevice&)=delete;
LANDevice& operator=(const LANDevice&)=delete;
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
std::string getAddress() const;
std::uint16_t getPort() const;
bool getEndpoint(std::string& address, std::uint16_t& port) const;
bool getEndpoint(std::uint16_t& port, std::string& address) const;
boost::system::error_code getErrorCode() const;
std::chrono::milliseconds getTimeout() const;
bool setAddress(const std::string& address);
bool setPort(const std::uint16_t port);
bool setEndpoint(const std::string& address, std::uint16_t port);
bool setEndpoint(std::uint16_t port, const std::string& address);
bool setTimeout(std::chrono::milliseconds waitTimeMs);
bool setTimeout(std::chrono::milliseconds::rep waitTimeMs);
bool isClosed() const;
bool isOpened() const;
bool isValid() const;
std::string toString() const;
static std::vector<std::string> getActiveSystemInterfaces();
static std::string getDefaultAddress();
static std::uint16_t getDefaultPort();
static bool getDefaultEndpoint(std::string& address, std::uint16_t& port);
static bool getDefaultEndpoint(std::uint16_t& port, std::string& address);
static std::chrono::milliseconds getDefaultTimeout();
static bool isValidAddress(const std::string& address);
static bool isValidPort(std::uint16_t port);
static bool isValidEndpoint(const std::string& address, std::uint16_t port);
static bool isValidEndpoint(std::uint16_t port, const std::string& address);
static bool isValidTimeout(std::chrono::milliseconds durationMs);
static bool isValidTimeout(std::chrono::milliseconds::rep durationMs);
private:
boost::asio::io_context Service;
boost::asio::ip::tcp::socket Interface;
boost::system::error_code ErrorCode;
std::chrono::milliseconds Timeout;
std::string Address;
std::uint16_t Port;
};
}
#endif
