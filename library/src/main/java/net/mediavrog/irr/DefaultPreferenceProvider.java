package net.mediavrog.irr;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 */
public class DefaultPreferenceProvider implements PreferenceValue.PreferenceProvider {

    private static final String PREF_FILE_NAME_SUFFIX = ".irr_default_rule_engine";

    private final Context context;

    public DefaultPreferenceProvider(Context context) {
        this.context = context;
    }

    @Override
    public SharedPreferences getPreferences() {
        return context.getSharedPreferences(getPrefFileName(context), Context.MODE_PRIVATE);
    }

    private static String getPrefFileName(Context context) {
        return context.getPackageName() + PREF_FILE_NAME_SUFFIX;
    }

}
