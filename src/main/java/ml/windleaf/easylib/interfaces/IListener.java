package ml.windleaf.easylib.interfaces;

import ml.windleaf.easylib.logging.PluginLogger;
import ml.windleaf.easylib.plugin.EasyLib;
import org.bukkit.event.Listener;

/**
 * 基本监听器接口
 */
public interface IListener extends Listener {
    /**
     * 插件实例对象，可直接在子类中调用
     */
    EasyLib instance = EasyLib.instance;

    /**
     * 插件的日志记录器，可直接在子类中调用
     */
    PluginLogger logger = EasyLib.logger;
}
