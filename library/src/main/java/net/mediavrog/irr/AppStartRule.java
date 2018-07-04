package net.mediavrog.irr;

import android.content.Context;

import static net.mediavrog.ruli.SimpleRule.Comparator.GT_EQ;

public class AppStartRule extends AppUsageRule<Integer> {

    public AppStartRule(Context context, int appStartCount) {
        this(new DefaultPreferenceProvider(context), appStartCount);
    }

    public AppStartRule(PreferenceValue.PreferenceProvider pp, int appStartCount) {
        super(PreferenceValue.i(pp, PREF_KEY_APP_STARTS), GT_EQ, appStartCount);
    }

    public static int getAppStartCount(Context context) {
        return PreferenceValue.i(new DefaultPreferenceProvider(context), PREF_KEY_APP_STARTS).get();
    }

    public static int getAppStartCount(PreferenceValue.PreferenceProvider pp) {
        return PreferenceValue.i(pp, PREF_KEY_APP_STARTS).get();
    }
}
