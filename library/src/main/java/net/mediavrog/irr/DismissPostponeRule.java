package net.mediavrog.irr;

import android.content.Context;

import net.mediavrog.ruli.Value;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static net.mediavrog.irr.DefaultRuleEngine.DEFAULT_DATE_FORMAT;
import static net.mediavrog.ruli.SimpleRule.Comparator.LT_EQ;

public class DismissPostponeRule extends DismissRule<String> {

    public DismissPostponeRule(Context context, int postponeDays) {
        this(new DefaultPreferenceProvider(context), postponeDays);
    }

    public DismissPostponeRule(PreferenceValue.PreferenceProvider pp, final int postponeDays) {
        super(PreferenceValue.s(pp, PREF_KEY_LAST_DISMISSED_AT), LT_EQ, new Value<String>() {
            @Override
            public String get() {
                // compare to postpone days before today; current value should be smaller than that
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, -1 * postponeDays);
                return new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()).format(c.getTime());
            }
        });
    }

}
