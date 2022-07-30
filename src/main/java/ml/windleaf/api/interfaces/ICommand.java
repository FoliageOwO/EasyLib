package ml.windleaf.api.interfaces;

import ml.windleaf.api.logging.PluginLogger;
import ml.windleaf.api.plugin.Plugin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICommand {
    Plugin instance = Plugin.instance;
    PluginLogger logger = Plugin.logger;

    String command();
    void onCommand(@NotNull CommandSender sender, @NotNull List<String> args);
}
