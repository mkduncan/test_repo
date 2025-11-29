public class TimerTaskHeartbeat extends TimerTask
{
final int DEFAULT_HEARTBEAT_INTERVAL=25000;
final int DEFAUTL_MAX_MISSED_HEARTBEATS=3;
private long interval;
private int maxMissedHeartbeats;
private int missedHeartbeats;
private Display display;
public TimerTaskHeartbeat()
{
super();
GUIHelper.logger.trace("");
interval=GUIHelper.prefs.getLong("HEARTBEAT_INTERVAL", DEFAULT_HEARTBEAT_INTERVAL);
maxMissedHeartbeats=GUIHelper.prefs.getInt("MAX_MISSED_HEARTBEATS", DEFAUTL_MAX_MISSED_HEARTBEATS);
GUIHelper.logger.debug("Heartbeat task created with interval ["+interval+"] and maxMissed ["+maxMissedHeartbeats+"].");
}
public void setDisplay(Display display)
{
this.display=display;
}
public long getInterval()
{
return interval;
}
@Override
public void run()
{
GUIHelper.logger.trace("");
try
{
PrinterHelper.printer.getVariable("WIDTH");
display.syncExec(new Runnable()
{
public void run()
{
try
{
GUIHelper.mainShell.displayConnectionStatus(ConnectionStatus.CONNECTED);
}
catch(SWTException e)
{
GUIHelper.logger.warn(e, e);
return;
}
}
});
}
catch(Exception e)
{
missedHeartbeats++;
GUIHelper.logger.warn("Missed a heartbeat:", e);
display.syncExec(new Runnable()
{
public void run()
{
try
{
GUIHelper.mainShell.displayConnectionStatus(ConnectionStatus.UNKNOWN);
}
catch(SWTException e)
{
GUIHelper.logger.warn(e, e);
return;
}
}
});
}
if(missedHeartbeats>maxMissedHeartbeats)
{
try
{
this.cancel();
PrinterHelper.printer.closeConnection();
display.syncExec(new Runnable()
{
public void run()
{
try
{
GUIHelper.mainShell.setConnectionState(false, false);
}
catch(SWTException e)
{
GUIHelper.logger.warn(e, e);
return;
}
}
});
}
catch(Exception e)
{
GUIHelper.logger.warn("closeConnection failed:", e);
}
}
GUIHelper.logger.trace("Exiting");
}
}
