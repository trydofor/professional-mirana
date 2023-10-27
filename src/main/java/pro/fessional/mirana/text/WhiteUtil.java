package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * More Handling of <a href="https://en.wikipedia.org/wiki/Whitespace_character">Whitespace chars</a> than Java builtin.
 *
 * @author trydofor
 * @since 2016-12-14
 */
public class WhiteUtil {
    protected WhiteUtil() {
    }

    private static final char[] WS = {
            0x0009, //U+0009 character tabulation
            0x000A, //U+000A line feed
            0x000B, //U+000B line tabulation
            0x000C, //U+000C form feed
            0x000D, //U+000D carriage return
            0x0020, //U+0020 space
            0x0085, //U+0085 next line
            0x00A0, //U+00A0 no-break space
            0x1680, //U+1680 ogham space mark
            0x2000, //U+2000 en quad
            0x2001, //U+2001 em quad
            0x2002, //U+2002 en space
            0x2003, //U+2003 em space
            0x2004, //U+2004 three-per-em space
            0x2005, //U+2005 four-per-em space
            0x2006, //U+2006 six-per-em space
            0x2007, //U+2007 figure space
            0x2008, //U+2008 punctuation space
            0x2009, //U+2009 thin space
            0x200A, //U+200A hair space
            0x202C, //U+202C POP DIRECTIONAL FORMATTING
            0x202D, //U+202D LEFT-TO-RIGHT OVERRIDE
            0x202E, //U+202E U+202E RIGHT-TO-LEFT OVERRIDE
            0x2028, //U+2028 line separator
            0x2029, //U+2029 paragraph separator
            0x202F, //U+202F narrow no-break space
            0x205F, //U+205F medium mathematical space
            0x3000, //U+3000 ideographic space
            0x180E, //U+180E mongolian vowel separator
            0x200B, //U+200B zero width space
            0x200C, //U+200C zero width non-joiner
            0x200D, //U+200D zero width joiner
            0x2060, //U+2060 word joiner
            0xFEFF,//U+FEFF zero width non-breaking
    };

    static {
        Arrays.sort(WS);
    }

    public static boolean notWhiteSpace(char c) {
        return Arrays.binarySearch(WS, c) < 0;
    }

    public static boolean isWhiteSpace(char c) {
        return Arrays.binarySearch(WS, c) >= 0;
    }

    /**
     * Remove all white chars from the header and footer.
     */
    @NotNull
    public static String trim(CharSequence str) {
        if (str == null) return "";

        final int len = str.length();
        int p1 = 0, p2 = len;

        while (p1 < p2 && Arrays.binarySearch(WS, str.charAt(p1)) >= 0) p1++;
        while (p2 > p1 && Arrays.binarySearch(WS, str.charAt(p2 - 1)) >= 0) p2--;

        return (p1 == 0 && p2 == len) ? str.toString() : str.subSequence(p1, p2).toString();
    }

    @NotNull
    public static String trim(CharSequence str, char... cs) {
        if (str == null) return "";

        final int len = str.length();
        int p1 = 0, p2 = len;

        o1:
        while (p1 < p2) {
            char c = str.charAt(p1);
            for (char c1 : cs) {
                if (c == c1) {
                    p1++;
                    continue o1;
                }
            }
            break;
        }

        o2:
        while (p2 > p1) {
            char c = str.charAt(p2 - 1);
            for (char c1 : cs) {
                if (c == c1) {
                    p2--;
                    continue o2;
                }
            }
            break;
        }

        return (p1 == 0 && p2 == len) ? str.toString() : str.subSequence(p1, p2).toString();
    }

    /**
     * Remove all white chars from the header and footer.
     * And replace the middle white chars (continuous ones are treated as one) with a single space (0x20)
     */
    @NotNull
    public static String space(CharSequence str) {
        if (str == null) return "";

        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        boolean ws = false;
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (notWhiteSpace(c)) {
                ws = false;
                sb.append(c);
            }
            else {
                if (!ws) {
                    sb.append(' ');
                }
                ws = true;
            }
        }

        if (sb.length() > 0 && sb.charAt(0) == ' ') sb.deleteCharAt(0);
        if (sb.length() > 1 && sb.charAt(sb.length() - 1) == ' ') sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * Delete all white chars
     */
    @NotNull
    public static String delete(CharSequence str) {
        if (str == null) return "";

        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (notWhiteSpace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Whether all white chars
     */
    public static boolean isAllWhite(CharSequence str) {
        if (str == null) return true;

        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!notWhiteSpace(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalsWithDeleted(CharSequence str1, CharSequence str2) {
        if (str1 == null && str2 == null) return true;
        String s1 = delete(str1);
        String s2 = delete(str2);
        return s1.equals(s2);
    }

    public static boolean equalsWithSpaced(CharSequence str1, CharSequence str2) {
        if (str1 == null && str2 == null) return true;
        String s1 = space(str1);
        String s2 = space(str2);
        return s1.equals(s2);
    }

    @NotNull
    public static List<String> lines(CharSequence str) {
        String spd = space(str);
        int len = spd.length();
        if (len == 0) return Collections.emptyList();

        int cnt = 0;
        for (int i = 0; i < len; i++) {
            int idx = spd.indexOf(' ', i);
            if (idx > 0) {
                cnt++;
                i = idx; // fast move
            }
            else {
                break;
            }
        }
        List<String> result = new ArrayList<>(cnt + 1);
        for (int i = 0; i < len; i++) {
            int idx = spd.indexOf(' ', i);
            if (idx > 0) {
                result.add(spd.substring(i, idx));
                i = idx;  // fast move
            }
            else {
                result.add(spd.substring(i));
                break;
            }
        }
        return result;
    }
}
