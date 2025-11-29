public class TestEngine
{
static String jadminURLBase=GUIHelper.prefs.get("MAN_JADMIN_URL", "http://jadmin.cognitive.com/updatePrinter.php")+"?db="+GUIHelper.prefs.get("MAN_DATABASE", "manufacturing");
static String jadminURLBaseTG=GUIHelper.prefs.get("MAN_JADMIN_URL", "http://jadmin.dascom.com/updatePrinter.php")+"?db="+GUIHelper.prefs.get("MAN_DATABASE", "manufacturing");
static public boolean isConnectionTypeParallelOnly=false;
static public boolean isConnectionTypeSerial=false;
static public boolean runningManufacturing=false;
static enum TestStatus
{
PASSED,
FAILED,
SKIPPED,
CANCELLED,
REDO,
ABORTED
}
static enum TestStep
{
INITIAL_SETUP,
FIRMWARE_DOWNLOAD,
PRINTER_IDENTIFICATION,
FONT_DOWNLOAD,
CPR_DOWNLOAD,
CONTROL_PANEL,
CONNECTION_TYPES,
PRINTHEAD_UP_DOWN,
TONE_CHECK,
RTC_SETUP,
CALIBRATION,
FEED_BUTTON_TEST,
PEELER_TEST,
PRINTHEAD_TEST,
BACKUP_MEMORY_TEST,
PRINT_QUALITY_TEST,
RIBBON_WRINKLE_TEST,
FINAL_SETTINGS,
SELF_TEST,
SERIAL_NUMBER_LABEL,
BOX_LABEL
}
public static String[] TestStatusString={
Messages.getString("TestEngine.104"),
Messages.getString("TestEngine.105"),
Messages.getString("TestEngine.106"),
Messages.getString("TestEngine.107")
};
static enum ProductLine
{
CI,
CXI,
DLX,
LX
}
static enum PrintMethod
{
DT,
TT
}
static enum PrintHead
{
Automatic,
ROHM_KDxxxxDF,
HEC,
Kyocera,
AOI,
ROHM_KFxxxxGD
}
static enum ExitOption
{
STANDARD,
TEAR_BAR,
PEELER
}
static enum MemRTC
{
MB4,
MB8,
MB8_RTC
}
static enum ConfigurationEmulation
{
STANDARD,
CUSTOM,
WINGMAN,
GATTACA,
GATTACA_CUSTOM_COMGRPH,
GATTACA_CUSTOM_DATACARD,
GATTACA_CUSTOM_SIEMAN_1,
GATTACA_CUSTOM_SIEMAN_2,
GATTACA_CUSTOM_HOMEDEPOT,
GATTACA_CUSTOM_TOUCHSTAR,
GATTACA_CUSTOM_SUNQUEST,
GATTACA_CUSTOM_METER
}
static enum PowerSupplyCord
{
US,
EU_UK,
US_EU_UK,
NO_CORD,
NO_POWER_SUPPLY
}
static enum CommunicationInterface
{
USB_AB,
USB_AB_NETWORK,
USB_AB_NETWORK_SERIAL,
USB_AB_NETWORK_SERIAL_PARALLEL,
USB_AB_SERIAL,
USB_AB_SERIAL_BLUETOOTH,
USB_AB_PARALLEL,
USB_AB_SERIAL_PARALLEL,
SERIAL,
PARALLEL,
SERIAL_PARALLEL,
SERIAL_NETWORK,
SERIAL_NETWORK_PARALLEL,
USB_AB_LEGACY_NO_CABLE,
USB_AB_LEGACY_SERIAL_CABLE,
USB_AB_LEGACY_ADAPTOR_SERIAL_CABLE,
USB_AB_LEGACY_PARALLEL_CABLE,
ALL
}
static enum LanguageOption
{
STANDARD,
PCL
}
static boolean brandNewPrinter=false;
static boolean changedModelNumber=false;
static boolean changedSerialNumber=false;
static boolean changedMAC=false;
static boolean supportsFontCheckCommand=false;
static boolean supportsFactoryRestoreCommand=false;
static LocationCode locationCode;
static String serialNumber;
static String macHex;
static ProductLine productLine;
static PrintMethod printMethod;
static int mediaWidth;
static int printDensity;
static ExitOption exitOption;
static MemRTC memRTC;
static int printSpeed;
static ConfigurationEmulation configurationEmulation;
static PowerSupplyCord powerSupplyCord;
static CommunicationInterface communicationInterface;
static PrintHead printHead;
static LanguageOption languageOption;
static String customization;
static String currentFirmware;
static String latestFirmware=null;
static String currentVersion=null;
static String latestFirmwarePath;
static String testSleepAfter="157";
static String originalSleepAfter=null;
static TGModelNumbers tgModelNumbers=null;
static boolean isTGModelNumber=false;
static boolean isTDModelNumber=false;
static String tgModelFeature;
static String tgModelEmbeddedNumber;
static String tgModelUPC;
static String tgCompanyName="DASCOM Europe GmbH";
static String tgGermanyAddress1="Heuweg 3, D-89079 Ulm,";
static String tgGermanyAddress2="Germany";
static String tgUSAddress1="4500 Daly Drive";
static String tgUSAddress2="Chantilly, VA 20151 USA";
static String currentModelNumber;
static public String testModelNumber;
static String ezCustomerPartNumber;
static boolean isWingman=false;
static boolean isPCL=false;
static boolean isGattaca=false;
static boolean isMadeInChina=false;
static public boolean isMadeByEnnoconnMalaysia=false;
static public boolean isLX=false;
static public boolean isTouchstar=false;
static public boolean isMeter=false;
static public boolean isSiemens=false;
static public boolean isResco=false;
static public boolean isHD=false;
static public boolean isApple=false;
static public boolean isBT=false;
static public boolean isNetwork=false;
static public boolean isSecurity=false;
static public boolean isGWN=false;
static public boolean isDT=false;
static public boolean isBS=false;
static public boolean isCustomLabel_K542=false, printCustom_K542=false;
static boolean isFontDensityDifferent=false;
static boolean skipLoadingFirmware=false;
static boolean skipLoadingFonts=false;
static boolean skipLoadingCPRs=false;
static boolean isTestAborted=false;
static String btDeviceName;
static String btMacAddress;
static boolean firmwareLoadingFlashVersionnProblem=false;
static ConnectionType connectionType=ConnectionType.UNKNOWN;
static boolean parseModelNumber(String modelNumber)
{
GUIHelper.logger.info(modelNumber);
try
{
isWingman=false;
isGattaca=false;
isLX=false;
isTouchstar=false;
isMeter=false;
isSiemens=false;
isHD=false;
isApple=false;
isBT=false;
isDT=false;
isMadeByEnnoconnMalaysia=(locationCode==LocationCode.E);
if(modelNumber.startsWith("DB")||modelNumber.startsWith("LB"))
{
isTGModelNumber=false;
isTDModelNumber=false;
return parseDLX_LXModelNumber(modelNumber);
}
else if(modelNumber.startsWith("C"))
{
isTGModelNumber=false;
isTDModelNumber=false;
return parseCModelNumber(modelNumber);
}
else if(modelNumber.startsWith("16"))
{
try
{
if(CompositeManufacturing.checkBoxTG.getSelection())
{
isTGModelNumber=true;
isTDModelNumber=false;
}
else
{
isTDModelNumber=true;
isTGModelNumber=false;
}
}
catch(Exception e)
{
String oemID=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW OEMIDENTIFIER"));
GUIHelper.logger.info("Called for Get Latest Firmware: "+oemID);
if(oemID.contains("DASCOM"))
{
isTDModelNumber=true;
isTGModelNumber=false;
}
else
{
isTGModelNumber=true;
isTDModelNumber=false;
}
}
return convertTGModelNumber(modelNumber);
}
else
{
GUIHelper.error(Messages.getString("TestEngine.0"));
GUIHelper.logger.error("Can't parse model number "+modelNumber);
return false;
}
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("TestEngine.1"));
GUIHelper.logger.error("Can't parse model number "+modelNumber);
return false;
}
}
static boolean convertTGModelNumber(String modelNumber)
{
GUIHelper.logger.trace(modelNumber);
if(tgModelNumbers==null)
{
tgModelNumbers=new TGModelNumbers(isTGModelNumber);
}
try
{
String convertedModelNumber=null;
convertedModelNumber=tgModelNumbers.getModelNumber(modelNumber);
convertedModelNumber=tgModelNumbers.getModelNumber(modelNumber);
tgModelFeature=tgModelNumbers.getModelFeature(modelNumber);
tgModelEmbeddedNumber=tgModelNumbers.getModelEmbeddedNumber(modelNumber);
tgModelUPC=tgModelNumbers.getModelUPC(modelNumber);
GUIHelper.logger.debug("Converted to: "+convertedModelNumber);
if(convertedModelNumber.startsWith("DB"))
{
return parseDLX_LXModelNumber(convertedModelNumber);
}
else if(convertedModelNumber.startsWith("C"))
{
return parseCModelNumber(convertedModelNumber);
}
else
{
GUIHelper.error(Messages.getString("TestEngine.0"));
GUIHelper.logger.error("Can't parse model number "+modelNumber);
return false;
}
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("TestEngine.1"));
GUIHelper.logger.error("Can't parse model number "+modelNumber);
return false;
}
}
static boolean parseDLX_LXModelNumber(String modelNumber)
{
GUIHelper.logger.info(modelNumber);
if(modelNumber.startsWith("DB"))
productLine=ProductLine.DLX;
else
{
productLine=ProductLine.LX;
isLX=true;
}
char c;
c=modelNumber.charAt(2);
switch(c)
{
case 'D':
printMethod=PrintMethod.DT;
break;
case 'T':
printMethod=PrintMethod.TT;
break;
default:
return false;
}
GUIHelper.logger.info(TestEngine.printMethod);
mediaWidth=Integer.parseInt(modelNumber.substring(3, 4));
GUIHelper.logger.info(mediaWidth);
c=modelNumber.charAt(6);
switch(c)
{
case '2':
printDensity=200;
break;
case '3':
printDensity=300;
break;
default:
return false;
}
GUIHelper.logger.info(printDensity);
c=modelNumber.charAt(7);
switch(c)
{
case '0':
exitOption=ExitOption.STANDARD;
break;
case '1':
exitOption=ExitOption.TEAR_BAR;
break;
case '4':
exitOption=ExitOption.PEELER;
break;
default:
return false;
}
GUIHelper.logger.info(exitOption);
c=modelNumber.charAt(8);
switch(c)
{
case '4':
memRTC=MemRTC.MB4;
break;
case '8':
if(isLX)
memRTC=MemRTC.MB8_RTC;
else
memRTC=MemRTC.MB8;
break;
case '9':
memRTC=MemRTC.MB8_RTC;
break;
default:
return false;
}
GUIHelper.logger.info(memRTC);
c=modelNumber.charAt(9);
printSpeed=c-48;
GUIHelper.logger.debug(printSpeed);
customization=null;
c=modelNumber.charAt(11);
if(productLine==ProductLine.LX)
{
configurationEmulation=ConfigurationEmulation.GATTACA;
isGattaca=true;
}
else
{
switch(c)
{
case 'G':
configurationEmulation=ConfigurationEmulation.GATTACA;
isGattaca=true;
break;
case '0':
configurationEmulation=ConfigurationEmulation.STANDARD;
break;
case 'C':
configurationEmulation=ConfigurationEmulation.CUSTOM;
customization=modelNumber.substring(12);
break;
case 'Z':
configurationEmulation=ConfigurationEmulation.WINGMAN;
isWingman=true;
break;
case 'M':
configurationEmulation=ConfigurationEmulation.WINGMAN;
isWingman=true;
break;
case 'P':
configurationEmulation=ConfigurationEmulation.STANDARD;
languageOption=LanguageOption.PCL;
break;
default:
return false;
}
}
if(isGattaca)
{
c=modelNumber.charAt(12);
switch(c)
{
case 'C':
configurationEmulation=ConfigurationEmulation.GATTACA_CUSTOM_COMGRPH;
break;
case 'B':
configurationEmulation=ConfigurationEmulation.GATTACA_CUSTOM_COMGRPH;
break;
case 'D':
configurationEmulation=ConfigurationEmulation.GATTACA_CUSTOM_DATACARD;
break;
case 'E':
configurationEmulation=ConfigurationEmulation.GATTACA_CUSTOM_SIEMAN_2;
break;
case 'H':
configurationEmulation=ConfigurationEmulation.GATTACA_CUSTOM_HOMEDEPOT;
break;
case 'S':
if(modelNumber.charAt(13)=='Q')
{
configurationEmulation=ConfigurationEmulation.GATTACA_CUSTOM_SUNQUEST;
}
else
{
configurationEmulation=ConfigurationEmulation.GATTACA_CUSTOM_SIEMAN_1;
}
break;
case 'T':
configurationEmulation=ConfigurationEmulation.GATTACA_CUSTOM_TOUCHSTAR;
break;
case 'L':
configurationEmulation=ConfigurationEmulation.GATTACA_CUSTOM_METER;
break;
default:
break;
}
}
GUIHelper.logger.info(configurationEmulation);
if(configurationEmulation.toString().indexOf("CUSTOM")==-1)
{
c=modelNumber.charAt(12);
switch(c)
{
case '1':
case 'F':
powerSupplyCord=PowerSupplyCord.US;
break;
case '2':
powerSupplyCord=PowerSupplyCord.EU_UK;
break;
case '3':
powerSupplyCord=PowerSupplyCord.US_EU_UK;
break;
case 'N':
powerSupplyCord=PowerSupplyCord.NO_POWER_SUPPLY;
break;
case '0':
powerSupplyCord=PowerSupplyCord.NO_POWER_SUPPLY;
break;
default:
return false;
}
GUIHelper.logger.info(powerSupplyCord);
c=modelNumber.charAt(13);
if(isGattaca)
{
switch(c)
{
case '1':
communicationInterface=CommunicationInterface.SERIAL;
break;
case '2':
communicationInterface=CommunicationInterface.PARALLEL;
break;
case '3':
communicationInterface=CommunicationInterface.SERIAL_PARALLEL;
break;
case '4':
communicationInterface=CommunicationInterface.SERIAL_NETWORK;
break;
case '6':
communicationInterface=CommunicationInterface.SERIAL_NETWORK_PARALLEL;
break;
case 'U':
communicationInterface=CommunicationInterface.USB_AB;
break;
case 'E':
if(mediaWidth==2)
{
communicationInterface=CommunicationInterface.USB_AB_NETWORK_SERIAL;
}
else
{
communicationInterface=CommunicationInterface.USB_AB_NETWORK_SERIAL_PARALLEL;
}
break;
case 'S':
if(mediaWidth==2)
{
communicationInterface=CommunicationInterface.USB_AB_SERIAL;
}
else
{
communicationInterface=CommunicationInterface.USB_AB_SERIAL_PARALLEL;
}
break;
case 'M':
communicationInterface=CommunicationInterface.USB_AB_SERIAL_PARALLEL;
break;
case 'B':
communicationInterface=CommunicationInterface.USB_AB_SERIAL_BLUETOOTH;
isBT=true;
break;
case 'A':
communicationInterface=CommunicationInterface.USB_AB_SERIAL_BLUETOOTH;
isBT=true;
isApple=true;
break;
case 'P':
if(mediaWidth==2)
{
communicationInterface=CommunicationInterface.USB_AB_PARALLEL;
}
else
{
communicationInterface=CommunicationInterface.USB_AB_SERIAL_PARALLEL;
}
break;
default:
return false;
}
}
else
{
switch(c)
{
case 'U':
communicationInterface=CommunicationInterface.USB_AB;
break;
case 'L':
communicationInterface=CommunicationInterface.USB_AB_LEGACY_NO_CABLE;
break;
case 'E':
communicationInterface=CommunicationInterface.USB_AB_NETWORK;
break;
case 'S':
communicationInterface=CommunicationInterface.USB_AB_LEGACY_SERIAL_CABLE;
break;
case 'M':
communicationInterface=CommunicationInterface.USB_AB_LEGACY_ADAPTOR_SERIAL_CABLE;
break;
case 'P':
communicationInterface=CommunicationInterface.USB_AB_LEGACY_PARALLEL_CABLE;
break;
default:
return false;
}
}
GUIHelper.logger.info(communicationInterface);
}
else
{
if(isGattaca)
{
switch(configurationEmulation)
{
case GATTACA_CUSTOM_COMGRPH:
powerSupplyCord=PowerSupplyCord.US;
if(modelNumber.charAt(13)=='E')
{
communicationInterface=CommunicationInterface.USB_AB_NETWORK_SERIAL;
}
else
{
communicationInterface=CommunicationInterface.USB_AB_SERIAL;
}
if(modelNumber.charAt(12)=='B')
{
customization="BS";
isBS=true;
}
else
{
customization="COMGRPH";
}
break;
case GATTACA_CUSTOM_DATACARD:
powerSupplyCord=PowerSupplyCord.US;
return false;
case GATTACA_CUSTOM_SUNQUEST:
powerSupplyCord=PowerSupplyCord.US;
communicationInterface=CommunicationInterface.USB_AB_NETWORK_SERIAL_PARALLEL;
customization="SQ";
break;
case GATTACA_CUSTOM_SIEMAN_1:
powerSupplyCord=PowerSupplyCord.US;
communicationInterface=CommunicationInterface.USB_AB_SERIAL_PARALLEL;
isSiemens=true;
customization="S1";
break;
case GATTACA_CUSTOM_SIEMAN_2:
powerSupplyCord=PowerSupplyCord.EU_UK;
isSiemens=true;
return false;
case GATTACA_CUSTOM_HOMEDEPOT:
powerSupplyCord=PowerSupplyCord.US;
communicationInterface=CommunicationInterface.USB_AB_NETWORK_SERIAL_PARALLEL;
isHD=true;
customization="HD";
break;
case GATTACA_CUSTOM_TOUCHSTAR:
powerSupplyCord=PowerSupplyCord.NO_POWER_SUPPLY;
if(modelNumber.charAt(13)=='B')
{
communicationInterface=CommunicationInterface.USB_AB_SERIAL_BLUETOOTH;
customization="TOUCHSTAR2";
}
else
{
communicationInterface=CommunicationInterface.USB_AB_SERIAL_PARALLEL;
customization="TOUCHSTAR1";
}
isTouchstar=true;
break;
case GATTACA_CUSTOM_METER:
powerSupplyCord=PowerSupplyCord.NO_POWER_SUPPLY;
communicationInterface=CommunicationInterface.USB_AB_SERIAL_PARALLEL;
customization="METER";
isMeter=true;
break;
default:
return false;
}
}
else
{
if(customization.equalsIgnoreCase("01"))
{
powerSupplyCord=PowerSupplyCord.US;
communicationInterface=CommunicationInterface.USB_AB_LEGACY_ADAPTOR_SERIAL_CABLE;
}
else
{
return false;
}
}
GUIHelper.logger.info(powerSupplyCord);
GUIHelper.logger.info(communicationInterface);
GUIHelper.logger.info(customization);
}
return true;
}
static boolean parseCModelNumber(String modelNumber)
{
GUIHelper.logger.info("");
char c;
c=modelNumber.charAt(1);
switch(c)
{
case 'I':
productLine=ProductLine.CI;
break;
case 'X':
productLine=ProductLine.CXI;
break;
default:
return false;
}
c=modelNumber.charAt(2);
switch(c)
{
case 'D':
printMethod=PrintMethod.DT;
break;
case 'T':
printMethod=PrintMethod.TT;
break;
default:
return false;
}
GUIHelper.logger.info(printMethod);
mediaWidth=Integer.parseInt(modelNumber.substring(3, 4));
GUIHelper.logger.info(mediaWidth);
c=modelNumber.charAt(6);
switch(c)
{
case '0':
printDensity=200;
break;
case '3':
printDensity=300;
break;
default:
return false;
}
GUIHelper.logger.info(printDensity);
c=modelNumber.charAt(7);
switch(c)
{
case '0':
languageOption=LanguageOption.STANDARD;
break;
case '3':
languageOption=LanguageOption.PCL;
break;
default:
return false;
}
GUIHelper.logger.info(languageOption);
c=modelNumber.charAt(8);
switch(c)
{
case '0':
exitOption=ExitOption.STANDARD;
break;
default:
return false;
}
GUIHelper.logger.info(exitOption);
isResco=false;
isSecurity=false;
isGWN=false;
if(modelNumber.length()>=12)
{
customization=modelNumber.substring(10);
GUIHelper.logger.info(customization);
if(customization.equalsIgnoreCase("RX"))
{
customization=null;
}
else if(customization.equalsIgnoreCase("CS0"))
{
isResco=true;
}
else if(customization.startsWith("S"))
{
isSecurity=true;
if(customization.startsWith("SGWN"))
{
isGWN=true;
}
}
}
else
{
customization=null;
}
if((modelNumber.length()==9)&&(modelNumber.charAt(7)=='0')&&(!isTDModelNumber)&&(!isTGModelNumber))
{
isMadeInChina=true;
}
else
{
isMadeInChina=false;
}
memRTC=MemRTC.MB8_RTC;
communicationInterface=CommunicationInterface.ALL;
return true;
}
static String getCurrentFirmwarePartNumber()
{
GUIHelper.logger.info("");
String currentFirmwarePartNumber=null;
if(!isConnectionTypeParallelOnly)
{
currentFirmware=GUIHelper.clean(PrinterHelper.commandWaitResponse("!QR"));
if(currentFirmware!=null)
{
String regexPartNumber="\\d{3}-\\d{3}-\\d{3}";
currentFirmwarePartNumber=GUIHelper.getMatch(currentFirmware, regexPartNumber);
}
}
else
{
determineLatestFirmware();
currentFirmware=latestFirmware;
currentFirmwarePartNumber=latestFirmware.substring(0, 3)+"-"+latestFirmware.substring(3, 6)+"-"+latestFirmware.substring(7);
}
GUIHelper.logger.info("currentFirmware="+currentFirmware);
GUIHelper.logger.info("currentFirmwarePartNumber="+currentFirmwarePartNumber);
return currentFirmwarePartNumber;
}
static String getCurrentFirmwareVersion(String currentPartNumber)
{
if(currentPartNumber!=null)
{
String regexVersion="\\d{3}$";
currentVersion=GUIHelper.getMatch(currentPartNumber, regexVersion);
}
return currentVersion;
}
static void determineLatestFirmware()
{
GUIHelper.logger.info("");
if(!isTGModelNumber&&!isTDModelNumber)
{
switch(productLine)
{
case LX:
latestFirmware=FirmwareMap.LX;
break;
case DLX:
latestFirmware=FirmwareMap.DLX;
if(isWingman)
{
latestFirmware=FirmwareMap.WM;
}
if(isGattaca)
{
if(isBS)
{
latestFirmware=FirmwareMap.CG;
}
else if(isBT)
{
latestFirmware=FirmwareMap.BT;
}
else
{
latestFirmware=FirmwareMap.GT;
}
}
if(isTouchstar)
{
latestFirmware=FirmwareMap.TS;
}
if(isMeter)
{
latestFirmware=FirmwareMap.ME;
}
if(isHD)
{
latestFirmware=FirmwareMap.HD;
}
if(isSiemens)
{
latestFirmware=FirmwareMap.SI;
}
break;
case CI:
latestFirmware=FirmwareMap.CI;
break;
case CXI:
latestFirmware=FirmwareMap.CXI;
if(isResco)
{
latestFirmware=FirmwareMap.Resco;
}
if(isGWN)
{
latestFirmware=FirmwareMap.GWN;
}
if(isSecurity)
{
latestFirmware=SecurityConfig.getSetting(customization, "FIRMWARE");
}
break;
}
}
else
{
latestFirmware=FirmwareMap.TG;
}
}
static final Map<String, String>customFirmwareMap=new HashMap<String, String>();
static
{
customFirmwareMap.put("DBT24-2095-GBS", "195170.406");
customFirmwareMap.put("DBT24-2095-GCS", "195170.410");
customFirmwareMap.put("DBD42-2085-GHE", "195170.408");
customFirmwareMap.put("DBD42-2085-GSS", "195170.328");
customFirmwareMap.put("DBD42-2085-GSO", "195170.409");
customFirmwareMap.put("DBD42-2085-GSQ", "195170.409");
customFirmwareMap.put("CXT2-1300-C01", "195170.335");
}
static boolean firmwareNeedsUpdate(boolean firmwareAsk, StringBuffer newFirmware) throws ManufacturingTestException
{
GUIHelper.logger.info("");
String currentFileName="";
boolean manufacturing=GUIHelper.prefs.getBoolean("ACTIVATE_MANUFACTURING", false);
boolean CustomFirmware=GUIHelper.prefs.getBoolean("ACTIVATE_CUSTOM_FIRMWARE", false);
if(manufacturing&&CustomFirmware)
{
latestFirmware=GUIHelper.prefs.get("CUSTOM_FIRMWARE", null);
latestFirmwarePath=GUIHelper.prefs.get("CUSTOM_FIRMWARE_PATH", null);
if(latestFirmware==null)
{
GUIHelper.logger.info("latestFirmware=null");
CustomFirmware=false;
}
else
{
GUIHelper.logger.info("latestFirmware="+latestFirmware);
}
}
if(!CustomFirmware)
{
GUIHelper.logger.info("Not custom firmware");
determineLatestFirmware();
}
if(customFirmwareMap.containsKey(testModelNumber))
{
latestFirmware=customFirmwareMap.get(testModelNumber);
CustomFirmware=true;
if((latestFirmware.matches("195\\d{3}[.]3\\d{2}")&&currentFirmware.matches("195[-]\\d{3}[-]4\\d{2}.*"))||(latestFirmware.matches("195\\d{3}[.]4\\d{2}")&&currentFirmware.matches("195[-]\\d{3}[-]3\\d{2}.*")))
{
firmwareLoadingFlashVersionnProblem=true;
GUIHelper.logger.info("Detected there will be firmware incompatibility between versions 195170.3XX and 195170.4XX due to internal flash memory storage changes.");
}
}
if(!CustomFirmware&&customization!=null)
{
if(customization.equals("PCL"))
{
if(!isTGModelNumber&&!isTDModelNumber)
{
latestFirmware=FirmwareMap.PCL;
}
else
{
latestFirmware=FirmwareMap.TGPCL;
}
}
}
GUIHelper.logger.info("Latest firmware: "+latestFirmware);
String currentPartNumber=getCurrentFirmwarePartNumber();
if(currentPartNumber==null)
{
throw new ManufacturingTestException("Unable to get current firmware version.");
}
currentVersion=getCurrentFirmwareVersion(currentPartNumber);
String suffix="";
if(currentFirmware.indexOf("P")!=-1)
{
suffix="P";
}
if(currentFirmware.indexOf("RC")!=-1)
{
suffix=currentFirmware.substring(currentFirmware.indexOf("RC"), currentFirmware.indexOf("RC")+3);
}
String[] tokens=currentPartNumber.split("-");
String massagedPartNumber=tokens[0]+tokens[1];
currentFileName=massagedPartNumber+suffix.toString()+"."+currentVersion;
GUIHelper.logger.info("Current part number: "+currentPartNumber);
GUIHelper.logger.info("Current version: "+currentVersion);
GUIHelper.logger.info("Current file name: "+currentFileName);
boolean firmwareNeedsUpdate=false;
if(!latestFirmware.trim().contains(currentFileName.trim())||isConnectionTypeParallelOnly)
{
firmwareNeedsUpdate=true;
if(!latestFirmware.contains("||"))
{
if(currentFileName.contains("195184."))
{
if(latestFirmware.contains("195184.")||latestFirmware.contains("195186."))
latestFirmware=FirmwareMap.BT;
if(latestFirmware.trim().contains(currentFileName.trim()))
firmwareNeedsUpdate=false;
}
else if(currentFileName.contains("195186."))
{
if(latestFirmware.contains("195184.")||latestFirmware.contains("195186."))
latestFirmware=FirmwareMap.newBT;
if(latestFirmware.trim().contains(currentFileName.trim()))
firmwareNeedsUpdate=false;
}
else if(currentFileName.contains("195170.")&&(latestFirmware.contains("195184.")||latestFirmware.contains("195186.")))
{
if(latestFirmware.contains("195184.")||latestFirmware.contains("195186."))
latestFirmware=FirmwareMap.newBT;
if(latestFirmware.trim().contains(currentFileName.trim()))
firmwareNeedsUpdate=false;
}
}
else
{
if(currentFileName.contains("195184."))
{
if(latestFirmware.contains("195184.")||latestFirmware.contains("195186."))
latestFirmware=FirmwareMap.BT;
}
else if(currentFileName.contains("195186."))
{
if(latestFirmware.contains("195184.")||latestFirmware.contains("195186."))
latestFirmware=FirmwareMap.newBT;
}
else if(currentFileName.contains("195170.")&&(latestFirmware.contains("195184.")||latestFirmware.contains("195186.")))
{
if(latestFirmware.contains("195184.")||latestFirmware.contains("195186."))
latestFirmware=FirmwareMap.newBT;
}
else
{
String[] acceptableFirmware=latestFirmware.split("[|][|]");
latestFirmware=acceptableFirmware[acceptableFirmware.length-1].trim();
}
}
newFirmware.append(latestFirmware);
}
GUIHelper.logger.info("newFirmware: "+newFirmware);
if(firmwareNeedsUpdate)
{
if(!CustomFirmware&&brandNewPrinter)
{
return true;
}
else
{
if(firmwareAsk)
{
StringBuffer message=new StringBuffer();
message.append(Messages.getString("TestEngine.2"));
message.append(Messages.getString("TestEngine.3")+currentFileName+"\n");
message.append(Messages.getString("TestEngine.4")+latestFirmware.toString()+"\n");
if(!isConnectionTypeParallelOnly)
{
message.append("\n\n");
message.append(Messages.getString("TestEngine.5"));
int button=GUIHelper.message(message.toString(), SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button==SWT.YES)
{
return true;
}
else
{
return false;
}
}
else
{
int button=GUIHelper.message(message.toString(), SWT.ICON_WORKING|SWT.OK);
if(button==SWT.OK)
{
return true;
}
else
{
return false;
}
}
}
else
{
return true;
}
}
}
else
{
return false;
}
}
static TestStatus doFirmwareDownload(boolean firmwareAsk, ProgressBar progressBarFirmwareDownload, boolean reEstablishConnection)
{
GUIHelper.logger.info("");
skipLoadingFirmware=GUIHelper.prefs.getBoolean("ACTIVATE_MANUFACTURING", false);
if(skipLoadingFirmware)
{
try
{
skipLoadingFirmware=CompositeManufacturing.checkBoxSkipLoad1.getSelection();
}
catch(Exception e)
{
skipLoadingFirmware=false;
}
}
if(!skipLoadingFirmware)
{
StringBuffer newFirmware=new StringBuffer();
try
{
if(!firmwareNeedsUpdate(firmwareAsk, newFirmware))
{
return TestStatus.SKIPPED;
}
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
GUIHelper.error(e.getMessage());
return TestStatus.FAILED;
}
byte buff[];
boolean CustomFirmware=GUIHelper.prefs.getBoolean("ACTIVATE_CUSTOM_FIRMWARE", false);
if(!CustomFirmware)
{
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/firmware/"+newFirmware.toString());
}
else
{
try
{
buff=GUIHelper.readBinaryFile(latestFirmwarePath+newFirmware.toString());
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
buff=null;
}
}
if(buff==null)
{
GUIHelper.error(Messages.getString("TestEngine.6")+newFirmware.toString()+"].");
return TestStatus.FAILED;
}
progressBarFirmwareDownload.setEnabled(true);
progressBarFirmwareDownload.setVisible(true);
GUIHelper.logger.info("Updating firmware.");
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
FirmwareUpdater firmwareUpdater=new FirmwareUpdater(buff, progressBarFirmwareDownload);
firmwareUpdater.updateFirmware(true);
if(isConnectionTypeParallelOnly)
{
GUIHelper.message(Messages.getString("TestEngine.109"), SWT.ICON_WORKING|SWT.OK);
}
GUIHelper.setCursor(SWT.DEFAULT);
GUIHelper.logger.info(Messages.getString("TestEngine.7"));
if(reEstablishConnection)
{
boolean success=establishPrimaryConnection(115200);
if(!success)
{
return TestStatus.FAILED;
}
}
}
catch(Exception e1)
{
GUIHelper.logger.error(e1, e1);
GUIHelper.error(Messages.getString("TestEngine.8"));
return TestStatus.FAILED;
}
finally
{
progressBarFirmwareDownload.setEnabled(false);
progressBarFirmwareDownload.setVisible(false);
}
if(!isConnectionTypeParallelOnly)
{
currentFirmware=GUIHelper.clean(PrinterHelper.commandWaitResponse("!QR"));
}
if(firmwareLoadingFlashVersionnProblem)
{
int button=GUIHelper.message("Firmware has successfully downloaded, but this version change may cause internal memory problems. Please manually power cycle the printer, and then continue the testing process.", SWT.OK|SWT.ICON_QUESTION);
firmwareLoadingFlashVersionnProblem=false;
}
return TestStatus.PASSED;
}
else
{
return TestStatus.SKIPPED;
}
}
static TestStatus doRTCSetup()
{
GUIHelper.logger.info("");
if(memRTC!=MemRTC.MB8_RTC)
{
return TestStatus.SKIPPED;
}
Calendar calendar=new GregorianCalendar();
int year=calendar.get(Calendar.YEAR);
int month=calendar.get(Calendar.MONTH)+1;
int day=calendar.get(Calendar.DAY_OF_MONTH);
int hour24=calendar.get(Calendar.HOUR_OF_DAY);
int minute=calendar.get(Calendar.MINUTE);
int second=calendar.get(Calendar.SECOND);
StringBuffer command=new StringBuffer();
String dateTime="!SET TIME "+year+" "+month+" "+day+" "+hour24+" "+minute+" "+second;
command.append(dateTime+"\n");
PrinterHelper.send(command.toString());
GUIHelper.logger.info(dateTime);
return TestStatus.PASSED;
}
static TestStatus doBackupMemoryTest(boolean testPowerCycle)
{
GUIHelper.logger.info("");
if(!isConnectionTypeParallelOnly)
{
if(testPowerCycle&&(memRTC==MemRTC.MB8_RTC))
{
if(isBT)
{
PrinterHelper.send("!!0 0 0 0");
PrinterHelper.send("!0 0 0 0");
PrinterHelper.send("VARIABLE BLUETOOTH CONFIGURE ON");
PrinterHelper.send("VARIABLE BLUETOOTH DEFAULT");
PrinterHelper.send("VARIABLE BLUETOOTH DEVICENAME "+testModelNumber);
PrinterHelper.send("VARIABLE BLUETOOTH DISCOVERABLE ON");
PrinterHelper.send("VARIABLE BLUETOOTH RESET");
PrinterHelper.send("END");
}
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
GUIHelper.error(Messages.getString("TestEngine.9"));
return TestStatus.FAILED;
}
String msg="";
if(connectionType==ConnectionType.OS_PRINTER)
{
msg=Messages.getString("TestEngine.64");
}
else if(connectionType==ConnectionType.SERIAL)
{
msg=Messages.getString("TestEngine.61");
}
else if(connectionType==ConnectionType.NETWORK)
{
msg=Messages.getString("TestEngine.60");
}
else if(connectionType==ConnectionType.PARALLEL)
{
msg=Messages.getString("TestEngine.59");
}
StringBuffer message=new StringBuffer();
message.append(Messages.getString("TestEngine.111"));
message.append(" ("+msg+").\n");
message.append(Messages.getString("TestEngine.112"));
GUIHelper.message(message.toString(), SWT.ICON_WORKING|SWT.OK);
if(!establishPrimaryConnection(9600))
{
return TestStatus.FAILED;
}
}
else
{
}
if(isBT)
{
PrinterHelper.send("!!0 0 0 0");
PrinterHelper.send("!0 0 0 0");
PrinterHelper.send("VARIABLE BLUETOOTH CONFIGURE ON");
PrinterHelper.send("VARIABLE BLUETOOTH DEFAULT");
PrinterHelper.send("VARIABLE BLUETOOTH DEVICENAME "+testModelNumber);
PrinterHelper.send("VARIABLE BLUETOOTH DISCOVERABLE ON");
PrinterHelper.send("VARIABLE BLUETOOTH RESET");
PrinterHelper.send("END");
}
String inchCount=PrinterHelper.commandWaitResponse("!SHOW INCHCOUNT");
if(inchCount==null)
{
return TestStatus.FAILED;
}
GUIHelper.logger.info(inchCount);
if(inchCount.matches("\\d{1,}"))
{
if(isSecurity)
{
int button;
PrinterHelper.send("!CAL 254\n!CAL 0\n!CAL 255\n!CAL 0\n");
button=GUIHelper.message("Is Paper Fed Until Perforation/Edge?", SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button!=SWT.YES)
return TestStatus.FAILED;
}
return TestStatus.PASSED;
}
else
{
String failedMessage=Messages.getString("TestEngine.10")+inchCount+Messages.getString("TestEngine.11");
GUIHelper.logger.error(failedMessage);
GUIHelper.error(failedMessage);
return TestStatus.FAILED;
}
}
else
{
return TestStatus.SKIPPED;
}
}
static TestStatus doToneCheck()
{
GUIHelper.logger.info("");
if(isLX)
{
return TestStatus.SKIPPED;
}
String command="";
command="!0 0 0 0\nBEEP 60\nEND\n";
PrinterHelper.send(command);
int button=GUIHelper.message(Messages.getString("TestEngine.113"), SWT.ICON_QUESTION|SWT.YES|SWT.NO);
command="!0 0 0 0\nBEEP 1\nEND\n";
PrinterHelper.send(command);
if(button==SWT.YES)
{
GUIHelper.logger.info("Good");
return TestStatus.PASSED;
}
else
{
GUIHelper.logger.info("Bad");
GUIHelper.error(Messages.getString("TestEngine.12"));
return TestStatus.FAILED;
}
}
static boolean establishPrimaryConnection(int baud)
{
GUIHelper.logger.info("");
isConnectionTypeParallelOnly=false;
IPrinterConnection conn=null;
try
{
if(PrinterHelper.printer!=null)
{
GUIHelper.logger.warn("Closing pre-existing connection.");
try
{
PrinterHelper.printer.closeConnection();
GUIHelper.mainShell.setConnectionState(false, false);
}
catch(Exception e)
{
GUIHelper.logger.warn(e, e);
}
}
PrinterHelper.printer=new Printer();
if(locationCode!=GUIHelper.LocationCode.E&&isMadeByEnnoconnMalaysia==false)
{
GUIHelper.logger.info("Possible TSC location code detected: disabling parallel interface.");
CompositeManufacturing.checkBoxParallel.setEnabled(true);
CompositeManufacturing.checkBoxParallel.setSelection(false);
CompositeManufacturing.checkBoxParallel.setEnabled(false);
CompositeManufacturing.textParallelPort.setEnabled(true);
CompositeManufacturing.textParallelPort.setText("");
CompositeManufacturing.textParallelPort.setEnabled(false);
}
if(CompositeManufacturing.checkBoxUSBB.getSelection())
{
isConnectionTypeSerial=false;
isConnectionTypeParallelOnly=false;
connectionType=ConnectionType.OS_PRINTER;
conn=new WindowsPrinterConnection();
PrinterHelper.printer.openConnection(conn);
}
else if(CompositeManufacturing.checkBoxSerial.getSelection())
{
connectionType=ConnectionType.SERIAL;
isConnectionTypeSerial=true;
isConnectionTypeParallelOnly=false;
conn=new SerialConnection(CompositeManufacturing.textSerialPort.getText(), baud, 1);
PrinterHelper.printer.openConnection(conn);
boolean received=false;
PrinterHelper.send("!SHOW MODELNUMBER");
received=PrinterHelper.waitFor(".*Model Number.*", 1);
if(!received)
{
try
{
int newBaud=115200;
if(baud==115200)
{
newBaud=9600;
}
GUIHelper.logger.info("Establishing serial connection failed at baud rate of "+baud);
GUIHelper.logger.info("Trying to connect again at a different baud rate of "+newBaud);
if(establishSerialConnection(newBaud))
{
if(newBaud==9600)
{
PrinterHelper.setVariable("COMM", "115200,N,8,1,N");
PrinterHelper.printer.waitForResponse(".*115200.*", 10);
GUIHelper.logger.info("Trying to re-establish serial connection at 115200 baud rate");
return establishSerialConnection(115200);
}
else
{
return true;
}
}
else
{
return false;
}
}
catch(Exception ex)
{
GUIHelper.error(Messages.getString("TestEngine.14")+connectionType+".\n\n"+ex);
GUIHelper.logger.error(ex, ex);
return false;
}
}
}
else if(CompositeManufacturing.checkBoxNetwork.getSelection())
{
connectionType=ConnectionType.NETWORK;
isConnectionTypeSerial=false;
isConnectionTypeParallelOnly=false;
conn=new NetworkConnection(CompositeManufacturing.textIPAddress.getText(), 9100);
PrinterHelper.printer.openConnection(conn);
}
else if(CompositeManufacturing.checkBoxParallel.getSelection())
{
connectionType=ConnectionType.PARALLEL;
isConnectionTypeSerial=false;
isConnectionTypeParallelOnly=true;
conn=new ParallelConnection(CompositeManufacturing.textParallelPort.getText());
PrinterHelper.printer.openConnection(conn);
}
else
{
GUIHelper.error(Messages.getString("TestEngine.13"));
return false;
}
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("TestEngine.14")+connectionType+".\n\n"+e);
GUIHelper.logger.error(e, e);
return false;
}
if(!isConnectionTypeParallelOnly&&!isConnectionTypeSerial)
{
if(!PrinterHelper.printer.isConnected())
{
if(connectionType==ConnectionType.OS_PRINTER)
{
GUIHelper.logger.warn("Closing pre-existing connection again for USB Direct Connect.");
try
{
PrinterHelper.printer.closeConnection();
PrinterHelper.printer.openConnection(conn);
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("TestEngine.14")+connectionType+".\n\n"+e);
GUIHelper.logger.error(e, e);
return false;
}
if(!PrinterHelper.printer.isConnected())
{
GUIHelper.error(Messages.getString("TestEngine.14")+connectionType);
return false;
}
}
GUIHelper.error(Messages.getString("TestEngine.14")+connectionType);
return false;
}
}
GUIHelper.logger.info("Connected.");
return true;
}
static boolean establishSerialConnection(int baud)
{
GUIHelper.logger.info("");
return establishSerialConnection(baud, true);
}
static boolean establishSerialConnection(int baud, boolean showErrorDialog)
{
GUIHelper.logger.info("");
IPrinterConnection conn=null;
try
{
if(PrinterHelper.printer!=null)
{
GUIHelper.logger.warn("Closing pre-existing connection.");
try
{
PrinterHelper.printer.closeConnection();
GUIHelper.mainShell.setConnectionState(false, false);
}
catch(Exception e)
{
GUIHelper.logger.warn(e, e);
}
}
PrinterHelper.printer=new Printer();
isConnectionTypeSerial=true;
isConnectionTypeParallelOnly=false;
if(runningManufacturing)
{
conn=new SerialConnection(CompositeManufacturing.textSerialPort.getText(), baud, 1);
}
else
{
conn=new SerialConnection(CompositeConnectionSettings.comPort, baud, CompositeConnectionSettings.stopBits);
}
PrinterHelper.printer.openConnection(conn);
PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 1);
boolean received=false;
PrinterHelper.send("!SHOW MODELNUMBER");
received=PrinterHelper.waitFor(".*Model Number.*", 1);
if(!received)
{
if(showErrorDialog)
{
GUIHelper.error(Messages.getString("TestEngine.14")+ConnectionType.SERIAL);
}
return false;
}
else
{
GUIHelper.logger.info("Connected.");
return true;
}
}
catch(Exception e)
{
if(showErrorDialog)
{
GUIHelper.error(Messages.getString("TestEngine.14")+ConnectionType.SERIAL);
}
return false;
}
}
static boolean setSN_MAC(PrinterId printerId)
{
GUIHelper.logger.info("");
macHex=printerId.getQualifiedMac();
serialNumber=printerId.getQualifiedSerialNumber();
if(isGattaca&&(!serialNumber.startsWith("V")))
{
isMadeInChina=false;
serialNumber=serialNumber.replace(serialNumber.charAt(0), 'L');
}
else if(!(productLine==ProductLine.CI||productLine==ProductLine.CXI))
{
isMadeInChina=true;
}
if(isMadeByEnnoconnMalaysia)
{
serialNumber=serialNumber.replace(serialNumber.charAt(0), 'E');
}
GUIHelper.logger.info("Assigning new serial number ["+serialNumber+"].");
PrinterHelper.send("!LOAD SERIALNUMBER "+serialNumber);
if(isNetwork)
{
PrinterHelper.send("!LOAD MAC "+macHex);
GUIHelper.logger.info("Assigning new MAC ["+macHex+"].");
}
return true;
}
static TestStatus setModelNumber()
{
GUIHelper.logger.info("");
if(!isConnectionTypeParallelOnly)
{
if(currentModelNumber==null)
{
return TestStatus.FAILED;
}
if(currentModelNumber.equalsIgnoreCase(testModelNumber))
{
return TestStatus.SKIPPED;
}
}
if(brandNewPrinter||isConnectionTypeParallelOnly)
{
PrinterHelper.send("!LOAD MODELNUMBER "+testModelNumber);
changedModelNumber=true;
currentModelNumber=testModelNumber;
return TestStatus.PASSED;
}
else
{
String message=Messages.getString("TestEngine.16")+currentModelNumber+".\n"+Messages.getString("TestEngine.17")+testModelNumber+".\n\n"+Messages.getString("TestEngine.18");
int button=GUIHelper.message(message, SWT.OK|SWT.CANCEL|SWT.ICON_QUESTION);
if(button==SWT.CANCEL)
{
changedModelNumber=false;
return TestStatus.CANCELLED;
}
PrinterHelper.send("!LOAD MODELNUMBER "+testModelNumber);
changedModelNumber=true;
return TestStatus.PASSED;
}
}
static void updateOEMIdentifier(TestStep testStep)
{
if(testStep.equals(TestStep.INITIAL_SETUP))
{
if(testModelNumber.startsWith("700"))
{
PrinterHelper.send("!SET OEMMODELID "+CompositeManufacturing.textModelNumber.getText());
if(isTDModelNumber)
{
PrinterHelper.send("!SET OEMIDENTIFIER TALLYDASCOM");
}
else
{
PrinterHelper.send("!SET OEMIDENTIFIER TALLYGENICOM");
}
}
else
{
PrinterHelper.send("!SET OEMMODELID 0");
PrinterHelper.send("!SET OEMIDENTIFIER COGNITIVE");
}
if(!isConnectionTypeParallelOnly)
{
if(supportsFactoryRestoreCommand)
{
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE FACTORY_RESTORE\nEND");
}
else
{
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE RESET\nEND");
}
}
else
{
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE RESET\nEND");
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE FACTORY_RESTORE\nEND");
}
}
if(testStep.equals(TestStep.PRINTER_IDENTIFICATION))
{
if(testModelNumber.startsWith("700"))
{
PrinterHelper.send("!SET OEMMODELID "+CompositeManufacturing.textModelNumber.getText());
if(isTDModelNumber)
{
PrinterHelper.send("!SET OEMIDENTIFIER TALLYDASCOM");
}
else
{
PrinterHelper.send("!SET OEMIDENTIFIER TALLYGENICOM");
}
}
else
{
PrinterHelper.send("!SET OEMMODELID "+"0");
PrinterHelper.send("!SET OEMIDENTIFIER COGNITIVE");
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE RESET\nEND");
try
{
PrinterHelper.setVariable("ETHERNET", "RESET");
Thread.sleep(40000);
}
catch(Exception e)
{
GUIHelper.logger.warn(e, e);
}
PrinterHelper.printer.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 40);
}
if(testStep.equals(TestStep.CPR_DOWNLOAD))
{
if(testModelNumber.startsWith("700"))
{
PrinterHelper.send("!SET OEMMODELID "+CompositeManufacturing.textModelNumber.getText());
if(isTDModelNumber)
{
PrinterHelper.send("!SET OEMIDENTIFIER TALLYDASCOM");
}
else
{
PrinterHelper.send("!SET OEMIDENTIFIER TALLYGENICOM");
}
}
else
{
PrinterHelper.send("!SET OEMMODELID 0");
PrinterHelper.send("!SET OEMIDENTIFIER COGNITIVE");
}
}
}
static TestStatus identifyBrandNewPrinter()
{
GUIHelper.logger.info("");
changedSerialNumber=false;
changedMAC=false;
PrinterId printerId=null;
try
{
printerId=dbCreateNewPrinter(locationCode.toString().charAt(0), testModelNumber, isNetwork);
}
catch(Exception e)
{
GUIHelper.error(e.toString());
GUIHelper.logger.error(e, e);
return TestStatus.FAILED;
}
if(printerId==null)
{
int button=GUIHelper.message(Messages.getString("TestEngine.19"), SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button==SWT.NO)
{
return TestStatus.FAILED;
}
else
{
return TestStatus.SKIPPED;
}
}
boolean success=setSN_MAC(printerId);
if(!success)
{
return TestStatus.FAILED;
}
else
{
changedSerialNumber=true;
if(isNetwork)
{
changedMAC=true;
}
return TestStatus.PASSED;
}
}
static boolean verifySerialNumberFormat(String sn)
{
if(sn.length()==0||sn.equalsIgnoreCase("z060800000"))
{
return false;
}
String match=GUIHelper.getMatch(sn, "\\w\\d{9}");
if(match.equals(sn))
{
if(isGattaca&&(!serialNumber.startsWith("V")))
{
isMadeInChina=false;
}
else if(!(productLine==ProductLine.CI||productLine==ProductLine.CXI))
{
isMadeInChina=true;
}
return true;
}
else
{
return false;
}
}
static boolean verifyMACFormat(String mc)
{
if(mc.length()==0||(mc.equalsIgnoreCase("00:00:00:00:00:ff")&&isNetwork))
{
return false;
}
if(isNetwork)
{
String match=GUIHelper.getMatch(mc, "\\w{2}(:\\w{2}){5}");
if(match.equals(mc))
{
return true;
}
else
{
return false;
}
}
else
{
return true;
}
}
static boolean verifySpecifiedIdentity()
{
if(!verifySerialNumberFormat(CompositeManufacturing.textSerialNumberActual.getText()))
{
GUIHelper.error(Messages.getString("TestEngine.20"));
return false;
}
if(!verifyMACFormat(CompositeManufacturing.textMacAddressActual.getText()))
{
GUIHelper.error(Messages.getString("TestEngine.21"));
return false;
}
return true;
}
static TestStatus assignSpecifiedIdentity()
{
GUIHelper.logger.info("");
try
{
if(!verifySpecifiedIdentity())
{
return TestStatus.FAILED;
}
PrinterHelper.send("!LOAD SERIALNUMBER "+CompositeManufacturing.textSerialNumberActual.getText());
PrinterHelper.send("!LOAD MAC "+CompositeManufacturing.textMacAddressActual.getText());
serialNumber=CompositeManufacturing.textSerialNumberActual.getText();
changedSerialNumber=true;
macHex=CompositeManufacturing.textMacAddressActual.getText();
if(isNetwork)
{
changedMAC=true;
}
CompositeManufacturing.checkBoxSpecSNMAC.setSelection(false);
CompositeManufacturing.textSerialNumberActual.setEditable(false);
CompositeManufacturing.textMacAddressActual.setEditable(false);
}
catch(Exception e)
{
return TestStatus.FAILED;
}
return TestStatus.PASSED;
}
static TestStatus doPrinterIdentification(boolean getNewPrinterID)
{
GUIHelper.logger.info("");
TestStatus subTestRes;
if(CompositeManufacturing.checkBoxSpecSNMAC.getSelection())
{
subTestRes=assignSpecifiedIdentity();
if(subTestRes==TestStatus.FAILED||subTestRes==TestStatus.CANCELLED)
{
return subTestRes;
}
}
else if(brandNewPrinter)
{
if(!isConnectionTypeParallelOnly)
{
PrinterHelper.send("!erase eventlog");
PrinterHelper.send("!LOAD INCHCOUNT 0x0000");
}
if(getNewPrinterID)
{
subTestRes=identifyBrandNewPrinter();
if(subTestRes==TestStatus.FAILED||subTestRes==TestStatus.CANCELLED)
{
return subTestRes;
}
}
}
subTestRes=setModelNumber();
if(subTestRes==TestStatus.FAILED||subTestRes==TestStatus.CANCELLED)
{
return subTestRes;
}
if(!(brandNewPrinter||changedMAC||changedModelNumber||changedSerialNumber))
{
return TestStatus.SKIPPED;
}
GUIHelper.logger.info("brandNewPrinter="+brandNewPrinter);
if(brandNewPrinter&&(!isConnectionTypeParallelOnly))
{
String ic=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW INCHCOUNT"));
if(!ic.equalsIgnoreCase("0"))
{
String msg=Messages.getString("TestEngine.22")+ic;
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return TestStatus.FAILED;
}
}
GUIHelper.logger.info("changedSerialNumber="+changedSerialNumber);
if(changedSerialNumber&&(!isConnectionTypeParallelOnly))
{
String sn=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW SERIALNUMBER"));
if(!sn.equalsIgnoreCase(serialNumber))
{
GUIHelper.error(Messages.getString("TestEngine.23")+serialNumber+"; current value is "+sn);
return TestStatus.FAILED;
}
}
GUIHelper.logger.info("changedModelNumber="+changedModelNumber);
if(changedModelNumber&&(!isConnectionTypeParallelOnly))
{
String mn=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MODELNUMBER"));
if(!mn.equalsIgnoreCase(testModelNumber))
{
GUIHelper.error(Messages.getString("TestEngine.24")+testModelNumber+Messages.getString("TestEngine.26")+mn);
return TestStatus.FAILED;
}
}
GUIHelper.logger.info("changedMAC="+changedMAC);
if(changedMAC&&(!isConnectionTypeParallelOnly))
{
String ma=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MAC"));
if(!ma.equalsIgnoreCase(macHex))
{
GUIHelper.error(Messages.getString("TestEngine.25")+macHex+Messages.getString("TestEngine.26")+ma);
return TestStatus.FAILED;
}
}
return TestStatus.PASSED;
}
static String httpGet(String urlString)
{
GUIHelper.logger.trace(urlString);
StringBuffer httpResponse=new StringBuffer();
try
{
GUIHelper.setCursor(SWT.CURSOR_WAIT);
URL server=new URL(urlString);
HttpURLConnection connection=(HttpURLConnection)server.openConnection();
connection.connect();
InputStream in=connection.getInputStream();
int c;
while((c=in.read())!=-1)
{
httpResponse.append((char)c);
}
in.close();
connection.disconnect();
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
return null;
}
finally
{
GUIHelper.setCursor(SWT.DEFAULT);
}
GUIHelper.logger.info(httpResponse.toString());
return httpResponse.toString();
}
static PrinterId dbCreateNewPrinter(char locationCode, String modelNumber, boolean isNetwork) throws DBException
{
GUIHelper.logger.info("");
Calendar calendar=new GregorianCalendar();
int year=calendar.get(Calendar.YEAR)-2000;
int week=calendar.get(Calendar.WEEK_OF_YEAR);
String snPrefix=String.format("%02d%02d", year, week);
StringBuffer urlEncodedQuery=new StringBuffer();
urlEncodedQuery.append("&");
urlEncodedQuery.append("act=inp");
urlEncodedQuery.append("&");
urlEncodedQuery.append("mn=");
urlEncodedQuery.append(modelNumber.replaceAll(" ", "%20"));
urlEncodedQuery.append("&");
urlEncodedQuery.append("lc=");
urlEncodedQuery.append(locationCode);
urlEncodedQuery.append("&");
urlEncodedQuery.append("snp=");
urlEncodedQuery.append(snPrefix);
urlEncodedQuery.append("&");
urlEncodedQuery.append("eth=");
urlEncodedQuery.append(isNetwork);
String url=jadminURLBase+urlEncodedQuery.toString();
if(isTGModelNumber||isTDModelNumber)
{
url=jadminURLBaseTG+urlEncodedQuery.toString();
}
String xmlPrinterId=httpGet(url);
if(xmlPrinterId==null)
{
return null;
}
else
{
return new PrinterId(xmlPrinterId);
}
}
static void displayPrinterID()
{
CompositeManufacturing.textSerialNumberActual.setText(serialNumber);
CompositeManufacturing.textMacAddressActual.setText(macHex);
CompositeManufacturing.textSerialNumberActual.setEnabled(true);
CompositeManufacturing.textMacAddressActual.setEnabled(true);
}
static TestStatus doLoadCPRs()
{
GUIHelper.logger.info("");
String currentFirmwarePartNumber=getCurrentFirmwarePartNumber();
if(Integer.parseInt(currentFirmwarePartNumber.substring(currentFirmwarePartNumber.length()-3))>326)
{
supportsFactoryRestoreCommand=true;
}
else
{
supportsFactoryRestoreCommand=false;
}
skipLoadingCPRs=GUIHelper.prefs.getBoolean("ACTIVATE_MANUFACTURING", false);
if(skipLoadingCPRs)
{
try
{
skipLoadingCPRs=CompositeManufacturing.checkBoxSkipLoad3.getSelection();
}
catch(Exception e)
{
skipLoadingCPRs=false;
}
}
if(!skipLoadingCPRs)
{
Vector<String>cprFiles=new Vector<String>();
switch(productLine)
{
case CI:
cprFiles.add("base_c.cpr");
if((isTGModelNumber||isTDModelNumber)&&testModelNumber.startsWith("7006"))
{
cprFiles.add("7006PID.cpr");
}
break;
case CXI:
cprFiles.add("base_c.cpr");
if((isTGModelNumber||isTDModelNumber)&&testModelNumber.startsWith("7008"))
{
cprFiles.add("7008PID.cpr");
}
break;
case LX:
if(isGattaca)
{
cprFiles.add("base_gattaca_lx.cpr");
}
break;
case DLX:
if(!isWingman)
{
if(!isGattaca)
{
cprFiles.add("base_dlx.cpr");
if((isTGModelNumber||isTDModelNumber)&&testModelNumber.startsWith("7005"))
{
cprFiles.add("7005PID.cpr");
}
}
else
{
cprFiles.add("base_gattaca_dlx.cpr");
}
}
else
{
cprFiles.add("base_wingman.cpr");
}
break;
}
if (printHead==PrintHead.Automatic)
{
if (productLine==ProductLine.DLX)
{
GUIHelper.logger.info("Printhead auto type selection: switching to use ROHM_KDxxxxDF type.");
printHead=PrintHead.ROHM_KDxxxxDF;
}
if ((productLine==ProductLine.CI||productLine==ProductLine.CXI))
{
GUIHelper.logger.info("Printhead auto type selection: switching to use HEC type.");
printHead=PrintHead.HEC;
}
}
if(productLine==ProductLine.DLX&&printHead!=PrintHead.ROHM_KDxxxxDF)
{
int button=GUIHelper.message("Warning!The printhead selected for the DLX printer is not set as "+"\"ROHM_KDxxxxDF\", which is what the production line uses by default. If the operator intended "+"on selecting a printhead other than \"ROHM_KDxxxxDF\", click yes to continue with the custom option. "+"Otherwise, click no to use the production line default printhead.", SWT.ICON_WARNING|SWT.YES|SWT.NO|SWT.CANCEL);
if (button==SWT.YES)
{
GUIHelper.logger.warn("User has manually decided to use a non-standard print head configuration of "+printHead.toString()+".");
}
else if(button==SWT.NO)
{
GUIHelper.logger.warn("User has manually decided to use a standard print head configuration of "+printHead.toString()+".");
CompositeManufacturing.comboPrintHead.select(1);
printHead=PrintHead.ROHM_KDxxxxDF;
}
else if(button==SWT.CANCEL)
{
return TestStatus.CANCELLED;
}
}
if((productLine==ProductLine.CI||productLine==ProductLine.CXI)&&printHead!=PrintHead.HEC)
{
int button=GUIHelper.message("Warning!The printhead selected for the C-Series printer is not set as "+"\"HEC\", which is what the production line uses by default. If the operator intended "+"on selecting a printhead other than \"HEC\", click*Yes*to continue with the custom option. "+"Otherwise, click*No*to use the production line default printhead.", SWT.ICON_WARNING|SWT.YES|SWT.NO|SWT.CANCEL);
if (button==SWT.YES)
{
GUIHelper.logger.warn("User has manually decided to use a non-standard print head configuration of "+printHead.toString()+".");
}
else if(button==SWT.NO)
{
GUIHelper.logger.warn("User has manually decided to use a standard print head configuration of "+printHead.toString()+".");
CompositeManufacturing.comboPrintHead.select(2);
printHead=PrintHead.HEC;
}
else if(button==SWT.CANCEL)
{
return TestStatus.CANCELLED;
}
}
if((productLine==ProductLine.DLX)&&(printHead==PrintHead.Kyocera))
{
cprFiles.add("phw_"+printHead.toString().toLowerCase()+"_"+mediaWidth+"in_"+printDensity+"dpi.cpr");
}
else
{
cprFiles.add("ph_"+printHead.toString().toLowerCase()+"_"+mediaWidth+"in_"+printDensity+"dpi.cpr");
}
switch(printMethod)
{
case DT:
cprFiles.add("mode_dt.cpr");
break;
case TT:
switch(productLine)
{
case CI:
case CXI:
cprFiles.add("mode_tt.cpr");
break;
case LX:
case DLX:
cprFiles.add("mode_tt_auto.cpr");
break;
}
}
switch(productLine)
{
case CI:
if(printMethod.equals(PrintMethod.TT))
{
cprFiles.add("ps_ci_tt.cpr");
}
else
{
cprFiles.add("ps_ci.cpr");
}
break;
case LX:
break;
case DLX:
cprFiles.add("ps_"+productLine.toString().toLowerCase()+".cpr");
break;
case CXI:
cprFiles.add("ps_"+productLine.toString().toLowerCase()+"_"+printDensity+"dpi.cpr");
break;
}
switch(productLine)
{
case CI:
case CXI:
cprFiles.add("tof_c.cpr");
break;
case LX:
case DLX:
cprFiles.add("tof_dlx.cpr");
break;
}
switch(exitOption)
{
case STANDARD:
case TEAR_BAR:
cprFiles.add("exit_standard.cpr");
break;
case PEELER:
cprFiles.add("exit_peeler_cutter.cpr");
break;
}
if(isApple)
{
cprFiles.add("apple.cpr");
}
cprFiles.add("serial_96008N1.cpr");
String cprPackage="com.cognitive.manufacturing.cpr";
Vector<String>fileNames=GUIHelper.getPackageEntries(cprPackage);
HashMap<String, Integer>patternBasedCprs=new HashMap<String, Integer>();
String modelNumber=CompositeManufacturing.textModelNumber.getText().trim();
if(!fileNames.isEmpty())
{
for(String fileName : fileNames)
{
String pattern=fileName.replace('x', '.');
pattern=pattern.replace('X', '.');
if(modelNumber.matches(pattern))
{
Integer count=new Integer(pattern.split("\\.",-1).length-1);
patternBasedCprs.put(fileName, count);
}
}
if(!patternBasedCprs.isEmpty())
{
LinkedHashMap<String, Integer>patternBasedSortedCprs=sortHashMapByValuesD(patternBasedCprs);
for(Iterator<String>it=patternBasedSortedCprs.keySet().iterator(); it.hasNext();)
{
cprFiles.add(it.next());
}
patternBasedSortedCprs.clear();
}
patternBasedCprs.clear();
}
fileNames.clear();
if(isSecurity)
{
String sSecurityCPR=SecurityConfig.getSetting(customization, "CPR_SETTINGS");
cprFiles.clear();
if(!sSecurityCPR.equals(""))
cprFiles.add(sSecurityCPR);
}
String cprResourcePath="com/cognitive/manufacturing/cpr/";
boolean success;
for(String cprFile : cprFiles)
{
success=loadCPRFile(cprResourcePath+cprFile);
if(!success)
{
return TestStatus.FAILED;
}
}
if(isMeter)
{
if(!loadCPRFile("com/cognitive/manufacturing/cpr/MeterEnabled.cpr"))
return TestStatus.FAILED;
PrinterHelper.setVariable("BUFFER_TIMED_RESET", "65534");
PrinterHelper.setVariable("DARKNESS", "50");
PrinterHelper.setVariable("TXTBFR", "8192 4096");
PrinterHelper.setVariable("LANGUAGE", "NONE");
PrinterHelper.setVariable("COMPATIBLE", "OFF");
PrinterHelper.setVariable("COMPATIBLE LOCAL_PITCH", "ON");
PrinterHelper.setVariable("COMPATIBLE LX_VAR_ERROR", "ON");
PrinterHelper.setVariable("COMPATIBLE DBF_ROT_LOC_ADJUST", "OFF");
PrinterHelper.setVariable("COMPATIBLE DISABLE_RG_JUSTIFY", "ON");
PrinterHelper.setVariable("COMPATIBLE POWERUP_PITCH", "OFF");
PrinterHelper.setVariable("COMPATIBLE USE_LX_PARSER", "ON");
PrinterHelper.setVariable("COMPATIBLE LX_HEAD_DEFS", "ON");
PrinterHelper.setVariable("COMPATIBLE LX_SINGLE_LABEL", "ON");
}
PrinterHelper.setVariable("USER_FEEDBACK", "ON");
if(isSecurity)
{
try
{
Thread.sleep(5000);
}
catch(Exception e)
{
}
String[] sVariables=new String[]{"CPR_SCRIPT", "SECURITY_SCRIPT", "PROFILE_SCRIPT"};
for(int iVar=0; iVar<sVariables.length;++iVar)
{
String sSecurityResource=SecurityConfig.getSetting(customization, sVariables[iVar]);
byte[] buff;
if(!sSecurityResource.equals(""))
{
buff=GUIHelper.getResourceAsByteArray(cprResourcePath+sSecurityResource);
if(buff==null)
{
GUIHelper.error("Can't load custom file: "+sSecurityResource);
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
}
}
return TestStatus.PASSED;
}
else
{
return TestStatus.SKIPPED;
}
}
@SuppressWarnings("unchecked")
static LinkedHashMap<String, Integer>sortHashMapByValuesD(HashMap passedMap)
{
List mapKeys=new ArrayList(passedMap.keySet());
List mapValues=new ArrayList(passedMap.values());
Comparator descending=Collections.reverseOrder();
Collections.sort(mapValues, descending);
Collections.sort(mapKeys);
LinkedHashMap sortedMap=new LinkedHashMap();
Iterator valueIt=mapValues.iterator();
while(valueIt.hasNext())
{
Object val=valueIt.next();
String comp2=val.toString();
Iterator keyIt=mapKeys.iterator();
while(keyIt.hasNext())
{
Object key=keyIt.next();
String comp1=passedMap.get(key).toString();
if(comp1.equals(comp2))
{
passedMap.remove(key);
mapKeys.remove(key);
sortedMap.put((String)key, (Double)val);
break;
}
}
}
return sortedMap;
}
static boolean loadCPRFile(String cprResourceName)
{
GUIHelper.logger.info("");
byte[] buff=GUIHelper.getResourceAsByteArray(cprResourceName);
if(buff!=null)
{
PrinterHelper.send(buff);
PrinterHelper.send("\n");
if(!isConnectionTypeParallelOnly)
{
PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 15);
}
if(cprResourceName.contains("base"))
{
updateOEMIdentifier(TestStep.CPR_DOWNLOAD);
}
if(supportsFactoryRestoreCommand)
{
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE FACTORY_RESTORE\nEND");
}
else
{
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE RESET\nEND");
}
GUIHelper.logger.info("Sent CPR file "+cprResourceName);
return true;
}
else
{
GUIHelper.error(Messages.getString("TestEngine.28")+cprResourceName);
GUIHelper.logger.error(Messages.getString("TestEngine.29")+cprResourceName);
return false;
}
}
static boolean loadBaseCPRFile(String cprResourceName)
{
GUIHelper.logger.info("");
byte[] buff=GUIHelper.getResourceAsByteArray(cprResourceName);
if(buff!=null)
{
PrinterHelper.send(buff);
PrinterHelper.send("\n");
GUIHelper.logger.info("Sent CPR file "+cprResourceName);
return true;
}
else
{
GUIHelper.error(Messages.getString("TestEngine.28")+cprResourceName);
GUIHelper.logger.error(Messages.getString("TestEngine.29")+cprResourceName);
return false;
}
}
static TestStatus doLoadFonts(int printDensity)
{
GUIHelper.logger.info("");
skipLoadingFonts=GUIHelper.prefs.getBoolean("ACTIVATE_MANUFACTURING", false);
if(skipLoadingFonts)
{
try
{
skipLoadingFonts=CompositeManufacturing.checkBoxSkipLoad2.getSelection();
}
catch(Exception e)
{
skipLoadingFonts=false;
}
}
if(!skipLoadingFonts)
{
if(!isSecurity)
{
PrinterHelper.setVariable("USER_FEEDBACK", "ON");
String currentFirmwarePartNumber=getCurrentFirmwarePartNumber();
if(Integer.parseInt(currentFirmwarePartNumber.substring(currentFirmwarePartNumber.length()-3))>156)
{
supportsFontCheckCommand=true;
}
else
{
supportsFontCheckCommand=false;
}
if((!supportsFontCheckCommand)||isConnectionTypeParallelOnly)
{
PrinterHelper.send("!i 3\n");
boolean success=false;
if(!isConnectionTypeParallelOnly)
{
success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 30);
}
else
{
try
{
Thread.sleep(4000);
success=true;
}
catch(Exception ex)
{
success=false;
}
}
if(!success)
{
String msg="Unable to delete all fonts and objects";
GUIHelper.logger.error(msg);
GUIHelper.error(msg);
return TestStatus.FAILED;
}
}
TestStatus subTest=TestStatus.FAILED;
String fontPackage="com.cognitive.manufacturing.fonts";
String[] fontList=null;
if(!isPCL)
{
if(printDensity==200)
{
if(isWingman)
{
fontList=FontMap.wingman200dpi;
}
else if(isLX)
{
fontList=FontMap.lx200dpi;
}
else if(isResco)
{
fontList=FontMap.resco;
}
else
{
fontList=FontMap.general200dpi;
}
}
else
{
if(isWingman)
{
fontList=FontMap.wingman300dpi;
}
else if(isLX)
{
fontList=FontMap.lx300dpi;
}
else
{
fontList=FontMap.general300dpi;
}
}
}
else
{
fontList=FontMap.pcl;
}
subTest=loadFonts(fontPackage, fontList);
if(subTest!=TestStatus.PASSED)
{
return subTest;
}
}
else
{
String sSecurityResource=SecurityConfig.getSetting(customization, "CTR_SCRIPT");
byte[] buff;
if(!sSecurityResource.equals(""))
{
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/"+sSecurityResource);
if(buff==null)
{
GUIHelper.error("Can't load custom file: "+sSecurityResource);
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
try
{
Thread.sleep(5000);
}
catch(Exception e)
{
}
}
if(customization.equals("S00"))
{
PrinterHelper.send("!OBJECT MARK_TYPE 07\n!OBJECT PACK\n");
try
{
Thread.sleep(2000);
}
catch(Exception e)
{
}
}
sSecurityResource=SecurityConfig.getSetting(customization, "SECURITY_IMAGE");
if(!sSecurityResource.equals(""))
{
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/"+sSecurityResource);
if(buff==null)
{
GUIHelper.error("Can't load custom file: "+sSecurityResource);
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
try
{
Thread.sleep(2000);
}
catch(Exception e)
{
}
}
}
return TestStatus.PASSED;
}
return TestStatus.SKIPPED;
}
static TestStatus loadFonts(String fontPackage, String[] fontList)
{
GUIHelper.logger.info("Loading fonts from: "+fontPackage);
Vector<String[]>currentObjects=new Vector<String[]>();
if(!isConnectionTypeParallelOnly)
{
currentObjects=GUIHelper.getParsedObjectList();
}
Vector<String[]>newObjects=new Vector<String[]>();
Vector<String>fileNames=GUIHelper.getPackageEntries(fontPackage);
if(!fileNames.isEmpty())
{
for(int i=0; i<fontList.length; i++)
{
for(String fileName : fileNames)
{
if(fontList[i].toUpperCase().equalsIgnoreCase(fileName.toUpperCase()))
{
String fontName=null;
String fontDesc=null;
int fontType=0;
String filePath=fontPackage.replace('.', '/')+"/"+fileName;
byte[] buff=GUIHelper.getResourceAsByteArray(filePath);
if(buff==null)
{
GUIHelper.logger.error("Can't read data-store font "+fileName);
return TestStatus.FAILED;
}
else
{
int numOfBytes=-1;
if(!currentObjects.isEmpty()&&supportsFontCheckCommand&&!isConnectionTypeParallelOnly)
{
numOfBytes=getObjectInfo(buff, filePath);
if(numOfBytes==-111)
{
GUIHelper.logger.error("Unable to Get Object Info");
GUIHelper.error("Unable to Get Object Info");
return TestStatus.FAILED;
}
}
fontName=getFontName(buff);
fontType=getFontType(buff);
fontDesc=getFontDesc(buff);
if(fontName==null)
{
String msg="Can't extract font name for file "+fileName;
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return TestStatus.FAILED;
}
else
{
newObjects.add(new String[]{fontName, String.valueOf(numOfBytes), fileName, String.valueOf(fontType), fontDesc});
}
}
break;
}
}
}
}
else
{
GUIHelper.logger.info("Font Package "+fontPackage+" is empty.");
}
if(!currentObjects.isEmpty()&&supportsFontCheckCommand&&!isConnectionTypeParallelOnly)
{
boolean isAnyMarkedForDeletion=false;
for(int i=currentObjects.size()-1; i>-1; i--)
{
String[] currentObject=currentObjects.get(i);
boolean found=false;
GUIHelper.logger.info("current font "+i+": "+currentObject[4]);
for(String[] newObject : newObjects)
{
if(currentObject[0].equalsIgnoreCase(newObject[0]))
{
GUIHelper.logger.info("current font "+i+": "+currentObject[4]+" found");
GUIHelper.logger.info("new font: "+newObject[4]);
found=true;
int numOfBytes=Integer.parseInt(newObject[1]);
if(numOfBytes>0)
{
PrinterHelper.send("!OBJECT MARK "+currentObject[0]);
isAnyMarkedForDeletion=true;
GUIHelper.logger.info("Marked for deletion Font (Older Version): "+currentObject[0]);
}
else if(((Integer.parseInt(newObject[3])==9)||(Integer.parseInt(newObject[3])==37))&&!currentObject[4].equalsIgnoreCase(newObject[4]))
{
PrinterHelper.send("!OBJECT MARK "+currentObject[0]);
isAnyMarkedForDeletion=true;
GUIHelper.logger.info("Marked for deletion Font (DPI change): "+currentObject[0]);
newObject[1]="-1";
}
break;
}
}
if(!found)
{
GUIHelper.logger.info("current font "+i+": "+currentObject[4]+" not found");
PrinterHelper.send("!OBJECT MARK "+currentObject[0]);
isAnyMarkedForDeletion=true;
GUIHelper.logger.info("Marked for deletion Font (Extra): "+currentObject[0]);
}
}
if(isAnyMarkedForDeletion)
{
GUIHelper.logger.info("Deleting all the fonts marked..");
PrinterHelper.send("!OBJECT PACK");
boolean success=false;
if(!isConnectionTypeParallelOnly)
{
success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 600);
if(!success)
{
String msg="Unable to delete fonts marked for deletion";
GUIHelper.logger.error(msg);
GUIHelper.error(msg);
return TestStatus.FAILED;
}
}
else
{
try
{
Thread.sleep(4000);
}
catch(Exception ex)
{
return TestStatus.FAILED;
}
}
}
}
for(String[] newObject : newObjects)
{
int numOfBytes=Integer.parseInt(newObject[1]);
if((numOfBytes==-1)||(numOfBytes>0))
{
boolean success=false;
byte[] buff=GUIHelper.getResourceAsByteArray(fontPackage.replace('.', '/')+"/"+newObject[2]);
if(buff==null)
{
GUIHelper.logger.error("Can't read data-store font "+newObject[2]);
return TestStatus.FAILED;
}
else
{
try
{
Thread.sleep(2000);
}
catch(Exception ex)
{
success=false;
}
PrinterHelper.send(buff);
if(!isConnectionTypeParallelOnly)
{
success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
}
else
{
try
{
int waitFor=(int)((buff.length/1024)*50);
Thread.sleep(waitFor);
success=true;
}
catch(Exception ex)
{
success=false;
}
}
if(!success)
{
String msg="Can't confirm flashing of font "+newObject[2];
if(newObject[2].contains("map"))
{
msg="Can't confirm flashing of codepage/keyboard file "+newObject[2];
}
GUIHelper.logger.error(msg);
GUIHelper.error(msg);
return TestStatus.FAILED;
}
GUIHelper.logger.info("Successfully loaded font "+newObject[2]);
}
}
}
return TestStatus.PASSED;
}
static boolean isAsianFont(String fileName)
{
boolean isAsianFont=false;
String[] fontList=FontMap.asianfonts;
for(int i=0; i<fontList.length; i++)
{
if(fontList[i].toUpperCase().equalsIgnoreCase(fileName.toUpperCase()))
{
isAsianFont=true;
}
}
return isAsianFont;
}
static int getObjectInfo(byte[] buff, String filePath)
{
String command=parseFont(buff, filePath);
int i=0;
GUIHelper.logger.info("Command to check font:"+command);
try
{
i=Integer.parseInt(GUIHelper.clean(PrinterHelper.commandWaitResponse(command)));
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
GUIHelper.error(e.getMessage());
i=-111;
}
return i;
}
private static String parseFont(byte[] buff, String filePath)
{
String id=null;
int type=0;
String crc=null;
String version=null;
int length=buff.length;
if(length>500)
{
length=500;
}
String command=null;
String fontString=new String(buff, 0, length);
StringBuffer hexString=new StringBuffer();
String hex=null;
int i=0;
if(buff[0]=='C')
{
i=fontString.indexOf('!');
}
id=fontString.substring(i+10, i+18).trim();
type=buff[i+7]&0xFF;
hexString.append("0x");
if(type>35)
{
hex=Integer.toHexString(buff[i+56]&0xFF);
if(hex.length()==1)
{
hexString.append('0');
}
hexString.append(hex);
hex=Integer.toHexString(buff[i+57]&0xFF);
if(hex.length()==1)
{
hexString.append('0');
}
hexString.append(hex);
version=hexString.toString();
command="!OBJECT INFO "+id+" "+type+" "+version+"\n";
}
else
{
if(type!=14)
{
hex=Integer.toHexString(buff[buff.length-2]&0xFF);
if(hex.length()==1)
{
hexString.append('0');
}
hexString.append(hex);
hex=Integer.toHexString(buff[buff.length-1]&0xFF);
if(hex.length()==1)
{
hexString.append('0');
}
hexString.append(hex);
}
else
{
hexString.append(GUIHelper.getCRC(filePath));
}
crc=hexString.toString();
command="!OBJECT INFO "+id+" "+type+" "+crc+"\n";
}
return command;
}
private static int getFontType(byte[] buff)
{
int fontType=0;
int length=buff.length;
if(length>500)
{
length=500;
}
String fontString=new String(buff, 0, length);
int i=0;
if(buff[0]=='C')
{
i=80;
}
fontType=buff[i+7]&0xFF;
return fontType;
}
private static String getFontName(byte[] buff)
{
String fontName=null;
int length=buff.length;
if(length>500)
{
length=500;
}
String fontString=new String(buff, 0, length);
int i=0;
if(buff[0]=='C')
{
i=80;
}
fontName=fontString.substring(i+10, i+18).trim();
return fontName;
}
private static String getFontDesc(byte[] buff)
{
String fontDesc=null;
int length=buff.length;
if(length>500)
{
length=500;
}
String fontString=new String(buff, 0, length);
int i=0;
if(buff[0]=='C')
{
i=80;
}
fontDesc=fontString.substring(i+22, i+54).trim();
return fontDesc;
}
static boolean fontExists(String name, Vector<String[]>objectList)
{
GUIHelper.logger.trace("");
for(String[] object : objectList)
{
String fontName=object[0];
if(fontName.equalsIgnoreCase(name))
{
String type=object[1];
if(!type.equalsIgnoreCase(GUIHelper.compressedBitmapFont))
{
return true;
}
else
{
String description=object[4];
int fontDensity;
if(description.indexOf("300")!=-1)
{
fontDensity=300;
}
else if(description.indexOf("200")!=-1)
{
fontDensity=200;
}
else
{
fontDensity=0;
}
if(fontDensity==printDensity||fontDensity==0)
{
return true;
}
else
{
isFontDensityDifferent=true;
GUIHelper.logger.debug("Changing from "+fontDensity+" DPI to "+printDensity+" DPI resolution");
GUIHelper.logger.debug("Deleting all the fonts and objects for "+fontDensity+" DPI resolution");
return false;
}
}
}
}
return false;
}
static boolean isIndexingGood(String mode, String feed)
{
GUIHelper.logger.trace("");
int button=GUIHelper.message(Messages.getString("TestEngine.30")+mode+" and feed-type "+feed+"?",
SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button==SWT.YES)
{
return true;
}
else
{
return false;
}
}
static TestStatus doCalibration(PrintMethod printMethod, boolean boardTest)
{
GUIHelper.logger.info("");
boolean result;
if(isSecurity)
{
String sSecurityScript=SecurityConfig.getSetting(customization, "CALIBRATION");
byte[] buff;
if(!sSecurityScript.equals(""))
{
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/"+sSecurityScript);
if(buff==null)
{
GUIHelper.error("Can't load custom file: "+sSecurityScript);
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
}
else
{
if(!boardTest)
{
result=calibrate("DT", "BAR", 0);
if(!result)
return TestStatus.FAILED;
result=calibrate("DT", "GAP", 1);
if(!result)
return TestStatus.FAILED;
}
if(printMethod==PrintMethod.TT)
{
PrinterHelper.setVariable("PRINT_MODE", "TT");
if(!boardTest)
GUIHelper.message(Messages.getString("TestEngine.31"), SWT.ICON_WORKING|SWT.OK);
result=calibrate("TT", "BAR", 23);
if(!result)
return TestStatus.FAILED;
result=calibrate("TT", "GAP", 2);
if(!result)
return TestStatus.FAILED;
}
}
return TestStatus.PASSED;
}
static boolean calibrate(String mode, String feed, int n)
{
String desc="Mode: "+mode+"; Feed: "+feed;
GUIHelper.logger.info(desc);
PrinterHelper.setVariable("PRINT_MODE", mode);
PrinterHelper.setVariable("FEED_TYPE", feed);
PrinterHelper.setVariable("MEASURE_LABEL", "OFF");
PrinterHelper.setVariable("INDEX", "OFF");
if(!doFactoryCalibrate(n, desc))
{
return false;
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nV INDEX SETTING CALIBRATE\nEND");
boolean received=false;
if(!isConnectionTypeParallelOnly)
{
received=PrinterHelper.waitFor(".*C3P.*", 60);
if(!received)
{
GUIHelper.error(Messages.getString("TestEngine.32")+desc);
return false;
}
}
else
{
try
{
Thread.sleep(8000);
}
catch(Exception ex)
{
return false;
}
}
PrinterHelper.setVariable("INDEX", "ON");
PrinterHelper.printTestLabel("", "", "Mode: "+mode+"; Feed: "+feed);
return isIndexingGood(mode, feed);
}
static boolean doFactoryCalibrate(int n, String desc)
{
PrinterHelper.send("!CAL 254");
PrinterHelper.send("!CAL "+n);
boolean received=false;
if(!isConnectionTypeParallelOnly)
{
received=PrinterHelper.waitFor(".*C"+n+"P.*", 60);
if(!received)
{
GUIHelper.error(Messages.getString("TestEngine.32")+desc);
return false;
}
}
else
{
try
{
Thread.sleep(8000);
}
catch(Exception ex)
{
return false;
}
}
PrinterHelper.send("!CAL 255");
return true;
}
static TestStatus doPrintHeadUpDown()
{
GUIHelper.logger.info("");
if(isConnectionTypeParallelOnly)
{
int button=GUIHelper.message(Messages.getString("TestEngine.114"), SWT.ICON_WORKING|SWT.OK);
if(button==SWT.OK)
{
try
{
Thread.sleep(5000);
}
catch(Exception ex)
{
return TestStatus.FAILED;
}
}
else
{
return TestStatus.FAILED;
}
button=GUIHelper.message(Messages.getString("TestEngine.115"), SWT.ICON_WORKING|SWT.OK);
if(button==SWT.OK)
{
try
{
PrinterHelper.setVariable("PRINT_MODE", "DT");
Thread.sleep(5000);
}
catch(Exception ex)
{
return TestStatus.FAILED;
}
}
else
{
return TestStatus.FAILED;
}
}
else
{
PrinterHelper.setVariable("PRINT_MODE", "DT");
JAdminDialogMessage dialog=new JAdminDialogMessage(Messages.getString("TestEngine.33"));
dialog.setMessage(Messages.getString("TestEngine.34"));
dialog.show();
boolean received=PrinterHelper.waitFor(".*U00000.*", 20);
dialog.hide();
if(!received)
{
dialog.close();
GUIHelper.error(Messages.getString("TestEngine.35"));
return TestStatus.FAILED;
}
dialog.setMessage(Messages.getString("TestEngine.36"));
dialog.show();
received=PrinterHelper.waitFor(".*[WR]00000.*", 60);
dialog.close();
if(!received)
{
return TestStatus.FAILED;
}
}
return TestStatus.PASSED;
}
static TestStatus doPrintHeadTest()
{
GUIHelper.logger.info("");
byte[] buff=null;
if(isLX)
{
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/labels/TestPattern_LX.txt");
}
else
{
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/labels/TestPattern.txt");
}
if(buff==null)
{
GUIHelper.error(Messages.getString("TestEngine.37"));
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
int button=GUIHelper.message(Messages.getString("TestEngine.54"), SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button==SWT.YES)
{
return TestStatus.PASSED;
}
else
{
return TestStatus.FAILED;
}
}
static StringBuffer getTestLabelPath(int mediaWidth, int printDensity)
{
StringBuffer path=new StringBuffer();
path.append("com/cognitive/manufacturing/labels/");
switch(mediaWidth)
{
case 2:
path.append("inch2/");
break;
case 4:
path.append("inch4/");
break;
default:
GUIHelper.error(Messages.getString("TestEngine.38")+mediaWidth);
return null;
}
switch(printDensity)
{
case 200:
path.append("dpi200/");
break;
case 300:
path.append("dpi300/");
break;
default:
GUIHelper.error(Messages.getString("TestEngine.39")+printDensity);
return null;
}
return path;
}
static TestStatus doPrintQuality(int mediaWidth, int printDensity)
{
GUIHelper.logger.info("");
boolean success;
StringBuffer path=getTestLabelPath(mediaWidth, printDensity);
if(path==null)
{
return TestStatus.FAILED;
}
PrinterHelper.setVariable("", "HIGHSPEED");
boolean testBT=((productLine!=ProductLine.LX)&&(CompositeManufacturing.checkBoxBT1.getSelection()));
if (testBT&&isBT)
{
StringBuffer btMacBarCode=new StringBuffer();
if(isLX)
{
btMacBarCode.append("!");
}
btMacBarCode.append("!0 100 300 1\n");
btMacBarCode.append("TEXT 1 20 15 Name:"+btDeviceName.replaceAll("BLUETOOTH DEVICENAME", "").replaceAll("=", "").trim().toUpperCase()+"\n");
btMacBarCode.append("TEXT 1 20 50 BT Address:"+btMacAddress+"\n");
btMacBarCode.append("END\n");
PrinterHelper.send(btMacBarCode.toString());
}
StringBuffer ladder=new StringBuffer(path.toString());
if(isWingman)
{
ladder.append("barcode_ladder_epl.txt");
}
else if(isLX)
{
ladder.append("barcode_ladder_LX.txt");
}
else
{
ladder.append("barcode_ladder.txt");
}
StringBuffer picket=new StringBuffer(path.toString());
if(isTDModelNumber)
{
picket.append("barcode_picket_td.txt");
}
else if(isTGModelNumber)
{
picket.append("barcode_picket_tg.txt");
}
else if(isWingman)
{
picket.append("barcode_picket_epl.txt");
}
else if(isLX)
{
picket.append("barcode_picket_LX.txt");
}
else
{
picket.append("barcode_picket.txt");
}
byte[] buff=GUIHelper.getResourceAsByteArray(ladder.toString());
if(buff==null)
{
GUIHelper.error(Messages.getString("TestEngine.40")+ladder.toString()+Messages.getString("TestEngine.41"));
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
success=isQualityAcceptable(CompositeManufacturing.checkBoxScanner.getSelection());
if(!success)
{
return TestStatus.FAILED;
}
buff=GUIHelper.getResourceAsByteArray(picket.toString());
if(buff==null)
{
GUIHelper.error(Messages.getString("TestEngine.40")+picket.toString()+Messages.getString("TestEngine.41"));
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
success=isQualityAcceptable(CompositeManufacturing.checkBoxScanner.getSelection());
if(!success)
{
return TestStatus.FAILED;
}
return TestStatus.PASSED;
}
static TestStatus doRibbonWrinkle(int mediaWidth, int printDensity)
{
GUIHelper.logger.trace("");
if(!((productLine==ProductLine.CI||productLine==ProductLine.CXI)&&printMethod==PrintMethod.TT))
{
return TestStatus.SKIPPED;
}
StringBuffer path=getTestLabelPath(mediaWidth, printDensity);
if(path==null)
{
return TestStatus.FAILED;
}
StringBuffer picket=new StringBuffer(path.toString());
if(isTDModelNumber)
{
picket.append("barcode_picket_td.txt");
}
else if(isTGModelNumber)
{
picket.append("barcode_picket_tg.txt");
}
else if(isLX)
{
picket.append("barcode_picket_LX.txt");
}
else
{
picket.append("barcode_picket.txt");
}
byte[] buff=GUIHelper.getResourceAsByteArray(picket.toString());
if(buff==null)
{
GUIHelper.error(Messages.getString("TestEngine.40")+picket.toString()+Messages.getString("TestEngine.41"));
return TestStatus.FAILED;
}
for(int i=0; i<15; i++)
{
PrinterHelper.send(buff);
try
{
Thread.sleep(700);
}
catch(Exception e)
{
}
}
int button=GUIHelper.message(Messages.getString("TestEngine.46"), SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button==SWT.YES)
{
return TestStatus.FAILED;
}
else
{
return TestStatus.PASSED;
}
}
static boolean scanQuality()
{
JAdminDialogMessage dialog=new JAdminDialogMessage(Messages.getString("TestEngine.47"));
dialog.setMessage(Messages.getString("TestEngine.48"));
dialog.show();
long currentTime=(new GregorianCalendar()).getTimeInMillis();
long maxTime=currentTime+30000;
boolean retVal=false;
while(currentTime<=maxTime)
{
String grade=null;
QualityScanner scanner=null;
try
{
scanner=new QualityScanner(CompositeManufacturing.textScanner.getText().toUpperCase(), 9600);
scanner.openConnection();
grade=scanner.waitForGrade();
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
GUIHelper.error(Messages.getString("TestEngine.49"));
}
finally
{
dialog.hide();
try
{
scanner.closeConnection();
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
GUIHelper.error(Messages.getString("TestEngine.50"));
}
}
if(grade==null)
{
retVal=false;
GUIHelper.error(Messages.getString("TestEngine.51"));
break;
}
GUIHelper.logger.info("Grade: "+grade);
if(grade.matches("[ABC]"))
{
GUIHelper.myDisplay.beep();
retVal=true;
break;
}
else
{
dialog.setMessage(Messages.getString("TestEngine.52")+grade+Messages.getString("TestEngine.53"));
GUIHelper.myDisplay.beep();
GUIHelper.myDisplay.beep();
currentTime=(new GregorianCalendar()).getTimeInMillis();
continue;
}
}
return retVal;
}
static boolean isQualityAcceptable(boolean scan)
{
GUIHelper.logger.trace("");
if(scan)
{
return scanQuality();
}
else
{
int button=GUIHelper.message(Messages.getString("TestEngine.124"), SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button==SWT.YES)
{
return true;
}
else
{
return false;
}
}
}
static TestStatus doFinalSettings()
{
GUIHelper.logger.info("");
boolean isSQ=false;
if(customization!=null)
{
if(customization.equals("01")||customization.equals("COMGRPH"))
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/W2M2.bmp");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: W2M2.bmp");
return TestStatus.FAILED;
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nGRAPHIC STORE BMP 3 W2M2");
PrinterHelper.send(buff);
boolean success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
if(!success)
{
GUIHelper.error("Object download error: W2M2.bmp");
return TestStatus.FAILED;
}
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/COMG_4mat_VTerm5.txt");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: COMG_4mat_VTerm5.txt");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/COMG_DLX_C01.cpr");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: COMG_DLX_C01.cpr");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
if(customization.equals("COMGRPH"))
{
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/DLX_NoLXHeadDefs.cpr");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: DLX_NoLXHeadDefs.cpr");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
}
else if(customization.equals("BS"))
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/bridgestone.txt");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: bridgestone.txt");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/COMG_DLX_C02.cpr");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: COMG_DLX_C02.cpr");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/DLX_NoLXHeadDefs.cpr");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: DLX_NoLXHeadDefs.cpr");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
else if(customization.equals("PCL"))
{
enablePCL(false);
isPCL=true;
}
else if(customization.equals("D1"))
{
boolean success=loadCPRFile("com/cognitive/manufacturing/cpr/DSCXD41000D1.cpr");
if(!success)
{
return TestStatus.FAILED;
}
}
else if(customization.equals("SQ"))
{
String modelNumber=CompositeManufacturing.textModelNumber.getText();
if(!(modelNumber.charAt(11)=='G'&&modelNumber.charAt(12)=='S'&&modelNumber.charAt(13)=='S'))
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/SunQuest.cpr");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: SunQuest.cpr");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
isSQ=true;
}
}
else if(customization.equals("HD"))
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/HomeDepot.cpr");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: HomeDepot.cpr");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
else if(customization.equals("S1"))
{
String modelNumber=CompositeManufacturing.textModelNumber.getText();
if(!(modelNumber.charAt(11)=='G'&&modelNumber.charAt(12)=='S'&&modelNumber.charAt(13)=='S'))
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/Siemens.cpr");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: Siemens.cpr");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
}
}
if(isGWN)
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/gwinb.pcx");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: gwinb.pcx");
return TestStatus.FAILED;
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nGRAPHIC STORE PCX 3 gwinb");
PrinterHelper.send(buff);
boolean success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
if(!success)
{
GUIHelper.error("Object download error: gwinb.pcx");
return TestStatus.FAILED;
}
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/gwn_85.pcx");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: gwn_85.pcx");
return TestStatus.FAILED;
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nGRAPHIC STORE PCX 3 gwn_85");
PrinterHelper.send(buff);
success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
if(!success)
{
GUIHelper.error("Object download error: gwn_85.pcx");
return TestStatus.FAILED;
}
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/gwn_wrnb.pcx");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: gwn_wrnb.pcx");
return TestStatus.FAILED;
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nGRAPHIC STORE PCX 3 gwn_wrnb");
PrinterHelper.send(buff);
success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
if(!success)
{
GUIHelper.error("Object download error: gwn_wrnb.pcx");
return TestStatus.FAILED;
}
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/gwnvdbig.pcx");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: gwnvdbig.pcx");
return TestStatus.FAILED;
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nGRAPHIC STORE PCX 3 gwnvdbig");
PrinterHelper.send(buff);
success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
if(!success)
{
GUIHelper.error("Object download error: gwnvdbig.pcx");
return TestStatus.FAILED;
}
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/vld3x4b.pcx");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: vld3x4b.pcx");
return TestStatus.FAILED;
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nGRAPHIC STORE PCX 3 vld3x4b");
PrinterHelper.send(buff);
success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
if(!success)
{
GUIHelper.error("Object download error: vld3x4b.pcx");
return TestStatus.FAILED;
}
success=loadCPRFile("com/cognitive/manufacturing/cpr/security.cpr");
if(!success)
{
return TestStatus.FAILED;
}
}
if(isResco)
{
boolean success=loadCPRFile("com/cognitive/manufacturing/cpr/resco.cpr");
if(!success)
{
return TestStatus.FAILED;
}
}
if(!isPCL&&CompositeManufacturing.checkBoxDisableLicenses.getSelection())
{
if((productLine==ProductLine.CXI)&&(mediaWidth==4))
{
if(!disablePCL())
{
return TestStatus.FAILED;
}
}
}
isPCL=false;
if((productLine==ProductLine.DLX)&&(printMethod==PrintMethod.TT)&&(printDensity==200)&&(mediaWidth==4))
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/DLX_NoLXHeadDefs.cpr");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: DLX_NoLXHeadDefs.cpr");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
if(isTouchstar)
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/_TSBMP_.bmp");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: _TSBMP_.bmp");
return TestStatus.FAILED;
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nGRAPHIC STORE BMP 3 _TSBMP_");
PrinterHelper.send(buff);
boolean success=PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 120);
if(!success)
{
GUIHelper.error("Object download error: _TSBMP_.bmp");
return TestStatus.FAILED;
}
if(customization.equalsIgnoreCase("TOUCHSTAR2"))
{
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/TS_TestLabel2.prn");
}
else
{
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/TS_TestLabel.prn");
}
if(buff==null)
{
GUIHelper.error("Can't load Custom File: TS_TestLabel.prn");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
success=loadCPRFile("com/cognitive/manufacturing/cpr/touchstar.1Stop.cpr");
if(!success)
{
return TestStatus.FAILED;
}
if(!enableTS())
{
return TestStatus.FAILED;
}
}
else if(isMeter)
{
GUIHelper.logger.trace("Meter-enabled printer-configuring final settings.");
String serialNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW SERIALNUMBER")).toUpperCase().trim();
String addressMAC=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MAC")).toUpperCase().trim();
String key="ERROR";
byte[] buffer=null;
GUIHelper.logger.trace("Generating license key with serial number \""+serialNumber+"\" and MAC address \""+addressMAC+"\".");
String exeName="FPKtool.exe";
String companyname=GUIHelper.brand.getProperty("company.name", "CognitiveTPG").replaceAll("TPG", "").replaceAll(" ", "");
String path="C:/Program Files/"+companyname+"/FPK/";
int exitVal;
try
{
Process proc=new ProcessBuilder(path+exeName, "-e0", "-k13", "-s=\""+serialNumber+"\"", "-m=\""+addressMAC+"\"", "\""+path+"eth_disable_meter_enable.txt\"", "\""+path+"fpk.out\"").start();
exitVal=proc.waitFor();
if(exitVal!=0)
{
String msg="FPK generation failed with error code ["+exitVal+"].";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return TestStatus.FAILED;
}
}
catch(Exception e)
{
String msg="FPK generation failed due to exception ["+e.toString()+"].";
GUIHelper.error(msg);
return TestStatus.FAILED;
}
StringBuffer licenseLabel=new StringBuffer();
try
{
File file=new File(path+"fpk.out");
FileReader fileReader=new FileReader(file);
BufferedReader bufferedReader=new BufferedReader(fileReader);
String line;
while((line=bufferedReader.readLine())!=null)
{
licenseLabel.append(line+"\n");
}
bufferedReader.close();
file.delete();
}
catch(FileNotFoundException e)
{
String msg="Can't find fpk output file.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return TestStatus.FAILED;
}
catch(IOException e)
{
String msg="Can't read fpk output file.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return TestStatus.FAILED;
}
key=licenseLabel.toString();
if(key.contains("ERROR")||key.equals(""))
{
GUIHelper.logger.trace("Failed to generate a license key.");
return TestStatus.FAILED;
}
PrinterHelper.send("!LOAD LICENSE 44 \n"+key+"\n");
buffer=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/Meter_TestLabel.prn");
if(buffer==null)
{
GUIHelper.error("Failed to load custom file \"Meter_TestLabel.prn\".");
return TestStatus.FAILED;
}
if(!loadCPRFile("com/cognitive/manufacturing/cpr/MeterEnabled.cpr"))
return TestStatus.FAILED;
PrinterHelper.send(buffer);
PrinterHelper.setVariable("BUFFER_TIMED_RESET", "65534");
PrinterHelper.setVariable("DARKNESS", "50");
PrinterHelper.setVariable("TXTBFR", "8192 4096");
PrinterHelper.setVariable("LANGUAGE", "NONE");
PrinterHelper.setVariable("COMPATIBLE", "OFF");
PrinterHelper.setVariable("COMPATIBLE LOCAL_PITCH", "ON");
PrinterHelper.setVariable("COMPATIBLE LX_VAR_ERROR", "ON");
PrinterHelper.setVariable("COMPATIBLE DBF_ROT_LOC_ADJUST", "OFF");
PrinterHelper.setVariable("COMPATIBLE DISABLE_RG_JUSTIFY", "ON");
PrinterHelper.setVariable("COMPATIBLE POWERUP_PITCH", "OFF");
PrinterHelper.setVariable("COMPATIBLE USE_LX_PARSER", "ON");
PrinterHelper.setVariable("COMPATIBLE LX_HEAD_DEFS", "ON");
PrinterHelper.setVariable("COMPATIBLE LX_SINGLE_LABEL", "ON");
}
else
{
if(CompositeManufacturing.checkBoxDisableLicenses.getSelection()&&(productLine==ProductLine.DLX)&&(mediaWidth==4))
{
if(!disableTS())
{
return TestStatus.FAILED;
}
}
boolean success=loadCPRFile("com/cognitive/manufacturing/cpr/user_feedback_off.cpr");
if(!success)
{
return TestStatus.FAILED;
}
}
if(isSQ)
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/Set_Pitch_100.txt");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: Set_Pitch_100.txt");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
isSQ=false;
}
if(isResco)
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/resco.txt");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: resco.txt");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
if(isGWN)
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/security.txt");
if(buff==null)
{
GUIHelper.error("Can't load Custom File: security.txt");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
if(isHD)
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE ETHERNET LINK AUTO\nVARIABLE WRITE\nEND\n");
if(testModelNumber.equals("CXT2-1300-C01"))
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE ETHERNET DHCP OFF\nVARIABLE ETHERNET IP 192.168.5.101\nVARIABLE ETHERNET NETMASK 255.255.255.0\nVARIABLE ETHERNET GATEWAY 192.168.5.1\nVARIABLE WRITE\nVARIABLE ETHERNET RESET\nEND\n");
if(isMadeByEnnoconnMalaysia&&serialNumber!=null&&serialNumber.length()>0)
{
if(serialNumber.substring(0, 1)!="E")
{
serialNumber='E'+serialNumber.substring(1);
GUIHelper.logger.info("Loading location-coded serial number ["+serialNumber+"] in final configuration.");
CompositeManufacturing.textSerialNumberActual.setText(serialNumber);
PrinterHelper.send("!LOAD SERIALNUMBER "+serialNumber+"\n\n");
}
}
GUIHelper.logger.info("Implemented factory default settings");
return TestStatus.PASSED;
}
static TestStatus doSelfTest()
{
GUIHelper.logger.info("");
if(isSecurity)
{
int button;
if(isSecurity)
{
String sSecurityTest=SecurityConfig.getSetting(customization, "TEST_SCRIPT");
byte[] buff;
if(!sSecurityTest.equals(""))
{
buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/"+sSecurityTest);
if(buff==null)
{
GUIHelper.error("Can't load test script: "+sSecurityTest);
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
button=GUIHelper.message("Is Voided Prescription Printed Correctly?", SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button!=SWT.YES)
return TestStatus.FAILED;
}
}
PrinterHelper.printSelfTestLabel();
if(isSecurity)
{
if(!customization.equals("S00"))
{
button=GUIHelper.message("Are Appropriate Security Settings Printed On Diagnostic Form?", SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button!=SWT.YES)
return TestStatus.FAILED;
}
PrinterHelper.send("!CAL 254\n!CAL 0\n!CAL 255\n!CAL 0\n");
}
if(isTouchstar)
GUIHelper.message(Messages.getString("TestEngine.123"), SWT.ICON_WORKING|SWT.OK);
}
else
{
if(isBT)
{
PrinterHelper.send("!!0 0 0 0");
PrinterHelper.send("!0 0 0 0");
PrinterHelper.send("VARIABLE BLUETOOTH CONFIGURE ON");
PrinterHelper.send("VARIABLE BLUETOOTH DEFAULT");
PrinterHelper.send("VARIABLE BLUETOOTH DEVICENAME "+testModelNumber);
PrinterHelper.send("VARIABLE BLUETOOTH DISCOVERABLE ON");
PrinterHelper.send("VARIABLE BLUETOOTH RESET");
PrinterHelper.send("END");
try
{
Thread.sleep(2000);
}
catch(Exception ex)
{
return TestStatus.FAILED;
}
}
PrinterHelper.printSelfTestLabel();
if(isTouchstar)
{
GUIHelper.message(Messages.getString("TestEngine.123"), SWT.ICON_WORKING|SWT.OK);
}
if(isGWN)
{
try
{
Thread.sleep(5000);
}
catch(Exception ex)
{
return TestStatus.FAILED;
}
GUIHelper.message(Messages.getString("TestEngine.129"), SWT.ICON_WORKING|SWT.OK);
boolean result;
result=calibrate("DT", "BAR", 0);
if(!result)
{
return TestStatus.FAILED;
}
if(customization.endsWith("A"))
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/GWNASample.txt");
if(buff==null)
{
GUIHelper.error("Can't load Gwinnett A sample script: GWNASample.txt");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
else
{
byte[] buff=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/cpr/GWNBSample.txt");
if(buff==null)
{
GUIHelper.error("Can't load Gwinnett B sample script: GWNBSample.txt");
return TestStatus.FAILED;
}
PrinterHelper.send(buff);
}
}
}
return TestStatus.PASSED;
}
static boolean followUpParallelTest()
{
GUIHelper.logger.info("");
String retString=PrinterHelper.getVariable("SLEEP_AFTER");
if(retString==null)
{
return false;
}
retString=retString.trim();
{
if(!retString.equals(testSleepAfter))
{
GUIHelper.error(Messages.getString("TestEngine.55"));
GUIHelper.logger.info("Testing parallel connection-part two: bad: SLEEP_AFTER is "+retString);
GUIHelper.logger.info("Bad");
return false;
}
else
{
GUIHelper.logger.info("Testing parallel connection-part two: good: SLEEP_AFTER is "+retString);
PrinterHelper.setVariable("SLEEP_AFTER", originalSleepAfter);
GUIHelper.logger.info("Testing parallel connection-part two: good: SLEEP_AFTER reverted to "+originalSleepAfter);
return true;
}
}
}
static boolean postNetworkTest()
{
String ipAddress=PrinterHelper.getVariable("ETHERNET IP");
if(ipAddress==null||!ipAddress.equals(CompositeManufacturing.textIPAddress.getText()))
{
GUIHelper.error(Messages.getString("TestEngine.56")+CompositeManufacturing.textIPAddress.getText()+Messages.getString("TestEngine.57")+ipAddress);
return false;
}
return true;
}
static boolean getNetworkParameters(StringBuffer ipNetmask, StringBuffer ipGateway)
{
return false;
}
static boolean preNetworkTest()
{
GUIHelper.logger.info("");
StringBuffer ipNetmask=new StringBuffer(GUIHelper.prefs.get("MAN_IP_NETMASK", "AUTO"));
StringBuffer ipGateway=new StringBuffer(GUIHelper.prefs.get("MAN_IP_GATEWAY", "AUTO"));
if(ipNetmask.toString().equalsIgnoreCase("AUTO")||ipGateway.toString().equalsIgnoreCase("AUTO"))
{
boolean success=getNetworkParameters(ipNetmask, ipGateway);
if(!success)
{
GUIHelper.error("Unable to determine network setup.\nPlease edit Preferences.xml to determine it manually.");
return false;
}
}
StringBuffer label=new StringBuffer();
label.append("!!0 0 0 0\n");
label.append("!0 0 0 0\n");
label.append("VARIABLE USER_FEEDBACK ON\n");
label.append("VARIABLE ETHERNET DHCP OFF\n");
label.append("VARIABLE ETHERNET IP "+CompositeManufacturing.textIPAddress.getText()+"\n");
label.append("VARIABLE ETHERNET NETMASK "+ipNetmask.toString()+"\n");
label.append("VARIABLE ETHERNET GATEWAY "+ipGateway.toString()+"\n");
label.append("VARIABLE WRITE\n");
label.append("VARIABLE ETHERNET RESET\n");
label.append("END\n");
PrinterHelper.send(label.toString());
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("TestEngine.62"));
return false;
}
try
{
Thread.sleep(10000);
}
catch(Exception ex)
{
return false;
}
boolean success=establishPrimaryConnection(115200);
if(!success)
{
GUIHelper.error(Messages.getString("TestEngine.67"));
return false;
}
try
{
Thread.sleep(20000);
}
catch(Exception ex)
{
return false;
}
return true;
}
static TestStatus doConnectionTypes()
{
btDeviceName="";
GUIHelper.logger.info("");
if(!isConnectionTypeParallelOnly)
{
boolean testForC=false;
boolean testForDLX=false;
boolean testParallel=false;
testForC=((productLine==ProductLine.CI||productLine==ProductLine.CXI)&&CompositeManufacturing.checkBoxParallel.getSelection());
testForDLX=((productLine==ProductLine.DLX||productLine==ProductLine.LX)&&CompositeManufacturing.checkBoxParallel.getSelection()&&((communicationInterface.toString().indexOf("LEGACY")!=-1)||(communicationInterface.toString().indexOf("PARALLEL")!=-1)));
testParallel=testForC||testForDLX;
boolean testSerial=false;
testForC=((productLine==ProductLine.CI||productLine==ProductLine.CXI)&&CompositeManufacturing.checkBoxSerial.getSelection());
testForDLX=((productLine==ProductLine.DLX||productLine==ProductLine.LX)&&CompositeManufacturing.checkBoxSerial.getSelection()&&((communicationInterface.toString().indexOf("LEGACY")!=-1)||(communicationInterface.toString().indexOf("SERIAL")!=-1)));
testSerial=testForC||testForDLX;
boolean testUSBB=((productLine!=ProductLine.LX)&&(CompositeManufacturing.checkBoxUSBB.getSelection()));
boolean testUSBA=((productLine!=ProductLine.LX)&&(CompositeManufacturing.checkBoxUSBA.getSelection()));
boolean testBT=((productLine!=ProductLine.LX)&&(CompositeManufacturing.checkBoxBT1.getSelection()));
boolean testNetwork=false;
testForC=((productLine==ProductLine.CI||productLine==ProductLine.CXI)&&CompositeManufacturing.checkBoxNetwork.getSelection());
testForDLX=((productLine==ProductLine.DLX||productLine==ProductLine.LX)&&(communicationInterface.toString().indexOf("NETWORK")!=-1)&&CompositeManufacturing.checkBoxNetwork.getSelection());
testNetwork=testForC||testForDLX;
JAdminDialogMessage ctDialog=new JAdminDialogMessage(Messages.getString("TestEngine.58"));
ctDialog.show();
try
{
if(testSerial||testBT)
{
PrinterHelper.setVariable("COMM", "115200,N,8,1,N");
PrinterHelper.printer.waitForResponse(".*115200.*", 10);
if(testBT)
{
ctDialog.setMessage(Messages.getString("TestEngine.125"));
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE BLUETOOTH DEFAULT ON\nEND");
try
{
Thread.sleep(5000);
}
catch(Exception e)
{
GUIHelper.logger.warn(e, e);
return TestStatus.FAILED;
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE BLUETOOTH DEVICENAME ?\nEND\n");
btDeviceName=PrinterHelper.printer.waitForResponse(".*BLUETOOTH DEVICENAME=.*", 20);
if(btDeviceName==null||!btDeviceName.trim().startsWith("BLUE"))
{
return TestStatus.FAILED;
}
String message=Messages.getString("TestEngine.126")+btDeviceName+"\n"+Messages.getString("TestEngine.127")+CompositeManufacturing.textBTPort1.getText()+" "+Messages.getString("TestEngine.128");
GUIHelper.message(message, SWT.OK);
}
else
{
ctDialog.setMessage(Messages.getString("TestEngine.61"));
}
}
if(testNetwork)
{
ctDialog.setMessage(Messages.getString("TestEngine.60"));
if(!preNetworkTest())
{
return TestStatus.FAILED;
}
}
if(testParallel)
{
ctDialog.setMessage(Messages.getString("TestEngine.59"));
originalSleepAfter=PrinterHelper.getVariable("SLEEP_AFTER");
if(originalSleepAfter==null)
{
return TestStatus.FAILED;
}
}
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("TestEngine.62"));
return TestStatus.FAILED;
}
if(testParallel)
{
ctDialog.setMessage(Messages.getString("TestEngine.59"));
GUIHelper.logger.info("Testing parallel connection-part one.");
testParallel=true;
String port=CompositeManufacturing.textParallelPort.getText();
IPrinterConnection conn=null;
try
{
conn=new ParallelConnection(port);
PrinterHelper.printer.openConnection(conn);
PrinterHelper.setVariable("SLEEP_AFTER", testSleepAfter);
GUIHelper.logger.info("Testing parallel connection-part one: SLEEP_AFTER set to "+testSleepAfter);
GUIHelper.logger.info("Closing parallel connection.");
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
}
}
if(testUSBB)
{
ctDialog.setMessage(Messages.getString("TestEngine.64"));
if(!testConnection(ConnectionType.OS_PRINTER, false))
{
return TestStatus.FAILED;
}
}
if(testSerial)
{
ctDialog.setMessage(Messages.getString("TestEngine.61"));
if(!testConnection(ConnectionType.SERIAL, false))
{
return TestStatus.FAILED;
}
}
if(testBT)
{
ctDialog.setMessage(Messages.getString("TestEngine.125"));
}
if(testNetwork)
{
ctDialog.setMessage(Messages.getString("TestEngine.60"));
if(!testConnection(ConnectionType.NETWORK, false))
{
return TestStatus.FAILED;
}
}
boolean success=establishPrimaryConnection(115200);
if(!success)
{
GUIHelper.error(Messages.getString("TestEngine.67"));
return TestStatus.FAILED;
}
else
{
if(testBT)
{
ctDialog.setMessage(Messages.getString("TestEngine.125"));
if(isBT)
{
PrinterHelper.send("!!0 0 0 0");
PrinterHelper.send("!0 0 0 0");
PrinterHelper.send("VARIABLE BLUETOOTH CONFIGURE ON");
PrinterHelper.send("VARIABLE BLUETOOTH DEFAULT");
PrinterHelper.send("VARIABLE BLUETOOTH DEVICENAME "+testModelNumber);
PrinterHelper.send("VARIABLE BLUETOOTH DISCOVERABLE ON");
PrinterHelper.send("VARIABLE BLUETOOTH RESET");
PrinterHelper.send("END");
}
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE BLUETOOTH CONFIGURE OFF\nEND\n");
PrinterHelper.printer.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 3);
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE BLUETOOTH BDADDR ?\nEND\n");
btMacAddress=PrinterHelper.printer.waitForResponse(".*BLUETOOTH BDADDR=.*", 20);
if(btMacAddress==null||!btMacAddress.trim().startsWith("BLUE"))
{
return TestStatus.FAILED;
}
btMacAddress=btMacAddress.replaceAll("BLUETOOTH BDADDR", "");
btMacAddress=btMacAddress.replaceAll("=", "").trim().toUpperCase();
btMacAddress=btMacAddress.substring(0, 2)+":"+btMacAddress.substring(2, 4)+":"+btMacAddress.substring(4, 6)+":"+btMacAddress.substring(6, 8)+":"+btMacAddress.substring(8, 10)+":"+btMacAddress.substring(10);
GUIHelper.logger.info(btDeviceName+" and BLUETOOTH Address: "+btMacAddress);
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE BLUETOOTH FORGET\nEND\n");
PrinterHelper.printer.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 2);
PrinterHelper.send("!!0 0 0 0\n!0 0 0 0\nVARIABLE BLUETOOTH RESET\nEND\n");
PrinterHelper.printer.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 4);
}
}
if(testParallel)
{
ctDialog.setMessage(Messages.getString("TestEngine.59"));
if(!followUpParallelTest())
{
return TestStatus.FAILED;
}
}
if(testNetwork)
{
ctDialog.setMessage(Messages.getString("TestEngine.60"));
postNetworkTest();
}
if(testUSBA)
{
ctDialog.setMessage(Messages.getString("TestEngine.70"));
originalSleepAfter=PrinterHelper.getVariable("SLEEP_AFTER");
if(originalSleepAfter==null)
{
return TestStatus.FAILED;
}
GUIHelper.logger.info("Testing USB-A: SLEEP_AFTER before: "+originalSleepAfter);
JAdminDialogMessage dialog=new JAdminDialogMessage("USB-A Test");
dialog.setMessage(Messages.getString("TestEngine.71"));
dialog.show();
int maxTime=60000;
int currentTime=0;
int intervalTime=1000;
String currentSleepAfter=null;
success=false;
while(currentTime<=maxTime)
{
try
{
Thread.sleep(intervalTime);
}
catch(InterruptedException e)
{
GUIHelper.logger.error(e, e);
}
currentTime+=intervalTime;
currentSleepAfter=PrinterHelper.getVariable("SLEEP_AFTER");
if(currentSleepAfter==null)
{
return TestStatus.FAILED;
}
if(currentSleepAfter.equals(originalSleepAfter))
{
continue;
}
else
{
GUIHelper.logger.info("Testing USB-A: SLEEP_AFTER after: "+currentSleepAfter);
PrinterHelper.setVariable("SLEEP_AFTER", originalSleepAfter);
GUIHelper.logger.info("Testing USB-A: SLEEP_AFTER reverted to: "+originalSleepAfter);
success=true;
break;
}
}
dialog.close();
GUIHelper.message(Messages.getString("TestEngine.72"), SWT.OK|SWT.ICON_WORKING);
if(!success)
{
GUIHelper.message(Messages.getString("TestEngine.73"), SWT.OK|SWT.ICON_ERROR);
return TestStatus.FAILED;
}
}
}
finally
{
ctDialog.close();
}
return TestStatus.PASSED;
}
else
{
return TestStatus.SKIPPED;
}
}
static boolean testConnection(ConnectionType connectionType, boolean testBT)
{
GUIHelper.logger.info(connectionType);
IPrinterConnection conn=null;
try
{
switch(connectionType)
{
case OS_PRINTER:
connectionType=ConnectionType.OS_PRINTER;
conn=new WindowsPrinterConnection();
PrinterHelper.printer.openConnection(conn);
break;
case SERIAL:
connectionType=ConnectionType.SERIAL;
if(!testBT)
{
conn=new SerialConnection(CompositeManufacturing.textSerialPort.getText(), 115200, 1);
}
else
{
try
{
conn=new SerialConnection(CompositeManufacturing.textBTPort1.getText(), 115200, 1);
}
catch(Exception e)
{
GUIHelper.logger.info("Can't open BT connection.");
return false;
}
}
PrinterHelper.printer.openConnection(conn);
break;
case NETWORK:
connectionType=ConnectionType.NETWORK;
conn=new NetworkConnection(CompositeManufacturing.textIPAddress.getText(), 9100);
PrinterHelper.printer.openConnection(conn);
break;
}
if(!PrinterHelper.printer.isConnected())
{
GUIHelper.logger.info("Can't open "+connectionType+" connection.");
GUIHelper.error("Can't open "+connectionType+" connection.");
return false;
}
}
catch(Exception e)
{
GUIHelper.error("Can't open "+connectionType+" connection.");
return false;
}
GUIHelper.logger.info(connectionType+" connection good.");
try
{
GUIHelper.logger.info("Closing "+connectionType+" connection.");
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
GUIHelper.error("Can't close "+connectionType+" connection.");
return false;
}
return true;
}
static TestStatus getHeadType()
{
GUIHelper.logger.info("");
String selection=CompositeManufacturing.comboPrintHead.getItem((CompositeManufacturing.comboPrintHead.getSelectionIndex()));
printHead=PrintHead.valueOf(selection);
GUIHelper.logger.info("Head type is "+printHead);
return TestStatus.PASSED;
}
static TestStatus doPaperOutCheck()
{
GUIHelper.logger.info("");
PrinterHelper.setVariable("PRINT_MODE", "DT");
PrinterHelper.setVariable("USER_FEEDBACK", "ON");
if(!isConnectionTypeParallelOnly)
{
JAdminDialogMessage dialog=new JAdminDialogMessage(Messages.getString("TestEngine.75"));
dialog.setMessage(Messages.getString("TestEngine.76"));
dialog.show();
boolean received=PrinterHelper.waitFor(".*O00000.*", 120);
dialog.close();
if(!received)
{
GUIHelper.error(Messages.getString("TestEngine.77"));
return TestStatus.FAILED;
}
}
else
{
int button=GUIHelper.message(Messages.getString("TestEngine.110"), SWT.ICON_WORKING|SWT.OK);
if(button==SWT.OK)
{
try
{
Thread.sleep(10000);
}
catch(Exception ex)
{
return TestStatus.FAILED;
}
}
else
{
return TestStatus.FAILED;
}
}
return TestStatus.PASSED;
}
static TestStatus doFeedButtonTest()
{
GUIHelper.logger.info("");
int button=GUIHelper.message(Messages.getString("TestEngine.116"), SWT.ICON_WORKING|SWT.OK);
if(button==SWT.OK)
{
int button1=GUIHelper.message(Messages.getString("TestEngine.117"), SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button1==SWT.YES)
{
return TestStatus.PASSED;
}
else
{
return TestStatus.FAILED;
}
}
else
{
return TestStatus.FAILED;
}
}
static TestStatus doInitialSetup(String message)
{
GUIHelper.logger.info("");
testModelNumber=CompositeManufacturing.textModelNumber.getText();
if(testModelNumber.equalsIgnoreCase("DLX-K542"))
{
establishPrimaryConnection(115200);
testModelNumber=PrinterHelper.commandWaitResponse("!SHOW MODELNUMBER");
CompositeManufacturing.textModelNumber.setText(testModelNumber);
isCustomLabel_K542=true;
}
else { isCustomLabel_K542=false; }
int button=GUIHelper.message(message, SWT.OK|SWT.CANCEL|SWT.ICON_WORKING);
if(button==SWT.CANCEL)
{
return TestStatus.CANCELLED;
}
boolean success=false;
success=parseModelNumber(CompositeManufacturing.textModelNumber.getText());
if(!success)
{
return TestStatus.FAILED;
}
isNetwork=((productLine==ProductLine.CI||productLine==ProductLine.CXI)||((productLine==ProductLine.DLX||productLine==ProductLine.LX)&&(communicationInterface.toString().indexOf("NETWORK")!=-1)));
success=establishPrimaryConnection(115200);
if(!success)
{
return TestStatus.FAILED;
}
PrinterHelper.setVariable("USER_FEEDBACK", "ON");
if(!isConnectionTypeParallelOnly)
{
serialNumber=PrinterHelper.commandWaitResponse("!SHOW SERIALNUMBER");
if(serialNumber==null)
{
return TestStatus.FAILED;
}
if(serialNumber.equalsIgnoreCase("z060800000"))
{
brandNewPrinter=true;
}
else if(Integer.parseInt(serialNumber.substring(1, 3))<6)
{
message="The serial number "+serialNumber+" is invalid.\n\nWould you like to assign a new serial number to this printer?";
button=GUIHelper.message(message, SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button==SWT.YES)
{
brandNewPrinter=true;
}
else
{
brandNewPrinter=false;
}
}
else
{
if(isGattaca&&(!serialNumber.startsWith("V")))
{
isMadeInChina=false;
}
else if(!(productLine==ProductLine.CI||productLine==ProductLine.CXI))
{
isMadeInChina=true;
}
brandNewPrinter=false;
if(isMadeByEnnoconnMalaysia)
{
CompositeManufacturing.textSerialNumberActual.setText(serialNumber);
}
}
}
else
{
if(CompositeManufacturing.checkBoxSpecSNMAC.getSelection())
{
if(!verifySpecifiedIdentity())
{
return TestStatus.FAILED;
}
serialNumber=CompositeManufacturing.textSerialNumberActual.getText();
macHex=CompositeManufacturing.textMacAddressActual.getText();
brandNewPrinter=false;
}
else
{
brandNewPrinter=true;
}
}
if(!isConnectionTypeParallelOnly)
{
macHex=PrinterHelper.commandWaitResponse("!SHOW MAC");
if(macHex==null)
{
return TestStatus.FAILED;
}
}
if(isTGModelNumber||isTDModelNumber)
{
testModelNumber=tgModelEmbeddedNumber;
}
GUIHelper.logger.info("testModelNumber "+testModelNumber);
GUIHelper.logger.info("brandNewPrinter "+brandNewPrinter);
if(!isConnectionTypeParallelOnly)
{
currentModelNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MODELNUMBER"));
GUIHelper.logger.info("currentModelNumber "+currentModelNumber);
}
String currentFirmwarePartNumber=getCurrentFirmwarePartNumber();
if(Integer.parseInt(currentFirmwarePartNumber.substring(currentFirmwarePartNumber.length()-3))>326)
{
supportsFactoryRestoreCommand=true;
}
else
{
supportsFactoryRestoreCommand=false;
}
if(!brandNewPrinter)
{
updateOEMIdentifier(TestStep.INITIAL_SETUP);
if(!isConnectionTypeParallelOnly)
{
if(currentModelNumber==null)
{
return TestStatus.FAILED;
}
if(((currentModelNumber.startsWith("DB")||currentModelNumber.startsWith("C"))&&testModelNumber.startsWith("700"))||(currentModelNumber.startsWith("700")&&(testModelNumber.startsWith("DB")||testModelNumber.startsWith("C"))))
{
PrinterHelper.send("!LOAD MODELNUMBER "+testModelNumber);
}
}
}
return TestStatus.PASSED;
}
static String getMonthYear()
{
GUIHelper.logger.trace("");
int year=2000+Integer.parseInt(serialNumber.substring(1, 3));
int weekOfYear=Integer.parseInt(serialNumber.substring(3, 5));
GregorianCalendar calendar=new GregorianCalendar(year, 0, 1);
calendar.set(Calendar.WEEK_OF_YEAR, Math.min(weekOfYear, 52));
calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
int monthInt=calendar.get(Calendar.MONTH);
String monthString=null;
switch(monthInt)
{
case 0:
monthString="January";
break;
case 1:
monthString="February";
break;
case 2:
monthString="March";
break;
case 3:
monthString="April";
break;
case 4:
monthString="May";
break;
case 5:
monthString="June";
break;
case 6:
monthString="July";
break;
case 7:
monthString="August";
break;
case 8:
monthString="September";
break;
case 9:
monthString="October";
break;
case 10:
monthString="November";
break;
case 11:
monthString="December";
break;
default:
GUIHelper.error("Can't decode month.");
GUIHelper.logger.error("Can't decode month "+monthInt);
return null;
}
return monthString.substring(0, 3).toUpperCase()+" "+year;
}
static boolean constructDLXSerialNumberLabel(StringBuffer cplPart)
{
GUIHelper.logger.trace("");
String monthYear=getMonthYear();
if(monthYear==null)
{
return false;
}
if(!isTGModelNumber&&!isTDModelNumber)
{
String configNo=CompositeManufacturing.textModelNumber.getText();
if(isWingman)
{
configNo=configNo.replace("DB", "EZ");
}
cplPart.append("!+0 100 310 1\n");
cplPart.append("P 200\n");
cplPart.append("W 300\n");
cplPart.append("TEXT 1 22 40 "+configNo+"\n");
cplPart.append("TEXT 0 385 44 "+monthYear+"\n");
cplPart.append("TEXT 1 605 40 "+serialNumber+"\n");
cplPart.append("B CODE39(2:4)-552 140 60 "+serialNumber+"\n");
if(communicationInterface.toString().indexOf("NETWORK")!=-1)
{
cplPart.append("TEXT 0 620 168 "+macHex.toUpperCase()+"\n");
}
if(isLX)
{
cplPart.append("TEXT 1 385 245 "+CompositeManufacturing.textModelNumber.getText().replace("G", "")+"\n");
}
else
{
cplPart.append("TEXT 1 385 245 "+CompositeManufacturing.textModelNumber.getText()+"\n");
}
cplPart.append("END");
}
else if(isTDModelNumber)
{
cplPart.append("!+0 100 310 1\n");
cplPart.append("P 200\n");
cplPart.append("W 300\n");
cplPart.append("TEXT 1 22 40 "+tgModelEmbeddedNumber+"\n");
cplPart.append("TEXT 0 265 44 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("TEXT 0 385 44 "+monthYear+"\n");
cplPart.append("TEXT 1 605 40 "+serialNumber+"\n");
cplPart.append("B CODE39(2:4)-552 140 60 "+serialNumber+"\n");
if(communicationInterface.toString().indexOf("NETWORK")!=-1)
{
cplPart.append("TEXT 0 660 152 "+macHex.toUpperCase()+"\n");
}
cplPart.append("TEXT 0 660 183 PRINTHEAD: "+printHead.toString().charAt(0)+"\n");
cplPart.append("STRING 12X16 803 220 24 VDC\n");
cplPart.append("STRING 12X16 803 248 6.25A\n");
cplPart.append("STRING 9X12 43 165 E231526\n");
cplPart.append("STRING 9X12 318 150 N270\n");
cplPart.append("TEXT 0 320 190 "+tgCompanyName+"\n");
cplPart.append("TEXT 0 320 220 "+tgGermanyAddress1+"\n");
cplPart.append("TEXT 0 320 250 "+tgGermanyAddress2+"\n");
cplPart.append("END");
}
else
{
cplPart.append("!+0 100 310 1\n");
cplPart.append("P 200\n");
cplPart.append("W 300\n");
cplPart.append("TEXT 1 22 40 "+tgModelEmbeddedNumber+"\n");
cplPart.append("TEXT 0 265 44 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("TEXT 0 385 44 "+monthYear+"\n");
cplPart.append("TEXT 1 605 40 "+serialNumber+"\n");
cplPart.append("B CODE39(2:4)-552 140 60 "+serialNumber+"\n");
if(communicationInterface.toString().indexOf("NETWORK")!=-1)
{
cplPart.append("TEXT 0 560 152 "+macHex.toUpperCase()+"\n");
}
cplPart.append("TEXT 0 560 183 PRINTHEAD: "+printHead.toString().charAt(0)+"\n");
cplPart.append("TEXT 0 340 230 "+tgGermanyAddress1+"\n");
cplPart.append("TEXT 0 340 260 "+tgGermanyAddress2+"\n");
cplPart.append("END");
}
return true;
}
static boolean constructCSerialNumberLabel(StringBuffer cplPart)
{
GUIHelper.logger.trace("");
String monthYear=getMonthYear();
if(monthYear==null)
{
return false;
}
if(!isTGModelNumber&&!isTDModelNumber)
{
String configNo=CompositeManufacturing.textModelNumber.getText();
cplPart.append("!+0 100 820 1\n");
cplPart.append("TEXT 1 60 45 "+configNo+"\n");
cplPart.append("BARCODE CODE128(2:5)-40 155 75 "+configNo+"\n");
if(!isSecurity)
{
cplPart.append("TEXT 1 425 100 "+monthYear+"\n");
cplPart.append("TEXT 1 635 45 "+serialNumber+"\n");
cplPart.append("BARCODE CODE128(2:5)-620 155 75 "+serialNumber+"\n");
cplPart.append("TEXT 1 60 225 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("TEXT 1 420 205 "+macHex.toUpperCase()+"\n");
cplPart.append("BARCODE CODE128(2:5)-380 315 75 "+macHex.toUpperCase()+"\n");
}
else
{
cplPart.append("TEXT 1 500 80 "+monthYear.substring(0, 3).toUpperCase()+"\n");
cplPart.append("TEXT 1 500 120 "+monthYear.substring(4).trim()+"\n");
cplPart.append("TEXT 1 635 45 "+serialNumber+"\n");
cplPart.append("BARCODE CODE128(2:5)-620 155 75 "+serialNumber+"\n");
cplPart.append("TEXT 1 30 225 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("TEXT 1 420 205 "+macHex.toUpperCase()+"\n");
cplPart.append("BARCODE CODE128(2:5)-395 315 75 "+macHex.toUpperCase()+"\n");
}
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
else if(isTDModelNumber)
{
cplPart.append("!+0 100 820 1\n");
cplPart.append("TEXT 1 90 45 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("BARCODE CODE128(2:5)-80 155 75 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("TEXT 1 340 100 "+monthYear+"\n");
cplPart.append("TEXT 1 585 45 "+serialNumber+"\n");
cplPart.append("BARCODE CODE128(2:5)-575 155 75 "+serialNumber+"\n");
cplPart.append("TEXT 1 65 520 "+tgModelEmbeddedNumber+"\n");
cplPart.append("TEXT 1 240 590 "+printHead.toString().charAt(0)+"\n");
cplPart.append("TEXT 1 410 495 "+macHex.toUpperCase()+"\n");
cplPart.append("BARCODE CODE128(2:5)-380 605 75 "+macHex.toUpperCase()+"\n");
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
else
{
cplPart.append("!+0 100 820 1\n");
cplPart.append("U B42 (5,0,0) 50 50 Model Number:\n");
cplPart.append("U B42 (5,0,0) 50 120 ID Number:\n");
cplPart.append("U B42 (5,0,0) 50 260 Serial Number:\n");
cplPart.append("U B42 (5,0,0) 50 400 MAC:\n");
cplPart.append("U B42 (5,0,0) 400 50 "+tgModelEmbeddedNumber+"\n");
cplPart.append("U B42 (5,0,0) 400 110 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("BARCODE CODE128(2:6)-400 230 75 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("U B42 (5,0,0) 400 250 "+serialNumber+"\n");
cplPart.append("BARCODE CODE128(2:6)-400 370 75 "+serialNumber+"\n");
cplPart.append("U B42 (5,0,0) 400 390 "+macHex.toUpperCase()+"\n");
cplPart.append("BARCODE CODE128(2:5)-400 510 75 "+macHex.toUpperCase()+"\n");
cplPart.append("U B42 (5,0,0) 50 480 Printhead: "+printHead.toString().charAt(0)+"\n");
cplPart.append("FILL_BOX 25 530 840 10\n");
cplPart.append("U B42 (5,0,0) 25 550 Input Power:\n");
cplPart.append("U B42 (5,0,0) 25 600 24V, 6.25A\n");
if(isMadeByEnnoconnMalaysia)
cplPart.append("U B40 (5,0,0) 25 735 Made in Malaysia\n");
else
cplPart.append("U B40 (5,0,0) 25 735 Made in Taiwan\n");
cplPart.append("U B42 (5,0,0) 375 670 "+tgGermanyAddress1+"\n");
cplPart.append("U B42 (5,0,0) 375 725 "+tgGermanyAddress2+"\n");
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
return true;
}
static boolean connectNamedPrinter(String name)
{
GUIHelper.logger.trace("");
IPrinterConnection conn=null;
try
{
PrinterHelper.printer.closeConnection();
if(name.matches("\\d{1,}\\.\\d{1,}\\.\\d{1,}\\.\\d{1,}"))
{
conn=new NetworkConnection(name, 9100);
}
else if(name.matches("COM.*"))
{
conn=new SerialConnection(name, 115200, 1);
}
else if(name.matches("LPT.*"))
{
conn=new ParallelConnection(name);
}
else if(name.matches("\\D.*"))
{
conn=new WindowsPrinterConnection();
}
GUIHelper.logger.info("Connected to "+name);
PrinterHelper.printer.openConnection(conn);
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
GUIHelper.error(Messages.getString("TestEngine.78")+name+"\"");
return false;
}
return true;
}
static TestStatus doSerialNumberLabel()
{
GUIHelper.logger.trace("");
if(!CompositeManufacturing.checkBoxSNLabelPrinter.getSelection())
{
return TestStatus.SKIPPED;
}
if(!connectNamedPrinter(CompositeManufacturing.textSNLabelPrinter.getText()))
{
return TestStatus.FAILED;
}
byte binaryPart[]=null;
StringBuffer cplPart=new StringBuffer();
String serialNumberLabel=null;
if(isCustomLabel_K542&&printCustom_K542)
{
try
{
String monthYear=getMonthYear();
if (printDensity==200)
{
binaryPart=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/labels/K542-200DPI.lbl");
cplPart=new StringBuffer();
cplPart.append("!+0 100 310 2\n");
cplPart.append("VARIABLE DARKNESS 100\n");
cplPart.append("VARIABLE ENERGY 50\n");
cplPart.append("VARIABLE PRINT_SPEED 5000\n");
cplPart.append("VARIABLE SHIFT LEFT 30\n");
cplPart.append("VARIABLE WIDTH 900\n");
cplPart.append("TEXT 2 412 115 PN: DLX-K542\n");
cplPart.append("TEXT 2 412 155 SN: "+serialNumber+"\n");
cplPart.append("TEXT 2 412 195 Mfg.: "+monthYear+"\n");
cplPart.append("TEXT 2 412 235 Made in Malaysia\n");
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
else if (printDensity==300)
{
binaryPart=GUIHelper.getResourceAsByteArray("com/cognitive/manufacturing/labels/K542-300DPI.lbl");
cplPart=new StringBuffer();
cplPart.append("!+0 100 465 2\n");
cplPart.append("VARIABLE DARKNESS 100\n");
cplPart.append("VARIABLE ENERGY 50\n");
cplPart.append("VARIABLE PRINT_SPEED 5000\n");
cplPart.append("VARIABLE SHIFT LEFT 0\n");
cplPart.append("VARIABLE WIDTH 639\n");
cplPart.append("TEXT 2 618 172 PN: DLX-K542\n");
cplPart.append("TEXT 2 618 232 SN: "+serialNumber+"\n");
cplPart.append("TEXT 2 618 292 Mfg.: "+monthYear+"\n");
cplPart.append("TEXT 2 618 352 Made in Malaysia\n");
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
PrinterHelper.send(binaryPart);
PrinterHelper.send(cplPart.toString());
GUIHelper.logger.info("Sent custom DLX-K542 serial label to printer:\n"+cplPart.toString());
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
GUIHelper.logger.info("Failed to send custom label format DLX-K542 to printer.");
return TestStatus.FAILED;
}
return TestStatus.PASSED;
}
switch(productLine)
{
case CI:
case CXI:
if(!isTGModelNumber&&!isTDModelNumber)
{
if(isMadeInChina)
{
serialNumberLabel="com/cognitive/manufacturing/labels/C_SN_withCCC.lbl";
}
else
{
if(isSecurity||isGWN)
{
serialNumberLabel="com/cognitive/manufacturing/labels/C_SN_S.lbl";
}
else
{
serialNumberLabel="com/cognitive/manufacturing/labels/C_SN.lbl";
}
}
}
else if(isTDModelNumber)
{
serialNumberLabel="com/cognitive/manufacturing/labels/TD_C_SN.lbl";
}
else
{
serialNumberLabel="com/cognitive/manufacturing/labels/TG_C_SN.lbl";
}
if(isMadeByEnnoconnMalaysia)
{
GUIHelper.logger.warn("Appending Ennoconn identifier string to serial label file name.");
if(!isTGModelNumber&&!isTDModelNumber)
serialNumberLabel=serialNumberLabel.replaceFirst("[.]lbl", "_ENNO.lbl");
}
binaryPart=GUIHelper.getResourceAsByteArray(serialNumberLabel);
if(binaryPart==null)
{
String message="Can't get serial number label "+serialNumberLabel;
GUIHelper.logger.error(message);
GUIHelper.error(message);
return TestStatus.FAILED;
}
if(!constructCSerialNumberLabel(cplPart))
{
return TestStatus.FAILED;
}
break;
case LX:
case DLX:
if(!isTGModelNumber&&!isTDModelNumber)
{
if(isSiemens)
{
serialNumberLabel="com/cognitive/manufacturing/labels/DLX_SN_BC.lbl";
}
else if(isMadeInChina||serialNumber.startsWith("V"))
{
serialNumberLabel="com/cognitive/manufacturing/labels/DLX_SN_China.lbl";
}
else
{
serialNumberLabel="com/cognitive/manufacturing/labels/DLX_SN.lbl";
}
}
else if(isTDModelNumber)
{
serialNumberLabel="com/cognitive/manufacturing/labels/TD_DLX_SN.lbl";
}
else
{
serialNumberLabel="com/cognitive/manufacturing/labels/TG_DLX_SN.lbl";
}
if(isMadeByEnnoconnMalaysia)
{
GUIHelper.logger.warn("Appending Ennoconn identifier string to serial label file name.");
if(!isTGModelNumber&&!isTDModelNumber)
serialNumberLabel=serialNumberLabel.replaceFirst("[.]lbl", "_ENNO.lbl");
}
binaryPart=GUIHelper.getResourceAsByteArray(serialNumberLabel);
if(binaryPart==null)
{
String message="Can't get serial number label "+serialNumberLabel;
GUIHelper.logger.error(message);
GUIHelper.error(message);
return TestStatus.FAILED;
}
if(!constructDLXSerialNumberLabel(cplPart))
{
return TestStatus.FAILED;
}
break;
}
if(binaryPart!=null)
{
PrinterHelper.send(binaryPart);
}
if(cplPart!=null)
{
PrinterHelper.send(cplPart.toString());
}
try { PrinterHelper.printer.closeConnection(); }
catch(Exception e)
{
GUIHelper.logger.error(e, e);
GUIHelper.message("Unable to close connection to serial number label printer.", SWT.ICON_WARNING|SWT.OK);
}
return TestStatus.PASSED;
}
static boolean constructCBoxLabel(StringBuffer cplPart)
{
GUIHelper.logger.trace("");
if(!isTGModelNumber&&!isTDModelNumber)
{
String firmwarePartNumber=null;
if(isConnectionTypeParallelOnly)
{
firmwarePartNumber=latestFirmware.substring(0, 3)+"-"+latestFirmware.substring(3, 3)+"-"+latestFirmware.substring(7, 3);
}
else
{
firmwarePartNumber=GUIHelper.getMatch(currentFirmware, "\\d{3}-\\d{3}-\\d{3}");
}
JAdminDialogRadio dialog=new JAdminDialogRadio(Messages.getString("TestEngine.79"));
dialog.addOption(Messages.getString("TestEngine.80"));
dialog.addOption(Messages.getString("TestEngine.81"));
dialog.addOption(Messages.getString("TestEngine.108"));
int selection=dialog.waitForSelection();
String option=null;
switch(selection)
{
case-1:
return false;
case 0:
option="US Power Cord";
break;
case 1:
option="UK/EU Power Cord";
break;
case 2:
option="No Power Cord";
break;
}
cplPart.append("!+0 100 1460 1\n");
cplPart.append("VARIABLE DARKNESS 100\n");
cplPart.append("VARIABLE ENERGY 50\n");
cplPart.append("VARIABLE PRINT_SPEED 5000\n");
cplPart.append("VARIABLE SHIFT LEFT-35\n");
cplPart.append("VARIABLE WIDTH 426\n");
cplPart.append("FILL_BOX 15 20 965 10\n");
if(isSecurity||isGWN)
{
cplPart.append("U B32X80 (10,2,0) 40 80 MODEL:\n");
cplPart.append("U B35X80 (8,2,0) 300 80 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("BARCODE CODE128(3:8)-300 260 100 "+CompositeManufacturing.textModelNumber.getText()+"\n");
}
else
{
cplPart.append("U B32X80 (10,2,0) 40 80 MODEL NO.:\n");
cplPart.append("U B35X80 (8,2,0) 425 80 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("BARCODE CODE128(3:8)-425 260 100 "+CompositeManufacturing.textModelNumber.getText()+"\n");
}
cplPart.append("U B32X80 (10,2,0) 40 320 SERIAL NO.:\n");
cplPart.append("U B35X80 (8,2,0) 495 320 "+serialNumber+"\n");
cplPart.append("BARCODE CODE128(3:8)-525 500 100 "+serialNumber+"\n");
cplPart.append("U B32X80 (10,2,0) 40 560 FIRMWARE:\n");
cplPart.append("U B35X80 (8,2,0) 480 560 "+firmwarePartNumber+"\n");
cplPart.append("BARCODE CODE128(3:8)-465 740 100 "+firmwarePartNumber+"\n");
cplPart.append("U B32X80 (10,2,0) 40 800 OPTION:\n");
cplPart.append("U B35X80 (8,2,0) 345 800 "+option+"\n");
cplPart.append("FILL_BOX 5 20 10 1400\n");
cplPart.append("FILL_BOX 980 20 10 1400\n");
cplPart.append("FILL_BOX 15 1410 965 10\n");
if(isMadeByEnnoconnMalaysia)
cplPart.append("TEXT 0 40 1350 Made in Malaysia\n");
else
cplPart.append("TEXT 0 40 1350 Made in Taiwan\n");
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
else if(isTDModelNumber)
{
cplPart.append("!+0 100 1460 1\n");
cplPart.append("FILL_BOX 25 30 900 10\n");
cplPart.append("U B32 (5,0,0) 30 50 MODEL NUMBER:\n");
cplPart.append("U B60 (6,0,0) 400 50 "+tgModelEmbeddedNumber+"\n");
cplPart.append("U B24 (3,0,0) 30 100 Thermal Printer\n");
cplPart.append("U B24 (3,0,0) 30 140 "+tgModelFeature+"\n");
cplPart.append("FILL_BOX 25 180 900 10\n");
cplPart.append("U B32 (5,0,0) 30 200 ID NUMBER:\n");
cplPart.append("U B42 (5,0,0) 400 200 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("BARCODE CODE39(3:8)-200 320 75 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("FILL_BOX 25 340 900 10\n");
cplPart.append("U B32 (5,0,0) 30 360 SERIAL NUMBER: (1S)\n");
cplPart.append("U B42 (5,0,0) 460 360 "+serialNumber+"\n");
cplPart.append("BARCODE CODE39(3:8)-150 480 75 "+serialNumber+"\n");
cplPart.append("FILL_BOX 25 500 900 10\n");
cplPart.append("BARCODE CODE39(2:6)-100 600 75 "+CompositeManufacturing.textModelNumber.getText()+serialNumber+"\n");
cplPart.append("FILL_BOX 25 620 900 10\n");
cplPart.append("U B32 (5,0,0) 30 670 PRINTHEAD:\n");
cplPart.append("U B32 (5,0,0) 250 670 "+printHead.toString().charAt(0)+"\n");
cplPart.append("FILL_BOX 25 760 900 10\n");
cplPart.append("U B32 (5,0,0) 30 780 MAC ADDRESS:\n");
cplPart.append("BARCODE_FONT U B20 (2,0,0) \n");
cplPart.append("BARCODE CODE39(3:8) 100 900 75 "+macHex.replace(":", "").toUpperCase()+"\n");
cplPart.append("FILL_BOX 25 940 900 10\n");
cplPart.append("U B42 (5,0,0) 55 1120 www.dascom.com\n");
cplPart.append("BARCODE_FONT 12X16(00,05,1,1,1,1)\n");
cplPart.append("BARCODE EAN13+(3:8) 470 1025 60 "+tgModelUPC+"\n");
cplPart.append("U B30 (3,0,0) 470 1100 "+tgCompanyName+"\n");
cplPart.append("U B30 (3,0,0) 470 1140 "+tgGermanyAddress1+" "+tgGermanyAddress2+"\n");
cplPart.append("FILL_BOX 15 30 10 1180\n");
cplPart.append("FILL_BOX 925 30 10 1180\n");
cplPart.append("FILL_BOX 25 1200 900 10\n");
if(isMadeByEnnoconnMalaysia)
cplPart.append("U A24 (4,0,0) 30 1220 Made in Malaysia\n");
else
cplPart.append("U A24 (4,0,0) 30 1220 Made in Taiwan\n");
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
else
{
cplPart.append("!+0 100 1460 1\n");
cplPart.append("FILL_BOX 25 30 900 10\n");
cplPart.append("U B32 (5,0,0) 30 50 MODEL NUMBER:\n");
cplPart.append("U B60 (6,0,0) 400 50 "+tgModelEmbeddedNumber+"\n");
cplPart.append("U B24 (3,0,0) 30 100 Thermal Printer\n");
cplPart.append("U B24 (3,0,0) 30 140 "+tgModelFeature+"\n");
cplPart.append("FILL_BOX 25 180 900 10\n");
cplPart.append("U B32 (5,0,0) 30 200 ID NUMBER:\n");
cplPart.append("U B42 (5,0,0) 400 200 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("BARCODE CODE39(3:8)-200 320 75 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("FILL_BOX 25 340 900 10\n");
cplPart.append("U B32 (5,0,0) 30 360 SERIAL NUMBER: (1S)\n");
cplPart.append("U B42 (5,0,0) 460 360 "+serialNumber+"\n");
cplPart.append("BARCODE CODE39(3:8)-150 480 75 "+serialNumber+"\n");
cplPart.append("FILL_BOX 25 500 900 10\n");
cplPart.append("BARCODE CODE39(2:6)-100 600 75 "+CompositeManufacturing.textModelNumber.getText()+serialNumber+"\n");
cplPart.append("FILL_BOX 25 620 900 10\n");
cplPart.append("U B32 (5,0,0) 30 670 PRINTHEAD:\n");
cplPart.append("U B32 (5,0,0) 250 670 "+printHead.toString().charAt(0)+"\n");
cplPart.append("FILL_BOX 25 760 900 10\n");
cplPart.append("U B32 (5,0,0) 30 780 MAC ADDRESS:\n");
cplPart.append("BARCODE_FONT U B20 (2,0,0) \n");
cplPart.append("BARCODE CODE39(3:8) 100 900 75 "+macHex.replace(":", "").toUpperCase()+"\n");
cplPart.append("FILL_BOX 25 940 900 10\n");
cplPart.append("U B30 (3,0,0) 200 1080 www.tallygenicom.com\n");
cplPart.append("BARCODE_FONT 12X16(00,05,1,1,1,1)\n");
cplPart.append("BARCODE EAN13+(3:8) 535 1120 60 "+tgModelUPC+"\n");
cplPart.append("U B24 (3,0,0) 50 1150 TallyGenicom, "+tgGermanyAddress1+", "+tgGermanyAddress2+"\n");
cplPart.append("FILL_BOX 15 30 10 1180\n");
cplPart.append("FILL_BOX 925 30 10 1180\n");
cplPart.append("FILL_BOX 25 1200 900 10\n");
if(isMadeByEnnoconnMalaysia)
cplPart.append("U A24 (4,0,0) 30 1220 Made in Malaysia\n");
else
cplPart.append("U A24 (4,0,0) 30 1220 Made in Taiwan\n");
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
return true;
}
static boolean constructDLXBoxLabel(StringBuffer cplPart)
{
GUIHelper.logger.trace("");
if(isTDModelNumber)
{
cplPart.append("!+0 100 1460 1\n");
cplPart.append("FILL_BOX 25 30 900 10\n");
cplPart.append("U B32 (5,0,0) 30 50 MODEL NUMBER:\n");
cplPart.append("U B60 (6,0,0) 400 50 "+tgModelEmbeddedNumber+"\n");
cplPart.append("U B24 (3,0,0) 30 100 Thermal Printer\n");
cplPart.append("U B24 (3,0,0) 30 140 "+tgModelFeature+"\n");
cplPart.append("FILL_BOX 25 180 900 10\n");
cplPart.append("U B32 (5,0,0) 30 200 ID NUMBER:\n");
cplPart.append("U B42 (5,0,0) 400 200 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("BARCODE CODE39(3:8)-200 320 75 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("FILL_BOX 25 340 900 10\n");
cplPart.append("U B32 (5,0,0) 30 360 SERIAL NUMBER: (1S)\n");
cplPart.append("U B42 (5,0,0) 460 360 "+serialNumber+"\n");
cplPart.append("BARCODE CODE39(3:8)-150 480 75 "+serialNumber+"\n");
cplPart.append("FILL_BOX 25 500 900 10\n");
cplPart.append("BARCODE CODE39(2:6)-100 600 75 "+CompositeManufacturing.textModelNumber.getText()+serialNumber+"\n");
cplPart.append("FILL_BOX 25 620 900 10\n");
cplPart.append("U B32 (5,0,0) 30 640 COMMUNICATIONS CONFIGURATION:\n");
if(communicationInterface.toString().indexOf("NETWORK")!=-1)
{
cplPart.append("U B880 (8,0,0) 50 42 .\n");
cplPart.append("TEXT 3(0,0,1,1) 240 662 O\n");
cplPart.append("TEXT 3(0,0,1,1) 440 662 O\n");
cplPart.append("U B880 (8,0,0) 700 42 .\n");
}
else if(communicationInterface.toString().indexOf("SERIAL")!=-1)
{
cplPart.append("U B880 (8,0,0) 50 42 .\n");
cplPart.append("U B880 (8,0,0) 240 42 .\n");
cplPart.append("TEXT 3(0,0,1,1) 440 662 O\n");
cplPart.append("TEXT 3(0,0,1,1) 700 662 O\n");
}
else if(communicationInterface.toString().indexOf("PARALLEL")!=-1)
{
cplPart.append("U B880 (8,0,0) 50 42 .\n");
cplPart.append("TEXT 3(0,0,1,1) 240 662 O\n");
cplPart.append("U B880 (8,0,0) 440 42 .\n");
cplPart.append("TEXT 3(0,0,1,1) 700 662 O\n");
}
else
{
cplPart.append("U B880 (8,0,0) 50 42 .\n");
cplPart.append("TEXT 3(0,0,1,1) 240 662 O\n");
cplPart.append("TEXT 3(0,0,1,1) 440 662 O\n");
cplPart.append("TEXT 3(0,0,1,1) 700 662 O\n");
}
cplPart.append("U B32 (5,0,0) 50 728 USB	SERIAL	PARALLEL	ETHERNET\n");
cplPart.append("FILL_BOX 25 760 900 10\n");
cplPart.append("U B32 (5,0,0) 30 780 MAC ADDRESS:\n");
if(communicationInterface.toString().indexOf("NETWORK")!=-1)
{
cplPart.append("BARCODE_FONT U B20 (2,0,0) \n");
cplPart.append("BARCODE CODE39(3:8) 100 900 75 "+macHex.replace(":", "").toUpperCase()+"\n");
}
cplPart.append("FILL_BOX 25 940 900 10\n");
cplPart.append("U B42 (5,0,0) 55 1120 www.dascom.com\n");
cplPart.append("BARCODE_FONT 12X16(00,05,1,1,1,1)\n");
cplPart.append("BARCODE EAN13+(3:8) 470 1025 60 "+tgModelUPC+"\n");
cplPart.append("U B30 (3,0,0) 470 1100 "+tgCompanyName+"\n");
cplPart.append("U B30 (3,0,0) 470 1140 "+tgGermanyAddress1+" "+tgGermanyAddress2+"\n");
cplPart.append("FILL_BOX 15 30 10 1180\n");
cplPart.append("FILL_BOX 925 30 10 1180\n");
cplPart.append("FILL_BOX 25 1200 900 10\n");
if(isMadeByEnnoconnMalaysia)
cplPart.append("U A24 (4,0,0) 30 1220 Made in Malaysia\n");
else
cplPart.append("U A24 (4,0,0) 30 1220 Made in Mexico\n");
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
else if(isTGModelNumber)
{
cplPart.append("!+0 100 1460 1\n");
cplPart.append("FILL_BOX 25 30 900 10\n");
cplPart.append("U B32 (5,0,0) 30 50 MODEL NUMBER:\n");
cplPart.append("U B60 (6,0,0) 400 50 "+tgModelEmbeddedNumber+"\n");
cplPart.append("U B24 (3,0,0) 30 100 Thermal Printer\n");
cplPart.append("U B24 (3,0,0) 30 140 "+tgModelFeature+"\n");
cplPart.append("FILL_BOX 25 180 900 10\n");
cplPart.append("U B32 (5,0,0) 30 200 ID NUMBER:\n");
cplPart.append("U B42 (5,0,0) 400 200 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("BARCODE CODE39(3:8)-200 320 75 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("FILL_BOX 25 340 900 10\n");
cplPart.append("U B32 (5,0,0) 30 360 SERIAL NUMBER: (1S)\n");
cplPart.append("U B42 (5,0,0) 460 360 "+serialNumber+"\n");
cplPart.append("BARCODE CODE39(3:8)-150 480 75 "+serialNumber+"\n");
cplPart.append("FILL_BOX 25 500 900 10\n");
cplPart.append("BARCODE CODE39(2:6)-100 600 75 "+CompositeManufacturing.textModelNumber.getText()+serialNumber+"\n");
cplPart.append("FILL_BOX 25 620 900 10\n");
cplPart.append("U B32 (5,0,0) 30 640 COMMUNICATIONS CONFIGURATION:\n");
if(communicationInterface.toString().indexOf("NETWORK")!=-1)
{
cplPart.append("U B880 (8,0,0) 50 42 .\n");
cplPart.append("TEXT 3(0,0,1,1) 240 662 O\n");
cplPart.append("TEXT 3(0,0,1,1) 440 662 O\n");
cplPart.append("U B880 (8,0,0) 700 42 .\n");
}
else if(communicationInterface.toString().indexOf("SERIAL")!=-1)
{
cplPart.append("U B880 (8,0,0) 50 42 .\n");
cplPart.append("U B880 (8,0,0) 240 42 .\n");
cplPart.append("TEXT 3(0,0,1,1) 440 662 O\n");
cplPart.append("TEXT 3(0,0,1,1) 700 662 O\n");
}
else if(communicationInterface.toString().indexOf("PARALLEL")!=-1)
{
cplPart.append("U B880 (8,0,0) 50 42 .\n");
cplPart.append("TEXT 3(0,0,1,1) 240 662 O\n");
cplPart.append("U B880 (8,0,0) 440 42 .\n");
cplPart.append("TEXT 3(0,0,1,1) 700 662 O\n");
}
else
{
cplPart.append("U B880 (8,0,0) 50 42 .\n");
cplPart.append("TEXT 3(0,0,1,1) 240 662 O\n");
cplPart.append("TEXT 3(0,0,1,1) 440 662 O\n");
cplPart.append("TEXT 3(0,0,1,1) 700 662 O\n");
}
cplPart.append("U B32 (5,0,0) 50 728 USB	SERIAL	PARALLEL	ETHERNET\n");
cplPart.append("FILL_BOX 25 760 900 10\n");
cplPart.append("U B32 (5,0,0) 30 780 MAC ADDRESS:\n");
if(communicationInterface.toString().indexOf("NETWORK")!=-1)
{
cplPart.append("BARCODE_FONT U B20 (2,0,0) \n");
cplPart.append("BARCODE CODE39(3:8) 100 900 75 "+macHex.replace(":", "").toUpperCase()+"\n");
}
cplPart.append("FILL_BOX 25 940 900 10\n");
cplPart.append("U B30 (3,0,0) 200 1080 www.tallygenicom.com\n");
cplPart.append("BARCODE_FONT 12X16(00,05,1,1,1,1)\n");
cplPart.append("BARCODE EAN13+(3:8) 535 1120 60 "+tgModelUPC+"\n");
cplPart.append("U B24 (3,0,0) 50 1150 TallyGenicom, "+tgGermanyAddress1+", "+tgGermanyAddress2+"\n");
cplPart.append("FILL_BOX 15 30 10 1180\n");
cplPart.append("FILL_BOX 925 30 10 1180\n");
cplPart.append("FILL_BOX 25 1200 900 10\n");
if(isMadeByEnnoconnMalaysia)
cplPart.append("U A24 (4,0,0) 30 1220 Made in Malaysia\n");
else
cplPart.append("U A24 (4,0,0) 30 1220 Made in Mexico\n");
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
else
{
String descriptionLine1="";
String descriptionLine2="200dpi, 8M, ";
String descriptionLine4="POWER CORD, ";
if(productLine==ProductLine.DLX)
descriptionLine1+="DLXi, ";
else if(productLine==ProductLine.LX)
descriptionLine1+="LX, ";
if(printMethod==PrintMethod.DT)
descriptionLine1+="DT, ";
else if(printMethod==PrintMethod.TT)
descriptionLine1+="TT, ";
if(mediaWidth==2)
descriptionLine1+="2\"";
else if(mediaWidth==4)
descriptionLine1+="4\"";
if(communicationInterface==CommunicationInterface.USB_AB)
descriptionLine2+="USB, ";
else if(communicationInterface==CommunicationInterface.USB_AB_NETWORK)
descriptionLine2+="USB/ETH, ";
else if(communicationInterface==CommunicationInterface.USB_AB_NETWORK_SERIAL)
descriptionLine2+="USB/ETH/SER, ";
else if(communicationInterface==CommunicationInterface.USB_AB_NETWORK_SERIAL_PARALLEL)
descriptionLine2+="USB/ETH/SER/PAR, ";
else if(communicationInterface==CommunicationInterface.USB_AB_SERIAL)
descriptionLine2+="SER, ";
else if(communicationInterface==CommunicationInterface.USB_AB_SERIAL_BLUETOOTH)
descriptionLine2+="SER/BTH, ";
else if(communicationInterface==CommunicationInterface.USB_AB_PARALLEL)
descriptionLine2+="USB/PAR, ";
else if(communicationInterface==CommunicationInterface.USB_AB_SERIAL_PARALLEL)
descriptionLine2+="USB/SER/PAR, ";
else if(communicationInterface==CommunicationInterface.SERIAL)
descriptionLine2+="SER, ";
else if(communicationInterface==CommunicationInterface.PARALLEL)
descriptionLine2+="PAR, ";
else if(communicationInterface==CommunicationInterface.SERIAL_PARALLEL)
descriptionLine2+="SER/PAR, ";
else if(communicationInterface==CommunicationInterface.SERIAL_NETWORK)
descriptionLine2+="SER/ETH, ";
else if(communicationInterface==CommunicationInterface.SERIAL_NETWORK_PARALLEL)
descriptionLine2+="SER/ETH/PAR, ";
else if(communicationInterface==CommunicationInterface.USB_AB_LEGACY_NO_CABLE)
descriptionLine2+="USB, ";
else if(communicationInterface==CommunicationInterface.USB_AB_LEGACY_SERIAL_CABLE)
descriptionLine2+="SER, ";
else if(communicationInterface==CommunicationInterface.USB_AB_LEGACY_ADAPTOR_SERIAL_CABLE)
descriptionLine2+="SER, ";
else if(communicationInterface==CommunicationInterface.USB_AB_LEGACY_PARALLEL_CABLE)
descriptionLine2+="PAR, ";
else if(communicationInterface==CommunicationInterface.ALL)
descriptionLine2+="USB/SER/PAR/ETH/BTH, ";
if(powerSupplyCord==PowerSupplyCord.US)
{
descriptionLine2+="US";
descriptionLine4+="US";
}
else if(powerSupplyCord==PowerSupplyCord.EU_UK)
{
descriptionLine2+="EURO+UK";
descriptionLine4+="EURO+UK";
}
else if(powerSupplyCord==PowerSupplyCord.US_EU_UK)
{
descriptionLine2+="US+EURO+UK";
descriptionLine4+="US+EURO+UK";
}
else if(powerSupplyCord==PowerSupplyCord.NO_CORD||powerSupplyCord==PowerSupplyCord.NO_POWER_SUPPLY)
{
descriptionLine2+="ANY";
descriptionLine4+="NO PS";
}
cplPart.append("!0 100 1460 1\n");
cplPart.append("VARIABLE DARKNESS 100\n");
cplPart.append("VARIABLE ENERGY 50\n");
cplPart.append("VARIABLE PRINT_SPEED 5000\n");
cplPart.append("VARIABLE SHIFT LEFT-35\n");
cplPart.append("VARIABLE WIDTH 426\n");
cplPart.append("DRAW_BOX 0 0 1025 1400 4\n");
cplPart.append("TEXT 2 54 25 MODEL NUMBER:\n");
cplPart.append("TEXT 3 140 105 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("BARCODE CODE128(5:8)-108 295 112 "+CompositeManufacturing.textModelNumber.getText()+"\n");
cplPart.append("DRAW_BOX 0 400 1025 1000 4\n");
cplPart.append("TEXT 3 50 450 SERIAL NUMBER:\n");
cplPart.append("TEXT 4 50 520 "+serialNumber+"\n");
cplPart.append("BARCODE CODE128(5:8)-50 750 100 "+serialNumber+"\n");
cplPart.append("DRAW_BOX 0 800 1025 600 4\n");
cplPart.append("TEXT 3 50 850 DESCRIPTION:\n");
cplPart.append("TEXT 2 50 950 "+descriptionLine1+"\n");
cplPart.append("TEXT 2 50 1000 "+descriptionLine2+"\n");
cplPart.append("TEXT 2 50 1050 POWER SUPPLY 70WATT\n");
cplPart.append("TEXT 2 50 1100 "+descriptionLine4+"\n");
if(descriptionLine2.contains("USB"))
cplPart.append("TEXT 2 50 1150 ACCESSORY CABLE, USB\n");
else
cplPart.append("TEXT 2 50 1150 ACCESSORY CABLE, NONE\n");
cplPart.append("TEXT 2 875 1350 Rev N\n");
if(isMadeByEnnoconnMalaysia)
cplPart.append("TEXT 2 50 1410 Made in Malaysia\n");
else
cplPart.append("TEXT 2 50 1410 Made in Mexico\n");
cplPart.append("INDEX\n");
cplPart.append("END\n");
}
return true;
}
static TestStatus doBoxLabel()
{
GUIHelper.logger.trace("");
if(!CompositeManufacturing.checkBoxBoxLabelPrinter.getSelection())
{
return TestStatus.SKIPPED;
}
if(!connectNamedPrinter(CompositeManufacturing.textBoxLabelPrinter.getText()))
{
return TestStatus.FAILED;
}
byte binaryPart[]=null;
byte binaryPart2[]=null;
StringBuffer cplPart=new StringBuffer();
String boxLabel=null;
String boxLabel2=null;
switch(productLine)
{
case CI:
case CXI:
if(!isTGModelNumber&&!isTDModelNumber)
{
boxLabel="com/cognitive/manufacturing/labels/C_BOX_2.lbl";
}
else
{
if(isTDModelNumber)
{
boxLabel="com/cognitive/manufacturing/labels/TD_C_BOX.lbl";
boxLabel2="com/cognitive/manufacturing/labels/TG_C_BOX_2.lbl";
}
else
{
boxLabel="com/cognitive/manufacturing/labels/TG_C_BOX.lbl";
boxLabel2="com/cognitive/manufacturing/labels/TG_C_BOX_2.lbl";
}
binaryPart2=GUIHelper.getResourceAsByteArray(boxLabel2);
if(binaryPart2==null)
{
String message="Can't get box label "+boxLabel2;
GUIHelper.logger.error(message);
GUIHelper.error(message);
return TestStatus.FAILED;
}
}
binaryPart=GUIHelper.getResourceAsByteArray(boxLabel);
if(binaryPart==null)
{
String message="Can't get box label "+boxLabel;
GUIHelper.logger.error(message);
GUIHelper.error(message);
return TestStatus.FAILED;
}
if(!constructCBoxLabel(cplPart))
{
return TestStatus.FAILED;
}
break;
case LX:
case DLX:
if(isTGModelNumber||isTDModelNumber)
{
if(isTDModelNumber)
{
boxLabel="com/cognitive/manufacturing/labels/TD_C_BOX.lbl";
}
else
{
boxLabel="com/cognitive/manufacturing/labels/TG_C_BOX.lbl";
}
binaryPart=GUIHelper.getResourceAsByteArray(boxLabel);
if(binaryPart==null)
{
String message="Can't get box label "+boxLabel;
GUIHelper.logger.error(message);
GUIHelper.error(message);
return TestStatus.FAILED;
}
}
else
{
}
if(!constructDLXBoxLabel(cplPart))
{
return TestStatus.FAILED;
}
break;
}
if(binaryPart!=null)
{
PrinterHelper.send(binaryPart);
}
if(binaryPart2!=null)
{
PrinterHelper.send(binaryPart2);
}
if(cplPart!=null)
{
PrinterHelper.send(cplPart.toString());
}
if(binaryPart!=null)
{
PrinterHelper.send(binaryPart);
}
if(binaryPart2!=null)
{
PrinterHelper.send(binaryPart2);
}
if(cplPart!=null)
{
PrinterHelper.send(cplPart.toString());
}
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
String message="Unable to close connection to box label printer.";
GUIHelper.logger.error(e, e);
GUIHelper.message(message, SWT.ICON_WARNING|SWT.OK);
}
return TestStatus.PASSED;
}
static TestStatus doPeelerTest()
{
GUIHelper.logger.info("");
if((communicationInterface.toString().indexOf("NETWORK")!=-1)||(communicationInterface==CommunicationInterface.ALL)||(CompositeManufacturing.checkBoxNetwork.getSelection()))
{
StringBuffer macBarCode=new StringBuffer();
if((!isWingman))
{
if(isLX)
{
macBarCode.append("!");
}
macBarCode.append("!0 100 300 1\n");
macBarCode.append("TEXT 1 20 15 MAC:\n");
macBarCode.append("TEXT 1 100 15 "+macHex.toUpperCase()+"\n");
macBarCode.append("BARCODE CODE128(2:5)-20 120 70 "+macHex.toUpperCase()+"\n");
macBarCode.append("END\n");
}
else
{
macBarCode.append("N\n");
macBarCode.append("A20,15,0,3,1,1,N,\"MAC:\"\n");
macBarCode.append("A100,15,0,3,1,1,N,\""+macHex.toUpperCase()+"\"\n");
macBarCode.append("B20,50,0,1A,2,4,70,N,\""+macHex.toUpperCase()+"\"\n");
macBarCode.append("P1\n");
}
PrinterHelper.send(macBarCode.toString());
}
if(exitOption!=ExitOption.PEELER)
{
return TestStatus.SKIPPED;
}
GUIHelper.message(Messages.getString("TestEngine.82"), SWT.OK|SWT.ICON_INFORMATION);
if(isLX)
{
PrinterHelper.send("!!0 100 200 1\nString 24X31 20 20 Peeler Test\nHALT\nEND");
}
else
{
PrinterHelper.send("!0 100 200 1\nString 24X31 20 20 Peeler Test\nHALT\nEND");
}
if(!isConnectionTypeParallelOnly)
{
if(!PrinterHelper.waitFor(".*W0....", 5))
{
GUIHelper.error(Messages.getString("TestEngine.83"));
return TestStatus.FAILED;
}
}
else
{
try
{
Thread.sleep(5000);
}
catch(Exception ex)
{
return TestStatus.FAILED;
}
}
GUIHelper.message(Messages.getString("TestEngine.84"), SWT.ICON_WORKING|SWT.OK);
if(!isConnectionTypeParallelOnly)
{
boolean success=PrinterHelper.waitFor(PrinterHelper.PEELER_PRINTER_READY, 10);
if(!success)
{
GUIHelper.error(Messages.getString("TestEngine.85"));
return TestStatus.FAILED;
}
}
else
{
try
{
Thread.sleep(5000);
}
catch(Exception ex)
{
return TestStatus.FAILED;
}
}
GUIHelper.message(Messages.getString("TestEngine.86"), SWT.OK|SWT.ICON_INFORMATION);
return TestStatus.PASSED;
}
static TestStatus doControlPanel()
{
if(productLine!=ProductLine.CXI)
{
return TestStatus.SKIPPED;
}
String message=Messages.getString("TestEngine.87")+Messages.getString("TestEngine.88")+Messages.getString("TestEngine.89")+Messages.getString("TestEngine.90")+Messages.getString("TestEngine.91")+Messages.getString("TestEngine.92")+Messages.getString("TestEngine.93")+Messages.getString("TestEngine.94")+Messages.getString("TestEngine.95")+Messages.getString("TestEngine.96")+Messages.getString("TestEngine.97")+Messages.getString("TestEngine.98")+Messages.getString("TestEngine.118")+Messages.getString("TestEngine.119")+Messages.getString("TestEngine.120")+Messages.getString("TestEngine.121")+Messages.getString("TestEngine.122")+Messages.getString("TestEngine.99");
int button=GUIHelper.message(message, SWT.ICON_QUESTION|SWT.YES|SWT.NO);
if(button==SWT.YES)
{
return TestStatus.PASSED;
}
else
{
return TestStatus.FAILED;
}
}
static boolean getShippingLabelInfo()
{
GUIHelper.logger.trace("");
boolean connected=establishPrimaryConnection(115200);
if(!connected)
{
GUIHelper.error(Messages.getString("TestEngine.100"));
return false;
}
String currentFirmwarePartNumber=TestEngine.getCurrentFirmwarePartNumber();
String modelNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MODELNUMBER"));
if(modelNumber.startsWith("700"))
{
if(Integer.parseInt(currentFirmwarePartNumber.substring(currentFirmwarePartNumber.length()-3))>150)
{
modelNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW OEMMODELID"));
}
else
{
if(tgModelNumbers==null)
{
tgModelNumbers=new TGModelNumbers(isTGModelNumber);
}
modelNumber=tgModelNumbers.getModelID(modelNumber);
}
}
if(CompositeManufacturing.textModelNumber.getText().equalsIgnoreCase("DLX-K542"))
{
testModelNumber=PrinterHelper.commandWaitResponse("!SHOW MODELNUMBER");
isCustomLabel_K542=true;
}
parseModelNumber(modelNumber);
if (productLine==null&&modelNumber.startsWith("D")) { productLine=ProductLine.DLX; }
if (productLine==null&&modelNumber.startsWith("C")) { productLine=ProductLine.CXI; }
if ((printHead==null||printHead==PrintHead.Automatic)&&productLine==ProductLine.DLX)
{
GUIHelper.logger.info("Printhead auto type selection: switching to use ROHM_KDxxxxDF type.");
CompositeManufacturing.comboPrintHead.select(1);
printHead=PrintHead.ROHM_KDxxxxDF;
}
if ((printHead==null||printHead==PrintHead.Automatic)&&(productLine==ProductLine.CI||productLine==ProductLine.CXI))
{
GUIHelper.logger.info("Printhead auto type selection: switching to use HEC type.");
CompositeManufacturing.comboPrintHead.select(2);
printHead=PrintHead.HEC;
}
if(productLine==ProductLine.DLX&&printHead!=PrintHead.ROHM_KDxxxxDF)
{
int button=GUIHelper.message("Warning!The printhead selected for the DLX printer is not set as "+"\"ROHM_KDxxxxDF\", which is what the production line uses by default. If the operator intended "+"on selecting a printhead other than \"ROHM_KDxxxxDF\", click yes to continue with the custom option. "+"Otherwise, click no to use the production line default printhead.", SWT.ICON_WARNING|SWT.YES|SWT.NO|SWT.CANCEL);
if (button==SWT.YES)
{
GUIHelper.logger.warn("User has manually decided to use a non-standard print head configuration of "+printHead.toString()+".");
}
else if(button==SWT.NO)
{
GUIHelper.logger.warn("User has manually decided to use a standard print head configuration of "+printHead.toString()+".");
CompositeManufacturing.comboPrintHead.select(1);
printHead=PrintHead.ROHM_KDxxxxDF;
}
else if(button==SWT.CANCEL)
{
return false;
}
}
if((productLine==ProductLine.CI||productLine==ProductLine.CXI)&&printHead!=PrintHead.HEC)
{
int button=GUIHelper.message("Warning!The printhead selected for the C-Series printer is not set as "+"\"HEC\", which is what the production line uses by default. If the operator intended "+"on selecting a printhead other than \"HEC\", click*Yes*to continue with the custom option. "+"Otherwise, click*No*to use the production line default printhead.", SWT.ICON_WARNING|SWT.YES|SWT.NO|SWT.CANCEL);
if (button==SWT.YES)
{
GUIHelper.logger.warn("User has manually decided to use a non-standard print head configuration of "+printHead.toString()+".");
}
else if(button==SWT.NO)
{
GUIHelper.logger.warn("User has manually decided to use a standard print head configuration of "+printHead.toString()+".");
CompositeManufacturing.comboPrintHead.select(2);
printHead=PrintHead.HEC;
}
else if(button==SWT.CANCEL)
{
return false;
}
}
String selection=CompositeManufacturing.comboPrintHead.getItem((CompositeManufacturing.comboPrintHead.getSelectionIndex()));
printHead=PrintHead.valueOf(selection);
serialNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW SERIALNUMBER"));
macHex=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MAC"));
currentFirmware=GUIHelper.clean(PrinterHelper.commandWaitResponse("!QR"));
CompositeManufacturing.textModelNumber.setText(modelNumber);
CompositeManufacturing.textSerialNumberActual.setText(serialNumber);
CompositeManufacturing.textMacAddressActual.setText(macHex);
return true;
}
static void printSNLabel()
{
boolean retVal=getShippingLabelInfo();
if(retVal==false)
{
return;
}
doSerialNumberLabel();
}
static void printBoxLabel()
{
boolean retVal=getShippingLabelInfo();
if(retVal==false)
{
return;
}
doBoxLabel();
}
static boolean checkDB(String qualifiedSerialNumber, String qualifiedMac, String modelNumber) throws DBException
{
GUIHelper.logger.trace("");
PrinterId printerId=new PrinterId(qualifiedSerialNumber, qualifiedMac, modelNumber);
GUIHelper.logger.debug(printerId.toString());
StringBuffer urlEncodedQuery=new StringBuffer();
urlEncodedQuery.append("&");
urlEncodedQuery.append("act=gsn");
urlEncodedQuery.append("&");
urlEncodedQuery.append("sn=");
urlEncodedQuery.append(printerId.getSerialNumber());
urlEncodedQuery.append("&");
urlEncodedQuery.append("snp=");
urlEncodedQuery.append(printerId.getSnPrefix());
urlEncodedQuery.append("&");
urlEncodedQuery.append("lc=");
urlEncodedQuery.append(printerId.getLocationCode());
String url=jadminURLBase+urlEncodedQuery.toString();
if(isTGModelNumber)
{
url=jadminURLBaseTG+urlEncodedQuery.toString();
}
String xmlPrinterId=httpGet(url);
if(xmlPrinterId==null)
{
throw new DBException("Can't connect to database.");
}
PrinterId dbPrinterId=new PrinterId(xmlPrinterId);
GUIHelper.logger.debug(dbPrinterId.toString());
return printerId.equals(dbPrinterId);
}
static TestStatus enablePCL(boolean isParsingRequired)
{
GUIHelper.logger.trace("");
boolean connected=establishPrimaryConnection(115200);
if(!connected)
{
GUIHelper.error(Messages.getString("TestEngine.100"));
return TestStatus.FAILED;
}
if(isParsingRequired)
{
String currentFirmwarePartNumber=TestEngine.getCurrentFirmwarePartNumber();
String modelNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MODELNUMBER"));
if(modelNumber.startsWith("700"))
{
if(Integer.parseInt(currentFirmwarePartNumber.substring(currentFirmwarePartNumber.length()-3))>150)
{
modelNumber=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW OEMMODELID"));
}
else
{
if(tgModelNumbers==null)
{
tgModelNumbers=new TGModelNumbers(isTGModelNumber);
}
modelNumber=tgModelNumbers.getModelID(modelNumber);
}
}
boolean success=parseModelNumber(modelNumber);
if(!success)
{
GUIHelper.error(Messages.getString("TestEngine.103"));
return TestStatus.FAILED;
}
}
if(productLine==null)
{
productLine=ProductLine.CXI;
}
if(customization==null)
{
customization="PCL";
}
TestStatus testStatus=doFirmwareDownload(false, CompositeManufacturing.progressBarFirmwareDownload, true);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
GUIHelper.error(Messages.getString("TestEngine.103"));
return TestStatus.FAILED;
}
isPCL=true;
testStatus=doLoadFonts(printDensity);
if(testStatus==TestStatus.FAILED||testStatus==TestStatus.CANCELLED)
{
isPCL=false;
GUIHelper.error(Messages.getString("TestEngine.103"));
return TestStatus.FAILED;
}
isPCL=false;
String sn=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW SERIALNUMBER")).toUpperCase();
String mac=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MAC")).toUpperCase();
String exeName="FPKtool.exe";
String companyname=GUIHelper.brand.getProperty("company.name", "CognitiveTPG").replaceAll("TPG", "").replaceAll(" ", "");
String path="C:/Program Files/"+companyname+"/FPK/";
int exitVal;
try
{
Process proc=new ProcessBuilder(path+exeName, "-e0", "-k13", "-s=\""+sn+"\"", "-m=\""+mac+"\"", "\""+path+"pcl_enable.txt\"", "\""+path+"fpk.out\"").start();
exitVal=proc.waitFor();
if(exitVal!=0)
{
String msg="FPK generation failed with error code ["+exitVal+"].";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return TestStatus.FAILED;
}
}
catch(Exception e)
{
String msg="FPK generation failed due to exception ["+e.toString()+"].";
GUIHelper.error(msg);
return TestStatus.FAILED;
}
StringBuffer licenseLabel=new StringBuffer();
licenseLabel.append("!LOAD LICENSE 40 \n");
try
{
File file=new File(path+"fpk.out");
FileReader fileReader=new FileReader(file);
BufferedReader bufferedReader=new BufferedReader(fileReader);
String line;
while((line=bufferedReader.readLine())!=null)
{
licenseLabel.append(line+"\n");
}
PrinterHelper.send(licenseLabel.toString());
bufferedReader.close();
file.delete();
}
catch(FileNotFoundException e)
{
String msg="Can't find fpk output file.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return TestStatus.FAILED;
}
catch(IOException e)
{
String msg="Can't read fpk output file.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return TestStatus.FAILED;
}
PrinterHelper.setVariable("LANGUAGE", "PCL");
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
String msg="Can't close printer connection.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return TestStatus.FAILED;
}
return TestStatus.PASSED;
}
static boolean enableTS()
{
GUIHelper.logger.trace("");
boolean connected=establishPrimaryConnection(115200);
if(!connected)
{
GUIHelper.error(Messages.getString("TestEngine.100"));
return false;
}
String sn=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW SERIALNUMBER")).toUpperCase();
String mac=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MAC")).toUpperCase();
String exeName="FPKtool.exe";
String companyname=GUIHelper.brand.getProperty("company.name", "CognitiveTPG").replaceAll("TPG", "").replaceAll(" ", "");
String path="C:/Program Files/"+companyname+"/FPK/";
int exitVal;
try
{
Process proc=new ProcessBuilder(path+exeName, "-e0", "-k13", "-s=\""+sn+"\"", "-m=\""+mac+"\"", "\""+path+"eth_disable_TS_enable.txt\"", "\""+path+"fpk.out\"").start();
exitVal=proc.waitFor();
if(exitVal!=0)
{
String msg="FPK generation failed with error code ["+exitVal+"].";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
}
catch(Exception e)
{
String msg="FPK generation failed due to exception ["+e.toString()+"].";
GUIHelper.error(msg);
return false;
}
StringBuffer licenseLabel=new StringBuffer();
licenseLabel.append("!LOAD LICENSE 40 \n");
try
{
File file=new File(path+"fpk.out");
FileReader fileReader=new FileReader(file);
BufferedReader bufferedReader=new BufferedReader(fileReader);
String line;
while((line=bufferedReader.readLine())!=null)
{
licenseLabel.append(line+"\n");
}
PrinterHelper.send(licenseLabel.toString());
bufferedReader.close();
file.delete();
}
catch(FileNotFoundException e)
{
String msg="Can't find fpk output file.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
catch(IOException e)
{
String msg="Can't read fpk output file.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
String msg="Can't close printer connection.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 5);
connected=establishPrimaryConnection(115200);
if(!connected)
{
GUIHelper.error(Messages.getString("TestEngine.100"));
return false;
}
return true;
}
static boolean disablePCL()
{
GUIHelper.logger.trace("");
boolean connected=establishPrimaryConnection(115200);
if(!connected)
{
GUIHelper.error(Messages.getString("TestEngine.100"));
return false;
}
String sn=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW SERIALNUMBER")).toUpperCase();
String mac=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MAC")).toUpperCase();
String exeName="FPKtool.exe";
String companyname=GUIHelper.brand.getProperty("company.name", "CognitiveTPG").replaceAll("TPG", "").replaceAll(" ", "");
String path="C:/Program Files/"+companyname+"/FPK/";
int exitVal;
try
{
Process proc=new ProcessBuilder(path+exeName, "-e0", "-k13", "-s=\""+sn+"\"", "-m=\""+mac+"\"", "\""+path+"pcl_disable.txt\"", "\""+path+"fpk.out\"").start();
exitVal=proc.waitFor();
if(exitVal!=0)
{
String msg="FPK generation failed with error code ["+exitVal+"].";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
}
catch(Exception e)
{
String msg="FPK generation failed due to exception ["+e.toString()+"].";
GUIHelper.error(msg);
return false;
}
StringBuffer licenseLabel=new StringBuffer();
licenseLabel.append("!LOAD LICENSE 40 \n");
try
{
File file=new File(path+"fpk.out");
FileReader fileReader=new FileReader(file);
BufferedReader bufferedReader=new BufferedReader(fileReader);
String line;
while((line=bufferedReader.readLine())!=null)
{
licenseLabel.append(line+"\n");
}
PrinterHelper.send(licenseLabel.toString());
bufferedReader.close();
file.delete();
}
catch(FileNotFoundException e)
{
String msg="Can't find fpk output file.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
catch(IOException e)
{
String msg="Can't read fpk output file.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
PrinterHelper.setVariable("LANGUAGE", "NONE");
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
String msg="Can't close printer connection.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 5);
connected=establishPrimaryConnection(115200);
if(!connected)
{
GUIHelper.error(Messages.getString("TestEngine.100"));
return false;
}
return true;
}
static boolean disableTS()
{
GUIHelper.logger.trace("");
boolean connected=establishPrimaryConnection(115200);
if(!connected)
{
GUIHelper.error(Messages.getString("TestEngine.100"));
return false;
}
String sn=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW SERIALNUMBER")).toUpperCase();
String mac=GUIHelper.clean(PrinterHelper.commandWaitResponse("!SHOW MAC")).toUpperCase();
String exeName="FPKtool.exe";
String companyname=GUIHelper.brand.getProperty("company.name", "CognitiveTPG").replaceAll("TPG", "").replaceAll(" ", "");
String path="C:/Program Files/"+companyname+"/FPK/";
int exitVal;
try
{
Process proc=new ProcessBuilder(path+exeName, "-e0", "-k13", "-s=\""+sn+"\"", "-m=\""+mac+"\"", "\""+path+"TS_disable.txt\"", "\""+path+"fpk.out\"").start();
exitVal=proc.waitFor();
if(exitVal!=0)
{
String msg="FPK generation failed with error code ["+exitVal+"].";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
}
catch(Exception e)
{
String msg="FPK generation failed due to exception ["+e.toString()+"].";
GUIHelper.error(msg);
return false;
}
StringBuffer licenseLabel=new StringBuffer();
licenseLabel.append("!LOAD LICENSE 40 \n");
try
{
File file=new File(path+"fpk.out");
FileReader fileReader=new FileReader(file);
BufferedReader bufferedReader=new BufferedReader(fileReader);
String line;
while((line=bufferedReader.readLine())!=null)
{
licenseLabel.append(line+"\n");
}
PrinterHelper.send(licenseLabel.toString());
bufferedReader.close();
file.delete();
}
catch(FileNotFoundException e)
{
String msg="Can't find fpk output file.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
catch(IOException e)
{
String msg="Can't read fpk output file.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
try
{
PrinterHelper.printer.closeConnection();
}
catch(Exception e)
{
String msg="Can't close printer connection.";
GUIHelper.error(msg);
GUIHelper.logger.error(msg);
return false;
}
PrinterHelper.waitFor(PrinterHelper.GENERAL_PRINTER_READY, 5);
connected=establishPrimaryConnection(115200);
if(!connected)
{
GUIHelper.error(Messages.getString("TestEngine.100"));
return false;
}
return true;
}
static void scanModelNumber()
{
QualityScanner scanner=null;
try
{
scanner=new QualityScanner(CompositeManufacturing.textScanner.getText().toUpperCase(), 9600);
scanner.openConnection();
CompositeManufacturing.textModelNumber.setText("");
CompositeManufacturing.buttonScanModelNumber.setEnabled(false);
String scannedModelNumber=scanner.waitForBarcode();
if(scannedModelNumber!=null)
{
CompositeManufacturing.textModelNumber.setText(scannedModelNumber);
}
}
catch(Exception e)
{
GUIHelper.error(Messages.getString("TestEngine.49"));
GUIHelper.logger.error(e, e);
}
finally
{
CompositeManufacturing.buttonScanModelNumber.setEnabled(true);
try
{
scanner.closeConnection();
}
catch(Exception e)
{
GUIHelper.logger.error(e, e);
GUIHelper.error(Messages.getString("TestEngine.50"));
}
}
}
}
