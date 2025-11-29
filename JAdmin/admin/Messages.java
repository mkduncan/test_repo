public class Messages
{
private static final String BUNDLE_NAME="com.cognitive.admin.messages";
private static final Locale locale=Locale.getDefault();
private static final ResourceBundle RESOURCE_BUNDLE=ResourceBundle
.getBundle(BUNDLE_NAME, locale);
private Messages()
{
}
public static String getString(String key)
{
try
{
return RESOURCE_BUNDLE.getString(key);
}
catch(MissingResourceException e)
{
return '!'+key+'!';
}
}
}
