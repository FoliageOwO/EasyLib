package ml.windleaf.easylib.utils;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    @NotNull
    public static Matcher getMatcher(@NotNull @Nls String regex, @NotNull @Nls CharSequence input) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
