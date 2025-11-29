public class Configurator
{
private Properties properties;
private String jAdminHome;
static String brand="Cognitive";
private enum OS
{
WINDOWS,
LINUX,
MAC,
UNKNOWN
}
private OS os;
public Configurator()
{
properties=System.getProperties();
os=getOS();
GUIHelper.loadBrandingProperties();
brand=GUIHelper.brand.getProperty("company.name", "Cognitive").replaceAll("TPG", "").replaceAll(" ", "");
jAdminHome=getAppDir();
configureLogging();
configurePreferences();
}
private OS getOS()
{
String osName=properties.getProperty("os.name");
System.out.println("OS:"+osName);
if(osName.indexOf("Windows")!=-1)
{
return OS.WINDOWS;
}
else if(osName.indexOf("Linux")!=-1)
{
return OS.LINUX;
}
else if(osName.indexOf("Mac")!=-1)
{
return OS.MAC;
}
else
{
return OS.UNKNOWN;
}
}
public void savePrefs()
{
FileOutputStream prefsOutputStream=null;
try
{
prefsOutputStream=new FileOutputStream(jAdminHome+"preferences.xml");
GUIHelper.prefs.exportNode(prefsOutputStream);
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
}
}
private boolean getOrMakeDir(String path)
{
File dir=null;
try
{
dir=new File(path);
if(dir.exists())
{
File test=new File(path+"/test");
boolean created=false;
try
{
created=test.createNewFile();
if(created)
{
test.delete();
}
}
catch(Exception e)
{
return false;
}
return true;
}
else if(dir.mkdir())
{
return true;
}
else
{
return false;
}
}
catch(Exception e)
{
return false;
}
}
private String getAppDir()
{
String testPath=null;
String publicPath=null;
final String cognitiveDirName=brand+"/";
switch(os)
{
case WINDOWS:
publicPath="C:\\Program Files\\";
break;
case LINUX:
case MAC:
default:
publicPath="/usr/local/";
break;
}
if(publicPath!=null)
{
testPath=publicPath+cognitiveDirName;
if(getOrMakeDir(testPath))
{
return testPath;
}
}
String privatePath=properties.getProperty("user.home")+"/";
testPath=privatePath+cognitiveDirName;
if(getOrMakeDir(testPath))
{
return testPath;
}
return null;
}
public void installDocumentation()
{
GUIHelper.logger.trace("");
if(GUIHelper.prefs.getBoolean("ACTIVATE_MANUFACTURING", false))
{
GUIHelper.logger.debug(Messages.getString("Configurator.0"));
return;
}
GUIHelper.logger.debug(Messages.getString("Configurator.1"));
String packageName="com.cognitive.doc";
String localPath=jAdminHome+"Documentation/";
atLeastOneFileCopied=false;
installJAR(packageName, localPath);
if(atLeastOneFileCopied)
{
GUIHelper.message(Messages.getString("Configurator.2")+localPath, SWT.OK|SWT.ICON_INFORMATION);
atLeastOneFileCopied=false;
installMenuLinks(localPath);
}
}
private void installMenuLinks(String localPath)
{
GUIHelper.logger.trace(localPath);
File dir=new File(localPath);
FilenameFilter filter=new FilenameFilter(){
public boolean accept(File dir, String name)
{
return name.endsWith(".pdf");
}
};
File[] list=dir.listFiles(filter);
URL url=null;
String urlString=null;
try
{
for(File file : list)
{
createShortcut(file);
}
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
}
}
private void createShortcut(File file)
{
GUIHelper.logger.trace(file.getName());
if(os!=OS.WINDOWS)
{
return;
}
final String userName=(properties.getProperty("user.name")).toLowerCase();
GUIHelper.logger.debug("userName: "+userName);
File dir=new File("C:\\Documents and Settings\\");
FilenameFilter filter=new FilenameFilter(){
public boolean accept(File dir, String name)
{
if(name.toLowerCase().indexOf(userName)==-1)
{
return false;
}
else
{
return true;
}
}
};
String[] userDirs=dir.list(filter);
GUIHelper.logger.debug("Possible user dirs: "+userDirs.length);
GUIHelper.logger.debug("Installing shortcuts for users: "+userDirs);
for(String userDir : userDirs)
{
String shortcutDest="";
if(brand.equalsIgnoreCase("Cognitive"))
{
shortcutDest="C:\\Documents and Settings\\"+userDir+"\\Start Menu\\Programs\\CognitiveTPG Printer Administrator\\";
}
else if(brand.equalsIgnoreCase("TallyDascom"))
{
shortcutDest="C:\\Documents and Settings\\"+userDir+"\\Start Menu\\Programs\\Tally Dascom Printer Administrator\\";
}
else
{
shortcutDest="C:\\Documents and Settings\\"+userDir+"\\Start Menu\\Programs\\"+brand+" Printer Administrator\\";
}
GUIHelper.logger.debug("Shortcut dest: "+shortcutDest);
File destDir=new File(shortcutDest);
if(!destDir.exists())
{
destDir.mkdir();
}
GUIHelper.logger.debug(userName);
GUIHelper.logger.debug(shortcutDest);
String shortcutName=file.getName();
String urlString=null;
File shortcutFile=null;
FileWriter fileWriter=null;
try
{
urlString=((file.toURI()).toURL()).toString();
shortcutFile=new File(shortcutDest+shortcutName+".url");
fileWriter=new FileWriter(shortcutFile);
fileWriter.write("[InternetShortcut]\n");
fileWriter.write("URL="+urlString+"\n");
fileWriter.write("IconIndex=0\n");
fileWriter.write("IconFile=C:\\Program Files\\"+brand+"\\Documentation\\pdf.ico\n");
fileWriter.write("HotKey=0\n");
fileWriter.write("IDList=\n");
fileWriter.write("[{000214A0-0000-0000-C000-000000000046}]\n");
fileWriter.write("Prop3=19,9\n");
fileWriter.close();
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
return;
}
}
}
public void installFPKTool()
{
GUIHelper.logger.trace("");
if(os!=OS.WINDOWS)
{
return;
}
String packageName="com.cognitive.manufacturing.fpk";
String localPath=jAdminHome+"FPK/";
if(GUIHelper.prefs.getBoolean("ACTIVATE_REPAIR", false)||GUIHelper.prefs.getBoolean("ACTIVATE_MANUFACTURING", false))
{
installJAR(packageName, localPath);
}
}
public void installDrivers()
{
GUIHelper.logger.trace("");
String packageName=null;
String localPath=jAdminHome+"Drivers/";
switch(os)
{
case WINDOWS:
packageName="com.cognitive.windows_drivers";
break;
default:
GUIHelper.logger.trace("No drivers to install for this operating system.");
return;
}
atLeastOneFileCopied=false;
installJAR(packageName, localPath);
if(GUIHelper.prefs.getBoolean("ACTIVATE_MANUFACTURING", false))
{
packageName+="_mfg";
installJAR(packageName, localPath);
}
if(atLeastOneFileCopied)
{
atLeastOneFileCopied=false;
}
}
private boolean atLeastOneFileCopied=false;
private void installJAR(String packageName, String localPath)
{
GUIHelper.logger.trace("");
File localDir=new File(localPath);
if(!localDir.exists())
{
if(!localDir.mkdir())
{
GUIHelper.logger.error("Can't create dir ["+localPath+"]");
return;
}
}
Vector<String>fileNames=null;
fileNames=GUIHelper.getPackageEntries(packageName);
Enumeration<String>e=fileNames.elements();
String fileName=null;
JAdminDialogMessage dialog=new JAdminDialogMessage(Messages.getString("Configurator.4"));
dialog.show();
while(e.hasMoreElements())
{
fileName=(String)e.nextElement();
copyResource(fileName, packageName, localPath, dialog);
}
dialog.close();
return;
}
private URL getConfigFile(String fileName, String localPath, String jarPath)
{
File file=new File(localPath+fileName);
if(file.exists())
{
try
{
return file.toURI().toURL();
}
catch(MalformedURLException e)
{
return null;
}
}
else
{
return copyResource(fileName, jarPath, localPath, null);
}
}
private void configureLogging()
{
try
{
Logger logger=Logger.getRootLogger();
String packageName="com.cognitive.admin";
String fileName="logger.cfg";
URL url=getConfigFile(fileName, jAdminHome, packageName);
if(url!=null)
{
PropertyConfigurator.configure(url);
RollingFileAppender fileAppender=(RollingFileAppender)logger.getAppender("file");
if(fileAppender!=null)
{
fileAppender.setFile(jAdminHome+"/JAdmin.log");
fileAppender.activateOptions();
}
}
else
{
PatternLayout layout=new PatternLayout("%d||%-10t|%-5p|%-7c|%50.50C|%35.35M|%m%n");
if(brand.startsWith("Tally"))
{
layout=new PatternLayout("%d||%-10t|%-5p|%-7c|%35.35M|%m%n");
}
ConsoleAppender consoleAppender=new ConsoleAppender(layout);
logger.addAppender(consoleAppender);
consoleAppender.activateOptions();
RollingFileAppender fileAppender=new RollingFileAppender(layout, jAdminHome+"/JAdmin.log");
logger.addAppender(fileAppender);
fileAppender.activateOptions();
logger.setLevel((Level)Level.ERROR);
}
GUIHelper.logger=Logger.getLogger("ADMIN");
GUIHelper.logger.info("Logger is up.");
}
catch(Exception e)
{
e.printStackTrace();
}
}
private void configurePreferences()
{
GUIHelper.logger.trace("");
try
{
GUIHelper.prefs.removeNode();
}
catch(Exception e)
{
GUIHelper.logger.warn(e, e);
}
String fileName="preferences.xml";
String packageName="com.cognitive.admin";
URL url=getConfigFile(fileName, jAdminHome, packageName);
try
{
if(url!=null)
{
InputStream inputStream=url.openStream();
if(inputStream!=null)
{
Preferences.importPreferences(inputStream);
}
}
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
}
GUIHelper.prefs=Preferences.systemNodeForPackage(MainShell.class);
fileName="security.xml";
try
{
File xml=new File(jAdminHome+fileName);
if(xml!=null&&!xml.delete())
GUIHelper.logger.warn("Failed to delete and reload \"security.xml\" file in JAdmin home directory.");
}
catch(Exception e)
{
GUIHelper.logger.warn("Failed to delete and reload \"security.xml\" file in JAdmin home directory.");
GUIHelper.logger.error(e, e);
}
url=getConfigFile(fileName, jAdminHome, packageName);
try
{
if(url!=null) SecurityConfig.loadConfigs(jAdminHome+fileName);
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
}
}
private URL copyResource(String resourceName, String packageName, String localPath, JAdminDialogMessage dialog)
{
String msg="Copying resource: ["+resourceName+"] from ["+packageName+"] to ["+localPath+"].";
logOrPrint(msg, null);
FileOutputStream out=null;
BufferedOutputStream bufferedOut=null;
String resourcePath=packageName.replace('.', '/')+"/";
InputStream inputStream=getClass().getClassLoader().getResourceAsStream(resourcePath+resourceName);
if(inputStream==null)
{
msg="Can't find resource ["+resourcePath+resourceName+"].";
logOrPrint(msg, null);
return null;
}
int resourceSize=-1;
try
{
resourceSize=inputStream.available();
}
catch(IOException e)
{
msg="Unable to get size for resource "+resourceName;
logOrPrint(msg, e);
return null;
}
msg="size of ["+resourceName+"] is "+resourceSize;
logOrPrint(msg, null);
File dest=new File(localPath+resourceName);
if(!dest.exists()||(dest.length()!=resourceSize))
{
msg="Updating/creating "+resourceName;
logOrPrint(msg, null);
if(dialog!=null)
{
dialog.setMessage(resourceName);
GUIHelper.update();
atLeastOneFileCopied=true;
}
try
{
dest.createNewFile();
out=new FileOutputStream(dest);
bufferedOut=new BufferedOutputStream(out);
int c;
while((c=inputStream.read())!=-1)
{
bufferedOut.write(c);
}
inputStream.close();
out.flush();
bufferedOut.flush();
bufferedOut.close();
out.close();
}
catch(Exception e)
{
logOrPrint(e.toString(), e);
if(dest.exists())
{
dest.delete();
}
return null;
}
}
else
{
logOrPrint("Skpping up-to-date "+resourceName, null);
}
try
{
return dest.toURI().toURL();
}
catch(Exception e)
{
return null;
}
}
private void logOrPrint(String msg, Exception e)
{
if(brand.startsWith("Tally"))
{
msg=msg.replace("cognitive", "***");
}
StackTraceElement[] stack=Thread.currentThread().getStackTrace();
StackTraceElement callingFuntion=stack[2];
String callingFuntionName=callingFuntion.getMethodName();
String displayMsg="<FROM "+callingFuntionName+">"+msg;
if(GUIHelper.logger!=null)
{
if(e!=null)
{
GUIHelper.logger.error(displayMsg, e);
}
else
{
GUIHelper.logger.debug(displayMsg);
}
}
else
{
System.out.println(displayMsg);
if(e!=null)
{
e.printStackTrace();
}
}
}
}
