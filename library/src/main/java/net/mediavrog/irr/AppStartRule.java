package net.mediavrog.irr;

import android.content.Context;
import android.content.SharedPreferences;

import static net.mediavrog.ruli.SimpleRule.Comparator.GT_EQ;

public class AppStartRule extends AppUsageRule<Integer> {

    public AppStartRule(final Context context, int appStartCount) {
        this(new PreferenceValue.PreferenceProvider() {
            @Override
            public SharedPreferences getPreferences() {
                return DefaultRuleEngine.getPreferences(context);
            }
        }, appStartCount);
    }

    public AppStartRule(PreferenceValue.PreferenceProvider pp, int appStartCount) {
        super(PreferenceValue.i(pp, PREF_KEY_APP_STARTS), GT_EQ, appStartCount);
    }

}
