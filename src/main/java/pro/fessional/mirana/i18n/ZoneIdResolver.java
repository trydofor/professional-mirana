package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.time.ThreadNow;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Supports case-insensitive and partial naming
 *
 * @author trydofor
 * @since 2019-07-01
 */
public class ZoneIdResolver {
    protected ZoneIdResolver() {
    }

    private static final Map<String, String> ZONE_IDS = new HashMap<>();

    static {
        for (String id : TimeZone.getAvailableIDs()) {
            ZONE_IDS.put(id, id.toUpperCase());
        }
    }

    @NotNull
    public static ZoneId zoneId(@NotNull String tag) {
        try {
            return ZoneId.of(standard(tag));
        }
        catch (Exception e) {
            return ThreadNow.sysZoneId();
        }
    }


    @NotNull
    public static TimeZone timeZone(@NotNull String tag) {
        try {
            return TimeZone.getTimeZone(standard(tag));
        }
        catch (Exception e) {
            return TimeZone.getTimeZone(ThreadNow.sysZoneId());
        }
    }

    private static String standard(String tag) {
        String s = ZONE_IDS.get(tag);
        if (s != null) {
            return tag;
        }

        String up = tag.toUpperCase();
        for (Map.Entry<String, String> entry : ZONE_IDS.entrySet()) {
            if (entry.getValue().contains(up)) {
                return entry.getKey();
            }
        }

        return tag;
    }
}
