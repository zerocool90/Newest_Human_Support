package test.newest_human_support;

import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class OkHttpHandler extends AsyncTask<String, Void, String> {
    OkHttpClient client = new OkHttpClient();
    String userName, passWord;

    public OkHttpHandler(String username, String password) {
        this.userName = username;
        this.passWord = password;
    }

    @Override
    protected String doInBackground(String... params) {

        RequestBody formBody = new FormEncodingBuilder()
                .add("username", userName)
                .add("password", passWord)
                .build();
        Request request = new Request.Builder()
                .url(params[0]).post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response.toString());
            return response.body().string();

        } catch (Exception e) {
        }

        return null;
    }
}