package ml.windleaf.api.plugin;

import ml.windleaf.api.logging.PluginLogger;
import ml.windleaf.api.register.RegisterManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Plugin extends JavaPlugin {
    protected String version = null;
    protected String packagePath = null;
    protected ChatColor loggerColor = ChatColor.GRAY;
    public static PluginLogger logger;
    public static Plugin instance;

    protected abstract void superConfig();

    @Override
    public final void onLoad() {
        this.superConfig();
        if (this.version == null || this.packagePath == null) {
            logger.logConsole("&cNo config found, please set version and package in `superConfig` method!");
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        logger = new PluginLogger(this.getName());
        logger.setLoggerColor(this.loggerColor);
        logger.logConsole(String.format("&aEnabling &b%s &e[v%s]&a...", this.getName(), this.version));
        try {
            getConfig().options().copyDefaults();
            saveDefaultConfig();
        } catch (IllegalArgumentException ignore) {}
        new RegisterManager(this, this.packagePath);
    }

    @Override
    public void onDisable() {
        logger.logConsole(String.format("&aDisabling &b%s&a...", this.getName()));
    }
}
