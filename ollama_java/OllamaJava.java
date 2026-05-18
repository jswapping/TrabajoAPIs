package ollama_java;

import java.util.Scanner;
import com.google.gson.JsonObject;

import apis.DiscordWebhook;
import apis.OpenStreetMap;
import apis.Wallapop;

import java.util.*;
//import com.google.gson.JsonObject;

public class OllamaJava {
	
	private static final Scanner sc = new Scanner(System.in);
	
    public static void main(String[] args) {
    	//System.setProperty("java.net.preferIPv4Stack" , "true");
        System.out.print("--- EL DORADO DEALS ---\n\n");
    	//String ip, latitude, longitude;
    	
        try {
        	JsonObject jsonRootIp = IpWhoService.getIpWhoRoot();
        	String ip = JsonUtils.getString(jsonRootIp, "ip");
        	
        	System.out.println("Peticiones realizadas desde: " + ip + "\n");
        	
        	System.out.println("Introduzca una ciudad/pueblo de España: ");
        	String ciudad = sc.nextLine();
        	
        	OpenStreetMap osmLocalizacion = new OpenStreetMap(ciudad);
        	
        	System.out.println("Localización: " + osmLocalizacion.toString());
        	
            DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1504545090054389772/J2EjRa-qlPX-Ok2cxvUVzZff9MUNdYF-qT7DJ1aFJtb4qR3uo6uDbl9a7i17_CV4So3Y");
        	
            System.out.println("Webhook inicializada con nombre: " + webhook.getName());
            
        	Wallapop wallapop = new Wallapop(osmLocalizacion.getLatitud(), osmLocalizacion.getLongitud(), osmLocalizacion.getRadioCiudad());
        	
        	// Ejemplo de response
            String responseLLM = "iPhone 13,PS5,Nintendo Switch OLED,RTX 3070";
            String[] keywordsSplitted = responseLLM.split(",");
            
            System.out.println("Buscando productos: " + responseLLM);
            
//          System.out.println(LLMService.preguntar(""
//    		+ "Dime los 5 productos mas vendidos de el ultimo año. Para wallapop. Solo hazme una lista."
//    		+ "Los articulos modelo. Por ejemplo: Iphone 12,RTX 2060... Deben tener ese formato, separado por coma."
//    		+ "Lo unico que debes decirme son los articulos separados por coma en este formato: producto1,producto2..."
//    		+ "Finalmente, no digas nada mas. Deben ser diferentes a los anteriores."));
            
            List<List<JsonObject>> listaItems = new ArrayList<>();
            int contadorItems = 0;
            for (String producto : keywordsSplitted) {
            	String resJson = wallapop.buscarProducto(producto);
            	List<JsonObject> productosEncontrados = wallapop.obtenerInformacionUtilItem(resJson);
            	int sizeProductos = productosEncontrados.size();
            	contadorItems+=sizeProductos;
            	
            	if (sizeProductos > 0) {
            		listaItems.add(productosEncontrados);
            	}
            	
            }
            System.out.println("Proudctos obtenidos en total: " + contadorItems);
//            
//            String itemsValidos = LLMService.preguntar("Necesito que a partir de los items de Wallapop que te he proporcionado, "
//            		+ "Me pases la URL de aquellos que valgan la pena comprar así de una explicación del porque de esta decisión.");
//            
            webhook.sendMessageAsFile(listaItems.toString(), "todosItems" + osmLocalizacion.getCiudad() + ".json");
        	
            System.out.println("Items enviados a discord");
            
        }catch (Exception ex){
        	System.out.println(ex.getMessage());
        }
    }

}
