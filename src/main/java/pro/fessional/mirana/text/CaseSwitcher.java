package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

/**
 * <pre>
 * 用来转换命名的工具。
 * - 连续的大写会当做一个词，如ID，视为id
 * - `_.-`为强分词
 * - `单独的大写字母`为弱分词
 * - 当有强分词时，弱分词无效
 * </pre>
 *
 * @author trydofor
 * @since 2020-09-11
 */
public class CaseSwitcher {

    public enum Case {
        Snake("snake_case"),
        Camel("camelCase"),
        Kebab("kebab-case"),
        Pascal("PascalCase"),
        Dot("dot.case"),
        Scream("SCREAMING_CASE");

        public final String example;

        Case(String eg) {
            this.example = eg;
        }
    }

    @NotNull
    public static String toCase(Case cas, CharSequence str) {
        if (cas == null) return Null.notNull(str);
        int len = str.length();

        // 定性
        boolean isWeak = true;
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c == '.' || c == '_' || c == '-') {
                isWeak = false;
                break;
            }
        }

        // 分词
        StringBuilder sb = new StringBuilder(len + 10);
        if (isWeak) {
            int up = 0;
            for (int i = 0; i < len; i++) {
                char c = str.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    up++;
                    if (up == 1) {
                        if (cas == Case.Dot) {
                            sb.append('.');
                        } else if (cas == Case.Kebab) {
                            sb.append('-');
                        } else if (cas == Case.Snake || cas == Case.Scream) {
                            sb.append('_');
                        }
                    }

                    if (cas == Case.Scream
                            || (cas == Case.Pascal && up == 1)
                            || (cas == Case.Camel && i > 0 && up == 1)
                    ) {
                        sb.append(c);
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                } else {
                    up = 0;
                    if (cas == Case.Scream || (cas == Case.Pascal && i == 0)) {
                        sb.append(Character.toUpperCase(c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        } else {
            boolean ed = false;
            boolean up;
            for (int i = 0; i < len; i++) {
                char c = str.charAt(i);
                if (c == '.' || c == '_' || c == '-') {
                    ed = true;
                } else {
                    up = false;
                    if (ed) {
                        if (cas == Case.Dot) {
                            sb.append('.');
                        } else if (cas == Case.Kebab) {
                            sb.append('-');
                        } else if (cas == Case.Snake || cas == Case.Scream) {
                            sb.append('_');
                        } else if (cas == Case.Camel || cas == Case.Pascal) {
                            up = true;
                        }
                        ed = false;
                    }

                    if (up || cas == Case.Scream || (cas == Case.Pascal && i == 0)) {
                        sb.append(Character.toUpperCase(c));
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * snake-case
     *
     * @param str 源
     * @return snake_case
     */
    @NotNull
    public static String kebab(CharSequence str) {
        return toCase(Case.Kebab, str);
    }

    /**
     * snake_case
     *
     * @param str 源
     * @return snake_case
     */
    @NotNull
    public static String snake(CharSequence str) {
        return toCase(Case.Snake, str);
    }

    /**
     * camelCase
     *
     * @param str 源
     * @return camelCase
     */
    @NotNull
    public static String camel(CharSequence str) {
        return toCase(Case.Camel, str);
    }

    /**
     * PascalCase
     *
     * @param str 源
     * @return PascalCase
     */
    @NotNull
    public static String pascal(CharSequence str) {
        return toCase(Case.Pascal, str);
    }

    /**
     * dot.case
     *
     * @param str 源
     * @return dot.case
     */
    @NotNull
    public static String dot(CharSequence str) {
        return toCase(Case.Dot, str);
    }

    /**
     * SCREAMING_SNAKE
     *
     * @param str 源
     * @return SCREAMING_SNAKE
     */
    @NotNull
    public static String scream(CharSequence str) {
        return toCase(Case.Scream, str);
    }
}
