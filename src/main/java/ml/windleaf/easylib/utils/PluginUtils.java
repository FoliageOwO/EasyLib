package ml.windleaf.easylib.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static ml.windleaf.easylib.EasyLibPlugin.instance;

/**
 * 和插件相关的工具类
 */
@SuppressWarnings({"unchecked", "unused"})
public class PluginUtils {
    /**
     * 调用事件
     *
     * @param event 事件
     */
    public static void callEvent(@NotNull Event event) {
        Bukkit.getScheduler().runTask(instance, () -> instance.getServer().getPluginManager().callEvent(event));
    }

    public static void registerEvent(@NotNull Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, instance);
    }

    /**
     * 根据所给的条件在集合中找到对应的元素
     *
     * @param collection 集合
     * @param predicate  判断匿名函数
     * @param <T>        元素泛型
     * @return 元素，不存在则为 `null`
     */
    @Nullable
    public static <T> T find(@NotNull Collection<T> collection, @NotNull Predicate<T> predicate) {
        AtomicReference<T> result = new AtomicReference<>();
        collection.forEach(any -> {
            if (predicate.test(any)) result.set(any);
        });
        return result.get();
    }

    /**
     * 获取配置对象
     *
     * @param path         配置路径
     * @param defaultValue 默认值
     * @param <T>          对象泛型
     * @return 获取到的配置对象
     */
    @NotNull
    public static <T> T getConfig(@NotNull @Nls String path, @NotNull T defaultValue) {
        T result;
        try {
            result = (T) instance.getConfig().get(path);
        } catch (Exception ignore) {
            result = defaultValue;
        }
        return result == null ? defaultValue : result;
    }
}
