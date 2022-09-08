package ml.windleaf.easylib.interfaces;

import ml.windleaf.easylib.logging.PluginLogger;
import ml.windleaf.easylib.plugin.EasyLib;
import org.bukkit.event.Listener;

public interface IListener extends Listener {
    EasyLib instance = EasyLib.instance;
    PluginLogger logger = EasyLib.logger;
}
