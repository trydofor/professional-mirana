package pro.fessional.mirana.cond;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * global static Flag of Enum
 *
 * @author trydofor
 * @since 2022-01-26
 */
public class StaticFlag {

    private static final Map<Enum<?>, Boolean> JvmFlags = new ConcurrentHashMap<>();

    public static void setFlag(@NotNull Enum<?> flag) {
        JvmFlags.put(flag, Boolean.TRUE);
    }

    public static void delFlag(@NotNull Enum<?> flag) {
        JvmFlags.remove(flag);
    }

    public static boolean hasFlag(@NotNull Enum<?> flag) {
        final Boolean bol = JvmFlags.get(flag);
        return bol != null;
    }

    public static boolean anyFlag(@NotNull Enum<?>... flags) {
        for (Enum<?> flag : flags) {
            if (hasFlag(flag)) {
                return true;
            }
        }
        return false;
    }

    public static boolean notFlag(@NotNull Enum<?> flag) {
        final Boolean bol = JvmFlags.get(flag);
        return bol == null;
    }

    private static final Map<Object, Map<Enum<?>, Boolean>> KeyFlags = new ConcurrentHashMap<>();

    public static void setFlag(@NotNull Object key, @NotNull Enum<?> flag) {
        KeyFlags.computeIfAbsent(key, k -> {
            Map<Enum<?>, Boolean> map = new ConcurrentHashMap<>();
            map.put(flag, Boolean.TRUE);
            return map;
        });
    }

    public static void delFlag(@NotNull Object key, @NotNull Enum<?> flag) {
        final Map<Enum<?>, Boolean> map = KeyFlags.get(key);
        if (map != null) {
            map.remove(flag);
        }
    }

    public static boolean hasFlag(@NotNull Object key, @NotNull Enum<?> flag) {
        final Map<Enum<?>, Boolean> map = KeyFlags.get(key);
        return map != null && map.get(flag) != null;
    }

    public static boolean anyFlag(@NotNull Object key, @NotNull Enum<?>... flags) {
        final Map<Enum<?>, Boolean> map = KeyFlags.get(key);
        if (map == null) return false;
        for (Enum<?> flag : flags) {
            if (map.get(flag) != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean notFlag(@NotNull Object key, @NotNull Enum<?> flag) {
        final Map<Enum<?>, Boolean> map = KeyFlags.get(key);
        return map == null || map.get(flag) == null;
    }

    private static final Pattern ListSplit = Pattern.compile("[\r\n\t ,]+");

    /**
     * <pre>
     * case-insensitive matching, the flag must be trimmed, vote does not need to be trimmed.
     * * `!` or `-` prefix means not, `-a`, `!a` is NOT `a`
     * * `a` votes `b` = `0`
     * * `!a` votes `b` = `1`
     * * `a` votes `a` = `2`
     * * `!a` votes `a` = `-2`
     * result true if votes > 0, false if <= 0
     * </pre>
     */
    public static int vote(@NotNull final String flag, @NotNull final String vote) {

        final int vln = vote.length();
        final int fln = flag.length();
        int not = 0;
        int off = 0;

        for (int i = 0; i < vln; i++) {
            char c = vote.charAt(i);
            if (c == '-' || c == '!') {
                not = 1;
                off = i + 1;
            }
            else if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                off = i + 1;
                continue;
            }
            break;
        }

        final int ln = vln - off;
        if (ln < fln) {
            return not;
        }
        else if (ln > fln) {
            char c = vote.charAt(off + fln);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n' && c != ',') return not;
        }

        boolean fnd = flag.regionMatches(true, 0, vote, off, fln);
        return fnd ? (not == 0 ? 2 : -2) : not;
    }

    /**
     * <pre>
     * eval the votes to flag, case-insensitive matching,
     * the flag must be trimmed, vote does not need to be trimmed.
     * * `[a,b,c]` is `a or b or c`
     * * null/empty means skip, no vote
     * *  `a` votes `b` = false
     * * `!a` votes `a` = false (veto power)
     * *  `a` votes `a` = true
     * * `!a` votes `b` = true
     * return true if has votes, otherwise false
     * </pre>
     */
    public static boolean hasVote(@Nullable String flag, @Nullable String... votes) {
        if (flag == null || flag.isEmpty() || votes == null) return false;

        boolean voted = false;
        for (String vt : votes) {
            if (vt == null || vt.isEmpty()) continue;

            final int vr = vote(flag, vt);
            if (vr > 0) {
                voted = true;
            }
            else if (vr < 0) {
                return false;
            }
        }

        return voted;
    }

    public static boolean hasVote(@Nullable String flag, @Nullable String votes) {
        if (votes == null || votes.isEmpty()) return false;
        return hasVote(flag, ListSplit.split(votes));
    }

    public static boolean notVote(@Nullable String flag, @Nullable String votes) {
        return !hasVote(flag, votes);
    }

    public static boolean notVote(@Nullable String flag, @Nullable String... votes) {
        return !hasVote(flag, votes);
    }
}
