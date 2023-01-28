package ml.windleaf.easylib.registration;

import ml.windleaf.easylib.interfaces.CommandInfo;
import ml.windleaf.easylib.interfaces.ICommand;
import ml.windleaf.easylib.utils.PluginUtils;
import ml.windleaf.easylib.utils.RegexUtils;
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
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * 插件第三方命令监听器
 * <p>
 * 用于解决原版命令监听的缺点
 */
public class CommandListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocessEvent(@NotNull PlayerCommandPreprocessEvent e) {
        handleCommand(e.getMessage(), e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommandEvent(@NotNull ServerCommandEvent e) {
        handleCommand(e.getCommand(), e.getSender());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabCompleteEvent(@NotNull TabCompleteEvent e) {
        String[] list = e.getCompletions().toArray(new String[0]);
        String command = list[0];
        String cmd = PluginUtils.find(RegistrationManager.commands.keySet(), it -> Objects.equals(command, it));
        if (cmd != null) {
            List<String> result = RegistrationManager.commands.get(cmd).onTabComplete(e.getSender(), list);
            e.setCompletions(result);
        }
    }

    /**
     * 将命令交给命令对应的 `ICommand` 实例对象处理
     * @param string 命令字符串
     * @param sender 发送命令的实体
     */
    private void handleCommand(@NotNull String string, @NotNull CommandSender sender) {
        CommandParserResult result = parseCommand(string);
        if (result != null) {
            // 获取 `ICommand` 实例对象和 `CommandInfo` 对象
            ICommand instance = RegistrationManager.commands.get(result.command);
            if (instance != null) {
                CommandInfo info = instance.getClass().getAnnotation(CommandInfo.class);
                // 判断权限并执行
                String permission = info.permission();
                if (sender.hasPermission(permission) || permission.equals("")) {
                    instance.onCommand(sender, result.args);
                }
            }
        }
    }

    /**
     * 将命令字符串解析为 `CommandParserResult` 对象
     * @param string 命令字符串
     * @return 解析后的 `CommandParserResult` 对象
     * @see CommandParserResult
     */
    private CommandParserResult parseCommand(String string) {
        // TODO 解析 JSON, Location, Player, World 等
        String command;
        // 匹配命令文本
        Matcher commandMatcher = RegexUtils.getMatcher("\\/?[a-zA-Z0-9_-]+", string);
        if (commandMatcher.find()) {
            command = commandMatcher.group().trim();
            string = string.replace(command, "").trim();
        } else return null;
        // 匹配带引号的参数 并在空格之间加入特殊字符 以便分割
        char c = '\u00AD';
        Matcher quotedArgsMatcher = RegexUtils.getMatcher("\"[a-zA-Z0-9_-]+\"", string);
        while (quotedArgsMatcher.find()) {
            String group = quotedArgsMatcher.group().trim();
            String copy = group;
            copy = copy.replaceAll("\"", "");
            copy = copy.replace(' ' , c);
            string = string.replace(group, copy).trim();
        }
        // 匹配所有参数
        ArrayList<String> split = Arrays.stream(string.split(" ")).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> args = split.stream().map(s -> s.trim().replace(c, ' ')).collect(Collectors.toCollection(ArrayList::new));
        return new CommandParserResult(command, args);
    }


    private static class CommandParserResult {
        public final String command;
        public final ArrayList<String> args;

        public CommandParserResult(String command, ArrayList<String> args) {
            this.command = command;
            this.args = args;
        }
    }
}
