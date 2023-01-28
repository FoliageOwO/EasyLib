package ml.windleaf.easylib.plugin;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

/**
 * 插件的 `MOTD`
 * <p>
 * 如果该注解被启用，启用插件时设置的文本会被输出
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MOTD {
    /**
     * `MOTD` 文本，可多行
     */
    @NotNull
    @Nls
    String[] value();
}
