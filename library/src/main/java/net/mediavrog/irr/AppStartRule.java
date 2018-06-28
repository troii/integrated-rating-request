package net.mediavrog.irr;

import static net.mediavrog.ruli.SimpleRule.Comparator.GT_EQ;

public class AppStartRule extends AppUsageRule<Integer> {

    AppStartRule(PreferenceValue.PreferenceProvider pp, int appStartCount) {
        super(PreferenceValue.i(pp, PREF_KEY_APP_STARTS), GT_EQ, appStartCount);
    }

}
