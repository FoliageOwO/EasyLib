package ml.windleaf.easylib.register;

import ml.windleaf.easylib.interfaces.CommandInfo;
import ml.windleaf.easylib.interfaces.ICommand;
import ml.windleaf.easylib.utils.PluginUtils;
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
        // TODO 优化带空格的参数
        //  如 `/cmd "ni hao"` 需要解析为 ["ni hao"] 单参数而不是 ["ni", "hao"]
        List<String> args = new ArrayList<>(Arrays.asList(StringUtils.removeStart(e.getMessage(), "/").split(" ")));
        handleCommand(args, e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommandEvent(@NotNull ServerCommandEvent e) {
        List<String> args = new ArrayList<>(Arrays.asList(e.getCommand().split(" ")));
        handleCommand(args, e.getSender());
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

    private void handleCommand(@NotNull List<String> args, @NotNull CommandSender sender) {
        String command = args.get(0);
        args.remove(0);
        String cmd = PluginUtils.find(RegisterManager.commands.keySet(), c -> Objects.equals(c, command));
        ICommand instance = RegisterManager.commands.get(cmd);
        CommandInfo info = instance.getClass().getAnnotation(CommandInfo.class);
        String permission = info.permission();
        if (sender.hasPermission(permission) || permission.equals("")) {
            RegisterManager.commands.get(cmd).onCommand(sender, args);
        }
    }
}
