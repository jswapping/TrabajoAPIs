package implementaciones_api;

import com.google.gson.JsonObject;

import ollama_java.ApiClient;
import ollama_java.JsonUtils;

public class IpWhoService {
    private static final String URL = "http://ipwho.is/";
    
//    public static String getIPv2() throws Exception{
//    	String json = ApiClient.get("https://api.ipify.org/");
//    	
//    	//JsonObject root = JsonUtils.parse(json);
//    	
//    	return json;
//    }
    
    public static JsonObject getIpWhoRoot() throws Exception {
        String json = ApiClient.get(URL);
        JsonObject root = JsonUtils.parse(json);
        return root;
    }
    
    public static String getLatitud() throws Exception{
    	JsonObject root = getIpWhoRoot();
        return JsonUtils.getString(root, "latitude");
    }
    
    public static String getLongitud() throws Exception{
    	JsonObject root = getIpWhoRoot();
        return JsonUtils.getString(root, "longitude");
    }
    
    public static String getIP() throws Exception{
    	JsonObject root = getIpWhoRoot();
        return JsonUtils.getString(root, "ip");
    }
    
}
