package ml.windleaf.api.interfaces;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICommand extends CommandExecutor {
    boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args);
}
