package net.mediavrog.irr;

import android.content.Context;
import android.content.SharedPreferences;

import net.mediavrog.ruli.SimpleRule;

import static net.mediavrog.ruli.SimpleRule.Comparator.EQ;

public class DidRateRule extends SimpleRule<Boolean> {

    private static final String PREF_KEY_DID_RATE = "didRate";

    public DidRateRule(Context context) {
        this(new DefaultPreferenceProvider(context));
    }

    public DidRateRule(PreferenceValue.PreferenceProvider pp) {
        super(PreferenceValue.b(pp, PREF_KEY_DID_RATE), EQ, false);
    }

    public static void trackRated(Context context) {
        trackRated(new DefaultPreferenceProvider(context));
    }

    public static void trackRated(PreferenceValue.PreferenceProvider pp) {
        SharedPreferences s = pp.getPreferences();
        s.edit()
                .putBoolean(PREF_KEY_DID_RATE, true)
                .apply();
    }

    public static boolean didRate(Context context) {
        return didRate(new DefaultPreferenceProvider(context));
    }

    public static boolean didRate(PreferenceValue.PreferenceProvider pp) {
        return PreferenceValue.b(pp, PREF_KEY_DID_RATE).get();
    }

}
