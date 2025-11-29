public class MainShell implements Observer
{
static TimerTaskHeartbeat timerTaskHeartbeat=null;
static Timer timer=null;
static Configurator config=null;
static boolean initializing=true;
private Shell sShell=null;
private TabFolder tabFolder=null;
private CompositeCPLEditor compositeCPLEditor=null;
private Group groupPrinterStatus=null;
private Button buttonHeartbeat=null;
private Label labelPrinterStatus=null;
private CompositeConnectionSettings compositeConnectionSettings=null;
private Label labelLogoImage=null;
private CompositeLabelPositioning compositeLabelPositioning=null;
private CompositePrinterSettings compositePrinterSettings=null;
private CompositeFontsAndObjects compositeFontsAndObjects=null;
private CompositePCLWindowing compositePCLWindowing=null;
private CompositeFirmwareCPR compositeFirmwareCPR=null;
private CompositeProfileManagement compositeProfileManagement=null;
private CompositeRepair compositeRepair=null;
private CompositeManufacturing compositeManufacturingTest=null;
private CompositeAbout compositeAbout=null;
public void update(Observable observable, Object object)
{
GUIHelper.logger.trace("");
String className=observable.getClass().getName();
if(className.equals("com.cognitive.printer.Printer"))
{
updatePrinter(object);
}
else
{
GUIHelper.logger.warn("Received update from unknown observable ["+observable.toString()+"]");
}
}
private void updatePrinter(Object object)
{
GUIHelper.logger.trace("");
if(object!=null)
{
if(object.toString().equals("030470 DISCONNECTED 074030"))
{
GUIHelper.logger.info("Disconnected");
}
}
else
{
GUIHelper.logger.warn("Received null object.");
}
}
public void setConnectionState(boolean connected, boolean dumbTerminal)
{
GUIHelper.logger.trace("");
buttonHeartbeat.setEnabled(connected&&!dumbTerminal);
compositeConnectionSettings.setConnectionState(connected, dumbTerminal);
compositeCPLEditor.setConnectionState(connected, dumbTerminal);
compositePrinterSettings.setConnectionState(connected, dumbTerminal);
compositeLabelPositioning.setConnectionState(connected, dumbTerminal);
compositePCLWindowing.setConnectionState(connected, dumbTerminal);
if(connected&&!dumbTerminal)
{
boolean hasPCLFirmware=false;
String currentFirmware=GUIHelper.clean(PrinterHelper.commandWaitResponse("!QR"));
if(currentFirmware!=null)
{
hasPCLFirmware=currentFirmware.indexOf("176")>0;
}
if(hasPCLFirmware)
{
TabItem tabPCLWindowing=new TabItem(tabFolder, SWT.NONE, 6);
tabPCLWindowing.setText(Messages.getString("MainShell.10"));
tabPCLWindowing.setControl(compositePCLWindowing);
tabFolder.redraw();
}
}
if((!connected)&&(tabFolder.getItem(6).getText().equalsIgnoreCase(Messages.getString("MainShell.10"))))
{
tabFolder.getItem(6).dispose();
}
compositeFontsAndObjects.setConnectionState(connected, dumbTerminal);
compositeFirmwareCPR.setConnectionState(connected, dumbTerminal);
compositeProfileManagement.setConnectionState(connected, dumbTerminal);
if(compositeRepair!=null)
{
compositeRepair.setConnectionState(connected, dumbTerminal);
}
if(timer==null)
{
timer=new Timer("Timer");
}
if(initializing)
{
initializing=false;
buttonHeartbeat.setSelection(GUIHelper.prefs.getBoolean("USE_HEARTBEATS", false));
}
if(connected)
{
GUIHelper.logger.trace("connected");
labelPrinterStatus.setText(Messages.getString("MainShell.0"));
labelPrinterStatus.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
if(!dumbTerminal&&buttonHeartbeat.getSelection())
{
startHeartbeats();
}
}
else
{
GUIHelper.logger.trace("disconnected");
try
{
buttonHeartbeat.setEnabled(false);
if(timer!=null)
{
timer.cancel();
timer=null;
}
}
catch(IllegalStateException e)
{
}
displayConnectionStatus(ConnectionStatus.DISCONNECTED);
}
}
public void toggleHeartbeats()
{
if(buttonHeartbeat.getSelection())
{
startHeartbeats();
}
else
{
stopHeartbeats();
}
}
public boolean heartbeatsEnabled()
{
return buttonHeartbeat.getSelection();
}
public void startHeartbeats()
{
buttonHeartbeat.setSelection(true);
try
{
if(PrinterHelper.printer.isConnected())
{
displayConnectionStatus(ConnectionStatus.CONNECTED);
timerTaskHeartbeat=new TimerTaskHeartbeat();
timerTaskHeartbeat.setDisplay(Display.getCurrent());
timer.schedule(timerTaskHeartbeat, timerTaskHeartbeat.getInterval(), timerTaskHeartbeat.getInterval());
}
}
catch(IllegalStateException e)
{
}
}
public void stopHeartbeats()
{
buttonHeartbeat.setSelection(false);
if(timerTaskHeartbeat!=null)
{
timerTaskHeartbeat.cancel();
timerTaskHeartbeat=null;
}
displayConnectionStatus(ConnectionStatus.UNKNOWN);
}
public enum ConnectionStatus
{
CONNECTED,
DISCONNECTED,
UNKNOWN
}
public void displayConnectionStatus(ConnectionStatus cs)
{
switch(cs)
{
case CONNECTED:
labelPrinterStatus.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
labelPrinterStatus.setText(Messages.getString("MainShell.0"));
break;
case DISCONNECTED:
labelPrinterStatus.setText(Messages.getString("MainShell.2"));
labelPrinterStatus.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
break;
case UNKNOWN:
default:
labelPrinterStatus.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
labelPrinterStatus.setText(Messages.getString("MainShell.3"));
break;
}
}
private void createTabFolder()
{
GridData gridData=new GridData();
gridData.horizontalAlignment=GridData.FILL;
gridData.grabExcessHorizontalSpace=true;
gridData.grabExcessVerticalSpace=true;
gridData.horizontalSpan=2;
gridData.verticalAlignment=GridData.FILL;
tabFolder=new TabFolder(sShell, SWT.V_SCROLL|SWT.H_SCROLL);
tabFolder.setLayoutData(gridData);
createCompositeCPLEditor();
createCompositeConnectionSettings();
createCompositeLabelPositioning();
createCompositePrinterSettings();
createCompositeFontsAndObjects();
createCompositePCLWindowing();
createCompositeFirmwareCPR();
createCompositeProfileManagement();
createCompositeAbout();
TabItem tabConnectionSettings=new TabItem(tabFolder, SWT.NONE);
tabConnectionSettings.setText(Messages.getString("MainShell.4"));
tabConnectionSettings.setControl(compositeConnectionSettings);
TabItem tabPrinterSettings=new TabItem(tabFolder, SWT.NONE);
tabPrinterSettings.setText(Messages.getString("MainShell.5"));
tabPrinterSettings.setControl(compositePrinterSettings);
TabItem tabLabelPositioning=new TabItem(tabFolder, SWT.NONE);
tabLabelPositioning.setText(Messages.getString("MainShell.6"));
tabLabelPositioning.setControl(compositeLabelPositioning);
TabItem tabFontsAndObjects=new TabItem(tabFolder, SWT.NONE);
tabFontsAndObjects.setText(Messages.getString("MainShell.7"));
tabFontsAndObjects.setControl(compositeFontsAndObjects);
TabItem tabProfileManagement=new TabItem(tabFolder, SWT.NONE);
tabProfileManagement.setText(Messages.getString("MainShell.8"));
tabProfileManagement.setControl(compositeProfileManagement);
TabItem tabFirmwareCPR=new TabItem(tabFolder, SWT.NONE);
tabFirmwareCPR.setText(Messages.getString("MainShell.9"));
tabFirmwareCPR.setControl(compositeFirmwareCPR);
TabItem tabPCLWindowing=new TabItem(tabFolder, SWT.NONE);
tabPCLWindowing.setText(Messages.getString("MainShell.10"));
tabPCLWindowing.setControl(compositePCLWindowing);
TabItem tabCPLEditor=new TabItem(tabFolder, SWT.NONE);
tabCPLEditor.setText(Messages.getString("MainShell.11"));
tabCPLEditor.setControl(compositeCPLEditor);
String yesNo=GUIHelper.prefs.get("SUPER_USER", "NO");
if(yesNo.equals("YES"))
{
createCompositeRepair();
TabItem tabRepair=new TabItem(tabFolder, SWT.NONE);
tabRepair.setText(Messages.getString("MainShell.12"));
tabRepair.setControl(compositeRepair);
}
if(GUIHelper.prefs.getBoolean("ACTIVATE_MANUFACTURING", false))
{
try
{
LocationCode.valueOf(GUIHelper.prefs.get("MAN_LOCATION_CODE", ""));
createCompositeManufacturingTest();
tabFolder.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
tabSelected(((TabItem)(e.item)).getText());
}
});
TabItem tabManufacturingTest=new TabItem(tabFolder, SWT.NONE);
tabManufacturingTest.setText(Messages.getString("MainShell.13"));
tabManufacturingTest.setControl(compositeManufacturingTest);
}
catch(IllegalArgumentException e)
{
String msg=Messages.getString("MainShell.14");
GUIHelper.logger.warn(msg);
GUIHelper.message(msg, SWT.OK|SWT.ICON_WARNING);
}
}
TabItem tabAbout=new TabItem(tabFolder, SWT.NONE);
tabAbout.setText(Messages.getString("MainShell.15"));
tabAbout.setControl(compositeAbout);
}
private void createCompositeCPLEditor()
{
compositeCPLEditor=new CompositeCPLEditor(tabFolder, SWT.NONE);
}
private void createGroupPrinterStatus()
{
GridData gridData3=new GridData();
gridData3.grabExcessHorizontalSpace=true;
gridData3.verticalAlignment=GridData.FILL;
gridData3.horizontalAlignment=GridData.FILL;
GridData gridData1=new GridData();
gridData1.grabExcessHorizontalSpace=true;
gridData1.verticalAlignment=GridData.CENTER;
gridData1.horizontalAlignment=GridData.CENTER;
GridLayout gridLayout1=new GridLayout();
gridLayout1.numColumns=3;
GridData gridData2=new GridData();
gridData2.horizontalAlignment=GridData.FILL;
gridData2.grabExcessHorizontalSpace=true;
gridData2.verticalAlignment=GridData.CENTER;
groupPrinterStatus=new Group(sShell, SWT.NONE);
groupPrinterStatus.setText(Messages.getString("MainShell.16"));
groupPrinterStatus.setLayout(gridLayout1);
groupPrinterStatus.setLayoutData(gridData2);
labelLogoImage=new Label(groupPrinterStatus, SWT.NONE);
labelLogoImage.setText("");
labelLogoImage.setImage(new Image(Display.getCurrent(), GUIHelper.myShell.getClass().getClassLoader().getResourceAsStream("com/cognitive/brand/MainLogo.gif")));
buttonHeartbeat=new Button(groupPrinterStatus, SWT.CHECK);
buttonHeartbeat.setText(Messages.getString("MainShell.17"));
buttonHeartbeat.setLayoutData(gridData1);
buttonHeartbeat
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
toggleHeartbeats();
}
});
labelPrinterStatus=new Label(groupPrinterStatus, SWT.NONE);
labelPrinterStatus.setText(Messages.getString("MainShell.2"));
labelPrinterStatus.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
labelPrinterStatus.setLayoutData(gridData3);
labelPrinterStatus.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
}
private void createCompositeConnectionSettings()
{
compositeConnectionSettings=new CompositeConnectionSettings(tabFolder,
SWT.NONE);
}
static private void configure()
{
config=new Configurator();
}
static private void finit()
{
try
{
config.savePrefs();
if(timer!=null)
{
timer.cancel();
timer=null;
Thread.sleep(500);
}
if(PrinterHelper.printer!=null)
{
PrinterHelper.printer.closeConnection();
}
}
catch(SWTException e)
{
GUIHelper.logger.debug("printer.closeConnection SWT shutdwon.");
}
catch(Exception e)
{
GUIHelper.logger.error("printer.closeConnection failed:", e);
}
}
private void createCompositeLabelPositioning()
{
compositeLabelPositioning=new CompositeLabelPositioning(tabFolder,
SWT.NONE);
}
private void createCompositePrinterSettings()
{
compositePrinterSettings=new CompositePrinterSettings(tabFolder, SWT.NONE);
}
private void createCompositeFontsAndObjects()
{
compositeFontsAndObjects=new CompositeFontsAndObjects(tabFolder, SWT.NONE);
}
private void createCompositePCLWindowing()
{
compositePCLWindowing=new CompositePCLWindowing(tabFolder, SWT.NONE);
}
private void createCompositeFirmwareCPR()
{
compositeFirmwareCPR=new CompositeFirmwareCPR(tabFolder, SWT.NONE);
}
private void createCompositeProfileManagement()
{
compositeProfileManagement=new CompositeProfileManagement(tabFolder,
SWT.NONE);
}
private void createCompositeRepair()
{
compositeRepair=new CompositeRepair(tabFolder, SWT.NONE);
if(GUIHelper.brand.getProperty("company.name", "CognitiveTPG").startsWith("Tally"))
{
CompositeRepair.labelModelNumber.setText("ID Number:");
}
}
private void createCompositeManufacturingTest()
{
compositeManufacturingTest=new CompositeManufacturing(tabFolder, SWT.NONE);
if(GUIHelper.brand.getProperty("company.name", "CognitiveTPG").startsWith("Tally"))
{
CompositeManufacturing.labelModelNumber.setText("ID Number:");
}
}
private void createCompositeAbout()
{
compositeAbout=new CompositeAbout(tabFolder, SWT.NONE);
}
public static void main(String[] args)
{
try
{
configure();
GUIHelper.logger.trace("");
GUIHelper.logger.info("Version jadmin="+Versions.jadmin);
GUIHelper.logger.info("Version manFirmware="+Versions.manFirmware);
GUIHelper.logger.info("Version manCpr="+Versions.manCpr);
GUIHelper.logger.info("Version manFonts="+Versions.manFonts);
GUIHelper.logger.info("Version manLabels="+Versions.manLabels);
GUIHelper.logger.info("Retrieving default display value...");
Display display=null;
try
{
display=Display.getDefault();
}
catch(Exception e)
{
}
if(display==null)
{
GUIHelper.logger.info("Failed to get default display value; retrieving current display value...");
display=Display.getCurrent();
GUIHelper.logger.info("Current display value retrieved.");
}
else
{
GUIHelper.logger.info("Default display value retrieved.");
}
GUIHelper.logger.info("Initializing main shell value...");
MainShell thisClass=new MainShell();
GUIHelper.logger.info("Main shell value initialized.");
GUIHelper.logger.info("Creating main shell...");
thisClass.createSShell();
GUIHelper.logger.info("Main shell created.");
GUIHelper.logger.info("Opening main shell...");
thisClass.sShell.open();
GUIHelper.logger.info("Main shell opened.");
initializing=true;
config.installFPKTool();
while(!thisClass.sShell.isDisposed())
{
try
{
if(!display.readAndDispatch())
display.sleep();
}
catch(NullPointerException e)
{
if(e.getMessage()!=null&&e.getMessage().equals("12345DISCONNECT54321"))
{
}
else
{
handleMainLoopException(e);
}
continue;
}
catch(Exception e)
{
handleMainLoopException(e);
}
}
finit();
GUIHelper.logger.info("Main shell deinitialized.");
display.dispose();
GUIHelper.logger.info("Main shell disposed.");
}
catch(Exception e)
{
GUIHelper.logger.info("Main shell exception: "+e.getMessage());
handleMainLoopException(e);
}
finally
{
if(com.cognitive.admin.WindowsPrinterManager.spoolerStopped)
{
GUIHelper.logger.trace("Spooler started");
com.cognitive.admin.WindowsPrinterManager.startSpooler();
}
GUIHelper.logger.trace("finally");
System.exit(0);
}
}
static private void handleMainLoopException(Exception e)
{
GUIHelper.error("Error: "+e.toString()+Messages.getString("MainShell.19"));
try
{
GUIHelper.logger.error(e, e);
PrinterHelper.printer.closeConnection();
GUIHelper.mainShell.setConnectionState(false, false);
}
catch(Exception e2)
{
GUIHelper.logger.error("Caught an exception closing printer connection after exception in main display loop.", e2);
}
GUIHelper.logger.error("Caught an Exception: ", e);
GUIHelper.setCursor(0);
}
private void tabSelected(String tabName)
{
if(tabName.equals(Messages.getString("MainShell.20")))
{
labelPrinterStatus.setVisible(false);
buttonHeartbeat.setVisible(false);
}
else
{
labelPrinterStatus.setVisible(true);
buttonHeartbeat.setVisible(true);
}
}
public ConnectionType getConnectionType()
{
return compositeConnectionSettings.getConnectionType();
}
private void createSShell()
{
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=3;
sShell=new Shell();
GUIHelper.myShell=sShell;
GUIHelper.myDisplay=sShell.getDisplay();
GUIHelper.mainShell=this;
sShell.setText(Messages.getString("MainShell.21").replace("Cognitive", GUIHelper.brand.getProperty("company.name", "CognitiveTPG"))+Versions.jadmin);
sShell.setImage(new Image(Display.getCurrent(), GUIHelper.myShell.getClass().getClassLoader().getResourceAsStream("com/cognitive/brand/Main.ico")));
sShell.setLayout(gridLayout);
Label filler=new Label(sShell, SWT.NONE);
createGroupPrinterStatus();
createTabFolder();
sShell.setSize(sShell.computeSize(SWT.DEFAULT, SWT.DEFAULT));
setConnectionState(false, false);
}
}
