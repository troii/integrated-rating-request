package net.mediavrog.irr;

import static net.mediavrog.ruli.SimpleRule.Comparator.GT_EQ;

public class DaysUsedRule extends AppUsageRule<Integer> {

    DaysUsedRule(PreferenceValue.PreferenceProvider pp, int distinctDays) {
        super(PreferenceValue.i(pp, PREF_KEY_DAYS_USED), GT_EQ, distinctDays);
    }

}
