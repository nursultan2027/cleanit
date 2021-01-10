package com.cleanitkz.nurs.hscroll;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    private ImageView img;
    private TextView txtSex, pdf, finish, birth;
    private String sex;
    private boolean check;
    private Intent confirmIntent, pdfIntent;
    private InputValidation inputValidationt;
    private ConstraintLayout constraintLayout;
    private fHelper fHelper;
    private Intent back;
    private CheckBox checkBox;
    public int y,m,d;
    private EditText email, firstName, secondName, phone;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        check=true;
        back= new Intent(this, AccountActivity.class);
        inputValidationt = new InputValidation(this);
        fHelper = new fHelper(this);
        img = (ImageView) findViewById(R.id.change);
        constraintLayout = (ConstraintLayout) findViewById(R.id.reggg);
        confirmIntent = new Intent(this,RegConfirmActivity.class);
        pdfIntent = new Intent(this, Ques.class);
        email = (EditText) findViewById(R.id.editText8);
        phone = (EditText) findViewById(R.id.editText7);
        firstName = (EditText) findViewById(R.id.editText5);
        secondName = (EditText) findViewById(R.id.editText6);
        pdf = (TextView) findViewById(R.id.pdf);
        txtSex = (TextView) findViewById(R.id.Sex);
        birth = (TextView)findViewById(R.id.textView27);
        finish = (TextView) findViewById(R.id.foxfox);
        sex="man";
        checkBox = (CheckBox) findViewById(R.id.checkBoxx);
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfIntent.putExtra("fileName", "contract_with_cleanit_kz.pdf");
                startActivity(pdfIntent);
            }
        });
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =new DatePickerDialog(
                        RegistrationActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                y = i;
                m = i1;
                d = i2;
                birth.setText(String.valueOf(d+" "+getMonthName(String.valueOf(m))+" "+y));
            }
        };
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(back); finish();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtSex.getText().equals("Мужской"))
                {
                    txtSex.setText("Женский");
                    sex="woman";
                } else
                {
                    txtSex.setText("Мужской");
                    sex="man";
                }
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check)
                {
                    if(CheckOrder())
                    {
                        check=false;
                        HTTPAsyncTask2 asd = new HTTPAsyncTask2();
                        asd.execute();
                    }
                }
            }
        });
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
    public boolean CheckOrder() {
        if(!birth.getText().toString().toLowerCase().equals("выбрать")
                && checkBox.isChecked()
                && inputValidationt.isInputEditTextFilled(firstName)
                && inputValidationt.validatePhoneNumber(phone)
                && inputValidationt.isInputEditTextFilled(secondName)
                && inputValidationt.isInputEditTextEmail(email))
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
    private class HTTPAsyncTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                try {
                    return HttpPost("http://cleanitapp.kz/v1/api/clients");
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
            if(result.toLowerCase().equals("created")){
                confirmIntent.putExtra("mail", email.getText().toString());
                startActivity(confirmIntent);
                finish();
            } else {
                check=true;
            }
        }
    }
    private String HttpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("token", Basket.token);

        JSONObject jsonObject = buidJsonObject();

        setPostRequestContent(conn, jsonObject);

        conn.connect();

        return conn.getResponseMessage()+"";

    }
    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jso1 = new JSONObject();
        jso1.put("day", d);
        jso1.put("month", m);
        jso1.put("year", y);

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("firstName", firstName.getText().toString());
        jsonObject.accumulate("lastName", secondName.getText().toString());
        jsonObject.accumulate("phone", phone.getText().toString());
        jsonObject.accumulate("mail", email.getText().toString());
        jsonObject.accumulate("birth", jso1);
        jsonObject.accumulate("sex",  sex);

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
    public void onBackPressed()
    {
        startActivity(back);
         finish();
    }
}
