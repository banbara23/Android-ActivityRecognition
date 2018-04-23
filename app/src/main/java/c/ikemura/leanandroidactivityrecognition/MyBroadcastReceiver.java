package c.ikemura.leanandroidactivityrecognition;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // ActivityTransitionResultを持っているか？
        if (!ActivityTransitionResult.hasResult(intent)) {
            return;
        }
        ActivityTransitionResult transitionResult = ActivityTransitionResult.extractResult(intent);
        for (ActivityTransitionEvent event : transitionResult.getTransitionEvents()) {
            postToSlack(event);
        }
    }

    private void postToSlack(ActivityTransitionEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("ActionType: " + getActionName(event.getActivityType()) + "\n");
        sb.append("TransitionType(): " + getTransitionName(event.getTransitionType()) + "\n");
//        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d(TAG, log);
        SlackHelper.post(log);
    }

    private String getTransitionName(int transitionType) {
        String name = "";
        switch (transitionType) {
            case ActivityTransition.ACTIVITY_TRANSITION_ENTER:
                name = "実行";
                break;
            case ActivityTransition.ACTIVITY_TRANSITION_EXIT:
                name = "終了";
                break;
        }
        return name;
    }

    private String getActionName(int activityType) {
        String name = "";
        switch (activityType) {
            case DetectedActivity.IN_VEHICLE:
                name = "車";
                break;
            case DetectedActivity.ON_BICYCLE:
                name = "自転車";
                break;
            case DetectedActivity.RUNNING:
                name = "走る";
                break;
            case DetectedActivity.STILL:
                name = "停止";
                break;
            case DetectedActivity.WALKING:
                name = "徒歩";
                break;
        }
        return name;
    }
}