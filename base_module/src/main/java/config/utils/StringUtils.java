package config.utils;

import com.google.common.base.CharMatcher;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static Pattern pattern = Pattern.compile("\\r");
    private static Pattern newLinePattern = Pattern.compile("\\n");
    private static Pattern whitespacePattern = Pattern.compile("[\\s ]+");
    private static Map<String, Pattern> patterns = new HashMap();


    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String simplifyText(String text) {
        text = pattern.matcher(text).replaceAll("");
        text = newLinePattern.matcher(text).replaceAll(" ");
        text = whitespacePattern.matcher(text).replaceAll(" ").trim();
        return text;
    }

    public static String decodeHtmlEntity(String input) {
        return input == null ? null : StringEscapeUtils.unescapeHtml4(input);
    }


    private static Pattern getPattern(String regex) {
        if (patterns.containsKey(regex)) {
            return (Pattern)patterns.get(regex);
        } else {
            Pattern pattern = Pattern.compile(regex);
            patterns.put(regex, pattern);
            return pattern;
        }
    }

    public static String replaceGroupValue(String input, String regex, String replacement, boolean replaceAll) {
        Pattern pattern = getPattern(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return replaceAll ? matcher.replaceAll(replacement) : matcher.replaceFirst(replacement);
        } else {
            return input;
        }
    }



    public static String trim(String text) {
        return text != null ? CharMatcher.WHITESPACE.trimFrom(text) : null;
    }


    public static String consolidateSpaces(String input) {
        if (input == null) {
            return null;
        } else {
            String spacesRegexp = "[\\s\\p{C}\\p{Z}]+";
            return trim(replaceGroupValue(input, "[\\s\\p{C}\\p{Z}]+", " ", true));
        }
    }

    public static String consolidateText(String text) {
        return consolidateSpaces(consolidateDashes(text)).trim();
    }


    public static String consolidateDashes(String input) {
        if (input == null) {
            return null;
        } else {
            String dashesRegexp = "‒|–|—|―|⁓|\u0097";
            return replaceGroupValue(input, "‒|–|—|―|⁓|\u0097", "-", true);
        }
    }


    public static String getStackTrace(Throwable aThrowable) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }


    public static String replaceTabLine(String srcString) {
        srcString = srcString.replace("\r\n", " ");
        srcString = srcString.replace("\n", " ");
        return srcString;
    }


    public static String arrayToString(Object[] objArr) {
        StringBuffer sb = null;
        if (objArr != null) {
            Object[] var2 = objArr;
            int var3 = objArr.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object o = var2[var4];
                if (sb != null) {
                    sb.append(',');
                } else {
                    sb = new StringBuffer();
                }

                sb.append(o.toString());
            }
        }
        return sb == null ? null : sb.toString();
    }


}
