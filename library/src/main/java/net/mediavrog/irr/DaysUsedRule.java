package net.mediavrog.irr;

import android.content.Context;
import android.content.SharedPreferences;

import static net.mediavrog.ruli.SimpleRule.Comparator.GT_EQ;

public class DaysUsedRule extends AppUsageRule<Integer> {

    public DaysUsedRule(final Context context, int distinctDays) {
        this(new PreferenceValue.PreferenceProvider() {
            @Override
            public SharedPreferences getPreferences() {
                return DefaultRuleEngine.getPreferences(context);
            }
        }, distinctDays);
    }

    public DaysUsedRule(PreferenceValue.PreferenceProvider pp, int distinctDays) {
        super(PreferenceValue.i(pp, PREF_KEY_DAYS_USED), GT_EQ, distinctDays);
    }

}
