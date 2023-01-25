package ml.windleaf.easylib.logging.format;

import ml.windleaf.easylib.utils.ChatColorUtils;
import org.bukkit.ChatColor;

/**
 * 日志记录器格式
 * <p>
 * 名字和信息之间的分隔符
 */
public enum Separator {
    /**
     * 空格分隔符
     * @example [TestPlugin] hello
     */
    EMPTY(" "),
    /**
     * 短横线分隔符
     * @example TestPlugin - hello
     */
    HYPHEN(" - "),
    /**
     * 竖横线分隔符
     * @example TestPlugin | hello
     */
    DELIMITER(" | "),
    /**
     * 单箭头分隔符
     * @example TestPlugin > hello
     */
    SINGLE_ARROW(" > "),
    /**
     * 三箭头分隔符
     * @example TestPlugin >>> hello
     */
    TRI_ARROW(" >>> "),
    ;

    private final String content;
    private final Boolean bold;

    Separator(String content) {
        this.content = content;
        this.bold = false;
    }

    Separator(String content, Boolean bold) {
        this.content = content;
        this.bold = bold;
    }

    /**
     * 获取该格式的结果
     * @return 格式化的文本
     * @example §l |
     * @example  >>>
     */
    public String getContent() {
        return this.bold
                ? ChatColorUtils.getTextColored(this.content, ChatColor.BOLD)
                : this.content;
    }
}
