package net.mediavrog.irr;

import android.content.Context;
import android.content.SharedPreferences;

import net.mediavrog.ruli.SimpleRule;
import net.mediavrog.ruli.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static net.mediavrog.irr.DefaultRuleEngine.DEFAULT_DATE_FORMAT;

public abstract class AppUsageRule<T extends Comparable<T>> extends SimpleRule<T> {

    static final String PREF_KEY_APP_STARTS = "appStarts";
    static final String PREF_KEY_LAST_APP_START = "lastAppStart";
    static final String PREF_KEY_DAYS_USED = "daysUsedApp";

    AppUsageRule(T lhs, Comparator c, T rhs) {
        super(lhs, c, rhs);
    }

    AppUsageRule(Value<T> lhs, Comparator c, T rhs) {
        super(lhs, c, rhs);
    }

    AppUsageRule(T lhs, Comparator c, Value<T> rhs) {
        super(lhs, c, rhs);
    }

    AppUsageRule(Value<T> lhs, Comparator c, Value<T> rhs) {
        super(lhs, c, rhs);
    }

    public static void trackAppStart(Context context) {
        trackAppStart(new DefaultPreferenceProvider(context));
    }

    public static void trackAppStart(PreferenceValue.PreferenceProvider pp) {
        SharedPreferences s = pp.getPreferences();
        int appStarts = s.getInt(PREF_KEY_APP_STARTS, 0) + 1;
        int daysUsed = s.getInt(PREF_KEY_DAYS_USED, 1);

        String today = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()).format(new Date());
        String lastAppStart = s.getString(PREF_KEY_LAST_APP_START, today);
        if (!lastAppStart.equals(today)) daysUsed++;

        s.edit()
                .putInt(PREF_KEY_APP_STARTS, appStarts)
                .putString(PREF_KEY_LAST_APP_START, today)
                .putInt(PREF_KEY_DAYS_USED, daysUsed)
                .apply();
    }

}
