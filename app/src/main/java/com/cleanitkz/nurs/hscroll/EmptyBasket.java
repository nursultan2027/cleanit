package com.cleanitkz.nurs.hscroll;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

