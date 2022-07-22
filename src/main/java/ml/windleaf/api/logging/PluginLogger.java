package ml.windleaf.api.logging;

import ml.windleaf.api.logging.format.NameFormat;
import ml.windleaf.api.logging.format.Separator;
import ml.windleaf.api.utils.ChatColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PluginLogger {
    private final String name;
    private final NameFormat nameFormat;
    private final Separator separator;
    private ChatColor color = ChatColor.WHITE;

    public PluginLogger(String name) {
        this.name = name;
        this.nameFormat = NameFormat.SQUARE_BRACKETS;
        this.separator = Separator.EMPTY;
    }

    public PluginLogger(String name, NameFormat nameFormat, Separator separator) {
        this.name = name;
        this.nameFormat = nameFormat;
        this.separator = separator;
    }

    public void setLoggerColor(ChatColor color) {
        this.color = color;
    }

    public void logConsole(Object... any) {
        StringBuilder sb = new StringBuilder();
        for (Object anyObject : any) {
            sb.append(" %s ".formatted(anyObject.toString()));
        }
        Bukkit.getConsoleSender().sendMessage("%s%s%s".formatted(
                this.nameFormat.getContent().formatted(ChatColorUtil.getTextColored(this.name, ChatColorUtil.getReverseColor(this.color))),
                this.separator.getContent(),
                sb.toString().trim())
        );
    }
}
