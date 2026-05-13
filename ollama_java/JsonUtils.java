package ollama_java;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtils {

	public static JsonObject parse(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    public static String getString(JsonObject obj, String key) {
        return obj.get(key).getAsString();
    }
    
    public static String getAllString(JsonObject obj) {
    	return obj.toString();
    }

    public static JsonObject getObject(JsonObject obj, String key) {
        return obj.getAsJsonObject(key);
    }
}
