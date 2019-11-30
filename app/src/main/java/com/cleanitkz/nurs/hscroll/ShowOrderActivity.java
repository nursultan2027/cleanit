package com.cleanitkz.nurs.hscroll;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowOrderActivity extends AppCompatActivity{
    private TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9;
    private ConstraintLayout constraintLayout20, statusC;
    private ImageView finish,statusImage;
    private static final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_order_activity);
        constraintLayout20 = (ConstraintLayout) findViewById(R.id.constraintLayout20);
        statusC = (ConstraintLayout) findViewById(R.id.statusColor);
        finish = (ImageView) findViewById(R.id.foxfox);
        statusImage= (ImageView) findViewById(R.id.statusImg);
        txt1 = (TextView) findViewById(R.id.textView34);
        txt2 = (TextView) findViewById(R.id.textView39);
        txt3 = (TextView) findViewById(R.id.textView41);
        txt4 = (TextView) findViewById(R.id.textView44);
        txt5 = (TextView) findViewById(R.id.textView46);
        txt6 = (TextView) findViewById(R.id.textView47);
        txt7 = (TextView) findViewById(R.id.textView50);
        txt8 = (TextView) findViewById(R.id.textView52);
        txt9 = (TextView) findViewById(R.id.textView43);

        Order myObj = (Order) getIntent().getParcelableExtra(Order.class.getCanonicalName());
        ProductAdapterMyOrder adp = new ProductAdapterMyOrder(this.getApplication(),R.layout.item2_my_order, myObj.products);
        LinearLayout listViewReplacement = (LinearLayout) findViewById(R.id.leadd);
        for (int i = 0; i < adp.getCount(); i++) {
            View view = adp.getView(i, null, listViewReplacement);
            listViewReplacement.addView(view);
        }
        txt1.setText(myObj.orderNumber);
        txt2.setText(myObj.createdDate);
        txt4.setText(myObj.collectionDate);
        txt5.setText(myObj.deliveryDate);
        txt6.setText(myObj.street);
        txt7.setText(myObj.house);
        txt8.setText(myObj.flat);
        if(Integer.parseInt(myObj.price)>5000)
        {
            txt3.setText(myObj.price+" ₸");
        }
        else {
            txt3.setText("5000 ₸");
        }

        if(myObj.status.toLowerCase().equals("created"))
        {
            statusC.setBackgroundColor(Color.parseColor("#a1a8b0"));
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.order_status_icon);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            statusImage.setImageDrawable(roundedBitmapDrawable);
            txt9.setText("Заказ в обработке");
        }
        else {
            if (myObj.status.toLowerCase().equals("rejected"))
            {
                statusC.setBackgroundColor(Color.parseColor("#fd6361"));
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.order_status_icon);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                statusImage.setImageDrawable(roundedBitmapDrawable);
                txt9.setText("Заказ отклонен");
            }
            else {
                if (myObj.status.toLowerCase().equals("confirmed"))
                {
                    statusC.setBackgroundColor(Color.parseColor("#f6a64f"));
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.order_status_icon);
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                    roundedBitmapDrawable.setCircular(true);
                    statusImage.setImageDrawable(roundedBitmapDrawable);
                    txt9.setText("Заказ подтвержден");
                }
                else {
                    if (myObj.status.toLowerCase().equals("cleaning"))
                    {
                        statusC.setBackgroundColor(Color.parseColor("#69aef3"));
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.order_status_icon);
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                        roundedBitmapDrawable.setCircular(true);
                        statusImage.setImageDrawable(roundedBitmapDrawable);
                        txt9.setText("Заказ выполняется");
                    }
                    else {
                        if (myObj.status.toLowerCase().equals("finish"))
                        {
                            statusC.setBackgroundColor(Color.parseColor("#6fc07e"));
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.order_status_icon);
                            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                            roundedBitmapDrawable.setCircular(true);
                            statusImage.setImageDrawable(roundedBitmapDrawable);
                            txt9.setText("Заказ выполнен");
                        }
                    }
                }
            }
        }

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        constraintLayout20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasPermissions())
                {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    if(Basket.theCity.equals("ata"))
                        callIntent.setData(Uri.parse("tel:+77782000249"));
                    else
                        callIntent.setData(Uri.parse("tel:+77789000247"));
                    startActivity(callIntent);
                }
                else {
                    requestPermissionWithRationale();
                }
            }
        });
    }
    private boolean hasPermissions(){
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }
    public void onBackPressed()
    {
        finish();
    }

    public void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            final String message = "Storage permission is needed to show files count";
            Snackbar.make(ShowOrderActivity.this.findViewById(R.id.activity_view), message, Snackbar.LENGTH_LONG)
                    .setAction("GRANT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPerms();
                        }
                    })
                    .show();
        } else {
            requestPerms();
        }
    }

    private void requestPerms(){
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:

                for (int res : grantResults) {
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;
            default:
                allowed = false;
                break;
        }

        if (allowed) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            if(Basket.theCity.equals("ata"))
                callIntent.setData(Uri.parse("tel:+77782000249"));
            else
                callIntent.setData(Uri.parse("tel:+77789000247"));
            startActivity(callIntent);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(this, "Storage Permissions denied.", Toast.LENGTH_SHORT).show();

                } else {
                    showNoStoragePermissionSnackbar();
                }
            }
        }
    }
        public void showNoStoragePermissionSnackbar() {
            Snackbar.make(ShowOrderActivity.this.findViewById(R.id.activity_view), "Storage permission isn't granted" , Snackbar.LENGTH_LONG)
                    .setAction("SETTINGS", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openApplicationSettings();

                            Toast.makeText(getApplicationContext(),
                                    "Open Permissions and grant the Storage permission",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .show();
        }
    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            if(Basket.theCity.equals("ata"))
                callIntent.setData(Uri.parse("tel:+77782000249"));
            else
                callIntent.setData(Uri.parse("tel:+77789000247"));
            startActivity(callIntent);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
