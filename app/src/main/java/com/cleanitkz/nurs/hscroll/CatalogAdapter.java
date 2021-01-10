package com.cleanitkz.nurs.hscroll;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CatalogAdapter extends ArrayAdapter<Catalog> {

    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Catalog> catalogs;


    public CatalogAdapter(Context context, int resource, ArrayList<Catalog> catalogs) {
        super(context, resource, catalogs);
        this.catalogs = catalogs;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView nameView = (TextView) view.findViewById(R.id.tvName);
        TextView capitalView = (TextView) view.findViewById(R.id.tvPrice);
        ConstraintLayout rel = (ConstraintLayout) view.findViewById(R.id.rel);

        Catalog catalog = catalogs.get(position);

        nameView.setText(catalog.getName());
        if(catalog.GetMinimum()==0)
        {
            capitalView.setText("Индивидуальная цена");
        }
        else {
            if(catalog.GetMeasure().equals("кг"))
            {
                capitalView.setText("от "+String.valueOf(catalog.GetMinimum())+" ₸/"+catalog.GetMeasure());
            }
            else {
                capitalView.setText("от "+String.valueOf(catalog.GetMinimum())+" ₸");
            }
        }
        int resID = view.getResources().getIdentifier(catalog.getImg(),
                "drawable", getContext().getPackageName());
        rel.setBackgroundResource(resID);
        return view;
    }
}