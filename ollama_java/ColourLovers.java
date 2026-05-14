package ollama_java;

import org.w3c.dom.Document;

public class ColourLovers {
	private static final String URL = "https://www.colourlovers.com/api/colors/random";

    public static int getColourId() {
        try {
            String xml = ApiClient.get(URL);
            Document root = XmlUtils.parse(xml);
            
            String content = XmlUtils.getString(XmlUtils.getElement(root, "id"));
            return Integer.valueOf(content);

        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
        
    }
}
