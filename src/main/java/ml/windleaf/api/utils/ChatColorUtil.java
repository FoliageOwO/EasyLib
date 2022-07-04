package ml.windleaf.api.utils;

import org.bukkit.ChatColor;

public class ChatColorUtil {
    public static String getTextColored(String text, ChatColor color) {
        return color + text + ChatColor.RESET;
    }
}
