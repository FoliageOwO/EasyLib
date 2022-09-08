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
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class RegisterManager {
    private final Plugin plugin;
    public static final Map<String, ICommand> commands = new HashMap<>();

    public RegisterManager(JavaPlugin plugin, String packagePath) {
        this.plugin = plugin;
        Consumer<Exception> catchException = e -> { throw new RuntimeException(e); };

        (new ClassUtil<>(ICommand.class)).getClassesBySuperclass(packagePath).forEach(command -> {
            try {
                ICommand instance = ((Class<ICommand>) command).getDeclaredConstructor().newInstance();
                CommandInfo info = command.getAnnotation(CommandInfo.class);
                Arrays.asList(info.value()).forEach(cmd -> RegisterManager.commands.put(cmd, instance));
            } catch (Exception e) { catchException.accept(e); }
        });

        (new ClassUtil<>(IListener.class)).getClassesBySuperclass(packagePath).forEach(listener -> {
            try {
                IListener instance = ((Class<IListener>) listener).getDeclaredConstructor().newInstance();
                this.registerEvent(instance);
            } catch (Exception e) { catchException.accept(e); }
        });

        this.registerEvent(new CommandListener());
    }

    private void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, this.plugin);
    }
}