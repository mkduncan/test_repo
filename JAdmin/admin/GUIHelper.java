public class GUIHelper
{
public static Shell myShell=null;
public static Display myDisplay=null;
public static Logger logger=null;
public static MainShell mainShell=null;
public static Preferences prefs=Preferences.systemNodeForPackage(MainShell.class);
public static Properties brand=null;
public enum LocationCode
{
A,
B,
C,
D,
E,
G,
J,
M,
N,
T,
V,
DocuShield
}
static private boolean isComposite(Object o)
{
Class<?>subclass=o.getClass();
Class<?>superclass=subclass.getSuperclass();
while(superclass!=null)
{
String className=superclass.getName();
if(className.equals("org.eclipse.swt.widgets.Composite"))
{
return true;
}
subclass=superclass;
superclass=subclass.getSuperclass();
}
return false;
}
public static void update()
{
myDisplay.syncExec(new Runnable()
{
public void run()
{
myDisplay.readAndDispatch();
myDisplay.update();
}
});
}
public static void setEnabled(Composite composite, boolean enabled)
{
composite.setEnabled(enabled);
Control[] children=composite.getChildren();
if(children==null)
{
return;
}
for(Control c : children)
{
if(isComposite(c))
{
setEnabled((Composite)c, enabled);
}
else
{
c.setEnabled(enabled);
}
}
}
public static void blankify(Composite composite)
{
Control[] children=composite.getChildren();
if(children==null)
{
return;
}
for(Control c : children)
{
if(isComposite(c))
{
blankify((Composite)c);
}
else
{
String className=c.getClass().getName();
if(className.equals("org.eclipse.swt.widgets.Text"))
{
Text w=(Text)c;
w.setText("");
}
else if(className.equals("org.eclipse.swt.widgets.Button"))
{
Button w=(Button)c;
w.setSelection(false);
}
else if(className.equals("org.eclipse.swt.widgets.Combo"))
{
Combo w=(Combo)c;
w.select(-1);
}
}
}
}
public static boolean booleanFromString(String onOff)
{
if(onOff==null)
{
return false;
}
if(onOff.startsWith("ON")||onOff.startsWith("1")||onOff.startsWith("YES"))
{
return true;
}
else
{
return false;
}
}
public static String stringFromBoolean(boolean bool)
{
if(bool)
{
return "ON";
}
else
{
return "OFF";
}
}
public static void setCursor(int cursorType)
{
Cursor cursor=myDisplay.getSystemCursor(cursorType);
myShell.setCursor(cursor);
}
public static void error(String msg)
{
message(msg, SWT.OK|SWT.ICON_ERROR);
}
public static void info(String msg)
{
message(msg, SWT.OK|SWT.ICON_INFORMATION);
}
public static int message(String message, int options)
{
MessageBox messageBox=new MessageBox(myShell, options);
messageBox.setMessage(message);
int retVal=messageBox.open();
myDisplay.update();
return retVal;
}
public static void applySimpleTextField(Text text, String variable)
{
logger.trace("");
try
{
setCursor(SWT.CURSOR_WAIT);
text.setText(PrinterHelper.printer.setAndGetVariable(variable, text.getText()));
}
catch(SocketException e)
{
PrinterHelper.handleSocketException(e);
}
catch(Exception e)
{
error(Messages.getString("GUIHelper.0")+variable+"].");
logger.error("Caught an exception on set-and-get ["+variable+"]: ", e);
return;
}
finally
{
setCursor(0);
}
}
public static void refreshSimpleTextField(Text text, String variable)
{
logger.trace("");
try
{
setCursor(SWT.CURSOR_WAIT);
text.setText(PrinterHelper.printer.getVariable(variable));
}
catch(SocketException e)
{
PrinterHelper.handleSocketException(e);
}
catch(Exception e)
{
error(Messages.getString("GUIHelper.1")+variable+"].");
logger.error("Caught an exception: ", e);
return;
}
finally
{
setCursor(0);
}
}
public static void applySimpleCheckBox(Button checkBox, String variable)
{
logger.trace("");
try
{
setCursor(SWT.CURSOR_WAIT);
String newVal;
if(checkBox.getSelection())
{
newVal="ON";
}
else
{
newVal="OFF";
}
PrinterHelper.setVariable(variable, newVal);
refreshSimpleCheckBox(checkBox, variable);
}
catch(Exception e)
{
error(Messages.getString("GUIHelper.2")+variable+"].");
logger.error(e, e);
return;
}
finally
{
setCursor(0);
}
}
public static void refreshSimpleCheckBox(Button checkBox, String variable)
{
logger.trace("");
try
{
setCursor(SWT.CURSOR_WAIT);
checkBox.setSelection(GUIHelper.booleanFromString((PrinterHelper.printer.getVariable(variable))));
}
catch(SocketException e)
{
PrinterHelper.handleSocketException(e);
}
catch(Exception e)
{
error(Messages.getString("GUIHelper.1")+variable+"].");
logger.error(e, e);
return;
}
finally
{
setCursor(0);
}
}
public static BufferedReader openTextFile(String path, StringBuffer pathUsed)
{
logger.trace("");
FileDialog fd=new FileDialog(myShell, SWT.OPEN);
fd.setFilterPath(path);
String fileName=fd.open();
if(fileName==null)
{
return null;
}
FileReader frd=null;
try
{
frd=new FileReader(fileName);
}
catch(FileNotFoundException e)
{
logger.warn("File not found: "+fileName);
error(Messages.getString("GUIHelper.4"));
return null;
}
BufferedReader brd=new BufferedReader(frd);
pathUsed.append(fd.getFilterPath());
return brd;
}
public static byte[] readBinaryFile(String fileName) throws Exception
{
logger.trace("");
File f=new File(fileName);
FileInputStream fis=new FileInputStream(f);
DataInputStream dis=new DataInputStream(fis);
byte[] b=new byte[(int)f.length()];
dis.readFully(b);
dis.close();
fis.close();
return b;
}
public static byte[] readBinaryFile(InputStream inputStream) throws Exception
{
logger.trace("");
int size=inputStream.available();
byte buff[]=new byte[size];
int c=-1;
int bytesRead=0;
while((c=inputStream.read())!=-1)
{
buff[bytesRead++]=(byte)c;
}
GUIHelper.logger.debug("inputStream size: "+size);
GUIHelper.logger.debug("bytes read: "+bytesRead);
return buff;
}
public static String saveTextFile(String text, String path)
{
logger.trace("");
FileDialog fd=new FileDialog(myShell, SWT.SAVE);
fd.setFilterPath(path);
String fileName=fd.open();
if(fileName==null)
{
return path;
}
FileWriter fwt=null;
try
{
fwt=new FileWriter(fileName);
}
catch(IOException e)
{
logger.error(e, e);
error(Messages.getString("GUIHelper.5"));
return path;
}
BufferedWriter bwt=new BufferedWriter(fwt);
try
{
bwt.write(text);
bwt.flush();
bwt.close();
fwt.close();
}
catch(Exception e)
{
error(Messages.getString("GUIHelper.6"));
logger.error(e, e);
return path;
}
return fd.getFilterPath();
}
static public String getMatch(String input, String regex)
{
String content=null;
try
{
Pattern pattern=Pattern.compile(regex);
Matcher matcher=pattern.matcher(input);
if(matcher.find())
{
content=matcher.group();
}
else
{
content="";
}
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
content="";
}
return content;
}
static byte[] getResourceAsByteArray(String resourceName)
{
InputStream inputStream=myShell.getClass().getClassLoader().getResourceAsStream(resourceName);
if(inputStream==null)
{
logger.error("Can't find resource: "+resourceName);
return null;
}
byte buff[]=null;
try
{
buff=GUIHelper.readBinaryFile(inputStream);
}
catch(Exception e)
{
logger.error(e, e);
return null;
}
return buff;
}
static char calculateCRC(byte c, char calc_crc)
{
char crc_tbl1[]={0x0000, 0xcc01, 0xd801, 0x1400,
0xf001, 0x3c00, 0x2800, 0xe401,
0xa001, 0x6c00, 0x7800, 0xb401,
0x5000, 0x9c01, 0x8801, 0x4400};
char crc_tbl2[]={0x0000, 0xc0c1, 0xc181, 0x0140,
0xc301, 0x03c0, 0x0280, 0xc241,
0xc601, 0x06c0, 0x0780, 0xc741,
0x0500, 0xc5c1, 0xc481, 0x0440};
char tbl1_idx;
char tbl2_idx;
tbl2_idx=(char)((c ^ calc_crc)&0x0f);
tbl1_idx=(char)(((c ^ calc_crc)&0xf0)>>4);
calc_crc=(char)(((calc_crc>>8)&0x00ff) ^ crc_tbl1[tbl1_idx] ^ crc_tbl2[tbl2_idx]);
return calc_crc;
}
static String getCRC(String resourceName)
{
InputStream inputStream=myShell.getClass().getClassLoader().getResourceAsStream(resourceName);
if(inputStream==null)
{
System.out.println("Can't find resource: "+resourceName);
return null;
}
String hex=null;
try
{
int size=inputStream.available();
int c=-1;
int bytesRead=0;
char crc=0;
int ctr=0;
while((c=inputStream.read())!=-1)
{
bytesRead=bytesRead+1;
if(!((bytesRead<23)||(bytesRead>60&&bytesRead<67)||(bytesRead>(size-2))))
{
ctr++;
crc=calculateCRC((byte)c, crc);
}
}
hex=Integer.toHexString(crc);
inputStream.close();
}
catch(Exception e)
{
logger.error(e, e);
return null;
}
return hex;
}
static void loadBrandingProperties()
{
brand=new Properties();
try
{
brand.load(MainShell.class.getClassLoader().getResourceAsStream("com/cognitive/brand/brand.properties"));
}
catch(IOException e)
{
brand.setProperty("company.name", "CognitiveTPG");
brand.setProperty("company.logo", "MainLogo.gif");
brand.setProperty("company.printerImage", "LabelPositioningImage.jpg");
}
}
static InputStream getManifestForClass(Class<?>theClass)
{
String classResName=theClass.getName().replace('.', '/')+".class";
GUIHelper.logger.trace(classResName);
URL urlToClass=theClass.getClassLoader().getResource(classResName);
String strUrl=urlToClass.toString();
int index=strUrl.lastIndexOf('!');
if(index==-1)
{
GUIHelper.logger.debug("Class wasn't loaded from a JAR file.");
return null;
}
String strUrlBase=strUrl.substring(0, index+1);
URL urlToManifest=null;
try
{
urlToManifest=new URL(strUrlBase+"/META-INF/MANIFEST.MF");
}
catch(MalformedURLException mue)
{
return null;
}
try
{
GUIHelper.logger.debug("Manifest: "+urlToManifest.toString());
return urlToManifest.openStream();
}
catch(IOException e)
{
GUIHelper.logger.debug("Manifest: "+urlToManifest.toString());
return null;
}
}
static Vector<String>getPackageEntries(String packageName)
{
String logPackageName=packageName;
if(brand.getProperty("company.name", "CognitiveTPG").startsWith("Tally"))
{
logPackageName=logPackageName.replace("cognitive", "***");
}
logger.trace(logPackageName);
Vector<String>fileNames=new Vector<String>();
Class<?>theClass=null;
try
{
theClass=Class.forName(packageName+".MarkerClass");
}
catch(ClassNotFoundException e)
{
GUIHelper.logger.error("Can't find marker class for "+logPackageName);
return fileNames;
}
InputStream is=getManifestForClass(theClass);
if(is==null)
{
GUIHelper.logger.error("Can't find manifest for ["+theClass.getName()+"].");
return fileNames;
}
InputStreamReader ir=new InputStreamReader(is);
if(ir==null)
{
GUIHelper.logger.error("Can't create InputStreamReader.");
return fileNames;
}
BufferedReader br=new BufferedReader(ir);
if(br==null)
{
GUIHelper.logger.error("Can't create BufferedReader.");
return fileNames;
}
try
{
String line=br.readLine();
String fileName=null;
int nameStartsHere=-1;
while(line!=null)
{
if(line.indexOf("Name: ")!=-1&&line.indexOf("MarkerClass")==-1)
{
fileName=line.substring(line.lastIndexOf('/')+1);
fileNames.add(fileName);
GUIHelper.logger.debug("Adding: "+fileName);
}
line=br.readLine();
}
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
}
return fileNames;
}
static public String clean(String str)
{
if(str==null)
{
return null;
}
str=str.replace('\n', ' ');
str=str.replace('\r', ' ');
str=str.trim();
return str;
}
static private String getStorage(String storage)
{
int iStorage=Integer.parseInt(storage);
switch(iStorage)
{
case 0:
return "RAM";
case 3:
return "Flash";
default:
return storage.trim();
}
}
static public String compressedBitmapFont=Messages.getString("GUIHelper.7");
static private String getType(String type)
{
int iType=Integer.parseInt(type);
switch(iType)
{
case 0:
return Messages.getString("GUIHelper.8");
case 1:
return Messages.getString("GUIHelper.9");
case 2:
return Messages.getString("GUIHelper.10");
case 3:
return Messages.getString("GUIHelper.11");
case 4:
return Messages.getString("GUIHelper.12");
case 5:
return Messages.getString("GUIHelper.13");
case 6:
return Messages.getString("GUIHelper.14");
case 7:
return Messages.getString("GUIHelper.15");
case 8:
return Messages.getString("GUIHelper.16");
case 9:
return compressedBitmapFont;
case 10:
return Messages.getString("GUIHelper.17");
case 11:
return Messages.getString("GUIHelper.18");
case 12:
return Messages.getString("GUIHelper.19");
case 13:
return Messages.getString("GUIHelper.20");
case 14:
return Messages.getString("GUIHelper.21");
case 15:
return Messages.getString("GUIHelper.22");
case 16:
return Messages.getString("GUIHelper.23");
case 17:
return Messages.getString("GUIHelper.24");
case 18:
return Messages.getString("GUIHelper.25");
case 19:
return Messages.getString("GUIHelper.26");
case 20:
return Messages.getString("GUIHelper.27");
case 21:
return Messages.getString("GUIHelper.28");
case 22:
return Messages.getString("GUIHelper.29");
case 23:
return Messages.getString("GUIHelper.30");
case 24:
return Messages.getString("GUIHelper.31");
case 25:
return Messages.getString("GUIHelper.32");
case 26:
return Messages.getString("GUIHelper.33");
case 35:
return Messages.getString("GUIHelper.34");
default:
return Messages.getString("GUIHelper.35")+iType;
}
}
static private String[] parseObject(String object)
{
logger.trace("");
String name=null;
String type=null;
String storage=null;
String size=null;
String description=null;
try
{
String[] fields=object.split(",");
int numFields=fields.length;
if(numFields<6)
{
return null;
}
storage=getStorage(fields[0].trim());
type=getType(fields[1].trim());
name=fields[2].trim();
size=fields[3].trim();
description=fields[5].trim();
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("GUIHelper.36")+e.toString());
logger.error(e, e);
return null;
}
return new String[]{name, type, storage, size, description};
}
public static Vector<String[]>getParsedObjectList()
{
logger.trace("");
Vector<String[]>parsedObjectList=new Vector<String[]>();
String objectList=PrinterHelper.commandWaitResponse("!LS");
if(objectList!=null)
{
GUIHelper.logger.debug(objectList);
String[] objects=objectList.split("\n");
for(String object : objects)
{
if(object.matches(".*END-OF-LIST.*"))
{
break;
}
else
{
String[] parsedObject=parseObject(object);
if(parsedObject!=null)
{
parsedObjectList.add(parsedObject);
}
}
}
}
return parsedObjectList;
}
}
