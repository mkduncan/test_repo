public class IOEnvironment
{
final static String INPUT_PIPE="PIPE00";
final static String OUTPUT_PIPE="PIPE01";
final static String sGUID="00873FDF-61A8-11d1-AA5E-00C04FB1728B";
private	int hReadPort;
private	int hWritePort;
private	byte[]	aIOBuffer;
private	String sUsbGUID;
public IOEnvironment()
{
hReadPort=0;
hWritePort=0;
sUsbGUID=sGUID;
}
public String getUsbGUID()
{
return sUsbGUID;
}
public void setIOBuffer(byte[] aIOBuffer)
{
this.aIOBuffer=aIOBuffer;
}
public int getReadPort()
{
return this.hReadPort;
}
public int getWritePort()
{
return this.hWritePort;
}
public void setReadPort(int hReadPort)
{
this.hReadPort=hReadPort;
}
public void setWritePort(int hWritePort)
{
this.hWritePort=hWritePort;
}
}
