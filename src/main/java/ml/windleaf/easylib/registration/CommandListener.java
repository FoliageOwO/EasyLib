package ml.windleaf.easylib.registration;

import com.google.common.collect.Maps;
import ml.windleaf.easylib.interfaces.CommandInfo;
import ml.windleaf.easylib.interfaces.ICommand;
import ml.windleaf.easylib.utils.PluginUtils;
import ml.windleaf.easylib.utils.RegexUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.*;
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
            if (result != null) e.setCompletions(result);
        }
    }

    /**
     * 将命令交给命令对应的 `ICommand` 实例对象处理
     *
     * @param string 命令字符串
     * @param sender 发送命令的实体
     */
    private void handleCommand(@NotNull @Nls String string, @NotNull CommandSender sender) {
        CommandParserResult result = parseCommand(string);
        if (result != null) {
            // 获取 `ICommand` 实例对象和 `CommandInfo` 对象
            ICommand instance = RegistrationManager.commands.get(result.command);
            if (instance != null) {
                CommandInfo info = instance.getClass().getAnnotation(CommandInfo.class);
                // 判断权限并执行
                String permission = info.permission();
                if (sender.hasPermission(permission) || permission.equals("")) {
                    parseOnCommand(result.args, instance, sender);
                }
            }
        }
    }

    /**
     * 解析 `ICommand` 对象的特殊 `onCommand` 方法
     *
     * @param args     参数列表
     * @param instance `ICommand` 对象
     * @param sender   命令发送的实体
     */
    private void parseOnCommand(@NotNull List<String> args, @NotNull ICommand instance, @NotNull CommandSender sender) {
        Method[] methods = instance.getClass().getDeclaredMethods();
        boolean triggered = false;
        boolean error = false;
        for (Method method : methods) {
            if (method.getName().equals("onCommand")) {
                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length > 2) {
                    // 优先调用特殊处理方法
                    triggered = true;
                    try {
                        List<Object> specialArgs = parseArgs(args, Arrays.asList(paramTypes));
                        if (specialArgs != null) {
                            method.invoke(instance, specialArgs.toArray());
                            error = false;
                        }
                    } catch (Exception ignore) {
                        error = true;
                    }
                }
            }
        }
        // 如果特殊处理方法出错 则用普通处理方法
        if (triggered && error) instance.onCommand(sender, args);
    }

    /**
     * 根据特殊 `onCommand` 方法的参数类型解析参数为类型对象
     *
     * @param args       参数列表
     * @param paramTypes 方法的参数类型列表
     * @return 解析后的类型对象列表
     */
    @Nullable
    private List<Object> parseArgs(@NotNull List<String> args, @NotNull List<Class<?>> paramTypes) {
        Set<String> pArgs = new HashSet<>(args);
        if (args.size() != paramTypes.size() || pArgs.size() != args.size()) return null;
        Map<String, Class<?>> kv = Maps.asMap(pArgs, s -> {
            int index = args.indexOf(s);
            return paramTypes.get(index);
        });
        // 开始匹配
        List<Object> result = new ArrayList<>();
        kv.forEach((arg, type) -> {
            try {
                result.add(new HashMap<Class<?>, Object>() {{
                    put(String.class, arg);
                    put(Character.class, arg.length() == 1 ? arg.toCharArray()[0] : null);
                    put(Integer.class, Integer.parseInt(arg));
                    put(Float.class, Float.parseFloat(arg));
                    put(Boolean.class, Boolean.parseBoolean(arg));
                    put(List.class, Arrays.asList(arg.split(",")));
                    put(ArrayList.class, Arrays.asList(arg.split(",")));
                    put(Player.class, Bukkit.getPlayer(arg));
                    put(World.class, Bukkit.getWorld(arg));
                }}.get(type));
            } catch (Exception ignore) {
                result.add(null);
            }
        });
        return result;
    }

    /**
     * 将命令字符串解析为 `CommandParserResult` 对象
     *
     * @param string 命令字符串
     * @return 解析后的 `CommandParserResult` 对象
     * @see CommandParserResult
     */
    @Nullable
    private CommandParserResult parseCommand(@NotNull @Nls String string) {
        String command;
        // 匹配命令文本
        Matcher commandMatcher = RegexUtils.getMatcher("\\/?[a-zA-Z0-9_-]+", string);
        if (commandMatcher.find()) {
            command = commandMatcher.group().trim().replaceFirst("/", "");
            string = string.replace(command, "").trim();
        } else return null;
        // 匹配带引号的参数 并在空格之间加入特殊字符 以便分割
        char c = '\u00AD';
        Matcher quotedArgsMatcher = RegexUtils.getMatcher("\"[a-zA-Z0-9_-]+\"", string);
        while (quotedArgsMatcher.find()) {
            String group = quotedArgsMatcher.group().trim();
            String copy = group;
            copy = copy.replaceAll("\"", "");
            copy = copy.replace(' ', c);
            string = string.replace(group, copy).trim();
        }
        // 匹配所有参数
        ArrayList<String> split = Arrays.stream(string.split(" ")).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> args = split.stream().map(s -> s.trim().replace(c, ' ')).collect(Collectors.toCollection(ArrayList::new));
        return new CommandParserResult(command, args);
    }


    private static class CommandParserResult {
        /**
         * 命令文本
         *
         * @example command
         */
        @NotNull
        @Nls
        public final String command;

        /**
         * 命令参数
         *
         * @example ["hello", "world", "ni hao"]
         */
        @NotNull
        public final ArrayList<String> args;

        public CommandParserResult(@NotNull @Nls String command, @NotNull ArrayList<String> args) {
            this.command = command;
            this.args = args;
        }
    }
}
