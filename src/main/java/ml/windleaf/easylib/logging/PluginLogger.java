package ml.windleaf.easylib.logging;

import ml.windleaf.easylib.logging.format.NameFormat;
import ml.windleaf.easylib.logging.format.Separator;
import ml.windleaf.easylib.utils.ChatColorUtils;
import ml.windleaf.easylib.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * 插件日志记录器
 */
@SuppressWarnings("unused")
public class PluginLogger {
    /**
     * 插件的名字
     *
     * @example TestPlugin
     */
    @NotNull
    @Nls
    private final String name;

    /**
     * 日志记录器格式 | 插件的名字
     *
     * @default NameFormat.SQUARE_BRACKETS
     * @example §lTestPlugin
     * @example [TestPlugin]
     */
    @NotNull
    private final NameFormat nameFormat;

    /**
     * 日志记录器格式 | 名字和信息之间的分隔符
     *
     * @default Separator.EMPTY
     * @example §l |
     * @example >>>
     */
    @NotNull
    private final Separator separator;

    /**
     * 插件的名字颜色
     *
     * @default ChatColor.WHITE
     */
    @NotNull
    private ChatColor color = ChatColor.WHITE;

    /**
     * 初始化一个日志记录器
     *
     * @param name 插件名
     */
    public PluginLogger(@NotNull String name) {
        this.name = name;
        this.nameFormat = NameFormat.SQUARE_BRACKETS;
        this.separator = Separator.EMPTY;
    }

    /**
     * 初始化一个日志记录器，自定义格式
     *
     * @param name       插件名
     * @param nameFormat 日志记录器格式 | 插件的名字
     * @param separator  日志记录器格式 | 名字和信息之间的分隔符
     */
    public PluginLogger(@NotNull String name, @NotNull NameFormat nameFormat, @NotNull Separator separator) {
        this.name = name;
        this.nameFormat = nameFormat;
        this.separator = separator;
    }

    /**
     * 手动设置插件的名字的颜色
     *
     * @param color 颜色
     * @see ChatColor
     */
    public void setLoggerColor(@NotNull ChatColor color) {
        this.color = color;
    }

    /**
     * 向控制台输出消息
     *
     * @param any 消息或任何对象
     */
    public void logConsole(@Nullable Object... any) {
        StringBuilder sb = new StringBuilder();
        Arrays.asList(any).forEach(obj -> sb.append(TextUtils.safeFormat(" %s ", obj)));
        Bukkit.getConsoleSender().sendMessage(String.format("%s%s%s",
                String.format(this.nameFormat.getContent(), ChatColorUtils.getTextColored(this.name, ChatColorUtils.getReverseColor(this.color))),
                this.separator.getContent(),
                TextUtils.translateColor(sb.toString().trim()))
        );
    }
}
