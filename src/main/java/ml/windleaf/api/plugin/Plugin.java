package ml.windleaf.api.plugin;

import ml.windleaf.api.logging.PluginLogger;
import ml.windleaf.api.register.RegisterManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Plugin extends JavaPlugin {
    protected String version;
    protected String packagePath;
    protected ChatColor loggerColor = ChatColor.GRAY;
    public static PluginLogger logger;

    protected abstract void superConfig();

    @Override
    public final void onLoad() {
        this.superConfig();
    }

    @Override
    public void onEnable() {
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
