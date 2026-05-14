package ollama_java;

import java.util.Scanner;
import com.google.gson.JsonObject;
import java.util.*;
//import com.google.gson.JsonObject;

public class OllamaJava {
	
	private static final Scanner sc = new Scanner(System.in);
	
    public static void main(String[] args) {
    	//System.setProperty("java.net.preferIPv4Stack" , "true");
        System.out.print("--- WALLAPOP SCRAPER 1ºDAM ---\n\n");
    	//String ip, latitude, longitude;
    	
        try {
//        	JsonObject jsonRootIp = IpWhoService.getIpWhoRoot();
//        	ip = JsonUtils.getString(jsonRootIp, "ip");
//        	latitude = JsonUtils.getString(jsonRootIp, "latitude");
//        	longitude = JsonUtils.getString(jsonRootIp, "longitude");
        	
        	System.out.println("Introduzca una ciudad/pueblo de España: ");
        	String ciudad = sc.nextLine();
        	
        	OpenStreetMap osmLocalizacion = new OpenStreetMap(ciudad);
        	
        	System.out.println(osmLocalizacion.toString());
        	
        	Wallapop wallapop = new Wallapop(osmLocalizacion.getLatitud(), osmLocalizacion.getLongitud(), osmLocalizacion.getRadioCiudad());
        	
        	// Ejemplo de response
            String responseLLM = "iPhone 13,PS5,Nintendo Switch OLED,RTX 3070,AirPods Pro,Steam Deck,Samsung Galaxy S22,Dyson V11,Xiaomi Scooter Pro 2,Apple Watch Series 8";
            String[] keywordsSplitted = responseLLM.split(",");
            
//          System.out.println(LLMService.preguntar(""
//    		+ "Dime los 10 productos mas vendidos de el ultimo año. Para wallapop. Solo hazme una lista."
//    		+ "Los articulos modelo. Por ejemplo: Iphone 12,RTX 2060... Deben tener ese formato, separado por coma."
//    		+ "Lo unico que debes decirme son los articulos separados por coma en este formato: producto1,producto2..."
//    		+ "Finalmente, no digas nada mas. Deben ser diferentes a los anteriores."));
            
            for (String producto : keywordsSplitted) {
            	String resJson = wallapop.buscarProducto(producto);
            	List<JsonObject> productosEncontrados = wallapop.obtenerInformacionUtilItem(resJson);
            	//System.out.println(productosEncontrados);
            	for (JsonObject jo : productosEncontrados) {
            		System.out.println(jo);
            	}
            	//Thread.sleep(1000);
            }
        	
        }catch (Exception ex){
        	System.out.println(ex.getMessage());
        }
    }

}
