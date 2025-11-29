public class CompositeAbout extends Composite
{
private Label filler1=null;
private Label filler4=null;
private Label filler11=null;
private Composite compositeAboutManufacturing=null;
private Label labelFirmware=null;
private Label labelFirmwareVersion=null;
private Label labelCPR=null;
private Label labelCPRVersion=null;
private Label labelFonts=null;
private Label labelFontsVersion=null;
private Label labelLabels=null;
private Label labelLabelsVersion=null;
private Composite compositeAboutBasic=null;
private Label labelFPK=null;
private Label labelFPKVersion=null;
private Composite compositeAbout=null;
private Label labelLogo=null;
private Label labelAddress1=null;
private Label labelAddress2=null;
private Label labelPhone=null;
private Link linkUrl=null;
private Label labelCopyright=null;
private Link linkEmail=null;
private Label labelJAdmin=null;
private Label labelVersion=null;
private Label labelRevision=null;
private Label labelCompany=null;
public CompositeAbout(Composite parent, int style)
{
super(parent, style);
initialize();
customize();
}
private void initialize()
{
GridData gridData4=new GridData();
gridData4.grabExcessHorizontalSpace=true;
GridData gridData3=new GridData();
gridData3.grabExcessHorizontalSpace=true;
GridData gridData1=new GridData();
gridData1.grabExcessVerticalSpace=true;
Label filler14=new Label(this, SWT.NONE);
Label filler=new Label(this, SWT.NONE);
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=4;
gridLayout.verticalSpacing=11;
gridLayout.horizontalSpacing=20;
filler1=new Label(this, SWT.NONE);
filler1.setText("Label");
filler1.setVisible(false);
filler1.setLayoutData(gridData1);
Label filler7=new Label(this, SWT.NONE);
Label filler2=new Label(this, SWT.NONE);
createCompositeAbout();
Label filler6=new Label(this, SWT.NONE);
Label filler12=new Label(this, SWT.NONE);
createCompositeAboutBasic();
filler4=new Label(this, SWT.NONE);
filler11=new Label(this, SWT.NONE);
filler11.setText("Label");
filler11.setVisible(false);
filler11.setLayoutData(gridData4);
filler4.setText("Label");
filler4.setLayoutData(gridData3);
filler4.setVisible(false);
this.setLayout(gridLayout);
this.setSize(new Point(422, 537));
}
private void createCompositeAboutManufacturing()
{
GridData gridData41=new GridData();
gridData41.horizontalAlignment=GridData.CENTER;
gridData41.horizontalSpan=2;
gridData41.verticalAlignment=GridData.CENTER;
GridData gridData23=new GridData();
gridData23.horizontalAlignment=GridData.END;
gridData23.verticalAlignment=GridData.CENTER;
GridData gridData22=new GridData();
gridData22.horizontalAlignment=GridData.END;
gridData22.verticalAlignment=GridData.CENTER;
GridData gridData21=new GridData();
gridData21.horizontalAlignment=GridData.END;
gridData21.verticalAlignment=GridData.CENTER;
GridData gridData20=new GridData();
gridData20.horizontalAlignment=GridData.END;
gridData20.verticalAlignment=GridData.CENTER;
GridData gridData19=new GridData();
gridData19.horizontalAlignment=GridData.END;
gridData19.verticalAlignment=GridData.CENTER;
GridLayout gridLayout1=new GridLayout();
gridLayout1.numColumns=2;
gridLayout1.verticalSpacing=10;
gridLayout1.horizontalSpacing=20;
compositeAboutManufacturing=new Composite(compositeAboutBasic, SWT.NONE);
compositeAboutManufacturing.setLayout(gridLayout1);
compositeAboutManufacturing.setLayoutData(gridData41);
labelFirmware=new Label(compositeAboutManufacturing, SWT.NONE);
labelFirmware.setText(Messages.getString("CompositeAbout.0"));
labelFirmwareVersion=new Label(compositeAboutManufacturing, SWT.NONE);
labelFirmwareVersion.setText(Versions.manFirmware);
labelFirmwareVersion.setLayoutData(gridData19);
labelCPR=new Label(compositeAboutManufacturing, SWT.NONE);
labelCPR.setText("CPRs:");
labelCPRVersion=new Label(compositeAboutManufacturing, SWT.NONE);
labelCPRVersion.setText(Versions.manCpr);
labelCPRVersion.setLayoutData(gridData20);
labelFonts=new Label(compositeAboutManufacturing, SWT.NONE);
labelFonts.setText(Messages.getString("CompositeAbout.1"));
labelFontsVersion=new Label(compositeAboutManufacturing, SWT.NONE);
labelFontsVersion.setText(Versions.manFonts);
labelFontsVersion.setLayoutData(gridData21);
labelLabels=new Label(compositeAboutManufacturing, SWT.NONE);
labelLabels.setText(Messages.getString("CompositeAbout.2"));
labelLabelsVersion=new Label(compositeAboutManufacturing, SWT.NONE);
labelLabelsVersion.setText(Versions.manLabels);
labelLabelsVersion.setLayoutData(gridData22);
labelFPK=new Label(compositeAboutManufacturing, SWT.NONE);
labelFPK.setText("FPK:");
labelFPKVersion=new Label(compositeAboutManufacturing, SWT.NONE);
labelFPKVersion.setText(Versions.manFpk);
labelFPKVersion.setLayoutData(gridData23);
}
private void customize()
{
boolean manufacturing=GUIHelper.prefs.getBoolean("ACTIVATE_MANUFACTURING", false);
boolean repair=GUIHelper.prefs.getBoolean("ACTIVATE_REPAIR", false);
compositeAboutBasic.setVisible(false);
compositeAboutManufacturing.setVisible(manufacturing||repair);
}
private void createCompositeAboutBasic()
{
GridData gridData=new GridData();
gridData.horizontalSpan=2;
gridData.verticalAlignment=GridData.CENTER;
gridData.horizontalAlignment=GridData.CENTER;
GridData gridData14=new GridData();
gridData14.horizontalAlignment=GridData.END;
gridData14.verticalAlignment=GridData.CENTER;
GridLayout gridLayout2=new GridLayout();
gridLayout2.horizontalSpacing=20;
gridLayout2.numColumns=2;
gridLayout2.verticalSpacing=10;
GridData gridData5=new GridData();
gridData5.horizontalSpan=2;
gridData5.verticalAlignment=GridData.CENTER;
gridData5.horizontalAlignment=GridData.CENTER;
compositeAboutBasic=new Composite(this, SWT.NONE);
compositeAboutBasic.setLayoutData(gridData5);
compositeAboutBasic.setLayout(gridLayout2);
labelRevision=new Label(compositeAboutBasic, SWT.NONE);
labelRevision.setText("Revision Numbers");
labelRevision.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.BOLD));
labelRevision.setLayoutData(gridData);
createCompositeAboutManufacturing();
}
private void createCompositeAbout()
{
GridData gridData17=new GridData();
gridData17.horizontalAlignment=GridData.CENTER;
gridData17.verticalAlignment=GridData.CENTER;
GridData gridData13=new GridData();
gridData13.horizontalAlignment=GridData.CENTER;
gridData13.verticalAlignment=GridData.CENTER;
GridData gridData2=new GridData();
gridData2.horizontalAlignment=GridData.CENTER;
gridData2.verticalAlignment=GridData.CENTER;
GridData gridData16=new GridData();
gridData16.horizontalAlignment=GridData.CENTER;
gridData16.verticalAlignment=GridData.CENTER;
GridData gridData12=new GridData();
gridData12.horizontalAlignment=GridData.CENTER;
gridData12.verticalAlignment=GridData.CENTER;
GridData gridData11=new GridData();
gridData11.horizontalAlignment=GridData.CENTER;
gridData11.verticalAlignment=GridData.CENTER;
GridData gridData10=new GridData();
gridData10.horizontalAlignment=GridData.CENTER;
gridData10.verticalAlignment=GridData.CENTER;
GridData gridData9=new GridData();
gridData9.horizontalAlignment=GridData.CENTER;
gridData9.verticalAlignment=GridData.CENTER;
GridData gridData8=new GridData();
gridData8.horizontalAlignment=GridData.CENTER;
gridData8.verticalAlignment=GridData.CENTER;
GridData gridData7=new GridData();
gridData7.horizontalAlignment=GridData.CENTER;
gridData7.verticalAlignment=GridData.CENTER;
GridData gridData6=new GridData();
gridData6.horizontalAlignment=GridData.CENTER;
gridData6.horizontalSpan=2;
gridData6.verticalAlignment=GridData.CENTER;
GridLayout gridLayout3=new GridLayout();
gridLayout3.numColumns=1;
gridLayout3.verticalSpacing=10;
gridLayout3.horizontalSpacing=20;
compositeAbout=new Composite(this, SWT.BORDER);
compositeAbout.setLayout(gridLayout3);
compositeAbout.setLayoutData(gridData6);
labelLogo=new Label(compositeAbout, SWT.NONE);
labelLogo.setText("Label");
labelLogo.setLayoutData(gridData16);
labelLogo.setImage(new Image(Display.getCurrent(), GUIHelper.myShell.getClass().getClassLoader().getResourceAsStream("com/cognitive/brand/About.gif")));
labelJAdmin=new Label(compositeAbout, SWT.NONE);
labelJAdmin.setText(Messages.getString("CompositeAbout.8").replace("Cognitive", GUIHelper.brand.getProperty("company.name", "CognitiveTPG")));
labelJAdmin.setLayoutData(gridData13);
labelJAdmin.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.BOLD));
labelVersion=new Label(compositeAbout, SWT.NONE);
labelVersion.setText("Version "+Versions.jadmin);
labelVersion.setLayoutData(gridData2);
labelVersion.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.BOLD));
labelCompany=new Label(compositeAbout, SWT.NONE);
labelCompany.setText(GUIHelper.brand.getProperty("company.about", "CognitiveTPG"));
labelCompany.setLayoutData(gridData17);
labelAddress1=new Label(compositeAbout, SWT.NONE);
labelAddress1.setText(GUIHelper.brand.getProperty("company.address1", "CognitiveTPG")+", "+GUIHelper.brand.getProperty("company.address2", "CognitiveTPG"));
labelAddress1.setLayoutData(gridData7);
labelAddress2=new Label(compositeAbout, SWT.NONE);
if(!labelCompany.getText().startsWith("DASCOM"))
{
labelAddress2.setText(GUIHelper.brand.getProperty("company.city", "CognitiveTPG")+", "+GUIHelper.brand.getProperty("company.state", "CognitiveTPG")+" "+GUIHelper.brand.getProperty("company.zip", "CognitiveTPG")+", "+GUIHelper.brand.getProperty("company.country", "CognitiveTPG"));
}
else
{
labelAddress2.setText(GUIHelper.brand.getProperty("company.country", "CognitiveTPG"));
}
labelAddress2.setLayoutData(gridData8);
labelPhone=new Label(compositeAbout, SWT.NONE);
labelPhone.setText(Messages.getString("CompositeAbout.5")+": "+GUIHelper.brand.getProperty("company.phone", "CognitiveTPG"));
labelPhone.setLayoutData(gridData9);
linkEmail=new Link(compositeAbout, SWT.NONE);
linkEmail.setText("<a>"+GUIHelper.brand.getProperty("company.email", "CognitiveTPG")+"</a>");
linkEmail.setLayoutData(gridData10);
linkEmail.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
Program.launch("mailto:"+GUIHelper.brand.getProperty("company.email", "CognitiveTPG"));
}
});
linkUrl=new Link(compositeAbout, SWT.NONE);
linkUrl.setText("<a>"+GUIHelper.brand.getProperty("company.url", "CognitiveTPG")+"</a>");
linkUrl.setLayoutData(gridData11);
linkUrl.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
Program.launch("http://"+GUIHelper.brand.getProperty("company.url", "CognitiveTPG"));
}
});
labelCopyright=new Label(compositeAbout, SWT.NONE);
labelCopyright.setText(Messages.getString("CompositeAbout.6")+" \u00a9 2021 "+GUIHelper.brand.getProperty("company.name", "Cognitive").replaceAll("Tally Dascom", "Dascom")+", "+Messages.getString("CompositeAbout.7"));
labelCopyright.setLayoutData(gridData12);
}
}
