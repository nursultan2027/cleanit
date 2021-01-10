package com.cleanitkz.nurs.hscroll;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegConfirmActivity extends Activity {
    private EditText ed1, ed2, ed3, ed4, ed5,ed6;
    private TextView finish, again;
    private boolean tf[];
    private Button btn;
    private Intent back;
    private String code;
    private Dialog epicDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_confirm_activity);
        epicDialog = new Dialog(this);
        code="";
        back = new Intent(this, AccountActivity.class);
        again = (TextView) findViewById(R.id.again);
        finish = (TextView) findViewById(R.id.foxfox);
        btn = (Button) findViewById(R.id.next);
        ed1 = (EditText) findViewById(R.id.editText30);
        ed2 = (EditText) findViewById(R.id.editText29);
        ed3 = (EditText) findViewById(R.id.editText28);
        ed4 = (EditText) findViewById(R.id.editText27);
        ed5 = (EditText) findViewById(R.id.editText26);
        ed6 = (EditText) findViewById(R.id.editText25);
        tf = new boolean[]{false,false,false,false,false,false};
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(back); finish();
            }
        });
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostCodeAgain();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostCode();
            }
        });
        ed1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == KeyEvent.KEYCODE_DEL) {
                    tf[0]=false;
                }
                else {
                    if(ed1.getText().length() == 1)
                    {
                        code=ed1.getText().toString()+ ed2.getText().toString()+ ed3.getText().toString()+ ed4.getText().toString()+ ed5.getText().toString()+ ed6.getText().toString();
                        if (code.length()==6)
                        {
                            PostCode();
                        }
                        else {
                            if(tf[0])
                            {

                            }
                            else {
                                ed2.requestFocus();
                                tf[0]=true;
                            }
                        }
                    }
                }
                return false;
            }
        });


        ed2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL) {
                    tf[1]=false;
                }
                else {
                    if(ed2.getText().length() == 1)
                    {
                        code=ed1.getText().toString()+ ed2.getText().toString()+ ed3.getText().toString()+ ed4.getText().toString()+ ed5.getText().toString()+ ed6.getText().toString();
                        if (code.length()==6)
                        {
                            PostCode();
                        }
                        else {
                            if (tf[1]) {

                            } else {
                                ed3.requestFocus();
                                tf[1] = true;
                            }
                        }
                    }
                }
                return false;
            }
        });

        ed3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == KeyEvent.KEYCODE_DEL) {
                    tf[2]=false;
                }
                else {
                    if(ed3.getText().length() == 1)
                    {
                        code=ed1.getText().toString()+ ed2.getText().toString()+ ed3.getText().toString()+ ed4.getText().toString()+ ed5.getText().toString()+ ed6.getText().toString();
                        if (code.length()==6)
                        {
                            PostCode();
                        }
                        else {
                            if (tf[2]) {

                            } else {
                                ed4.requestFocus();
                                tf[2] = true;
                            }
                        }
                    }
                }
                return false;
            }
        });

        ed4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == KeyEvent.KEYCODE_DEL) {
                    tf[3]=false;
                }
                else {
                    if(ed4.getText().length() == 1)
                    {
                        code=ed1.getText().toString()+ ed2.getText().toString()+ ed3.getText().toString()+ ed4.getText().toString()+ ed5.getText().toString()+ ed6.getText().toString();
                        if (code.length()==6)
                        {
                            PostCode();
                        }
                        else {
                            if (tf[3]) {

                            } else {
                                ed5.requestFocus();
                                tf[3] = true;
                            }
                        }
                    }
                }
                return false;
            }
        });
        ed5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == KeyEvent.KEYCODE_DEL) {
                    tf[4]=false;
                }
                else {
                    if(ed5.getText().length() == 1)
                    {
                        code=ed1.getText().toString()+ ed2.getText().toString()+ ed3.getText().toString()+ ed4.getText().toString()+ ed5.getText().toString()+ ed6.getText().toString();
                        if (code.length()==6)
                        {
                            PostCode();
                        }
                        else {
                            if (tf[4]) {

                            } else {
                                ed6.requestFocus();
                                tf[4] = true;
                            }
                        }
                    }
                }
                return false;
            }
        });

        ed6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == KeyEvent.KEYCODE_DEL) {
                    tf[5]=false;
                }
                else {
                    if(ed6.getText().length() == 1)
                    {
                        code=ed1.getText().toString()+ ed2.getText().toString()+ ed3.getText().toString()+ ed4.getText().toString()+ ed5.getText().toString()+ ed6.getText().toString();
                        PostCode();
                    }
                }
                return false;
            }
        });

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        );
    }

    public void PostCode()
    {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("code", code)
                .build();
        final Request request = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/clients/accounts/confirm")
                .post(formBody)
                .addHeader("token", Basket.token)
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
                        JSONObject jsonObject = new JSONObject(s);
                        if(jsonObject.getString("message").toLowerCase().equals("success"))
                        {
                            setContentView(R.layout.user_created);
                            Button button = (Button) findViewById(R.id.next);
                            TextView txt = (TextView) findViewById(R.id.finish);
                            final Intent backSec = new Intent(RegConfirmActivity.this, Second.class);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(backSec);
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
                        }
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Неверный код", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask.execute();
    }

    public void PostCodeAgain()
    {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("mail", getIntent().getStringExtra("mail"))
                .build();
        final Request request = new Request.Builder()
                .url("http://cleanitapp.kz/v1/api/clients/accounts/code")
                .post(formBody)
                .addHeader("token", Basket.token)
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
                        JSONObject jsonObject = new JSONObject(s);
                        if(jsonObject.getString("message").toLowerCase().equals("success"))
                        {
                            sDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask.execute();
    }

    public void sDialog()
    {
        epicDialog.setContentView(R.layout.ok_dialog);
        final Button asd = (Button) epicDialog.findViewById(R.id.buttonCancel);
        final TextView asd2 = (TextView) epicDialog.findViewById(R.id.changeText);
        asd2.setText("Код подтверждения был отправлен заново");
        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(back);
        finish();
    }
}
