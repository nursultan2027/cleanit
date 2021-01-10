package com.cleanitkz.nurs.hscroll;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccountActivity extends AppCompatActivity {
    public Intent back, cityIntent, EmailIntent, regIntent, authIntent, authIntent2, quIntent, editIntent, qandA;
    private fHelper fHelper;
    private boolean auth;
    private Dialog epicDialog;
    private TextView txt, finish, textView, textView2, textView4, textView4b;
    public ConstraintLayout contactUs, area, qanaA, constraintLayout, constraintLayout2, constraintLayouta1, constraintLayout3, constraintLayout4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        fHelper = new fHelper(this);
        txt = (TextView) findViewById(R.id.CityTextView);
        finish = (TextView) findViewById(R.id.textView31);
        back = new Intent(this, Second.class);
        cityIntent = new Intent(this,cityActivity.class);
        regIntent = new Intent(this, RegistrationActivity.class);
        authIntent = new Intent(this, AuthActivity.class);
        authIntent2 = new Intent(this, AuthConfirmActivity.class);
        editIntent = new Intent(this, EditAccActivity.class);
        quIntent = new Intent(this, Ques.class);
        qandA = new Intent(this, QandAActivity.class);
        EmailIntent = new Intent(this, EmailSend.class);
        contactUs = (ConstraintLayout) findViewById(R.id.constraintLayout55);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout5);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        qanaA = (ConstraintLayout) findViewById(R.id.constraintLayout3);
        area = (ConstraintLayout) findViewById(R.id.areaConst);
        epicDialog = new Dialog(this);
        constraintLayouta1 = (ConstraintLayout) findViewById(R.id.constraintLayouta1);
        constraintLayout2 = (ConstraintLayout) findViewById(R.id.constraintLayout2);
        constraintLayout3 = (ConstraintLayout) findViewById(R.id.constraintLayout33);
        constraintLayout4 = (ConstraintLayout) findViewById(R.id.constraintLayout34);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView4b = (TextView) findViewById(R.id.textViewb4);
        auth = fHelper.readAccountFile().isEmpty();
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toActivity(EmailIntent);finish();
            }
        });
        qanaA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qandA.putExtra("target", "q");
                toActivity(qandA);
                finish();
            }
        });
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qandA.putExtra("target", "A");
                toActivity(qandA);
                finish();
            }
        });
        constraintLayouta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected())
                {
                    if(auth) {
                        toActivity(authIntent);
                        finish();
                    }
                    else
                        dialog();
                }
                else {
                    Intent noConn = new Intent(AccountActivity.this, NoConnectionActivity.class);
                    startActivity(noConn);
                }
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isConnected())
                {
                    toActivity(cityIntent);
                    finish();
                }
                else {
                    Intent noConn = new Intent(AccountActivity.this, NoConnectionActivity.class);
                    startActivity(noConn);
                }
            }
        });
        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected())
                {
                    if(auth)
                    {
                        toActivity(regIntent);
                        finish();
                    }
                    else {
                        toActivity(editIntent);
                        finish();
                    }
                }
                else {
                    Intent noConn = new Intent(AccountActivity.this, NoConnectionActivity.class);
                    startActivity(noConn);
                }
            }
        });
        constraintLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quIntent.putExtra("fileName", "contract_with_cleanit_kz.pdf");
                toActivity(quIntent);
            }
        });
        constraintLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quIntent.putExtra("fileName", "CleanitkzPrivacyPolicy.pdf");
                toActivity(quIntent);
            }
        });
        String savedText = fHelper.readCityFile();
        if (savedText.equals("ata"))
            txt.setText("Алматы");
        else
            txt.setText("Астана");

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(back);
                finish();
            }
        });
        if(!auth)
        {
            try {
                JSONObject accObject = new JSONObject(fHelper.readAccountFile());
                textView.setText(accObject.getString("firstName")+" "+accObject.getString("lastName"));
                textView2.setText(accObject.getString("mail"));
                textView4.setText("Редактировать");
                textView4b.setText("Выйти");
                textView4b.setTextColor(Color.parseColor("#d9552f"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void SetViews()    {
        auth = fHelper.readAccountFile().isEmpty();
        textView.setText("Гость");
        textView2.setText("Необходимо авторизоваться");
        textView4.setText("Зарегистрироваться");
        textView4b.setText("Войти");
        textView4b.setTextColor(Color.parseColor("#000000"));
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
        }
        return connected;
    }
    public void toActivity(Intent intent) {
        this.startActivity(intent);
    }
    void LogOut(){
    final    OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("secret", "cifer")
                    .add("city", fHelper.readCityFile())
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
                            SetViews();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            asyncTask.execute();
    }
    public void onBackPressed()
    {
        startActivity(back);
        finish();
    }

    public void dialog()
    {
        epicDialog.setContentView(R.layout.auth_logout);
        final Button asd = (Button) epicDialog.findViewById(R.id.buttonOkok);
        final Button asd2 = (Button) epicDialog.findViewById(R.id.buttonCancel);

        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
        asd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
                epicDialog.dismiss();
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
}
