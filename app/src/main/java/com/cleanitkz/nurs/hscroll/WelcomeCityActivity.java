package com.cleanitkz.nurs.hscroll;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class WelcomeCityActivity extends AppCompatActivity {

        private String city;
        final String SAVED_TEXT = "token";
        public JSONArray ff;
        private String st,st2;
        public String pageName;
        public DatabaseHelper databaseHelper;
        private OkHttpClient client = new OkHttpClient();
        private fHelper fHelper;
        private TextView finish, almatyText, astanaText, finishsh;
        private Intent second;
        private ImageView img1,img2;
        private ConstraintLayout constraintLayout1, constraintLayout2;
        private Button btn1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.city_activity);
            city = "";
            fHelper = new fHelper(this);
            databaseHelper = new DatabaseHelper(this);
            second = new Intent(this, Second.class);
            img1 = (ImageView) findViewById(R.id.imageView4);
            img2 = (ImageView) findViewById(R.id.imageView3);
            finishsh = (TextView) findViewById(R.id.finishsh);
            finishsh.setVisibility(View.GONE);
            constraintLayout1 = findViewById(R.id.constraintLayout13);
            constraintLayout2 = findViewById(R.id.constraintLayout14);
            astanaText = findViewById(R.id.TextAstana);
            almatyText = findViewById(R.id.TextAlmaty);
            img2.setImageResource(R.drawable.almaty_image);
            img1.setImageResource(R.drawable.astana_image);
            btn1 = (Button) findViewById(R.id.button);
            finish = (TextView) findViewById(R.id.finishsh);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            constraintLayout1.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    city = "ata";
                    img2.setImageResource(R.drawable.almaty_active_image);
                    img1.setImageResource(R.drawable.astana_image);
                    almatyText.setTextColor(Color.parseColor("#000000"));
                    astanaText.setTextColor(Color.parseColor("#727272"));
                }
            });

            constraintLayout2.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    city = "ast";
                    img1.setImageResource(R.drawable.astana_active_image);
                    img2.setImageResource(R.drawable.almaty_image);
                    astanaText.setTextColor(Color.parseColor("#000000"));
                    almatyText.setTextColor(Color.parseColor("#727272"));
                }
            });

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(!city.isEmpty())
                        {
                            GetDate();
                            btn1.setEnabled(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    public void GetDate() throws JSONException {
        final OkHttpClient client = new OkHttpClient();
                final AsyncTask<Void, Void, Void> anTsk = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        setContentView(R.layout.activity_main);
                        PostToken();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        super.onPostExecute(result);
                        try {
                            GetProductVersion();
                            GetProducts();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                anTsk.execute();
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

    public void PostToken() {
            fHelper.writeCityFile(city);
            Basket.theCity = city;
        RequestBody formBody = new FormBody.Builder()
                .add("secret", "cifer")
                .add("city", city)
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
                        pageName = obj.getString(SAVED_TEXT);
                        fHelper.writeTokenFile(pageName);
                        Basket.token = pageName;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask.execute();
    }

    public void GetProductVersion()    {
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
                         fHelper.writeProductVersionFile(String.valueOf(jsonObject.getInt("version")));
                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }

    public void GetProducts() throws JSONException {

        final Request request2 = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/clients/products")
                .get()
                .addHeader(SAVED_TEXT, Basket.token)
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
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
                        ff = new JSONArray(s1);
                        categsList();
                        TimeUnit.MILLISECONDS.sleep(100);
                        fHelper.writeFile1(st);
                        fHelper.writeFile2(st2);
                        fHelper.wrteWelcomeFile("OK");
                        startActivity(second);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }
}
