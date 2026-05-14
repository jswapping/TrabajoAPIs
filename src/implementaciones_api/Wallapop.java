package implementaciones_api;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Wallapop {
    
    private static final String SEARCH_ENDPOINT = "https://api.wallapop.com/api/v3/search";
    private static int instancias = 0;
    
    private double latitude;
    private double longitude;
    
    public Wallapop(double latitude, double longitude) {
        Wallapop.instancias++;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
	public String buscarProducto(String product) {
	    try {
	        String encodedQuery = URLEncoder.encode(product, StandardCharsets.UTF_8);
	        String url = SEARCH_ENDPOINT + "?keywords=" + encodedQuery 
	                + "&latitude=" + latitude + "&longitude=" + longitude 
	                + "&distance_in_km=50&order_by=newest&source=side_bar_filters";
	
	        HttpClient client = HttpClient.newHttpClient();
	        
	        // Es CRUCIAL añadir estos headers para saltarse el bloqueo de CloudFront
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
	                .header("Accept", "application/json, text/plain, */*")
	                .header("Accept-Language", "es,es-ES;q=0.9")
	                .header("Origin", "https://es.wallapop.com")
	                .header("Referer", "https://es.wallapop.com/")
	                .header("deviceos", "0")
	                .GET()
	                .build();
	
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	
	        // Si te sigue dando 403, imprime el status para saber si es por cookies o TLS
	        if (response.statusCode() == 200) {
	            return response.body();
	        } else {
	            System.out.println("Código de error: " + response.statusCode());
	            System.out.println("Cuerpo del error: " + response.body());
	        }
	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "";
	}
    
    public int getInstancias() {
        return Wallapop.instancias;
    }
}