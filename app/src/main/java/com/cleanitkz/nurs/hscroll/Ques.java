package com.cleanitkz.nurs.hscroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

public class Ques extends AppCompatActivity {
    private PDFView pdfView;
    private TextView lolo;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ques_activity);
        lolo = (TextView) findViewById(R.id.lolo);
        img = (ImageView) findViewById(R.id.finish);
        if(getIntent().getStringExtra("fileName").equals("CleanitkzPrivacyPolicy.pdf"))
        {
            lolo.setText("Политика конфиденциальности");
        }
        else {
            lolo.setText("Условия использования");
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pdfView = (PDFView) findViewById(R.id.quesPdf);
        pdfView.fromAsset(getIntent().getStringExtra("fileName")).load();
    }

    public void onBackPressed()
    {
        finish();
    }
}
