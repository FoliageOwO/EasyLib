package ml.windleaf.api.interfaces;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICommand {
    String command();
    void onCommand(@NotNull CommandSender sender, @NotNull List<String> args);
}
