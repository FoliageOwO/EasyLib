package ml.windleaf.api.register;

import ml.windleaf.api.logging.PluginLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public record RegisterManager(JavaPlugin plugin) {
    private static final PluginLogger logger = new PluginLogger("PlugApi");

    public void register(Listener listener) {
        logger.logConsole("Registering listener => %s".formatted(listener.getClass().getSimpleName()));
        Bukkit.getServer().getPluginManager().registerEvents(listener, this.plugin);
    }

    public void register(String cmd, CommandExecutor executor) {
        Objects.requireNonNull(this.plugin.getCommand(cmd)).setExecutor(executor);
    }
}
