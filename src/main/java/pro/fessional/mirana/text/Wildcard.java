package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * <pre>
 * 高性能，低碎片的通配符Wildcard贪婪匹配。
 * 提供从左到右的wildcard的匹配模式，其中
 *  - `?` 表示任意一个字符
 *  - `*` 表示任意多个字符
 *  - `?*` 等于至少一个字符
 *  - `**` 按`*`处理
 *  - `*?` 按`?*`处理
 * 注意，按字符而非字节匹配
 * </pre>
 *
 * @author trydofor
 * @since 2020-09-26
 */
public class Wildcard {

    /**
     * <pre>
     * 按`*`分割成数组，`**` 按`*`处理，`*?` 按`?*`处理
     * `*.doc` = [`*`,`.doc`]
     * `abc?.doc` = [`abc?.doc`]
     * `**.doc` = [`*`,`.doc`]
     * `??*.doc` = [`??`,`*`,`.doc`]
     * `**?**.doc` = [`?`,`*`,`.doc`]
     * </pre>
     *
     * @param str 模式
     * @return 分解后pattern
     */
    @NotNull
    public static String[] compile(CharSequence str) {
        if (str == null) return Null.StrArr;
        final StringBuilder buf = new StringBuilder(16);
        final ArrayList<String> ptn = new ArrayList<>(4);

        char lastChr = 0;
        for (int i = 0, len = str.length(); i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '*') {
                if (buf.length() > 0) {
                    ptn.add(buf.toString());
                    buf.setLength(0);
                }
                if (lastChr != '*') {
                    ptn.add("*");
                }
                // skip else
                // `**` 按`*`处理
                lastChr = ch;
            }
            else if (ch == '?') {
                if (lastChr == '*') {
                    // `*?` 按`?*`处理
                    int ps = ptn.size();
                    if (ps == 1) {
                        ptn.add(0, "?");
                    }
                    else {
                        ptn.set(ps - 1, ptn.get(ps - 1) + "?");
                    }
                    lastChr = '*';
                }
                else {
                    buf.append(ch);
                    lastChr = ch;
                }
            }
            else {
                buf.append(ch);
                lastChr = ch;
            }
        }

        if (buf.length() > 0) {
            ptn.add(buf.toString());
        }

        return ptn.toArray(Null.StrArr);
    }

    /**
     * <pre>
     * 高性能，低碎片的通配符Wildcard贪婪匹配。
     * 提供从左到右的wildcard的匹配模式，其中
     *  - `?` 表示任意一个字符
     *  - `*` 表示任意多个字符
     *  - `?*` 等于至少一个字符
     *  - `**` 按`*`处理
     *  - `*?` 按`?*`处理
     * 注意，按字符而非字节匹配
     * 进行wildcard匹配，null不匹配任何字符，但全null时匹配。
     * </pre>
     *
     * @param igc 忽略大小写
     * @param str 匹配字符，null不匹配
     * @param ptn 规整的模式（compile更合规），null或empty不匹配
     * @return 是否匹配
     */
    public static boolean match(boolean igc, CharSequence str, String... ptn) {
        if (str == null && ptn == null) return true;
        if (str == null || ptn == null || ptn.length == 0) return false;

        // 借用 FilenameUtils.wildcardMatch
        boolean any = false;
        int strIdx = 0;
        int ptnIdx = 0;
        final ArrayDeque<int[]> deque = new ArrayDeque<>(ptn.length);
        final int sln = str.length();
        do {
            if (!deque.isEmpty()) {
                final int[] array = deque.pop();
                ptnIdx = array[0];
                strIdx = array[1];
                any = true;
            }

            while (ptnIdx < ptn.length) {
                if (ptn[ptnIdx].equals("*")) {
                    any = true;
                    if (ptnIdx == ptn.length - 1) {
                        strIdx = sln;
                    }

                }
                else {
                    if (any) {
                        strIdx = index(igc, str, strIdx, ptn[ptnIdx]);
                        if (strIdx == -1) {
                            break;
                        }
                        final int repeat = index(igc, str, strIdx + 1, ptn[ptnIdx]);
                        if (repeat >= 0) {
                            deque.push(new int[]{ptnIdx, repeat});
                        }
                    }
                    else {
                        if (index(igc, str, strIdx, ptn[ptnIdx]) == -1) {
                            break;
                        }
                    }

                    strIdx += ptn[ptnIdx].length();
                    any = false;
                }
                ptnIdx++;
            }

            if (ptnIdx == ptn.length && strIdx == sln) {
                return true;
            }

        } while (!deque.isEmpty());

        return false;
    }

    /**
     * 不区分大小写，从头匹配
     *
     * @param str 字符
     * @param ptn 模式
     * @return 查找结果，-1表示没有找到
     * @see #index(boolean, CharSequence, int, String)
     */
    public static int index(@NotNull CharSequence str, @NotNull String ptn) {
        return index(true, str, 0, ptn);
    }

    /**
     * 从头匹配
     *
     * @param igc 忽略大小写
     * @param str 字符
     * @param ptn 模式
     * @return 查找结果，-1表示没有找到
     * @see #index(boolean, CharSequence, int, String)
     */
    public static int index(boolean igc, @NotNull CharSequence str, @NotNull String ptn) {
        return index(igc, str, 0, ptn);
    }

    /**
     * 支持`?`和不区分大小写的字符串查找，等同于 String.indexOf
     *
     * @param igc 忽略大小写
     * @param str 字符
     * @param off 开始查找的位置
     * @param ptn 模式
     * @return 查找结果，-1表示没有找到
     */
    public static int index(boolean igc, @NotNull CharSequence str, int off, @NotNull String ptn) {
        final int pln = ptn.length();
        if (pln > str.length() - off) return -1;

        char p, c;
        out:
        for (int i = off, lop = str.length() - pln; i <= lop; i++) {
            for (int j = i, k = 0; k < pln; j++, k++) {
                p = ptn.charAt(k);
                if (p == '?') continue;
                c = str.charAt(j);
                if (p == c) continue;
                if (igc && (p == Character.toLowerCase(c) || p == Character.toUpperCase(c))) continue;
                continue out;
            }
            return i;
        }
        return -1;
    }
}
