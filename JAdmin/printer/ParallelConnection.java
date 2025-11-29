public class ParallelConnection implements IPrinterConnection
{
private OutputStream outputStream;
private ParallelPort parallelPort;
private CommPortIdentifier portId;
public ParallelConnection(String port) throws NoSuchPortException
{
Printer.logger.trace("");
portId=CommPortIdentifier.getPortIdentifier(port);
}
public void openConnection() throws IOException, PortInUseException, UnsupportedCommOperationException
{
Printer.logger.trace("");
parallelPort=(ParallelPort)portId.open("com.cogntive.printer", 30000);
GUIHelper.logger.info("Port name: "+parallelPort.getName());
parallelPort.setMode(ParallelPort.LPT_MODE_ANY);
int mode=parallelPort.getMode();
switch(mode)
{
case ParallelPort.LPT_MODE_ECP:
GUIHelper.logger.info("Mode is: ECP");
break;
case ParallelPort.LPT_MODE_EPP:
GUIHelper.logger.info("Mode is: EPP");
break;
case ParallelPort.LPT_MODE_NIBBLE:
GUIHelper.logger.info("Mode is: Nibble Mode.");
break;
case ParallelPort.LPT_MODE_PS2:
GUIHelper.logger.info("Mode is: Byte mode.");
break;
case ParallelPort.LPT_MODE_SPP:
GUIHelper.logger.info("Mode is: Compatibility mode.");
break;
case ParallelPort.LPT_MODE_ANY:
GUIHelper.logger.info("Mode is: Any mode.");
break;
default:
GUIHelper.logger.info("Mode is:"+mode);
break;
}
outputStream=parallelPort.getOutputStream();
}
public void closeConnection() throws IOException
{
Printer.logger.trace("");
if(null!=outputStream)
{
outputStream.flush();
outputStream.close();
outputStream=null;
}
if(null!=parallelPort)
{
parallelPort.close();
parallelPort=null;
}
}
public void send(String str) throws IOException
{
Printer.logger.trace(str);
send(str.getBytes());
}
public void send(byte data[]) throws IOException
{
Printer.logger.trace("");
outputStream.write(data);
outputStream.flush();
}
public boolean ready() throws IOException
{
return false;
}
public String readLine() throws IOException
{
return "";
}
public ConnectionType getType()
{
return ConnectionType.PARALLEL;
}
}
