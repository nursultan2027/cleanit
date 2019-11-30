package com.cleanitkz.nurs.hscroll;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class cityActivity extends AppCompatActivity {

        private String city;
        public DatabaseHelper databaseHelper;
        private fHelper fHelper;
        private TextView finish,almatyText, astanaText;
        private Intent back;
        private ImageView img1,img2;
        private ConstraintLayout constraintLayout1, constraintLayout2;
        private Button btn1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.city_activity);
            fHelper = new fHelper(this);
            databaseHelper = new DatabaseHelper(this);
            back = new Intent(this, AccountActivity.class);
            img1 = (ImageView) findViewById(R.id.imageView4);
            img2 = (ImageView) findViewById(R.id.imageView3);
            astanaText = findViewById(R.id.TextAstana);
            almatyText = findViewById(R.id.TextAlmaty);
            constraintLayout1 = findViewById(R.id.constraintLayout13);
            constraintLayout2 = findViewById(R.id.constraintLayout14);
                if (Basket.theCity.equals("ata"))
                {
                    img2.setImageResource(R.drawable.almaty_active_image);
                    img1.setImageResource(R.drawable.astana_image);
                    city="ata";
                    almatyText.setTextColor(Color.parseColor("#000000"));
                    astanaText.setTextColor(Color.parseColor("#727272"));
                }
                else {
                    img2.setImageResource(R.drawable.almaty_image);
                    img1.setImageResource(R.drawable.astana_active_image);
                    city="ast";
                    astanaText.setTextColor(Color.parseColor("#000000"));
                    almatyText.setTextColor(Color.parseColor("#727272"));
                }
            btn1 = (Button) findViewById(R.id.button);
            finish = (TextView) findViewById(R.id.finishsh);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(back); finish();
                }
            });

            constraintLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    city = "ata";
                    img2.setImageResource(R.drawable.almaty_active_image);
                    img1.setImageResource(R.drawable.astana_image);
                    almatyText.setTextColor(Color.parseColor("#000000"));
                    astanaText.setTextColor(Color.parseColor("#727272"));
                }
            });

            constraintLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    city = "ast";
                    img1.setImageResource(R.drawable.astana_active_image);
                    img2.setImageResource(R.drawable.almaty_image);
                    astanaText.setTextColor(Color.parseColor("#000000"));
                    almatyText.setTextColor(Color.parseColor("#727272"));
                }
            });

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(!city.isEmpty())
                        {
                            GetDate();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            }

    public void GetDate() throws JSONException {
        final OkHttpClient client = new OkHttpClient();
            final RequestBody foBody = new FormBody.Builder()
                    .add("city", city)
                    .build();
            final Request request3 = new Request.Builder()
                    .url("http://cleanitapp.kz/v1/api/clients/cities")
                    .put(foBody)
                    .addHeader("token", Basket.token)
                    .build();

            AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    try {
                        Response response2 = client.newCall(request3).execute();
                        if (!response2.isSuccessful()) {
                            GetNewToken();
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
                        try {
                            JSONObject jsonObject = new JSONObject(s1);
                            if(jsonObject.getString("message").toLowerCase().equals("edited"))
                            {
                                fHelper.writeCityFile(city);
                                Basket.theCity=city;
                                startActivity(back);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            asyncTask1.execute();

        }
    public void onBackPressed()
    {
        startActivity(back); finish();
    }

    public void GetNewToken()    {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("secret", "cifer")
                .add("city", Basket.theCity)
                .build();
        final Request request = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/auth/clients")
                .post(formBody)
                .build();
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        return null;
                    }
                    return response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    try {
                        JSONObject obj = new JSONObject(s);
                        String pageName = obj.getString("token");
                        fHelper.writeTokenFile(pageName);
                        Basket.token = pageName;
                        fHelper.writeAccountFile("");
                        GetDate();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask.execute();
    }

}
