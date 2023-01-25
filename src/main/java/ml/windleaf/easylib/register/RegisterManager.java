package ml.windleaf.easylib.register;

import ml.windleaf.easylib.interfaces.CommandInfo;
import ml.windleaf.easylib.interfaces.ICommand;
import ml.windleaf.easylib.interfaces.IListener;
import ml.windleaf.easylib.utils.ClassUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RegisterManager {
    private final Plugin plugin;
    public static final Map<String, ICommand> commands = new HashMap<>();

    public RegisterManager(JavaPlugin plugin, String packagePath) {
        this.plugin = plugin;

        ClassUtil.getSubClasses(ICommand.class, packagePath).forEach(command -> {
            ICommand instance = ClassUtil.newInstance(command);
            CommandInfo info = command.getAnnotation(CommandInfo.class);
            if (info != null) {
                Arrays.asList(info.value()).forEach(cmd -> RegisterManager.commands.put(cmd, instance));
            }
        });

        ClassUtil.getSubClasses(IListener.class, packagePath).forEach(listener -> {
            IListener instance = ClassUtil.newInstance(listener);
            this.registerEvent(instance);
        });

        this.registerEvent(new CommandListener());
    }

    private void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, this.plugin);
    }
}
