public class WindowsPrinterConnection implements IPrinterConnection
{
String address=null;
int port=-1;
private String printerName=null;
private UsbPort dll=null;
public WindowsPrinterConnection()
{
Printer.logger.trace("");
this.printerName="Printer";
dll=new UsbPort();
}
public void openConnection() throws NativeLibraryException
{
Printer.logger.trace("");
if(!com.cognitive.admin.WindowsPrinterManager.spoolerStopped)
{
com.cognitive.admin.WindowsPrinterManager.stopSpooler();
}
Printer.logger.trace("Spooler stopped");
int i=dll.open(printerName);
if(0!=i)
{
if(com.cognitive.admin.WindowsPrinterManager.spoolerStopped)
{
com.cognitive.admin.WindowsPrinterManager.startSpooler();
}
Printer.logger.trace("Spooler started");
Printer.logger.error("Native error code "+i);
throw new NativeLibraryException("WindowsPrinterConnection.openConnection returned error code "+i);
}
}
public void closeConnection() throws NativeLibraryException
{
Printer.logger.trace("");
int i=dll.close();
if(com.cognitive.admin.WindowsPrinterManager.spoolerStopped)
{
Printer.logger.trace("Spooler started");
com.cognitive.admin.WindowsPrinterManager.startSpooler();
}
if(0!=i)
{
if(com.cognitive.admin.WindowsPrinterManager.spoolerStopped)
{
Printer.logger.trace("Spooler started");
com.cognitive.admin.WindowsPrinterManager.startSpooler();
}
Printer.logger.error("Native error code "+i);
throw new NativeLibraryException("WindowsPrinterConnection.closeConnection returned error code "+i);
}
}
public void send(String str) throws NativeLibraryException
{
Printer.logger.trace(str);
int i=dll.printBuffer(str.getBytes());
if(0!=i)
{
Printer.logger.error("Native error code "+i);
throw new NativeLibraryException("WindowsPrinterConnection.send returned error code "+i);
}
}
public void send(byte data[]) throws NativeLibraryException
{
Printer.logger.trace("");
int i=dll.printBuffer(data);
if(0!=i)
{
Printer.logger.error("Native error code "+i);
throw new NativeLibraryException("WindowsPrinterConnection.send returned error code "+i);
}
}
public boolean ready() throws NativeLibraryException
{
int i=dll.ready();
switch(i)
{
case 0:
{
return false;
}
case 1:
{
return true;
}
default:
{
Printer.logger.error("Native error code "+i);
throw new NativeLibraryException("WindowsPrinterConnection.ready returned error code "+i);
}
}
}
public String readLine() throws NativeLibraryException
{
Printer.logger.trace("");
String line=dll.read();
if(line.startsWith("GetLastError"))
{
Printer.logger.error(line);
throw new NativeLibraryException("WindowsPrinterConnection.readLine "+line);
}
return line;
}
public static String select()
{
Printer.logger.trace("");
PrinterJob printJob=PrinterJob.getPrinterJob();
String printerName=null;
if(printJob.printDialog())
{
try
{
printerName=printJob.getPrintService().getName();
}
catch
(Exception PrintException)
{
}
}
return printerName;
}
public ConnectionType getType()
{
return ConnectionType.OS_PRINTER;
}
}
