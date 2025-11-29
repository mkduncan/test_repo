public class NetworkConnection implements IPrinterConnection
{
private String address=null;
private int port=-1;
private Socket socket=null;
private DataOutputStream outputStream=null;
private BufferedReader reader=null;
public NetworkConnection(String address, int port)
{
Printer.logger.trace("");
this.address=address;
this.port=port;
}
public void openConnection() throws IOException
{
Printer.logger.trace("");
socket=new Socket();
socket.connect(new InetSocketAddress(address, port), 10000);
outputStream=new DataOutputStream(socket.getOutputStream());
reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
}
public void closeConnection() throws IOException
{
Printer.logger.trace("");
if(null!=outputStream)
{
outputStream.flush();
outputStream.close();
outputStream=null;
}
if(null!=reader)
{
reader.close();
reader=null;
}
if(null!=socket)
{
socket.close();
socket=null;
}
}
public void send(String str) throws IOException
{
Printer.logger.trace(str);
send(str.getBytes());
}
public void send(byte data[]) throws IOException
{
Printer.logger.trace("");
outputStream.write(data);
}
public boolean ready() throws IOException
{
if(reader!=null)
{
return reader.ready();
}
else
{
Printer.logger.error("ready() called on null reader; returning false.");
throw new IOException("ready() called on null reader");
}
}
public String readLine() throws IOException
{
Printer.logger.trace("");
return reader.readLine()+"\n";
}
public ConnectionType getType()
{
return ConnectionType.NETWORK;
}
}
