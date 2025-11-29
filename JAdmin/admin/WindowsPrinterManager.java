public class WindowsPrinterManager
{
static public boolean spoolerStopped=false;
static public boolean spoolerStarted=false;
public void deletePrinter(String printerName)
{
try
{
String companyname=GUIHelper.brand.getProperty("company.name", "CognitiveTPG").replaceAll("TPG", "").replaceAll(" ", "");
Runtime.getRuntime().exec("C:\\Program Files\\"+companyname+"\\Documentation\\delete.bat");
}
catch(IOException e)
{
e.printStackTrace();
}
}
public static void stopSpooler()
{
try
{
Runtime.getRuntime().exec("net.exe stop spooler");
Thread.sleep(3000);
spoolerStopped=true;
spoolerStarted=false;
}
catch(IOException e)
{
e.printStackTrace();
}
catch(Exception e)
{
e.printStackTrace();
}
}
public static void startSpooler()
{
try
{
Runtime.getRuntime().exec("net.exe start spooler");
spoolerStarted=true;
spoolerStopped=false;
}
catch(IOException e)
{
e.printStackTrace();
}
}
}
