package ml.windleaf.easylib.registration;

import ml.windleaf.easylib.interfaces.CommandInfo;
import ml.windleaf.easylib.interfaces.ICommand;
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

/**
 * 插件第三方命令监听器
 * <p>
 * 用于解决原版命令监听的缺点
 */
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
        RegistrationManager.commands.keySet().forEach(cmd -> {
            if (Objects.equals(command, cmd)) {
                List<String> result = RegistrationManager.commands.get(cmd).onTabComplete(e.getSender(), list);
                e.setCompletions(result);
            }
        });
    }

    /**
     * 将命令交给命令对应的 `ICommand` 实例对象处理
     * @param args 命令参数
     * @param sender 发送命令的实体
     */
    private void handleCommand(@NotNull List<String> args, @NotNull CommandSender sender) {
        // 获取命令文本
        String cmd = args.get(0);
        args.remove(0);
        // 获取 `ICommand` 实例对象和 `CommandInfo` 对象
        ICommand instance = RegistrationManager.commands.get(cmd);
        if (instance != null) {
            CommandInfo info = instance.getClass().getAnnotation(CommandInfo.class);
            // 判断权限并执行
            String permission = info.permission();
            if (sender.hasPermission(permission) || permission.equals("")) {
                RegistrationManager.commands.get(cmd).onCommand(sender, args);
            }
        }
    }
}
