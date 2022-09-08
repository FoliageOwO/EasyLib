package ml.windleaf.api.interfaces;

import ml.windleaf.api.logging.PluginLogger;
import ml.windleaf.api.plugin.EasyLib;
import org.bukkit.event.Listener;

public interface IListener extends Listener {
    EasyLib instance = EasyLib.instance;
    PluginLogger logger = EasyLib.logger;
}
