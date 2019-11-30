package com.cleanitkz.nurs.hscroll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapterMyOrder extends ArrayAdapter<Product> {

    private LayoutInflater inflater;
    private DatabaseHelper databaseHelper;
    private int layout;
    private ArrayList<Product> products;

    public ProductAdapterMyOrder(Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.products = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);
        databaseHelper = new DatabaseHelper(getContext());
        TextView nameView = (TextView) view.findViewById(R.id.prod_name);
        final TextView priceView = (TextView) view.findViewById(R.id.prod_price);
        final TextView count = (TextView) view.findViewById(R.id.prod_count);
        ImageView img = (ImageView) view.findViewById(R.id.prod_img);

        Product prod = products.get(position);
        nameView.setText(String.valueOf(prod.getName()));
        count.setText(String.valueOf(" x"+prod.getCount()));
        if (prod.city.equals("ata"))
            priceView.setText(String.valueOf(prod.getPrice1())+" ₸");
        else
            priceView.setText(String.valueOf(prod.getPrice2())+" ₸");
        int resID = view.getResources().getIdentifier(prod.getImgResource(),
                "drawable", getContext().getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), resID);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        img.setImageDrawable(roundedBitmapDrawable);
        return view;
    }
}