package com.cleanitkz.nurs.hscroll;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private LayoutInflater inflater;
    private DatabaseHelper databaseHelper;
    private int layout;
    private ArrayList<Product> products;
    public ProductAdapter (Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.products = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);
        databaseHelper = new DatabaseHelper(getContext());
        TextView nameView = (TextView) view.findViewById(R.id.prod_name);
        TextView disView = (TextView) view.findViewById(R.id.prod_dis);
        final TextView priceView = (TextView) view.findViewById(R.id.prod_price);
        final TextView count = (TextView) view.findViewById(R.id.prod_count);
        final TextView view2 = (TextView) Basket.chang.findViewById(R.id.texttext);
        ImageView img = (ImageView) view.findViewById(R.id.prod_img);
        ImageView btn1 = (ImageView) view.findViewById(R.id.btnPlus);
        ImageView btn2 = (ImageView) view.findViewById(R.id.btnMinus);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product prod = products.get(position);
                if (databaseHelper.checkProduct(prod.value)) {
                    prod.setCount(prod.getCount() + 1);
                    databaseHelper.updateProduct(prod);
                    databaseHelper.updtProduct(prod);
                }
                else
                {
                    prod.setCount(prod.getCount() + 1);
                    databaseHelper.updtProduct(prod);
                    databaseHelper.addProductToBasket(prod);
                }
                count.setText(String.valueOf("  x"+prod.getCount()));
                count.setVisibility(View.VISIBLE);
                view2.setText(String.valueOf(databaseHelper.getScore())+" ₸");
                if(databaseHelper.getBasket().size()==1)
                {
                    ImageView img = (ImageView) Basket.chang.findViewById(R.id.imageView20);
                    TextView txtx = (TextView) Basket.chang.findViewById(R.id.txtxt);
                    view2.setVisibility(View.VISIBLE);
                    img.setVisibility(View.VISIBLE);
                    txtx.setVisibility(View.VISIBLE);
                    TextView txtx21 = (TextView) Basket.chang.findViewById(R.id.basket_score);
                    txtx21.setVisibility(View.GONE);
                }
                Basket.cleanit.setScore(String.valueOf(databaseHelper.getScore())+" ₸");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product prod = products.get(position);
                if (prod.getCount()>0) {
                    if (prod.getCount() == 1) {
                        prod.setCount(0);
                        databaseHelper.deleteProduct(prod);
                        databaseHelper.updtProduct(prod);
                        count.setVisibility(View.INVISIBLE);
                    } else {
                        prod.setCount(prod.getCount() - 1);
                        databaseHelper.updateProduct(prod);
                        databaseHelper.updtProduct(prod);
                        count.setText(String.valueOf("  x" + prod.getCount()));
                    }
                }
                view2.setText(String.valueOf(databaseHelper.getScore())+" ₸");
                if(databaseHelper.getScore()==0)
                {
                    ImageView img = (ImageView) Basket.chang.findViewById(R.id.imageView20);
                    TextView txtx = (TextView) Basket.chang.findViewById(R.id.txtxt);
                    view2.setVisibility(View.GONE);
                    img.setVisibility(View.GONE);
                    txtx.setVisibility(View.GONE);
                    TextView txt2 = (TextView) Basket.chang.findViewById(R.id.basket_score);
                    txt2.setVisibility(View.VISIBLE);
                    txt2.setText("Пропустить выбор товаров");
                }
                Basket.cleanit.setScore(String.valueOf(databaseHelper.getScore())+" ₸");
            }
        });

        Product prod = products.get(position);
        nameView.setText(String.valueOf(prod.getName()));
        disView.setText(String.valueOf(prod.getDis()));
        count.setText(String.valueOf("  x"+prod.getCount()));
        if(prod.getMeasure().equals("кг"))
        {
            priceView.setText(String.valueOf(prod.getPrice())+" ₸/"+prod.getMeasure());
        }
        else {
            priceView.setText(String.valueOf(prod.getPrice())+" ₸");
        }
        if (prod.getCount()<1)
        {
            count.setVisibility(View.INVISIBLE);
        }
        int resID = view.getResources().getIdentifier(prod.getImgResource(),
                "drawable", getContext().getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), resID);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        img.setImageDrawable(roundedBitmapDrawable);

        return view;
    }
}