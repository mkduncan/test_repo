public class Printer extends Observable
{
final int WAIT_FOR_DATA_TIMEOUT=10000;
final int WAIT_FOR_DATA_INTERVAL=100;
final int WAIT_FOR_MUTEX_TIMEOUT=WAIT_FOR_DATA_TIMEOUT+1000;
private boolean continueReceiving=true;
private ReentrantLock mutex=null;
static private IPrinterConnection conn;
static private ReceiveThread receiver;
static public String modelNumber="";
static boolean showWaitFor=GUIHelper.prefs.getBoolean("SHOW_WAIT_FOR", false);
static Logger logger;
int responses=0;
long totalResponseTime=0;
public Printer()
{
logger=Logger.getLogger("PRINTER");
logger.trace("");
mutex=new ReentrantLock();
}
public void stopReceiver()
{
logger.trace("");
continueReceiving=false;
try
{
receiver.join();
}
catch(Exception e)
{
logger.error("Interrupted while joining receiver.");
}
}
public boolean acquireMutex()
{
try
{
return mutex.tryLock(WAIT_FOR_MUTEX_TIMEOUT, TimeUnit.MILLISECONDS);
}
catch(Exception e)
{
logger.error("Caught an exception: ", e);
return false;
}
}
public void releaseMutex()
{
if(mutex.isHeldByCurrentThread())
{
mutex.unlock();
}
}
public void openConnection(IPrinterConnection newConn) throws UnsupportedCommOperationException,
PortInUseException,
NativeLibraryException,
IOException
{
logger.trace("");
if((com.cognitive.admin.TestEngine.isConnectionTypeParallelOnly)&&(com.cognitive.admin.TestEngine.runningManufacturing))
{
modelNumber=com.cognitive.admin.TestEngine.testModelNumber;
}
conn=newConn;
conn.openConnection();
createReceiver();
}
public void closeConnection() throws UnsupportedCommOperationException,
PortInUseException,
NativeLibraryException,
IOException
{
logger.trace("");
continueReceiving=false;
try
{
if(receiver!=null)
{
receiver.join();
logger.debug("Receiver joined.");
}
if(conn!=null)
{
conn.closeConnection();
}
setChanged();
notifyObservers(new String("030470 DISCONNECTED 074030"));
}
catch(InterruptedException e)
{
logger.warn("Interrupted while waiting for receiver thread to exit: ", e);
}
}
private void createReceiver()
{
logger.trace("");
continueReceiving=true;
receiver=new ReceiveThread();
receiver.start();
}
public void send(byte data[]) throws InterruptedException,
NativeLibraryException, IOException, PrinterOperationException
{
logger.trace("");
try
{
if(mutex.tryLock(WAIT_FOR_MUTEX_TIMEOUT, TimeUnit.MILLISECONDS))
{
conn.send(data);
}
else
{
throw new PrinterOperationException(
"Timeout acquiring comm mutex.");
}
}
finally
{
if(mutex.isHeldByCurrentThread())
{
mutex.unlock();
}
}
}
public void send(String str) throws InterruptedException,
NativeLibraryException,
IOException,
PrinterOperationException
{
logger.trace(str);
try
{
if(mutex.tryLock(WAIT_FOR_MUTEX_TIMEOUT, TimeUnit.MILLISECONDS))
{
conn.send(str);
}
else
{
throw new PrinterOperationException("Timeout acquiring comm mutex.");
}
}
finally
{
if(mutex.isHeldByCurrentThread())
{
mutex.unlock();
}
}
}
private void waitForData() throws NativeLibraryException,
IOException,
InterruptedException,
PrinterOperationException
{
logger.trace("");
int waitTime=0;
while(!conn.ready())
{
Thread.sleep(WAIT_FOR_DATA_INTERVAL);
waitTime+=WAIT_FOR_DATA_INTERVAL;
if(waitTime>WAIT_FOR_DATA_TIMEOUT)
{
throw new PrinterOperationException("waitForData: timed out.");
}
}
}
public String clean(String str)
{
logger.trace("");
str=str.replace('\n', ' ');
str=str.replace('\r', ' ');
str=str.trim();
return str;
}
public String setAndGetVariable(String variable, String value) throws InterruptedException,
NativeLibraryException,
IOException,
PrinterOperationException,
CPLParsingException
{
try
{
if(mutex.tryLock(WAIT_FOR_MUTEX_TIMEOUT, TimeUnit.MILLISECONDS))
{
setVariable(variable, value);
return getVariable(variable);
}
else
{
throw new PrinterOperationException("Timeout acquiring comm mutex.");
}
}
finally
{
if(mutex.isHeldByCurrentThread())
{
mutex.unlock();
}
}
}
public String getVariable(String variable) throws PrinterOperationException,
CPLParsingException,
InterruptedException,
IOException,
NativeLibraryException
{
logger.trace("");
String preVar="!!0 0 0 0\n!0 0 0 0\nVARIABLE ";
String postVar=" ?\nEND\n";
String command=preVar+variable+postVar;
long t0=System.currentTimeMillis();
String response=commandWaitResponse(command);
long t1=System.currentTimeMillis();
logger.debug("Response time: "+(t1-t0)+" milliseconds.");
responses++;
totalResponseTime+=(t1-t0);
logger.debug("Average response time: "+(totalResponseTime/responses)+" for "+responses+" responses.");
return response;
}
public void setVariable(String variable, String value) throws InterruptedException,
NativeLibraryException,
IOException,
PrinterOperationException
{
logger.trace("");
String preVar="!!0 0 0 0\n!0 0 0 0\nVARIABLE ";
String postVar=" \nVARIABLE WRITE\nEND\n";
String command=preVar+variable+" "+value+postVar;
send(command);
}
public boolean waitFor(String pattern, int seconds)
{
logger.trace(pattern+" "+seconds);
String msg;
int numLoops=seconds*1000/WAIT_FOR_DATA_INTERVAL;
try
{
if(mutex.tryLock(WAIT_FOR_DATA_INTERVAL, TimeUnit.MILLISECONDS))
{
for(int i=0; i<numLoops; i++)
{
if(conn.ready())
{
msg=clean(conn.readLine());
if(msg.matches(pattern))
{
mutex.unlock();
logger.debug("Received ["+msg+"] matches pattern ["+pattern+"]");
return true;
}
else
{
Printer.logger.debug("Asynchronous message 3: ["+clean(msg)+"] while waiting for ["+pattern+"]");
setChanged();
notifyObservers(msg);
}
}
Thread.sleep(WAIT_FOR_DATA_INTERVAL);
}
}
}
catch(InterruptedException e)
{
Printer.logger.debug(e.toString());
}
catch(Exception e)
{
continueReceiving=false;
Printer.logger.fatal("Exiting RcvThrd due to exception:", e);
}
finally
{
if(mutex.isHeldByCurrentThread())
{
mutex.unlock();
}
}
if(mutex.isHeldByCurrentThread())
{
mutex.unlock();
}
logger.debug("Did not receive "+pattern);
return false;
}
public String commandWaitResponse(String command) throws PrinterOperationException,
CPLParsingException,
InterruptedException,
IOException,
NativeLibraryException
{
logger.trace("");
String msg="";
StringBuffer responsePatternStart=new StringBuffer("");
StringBuffer responsePatternEnd=new StringBuffer("");
StringBuffer response=new StringBuffer("");
try
{
if(mutex.tryLock(WAIT_FOR_MUTEX_TIMEOUT, TimeUnit.MILLISECONDS))
{
CPLParser.getResponsePattern(command, responsePatternStart, responsePatternEnd);
conn.send(command);
int ctr=0;
while(ctr<10)
{
waitForData();
msg=conn.readLine();
if(msg.trim().isEmpty())
{
ctr++;
if(ctr==10)
{
logger.debug("Receiving only empty strings..");
throw new PrinterOperationException("waitForData: timed out.");
}
}
else
{
ctr=10;
}
}
while(!clean(msg).matches(responsePatternStart.toString())&&!clean(msg).matches(responsePatternEnd.toString()))
{
Printer.logger.debug("Asynchronous message 2: ["+clean(msg)+"] while response pattern is ["+responsePatternStart+"]");
setChanged();
notifyObservers(msg);
waitForData();
msg=conn.readLine();
}
logger.trace("Response so far: "+clean(msg));
response.append(msg);
if(responsePatternEnd.toString().equals("null"))
{
logger.trace("returning one-line response: "+response.toString());
return CPLParser.getResponseValue(response.toString(), command);
}
else
{
logger.trace("waiting for multi-line response");
if(clean(msg).matches(responsePatternEnd.toString()))
{
return CPLParser.getResponseValue(response.toString(), command);
}
do
{
waitForData();
msg=conn.readLine();
logger.debug("Concatenating to multi-line response: "+clean(msg));
response.append(msg);
}
while(!clean(response.toString()).matches(responsePatternEnd.toString()));
logger.trace("returning multi-line response: "+clean(response.toString()));
return CPLParser.getResponseValue(response.toString(), command);
}
}
else
{
throw new PrinterOperationException("Timeout acquiring comm mutex.");
}
}
finally
{
if(mutex.isHeldByCurrentThread())
{
logger.trace("releasing mutex");
mutex.unlock();
}
}
}
public boolean isConnected()
{
logger.trace("");
String str=null;
try
{
str=commandWaitResponse("!SHOW MODELNUMBER\n");
modelNumber=str;
}
catch(PrinterOperationException e)
{
try
{
logger.warn("second attempt.");
str=commandWaitResponse("!SHOW MODELNUMBER\n");
modelNumber=str;
}
catch(Exception e2)
{
logger.error("", e2);
return false;
}
}
catch(Exception e)
{
logger.error("", e);
return false;
}
if(str=="")
{
return false;
}
else
{
return true;
}
}
class ReceiveThread extends Thread
{
String msg=null;
public ReceiveThread()
{
super();
logger.trace("");
}
public void run()
{
this.setName("RcvThrd");
logger.trace("");
while(continueReceiving)
{
try
{
if(mutex.tryLock(WAIT_FOR_DATA_INTERVAL, TimeUnit.MILLISECONDS))
{
if(conn.ready())
{
msg=conn.readLine();
mutex.unlock();
Printer.logger.debug("Asynchronous message 1: ["+clean(msg)+"]");
setChanged();
notifyObservers(msg);
}
else
{
mutex.unlock();
Thread.sleep(WAIT_FOR_DATA_INTERVAL);
}
}
}
catch(InterruptedException e)
{
Printer.logger.debug(e.toString());
}
catch(Exception e)
{
continueReceiving=false;
Printer.logger.fatal("Exiting RcvThrd due to exception:", e);
}
finally
{
if(mutex.isHeldByCurrentThread())
{
mutex.unlock();
}
}
}
logger.trace("Exiting");
}
}
public IPrinterConnection getConnection()
{
return conn;
}
public String waitForResponse(String pattern, int seconds)
{
logger.trace(pattern+" "+seconds);
String msg;
int numLoops=seconds*1000/WAIT_FOR_DATA_INTERVAL;
try
{
if(mutex.tryLock(WAIT_FOR_DATA_INTERVAL, TimeUnit.MILLISECONDS))
{
for(int i=0; i<numLoops; i++)
{
if(conn.ready())
{
msg=clean(conn.readLine());
if(msg.matches(pattern))
{
mutex.unlock();
logger.debug("Received ["+msg+"] matches pattern ["+pattern+"]");
return msg;
}
else
{
Printer.logger.debug("Asynchronous message 3: ["+clean(msg)+"] while waiting for ["+pattern+"]");
setChanged();
notifyObservers(msg);
}
}
Thread.sleep(WAIT_FOR_DATA_INTERVAL);
}
}
}
catch(InterruptedException e)
{
Printer.logger.debug(e.toString());
}
catch(Exception e)
{
continueReceiving=false;
Printer.logger.fatal("Exiting RcvThrd due to exception:", e);
}
finally
{
if(mutex.isHeldByCurrentThread())
{
mutex.unlock();
}
}
if(mutex.isHeldByCurrentThread())
{
mutex.unlock();
}
logger.debug("Did not receive "+pattern);
return "NONE";
}
}
