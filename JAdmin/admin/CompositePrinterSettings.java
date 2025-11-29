public class CompositePrinterSettings extends Composite
{
private static boolean hasPCLFirmware=false;
private static String currentFirmwarePartNumber="";
private static boolean showAuto=false;
private boolean connected=false;
private boolean firstPaintSinceConnected=true;
private boolean dumbTerminal=true;
private Logger logger=null;
private String modelNumber="";
private Label labelPrintSpeed=null;
private Combo comboPrintSpeed=null;
private Button buttonPrintSpeed=null;
private Label labelNoMediaOut=null;
private Text textNoMediaOut=null;
private Button buttonNoMediaOut=null;
private Button checkBoxAuxPower=null;
private Button buttonAuxPower=null;
private Label labelDebugReportLevel=null;
private Combo comboDebugReportLevel=null;
private Button buttonDebugReportLevel=null;
private Button checkBoxAutoReprint=null;
private Button buttonAutoReprint=null;
private Label labelMediaAdjust=null;
private Text textMediaAdjust=null;
private Button buttonMediaAdjust=null;
private Label labelPitch=null;
private Combo comboPitch=null;
private Button buttonPitch=null;
private Group groupPrimaryLanguage=null;
private Button radioButtonPL_PCL=null;
private Button radioButtonPL_ZPL=null;
private Button radioButtonPL_EPL=null;
private Button radioButtonPL_CPL=null;
private Button buttonPrimaryLanguage=null;
private Group groupThermalMode=null;
private Button radioButtonTM_DT=null;
private Button radioButtonTM_TT=null;
private Button buttonTM=null;
private Label filler5=null;
private Label separator=null;
private Label separator1=null;
private Label separator2=null;
private Label separator3=null;
private Label separator4=null;
private Label separator5=null;
private Label separator6=null;
private Label separator7=null;
private Label labelTime=null;
private Text textTime=null;
private Label labelDate=null;
private Text textDate=null;
private Button buttonTDRefresh=null;
private Button buttonTDApply=null;
private Button buttonTestLabel=null;
private Button buttonRefresh=null;
private Label filler8=null;
private Label filler14=null;
private Label filler15=null;
private Label filler16=null;
private Label separator8=null;
private Group groupDarkness=null;
private Text textDarkness=null;
private Slider sliderDarkness=null;
private Button buttonDarkness=null;
private Label filler6=null;
private Label labelCustomSpeed=null;
private Label filler51=null;
public CompositePrinterSettings(Composite parent, int style)
{
super(parent, style);
initialize();
}
private void initialize()
{
GridData gridData48=new GridData();
gridData48.horizontalAlignment=GridData.BEGINNING;
gridData48.verticalAlignment=GridData.CENTER;
GridData gridData47=new GridData();
gridData47.horizontalSpan=2;
GridData gridData46=new GridData();
gridData46.horizontalSpan=2;
GridData gridData45=new GridData();
gridData45.horizontalSpan=2;
GridData gridData44=new GridData();
gridData44.horizontalSpan=2;
GridData gridData43=new GridData();
gridData43.horizontalSpan=2;
GridData gridData42=new GridData();
gridData42.horizontalSpan=2;
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
gridData34.horizontalAlignment=GridData.FILL;
gridData34.verticalAlignment=GridData.CENTER;
GridData gridData33=new GridData();
gridData33.horizontalAlignment=GridData.FILL;
gridData33.verticalAlignment=GridData.CENTER;
GridData gridData32=new GridData();
gridData32.horizontalAlignment=GridData.FILL;
gridData32.verticalAlignment=GridData.CENTER;
GridData gridData31=new GridData();
gridData31.horizontalAlignment=GridData.FILL;
gridData31.verticalAlignment=GridData.CENTER;
GridData gridData30=new GridData();
gridData30.horizontalAlignment=GridData.FILL;
gridData30.verticalAlignment=GridData.CENTER;
GridData gridData29=new GridData();
gridData29.horizontalAlignment=GridData.FILL;
gridData29.verticalAlignment=GridData.CENTER;
GridData gridData28=new GridData();
gridData28.horizontalAlignment=GridData.FILL;
gridData28.verticalAlignment=GridData.CENTER;
GridData gridData27=new GridData();
gridData27.horizontalAlignment=GridData.FILL;
gridData27.verticalAlignment=GridData.CENTER;
GridData gridData26=new GridData();
gridData26.horizontalAlignment=GridData.FILL;
gridData26.verticalAlignment=GridData.CENTER;
GridData gridData20=new GridData();
gridData20.horizontalSpan=6;
gridData20.verticalAlignment=GridData.CENTER;
gridData20.horizontalAlignment=GridData.FILL;
GridData gridData19=new GridData();
gridData19.grabExcessHorizontalSpace=true;
Label filler18=new Label(this, SWT.NONE);
GridData gridData17=new GridData();
gridData17.grabExcessHorizontalSpace=true;
GridData gridData16=new GridData();
gridData16.grabExcessVerticalSpace=true;
GridData gridData15=new GridData();
gridData15.grabExcessVerticalSpace=true;
Label filler7=new Label(this, SWT.NONE);
GridData gridData13=new GridData();
gridData13.horizontalSpan=6;
gridData13.verticalAlignment=GridData.CENTER;
gridData13.horizontalAlignment=GridData.CENTER;
GridData gridData12=new GridData();
gridData12.horizontalSpan=6;
gridData12.verticalAlignment=GridData.CENTER;
gridData12.horizontalAlignment=GridData.CENTER;
GridData gridData11=new GridData();
gridData11.horizontalSpan=4;
gridData11.verticalAlignment=GridData.CENTER;
gridData11.horizontalAlignment=GridData.FILL;
GridData gridData10=new GridData();
gridData10.horizontalAlignment=GridData.FILL;
gridData10.horizontalSpan=4;
gridData10.verticalAlignment=GridData.CENTER;
GridData gridData9=new GridData();
gridData9.horizontalAlignment=GridData.FILL;
gridData9.horizontalSpan=4;
gridData9.verticalAlignment=GridData.CENTER;
GridData gridData8=new GridData();
gridData8.horizontalAlignment=GridData.FILL;
gridData8.horizontalSpan=4;
gridData8.verticalAlignment=GridData.CENTER;
GridData gridData7=new GridData();
gridData7.horizontalAlignment=GridData.FILL;
gridData7.horizontalSpan=4;
gridData7.verticalAlignment=GridData.CENTER;
GridData gridData6=new GridData();
gridData6.horizontalAlignment=GridData.FILL;
gridData6.horizontalSpan=4;
gridData6.verticalAlignment=GridData.CENTER;
GridData gridData5=new GridData();
gridData5.horizontalAlignment=GridData.FILL;
gridData5.horizontalSpan=4;
gridData5.verticalAlignment=GridData.CENTER;
GridData gridData56=new GridData();
gridData56.horizontalAlignment=GridData.CENTER;
gridData56.verticalSpan=18;
gridData56.widthHint=-1;
gridData56.grabExcessHorizontalSpace=true;
gridData56.verticalAlignment=GridData.FILL;
GridData gridData18=new GridData();
gridData18.horizontalSpan=2;
GridData gridData14=new GridData();
gridData14.horizontalSpan=3;
Label filler36=new Label(this, SWT.NONE);
filler8=new Label(this, SWT.NONE);
filler8.setText("");
filler8.setLayoutData(gridData15);
Label filler9=new Label(this, SWT.NONE);
Label filler10=new Label(this, SWT.NONE);
Label filler11=new Label(this, SWT.NONE);
Label filler16207=new Label(this, SWT.NONE);
Label filler17=new Label(this, SWT.NONE);
labelPrintSpeed=new Label(this, SWT.NONE);
labelPrintSpeed.setText(Messages.getString("CompositePrinterSettings.0"));
labelCustomSpeed=new Label(this, SWT.NONE);
labelCustomSpeed.setVisible(false);
labelCustomSpeed.setText("(0000)");
labelCustomSpeed.setLayoutData(gridData48);
createComboPrintSpeed();
buttonPrintSpeed=new Button(this, SWT.NONE);
buttonPrintSpeed.setText(Messages.getString("General.0"));
buttonPrintSpeed.setLayoutData(gridData26);
buttonPrintSpeed
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyPrintSpeed();
}
});
separator=new Label(this, SWT.SEPARATOR);
separator.setText("");
separator.setLayoutData(gridData56);
createGroupDarkness();
Label filler16206=new Label(this, SWT.NONE);
filler16=new Label(this, SWT.NONE);
filler16.setText("");
filler16.setLayoutData(gridData19);
separator1=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator1.setText("");
separator1.setLayoutData(gridData9);
Label filler16205=new Label(this, SWT.NONE);
Label filler16225=new Label(this, SWT.NONE);
labelNoMediaOut=new Label(this, SWT.NONE);
labelNoMediaOut.setText(Messages.getString("CompositePrinterSettings.2"));
labelNoMediaOut.setLayoutData(gridData42);
textNoMediaOut=new Text(this, SWT.BORDER);
textNoMediaOut.setLayoutData(gridData35);
buttonNoMediaOut=new Button(this, SWT.NONE);
buttonNoMediaOut.setText(Messages.getString("General.0"));
buttonNoMediaOut.setLayoutData(gridData27);
buttonNoMediaOut
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyNoMediaOut();
}
});
filler15=new Label(this, SWT.NONE);
filler15.setText("");
filler15.setLayoutData(gridData17);
Label filler16224=new Label(this, SWT.NONE);
separator2=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator2.setText("Label");
separator2.setLayoutData(gridData8);
Label filler1916=new Label(this, SWT.NONE);
Label filler15204=new Label(this, SWT.NONE);
Label filler16223=new Label(this, SWT.NONE);
labelDebugReportLevel=new Label(this, SWT.NONE);
labelDebugReportLevel.setText(Messages.getString("CompositePrinterSettings.4"));
labelDebugReportLevel.setLayoutData(gridData43);
createComboDebugReportLevel();
buttonDebugReportLevel=new Button(this, SWT.NONE);
buttonDebugReportLevel.setText(Messages.getString("General.0"));
buttonDebugReportLevel.setLayoutData(gridData28);
createGroupPrimaryLanguage();
buttonDebugReportLevel
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyReportLevel();
}
});
Label filler15203=new Label(this, SWT.NONE);
Label filler28=new Label(this, SWT.NONE);
Label filler29=new Label(this, SWT.NONE);
Label filler33=new Label(this, SWT.NONE);
Label filler30=new Label(this, SWT.NONE);
Label filler31=new Label(this, SWT.NONE);
Label filler27=new Label(this, SWT.NONE);
Label filler16222=new Label(this, SWT.NONE);
separator6=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator6.setText("Label");
separator6.setLayoutData(gridData10);
Label filler15202=new Label(this, SWT.NONE);
Label filler16221=new Label(this, SWT.NONE);
checkBoxAuxPower=new Button(this, SWT.CHECK);
checkBoxAuxPower.setText(Messages.getString("CompositePrinterSettings.6"));
checkBoxAuxPower.setLayoutData(gridData14);
buttonAuxPower=new Button(this, SWT.NONE);
buttonAuxPower.setText(Messages.getString("General.0"));
buttonAuxPower.setLayoutData(gridData29);
buttonAuxPower
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyAuxPower();
}
});
Label filler15201=new Label(this, SWT.NONE);
Label filler16220=new Label(this, SWT.NONE);
separator3=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator3.setText("Label");
separator3.setLayoutData(gridData7);
Label filler15200=new Label(this, SWT.NONE);
Label filler16219=new Label(this, SWT.NONE);
checkBoxAutoReprint=new Button(this, SWT.CHECK);
checkBoxAutoReprint.setText(Messages.getString("CompositePrinterSettings.8"));
checkBoxAutoReprint.setLayoutData(gridData18);
Label filler733=new Label(this, SWT.NONE);
buttonAutoReprint=new Button(this, SWT.NONE);
buttonAutoReprint.setText(Messages.getString("General.0"));
buttonAutoReprint.setLayoutData(gridData30);
buttonAutoReprint
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyAutoReprint();
}
});
Label filler15199=new Label(this, SWT.NONE);
Label filler16218=new Label(this, SWT.NONE);
separator4=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator4.setText("Label");
separator4.setLayoutData(gridData6);
Label filler15198=new Label(this, SWT.NONE);
Label filler16217=new Label(this, SWT.NONE);
labelMediaAdjust=new Label(this, SWT.NONE);
labelMediaAdjust.setText(Messages.getString("CompositePrinterSettings.10"));
labelMediaAdjust.setLayoutData(gridData44);
textMediaAdjust=new Text(this, SWT.BORDER);
textMediaAdjust.setLayoutData(gridData36);
buttonMediaAdjust=new Button(this, SWT.NONE);
buttonMediaAdjust.setText(Messages.getString("General.0"));
buttonMediaAdjust.setLayoutData(gridData31);
createGroupThermalMode();
buttonMediaAdjust
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyMediaAdjust();
}
});
Label filler15197=new Label(this, SWT.NONE);
Label filler16216=new Label(this, SWT.NONE);
separator5=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator5.setText("Label");
separator5.setLayoutData(gridData5);
Label filler15196=new Label(this, SWT.NONE);
Label filler16215=new Label(this, SWT.NONE);
labelPitch=new Label(this, SWT.NONE);
labelPitch.setText(Messages.getString("CompositePrinterSettings.12"));
labelPitch.setLayoutData(gridData45);
createComboPitch();
buttonPitch=new Button(this, SWT.NONE);
buttonPitch.setText(Messages.getString("General.0"));
buttonPitch.setLayoutData(gridData32);
buttonPitch.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyPitch();
}
});
Label filler15195=new Label(this, SWT.NONE);
Label filler16214=new Label(this, SWT.NONE);
separator7=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator7.setText("Label");
separator7.setLayoutData(gridData11);
Label filler15194=new Label(this, SWT.NONE);
Label filler16213=new Label(this, SWT.NONE);
labelTime=new Label(this, SWT.NONE);
labelTime.setText(Messages.getString("CompositePrinterSettings.14"));
labelTime.setLayoutData(gridData46);
textTime=new Text(this, SWT.BORDER);
textTime.setLayoutData(gridData37);
buttonTDRefresh=new Button(this, SWT.NONE);
buttonTDRefresh.setText(Messages.getString("General.1"));
buttonTDRefresh.setLayoutData(gridData33);
buttonTDRefresh
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
refreshDateTime();
}
});
Label filler15193=new Label(this, SWT.NONE);
Label filler16212=new Label(this, SWT.NONE);
labelDate=new Label(this, SWT.NONE);
labelDate.setText("Date (mm/dd/yy or dd.mm.yy):");
labelDate.setLayoutData(gridData47);
labelDate.setToolTipText(Messages.getString("CompositePrinterSettings.68"));
textDate=new Text(this, SWT.BORDER);
textDate.setToolTipText(Messages.getString("CompositePrinterSettings.68"));
textDate.setLayoutData(gridData38);
buttonTDApply=new Button(this, SWT.NONE);
buttonTDApply.setText(Messages.getString("General.0"));
buttonTDApply.setLayoutData(gridData34);
buttonTDApply
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyDateTime();
}
});
Label filler15192=new Label(this, SWT.NONE);
Label filler16211=new Label(this, SWT.NONE);
Label filler15106=new Label(this, SWT.NONE);
Label filler20=new Label(this, SWT.NONE);
Label filler15107=new Label(this, SWT.NONE);
Label filler15108=new Label(this, SWT.NONE);
Label filler1911=new Label(this, SWT.NONE);
Label filler15191=new Label(this, SWT.NONE);
Label filler21=new Label(this, SWT.NONE);
separator8=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator8.setText("");
separator8.setLayoutData(gridData20);
Label filler26=new Label(this, SWT.NONE);
Label filler16210=new Label(this, SWT.NONE);
buttonRefresh=new Button(this, SWT.NONE);
buttonRefresh.setText(Messages.getString("General.1"));
buttonRefresh.setLayoutData(gridData13);
buttonRefresh
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
refresh();
}
});
Label filler15190=new Label(this, SWT.NONE);
Label filler16209=new Label(this, SWT.NONE);
buttonTestLabel=new Button(this, SWT.NONE);
buttonTestLabel.setText(Messages.getString("CompositePrinterSettings.19"));
buttonTestLabel.setLayoutData(gridData12);
buttonTestLabel
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
PrinterHelper.printSelfTestLabel();
}
});
Label filler15189=new Label(this, SWT.NONE);
Label filler16208=new Label(this, SWT.NONE);
Label filler12=new Label(this, SWT.NONE);
Label filler19=new Label(this, SWT.NONE);
Label filler13=new Label(this, SWT.NONE);
filler14=new Label(this, SWT.NONE);
filler14.setText("");
filler14.setLayoutData(gridData16);
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=8;
gridLayout.marginWidth=0;
gridLayout.horizontalSpacing=10;
gridLayout.verticalSpacing=3;
gridLayout.marginHeight=5;
this.setLayout(gridLayout);
this.setSize(new Point(526, 480));
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
private void createComboPrintSpeed()
{
GridData gridData41=new GridData();
gridData41.horizontalAlignment=GridData.FILL;
gridData41.verticalAlignment=GridData.CENTER;
comboPrintSpeed=new Combo(this, SWT.READ_ONLY);
comboPrintSpeed.setLayoutData(gridData41);
comboPrintSpeed
.addSelectionListener(new org.eclipse.swt.events.SelectionListener()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
if(comboPrintSpeed.getSelectionIndex()==3)
{
buttonPrintSpeed.setEnabled(false);
}
else
{
buttonPrintSpeed.setEnabled(true);
}
}
public void widgetDefaultSelected(
org.eclipse.swt.events.SelectionEvent e)
{
}
});
}
private void createComboDebugReportLevel()
{
GridData gridData40=new GridData();
gridData40.horizontalAlignment=GridData.FILL;
gridData40.verticalAlignment=GridData.CENTER;
comboDebugReportLevel=new Combo(this, SWT.READ_ONLY);
comboDebugReportLevel.setLayoutData(gridData40);
}
private void createComboPitch()
{
GridData gridData39=new GridData();
gridData39.horizontalAlignment=GridData.FILL;
gridData39.verticalAlignment=GridData.CENTER;
comboPitch=new Combo(this, SWT.READ_ONLY);
comboPitch.setLayoutData(gridData39);
}
private void createGroupPrimaryLanguage()
{
GridData gridData71=new GridData();
gridData71.horizontalSpan=2;
gridData71.verticalAlignment=GridData.CENTER;
gridData71.grabExcessHorizontalSpace=true;
gridData71.horizontalAlignment=GridData.BEGINNING;
GridData gridData1=new GridData();
gridData1.horizontalSpan=3;
gridData1.verticalAlignment=GridData.CENTER;
gridData1.horizontalAlignment=GridData.CENTER;
GridLayout gridLayout1=new GridLayout();
gridLayout1.numColumns=3;
gridLayout1.marginWidth=10;
gridLayout1.marginHeight=15;
GridData gridData=new GridData();
gridData.verticalAlignment=GridData.FILL;
gridData.verticalSpan=7;
gridData.horizontalAlignment=GridData.FILL;
groupPrimaryLanguage=new Group(this, SWT.NONE);
groupPrimaryLanguage.setLayoutData(gridData);
groupPrimaryLanguage.setLayout(gridLayout1);
groupPrimaryLanguage.setText(Messages.getString("CompositePrinterSettings.20"));
radioButtonPL_PCL=new Button(groupPrimaryLanguage, SWT.RADIO);
radioButtonPL_PCL.setText("Auto (EcPL/ZcPL)");
radioButtonPL_PCL.setLayoutData(gridData71);
radioButtonPL_PCL.setToolTipText(Messages.getString("CompositePrinterSettings.21"));
radioButtonPL_EPL=new Button(groupPrimaryLanguage, SWT.RADIO);
radioButtonPL_EPL.setText("EcPL");
radioButtonPL_EPL.setToolTipText(Messages.getString("CompositePrinterSettings.22"));
radioButtonPL_ZPL=new Button(groupPrimaryLanguage, SWT.RADIO);
radioButtonPL_ZPL.setText("ZcPL");
radioButtonPL_ZPL.setToolTipText(Messages.getString("CompositePrinterSettings.23"));
Label filler=new Label(groupPrimaryLanguage, SWT.NONE);
radioButtonPL_CPL=new Button(groupPrimaryLanguage, SWT.RADIO);
radioButtonPL_CPL.setText("CPL");
radioButtonPL_CPL.setToolTipText(Messages.getString("CompositePrinterSettings.24"));
Label filler1=new Label(groupPrimaryLanguage, SWT.NONE);
filler51=new Label(groupPrimaryLanguage, SWT.NONE);
filler51.setText("");
Label filler281=new Label(groupPrimaryLanguage, SWT.NONE);
buttonPrimaryLanguage=new Button(groupPrimaryLanguage, SWT.NONE);
buttonPrimaryLanguage.setText(Messages.getString("General.0"));
buttonPrimaryLanguage.setLayoutData(gridData1);
buttonPrimaryLanguage
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyPrimaryLanguage();
}
});
}
private void createGroupThermalMode()
{
GridData gridData3=new GridData();
gridData3.horizontalAlignment=GridData.CENTER;
gridData3.verticalAlignment=GridData.CENTER;
GridData gridData2=new GridData();
gridData2.grabExcessVerticalSpace=false;
gridData2.horizontalAlignment=GridData.FILL;
gridData2.verticalAlignment=GridData.FILL;
gridData2.verticalSpan=6;
gridData2.grabExcessHorizontalSpace=false;
GridLayout gridLayout2=new GridLayout();
gridLayout2.marginWidth=10;
gridLayout2.marginHeight=15;
groupThermalMode=new Group(this, SWT.NONE);
groupThermalMode.setText(Messages.getString("CompositePrinterSettings.26"));
groupThermalMode.setLayoutData(gridData2);
groupThermalMode.setLayout(gridLayout2);
radioButtonTM_DT=new Button(groupThermalMode, SWT.RADIO);
radioButtonTM_DT.setText(Messages.getString("CompositePrinterSettings.27"));
radioButtonTM_TT=new Button(groupThermalMode, SWT.RADIO);
radioButtonTM_TT.setText(Messages.getString("CompositePrinterSettings.28"));
filler5=new Label(groupThermalMode, SWT.NONE);
filler5.setText("");
buttonTM=new Button(groupThermalMode, SWT.NONE);
buttonTM.setText(Messages.getString("General.0"));
buttonTM.setLayoutData(gridData3);
buttonTM.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyThermalMode();
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
layout(true, true);
}
private void applyMediaAdjust()
{
GUIHelper.applySimpleTextField(textMediaAdjust, "MEDIA_ADJUST");
}
private void applyNoMediaOut()
{
GUIHelper.applySimpleTextField(textNoMediaOut, "NO_MEDIA");
}
private void applyAuxPower()
{
GUIHelper.applySimpleCheckBox(checkBoxAuxPower, "AUX_POWER");
}
private void applyAutoReprint()
{
GUIHelper.applySimpleCheckBox(checkBoxAutoReprint, "REPRINT");
}
private void refresh()
{
logger.trace("");
GUIHelper.blankify(this);
update();
try
{
hasPCLFirmware();
refreshPrintSpeed();
refreshReportLevel();
refreshPitch();
refreshDateTime();
refreshPrimaryLanguage();
refreshThermalMode();
refreshDarkness();
GUIHelper.refreshSimpleTextField(textNoMediaOut, "NO_MEDIA");
GUIHelper.refreshSimpleCheckBox(checkBoxAuxPower, "AUX_POWER");
GUIHelper.refreshSimpleCheckBox(checkBoxAutoReprint, "REPRINT");
GUIHelper.refreshSimpleTextField(textMediaAdjust, "MEDIA_ADJUST");
}
finally
{
GUIHelper.setCursor(0);
}
}
private void applyReportLevel()
{
int selectedReportLevel=comboDebugReportLevel.getSelectionIndex();
String newReportLevel=null;
switch(selectedReportLevel)
{
case 0:
newReportLevel="0";
break;
case 1:
newReportLevel="1";
break;
case 2:
newReportLevel="2";
break;
default:
GUIHelper.error(Messages.getString("CompositePrinterSettings.35")+selectedReportLevel);
logger.error("Invalid reporting level: "+selectedReportLevel);
return;
}
PrinterHelper.setVariable("REPORT_LEVEL", newReportLevel);
refreshReportLevel();
}
private void refreshReportLevel()
{
comboDebugReportLevel.removeAll();
comboDebugReportLevel.add(Messages.getString("CompositePrinterSettings.32"));
comboDebugReportLevel.add(Messages.getString("CompositePrinterSettings.33"));
comboDebugReportLevel.add(Messages.getString("CompositePrinterSettings.34"));
String reportLevel=null;
reportLevel=PrinterHelper.getVariable("REPORT_LEVEL");
if(reportLevel==null)
{
return;
}
switch(Integer.parseInt(reportLevel))
{
case 0:
comboDebugReportLevel.select(0);
break;
case 1:
comboDebugReportLevel.select(1);
break;
case 2:
comboDebugReportLevel.select(2);
break;
default:
logger.error(Messages.getString("CompositePrinterSettings.43")+reportLevel);
GUIHelper.error("Can't translate debug report level."+reportLevel);
}
}
private void applyPrintSpeed()
{
int selected=comboPrintSpeed.getSelectionIndex();
String newSpeed=null;
switch(selected)
{
case 0:
newSpeed="LOWSPEED";
break;
case 1:
newSpeed="NORMAL";
break;
case 2:
newSpeed="HIGHSPEED";
break;
case 3:
buttonPrintSpeed.setEnabled(false);
break;
default:
GUIHelper.error(Messages.getString("CompositePrinterSettings.48"));
logger.error("Invalid speed selected");
return;
}
PrinterHelper.setVariable("", newSpeed);
refreshPrintSpeed();
}
private void refreshPrintSpeed()
{
logger.trace("");
comboPrintSpeed.removeAll();
comboPrintSpeed.add(Messages.getString("CompositePrinterSettings.45"));
comboPrintSpeed.add(Messages.getString("CompositePrinterSettings.46"));
comboPrintSpeed.add(Messages.getString("CompositePrinterSettings.47"));
comboPrintSpeed.add(Messages.getString("CompositePrinterSettings.49"));
buttonPrintSpeed.setEnabled(true);
String pitch=PrinterHelper.getVariable("PITCH");
modelNumber=com.cognitive.printer.Printer.modelNumber;
boolean isCI=false;
boolean isDLX=false;
if(modelNumber.startsWith("CI")||modelNumber.startsWith("7006"))
{
isCI=true;
}
else if(modelNumber.startsWith("DB")||modelNumber.startsWith("7005"))
{
isDLX=true;
}
String speed="";
if(!hasPCLFirmware)
{
speed=PrinterHelper.getVariable("PRINT_SPEED");
}
else
{
speed="NORMAL";
if(isDLX)
{
speed="HIGHSPEED";
}
}
labelCustomSpeed.setVisible(false);
labelCustomSpeed.setText("("+speed+")");
if(pitch==null||speed==null)
{
return;
}
int iPitch=Integer.parseInt(pitch);
if(speed.equalsIgnoreCase("LOWSPEED"))
{
comboPrintSpeed.select(0);
}
else if(speed.equalsIgnoreCase("NORMAL"))
{
comboPrintSpeed.select(1);
}
else if(speed.equalsIgnoreCase("HIGHSPEED"))
{
comboPrintSpeed.select(2);
}
else
{
int iSpeed=Integer.parseInt(speed);
switch(iPitch)
{
case 200:
case 100:
switch(iSpeed)
{
case 4000:
if(isCI)
{
comboPrintSpeed.select(1);
}
else
{
comboPrintSpeed.select(0);
}
break;
case 2000:
comboPrintSpeed.select(0);
break;
case 3950:
comboPrintSpeed.select(0);
break;
case 6000:
if(isCI)
{
comboPrintSpeed.select(2);
}
else
{
comboPrintSpeed.select(1);
}
break;
case 3000:
comboPrintSpeed.select(1);
break;
case 8000:
case 5000:
comboPrintSpeed.select(2);
break;
default:
comboPrintSpeed.select(3);
labelCustomSpeed.setVisible(true);
buttonPrintSpeed.setEnabled(false);
logger.info("Custom speed received from printer: "+iSpeed);
break;
}
break;
case 300:
case 150:
switch(iSpeed)
{
case 2000:
comboPrintSpeed.select(0);
break;
case 3950:
comboPrintSpeed.select(0);
break;
case 4000:
case 3000:
comboPrintSpeed.select(1);
break;
case 6000:
case 5000:
comboPrintSpeed.select(2);
break;
default:
comboPrintSpeed.select(3);
labelCustomSpeed.setVisible(true);
buttonPrintSpeed.setEnabled(false);
logger.info("Custom speed received from printer: "+iSpeed);
break;
}
break;
default:
GUIHelper.error(Messages.getString("CompositePrinterSettings.61")+iPitch);
logger.error("Invalid pitch received from printer: "+iPitch);
break;
}
}
}
private void applyPitch()
{
PrinterHelper.setVariable("PITCH", comboPitch.getText());
refreshPitch();
}
private void refreshPitch()
{
logger.trace("");
String pitch=null;
pitch=PrinterHelper.getVariable("PITCH");
if(null==pitch)
{
return;
}
comboPitch.removeAll();
if(pitch.equals("300")||pitch.equals("150"))
{
comboPitch.add("150");
comboPitch.add("300");
}
else
{
comboPitch.add("100");
comboPitch.add("200");
}
comboPitch.setText(pitch);
}
private void applyDateTime()
{
String data;
String time=textTime.getText();
if(!time.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}"))
{
GUIHelper.error(Messages.getString("CompositePrinterSettings.62"));
return;
}
DateFormat dateFormat=DateFormat.getDateInstance(3);
Date date=null;
try
{
date=dateFormat.parse(textDate.getText());
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("CompositePrinterSettings.69"));
logger.error(e, e);
return;
}
Calendar calendar=new GregorianCalendar();
calendar.setTime(date);
int year=calendar.get(Calendar.YEAR);
int month=calendar.get(Calendar.MONTH)+1;
int dom=calendar.get(Calendar.DAY_OF_MONTH);
String[] dateParts=textDate.getText().split("/");
String timeParts=time.replaceAll(":", " ");
data="!!0 0 0 0\n!0 0 0 0\nTIME SET "+year+" "+month+" "+dom+" "+timeParts+"\nEND\n";
PrinterHelper.send(data);
refreshDateTime();
}
private void refreshDateTime()
{
logger.trace("");
modelNumber=com.cognitive.printer.Printer.modelNumber;
boolean hasRTC=!(modelNumber.charAt(0)=='D'&&modelNumber.charAt(8)=='8');
labelDate.setEnabled(hasRTC);
textDate.setEnabled(hasRTC);
labelTime.setEnabled(hasRTC);
textTime.setEnabled(hasRTC);
buttonTDRefresh.setEnabled(hasRTC);
buttonTDApply.setEnabled(hasRTC);
if(!hasRTC)
{
return;
}
String[] tokens=null;
String dateTime=PrinterHelper.getVariable("TIME");
if(dateTime==null)
{
return;
}
tokens=dateTime.split(" ");
String time=tokens[2];
textTime.setText(time.trim());
String dateString=tokens[1];
String[] dateParts=dateString.split("/");
int month=Integer.parseInt(dateParts[0])-1;
int date=Integer.parseInt(dateParts[1]);
int year=Integer.parseInt(dateParts[2]);
Calendar calendar=new GregorianCalendar();
calendar.clear();
calendar.setLenient(true);
calendar.set(Calendar.YEAR, year);
calendar.set(Calendar.MONTH, month);
calendar.set(Calendar.DATE, date);
Date d=calendar.getTime();
DateFormat dateFormat=DateFormat.getDateInstance(3);
dateFormat.setCalendar(calendar);
textDate.setText(dateFormat.format(d));
}
private void applyPrimaryLanguage()
{
String language=null;
if(radioButtonPL_PCL.getSelection())
{
if(showAuto)
{
language="AUTO";
}
else
{
language="PCL";
int ret=GUIHelper.message(Messages.getString("CompositePrinterSettings.65"), SWT.OK|SWT.CANCEL|SWT.ICON_INFORMATION);
if(ret==SWT.CANCEL)
{
refreshPrimaryLanguage();
return;
}
}
}
else if(radioButtonPL_EPL.getSelection())
{
language="EPL";
}
else if(radioButtonPL_ZPL.getSelection())
{
language="ZPL";
}
else if(radioButtonPL_CPL.getSelection())
{
language="NONE";
}
else
{
GUIHelper.error(Messages.getString("CompositePrinterSettings.64"));
logger.error("Invalid language selected");
refreshPrimaryLanguage();
return;
}
PrinterHelper.setVariable("LANGUAGE", language);
refreshPrimaryLanguage();
}
private void hasPCLFirmware()
{
currentFirmwarePartNumber=TestEngine.getCurrentFirmwarePartNumber();
if(currentFirmwarePartNumber!=null)
{
hasPCLFirmware=currentFirmwarePartNumber.split("-")[1].equals("176");
}
}
private void setScreenProperties()
{
if(!hasPCLFirmware)
{
showAuto=Integer.parseInt(currentFirmwarePartNumber.split("-")[2])>155;
}
else
{
showAuto=false;
}
if(showAuto)
{
radioButtonPL_PCL.setText("Auto (EcPL/ZcPL)");
radioButtonPL_PCL.setToolTipText("Auto (EcPL/ZcPL)");
}
else
{
radioButtonPL_PCL.setText("TPCL                    ");
radioButtonPL_PCL.setToolTipText(Messages.getString("CompositePrinterSettings.21"));
}
}
private void refreshPrimaryLanguage()
{
setScreenProperties();
radioButtonPL_PCL.setEnabled(hasPCLFirmware||showAuto);
if(hasPCLFirmware)
{
radioButtonPL_EPL.setEnabled(false);
radioButtonPL_ZPL.setEnabled(false);
}
String language=PrinterHelper.getVariable("LANGUAGE");
if(language==null)
{
return;
}
if(language.equals("PCL"))
{
radioButtonPL_PCL.setSelection(true);
radioButtonPL_EPL.setSelection(false);
radioButtonPL_ZPL.setSelection(false);
radioButtonPL_CPL.setSelection(false);
}
else if(language.equals("EPL"))
{
radioButtonPL_PCL.setSelection(false);
radioButtonPL_EPL.setSelection(true);
radioButtonPL_ZPL.setSelection(false);
radioButtonPL_CPL.setSelection(false);
}
else if(language.equals("ZPL"))
{
radioButtonPL_PCL.setSelection(false);
radioButtonPL_EPL.setSelection(false);
radioButtonPL_ZPL.setSelection(true);
radioButtonPL_CPL.setSelection(false);
}
else if(language.equals("AUTO"))
{
radioButtonPL_PCL.setSelection(true);
radioButtonPL_EPL.setSelection(false);
radioButtonPL_ZPL.setSelection(false);
radioButtonPL_CPL.setSelection(false);
}
else
{
radioButtonPL_PCL.setSelection(false);
radioButtonPL_EPL.setSelection(false);
radioButtonPL_ZPL.setSelection(false);
radioButtonPL_CPL.setSelection(true);
}
}
void applyThermalMode()
{
String mode;
if(radioButtonTM_DT.getSelection())
{
mode="DT";
}
else if(radioButtonTM_TT.getSelection())
{
mode="TT";
}
else
{
GUIHelper.error(Messages.getString("CompositePrinterSettings.66"));
logger.error("Invalid mode selected");
refreshThermalMode();
return;
}
PrinterHelper.setVariable("PRINT_MODE", mode);
refreshThermalMode();
}
void refreshThermalMode()
{
String mode=PrinterHelper.getVariable("PRINT_MODE");
if(mode==null)
{
return;
}
if(mode.equals("0"))
{
radioButtonTM_DT.setSelection(true);
radioButtonTM_TT.setSelection(false);
}
else
{
radioButtonTM_DT.setSelection(false);
radioButtonTM_TT.setSelection(true);
}
}
private void createGroupDarkness()
{
GridData gridData25=new GridData();
gridData25.horizontalAlignment=GridData.CENTER;
gridData25.grabExcessHorizontalSpace=true;
gridData25.verticalAlignment=GridData.CENTER;
GridData gridData24=new GridData();
gridData24.horizontalSpan=2;
gridData24.verticalAlignment=GridData.CENTER;
gridData24.grabExcessHorizontalSpace=true;
gridData24.horizontalAlignment=GridData.FILL;
GridData gridData23=new GridData();
gridData23.horizontalSpan=4;
GridData gridData22=new GridData();
gridData22.horizontalAlignment=GridData.CENTER;
gridData22.grabExcessHorizontalSpace=true;
gridData22.verticalAlignment=GridData.CENTER;
GridLayout gridLayout3=new GridLayout();
gridLayout3.numColumns=4;
GridData gridData21=new GridData();
gridData21.verticalAlignment=GridData.CENTER;
gridData21.verticalSpan=3;
gridData21.horizontalAlignment=GridData.CENTER;
groupDarkness=new Group(this, SWT.NONE);
groupDarkness.setLayoutData(gridData21);
groupDarkness.setLayout(gridLayout3);
groupDarkness.setText(Messages.getString("CompositePrinterSettings.67"));
textDarkness=new Text(groupDarkness, SWT.BORDER);
textDarkness.setText("-200");
textDarkness.setLayoutData(gridData24);
filler6=new Label(groupDarkness, SWT.NONE);
filler6.setText("");
filler6.setLayoutData(gridData25);
buttonDarkness=new Button(groupDarkness, SWT.NONE);
buttonDarkness.setText(Messages.getString("General.0"));
buttonDarkness.setLayoutData(gridData22);
textDarkness.addModifyListener(new org.eclipse.swt.events.ModifyListener()
{
public void modifyText(org.eclipse.swt.events.ModifyEvent e)
{
darknessTextModified();
}
});
buttonDarkness
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyDarkness();
}
});
sliderDarkness=new Slider(groupDarkness, SWT.HORIZONTAL);
sliderDarkness.setMaximum(1023);
sliderDarkness.setMinimum(0);
sliderDarkness.setIncrement(50);
sliderDarkness.setSelection(0);
sliderDarkness.setLayoutData(gridData23);
sliderDarkness
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
darknessSliderModified();
}
});
}
private void refreshDarkness()
{
String darkness=PrinterHelper.getVariable("DARKNESS");
textDarkness.setText(darkness);
sliderDarkness.setSelection(Integer.parseInt(darkness)+200);
}
private void applyDarkness()
{
PrinterHelper.setVariable("DARKNESS", textDarkness.getText());
refreshDarkness();
}
private void darknessTextModified()
{
try
{
sliderDarkness.setSelection(Integer.parseInt(textDarkness.getText())+200);
}
catch(NumberFormatException e)
{
sliderDarkness.setSelection(sliderDarkness.getMinimum());
}
}
private void darknessSliderModified()
{
textDarkness.setText(Integer.toString(sliderDarkness.getSelection()-200));
}
}
