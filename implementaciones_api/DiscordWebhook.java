package implementaciones_api;

import java.util.*;

import com.google.gson.JsonObject;

import ollama_java.ApiClient;
import ollama_java.JsonUtils;

public class DiscordWebhook {
	private String webhook;
	private String name;
	private Long id;
	
	public DiscordWebhook(String webhook) throws Exception{
		setWebhook(webhook);
	}
	
	public String getWebhookData(String webhook) throws Exception{
		String response = ApiClient.get(webhook);
		return response;
	}
	
	// TODO: Corregir esta parte
	
	private void setWebhook(String webhook) throws Exception{
		String response = getWebhookData(webhook);
		
		if (response == null) {
			throw new Exception("Webhook no válida");
		}
		
		JsonObject root = JsonUtils.parse(response);
		String nombre = JsonUtils.getString(root, "name");
		String id = JsonUtils.getString(root, "id");
	}
	
	// TODO: Hacer sendMessage(String msg)
	
	
}
