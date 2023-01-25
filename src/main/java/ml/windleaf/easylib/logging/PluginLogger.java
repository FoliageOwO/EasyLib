package ml.windleaf.easylib.logging;

import ml.windleaf.easylib.logging.format.NameFormat;
import ml.windleaf.easylib.logging.format.Separator;
import ml.windleaf.easylib.utils.ChatColorUtils;
import ml.windleaf.easylib.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;

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
        Arrays.asList(any).forEach(obj -> sb.append(String.format(" %s ", obj.toString())));
        Bukkit.getConsoleSender().sendMessage(String.format("%s%s%s",
                String.format(this.nameFormat.getContent(), ChatColorUtils.getTextColored(this.name, ChatColorUtils.getReverseColor(this.color))),
                this.separator.getContent(),
                ChatUtils.translateColor(sb.toString().trim()))
        );
    }
}
