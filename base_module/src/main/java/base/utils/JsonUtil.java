package base.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.MobileWatcherConfig;
import config.WebWatcherConfig;
import config.beans.WebWatchObject;
import config.utils.ConfigUtil;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;


/**
 * The json util used to handle json converting,
 * <br> Including covert json to string and convert string to json.
 */
public class JsonUtil {


    private static final String JSON_RESOURCES_PREFIX = "src"+File.separator+"main"+File.separator+"java"+File.separator;


    /** The google gson object*/
    private static final Gson googleJson = new Gson();

    /**
     * Get google gson object
     * @return  Google gson object
     */
    public static Gson getGoogleJson(){
        return googleJson;
    }

    /**
     * Convert json string to an object using google gson
     * @param json The json string
     * @param class1  The class you want to map to the json string
     * @param <T>  Type parameter, What type of object you want to convert to.
     * @return The converted object
     */
    public static <T> Object strToObject(String json , Class<T> class1){
        return googleJson.fromJson(json, class1);
    }


    /**
     * Convert Java object to json string using google gson
     * @param object The java object you want to convert.
     * @return  The converted json string.
     */
    public static String objectToStr(Object object){
        return googleJson.toJson(object);
    }

    /**
     * Get result field from a whole json string
     * @param wholeJson   The whole json string
     * @param xpath The field xpath
     *              <p>
     *              <br> <b>See a demo, given the below json string,</b>
     *              <br> {"persons":[{"name":"zhangsan","age":19},{"name":"lisi","age":20}]}
     *              <br> You want get the name "lisi" of the second person,
     *              <br> The xpath should be "persons/[1]/name"
     * @return  The required field as an object.
     */
    public static Object getResultField(String wholeJson,String xpath) {
        String [] xpathArray = xpath.split("/");
        Object lastObject;
        if(xpathArray[0].startsWith("[")) {
            lastObject = JSONArray.fromObject(wholeJson);
        }else {
            lastObject = JSONObject.fromObject(wholeJson);
        }

        for(String path : xpathArray) {
            if(path.startsWith("[")) {
                path = path.replace("[","");
                path = path.replace("]","");
                int index = Integer.parseInt(path);
                lastObject = ((JSONArray)lastObject).get(index);
            }else {
                lastObject = ((JSONObject)lastObject).get(path);
            }
        }

        return lastObject;
    }


    /**
     * Load the web and mobile watcher config as json
     * @param classPath   The config class
     * @param rawType  Raw type of the collection class, eg. List.class, Map.class
     * @param typeArguments  Raw type of the class contained in collection class
     * @param <T> Type parameter,return a collection of objects
     * @return Return a collection of objects
     * @throws Exception This method throws an exception when reflection fails
     */
    public static <T>  T  loadConfigType(Class classPath , Type rawType, Type ... typeArguments) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("config");
        sb.append(File.separator);
        sb.append("watcher");
        sb.append(File.separator);

        String className = classPath.getName();
        String [] dirNames = className.split("\\.");
        String fileName = dirNames[dirNames.length-1];
        sb.append(fileName);


        String locale = ConfigUtil.getCurrentLocale();
        if(!locale.equals("en_US")) {
            sb.append("_");
            sb.append(locale);
        }
        sb.append(".json");

        String jsonFilePath = sb.toString();
        return loadComplexType(jsonFilePath,rawType,typeArguments);
    }





    /**
     * Load complex type( collection type) from a json file
     * @param jsonFilePath  Json file path
     * @param rawType Raw type of the collection class, eg. List.class, Map.class
     * @param typeArguments Raw type of the class contained in collection class
     * @param <T>  Type parameter,return a collection of objects
     * @return Return a collection of objects
     * @throws Exception  This method throws an exception when reflection fails
     */
    public static <T>  T  loadComplexType(String jsonFilePath , Type rawType, Type ... typeArguments) throws Exception {
        String json = JFileUtils.readFile(jsonFilePath);
        Type type = TypeToken.getParameterized(rawType, typeArguments).getType();
        T map = JsonUtil.getGoogleJson().fromJson(json,  type);
        return map;
    }

    /**
     * Load complex type( collection type) from a json string
     * @param jsonStr The json string to convert to complex type
     * @param rawType Raw type of the collection class, eg. List.class, Map.class
     * @param typeArguments Raw type of the class contained in collection class
     * @param <T> Type parameter,return a collection of objects
     * @return Return a collection of objects
     * @throws Exception This method throws an exception when reflection fails
     */
    public static <T>  T  loadComplexTypeByStr(String jsonStr , Type rawType, Type ... typeArguments) throws Exception {
        Type type = TypeToken.getParameterized(rawType, typeArguments).getType();
        T map = JsonUtil.getGoogleJson().fromJson(jsonStr,  type);
        return map;
    }


    /**
     * Method to convert a class name to path
     * @param classPath  Class name can be parsed to a path
     * @return  The converted path
     * @throws Exception   Throws exception when can not get the class path
     */
    public static String convertClassPathToPath(Class classPath) throws Exception{
        String className = classPath.getName();
        //For Debug only
//        System.out.println(className);
        String [] dirNames = className.split("\\.");

        int len = dirNames.length;
        if(len == 1) {
            throw new Exception("Put Page object under root dir is not allowed!!");
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<dirNames.length-1;i++) {
            sb.append(dirNames[i]);
            sb.append(File.separator);
        }


        sb.append(dirNames[dirNames.length-1]);
        sb.append(".json");
        String jsonFilePath = sb.toString();
        jsonFilePath = JSON_RESOURCES_PREFIX + jsonFilePath;
        //for debug only
//        System.out.println(jsonFilePath);
        return jsonFilePath;
    }





    public static Object getLanguageFileField(Class classPath,String xpath) throws  Exception{
        String filePath = convertClassPathToPath(classPath);
        File f = new File(filePath);

        if(!f.exists()) {
            return null;
        }

        String fileContent = JFileUtils.readFile(filePath);
        String locale = ConfigUtil.getCurrentLocale();
        String fieldXpath = locale+"/"+xpath;
        return JsonUtil.getResultField(fileContent,fieldXpath);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getLanguageFileField(MobileWatcherConfig.class,"persons/[1]/name"));
    }


}
