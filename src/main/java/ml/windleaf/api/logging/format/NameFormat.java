package ml.windleaf.api.logging.format;

import org.bukkit.ChatColor;

public enum NameFormat {
    EMPTY("{}"),
    EMPTY_BOLD(ChatColor.BOLD + "" + ChatColor.RESET),
    SQUARE_BRACKETS("[{}]"),
    SQUARE_BRACKETS_BOLD(ChatColor.BOLD + "[{}]" + ChatColor.RESET)
    ;

    public final String content;

    NameFormat(String content) {
        this.content = content;
    }
}
