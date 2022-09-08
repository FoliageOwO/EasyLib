package ml.windleaf.api.register;

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
        boolean handle = handleCommand(list, e.getPlayer());
        if (handle) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommandEvent(@NotNull ServerCommandEvent e) {
        List<String> list = new ArrayList<>(Arrays.asList(e.getCommand().split(" ")));
        boolean handle = handleCommand(list, e.getSender());
        if (handle) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabCompleteEvent(@NotNull TabCompleteEvent e) {
        String[] list = e.getCompletions().toArray(new String[0]);
        String command = list[0];
        for (String cmd : RegisterManager.commands.keySet()) {
            if (Objects.equals(command, cmd)) {
                List<String> result = RegisterManager.commands.get(cmd).onTabComplete(e.getSender(), list);
                e.setCompletions(result);
            }
        }
    }

    private boolean handleCommand(@NotNull List<String> list, @NotNull CommandSender sender) {
        String command = list.get(0);
        list.remove(0);
        for (String cmd : RegisterManager.commands.keySet()) {
            if (Objects.equals(command, cmd)) {
                RegisterManager.commands.get(cmd).onCommand(sender, list);
                return true;
            }
        }
        return false;
    }
}
