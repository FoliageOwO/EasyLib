package ml.windleaf.api.logging.format;

import org.bukkit.ChatColor;

public enum NameFormat {
    EMPTY("{}"),
    EMPTY_BOLD("{}"),
    SQUARE_BRACKETS("[{}]"),
    ;

    public final String content;
    public final Boolean bold;

    NameFormat(String content) {
        this.content = content;
        this.bold = false;
    }

    NameFormat(String content, Boolean bold) {
        this.content = content;
        this.bold = bold;
    }
}
