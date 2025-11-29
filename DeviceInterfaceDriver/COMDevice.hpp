#ifndef COMDevice_H
#define COMDevice_H
#include <boost/asio.hpp>
#include "Device.hpp"
#include "Utility.hpp"
namespace Cognitive
{
class COMDevice : public Device
{
public:
using Option=boost::asio::serial_port_base;
COMDevice();
~COMDevice();
COMDevice(COMDevice&&moveValue);
COMDevice& operator=(COMDevice&&moveValue);
COMDevice(const COMDevice&)=delete;
COMDevice& operator=(const COMDevice&)=delete;
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
Option::baud_rate getBaudRate() const;
Option::character_size getCharSize() const;
Option::flow_control getFlowControl() const;
Option::parity getParity() const;
Option::stop_bits getStopBits() const;
boost::system::error_code getErrorCode() const;
std::chrono::milliseconds getTimeout() const;
bool setPortName(const std::string& portName);
bool setBaudRate(Option::baud_rate baudRate);
bool setBaudRate(std::uint32_t baudRate);
bool setCharSize(Option::character_size charSize);
bool setCharSize(std::uint8_t charSize);
bool setFlowControl(Option::flow_control flowControl);
bool setParity(Option::parity parity);
bool setStopBits(Option::stop_bits stopBits);
bool setTimeout(std::chrono::milliseconds durationMs);
bool setTimeout(std::chrono::milliseconds::rep durationMs);
bool isClosed() const;
bool isOpened() const;
bool isValid() const;
std::string toString() const;
static std::vector<std::string> getActiveSystemPorts();
static Option::baud_rate getDefaultBaudRate();
static Option::character_size getDefaultCharSize();
static Option::flow_control getDefaultFlowControl();
static Option::parity getDefaultParity();
static std::string getDefaultPortName();
static Option::stop_bits getDefaultStopBits();
static std::chrono::milliseconds getDefaultTimeout();
static bool isValidBaudRate(Option::baud_rate baudRate);
static bool isValidBaudRate(std::uint32_t baudRate);
static bool isValidCharSize(Option::character_size charSize);
static bool isValidCharSize(std::uint8_t charSize);
static bool isValidFlowControl(Option::flow_control flowControl);
static bool isValidParity(Option::parity parity);
static bool isValidPortName(const std::string& portName);
static bool isValidStopBits(Option::stop_bits stopBits);
static bool isValidTimeout(std::chrono::milliseconds durationMs);
static bool isValidTimeout(std::chrono::milliseconds::rep durationMs);
private:
boost::asio::io_context Service;
boost::asio::serial_port Interface;
boost::system::error_code ErrorCode;
std::chrono::milliseconds Timeout;
std::string PortName;
Option::baud_rate BaudRate;
Option::character_size CharSize;
Option::flow_control FlowControl;
Option::parity Parity;
Option::stop_bits StopBits;
};
}
#endif
