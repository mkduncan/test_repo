# LLM Instructional Prompt
**Role:** Senior C++ Systems Architect, Hardware Driver Specialist, & GUI Engineer
**Objective:** Perform a high-fidelity, production-ready port of the legacy Java application "**JAdmin**" into a modern C++23 Desktop Application named "**CAdmin**". You must also perform an exhaustive audit of the provided context to ensure absolute functional parity and architectural superiority.
**Input Context:**
The following codebases are provided as context for this task:
1.  **`JAdmin`**: Legacy Java source containing business logic, CPL parsing, manufacturing tests, and original behavior. This is the "Source of Truth" for functionality.
2.  **`CAdmin`**: The current state of the C++23 project (if any exists), serving as the target structure.
3.  **`DeviceInterfaceDrivers`**: Reference C++ hardware drivers.
4. **`CPL_Commands.md`**: CPL programmer's guide for scripting commands that control label printer devices.
5. **`Showcase.html`**: Target user interface that should be rendered when drawing the C++ GUI.
---
## 1. Execution Strategy & Audit Protocol
You are required to perform a meticulous, exhaustive mental audit and analysis of the provided `JAdmin` codebase. Refactor, restructure, and reorganize this legacy logic in your mind to align with modern C++23 best practices, then implement the result in `CAdmin`.
*   **Audit Requirements:** Ensure the low-level hardware behavior, manufacturing test sequences, and parsing logic of `CAdmin` matches or exceeds `JAdmin`.
*   **Refactoring:** Do not simply transliterate Java to C++. Rewrite logic to utilize C++23 features (concepts, ranges, spans) and efficient memory management, while maintaining strict functional parity.
## 2. Core Architecture
The application operates as a **Hybrid Native/Web Client**:
*   **Frontend:** A `wxWidgets` window embedding a `wxWebView` (specifically using the **WebView2** backend on Windows) to host the `Showcase.html` interface.
*   **Backend:** High-performance C++23 business logic.
*   **Bridge:** Bidirectional JSON-RPC layer mapping JavaScript calls to C++ methods.
## 3. Strict Technical Constraints
*   **Language Standard:** C++23.
*   **Platform Targets:** Windows 10/11 (Primary), Linux (Debian/Fedora), macOS.
*   **Build System:** CMake.
*   **External Libraries:**
    *   `wxWidgets 3.2+` (GUI, WebView).
    *   `Boost` (Asio, Beast, JSON, FileSystem, System).
    *   `liblzma` (LZMA2 Compression).
    *   `libusb-1.0` (Cross-platform USB).
    *   **Note:** Prefer C++23 standard library over Boost where reasonable, *except* for the forbidden types below.
*   **Forbidden Types:** You must **NOT** use `std::optional`, `std::expected`, nor `std::any`. Use `boost::optional` or error codes/exceptions.
*   **Security:** All internal string literals (commands, keys) must be obfuscated at compile-time using a `consteval` XOR cipher macro (e.g., `OBF("string")`).
## 4. Hardware Abstraction Layer (HAL) Requirements
You must implement a unified `Cognitive::Device` interface. The low-level behavior must align with the following specific driver protocols:
*   **Bluetooth (`BTDevice`):**
    *   **Do NOT** use SimpleBLE or generic wrappers. You must use **Native Host OS APIs**.
    *   *Windows:* WinSock for Classic (RFCOMM/SPP); WinRT (`winrt::Windows::Devices::Bluetooth`) for BLE.
    *   *Linux:* BlueZ via DBus.
    *   *macOS:* IOBluetooth framework.
    *   Support **both** Classic (SPP) and LE (GATT) in a single class.
*   **USB (`USBDevice`):**
    *   *Windows:* Primary connection method must be **native Win32** (`CreateFile`, `WinUsb`). If native access fails, seamlessly fallback to `libusb`.
    *   *Non-Windows:* Use `libusb` as the primary interface.
    *   Logic must support bulk transfer and endpoint detection.
*   **Network (`LANDevice`):**
    *   Use `boost::asio`.
    *   Support both **Ethernet and WiFi** connections.
    *   Implement UDP Broadcast (Ports 9100/3001) for device discovery.
*   **Legacy Ports (`COMDevice` / `LPTDevice`):**
    *   Implement serial (`boost::asio::serial_port`) and parallel port communication with robust error handling.
## 5. Coding Standards & Output Format
*   **Zero Elision:** Generate **100% complete, immediately executable source code**. Never use placeholders (e.g., `// ...`, `/* rest of code */`), stubs, mocks, or simplified implementations.
*   **No Comments in Source:** Do **NOT** include comments in `.cpp` files. Remove pre-existing comments during porting.
*   **Documentation:** Provide multi-line documentation comments (`/** ... */`) **only** in `.hpp` header files.
*   **Error Handling:** If a dependency is missing or a task is impossible, output a specific `//TODO: [Description]` comment.
*   **Output Structure:**
    1.  **Header Generation:** Output all `.hpp` files first, sorted by dependency.
    2.  **Source Generation:** Output all `.cpp` files, implementing every method defined in the headers.
## 6. Implementation Roadmap
1.  **Infrastructure:** `CMakeLists.txt`, `Obfuscation.hpp`, `Compression.hpp` (LZMA2).
2.  **HAL:** `Device.hpp`, `USBDevice.cpp`, `BTDevice.cpp` (PIMPL idiom), `LANDevice.cpp`.
3.  **Business Logic:** Port `TestEngine.java` (Manufacturing tests), `PrinterHelper.java` (CPL generation), and `CPLParser.java` (Response parsing). Ensure Java synchronous waits (`Thread.sleep`) are converted to non-blocking C++ mechanisms to prevent UI freezing.
4.  **Networking:** `HttpServer.hpp` (Boost.Beast) for firmware/logs.
5.  **UI Bridge:** `WebBridge.cpp` (JSON-RPC binding C++ to JS).
6.  **UI Shell:** `MainFrame` (`wxFrame`) hosting `wxWebView`.
**Immediate Action:**
Begin by modifying or adding anything further or necessary involving **Header Files (.hpp)**. You may begin source code generation with **Source Files (.cpp)**. Ensure all classes mirror the functional requirements of `JAdmin` while adhering to the C++23 constraints defined above.

Note: when writing to files with extension ".hpp" or ".cpp", write to the same file but with ".txt" appended to the extension: for example, write to file "Device.hpp.txt" instead of "Device.hpp", write to file "Device.cpp.txt" and not "Device.cpp".