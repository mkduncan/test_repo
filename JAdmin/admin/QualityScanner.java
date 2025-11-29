public class QualityScanner
{
final int WAIT_FOR_DATA_TIMEOUT=30000;
final int WAIT_FOR_DATA_INTERVAL=100;
private InputStream inputStream;
private SerialPort serialPort;
private int baudRate;
private CommPortIdentifier portId;
private BufferedReader reader=null;
public QualityScanner(String port, int baud) throws NoSuchPortException
{
GUIHelper.logger.trace("");
baudRate=baud;
portId=CommPortIdentifier.getPortIdentifier(port);
}
public void openConnection() throws IOException, PortInUseException, UnsupportedCommOperationException
{
GUIHelper.logger.trace("");
serialPort=(SerialPort)portId.open("Quality Scanner", 2000);
inputStream=serialPort.getInputStream();
reader=new BufferedReader(new InputStreamReader(inputStream));
serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8,
SerialPort.STOPBITS_1,
SerialPort.PARITY_NONE);
serialPort.setDTR(true);
serialPort.setRTS(true);
}
public void closeConnection() throws IOException
{
GUIHelper.logger.trace("");
if(null!=reader)
{
reader.close();
reader=null;
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
public boolean ready() throws IOException
{
if(reader!=null)
{
return reader.ready();
}
else
{
GUIHelper.logger.error("ready() called on null reader; returning false.");
throw new IOException("ready() called on null reader");
}
}
public String readLine() throws IOException
{
return reader.readLine()+"\n";
}
public String waitForBarcode()
{
String line=null;
String barcode=null;
long currentTime=(new GregorianCalendar()).getTimeInMillis();
long maxTime=currentTime+WAIT_FOR_DATA_TIMEOUT;
while(currentTime<=maxTime)
{
try
{
if(ready())
{
line=readLine().trim();
if(line.length()>0)
{
GUIHelper.logger.info(line);
if(line.charAt(0)=='*'&&line.charAt(line.length()-1)=='*'&&line.indexOf("Quick-Check")==-1)
{
barcode=line.substring(1, line.length()-1);
return barcode;
}
}
}
else
{
Thread.sleep(WAIT_FOR_DATA_INTERVAL);
GUIHelper.update();
currentTime=(new GregorianCalendar()).getTimeInMillis();
}
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
}
}
return null;
}
public String waitForGrade()
{
String gradeLine=null;
String grade=null;
String line=null;
long currentTime=(new GregorianCalendar()).getTimeInMillis();
long maxTime=currentTime+WAIT_FOR_DATA_TIMEOUT;
while(gradeLine==null&&currentTime<=maxTime)
{
try
{
if(ready())
{
line=readLine();
System.out.println(line);
if(line.indexOf("Decodability")!=-1)
{
gradeLine=line;
}
}
else
{
Thread.sleep(WAIT_FOR_DATA_INTERVAL);
GUIHelper.update();
currentTime=(new GregorianCalendar()).getTimeInMillis();
}
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
}
}
if(gradeLine!=null)
{
GUIHelper.logger.info("Grade: "+gradeLine);
grade=String.valueOf((GUIHelper.getMatch(line, "<[a-zA-Z]>")).charAt(1));
}
return grade;
}
}
