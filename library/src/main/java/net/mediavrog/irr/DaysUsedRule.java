package net.mediavrog.irr;

import android.content.Context;

import static net.mediavrog.ruli.SimpleRule.Comparator.GT_EQ;

public class DaysUsedRule extends AppUsageRule<Integer> {

    public DaysUsedRule(Context context, int distinctDays) {
        this(new DefaultPreferenceProvider(context), distinctDays);
    }

    public DaysUsedRule(PreferenceValue.PreferenceProvider pp, int distinctDays) {
        super(PreferenceValue.i(pp, PREF_KEY_DAYS_USED), GT_EQ, distinctDays);
    }

    public static int getDaysUsedCount(Context context) {
        return getDaysUsedCount(new DefaultPreferenceProvider(context));
    }

    public static int getDaysUsedCount(PreferenceValue.PreferenceProvider pp) {
        return PreferenceValue.i(pp, PREF_KEY_DAYS_USED).get();
    }

}
