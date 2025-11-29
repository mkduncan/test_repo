#ifndef Device_H
#define Device_H
#include <boost/system/error_code.hpp>
#include <chrono>
#include <string>
#include <vector>
namespace Cognitive
{
class Device
{
public:
Device()=default;
virtual ~Device()=default;
Device(const Device&)=delete;
Device(Device&&)=delete;
Device& operator=(const Device&)=delete;
Device& operator=(Device&&)=delete;
virtual bool Close()=0;
virtual bool Flush()=0;
virtual bool Open()=0;
virtual bool Reopen()=0;
virtual bool Reset()=0;
virtual bool Read(std::string& readData)=0;
virtual bool Read(std::string& readData, char readUntil)=0;
virtual bool Write(const std::string& writeData)=0;
virtual bool WriteRead(const std::string& writeData, std::string& readData,
const std::chrono::milliseconds pauseDuration=std::chrono::milliseconds{50})=0;
virtual bool WriteRead(const std::string& writeData, std::string& readData, char readUntil,
const std::chrono::milliseconds pauseDuration=std::chrono::milliseconds{50})=0;
virtual std::size_t getAvailableBytes() const=0;
virtual boost::system::error_code getErrorCode() const=0;
virtual std::chrono::milliseconds getTimeout() const=0;
virtual bool setTimeout(std::chrono::milliseconds waitTimeMs)=0;
virtual bool setTimeout(std::chrono::milliseconds::rep waitTimeMs)=0;
virtual bool isClosed() const=0;
virtual bool isOpened() const=0;
virtual bool isValid() const=0;
virtual std::string toString() const=0;
};
}
#endif
