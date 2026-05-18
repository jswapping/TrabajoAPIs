package apis;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;
import ollama_java.ApiClient;
import ollama_java.JsonUtils;

public class DiscordWebhook {
	private String webhook;
	private String name;
	private Long id;
	
	private static final int MAXIMO_CARACTERES = 1999;
	
	public DiscordWebhook(String webhook) throws Exception{
		setWebhook(webhook);
	}
	
	public String getWebhookData(String webhook) throws Exception{
		String response = ApiClient.get(webhook);
		return response;
	}
	
	private void setWebhook(String webhook) throws Exception{
		String response = getWebhookData(webhook);
		
		if (response == null) {
			throw new Exception("Webhook no válida");
		}
		this.webhook = webhook;
		JsonObject root = JsonUtils.parse(response);
		String nombre = JsonUtils.getString(root, "name");
		this.name = nombre;
		String id = JsonUtils.getString(root, "id");
		this.id = Long.parseLong(id);
	}

	// Método extra creado con IA: Intercepté la petición y le dije a gemini que la replicara en Java.
	public void sendMessageAsFile(String msg, String filename) throws Exception {
	    String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();
	    byte[] fileBytes = msg.getBytes(StandardCharsets.UTF_8);

	    @SuppressWarnings("deprecation")
		HttpURLConnection conn = (HttpURLConnection) new URL(this.webhook).openConnection();
	    conn.setRequestMethod("POST");
	    conn.setDoOutput(true);
	    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

	    try (OutputStream os = conn.getOutputStream()) {
	        String header = "--" + boundary + "\r\n" +
	            "Content-Disposition: form-data; name=\"file\"; filename=\"" + filename + "\"\r\n" +
	            "Content-Type: text/plain\r\n\r\n";
	        os.write(header.getBytes());
	        os.write(fileBytes);
	        os.write(("\r\n--" + boundary + "--\r\n").getBytes());
	    }
	    
	    conn.getResponseCode();
	    conn.disconnect();
	}
	
	public void sendMessage(String msg) throws Exception {

	    for (int i = 0; i < msg.length(); i += MAXIMO_CARACTERES) {
	        String parte = msg.substring(i, Math.min(i + MAXIMO_CARACTERES, msg.length()));

	        JsonObject jo = new JsonObject();
	        jo.addProperty("content", parte);
	        ApiClient.post(this.webhook, jo.toString());
	    }
	}

	public String getWebhook() {
		return webhook;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}
	
	
	
}