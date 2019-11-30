package com.cleanitkz.nurs.hscroll;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public RecyclerAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    public ArrayList<Product> products;
    public Context context;
    private DatabaseHelper databaseHelper;
    private Product prod;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_basket,parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        databaseHelper = new DatabaseHelper(context);
        prod = products.get(position);
        int resID = context.getResources().getIdentifier(prod.getImgResource(),
                "drawable", context.getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        holder.txt1.setText(prod.getName());
        holder.txt2.setText(String.valueOf(prod.getCount())+" ");
        holder.txt3.setText(prod.getPrice()+" ₸");
        holder.img1.setImageDrawable(roundedBitmapDrawable);
        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prod = products.get(position);
                products.remove(prod);
                prod.setCount(0);
                databaseHelper.updateProduct(prod);
                databaseHelper.deleteProduct(prod);
                databaseHelper.updtProduct(prod);
                notifyDataSetChanged();
                Basket.cleanit.setScore(String.valueOf(databaseHelper.getScore())+" ₸");
                if (databaseHelper.getBasket().size()<1) {
                    Intent empty = new Intent(context, EmptyBasket.class);
                    context.startActivity(empty);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txt1;
        public TextView txt2;
        public TextView txt3;
        public ImageView img1;
        public ImageView img2;

        public ViewHolder(View itemView) {
            super(itemView);

            txt1 = (TextView) itemView.findViewById(R.id.prod_name);
            txt2 = (TextView) itemView.findViewById(R.id.prod_count);
            txt3 = (TextView) itemView.findViewById(R.id.prod_price);

            img1 = (ImageView) itemView.findViewById(R.id.prod_img);
            img2 = (ImageView) itemView.findViewById(R.id.btnXXX);

        }
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
        }
        return connected;
    }
}
