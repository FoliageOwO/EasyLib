package ml.windleaf.api.plugin;

import ml.windleaf.api.logging.PluginLogger;
import ml.windleaf.api.register.RegisterManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Plugin extends JavaPlugin {
    public static PluginLogger logger;

    public abstract Plugin getInstance();

    public String getPluginName() {
        return this.getName();
    }

    public abstract String getPluginVersion();

    public ChatColor getLoggerColor() {
        return ChatColor.AQUA;
    }

    public abstract String getPackagePath();

    public PluginLogger getPluginLogger() {
        return logger;
    }

    @Override
    public void onEnable() {
        logger = new PluginLogger(this.getPluginName());
        logger.setLoggerColor(this.getLoggerColor());
        logger.logConsole(String.format("&aEnabling &b%s &e[v%s]&a...", this.getPluginName(), this.getPluginVersion()));
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        new RegisterManager(this.getInstance(), this.getPackagePath());
    }

    @Override
    public void onDisable() {
        logger.logConsole(String.format("&aDisabling &b%s&a...", this.getPluginName()));
    }
}
