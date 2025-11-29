public class CompositeLabelPositioning extends Composite
{
private boolean connected=false;
private boolean firstPaintSinceConnected=true;
private boolean dumbTerminal=true;
private Logger logger=null;
private Label labelPrintWidth=null;
private Text textPrintWidth=null;
private Button buttonPrintWidth=null;
private Button checkBoxAutoRecalibration=null;
private Button buttonAutoRecalibration=null;
private Button checkBoxAutoTOF=null;
private Button buttonAutoTOF=null;
private Label separator1=null;
private Label separator2=null;
private Label filler3=null;
private Label separator=null;
private Group groupIndexing=null;
private Group groupLabelPresentation=null;
private Button checkBoxIndexingOn=null;
private Button radioButtonFeedBar=null;
private Button radioButtonFeedGap=null;
private Button radioButtonFeedNotch=null;
private Label filler44=null;
private Button buttonIndexing=null;
private Button buttonCalibrate=null;
private Label filler42=null;
private Label filler43=null;
private Label filler47=null;
private Label filler63=null;
private Button checkBoxLabelPresentation=null;
private Text textAdvance=null;
private Text textRetract=null;
private Text textTime=null;
private Label labelAdvance=null;
private Label labelRetract=null;
private Label labelTime=null;
private Button buttonLabelPresentation=null;
private Label filler70=null;
private Label filler72=null;
private Label filler75=null;
private Label filler82=null;
private Composite compositeTOF_SL=null;
private Label filler6=null;
private Label labelShiftLeftArrow=null;
private Label labelShiftLeftTip=null;
private Label labelShiftLeft=null;
private Label labelPrinterImage=null;
private Composite compositeTOFOnly=null;
private Label labelTOF=null;
private Text textTOF=null;
private Button buttonTOF=null;
private Label labelTOFTip=null;
private Label labelTOFArrow=null;
private Label filler13=null;
private Label filler20=null;
private Label separator4=null;
private Label filler30=null;
private Label labelCalibrate=null;
private Composite compositeButtons=null;
private Button buttonRefresh=null;
private Button buttonPrint=null;
private Label filler15=null;
private Label filler17=null;
private Label filler22=null;
private Composite compositeShiftLeft=null;
private Text textShiftLeft=null;
private Button buttonShiftLeft=null;
public CompositeLabelPositioning(Composite parent, int style)
{
super(parent, style);
initialize();
}
private void initialize()
{
GridData gridData110=new GridData();
gridData110.horizontalAlignment=GridData.FILL;
gridData110.verticalAlignment=GridData.CENTER;
GridData gridData158=new GridData();
gridData158.verticalAlignment=GridData.CENTER;
gridData158.horizontalSpan=4;
gridData158.horizontalAlignment=GridData.FILL;
GridData gridData151=new GridData();
gridData151.grabExcessVerticalSpace=true;
GridData gridData150=new GridData();
gridData150.grabExcessVerticalSpace=true;
Label filler7=new Label(this, SWT.NONE);
GridData gridData146=new GridData();
gridData146.grabExcessHorizontalSpace=true;
GridData gridData39=new GridData();
gridData39.horizontalAlignment=GridData.FILL;
gridData39.horizontalSpan=4;
gridData39.verticalAlignment=GridData.CENTER;
GridData gridData62=new GridData();
gridData62.horizontalAlignment=GridData.CENTER;
gridData62.grabExcessHorizontalSpace=true;
gridData62.verticalAlignment=GridData.CENTER;
GridData gridData31=new GridData();
gridData31.horizontalAlignment=GridData.CENTER;
gridData31.verticalSpan=9;
gridData31.widthHint=5;
gridData31.grabExcessHorizontalSpace=true;
gridData31.verticalAlignment=GridData.FILL;
GridData gridData27=new GridData();
gridData27.verticalAlignment=GridData.CENTER;
gridData27.horizontalSpan=4;
gridData27.horizontalAlignment=GridData.FILL;
GridData gridData1=new GridData();
gridData1.horizontalSpan=3;
GridData gridData=new GridData();
gridData.horizontalSpan=3;
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=8;
Label filler8=new Label(this, SWT.NONE);
Label filler107=new Label(this, SWT.NONE);
Label filler9=new Label(this, SWT.NONE);
Label filler11=new Label(this, SWT.NONE);
Label filler12=new Label(this, SWT.NONE);
filler13=new Label(this, SWT.NONE);
filler13.setText("");
filler13.setLayoutData(gridData150);
Label filler14=new Label(this, SWT.NONE);
filler3=new Label(this, SWT.NONE);
filler3.setText("");
filler3.setLayoutData(gridData62);
Label filler101=new Label(this, SWT.NONE);
Label filler106=new Label(this, SWT.NONE);
Label filler102=new Label(this, SWT.NONE);
Label filler103=new Label(this, SWT.NONE);
separator2=new Label(this, SWT.SEPARATOR);
separator2.setText("");
separator2.setLayoutData(gridData31);
createCompositeTOF_SL();
filler6=new Label(this, SWT.NONE);
filler6.setText("");
filler6.setLayoutData(gridData146);
Label filler360=new Label(this, SWT.NONE);
labelPrintWidth=new Label(this, SWT.NONE);
labelPrintWidth.setText(Messages.getString("CompositeLabelPositioning.0"));
Label filler105=new Label(this, SWT.NONE);
textPrintWidth=new Text(this, SWT.BORDER);
textPrintWidth.setLayoutData(gridData110);
buttonPrintWidth=new Button(this, SWT.NONE);
buttonPrintWidth.setText(Messages.getString("General.0"));
buttonPrintWidth
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyPrintWidth();
}
});
Label filler5=new Label(this, SWT.NONE);
Label filler359=new Label(this, SWT.NONE);
separator1=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator1.setText("");
separator1.setLayoutData(gridData27);
Label filler4=new Label(this, SWT.NONE);
Label filler29=new Label(this, SWT.NONE);
checkBoxAutoRecalibration=new Button(this, SWT.CHECK);
checkBoxAutoRecalibration.setText(Messages.getString("CompositeLabelPositioning.5"));
checkBoxAutoRecalibration.setLayoutData(gridData);
buttonAutoRecalibration=new Button(this, SWT.NONE);
buttonAutoRecalibration.setText(Messages.getString("General.0"));
buttonAutoRecalibration
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyAutoRecalibration();
}
});
Label filler2=new Label(this, SWT.NONE);
Label filler358=new Label(this, SWT.NONE);
separator=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator.setText("Label");
separator.setLayoutData(gridData39);
Label filler1=new Label(this, SWT.NONE);
Label filler21153=new Label(this, SWT.NONE);
checkBoxAutoTOF=new Button(this, SWT.CHECK);
checkBoxAutoTOF.setText(Messages.getString("CompositeLabelPositioning.7"));
checkBoxAutoTOF.setLayoutData(gridData1);
buttonAutoTOF=new Button(this, SWT.NONE);
buttonAutoTOF.setText(Messages.getString("General.0"));
buttonAutoTOF
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyAutoTOF();
}
});
Label filler21156=new Label(this, SWT.NONE);
Label filler21=new Label(this, SWT.NONE);
separator4=new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
separator4.setText(Messages.getString("CompositeLabelPositioning.9"));
separator4.setLayoutData(gridData158);
Label filler27=new Label(this, SWT.NONE);
Label filler4640=new Label(this, SWT.NONE);
createGroupIndexing();
Label filler=new Label(this, SWT.NONE);
filler30=new Label(this, SWT.NONE);
filler30.setText("");
createGroupLabelPresentation();
Label filler36=new Label(this, SWT.NONE);
this.setLayout(gridLayout);
this.setSize(new Point(794, 631));
Label filler10=new Label(this, SWT.NONE);
createCompositeButtons();
Label filler1567=new Label(this, SWT.NONE);
this.addPaintListener(new org.eclipse.swt.events.PaintListener()
{
public void paintControl(org.eclipse.swt.events.PaintEvent e)
{
if(connected&&firstPaintSinceConnected&&!dumbTerminal)
{
firstPaintSinceConnected=false;
labelCalibrate.setText("");
refresh();
}
}
});
Label filler16=new Label(this, SWT.NONE);
Label filler109=new Label(this, SWT.NONE);
Label filler108=new Label(this, SWT.NONE);
Label filler18=new Label(this, SWT.NONE);
Label filler19=new Label(this, SWT.NONE);
filler20=new Label(this, SWT.NONE);
filler20.setText("");
filler20.setLayoutData(gridData151);
}
private void createGroupIndexing()
{
GridData gridData30=new GridData();
gridData30.horizontalSpan=2;
GridData gridData13=new GridData();
gridData13.horizontalAlignment=GridData.CENTER;
gridData13.grabExcessVerticalSpace=true;
gridData13.verticalAlignment=GridData.CENTER;
GridData gridData12=new GridData();
gridData12.horizontalAlignment=GridData.CENTER;
gridData12.grabExcessVerticalSpace=true;
gridData12.verticalAlignment=GridData.CENTER;
GridData gridData11=new GridData();
gridData11.grabExcessHorizontalSpace=true;
gridData11.verticalAlignment=GridData.CENTER;
gridData11.horizontalAlignment=GridData.CENTER;
GridData gridData10=new GridData();
gridData10.horizontalAlignment=GridData.BEGINNING;
gridData10.grabExcessHorizontalSpace=true;
gridData10.verticalAlignment=GridData.CENTER;
GridData gridData9=new GridData();
gridData9.horizontalAlignment=GridData.FILL;
gridData9.verticalAlignment=GridData.CENTER;
GridData gridData8=new GridData();
gridData8.horizontalAlignment=GridData.FILL;
gridData8.verticalAlignment=GridData.CENTER;
GridData gridData7=new GridData();
gridData7.horizontalIndent=10;
GridData gridData6=new GridData();
gridData6.horizontalIndent=10;
GridData gridData5=new GridData();
gridData5.horizontalIndent=10;
GridLayout gridLayout1=new GridLayout();
gridLayout1.numColumns=4;
GridData gridData3=new GridData();
gridData3.horizontalAlignment=GridData.FILL;
gridData3.horizontalSpan=4;
gridData3.verticalAlignment=GridData.FILL;
groupIndexing=new Group(this, SWT.NONE);
groupIndexing.setLayoutData(gridData3);
groupIndexing.setLayout(gridLayout1);
groupIndexing.setText(Messages.getString("CompositeLabelPositioning.10"));
Label filler4763=new Label(groupIndexing, SWT.NONE);
filler47=new Label(groupIndexing, SWT.NONE);
filler47.setText("");
filler47.setLayoutData(gridData12);
Label filler58=new Label(groupIndexing, SWT.NONE);
Label filler59=new Label(groupIndexing, SWT.NONE);
Label filler4762=new Label(groupIndexing, SWT.NONE);
checkBoxIndexingOn=new Button(groupIndexing, SWT.CHECK);
checkBoxIndexingOn.setText(Messages.getString("CompositeLabelPositioning.11"));
checkBoxIndexingOn
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
labelCalibrate.setText("");
}
});
filler44=new Label(groupIndexing, SWT.NONE);
filler44.setText("");
Label filler4359=new Label(groupIndexing, SWT.NONE);
filler43=new Label(groupIndexing, SWT.NONE);
filler43.setText("");
filler43.setLayoutData(gridData11);
radioButtonFeedBar=new Button(groupIndexing, SWT.RADIO);
radioButtonFeedBar.setText(Messages.getString("CompositeLabelPositioning.12"));
radioButtonFeedBar.setLayoutData(gridData5);
radioButtonFeedBar
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
labelCalibrate.setText("");
}
});
buttonIndexing=new Button(groupIndexing, SWT.NONE);
buttonIndexing.setText(Messages.getString("General.0"));
buttonIndexing.setLayoutData(gridData8);
buttonIndexing
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
labelCalibrate.setText("");
applyIndexing();
}
});
filler42=new Label(groupIndexing, SWT.NONE);
filler42.setText("");
filler42.setLayoutData(gridData10);
Label filler4361=new Label(groupIndexing, SWT.NONE);
radioButtonFeedGap=new Button(groupIndexing, SWT.RADIO);
radioButtonFeedGap.setText(Messages.getString("CompositeLabelPositioning.14"));
radioButtonFeedGap.setLayoutData(gridData6);
radioButtonFeedGap
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
labelCalibrate.setText("");
}
});
buttonCalibrate=new Button(groupIndexing, SWT.NONE);
buttonCalibrate.setText(Messages.getString("CompositeLabelPositioning.15"));
buttonCalibrate.setLayoutData(gridData9);
buttonCalibrate
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
labelCalibrate.setText("");
calibrate();
}
});
Label filler4258=new Label(groupIndexing, SWT.NONE);
Label filler4360=new Label(groupIndexing, SWT.NONE);
radioButtonFeedNotch=new Button(groupIndexing, SWT.RADIO);
radioButtonFeedNotch.setText(Messages.getString("CompositeLabelPositioning.16"));
radioButtonFeedNotch.setLayoutData(gridData7);
radioButtonFeedNotch
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
labelCalibrate.setText("");
}
});
labelCalibrate=new Label(groupIndexing, SWT.NONE);
labelCalibrate.setText("");
labelCalibrate.setLayoutData(gridData30);
Label filler62=new Label(groupIndexing, SWT.NONE);
filler63=new Label(groupIndexing, SWT.NONE);
filler63.setText("");
filler63.setLayoutData(gridData13);
}
private void createGroupLabelPresentation()
{
GridData gridData40=new GridData();
gridData40.horizontalAlignment=GridData.FILL;
gridData40.verticalAlignment=GridData.CENTER;
GridData gridData38=new GridData();
gridData38.horizontalAlignment=GridData.FILL;
gridData38.verticalAlignment=GridData.CENTER;
GridData gridData36=new GridData();
gridData36.horizontalAlignment=GridData.FILL;
gridData36.verticalAlignment=GridData.CENTER;
GridData gridData22=new GridData();
gridData22.horizontalSpan=2;
GridData gridData21=new GridData();
gridData21.horizontalAlignment=GridData.CENTER;
gridData21.grabExcessVerticalSpace=true;
gridData21.verticalAlignment=GridData.CENTER;
GridData gridData20=new GridData();
gridData20.grabExcessVerticalSpace=true;
gridData20.verticalAlignment=GridData.CENTER;
gridData20.horizontalAlignment=GridData.CENTER;
GridData gridData19=new GridData();
gridData19.grabExcessHorizontalSpace=true;
gridData19.verticalAlignment=GridData.CENTER;
gridData19.horizontalAlignment=GridData.CENTER;
GridData gridData18=new GridData();
gridData18.horizontalAlignment=GridData.CENTER;
gridData18.grabExcessHorizontalSpace=true;
gridData18.verticalAlignment=GridData.CENTER;
GridData gridData17=new GridData();
gridData17.horizontalAlignment=GridData.END;
gridData17.verticalAlignment=GridData.CENTER;
GridData gridData16=new GridData();
gridData16.horizontalAlignment=GridData.END;
gridData16.verticalAlignment=GridData.CENTER;
GridData gridData14=new GridData();
gridData14.horizontalAlignment=GridData.END;
gridData14.verticalAlignment=GridData.CENTER;
GridLayout gridLayout2=new GridLayout();
gridLayout2.numColumns=5;
GridData gridData4=new GridData();
gridData4.verticalAlignment=GridData.FILL;
gridData4.horizontalSpan=4;
gridData4.horizontalAlignment=GridData.FILL;
groupLabelPresentation=new Group(this, SWT.NONE);
groupLabelPresentation.setText(Messages.getString("CompositeLabelPositioning.18"));
groupLabelPresentation.setLayout(gridLayout2);
groupLabelPresentation.setLayoutData(gridData4);
Label filler74=new Label(groupLabelPresentation, SWT.NONE);
filler75=new Label(groupLabelPresentation, SWT.NONE);
filler75.setText("");
filler75.setLayoutData(gridData20);
Label filler76=new Label(groupLabelPresentation, SWT.NONE);
Label filler77=new Label(groupLabelPresentation, SWT.NONE);
Label filler78=new Label(groupLabelPresentation, SWT.NONE);
Label filler73=new Label(groupLabelPresentation, SWT.NONE);
checkBoxLabelPresentation=new Button(groupLabelPresentation, SWT.CHECK);
checkBoxLabelPresentation.setText(Messages.getString("CompositeLabelPositioning.19"));
checkBoxLabelPresentation.setLayoutData(gridData22);
Label filler6565=new Label(groupLabelPresentation, SWT.NONE);
Label filler67=new Label(groupLabelPresentation, SWT.NONE);
filler72=new Label(groupLabelPresentation, SWT.NONE);
filler72.setText("");
filler72.setLayoutData(gridData19);
labelAdvance=new Label(groupLabelPresentation, SWT.NONE);
labelAdvance.setText(Messages.getString("CompositeLabelPositioning.20"));
labelAdvance.setLayoutData(gridData17);
textAdvance=new Text(groupLabelPresentation, SWT.BORDER);
textAdvance.setLayoutData(gridData36);
buttonLabelPresentation=new Button(groupLabelPresentation, SWT.NONE);
buttonLabelPresentation.setText(Messages.getString("General.0"));
buttonLabelPresentation
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyLabelPresentation();
}
});
filler70=new Label(groupLabelPresentation, SWT.NONE);
filler70.setText("");
filler70.setLayoutData(gridData18);
Label filler71=new Label(groupLabelPresentation, SWT.NONE);
labelRetract=new Label(groupLabelPresentation, SWT.NONE);
labelRetract.setText(Messages.getString("CompositeLabelPositioning.22"));
labelRetract.setLayoutData(gridData16);
textRetract=new Text(groupLabelPresentation, SWT.BORDER);
textRetract.setLayoutData(gridData38);
Label filler68=new Label(groupLabelPresentation, SWT.NONE);
Label filler64=new Label(groupLabelPresentation, SWT.NONE);
Label filler66=new Label(groupLabelPresentation, SWT.NONE);
labelTime=new Label(groupLabelPresentation, SWT.NONE);
labelTime.setText(Messages.getString("CompositeLabelPositioning.23"));
labelTime.setLayoutData(gridData14);
textTime=new Text(groupLabelPresentation, SWT.BORDER);
textTime.setLayoutData(gridData40);
Label filler79=new Label(groupLabelPresentation, SWT.NONE);
Label filler80=new Label(groupLabelPresentation, SWT.NONE);
Label filler81=new Label(groupLabelPresentation, SWT.NONE);
filler82=new Label(groupLabelPresentation, SWT.NONE);
filler82.setText("");
filler82.setLayoutData(gridData21);
}
private void createCompositeTOF_SL()
{
GridData gridData25=new GridData();
GridData gridData24=new GridData();
GridData gridData23=new GridData();
GridData gridData15=new GridData();
gridData15.horizontalSpan=2;
GridLayout gridLayout3=new GridLayout();
gridLayout3.numColumns=2;
GridData gridData2=new GridData();
gridData2.verticalSpan=9;
gridData2.verticalAlignment=GridData.FILL;
gridData2.horizontalAlignment=GridData.FILL;
compositeTOF_SL=new Composite(this, SWT.NONE);
compositeTOF_SL.setLayoutData(gridData2);
compositeTOF_SL.setLayout(gridLayout3);
labelPrinterImage=new Label(compositeTOF_SL, SWT.NONE);
labelPrinterImage.setText("Label");
labelPrinterImage.setLayoutData(gridData25);
labelPrinterImage.setImage(new Image(Display.getCurrent(), GUIHelper.myShell.getClass().getClassLoader().getResourceAsStream("com/cognitive/brand/LabelPositioningImage.jpg")));
createCompositeTOFOnly();
labelShiftLeftArrow=new Label(compositeTOF_SL, SWT.NONE);
labelShiftLeftArrow.setText("Arrow");
labelShiftLeftArrow.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/com/cognitive/admin/right-arrow.png")));
labelShiftLeftArrow.setLayoutData(gridData24);
Label filler23=new Label(compositeTOF_SL, SWT.NONE);
labelShiftLeftTip=new Label(compositeTOF_SL, SWT.NONE);
labelShiftLeftTip.setText(Messages.getString("CompositeLabelPositioning.24"));
labelShiftLeftTip.setLayoutData(gridData15);
labelShiftLeft=new Label(compositeTOF_SL, SWT.NONE);
labelShiftLeft.setText(Messages.getString("CompositeLabelPositioning.25"));
labelShiftLeft.setLayoutData(gridData23);
Label filler24=new Label(compositeTOF_SL, SWT.NONE);
createCompositeShiftLeft();
}
private void createCompositeTOFOnly()
{
GridData gridData41=new GridData();
gridData41.horizontalAlignment=GridData.FILL;
gridData41.grabExcessHorizontalSpace=true;
gridData41.verticalAlignment=GridData.CENTER;
GridData gridData29=new GridData();
gridData29.horizontalSpan=2;
GridData gridData28=new GridData();
gridData28.horizontalSpan=2;
GridLayout gridLayout4=new GridLayout();
gridLayout4.numColumns=2;
GridData gridData26=new GridData();
gridData26.horizontalAlignment=GridData.FILL;
gridData26.grabExcessHorizontalSpace=false;
gridData26.verticalAlignment=GridData.FILL;
compositeTOFOnly=new Composite(compositeTOF_SL, SWT.NONE);
compositeTOFOnly.setLayoutData(gridData26);
compositeTOFOnly.setLayout(gridLayout4);
labelTOF=new Label(compositeTOFOnly, SWT.NONE);
labelTOF.setText(Messages.getString("CompositeLabelPositioning.26"));
labelTOF.setLayoutData(gridData28);
textTOF=new Text(compositeTOFOnly, SWT.BORDER);
textTOF.setText("XXX");
textTOF.setLayoutData(gridData41);
buttonTOF=new Button(compositeTOFOnly, SWT.NONE);
buttonTOF.setText(Messages.getString("General.0"));
buttonTOF.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyTOF();
}
});
labelTOFTip=new Label(compositeTOFOnly, SWT.NONE);
labelTOFTip.setText(Messages.getString("CompositeLabelPositioning.28"));
labelTOFTip.setLayoutData(gridData29);
labelTOFArrow=new Label(compositeTOFOnly, SWT.NONE);
labelTOFArrow.setText("Arrow");
labelTOFArrow.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/com/cognitive/admin/up-arrow.png")));
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
private void refresh()
{
GUIHelper.blankify(this);
update();
refreshPrintWidth();
refreshAutoRecalibration();
refreshAutoTOF();
refreshIndexing();
refreshLabelPresentation();
refreshTOF();
refreshShiftLeft();
}
private void applyPrintWidth()
{
String width=textPrintWidth.getText();
float fWidth=Float.parseFloat(width)*100;
int iWidth=(int)fWidth;
PrinterHelper.setVariable("WIDTH", Integer.toString(iWidth));
refreshPrintWidth();
}
private void refreshPrintWidth()
{
String width=PrinterHelper.getVariable("WIDTH");
if(width==null)
{
return;
}
width=GUIHelper.getMatch(width, "\\d+");
float fWidth=Float.parseFloat(width)/100;
textPrintWidth.setText(Float.toString(fWidth));
}
private void applyAutoRecalibration()
{
GUIHelper.applySimpleCheckBox(checkBoxAutoRecalibration, "RECALIBRATE");
}
private void refreshAutoRecalibration()
{
GUIHelper.refreshSimpleCheckBox(checkBoxAutoRecalibration, "RECALIBRATE");
}
private void applyAutoTOF()
{
GUIHelper.applySimpleCheckBox(checkBoxAutoTOF, "AUTO_TOF");
}
private void refreshAutoTOF()
{
GUIHelper.refreshSimpleCheckBox(checkBoxAutoTOF, "AUTO_TOF");
}
private void applyIndexing()
{
String feedType;
if(radioButtonFeedBar.getSelection())
{
feedType="BAR";
}
else if(radioButtonFeedGap.getSelection())
{
feedType="GAP";
}
else if(radioButtonFeedNotch.getSelection())
{
feedType="NOTCH";
}
else
{
GUIHelper.error(Messages.getString("CompositeLabelPositioning.29"));
logger.error("Invalid feed-type selected.");
return;
}
PrinterHelper.setVariable("FEED_TYPE", feedType);
GUIHelper.applySimpleCheckBox(checkBoxIndexingOn, "INDEX");
}
private void refreshIndexing()
{
GUIHelper.refreshSimpleCheckBox(checkBoxIndexingOn, "INDEX");
labelCalibrate.setText("");
String feedType=PrinterHelper.getVariable("FEED_TYPE");
if(feedType==null)
{
return;
}
if(feedType.equalsIgnoreCase("BAR"))
{
radioButtonFeedBar.setSelection(true);
radioButtonFeedGap.setSelection(false);
radioButtonFeedNotch.setSelection(false);
}
else if(feedType.equalsIgnoreCase("GAP"))
{
radioButtonFeedBar.setSelection(false);
radioButtonFeedGap.setSelection(true);
radioButtonFeedNotch.setSelection(false);
}
else if(feedType.equalsIgnoreCase("NOTCH"))
{
radioButtonFeedBar.setSelection(false);
radioButtonFeedGap.setSelection(false);
radioButtonFeedNotch.setSelection(true);
}
else
{
GUIHelper.error(Messages.getString("CompositeLabelPositioning.31")+feedType);
logger.error("Received invalid feed type from printer:"+feedType);
return;
}
}
private void calibrate()
{
logger.trace("");
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nV INDEX SETTING CALIBRATE\nEND");
boolean received=PrinterHelper.waitFor(".*C3P.*", 60);
if(received)
{
labelCalibrate.setText(Messages.getString("CompositeLabelPositioning.32"));
labelCalibrate.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
}
else
{
labelCalibrate.setText(Messages.getString("CompositeLabelPositioning.33"));
labelCalibrate.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
}
groupIndexing.layout(true);
}
private void applyLabelPresentation()
{
String onOff;
if(checkBoxLabelPresentation.getSelection())
{
onOff="ON";
}
else
{
onOff="OFF";
}
String value=onOff+" "+textAdvance.getText()+" "+textRetract.getText()+" "+textTime.getText();
PrinterHelper.setVariable("PRESENTLABEL", value);
refreshLabelPresentation();
}
private void refreshLabelPresentation()
{
String result=PrinterHelper.getVariable("PRESENTLABEL");
if(result==null)
{
return;
}
String[] params=result.split(", ");
checkBoxLabelPresentation.setSelection(params[0].equals("ON"));
textAdvance.setText(params[1]);
textRetract.setText(params[2]);
textTime.setText(params[3]);
}
private void applyTOF()
{
GUIHelper.applySimpleTextField(textTOF, "TOF");
PrinterHelper.printTestLabel(textTOF.getText(), textShiftLeft.getText(), "");
}
private void refreshTOF()
{
GUIHelper.refreshSimpleTextField(textTOF, "TOF");
}
private void applyShiftLeft()
{
GUIHelper.applySimpleTextField(textShiftLeft, "SHIFT LEFT");
PrinterHelper.printTestLabel(textTOF.getText(), textShiftLeft.getText(), "");
}
private void refreshShiftLeft()
{
GUIHelper.refreshSimpleTextField(textShiftLeft, "SHIFT LEFT");
}
private void createCompositeButtons()
{
GridData gridData35=new GridData();
gridData35.horizontalAlignment=GridData.BEGINNING;
gridData35.grabExcessHorizontalSpace=true;
gridData35.verticalAlignment=GridData.CENTER;
GridData gridData34=new GridData();
gridData34.horizontalAlignment=GridData.BEGINNING;
gridData34.grabExcessHorizontalSpace=true;
gridData34.verticalAlignment=GridData.CENTER;
GridData gridData33=new GridData();
gridData33.horizontalAlignment=GridData.BEGINNING;
gridData33.grabExcessHorizontalSpace=true;
gridData33.verticalAlignment=GridData.CENTER;
GridLayout gridLayout5=new GridLayout();
gridLayout5.numColumns=5;
GridData gridData32=new GridData();
gridData32.horizontalAlignment=GridData.FILL;
gridData32.horizontalSpan=6;
gridData32.verticalAlignment=GridData.CENTER;
compositeButtons=new Composite(this, SWT.NONE);
compositeButtons.setLayoutData(gridData32);
compositeButtons.setLayout(gridLayout5);
filler22=new Label(compositeButtons, SWT.NONE);
filler22.setText("");
filler22.setLayoutData(gridData33);
buttonRefresh=new Button(compositeButtons, SWT.NONE);
buttonRefresh.setText(Messages.getString("General.1"));
filler17=new Label(compositeButtons, SWT.NONE);
filler17.setText("");
filler17.setLayoutData(gridData34);
buttonRefresh.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
refresh();
}
});
buttonPrint=new Button(compositeButtons, SWT.NONE);
buttonPrint.setText(Messages.getString("CompositeLabelPositioning.35"));
filler15=new Label(compositeButtons, SWT.NONE);
filler15.setText("");
filler15.setLayoutData(gridData35);
buttonPrint.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
PrinterHelper.printTestLabel(textTOF.getText(), textShiftLeft.getText(), "");
}
});
}
private void createCompositeShiftLeft()
{
GridData gridData42=new GridData();
gridData42.horizontalAlignment=GridData.FILL;
gridData42.grabExcessHorizontalSpace=true;
gridData42.verticalAlignment=GridData.CENTER;
GridLayout gridLayout6=new GridLayout();
gridLayout6.numColumns=2;
GridData gridData37=new GridData();
gridData37.horizontalAlignment=GridData.FILL;
gridData37.verticalAlignment=GridData.CENTER;
compositeShiftLeft=new Composite(compositeTOF_SL, SWT.NONE);
compositeShiftLeft.setLayoutData(gridData37);
compositeShiftLeft.setLayout(gridLayout6);
textShiftLeft=new Text(compositeShiftLeft, SWT.BORDER);
textShiftLeft.setText("XXX");
textShiftLeft.setLayoutData(gridData42);
buttonShiftLeft=new Button(compositeShiftLeft, SWT.NONE);
buttonShiftLeft.setText(Messages.getString("General.0"));
buttonShiftLeft
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
applyShiftLeft();
}
});
}
}
