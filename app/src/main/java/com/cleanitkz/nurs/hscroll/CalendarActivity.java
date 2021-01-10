package com.cleanitkz.nurs.hscroll;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CalendarActivity extends AppCompatActivity {
    private TextView [] days, elec;
    private ImageView[]  buttons;
    public JSONArray ff1;
    private fHelper fHelper;
    private View view1;
    private ImageView finish;
    final String SAVED_TEXT = "token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            availableTime();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }

    public void Choose(int n)
    {
        for (int i=0;i<7;i++)
        {
            if (i!=n)
            {
                buttons[i].setImageResource(R.drawable.r_ectangle);
                elec[i].setVisibility(View.VISIBLE);
            }
            else {
                buttons[i].setImageResource(R.drawable.ic_gg1);
                buttons[i].setBackgroundResource(R.color.colorDef);
                elec[i].setVisibility(View.GONE);
            }
        }
        Basket.chang.recreate();
    }
    public void onBackPressed()
    {
        finish();
    }
    public void availableTime() throws JSONException {
        final OkHttpClient client = new OkHttpClient();
        RequestBody fBody = new FormBody.Builder()
                .add("type", "COLLECTION")
                .build();

        final Request request3 = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/clients/orders/available/time")
                .put(fBody)
                .addHeader(SAVED_TEXT, Basket.token)
                .build();

        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                setContentView(R.layout.activity_main);
            }
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
                        setContentView(R.layout.calendar1);
                        finish = (ImageView) findViewById(R.id.finish);
                        fHelper = new fHelper(CalendarActivity.this);
                        days = new TextView[7];
                        elec = new TextView[7];
                        buttons = new ImageView[7];
                        buttons[0] = (ImageView) findViewById(R.id.button2);
                        buttons[1] = (ImageView) findViewById(R.id.button3);
                        buttons[2] = (ImageView) findViewById(R.id.button4);
                        buttons[3] = (ImageView) findViewById(R.id.button6);
                        buttons[4] = (ImageView) findViewById(R.id.button7);
                        buttons[5] = (ImageView) findViewById(R.id.button8);
                        buttons[6] = (ImageView) findViewById(R.id.button9);
                        elec[0] = (TextView) findViewById(R.id.elec1);
                        elec[1] = (TextView) findViewById(R.id.elec2);
                        elec[2] = (TextView) findViewById(R.id.elec3);
                        elec[3] = (TextView) findViewById(R.id.elec4);
                        elec[4] = (TextView) findViewById(R.id.elec5);
                        elec[5] = (TextView) findViewById(R.id.elec6);
                        elec[6] = (TextView) findViewById(R.id.elec7);
                        view1 = (View) findViewById(R.id.view9);
                        finish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                        buttons[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Basket.jsonObject = ff1.getJSONObject(0);
                                    Basket.time1=null;
                                    Basket.time2 = null;
                                    Basket.jsonObject2 = null;
                                    Choose(0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        buttons[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Basket.jsonObject = ff1.getJSONObject(1);
                                    Basket.time1=null;
                                    Basket.time2 = null;
                                    Basket.jsonObject2 = null;
                                    Choose(1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        buttons[2].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Basket.jsonObject = ff1.getJSONObject(2);
                                    Basket.time1=null;
                                    Basket.time2 = null;
                                    Basket.jsonObject2 = null;
                                    Choose(2);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        buttons[3].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Basket.jsonObject = ff1.getJSONObject(3);
                                    Basket.time1=null;
                                    Basket.time2 = null;
                                    Basket.jsonObject2 = null;
                                    Choose(3);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        buttons[4].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Basket.jsonObject = ff1.getJSONObject(4);
                                    Basket.time1=null;
                                    Basket.time2 = null;
                                    Basket.jsonObject2 = null;
                                    Choose(4);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                        buttons[5].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Basket.jsonObject = ff1.getJSONObject(5);
                                    Basket.time1=null;
                                    Basket.time2 = null;
                                    Basket.jsonObject2 = null;
                                    Choose(5);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        buttons[6].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Basket.jsonObject = ff1.getJSONObject(6);
                                    Basket.time1=null;
                                    Basket.time2 = null;
                                    Basket.jsonObject2 = null;
                                    Choose(6);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        int [] res = new int[]{
                                R.id.day1,
                                R.id.day2,
                                R.id.day3,
                                R.id.day4,
                                R.id.day5,
                                R.id.day6,
                                R.id.day7
                        };

                        for (int j=0; j<7 ;j++)
                        {
                            days[j] = (TextView) findViewById(res[j]);
                        }
                        ff1 = new JSONArray(s1);
                        Gson googleJson = new Gson();
                        ArrayList jsonObjList = googleJson.fromJson(String.valueOf(ff1), ArrayList.class);
                        for (int j=0; j<7 ;j++)
                        {
                            days[j].setText(ff1.getJSONObject(j).getString("date_title"));
                            if(Basket.jsonObject!=null)
                            {
                                if(Basket.jsonObject.toString().equals(ff1.getJSONObject(j).toString()))
                                {
                                    Choose(j);
                                }
                            }
                            if (jsonObjList.size()<7)
                            {
                                days[6].setVisibility(View.GONE);
                                buttons[6].setVisibility(View.GONE);
                                view1.setVisibility(View.GONE);
                            }
                        }

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
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
                        availableTime();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask.execute();
    }
}