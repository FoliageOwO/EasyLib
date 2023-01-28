package ml.windleaf.easylib.logging.format;

import ml.windleaf.easylib.utils.ChatColorUtils;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

/**
 * 日志记录器格式
 * <p>
 * 名字和信息之间的分隔符
 */
@SuppressWarnings("unused")
public enum Separator {
    /**
     * 空格分隔符
     *
     * @example [TestPlugin] hello
     */
    EMPTY(" "),
    /**
     * 短横线分隔符
     *
     * @example TestPlugin - hello
     */
    HYPHEN(" - "),
    /**
     * 竖横线分隔符
     *
     * @example TestPlugin | hello
     */
    DELIMITER(" | "),
    /**
     * 单箭头分隔符
     *
     * @example TestPlugin > hello
     */
    SINGLE_ARROW(" > "),
    /**
     * 三箭头分隔符
     *
     * @example TestPlugin >>> hello
     */
    TRI_ARROW(" >>> "),
    ;

    @NotNull
    @Nls
    private final String content;
    @NotNull
    private final Boolean bold;

    Separator(@NotNull String content) {
        this.content = content;
        this.bold = false;
    }

    Separator(@NotNull String content, @NotNull Boolean bold) {
        this.content = content;
        this.bold = bold;
    }

    /**
     * 获取该格式的结果
     *
     * @return 格式化的文本
     * @example §l |
     * @example >>>
     */
    @NotNull
    @Nls
    public String getContent() {
        return this.bold
                ? ChatColorUtils.getTextColored(this.content, ChatColor.BOLD)
                : this.content;
    }
}
