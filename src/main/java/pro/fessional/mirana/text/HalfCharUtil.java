package pro.fessional.mirana.text;

/**
 * convert full char to its half char
 *
 * @author trydofor
 * @since 2017-10-15
 */
public class HalfCharUtil {

    public static String half(CharSequence cs) {
        if (cs == null) return null;
        int len = cs.length();
        if (len == 0) return "";

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = cs.charAt(i);
            if (c == '\u3000') {
                c = ' ';
            }
            else //noinspection UnnecessaryUnicodeEscape
                if (c > '\uFF00' && c < '\uFF5F') {
                    c = (char) (c - 65248);
                }
            sb.append(c);
        }
        return sb.toString();
    }

    public static char half(char c) {
        if (c == '\u3000') {
            c = ' ';
        }
        else //noinspection UnnecessaryUnicodeEscape
            if (c > '\uFF00' && c < '\uFF5F') {
                c = (char) (c - 65248);
            }
        return c;
    }
}
