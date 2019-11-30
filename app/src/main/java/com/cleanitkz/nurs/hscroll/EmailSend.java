package com.cleanitkz.nurs.hscroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EmailSend extends Activity{
    private Button next;
    private Intent send, back;
    private TextView finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        finish = (TextView) findViewById(R.id.finish);
        back = new Intent(this, AccountActivity.class);
        send = new Intent(Intent.ACTION_SEND);
        send.putExtra(Intent.EXTRA_EMAIL, new String[]{"admin@cleanit.kz"});
        send.putExtra(Intent.EXTRA_SUBJECT, "Cleanit App");
        send.setType("text/plain");
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(send);
              next.setEnabled(false);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(back); finish();
            }
        });
    }
    public void onBackPressed()
    {
        startActivity(back); finish();
    }
}
