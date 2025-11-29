public class JAdminDialogMessage
{
private Shell sShell=null;
private Label message=null;
public JAdminDialogMessage(String title)
{
sShell=new Shell(SWT.APPLICATION_MODAL|SWT.TITLE|SWT.BORDER);
sShell.setSize(new Point(334, 138));
sShell.setText(title);
GridData gridData=new GridData();
gridData.horizontalAlignment=GridData.CENTER;
gridData.grabExcessHorizontalSpace=true;
gridData.grabExcessVerticalSpace=true;
gridData.verticalAlignment=GridData.CENTER;
sShell.setLayout(new GridLayout());
message=new Label(sShell, SWT.CENTER|SWT.WRAP);
message.setText(title);
message.setLayoutData(gridData);
show();
}
public void show()
{
Rectangle bounds=GUIHelper.myShell.getBounds();
Rectangle rect=sShell.getBounds();
int x=bounds.x+(bounds.width-rect.width)/2;
int y=bounds.y+(bounds.height-rect.height)/2;
sShell.setLocation(x, y);
sShell.setActive();
sShell.setVisible(true);
GUIHelper.update();
}
public void setMessage(String msg)
{
message.setText(msg);
sShell.layout();
Point currentSize=sShell.getSize();
Point preferredSize=sShell.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
GUIHelper.update();
}
public void closeAfter(int milliseconds)
{
try
{
Thread.sleep(milliseconds);
}
catch(InterruptedException e)
{
GUIHelper.logger.error(e, e);
}
finally
{
sShell.dispose();
}
}
public void hide()
{
sShell.setVisible(false);
}
public void close()
{
sShell.dispose();
}
}
