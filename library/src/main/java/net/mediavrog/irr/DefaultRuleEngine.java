package net.mediavrog.irr;

import android.content.Context;

import net.mediavrog.ruli.Rule;
import net.mediavrog.ruli.RuleEngine;
import net.mediavrog.ruli.RuleSet;

import java.util.ArrayList;
import java.util.List;


public class DefaultRuleEngine extends RuleEngine {

    /**
     * Start nudging the user after this amount of app starts (== limit to engaged users)
     */
    public static final int DEFAULT_APP_START_COUNT = 10;

    /**
     * Start nudging the user after this amount of days (== engaged user in combination with app starts)
     */
    public static final int DEFAULT_DISTINCT_DAYS = 3;

    /**
     * Postpone next nudge by this amount of days.
     */
    public static final int DEFAULT_POSTPONE_DAYS = 6;

    /**
     * Stop nudging after with amount of dismissals. At one point you gotta give up ^^.
     */
    public static final int DEFAULT_MAX_DISMISS_COUNT = 3;

    static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private final PreferenceValue.PreferenceProvider preferenceProvider;
    private IrrLayout.OnUserActionListener mListener;

    public static DefaultRuleEngine newInstance(final Context ctx, int appStartCount, int distinctDays, final int postponeDays, int maxDismissCount) {
        PreferenceValue.PreferenceProvider pp = new DefaultPreferenceProvider(ctx);

        RuleSet rule = new RuleSet.Builder()
                .addRule(new DidRateRule(pp))
                .addRule(new AppStartRule(pp, appStartCount))
                .addRule(new DaysUsedRule(pp, distinctDays))
                .addRule(new DismissalCountRule(pp, maxDismissCount))
                .addRule(new DismissPostponeRule(pp, postponeDays)).build();

        ArrayList<Rule> rules = new ArrayList<>();
        rules.add(rule);

        return new DefaultRuleEngine(pp, rules);
    }

    private DefaultRuleEngine(PreferenceValue.PreferenceProvider pp, List<Rule> rules) {
        super(rules);
        this.preferenceProvider = pp;
    }

    public IrrLayout.OnUserActionListener getListener() {
        if (mListener == null) mListener = new DefaultOnUserActionListener();
        return mListener;
    }

    public void setListener(IrrLayout.OnUserActionListener l) {
        mListener = l;
    }

    public void trackAppStart() {
        AppStartRule.trackAppStart(preferenceProvider);
    }

    public void trackDismissal() {
        DismissRule.trackDismissal(preferenceProvider);
    }

    public void trackRated() {
        DidRateRule.trackRated(preferenceProvider);
    }

    public void reset() {
        reset(preferenceProvider);
    }

    @Override
    public String toString(boolean evaluate) {
        return "DefaultRuleEngine" + "\n" + super.toString(evaluate);
    }

    public static void reset(Context ctx) {
        reset(new DefaultPreferenceProvider(ctx));
    }

    public static void reset(PreferenceValue.PreferenceProvider preferenceProvider) {
        preferenceProvider.getPreferences().edit().clear().apply();
    }

}
