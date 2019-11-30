package com.cleanitkz.nurs.hscroll;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Order implements Parcelable {
    public String city, orderNumber,createdDate,price, status, collectionDate, deliveryDate, street, house, flat;
    public ArrayList<Product> products;
    public Order(){}
    public Order(String city, String o1, String o2, String o3, String o4, String o5, String o6, String o7, String o8, String o9)
    {
        this.city = city;
        this.orderNumber = o1;
        this.createdDate= o2;
        this.price= o3;
        this.status = o4;
        this.collectionDate = o5;
        this.deliveryDate = o6;
        this.street= o7;
        this.house = o8;
        this.flat = o9;
        this.products = new ArrayList<>();
    }

    protected Order(Parcel in) {
        city = in.readString();
        orderNumber = in.readString();
        createdDate = in.readString();
        price = in.readString();
        status = in.readString();
        collectionDate = in.readString();
        deliveryDate = in.readString();
        street = in.readString();
        house = in.readString();
        flat = in.readString();
        products = in.createTypedArrayList(Product.CREATOR);
    }
    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(city);
        parcel.writeString(orderNumber);
        parcel.writeString(createdDate);
        parcel.writeString(price);
        parcel.writeString(status);
        parcel.writeString(collectionDate);
        parcel.writeString(deliveryDate);
        parcel.writeString(street);
        parcel.writeString(house);
        parcel.writeString(flat);
        parcel.writeTypedList(products);
    }
}
