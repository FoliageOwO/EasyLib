package ml.windleaf.easylib.utils;

import org.bukkit.ChatColor;

public class ColoredTextUtil {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
