package com.cleanitkz.nurs.hscroll;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cleanitkz.nurs.hscroll.databinding.BasketActivityBinding;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {
    private  TextView totalScore, zak;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private BasketActivityBinding binding;
    private DatabaseHelper databaseHelper;
    private Button btn;
    private Intent orderIntent, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);
        back = new Intent(this, Second.class);
        ArrayList<Product> products = databaseHelper.getBasket();
        if (products.size()==0)
        {
            orderIntent = new Intent(this, ActivityOrder.class);
            setContentView(R.layout.basket_empty);
            zak = (TextView) findViewById(R.id.finish);
            btn = (Button) findViewById(R.id.next);
            zak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(back); finish();
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isConnected())
                    {
                        startActivity(orderIntent);
                        finish();
                    }
                    else {
                        Intent noConn = new Intent(BasketActivity.this, NoConnectionActivity.class);
                        startActivity(noConn);
                    }
                }
            });
        }
        else {
            orderIntent = new Intent(this, ActivityOrder.class);
            setContentView(R.layout.basket_activity);
            binding = DataBindingUtil.setContentView(this,R.layout.basket_activity);
            binding.setCleanit(Basket.cleanit);

            Button btn2 = (Button) findViewById(R.id.newbutton2);
            zak = (TextView) findViewById(R.id.zakryto);
            totalScore = (TextView) findViewById(R.id.totalScore);
            totalScore.setText(String.valueOf(databaseHelper.getScore())+" ₸");
            loadData();
            zak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(back); finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isConnected())
                    {
                        orderIntent = new Intent(BasketActivity.this, ActivityOrder.class);
                        startActivity(orderIntent);
                        finish();
                    }
                    else {
                        Intent noConn = new Intent(BasketActivity.this, NoConnectionActivity.class);
                        startActivity(noConn);
                    }
                }
            });
        }

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

    public void loadData(){
        recyclerView = (RecyclerView) findViewById(R.id.prodss);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(databaseHelper.getBasket(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
    }
    public void onBackPressed()
    {
        startActivity(back); finish();
    }
}
