public class CompositeCPLEditor extends Composite implements Observer, HasCallback
{
private static boolean hasPCLFirmware=false;
private boolean dumbTerminal=true;
private String pathCPLInput=null;
private String pathCPLOutput=null;
private TimerTaskCPL timerTaskCPL=null;
private Text textAreaInput=null;
private Text textAreaOutput=null;
private Group groupInputButtons=null;
private Group groupOutputButtons=null;
private Group groupTimerButtons=null;
private Button buttonOpenFile=null;
private Button buttonSaveFile=null;
private Button buttonSendToPrinter=null;
private Button buttonViewVariables=null;
private Button buttonViewTime=null;
private Button buttonSaveOutput=null;
private Button buttonClearOutput=null;
private Label labelPrinterInput=null;
private Label labelPrinterOutput=null;
private Label labelInterval=null;
private Text textInterval=null;
private Label labelRepeat=null;
private Text textRepeat=null;
private Label labelCurrent=null;
private Label labelCounter=null;
private Button buttonTimerStart=null;
private Button buttonTimerPause=null;
private Button buttonTimerResume=null;
private Button buttonTimerStop=null;
private Label filler11=null;
private Label filler3=null;
private Label filler9=null;
private Label filler12=null;
private Label filler13=null;
private Label filler17=null;
private Label filler24=null;
private Label filler32=null;
private Button buttonClearInput=null;
private Button buttonSendFileToPrinter=null;
public void setConnectionState(boolean connected, boolean dumbTerminal)
{
GUIHelper.logger.trace("");
this.dumbTerminal=dumbTerminal;
GUIHelper.setEnabled(this, connected);
GUIHelper.setEnabled(groupOutputButtons, connected);
textAreaOutput.setEnabled(connected);
if(connected&&!dumbTerminal)
{
PrinterHelper.printer.addObserver(this);
timerStop();
}
}
public CompositeCPLEditor(Composite parent, int style)
{
super(parent, style);
initialize();
}
private void initialize()
{
GridData gridData21=new GridData();
gridData21.horizontalAlignment=GridData.FILL;
gridData21.grabExcessHorizontalSpace=true;
gridData21.grabExcessVerticalSpace=true;
gridData21.verticalAlignment=GridData.FILL;
labelPrinterInput=new Label(this, SWT.NONE);
labelPrinterInput.setText(Messages.getString("CompositeCPLEditor.0"));
GridData gridData1=new GridData();
gridData1.grabExcessHorizontalSpace=true;
gridData1.horizontalAlignment=GridData.FILL;
gridData1.verticalAlignment=GridData.FILL;
gridData1.verticalSpan=2;
gridData1.grabExcessVerticalSpace=true;
labelPrinterOutput=new Label(this, SWT.NONE);
labelPrinterOutput.setText(Messages.getString("CompositeCPLEditor.1"));
textAreaInput=new Text(this, SWT.MULTI|SWT.WRAP|SWT.V_SCROLL|SWT.H_SCROLL);
textAreaInput.setText("");
textAreaInput.setFont(new Font(Display.getDefault(), "Courier New", 8, SWT.NORMAL));
textAreaInput.setLayoutData(gridData1);
textAreaOutput=new Text(this, SWT.MULTI|SWT.WRAP|SWT.V_SCROLL|SWT.H_SCROLL);
textAreaOutput.setText("");
textAreaOutput.setFont(new Font(Display.getDefault(), "Courier New", 8, SWT.NORMAL));
textAreaOutput.setLayoutData(gridData21);
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=2;
gridLayout.makeColumnsEqualWidth=true;
this.setLayout(gridLayout);
createGroupOutputButtons();
createGroupInputButtons();
createGroupTimerButtons();
this.setSize(new Point(646, 344));
}
private void createGroupInputButtons()
{
GridData gridData25=new GridData();
gridData25.horizontalSpan=4;
gridData25.verticalAlignment=GridData.CENTER;
gridData25.horizontalAlignment=GridData.CENTER;
GridData gridData17=new GridData();
gridData17.horizontalAlignment=GridData.CENTER;
gridData17.grabExcessHorizontalSpace=true;
gridData17.verticalAlignment=GridData.CENTER;
GridData gridData14=new GridData();
gridData14.horizontalAlignment=GridData.CENTER;
gridData14.grabExcessHorizontalSpace=true;
gridData14.verticalAlignment=GridData.CENTER;
GridData gridData13=new GridData();
gridData13.grabExcessVerticalSpace=true;
gridData13.verticalAlignment=GridData.CENTER;
gridData13.horizontalAlignment=GridData.CENTER;
GridLayout gridLayout1=new GridLayout();
gridLayout1.numColumns=6;
GridData gridData3=new GridData();
gridData3.horizontalAlignment=GridData.FILL;
gridData3.grabExcessHorizontalSpace=false;
gridData3.verticalAlignment=GridData.FILL;
groupInputButtons=new Group(this, SWT.NONE);
groupInputButtons.setLayoutData(gridData3);
groupInputButtons.setLayout(gridLayout1);
Label filler14=new Label(groupInputButtons, SWT.NONE);
Label filler16=new Label(groupInputButtons, SWT.NONE);
filler17=new Label(groupInputButtons, SWT.NONE);
filler17.setText("");
filler17.setLayoutData(gridData13);
Label filler1=new Label(groupInputButtons, SWT.NONE);
Label filler19=new Label(groupInputButtons, SWT.NONE);
Label filler20=new Label(groupInputButtons, SWT.NONE);
filler13=new Label(groupInputButtons, SWT.NONE);
filler13.setText("");
filler13.setLayoutData(gridData17);
buttonOpenFile=new Button(groupInputButtons, SWT.NONE);
buttonOpenFile.setText(Messages.getString("CompositeCPLEditor.2"));
buttonOpenFile
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
openCPLFile();
}
});
buttonSaveFile=new Button(groupInputButtons, SWT.NONE);
buttonSaveFile.setText(Messages.getString("CompositeCPLEditor.3"));
buttonClearInput=new Button(groupInputButtons, SWT.NONE);
buttonClearInput.setText(Messages.getString("CompositeCPLEditor.4"));
buttonClearInput
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
clearInput();
}
});
buttonSaveFile
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
if(pathCPLInput==null)
{
pathCPLInput=GUIHelper.prefs.get("PATH_CPL_INPUT", ".");
}
saveCPLFile();
}
});
buttonSendToPrinter=new Button(groupInputButtons, SWT.NONE);
buttonSendToPrinter.setText(Messages.getString("CompositeCPLEditor.5"));
buttonSendToPrinter
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
sendToPrinter();
}
});
filler12=new Label(groupInputButtons, SWT.NONE);
filler12.setText("");
filler12.setLayoutData(gridData14);
Label filler46=new Label(groupInputButtons, SWT.NONE);
buttonSendFileToPrinter=new Button(groupInputButtons, SWT.NONE);
buttonSendFileToPrinter.setText(Messages.getString("CompositeCPLEditor.20"));
buttonSendFileToPrinter.setLayoutData(gridData25);
buttonSendFileToPrinter
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
sendFile();
}
});
}
private void createGroupOutputButtons()
{
GridData gridData24=new GridData();
gridData24.horizontalAlignment=GridData.FILL;
gridData24.verticalAlignment=GridData.CENTER;
GridData gridData23=new GridData();
gridData23.horizontalAlignment=GridData.FILL;
gridData23.verticalAlignment=GridData.CENTER;
GridData gridData22=new GridData();
gridData22.horizontalAlignment=GridData.FILL;
gridData22.verticalAlignment=GridData.CENTER;
GridData gridData20=new GridData();
gridData20.horizontalAlignment=GridData.FILL;
gridData20.verticalAlignment=GridData.CENTER;
GridData gridData19=new GridData();
gridData19.horizontalAlignment=GridData.CENTER;
gridData19.grabExcessHorizontalSpace=true;
gridData19.verticalAlignment=GridData.CENTER;
GridData gridData18=new GridData();
gridData18.horizontalAlignment=GridData.CENTER;
gridData18.grabExcessHorizontalSpace=true;
gridData18.verticalAlignment=GridData.CENTER;
GridLayout gridLayout3=new GridLayout();
gridLayout3.numColumns=4;
GridData gridData2=new GridData();
gridData2.horizontalAlignment=GridData.FILL;
gridData2.verticalAlignment=GridData.FILL;
groupOutputButtons=new Group(this, SWT.NONE);
groupOutputButtons.setLayoutData(gridData2);
groupOutputButtons.setLayout(gridLayout3);
Label filler33=new Label(groupOutputButtons, SWT.NONE);
buttonViewVariables=new Button(groupOutputButtons, SWT.NONE);
buttonViewVariables.setText(Messages.getString("CompositeCPLEditor.6"));
buttonViewVariables.setLayoutData(gridData20);
buttonViewVariables
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
viewVariables();
}
});
buttonViewTime=new Button(groupOutputButtons, SWT.NONE);
buttonViewTime.setText(Messages.getString("CompositeCPLEditor.7"));
buttonViewTime.setLayoutData(gridData24);
buttonViewTime
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
viewTime();
}
});
Label filler25=new Label(groupOutputButtons, SWT.NONE);
filler32=new Label(groupOutputButtons, SWT.NONE);
filler32.setText("");
filler32.setLayoutData(gridData19);
buttonSaveOutput=new Button(groupOutputButtons, SWT.NONE);
buttonSaveOutput.setText(Messages.getString("CompositeCPLEditor.8"));
buttonSaveOutput.setLayoutData(gridData22);
buttonSaveOutput
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
if(pathCPLOutput==null)
{
pathCPLOutput=GUIHelper.prefs.get("PATH_CPL_OUTPUT", ".");
}
saveOutputFile();
}
});
buttonClearOutput=new Button(groupOutputButtons, SWT.NONE);
buttonClearOutput.setText(Messages.getString("CompositeCPLEditor.9"));
buttonClearOutput.setLayoutData(gridData23);
buttonClearOutput
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
clearOutput();
}
});
filler24=new Label(groupOutputButtons, SWT.NONE);
filler24.setText("");
filler24.setLayoutData(gridData18);
}
private void createGroupTimerButtons()
{
GridData gridData16=new GridData();
gridData16.horizontalAlignment=GridData.CENTER;
gridData16.grabExcessHorizontalSpace=true;
gridData16.verticalAlignment=GridData.CENTER;
GridData gridData15=new GridData();
gridData15.horizontalAlignment=GridData.CENTER;
gridData15.grabExcessHorizontalSpace=true;
gridData15.verticalAlignment=GridData.CENTER;
GridData gridData7=new GridData();
gridData7.horizontalAlignment=GridData.FILL;
gridData7.verticalAlignment=GridData.CENTER;
GridData gridData6=new GridData();
gridData6.horizontalAlignment=GridData.FILL;
gridData6.verticalAlignment=GridData.CENTER;
GridData gridData5=new GridData();
gridData5.horizontalAlignment=GridData.FILL;
gridData5.verticalAlignment=GridData.CENTER;
GridData gridData=new GridData();
gridData.horizontalAlignment=GridData.FILL;
gridData.verticalAlignment=GridData.CENTER;
GridData gridData11=new GridData();
gridData11.horizontalAlignment=GridData.FILL;
gridData11.verticalAlignment=GridData.CENTER;
GridData gridData10=new GridData();
gridData10.horizontalAlignment=GridData.FILL;
gridData10.verticalAlignment=GridData.CENTER;
GridData gridData9=new GridData();
gridData9.horizontalAlignment=GridData.END;
gridData9.verticalAlignment=GridData.CENTER;
GridData gridData8=new GridData();
gridData8.horizontalAlignment=GridData.END;
gridData8.verticalAlignment=GridData.CENTER;
GridLayout gridLayout2=new GridLayout();
gridLayout2.numColumns=6;
gridLayout2.makeColumnsEqualWidth=false;
GridData gridData4=new GridData();
gridData4.horizontalAlignment=GridData.FILL;
gridData4.grabExcessVerticalSpace=false;
gridData4.verticalAlignment=GridData.FILL;
groupTimerButtons=new Group(this, SWT.NONE);
groupTimerButtons.setText(Messages.getString("CompositeCPLEditor.10"));
groupTimerButtons.setLayout(gridLayout2);
groupTimerButtons.setLayoutData(gridData4);
Label filler10=new Label(groupTimerButtons, SWT.NONE);
labelInterval=new Label(groupTimerButtons, SWT.NONE);
labelInterval.setText(Messages.getString("CompositeCPLEditor.11"));
labelInterval.setLayoutData(gridData9);
textInterval=new Text(groupTimerButtons, SWT.BORDER);
textInterval.setLayoutData(gridData11);
labelRepeat=new Label(groupTimerButtons, SWT.NONE);
labelRepeat.setText(Messages.getString("CompositeCPLEditor.12"));
labelRepeat.setLayoutData(gridData8);
textRepeat=new Text(groupTimerButtons, SWT.BORDER);
textRepeat.setLayoutData(gridData10);
Label filler4=new Label(groupTimerButtons, SWT.NONE);
filler9=new Label(groupTimerButtons, SWT.NONE);
filler9.setText("");
filler9.setLayoutData(gridData16);
filler11=new Label(groupTimerButtons, SWT.NONE);
filler11.setText("");
labelCurrent=new Label(groupTimerButtons, SWT.NONE);
labelCurrent.setText(Messages.getString("CompositeCPLEditor.13"));
labelCounter=new Label(groupTimerButtons, SWT.NONE);
labelCounter.setText("xxx/yyy");
Label filler5=new Label(groupTimerButtons, SWT.NONE);
filler3=new Label(groupTimerButtons, SWT.NONE);
filler3.setText("");
filler3.setLayoutData(gridData15);
Label filler8=new Label(groupTimerButtons, SWT.NONE);
buttonTimerStart=new Button(groupTimerButtons, SWT.NONE);
buttonTimerStart.setText(Messages.getString("CompositeCPLEditor.14"));
buttonTimerStart.setLayoutData(gridData5);
buttonTimerStart
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
timerStart();
}
});
buttonTimerPause=new Button(groupTimerButtons, SWT.NONE);
buttonTimerPause.setText(Messages.getString("CompositeCPLEditor.15"));
buttonTimerPause.setLayoutData(gridData);
buttonTimerPause
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
timerPause();
}
});
buttonTimerResume=new Button(groupTimerButtons, SWT.NONE);
buttonTimerResume.setText(Messages.getString("CompositeCPLEditor.16"));
buttonTimerResume.setLayoutData(gridData6);
buttonTimerResume
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
timerResume();
}
});
buttonTimerStop=new Button(groupTimerButtons, SWT.NONE);
buttonTimerStop.setText(Messages.getString("CompositeCPLEditor.17"));
buttonTimerStop.setLayoutData(gridData7);
buttonTimerStop
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
timerStop();
}
});
}
private void saveOutputFile()
{
pathCPLOutput=GUIHelper.saveTextFile(textAreaOutput.getText(), pathCPLOutput);
GUIHelper.prefs.put("PATH_CPL_INPUT", pathCPLInput);
if(pathCPLInput==null)
{
pathCPLInput=pathCPLOutput;
GUIHelper.prefs.put("PATH_CPL_INPUT", pathCPLInput);
}
}
private void saveCPLFile()
{
pathCPLInput=GUIHelper.saveTextFile(textAreaInput.getText(), pathCPLInput);
GUIHelper.prefs.put("PATH_CPL_INPUT", pathCPLInput);
if(pathCPLOutput==null)
{
pathCPLOutput=pathCPLInput;
GUIHelper.prefs.put("PATH_CPL_OUTPUT", pathCPLOutput);
}
}
private void openCPLFile()
{
if(pathCPLInput==null)
{
pathCPLInput=GUIHelper.prefs.get("PATH_CPL_INPUT", ".");
}
StringBuffer pathUsed=new StringBuffer();
BufferedReader brd=GUIHelper.openTextFile(pathCPLInput, pathUsed);
if(brd==null)
{
return;
}
pathCPLInput=pathUsed.toString();
GUIHelper.prefs.put("PATH_CPL_INPUT", pathCPLInput);
if(pathCPLOutput==null)
{
pathCPLOutput=pathCPLInput;
GUIHelper.prefs.put("PATH_CPL_OUTPUT", pathCPLOutput);
}
try
{
String line;
while((line=brd.readLine())!=null)
{
textAreaInput.append(line+'\n');
}
}
catch(IOException e)
{
GUIHelper.message(Messages.getString("CompositeCPLEditor.18"), SWT.OK|SWT.ICON_ERROR);
return;
}
return;
}
private void sendFile()
{
if(pathCPLInput==null)
{
pathCPLInput=GUIHelper.prefs.get("PATH_CPL_INPUT", ".");
}
FileDialog fd=new FileDialog(GUIHelper.myShell, SWT.OPEN);
fd.setFilterPath(pathCPLInput);
String fileName=fd.open();
if(fileName==null)
{
return;
}
pathCPLInput=fd.getFilterPath();
GUIHelper.prefs.put("PATH_CPL_INPUT", pathCPLInput);
byte[] data;
try
{
data=GUIHelper.readBinaryFile(fileName);
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("CompositePCLWindowing.22")+fileName);
return;
}
try
{
PrinterHelper.send(data);
}
catch(Exception e)
{
GUIHelper.logger.error("Failed:", e);
GUIHelper.error(Messages.getString("CompositeCPLEditor.21")+e.toString());
}
}
private void sendToPrinter()
{
try
{
PrinterHelper.send(textAreaInput.getText());
}
catch(Exception e)
{
GUIHelper.logger.error("Failed:", e);
GUIHelper.error(Messages.getString("CompositeCPLEditor.19")+e.toString());
}
}
private void viewTime()
{
try
{
textAreaOutput.append(PrinterHelper.getVariable("TIME")+"\n");
}
catch(Exception e)
{
GUIHelper.logger.error("Failed:", e);
GUIHelper.error(Messages.getString("CompositeCPLEditor.19")+e.toString());
}
}
public void update(Observable observable, final Object object)
{
GUIHelper.logger.trace("");
String className=observable.getClass().getName();
if(className.equals("com.cognitive.printer.Printer"))
{
if(object==null)
{
GUIHelper.logger.warn("Received null object in notification.  Ignoring it.");
return;
}
GUIHelper.myDisplay.asyncExec(new Runnable()
{
public void run()
{
textAreaOutput.append(object.toString());
}
});
}
else
{
GUIHelper.logger.warn("Received update from unknown observable ["+observable.toString()+"]");
}
}
private void viewVariables()
{
String currentFirmwarePartNumber=TestEngine.getCurrentFirmwarePartNumber();
if(currentFirmwarePartNumber!=null)
{
hasPCLFirmware=currentFirmwarePartNumber.split("-")[1].equals("176");
}
String viewLabel="";
viewLabel+="!QR\r\n";
viewLabel+="!!0 0 0 0\r\n";
viewLabel+="!0 0 0 0\r\n";
viewLabel+="v time ?\r\n";
viewLabel+="v aux_power ?\r\n";
viewLabel+="v buffer_timed_reset ?\r\n";
viewLabel+="v comm ?\r\n";
viewLabel+="v comm dsr ?\r\n";
viewLabel+="v comm dtr ?\r\n";
viewLabel+="v darkness ?\r\n";
viewLabel+="v error_level ?\r\n";
viewLabel+="v index ?\r\n";
if(!hasPCLFirmware)
{
viewLabel+="v print_speed ?\r\n";
}
viewLabel+="v feed ?\r\n";
viewLabel+="v no_media ?\r\n";
viewLabel+="v pitch ?\r\n";
viewLabel+="v position ?\r\n";
viewLabel+="v presentlabel ?\r\n";
viewLabel+="v report_level ?\r\n";
viewLabel+="v report_type ?\r\n";
viewLabel+="v sleep_after ?\r\n";
viewLabel+="v off_after ?\r\n";
viewLabel+="v shift LEFT ?\r\n";
viewLabel+="v user_feedback ?\r\n";
viewLabel+="v width ?\r\n";
viewLabel+="v feed_type ?\r\n";
viewLabel+="cv allocate ?\r\n";
viewLabel+="cv autocut ?\r\n";
viewLabel+="cv low_battery_ignore ?\r\n";
viewLabel+="cv speed\r\n";
viewLabel+="cv media_adjust ?\r\n";
viewLabel+="cv mode ?\r\n";
viewLabel+="cv on_time ?\r\n";
viewLabel+="v print_mode ?\r\n";
viewLabel+="v txtbfr ?\r\n";
viewLabel+="v irda ?\r\n";
viewLabel+="E\r\n";
try
{
PrinterHelper.printer.send(viewLabel);
}
catch(Exception e)
{
GUIHelper.logger.error("Failed:", e);
GUIHelper.error(Messages.getString("CompositeCPLEditor.19")+e.toString());
}
}
private void clearOutput()
{
textAreaOutput.setText("");
}
private void clearInput()
{
textAreaInput.setText("");
}
private void timerStart()
{
buttonTimerStart.setEnabled(false);
buttonTimerPause.setEnabled(true);
buttonTimerResume.setEnabled(false);
buttonTimerStop.setEnabled(true);
timerTaskCPL=new TimerTaskCPL(Integer.parseInt(textRepeat.getText()),
1,
textAreaInput.getText(),
labelCounter,
this);
MainShell.timer.schedule(timerTaskCPL, 0, 1000*(Long.parseLong(textInterval.getText())));
}
private void timerPause()
{
buttonTimerStart.setEnabled(false);
buttonTimerPause.setEnabled(false);
buttonTimerResume.setEnabled(true);
buttonTimerStop.setEnabled(true);
timerTaskCPL.cancel();
timerTaskCPL=null;
}
private void timerResume()
{
buttonTimerStart.setEnabled(false);
buttonTimerPause.setEnabled(true);
buttonTimerResume.setEnabled(false);
buttonTimerStop.setEnabled(true);
String parts[]=labelCounter.getText().split("/");
int current=Integer.parseInt(parts[0])+1;
timerTaskCPL=new TimerTaskCPL(Integer.parseInt(textRepeat.getText()),
current,
textAreaInput.getText(),
labelCounter,
this);
MainShell.timer.schedule(timerTaskCPL, 0, 1000*(Long.parseLong(textInterval.getText())));
}
private void timerStop()
{
buttonTimerStart.setEnabled(true);
buttonTimerPause.setEnabled(false);
buttonTimerResume.setEnabled(false);
buttonTimerStop.setEnabled(false);
if(timerTaskCPL!=null)
{
timerTaskCPL.cancel();
timerTaskCPL=null;
}
}
public void callback()
{
timerStop();
}
}
