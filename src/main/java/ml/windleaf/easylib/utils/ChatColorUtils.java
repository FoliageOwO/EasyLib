package ml.windleaf.easylib.utils;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

import static org.bukkit.ChatColor.*;

/**
 * 和 `ChatColor` 相关的工具类
 * @see ChatColor
 */
public class ChatColorUtils {
    /**
     * 将文本用 `ChatColor` 染色
     * @param text 文本
     * @param color `ChatColor` 对象
     * @return 染色后的文本
     */
    public static String getTextColored(String text, ChatColor color) {
        return color + text + ChatColor.RESET;
    }

    /**
     * 根据颜色获取相反的颜色
     * @param color 颜色
     * @return 反色
     */
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
