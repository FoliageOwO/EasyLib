package ml.windleaf.api.logging.format;

import ml.windleaf.api.utils.ChatColorUtil;
import org.bukkit.ChatColor;

public enum NameFormat {
    EMPTY("{}"),
    SQUARE_BRACKETS("[{}]"),
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

    public String getContent() {
        return this.bold
                ? ChatColorUtil.getTextColored(this.content, ChatColor.BOLD)
                : this.content;
    }
}
