package net.mediavrog.irr;

import android.content.Context;
import android.content.SharedPreferences;

import net.mediavrog.ruli.SimpleRule;

import static net.mediavrog.irr.DefaultRuleEngine.getPreferences;
import static net.mediavrog.ruli.SimpleRule.Comparator.EQ;

public class DidRateRule extends SimpleRule<Boolean> {

    private static final String PREF_KEY_DID_RATE = "didRate";

    public DidRateRule(final Context context) {
        this(new PreferenceValue.PreferenceProvider() {
            @Override
            public SharedPreferences getPreferences() {
                return DefaultRuleEngine.getPreferences(context);
            }
        });
    }

    public DidRateRule(PreferenceValue.PreferenceProvider pp) {
        super(PreferenceValue.b(pp, PREF_KEY_DID_RATE), EQ, false);
    }

    public static void trackRated(Context ctx) {
        SharedPreferences s = getPreferences(ctx);
        s.edit()
                .putBoolean(PREF_KEY_DID_RATE, true)
                .apply();
    }

}
