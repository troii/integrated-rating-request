package net.mediavrog.irr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Default implementation of a {@link net.mediavrog.irr.IrrLayout.OnUserActionListener}.
 * <ul>
 * <li>onRate(): Launches {@link Intent#ACTION_VIEW} intent for url given as irr:storeUrl or the play store url generated from the package name</li>
 * <li>onFeedback(): Launches {@link Intent#ACTION_VIEW} intent url given as irr:feedbackUrl</li>
 * </ul>
 * If the context passed to this listener is an activity, this listener
 * will use {@link Activity#startActivityForResult} with request codes
 * {@link net.mediavrog.irr.DefaultOnUserActionListener#RATE_REQUEST}
 * and {@link net.mediavrog.irr.DefaultOnUserActionListener#FEEDBACK_REQUEST}.
 */
public class DefaultOnUserActionListener implements IrrLayout.OnUserActionListener {

    public static int RATE_REQUEST = 7650;
    public static int FEEDBACK_REQUEST = 7651;

    private String mRatingUrl;

    private String mFeedbackUrl;

    public DefaultOnUserActionListener() {
        this(null, null);
    }

    public DefaultOnUserActionListener(String rateUrl, String feedbackUrl) {
        mRatingUrl = rateUrl;
        mFeedbackUrl = feedbackUrl;
    }

    @Override
    public void onRate(Context ctx) {
        if (mRatingUrl == null) mRatingUrl = getDefaultRatingUrl(ctx);
        if (mRatingUrl != null) startViewIntent(ctx, mRatingUrl, RATE_REQUEST);
        DidRateRule.trackRated(ctx);
    }

    @Override
    public void onFeedback(Context ctx) {
        if (mFeedbackUrl != null) startViewIntent(ctx, mFeedbackUrl, FEEDBACK_REQUEST);
    }

    @Override
    public void onDismiss(Context ctx, IrrLayout.State s) {
        if (s == IrrLayout.State.NUDGE) {
            DismissRule.trackDismissal(ctx);
        }
    }

    String getDefaultRatingUrl(Context ctx) {
        return "https://play.google.com/store/apps/details?id=" + ctx.getPackageName();
    }

    private void startViewIntent(Context ctx, String uri, int requestCode) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        if (ctx instanceof Activity) {
            ((Activity) ctx).startActivityForResult(i, requestCode);
        } else {
            ctx.startActivity(i);
        }
    }
}
