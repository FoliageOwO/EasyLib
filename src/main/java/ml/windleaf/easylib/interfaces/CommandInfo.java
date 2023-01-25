package ml.windleaf.easylib.interfaces;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
    String[] value();

    String permission() default "";
}
