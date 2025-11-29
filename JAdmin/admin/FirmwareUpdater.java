public class FirmwareUpdater
{
byte[] buff=null;
ProgressBar progressBar=null;
boolean isThroughManufacturing=false;
public FirmwareUpdater(byte[] buff, ProgressBar progressBar)
{
this.buff=buff;
this.progressBar=progressBar;
}
public void updateFirmware(boolean isThroughManufacturing)
{
this.isThroughManufacturing=isThroughManufacturing;
try
{
String val="100";
int currentVersion=0;
String bufferTimedReset=null;
if((isThroughManufacturing&&(!TestEngine.isConnectionTypeParallelOnly))||((!isThroughManufacturing)&&(!CompositeConnectionSettings.isConnectionTypeParallel)))
{
bufferTimedReset=PrinterHelper.setAndGetVariable("BUFFER_TIMED_RESET", val);
if(bufferTimedReset==null)
{
return;
}
if(!bufferTimedReset.equals(val))
{
GUIHelper.logger.warn("Failed setting BUFFER_TIMED_RESET; Staying with "+bufferTimedReset);
}
else
{
GUIHelper.logger.info("BUFFER_TIMED_RESET set to "+bufferTimedReset);
}
String currentFirmware=GUIHelper.clean(PrinterHelper.commandWaitResponse("!QR"));
if(currentFirmware==null)
{
return;
}
String regexPartNumber="\\d{3}-\\d{3}-\\d{3}";
String currentPartNumber=GUIHelper.getMatch(currentFirmware, regexPartNumber);
String regexVersion="\\d{3}$";
currentVersion=Integer.parseInt(GUIHelper.getMatch(currentPartNumber, regexVersion));
}
else
{
PrinterHelper.setVariable("BUFFER_TIMED_RESET", val);
currentVersion=Integer.parseInt(TestEngine.currentVersion);
GUIHelper.logger.info("BUFFER_TIMED_RESET set to "+val);
}
Downloader downloader=new Downloader(buff, currentVersion);
downloader.start();
progressBar.setMinimum(0);
progressBar.setMaximum(100);
int i=0;
while(downloader.isAlive())
{
Thread.sleep(100);
progressBar.setSelection(i%100);
progressBar.update();
GUIHelper.myDisplay.update();
}
val="65534";
if((isThroughManufacturing&&(!TestEngine.isConnectionTypeParallelOnly))||((!isThroughManufacturing)&&(!CompositeConnectionSettings.isConnectionTypeParallel)))
{
if((isThroughManufacturing&&TestEngine.isConnectionTypeSerial)||((!isThroughManufacturing)&&CompositeConnectionSettings.isConnectionTypeSerial))
{
if(!TestEngine.establishSerialConnection(115200, false))
{
TestEngine.establishSerialConnection(9600);
PrinterHelper.setVariable("COMM", "115200,N,8,1,N");
PrinterHelper.printer.waitForResponse(".*115200.*", 10);
TestEngine.establishSerialConnection(115200);
}
}
bufferTimedReset=PrinterHelper.setAndGetVariable("BUFFER_TIMED_RESET", val);
if(bufferTimedReset==null)
{
return;
}
if(!bufferTimedReset.equals(val))
{
GUIHelper.logger.warn("Failed setting BUFFER_TIMED_RESET; Staying with "+bufferTimedReset);
}
else
{
GUIHelper.logger.info("BUFFER_TIMED_RESET set to "+bufferTimedReset);
}
}
else
{
PrinterHelper.setVariable("BUFFER_TIMED_RESET", val);
GUIHelper.logger.info("BUFFER_TIMED_RESET set to "+val);
}
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("FirmwareUpdater.0")+e.toString()+Messages.getString("FirmwareUpdater.1"));
GUIHelper.logger.error("Caught an exception: ", e);
try
{
PrinterHelper.printer.closeConnection();
GUIHelper.mainShell.setConnectionState(false, false);
}
catch(Exception e2)
{
GUIHelper.logger.error(e, e);
}
return;
}
}
class Downloader extends Thread
{
IPrinterConnection conn=null;
byte[] buff;
int currentVersion;
boolean persistentConnection=false;
public Downloader(byte[] buff, int currentVersion)
{
GUIHelper.logger.trace("");
conn=PrinterHelper.printer.getConnection();
switch(conn.getType())
{
case OS_PRINTER:
case SERIAL:
persistentConnection=true;
break;
default:
persistentConnection=false;
break;
}
this.setName("Downloader");
this.buff=buff;
this.currentVersion=currentVersion;
GUIHelper.setCursor(SWT.CURSOR_WAIT);
}
private void disconnect()
{
try
{
PrinterHelper.printer.closeConnection();
GUIHelper.mainShell.setConnectionState(false, false);
}
catch(Exception e)
{
}
}
private void reconnect()
{
disconnect();
int i=0;
boolean connected=false;
while(!connected&&(i++<15))
{
try
{
Thread.sleep(5000);
}
catch(Exception e)
{
GUIHelper.logger.warn(e, e);
}
GUIHelper.logger.debug("Trying to establish connection...");
try
{
PrinterHelper.printer.openConnection(conn);
connected=PrinterHelper.printer.isConnected();
}
catch(Exception e)
{
GUIHelper.logger.debug(e, e);
connected=false;
}
}
}
public void run()
{
try
{
PrinterHelper.printer.send(buff);
if((isThroughManufacturing&&(!TestEngine.isConnectionTypeParallelOnly))||((!isThroughManufacturing)&&(!CompositeConnectionSettings.isConnectionTypeParallel)))
{
PrinterHelper.printer.waitFor(".*Success.*", 180);
PrinterHelper.printer.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 3);
if(currentVersion<100)
{
GUIHelper.logger.info("Sending Ethernet Reset to reboot for original firmware version "+currentVersion);
PrinterHelper.printer.setVariable("ETHERNET", "RESET");
}
if(!persistentConnection)
{
reconnect();
}
PrinterHelper.printer.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 30);
try
{
GUIHelper.logger.info("Waiting for another 30 seconds for the printer to be actually ready");
Thread.sleep(30000);
}
catch(Exception e)
{
GUIHelper.logger.warn(e, e);
}
}
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
GUIHelper.myDisplay.syncExec(new Runnable()
{
public void run()
{
GUIHelper.error(Messages.getString("FirmwareUpdater.2"));
}
});
return;
}
}
}
}
