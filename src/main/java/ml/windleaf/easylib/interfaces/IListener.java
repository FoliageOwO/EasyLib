package ml.windleaf.easylib.interfaces;

import ml.windleaf.easylib.plugin.EasyLibPlugin;
import ml.windleaf.easylib.logging.PluginLogger;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * 基本监听器接口
 */
@SuppressWarnings("unused")
public interface IListener extends Listener {
    /**
     * 插件实例对象，可直接在子类中调用
     */
    @NotNull
    EasyLibPlugin instance = EasyLibPlugin.instance;

    /**
     * 插件的日志记录器，可直接在子类中调用
     */
    @NotNull
    PluginLogger logger = EasyLibPlugin.logger;
}
