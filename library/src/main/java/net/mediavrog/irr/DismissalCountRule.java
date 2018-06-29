package net.mediavrog.irr;

import android.content.Context;
import android.content.SharedPreferences;

import static net.mediavrog.ruli.SimpleRule.Comparator.LT;

public class DismissalCountRule extends DismissRule<Integer> {

    public DismissalCountRule(final Context context, int maxDismissCount) {
        this(new PreferenceValue.PreferenceProvider() {
            @Override
            public SharedPreferences getPreferences() {
                return DefaultRuleEngine.getPreferences(context);
            }
        }, maxDismissCount);
    }

    public DismissalCountRule(PreferenceValue.PreferenceProvider pp, int maxDismissCount) {
        super(PreferenceValue.i(pp, PREF_KEY_DISMISSAL_COUNT), LT, maxDismissCount);
    }

}
