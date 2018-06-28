package net.mediavrog.samples.irr;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.mediavrog.irr.AppUsageRule;
import net.mediavrog.irr.DefaultOnUserActionListener;
import net.mediavrog.irr.DefaultRuleEngine;
import net.mediavrog.irr.DismissRule;
import net.mediavrog.irr.IrrLayout;

public class DefaultEngineActivity extends AppCompatActivity {

    public static final String TAG = DefaultEngineActivity.class.getSimpleName();

    protected TextView dump;
    protected IrrLayout irr;
    protected DefaultRuleEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // simulate an app start; this should really go into a custom Application#onStart
        AppUsageRule.trackAppStart(this);

        // now we let the layout handle all the default setup using attributes
        setContentView(getLayoutResId());

        // status textfield for debug info
        dump = findViewById(R.id.dump);

        initialize();
        setupControls();
        evaluateRules(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                Toast.makeText(this, R.string.help, Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    protected int getLayoutResId() {
        return R.layout.activity_default_engine;
    }

    protected void initialize() {
        irr = findViewById(R.id.irr_layout);

        engine = (DefaultRuleEngine) irr.getRuleEngine();
        engine.setListener(new DefaultOnUserActionListener() {

            @Override
            public void onRate(Context ctx) {
                super.onRate(ctx);
                evaluateRules(true);
            }

            @Override
            public void onFeedback(Context ctx) {
                super.onFeedback(ctx);
            }

            @Override
            public void onDismiss(Context ctx, IrrLayout.State s) {
                super.onDismiss(ctx, s);
                evaluateRules(true);
            }
        });
    }

    /**
     * Those controls allow us to manipulate the data backing the rule engine.
     */
    void setupControls() {
        findViewById(R.id.incrAppStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUsageRule.trackAppStart(v.getContext());
                evaluateRules();
            }
        });

        findViewById(R.id.incrDaysUsed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                SharedPreferences p = DefaultRuleEngine.getPreferences(v.getContext());
                p
                        .edit()
                        .putInt(AppUsageRule.PREF_KEY_DAYS_USED, p.getInt(DefaultRuleEngine.PREF_KEY_DAYS_USED, 0) + 1)
                        .apply();
                */
                Toast.makeText(DefaultEngineActivity.this, "Increase Days Used not implemented yet", Toast.LENGTH_SHORT).show();

                evaluateRules();
            }
        });

        findViewById(R.id.incrDismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DismissRule.trackDismissal(v.getContext());
                evaluateRules();
            }
        });

        findViewById(R.id.resetDismisAt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                SharedPreferences p = DefaultRuleEngine.getPreferences(v.getContext());
                p
                        .edit()
                        .putString(DefaultRuleEngine.PREF_KEY_LAST_DISMISSED_AT, "")
                        .apply();
                */
                Toast.makeText(DefaultEngineActivity.this, "Last Dismissed not implemented yet", Toast.LENGTH_SHORT).show();

                evaluateRules();
            }
        });

        findViewById(R.id.toggleRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                SharedPreferences p = DefaultRuleEngine.getPreferences(v.getContext());
                p
                        .edit()
                        .putBoolean(DefaultRuleEngine.PREF_KEY_DID_RATE, !p.getBoolean(DefaultRuleEngine.PREF_KEY_DID_RATE, false))
                        .apply();
                */
                Toast.makeText(DefaultEngineActivity.this, "Did Rate not implemented yet", Toast.LENGTH_SHORT).show();

                evaluateRules();
            }
        });

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultRuleEngine.reset(v.getContext());
                evaluateRules();
            }
        });
    }

    void evaluateRules() {
        evaluateRules(false);
    }

    void evaluateRules(boolean onlyDump) {
        if (!onlyDump) engine.evaluate();

        dump.setText(engine.toString(!onlyDump));
    }
}
