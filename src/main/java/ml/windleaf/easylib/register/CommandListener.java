package ml.windleaf.easylib.register;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocessEvent(@NotNull PlayerCommandPreprocessEvent e) {
        List<String> list = new ArrayList<>(Arrays.asList(StringUtils.removeStart(e.getMessage(), "/").split(" ")));
        handleCommand(list, e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommandEvent(@NotNull ServerCommandEvent e) {
        List<String> list = new ArrayList<>(Arrays.asList(e.getCommand().split(" ")));
        handleCommand(list, e.getSender());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabCompleteEvent(@NotNull TabCompleteEvent e) {
        String[] list = e.getCompletions().toArray(new String[0]);
        String command = list[0];
        RegisterManager.commands.keySet().forEach(cmd -> {
            if (Objects.equals(command, cmd)) {
                List<String> result = RegisterManager.commands.get(cmd).onTabComplete(e.getSender(), list);
                e.setCompletions(result);
            }
        });
    }

    private void handleCommand(@NotNull List<String> list, @NotNull CommandSender sender) {
        String command = list.get(0);
        list.remove(0);
        RegisterManager.commands.keySet().forEach(cmd -> {
            if (Objects.equals(command, cmd)) {
                RegisterManager.commands.get(cmd).onCommand(sender, list);
            }
        });
    }
}
