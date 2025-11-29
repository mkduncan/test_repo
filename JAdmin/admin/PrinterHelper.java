public class PrinterHelper
{
static void handleSocketException(SocketException e)
{
GUIHelper.error(Messages.getString("PrinterHelper.0"));
GUIHelper.logger.error(e, e);
try
{
printer.closeConnection();
}
catch(Exception anotherException)
{
}
GUIHelper.mainShell.setConnectionState(false, false);
throw new NullPointerException("12345DISCONNECT54321");
}
public static void printTestLabel(String tof, String shiftLeft, String message)
{
GUIHelper.logger.trace("");
boolean isWingman=false;
boolean isTallyGenicom=false;
boolean isTallyDascom=false;
boolean isLX=false;
int printDensity=200;
int mediaWidth=2;
if(TestEngine.runningManufacturing&&TestEngine.isConnectionTypeParallelOnly)
{
isWingman=TestEngine.isWingman;
isTallyDascom=TestEngine.isTDModelNumber;
isTallyGenicom=TestEngine.isTGModelNumber;
printDensity=TestEngine.printDensity;
mediaWidth=TestEngine.mediaWidth;
isLX=TestEngine.isLX;
}
else
{
String mn=com.cognitive.printer.Printer.modelNumber;
isLX=mn.startsWith("LB");
String cname=GUIHelper.clean(commandWaitResponse("!SHOW OEMIDENTIFIER"));
if(mn.startsWith("DB"))
{
if(mn.charAt(11)=='Z')
{
isWingman=true;
}
}
if(cname.equalsIgnoreCase("TALLYDASCOM"))
{
isTallyDascom=true;
}
else if(cname.equalsIgnoreCase("TALLYGENICOM"))
{
isTallyGenicom=true;
}
}
StringBuffer label=new StringBuffer();
if(!isWingman)
{
if(isLX)
{
label.append("!");
}
label.append("!0 100 200 1\n");
label.append("INDEX\n");
label.append("END\n");
}
else
{
label.append("N\nP1\n");
}
send(label.toString());
label=new StringBuffer();
String width="";
String pitch="";
if(TestEngine.runningManufacturing&&TestEngine.isConnectionTypeParallelOnly)
{
if(mediaWidth==2)
{
width="220";
}
else
{
width="409";
}
pitch=""+printDensity;
}
else
{
width=getVariable("WIDTH");
if(width==null)
{
return;
}
pitch=getVariable("PITCH");
if(pitch==null)
{
return;
}
}
width=GUIHelper.getMatch(width, "\\d+");
float fWidth=Float.parseFloat(width);
int iPitch=Integer.parseInt(pitch);
int iDots=(int)(fWidth/100*iPitch);
String widthDots=Integer.toString(iDots);
String companyName="COGNITIVE TPG";
if(isTallyDascom)
{
companyName="TALLY DASCOM";
}
else if(isTallyGenicom)
{
companyName="TALLYGENICOM";
}
if(!isWingman)
{
if(isLX)
{
label.append("!");
}
label.append("!0 100 250 1\n");
label.append("DRAW_BOX 0 0 "+widthDots+" 2 3\n");
label.append("DRAW_BOX 0 0 2 200 3\n");
label.append("U B24 (3,0,0) 20 20 "+companyName+"\n");
label.append("U B24 (3,0,0) 20 60 Test Page\n");
}
else
{
label.append("N\n");
label.append("A20,20,0,1,1,1,N,\"COGNITIVE TPG\"\n");
label.append("A20,60,0,1,1,1,N,\"Test Page\"\n");
}
if(tof!="")
{
if(!isWingman)
{
label.append("U B24 (3,0,0) 20 100 "+Messages.getString("PrinterHelper.8")+tof+"\n");
}
else
{
label.append("A20,100,0,1,1,1,N,\""+Messages.getString("PrinterHelper.8")+tof+"\"\n");
}
}
if(shiftLeft!="")
{
if(!isWingman)
{
label.append("U B24 (3,0,0) 20 140 "+Messages.getString("PrinterHelper.9")+shiftLeft+"\n");
}
else
{
label.append("A20,140,0,1,1,1,N,\""+Messages.getString("PrinterHelper.9")+shiftLeft+"\"\n");
}
}
if(message!="")
{
if(!isWingman)
{
label.append("U B24 (3,0,0) 20 180 "+message+"\n");
}
else
{
label.append("A20,180,0,1,1,1,N,\""+message+"\"\n");
}
}
if(!isWingman)
{
label.append("END\n");
}
else
{
label.append("X0,0,3,"+widthDots+",2\n");
label.append("X0,0,3,2,200\n");
label.append("P1\n");
}
send(label.toString());
}
public static void printSelfTestLabel()
{
try
{
send("!PRINT TESTLABEL");
send("!L");
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("PrinterHelper.1")+e.toString());
}
}
public static String setAndGetVariable(String variable, String value)
{
GUIHelper.logger.trace(variable+" : "+value);
String retVal=null;
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
retVal=printer.setAndGetVariable(variable, value);
}
catch(SocketException e)
{
handleSocketException(e);
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("PrinterHelper.2")+variable+"].");
GUIHelper.logger.error(e, e);
return null;
}
finally
{
GUIHelper.setCursor(0);
}
return retVal;
}
public static void setVariable(String variable, String value)
{
GUIHelper.logger.trace(variable+" : "+value);
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
printer.setVariable(variable, value);
}
catch(SocketException e)
{
handleSocketException(e);
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("PrinterHelper.3")+variable+"].\n\n"+e);
GUIHelper.logger.error(e, e);
}
finally
{
GUIHelper.setCursor(0);
}
}
public static void send(byte[] data)
{
GUIHelper.logger.trace("");
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
printer.send(data);
}
catch(SocketException e)
{
handleSocketException(e);
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("PrinterHelper.4"));
GUIHelper.logger.error(e, e);
}
finally
{
GUIHelper.setCursor(0);
}
}
public static void send(String data)
{
GUIHelper.logger.trace(data);
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
printer.send(data+"\n");
}
catch(SocketException e)
{
handleSocketException(e);
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("PrinterHelper.4"));
GUIHelper.logger.error(e, e);
}
finally
{
GUIHelper.setCursor(0);
}
}
public static String commandWaitResponse(String command)
{
GUIHelper.logger.trace(command);
String retVal=null;
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
retVal=printer.commandWaitResponse(command+"\n");
}
catch(SocketException e)
{
handleSocketException(e);
}
catch(Exception e)
{
command=(command.replace('\n', ' ')).replace('\r', ' ').trim();
GUIHelper.error(Messages.getString("PrinterHelper.6")+command+"].");
GUIHelper.logger.error(e, e);
return null;
}
finally
{
GUIHelper.setCursor(0);
}
return retVal;
}
public static boolean waitFor(String pattern, int seconds)
{
boolean retVal=false;
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
retVal=printer.waitFor(pattern, seconds);
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
return false;
}
finally
{
GUIHelper.setCursor(0);
}
return retVal;
}
public static String getVariable(String variable)
{
GUIHelper.logger.trace(variable);
String retVal=null;
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
retVal=printer.getVariable(variable);
}
catch(SocketException e)
{
handleSocketException(e);
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("PrinterHelper.7")+variable+"].");
GUIHelper.logger.error(e, e);
return null;
}
finally
{
GUIHelper.setCursor(0);
}
return retVal;
}
public static final String GENERAL_PRINTER_READY=".*[RoOU]00000.*";
public static final String PEELER_PRINTER_READY=".*[RoOUP]0000.*";
public static Printer printer=new Printer();
}
