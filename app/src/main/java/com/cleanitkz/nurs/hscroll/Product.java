package com.cleanitkz.nurs.hscroll;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String name;
    private String catalogName;
    public String value;
    private String disc;
    public String imgResource;
    public String measure;
    private int count;
    public int id;
    public String isAvialable1;
    public String price1;
    public String isAvialable2;
    public String city;
    public String price2;
    public Product(){}

    public Product(String measure, String isAvialable1, String price1, String isAvialable2,String price2, String name1, String value, String img, String disc) {
        this.measure = measure;
        this.name = name1;
        this.value = value;
        this.imgResource = img;
        this.disc = disc;
        this.count =0;
        this.isAvialable1 = isAvialable1;
        this.price1 = price1;
        this.isAvialable2 = isAvialable2;
        this.price2 = price2;
    }

    public Product(int count, String isAvialable1, String price1, String isAvialable2,String price2, String name1, String value, String img, String disc) {
        this.name = name1;
        this.value = value;
        this.imgResource = img;
        this.disc = disc;
        this.count = count;
        this.isAvialable1 = isAvialable1;
        this.price1 = price1;
        this.isAvialable2 = isAvialable2;
        this.price2 = price2;
    }


    public Product(String city, int count, String isAvialable1, String price1, String isAvialable2,String price2, String name1, String value, String img, String disc) {
        this.city = city;
        this.name = name1;
        this.value = value;
        this.imgResource = img;
        this.disc = disc;
        this.count = count;
        this.isAvialable1 = isAvialable1;
        this.price1 = price1;
        this.isAvialable2 = isAvialable2;
        this.price2 = price2;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name2) {
        this.name = name2;
    }

    public String getValue() {
        return this.value;
    }
    public void setValue(String value2) {
        this.value = value2;
    }

    public String getMeasure() {
        return this.measure;
    }
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public int getId(){ return this.id;}
    public void setId(int id){this.id=id;}
    public String getCatalogName() {
        return this.catalogName;
    }
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getPrice() {
        if (Basket.theCity.equals("ata"))
            return this.price1;
        else
            return this.price2;
    }

    public void setPrice(String price) {
        if (Basket.theCity.equals("ata"))
            this.price1 = price;
        else
            this.price2 = price;
    }

    public void SetPrice1(String price){
        this.price1 = price;
    }
    public void SetPrice2(String price){
        this.price2 = price;
    }

    public String getPrice1() {
        return this.price1;
    }
    public String getPrice2() {
        return this.price2;
    }

    public void SetIsAvailable1(String nn){
        this.isAvialable1 = nn;
    }
    public void SetIsAvailable2(String nn){
        this.isAvialable2 = nn;
    }

    public String getIsAvailable1() {
        return this.isAvialable1;
    }
    public String getIsAvailable2() {
        return this.isAvialable2;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setImgResource(String price) {
        this.imgResource = price;
    }

    public String getImgResource() {
        return this.imgResource;
    }

    public String getDis() {
        return this.disc;
    }

    public void setDics(String price) {
        this.disc = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(city);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(disc);
        parcel.writeString(imgResource);
        parcel.writeString(value);
        parcel.writeInt(count);
        parcel.writeString(price1);
        parcel.writeString(isAvialable1);
        parcel.writeString(price2);
        parcel.writeString(isAvialable2);
        parcel.writeString(measure);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel parcel) {
        city = parcel.readString();
        id = parcel.readInt();
        name = parcel.readString();
        disc = parcel.readString();
        imgResource = parcel.readString();
        value = parcel.readString();
        count = parcel.readInt();
        price1 = parcel.readString();
        isAvialable1 = parcel.readString();
        price2 = parcel.readString();
        isAvialable2 = parcel.readString();
        measure = parcel.readString();
    }
}
