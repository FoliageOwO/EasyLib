package ml.windleaf.easylib.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.*;

/**
 * 和 `ChatColor` 相关的工具类
 *
 * @see ChatColor
 */
@SuppressWarnings("unused")
public class ChatColorUtils {
    /**
     * 存放颜色的列表，用于随机颜色生成
     */
    @NotNull
    private static final ArrayList<ChatColor> colors = new ArrayList<>();

    /**
     * 存放颜色代码对应的中文翻译
     */
    @NotNull
    private static final HashMap<ChatColor, String> colorsNameMap = new HashMap<ChatColor, String>() {{
        put(AQUA, "青");
        put(BLACK, "黑");
        put(BLUE, "蓝");
        put(DARK_AQUA, "深青");
        put(DARK_BLUE, "深蓝");
        put(DARK_GRAY, "深灰");
        put(DARK_GREEN, "深绿");
        put(DARK_PURPLE, "深紫");
        put(DARK_RED, "深红");
        put(GOLD, "金");
        put(GRAY, "灰");
        put(GREEN, "绿");
        put(LIGHT_PURPLE, "浅紫");
        put(RED, "红");
        put(WHITE, "白");
        put(YELLOW, "黄");
    }};

    /**
     * 将文本用 `ChatColor` 染色
     *
     * @param text  文本
     * @param color `ChatColor` 对象
     * @return 染色后的文本
     */
    @NotNull
    public static String getTextColored(@NotNull @Nls String text, @NotNull ChatColor color) {
        return color + text + ChatColor.RESET;
    }


    /**
     * 重设颜色
     */
    private static void resetColors() {
        colors.clear();
        colors.addAll(Arrays.stream(values()).filter(colorsNameMap::containsKey).collect(Collectors.toList()));
    }

    /**
     * 随机抽取颜色
     *
     * @return 颜色
     */
    public static ChatColor randomColor() {
        Collections.shuffle(colors);
        Optional<ChatColor> random = colors.stream().findFirst();
        Supplier<ChatColor> getter = random.isPresent() ? random::get : () -> {
            resetColors();
            return randomColor();
        };
        return getter.get();
    }

    /**
     * 将颜色代码转换成中文文字
     *
     * @param color 颜色代码
     * @return 对应中文文字
     */
    public static String getColorName(ChatColor color) {
        return colorsNameMap.getOrDefault(color, "未知");
    }


    /**
     * 根据颜色获取相反的颜色
     *
     * @param color 颜色
     * @return 反色
     */
    @NotNull
    public static ChatColor getReverseColor(@NotNull ChatColor color) {
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
