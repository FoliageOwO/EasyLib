package ml.windleaf.easylib.utils;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class ChatColorUtils {
    public static String getTextColored(String text, ChatColor color) {
        return color + text + ChatColor.RESET;
    }

    public static ChatColor getReverseColor(ChatColor color) {
        List<ChatColor> list1 = Arrays.asList(
                BLUE,
                GREEN,
                RED,
                AQUA,
                LIGHT_PURPLE,
                YELLOW,
                WHITE,
                GRAY,
                DARK_GRAY);
        List<ChatColor> list2 = Arrays.asList(
                DARK_BLUE,
                DARK_GREEN,
                DARK_RED,
                DARK_AQUA,
                DARK_PURPLE,
                GOLD,
                GRAY,
                DARK_GRAY,
                BLACK
        );
        if (list1.contains(color)) {
            return list2.get(list1.indexOf(color));
        } else if (list2.contains(color)) {
            return list1.get(list2.indexOf(color));
        } else {
            return color;
        }
    }
}
