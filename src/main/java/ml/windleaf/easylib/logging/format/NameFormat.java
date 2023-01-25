package ml.windleaf.easylib.logging.format;

import ml.windleaf.easylib.utils.ChatColorUtils;
import org.bukkit.ChatColor;

/**
 * 日志记录器格式
 * <p>
 * 插件的名字
 */
public enum NameFormat {
    /**
     * 空的格式
     * @example TestPlugin
     */
    EMPTY("%s"),
    /**
     * 带方括号的格式
     * @example [TestPlugin]
     */
    SQUARE_BRACKETS("[%s]"),
    ;

    private final String content;
    private final Boolean bold;

    NameFormat(String content) {
        this.content = content;
        this.bold = false;
    }

    NameFormat(String content, Boolean bold) {
        this.content = content;
        this.bold = bold;
    }

    /**
     * 获取该格式的结果
     * @return 格式化的文本
     * @example §lTestPlugin
     * @example [TestPlugin]
     */
    public String getContent() {
        return this.bold
                ? ChatColorUtils.getTextColored(this.content, ChatColor.BOLD)
                : this.content;
    }
}
