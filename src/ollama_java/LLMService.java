package ollama_java;

import com.google.gson.JsonObject;

public class LLMService {

    private static final String URL = "http://localhost:11434/api/generate";

    public static String preguntar(String prompt) {
    	try {

            JsonObject options = new JsonObject();
            options.addProperty("temperature", 0);
            options.addProperty("top_p", 0.1);

            JsonObject body = new JsonObject();
            body.addProperty("model", "mistral");
            body.addProperty("prompt", prompt);
            body.addProperty("stream", false);
            body.add("options", options);

            String json = ApiClient.post(URL, body.toString());

            JsonObject root = JsonUtils.parse(json);

            return JsonUtils.getString(root, "response");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void contexto(String mensaje) {
        int numContext = 1;
        String prompt = """
	    Clasifica el mensaje del usuario.
	
	    Responde SOLO con un número.
	
	    1 = texto
	    2 = ip
	    3 = color
	
	    Mensaje:
        		""" + mensaje;

        String seleccion = preguntar(prompt).trim();
        System.out.println("------------[DEBUG]------------\n"+seleccion+"\n------------[DEBUG]------------");
        for (char c : seleccion.toCharArray()) {
            if (Character.isDigit(c)) {
                numContext = Character.getNumericValue(c);
            }
        }

        switch (numContext) {
            case 1:
                System.out.println(preguntar(mensaje));
                break;
            case 2:
            	System.out.println("Get IP");
                //System.out.println(IpWhoService.getIP());
                break;
            case 3:
            	System.out.println(ColourLovers.getColourId());
            	break;
            	
            	
        }
    }
}
