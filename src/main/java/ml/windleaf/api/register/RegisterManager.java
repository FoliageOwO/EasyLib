package ml.windleaf.api.register;

import ml.windleaf.api.interfaces.ICommand;
import ml.windleaf.api.interfaces.IListener;
import ml.windleaf.api.logging.PluginLogger;
import ml.windleaf.api.utils.ResolverUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class RegisterManager {
    private final Plugin plugin;
    private static final PluginLogger logger = new PluginLogger("PlugApi");
    public static final Map<String, ICommand> commands = new HashMap<>();

    public RegisterManager(JavaPlugin plugin, String packagePath) {
        try {
            this.plugin = plugin;
            ResolverUtil<ICommand> commandResolverUtil = new ResolverUtil<>(ICommand.class);
            ResolverUtil<IListener> listenerResolverUtil = new ResolverUtil<>(IListener.class);
            List<Class<? extends ICommand>> commands = commandResolverUtil.getClassesBySuperclass(packagePath);
            List<Class<? extends IListener>> listeners = listenerResolverUtil.getClassesBySuperclass(packagePath);
            for (Class<? extends ICommand> iCommand : commands) {
                ICommand iC = ((Class<ICommand>) iCommand).getDeclaredConstructor().newInstance();
                RegisterManager.commands.put(iC.command(), iC);
            }
            for (Class<? extends IListener> iListener : listeners) {
                IListener iL = ((Class<IListener>) iListener).getDeclaredConstructor().newInstance();
                this.registerEvent(iL);
            }
            this.registerEvent(new CommandListener());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, this.plugin);
    }
}
