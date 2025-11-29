public class CompositeManufacturing extends Composite
{
private boolean firstPaint=true;
private Label labelFirmwareLoad=null;
private Label labelRTCSetup=null;
private Label labelToneCheck=null;
private Label labelFirmwareLoadStatus=null;
private Label labelRTCSetupStatus=null;
private Label labelToneCheckStatus=null;
static ProgressBar progressBarFirmwareDownload=null;
private Label labelPrinterIdentification=null;
private Label labelPrinterIdentificationStatus=null;
private Label labelCPRDownload=null;
private Label labelCPRDownloadStatus=null;
private Label labelFontLoading=null;
private Label labelFontLoadingStatus=null;
private Label labelCalibration=null;
private Label labelCalibrationStatus=null;
private Label labelPrintHeadUpDown=null;
private Label labelPrintHeadUpDownStatus=null;
private Label labelPrintQuality=null;
private Label labelPrintQualityStatus=null;
private Label labelPrintHead=null;
private Label labelPrintHeadStatus=null;
private Label labelSelfTest=null;
private Label labelSelfTestStatus=null;
private Composite compositeManufacturingSettings=null;
static Text textModelNumber=null;
private Button checkBoxFirmwareAsk=null;
static Text textSerialPort=null;
static Text textParallelPort=null;
static Text textIPAddress=null;
static Text textWindowsPrinterName=null;
static Button checkBoxSerial=null;
static Button checkBoxNetwork=null;
static Button checkBoxUSBB=null;
static Button checkBoxUSBA=null;
static Button checkBoxParallel=null;
private Label labelUUT=null;
private Label labelSeparator=null;
static Button checkBoxScanner=null;
private Label labelPeripherals=null;
static Text textScanner=null;
static Button checkBoxSNLabelPrinter=null;
static Button checkBoxBoxLabelPrinter=null;
static Text textSNLabelPrinter=null;
static Text textBoxLabelPrinter=null;
private Label labelSeparator2=null;
private Label labelFiller=null;
private Label labelFiller1=null;
private Label labelFiller5=null;
static Combo comboPrintHead=null;
private Label labelPrintHeadSelect=null;
private Label labelInterfaceTest=null;
private Label labelInterfaceTestStatus=null;
private Label labelInitialSetup=null;
private Label labelInitialSetupStatus=null;
private Label labelBackupMemory=null;
private Label labelBackupMemoryStatus=null;
private Label labelShippingLabel=null;
private Label labelShippingLabelStatus=null;
private Label labelSerialNumber=null;
private Label labelMacAddress=null;
private Label labelPeeler=null;
private Label labelPeelerStatus=null;
private Label labelFiller2=null;
private Label labelFinalSettings=null;
private Label labelFinalSettingsStatus=null;
private Label labelControlPanel=null;
private Label labelControlPanelStatus=null;
private Label labelRibbonWrinkle=null;
private Label labelRibbonWrinkleStatus=null;
private Composite compositeShippingLabels=null;
private Button buttonSNLabel=null;
private Button buttonBoxLabel=null;
static Text textSerialNumberActual=null;
static Text textMacAddressActual=null;
static Button checkBoxSpecSNMAC=null;
private Button buttonPCL=null;
private Button checkBoxBoardTest=null;
private Composite compositeModelNumber=null;
static Label labelModelNumber=null;
static Button buttonScanModelNumber=null;
private Composite compositeStart=null;
private Button buttonStart=null;
static Button checkBoxTG=null;
static Button checkBoxSkipLoad1=null;
static Button checkBoxSkipLoad2=null;
static Button checkBoxSkipLoad3=null;
private Label labelFeedButton=null;
private Label labelFeedButtonStatus=null;
static Button checkBoxDisableLicenses=null;
private Button buttonAbort=null;
private Button buttonRunNext=null;
private Button buttonRerun=null;
private Button checkBoxAutorun=null;
private static TestStep testStep=null;
static Button checkBoxBT=null;
static Button checkBoxBT1=null;
static Text textBTPort1=null;
public CompositeManufacturing(Composite parent, int style)
{
super(parent, style);
initialize();
}
private void initialize()
{
GridData gridData27=new GridData();
gridData27.horizontalAlignment=GridData.FILL;
gridData27.verticalAlignment=GridData.CENTER;
GridData gridData22=new GridData();
gridData22.horizontalAlignment=GridData.FILL;
gridData22.verticalAlignment=GridData.CENTER;
GridData gridData171=new GridData();
gridData171.grabExcessVerticalSpace=true;
gridData171.grabExcessHorizontalSpace=true;
Label filler113=new Label(this, SWT.NONE);
GridData gridData19=new GridData();
gridData19.horizontalAlignment=GridData.FILL;
gridData19.verticalAlignment=GridData.CENTER;
GridData gridData161=new GridData();
gridData161.horizontalAlignment=GridData.FILL;
gridData161.verticalAlignment=GridData.CENTER;
GridData gridData151=new GridData();
gridData151.horizontalAlignment=GridData.FILL;
gridData151.verticalAlignment=GridData.CENTER;
GridData gridData141=new GridData();
gridData141.horizontalAlignment=GridData.FILL;
gridData141.verticalAlignment=GridData.CENTER;
GridData gridData131=new GridData();
gridData131.horizontalAlignment=GridData.FILL;
gridData131.verticalAlignment=GridData.CENTER;
GridData gridData122=new GridData();
gridData122.horizontalAlignment=GridData.FILL;
gridData122.verticalAlignment=GridData.CENTER;
GridData gridData111=new GridData();
gridData111.horizontalAlignment=GridData.FILL;
gridData111.verticalAlignment=GridData.CENTER;
GridData gridData101=new GridData();
gridData101.horizontalAlignment=GridData.FILL;
gridData101.verticalAlignment=GridData.CENTER;
GridData gridData91=new GridData();
gridData91.horizontalAlignment=GridData.FILL;
gridData91.verticalAlignment=GridData.CENTER;
GridData gridData82=new GridData();
gridData82.horizontalAlignment=GridData.FILL;
gridData82.verticalAlignment=GridData.CENTER;
GridData gridData61=new GridData();
gridData61.horizontalAlignment=GridData.FILL;
gridData61.verticalAlignment=GridData.CENTER;
GridData gridData51=new GridData();
gridData51.horizontalAlignment=GridData.FILL;
gridData51.verticalAlignment=GridData.CENTER;
GridData gridData41=new GridData();
gridData41.horizontalAlignment=GridData.FILL;
gridData41.verticalAlignment=GridData.CENTER;
GridData gridData31=new GridData();
gridData31.horizontalAlignment=GridData.FILL;
gridData31.verticalAlignment=GridData.CENTER;
GridData gridData21=new GridData();
gridData21.horizontalAlignment=GridData.FILL;
gridData21.verticalAlignment=GridData.CENTER;
this.setSize(new Point(662, 665));
GridData gridData120=new GridData();
gridData120.grabExcessVerticalSpace=true;
gridData120.grabExcessHorizontalSpace=true;
GridData gridData81=new GridData();
gridData81.verticalAlignment=GridData.CENTER;
gridData81.horizontalSpan=2;
gridData81.horizontalAlignment=GridData.FILL;
GridData gridData80=new GridData();
gridData80.grabExcessHorizontalSpace=true;
GridData gridData79=new GridData();
gridData79.grabExcessHorizontalSpace=true;
GridData gridData78=new GridData();
gridData78.verticalSpan=23;
gridData78.verticalAlignment=GridData.FILL;
gridData78.horizontalAlignment=GridData.FILL;
GridData gridData3=new GridData();
gridData3.verticalAlignment=GridData.CENTER;
gridData3.horizontalAlignment=GridData.FILL;
GridLayout gridLayout1=new GridLayout();
gridLayout1.numColumns=8;
gridLayout1.verticalSpacing=10;
gridLayout1.marginWidth=10;
gridLayout1.marginHeight=10;
gridLayout1.horizontalSpacing=15;
Label filler121=new Label(this, SWT.NONE);
Label filler13=new Label(this, SWT.NONE);
Label filler14=new Label(this, SWT.NONE);
Label filler151=new Label(this, SWT.NONE);
Label filler161=new Label(this, SWT.NONE);
Label filler371=new Label(this, SWT.NONE);
labelFiller2=new Label(this, SWT.NONE);
labelFiller2.setText("Label");
labelFiller2.setVisible(false);
labelFiller2.setLayoutData(gridData171);
Label filler112=new Label(this, SWT.NONE);
createCompositeManufacturingSettings();
labelFiller1=new Label(this, SWT.NONE);
labelFiller1.setText("");
labelFiller1.setLayoutData(gridData80);
labelSeparator2=new Label(this, SWT.SEPARATOR);
labelSeparator2.setText("Label");
labelSeparator2.setLayoutData(gridData78);
Label filler24=new Label(this, SWT.NONE);
createCompositeStart();
buttonPCL=new Button(this, SWT.NONE);
buttonPCL.setText(Messages.getString("CompositeManufacturing.1"));
buttonPCL.setVisible(true);
buttonPCL.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
initializeDisplay();
indicateRunning(labelFirmwareLoad);
TestStatus testStatus=TestEngine.enablePCL(true);
indicateDone(labelFirmwareLoad, labelFirmwareLoadStatus, testStatus);
}
});
buttonAbort=new Button(this, SWT.NONE);
buttonAbort.setText("Abort");
buttonAbort.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
abortTest();
}
});
buttonAbort.setVisible(false);
Label filler19=new Label(this, SWT.NONE);
Label filler22=new Label(this, SWT.NONE);
Label filler38=new Label(this, SWT.NONE);
labelInitialSetup=new Label(this, SWT.NONE);
labelInitialSetup.setText(Messages.getString("CompositeManufacturing.2"));
labelInitialSetup.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelInitialSetupStatus=new Label(this, SWT.NONE);
labelInitialSetupStatus.setVisible(false);
labelInitialSetupStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelInitialSetupStatus.setLayoutData(gridData31);
labelInitialSetupStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
Label filler351=new Label(this, SWT.NONE);
Label filler7=new Label(this, SWT.NONE);
Label filler9=new Label(this, SWT.NONE);
Label filler106=new Label(this, SWT.NONE);
labelFirmwareLoad=new Label(this, SWT.NONE);
labelFirmwareLoadStatus=new Label(this, SWT.NONE);
checkBoxSkipLoad1=new Button(this, SWT.CHECK);
checkBoxSkipLoad1.setText("Skip");
Label filler111=new Label(this, SWT.NONE);
Label filler37=new Label(this, SWT.NONE);
labelFiller=new Label(this, SWT.NONE);
labelFiller.setText("");
labelFiller.setLayoutData(gridData79);
labelFirmwareLoad.setText(Messages.getString("CompositeManufacturing.3"));
labelFirmwareLoad.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelFirmwareLoadStatus.setVisible(false);
labelFirmwareLoadStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelFirmwareLoadStatus.setLayoutData(gridData21);
labelFirmwareLoadStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
progressBarFirmwareDownload=new ProgressBar(this, SWT.INDETERMINATE);
progressBarFirmwareDownload.setVisible(false);
progressBarFirmwareDownload.setLayoutData(gridData81);
Label filler331=new Label(this, SWT.NONE);
Label filler65=new Label(this, SWT.NONE);
Label filler66=new Label(this, SWT.NONE);
Label filler1071=new Label(this, SWT.NONE);
labelPrinterIdentification=new Label(this, SWT.NONE);
labelPrinterIdentificationStatus=new Label(this, SWT.NONE);
Label filler32=new Label(this, SWT.NONE);
Label filler58=new Label(this, SWT.NONE);
Label filler59=new Label(this, SWT.NONE);
Label filler60=new Label(this, SWT.NONE);
labelFontLoading=new Label(this, SWT.NONE);
labelFontLoadingStatus=new Label(this, SWT.NONE);
checkBoxSkipLoad2=new Button(this, SWT.CHECK);
checkBoxSkipLoad2.setText("Skip");
Label filler110=new Label(this, SWT.NONE);
Label filler39=new Label(this, SWT.NONE);
Label filler40=new Label(this, SWT.NONE);
labelCPRDownload=new Label(this, SWT.NONE);
labelCPRDownloadStatus=new Label(this, SWT.NONE);
checkBoxSkipLoad3=new Button(this, SWT.CHECK);
checkBoxSkipLoad3.setText("Skip");
Label filler10=new Label(this, SWT.NONE);
Label filler28=new Label(this, SWT.NONE);
Label filler49=new Label(this, SWT.NONE);
labelControlPanel=new Label(this, SWT.NONE);
labelControlPanel.setText(Messages.getString("CompositeManufacturing.4"));
labelControlPanelStatus=new Label(this, SWT.NONE);
labelControlPanelStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelControlPanelStatus.setVisible(false);
labelControlPanelStatus.setLayoutData(gridData22);
Label filler55=new Label(this, SWT.NONE);
Label filler12=new Label(this, SWT.NONE);
Label filler25=new Label(this, SWT.NONE);
Label filler26=new Label(this, SWT.NONE);
labelInterfaceTest=new Label(this, SWT.NONE);
labelInterfaceTestStatus=new Label(this, SWT.NONE);
Label filler291=new Label(this, SWT.NONE);
Label filler47=new Label(this, SWT.NONE);
Label filler48=new Label(this, SWT.NONE);
Label filler53=new Label(this, SWT.NONE);
labelInterfaceTest.setText(Messages.getString("CompositeManufacturing.5"));
labelInterfaceTest.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelInterfaceTestStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelInterfaceTestStatus.setLayoutData(gridData61);
labelInterfaceTestStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelInterfaceTestStatus.setVisible(false);
labelPrintHeadUpDown=new Label(this, SWT.NONE);
labelPrintHeadUpDownStatus=new Label(this, SWT.NONE);
Label filler281=new Label(this, SWT.NONE);
Label filler2=new Label(this, SWT.NONE);
Label filler4=new Label(this, SWT.NONE);
Label filler6=new Label(this, SWT.NONE);
labelToneCheck=new Label(this, SWT.NONE);
labelToneCheckStatus=new Label(this, SWT.NONE);
Label filler271=new Label(this, SWT.NONE);
Label filler44=new Label(this, SWT.NONE);
Label filler45=new Label(this, SWT.NONE);
Label filler46=new Label(this, SWT.NONE);
labelRTCSetup=new Label(this, SWT.NONE);
labelRTCSetupStatus=new Label(this, SWT.NONE);
Label filler261=new Label(this, SWT.NONE);
Label filler50=new Label(this, SWT.NONE);
Label filler51=new Label(this, SWT.NONE);
Label filler52=new Label(this, SWT.NONE);
labelCalibration=new Label(this, SWT.NONE);
labelCalibrationStatus=new Label(this, SWT.NONE);
Label filler251=new Label(this, SWT.NONE);
Label filler109=new Label(this, SWT.NONE);
Label filler36=new Label(this, SWT.NONE);
Label filler23=new Label(this, SWT.NONE);
labelFeedButton=new Label(this, SWT.NONE);
labelFeedButton.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelFeedButton.setText(Messages.getString("CompositeManufacturing.51"));
labelFeedButtonStatus=new Label(this, SWT.NONE);
labelFeedButtonStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelFeedButtonStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelFeedButtonStatus.setLayoutData(gridData27);
labelFeedButtonStatus.setVisible(false);
Label filler241=new Label(this, SWT.NONE);
Label filler41=new Label(this, SWT.NONE);
Label filler42=new Label(this, SWT.NONE);
Label filler43=new Label(this, SWT.NONE);
labelPeeler=new Label(this, SWT.NONE);
labelPeeler.setText(Messages.getString("CompositeManufacturing.7"));
labelPeelerStatus=new Label(this, SWT.NONE);
labelPeelerStatus.setVisible(false);
labelPeelerStatus.setLayoutData(gridData19);
labelPeelerStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
Label filler231=new Label(this, SWT.NONE);
Label filler108=new Label(this, SWT.NONE);
Label filler35=new Label(this, SWT.NONE);
Label filler20=new Label(this, SWT.NONE);
labelRTCSetup.setText(Messages.getString("CompositeManufacturing.8"));
labelRTCSetup.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelRTCSetupStatus.setLayoutData(gridData101);
labelRTCSetupStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelRTCSetupStatus.setVisible(false);
labelRTCSetupStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelToneCheck.setText(Messages.getString("CompositeManufacturing.9"));
labelToneCheck.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelToneCheckStatus.setLayoutData(gridData91);
labelToneCheckStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelToneCheckStatus.setVisible(false);
labelToneCheckStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelPrintHead=new Label(this, SWT.NONE);
labelPrintHeadStatus=new Label(this, SWT.NONE);
Label filler221=new Label(this, SWT.NONE);
Label filler11=new Label(this, SWT.NONE);
Label filler15=new Label(this, SWT.NONE);
Label filler16=new Label(this, SWT.NONE);
labelBackupMemory=new Label(this, SWT.NONE);
labelBackupMemory.setText(Messages.getString("CompositeManufacturing.10"));
labelBackupMemoryStatus=new Label(this, SWT.NONE);
labelBackupMemoryStatus.setVisible(false);
labelBackupMemoryStatus.setLayoutData(gridData151);
labelBackupMemoryStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
Label filler211=new Label(this, SWT.NONE);
Label filler107=new Label(this, SWT.NONE);
Label filler34=new Label(this, SWT.NONE);
Label filler18=new Label(this, SWT.NONE);
labelPrinterIdentification.setText(Messages.getString("CompositeManufacturing.11"));
labelPrinterIdentification.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelPrinterIdentificationStatus.setLayoutData(gridData3);
labelPrinterIdentificationStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelPrinterIdentificationStatus.setVisible(false);
labelPrinterIdentificationStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelPrintQuality=new Label(this, SWT.NONE);
labelPrintQualityStatus=new Label(this, SWT.NONE);
Label filler201=new Label(this, SWT.NONE);
Label filler54=new Label(this, SWT.NONE);
Label filler56=new Label(this, SWT.NONE);
Label filler57=new Label(this, SWT.NONE);
labelRibbonWrinkle=new Label(this, SWT.NONE);
labelRibbonWrinkle.setText(Messages.getString("CompositeManufacturing.12"));
labelRibbonWrinkleStatus=new Label(this, SWT.NONE);
labelRibbonWrinkleStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelRibbonWrinkleStatus.setVisible(false);
Label filler62=new Label(this, SWT.NONE);
Label filler=new Label(this, SWT.NONE);
Label filler1=new Label(this, SWT.NONE);
Label filler8=new Label(this, SWT.NONE);
labelFinalSettings=new Label(this, SWT.NONE);
labelFinalSettings.setText(Messages.getString("CompositeManufacturing.13"));
labelFinalSettingsStatus=new Label(this, SWT.NONE);
labelFinalSettingsStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelFinalSettingsStatus.setVisible(false);
Label filler2811=new Label(this, SWT.NONE);
Label filler1081=new Label(this, SWT.NONE);
Label filler33=new Label(this, SWT.NONE);
Label filler17=new Label(this, SWT.NONE);
labelCPRDownload.setText(Messages.getString("CompositeManufacturing.14"));
labelCPRDownload.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelCPRDownloadStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelCPRDownloadStatus.setLayoutData(gridData51);
labelCPRDownloadStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelCPRDownloadStatus.setVisible(false);
labelSelfTest=new Label(this, SWT.NONE);
labelSelfTestStatus=new Label(this, SWT.NONE);
labelFontLoading.setText(Messages.getString("CompositeManufacturing.15"));
labelFontLoading.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelFontLoadingStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelFontLoadingStatus.setLayoutData(gridData41);
labelFontLoadingStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelFontLoadingStatus.setVisible(false);
labelCalibration.setText(Messages.getString("CompositeManufacturing.16"));
labelCalibration.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelCalibrationStatus.setLayoutData(gridData111);
labelCalibrationStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelCalibrationStatus.setVisible(false);
labelCalibrationStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelPrintHeadUpDown.setText(Messages.getString("CompositeManufacturing.17"));
labelPrintHeadUpDown.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelPrintHeadUpDownStatus.setLayoutData(gridData82);
labelPrintHeadUpDownStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelPrintHeadUpDownStatus.setVisible(false);
labelPrintHeadUpDownStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelPrintQuality.setText(Messages.getString("CompositeManufacturing.18"));
labelPrintQuality.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelPrintQualityStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelPrintQualityStatus.setLayoutData(gridData131);
labelPrintQualityStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelPrintQualityStatus.setVisible(false);
labelPrintHead.setText(Messages.getString("CompositeManufacturing.19"));
labelPrintHead.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelPrintHeadStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelPrintHeadStatus.setLayoutData(gridData122);
labelPrintHeadStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelPrintHeadStatus.setVisible(false);
labelSelfTest.setText(Messages.getString("CompositeManufacturing.20"));
labelSelfTest.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelSelfTestStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
labelSelfTestStatus.setLayoutData(gridData141);
labelSelfTestStatus.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.NORMAL));
labelSelfTestStatus.setVisible(false);
Label filler191=new Label(this, SWT.NONE);
Label filler71=new Label(this, SWT.NONE);
Label filler72=new Label(this, SWT.NONE);
Label filler73=new Label(this, SWT.NONE);
labelShippingLabel=new Label(this, SWT.NONE);
labelShippingLabel.setText(Messages.getString("CompositeManufacturing.21"));
labelShippingLabelStatus=new Label(this, SWT.NONE);
Label filler70=new Label(this, SWT.NONE);
Label filler21=new Label(this, SWT.NONE);
Label filler29=new Label(this, SWT.NONE);
Label filler31=new Label(this, SWT.NONE);
labelShippingLabelStatus.setVisible(false);
labelShippingLabelStatus.setLayoutData(gridData161);
labelShippingLabelStatus.setText(TestEngine.TestStatusString[TestStatus.CANCELLED.ordinal()]);
buttonRerun=new Button(this, SWT.NONE);
Label filler74=new Label(this, SWT.NONE);
Label filler181=new Label(this, SWT.NONE);
Label filler3=new Label(this, SWT.NONE);
Label filler5=new Label(this, SWT.NONE);
Label filler30=new Label(this, SWT.NONE);
Label filler61=new Label(this, SWT.NONE);
Label filler63=new Label(this, SWT.NONE);
buttonRerun.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
rerunTest();
}
});
buttonRerun.setText("Re-run");
buttonRerun.setVisible(true);
Label filler69=new Label(this, SWT.NONE);
Label filler67=new Label(this, SWT.NONE);
buttonRunNext=new Button(this, SWT.NONE);
buttonRunNext.setText("Run Next");
buttonRunNext
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
runNextTest();
}
});
buttonRunNext.setVisible(false);
labelFiller5=new Label(this, SWT.NONE);
labelFiller5.setText("Label");
labelFiller5.setLayoutData(gridData120);
labelFiller5.setVisible(false);
GridLayout gridLayout=new GridLayout();
this.setLayout(gridLayout1);
this.addPaintListener(new org.eclipse.swt.events.PaintListener()
{
public void paintControl(org.eclipse.swt.events.PaintEvent e)
{
if(firstPaint)
{
displayPreferences();
}
}
});
this.setSize(new Point(723, 622));
}
private void createCompositeManufacturingSettings()
{
GridData gridData30=new GridData();
gridData30.horizontalAlignment=GridData.FILL;
gridData30.verticalAlignment=GridData.CENTER;
GridData gridData24=new GridData();
gridData24.horizontalAlignment=GridData.BEGINNING;
gridData24.verticalAlignment=GridData.CENTER;
GridData gridData18=new GridData();
gridData18.horizontalAlignment=GridData.FILL;
gridData18.verticalAlignment=GridData.CENTER;
GridData gridData17=new GridData();
gridData17.horizontalAlignment=GridData.FILL;
gridData17.verticalAlignment=GridData.CENTER;
GridData gridData15=new GridData();
gridData15.horizontalAlignment=GridData.FILL;
gridData15.verticalAlignment=GridData.CENTER;
GridData gridData14=new GridData();
gridData14.horizontalAlignment=GridData.FILL;
gridData14.verticalAlignment=GridData.CENTER;
GridData gridData13=new GridData();
gridData13.horizontalAlignment=GridData.FILL;
gridData13.verticalAlignment=GridData.CENTER;
GridData gridData12=new GridData();
gridData12.horizontalSpan=2;
gridData12.verticalAlignment=GridData.CENTER;
gridData12.horizontalAlignment=GridData.CENTER;
GridData gridData11=new GridData();
gridData11.verticalAlignment=GridData.CENTER;
gridData11.horizontalSpan=2;
gridData11.horizontalAlignment=GridData.FILL;
GridData gridData10=new GridData();
gridData10.verticalAlignment=GridData.CENTER;
gridData10.horizontalAlignment=GridData.BEGINNING;
GridData gridData9=new GridData();
gridData9.horizontalAlignment=GridData.FILL;
gridData9.verticalAlignment=GridData.CENTER;
GridData gridData8=new GridData();
gridData8.horizontalAlignment=GridData.FILL;
gridData8.verticalAlignment=GridData.CENTER;
GridData gridData7=new GridData();
gridData7.horizontalAlignment=GridData.FILL;
gridData7.verticalAlignment=GridData.CENTER;
GridData gridData6=new GridData();
gridData6.horizontalAlignment=GridData.FILL;
gridData6.verticalAlignment=GridData.CENTER;
GridData gridData5=new GridData();
gridData5.horizontalAlignment=GridData.FILL;
gridData5.verticalAlignment=GridData.CENTER;
GridData gridData2=new GridData();
GridLayout gridLayout2=new GridLayout();
gridLayout2.numColumns=2;
gridLayout2.verticalSpacing=11;
gridLayout2.horizontalSpacing=12;
GridData gridData1=new GridData();
gridData1.horizontalAlignment=GridData.FILL;
gridData1.grabExcessVerticalSpace=false;
gridData1.grabExcessHorizontalSpace=false;
gridData1.verticalSpan=23;
gridData1.verticalAlignment=GridData.FILL;
compositeManufacturingSettings=new Composite(this, SWT.NONE);
compositeManufacturingSettings.setLayoutData(gridData1);
compositeManufacturingSettings.setLayout(gridLayout2);
labelUUT=new Label(compositeManufacturingSettings, SWT.NONE);
labelUUT.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.BOLD));
labelUUT.setText(Messages.getString("CompositeManufacturing.22"));
labelUUT.setLayoutData(gridData10);
Label filler64=new Label(compositeManufacturingSettings, SWT.NONE);
checkBoxBT=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxBT.setVisible(false);
checkBoxBT.setText(Messages.getString("CompositeManufacturing.56"));
checkBoxBT.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
displayBTText();
}
});
checkBoxBoardTest=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxBoardTest.setText(Messages.getString("CompositeManufacturing.23"));
checkBoxBoardTest.setLayoutData(gridData24);
checkBoxTG=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxTG.setText(Messages.getString("CompositeManufacturing.50"));
checkBoxTG.setVisible(true);
checkBoxDisableLicenses=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxDisableLicenses.setText(Messages.getString("CompositeManufacturing.54"));
checkBoxBoardTest
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
displayBoardTest();
}
});
createCompositeModelNumber();
textModelNumber=new Text(compositeManufacturingSettings, SWT.BORDER);
textModelNumber.setText("");
textModelNumber.setLayoutData(gridData9);
labelSerialNumber=new Label(compositeManufacturingSettings, SWT.NONE);
labelSerialNumber.setText(Messages.getString("CompositeManufacturing.24"));
textSerialNumberActual=new Text(compositeManufacturingSettings, SWT.BORDER);
textSerialNumberActual.setEditable(false);
textSerialNumberActual.setLayoutData(gridData18);
labelMacAddress=new Label(compositeManufacturingSettings, SWT.NONE);
labelMacAddress.setText(Messages.getString("CompositeManufacturing.25"));
textMacAddressActual=new Text(compositeManufacturingSettings, SWT.BORDER);
textMacAddressActual.setEditable(false);
textMacAddressActual.setLayoutData(gridData17);
checkBoxFirmwareAsk=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxFirmwareAsk.setSelection(false);
checkBoxFirmwareAsk.setLayoutData(gridData2);
checkBoxFirmwareAsk.setText(Messages.getString("CompositeManufacturing.26"));
checkBoxSpecSNMAC=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxSpecSNMAC.setText(Messages.getString("CompositeManufacturing.27"));
checkBoxSpecSNMAC
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
displaySpecifySNMAC();
}
});
checkBoxSerial=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxSerial.setText(Messages.getString("CompositeManufacturing.28"));
checkBoxSerial.setSelection(true);
textSerialPort=new Text(compositeManufacturingSettings, SWT.BORDER);
textSerialPort.setText("");
textSerialPort.setLayoutData(gridData5);
checkBoxBT1=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxBT1.setSelection(true);
checkBoxBT1.setText(Messages.getString("CompositeManufacturing.55"));
textBTPort1=new Text(compositeManufacturingSettings, SWT.BORDER);
textBTPort1.setText("");
textBTPort1.setLayoutData(gridData30);
checkBoxParallel=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxParallel.setText(Messages.getString("CompositeManufacturing.29"));
checkBoxParallel.setSelection(false);
checkBoxParallel.setEnabled(true);
textParallelPort=new Text(compositeManufacturingSettings, SWT.BORDER);
textParallelPort.setText("");
textParallelPort.setLayoutData(gridData6);
textParallelPort.setEnabled(true);
checkBoxNetwork=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxNetwork.setText(Messages.getString("CompositeManufacturing.30"));
checkBoxNetwork.setSelection(true);
textIPAddress=new Text(compositeManufacturingSettings, SWT.BORDER);
textIPAddress.setText("");
textIPAddress.setLayoutData(gridData7);
checkBoxUSBB=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxUSBB.setText(Messages.getString("CompositeManufacturing.53"));
checkBoxUSBB.setSelection(true);
textWindowsPrinterName=new Text(compositeManufacturingSettings, SWT.BORDER);
textWindowsPrinterName.setText("");
textWindowsPrinterName.setLayoutData(gridData8);
textWindowsPrinterName.setEditable(false);
textWindowsPrinterName.setVisible(false);
checkBoxUSBA=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxUSBA.setText("USB-A");
checkBoxUSBA.setSelection(true);
Label filler27=new Label(compositeManufacturingSettings, SWT.NONE);
labelPrintHeadSelect=new Label(compositeManufacturingSettings, SWT.NONE);
labelPrintHeadSelect.setText(Messages.getString("CompositeManufacturing.32"));
createComboPrintHead();
labelSeparator=new Label(compositeManufacturingSettings, SWT.SEPARATOR|SWT.HORIZONTAL);
labelSeparator.setText("");
labelSeparator.setLayoutData(gridData11);
labelPeripherals=new Label(compositeManufacturingSettings, SWT.NONE);
labelPeripherals.setText(Messages.getString("CompositeManufacturing.33"));
labelPeripherals.setLayoutData(gridData12);
labelPeripherals.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.BOLD));
checkBoxScanner=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxScanner.setText(Messages.getString("CompositeManufacturing.34"));
checkBoxScanner.setSelection(true);
checkBoxScanner.setEnabled(true);
textScanner=new Text(compositeManufacturingSettings, SWT.BORDER);
textScanner.setEnabled(true);
textScanner.setLayoutData(gridData13);
checkBoxSNLabelPrinter=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxSNLabelPrinter.setText(Messages.getString("CompositeManufacturing.35"));
checkBoxSNLabelPrinter.setSelection(true);
textSNLabelPrinter=new Text(compositeManufacturingSettings, SWT.BORDER);
textSNLabelPrinter.setLayoutData(gridData14);
checkBoxBoxLabelPrinter=new Button(compositeManufacturingSettings, SWT.CHECK);
checkBoxBoxLabelPrinter.setText(Messages.getString("CompositeManufacturing.36"));
checkBoxBoxLabelPrinter.setSelection(true);
checkBoxBoxLabelPrinter.setEnabled(true);
textBoxLabelPrinter=new Text(compositeManufacturingSettings, SWT.BORDER);
textBoxLabelPrinter.setEnabled(true);
textBoxLabelPrinter.setLayoutData(gridData15);
createCompositeShippingLabels();
}
private void createComboPrintHead()
{
GridData gridData29=new GridData();
gridData29.horizontalAlignment=GridData.FILL;
gridData29.verticalAlignment=GridData.CENTER;
comboPrintHead=new Combo(compositeManufacturingSettings, SWT.READ_ONLY);
comboPrintHead.setLayoutData(gridData29);
for(PrintHead ph : PrintHead.values())
{
comboPrintHead.add(ph.toString());
}
comboPrintHead.select(0);
}
private void displayAutorunOptions()
{
}
private void abortTest()
{
GUIHelper.logger.info("");
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("CompositeManufacturing.41")+e);
GUIHelper.logger.error(e, e);
}
buttonRerun.setEnabled(true);
buttonRunNext.setEnabled(false);
TestEngine.isTestAborted=true;
}
private void runNextTest()
{
GUIHelper.logger.info("");
GUIHelper.setEnabled(compositeManufacturingSettings, false);
TestEngine.isTestAborted=false;
TestStatus testStatus;
if(checkBoxBoardTest.getSelection())
{
testStatus=executeBoardTestProcedure();
}
else
{
testStatus=executePrinterTestProcedure();
if(GUIHelper.prefs.getBoolean("MAN_BLANKIFY_MODEL_NUMBER", true))
{
textModelNumber.setText("");
}
}
String msg=null;
switch(testStatus)
{
case PASSED:
msg=Messages.getString("CompositeManufacturing.38");
GUIHelper.logger.info(msg);
GUIHelper.message(msg, SWT.OK|SWT.ICON_INFORMATION);
GUIHelper.setEnabled(compositeManufacturingSettings, true);
buttonRerun.setVisible(true);
buttonRunNext.setVisible(false);
buttonRerun.setEnabled(false);
buttonRunNext.setEnabled(false);
break;
case FAILED:
TestEngine.runningManufacturing=false;
msg=Messages.getString("CompositeManufacturing.39");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
buttonRerun.setVisible(true);
buttonRunNext.setVisible(false);
buttonRerun.setEnabled(true);
buttonRunNext.setEnabled(false);
break;
case CANCELLED:
TestEngine.runningManufacturing=false;
msg=Messages.getString("CompositeManufacturing.40");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
buttonRerun.setVisible(true);
buttonRunNext.setVisible(false);
buttonRerun.setEnabled(true);
buttonRunNext.setEnabled(false);
break;
}
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("CompositeManufacturing.41")+e);
GUIHelper.logger.error(e, e);
}
}
private void rerunTest()
{
GUIHelper.logger.info("");
GUIHelper.setEnabled(compositeManufacturingSettings, false);
TestEngine.isTestAborted=false;
TestStatus testStatus;
boolean success=TestEngine.establishPrimaryConnection(115200);
if(!success)
{
testStatus=TestStatus.FAILED;
}
if(checkBoxBoardTest.getSelection())
{
testStatus=executeBoardTestProcedure();
}
else
{
testStatus=executePrinterTestProcedure();
}
String msg=null;
switch(testStatus)
{
case PASSED:
msg=Messages.getString("CompositeManufacturing.38");
GUIHelper.logger.info(msg);
GUIHelper.message(msg, SWT.OK|SWT.ICON_INFORMATION);
GUIHelper.setEnabled(compositeManufacturingSettings, true);
buttonRerun.setVisible(true);
buttonRunNext.setVisible(false);
buttonRerun.setEnabled(false);
buttonRunNext.setEnabled(false);
if(GUIHelper.prefs.getBoolean("MAN_BLANKIFY_MODEL_NUMBER", true))
{
textModelNumber.setText("");
}
break;
case FAILED:
TestEngine.runningManufacturing=false;
msg=Messages.getString("CompositeManufacturing.39");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
buttonRerun.setVisible(true);
buttonRunNext.setVisible(false);
buttonRerun.setEnabled(true);
buttonRunNext.setEnabled(false);
break;
case CANCELLED:
TestEngine.runningManufacturing=false;
msg=Messages.getString("CompositeManufacturing.40");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
buttonRerun.setVisible(true);
buttonRunNext.setVisible(false);
buttonRerun.setEnabled(true);
buttonRunNext.setEnabled(false);
break;
}
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("CompositeManufacturing.41")+e);
GUIHelper.logger.error(e, e);
}
GUIHelper.setEnabled(compositeManufacturingSettings, true);
}
private boolean requiresSecurityTest(final String modelNumber)
{
return modelNumber.length()>=11&&modelNumber.startsWith("CXD4-1300-S");
}
private TestStatus startSecurityTest(final String sModel, final boolean bCustomTest, final boolean bCustomType, final boolean bSkipLabel)
{
TestStatus testResult;
textModelNumber.setText(sModel);
if(bCustomTest)
testResult=executeSecurityTestProcedure(bCustomType, bSkipLabel);
else
testResult=executePrinterTestProcedure();
return testResult;
}
private TestStatus startAutomatedSecurityTest(final String sInputType, final String sCurrentModel)
{
String sModel=sCurrentModel;
TestStatus testResult=TestStatus.FAILED;
if(sModel==null||!sModel.startsWith("CXD4-1300"))
{
sModel="CXD4-1300";
testResult=startSecurityTest(sModel, false, false, true);
if(!testResult.equals(TestStatus.PASSED))
return testResult;
}
if(SecurityConfig.isValidSetting(sInputType))
{
if(!sModel.equals("CXD4-1300-S00"))
{
sModel="CXD4-1300-S00";
testResult=startSecurityTest(sModel, true, false,!sInputType.equals("S00"));
if(!testResult.equals(TestStatus.PASSED))
return testResult;
}
if(sModel.equals("CXD4-1300-S00")&&!sInputType.equals("S00"))
{
sModel="CXD4-1300-"+sInputType;
testResult=startSecurityTest(sModel, true, true, false);
}
}
return testResult;
}
private void startTest()
{
GUIHelper.logger.info("");
rememberPreferences();
GUIHelper.setEnabled(compositeManufacturingSettings, false);
initializeDisplay();
TestEngine.isTestAborted=false;
TestStatus testStatus=TestStatus.FAILED;
String msg=null;
testStep=null;
if(requiresSecurityTest(textModelNumber.getText()))
{
String sInputType=textModelNumber.getText().substring(10);
String sCurrentModel=null;
if(TestEngine.establishPrimaryConnection(115200))
sCurrentModel=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MODELNUMBER"));
testStatus=startAutomatedSecurityTest(sInputType, sCurrentModel);
}
else
{
if(checkBoxBoardTest.getSelection())
testStatus=executeBoardTestProcedure();
else
testStatus=executePrinterTestProcedure();
}
switch(testStatus)
{
case PASSED:
msg=Messages.getString("CompositeManufacturing.38");
GUIHelper.logger.info(msg);
GUIHelper.message(msg, SWT.OK|SWT.ICON_INFORMATION);
buttonRerun.setVisible(true);
buttonRunNext.setVisible(false);
buttonRerun.setEnabled(false);
buttonRunNext.setEnabled(false);
comboPrintHead.select(0);
if(GUIHelper.prefs.getBoolean("MAN_BLANKIFY_MODEL_NUMBER", true))
{
textModelNumber.setText("");
}
break;
case FAILED:
TestEngine.runningManufacturing=false;
msg=Messages.getString("CompositeManufacturing.39");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
buttonRerun.setVisible(true);
buttonRunNext.setVisible(false);
if(!(testStep==null||testStep==TestStep.INITIAL_SETUP||testStep==TestStep.FIRMWARE_DOWNLOAD))
{
buttonRerun.setEnabled(true);
}
else
{
buttonRerun.setEnabled(false);
}
buttonRunNext.setEnabled(false);
break;
case ABORTED:
case CANCELLED:
TestEngine.runningManufacturing=false;
msg=Messages.getString("CompositeManufacturing.40");
GUIHelper.logger.info(msg);
GUIHelper.error(msg);
buttonRerun.setVisible(true);
buttonRunNext.setVisible(false);
if(!(testStep==null||testStep==TestStep.INITIAL_SETUP||testStep==TestStep.FIRMWARE_DOWNLOAD))
{
buttonRerun.setEnabled(true);
}
else
{
buttonRerun.setEnabled(false);
}
buttonRunNext.setEnabled(false);
break;
}
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("CompositeManufacturing.41")+e);
GUIHelper.logger.error(e, e);
}
GUIHelper.setEnabled(compositeManufacturingSettings, true);
}
private TestStatus executePrinterTestProcedure()
{
GUIHelper.logger.info("");
TestEngine.isTestAborted=false;
TestStatus testStatus;
buttonRerun.setEnabled(false);
buttonRunNext.setEnabled(false);
if(testStep==null||testStep==TestStep.INITIAL_SETUP)
{
String message=Messages.getString("CompositeManufacturing.47");
indicateRunning(labelInitialSetup);
TestEngine.runningManufacturing=true;
testStep=TestStep.INITIAL_SETUP;
testStatus=TestEngine.doInitialSetup(message);
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelInitialSetup, labelInitialSetupStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
indicateRunning(labelInitialSetup);
testStatus=TestEngine.getHeadType();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelInitialSetup, labelInitialSetupStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.INITIAL_SETUP||testStep==TestStep.FIRMWARE_DOWNLOAD||testStep==null)
{
indicateRunning(labelFirmwareLoad);
testStep=TestStep.FIRMWARE_DOWNLOAD;
buttonAbort.setEnabled(false);
testStatus=TestEngine.doFirmwareDownload(false, progressBarFirmwareDownload, true);
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelFirmwareLoad, labelFirmwareLoadStatus, testStatus);
if(GUIHelper.prefs.getBoolean("ACTIVATE_CUSTOM_FIRMWARE", false))
{
labelFirmwareLoad.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
}
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
buttonAbort.setEnabled(true);
}
if(testStep==TestStep.FIRMWARE_DOWNLOAD||testStep==TestStep.PRINTER_IDENTIFICATION||testStep==null)
{
indicateRunning(labelPrinterIdentification);
testStep=TestStep.PRINTER_IDENTIFICATION;
testStatus=TestEngine.doPrinterIdentification(true);
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelPrinterIdentification, labelPrinterIdentificationStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
TestEngine.displayPrinterID();
}
if(testStep==TestStep.PRINTER_IDENTIFICATION||testStep==TestStep.FONT_DOWNLOAD||testStep==null)
{
indicateRunning(labelFontLoading);
testStep=TestStep.FONT_DOWNLOAD;
testStatus=TestEngine.doLoadFonts(TestEngine.printDensity);
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelFontLoading, labelFontLoadingStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.FONT_DOWNLOAD||testStep==TestStep.CPR_DOWNLOAD||testStep==null)
{
indicateRunning(labelCPRDownload);
testStep=TestStep.CPR_DOWNLOAD;
buttonAbort.setEnabled(false);
testStatus=TestEngine.doLoadCPRs();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelCPRDownload, labelCPRDownloadStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
buttonAbort.setEnabled(true);
}
if(testStep==TestStep.CPR_DOWNLOAD||testStep==TestStep.CONTROL_PANEL||testStep==null)
{
indicateRunning(labelControlPanel);
testStep=TestStep.CONTROL_PANEL;
testStatus=TestEngine.doControlPanel();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelControlPanel, labelControlPanelStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.CONTROL_PANEL||testStep==TestStep.CONNECTION_TYPES||testStep==null)
{
indicateRunning(labelInterfaceTest);
testStep=TestStep.CONNECTION_TYPES;
testStatus=TestEngine.doConnectionTypes();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelInterfaceTest, labelInterfaceTestStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.CONNECTION_TYPES||testStep==TestStep.PRINTHEAD_UP_DOWN||testStep==null)
{
indicateRunning(labelPrintHeadUpDown);
testStep=TestStep.PRINTHEAD_UP_DOWN;
testStatus=TestEngine.doPrintHeadUpDown();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelPrintHeadUpDown, labelPrintHeadUpDownStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.PRINTHEAD_UP_DOWN||testStep==TestStep.TONE_CHECK||testStep==null)
{
indicateRunning(labelToneCheck);
testStep=TestStep.TONE_CHECK;
testStatus=TestEngine.doToneCheck();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelToneCheck, labelToneCheckStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.TONE_CHECK||testStep==TestStep.RTC_SETUP||testStep==null)
{
indicateRunning(labelRTCSetup);
testStep=TestStep.RTC_SETUP;
testStatus=TestEngine.doRTCSetup();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelRTCSetup, labelRTCSetupStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.RTC_SETUP||testStep==TestStep.CALIBRATION||testStep==null)
{
indicateRunning(labelCalibration);
testStep=TestStep.CALIBRATION;
testStatus=TestEngine.doCalibration(TestEngine.printMethod, false);
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelCalibration, labelCalibrationStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.CALIBRATION||testStep==TestStep.FEED_BUTTON_TEST||testStep==null)
{
indicateRunning(labelFeedButton);
testStep=TestStep.FEED_BUTTON_TEST;
testStatus=TestEngine.doFeedButtonTest();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelFeedButton, labelFeedButtonStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.FEED_BUTTON_TEST||testStep==TestStep.PEELER_TEST||testStep==null)
{
indicateRunning(labelPeeler);
testStep=TestStep.PEELER_TEST;
testStatus=TestEngine.doPeelerTest();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelPeeler, labelPeelerStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.PEELER_TEST||testStep==TestStep.PRINTHEAD_TEST||testStep==null)
{
indicateRunning(labelPrintHead);
testStep=TestStep.PRINTHEAD_TEST;
testStatus=TestEngine.doPrintHeadTest();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelPrintHead, labelPrintHeadStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.PRINTHEAD_TEST||testStep==TestStep.BACKUP_MEMORY_TEST||testStep==null)
{
indicateRunning(labelBackupMemory);
testStep=TestStep.BACKUP_MEMORY_TEST;
testStatus=TestEngine.doBackupMemoryTest(true);
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelBackupMemory, labelBackupMemoryStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.BACKUP_MEMORY_TEST||testStep==TestStep.PRINT_QUALITY_TEST||testStep==null)
{
indicateRunning(labelPrintQuality);
testStep=TestStep.PRINT_QUALITY_TEST;
testStatus=TestEngine.doPrintQuality(TestEngine.mediaWidth, TestEngine.printDensity);
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelPrintQuality, labelPrintQualityStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.PRINT_QUALITY_TEST||testStep==TestStep.RIBBON_WRINKLE_TEST||testStep==null)
{
indicateRunning(labelRibbonWrinkle);
testStep=TestStep.RIBBON_WRINKLE_TEST;
testStatus=TestEngine.doRibbonWrinkle(TestEngine.mediaWidth, TestEngine.printDensity);
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelRibbonWrinkle, labelRibbonWrinkleStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.RIBBON_WRINKLE_TEST||testStep==TestStep.FINAL_SETTINGS||testStep==null)
{
indicateRunning(labelFinalSettings);
testStep=TestStep.FINAL_SETTINGS;
testStatus=TestEngine.doFinalSettings();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelFinalSettings, labelFinalSettingsStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.FINAL_SETTINGS||testStep==TestStep.SELF_TEST||testStep==null)
{
indicateRunning(labelSelfTest);
testStep=TestStep.SELF_TEST;
testStatus=TestEngine.doSelfTest();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelSelfTest, labelSelfTestStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.SELF_TEST||testStep==TestStep.SERIAL_NUMBER_LABEL||testStep==null||TestEngine.isCustomLabel_K542)
{
indicateRunning(labelShippingLabel);
testStep=TestStep.SERIAL_NUMBER_LABEL;
if (TestEngine.isCustomLabel_K542) { TestEngine.printCustom_K542=true; }
testStatus=TestEngine.doSerialNumberLabel();
if (TestEngine.isCustomLabel_K542) { TestEngine.printCustom_K542=false; }
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelShippingLabel, labelShippingLabelStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
if(testStep==TestStep.SERIAL_NUMBER_LABEL||testStep==TestStep.BOX_LABEL||testStep==null)
{
indicateRunning(labelShippingLabel);
testStep=TestStep.BOX_LABEL;
testStatus=TestEngine.doBoxLabel();
if(TestEngine.isTestAborted)
{
testStatus=TestStatus.ABORTED;
}
indicateDone(labelShippingLabel, labelShippingLabelStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
{
return testStatus;
}
}
testStep=null;
TestEngine.runningManufacturing=false;
return TestStatus.PASSED;
}
private TestStatus executeBoardTestProcedure()
{
GUIHelper.logger.info("");
TestStatus testStatus;
String message=Messages.getString("CompositeManufacturing.48");
indicateRunning(labelInitialSetup);
TestEngine.runningManufacturing=true;
testStatus=TestEngine.doInitialSetup(message);
indicateDone(labelInitialSetup, labelInitialSetupStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
GUIHelper.message(Messages.getString("CompositeManufacturing.52"), SWT.OK|SWT.ICON_WORKING);
indicateRunning(labelInitialSetup);
testStatus=TestEngine.getHeadType();
indicateDone(labelInitialSetup, labelInitialSetupStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
indicateRunning(labelFirmwareLoad);
testStatus=TestEngine.doFirmwareDownload(false, progressBarFirmwareDownload, true);
indicateDone(labelFirmwareLoad, labelFirmwareLoadStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
indicateRunning(labelPrinterIdentification);
testStatus=TestEngine.doPrinterIdentification(false);
indicateDone(labelPrinterIdentification, labelPrinterIdentificationStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
TestEngine.displayPrinterID();
indicateRunning(labelFontLoading);
testStatus=TestEngine.doLoadFonts(TestEngine.printDensity);
indicateDone(labelFontLoading, labelFontLoadingStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
indicateRunning(labelCPRDownload);
testStatus=TestEngine.doLoadCPRs();
indicateDone(labelCPRDownload, labelCPRDownloadStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
indicateDone(labelControlPanel, labelControlPanelStatus, TestStatus.SKIPPED);
indicateRunning(labelInterfaceTest);
testStatus=TestEngine.doConnectionTypes();
indicateDone(labelInterfaceTest, labelInterfaceTestStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
indicateRunning(labelPrintHeadUpDown);
testStatus=TestEngine.doPrintHeadUpDown();
indicateDone(labelPrintHeadUpDown, labelPrintHeadUpDownStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
indicateRunning(labelToneCheck);
testStatus=TestEngine.doToneCheck();
indicateDone(labelToneCheck, labelToneCheckStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
indicateDone(labelRTCSetup, labelRTCSetupStatus, TestStatus.SKIPPED);
indicateRunning(labelCalibration);
testStatus=TestEngine.doCalibration(TestEngine.printMethod, true);
indicateDone(labelCalibration, labelCalibrationStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
indicateDone(labelFeedButton, labelFeedButtonStatus, TestStatus.SKIPPED);
indicateDone(labelPeeler, labelPeelerStatus, TestStatus.SKIPPED);
indicateRunning(labelPrintHead);
testStatus=TestEngine.doPrintHeadTest();
indicateDone(labelPrintHead, labelPrintHeadStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
indicateRunning(labelBackupMemory);
testStatus=TestEngine.doBackupMemoryTest(false);
indicateDone(labelBackupMemory, labelBackupMemoryStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
return testStatus;
}
indicateDone(labelPrintQuality, labelPrintQualityStatus, TestStatus.SKIPPED);
indicateDone(labelRibbonWrinkle, labelRibbonWrinkleStatus, TestStatus.SKIPPED);
indicateDone(labelFinalSettings, labelFinalSettingsStatus, TestStatus.SKIPPED);
indicateDone(labelSelfTest, labelSelfTestStatus, TestStatus.SKIPPED);
indicateDone(labelShippingLabel, labelShippingLabelStatus, TestStatus.SKIPPED);
TestEngine.runningManufacturing=false;
return TestStatus.PASSED;
}
private TestStatus executeSecurityTestProcedure(final boolean bQuickTest, final boolean bSkipLabel)
{
GUIHelper.logger.info("");
initializeDisplay();
TestEngine.isTestAborted=false;
TestStatus testStatus;
buttonRerun.setEnabled(false);
buttonRunNext.setEnabled(false);
testStep=TestStep.INITIAL_SETUP;
if(testStep==TestStep.INITIAL_SETUP)
{
String message=Messages.getString("CompositeManufacturing.47");
indicateRunning(labelInitialSetup);
TestEngine.runningManufacturing=true;
testStatus=TestEngine.doInitialSetup(message);
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelInitialSetup, labelInitialSetupStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
indicateRunning(labelInitialSetup);
testStatus=TestEngine.getHeadType();
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelInitialSetup, labelInitialSetupStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
testStep=TestStep.FIRMWARE_DOWNLOAD;
}
if(testStep==TestStep.FIRMWARE_DOWNLOAD)
{
indicateRunning(labelFirmwareLoad);
buttonAbort.setEnabled(false);
testStatus=TestEngine.doFirmwareDownload(checkBoxFirmwareAsk.getSelection(), progressBarFirmwareDownload, true);
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelFirmwareLoad, labelFirmwareLoadStatus, testStatus);
if(GUIHelper.prefs.getBoolean("ACTIVATE_CUSTOM_FIRMWARE", false))
labelFirmwareLoad.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
buttonAbort.setEnabled(true);
testStep=TestStep.PRINTER_IDENTIFICATION;
}
if(testStep==TestStep.PRINTER_IDENTIFICATION)
{
indicateRunning(labelPrinterIdentification);
testStatus=TestEngine.doPrinterIdentification(true);
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelPrinterIdentification, labelPrinterIdentificationStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
TestEngine.displayPrinterID();
testStep=TestStep.CALIBRATION;
}
if(testStep==TestStep.CALIBRATION)
{
indicateRunning(labelCalibration);
testStatus=TestEngine.doCalibration(TestEngine.printMethod, false);
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelCalibration, labelCalibrationStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
testStep=TestStep.CPR_DOWNLOAD;
}
if(testStep==TestStep.CPR_DOWNLOAD)
{
if(bQuickTest)
indicateDone(labelCPRDownload, labelCPRDownloadStatus, TestStatus.SKIPPED);
else
{
indicateRunning(labelCPRDownload);
buttonAbort.setEnabled(false);
testStatus=TestEngine.doLoadCPRs();
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelCPRDownload, labelCPRDownloadStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
buttonAbort.setEnabled(true);
}
testStep=TestStep.FONT_DOWNLOAD;
}
if(testStep==TestStep.FONT_DOWNLOAD)
{
indicateRunning(labelFontLoading);
testStatus=TestEngine.doLoadFonts(TestEngine.printDensity);
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelFontLoading, labelFontLoadingStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
testStep=TestStep.CONTROL_PANEL;
}
if(testStep==TestStep.CONTROL_PANEL)
{
if(bQuickTest)
indicateDone(labelControlPanel, labelControlPanelStatus, TestStatus.SKIPPED);
else
{
indicateRunning(labelControlPanel);
testStatus=TestEngine.doControlPanel();
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelControlPanel, labelControlPanelStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
}
testStep=TestStep.CONNECTION_TYPES;
}
if(testStep==TestStep.CONNECTION_TYPES)
{
if(bQuickTest)
indicateDone(labelInterfaceTest, labelInterfaceTestStatus, TestStatus.SKIPPED);
else
{
indicateRunning(labelInterfaceTest);
testStatus=TestEngine.doConnectionTypes();
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelInterfaceTest, labelInterfaceTestStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
}
testStep=TestStep.PRINTHEAD_UP_DOWN;
}
if(testStep==TestStep.PRINTHEAD_UP_DOWN)
{
indicateDone(labelPrintHeadUpDown, labelPrintHeadUpDownStatus, TestStatus.SKIPPED);
testStep=TestStep.TONE_CHECK;
}
if(testStep==TestStep.TONE_CHECK)
{
indicateDone(labelToneCheck, labelToneCheckStatus, TestStatus.SKIPPED);
testStep=TestStep.RTC_SETUP;
}
if(testStep==TestStep.RTC_SETUP)
{
if(bQuickTest)
indicateDone(labelRTCSetup, labelRTCSetupStatus, TestStatus.SKIPPED);
else
{
indicateRunning(labelRTCSetup);
testStatus=TestEngine.doRTCSetup();
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelRTCSetup, labelRTCSetupStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
}
testStep=TestStep.FEED_BUTTON_TEST;
}
if(testStep==TestStep.FEED_BUTTON_TEST)
{
if(bQuickTest)
indicateDone(labelFeedButton, labelFeedButtonStatus, TestStatus.SKIPPED);
else
{
indicateRunning(labelFeedButton);
testStatus=TestEngine.doFeedButtonTest();
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelFeedButton, labelFeedButtonStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
}
testStep=TestStep.PRINTHEAD_TEST;
}
indicateDone(labelPeeler, labelPeelerStatus, TestStatus.SKIPPED);
if(testStep==TestStep.PRINTHEAD_TEST)
{
if(bQuickTest)
indicateDone(labelPrintHead, labelPrintHeadStatus, TestStatus.SKIPPED);
else
{
indicateRunning(labelPrintHead);
testStatus=TestEngine.doPrintHeadTest();
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelPrintHead, labelPrintHeadStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
}
testStep=TestStep.BACKUP_MEMORY_TEST;
}
if(testStep==TestStep.BACKUP_MEMORY_TEST)
{
indicateRunning(labelBackupMemory);
testStatus=TestEngine.doBackupMemoryTest(true);
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelBackupMemory, labelBackupMemoryStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
testStep=TestStep.PRINT_QUALITY_TEST;
}
if(testStep==TestStep.PRINT_QUALITY_TEST)
{
if(bQuickTest)
indicateDone(labelPrintQuality, labelPrintQualityStatus, TestStatus.SKIPPED);
else
{
indicateRunning(labelPrintQuality);
testStep=TestStep.PRINT_QUALITY_TEST;
testStatus=TestEngine.doPrintQuality(TestEngine.mediaWidth, TestEngine.printDensity);
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelPrintQuality, labelPrintQualityStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
}
testStep=TestStep.FINAL_SETTINGS;
}
indicateDone(labelRibbonWrinkle, labelRibbonWrinkleStatus, TestStatus.SKIPPED);
if(testStep==TestStep.FINAL_SETTINGS)
{
indicateRunning(labelFinalSettings);
testStatus=TestEngine.doFinalSettings();
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelFinalSettings, labelFinalSettingsStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
testStep=TestStep.SELF_TEST;
}
if(testStep==TestStep.SELF_TEST)
{
indicateRunning(labelSelfTest);
testStatus=TestEngine.doSelfTest();
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelSelfTest, labelSelfTestStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
testStep=TestStep.SERIAL_NUMBER_LABEL;
}
if(testStep==TestStep.SERIAL_NUMBER_LABEL||TestEngine.isCustomLabel_K542)
{
if(bSkipLabel)
indicateDone(labelShippingLabel, labelShippingLabelStatus, TestStatus.SKIPPED);
else
{
indicateRunning(labelShippingLabel);
if (TestEngine.isCustomLabel_K542) { TestEngine.printCustom_K542=true; }
testStatus=TestEngine.doSerialNumberLabel();
if (TestEngine.isCustomLabel_K542) { TestEngine.isCustomLabel_K542=false; TestEngine.printCustom_K542=false; }
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelShippingLabel, labelShippingLabelStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
}
testStep=TestStep.BOX_LABEL;
}
if(testStep==TestStep.BOX_LABEL)
{
if(bSkipLabel)
indicateDone(labelShippingLabel, labelShippingLabelStatus, TestStatus.SKIPPED);
else
{
indicateRunning(labelShippingLabel);
testStatus=TestEngine.doBoxLabel();
if(TestEngine.isTestAborted)
testStatus=TestStatus.ABORTED;
indicateDone(labelShippingLabel, labelShippingLabelStatus, testStatus);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED||testStatus==TestStatus.ABORTED)
return testStatus;
}
}
testStep=null;
TestEngine.runningManufacturing=false;
return TestStatus.PASSED;
}
private void initializeDisplay()
{
GUIHelper.logger.trace("");
progressBarFirmwareDownload.setEnabled(false);
progressBarFirmwareDownload.setVisible(false);
Control[] children=getChildren();
for(Control c : children)
{
String className=c.getClass().getName();
if(className.equals("org.eclipse.swt.widgets.Label"))
{
Label l=(Label)c;
l.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
for(String s : TestEngine.TestStatusString)
{
if(l.getText().equals(s))
{
l.setVisible(false);
break;
}
}
}
}
if(!checkBoxSpecSNMAC.getSelection())
{
textSerialNumberActual.setText("");
textMacAddressActual.setText("");
}
}
private void indicateRunning(Label stepLabel)
{
GUIHelper.logger.trace("");
stepLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
stepLabel.update();
}
private void indicateDone(Label stepLabel, Label statusLabel, TestStatus testStatus)
{
GUIHelper.logger.trace("");
stepLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
statusLabel.setText(TestEngine.TestStatusString[testStatus.ordinal()]);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
statusLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
}
else
{
statusLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
}
statusLabel.setVisible(true);
statusLabel.update();
}
private void rememberPreferences()
{
GUIHelper.prefs.put("MAN_MODEL_NUMBER", textModelNumber.getText());
GUIHelper.prefs.put("MAN_PRINT_HEAD", comboPrintHead.getText());
GUIHelper.prefs.putBoolean("MAN_FIRMWARE_ASK", checkBoxFirmwareAsk.getSelection());
GUIHelper.prefs.putBoolean("MAN_USB_A", checkBoxUSBA.getSelection());
GUIHelper.prefs.putBoolean("MAN_BOARD_TEST", checkBoxBoardTest.getSelection());
rememberOptionalPeripheral(checkBoxBT1, textBTPort1, "MAN_BT_PORT");
rememberOptionalPeripheral(checkBoxSerial, textSerialPort, "MAN_SERIAL_PORT");
rememberOptionalPeripheral(checkBoxParallel, textParallelPort, "MAN_PARALLEL_PORT");
rememberOptionalPeripheral(checkBoxUSBB, textWindowsPrinterName, "MAN_PRINTER_NAME");
rememberOptionalPeripheral(checkBoxNetwork, textIPAddress, "MAN_IP_ADDRESS");
rememberOptionalPeripheral(checkBoxScanner, textScanner, "MAN_SCANNER");
rememberOptionalPeripheral(checkBoxSNLabelPrinter, textSNLabelPrinter, "MAN_SN_PRINTER");
rememberOptionalPeripheral(checkBoxBoxLabelPrinter, textBoxLabelPrinter, "MAN_BOX_PRINTER");
GUIHelper.prefs.putBoolean("MAN_AUTORUN", checkBoxAutorun.getSelection());
}
private void displayPreferences()
{
firstPaint=false;
if(GUIHelper.prefs.getBoolean("MAN_BLANKIFY_MODEL_NUMBER", true))
{
textModelNumber.setText("");
}
else
{
textModelNumber.setText(GUIHelper.prefs.get("MAN_MODEL_NUMBER", "DBD24-2085-00L"));
}
buttonPCL.setVisible(GUIHelper.prefs.getBoolean("ACTIVATE_REPAIR", false));
checkBoxTG.setVisible(GUIHelper.prefs.getBoolean("ACTIVATE_REPAIR", false));
checkBoxDisableLicenses.setVisible(GUIHelper.prefs.getBoolean("ACTIVATE_REPAIR", false));
checkBoxSkipLoad3.setVisible(GUIHelper.prefs.getBoolean("SHOW_SKIP_LOAD", false));
String defaultPrintHead=GUIHelper.prefs.get("MAN_PRINT_HEAD", "Automatic");
comboPrintHead.select(0);
checkBoxFirmwareAsk.setSelection(GUIHelper.prefs.getBoolean("MAN_FIRMWARE_ASK", false));
checkBoxUSBA.setSelection(GUIHelper.prefs.getBoolean("MAN_USB_A", true));
displayOptionalPeripheral(checkBoxBT1, textBTPort1, "MAN_BT_PORT", "COM7");
displayOptionalPeripheral(checkBoxSerial, textSerialPort, "MAN_SERIAL_PORT", "COM1");
if (checkBoxParallel.isEnabled()&&textParallelPort.isEnabled())
{
displayOptionalPeripheral(checkBoxParallel, textParallelPort, "MAN_PARALLEL_PORT", "LPT1");
}
displayOptionalPeripheral(checkBoxUSBB, textWindowsPrinterName, "MAN_PRINTER_NAME", "CSeriesUSBptr");
displayOptionalPeripheral(checkBoxNetwork, textIPAddress, "MAN_IP_ADDRESS", "192.168.1.0");
displayOptionalPeripheral(checkBoxScanner, textScanner, "MAN_SCANNER", "COM3");
displayOptionalPeripheral(checkBoxSNLabelPrinter, textSNLabelPrinter, "MAN_SN_PRINTER", "COM4");
displayOptionalPeripheral(checkBoxBoxLabelPrinter, textBoxLabelPrinter, "MAN_BOX_PRINTER", "COM5");
boolean allowSpecify=GUIHelper.prefs.getBoolean("ACTIVATE_REPAIR", false);
checkBoxSpecSNMAC.setVisible(allowSpecify);
checkBoxSpecSNMAC.setSelection(false);
displaySpecifySNMAC();
try
{
TestEngine.isMadeByEnnoconnMalaysia=false;
TestEngine.locationCode=LocationCode.valueOf(GUIHelper.prefs.get("MAN_LOCATION_CODE", ""));
if(TestEngine.locationCode==LocationCode.E)
TestEngine.isMadeByEnnoconnMalaysia=true;
}
catch(IllegalArgumentException e)
{
String msg=Messages.getString("CompositeManufacturing.42");
GUIHelper.logger.error(msg);
GUIHelper.error(msg);
buttonStart.setEnabled(false);
}
if((TestEngine.locationCode==LocationCode.T)||(TestEngine.locationCode==LocationCode.D)||(TestEngine.locationCode==LocationCode.V)||(TestEngine.locationCode==LocationCode.E))
{
checkBoxBoardTest.setVisible(true);
checkBoxBoardTest.setSelection(GUIHelper.prefs.getBoolean("MAN_BOARD_TEST", false));
displayBoardTest();
}
else
{
checkBoxBoardTest.setVisible(false);
checkBoxBoardTest.setSelection(false);
}
if(GUIHelper.prefs.getBoolean("ACTIVATE_CUSTOM_FIRMWARE", false))
{
labelFirmwareLoad.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
}
checkBoxAutorun.setSelection(GUIHelper.prefs.getBoolean("MAN_AUTORUN", true));
buttonRerun.setEnabled(false);
buttonRunNext.setEnabled(false);
}
private void displaySpecifySNMAC()
{
boolean specify=checkBoxSpecSNMAC.getSelection();
textSerialNumberActual.setEditable(specify);
textMacAddressActual.setEditable(specify);
textSerialNumberActual.setText("");
textMacAddressActual.setText("");
if(specify)
{
checkBoxBoardTest.setSelection(false);
displayBoardTest();
}
}
private void displayBTText()
{
}
private void displayBoardTest()
{
boolean bBoardTest=checkBoxBoardTest.getSelection();
boolean bFirmwareAsk=checkBoxFirmwareAsk.getSelection();
boolean bSpecSNMAC=checkBoxSpecSNMAC.getSelection();
boolean bScanner=checkBoxScanner.getSelection();
boolean bBoxLabelPrinter=checkBoxBoxLabelPrinter.getSelection();
boolean bSNLabelPrinter=checkBoxSNLabelPrinter.getSelection();
boolean bSkipLoad1=checkBoxSkipLoad1.getSelection();
boolean bSkipLoad2=checkBoxSkipLoad2.getSelection();
boolean bSkipLoad3=checkBoxSkipLoad3.getSelection();
boolean b2FirmwareAsk=checkBoxFirmwareAsk.isEnabled();
boolean b2SpecSNMAC=checkBoxSpecSNMAC.isEnabled();
boolean b2Scanner=checkBoxScanner.isEnabled();
boolean b2BoxLabelPrinter=checkBoxBoxLabelPrinter.isEnabled();
boolean b2SNLabelPrinter=checkBoxSNLabelPrinter.isEnabled();
boolean b2SkipLoad1=checkBoxSkipLoad1.isEnabled();
boolean b2SkipLoad2=checkBoxSkipLoad2.isEnabled();
boolean b2SkipLoad3=checkBoxSkipLoad3.isEnabled();
if(TestEngine.locationCode==LocationCode.T)
{
if(bBoardTest)
{
textModelNumber.setText("CXT2-1000");
textModelNumber.setEnabled(false);
checkBoxFirmwareAsk.setSelection(false);
checkBoxSpecSNMAC.setSelection(false);
displaySpecifySNMAC();
int index=comboPrintHead.indexOf("HEC");
comboPrintHead.select(index);
checkBoxScanner.setSelection(false);
checkBoxBoxLabelPrinter.setSelection(false);
checkBoxSNLabelPrinter.setSelection(false);
}
else
{
textModelNumber.setEnabled(true);
}
}
else
{
if(bBoardTest)
{
textModelNumber.setText("");
textModelNumber.setEnabled(true);
checkBoxFirmwareAsk.setSelection(false);
checkBoxFirmwareAsk.setEnabled(false);
checkBoxSpecSNMAC.setSelection(false);
checkBoxSpecSNMAC.setEnabled(false);
displaySpecifySNMAC();
checkBoxSkipLoad1.setSelection(false);
checkBoxSkipLoad1.setEnabled(false);
checkBoxSkipLoad2.setSelection(false);
checkBoxSkipLoad2.setEnabled(false);
checkBoxSkipLoad3.setSelection(false);
checkBoxSkipLoad3.setEnabled(false);
checkBoxScanner.setSelection(false);
checkBoxScanner.setEnabled(false);
checkBoxBoxLabelPrinter.setSelection(false);
checkBoxBoxLabelPrinter.setEnabled(false);
checkBoxSNLabelPrinter.setSelection(false);
checkBoxSNLabelPrinter.setEnabled(false);
buttonSNLabel.setEnabled(false);
buttonBoxLabel.setEnabled(false);
}
else
{
textModelNumber.setText("");
textModelNumber.setEnabled(true);
checkBoxFirmwareAsk.setEnabled(true);
checkBoxSpecSNMAC.setEnabled(b2SpecSNMAC);
checkBoxSkipLoad1.setEnabled(true);
checkBoxSkipLoad2.setEnabled(true);
checkBoxSkipLoad3.setEnabled(true);
checkBoxScanner.setEnabled(true);
checkBoxBoxLabelPrinter.setEnabled(true);
checkBoxSNLabelPrinter.setEnabled(true);
buttonSNLabel.setEnabled(true);
buttonBoxLabel.setEnabled(true);
}
}
}
private void rememberOptionalPeripheral(Button checkBox, Text text, String prefName)
{
String prefValue;
if(checkBox.getSelection())
{
prefValue=text.getText();
}
else
{
prefValue="NONE";
}
GUIHelper.prefs.put(prefName, prefValue);
}
private void displayOptionalPeripheral(Button checkBox, Text text, String prefName, String def)
{
String tmp=null;
tmp=GUIHelper.prefs.get(prefName, def);
if(tmp.equals("NONE"))
{
checkBox.setSelection(false);
text.setText("");
}
else
{
checkBox.setSelection(true);
text.setText(tmp);
}
}
private void createCompositeShippingLabels()
{
GridData gridData23=new GridData();
gridData23.horizontalAlignment=GridData.FILL;
gridData23.grabExcessHorizontalSpace=true;
gridData23.grabExcessVerticalSpace=true;
gridData23.verticalAlignment=GridData.FILL;
GridData gridData20=new GridData();
gridData20.horizontalAlignment=GridData.FILL;
gridData20.grabExcessHorizontalSpace=true;
gridData20.grabExcessVerticalSpace=true;
gridData20.verticalAlignment=GridData.FILL;
GridLayout gridLayout3=new GridLayout();
gridLayout3.numColumns=2;
GridData gridData16=new GridData();
gridData16.horizontalAlignment=GridData.FILL;
gridData16.grabExcessHorizontalSpace=true;
gridData16.grabExcessVerticalSpace=true;
gridData16.horizontalSpan=2;
gridData16.verticalAlignment=GridData.FILL;
compositeShippingLabels=new Composite(compositeManufacturingSettings,
SWT.NONE);
compositeShippingLabels.setLayoutData(gridData16);
compositeShippingLabels.setLayout(gridLayout3);
buttonSNLabel=new Button(compositeShippingLabels, SWT.NONE);
buttonSNLabel.setText(Messages.getString("CompositeManufacturing.43"));
buttonSNLabel.setLayoutData(gridData20);
buttonSNLabel.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
if(textModelNumber.getText().equalsIgnoreCase("DLX-K542"))
{
TestEngine.printCustom_K542=true;
TestEngine.isCustomLabel_K542=true;
TestEngine.printSNLabel();
TestEngine.printCustom_K542=false;
TestEngine.isCustomLabel_K542=false;
}
else
{
TestEngine.printSNLabel();
}
}
});
buttonBoxLabel=new Button(compositeShippingLabels, SWT.NONE);
buttonBoxLabel.setText(Messages.getString("CompositeManufacturing.44"));
buttonBoxLabel.setEnabled(true);
buttonBoxLabel.setLayoutData(gridData23);
buttonBoxLabel
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
TestEngine.printBoxLabel();
}
});
}
private void createCompositeModelNumber()
{
GridData gridData26=new GridData();
gridData26.grabExcessHorizontalSpace=true;
GridData gridData25=new GridData();
gridData25.horizontalAlignment=GridData.END;
gridData25.verticalAlignment=GridData.CENTER;
GridLayout gridLayout4=new GridLayout();
gridLayout4.numColumns=2;
gridLayout4.marginHeight=0;
gridLayout4.horizontalSpacing=0;
gridLayout4.verticalSpacing=0;
gridLayout4.marginWidth=0;
GridData gridData4=new GridData();
gridData4.horizontalAlignment=GridData.FILL;
gridData4.verticalAlignment=GridData.FILL;
compositeModelNumber=new Composite(compositeManufacturingSettings,
SWT.NONE);
compositeModelNumber.setLayoutData(gridData4);
compositeModelNumber.setLayout(gridLayout4);
labelModelNumber=new Label(compositeModelNumber, SWT.NONE);
labelModelNumber.setText(Messages.getString("CompositeManufacturing.45"));
labelModelNumber.setLayoutData(gridData26);
buttonScanModelNumber=new Button(compositeModelNumber, SWT.NONE);
buttonScanModelNumber.setText(Messages.getString("CompositeManufacturing.46"));
buttonScanModelNumber.setLayoutData(gridData25);
buttonScanModelNumber
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
TestEngine.scanModelNumber();
}
});
}
private void createCompositeStart()
{
GridData gridData110=new GridData();
gridData110.horizontalAlignment=GridData.END;
gridData110.grabExcessHorizontalSpace=true;
gridData110.verticalAlignment=GridData.CENTER;
GridData gridData28=new GridData();
gridData28.grabExcessHorizontalSpace=false;
GridData gridData=new GridData();
gridData.horizontalAlignment=GridData.FILL;
gridData.verticalAlignment=GridData.FILL;
GridLayout gridLayout5=new GridLayout();
gridLayout5.numColumns=2;
gridLayout5.verticalSpacing=0;
gridLayout5.marginWidth=0;
gridLayout5.marginHeight=0;
gridLayout5.horizontalSpacing=0;
compositeStart=new Composite(this, SWT.NONE);
compositeStart.setLayout(gridLayout5);
compositeStart.setLayoutData(gridData);
buttonStart=new Button(compositeStart, SWT.NONE);
buttonStart.setText(Messages.getString("CompositeManufacturing.0"));
buttonStart.setLayoutData(gridData28);
checkBoxAutorun=new Button(compositeStart, SWT.CHECK);
checkBoxAutorun.setSelection(true);
checkBoxAutorun.setLayoutData(gridData110);
checkBoxAutorun.setText("Autorun");
checkBoxAutorun
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
displayAutorunOptions();
}
});
checkBoxAutorun.setVisible(false);
buttonStart.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
startTest();
}
});
}
}
