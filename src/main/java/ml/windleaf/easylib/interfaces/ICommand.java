package ml.windleaf.easylib.interfaces;

import ml.windleaf.easylib.logging.PluginLogger;
import ml.windleaf.easylib.EasyLib;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 基本命令接口
 */
public interface ICommand {
    /**
     * 插件实例对象，可直接在子类中调用
     */
    EasyLib instance = EasyLib.instance;

    /**
     * 插件的日志记录器，可直接在子类中调用
     */
    PluginLogger logger = EasyLib.logger;

    /**
     * 普通命令处理方法
     * @param sender 发送命令的实体
     * @param args 命令的参数
     */
    default void onCommand(@NotNull CommandSender sender, @NotNull List<String> args) {};

    /**
     * 命令补全方法
     * @param sender 发送命令的实体
     * @param args 命令的参数
     * @return 补全的参数
     */
    default List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
