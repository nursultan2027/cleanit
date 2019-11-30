package com.cleanitkz.nurs.hscroll;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

public class CalendarActivity22 extends AppCompatActivity {
    private ImageView [] hours;
    private TextView[] hoursV, elec;
    private ImageView finish;
    private View [] views;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar1_2);
        finish = (ImageView) findViewById(R.id.finish);
        TextView title = (TextView) findViewById(R.id.tttitttle);
        title.setText("Время доставки");
        hours = new ImageView[10];
        elec = new TextView [10];
        hoursV = new TextView[10];
        views = new View[9];
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int [] res = new int[]{
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button6,
                R.id.button7,
                R.id.button8,
                R.id.button9,
                R.id.button5,
                R.id.button10,
                R.id.button11
        };

        int [] resV = new int []{
                R.id.day1,
                R.id.day2,
                R.id.h3,
                R.id.h4,
                R.id.h5,
                R.id.h6,
                R.id.day7,
                R.id.h8,
                R.id.h9,
                R.id.h10
        };

        int [] resElec = new int []{
                R.id.elec1,
                R.id.elec2,
                R.id.elec3,
                R.id.elec4,
                R.id.elec5,
                R.id.elec6,
                R.id.elec7,
                R.id.elec8,
                R.id.elec9,
                R.id.elec10
        };

        int [] resVV = new int []{
                R.id.view4,
                R.id.view5,
                R.id.view6,
                R.id.view7,
                R.id.view8,
                R.id.view9,
                R.id.view2,
                R.id.view3,
                R.id.view10
        };
        for (int j=0; j<10 ;j++) {
            hours[j] = (ImageView) findViewById(res[j]);
            elec[j] = (TextView) findViewById(resElec[j]);
            hours[j].setEnabled(false);
            hours[j].setVisibility(View.GONE);
            elec[j].setVisibility(View.GONE);
            hoursV[j] = (TextView) findViewById(resV[j]);
            hoursV[j].setVisibility(View.GONE);
            if (j < 9) {
                views[j] = (View) findViewById(resVV[j]);
                views[j].setVisibility(View.GONE);
            }
        }

        hours[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.time2 = "10:00 - 11:00";
                ChooseTime(0);
            }
        });

        hours[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.time2 = "11:00 - 12:00";
                ChooseTime(1);
            }
        });

        hours[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.time2 = "12:00 - 13:00";
                ChooseTime(2);
            }
        });

        hours[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.time2 = "13:00 - 14:00";
                ChooseTime(3);
            }
        });

        hours[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.time2 = "14:00 - 15:00";
                ChooseTime(4);
            }
        });

        hours[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.time2 = "15:00 - 16:00";
                ChooseTime(5);
            }
        });

        hours[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.time2 = "16:00 - 17:00";
                ChooseTime(6);
            }
        });

        hours[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.time2 = "17:00 - 18:00";
                ChooseTime(7);
            }
        });

        hours[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.time2 = "18:00 - 19:00";
                ChooseTime(8);
            }
        });

        hours[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.time2 = "19:00 - 20:00";
                ChooseTime(9);
            }
        });

        try {
            availableTime();
            if (Basket.time2!=null)
            {
                availableTime2();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void availableTime() throws JSONException {
        for (int j=0; j<10 ;j++) {
            switch (Basket.jsonObject2.getJSONArray("times").getString(j)) {
                case "10:00 - 11:00":
                    hours[0].setEnabled(true);
                    hours[0].setVisibility(View.VISIBLE);
                    hoursV[0].setVisibility(View.VISIBLE);
                    hours[0].setBackgroundResource(R.drawable.r_ectangle);
                    views[0].setVisibility(View.VISIBLE);
                    elec[0].setVisibility(View.VISIBLE);
                    break;
                case "11:00 - 12:00":
                    hours[1].setEnabled(true);
                    hours[1].setBackgroundResource(R.drawable.r_ectangle);
                    hours[1].setVisibility(View.VISIBLE);
                    hoursV[1].setVisibility(View.VISIBLE);
                    views[1].setVisibility(View.VISIBLE);
                    elec[1].setVisibility(View.VISIBLE);
                    break;
                case "12:00 - 13:00":
                    hours[2].setEnabled(true);
                    hours[2].setBackgroundResource(R.drawable.r_ectangle);
                    hours[2].setVisibility(View.VISIBLE);
                    hoursV[2].setVisibility(View.VISIBLE);
                    views[2].setVisibility(View.VISIBLE);
                    elec[2].setVisibility(View.VISIBLE);
                    break;
                case "13:00 - 14:00":
                    hours[3].setEnabled(true);
                    hours[3].setBackgroundResource(R.drawable.r_ectangle);
                    hours[3].setVisibility(View.VISIBLE);
                    hoursV[3].setVisibility(View.VISIBLE);
                    views[3].setVisibility(View.VISIBLE);
                    elec[3].setVisibility(View.VISIBLE);
                    break;
                case "14:00 - 15:00":
                    hours[4].setEnabled(true);
                    hours[4].setBackgroundResource(R.drawable.r_ectangle);
                    hours[4].setVisibility(View.VISIBLE);
                    hoursV[4].setVisibility(View.VISIBLE);
                    views[4].setVisibility(View.VISIBLE);
                    elec[4].setVisibility(View.VISIBLE);
                    break;
                case "15:00 - 16:00":
                    hours[5].setEnabled(true);
                    hours[5].setBackgroundResource(R.drawable.r_ectangle);
                    hours[5].setVisibility(View.VISIBLE);
                    hoursV[5].setVisibility(View.VISIBLE);
                    views[5].setVisibility(View.VISIBLE);
                    elec[5].setVisibility(View.VISIBLE);
                    break;
                case "16:00 - 17:00":
                    hours[6].setEnabled(true);
                    hours[6].setBackgroundResource(R.drawable.r_ectangle);
                    hours[6].setVisibility(View.VISIBLE);
                    hoursV[6].setVisibility(View.VISIBLE);
                    views[6].setVisibility(View.VISIBLE);
                    elec[6].setVisibility(View.VISIBLE);
                    break;
                case "17:00 - 18:00":
                    hours[7].setEnabled(true);
                    hours[7].setBackgroundResource(R.drawable.r_ectangle);
                    hours[7].setVisibility(View.VISIBLE);
                    hoursV[7].setVisibility(View.VISIBLE);
                    views[7].setVisibility(View.VISIBLE);
                    elec[7].setVisibility(View.VISIBLE);
                    break;
                case "18:00 - 19:00":
                    hours[8].setEnabled(true);
                    hours[8].setBackgroundResource(R.drawable.r_ectangle);
                    hours[8].setVisibility(View.VISIBLE);
                    hoursV[8].setVisibility(View.VISIBLE);
                    views[8].setVisibility(View.VISIBLE);
                    elec[8].setVisibility(View.VISIBLE);
                    break;
                case "19:00 - 20:00":
                    hours[9].setEnabled(true);
                    hours[9].setBackgroundResource(R.drawable.r_ectangle);
                    hours[9].setVisibility(View.VISIBLE);
                    hoursV[9].setVisibility(View.VISIBLE);
                    elec[9].setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    public void availableTime2() throws JSONException {
        for (int j=0; j<10 ;j++) {
            switch (Basket.time2) {
                case "10:00 - 11:00":
                    ChooseTime(0);
                    break;
                case "11:00 - 12:00":
                    ChooseTime(1);
                    break;
                case "12:00 - 13:00":
                    ChooseTime(2);
                    break;
                case "13:00 - 14:00":
                    ChooseTime(3);
                    break;
                case "14:00 - 15:00":
                    ChooseTime(4);
                    break;
                case "15:00 - 16:00":
                    ChooseTime(5);
                    break;
                case "16:00 - 17:00":
                    ChooseTime(6);
                    break;
                case "17:00 - 18:00":
                    ChooseTime(7);
                    break;
                case "18:00 - 19:00":
                    ChooseTime(8);
                    break;
                case "19:00 - 20:00":
                    ChooseTime(9);
                    break;
            }
        }
    }

    public void ChooseTime(int n)
    {
        for (int i=0; i<10; i++)
        {
            if (i!=n)
            {
                hours[i].setImageResource(R.drawable.r_ectangle);
                elec[i].setVisibility(View.VISIBLE);
            }
            else {
                hours[i].setImageResource(R.drawable.ic_gg1);
                hours[i].setBackgroundResource(R.color.colorDef);
                elec[i].setVisibility(View.GONE);
            }
        }
        Basket.chang.recreate();
    }
    public void onBackPressed()
    {
        finish();
    }
}