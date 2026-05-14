package ollama_java;

import java.io.InputStream;

public class FileUtils {

    public static String readResource(String path) throws Exception {
        InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(path);
        return new String(is.readAllBytes());
    }
}
