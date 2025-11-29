public class TimerTaskCPL extends TimerTask
{
private int repetitions;
private int current;
private String CPLString;
private Label labelCounter;
private HasCallback hasCallback;
public TimerTaskCPL(int repetitions, int current, String CPLString, Label labelCounter, HasCallback hasCallback)
{
super();
GUIHelper.logger.trace("");
this.CPLString=CPLString;
this.repetitions=repetitions;
this.current=current;
this.labelCounter=labelCounter;
this.hasCallback=hasCallback;
}
@Override
public void run()
{
GUIHelper.logger.trace("Repetition "+current);
try
{
PrinterHelper.printer.send(CPLString+"\n");
GUIHelper.myDisplay.syncExec(new Runnable()
{
public void run()
{
labelCounter.setText(current+"/"+repetitions);
}
});
}
catch(Exception e)
{
GUIHelper.logger.warn("Failed sending: "+e.toString(), e);
}
if(current==repetitions)
{
GUIHelper.myDisplay.syncExec(new Runnable()
{
public void run()
{
hasCallback.callback();
}
});
}
current++;
GUIHelper.logger.trace("Exiting");
}
}
