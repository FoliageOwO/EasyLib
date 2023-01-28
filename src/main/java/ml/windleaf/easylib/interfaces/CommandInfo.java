package ml.windleaf.easylib.interfaces;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

/**
 * 命令信息，用于标注命令的文本和权限等
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
    /**
     * 命令文本，可以多个作为别名
     *
     * @example "reload"
     * @example {"reload", "rld"}
     */
    @NotNull
    @Nls
    String[] value();

    /**
     * 命令的权限
     *
     * @default ""
     * @example "plugin.reload"
     */
    @NotNull
    @Nls
    String permission() default "";
}
