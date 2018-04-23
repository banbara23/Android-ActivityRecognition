package c.ikemura.leanandroidactivityrecognition;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SlackHelper {
    private static final String TAG = "SlackHelper";

    public static void post(String message) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token", "xoxp-108876200531-108268499681-151256661746-e3ea10493a1bdab251f69d37435bf853")
                .add("channel", "notification")
                .add("text", message)
                .add("username", "android-bot")
                .add("icon_emoji", ":monkey_face:")
                .build();

        Request request = new Request.Builder()
                .url("https://slack.com/api/chat.postMessage")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.message());
            }
        });
    }
}
