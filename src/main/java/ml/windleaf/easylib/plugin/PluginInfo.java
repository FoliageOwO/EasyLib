package ml.windleaf.easylib.plugin;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginInfo {
    /**
     * 插件包名
     *
     * @example ml.windleaf.testplugin
     */
    @NotNull
    @Nls
    String packagePath();

    /**
     * 插件版本
     *
     * @example 1.0-SNAPSHOT
     */
    @NotNull
    @Nls
    String version();

    /**
     * 插件名字的颜色
     *
     * @example ChatColor.BLUE
     * @default ChatColor.GRAY
     */
    @NotNull
    ChatColor loggerColor() default ChatColor.GRAY;
}
