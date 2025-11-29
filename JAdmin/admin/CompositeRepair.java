public class CompositeRepair extends Composite
{
private boolean connected=false;
private boolean firstPaintSinceConnected=true;
private boolean dumbTerminal=true;
private Logger logger=null;
private Composite compositePrinterID=null;
static Label labelModelNumber=null;
private Label labelSerialNumber=null;
private Label labelMAC=null;
private Label labelModelNumberActual=null;
private Label labelSerialNumberActual=null;
private Label labelMACActual=null;
private Composite compositeButtons=null;
private Button buttonRenewSN_MAC=null;
private Button buttonKtoD=null;
private Button buttonRefresh=null;
public CompositeRepair(Composite parent, int style)
{
super(parent, style);
initialize();
}
private void initialize()
{
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=1;
gridLayout.verticalSpacing=15;
gridLayout.horizontalSpacing=15;
createCompositePrinterID();
this.setLayout(gridLayout);
createCompositeButtons();
setSize(new Point(606, 200));
this.addPaintListener(new org.eclipse.swt.events.PaintListener()
{
public void paintControl(org.eclipse.swt.events.PaintEvent e)
{
if(connected&&firstPaintSinceConnected&&!dumbTerminal)
{
firstPaintSinceConnected=false;
refresh();
}
}
});
}
public void setConnectionState(boolean connected, boolean dumbTerminal)
{
logger=GUIHelper.logger;
logger.trace("");
this.connected=connected;
this.dumbTerminal=dumbTerminal;
firstPaintSinceConnected=true;
GUIHelper.setEnabled(this, connected&&!dumbTerminal);
}
private void convertKtoD()
{
String serialNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!show serialnumber"));
JAdminDialogMessage dialog=new JAdminDialogMessage(Messages.getString("CompositeRepair.3"));
if(serialNumber.charAt(0)=='K')
{
StringBuffer newSerialNumber=new StringBuffer(serialNumber);
newSerialNumber.setCharAt(0, 'D');
PrinterHelper.send("!load serialnumber "+newSerialNumber.toString());
serialNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!show serialnumber"));
if(serialNumber.equals(newSerialNumber.toString()))
{
dialog.setMessage(Messages.getString("CompositeRepair.2"));
}
else
{
dialog.setMessage(Messages.getString("CompositeRepair.1"));
}
}
else
{
dialog.setMessage(Messages.getString("CompositeRepair.0"));
}
dialog.closeAfter(2000);
}
private void renewSN_MAC()
{
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
StringBuffer newSerialNumber=new StringBuffer();
StringBuffer newMAC=new StringBuffer();
char locationCode;
if(labelModelNumberActual.getText().equalsIgnoreCase("z060800000"))
{
locationCode=(GUIHelper.prefs.get("MAN_LOCATION_CODE", "-")).charAt(0);
if(locationCode=='-')
{
String msg=Messages.getString("CompositeRepair.4");
GUIHelper.logger.error(msg);
GUIHelper.error(msg);
return;
}
}
else
{
locationCode=labelModelNumberActual.getText().charAt(0);
}
PrinterId printerId=TestEngine.dbCreateNewPrinter(locationCode, labelModelNumberActual.getText(), true);
if(printerId==null)
{
GUIHelper.error(Messages.getString("CompositeRepair.5"));
return;
}
else
{
if(!TestEngine.setSN_MAC(printerId))
{
GUIHelper.error(Messages.getString("CompositeRepair.6"));
return;
}
}
refresh();
}
catch(Exception e)
{
return;
}
finally
{
GUIHelper.setCursor(0);
}
}
private void createCompositePrinterID()
{
GridData gridData3=new GridData();
gridData3.horizontalAlignment=GridData.FILL;
gridData3.grabExcessHorizontalSpace=true;
gridData3.verticalAlignment=GridData.CENTER;
GridData gridData2=new GridData();
gridData2.horizontalAlignment=GridData.FILL;
gridData2.grabExcessHorizontalSpace=true;
gridData2.verticalAlignment=GridData.CENTER;
GridData gridData1=new GridData();
gridData1.horizontalAlignment=GridData.FILL;
gridData1.grabExcessHorizontalSpace=true;
gridData1.verticalAlignment=GridData.CENTER;
GridLayout gridLayout1=new GridLayout();
gridLayout1.numColumns=2;
gridLayout1.verticalSpacing=15;
gridLayout1.horizontalSpacing=15;
GridData gridData=new GridData();
gridData.horizontalAlignment=GridData.FILL;
gridData.grabExcessHorizontalSpace=true;
gridData.grabExcessVerticalSpace=false;
gridData.verticalAlignment=GridData.FILL;
compositePrinterID=new Composite(this, SWT.NONE);
compositePrinterID.setLayoutData(gridData);
compositePrinterID.setLayout(gridLayout1);
labelModelNumber=new Label(compositePrinterID, SWT.NONE);
labelModelNumber.setText(Messages.getString("CompositeRepair.7"));
labelModelNumberActual=new Label(compositePrinterID, SWT.NONE);
labelModelNumberActual.setText("");
labelModelNumberActual.setLayoutData(gridData1);
labelSerialNumber=new Label(compositePrinterID, SWT.NONE);
labelSerialNumber.setText(Messages.getString("CompositeRepair.8"));
labelSerialNumberActual=new Label(compositePrinterID, SWT.NONE);
labelSerialNumberActual.setText("");
labelSerialNumberActual.setLayoutData(gridData2);
labelMAC=new Label(compositePrinterID, SWT.NONE);
labelMAC.setText(Messages.getString("CompositeRepair.9"));
labelMACActual=new Label(compositePrinterID, SWT.NONE);
labelMACActual.setText("");
labelMACActual.setLayoutData(gridData3);
buttonRefresh=new Button(compositePrinterID, SWT.NONE);
buttonRefresh.setText(Messages.getString("General.1"));
buttonRefresh
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
refresh();
}
});
}
private void refresh()
{
String serialNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW SERIALNUMBER"));
labelSerialNumberActual.setText(serialNumber);
String mac=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MAC"));
labelMACActual.setText(mac);
String modelNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MODELNUMBER"));
labelModelNumberActual.setText(modelNumber);
boolean printerExistsInDb=false;
try
{
printerExistsInDb=TestEngine.checkDB(serialNumber, mac, modelNumber);
}
catch(DBException e)
{
return;
}
if(!printerExistsInDb)
{
GUIHelper.error(Messages.getString("CompositeRepair.11"));
}
}
private void createCompositeButtons()
{
GridLayout gridLayout2=new GridLayout();
gridLayout2.numColumns=2;
GridData gridData4=new GridData();
gridData4.horizontalAlignment=GridData.FILL;
gridData4.grabExcessHorizontalSpace=true;
gridData4.grabExcessVerticalSpace=true;
gridData4.verticalAlignment=GridData.FILL;
compositeButtons=new Composite(this, SWT.NONE);
compositeButtons.setLayoutData(gridData4);
compositeButtons.setLayout(gridLayout2);
buttonRenewSN_MAC=new Button(compositeButtons, SWT.NONE);
buttonRenewSN_MAC.setText(Messages.getString("CompositeRepair.12"));
buttonRenewSN_MAC
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
renewSN_MAC();
}
});
buttonKtoD=new Button(compositeButtons, SWT.NONE);
buttonKtoD.setText(Messages.getString("CompositeRepair.13"));
buttonKtoD.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
convertKtoD();
}
});
}
}
