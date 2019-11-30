package com.cleanitkz.nurs.hscroll;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class EmptyBasket extends AppCompatActivity {
    private Intent orderIntent, back;
    private Button btn1;
    private TextView zak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_empty);
        zak = (TextView) findViewById(R.id.finish);
        back = new Intent(this, Second.class);
        orderIntent = new Intent(this, ActivityOrder.class);
        btn1 = (Button) findViewById(R.id.next);
        zak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(back); finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected())
                {
                    startActivity(orderIntent);
                    btn1.setEnabled(false);
                    finish();
                }
                else {
                    Intent noConn = new Intent(EmptyBasket.this, NoConnectionActivity.class);
                    startActivity(noConn);
                }
            }
        });

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
    @Override
    public void onBackPressed()
    {
        startActivity(back); finish();
    }

}

