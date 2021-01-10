package com.cleanitkz.nurs.hscroll;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
        import android.os.Bundle;
        import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ImageView;
import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
        import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivityOrder extends Activity {
    private ConstraintLayout calendar1, calendar2, calendar3, calendar4, order, promo;
    private TextView text1, text2, pdf, text3, text4, totalScre, promocod;
    private EditText editStreet, editHouse, editKv,editInstruction, editFIO, editTel, editEmail, editInstruction2;
    private Intent CalendarIntent, CalendarIntent12, CalendarIntent2, CalendarIntent22, pdfIntent;
    private CheckBox checkBox;
    private DatabaseHelper databaseHelper;
    private fHelper fHelper;
    private ImageView finish;

    private Intent backSec, back;
    final String SAVED_TEXT = "token";
    private InputValidation inputValidationt;
    private JSONArray arr, arr2;
    private String promocode=null, promocodeDiscount= null;
    private boolean check;
    private int discount = 0;
    private int DIALOG_CALENDAR2 = 1;
    private int DIALOG_CALENDAR3 = 2;
    private int DIALOG_CALENDAR4 = 3;
    private int DIALOG = 4;
    private Dialog epicDialog, epicDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        Basket.chang = this;
        fHelper = new fHelper(this);
        check = true;
        backSec = new Intent(this, Second.class);
        back = new Intent(this, BasketActivity.class);
        databaseHelper = new DatabaseHelper(this);
        inputValidationt = new InputValidation(this);
        checkBox = (CheckBox) findViewById(R.id.checkBoxx);
        finish = (ImageView) findViewById(R.id.foxfox);
        pdf = (TextView) findViewById(R.id.pdf);
        pdfIntent = new Intent(this, Ques.class);
        calendar1 = (ConstraintLayout) findViewById(R.id.constraintLayout9);
        calendar2 = (ConstraintLayout) findViewById(R.id.constraintLayout10);
        calendar3 = (ConstraintLayout) findViewById(R.id.constraintLayout11);
        calendar4 = (ConstraintLayout) findViewById(R.id.constraintLayout12);
        promo = (ConstraintLayout) findViewById(R.id.constraintPromo);
        order = (ConstraintLayout) findViewById(R.id.orderrr);
        editStreet = (EditText) findViewById(R.id.editText);
        editHouse = (EditText) findViewById(R.id.editText2);
        epicDialog = new Dialog(this);
        epicDialog2 = new Dialog(this);
        editKv = (EditText) findViewById(R.id.editText3);
        editInstruction = (EditText) findViewById(R.id.editText4);
        editFIO = (EditText) findViewById(R.id.textView21);
        editTel = (EditText) findViewById(R.id.textView22);
        editEmail = (EditText) findViewById(R.id.email23);
        editInstruction2 = (EditText) findViewById(R.id.instruction);
        totalScre = (TextView) findViewById(R.id.gf1);
        text1 = (TextView) findViewById(R.id.textView17);
        text2 = (TextView) findViewById(R.id.textView14);
        text3 = (TextView) findViewById(R.id.textView19);
        text4 = (TextView) findViewById(R.id.textView18);
        promocod = (TextView) findViewById(R.id.promocod);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfIntent.putExtra("fileName", "contract_with_cleanit_kz.pdf");
                startActivity(pdfIntent);
            }
        });
        promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                epicDialog.setContentView(R.layout.promo_promt);
                final EditText input = (EditText) epicDialog.findViewById(R.id.promoEdit);
                final Button asd = (Button) epicDialog.findViewById(R.id.buttonOkok);
                final Button asd2 = (Button) epicDialog.findViewById(R.id.buttonCancel);
                asd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            promocode = input.getText().toString();
                            availablePromo();
                            epicDialog.dismiss();
                            } catch (JSONException e) {
                            e.printStackTrace();
                            }
                    }
                });
                asd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        epicDialog.dismiss();
                    }
                });
                epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                epicDialog.show();
            }
        });
        CalendarIntent = new Intent(this, CalendarActivity.class);
        CalendarIntent12 = new Intent(this, CalendarActivity12.class);
        CalendarIntent2 = new Intent(this, CalendarActivity2.class);
        CalendarIntent22 = new Intent(this, CalendarActivity22.class);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(back);
                finish();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check)
                {
                    if(CheckOrder())
                    {
                        arr = new JSONArray();
                        arr2 = new JSONArray();
                        try {
                            GetValueAndCountArr();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HTTPAsyncTask task = new HTTPAsyncTask();
                        task.execute();
                        try {
                            TimeUnit.MILLISECONDS.sleep(700);
                            setContentView(R.layout.order_created);
                            Button button = (Button) findViewById(R.id.next);
                            TextView txt = (TextView) findViewById(R.id.finish);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent myOrders = new Intent(ActivityOrder.this, myOrders.class);
                                    startActivity(myOrders);
                                    finish();
                                }
                            });
                            txt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(backSec);
                                    finish();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        check = false;
                    }
                }
            }
        });
        calendar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CalendarIntent);
            }
        });

        if (Basket.jsonObject!=null){
            try {
                text1.setText(Basket.jsonObject.getString("date_title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            calendar2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToCalendar(CalendarIntent12);
                }
            });
            if (Basket.time1!=null)
            {
                text2.setText(Basket.time1);
                calendar3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToCalendar(CalendarIntent2);
                    }
                });
                if(Basket.jsonObject2!=null)
                {
                    try {
                        text3.setText(Basket.jsonObject2.getString("date_title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    calendar4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToCalendar(CalendarIntent22);
                        }
                    });
                    if(Basket.time2!=null)
                    {
                        text4.setText(Basket.time2);
                    }
                }
                else {
                    calendar4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            asddd(DIALOG_CALENDAR4);
                        }
                    });
                }
            }
            else {
                calendar3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        asddd(DIALOG_CALENDAR3);
                    }
                });
                calendar4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        asddd(DIALOG_CALENDAR3);
                    }
                });
            }
        }
        else {
            calendar2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    asddd(DIALOG_CALENDAR2);
                }
            });
            calendar3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    asddd(DIALOG_CALENDAR2);
                }
            });
            calendar4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    asddd(DIALOG_CALENDAR2);
                }
            });
        }
        if (databaseHelper.getScore()>=5000) {
            totalScre.setText(String.valueOf(databaseHelper.getScore())+" ₸");
        }
        else{
            totalScre.setText(String.valueOf(5000)+" ₸");
        }
        if (fHelper.readAccountFile()!="")
        {
            try {
                JSONObject accObject = new JSONObject(fHelper.readAccountFile());
                editFIO.setText(accObject.getString("firstName")+" "+accObject.getString("lastName"));
                editTel.setText(accObject.getString("phone"));
                editEmail.setText(accObject.getString("mail"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void ToCalendar(Intent asd)
    {
        this.startActivity(asd);
    }
    public boolean CheckOrder()    {
        if(checkBox.isChecked() && inputValidationt.isInputEditTextFilled(editStreet)
                && inputValidationt.isInputEditTextFilled(editHouse)
                && inputValidationt.isInputEditTextFilled(editFIO)
                && inputValidationt.validatePhoneNumber(editTel)
                && inputValidationt.isInputEditTextEmail(editEmail)
                && Basket.jsonObject!=null && Basket.jsonObject2!=null
                && Basket.time1!=null && Basket.time2!=null)
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
    public void GetValueAndCountArr() throws JSONException {
        databaseHelper.getBasket();
        for (int i=0; i<databaseHelper.getBasket().size();i++)
        {
            arr.put(i,databaseHelper.getBasket().get(i).getValue());
            arr2.put(i,databaseHelper.getBasket().get(i).getCount());
        }
    }
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                try {
                    return HttpPost("http://cleanitapp.kz/v1/api/clients/orders");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.toLowerCase().equals("created"))
            {
                databaseHelper.clearBasket();
                Basket.jsonObject = null;
                Basket.jsonObject2 = null;
                Basket.time1 = null;
                Basket.time2 = null;
            }
            else {
                check = true;
            }
        }
    }
    private String HttpPost(String myUrl) throws IOException, JSONException {
        String result = "";
        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty(SAVED_TEXT, fHelper.readTokenFile());
        JSONObject jsonObject = buidJsonObject();
        setPostRequestContent(conn, jsonObject);
        conn.connect();
        return conn.getResponseMessage()+"";

    }
    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jso1 = new JSONObject();
        jso1.put("day", Basket.jsonObject.getString("day"));
        jso1.put("month", Basket.jsonObject.getString("month"));
        jso1.put("year", Basket.jsonObject.getString("year"));
        jso1.put("time", Basket.time1);

        JSONObject jso2 = new JSONObject();
        jso2.put("day", Basket.jsonObject2.getString("day"));
        jso2.put("month", Basket.jsonObject2.getString("month"));
        jso2.put("year", Basket.jsonObject2.getString("year"));
        jso2.put("time", Basket.time2);

        JSONObject jso3 = new JSONObject();
        jso3.put("values", arr);
        jso3.put("counts", arr2);

        JSONObject jsonObject = new JSONObject();
        String em = editEmail.getText().toString().replace(" ", "");
        jsonObject.accumulate("street", editStreet.getText().toString());
        jsonObject.accumulate("home", editHouse.getText().toString());
        jsonObject.accumulate("flat", editKv.getText().toString());
        jsonObject.accumulate("description", editInstruction.getText().toString());
        jsonObject.accumulate("notes", editInstruction2.getText().toString());
        jsonObject.accumulate("name", editFIO.getText().toString());
        jsonObject.accumulate("phone", editTel.getText().toString());
        jsonObject.accumulate("mail", em);
        jsonObject.accumulate("deliveryTime", jso2);
        jsonObject.accumulate("collectionTime",  jso1);
        jsonObject.accumulate("basket",  jso3);
        if(promocodeDiscount!=null){
            jsonObject.accumulate("promo",promocodeDiscount);
        }
        return jsonObject;
    }
    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }
    public void availablePromo() throws JSONException {
        final OkHttpClient client = new OkHttpClient();
        RequestBody fBody = new FormBody.Builder()
                .add("value",promocode)
                .build();

        final Request request3 = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/clients/orders/available/promo")
                .post(fBody)
                .addHeader(SAVED_TEXT, Basket.token)
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
                        JSONObject jsonObject = new JSONObject(s1);
                        if(jsonObject.getBoolean("available"))
                        {
                            discount = jsonObject.getInt("discount");
                            int price = databaseHelper.getScore();
                            price = price - ((databaseHelper.getScore()/100)*discount);
                            promocodeDiscount = promocode;
                            promocod.setText("Ваша скидка "+ String.valueOf(jsonObject.getInt("discount"))+"%");
                            if (price >=5000){
                                totalScre.setText(String.valueOf(price)+" ₸");
                            }
                            else{
                                totalScre.setText(String.valueOf(5000)+" ₸");
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

    @Override
    public void onBackPressed()
    {
        startActivity(back);
        finish();
    }

    protected void asddd(int id) {
        epicDialog2.setContentView(R.layout.ok_calendar_dialog);
        final Button asd = (Button) epicDialog2.findViewById(R.id.buttonCancel);
        final TextView asd2 = (TextView) epicDialog2.findViewById(R.id.changeText);
        final TextView asd3 = (TextView) epicDialog2.findViewById(R.id.disc);
        if (id == DIALOG_CALENDAR2) {
            asd2.setText("Не выберан день приема");
            asd3.setText("Пожалуйста, выберите день приема");
        }
        else {
            if (id == DIALOG_CALENDAR3) {
                asd2.setText("Не выберано время приема");
                asd3.setText("Пожалуйста, выберите время приема");
            }
            else if (id == DIALOG_CALENDAR4) {
                asd2.setText("Не выбран день доставки");
                asd3.setText("Пожалуйста, выберите день доставки");
            }
        }
        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog2.dismiss();
            }
        });
        epicDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog2.show();
    }
}