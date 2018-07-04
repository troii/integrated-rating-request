package net.mediavrog.irr;

import android.content.Context;

import static net.mediavrog.ruli.SimpleRule.Comparator.LT;

public class DismissalCountRule extends DismissRule<Integer> {

    public DismissalCountRule(Context context, int maxDismissCount) {
        this(new DefaultPreferenceProvider(context), maxDismissCount);
    }

    public DismissalCountRule(PreferenceValue.PreferenceProvider pp, int maxDismissCount) {
        super(PreferenceValue.i(pp, PREF_KEY_DISMISSAL_COUNT), LT, maxDismissCount);
    }

}
