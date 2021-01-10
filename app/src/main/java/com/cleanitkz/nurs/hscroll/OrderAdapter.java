package com.cleanitkz.nurs.hscroll;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Order> orders;

    public OrderAdapter (Context context, int resource, ArrayList<Order> orders) {
        super(context, resource, orders);
        this.orders = orders;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        TextView number = (TextView) view.findViewById(R.id.aaa1);
        TextView createDate = (TextView) view.findViewById(R.id.aaa2);
        TextView price = (TextView) view.findViewById(R.id.aaa3);
        TextView textView54 = (TextView) view.findViewById(R.id.textView54);
        TextView desc = (TextView) view.findViewById(R.id.descrip);
        ImageView img = (ImageView) view.findViewById(R.id.imageView37);
        ConstraintLayout lay = (ConstraintLayout) view.findViewById(R.id.constt);
        ConstraintLayout green = (ConstraintLayout) view.findViewById(R.id.constraintLayout21);
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = orders.get(position);
                Intent details = new Intent(getContext(), ShowOrderActivity.class);
                details.putExtra(Order.class.getCanonicalName(), order);
                details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(details);
            }
        });
        Order prod = orders.get(position);
        number.setText(String.valueOf(prod.orderNumber));
        createDate.setText(String.valueOf(prod.createdDate));
        if(Integer.parseInt(prod.price)>5000)
        {
            price.setText(String.valueOf(prod.price)+" ₸");
        }
        else {
            price.setText("5000 ₸");
        }

        if(prod.status.toLowerCase().equals("created"))
        {
            Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.order_waiting_image);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(view.getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            img.setImageDrawable(roundedBitmapDrawable);
            textView54.setText("Заказ в обработке");
            desc.setText("В данный момент мы обрабатываем Ваш заказ.");
            green.setBackgroundColor(Color.parseColor("#a1a8b0"));
        }
        else {
            if(prod.status.toLowerCase().equals("rejected"))
            {
                Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.order_declined_image);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(view.getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                img.setImageDrawable(roundedBitmapDrawable);
                textView54.setText("Заказ отклонен");
                desc.setText("К сожалению, Ваш заказ был отклонен.");
                green.setBackgroundColor(Color.parseColor("#fd6361"));
            }
            else {
                if(prod.status.toLowerCase().equals("confirmed"))
                {
                    Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.order_accepted_image);
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(view.getResources(), bitmap);
                    roundedBitmapDrawable.setCircular(true);
                    img.setImageDrawable(roundedBitmapDrawable);
                    textView54.setText("Заказ подтвержден");
                    desc.setText("Данный заказ был отменен");
                    green.setBackgroundColor(Color.parseColor("#f6a64f"));
                }
                else {
                    if(prod.status.toLowerCase().equals("cleaning"))
                    {
                        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.inprogress_icon);
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(view.getResources(), bitmap);
                        roundedBitmapDrawable.setCircular(true);
                        img.setImageDrawable(roundedBitmapDrawable);
                        textView54.setText("Заказ выполняется");
                        desc.setText("Данный заказ был отменен");
                        green.setBackgroundColor(Color.parseColor("#69aef3"));
                    }
                    else {
                        if(prod.status.toLowerCase().equals("finish"))
                        {
                            Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.order_done_icon);
                            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(view.getResources(), bitmap);
                            roundedBitmapDrawable.setCircular(true);
                            img.setImageDrawable(roundedBitmapDrawable);
                            textView54.setText("Заказ выполнен");
                            desc.setText("Данный заказ был успешно выполнен, вещи были доставлены.");
                            green.setBackgroundColor(Color.parseColor("#6fc07e"));
                        }
                    }
                }
            }
        }

        return view;
    }
}
