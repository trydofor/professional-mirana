package pro.fessional.mirana.text;

/**
 * 半角字符转化
 *
 * @author trydofor
 * @since 2017-10-15
 */
public class HalfCharUtil {

    public static String half(CharSequence cs) {
        if (cs == null) return null;
        int length = cs.length();
        if (length == 0) return "";

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < cs.length(); i++) {
            char c = cs.charAt(i);
            if (c == '\u3000') {
                c = ' ';
            } else if (c > '\uFF00' && c < '\uFF5F') {
                c = (char) (c - 65248);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static char half(char c) {
        if (c == '\u3000') {
            c = ' ';
        } else if (c > '\uFF00' && c < '\uFF5F') {
            c = (char) (c - 65248);
        }
        return c;
    }
}
