package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * <pre>
 * High-performance, low-fragmentation wildcard matching.
 * Provides a left-to-right wildcard matching pattern, where
 *  - `?` means any one char
 *  - `*` means any number of char
 *  - `?*` means at least one char
 *  - `**` is treated as `*`
 *  - `*?` is treated as `?*`.
 * Note that matching is by char, not by byte
 * </pre>
 *
 * @author trydofor
 * @since 2020-09-26
 */
public class Wildcard {

    /**
     * <pre>
     * Split into arrays by `*`. `**` treated as `*`, `*?` treated as `?*`
     * `*.doc` = [`*`,`.doc`]
     * `abc?.doc` = [`abc?.doc`]
     * `**.doc` = [`*`,`.doc`]
     * `??*.doc` = [`??`,`*`,`.doc`]
     * `**?**.doc` = [`?`,`*`,`.doc`]
     * </pre>
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
                // handle `**` as `*`
                lastChr = ch;
            }
            else if (ch == '?') {
                if (lastChr == '*') {
                    // handle `*?` as `?*`
                    int ps = ptn.size();
                    if (ps == 1) {
                        ptn.add(0, "?");
                    }
                    else {
                        ptn.set(ps - 1, ptn.get(ps - 1) + "?");
                    }
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
     * null does not match any character, but matches if all null are present.
     *
     * @param igc whether ignore case (case-insensitive)
     * @param str string to match, skip null
     * @param ptn patterns (should from `compile`), null or empty don't match
     */
    public static boolean match(boolean igc, CharSequence str, String... ptn) {
        if (str == null && ptn == null) return true;
        if (str == null || ptn == null || ptn.length == 0) return false;

        // see FilenameUtils.wildcardMatch
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
                if ("*".equals(ptn[ptnIdx])) {
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
                            deque.push(new int[]{ ptnIdx, repeat });
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
     * Case-insensitive match the index from the beginning, -1 means not found.
     *
     * @see #index(boolean, CharSequence, int, String)
     */
    public static int index(@NotNull CharSequence str, @NotNull String ptn) {
        return index(true, str, 0, ptn);
    }

    /**
     * match the index from the beginning, -1 means not found.
     *
     * @see #index(boolean, CharSequence, int, String)
     */
    public static int index(boolean igc, @NotNull CharSequence str, @NotNull String ptn) {
        return index(igc, str, 0, ptn);
    }

    /**
     * match the index from offset at the beginning, -1 means not found.
     * support `?` and case-insensitive match, equal to String.indexOf
     *
     * @param igc whether ignore case (case-insensitive)
     * @param str string to match
     * @param off the offset to match from
     * @param ptn the pattern to match
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
