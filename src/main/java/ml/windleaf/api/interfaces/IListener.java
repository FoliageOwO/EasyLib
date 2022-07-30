package ml.windleaf.api.interfaces;

import ml.windleaf.api.logging.PluginLogger;
import ml.windleaf.api.plugin.Plugin;
import org.bukkit.event.Listener;

public interface IListener extends Listener {
    Plugin instance = Plugin.instance;
    PluginLogger logger = Plugin.logger;
}
