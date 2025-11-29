#include "Utility.hpp"
#include <format>
#include <random>
bool Cognitive::BlockThread(std::chrono::milliseconds duration)
{
if(duration > std::chrono::milliseconds::zero())
{
return (std::this_thread::sleep_for(duration), true);
}
return false;
}
bool Cognitive::BlockThread(std::chrono::milliseconds::rep duration)
{
return BlockThread(std::chrono::milliseconds{duration});
}
std::span<std::byte> Cognitive::StringToByteSpan(std::string& input)
{
return std::span<std::byte>{reinterpret_cast<std::byte*>(input.data()), input.size()};
}
std::span<const std::byte> Cognitive::StringToConstByteSpan(const std::string& input)
{
return {reinterpret_cast<const std::byte*>(input.data()), input.size()};
}
std::string Cognitive::AddNewlines(const std::string& input)
{
return '\n' + input + '\n';
}
std::string Cognitive::TrimNewlines(const std::string& input)
{
const std::size_t start=input.find_first_not_of('\n');
return (start==std::string::npos) ? std::string{} : input.substr(start, input.find_last_not_of('\n') - start + 1);
}
std::vector<std::string> Cognitive::SplitLines(const std::string& input)
{
std::vector<std::string> lines;
std::size_t start=0, end=0;
while((end=input.find('\n', start))!=std::string::npos)
{
lines.push_back(input.substr(start, end - std::exchange(start, end + 1)));
}
if(start < input.size())
{
lines.push_back(input.substr(start));
}
return lines;
}
