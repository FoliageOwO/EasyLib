package ml.windleaf.easylib.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static ml.windleaf.easylib.plugin.EasyLib.instance;

public class PluginUtils {
    /**
     * 调用事件
     * @param event 事件
     */
    public static void callEvent(Event event) {
        Bukkit.getScheduler().runTask(instance, () -> instance.getServer().getPluginManager().callEvent(event));
    }

    /**
     * 根据所给的条件在集合中找到对应的元素
     * @param collection 集合
     * @param function 判断匿名函数
     * @return 元素，不存在则为 `null`
     * @param <T> 元素泛型
     */
    public static <T> T find(Collection<T> collection, Function<T, Boolean> function) {
        AtomicReference<T> result = new AtomicReference<>();
        collection.forEach(any -> {
            if (function.apply(any)) result.set(any);
        });
        return result.get();
    }
}
