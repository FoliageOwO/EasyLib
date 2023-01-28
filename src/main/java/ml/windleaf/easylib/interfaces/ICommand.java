package ml.windleaf.easylib.interfaces;

import ml.windleaf.easylib.plugin.EasyLibPlugin;
import ml.windleaf.easylib.logging.PluginLogger;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 基本命令接口
 */
@SuppressWarnings("unused")
public interface ICommand {
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

    /**
     * 普通命令处理方法
     *
     * @param sender 发送命令的实体
     * @param args   命令的参数
     */
    default void onCommand(CommandSender sender, List<String> args) {
    }

    /**
     * 命令补全方法
     *
     * @param sender 发送命令的实体
     * @param args   命令的参数
     * @return 补全的参数
     */
    @Nullable
    default List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
