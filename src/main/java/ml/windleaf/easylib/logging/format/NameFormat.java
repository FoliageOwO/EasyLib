package ml.windleaf.easylib.logging.format;

import ml.windleaf.easylib.utils.ChatColorUtils;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

/**
 * 日志记录器格式
 * <p>
 * 插件的名字
 */
@SuppressWarnings("unused")
public enum NameFormat {
    /**
     * 空的格式
     *
     * @example TestPlugin
     */
    EMPTY("%s"),
    /**
     * 带方括号的格式
     *
     * @example [TestPlugin]
     */
    SQUARE_BRACKETS("[%s]"),
    ;

    @NotNull
    @Nls
    private final String content;
    @NotNull
    private final Boolean bold;

    NameFormat(@NotNull @Nls String content) {
        this.content = content;
        this.bold = false;
    }

    NameFormat(@NotNull @Nls String content, @NotNull Boolean bold) {
        this.content = content;
        this.bold = bold;
    }

    /**
     * 获取该格式的结果
     *
     * @return 格式化的文本
     * @example §lTestPlugin
     * @example [TestPlugin]
     */
    @NotNull
    @Nls
    public String getContent() {
        return this.bold
                ? ChatColorUtils.getTextColored(this.content, ChatColor.BOLD)
                : this.content;
    }
}
