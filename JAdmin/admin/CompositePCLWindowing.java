public class CompositePCLWindowing extends Composite
{
Logger logger=null;
String pathPJL=null;
String pathPCL=null;
private Group groupDiagram=null;
private Group groupPJLParameters=null;
private Label labelOrigin=null;
private Label labelDiagram=null;
private Label labelInstructions=null;
private Label labelPJLOrigin=null;
private Text textPJLX=null;
private Text textPJLY=null;
private Label labelActualOrigin=null;
private Label labelPJLLength=null;
private Label labelActualLength=null;
private Label labelPJLWidth=null;
private Label labelActualWidth=null;
private Text textPJLLength=null;
private Text textPJLWidth=null;
private Composite compositePJLButtons=null;
private Button buttonSavePJL=null;
private Button buttonLoadPJL=null;
private Button buttonSendPJL=null;
private Group groupPrintPCL=null;
private Button buttonSelectFile=null;
private Button buttonSendFile=null;
private Text textPCLFile=null;
private Label labelActualX=null;
private Label labelActualY=null;
private Label labelActualLengthNumber=null;
private Label labelActualWidthNumber=null;
private Label filler2=null;
private Label filler6=null;
private Label filler12=null;
private Label filler16=null;
public CompositePCLWindowing(Composite parent, int style)
{
super(parent, style);
initialize();
}
private void initialize()
{
GridData gridData16=new GridData();
gridData16.grabExcessHorizontalSpace=true;
GridData gridData15=new GridData();
gridData15.grabExcessHorizontalSpace=true;
Label filler13=new Label(this, SWT.NONE);
GridData gridData14=new GridData();
gridData14.grabExcessVerticalSpace=true;
GridData gridData13=new GridData();
gridData13.grabExcessVerticalSpace=true;
filler2=new Label(this, SWT.NONE);
filler2.setText("");
filler2.setLayoutData(gridData13);
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=4;
Label filler3=new Label(this, SWT.NONE);
Label filler17=new Label(this, SWT.NONE);
filler12=new Label(this, SWT.NONE);
filler12.setText("");
filler12.setLayoutData(gridData15);
createGroupDiagram();
this.setLayout(gridLayout);
setSize(new Point(683, 497));
createGroupPJLParameters();
filler16=new Label(this, SWT.NONE);
filler16.setText("");
filler16.setLayoutData(gridData16);
Label filler10=new Label(this, SWT.NONE);
createGroupPrintPCL();
Label filler15=new Label(this, SWT.NONE);
Label filler9=new Label(this, SWT.NONE);
Label filler4=new Label(this, SWT.NONE);
Label filler14=new Label(this, SWT.NONE);
Label filler7=new Label(this, SWT.NONE);
filler6=new Label(this, SWT.NONE);
filler6.setText("");
filler6.setLayoutData(gridData14);
}
private void createGroupDiagram()
{
GridData gridData269=new GridData();
gridData269.horizontalAlignment=GridData.FILL;
gridData269.verticalAlignment=GridData.CENTER;
GridData gridData266=new GridData();
GridLayout gridLayout1=new GridLayout();
gridLayout1.numColumns=2;
gridLayout1.marginHeight=15;
gridLayout1.horizontalSpacing=5;
gridLayout1.marginWidth=8;
GridData gridData=new GridData();
gridData.verticalSpan=3;
gridData.verticalAlignment=GridData.FILL;
gridData.horizontalAlignment=GridData.FILL;
groupDiagram=new Group(this, SWT.NONE);
groupDiagram.setLayoutData(gridData);
groupDiagram.setLayout(gridLayout1);
groupDiagram.setText("Label Window Within Standard Page");
labelOrigin=new Label(groupDiagram, SWT.NONE);
labelOrigin.setText("(0,0)");
labelOrigin.setLayoutData(gridData269);
labelOrigin.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.ITALIC));
Label filler270=new Label(groupDiagram, SWT.NONE);
Label filler1=new Label(groupDiagram, SWT.NONE);
labelDiagram=new Label(groupDiagram, SWT.NONE);
labelDiagram.setText("Label");
labelDiagram.setLayoutData(gridData266);
labelDiagram.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/com/cognitive/admin/PCLPaper.png")));
}
private void createGroupPJLParameters()
{
GridData gridData24=new GridData();
gridData24.horizontalAlignment=GridData.FILL;
gridData24.verticalAlignment=GridData.CENTER;
GridData gridData23=new GridData();
gridData23.horizontalAlignment=GridData.FILL;
gridData23.verticalAlignment=GridData.CENTER;
GridData gridData22=new GridData();
gridData22.horizontalAlignment=GridData.FILL;
gridData22.grabExcessHorizontalSpace=true;
gridData22.verticalAlignment=GridData.CENTER;
GridData gridData21=new GridData();
gridData21.horizontalAlignment=GridData.FILL;
gridData21.grabExcessHorizontalSpace=true;
gridData21.verticalAlignment=GridData.CENTER;
GridData gridData20=new GridData();
gridData20.horizontalAlignment=GridData.FILL;
gridData20.verticalAlignment=GridData.CENTER;
GridData gridData19=new GridData();
gridData19.horizontalAlignment=GridData.FILL;
gridData19.verticalAlignment=GridData.CENTER;
GridData gridData18=new GridData();
gridData18.horizontalAlignment=GridData.FILL;
gridData18.verticalAlignment=GridData.CENTER;
GridData gridData17=new GridData();
gridData17.horizontalAlignment=GridData.FILL;
gridData17.verticalAlignment=GridData.CENTER;
GridData gridData6=new GridData();
gridData6.horizontalAlignment=GridData.END;
gridData6.verticalAlignment=GridData.CENTER;
GridData gridData5=new GridData();
gridData5.horizontalAlignment=GridData.END;
gridData5.verticalAlignment=GridData.CENTER;
GridData gridData4=new GridData();
gridData4.horizontalAlignment=GridData.END;
gridData4.verticalAlignment=GridData.CENTER;
GridData gridData3=new GridData();
gridData3.horizontalSpan=4;
GridLayout gridLayout2=new GridLayout();
gridLayout2.numColumns=4;
gridLayout2.marginHeight=13;
gridLayout2.verticalSpacing=15;
GridData gridData2=new GridData();
gridData2.horizontalAlignment=GridData.FILL;
gridData2.verticalAlignment=GridData.FILL;
groupPJLParameters=new Group(this, SWT.NONE);
groupPJLParameters.setLayoutData(gridData2);
groupPJLParameters.setLayout(gridLayout2);
groupPJLParameters.setText(Messages.getString("CompositePCLWindowing.15"));
labelInstructions=new Label(groupPJLParameters, SWT.NONE);
labelInstructions.setText(Messages.getString("CompositePCLWindowing.16"));
labelInstructions.setLayoutData(gridData3);
labelPJLOrigin=new Label(groupPJLParameters, SWT.NONE);
labelPJLOrigin.setText(Messages.getString("CompositePCLWindowing.17"));
textPJLX=new Text(groupPJLParameters, SWT.BORDER);
textPJLX.setLayoutData(gridData21);
textPJLX.addFocusListener(new org.eclipse.swt.events.FocusAdapter()
{
public void focusGained(org.eclipse.swt.events.FocusEvent e)
{
labelActualX.setText("");
}
public void focusLost(org.eclipse.swt.events.FocusEvent e)
{
displayActual(textPJLX, labelActualX);
}
});
textPJLY=new Text(groupPJLParameters, SWT.BORDER);
textPJLY.setLayoutData(gridData22);
textPJLY.addFocusListener(new org.eclipse.swt.events.FocusAdapter()
{
public void focusGained(org.eclipse.swt.events.FocusEvent e)
{
labelActualY.setText("");
}
public void focusLost(org.eclipse.swt.events.FocusEvent e)
{
displayActual(textPJLY, labelActualY);
}
});
Label filler2264=new Label(groupPJLParameters, SWT.NONE);
labelActualOrigin=new Label(groupPJLParameters, SWT.RIGHT);
labelActualOrigin.setText(Messages.getString("CompositePCLWindowing.4"));
labelActualOrigin.setLayoutData(gridData4);
labelActualOrigin.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.ITALIC));
labelActualX=new Label(groupPJLParameters, SWT.CENTER);
labelActualX.setText("");
labelActualX.setLayoutData(gridData17);
labelActualX.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.ITALIC));
labelActualY=new Label(groupPJLParameters, SWT.CENTER);
labelActualY.setText("");
labelActualY.setLayoutData(gridData18);
labelActualY.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.ITALIC));
Label filler=new Label(groupPJLParameters, SWT.NONE);
labelPJLLength=new Label(groupPJLParameters, SWT.NONE);
labelPJLLength.setText(Messages.getString("CompositePCLWindowing.7"));
textPJLLength=new Text(groupPJLParameters, SWT.BORDER);
textPJLLength.setLayoutData(gridData23);
textPJLLength.addFocusListener(new org.eclipse.swt.events.FocusAdapter()
{
public void focusGained(org.eclipse.swt.events.FocusEvent e)
{
labelActualLengthNumber.setText("");
}
public void focusLost(org.eclipse.swt.events.FocusEvent e)
{
displayActual(textPJLLength, labelActualLengthNumber);
}
});
Label filler5=new Label(groupPJLParameters, SWT.NONE);
Label filler2263=new Label(groupPJLParameters, SWT.NONE);
labelActualLength=new Label(groupPJLParameters, SWT.RIGHT);
labelActualLength.setText(Messages.getString("CompositePCLWindowing.4"));
labelActualLength.setLayoutData(gridData5);
labelActualLength.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.ITALIC));
labelActualLengthNumber=new Label(groupPJLParameters, SWT.CENTER);
labelActualLengthNumber.setText("");
labelActualLengthNumber.setLayoutData(gridData19);
labelActualLengthNumber.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.ITALIC));
Label filler8=new Label(groupPJLParameters, SWT.NONE);
Label filler2262=new Label(groupPJLParameters, SWT.NONE);
labelPJLWidth=new Label(groupPJLParameters, SWT.NONE);
labelPJLWidth.setText(Messages.getString("CompositePCLWindowing.5"));
textPJLWidth=new Text(groupPJLParameters, SWT.BORDER);
textPJLWidth.setLayoutData(gridData24);
textPJLWidth.addFocusListener(new org.eclipse.swt.events.FocusAdapter()
{
public void focusGained(org.eclipse.swt.events.FocusEvent e)
{
labelActualWidthNumber.setText("");
}
public void focusLost(org.eclipse.swt.events.FocusEvent e)
{
displayActual(textPJLWidth, labelActualWidthNumber);
}
});
Label filler11=new Label(groupPJLParameters, SWT.NONE);
Label filler2261=new Label(groupPJLParameters, SWT.NONE);
labelActualWidth=new Label(groupPJLParameters, SWT.RIGHT);
labelActualWidth.setText(Messages.getString("CompositePCLWindowing.4"));
labelActualWidth.setLayoutData(gridData6);
labelActualWidth.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.ITALIC));
labelActualWidthNumber=new Label(groupPJLParameters, SWT.CENTER);
labelActualWidthNumber.setText("");
labelActualWidthNumber.setLayoutData(gridData20);
labelActualWidthNumber.setFont(new Font(Display.getDefault(), "Tahoma", 8, SWT.ITALIC));
Label filler2259=new Label(groupPJLParameters, SWT.NONE);
Label filler2260=new Label(groupPJLParameters, SWT.NONE);
createCompositePJLButtons();
}
private void createCompositePJLButtons()
{
GridData gridData9=new GridData();
gridData9.horizontalAlignment=GridData.CENTER;
gridData9.grabExcessHorizontalSpace=true;
gridData9.verticalAlignment=GridData.CENTER;
GridData gridData8=new GridData();
gridData8.horizontalAlignment=GridData.CENTER;
gridData8.grabExcessHorizontalSpace=true;
gridData8.verticalAlignment=GridData.CENTER;
GridData gridData7=new GridData();
gridData7.horizontalAlignment=GridData.CENTER;
gridData7.grabExcessHorizontalSpace=true;
gridData7.verticalAlignment=GridData.CENTER;
GridLayout gridLayout3=new GridLayout();
gridLayout3.numColumns=3;
GridData gridData1=new GridData();
gridData1.horizontalSpan=4;
gridData1.verticalAlignment=GridData.FILL;
gridData1.horizontalAlignment=GridData.FILL;
compositePJLButtons=new Composite(groupPJLParameters, SWT.NONE);
compositePJLButtons.setLayoutData(gridData1);
compositePJLButtons.setLayout(gridLayout3);
buttonSavePJL=new Button(compositePJLButtons, SWT.NONE);
buttonSavePJL.setText(Messages.getString("CompositePCLWindowing.3"));
buttonSavePJL.setLayoutData(gridData9);
buttonSavePJL
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
savePJLFile();
}
});
buttonLoadPJL=new Button(compositePJLButtons, SWT.NONE);
buttonLoadPJL.setText(Messages.getString("CompositePCLWindowing.2"));
buttonLoadPJL.setLayoutData(gridData8);
buttonLoadPJL
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
openPJLFile();
}
});
buttonSendPJL=new Button(compositePJLButtons, SWT.NONE);
buttonSendPJL.setText(Messages.getString("CompositePCLWindowing.1"));
buttonSendPJL.setLayoutData(gridData7);
buttonSendPJL
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
sendPJLToPrinter();
}
});
}
private void createGroupPrintPCL()
{
GridData gridData12=new GridData();
gridData12.horizontalAlignment=GridData.END;
gridData12.verticalAlignment=GridData.CENTER;
GridData gridData11=new GridData();
gridData11.horizontalSpan=2;
gridData11.verticalAlignment=GridData.CENTER;
gridData11.grabExcessHorizontalSpace=true;
gridData11.horizontalAlignment=GridData.FILL;
GridLayout gridLayout4=new GridLayout();
gridLayout4.numColumns=2;
gridLayout4.marginHeight=17;
gridLayout4.verticalSpacing=15;
gridLayout4.marginWidth=5;
GridData gridData10=new GridData();
gridData10.horizontalAlignment=GridData.FILL;
gridData10.verticalAlignment=GridData.FILL;
groupPrintPCL=new Group(this, SWT.NONE);
groupPrintPCL.setText(Messages.getString("CompositePCLWindowing.0"));
groupPrintPCL.setLayout(gridLayout4);
groupPrintPCL.setLayoutData(gridData10);
buttonSelectFile=new Button(groupPrintPCL, SWT.NONE);
buttonSelectFile.setText(Messages.getString("CompositePCLWindowing.18"));
buttonSelectFile
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
selectPCLFile();
}
});
buttonSendFile=new Button(groupPrintPCL, SWT.NONE);
buttonSendFile.setText(Messages.getString("CompositePCLWindowing.19"));
buttonSendFile.setLayoutData(gridData12);
buttonSendFile
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
sendPCLFile();
}
});
textPCLFile=new Text(groupPrintPCL, SWT.BORDER);
textPCLFile.setLayoutData(gridData11);
}
final float DECIPOINTS_PER_INCH=720;
private String parsePJLLine(String line)
{
String val;
int tmpInt;
float tmpFloat;
val=line.substring(line.indexOf("=")+1);
tmpInt=Integer.parseInt(val);
tmpFloat=((float)(tmpInt))/DECIPOINTS_PER_INCH;
return Float.toString(tmpFloat);
}
private void parsePJLFile(BufferedReader br)
{
String line;
try
{
while((line=br.readLine())!=null)
{
if(line.contains("PAGECLIPX"))
{
textPJLX.setText(parsePJLLine(line));
labelActualX.setText(textPJLX.getText());
}
else if(line.contains("PAGECLIPY"))
{
textPJLY.setText(parsePJLLine(line));
labelActualY.setText(textPJLY.getText());
}
else if(line.contains("PAGECLIPWIDTH"))
{
textPJLWidth.setText(parsePJLLine(line));
labelActualWidthNumber.setText(textPJLWidth.getText());
}
else if(line.contains("PAGECLIPLENGTH"))
{
textPJLLength.setText(parsePJLLine(line));
labelActualLengthNumber.setText(textPJLLength.getText());
}
else
{
}
}
}
catch(Exception e)
{
}
}
private void openPJLFile()
{
if(pathPJL==null)
{
pathPJL=GUIHelper.prefs.get("PATH_PJL", ".");
}
StringBuffer pathUsed=new StringBuffer("");
BufferedReader br=GUIHelper.openTextFile(pathPJL, pathUsed);
if(br==null)
{
return;
}
pathPJL=pathUsed.toString();
GUIHelper.prefs.put("PATH_PJL", pathPJL);
parsePJLFile(br);
try
{
br.close();
}
catch(Exception e)
{
}
}
private String strInchesToStrDecipoints(String inches) throws Exception
{
int decipoints=(int)(Float.parseFloat(inches)*DECIPOINTS_PER_INCH);
return Integer.toString(decipoints);
}
private String strInchesToStrDecipointsAndBack(String inches)
{
int decipoints=(int)(Float.parseFloat(inches)*DECIPOINTS_PER_INCH);
float actual=decipoints/DECIPOINTS_PER_INCH;
return Float.toString(actual);
}
private String generatePJL()
{
StringBuffer script=new StringBuffer("");
char escape=27;
try
{
script.append(escape);
script.append("%-12345X@PJL\r\n");
script.append("@PJL COMMENT \"Generated by JAdmin\"\r\n");
script.append("@PJL DEFAULT PAPER=LETTER\r\n");
script.append("@PJL DEFAULT PAPERSIZEOVERRIDE=OFF\r\n");
script.append("@PJL DEFAULT PAGECLIPPING=ON\r\n");
script.append("@PJL DEFAULT PAGECLIPX="+strInchesToStrDecipoints(textPJLX.getText())+"\r\n");
script.append("@PJL DEFAULT PAGECLIPY="+strInchesToStrDecipoints(textPJLY.getText())+"\r\n");
script.append("@PJL DEFAULT PAGECLIPWIDTH="+strInchesToStrDecipoints(textPJLWidth.getText())+"\r\n");
script.append("@PJL DEFAULT PAGECLIPLENGTH="+strInchesToStrDecipoints(textPJLLength.getText())+"\r\n");
script.append("@PJL RESET\r\n");
script.append(escape);
script.append("%-12345X\r\n");
}
catch(Exception e)
{
return null;
}
return script.toString();
}
private void savePJLFile()
{
if(pathPJL==null)
{
pathPJL=GUIHelper.prefs.get("PATH_PJL", ".");
}
StringBuffer pathUsed=new StringBuffer("");
String pjl=generatePJL();
if(pjl==null)
{
GUIHelper.error(Messages.getString("CompositePCLWindowing.20"));
return;
}
pathPJL=GUIHelper.saveTextFile(pjl, pathPJL);
GUIHelper.prefs.put("PATH_PJL", pathPJL);
}
private void displayActual(Text text, Label label)
{
try
{
String given=text.getText();
String actual=strInchesToStrDecipointsAndBack(given);
String formatted=String.format("%6.3f", Float.parseFloat(actual));
label.setText(formatted);
label.setForeground(Display.getCurrent().getSystemColor(SWT.DEFAULT));
}
catch(Exception e)
{
label.setText("???");
label.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
}
}
private void printPCLWindowBox()
{
String pitch=PrinterHelper.getVariable("PITCH");
if(pitch==null)
{
return;
}
int iPitch=Integer.parseInt(pitch);
int widthDots=(int)(Float.parseFloat(textPJLWidth.getText())*iPitch);
int lengthDots=(int)(Float.parseFloat(textPJLLength.getText())*iPitch);
StringBuffer boxLabel=new StringBuffer();
boxLabel.append("!0 100 "+lengthDots+" 1\n");
boxLabel.append("DRAW_BOX 0 0 ");
boxLabel.append(widthDots+" ");
boxLabel.append(lengthDots+" 3 \n");
boxLabel.append("U B24 (3,0,0) 20 20 Box length="+textPJLLength.getText()+" inches.\n");
boxLabel.append("U B24 (3,0,0) 20 60 Box width="+textPJLWidth.getText()+" inches.\n");
boxLabel.append("END\n");
PrinterHelper.send(boxLabel.toString());
}
private void sendPJLToPrinter()
{
String pjl=generatePJL();
if(pjl==null)
{
GUIHelper.error(Messages.getString("CompositePCLWindowing.20"));
return;
}
printPCLWindowBox();
PrinterHelper.send(pjl);
}
private void selectPCLFile()
{
if(pathPCL==null)
{
pathPCL=GUIHelper.prefs.get("PATH_PCL", ".");
}
FileDialog fd=new FileDialog(GUIHelper.myShell, SWT.OPEN);
fd.setFilterPath(pathPJL);
String pclFileName=fd.open();
if(pclFileName==null)
{
return;
}
pathPCL=fd.getFilterPath();
GUIHelper.prefs.put("PATH_PCL", pathPCL);
textPCLFile.setText(pclFileName);
}
private void sendPCLFile()
{
String fileName=textPCLFile.getText();
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
PrinterHelper.send(data);
}
public void setConnectionState(boolean connected, boolean dumbTerminal)
{
logger=GUIHelper.logger;
logger.trace("");
GUIHelper.setEnabled(this, connected);
GUIHelper.setEnabled(groupPJLParameters, true);
buttonSendPJL.setEnabled(connected);
}
}
