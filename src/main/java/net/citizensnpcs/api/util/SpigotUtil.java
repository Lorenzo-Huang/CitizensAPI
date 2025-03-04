package net.citizensnpcs.api.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public class SpigotUtil {
    public static boolean checkYSafe(double y, World world) {
        if (!SUPPORT_WORLD_HEIGHT || world == null) {
            return y >= 0 && y <= 255;
        }
        try {
            return y >= world.getMinHeight() && y <= world.getMaxHeight();
        } catch (Throwable t) {
            SUPPORT_WORLD_HEIGHT = false;
            return y >= 0 && y <= 255;
        }
    }

    public static int getMaxNameLength(EntityType type) {
        return isUsing1_13API() ? 256 : 64;
    }

    public static int[] getVersion() {
        if (BUKKIT_VERSION == null) {
            String version = Bukkit.getVersion();
            if (version == null || version.isEmpty()) {
                return new int[] { 1, 8 };
            }
            String[] parts = version.split("_");
            return BUKKIT_VERSION = new int[] { Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) };
        }
        return BUKKIT_VERSION;
    }

    public static boolean isUsing1_13API() {
        if (using1_13API == null) {
            try {
                Enchantment.getByKey(Enchantment.ARROW_DAMAGE.getKey());
                using1_13API = true;
            } catch (Exception ex) {
                using1_13API = false;
            } catch (NoSuchMethodError ex) {
                using1_13API = false;
            }
        }
        return using1_13API;
    }

    public static Duration parseDuration(String raw, TimeUnit defaultUnits) {
        if (defaultUnits == null) {
            Integer ticks = Ints.tryParse(raw);
            if (ticks != null) {
                return Duration.ofMillis(ticks * 50);
            }
        } else if (NUMBER_MATCHER.matcher(raw).matches()) {
            return Duration.of(Longs.tryParse(raw), toChronoUnit(defaultUnits));
        }

        if (raw.endsWith("t")) {
            return Duration.ofMillis(Integer.parseInt(raw.substring(0, raw.length() - 1)) * 50);
        }
        raw = DAY_MATCHER.matcher(raw).replaceFirst("P$1T").replace("min", "m").replace("hr", "h");
        if (raw.charAt(0) != 'P') {
            raw = "PT" + raw;
        }
        return Duration.parse(raw);
    }

    private static final Map<TimeUnit, ChronoUnit> TIME_UNIT_TO_CHRONO_UNIT_MAP;

    static {
        Map<TimeUnit, ChronoUnit> map = new HashMap<>();
        map.put(TimeUnit.NANOSECONDS, ChronoUnit.NANOS);
        map.put(TimeUnit.MICROSECONDS, ChronoUnit.MICROS);
        map.put(TimeUnit.MILLISECONDS, ChronoUnit.MILLIS);
        map.put(TimeUnit.SECONDS, ChronoUnit.SECONDS);
        map.put(TimeUnit.MINUTES, ChronoUnit.MINUTES);
        map.put(TimeUnit.HOURS, ChronoUnit.HOURS);
        map.put(TimeUnit.DAYS, ChronoUnit.DAYS);
        TIME_UNIT_TO_CHRONO_UNIT_MAP = Collections.unmodifiableMap(map);
    }

    private static ChronoUnit toChronoUnit(TimeUnit tu) {
        ChronoUnit chronoUnit = TIME_UNIT_TO_CHRONO_UNIT_MAP.get(tu);
        if (chronoUnit == null) {
            throw new AssertionError();
        }
        return chronoUnit;
    }


    private static int[] BUKKIT_VERSION = null;
    private static Pattern DAY_MATCHER = Pattern.compile("(\\d+d)");
    private static Pattern NUMBER_MATCHER = Pattern.compile("(\\d+)");
    private static boolean SUPPORT_WORLD_HEIGHT = true;
    private static Boolean using1_13API;
}
