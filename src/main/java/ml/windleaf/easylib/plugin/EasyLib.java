package ml.windleaf.easylib.plugin;

import ml.windleaf.easylib.logging.PluginLogger;
import ml.windleaf.easylib.register.RegisterManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 基本插件接口
 */
public abstract class EasyLib extends JavaPlugin {
    /**
     * 插件版本
     * @example 1.0.0-SNAPSHOT
     * @default null
     */
    protected String version = null;

    /**
     * 插件包名
     * @example ml.windleaf.testplugin
     * @default null
     */
    protected String packagePath = null;

    /**
     * 插件名字的颜色
     * @example {@link ChatColor.BLUE}
     * @default {@link ChatColor.GRAY}
     */
    protected ChatColor loggerColor = ChatColor.GRAY;

    /**
     * 插件的日志记录器
     */
    public static PluginLogger logger;

    /**
     * 插件实例对象
     */
    public static EasyLib instance;

    /**
     * 子类自定义配置
     */
    protected abstract void superConfig();

    @Override
    public final void onLoad() {
        this.superConfig();
        // 必须执行 `superConfig` 方法，否则无法启动
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
        try {
            getConfig().options().copyDefaults();
            saveDefaultConfig();
        } catch (IllegalArgumentException ignore) {}
        new RegisterManager(this.packagePath);
    }
}
