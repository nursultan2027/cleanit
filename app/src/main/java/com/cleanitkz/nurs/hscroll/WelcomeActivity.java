package com.cleanitkz.nurs.hscroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends Activity{
    private Button next;
    private Intent second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        second = new Intent(this, WelcomeCityActivity.class);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next.setEnabled(false);
                setContentView(R.layout.second_welcome_page);
                next = (Button) findViewById(R.id.next);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        next.setEnabled(false);
                        startActivity(second);
                        finish();
                    }
                });
            }
        });
    }
}
