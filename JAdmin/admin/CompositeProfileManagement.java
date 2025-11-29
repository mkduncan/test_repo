public class CompositeProfileManagement extends Composite
{
private static boolean hasPCLFirmware=false;
String pathProfiles=null;
private boolean connected=false;
private boolean firstPaintSinceConnected=true;
private boolean dumbTerminal=true;
private Logger logger=null;
private Text textAreaProfile=null;
private Button buttonOpenProfile=null;
private Button buttonGetProfile=null;
private Button buttonSaveProfile=null;
private Button buttonApplyProfile=null;
public CompositeProfileManagement(Composite parent, int style)
{
super(parent, style);
initialize();
}
private void initialize()
{
GridData gridData4=new GridData();
gridData4.horizontalAlignment=GridData.BEGINNING;
gridData4.verticalAlignment=GridData.CENTER;
GridData gridData3=new GridData();
gridData3.horizontalAlignment=GridData.BEGINNING;
gridData3.verticalAlignment=GridData.CENTER;
GridData gridData2=new GridData();
gridData2.horizontalAlignment=GridData.BEGINNING;
gridData2.verticalAlignment=GridData.CENTER;
GridData gridData1=new GridData();
gridData1.horizontalAlignment=GridData.BEGINNING;
gridData1.verticalAlignment=GridData.CENTER;
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=4;
GridData gridData=new GridData();
gridData.horizontalAlignment=GridData.FILL;
gridData.grabExcessHorizontalSpace=true;
gridData.grabExcessVerticalSpace=true;
gridData.horizontalSpan=4;
gridData.verticalAlignment=GridData.FILL;
textAreaProfile=new Text(this, SWT.MULTI|SWT.WRAP|SWT.V_SCROLL);
textAreaProfile.setLayoutData(gridData);
buttonOpenProfile=new Button(this, SWT.NONE);
buttonOpenProfile.setText(Messages.getString("CompositeProfileManagement.2"));
buttonOpenProfile.setLayoutData(gridData4);
buttonOpenProfile
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
openStoredProfile();
}
});
buttonGetProfile=new Button(this, SWT.NONE);
buttonGetProfile.setText(Messages.getString("CompositeProfileManagement.3"));
buttonGetProfile.setLayoutData(gridData3);
buttonGetProfile
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
getProfile();
}
});
buttonSaveProfile=new Button(this, SWT.NONE);
buttonSaveProfile.setText(Messages.getString("CompositeProfileManagement.4"));
buttonSaveProfile.setLayoutData(gridData2);
buttonSaveProfile
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
saveProfile();
}
});
buttonApplyProfile=new Button(this, SWT.NONE);
buttonApplyProfile.setText(Messages.getString("CompositeProfileManagement.5"));
buttonApplyProfile.setLayoutData(gridData1);
buttonApplyProfile
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyProfile();
}
});
this.setLayout(gridLayout);
this.setSize(new Point(499, 200));
this.addPaintListener(new org.eclipse.swt.events.PaintListener()
{
public void paintControl(org.eclipse.swt.events.PaintEvent e)
{
if(connected&&firstPaintSinceConnected&&!dumbTerminal)
{
firstPaintSinceConnected=false;
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
private void openStoredProfile()
{
logger.trace("");
if(pathProfiles==null)
{
pathProfiles=GUIHelper.prefs.get("PATH_PROFILES", ".");
}
StringBuffer pathUsed=new StringBuffer("");
BufferedReader brd=GUIHelper.openTextFile(pathProfiles, pathUsed);
if(brd==null)
{
return;
}
textAreaProfile.selectAll();
textAreaProfile.clearSelection();
pathProfiles=pathUsed.toString();
GUIHelper.prefs.put("PATH_PROFILES", pathProfiles);
try
{
String line;
while((line=brd.readLine())!=null)
{
textAreaProfile.append(line+'\n');
}
}
catch(IOException e)
{
GUIHelper.message(Messages.getString("CompositeProfileManagement.6"), SWT.OK|SWT.ICON_ERROR);
return;
}
}
private void saveProfile()
{
logger.trace("");
pathProfiles=GUIHelper.saveTextFile(textAreaProfile.getText(), pathProfiles);
}
private void getProfile()
{
String currentFirmwarePartNumber=TestEngine.getCurrentFirmwarePartNumber();
if(currentFirmwarePartNumber!=null)
{
hasPCLFirmware=currentFirmwarePartNumber.split("-")[1].equals("176");
}
String[] varsnormal={
"COMM",
"TXTBFR",
"ETHERNET IP",
"ETHERNET NETMASK",
"ETHERNET GATEWAY",
"ETHERNET LPD",
"ETHERNET DHCP",
"ETHERNET RTEL",
"ETHERNET TXTBFR",
"DARKNESS",
"FEED_TYPE",
"TOF",
"PITCH",
"PRINT_SPEED",
"PRINT_MODE",
"PRESENTLABEL",
"RECALIBRATE",
"WIDTH",
"NO_MEDIA",
"OFF_AFTER",
"AUX_POWER",
"ERROR_LEVEL",
"REPORT_LEVEL",
"SHIFT LEFT",
"REPRINT",
"AUTO_TOF",
"MEDIA_ADJUST",
"LANGUAGE"
};
String[] varspcl={
"COMM",
"TXTBFR",
"ETHERNET IP",
"ETHERNET NETMASK",
"ETHERNET GATEWAY",
"ETHERNET LPD",
"ETHERNET DHCP",
"ETHERNET RTEL",
"ETHERNET TXTBFR",
"DARKNESS",
"FEED_TYPE",
"TOF",
"PITCH",
"PRINT_MODE",
"PRESENTLABEL",
"RECALIBRATE",
"WIDTH",
"NO_MEDIA",
"OFF_AFTER",
"AUX_POWER",
"ERROR_LEVEL",
"REPORT_LEVEL",
"SHIFT LEFT",
"REPRINT",
"AUTO_TOF",
"MEDIA_ADJUST",
"LANGUAGE"
};
String[] vars=varsnormal;
if(hasPCLFirmware)
{
vars=varspcl;
}
textAreaProfile.setText("!!0 0 0 0\n!0 0 0 0\n");
String val;
for(String var : vars)
{
val=PrinterHelper.getVariable(var);
if(val==null)
{
continue;
}
if(var!=null)
{
if(var.equals("COMM"))
{
val=val.replace("OFF", "N");
val=val.replace("ON", "R");
}
else if(var.equals("TXTBFR"))
{
val=val.replace(",", " ");
}
else if(var.equals("ETHERNET IP"))
{
val=val.replace("ADDRESS", "");
}
else if(var.equals("ERROR_LEVEL"))
{
int i=Integer.parseInt(val);
switch(i)
{
case 0:
val="INFO";
break;
case 1:
val="VALIDATION_INFO";
break;
case 2:
val="FEEDBACK";
break;
case 3:
val="PARSE_ERROR";
break;
case 4:
val="RENDER_ERROR";
break;
case 5:
val="SEVERE_ERROR";
break;
default:
val="SEVERE_ERROR";
break;
}
}
else if(var.equals("PRINT_MODE"))
{
if(val.equals("0"))
{
val="DT";
}
else
{
val="TT";
}
}
else if(var.equals("AUX_POWER"))
{
if(val.equals("0"))
{
val="OFF";
}
else
{
val="ON";
}
}
textAreaProfile.append("VARIABLE "+var+" "+val+"\n");
textAreaProfile.update();
}
}
textAreaProfile.append("VARIABLE WRITE\n");
textAreaProfile.append("END\n");
}
private void applyProfile()
{
String profile=textAreaProfile.getText();
PrinterHelper.send(profile);
JAdminDialogMessage dialog=new JAdminDialogMessage(Messages.getString("CompositeProfileManagement.0"));
dialog.setMessage(Messages.getString("CompositeProfileManagement.1"));
dialog.closeAfter(2000);
}
}
