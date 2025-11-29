public class SecurityConfig
{
private static HashMap<String, ConfigType>aConfigs=new HashMap<String, ConfigType>();
public static boolean loadConfigs(final String sConfigPath)
{
try
{
File inputFile=new File(sConfigPath);
DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
Document document=dBuilder.parse(inputFile);
NodeList nList, nSettings;
Node nNode;
Element eNodeElement;
String sModelType;
aConfigs.clear();
document.getDocumentElement().normalize();
nList=document.getElementsByTagName("class");
eNodeElement=(Element)nList.item(0);
if(nList.getLength()!=1||!eNodeElement.getAttribute("name").equals("Security Configurations"))
return false;
nList=nList.item(0).getChildNodes();
if(nList.getLength()==0)
return false;
for(int iList=0; iList<nList.getLength();++iList)
{
nNode=nList.item(iList);
if(nNode.getNodeType()==Node.ELEMENT_NODE&&nNode.getNodeName().equals("configuration"))
{
eNodeElement=(Element)nNode;
sModelType=eNodeElement.getAttribute("type");
if(sModelType.startsWith("S"))
{
nSettings=eNodeElement.getElementsByTagName("setting");
aConfigs.put(sModelType, new ConfigType());
for(int iSetting=0; iSetting<nSettings.getLength();++iSetting)
{
nNode=nSettings.item(iSetting);
eNodeElement=(Element)nNode;
aConfigs.get(sModelType).addElement(eNodeElement);
}
}
}
}
return true;
}
catch(Exception e)
{
}
return false;
}
public static boolean isValidSetting(final String sModelType)
{
return aConfigs.containsKey(sModelType);
}
public static String getSetting(final String sModelType, final String sSetting)
{
if(isValidSetting(sModelType))
return aConfigs.get(sModelType).getValue(sSetting);
return "";
}
private static class ConfigType
{
private HashMap<String, String>hKeyValuePairs;
public ConfigType()
{
hKeyValuePairs=new HashMap<String, String>();
}
public void addElement(final Element eNodeElement)
{
hKeyValuePairs.put(eNodeElement.getAttribute("key"), eNodeElement.getAttribute("value"));
}
public String getValue(final String sKey)
{
if(hKeyValuePairs.containsKey(sKey))
return hKeyValuePairs.get(sKey);
return "";
}
}
}
