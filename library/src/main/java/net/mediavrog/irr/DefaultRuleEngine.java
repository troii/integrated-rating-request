package net.mediavrog.irr;

import android.content.Context;
import android.content.SharedPreferences;

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

    private static final String PREF_FILE_NAME_SUFFIX = ".irr_default_rule_engine";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private static SharedPreferences sPrefs;

    private Context mContext;
    private IrrLayout.OnUserActionListener mListener;

    public static DefaultRuleEngine newInstance(final Context ctx, int appStartCount, int distinctDays, final int postponeDays, int maxDismissCount) {
        PreferenceValue.PreferenceProvider pp = new PreferenceValue.PreferenceProvider() {
            SharedPreferences prefs;

            @Override
            public SharedPreferences getPreferences() {
                if (prefs == null) prefs = DefaultRuleEngine.getPreferences(ctx);
                return prefs;
            }
        };

        RuleSet rule = new RuleSet.Builder()
                .addRule(new DidRateRule(pp))
                .addRule(new AppStartRule(pp, appStartCount))
                .addRule(new DaysUsedRule(pp, distinctDays))
                .addRule(new DismissalCountRule(pp, maxDismissCount))
                .addRule(new DismissPostponeRule(pp, postponeDays)).build();

        ArrayList<Rule> rules = new ArrayList<>();
        rules.add(rule);

        return new DefaultRuleEngine(ctx, rules);
    }

    public DefaultRuleEngine(Context ctx, List<Rule> rules) {
        super(rules);
        mContext = ctx;
    }

    public IrrLayout.OnUserActionListener getListener() {
        if (mListener == null) mListener = new DefaultOnUserActionListener();
        return mListener;
    }

    public void setListener(IrrLayout.OnUserActionListener l) {
        mListener = l;
    }

    @Override
    public String toString(boolean evaluate) {
        return "DefaultRuleEngine" + "\n" + super.toString(evaluate);
    }

    public void reset() {
        reset(mContext);
    }

    public static void reset(Context ctx) {
        getPreferences(ctx).edit().clear().apply();
    }

    public void trackAppStart() {
        AppStartRule.trackAppStart(mContext);
    }

    public void trackDismissal() {
        DismissRule.trackDismissal(mContext);
    }

    public void trackRated() {
        DidRateRule.trackRated(mContext);
    }

    public static SharedPreferences getPreferences(Context ctx) {
        if (sPrefs == null)
            sPrefs = ctx.getSharedPreferences(getPrefFileName(ctx), Context.MODE_PRIVATE);
        return sPrefs;
    }

    private static String getPrefFileName(Context ctx) {
        return ctx.getPackageName() + PREF_FILE_NAME_SUFFIX;
    }
}
