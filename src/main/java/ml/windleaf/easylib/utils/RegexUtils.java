package ml.windleaf.easylib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static Matcher getMatcher(String regex, CharSequence input) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
