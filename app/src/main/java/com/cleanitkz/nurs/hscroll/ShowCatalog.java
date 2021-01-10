package com.cleanitkz.nurs.hscroll;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cleanitkz.nurs.hscroll.databinding.ShowCatalogBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShowCatalog extends AppCompatActivity {

    private Toolbar mToolbar;
    private DatabaseHelper databaseHelper;
    private InputValidation inputValidation;
    private ConstraintLayout fm, fm2;
    private fHelper fHelper;
    private boolean check;
    private ShowCatalogBinding binding;
    private TextView txt;
    private Intent BasketIntent, back;
    private String value;
    private TextView nameL, disL, logo;
    private ImageView linearLayout, finish;
    private EditText edPhone, edEmail, edCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Catalog myObj = (Catalog) getIntent().getParcelableExtra(Catalog.class.getCanonicalName());
        value = myObj.getValue();
        fHelper = new fHelper(this);
        Basket.chang= this;
        databaseHelper = new DatabaseHelper(this);
        back = new Intent(this, Second.class);
        BasketIntent = new Intent(this, BasketActivity.class);
        inputValidation = new InputValidation(this);
        if (myObj.products.size()>0)
        {
            setContentView(R.layout.show_catalog);
            binding = DataBindingUtil.setContentView(this, R.layout.show_catalog);
            binding.setCleanit(Basket.cleanit);
            fm = (ConstraintLayout) findViewById(R.id.basket_footer);
            fm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBasketClick();
                }
            });
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            finish = (ImageView) findViewById(R.id.foxfox);
            nameL = (TextView) findViewById(R.id.name);
            logo= (TextView)findViewById(R.id.imageView19);
            Typeface face= Typeface.createFromAsset(getAssets(), "KGHAPPY.ttf");
            logo.setTypeface(face);
            disL = (TextView) findViewById(R.id.dis);
            txt = (TextView) findViewById(R.id.texttext);
            ImageView img = (ImageView) findViewById(R.id.imageView20);
            TextView txtx = (TextView) findViewById(R.id.txtxt);
            TextView txx = (TextView) findViewById(R.id.basket_score);
            if (databaseHelper.getScore()>1)
            {
                txx.setVisibility(View.GONE);
                txtx.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                txt.setVisibility(View.VISIBLE);
                txt.setText(String.valueOf(databaseHelper.getScore())+" ₸");
            }
            else {
                txx.setVisibility(View.VISIBLE);
                txtx.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
                txt.setVisibility(View.GONE);
                txx.setText("Пропустить выбор товаров");
            }
            linearLayout = (ImageView) findViewById(R.id.fon);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(back);
                    finish();
                }
            });
            setSupportActionBar(mToolbar);
            String id = myObj.getImg();
            int resID = getResources().getIdentifier(id,"drawable", getPackageName());
            String name = myObj.getName();
            String dis = myObj.getDiscription();
            linearLayout.setBackgroundResource(resID);
            ArrayList<Product> products = myObj.products;
            nameL.setText(name);
            disL.setText(dis);
            ProductAdapter adp = new ProductAdapter(this.getApplication(),R.layout.item2, products);
            LinearLayout listViewReplacement = (LinearLayout) findViewById(R.id.lidea);
            for (int i = 0; i < adp.getCount(); i++) {
                View view = adp.getView(i, null, listViewReplacement);
                listViewReplacement.addView(view);
            }

        }
        else
        {
            setContentView(R.layout.show_businessu_catalog);
            check = true;
            fm = (ConstraintLayout) findViewById(R.id.basket_footer);
            fm2 = (ConstraintLayout) findViewById(R.id.constraintLayout39);
            logo= (TextView)findViewById(R.id.imageView19);
            Typeface face= Typeface.createFromAsset(getAssets(), "KGHAPPY.ttf");
            logo.setTypeface(face);
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
            fm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBasketClick();
                }
            });
            fm2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                if(check)
                {
                    if (isConnected())
                    {
                        if(CheckForm()){
                            check = false;
                            SendForm();
                        }
                    }
                    else {
                        Intent noConn = new Intent(ShowCatalog.this, NoConnectionActivity.class);
                        startActivity(noConn);
                    }
                }
                }
            });
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            finish = (ImageView) findViewById(R.id.foxfox);
            nameL = (TextView) findViewById(R.id.name);
            disL = (TextView) findViewById(R.id.dis);
            txt = (TextView) findViewById(R.id.texttext);
            ImageView img = (ImageView) findViewById(R.id.imageView20);
            TextView txtx = (TextView) findViewById(R.id.txtxt);
            TextView txx = (TextView) findViewById(R.id.basket_score);
            if (databaseHelper.getScore()>0)
            {
                txx.setVisibility(View.GONE);
                txtx.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                txt.setVisibility(View.VISIBLE);
                txt.setText(String.valueOf(databaseHelper.getScore())+" ₸");
            }
            else {
                txx.setVisibility(View.VISIBLE);
                txtx.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
                txt.setVisibility(View.GONE);
                txx.setText("Пропустить выбор товаров");
            }

            edPhone = (EditText) findViewById(R.id.editText11);
            edEmail = (EditText) findViewById(R.id.editTextMail);
            edCompany = (EditText) findViewById(R.id.editText10);
            linearLayout = (ImageView) findViewById(R.id.fon);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(back);
                    finish();
                }
            });
            setSupportActionBar(mToolbar);
            String id = myObj.getImg();
            int resID = getResources().getIdentifier(id,"drawable", getPackageName());
            String name = myObj.getName();
            linearLayout.setBackgroundResource(resID);
            nameL.setText(name);
            disL.setText("Заполните заявку и мы обязательно свяжемся с Вами!");
            if(!fHelper.readAccountFile().isEmpty())
            {
                try {
                    JSONObject accObject = new JSONObject(fHelper.readAccountFile());
                    edPhone.setText(accObject.getString("phone"));
                    edEmail.setText(accObject.getString("mail"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onBasketClick() {
        this.startActivity(BasketIntent);
        finish();
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
    public boolean CheckForm(){
        if(inputValidation.isInputEditTextFilled(edCompany) && inputValidation.validatePhoneNumber(edPhone)
                && inputValidation.isInputEditTextEmail(edEmail))
        {
            return true;
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Проверьте правильность данных", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }
    public void SendForm(){
        final OkHttpClient client = new OkHttpClient();

        final RequestBody foBody = new FormBody.Builder()
                .add("mail", edEmail.getText().toString())
                .add("phone", edPhone.getText().toString())
                .add("company", edCompany.getText().toString())
                .add("value", value)
                .build();

        final Request request3 = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/clients/applications")
                .post(foBody)
                .addHeader("token", Basket.token)
                .build();

        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request3).execute();
                    if (!response2.isSuccessful()) {
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
                        JSONObject object = new JSONObject(s1);
                        String s = object.getString("message");
                        if (s.toUpperCase().equals("CREATED"))
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Заявка отправлена", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            check = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }
    public void onBackPressed()
    {
        startActivity(back);
        finish();
    }
}