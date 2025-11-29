public class UsbPort
{
private static boolean m_bLibraryLoad=true;
private IOEnvironment ioe=null;
int ihWriteHandle=0;
int ihReadHandle=0;
int iGenericWrite=(int)0x40000000;
int iGenericRead=(int)0x80000000;
private boolean bConnected=false;
native int usbInitPipe(IOEnvironment ioe, int nDesiredAccess);
native int usbInitReadPipe(IOEnvironment ioe);
native int usbReadInputPipe(int nHandle, byte[] aReadData);
native int usbWriteOutputPipe(int nHandle, byte[] writeData);
native int usbClosePipe(int nHandle);
native int usbGetGenericReadValue();
native int usbGetBytesAvailable();
native int usbGetGenericWriteValue();
native String	usbGetErrorString(int nErrorCode);
native int usbSetCommTimeout(int nHandle, int nRITimeout, int nRTTMultiplier,
int nRTTConstant, int nWTTMultiplier, int nWTTConstant);
static
{
try
{
System.loadLibrary("JAdminUSB");
}
catch(UnsatisfiedLinkError ule)
{
m_bLibraryLoad=false;
}
}
public UsbPort()
{
ioe=new IOEnvironment();
}
public int open(String printerName) throws NativeLibraryException
{
int ret=-1;
int rc1=0;
int rc2=0;
if(ioe!=null)
{
if(m_bLibraryLoad)
{
try
{
int iDesc=usbGetGenericWriteValue();
rc1=usbInitPipe(ioe, iDesc);
if(rc1==1)
{
ihWriteHandle=ioe.getWritePort();
}
else
{
Integer iRc=new Integer(rc1);
}
iDesc=usbGetGenericReadValue();
rc2=usbInitPipe(ioe, iDesc);
if(rc2==1)
{
ihReadHandle=ioe.getReadPort();
}
else
{
Integer iRc=new Integer(rc2);
}
if(usbGetBytesAvailable()>0)
{
String sIgnore=read();
}
}
catch(UnsatisfiedLinkError e1)
{
e1.printStackTrace();
}
if(rc1==1&&rc2==1)
{
bConnected=true;
ret=0;
}
}
}
Integer iRc=new Integer(ret);
return ret;
};
public int print(String printString)
{
byte data[]=printString.getBytes();
int ret=usbWriteOutputPipe(ihWriteHandle, data);
return ret;
};
public int printBuffer(byte data[])
{
int ret=usbWriteOutputPipe(ihWriteHandle, data);
Integer iRet=new Integer(ret);
return ret;
};
public int close()
{
int ret=usbClosePipe(ihWriteHandle);
bConnected=false;
return ret;
};
public String read()
{
String sRet=null;
int iBytesAvailable=usbGetBytesAvailable();
byte[] aReadData=new byte[iBytesAvailable];
for(int i=0; i<iBytesAvailable; i++)
aReadData[i]=(byte)0x00;
usbReadInputPipe(ihReadHandle, aReadData);
sRet=new String(aReadData);
return sRet;
};
public int ready()
{
int ret=0;
if(usbGetBytesAvailable()>0)
ret=1;
return ret;
};
}
