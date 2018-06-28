package net.mediavrog.irr;

import static net.mediavrog.ruli.SimpleRule.Comparator.LT;

public class DismissalCountRule extends DismissRule<Integer> {

    DismissalCountRule(PreferenceValue.PreferenceProvider pp, int maxDismissCount) {
        super(PreferenceValue.i(pp, PREF_KEY_DISMISSAL_COUNT), LT, maxDismissCount);
    }

}
