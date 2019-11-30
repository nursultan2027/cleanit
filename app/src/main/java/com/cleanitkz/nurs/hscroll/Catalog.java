package com.cleanitkz.nurs.hscroll;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Catalog implements Parcelable{
    public String name;
    public String value;
    public String discription;
    public String imgResource;
    public String categoryName;
    public ArrayList<Product> products;

    public Catalog(){}
    public Catalog(String name,String value, String discription, String img){
        this.discription = discription;
        this.name = name;
        this.value = value;
        this.imgResource = img;
        this.products = new ArrayList<Product>();
    }
    public String getName() {
        return this.name;
    }
    public String getValue() {
        return this.value;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public String getCategoryName() {
        return this.categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImg() {
        return this.imgResource;
    }
    public void setImg(String img) {
        this.imgResource = img;
    }

    public String getDiscription() {
        return this.discription;
    }
    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void AddProduct (Product pr){
        this.products.add(pr);
    }

    public ArrayList<Product> getProducts() {
        return this.products;
    }

    public int GetMinimum(){
        if (this.products.size()==0)
        {
            return 0;
        }
        else {
            int min = Integer.parseInt(this.products.get(0).getPrice());
            for (int i=1;i<this.products.size(); i++)
            {
                if (Integer.parseInt(products.get(i).getPrice())<min)
                {
                    min = Integer.parseInt(products.get(i).getPrice());
                }
            }
            return min;
        }
    }

    public String GetMeasure()
    {
        String result="";
        int k=0;
        if (this.products.size()==0)
        {
            return null;
        }
        else {
            for (int i=0;i<this.products.size(); i++)
            {
                if (products.get(i).getMeasure().equals("кг"))
                k++;
            }
            if(k==this.products.size())
                result = "кг";
        }
        return result;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(value);
        parcel.writeString(discription);
        parcel.writeString(imgResource);
        parcel.writeTypedList(products);
    }

    public static final Parcelable.Creator<Catalog> CREATOR = new Parcelable.Creator<Catalog>() {
        public Catalog createFromParcel(Parcel in) {
            return new Catalog(in);
        }

        public Catalog[] newArray(int size) {
            return new Catalog[size];
        }
    };
    private Catalog(Parcel parcel) {
        name = parcel.readString();
        value = parcel.readString();
        discription = parcel.readString();
        imgResource = parcel.readString();
        products = new ArrayList<Product>();
        parcel.readTypedList(products, Product.CREATOR);
    }
}
