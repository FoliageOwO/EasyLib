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

    /**
     * 插件的 `GitHub` 地址, 用于自动更新
     * <p>
     * 如果不填则表示不自动更新
     * <p>
     * 如果启用, `GitHub` 的仓库必须要使用 `1.2.0` 类似的版本发布版本
     *
     * @exmaple WindLeaf233/ExampleMod
     */
    @NotNull
    @Nls
    String repository() default "";
}
