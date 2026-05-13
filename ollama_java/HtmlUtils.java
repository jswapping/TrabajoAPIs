package ollama_java;

import java.awt.Desktop;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {
    public static String getTagContent(String html, String tagName) {
        String regex = "<" + tagName + ".*?>(.*?)</" + tagName + ">";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }
    public static void openUrl(String url) throws Exception {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
        }
    }
}
