package ml.windleaf.api.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableMap;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.*;

public class ChatColorUtil {
    public static String getTextColored(String text, ChatColor color) {
        return color + text + ChatColor.RESET;
    }

    public static ChatColor getReverseColor(ChatColor color) {
        Map<ChatColor, ChatColor> mapping = ImmutableMap.of(
                BLUE,         DARK_BLUE,
                GREEN,        DARK_GREEN,
                RED,          DARK_RED,
                AQUA,         DARK_AQUA,
                LIGHT_PURPLE, DARK_PURPLE,
                YELLOW,       GOLD,
                WHITE,        GRAY,
                GRAY,         DARK_GRAY,
                DARK_GRAY,    BLACK
        );
        Map<ChatColor, ChatColor> reversedMapping = mapping.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        Map<?, ?> map = mapping.containsKey(color) ? mapping : reversedMapping;
        if (map.containsKey(color)) {
            return mapping.getOrDefault(color, color);
        } else {
            return BLACK;
        }
    }
}
