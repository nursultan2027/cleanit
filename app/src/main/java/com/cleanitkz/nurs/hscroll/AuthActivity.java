package com.cleanitkz.nurs.hscroll;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private fHelper fHelper;
    private TextView finish;
    private Intent back;
    private Intent AuthConfirmIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);

        back = new Intent(this, AccountActivity.class);
        AuthConfirmIntent = new Intent(this, AuthConfirmActivity.class);
        fHelper = new fHelper(this);
        finish = (TextView) findViewById(R.id.foxfox);
        editText = (EditText) findViewById(R.id.editText9);
        button = (Button) findViewById(R.id.gogo);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(back); finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Auth();
                    button.setEnabled(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Auth() throws JSONException {
        final OkHttpClient client = new OkHttpClient();
        RequestBody fBody = new FormBody.Builder()
                .add("mail", editText.getText().toString())
                .build();

        final Request request3 = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/clients/accounts")
                .put(fBody)
                .addHeader("token", fHelper.readTokenFile())
                .build();

        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request3).execute();
                    if (!response2.isSuccessful()) {
                        return null;
                    }
                    return response2.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                if (s1 != null) {
                    AuthConfirmIntent.putExtra("mail", editText.getText().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(s1);
                        if(jsonObject.getString("message").toLowerCase().equals("success"))
                        {
                            startActivity(AuthConfirmIntent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    button.setEnabled(true);
                }
            }
        };
        asyncTask1.execute();
    }
    public void onBackPressed()
    {
        startActivity(back);
        finish();
    }
}
