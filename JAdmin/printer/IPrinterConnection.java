public interface IPrinterConnection
{
void openConnection() throws IOException,
PortInUseException,
UnsupportedCommOperationException,
NativeLibraryException;
void closeConnection() throws IOException, NativeLibraryException;
void send(String str) throws IOException, NativeLibraryException;
void send(byte data[]) throws IOException, NativeLibraryException;
boolean ready() throws IOException, NativeLibraryException;
String readLine() throws IOException, NativeLibraryException;
ConnectionType getType();
public enum ConnectionType
{
SERIAL,
PARALLEL,
OS_PRINTER,
NETWORK,
UNKNOWN
}
}
