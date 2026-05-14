package ollama_java;

import java.net.URI;
import java.net.http.*;


import com.google.gson.*;

public class OpenStreetMap {
	// https://nominatim.openstreetmap.org/search?q=Sevilla+Espa%C3%B1a&format=json
	private static final String WEB = "https://nominatim.openstreetmap.org";
	
	private String ciudad;
	private String urlFormateada;
	private JsonArray respuestaPeticion;
	
	private String latitud;
	private String longitud;
	private String displayNameCiudad;
	private String radioCiudad;
	
	public OpenStreetMap(String ciudad) throws Exception{
		setInformacionCiudad(ciudad);
		
		JsonObject jsonCiudad = respuestaPeticion.get(0).getAsJsonObject();
		this.latitud = JsonUtils.getString(jsonCiudad, "lat");
		this.longitud = JsonUtils.getString(jsonCiudad, "lon");
		this.displayNameCiudad = JsonUtils.getString(jsonCiudad, "display_name");
		this.radioCiudad = String.valueOf(getRadioCiudadAprox());
	}
	
	private void setInformacionCiudad(String ciudad) throws Exception {
		if (ciudad == null) {
			throw new Exception("Introduzca una ciudad correcta");
		}
		
		HttpClient client = HttpClient.newHttpClient();
		String urlFormada = WEB + "/search?q=" + ciudad.replace(" ", "+") + "+Espa%C3%B1a&format=json";
		HttpRequest req = HttpRequest.newBuilder()
				.uri(URI.create(urlFormada))
				.header("Accept", "application/json, text/plain, */*")
				.GET()
				.build();
		
		HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
		
		String body = res.body();
		
		if (body.equals("[]")) {
			throw new Exception("La ciudad es incorrecta");
		}
		
		this.ciudad = ciudad;
		this.urlFormateada = urlFormada;
		this.respuestaPeticion = JsonParser.parseString(body).getAsJsonArray();
	}
	
	// Generado con Gemini: Le pedí como calcular el radio en km aproximado de un lugar en función de sus medias de latitud y longitud:     
	//	"boundingbox": [
	//    "37.1868230",
	//    "37.3903553",
	//    "-5.9484204",
	//    "-5.6495452"
	//  ]
	private int getRadioCiudadAprox() {
	    JsonObject ciudad = respuestaPeticion.get(0).getAsJsonObject();
	    JsonArray bbox = ciudad.getAsJsonArray("boundingbox");

	    double latMin = Double.parseDouble(bbox.get(0).getAsString());
	    double latMax = Double.parseDouble(bbox.get(1).getAsString());
	    double lonMin = Double.parseDouble(bbox.get(2).getAsString());
	    double lonMax = Double.parseDouble(bbox.get(3).getAsString());
	    
	    double latCentro = (latMin + latMax) / 2;
	    double lonCentro = (lonMin + lonMax) / 2;

	    double R = 6371;
	    double dLat = Math.toRadians(latMax - latCentro);
	    double dLon = Math.toRadians(lonMax - lonCentro);

	    double a = Math.sin(dLat/2) * Math.sin(dLat/2)
	             + Math.cos(Math.toRadians(latCentro)) * Math.cos(Math.toRadians(latMax))
	             * Math.sin(dLon/2) * Math.sin(dLon/2);

	    double radio = R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    return (int) Math.round(radio) + 15; // le añado 15 para que salgan más resultados
	}
	
	public String getCiudad() {
		return ciudad;
	}

	public String getUrlFormateada() {
		return urlFormateada;
	}

//	public JsonArray getRespuestaPeticion() {
//		return respuestaPeticion;
//	}

	public String getLatitud() {
		return latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public String getDisplayNameCiudad() {
		return displayNameCiudad;
	}
	
	public String getRadioCiudad() {
		return this.radioCiudad;
	}

	@Override
	public String toString() {
		return "OpenStreetMap [ciudad=" + ciudad + ", latitud=" + latitud + ", longitud=" + longitud
				+ ", displayNameCiudad=" + displayNameCiudad + ", radioCiudad=" + radioCiudad + "]";
	}
}
