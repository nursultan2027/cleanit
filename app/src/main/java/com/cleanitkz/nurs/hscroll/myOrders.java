package com.cleanitkz.nurs.hscroll;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class myOrders extends AppCompatActivity {
    private fHelper fHelper;
    private TextView finish;
    private Intent back;
    public JSONArray ff;
    private ArrayList<Order> orders = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back = new Intent(this, Second.class);
        fHelper = new fHelper(this);
        try {
            GetOrders();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void GetOrders() throws JSONException {
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/clients/orders")
                .get()
                .addHeader("token",Basket.token)
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
                        ff = new JSONArray(s1);
                        categsList(ff);
                        setView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }
      public void categsList(JSONArray ff) throws JSONException {
        Gson googleJson = new Gson();
        ArrayList jsonObjList = googleJson.fromJson(String.valueOf(ff), ArrayList.class);
        for (int i=0; i<jsonObjList.size(); i++)
        {
            JSONObject object = ff.getJSONObject(i);
            JSONObject date1object = object.getJSONObject("deliveryTime");
            JSONObject date2object = object.getJSONObject("collectionTime");
            JSONArray basket = object.getJSONArray("basket");
            String date1 = date1object.getString("day")+" "+ getMonthName(date1object.getString("month"))+" "+date1object.getString("year")+"г., "+date1object.getString("time");
            String date2 = date2object.getString("day")+" "+ getMonthName(date2object.getString("month"))+" "+date2object.getString("year")+"г., "+date2object.getString("time");
            ArrayList jsonObjList1 = googleJson.fromJson(String.valueOf(basket), ArrayList.class);
            ArrayList<Product> products = new ArrayList<>();
            int price=0;
            for (int j=0; j<jsonObjList1.size();j++)
            {
                String priceAta, priceAst, isAvaiAta, isAvaiAst;
                JSONObject jsonObject1 = basket.getJSONObject(j);
                JSONObject jsonObject2 = jsonObject1.getJSONObject("productID");
                JSONObject c1 = jsonObject2.getJSONArray("cities").getJSONObject(0);
                JSONObject c2 = jsonObject2.getJSONArray("cities").getJSONObject(1);
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

                Product prd = new Product(object.getString("city"), jsonObject1.getInt("count"), isAvaiAta, priceAta, isAvaiAst, priceAst, jsonObject2.getString("name"), jsonObject2.getString("value"), replacePhoto(jsonObject2.getString("imageUrl")), jsonObject2.getString("description"));
                String pp;

                if(object.getBoolean("hasPromo"))
                    pp = jsonObject1.getString("price");
                else{
                    if (object.getString("city").equals("ata")) {
                        pp = prd.getPrice1();
                    } else {
                        pp = prd.getPrice2();
                    }
                }

                price += prd.getCount()* Integer.parseInt(pp);
                products.add(prd);
            }
            Order order = new Order(object.getString("city"),object.getString("number"),getDate(object.getString("createdAt")),
                    String.valueOf(price),
                    object.getString("status"),
                    date1, date2, object.getString("street"), object.getString("home"),
                    object.getString("flat"));
            order.products = products;
            orders.add(order);
        }
    }
    public String replacePhoto(String str1){
        String myStr =str1.replace("-", "_");
        myStr = myStr.replaceAll(".png","");
        return myStr;
    }
    public void setView() {
        if (orders.size()>0)
        {
            setContentView(R.layout.my_orders_activity);
            finish = (TextView) findViewById(R.id.foxfox);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(back); finish();
                }
            });
            OrderAdapter adp = new OrderAdapter(this.getApplication(),R.layout.item_order, orders);
            ListView listView = (ListView) findViewById(R.id.leadd);
            listView.setAdapter(adp);
        } else {
            setContentView(R.layout.order_empty);
            finish = (TextView) findViewById(R.id.finish);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(back); finish();
                }
            });
        }
    }
    public String getDate(String s)    {
        String [] ss = s.split("T");
        String [] ss2 = ss[0].split("-");
        if (ss2[1].equals("01"))
        {
            ss2[1] = "Января";
        } else {
            if (ss2[1].equals("02"))
            {
                ss2[1] = "Февраля";
            }
            else {
                if (ss2[1].equals("03"))
                {
                    ss2[1] = "Марта";
                }
                else {
                    if (ss2[1].equals("04"))
                    {
                        ss2[1] = "Апреля";
                    } else {
                        if (ss2[1].equals("05"))
                        {
                            ss2[1] = "Мая";
                        } else {
                            if (ss2[1].equals("06"))
                            {
                                ss2[1] = "Июня";
                            }
                            else {
                                if (ss2[1].equals("07"))
                                {
                                    ss2[1] = "Июля";
                                } else {
                                    if (ss2[1].equals("08"))
                                    {
                                        ss2[1] = "Августа";
                                    }
                                    else {
                                        if (ss2[1].equals("09"))
                                        {
                                            ss2[1] = "Сентября";
                                        }
                                        else {
                                            if (ss2[1].equals("10"))
                                            {
                                                ss2[1] = "Октября";
                                            }
                                            else {
                                                if (ss2[1].equals("11"))
                                                {
                                                    ss2[1] = "Ноября";
                                                }
                                                else {
                                                    if (ss2[1].equals("12"))
                                                    {
                                                        ss2[1] = "Декабря";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ss2[2] +" "+ss2[1]+" "+ss2[0];
    }
    public String getMonthName(String ss2)    {
        if (ss2.equals("0"))
        {
            ss2 = "Января";
        } else {
            if (ss2.equals("1"))
            {
                ss2 = "Февраля";
            }
            else {
                if (ss2.equals("2"))
                {
                    ss2= "Марта";
                }
                else {
                    if (ss2.equals("3"))
                    {
                        ss2= "Апреля";
                    } else {
                        if (ss2.equals("4"))
                        {
                            ss2= "Мая";
                        } else {
                            if (ss2.equals("5"))
                            {
                                ss2= "Июня";
                            }
                            else {
                                if (ss2.equals("6"))
                                {
                                    ss2 = "Июля";
                                } else {
                                    if (ss2.equals("7"))
                                    {
                                        ss2 = "Августа";
                                    }
                                    else {
                                        if (ss2.equals("8"))
                                        {
                                            ss2 = "Сентября";
                                        }
                                        else {
                                            if (ss2.equals("9"))
                                            {
                                                ss2= "Октября";
                                            }
                                            else {
                                                if (ss2.equals("10"))
                                                {
                                                    ss2= "Ноября";
                                                }
                                                else {
                                                    if (ss2.equals("11"))
                                                    {
                                                        ss2 = "Декабря";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ss2;
    }
    public void onBackPressed()
    {
        startActivity(back);
        finish();
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
                        GetOrders();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask.execute();
    }
}