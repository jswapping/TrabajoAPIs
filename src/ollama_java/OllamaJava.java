package ollama_java;

import java.util.Scanner;

import com.google.gson.JsonObject;

import implementaciones_api.*;

import java.util.*;

public class OllamaJava {
	
	private static final Scanner sc = new Scanner(System.in);
	
    public static void main(String[] args) {
    	//System.setProperty("java.net.preferIPv4Stack" , "true");
        System.out.print("--- WALLAPOP SCRAPER 1ºDAM ---\n\n");
    	String ip, latitude, longitude;
    	
        try {
        	
        	JsonObject root = IpWhoService.getIpWhoRoot();
        	ip = JsonUtils.getString(root, "ip");
        	latitude = JsonUtils.getString(root, "latitude");
        	longitude = JsonUtils.getString(root, "longitude");
        	
        	System.out.println(ip);
        	System.out.println(latitude);
        	System.out.println(longitude);
        	
        	Wallapop wallapop = new Wallapop(Double.parseDouble(latitude), Double.parseDouble(longitude));
        	
            String responseLLM = "iPhone 13,PS5,Nintendo Switch OLED,RTX 3070,AirPods Pro,Steam Deck,Samsung Galaxy S22,Dyson V11,Xiaomi Scooter Pro 2,Apple Watch Series 8";
            String[] keywordsSplitted = responseLLM.split(",");
            
            //HashSet<String> keywords = new HashSet<String>();
            
            for (String product : keywordsSplitted) {
            	String res = wallapop.buscarProducto(product);
            	System.out.println(res);
            	System.out.println("----------------");
            }
        	
        	
        }catch (Exception ex){
        	System.out.println(ex.getMessage());
        }
        
        System.out.println("--- Los 10 producto mas vendidos ---");
//        System.out.println(LLMService.preguntar(""
//        		+ "Dime los 10 productos mas vendidos de el ultimo año. Para wallapop. Solo hazme una lista."
//        		+ "Los articulos modelo. Por ejemplo: Iphone 12,RTX 2060... Deben tener ese formato, separado por coma."
//        		+ "Lo unico que debes decirme son los articulos separados por coma en este formato: producto1,producto2..."
//        		+ "Finalmente, no digas nada mas. Deben ser diferentes a los anteriores."));
        
        String responseLLM = "iPhone 13,PS5,Nintendo Switch OLED,RTX 3070,AirPods Pro,Steam Deck,Samsung Galaxy S22,Dyson V11,Xiaomi Scooter Pro 2,Apple Watch Series 8";
        String[] keywordsSplitted = responseLLM.split(",");
        
        HashSet<String> keywords = new HashSet<String>();
        
        for (String product : keywordsSplitted) {
        	System.out.println(product);
        	keywords.add(product);
        }
        
        System.out.println(keywords);
        
        

    }

}
