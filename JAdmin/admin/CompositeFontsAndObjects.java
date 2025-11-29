public class CompositeFontsAndObjects extends Composite
{
String pathObjects=null;
private boolean connected=false;
private boolean dumbTerminal=false;
private boolean firstPaintSinceConnected=true;
private Logger logger=null;
private Table tableObjects=null;
private Button buttonSendObjects=null;
private Button buttonDeleteSelected=null;
private Button buttonRefresh=null;
private ProgressBar progressBar=null;
static Label labelNote=null;
public CompositeFontsAndObjects(Composite parent, int style)
{
super(parent, style);
initialize();
}
private void initialize()
{
GridData gridData2=new GridData();
gridData2.horizontalAlignment=GridData.FILL;
gridData2.horizontalSpan=6;
gridData2.verticalSpan=2;
gridData2.verticalAlignment=GridData.FILL;
GridData gridData1=new GridData();
gridData1.horizontalAlignment=GridData.FILL;
gridData1.verticalAlignment=GridData.CENTER;
GridLayout gridLayout=new GridLayout();
gridLayout.numColumns=6;
GridData gridData=new GridData();
gridData.horizontalAlignment=GridData.FILL;
gridData.grabExcessHorizontalSpace=true;
gridData.grabExcessVerticalSpace=true;
gridData.horizontalSpan=6;
gridData.verticalAlignment=GridData.FILL;
tableObjects=new Table(this, SWT.MULTI|SWT.FULL_SELECTION);
tableObjects.setHeaderVisible(true);
tableObjects.setLayoutData(gridData);
tableObjects.setLinesVisible(true);
buttonSendObjects=new Button(this, SWT.NONE);
buttonSendObjects.setText(Messages.getString("CompositeFontsAndObjects.0"));
Label filler1=new Label(this, SWT.NONE);
buttonSendObjects
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
sendObjects();
}
});
buttonDeleteSelected=new Button(this, SWT.NONE);
buttonDeleteSelected.setText(Messages.getString("CompositeFontsAndObjects.1"));
buttonDeleteSelected
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
deleteSelectedObjects();
}
});
buttonRefresh=new Button(this, SWT.NONE);
buttonRefresh.setText(Messages.getString("General.1"));
buttonRefresh
.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
refresh();
}
});
Label filler=new Label(this, SWT.NONE);
progressBar=new ProgressBar(this, SWT.NONE);
progressBar.setLayoutData(gridData1);
labelNote=new Label(this, SWT.NONE);
labelNote.setText(Messages.getString("CompositeFontsAndObjects.12"));
labelNote.setLayoutData(gridData2);
TableColumn tableColumnName=new TableColumn(tableObjects, SWT.NONE);
tableColumnName.setWidth(100);
tableColumnName.setText(Messages.getString("CompositeFontsAndObjects.3"));
TableColumn tableColumnType=new TableColumn(tableObjects, SWT.NONE);
tableColumnType.setWidth(100);
tableColumnType.setText(Messages.getString("CompositeFontsAndObjects.4"));
TableColumn tableColumnStorage=new TableColumn(tableObjects, SWT.NONE);
tableColumnStorage.setWidth(100);
tableColumnStorage.setText(Messages.getString("CompositeFontsAndObjects.5"));
TableColumn tableColumnSize=new TableColumn(tableObjects, SWT.NONE);
tableColumnSize.setWidth(100);
tableColumnSize.setText(Messages.getString("CompositeFontsAndObjects.6"));
TableColumn tableColumnDescription=new TableColumn(tableObjects, SWT.NONE);
tableColumnDescription.setWidth(200);
tableColumnDescription.setText(Messages.getString("CompositeFontsAndObjects.7"));
this.setLayout(gridLayout);
this.setSize(new Point(734, 200));
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
private void refresh()
{
logger.trace("");
displayObjectList(GUIHelper.getParsedObjectList());
layout(true, true);
}
private String[] selectObjects()
{
logger.trace("");
if(pathObjects==null)
{
pathObjects=GUIHelper.prefs.get("PATH_OBJECTS", ".");
}
FileDialog fd=null;
try
{
fd=new FileDialog(Display.getCurrent().getShells()[0], SWT.OPEN|SWT.MULTI);
fd.setFilterPath(pathObjects);
if(fd.open()==null)
{
return null;
}
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("CompositeFontsAndObjects.8"));
return null;
}
String[] fileNames=fd.getFileNames();
pathObjects=fd.getFilterPath()+"/";
GUIHelper.prefs.put("PATH_OBJECTS", pathObjects);
return fileNames;
}
private void sendGenericObject(String objectName, byte[] data)
{
logger.trace("");
boolean success=false;
PrinterHelper.send(data);
success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
if(!success)
{
GUIHelper.error(Messages.getString("CompositeFontsAndObjects.9"));
}
}
private void sendImage(String objectName, String type, byte[] data)
{
logger.trace("");
int dotIndex=objectName.indexOf(".");
if(dotIndex!=-1)
{
objectName=objectName.substring(0, dotIndex);
}
if(objectName.length()>8)
{
objectName=objectName.substring(0, 8);
}
objectName=objectName.replace(' ', '_');
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nGRAPHIC STORE "+type+" 3 "+objectName);
PrinterHelper.send(data);
boolean success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
if(!success)
{
GUIHelper.error(Messages.getString("CompositeFontsAndObjects.10"));
}
PrinterHelper.send("!!0 100 400 1\n!0 100 400 1\nGRAPHIC RECALL "+objectName+" 30 30\nEND");
}
private void sendObject(String fileName, byte[] data)
{
logger.trace("");
String fileType=fileName.substring(fileName.indexOf(".")+1);
if(fileType.equalsIgnoreCase("BMP")||fileType.equalsIgnoreCase("PCX"))
{
sendImage(fileName, fileType, data);
}
else
{
sendGenericObject(fileName, data);
}
}
private void sendObjects()
{
logger.trace("");
String[] fileNames=selectObjects();
if(fileNames!=null)
{
int numFiles=fileNames.length;
int currentFile=0;
progressBar.setMaximum(numFiles);
progressBar.setMinimum(currentFile);
PrinterHelper.setVariable("USER_FEEDBACK", "ON");
boolean success;
byte[] data=null;
for(String fileName : fileNames)
{
GUIHelper.logger.debug(fileName);
progressBar.setSelection(++currentFile);
progressBar.setToolTipText(fileName);
try
{
data=GUIHelper.readBinaryFile(pathObjects+fileName);
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("CompositeFontsAndObjects.11")+pathObjects+fileName);
}
if(fileNames.length>1)
{
try
{
Thread.sleep(2000);
}
catch(Exception ex)
{
success=false;
}
}
sendObject(fileName, data);
}
progressBar.setSelection(0);
refresh();
}
}
private void displayObjectList(Vector<String[]>parsedObjectList)
{
GUIHelper.logger.trace("");
tableObjects.removeAll();
if(!parsedObjectList.isEmpty())
{
for(String[] parsedObject : parsedObjectList)
{
addObjectToTable(parsedObject);
}
}
}
private void addObjectToTable(String[] object)
{
if(object!=null)
{
TableItem item=new TableItem(tableObjects, SWT.NONE);
item.setText(object);
tableObjects.update();
}
}
private void deleteSelectedObjects()
{
logger.trace("");
TableItem[] selectedItems=tableObjects.getSelection();
if(selectedItems==null)
{
return;
}
int max=selectedItems.length;
int current=0;
logger.debug(max+" items selected for deletion.");
progressBar.setMaximum(max);
progressBar.setMinimum(current);
String itemName=null;
boolean supportsFontCheckCommand=false;
String command="";
String currentFirmwarePartNumber=TestEngine.getCurrentFirmwarePartNumber();
if(Integer.parseInt(currentFirmwarePartNumber.split("-")[2])>156)
{
supportsFontCheckCommand=true;
}
if(supportsFontCheckCommand)
{
command="!OBJECT MARK ";
}
else
{
command="!D ";
}
for(TableItem item : selectedItems)
{
progressBar.setSelection(++current);
itemName=item.getText(0);
PrinterHelper.send(command+itemName);
if(!supportsFontCheckCommand)
{
PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
}
}
if(supportsFontCheckCommand)
{
GUIHelper.logger.info("Deleting all the fonts marked..");
PrinterHelper.send("!OBJECT PACK");
PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
}
progressBar.setSelection(0);
refresh();
}
}
