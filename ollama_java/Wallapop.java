package ollama_java;

//import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
//import java.util.zip.GZIPInputStream;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class Wallapop {
    private static final String SEARCH_ENDPOINT = "https://api.wallapop.com/api/v3/search";
    
    private String latitude;
    private String longitude;
    private String distanceKm;
    
    public Wallapop(String latitude, String longitude, String distanceKm) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceKm = distanceKm;
    }
    // Headers no permitidos en eclipse
    
//    .header("Host", "api.wallapop.com")
//    .header("Connection", "keep-alive")
    // https://api.wallapop.com/api/v3/search?keywords=rtx+2060&source=side_bar_filters&latitude=37.339677&longitude=-5.841805&distance_in_km=50&order_by=newest&min_sale_price=0&max_sale_price=600
	// No va: https://api.wallapop.com/api/v3/search?keywords=Xiaomi+Scooter+Pro+2&latitude=37.401173&longitude=-6.0321884&distance_in_km=50&order_by=newest&source=side_bar_filters
	public String buscarProducto(String product) {
	    try {
	    	// " " -> "+"
	        String encodedQuery = URLEncoder.encode(product, StandardCharsets.UTF_8);
	        
	        // Creamos la url
	        String url = SEARCH_ENDPOINT + "?keywords=" + encodedQuery
	                + "&source=side_bar_filters"
	                + "&latitude=" + this.latitude
	                + "&longitude=" + this.longitude
	                + "&distance_in_km=" + this.distanceKm
	                + "&order_by=newest";
	        
	        // Es el navegador
	        HttpClient client = HttpClient.newHttpClient();
	        // La petición copiada de
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .header("deviceos", "0")
	                .header("x-deviceos", "0")
	                .header("x-appversion", "88570")
	                .header("sec-ch-ua-platform", "\"Windows\"")
	                .header("sec-ch-ua-mobile", "?0")
	                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
	                .header("Accept", "application/json, text/plain, */*")
	                .header("Accept-Language", "es,es-ES;q=0.9")
	                //.header("Accept-Encoding", "gzip, deflate, br") Aceptar el formato que te proporciona por defecto, texto plano.
	                .header("Origin", "https://es.wallapop.com")
	                .header("Referer", "https://es.wallapop.com/")
	                .GET()
	                .build();
	
	        // La respuesta. será un string
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        // Devuelve el json
	        return response.body();
	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    // Si no encuentra nada, entonces, retorna un String vacío
	    
	    return "";
	}
    
	public List<JsonObject> obtenerInformacionUtilItem(String jsonProductos){
		List<JsonObject> resultados = new ArrayList<>();
		
		try {
			JsonArray items = JsonParser.parseString(jsonProductos)
					.getAsJsonObject()
					.getAsJsonObject("data")
					.getAsJsonObject("section")
					.getAsJsonObject("payload")
					.getAsJsonArray("items"); // Los items son una array de elementos json
			
			for (JsonElement jo : items) {
				JsonObject item = jo.getAsJsonObject();
				JsonObject result = new JsonObject();
				
	            result.addProperty("id",item.get("id").getAsString());
	            result.addProperty("title", item.get("title").getAsString());
	            result.addProperty("price",item.getAsJsonObject("price").get("amount").getAsDouble());
	            result.addProperty("currency",item.getAsJsonObject("price").get("currency").getAsString());
	            result.addProperty("city",item.getAsJsonObject("location").get("city").getAsString());
	            result.addProperty("image",item.getAsJsonArray("images").get(0).getAsJsonObject().getAsJsonObject("urls").get("medium").getAsString());
	            result.addProperty("url","https://es.wallapop.com/item/" + item.get("web_slug").getAsString());
	            result.addProperty("description",item.get("description").getAsString());

	            resultados.add(result);	
			}
			
		}catch(Exception ex) {
			ex.getStackTrace();
		}
		return resultados;
	}
	
}