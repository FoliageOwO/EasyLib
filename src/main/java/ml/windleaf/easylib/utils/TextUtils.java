package ml.windleaf.easylib.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * 和文本、颜色相关的工具类
 */
@SuppressWarnings("unused")
public class TextUtils {
    /**
     * 将带特殊颜色代码的文本转为带颜色代码的文本
     * <p>换行
     * 例如："#RED#你输了！" -> "§c你输了！"
     *
     * @param string 带特殊颜色代码的文本
     * @return 转换后的文本
     */
    @NotNull
    @Nls
    public static String translateColor(@NotNull @Nls String string) {
        String result = string;
        Matcher matcher = RegexUtils.getMatcher("#[A-Z_-]+#", result);
        while (matcher.find()) {
            String code = matcher.group();
            result = result.replaceFirst(
                    code,
                    "§" + ChatColor.valueOf(code.replaceAll("#", "")).getChar()
            );
        }
        return result;
    }

    /**
     * 将带特殊颜色代码的文本转为带颜色代码的文本 (带占位符)
     * <p>
     * 例如："#RED#你输了！" -> "§c你输了！"
     *
     * @param string 带特殊颜色代码的文本
     * @param args   占位符替换
     * @return 转换后的文本
     */
    @NotNull
    @Nls
    public static String translateColor(@NotNull @Nls String string, @Nullable Object... args) {
        return translateColor(safeFormat(string, args));
    }

    /**
     * 全服公告消息(带占位符)
     *
     * @param message 要公告的消息
     * @param args    占位符替换
     */
    public static void broadcast(@NotNull @Nls String message, @Nullable Object... args) {
        Bukkit.getOnlinePlayers().forEach(it -> PlayerUtils.send(it, message, args));
    }

    /**
     * 安全格式化文本, 将为 `null` 的参数化为 "null" 字符串
     *
     * @param string 要格式化的文本
     * @param args   参数
     * @return 格式化后的文本
     */
    @NotNull
    public static String safeFormat(@NotNull String string, @Nullable Object... args) {
        return String.format(string, Arrays.stream(args).map(arg -> arg == null ? "null" : arg.toString()));
    }
}
