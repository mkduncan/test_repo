public class JAdminDialogRadio
{
private Shell sShell=null;
private Button buttonOk=null;
private Button buttonCancel=null;
private boolean done=false;
private int selectedButton=-1;
private Vector<Button>radioButtons=null;
public JAdminDialogRadio(String title)
{
sShell=new Shell(SWT.APPLICATION_MODAL|SWT.TITLE|SWT.BORDER);
sShell.setSize(new Point(334, 138));
sShell.setText(title);
RowLayout rowLayout=new RowLayout();
rowLayout.type=org.eclipse.swt.SWT.VERTICAL;
rowLayout.justify=true;
rowLayout.fill=true;
rowLayout.marginBottom=10;
rowLayout.marginHeight=10;
rowLayout.marginLeft=10;
rowLayout.marginRight=10;
rowLayout.marginTop=10;
rowLayout.marginWidth=10;
sShell.setLayout(rowLayout);
radioButtons=new Vector<Button>();
}
public void addOption(String option)
{
Button button=new Button(sShell, SWT.RADIO);
button.setText(option);
button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
buttonOk.setEnabled(true);
}
});
radioButtons.add(button);
sShell.pack(true);
}
private void addOkCancel()
{
RowLayout rowLayout=new RowLayout();
rowLayout.type=org.eclipse.swt.SWT.HORIZONTAL;
rowLayout.justify=true;
rowLayout.fill=true;
rowLayout.marginBottom=0;
rowLayout.marginHeight=0;
rowLayout.marginLeft=10;
rowLayout.marginRight=10;
rowLayout.marginTop=10;
rowLayout.marginWidth=10;
Composite actionButtons=new Composite(sShell, SWT.NONE);
actionButtons.setLayout(rowLayout);
buttonOk=new Button(actionButtons, SWT.PUSH);
buttonOk.setText("  OK  ");
buttonOk.setEnabled(false);
buttonOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
done=true;
selectedButton=findSelectedButton();
}
});
buttonCancel=new Button(actionButtons, SWT.PUSH);
buttonCancel.setText("Cancel");
buttonCancel.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
{
public void widgetSelected(org.eclipse.swt.events.SelectionEvent e)
{
done=true;
selectedButton=-1;
}
});
actionButtons.pack(true);
sShell.pack(true);
}
private void show()
{
addOkCancel();
Rectangle bounds=GUIHelper.myShell.getBounds();
Rectangle rect=sShell.getBounds();
int x=bounds.x+(bounds.width-rect.width)/2;
int y=bounds.y+(bounds.height-rect.height)/2;
sShell.setLocation(x, y);
sShell.setActive();
sShell.setVisible(true);
}
public int waitForSelection()
{
show();
while(!done)
{
try
{
Thread.sleep(50);
GUIHelper.update();
}
catch(InterruptedException e)
{
GUIHelper.logger.error(e, e);
sShell.dispose();
return-1;
}
}
sShell.dispose();
return selectedButton;
}
private int findSelectedButton()
{
for(int i=0; i<radioButtons.size(); i++)
if(radioButtons.elementAt(i).getSelection())
return i;
return-1;
}
}
