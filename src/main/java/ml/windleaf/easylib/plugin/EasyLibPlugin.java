package ml.windleaf.easylib.plugin;

import ml.windleaf.easylib.logging.PluginLogger;
import ml.windleaf.easylib.registration.RegistrationManager;
import ml.windleaf.easylib.utils.PluginUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

/**
 * 基本插件接口
 */
public abstract class EasyLibPlugin extends JavaPlugin {
    /**
     * 插件的日志记录器
     */
    public static PluginLogger pLogger;

    /**
     * 插件实例对象
     */
    public static EasyLibPlugin instance;

    /**
     * 插件更新目录
     */
    public static File updateFolder = new File(PluginUtils.getWorkingPath(), "update");

    /**
     * 插件额外加载逻辑
     */
    protected abstract void onPluginLoad();

    /**
     * 插件额外卸载逻辑
     */
    protected abstract void onPluginUnload();

    @Override
    public void onEnable() {
        instance = this;
        PluginInfo info = getClass().getAnnotation(PluginInfo.class);
        pLogger = new PluginLogger(getName());
        if (!updateFolder.exists()) updateFolder.mkdir();
        if (info != null) {
            pLogger.setLoggerColor(info.loggerColor());
            PluginUtils.checkUpdate(info.version(), info.repository());
            MOTD motd = getClass().getAnnotation(MOTD.class);
            if (motd != null) Arrays.stream(motd.value()).forEach(pLogger::logConsole);
            try {
                getConfig().options().copyDefaults();
                saveDefaultConfig();
            } catch (IllegalArgumentException ignore) {
            }
            RegistrationManager.init(info.packagePath());
        } else {
            pLogger.logConsole("#RED#No plugin info found, please check the annotation!");
            throw new IllegalArgumentException();
        }
        onPluginLoad();
    }

    @Override
    public void onDisable() {
        File[] files = updateFolder.listFiles();
        if (files != null) Arrays.stream(files).forEach(File::delete);
    }
}
