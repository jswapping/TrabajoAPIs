package ollama_java;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.ByteArrayInputStream;

public class XmlUtils {

    public static Document parse(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

    public static String getString(Document doc, String tagName) {
        return doc.getElementsByTagName(tagName).item(0).getTextContent();
    }

    public static Element getElement(Document doc, String tagName) {
        NodeList list = doc.getElementsByTagName(tagName);
        if (list.getLength() > 0) {
            return (Element) list.item(0);
        }
        return null;
    }

    public static Element getElement(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() > 0) {
            return (Element) list.item(0);
        }
        return null;
    }
    
    public static String getString(Element el) {
        if (el != null) {
            return el.getTextContent().trim();
        }
        return "";
    }
}
