package com.cleanitkz.nurs.hscroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QandAActivity extends Activity {
    private ImageView finish, map;
    private Intent back;
    private TextView city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back = new Intent(this, AccountActivity.class);
        if (getIntent().getStringExtra("target").equals("q"))
        {
            setContentView(R.layout.ques_ans);
            finish = (ImageView) findViewById(R.id.finish);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(back); finish();
                }
            });
        }
        else {
            setContentView(R.layout.area_activity);
            finish = (ImageView) findViewById(R.id.finish);
            map = (ImageView) findViewById(R.id.map);
            city = (TextView) findViewById(R.id.cityMap);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(back); finish();
                }
            });
            if(Basket.theCity.equals("ast"))
            {
                map.setImageResource(R.drawable.astana_map);
                city.setText("Астана");
            }
            else {
                map.setImageResource(R.drawable.almaty_map);
                city.setText("Алматы");
            }
        }
    }

    public void onBackPressed()
    {
        startActivity(back); finish();
    }
}