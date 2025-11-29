public class PrinterId
{
private String modelNumber;
private int mac;
private char locationCode;
private String snPrefix;
private int serialNumber;
public PrinterId(String xmlPrinterId) throws DBException
{
GUIHelper.logger.trace(xmlPrinterId);
try
{
String snElement=GUIHelper.getMatch(xmlPrinterId, "<sn>.*</sn>");
serialNumber=Integer.parseInt(elementValue(snElement));
String macElement=GUIHelper.getMatch(xmlPrinterId, "<mac>.*</mac>");
mac=Integer.parseInt(elementValue(macElement));
String mnElement=GUIHelper.getMatch(xmlPrinterId, "<mn>.*</mn>");
modelNumber=new String(elementValue(mnElement));
String lcElement=GUIHelper.getMatch(xmlPrinterId, "<lc>.*</lc>");
locationCode=elementValue(lcElement).charAt(0);
String snpElement=GUIHelper.getMatch(xmlPrinterId, "<snp>.*</snp>");
snPrefix=new String(elementValue(snpElement));
}
catch(Exception e)
{
throw new DBException("Database access error: "+e.toString());
}
}
public PrinterId(String qualifiedSerialNumber, String qualifiedMac, String modelNumber)
{
GUIHelper.logger.trace(qualifiedSerialNumber+";"+qualifiedMac+"; "+modelNumber);
locationCode=qualifiedSerialNumber.charAt(0);
snPrefix=qualifiedSerialNumber.substring(1, 5);
serialNumber=Integer.parseInt(qualifiedSerialNumber.substring(5));
this.modelNumber=modelNumber;
String macHex=String.format("%2s%2s%2s",
qualifiedMac.substring(9, 11),
qualifiedMac.substring(12, 14),
qualifiedMac.substring(15));
mac=Integer.parseInt(macHex, 16);
}
@Override
public String toString()
{
StringBuffer retString=new StringBuffer();
retString.append("PrinterId:");
retString.append(" SN="+serialNumber);
retString.append("; SNP="+snPrefix);
retString.append("; LC="+locationCode);
retString.append("; MN="+modelNumber);
retString.append("; MAC="+mac);
return retString.toString();
}
@Override
public boolean equals(Object other)
{
if(this==other)
{
return true;
}
if(!(other instanceof PrinterId))
{
return false;
}
PrinterId otherPrinterId=(PrinterId)(other);
GUIHelper.logger.debug("This: "+this);
GUIHelper.logger.debug("Other: "+otherPrinterId);
return
this.serialNumber==otherPrinterId.serialNumber&&this.snPrefix.equals(otherPrinterId.snPrefix)&&this.locationCode==otherPrinterId.locationCode&&this.modelNumber.equals(otherPrinterId.modelNumber)&&this.mac==otherPrinterId.mac;
}
private String elementValue(String xmlElement)
{
int startIndex=xmlElement.indexOf('>')+1;
int endIndex=xmlElement.indexOf('<', startIndex);
return xmlElement.substring(startIndex, endIndex);
}
public String getQualifiedSerialNumber()
{
return String.format("%1s%4s%05d", locationCode, snPrefix, serialNumber);
}
public String getQualifiedMac()
{
String macLSB=String.format("%06X", mac);
String macHex="00:E0:70:"+macLSB.substring(0, 2)+":"+macLSB.substring(2, 4)+":"+macLSB.substring(4);
return macHex;
}
public int getSerialNumber()
{
return serialNumber;
}
public void setSerialNumber(int serialNumber)
{
this.serialNumber=serialNumber;
}
public String getModelNumber()
{
return modelNumber;
}
public void setModelNumber(String modelNumber)
{
this.modelNumber=modelNumber;
}
public char getLocationCode()
{
return locationCode;
}
public void setLocationCode(char locationCode)
{
this.locationCode=locationCode;
}
public int getMac()
{
return mac;
}
public void setMac(int mac)
{
this.mac=mac;
}
public String getSnPrefix()
{
return snPrefix;
}
public void setSnPrefix(String snPrefix)
{
this.snPrefix=snPrefix;
}
}
