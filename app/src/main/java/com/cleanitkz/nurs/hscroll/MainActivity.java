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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    private fHelper fHelper;
    private Intent SecondIntent, WelcomeIntent;
    public DatabaseHelper databaseHelper;
    private TextView logo;
    public JSONArray ff;
    public int prodVersion;
    private String st,st2;
    private Dialog epicDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SecondIntent = new Intent(this, Second.class);
        WelcomeIntent = new Intent(this, WelcomeActivity.class);
        epicDialog = new Dialog(this);
        databaseHelper = new DatabaseHelper(this);
        logo = (TextView) findViewById(R.id.imageView19);
        Typeface face= Typeface.createFromAsset(getAssets(), "KGHAPPY.ttf");
        logo.setTypeface(face);
        fHelper = new fHelper(this);
        if (fHelper.readWelcomeFile().isEmpty()) {
            if(isConnected())
            {
                startActivity(WelcomeIntent);
                finish();
            }
            else {
                    setContentView(R.layout.no_connection);
                    TextView refresh = (TextView) findViewById(R.id.next);
                    TextView finish = (TextView) findViewById(R.id.finish);
                    finish.setVisibility(View.GONE);
                    Typeface face2= Typeface.createFromAsset(getAssets(), "KGHAPPY.ttf");
                    TextView logo2 = (TextView) findViewById(R.id.textView80);
                    logo2.setTypeface(face2);
                    refresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isConnected())
                            {
                                startActivity(WelcomeIntent);
                            }
                        }
                    });
            }
        } else {
            Basket.token = fHelper.readTokenFile();
            Basket.theCity = fHelper.readCityFile();
            if(isConnected())
            {
                GetVersion();
            }
            else {
                secondd();
            }
        }
    }


    public void GetVersion()
    {
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/clients/settings")
                .get()
                .addHeader("token", Basket.token)
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        GetNewToken();
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
                        JSONObject jsonObject = new JSONObject(s1);
                        if (jsonObject.has("android_version")) {
                            if (Basket.version.equals(jsonObject.getString("android_version"))) {
                                if (fHelper.readProductVersionFile().equals(String.valueOf(jsonObject.getInt("version")))) {
                                    if (fHelper.readPriceFile().isEmpty())
                                    {
                                        GetNewVersionProducts();
                                        prodVersion = jsonObject.getInt("version");
                                    }
                                    else {
                                        secondd();
                                    }
                                } else {
                                    GetNewVersionProducts();
                                    prodVersion = jsonObject.getInt("version");
                                }
                            }
                            else{
                                if(jsonObject.has("android_link"))
                                {
                                    String url = jsonObject.getString("android_link");
                                    sDialog(url);
                                }
                            }
                        } else {
                            if (fHelper.readProductVersionFile().equals(String.valueOf(jsonObject.getInt("version")))) {
                                secondd();
                            } else {
                                GetNewVersionProducts();
                                prodVersion = jsonObject.getInt("version");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }

    protected void sDialog(final String uri) {
        epicDialog.setContentView(R.layout.auth_logout);
        final Button asd = (Button) epicDialog.findViewById(R.id.buttonCancel);
        final Button asd3 = (Button) epicDialog.findViewById(R.id.buttonOkok);
        asd.setTextColor(Color.parseColor("#69aef3"));
        final TextView asd2 = (TextView) epicDialog.findViewById(R.id.changeText);
        asd2.setText("Обновить приложение");
        asd.setText("Обновить");
        asd3.setText("Потом");
        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(browserIntent);
            }
        });
        asd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                secondd();
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
    public void GetNewVersionProducts() {
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/clients/products")
                .get()
                .addHeader("token", Basket.token)
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        secondd();
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
                        ff = new JSONArray(s1);
                        categsList();
                        TimeUnit.MILLISECONDS.sleep(100);
                        fHelper.writeFile1(st);
                        fHelper.writeFile2(st2);
                        fHelper.writeProductVersionFile(String.valueOf(prodVersion));
                        secondd();
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }

    public void categsList() throws JSONException {
        Gson googleJson = new Gson();
        databaseHelper.onUpgrade(databaseHelper.getReadableDatabase(),0,1);
        ArrayList jsonObjList = googleJson.fromJson(String.valueOf(ff), ArrayList.class);
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        st="";
        st2="";
        for (int i=0; i<jsonObjList.size(); i++)
        {
            ArrayList<Catalog> CatalogArrayList = new ArrayList<>();
            JSONObject categoryObject = ff.getJSONObject(i);
            ArrayList jsonObjList2 = googleJson.fromJson(String.valueOf(categoryObject.getJSONArray("catalogs")), ArrayList.class);
            Category ctt1 = new Category(categoryObject.getString("name"), categoryObject.getString("value"));
            databaseHelper.addCategory(ctt1);
            for (int j =0; j<jsonObjList2.size();j++)
            {
                JSONObject catalogObj = categoryObject.getJSONArray("catalogs").getJSONObject(j);
                Catalog clg1 = new Catalog(catalogObj.getString("name"), catalogObj.getString("value"), catalogObj.getString("description"),replacePhoto(catalogObj.getString("imageUrl")));
                st = st +" "+clg1.getValue();

                databaseHelper.addCatalog(clg1, ctt1.getName());
                ArrayList jsonObjList3 = googleJson.fromJson(String.valueOf(catalogObj.getJSONArray("products")), ArrayList.class);
                for (int n=0; n<jsonObjList3.size();n++)
                {
                    JSONObject prooductObj = catalogObj.getJSONArray("products").getJSONObject(n);
                    String priceAta, priceAst, isAvaiAta, isAvaiAst;
                    JSONObject c1 = prooductObj.getJSONArray("cities").getJSONObject(0);
                    JSONObject c2 = prooductObj.getJSONArray("cities").getJSONObject(1);
                    if (c1.getString("value").equals("ata")){
                        isAvaiAta = c1.getString("isAvailable");
                        priceAta = c1.getString("price");
                        isAvaiAst = c2.getString("isAvailable");
                        priceAst = c2.getString("price");
                    } else {
                        isAvaiAta = c2.getString("isAvailable");
                        priceAta = c2.getString("price");
                        isAvaiAst = c1.getString("isAvailable");
                        priceAst = c1.getString("price");
                    }
                    Product prd = new Product(prooductObj.getString("measure"), isAvaiAta, priceAta, isAvaiAst, priceAst, prooductObj.getString("name"), prooductObj.getString("value"), replacePhoto(prooductObj.getString("imageUrl")), prooductObj.getString("description"));
                    databaseHelper.addProduct(prd, catalogObj.getString("name"));
                    clg1.products.add(prd);
                    st2 = st2 + " " +prooductObj.getString("value");
                }
                ctt1.catalogs.add(clg1);
            }
            categoryArrayList.add(ctt1);
        }
        fHelper.writePriceFile("ok");
    }

    public String replacePhoto(String str1){
        String myStr =str1.replace("-", "_");
        myStr = myStr.replaceAll(".png","");
        return myStr;
    }

    public void GetNewToken()    {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("secret", "cifer")
                .add("city", Basket.theCity)
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
                        GetVersion();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask.execute();
    }

    public void secondd(){
        this.startActivity(SecondIntent);
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
}