#ifndef Utility_H
#define Utility_H
#include <array>
#include <chrono>
#include <cstdint>
#include <filesystem>
#include <format>
#include <future>
#include <print>
#include <span>
#include <string>
#include <system_error>
#include <thread>
namespace Cognitive
{
template <typename F, typename... V> bool ExecuteWithTimeout(std::chrono::milliseconds wait, F&&function, V&&... inputs);
template <typename... V> std::string Format(const std::string& format, V&&... inputs);
template <typename... V> std::string ErrorFormat(std::int32_t line, const std::string& function,
const std::string& path, const std::string& format, V&&... inputs);
template <typename... V> bool Error(V&&... inputs);
template <typename T> T& Clear(T& container);
template <typename T> T& Resize(T& container, std::size_t length);
template <typename T> T& Shrink(T& container);
extern bool BlockThread(std::chrono::milliseconds duration);
extern bool BlockThread(std::chrono::milliseconds::rep duration);
extern std::span<std::byte> StringToByteSpan(std::string& input);
extern std::span<const std::byte> StringToConstByteSpan(const std::string& input);
extern std::string AddNewlines(const std::string& input);
extern std::string TrimNewlines(const std::string& input);
extern std::vector<std::string> SplitLines(const std::string& input);
}
template <typename F, typename... V> bool Cognitive::ExecuteWithTimeout(std::chrono::milliseconds maxWaitTime, F&&function, V&&... inputValues)
{
if(maxWaitTime.count()==0)
{
function(std::forward<V>(inputValues)...);
return true;
}
auto future=std::async(std::launch::async, std::forward<F>(function), std::forward<V>(inputValues)...);
if(future.wait_for(maxWaitTime)==std::future_status::ready)
{
future.get();
return true;
}
return false;
}
template <typename... V> std::string Cognitive::Format(const std::string& format, V&&... inputs)
{
return std::vformat(format, std::make_format_args(inputs...));
}
template <typename... V> std::string Cognitive::ErrorFormat(std::int32_t line, const std::string& function,
const std::string& path, const std::string& format, V&&... inputs)
{
const std::string formatString{"[ {:%m/%d/%Y %H:%M:%S} ] - " + format + " (line #{} in function {}() in file \"{}\")\n\n"};
const std::chrono::system_clock::time_point currentTime{std::chrono::system_clock::from_time_t(time(nullptr))};
const std::string fileName{std::filesystem::path(path).filename().string()};
return Format(formatString, currentTime, inputs..., line, function, fileName);
}
template <typename... V> bool Cognitive::Error(V&&... inputs)
{
return (std::print(stderr, "{}", ErrorFormat(inputs...)), false);
}
template <typename T> T& Cognitive::Clear(T& container)
{
return (container.clear(), Shrink(container));
}
template <typename T> T& Cognitive::Resize(T& container, std::size_t length)
{
return (container.resize(length), Shrink(container));
}
template <typename T> T& Cognitive::Shrink(T& container)
{
return (container.shrink_to_fit(), container);
}
#endif
