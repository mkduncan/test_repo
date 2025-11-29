public class WindowsPrinterConnectionDLL
{
public native int open(String printerName);
public native int print(String printString);
public native int printBuffer(byte data[]);
public native int close();
public native String read();
public native int ready();
private static final long serialVersionUID=1;
static
{
Printer.logger.trace("");
System.loadLibrary("WindowsPrinterConnectionDLL");
}
}
