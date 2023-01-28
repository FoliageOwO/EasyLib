package ml.windleaf.easylib.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * 和玩家相关的工具类
 */
@SuppressWarnings("unused")
public class PlayerUtils {
    /**
     * 给玩家发送信息
     *
     * @param sender  玩家或控制台
     * @param message 要发送的消息
     */
    public static void send(@NotNull CommandSender sender, @NotNull @Nls String message) {
        StringBuilder sb = new StringBuilder();
        Collections.singletonList(message).forEach(it -> sb.append(it).append(" "));
        sender.sendMessage(TextUtils.translateColor(sb.toString()));
    }

    /**
     * 给玩家发送消息（带占位符)
     *
     * @param sender  玩家或控制台
     * @param message 要发送的信息
     * @param args    占位符替换
     */
    public static void send(@NotNull CommandSender sender, @NotNull @Nls String message, @Nullable Object... args) {
        send(sender, TextUtils.safeFormat(message, args));
    }

    /**
     * 给玩家显示标题
     *
     * @param player   玩家
     * @param title    要显示的标题
     * @param subtitle 要显示的副标题
     */
    public static void showTitle(@NotNull Player player, @NotNull @Nls String title, @NotNull @Nls String subtitle) {
        player.sendTitle(TextUtils.translateColor(title), TextUtils.translateColor(subtitle), 10, 70, 20);
    }

    /**
     * 给玩家显示标题
     *
     * @param player       玩家
     * @param title        标题
     * @param subtitle     副标题
     * @param titleArgs    标题占位符
     * @param subtitleArgs 副标题占位符
     */
    public static void showTitle(@NotNull Player player, @NotNull @Nls String title, @NotNull @Nls String subtitle, @Nullable List<Object> titleArgs, @Nullable List<Object> subtitleArgs) {
        showTitle(player,
                titleArgs != null ? String.format(title, titleArgs) : title,
                subtitleArgs != null ? String.format(subtitle, subtitleArgs) : subtitle);
    }
}
