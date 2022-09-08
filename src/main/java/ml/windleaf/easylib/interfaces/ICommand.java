package ml.windleaf.easylib.interfaces;

import ml.windleaf.easylib.logging.PluginLogger;
import ml.windleaf.easylib.plugin.EasyLib;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICommand {
    EasyLib instance = EasyLib.instance;
    PluginLogger logger = EasyLib.logger;

    void onCommand(@NotNull CommandSender sender, @NotNull List<String> args);
    default List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
