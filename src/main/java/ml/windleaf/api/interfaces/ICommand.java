package ml.windleaf.api.interfaces;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICommand {
    String command();
    boolean onCommand(@NotNull CommandSender sender, @NotNull List<String> args);
}
