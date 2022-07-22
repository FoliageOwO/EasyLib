package ml.windleaf.api.utils;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ColoredTextUtil {
    private static final Map<String, String> mapping = new HashMap<>(){{
        put("&", "§");
    }};

    public static String color(String text) {
        for (String key : mapping.keySet()) {
            text = text.replaceAll(key, mapping.get(key));
        }
        return text;
    }
}
