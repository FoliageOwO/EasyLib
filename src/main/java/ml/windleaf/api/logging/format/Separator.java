package ml.windleaf.api.logging.format;

import ml.windleaf.api.utils.ChatColorUtil;
import org.bukkit.ChatColor;

public enum Separator {
    EMPTY(" "),
    HYPHEN(" - "),
    DELIMITER(" | "),
    SINGLE_ARROW(" > "),
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

    public String getContent() {
        return this.bold
                ? ChatColorUtil.getTextColored(this.content, ChatColor.BOLD)
                : this.content;
    }
}
