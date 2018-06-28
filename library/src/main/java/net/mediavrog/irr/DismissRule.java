package net.mediavrog.irr;

import android.content.Context;
import android.content.SharedPreferences;

import net.mediavrog.ruli.SimpleRule;
import net.mediavrog.ruli.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static net.mediavrog.irr.DefaultRuleEngine.getPreferences;

public abstract class DismissRule<T extends Comparable<T>> extends SimpleRule<T> {

    static final String PREF_KEY_DISMISSAL_COUNT = "dismissCount";
    static final String PREF_KEY_LAST_DISMISSED_AT = "lastDismissedAt";

    DismissRule(T lhs, Comparator c, T rhs) {
        super(lhs, c, rhs);
    }

    DismissRule(Value<T> lhs, Comparator c, T rhs) {
        super(lhs, c, rhs);
    }

    DismissRule(T lhs, Comparator c, Value<T> rhs) {
        super(lhs, c, rhs);
    }

    DismissRule(Value<T> lhs, Comparator c, Value<T> rhs) {
        super(lhs, c, rhs);
    }

    public static void trackDismissal(Context ctx) {
        SharedPreferences s = getPreferences(ctx);
        int dismissalCount = s.getInt(PREF_KEY_DISMISSAL_COUNT, 0) + 1;
        String today = new SimpleDateFormat(DefaultRuleEngine.DEFAULT_DATE_FORMAT, Locale.getDefault()).format(new Date());

        s.edit()
                .putInt(PREF_KEY_DISMISSAL_COUNT, dismissalCount)
                .putString(PREF_KEY_LAST_DISMISSED_AT, today)
                .apply();
    }

}
