public class CompositeConnectionSettings extends Composite
{
private ConnectionType connectionType=ConnectionType.UNKNOWN;
private Group groupConnectionType=null;
private Group groupConnectionParameters=null;
private Button buttonConnect=null;
private Group groupSerialParameters=null;
private Group groupNetworkParameters=null;
private Button radioButtonSerialConnection=null;
private Label labelComPort=null;
private Label labelBaudRate=null;
private Combo comboBaudRate=null;
private Button radioButtonSystemPrinterConnection=null;
private Button radioButtonNetworkConnection=null;
private Text textIPAddress=null;
private Label labelIPAddress=null;
private Label labelIPPort=null;
private Text textIPPort=null;
private Button buttonTestLabel=null;
private Combo comboComPort=null;
private Label labelNPIPAddress=null;
private Label labelNPNetmask=null;
private Label labelNPGateway=null;
private Button checkBoxNPLPD=null;
private Button checkBoxNPRTEL=null;
private Button checkBoxNPDHCP=null;
private Label labelNPRTELPort=null;
private Label labelNPBufSize=null;
private Text textNPIPAddress=null;
private Text textNPNetmask=null;
private Text textNPRTELPort=null;
private Text textNPBufSize=null;
private Label filler3=null;
private Label filler5=null;
private Label filler6=null;
private Label filler7=null;
private Text textNPGateway=null;
private Label filler9=null;
private Label filler22=null;
private Label filler12=null;
private Label filler15=null;
private Label filler33=null;
private Label filler37=null;
private Label filler41=null;
private Button buttonNPApply=null;
private Button buttonNPRefresh=null;
private Label labelSCBaudRate=null;
private Label labelSCBufSize=null;
private Label labelSCBufOverflow=null;
private Combo comboSCBaudRate=null;
private Text textSCBufSize=null;
private Text textSCBufOverflow=null;
private Label filler48=null;
private Label filler50=null;
private Label filler64=null;
private Label filler74=null;
private Button buttonSCApply=null;
private Button buttonSCRefresh=null;
private Label filler59=null;
private Label filler63=null;
private Button checkBoxDumbTerminal=null;
public static String comPort;
public static int baudRate;
public static int stopBits;
String printerName;
String IPAddress;
int IPPort;
String lptPort;
boolean dumbTerminal;
private Button radioButtonParallelConnection=null;
private Label labelParallelPort=null;
private Combo comboParallelPort=null;
private Label labelParallelNote=null;
private Combo comboStopBits=null;
private Label labelStopBits=null;
private Button checkBoxSCXonXoff=null;
private Label labelSCStopBits=null;
private Combo comboSCStopBits=null;
static public boolean isConnectionTypeParallel=false;
static public boolean isConnectionTypeSerial=false;
private Label filler121=null;
private Label filler1811=null;
private Text textArea=null;
private Label labelNoteEthernetSettings=null;
private Button checkBoxIsBT=null;
private void enableSerialConnection()
{
GUIHelper.logger.trace("");
checkBoxDumbTerminal.setSelection(false);
checkBoxDumbTerminal.setEnabled(false);
radioButtonSerialConnection.setSelection(true);
radioButtonSystemPrinterConnection.setSelection(false);
radioButtonNetworkConnection.setSelection(false);
radioButtonParallelConnection.setSelection(false);
comboComPort.setEnabled(true);
comboBaudRate.setEnabled(true);
labelComPort.setEnabled(true);
labelBaudRate.setEnabled(true);
comboStopBits.setEnabled(true);
labelStopBits.setEnabled(true);
textIPAddress.setEnabled(false);
textIPPort.setEnabled(false);
labelIPAddress.setEnabled(false);
labelIPPort.setEnabled(false);
comboParallelPort.setEnabled(false);
labelParallelPort.setEnabled(false);
}
private void enableOSPrinterConnection()
{
GUIHelper.logger.trace("");
checkBoxDumbTerminal.setSelection(false);
checkBoxDumbTerminal.setEnabled(false);
radioButtonSerialConnection.setSelection(false);
radioButtonSystemPrinterConnection.setSelection(true);
radioButtonNetworkConnection.setSelection(false);
radioButtonParallelConnection.setSelection(false);
comboComPort.setEnabled(false);
comboBaudRate.setEnabled(false);
labelComPort.setEnabled(false);
labelBaudRate.setEnabled(false);
comboStopBits.setEnabled(false);
labelStopBits.setEnabled(false);
textIPAddress.setEnabled(false);
textIPPort.setEnabled(false);
labelIPAddress.setEnabled(false);
labelIPPort.setEnabled(false);
comboParallelPort.setEnabled(false);
labelParallelPort.setEnabled(false);
}
private void enableNetworkConnection()
{
GUIHelper.logger.trace("");
checkBoxDumbTerminal.setSelection(false);
checkBoxDumbTerminal.setEnabled(false);
radioButtonSerialConnection.setSelection(false);
radioButtonSystemPrinterConnection.setSelection(false);
radioButtonNetworkConnection.setSelection(true);
radioButtonParallelConnection.setSelection(false);
comboComPort.setEnabled(false);
comboBaudRate.setEnabled(false);
labelComPort.setEnabled(false);
labelBaudRate.setEnabled(false);
comboStopBits.setEnabled(false);
labelStopBits.setEnabled(false);
textIPAddress.setEnabled(true);
textIPPort.setEnabled(true);
labelIPAddress.setEnabled(true);
labelIPPort.setEnabled(true);
comboParallelPort.setEnabled(false);
labelParallelPort.setEnabled(false);
}
private void enableParallelConnection()
{
GUIHelper.logger.trace("");
checkBoxDumbTerminal.setSelection(true);
checkBoxDumbTerminal.setEnabled(false);
radioButtonSerialConnection.setSelection(false);
radioButtonSystemPrinterConnection.setSelection(false);
radioButtonNetworkConnection.setSelection(false);
radioButtonParallelConnection.setSelection(true);
comboComPort.setEnabled(false);
comboBaudRate.setEnabled(false);
labelComPort.setEnabled(false);
labelBaudRate.setEnabled(false);
comboStopBits.setEnabled(false);
labelStopBits.setEnabled(false);
textIPAddress.setEnabled(false);
textIPPort.setEnabled(false);
labelIPAddress.setEnabled(false);
labelIPPort.setEnabled(false);
comboParallelPort.setEnabled(true);
labelParallelPort.setEnabled(true);
}
private void displayBTText()
{
if(checkBoxIsBT.getSelection())
{
radioButtonSerialConnection.setText(Messages.getString("CompositeConnectionSettings.41"));
}
else
{
radioButtonSerialConnection.setText(Messages.getString("CompositeConnectionSettings.10"));
}
}
private void togglePrinterConnection()
{
GUIHelper.logger.trace("");
dumbTerminal=checkBoxDumbTerminal.getSelection();
if(buttonConnect.getText().equals(Messages.getString("CompositeConnectionSettings.0")))
{
disconnect();
}
else
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
if(connect())
{
GUIHelper.mainShell.setConnectionState(true, dumbTerminal);
}
else
{
GUIHelper.error(Messages.getString("CompositeConnectionSettings.1"));
}
GUIHelper.setCursor(0);
}
}
private void populateBaudRateCombos()
{
GUIHelper.logger.trace("");
comboBaudRate.add("4800");
comboBaudRate.add("9600");
comboBaudRate.add("19200");
comboBaudRate.add("38400");
comboBaudRate.add("115200");
comboBaudRate.select(4);
comboSCBaudRate.add("4800");
comboSCBaudRate.add("9600");
comboSCBaudRate.add("19200");
comboSCBaudRate.add("38400");
comboSCBaudRate.add("115200");
comboSCBaudRate.select(4);
}
private void populateStopBitsCombos()
{
GUIHelper.logger.trace("");
comboStopBits.add("1");
comboStopBits.add("2");
comboStopBits.select(0);
comboSCStopBits.add("1");
comboSCStopBits.add("2");
comboSCStopBits.select(0);
}
public void setConnectionState(boolean connected, boolean dumbTerminal)
{
GUIHelper.logger.trace("");
GUIHelper.setEnabled(groupConnectionParameters, connected&&!dumbTerminal);
if(connected)
{
GUIHelper.setEnabled(groupConnectionType, false);
groupConnectionType.setEnabled(true);
checkBoxDumbTerminal.setSelection(dumbTerminal);
checkBoxDumbTerminal.setEnabled(false);
buttonConnect.setEnabled(true);
buttonConnect.setText(Messages.getString("CompositeConnectionSettings.0"));
buttonTestLabel.setEnabled(true);
if(!dumbTerminal)
{
refreshGroupNetworkParameters();
refreshGroupSerialParameters();
}
}
else
{
GUIHelper.setEnabled(groupConnectionType, true);
switch(connectionType)
{
case SERIAL:
{
enableSerialConnection();
}
break;
case OS_PRINTER:
{
enableOSPrinterConnection();
}
break;
case NETWORK:
{
enableNetworkConnection();
}
break;
case PARALLEL:
{
enableParallelConnection();
}
break;
default:
{
connectionType=ConnectionType.SERIAL;
enableSerialConnection();
}
}
buttonConnect.setText(Messages.getString("CompositeConnectionSettings.3"));
buttonTestLabel.setEnabled(false);
}
layout(true, true);
}
public void disconnect()
{
GUIHelper.logger.trace("");
try
{
if(PrinterHelper.printer!=null)
{
PrinterHelper.printer.closeConnection();
GUIHelper.mainShell.setConnectionState(false, false);
}
}
catch(Exception e)
{
GUIHelper.logger.error("", e);
GUIHelper.myDisplay.syncExec(new Runnable()
{
public void run()
{
GUIHelper.error(Messages.getString("CompositeConnectionSettings.4"));
}
});
}
}
public boolean connect()
{
GUIHelper.logger.trace("");
boolean connected=false;
if(radioButtonSerialConnection.getSelection()==true)
{
comPort=comboComPort.getText();
baudRate=Integer.parseInt(comboBaudRate.getText());
stopBits=Integer.parseInt(comboStopBits.getText());
connectionType=ConnectionType.SERIAL;
GUIHelper.prefs.put("DEFAULT_CONNECTION_TYPE", "SERIAL");
GUIHelper.prefs.put("COM_PORT", comPort);
GUIHelper.prefs.putInt("BAUD_RATE", baudRate);
GUIHelper.prefs.putInt("STOP_BITS", stopBits);
}
else if(radioButtonSystemPrinterConnection.getSelection()==true)
{
printerName="Printer";
connectionType=ConnectionType.OS_PRINTER;
GUIHelper.prefs.put("DEFAULT_CONNECTION_TYPE", "OS_PRINTER");
GUIHelper.prefs.put("PRINTER_NAME", printerName);
}
else if(radioButtonNetworkConnection.getSelection()==true)
{
IPAddress=textIPAddress.getText();
IPPort=Integer.parseInt(textIPPort.getText());
connectionType=ConnectionType.NETWORK;
GUIHelper.prefs.put("DEFAULT_CONNECTION_TYPE", "NETWORK");
GUIHelper.prefs.put("IP_ADDRESS", IPAddress);
GUIHelper.prefs.putInt("IP_PORT", IPPort);
}
else if(radioButtonParallelConnection.getSelection()==true)
{
lptPort=comboParallelPort.getText();
connectionType=ConnectionType.PARALLEL;
GUIHelper.prefs.put("DEFAULT_CONNECTION_TYPE", "PARALLEL");
GUIHelper.prefs.put("LPT_PORT", lptPort);
}
else
{
connectionType=ConnectionType.UNKNOWN;
GUIHelper.error(Messages.getString("CompositeConnectionSettings.5"));
return connected;
}
return establishConnection();
}
public boolean establishConnection()
{
GUIHelper.logger.trace("");
boolean connected=false;
TestEngine.runningManufacturing=false;
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
GUIHelper.logger.warn(e, e);
}
PrinterHelper.printer.addObserver(GUIHelper.mainShell);
IPrinterConnection conn=null;
try
{
switch(connectionType)
{
case SERIAL:
conn=new SerialConnection(comPort, baudRate, stopBits);
PrinterHelper.printer.openConnection(conn);
isConnectionTypeSerial=true;
isConnectionTypeParallel=false;
break;
case OS_PRINTER:
conn=new WindowsPrinterConnection();
PrinterHelper.printer.openConnection(conn);
isConnectionTypeSerial=false;
isConnectionTypeParallel=false;
break;
case NETWORK:
conn=new NetworkConnection(IPAddress, IPPort);
PrinterHelper.printer.openConnection(conn);
isConnectionTypeSerial=false;
isConnectionTypeParallel=false;
break;
case PARALLEL:
conn=new ParallelConnection(lptPort);
PrinterHelper.printer.openConnection(conn);
isConnectionTypeSerial=false;
isConnectionTypeParallel=true;
break;
}
if(!dumbTerminal)
{
if(!PrinterHelper.printer.isConnected())
{
PrinterHelper.printer.closeConnection();
connected=false;
}
else
{
connected=true;
}
}
else
{
connected=true;
}
}
catch(Exception e)
{
connected=false;
GUIHelper.logger.error(e, e);
}
return connected;
}
private void refreshComPortList()
{
GUIHelper.logger.trace("");
ArrayList<String>list=new ArrayList<String>();
CommPortIdentifier port;
Enumeration portList;
boolean portFound=false;
String defaultPort="";
portList=(Enumeration)CommPortIdentifier.getPortIdentifiers();
while(portList.hasMoreElements())
{
port=(CommPortIdentifier)portList.nextElement();
if(port.getPortType()==CommPortIdentifier.PORT_SERIAL)
{
GUIHelper.logger.debug("["+port.getName()+"]");
comboComPort.add(port.getName());
}
}
}
private void refreshParallelPortList()
{
GUIHelper.logger.trace("");
ArrayList<String>list=new ArrayList<String>();
CommPortIdentifier port;
Enumeration portList;
boolean portFound=false;
String defaultPort="";
portList=(Enumeration)CommPortIdentifier.getPortIdentifiers();
while(portList.hasMoreElements())
{
port=(CommPortIdentifier)portList.nextElement();
if(port.getPortType()==CommPortIdentifier.PORT_PARALLEL)
{
GUIHelper.logger.debug("["+port.getName()+"]");
comboParallelPort.add(port.getName());
}
}
}
private void refreshPrinterList()
{
GUIHelper.logger.trace("");
PrintService[] services=PrintServiceLookup.lookupPrintServices(null, null);
for(int i=0; i<services.length; i++)
{
GUIHelper.logger.debug(services[i].getName());
}
}
private void setGroupSerialParameters()
{
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
String value=null;
String xOnOff=null;
if(checkBoxSCXonXoff.getSelection())
{
xOnOff="R";
}
else
{
xOnOff="N";
}
value=comboSCBaudRate.getText()+",N,8,"+comboSCStopBits.getText()+","+xOnOff;
PrinterHelper.setVariable("COMM", value);
PrinterHelper.setVariable("TXTBFR", textSCBufSize.getText()+","+textSCBufOverflow.getText());
}
catch(Exception e)
{
GUIHelper.logger.error("", e);
GUIHelper.error(Messages.getString("CompositeConnectionSettings.6")+e.toString());
}
finally
{
if(connectionType==ConnectionType.SERIAL)
{
try
{
PrinterHelper.printer.closeConnection();
GUIHelper.mainShell.setConnectionState(false, false);
}
catch(Exception e)
{
GUIHelper.logger.error("In finally{}-unable to close connection:", e);
}
}
GUIHelper.setCursor(0);
}
}
private void refreshGroupSerialParameters()
{
GUIHelper.logger.trace("");
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
String comm=PrinterHelper.getVariable("COMM");
if(comm==null)
{
return;
}
String[] params=comm.split(",");
comboSCBaudRate.select(comboSCBaudRate.indexOf(params[0].trim()));
comboSCStopBits.select(comboSCStopBits.indexOf(params[3].trim()));
comboBaudRate.select(comboBaudRate.indexOf(params[0].trim()));
comboStopBits.select(comboStopBits.indexOf(params[3].trim()));
if(params.length>4)
{
if(params[4].equals("ROBUST")||params[4].equals("ON"))
{
checkBoxSCXonXoff.setSelection(true);
}
else
{
checkBoxSCXonXoff.setSelection(false);
}
}
else
{
checkBoxSCXonXoff.setSelection(false);
}
String buffer=PrinterHelper.getVariable("TXTBFR");
if(buffer==null)
{
return;
}
params=buffer.split(",");
textSCBufSize.setText(params[0].trim());
textSCBufOverflow.setText(params[1].trim());
}
finally
{
GUIHelper.setCursor(0);
}
}
private void setGroupNetworkParameters()
{
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
PrinterHelper.setVariable("ETHERNET IP", textNPIPAddress.getText());
PrinterHelper.setVariable("ETHERNET NETMASK", textNPNetmask.getText());
PrinterHelper.setVariable("ETHERNET GATEWAY", textNPGateway.getText());
PrinterHelper.setVariable("ETHERNET DHCP", GUIHelper.stringFromBoolean(checkBoxNPDHCP.getSelection()));
PrinterHelper.setVariable("ETHERNET LPD", GUIHelper.stringFromBoolean(checkBoxNPLPD.getSelection()));
PrinterHelper.setVariable("ETHERNET RTEL", GUIHelper.stringFromBoolean(checkBoxNPRTEL.getSelection()));
PrinterHelper.setVariable("ETHERNET RTEL PORT", textNPRTELPort.getText());
PrinterHelper.setVariable("ETHERNET TXTBFR", textNPBufSize.getText());
}
catch(Exception e)
{
GUIHelper.logger.error("", e);
GUIHelper.error(Messages.getString("CompositeConnectionSettings.6")+e.toString());
}
finally
{
if(connectionType==ConnectionType.NETWORK)
{
try
{
PrinterHelper.printer.closeConnection();
GUIHelper.mainShell.setConnectionState(false, false);
}
catch(Exception e)
{
GUIHelper.logger.error("In finally{}-unable to close connection:", e);
}
}
GUIHelper.setCursor(0);
}
}
private void refreshGroupNetworkParameters()
{
GUIHelper.logger.trace("");
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
GUIHelper.blankify(groupNetworkParameters);
String IPAddress=PrinterHelper.getVariable("ETHERNET IP");
textNPIPAddress.setText(IPAddress);
textNPNetmask.setText(PrinterHelper.getVariable("ETHERNET NETMASK"));
textNPGateway.setText(PrinterHelper.getVariable("ETHERNET GATEWAY"));
checkBoxNPDHCP.setSelection(GUIHelper.booleanFromString(PrinterHelper.printer.getVariable("ETHERNET DHCP")));
checkBoxNPLPD.setSelection(GUIHelper.booleanFromString(PrinterHelper.printer.getVariable("ETHERNET LPD")));
checkBoxNPRTEL.setSelection(GUIHelper.booleanFromString(PrinterHelper.printer.getVariable("ETHERNET RTEL")));
String IPPort=PrinterHelper.getVariable("ETHERNET RTEL PORT");
textNPRTELPort.setText(IPPort);
textNPBufSize.setText(PrinterHelper.getVariable("ETHERNET TXTBFR"));
if(!IPAddress.equalsIgnoreCase(textIPAddress.getText()))
{
}
else
{
textIPAddress.setText(IPAddress);
}
textIPPort.setText(IPPort);
}
catch(NullPointerException npe)
{
throw npe;
}
catch(Exception e)
{
GUIHelper.logger.error("", e);
GUIHelper.error(Messages.getString("CompositeConnectionSettings.6")+e.toString());
}
finally
{
GUIHelper.setCursor(0);
}
}
public CompositeConnectionSettings(Composite parent, int style)
{
super(parent, style);
GUIHelper.logger.trace("");
initialize();
}
private void initialize()
{
GUIHelper.logger.trace("");
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=2;
gridLayout.makeColumnsEqualWidth=false;
this.setLayout(gridLayout);
createGroupConnectionType();
createGroupConnectionParameters();
this.setSize(new Point(545, 477));
showDumbTerminalCheckBox();
setDefaultConnectionParameters();
}
private void createGroupConnectionType()
{
GUIHelper.logger.trace("");
GridData gridData58=new GridData();
gridData58.horizontalAlignment=GridData.BEGINNING;
gridData58.verticalAlignment=GridData.END;
GridData gridData56=new GridData();
gridData56.horizontalAlignment=GridData.FILL;
gridData56.grabExcessHorizontalSpace=true;
gridData56.grabExcessVerticalSpace=true;
gridData56.verticalAlignment=GridData.FILL;
GridData gridData20=new GridData();
gridData20.horizontalAlignment=GridData.FILL;
gridData20.grabExcessVerticalSpace=true;
gridData20.grabExcessHorizontalSpace=true;
gridData20.verticalAlignment=GridData.FILL;
GridData gridData18=new GridData();
gridData18.horizontalAlignment=GridData.FILL;
gridData18.grabExcessHorizontalSpace=true;
gridData18.grabExcessVerticalSpace=true;
gridData18.verticalAlignment=GridData.FILL;
GridData gridData55=new GridData();
gridData55.horizontalAlignment=GridData.END;
gridData55.verticalAlignment=GridData.CENTER;
GridData gridData54=new GridData();
gridData54.horizontalSpan=2;
GridData gridData53=new GridData();
gridData53.horizontalIndent=0;
GridData gridData52=new GridData();
gridData52.horizontalIndent=0;
GridData gridData51=new GridData();
gridData51.horizontalAlignment=GridData.END;
gridData51.verticalAlignment=GridData.CENTER;
GridData gridData19=new GridData();
gridData19.grabExcessHorizontalSpace=true;
GridData gridData16=new GridData();
gridData16.grabExcessVerticalSpace=true;
gridData16.verticalAlignment=GridData.FILL;
gridData16.grabExcessHorizontalSpace=true;
gridData16.horizontalAlignment=GridData.FILL;
GridData gridData25=new GridData();
gridData25.grabExcessHorizontalSpace=false;
GridData gridData24=new GridData();
gridData24.grabExcessHorizontalSpace=false;
gridData24.horizontalAlignment=GridData.BEGINNING;
gridData24.verticalAlignment=GridData.CENTER;
gridData24.widthHint=-1;
GridData gridData17=new GridData();
gridData17.horizontalAlignment=GridData.CENTER;
gridData17.grabExcessHorizontalSpace=false;
gridData17.horizontalSpan=2;
gridData17.verticalAlignment=GridData.CENTER;
GridData gridData15=new GridData();
gridData15.horizontalIndent=0;
gridData15.verticalAlignment=GridData.CENTER;
gridData15.grabExcessHorizontalSpace=false;
gridData15.horizontalAlignment=GridData.FILL;
GridData gridData14=new GridData();
gridData14.horizontalIndent=0;
gridData14.verticalAlignment=GridData.CENTER;
gridData14.grabExcessHorizontalSpace=false;
gridData14.horizontalAlignment=GridData.BEGINNING;
GridData gridData13=new GridData();
gridData13.horizontalIndent=0;
gridData13.verticalAlignment=GridData.CENTER;
gridData13.grabExcessHorizontalSpace=false;
gridData13.horizontalAlignment=GridData.BEGINNING;
GridData gridData9=new GridData();
gridData9.horizontalAlignment=GridData.END;
gridData9.horizontalIndent=0;
gridData9.grabExcessHorizontalSpace=false;
gridData9.verticalAlignment=GridData.CENTER;
GridData gridData8=new GridData();
gridData8.horizontalAlignment=GridData.END;
gridData8.horizontalIndent=0;
gridData8.grabExcessHorizontalSpace=false;
gridData8.verticalAlignment=GridData.CENTER;
GridData gridData4=new GridData();
gridData4.horizontalAlignment=GridData.END;
gridData4.horizontalIndent=0;
gridData4.grabExcessHorizontalSpace=false;
gridData4.verticalAlignment=GridData.CENTER;
GridData gridData3=new GridData();
gridData3.horizontalAlignment=GridData.END;
gridData3.horizontalIndent=0;
gridData3.grabExcessHorizontalSpace=false;
gridData3.verticalAlignment=GridData.CENTER;
GridData gridData2=new GridData();
gridData2.horizontalAlignment=GridData.END;
gridData2.horizontalIndent=0;
gridData2.grabExcessHorizontalSpace=false;
gridData2.verticalAlignment=GridData.CENTER;
GridLayout gridLayout2=new GridLayout();
gridLayout2.numColumns=4;
gridLayout2.marginWidth=5;
gridLayout2.horizontalSpacing=5;
gridLayout2.verticalSpacing=5;
gridLayout2.makeColumnsEqualWidth=false;
gridLayout2.marginHeight=5;
GridData gridData5=new GridData();
gridData5.horizontalAlignment=GridData.CENTER;
gridData5.grabExcessHorizontalSpace=false;
gridData5.grabExcessVerticalSpace=false;
gridData5.heightHint=-1;
gridData5.widthHint=-1;
gridData5.horizontalSpan=2;
gridData5.verticalAlignment=GridData.END;
GridData gridData1=new GridData();
gridData1.horizontalAlignment=GridData.FILL;
gridData1.grabExcessHorizontalSpace=true;
gridData1.grabExcessVerticalSpace=true;
gridData1.horizontalIndent=0;
gridData1.heightHint=-1;
gridData1.verticalAlignment=GridData.FILL;
groupConnectionType=new Group(this, SWT.NONE);
groupConnectionType.setText(Messages.getString("CompositeConnectionSettings.9"));
groupConnectionType.setLayout(gridLayout2);
groupConnectionType.setLayoutData(gridData1);
filler12=new Label(groupConnectionType, SWT.NONE);
filler12.setText("");
filler12.setLayoutData(gridData16);
checkBoxIsBT=new Button(groupConnectionType, SWT.CHECK);
checkBoxIsBT.setText(Messages.getString("CompositeConnectionSettings.42"));
checkBoxIsBT.setLayoutData(gridData58);
checkBoxIsBT
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
displayBTText();
}
});
Label filler35=new Label(groupConnectionType, SWT.NONE);
Label filler181=new Label(groupConnectionType, SWT.NONE);
filler181.setLayoutData(gridData18);
filler15=new Label(groupConnectionType, SWT.NONE);
filler15.setText("");
filler15.setLayoutData(gridData19);
radioButtonSerialConnection=new Button(groupConnectionType, SWT.RADIO);
radioButtonSerialConnection.setText(Messages.getString("CompositeConnectionSettings.10"));
radioButtonSerialConnection.setLayoutData(gridData15);
Label filler34=new Label(groupConnectionType, SWT.NONE);
Label filler180=new Label(groupConnectionType, SWT.NONE);
radioButtonSerialConnection
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
enableSerialConnection();
}
});
Label filler30=new Label(groupConnectionType, SWT.NONE);
labelComPort=new Label(groupConnectionType, SWT.NONE);
labelComPort.setText(Messages.getString("CompositeConnectionSettings.11"));
labelComPort.setLayoutData(gridData2);
createComboComPort();
Label filler179=new Label(groupConnectionType, SWT.NONE);
Label filler36=new Label(groupConnectionType, SWT.NONE);
labelBaudRate=new Label(groupConnectionType, SWT.LEFT);
labelBaudRate.setText(Messages.getString("CompositeConnectionSettings.12"));
labelBaudRate.setLayoutData(gridData3);
createComboBaudRate();
Label filler178=new Label(groupConnectionType, SWT.NONE);
Label filler29=new Label(groupConnectionType, SWT.NONE);
labelStopBits=new Label(groupConnectionType, SWT.LEFT);
labelStopBits.setText(Messages.getString("CompositeConnectionSettings.38"));
labelStopBits.setLayoutData(gridData55);
createComboStopBits();
Label filler176=new Label(groupConnectionType, SWT.NONE);
filler33=new Label(groupConnectionType, SWT.NONE);
filler33.setText("");
Label filler177=new Label(groupConnectionType, SWT.NONE);
Label filler27=new Label(groupConnectionType, SWT.NONE);
Label filler174=new Label(groupConnectionType, SWT.NONE);
Label filler165=new Label(groupConnectionType, SWT.NONE);
radioButtonSystemPrinterConnection=new Button(groupConnectionType, SWT.RADIO);
radioButtonSystemPrinterConnection.setText(Messages.getString("CompositeConnectionSettings.39"));
radioButtonSystemPrinterConnection.setLayoutData(gridData13);
Label filler25=new Label(groupConnectionType, SWT.NONE);
Label filler173=new Label(groupConnectionType, SWT.NONE);
radioButtonSystemPrinterConnection
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
enableOSPrinterConnection();
}
});
filler37=new Label(groupConnectionType, SWT.NONE);
filler37.setText("");
Label filler175=new Label(groupConnectionType, SWT.NONE);
Label filler24=new Label(groupConnectionType, SWT.NONE);
Label filler172=new Label(groupConnectionType, SWT.NONE);
Label filler279=new Label(groupConnectionType, SWT.NONE);
radioButtonNetworkConnection=new Button(groupConnectionType, SWT.RADIO);
radioButtonNetworkConnection.setText(Messages.getString("CompositeConnectionSettings.15"));
radioButtonNetworkConnection.setLayoutData(gridData14);
Label filler23=new Label(groupConnectionType, SWT.NONE);
Label filler170=new Label(groupConnectionType, SWT.NONE);
radioButtonNetworkConnection
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
enableNetworkConnection();
}
});
Label filler28=new Label(groupConnectionType, SWT.NONE);
labelIPAddress=new Label(groupConnectionType, SWT.NONE);
labelIPAddress.setText(Messages.getString("CompositeConnectionSettings.16"));
labelIPAddress.setLayoutData(gridData8);
textIPAddress=new Text(groupConnectionType, SWT.BORDER);
textIPAddress.setText("255.255.255.255");
textIPAddress.setLayoutData(gridData24);
Label filler169=new Label(groupConnectionType, SWT.NONE);
Label filler21=new Label(groupConnectionType, SWT.NONE);
labelIPPort=new Label(groupConnectionType, SWT.NONE);
labelIPPort.setText(Messages.getString("CompositeConnectionSettings.17"));
labelIPPort.setLayoutData(gridData9);
textIPPort=new Text(groupConnectionType, SWT.BORDER);
textIPPort.setText("9100");
textIPPort.setLayoutData(gridData25);
Label filler168=new Label(groupConnectionType, SWT.NONE);
filler41=new Label(groupConnectionType, SWT.NONE);
filler41.setText("");
Label filler171=new Label(groupConnectionType, SWT.NONE);
Label filler17=new Label(groupConnectionType, SWT.NONE);
Label filler167=new Label(groupConnectionType, SWT.NONE);
Label filler1770=new Label(groupConnectionType, SWT.NONE);
radioButtonParallelConnection=new Button(groupConnectionType, SWT.RADIO);
radioButtonParallelConnection.setText(Messages.getString("CompositeConnectionSettings.35"));
radioButtonParallelConnection.setLayoutData(gridData52);
Label filler16=new Label(groupConnectionType, SWT.NONE);
Label filler32=new Label(groupConnectionType, SWT.NONE);
radioButtonParallelConnection
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
enableParallelConnection();
}
});
Label filler18=new Label(groupConnectionType, SWT.NONE);
labelParallelPort=new Label(groupConnectionType, SWT.NONE);
labelParallelPort.setText(Messages.getString("CompositeConnectionSettings.36"));
labelParallelPort.setLayoutData(gridData51);
createComboParallelPort();
Label filler31=new Label(groupConnectionType, SWT.NONE);
Label filler184=new Label(groupConnectionType, SWT.NONE);
labelParallelNote=new Label(groupConnectionType, SWT.WRAP);
labelParallelNote.setText(Messages.getString("CompositeConnectionSettings.37"));
labelParallelNote.setLayoutData(gridData54);
Label filler26=new Label(groupConnectionType, SWT.NONE);
Label filler1822=new Label(groupConnectionType, SWT.NONE);
checkBoxDumbTerminal=new Button(groupConnectionType, SWT.CHECK);
checkBoxDumbTerminal.setText(Messages.getString("CompositeConnectionSettings.18"));
checkBoxDumbTerminal.setLayoutData(gridData53);
checkBoxDumbTerminal.setEnabled(false);
Label filler2=new Label(groupConnectionType, SWT.NONE);
Label filler13=new Label(groupConnectionType, SWT.NONE);
Label filler185=new Label(groupConnectionType, SWT.NONE);
buttonConnect=new Button(groupConnectionType, SWT.NONE);
buttonConnect.setText(Messages.getString("CompositeConnectionSettings.0"));
buttonConnect.setLayoutData(gridData5);
Label filler=new Label(groupConnectionType, SWT.NONE);
Label filler166=new Label(groupConnectionType, SWT.NONE);
buttonTestLabel=new Button(groupConnectionType, SWT.NONE);
buttonTestLabel.setText(Messages.getString("CompositeConnectionSettings.20"));
buttonTestLabel.setLayoutData(gridData17);
Label filler14=new Label(groupConnectionType, SWT.NONE);
filler121=new Label(groupConnectionType, SWT.NONE);
filler121.setText("");
filler121.setLayoutData(gridData20);
Label filler38=new Label(groupConnectionType, SWT.NONE);
Label filler39=new Label(groupConnectionType, SWT.NONE);
filler1811=new Label(groupConnectionType, SWT.NONE);
filler1811.setLayoutData(gridData56);
buttonConnect
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
togglePrinterConnection();
}
});
buttonTestLabel
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
PrinterHelper.printSelfTestLabel();
}
});
}
private void createGroupConnectionParameters()
{
GUIHelper.logger.trace("");
GridLayout gridLayout1=new GridLayout();
gridLayout1.makeColumnsEqualWidth=false;
GridData gridData=new GridData();
gridData.horizontalAlignment=GridData.FILL;
gridData.grabExcessHorizontalSpace=true;
gridData.grabExcessVerticalSpace=true;
gridData.verticalAlignment=GridData.FILL;
groupConnectionParameters=new Group(this, SWT.NONE);
groupConnectionParameters.setText(Messages.getString("CompositeConnectionSettings.21"));
createGroupSerialParameters();
groupConnectionParameters.setLayoutData(gridData);
groupConnectionParameters.setLayout(gridLayout1);
createGroupNetworkParameters();
}
private void createGroupSerialParameters()
{
GUIHelper.logger.trace("");
GridData gridData46=new GridData();
gridData46.horizontalAlignment=GridData.FILL;
gridData46.verticalAlignment=GridData.CENTER;
GridData gridData45=new GridData();
gridData45.horizontalAlignment=GridData.FILL;
gridData45.verticalAlignment=GridData.CENTER;
GridData gridData42=new GridData();
gridData42.grabExcessVerticalSpace=true;
GridData gridData41=new GridData();
gridData41.grabExcessVerticalSpace=true;
GridData gridData40=new GridData();
gridData40.grabExcessVerticalSpace=true;
GridData gridData39=new GridData();
gridData39.grabExcessHorizontalSpace=true;
GridData gridData23=new GridData();
gridData23.grabExcessHorizontalSpace=true;
GridLayout gridLayout4=new GridLayout();
gridLayout4.numColumns=4;
gridLayout4.verticalSpacing=2;
gridLayout4.marginWidth=2;
gridLayout4.marginHeight=2;
gridLayout4.horizontalSpacing=2;
GridData gridData6=new GridData();
gridData6.horizontalAlignment=GridData.FILL;
gridData6.grabExcessVerticalSpace=true;
gridData6.grabExcessHorizontalSpace=true;
gridData6.verticalAlignment=GridData.FILL;
groupSerialParameters=new Group(groupConnectionParameters, SWT.NONE);
groupSerialParameters.setLayoutData(gridData6);
groupSerialParameters.setLayout(gridLayout4);
groupSerialParameters.setText(Messages.getString("CompositeConnectionSettings.22"));
Label filler75=new Label(groupSerialParameters, SWT.NONE);
Label filler47=new Label(groupSerialParameters, SWT.NONE);
filler48=new Label(groupSerialParameters, SWT.NONE);
filler48.setText("");
filler48.setLayoutData(gridData40);
Label filler65=new Label(groupSerialParameters, SWT.NONE);
filler74=new Label(groupSerialParameters, SWT.NONE);
filler74.setText("");
filler74.setLayoutData(gridData23);
labelSCBaudRate=new Label(groupSerialParameters, SWT.NONE);
labelSCBaudRate.setText(Messages.getString("CompositeConnectionSettings.23"));
createComboSCBaudRate();
filler64=new Label(groupSerialParameters, SWT.NONE);
filler64.setText("");
filler64.setLayoutData(gridData39);
Label filler464=new Label(groupSerialParameters, SWT.NONE);
labelSCStopBits=new Label(groupSerialParameters, SWT.LEFT);
labelSCStopBits.setText(Messages.getString("CompositeConnectionSettings.38"));
createComboSCStopBits();
Label filler466=new Label(groupSerialParameters, SWT.NONE);
Label filler461=new Label(groupSerialParameters, SWT.NONE);
checkBoxSCXonXoff=new Button(groupSerialParameters, SWT.CHECK);
checkBoxSCXonXoff.setText("XON/XOFF");
Label filler462=new Label(groupSerialParameters, SWT.NONE);
Label filler463=new Label(groupSerialParameters, SWT.NONE);
Label filler72=new Label(groupSerialParameters, SWT.NONE);
labelSCBufSize=new Label(groupSerialParameters, SWT.NONE);
labelSCBufSize.setText(Messages.getString("CompositeConnectionSettings.24"));
textSCBufSize=new Text(groupSerialParameters, SWT.BORDER);
textSCBufSize.setText("");
textSCBufSize.setLayoutData(gridData45);
Label filler52=new Label(groupSerialParameters, SWT.NONE);
Label filler71=new Label(groupSerialParameters, SWT.NONE);
labelSCBufOverflow=new Label(groupSerialParameters, SWT.NONE);
labelSCBufOverflow.setText(Messages.getString("CompositeConnectionSettings.25"));
textSCBufOverflow=new Text(groupSerialParameters, SWT.BORDER);
textSCBufOverflow.setText("");
textSCBufOverflow.setLayoutData(gridData46);
Label filler51=new Label(groupSerialParameters, SWT.NONE);
Label filler70=new Label(groupSerialParameters, SWT.NONE);
Label filler49=new Label(groupSerialParameters, SWT.NONE);
filler50=new Label(groupSerialParameters, SWT.NONE);
filler50.setText("");
filler50.setLayoutData(gridData41);
Label filler80=new Label(groupSerialParameters, SWT.NONE);
Label filler81=new Label(groupSerialParameters, SWT.NONE);
buttonSCApply=new Button(groupSerialParameters, SWT.NONE);
buttonSCApply.setText(Messages.getString("General.0"));
buttonSCApply
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
setGroupSerialParameters();
}
});
buttonSCRefresh=new Button(groupSerialParameters, SWT.NONE);
buttonSCRefresh.setText(Messages.getString("General.1"));
buttonSCRefresh
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
refreshGroupSerialParameters();
}
});
Label filler56=new Label(groupSerialParameters, SWT.NONE);
Label filler57=new Label(groupSerialParameters, SWT.NONE);
Label filler58=new Label(groupSerialParameters, SWT.NONE);
filler59=new Label(groupSerialParameters, SWT.NONE);
filler59.setText("");
filler59.setLayoutData(gridData42);
}
private void createGroupNetworkParameters()
{
GUIHelper.logger.trace("");
GridData gridData57=new GridData();
gridData57.verticalSpan=3;
gridData57.verticalAlignment=GridData.BEGINNING;
gridData57.horizontalAlignment=GridData.BEGINNING;
GridData gridData50=new GridData();
gridData50.horizontalAlignment=GridData.FILL;
gridData50.verticalAlignment=GridData.CENTER;
GridData gridData49=new GridData();
gridData49.horizontalAlignment=GridData.FILL;
gridData49.verticalAlignment=GridData.CENTER;
GridData gridData48=new GridData();
gridData48.horizontalAlignment=GridData.FILL;
gridData48.verticalAlignment=GridData.CENTER;
GridData gridData47=new GridData();
gridData47.horizontalAlignment=GridData.FILL;
gridData47.verticalAlignment=GridData.CENTER;
GridData gridData43=new GridData();
gridData43.grabExcessVerticalSpace=true;
GridData gridData22=new GridData();
gridData22.horizontalAlignment=GridData.CENTER;
gridData22.verticalAlignment=GridData.CENTER;
GridData gridData21=new GridData();
gridData21.verticalAlignment=GridData.CENTER;
gridData21.horizontalAlignment=GridData.CENTER;
GridData gridData38=new GridData();
gridData38.horizontalAlignment=GridData.FILL;
gridData38.verticalAlignment=GridData.CENTER;
GridData gridData37=new GridData();
gridData37.horizontalAlignment=GridData.FILL;
gridData37.verticalAlignment=GridData.CENTER;
GridData gridData36=new GridData();
gridData36.horizontalAlignment=GridData.FILL;
gridData36.verticalAlignment=GridData.CENTER;
GridData gridData35=new GridData();
gridData35.horizontalAlignment=GridData.FILL;
gridData35.verticalAlignment=GridData.CENTER;
GridData gridData34=new GridData();
gridData34.horizontalAlignment=GridData.BEGINNING;
gridData34.verticalAlignment=GridData.CENTER;
GridData gridData33=new GridData();
gridData33.horizontalAlignment=GridData.BEGINNING;
gridData33.verticalAlignment=GridData.CENTER;
GridData gridData32=new GridData();
gridData32.horizontalAlignment=GridData.BEGINNING;
gridData32.verticalAlignment=GridData.CENTER;
GridData gridData31=new GridData();
gridData31.horizontalAlignment=GridData.BEGINNING;
gridData31.verticalAlignment=GridData.CENTER;
GridData gridData30=new GridData();
gridData30.horizontalAlignment=GridData.BEGINNING;
gridData30.verticalAlignment=GridData.CENTER;
GridData gridData29=new GridData();
gridData29.grabExcessHorizontalSpace=true;
GridData gridData28=new GridData();
gridData28.grabExcessHorizontalSpace=true;
gridData28.grabExcessVerticalSpace=false;
GridData gridData27=new GridData();
gridData27.grabExcessHorizontalSpace=false;
gridData27.grabExcessVerticalSpace=true;
GridData gridData26=new GridData();
gridData26.grabExcessHorizontalSpace=false;
gridData26.grabExcessVerticalSpace=true;
GridLayout gridLayout3=new GridLayout();
gridLayout3.numColumns=4;
gridLayout3.horizontalSpacing=2;
gridLayout3.marginWidth=2;
gridLayout3.marginHeight=2;
gridLayout3.verticalSpacing=2;
GridData gridData7=new GridData();
gridData7.horizontalAlignment=GridData.FILL;
gridData7.grabExcessHorizontalSpace=true;
gridData7.grabExcessVerticalSpace=true;
gridData7.verticalAlignment=GridData.FILL;
groupNetworkParameters=new Group(groupConnectionParameters, SWT.NONE);
groupNetworkParameters.setLayoutData(gridData7);
groupNetworkParameters.setLayout(gridLayout3);
groupNetworkParameters.setText(Messages.getString("CompositeConnectionSettings.28"));
Label filler935=new Label(groupNetworkParameters, SWT.NONE);
filler3=new Label(groupNetworkParameters, SWT.NONE);
filler3.setText("");
filler3.setLayoutData(gridData26);
Label filler511=new Label(groupNetworkParameters, SWT.NONE);
Label filler936=new Label(groupNetworkParameters, SWT.NONE);
filler7=new Label(groupNetworkParameters, SWT.NONE);
filler7.setText("");
filler7.setLayoutData(gridData29);
labelNPIPAddress=new Label(groupNetworkParameters, SWT.NONE);
labelNPIPAddress.setText(Messages.getString("CompositeConnectionSettings.29"));
labelNPIPAddress.setLayoutData(gridData30);
textNPIPAddress=new Text(groupNetworkParameters, SWT.BORDER);
textNPIPAddress.setText("XXX.XXX.XXX.XXX");
textNPIPAddress.setLayoutData(gridData38);
filler6=new Label(groupNetworkParameters, SWT.NONE);
filler6.setText("");
filler6.setLayoutData(gridData28);
Label filler734=new Label(groupNetworkParameters, SWT.NONE);
labelNPNetmask=new Label(groupNetworkParameters, SWT.NONE);
labelNPNetmask.setText(Messages.getString("CompositeConnectionSettings.30"));
labelNPNetmask.setLayoutData(gridData31);
textNPNetmask=new Text(groupNetworkParameters, SWT.BORDER);
textNPNetmask.setText("XXX.XXX.XXX.XXX");
textNPNetmask.setLayoutData(gridData47);
Label filler625=new Label(groupNetworkParameters, SWT.NONE);
Label filler733=new Label(groupNetworkParameters, SWT.NONE);
labelNPGateway=new Label(groupNetworkParameters, SWT.NONE);
labelNPGateway.setText(Messages.getString("CompositeConnectionSettings.31"));
labelNPGateway.setLayoutData(gridData32);
textNPGateway=new Text(groupNetworkParameters, SWT.BORDER);
textNPGateway.setText("XXX.XXX.XXX.XXX");
textNPGateway.setLayoutData(gridData48);
Label filler972=new Label(groupNetworkParameters, SWT.NONE);
Label filler973=new Label(groupNetworkParameters, SWT.NONE);
filler9=new Label(groupNetworkParameters, SWT.NONE);
filler9.setText("");
Label filler10=new Label(groupNetworkParameters, SWT.NONE);
Label filler11=new Label(groupNetworkParameters, SWT.NONE);
Label filler732=new Label(groupNetworkParameters, SWT.NONE);
checkBoxNPDHCP=new Button(groupNetworkParameters, SWT.CHECK);
checkBoxNPDHCP.setText("DHCP");
checkBoxNPDHCP.setLayoutData(gridData35);
labelNoteEthernetSettings=new Label(groupNetworkParameters, SWT.NONE);
labelNoteEthernetSettings.setText(Messages.getString("CompositeConnectionSettings.40"));
labelNoteEthernetSettings.setLayoutData(gridData57);
Label filler8=new Label(groupNetworkParameters, SWT.NONE);
Label filler731=new Label(groupNetworkParameters, SWT.NONE);
checkBoxNPLPD=new Button(groupNetworkParameters, SWT.CHECK);
checkBoxNPLPD.setText("LPD");
checkBoxNPLPD.setLayoutData(gridData36);
Label filler4=new Label(groupNetworkParameters, SWT.NONE);
Label filler730=new Label(groupNetworkParameters, SWT.NONE);
checkBoxNPRTEL=new Button(groupNetworkParameters, SWT.CHECK);
checkBoxNPRTEL.setText("RTEL");
checkBoxNPRTEL.setLayoutData(gridData37);
Label filler1=new Label(groupNetworkParameters, SWT.NONE);
Label filler19=new Label(groupNetworkParameters, SWT.NONE);
filler22=new Label(groupNetworkParameters, SWT.NONE);
filler22.setText("");
Label filler53=new Label(groupNetworkParameters, SWT.NONE);
Label filler54=new Label(groupNetworkParameters, SWT.NONE);
Label filler729=new Label(groupNetworkParameters, SWT.NONE);
labelNPRTELPort=new Label(groupNetworkParameters, SWT.NONE);
labelNPRTELPort.setText(Messages.getString("CompositeConnectionSettings.32"));
labelNPRTELPort.setLayoutData(gridData33);
textNPRTELPort=new Text(groupNetworkParameters, SWT.BORDER);
textNPRTELPort.setText("9100");
textNPRTELPort.setLayoutData(gridData49);
Label filler620=new Label(groupNetworkParameters, SWT.NONE);
Label filler728=new Label(groupNetworkParameters, SWT.NONE);
labelNPBufSize=new Label(groupNetworkParameters, SWT.NONE);
labelNPBufSize.setText(Messages.getString("CompositeConnectionSettings.33"));
labelNPBufSize.setLayoutData(gridData34);
textNPBufSize=new Text(groupNetworkParameters, SWT.BORDER);
textNPBufSize.setText("32768");
textNPBufSize.setLayoutData(gridData50);
Label filler619=new Label(groupNetworkParameters, SWT.NONE);
Label filler727=new Label(groupNetworkParameters, SWT.NONE);
filler5=new Label(groupNetworkParameters, SWT.NONE);
filler5.setText("");
filler5.setLayoutData(gridData27);
Label filler20=new Label(groupNetworkParameters, SWT.NONE);
Label filler44=new Label(groupNetworkParameters, SWT.NONE);
Label filler45=new Label(groupNetworkParameters, SWT.NONE);
buttonNPApply=new Button(groupNetworkParameters, SWT.NONE);
buttonNPApply.setText(Messages.getString("General.0"));
buttonNPApply.setLayoutData(gridData21);
buttonNPApply
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
setGroupNetworkParameters();
}
});
buttonNPRefresh=new Button(groupNetworkParameters, SWT.NONE);
buttonNPRefresh.setText(Messages.getString("General.1"));
buttonNPRefresh.setLayoutData(gridData22);
buttonNPRefresh
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
refreshGroupNetworkParameters();
}
});
Label filler60=new Label(groupNetworkParameters, SWT.NONE);
Label filler61=new Label(groupNetworkParameters, SWT.NONE);
Label filler62=new Label(groupNetworkParameters, SWT.NONE);
filler63=new Label(groupNetworkParameters, SWT.NONE);
filler63.setText("");
filler63.setLayoutData(gridData43);
}
private void createComboBaudRate()
{
GUIHelper.logger.trace("");
GridData gridData11=new GridData();
gridData11.horizontalAlignment=GridData.BEGINNING;
gridData11.grabExcessHorizontalSpace=false;
gridData11.verticalAlignment=GridData.CENTER;
comboBaudRate=new Combo(groupConnectionType, SWT.READ_ONLY);
comboBaudRate.setLayoutData(gridData11);
}
private void createComboPrinterName()
{
GUIHelper.logger.trace("");
GridData gridData12=new GridData();
gridData12.horizontalAlignment=GridData.BEGINNING;
gridData12.grabExcessHorizontalSpace=false;
gridData12.horizontalSpan=2;
gridData12.widthHint=100;
gridData12.verticalAlignment=GridData.CENTER;
}
private void createComboComPort()
{
GUIHelper.logger.trace("");
GridData gridData10=new GridData();
gridData10.horizontalAlignment=GridData.BEGINNING;
gridData10.grabExcessHorizontalSpace=false;
gridData10.verticalAlignment=GridData.CENTER;
comboComPort=new Combo(groupConnectionType, SWT.DROP_DOWN|SWT.READ_ONLY);
comboComPort.setLayoutData(gridData10);
}
private void createComboSCBaudRate()
{
GUIHelper.logger.trace("");
GridData gridData44=new GridData();
gridData44.horizontalAlignment=GridData.FILL;
gridData44.verticalAlignment=GridData.CENTER;
comboSCBaudRate=new Combo(groupSerialParameters, SWT.READ_ONLY);
comboSCBaudRate.setLayoutData(gridData44);
}
private void showDumbTerminalCheckBox()
{
checkBoxDumbTerminal.setEnabled(false);
checkBoxDumbTerminal.setVisible(GUIHelper.prefs.getBoolean("SHOW_DUMB_TERMINAL_CHECKBOX", false));
checkBoxDumbTerminal.setSelection(GUIHelper.prefs.getBoolean("DUMB_TERMINAL", false));
}
private void setDefaultConnectionParameters()
{
GUIHelper.logger.trace("");
connectionType=ConnectionType.valueOf(GUIHelper.prefs.get("DEFAULT_CONNECTION_TYPE", "UNKNOWN"));
GUIHelper.logger.debug("Default Connection Type: "+connectionType);
refreshComPortList();
String defaultPort=GUIHelper.prefs.get("COM_PORT", "COM1");
int index=comboComPort.indexOf(defaultPort);
comboComPort.select(index);
populateBaudRateCombos();
String defaultBaudRate=GUIHelper.prefs.get("BAUD_RATE", "115200");
index=comboBaudRate.indexOf(defaultBaudRate);
comboBaudRate.select(index);
populateStopBitsCombos();
String defaultStopBits=GUIHelper.prefs.get("STOP_BITS", "1");
index=comboStopBits.indexOf(defaultStopBits);
comboStopBits.select(index);
textIPAddress.setText(GUIHelper.prefs.get("IP_ADDRESS", ""));
textIPPort.setText(GUIHelper.prefs.get("IP_PORT", "9100"));
refreshParallelPortList();
String defaultLPTPort=GUIHelper.prefs.get("LPT_PORT", "LPT1");
int indexLPT=comboParallelPort.indexOf(defaultLPTPort);
comboParallelPort.select(indexLPT);
}
public ConnectionType getConnectionType()
{
return connectionType;
}
private void createComboParallelPort()
{
GUIHelper.logger.trace("");
comboParallelPort=new Combo(groupConnectionType, SWT.DROP_DOWN|SWT.READ_ONLY);
}
private void createComboStopBits()
{
comboStopBits=new Combo(groupConnectionType, SWT.READ_ONLY);
comboStopBits.setVisibleItemCount(2);
}
private void createComboSCStopBits()
{
comboSCStopBits=new Combo(groupSerialParameters, SWT.READ_ONLY);
comboSCStopBits.setVisibleItemCount(2);
}
}
