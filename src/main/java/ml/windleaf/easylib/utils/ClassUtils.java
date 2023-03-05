package ml.windleaf.easylib.utils;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static ml.windleaf.easylib.plugin.EasyLibPlugin.pLogger;
import static org.reflections.scanners.Scanners.SubTypes;

/**
 * 和类相关的工具类
 */
@SuppressWarnings("unchecked")
public class ClassUtils {
    /**
     * 获取指定包下的所有指定类的子类
     *
     * @param clazz       要获取的类
     * @param packagePath 包路径
     * @param <T>         类泛型
     * @return 子类列表
     */
    @NotNull
    public static <T> ArrayList<Class<? extends T>> getSubClasses(@NotNull Class<T> clazz, @NotNull @Nls String packagePath) {
        Reflections reflections = new Reflections(packagePath);
        return reflections.get(SubTypes.of(clazz).asClass())
                .stream().map(c -> (Class<? extends T>) c)
                .distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 根据指定类初始化对象实例
     *
     * @param clazz 类
     * @param args  参数，可为空
     * @param <T>   对象泛型
     * @return 初始化后的对象实例
     */
    @Nullable
    public static <T> T newInstance(@NotNull Class<T> clazz, @NotNull Object... args) {
        try {
            if (args.length == 0) return clazz.newInstance();
            else {
                Constructor<T> constructor = clazz.getDeclaredConstructor(
                        (Class<?>[]) Arrays.stream(args).map(o -> (Class<?>) o.getClass()).toArray()
                );
                return constructor.newInstance(args);
            }
        } catch (Exception e) {
            pLogger.logConsole("#RED#Internal Error: Could not create the instance of [", clazz, "]:", e.getMessage());
        }
        return null;
    }
}
