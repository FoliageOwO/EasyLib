package ml.windleaf.api.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ColoredTextUtil {
    private static final Map<String, String> mapping = new HashMap<>();

    static {
        mapping.put("&", "§");
    }

    public static String color(String text) {
        AtomicReference<String> string = new AtomicReference<>(text);
        mapping.keySet().forEach(key -> string.set(text.replaceAll(key, mapping.get(key))));
        return string.get();
    }
}
