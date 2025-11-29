public class CPLParser
{
static public String clean(String str)
{
Printer.logger.trace("");
str=str.replace('\n', ' ');
str=str.replace('\r', ' ');
str=str.trim();
return str;
}
static public String getResponseValue(String response, String command) throws CPLParsingException
{
Printer.logger.trace("");
String retStr=null;
String tmp=command.replace('\n', ' ');
tmp=tmp.replace('\r', ' ');
tmp=tmp.trim();
tmp=tmp.toUpperCase();
if(clean(response).matches(".*=.*"))
{
if(tmp.matches(".*VARIABLE .*?.*")||tmp.matches("V .*?.*"))
{
int k=response.indexOf(getVariableName(tmp));
if(k>-1)
{
response=response.substring(k);
}
}
int i=response.indexOf('=');
int j=response.indexOf('\n');
if(j==-1)
{
retStr=response.substring(i+1).trim();
}
else
{
retStr=response.substring(i+1, j).trim();
}
}
else
{
if(tmp.matches(".*INDEX.*SETTING.*CALIBRATE.*")||tmp.matches(".*!CAL [012]3*"))
{
retStr=response.substring(response.indexOf("C"));
}
else
{
retStr=response;
}
}
Printer.logger.debug(retStr);
return retStr;
}
static public String getVariableName(String command) throws CPLParsingException
{
Printer.logger.trace("");
if(command.matches(".*VARIABLE .*?.*")||command.matches(".*V .*?.*"))
{
command=command.toUpperCase();
int i=command.indexOf("VARIABLE");
if(i!=-1)
{
i=command.indexOf('E')+1;
}
else
{
i=command.indexOf('V')+1;
}
int j=command.indexOf('?')-1;
Printer.logger.debug("getVariableName: ["+command.substring(i, j).trim()+"]");
return command.substring(i, j).trim();
}
else
{
throw new CPLParsingException("getVariableName called on non-variable command");
}
}
static public void getResponsePattern(String command, StringBuffer sbPatternStart, StringBuffer sbPatternEnd) throws CPLParsingException
{
Printer.logger.trace("");
String tmp=command.replace('\n', ' ');
tmp=tmp.replace('\r', ' ');
tmp=tmp.trim();
tmp=tmp.toUpperCase();
String patternStart=null;
String patternEnd=null;
if(tmp.matches(".*VARIABLE .*?.*")||tmp.matches("V .*?.*"))
{
if(tmp.matches(".*TIME .*"))
{
patternStart="(Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday).*";
}
else if(tmp.matches(".*AUX_POWER.*"))
{
patternStart=".*AUXPOWER.*";
}
else if(tmp.matches(".*LANGUAGE.*"))
{
patternStart=".*AUX LANGUAGE.*";
}
else if(tmp.matches(".*BLUETOOTH DEVICENAME.*"))
{
patternStart=".*BLUETOOTH DEVICENAME=.*";
}
else if(tmp.matches(".*BLUETOOTH BDADDR.*"))
{
patternStart=".*BLUETOOTH BDADDR=.*";
}
else if(tmp.matches(".*INDEX.*"))
{
patternStart=".*INDEX.*";
}
else
{
patternStart=".*"+getVariableName(tmp)+".*";
}
}
else if(tmp.matches(".*INDEX.*SETTING.*CALIBRATE.*"))
{
patternStart=".*C3[FP]";
}
else if(tmp.matches(".*!CAL [012]3*"))
{
patternStart=".*C[012]3*[FP]";
}
else if(tmp.matches(".*SHOW MODELNUMBER.*"))
{
patternStart="Model Number.*";
}
else if(tmp.matches(".*SHOW SERIALNUMBER.*"))
{
patternStart=".*Serial Number.*";
}
else if(tmp.matches(".*SHOW INCHCOUNT.*"))
{
patternStart=".*Inches Printed.*";
}
else if(tmp.matches(".*SHOW MAC.*"))
{
patternStart=".*MAC.*";
}
else if(tmp.matches(".*!.*QR.*"))
{
patternStart=".*195-...-....*";
}
else if(tmp.matches(".*!.*QS.*"))
{
patternStart=".*[a-zA-Z]\\d{5}.*";
}
else if(tmp.matches(".*!.*LS.*"))
{
patternStart=".*,.*,.*,.*,.*,.*";
patternEnd=".*END-OF-LIST.*";
}
else if(tmp.matches(".*!D.*"))
{
patternStart="Success";
}
else if(tmp.matches(".*SHOW.*AD.*"))
{
patternStart="(\\d=\\d{3}\\s*){8}.*";
}
else
{
patternStart=".*";
}
Printer.logger.debug("getResponsePattern command: ["+tmp+"]");
Printer.logger.debug("getResponsePattern pattern: ["+patternStart+"] .. ["+patternEnd+"]");
sbPatternStart.append(patternStart);
sbPatternEnd.append(patternEnd);
}
}
