public class SerialConnection implements IPrinterConnection
{
private InputStream inputStream;
private DataOutputStream outputStream;
private SerialPort serialPort;
private int baudRate;
private int dataBits;
private int stopBits;
private int parity;
private CommPortIdentifier portId;
private BufferedReader reader=null;
@SuppressWarnings("unchecked")
public SerialConnection(String port, int baud, int stopbits) throws NoSuchPortException
{
Printer.logger.trace("");
baudRate=baud;
Enumeration pList=CommPortIdentifier.getPortIdentifiers();
while(pList.hasMoreElements())
{
CommPortIdentifier cpi=(CommPortIdentifier)pList.nextElement();
Printer.logger.info("Port "+cpi.getName()+" ");
if(cpi.getPortType()==CommPortIdentifier.PORT_SERIAL)
{
Printer.logger.info("is a Serial Port: "+cpi);
}
else if(cpi.getPortType()==CommPortIdentifier.PORT_PARALLEL)
{
Printer.logger.info("is a Parallel Port: "+cpi);
}
else
{
Printer.logger.info("is an Unknown Port: "+cpi);
}
if(cpi.getName().equalsIgnoreCase(port))
{
Printer.logger.info("Initializing... "+port);
portId=cpi;
break;
}
}
dataBits=SerialPort.DATABITS_8;
stopBits=SerialPort.STOPBITS_1;
if(stopbits==2)
{
stopBits=SerialPort.STOPBITS_2;
}
parity=SerialPort.PARITY_NONE;
}
public void openConnection() throws IOException, PortInUseException, UnsupportedCommOperationException
{
Printer.logger.trace("");
serialPort=(SerialPort)portId.open("com.cogntive.printer", 2000);
inputStream=serialPort.getInputStream();
reader=new BufferedReader(new InputStreamReader(inputStream));
serialPort.setSerialPortParams(baudRate, dataBits, stopBits, parity);
serialPort.setDTR(true);
serialPort.setRTS(true);
serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN|SerialPort.FLOWCONTROL_RTSCTS_OUT);
outputStream=new DataOutputStream(serialPort.getOutputStream());
}
public void closeConnection() throws IOException
{
Printer.logger.trace("");
if(null!=reader)
{
reader.close();
reader=null;
}
if(null!=outputStream)
{
outputStream.flush();
outputStream.close();
outputStream=null;
}
if(null!=inputStream)
{
inputStream.close();
inputStream=null;
}
if(null!=serialPort)
{
serialPort.close();
serialPort=null;
}
}
public void send(String str) throws IOException
{
Printer.logger.trace(str);
outputStream.writeBytes(str);
outputStream.flush();
}
public void send(byte data[]) throws IOException
{
Printer.logger.trace("");
outputStream.write(data);
outputStream.flush();
}
public boolean ready() throws IOException
{
if(reader!=null)
{
return reader.ready();
}
else
{
Printer.logger.error("ready() called on null reader; returning false.");
throw new IOException("ready() called on null reader");
}
}
public String readLine() throws IOException
{
Printer.logger.trace("");
int numLoops=0;
String retS="";
char c=(char)reader.read();
retS+=c;
while((c!='\r')&&(c!='\n'))
{
numLoops=0;
while(reader.ready()==false)
{
if(numLoops>=10)
{
retS+='\n';
return retS;
}
try
{
Thread.sleep(1);
}
catch(InterruptedException e)
{
Printer.logger.error("In readLine() Thread.sleep(1) threw an InterruptedException.");
throw new IOException("readLine(): unable to do Thread.sleep(1) while waiting for reader.ready()");
}
numLoops++;
}
c=(char)reader.read();
retS+=c;
}
return retS;
}
public ConnectionType getType()
{
return ConnectionType.SERIAL;
}
}
