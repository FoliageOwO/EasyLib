package ml.windleaf.api.utils;

import java.util.HashMap;
import java.util.Map;

public class ColoredTextUtil {
    private static final Map<String, String> mapping = new HashMap<>();

    static {
        mapping.put("&", "§");
    }

    public static String color(String text) {
        for (String key : mapping.keySet()) {
            text = text.replaceAll(key, mapping.get(key));
        }
        return text;
    }
}
