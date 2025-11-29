public class CompositeFirmwareCPR extends Composite
{
private boolean connected=false;
private boolean firstPaintSinceConnected=true;
private boolean dumbTerminal=false;
private String pathFirmware=null;
private String currentFirmware=null;
private String fileName=null;
private Group groupFirmware=null;
private Group groupCPR=null;
private Label labelPartNumber=null;
private Label labelVersionNumber=null;
private Label labelReleaseDate=null;
private Label labelPartNumberActual=null;
private Label labelVersionNumberActual=null;
private Label labelReleaseDateActual=null;
private Label separator=null;
private Label labelNewImage=null;
private Button buttonSurfFirmware=null;
private Text textNewImage=null;
private Button buttonUpdateFirmware=null;
private Label labelUpdateProgress=null;
private ProgressBar progressBarFirmwareUpdate=null;
private Button buttonSelectFirmwareFile=null;
private Label filler=null;
private Label filler2=null;
private Label filler8=null;
private Label filler12=null;
private Button buttonFirmwareRefresh=null;
private Label labelFirmwareCurrent=null;
private Label labelFirmwareUpgrade=null;
private Label labelState=null;
static Label labelNote=null;
public CompositeFirmwareCPR(Composite parent, int style)
{
super(parent, style);
initialize();
}
private void initialize()
{
GridData gridData17=new GridData();
gridData17.grabExcessHorizontalSpace=true;
Label filler14=new Label(this, SWT.NONE);
GridData gridData16=new GridData();
gridData16.grabExcessHorizontalSpace=true;
GridData gridData15=new GridData();
gridData15.grabExcessVerticalSpace=true;
GridData gridData14=new GridData();
gridData14.grabExcessVerticalSpace=true;
filler=new Label(this, SWT.NONE);
filler.setText("");
filler.setLayoutData(gridData14);
GridLayout gridLayout1=new GridLayout();
gridLayout1.numColumns=3;
Label filler9=new Label(this, SWT.NONE);
Label filler13=new Label(this, SWT.NONE);
createGroupFirmware();
this.setLayout(gridLayout1);
Label filler7=new Label(this, SWT.NONE);
filler12=new Label(this, SWT.NONE);
filler12.setText("");
filler12.setLayoutData(gridData17);
filler8=new Label(this, SWT.NONE);
filler8.setText("");
filler8.setLayoutData(gridData16);
Label filler11=new Label(this, SWT.NONE);
createGroupCPR();
setSize(new Point(601, 297));
Label filler3=new Label(this, SWT.NONE);
Label filler10=new Label(this, SWT.NONE);
filler2=new Label(this, SWT.NONE);
filler2.setText("");
filler2.setLayoutData(gridData15);
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
private void createGroupFirmware()
{
GridData gridData20=new GridData();
gridData20.horizontalAlignment=GridData.FILL;
gridData20.horizontalIndent=0;
gridData20.grabExcessHorizontalSpace=false;
gridData20.horizontalSpan=4;
gridData20.verticalSpan=3;
gridData20.grabExcessVerticalSpace=false;
gridData20.verticalAlignment=GridData.FILL;
GridData gridData19=new GridData();
gridData19.horizontalAlignment=GridData.FILL;
gridData19.verticalAlignment=GridData.CENTER;
GridData gridData18=new GridData();
gridData18.horizontalAlignment=GridData.CENTER;
gridData18.verticalAlignment=GridData.CENTER;
GridData gridData13=new GridData();
gridData13.horizontalAlignment=GridData.CENTER;
gridData13.verticalAlignment=GridData.CENTER;
GridData gridData12=new GridData();
gridData12.verticalAlignment=GridData.CENTER;
gridData12.horizontalSpan=2;
gridData12.horizontalAlignment=GridData.FILL;
GridData gridData11=new GridData();
gridData11.horizontalAlignment=GridData.END;
gridData11.verticalAlignment=GridData.CENTER;
GridData gridData10=new GridData();
gridData10.horizontalAlignment=GridData.FILL;
gridData10.horizontalSpan=3;
gridData10.verticalAlignment=GridData.CENTER;
GridData gridData9=new GridData();
gridData9.horizontalAlignment=GridData.END;
gridData9.verticalAlignment=GridData.CENTER;
GridData gridData8=new GridData();
gridData8.horizontalAlignment=GridData.FILL;
gridData8.grabExcessHorizontalSpace=true;
gridData8.horizontalSpan=4;
gridData8.verticalAlignment=GridData.CENTER;
GridData gridData7=new GridData();
gridData7.horizontalAlignment=GridData.FILL;
gridData7.grabExcessHorizontalSpace=true;
gridData7.horizontalSpan=2;
gridData7.verticalAlignment=GridData.CENTER;
GridData gridData6=new GridData();
gridData6.horizontalAlignment=GridData.FILL;
gridData6.grabExcessHorizontalSpace=true;
gridData6.horizontalSpan=2;
gridData6.verticalAlignment=GridData.CENTER;
GridData gridData5=new GridData();
gridData5.horizontalAlignment=GridData.FILL;
gridData5.grabExcessHorizontalSpace=true;
gridData5.horizontalSpan=2;
gridData5.verticalAlignment=GridData.CENTER;
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=4;
gridLayout.marginWidth=10;
gridLayout.marginHeight=15;
GridData gridData4=new GridData();
gridData4.horizontalAlignment=GridData.END;
gridData4.verticalAlignment=GridData.CENTER;
GridData gridData3=new GridData();
gridData3.horizontalAlignment=GridData.END;
gridData3.verticalAlignment=GridData.CENTER;
GridData gridData2=new GridData();
gridData2.horizontalAlignment=GridData.END;
gridData2.verticalAlignment=GridData.CENTER;
GridData gridData1=new GridData();
gridData1.horizontalAlignment=GridData.FILL;
gridData1.grabExcessHorizontalSpace=true;
gridData1.grabExcessVerticalSpace=true;
gridData1.verticalSpan=2;
gridData1.verticalAlignment=GridData.FILL;
groupFirmware=new Group(this, SWT.NONE);
groupFirmware.setLayoutData(gridData1);
groupFirmware.setLayout(gridLayout);
groupFirmware.setText("Firmware");
labelFirmwareCurrent=new Label(groupFirmware, SWT.NONE);
labelFirmwareCurrent.setText(Messages.getString("CompositeFirmwareCPR.2"));
labelFirmwareCurrent.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.BOLD));
Label filler57=new Label(groupFirmware, SWT.NONE);
Label filler58=new Label(groupFirmware, SWT.NONE);
Label filler59=new Label(groupFirmware, SWT.NONE);
labelPartNumber=new Label(groupFirmware, SWT.NONE);
labelPartNumber.setText(Messages.getString("CompositeFirmwareCPR.3"));
labelPartNumber.setLayoutData(gridData2);
labelPartNumberActual=new Label(groupFirmware, SWT.NONE);
labelPartNumberActual.setText("Part Number");
labelPartNumberActual.setLayoutData(gridData5);
Label filler6=new Label(groupFirmware, SWT.NONE);
labelVersionNumber=new Label(groupFirmware, SWT.NONE);
labelVersionNumber.setText(Messages.getString("CompositeFirmwareCPR.4"));
labelVersionNumber.setLayoutData(gridData3);
labelVersionNumberActual=new Label(groupFirmware, SWT.NONE);
labelVersionNumberActual.setText("Version Number");
labelVersionNumberActual.setLayoutData(gridData6);
buttonFirmwareRefresh=new Button(groupFirmware, SWT.NONE);
buttonFirmwareRefresh.setText("Refresh");
buttonFirmwareRefresh.setLayoutData(gridData18);
buttonFirmwareRefresh
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
refreshFirmware();
}
});
labelReleaseDate=new Label(groupFirmware, SWT.NONE);
labelReleaseDate.setText(Messages.getString("CompositeFirmwareCPR.5"));
labelReleaseDate.setLayoutData(gridData4);
labelReleaseDateActual=new Label(groupFirmware, SWT.NONE);
labelReleaseDateActual.setText("Release Date");
labelReleaseDateActual.setLayoutData(gridData7);
Label filler4=new Label(groupFirmware, SWT.NONE);
separator=new Label(groupFirmware, SWT.SEPARATOR|SWT.HORIZONTAL);
separator.setText("Label");
separator.setLayoutData(gridData8);
labelFirmwareUpgrade=new Label(groupFirmware, SWT.NONE);
labelFirmwareUpgrade.setText(Messages.getString("CompositeFirmwareCPR.6"));
labelFirmwareUpgrade.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.BOLD));
Label filler510=new Label(groupFirmware, SWT.NONE);
Label filler511=new Label(groupFirmware, SWT.NONE);
Label filler512=new Label(groupFirmware, SWT.NONE);
Label filler1=new Label(groupFirmware, SWT.NONE);
buttonSelectFirmwareFile=new Button(groupFirmware, SWT.NONE);
buttonSelectFirmwareFile.setText(Messages.getString("CompositeFirmwareCPR.7"));
buttonSelectFirmwareFile
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
selectFirmwareFile();
}
});
buttonSurfFirmware=new Button(groupFirmware, SWT.NONE);
buttonSurfFirmware.setText(Messages.getString("CompositeFirmwareCPR.8").replace("Cognitive", GUIHelper.brand.getProperty("company.name", "CognitiveTPG")));
buttonSurfFirmware.setLayoutData(gridData13);
buttonSurfFirmware
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
getLatestFirmware();
}
});
buttonUpdateFirmware=new Button(groupFirmware, SWT.NONE);
buttonUpdateFirmware.setText(Messages.getString("CompositeFirmwareCPR.9"));
buttonUpdateFirmware
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
updateFirmware();
}
});
labelNewImage=new Label(groupFirmware, SWT.NONE);
labelNewImage.setText(Messages.getString("CompositeFirmwareCPR.10"));
labelNewImage.setLayoutData(gridData9);
textNewImage=new Text(groupFirmware, SWT.BORDER);
textNewImage.setLayoutData(gridData10);
labelUpdateProgress=new Label(groupFirmware, SWT.NONE);
labelUpdateProgress.setText(Messages.getString("CompositeFirmwareCPR.11"));
labelUpdateProgress.setLayoutData(gridData11);
labelState=new Label(groupFirmware, SWT.CENTER);
labelState.setText(Messages.getString("CompositeFirmwareCPR.12"));
labelState.setLayoutData(gridData19);
labelState.setVisible(false);
labelState.setAlignment(SWT.CENTER);
progressBarFirmwareUpdate=new ProgressBar(groupFirmware, SWT.INDETERMINATE);
progressBarFirmwareUpdate.setVisible(false);
progressBarFirmwareUpdate.setEnabled(false);
progressBarFirmwareUpdate.setLayoutData(gridData12);
labelNote=new Label(groupFirmware, SWT.NONE);
labelNote.setText(Messages.getString("CompositeFirmwareCPR.22"));
labelNote.setLayoutData(gridData20);
}
private void createGroupCPR()
{
GridData gridData=new GridData();
gridData.horizontalAlignment=GridData.FILL;
gridData.grabExcessHorizontalSpace=true;
gridData.grabExcessVerticalSpace=true;
gridData.verticalAlignment=GridData.FILL;
groupCPR=new Group(this, SWT.NONE);
groupCPR.setLayout(new GridLayout());
groupCPR.setLayoutData(gridData);
groupCPR.setText("CPR");
groupCPR.setVisible(false);
}
public void setConnectionState(boolean connected, boolean dumbTerminal)
{
GUIHelper.logger.trace("");
this.connected=connected;
this.dumbTerminal=dumbTerminal;
firstPaintSinceConnected=true;
GUIHelper.setEnabled(this, connected&&!dumbTerminal);
}
private void refresh()
{
GUIHelper.blankify(this);
refreshFirmware();
layout(true, true);
}
private void refreshFirmware()
{
GUIHelper.logger.trace("");
String firmware=GUIHelper.clean((PrinterHelper.commandWaitResponse("!QR")));
String part=null;
String regex=null;
if(firmware==null)
{
return;
}
regex="\\d{3}-\\d{3}-\\d{3}";
currentFirmware=GUIHelper.getMatch(firmware, regex);
labelPartNumberActual.setText(currentFirmware);
regex="[PV]\\d{1,2}\\.\\d{1,2}\\s*[a-zA-Z]{0,1}\\$\\d{0,2}\\s*(RC\\d{1,2}){0,1}";
labelVersionNumberActual.setText(GUIHelper.getMatch(firmware, regex));
regex="(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec).*";
labelReleaseDateActual.setText(GUIHelper.getMatch(firmware, regex));
}
private void selectFirmwareFile()
{
GUIHelper.logger.trace("");
if(pathFirmware==null)
{
pathFirmware=GUIHelper.prefs.get("PATH_FIRMWARE", ".");
}
FileDialog fd=null;
try
{
fd=new FileDialog(Display.getCurrent().getShells()[0], SWT.OPEN);
fd.setFilterPath(pathFirmware);
if(fd.open()==null)
{
return;
}
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("CompositeFirmwareCPR.0"));
return;
}
pathFirmware=fd.getFilterPath();
GUIHelper.prefs.put("PATH_FIRMWARE", pathFirmware);
fileName=fd.getFileName();
textNewImage.setText(pathFirmware+"/"+fileName);
}
private void updateFirmware()
{
GUIHelper.logger.trace("");
boolean reEnableHeartbeats=false;
if(GUIHelper.mainShell.heartbeatsEnabled())
{
reEnableHeartbeats=true;
GUIHelper.mainShell.stopHeartbeats();
}
else
{
reEnableHeartbeats=false;
}
progressBarFirmwareUpdate.setVisible(true);
progressBarFirmwareUpdate.setEnabled(true);
byte[] buff=null;
try
{
buff=GUIHelper.readBinaryFile(textNewImage.getText());
}
catch(Exception e)
{
if(textNewImage.getText().trim().length()>0)
{
GUIHelper.error(Messages.getString("CompositeFirmwareCPR.1")+textNewImage.getText());
}
else
{
GUIHelper.error(Messages.getString("CompositeFirmwareCPR.21"));
}
GUIHelper.logger.error(e, e);
progressBarFirmwareUpdate.setVisible(false);
progressBarFirmwareUpdate.setEnabled(false);
return;
}
if(currentFirmware!=null)
{
String currentFirmwareType=currentFirmware.split("-")[1];
int currentFirmwareVersion=Integer.parseInt(currentFirmware.split("-")[2]);
String toBeDownloadedFirmwareType=fileName.substring(3, 6);
GUIHelper.logger.info("To be downloaded firmware: "+fileName);
int toBeDownloadedFirmwareVersion=Integer.parseInt(fileName.substring(fileName.length()-3, fileName.length()));
if(currentFirmwareType.equals(toBeDownloadedFirmwareType)&&(currentFirmwareVersion>toBeDownloadedFirmwareVersion))
{
GUIHelper.message(Messages.getString("CompositeFirmwareCPR.23"), SWT.OK|SWT.ICON_WARNING);
progressBarFirmwareUpdate.setVisible(false);
progressBarFirmwareUpdate.setEnabled(false);
return;
}
}
FirmwareUpdater firmwareUpdater=new FirmwareUpdater(buff, progressBarFirmwareUpdate);
firmwareUpdater.updateFirmware(false);
progressBarFirmwareUpdate.setVisible(false);
progressBarFirmwareUpdate.setEnabled(false);
refresh();
if(reEnableHeartbeats)
{
GUIHelper.mainShell.startHeartbeats();
}
String currentFirmwarePartNumber=TestEngine.getCurrentFirmwarePartNumber();
boolean hasStandardFirmware=false;
if(currentFirmwarePartNumber!=null)
{
hasStandardFirmware=currentFirmwarePartNumber.split("-")[1].equals("170")||currentFirmwarePartNumber.split("-")[1].equals("184");
}
if(hasStandardFirmware)
{
String language=PrinterHelper.getVariable("LANGUAGE");
if(language.equals("PCL"))
{
PrinterHelper.setVariable("LANGUAGE", "NONE");
}
}
}
private void getLatestFirmware()
{
GUIHelper.logger.trace("");
boolean reEnableHeartbeats=false;
if(GUIHelper.mainShell.heartbeatsEnabled())
{
reEnableHeartbeats=true;
GUIHelper.mainShell.stopHeartbeats();
}
else
{
reEnableHeartbeats=false;
}
boolean hasStandardFirmware=false;
if(currentFirmware!=null)
{
hasStandardFirmware=currentFirmware.split("-")[1].equals("170")||currentFirmware.split("-")[1].equals("184");
}
if(hasStandardFirmware)
{
TestStatus testStatus;
String modelNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MODELNUMBER"));
String language=PrinterHelper.getVariable("LANGUAGE");
String feedback=PrinterHelper.getVariable("USER_FEEDBACK");
if(modelNumber.startsWith("700"))
{
if(Integer.parseInt(currentFirmware.split("-")[2])>150)
{
modelNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW OEMMODELID"));
}
else
{
TGModelNumbers tgModelNumbers=new TGModelNumbers(false);
modelNumber=tgModelNumbers.getModelID(modelNumber);
}
}
boolean success=TestEngine.parseModelNumber(modelNumber);
if(!success)
{
String msg=Messages.getString("CompositeManufacturing.39");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
return;
}
testStatus=TestEngine.doFirmwareDownload(true, progressBarFirmwareUpdate, false);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
String msg=null;
switch(testStatus)
{
case FAILED:
msg=Messages.getString("CompositeFirmwareCPR.13");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
break;
case CANCELLED:
msg=Messages.getString("CompositeFirmwareCPR.14");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
break;
}
}
refresh();
int button=SWT.NO;
if(testStatus==TestStatus.PASSED)
{
button=GUIHelper.message(Messages.getString("CompositeFirmwareCPR.17"), SWT.OK|SWT.ICON_INFORMATION);
}
else if(testStatus==TestStatus.SKIPPED)
{
button=GUIHelper.message(Messages.getString("CompositeFirmwareCPR.18"), SWT.ICON_QUESTION|SWT.YES|SWT.NO);
}
if(button==SWT.YES||button==SWT.OK)
{
progressBarFirmwareUpdate.setVisible(true);
progressBarFirmwareUpdate.setEnabled(true);
progressBarFirmwareUpdate.setMinimum(0);
progressBarFirmwareUpdate.setMaximum(100);
progressBarFirmwareUpdate.setSelection(10);
progressBarFirmwareUpdate.update();
progressBarFirmwareUpdate.setSelection(40);
progressBarFirmwareUpdate.update();
PrinterHelper.setVariable("LANGUAGE", "NONE");
testStatus=TestEngine.doLoadFonts(TestEngine.printDensity);
progressBarFirmwareUpdate.setSelection(95);
progressBarFirmwareUpdate.update();
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
String msg=null;
switch(testStatus)
{
case FAILED:
msg=Messages.getString("CompositeFirmwareCPR.15");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
break;
case CANCELLED:
msg=Messages.getString("CompositeFirmwareCPR.16");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
break;
}
}
PrinterHelper.setVariable("USER_FEEDBACK", feedback);
if(!language.equals("PCL"))
{
PrinterHelper.setVariable("LANGUAGE", language);
}
progressBarFirmwareUpdate.setVisible(false);
progressBarFirmwareUpdate.setEnabled(false);
GUIHelper.message(Messages.getString("CompositeFirmwareCPR.19"), SWT.OK|SWT.ICON_INFORMATION);
}
}
else
{
GUIHelper.message(Messages.getString("CompositeFirmwareCPR.20"), SWT.OK|SWT.ICON_INFORMATION);
}
if(reEnableHeartbeats)
{
GUIHelper.mainShell.startHeartbeats();
}
}
}
