package com.cleanitkz.nurs.hscroll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cleanit.db";

    // Table name
    private static final String TABLE_PRODUCT = "product";
    private static final String TABLE_CATALOG = "catalog";
    private static final String TABLE_CATEGORY = "category";

    private static final String TABLE_BASKET = "basket";
    // Table Columns names
    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_PRODUCT_PRICE1 = "product_price1";
    private static final String COLUMN_PRODUCT_ISAVAILABLE1 = "product_isAvailable1";
    private static final String COLUMN_PRODUCT_PRICE2 = "product_price2";
    private static final String COLUMN_PRODUCT_ISAVAILABLE2 = "product_isAvailable2";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRODUCT_COUNT = "product_count";
    private static final String COLUMN_PRODUCT_VALUE = "product_value";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "product_description";
    private static final String COLUMN_PRODUCT_IMAGEURL = "product_imageUrl";
    private static final String COLUMN_PRODUCT_MEASURE = "product_measure";
    private static final String COLUMN_PRODUCT_CATALOG_ID = "product_catalog_fk";

    private String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
            + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_PRODUCT_PRICE1+" INTEGER," + COLUMN_PRODUCT_COUNT+" INTEGER,"
            + COLUMN_PRODUCT_ISAVAILABLE1 + " TEXT," + COLUMN_PRODUCT_PRICE2+" INTEGER,"
            + COLUMN_PRODUCT_ISAVAILABLE2 + " TEXT,"+ COLUMN_PRODUCT_NAME + " TEXT,"
            + COLUMN_PRODUCT_VALUE + " TEXT," + COLUMN_PRODUCT_DESCRIPTION + " TEXT,"
            + COLUMN_PRODUCT_IMAGEURL + " TEXT,"+ COLUMN_PRODUCT_MEASURE + " TEXT,"
            + COLUMN_PRODUCT_CATALOG_ID +" TEXT" + ")";

    private String CREATE_BASKET_TABLE = "CREATE TABLE " + TABLE_BASKET + "("
            + COLUMN_PRODUCT_ID + " INTEGER,"+ COLUMN_PRODUCT_PRICE1+" INTEGER," + COLUMN_PRODUCT_COUNT+" INTEGER,"
            + COLUMN_PRODUCT_ISAVAILABLE1 + " TEXT," + COLUMN_PRODUCT_PRICE2+" INTEGER,"
            + COLUMN_PRODUCT_ISAVAILABLE2 + " TEXT,"+ COLUMN_PRODUCT_NAME + " TEXT,"
            + COLUMN_PRODUCT_VALUE + " TEXT," + COLUMN_PRODUCT_DESCRIPTION + " TEXT,"
            + COLUMN_PRODUCT_IMAGEURL +" TEXT" + ")";

    private static final String COLUMN_CATALOG_ID = "catalog_id";
    private static final String COLUMN_CATALOG_NAME = "catalog_name";
    private static final String COLUMN_CATALOG_VALUE = "catalog_value";
    private static final String COLUMN_CATALOG_DESCRIPTION = "catalog_description";
    private static final String COLUMN_CATALOG_IMAGEURL = "catalog_imageUrl";
    private static final String COLUMN_CATALOG_CATEGORY_ID = "catalog_category_fk";

    private String CREATE_CATALOG_TABLE = "CREATE TABLE " + TABLE_CATALOG + "("
            + COLUMN_CATALOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CATALOG_NAME + " TEXT,"
            + COLUMN_CATALOG_VALUE + " TEXT," + COLUMN_CATALOG_DESCRIPTION + " TEXT,"
            + COLUMN_CATALOG_IMAGEURL + " TEXT,"+ COLUMN_CATALOG_CATEGORY_ID +" TEXT" + ")";

    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_CATEGORY_NAME = "category_name";
    private static final String COLUMN_CATEGORY_VALUE = "category_value";

    private String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CATEGORY_NAME + " TEXT,"
            + COLUMN_CATEGORY_VALUE + " TEXT" + ")";

    private String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
    private String DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + TABLE_CATEGORY;
    private String DROP_CATALOG_TABLE = "DROP TABLE IF EXISTS " + TABLE_CATALOG;
    private String DROP_BASKET_TABLE = "DROP TABLE IF EXISTS " + TABLE_BASKET;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_CATALOG_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_BASKET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Table if exist
        db.execSQL(DROP_PRODUCT_TABLE);
        db.execSQL(DROP_CATALOG_TABLE);
        db.execSQL(DROP_CATEGORY_TABLE);
        db.execSQL(DROP_BASKET_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        values1.put(COLUMN_CATEGORY_NAME, category.getName());
        values1.put(COLUMN_CATEGORY_VALUE, category.value);
        db.insert(TABLE_CATEGORY, null, values1);
        db.close();
    }

    public void addCatalog(Catalog catalog, String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_CATALOG_NAME, catalog.getName());
        values2.put(COLUMN_CATALOG_VALUE, catalog.getValue());
        values2.put(COLUMN_CATALOG_DESCRIPTION, catalog.getDiscription());
        values2.put(COLUMN_CATALOG_IMAGEURL, catalog.getImg());
        values2.put(COLUMN_CATALOG_CATEGORY_ID, categoryName);
        db.insert(TABLE_CATALOG, null, values2);
        db.close();
    }

    public void addProduct(Product product, String catalogName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values3 = new ContentValues();
        values3.put(COLUMN_PRODUCT_COUNT, product.getCount());
        values3.put(COLUMN_PRODUCT_PRICE1, product.price1);
        values3.put(COLUMN_PRODUCT_ISAVAILABLE1, product.isAvialable1);
        values3.put(COLUMN_PRODUCT_PRICE2, product.price2);
        values3.put(COLUMN_PRODUCT_ISAVAILABLE2, product.isAvialable2);
        values3.put(COLUMN_PRODUCT_NAME, product.getName());
        values3.put(COLUMN_PRODUCT_VALUE, product.value);
        values3.put(COLUMN_PRODUCT_DESCRIPTION, product.getDis());
        values3.put(COLUMN_PRODUCT_IMAGEURL, product.getImgResource());
        values3.put(COLUMN_PRODUCT_MEASURE, product.getMeasure());
        values3.put(COLUMN_PRODUCT_CATALOG_ID, catalogName);
        db.insert(TABLE_PRODUCT, null, values3);
        db.close();
    }

    public void addProductToBasket(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values4 = new ContentValues();
        values4.put(COLUMN_PRODUCT_ID, product.getId());
        values4.put(COLUMN_PRODUCT_PRICE1, product.price1);
        values4.put(COLUMN_PRODUCT_ISAVAILABLE1, product.isAvialable1);
        values4.put(COLUMN_PRODUCT_PRICE2, product.price2);
        values4.put(COLUMN_PRODUCT_ISAVAILABLE2, product.isAvialable2);
        values4.put(COLUMN_PRODUCT_COUNT, product.getCount());
        values4.put(COLUMN_PRODUCT_NAME, product.getName());
        values4.put(COLUMN_PRODUCT_VALUE, product.value);
        values4.put(COLUMN_PRODUCT_DESCRIPTION, product.getDis());
        values4.put(COLUMN_PRODUCT_IMAGEURL, product.getImgResource());
        db.insert(TABLE_BASKET, null, values4);
        db.close();
    }
    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BASKET, COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
    }

    public void clearBasket()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BASKET, null, null);
        db.close();
    }


    public ArrayList<Category> getAllCategories() {
        String[] columns = {
                COLUMN_CATEGORY_NAME,
                COLUMN_CATEGORY_VALUE
        };
        String sortOrder =
                COLUMN_CATEGORY_NAME + " ASC";
        ArrayList<Category> categList = new ArrayList<Category>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORY, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        if (cursor.moveToFirst()) {
            do {
                Category category1 = new Category();
                category1.setName(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME)));
                category1.setValue(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_VALUE)));
                categList.add(category1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categList;
    }


    public ArrayList<Catalog> getAllCatalogs() {
        String[] columns2 = {
                COLUMN_CATALOG_NAME,
                COLUMN_CATALOG_VALUE,
                COLUMN_CATALOG_DESCRIPTION,
                COLUMN_CATALOG_IMAGEURL,
                COLUMN_CATALOG_CATEGORY_ID
        };
        String sortOrder2 =
                COLUMN_CATALOG_NAME + " ASC";
        ArrayList<Catalog> catalList = new ArrayList<Catalog>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor2 = db.query(TABLE_CATALOG, //Table to query
                columns2,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder2); //The sort order

        if (cursor2.moveToFirst()) {
            do {
                Catalog catalog1 = new Catalog();
                catalog1.setName(cursor2.getString(cursor2.getColumnIndex(COLUMN_CATALOG_NAME)));
                catalog1.setValue(cursor2.getString(cursor2.getColumnIndex(COLUMN_CATALOG_VALUE)));
                catalog1.setDiscription(cursor2.getString(cursor2.getColumnIndex(COLUMN_CATALOG_DESCRIPTION)));
                catalog1.setImg(cursor2.getString(cursor2.getColumnIndex(COLUMN_CATALOG_IMAGEURL)));
                catalog1.setCategoryName(cursor2.getString(cursor2.getColumnIndex(COLUMN_CATALOG_CATEGORY_ID)));
                catalList.add(catalog1);
            } while (cursor2.moveToNext());
        }
        cursor2.close();
        db.close();
        return catalList;
    }

    public ArrayList<Product> getAllProducts() {
        String[] columns3 = {
                COLUMN_PRODUCT_ID,
                COLUMN_PRODUCT_PRICE1,
                COLUMN_PRODUCT_ISAVAILABLE1,
                COLUMN_PRODUCT_PRICE2,
                COLUMN_PRODUCT_ISAVAILABLE2,
                COLUMN_PRODUCT_COUNT,
                COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_VALUE,
                COLUMN_PRODUCT_DESCRIPTION,
                COLUMN_PRODUCT_IMAGEURL,
                COLUMN_PRODUCT_MEASURE,
                COLUMN_PRODUCT_CATALOG_ID
        };
        String sortOrder3 =
                COLUMN_PRODUCT_NAME + " ASC";
        ArrayList<Product> prodList = new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor3 = db.query(TABLE_PRODUCT, //Table to query
                columns3,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder3); //The sort order

        if (cursor3.moveToFirst()) {
            do {
                Product product1 = new Product();
                product1.setId(cursor3.getInt(cursor3.getColumnIndex(COLUMN_PRODUCT_ID)));
                product1.SetPrice1(cursor3.getString(cursor3.getColumnIndex(COLUMN_PRODUCT_PRICE1)));
                product1.SetIsAvailable1(cursor3.getString(cursor3.getColumnIndex(COLUMN_PRODUCT_ISAVAILABLE1)));
                product1.SetPrice2(cursor3.getString(cursor3.getColumnIndex(COLUMN_PRODUCT_PRICE2)));
                product1.SetIsAvailable2(cursor3.getString(cursor3.getColumnIndex(COLUMN_PRODUCT_ISAVAILABLE2)));
                product1.setCount(cursor3.getInt(cursor3.getColumnIndex(COLUMN_PRODUCT_COUNT)));
                product1.setName(cursor3.getString(cursor3.getColumnIndex(COLUMN_PRODUCT_NAME)));
                product1.setValue(cursor3.getString(cursor3.getColumnIndex(COLUMN_PRODUCT_VALUE)));
                product1.setDics(cursor3.getString(cursor3.getColumnIndex(COLUMN_PRODUCT_DESCRIPTION)));
                product1.setImgResource(cursor3.getString(cursor3.getColumnIndex(COLUMN_PRODUCT_IMAGEURL)));
                product1.setMeasure(cursor3.getString(cursor3.getColumnIndex(COLUMN_PRODUCT_MEASURE)));
                product1.setCatalogName(cursor3.getString(cursor3.getColumnIndex(COLUMN_PRODUCT_CATALOG_ID)));
                prodList.add(product1);
            } while (cursor3.moveToNext());
        }
        cursor3.close();
        db.close();
        return prodList;
    }

    public ArrayList<Product> getBasket() {
        String[] columns4 = {
                COLUMN_PRODUCT_ID,
                COLUMN_PRODUCT_PRICE1,
                COLUMN_PRODUCT_ISAVAILABLE1,
                COLUMN_PRODUCT_PRICE2,
                COLUMN_PRODUCT_ISAVAILABLE2,
                COLUMN_PRODUCT_COUNT,
                COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_VALUE,
                COLUMN_PRODUCT_DESCRIPTION,
                COLUMN_PRODUCT_IMAGEURL
        };
        String sortOrder4 =
                COLUMN_PRODUCT_NAME + " ASC";
        ArrayList<Product> prodList = new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor4 = db.query(TABLE_BASKET, //Table to query
                columns4,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder4); //The sort order

        if (cursor4.moveToFirst()) {
            do {
                Product product1 = new Product();
                product1.setId(cursor4.getInt(cursor4.getColumnIndex(COLUMN_PRODUCT_ID)));
                product1.SetPrice1(cursor4.getString(cursor4.getColumnIndex(COLUMN_PRODUCT_PRICE1)));
                product1.SetIsAvailable1(cursor4.getString(cursor4.getColumnIndex(COLUMN_PRODUCT_ISAVAILABLE1)));
                product1.SetPrice2(cursor4.getString(cursor4.getColumnIndex(COLUMN_PRODUCT_PRICE2)));
                product1.SetIsAvailable2(cursor4.getString(cursor4.getColumnIndex(COLUMN_PRODUCT_ISAVAILABLE2)));
                product1.setCount(cursor4.getInt(cursor4.getColumnIndex(COLUMN_PRODUCT_COUNT)));
                product1.setName(cursor4.getString(cursor4.getColumnIndex(COLUMN_PRODUCT_NAME)));
                product1.setValue(cursor4.getString(cursor4.getColumnIndex(COLUMN_PRODUCT_VALUE)));
                product1.setDics(cursor4.getString(cursor4.getColumnIndex(COLUMN_PRODUCT_DESCRIPTION)));
                product1.setImgResource(cursor4.getString(cursor4.getColumnIndex(COLUMN_PRODUCT_IMAGEURL)));
                prodList.add(product1);
            } while (cursor4.moveToNext());
        }
        cursor4.close();
        db.close();
        return prodList;
    }

    public int getScore() {
        ArrayList<Product> prodList = getBasket();
        int Schet=0;
        if (prodList.size()>0) {
            for (int i = 0; i < prodList.size(); i++) {
                Schet += (Integer.parseInt(prodList.get(i).getPrice())*prodList.get(i).getCount());
            }
        }
        return Schet;
    }


    public boolean checkProduct(String value) {
        String[] columns = {
                COLUMN_PRODUCT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_PRODUCT_VALUE + " = ?";
        String[] selectionArgs = {value};
        Cursor cursor = db.query(TABLE_BASKET, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_PRICE1, product.getPrice1());
        values.put(COLUMN_PRODUCT_ISAVAILABLE1, product.getIsAvailable1());
        values.put(COLUMN_PRODUCT_PRICE2, product.getPrice2());
        values.put(COLUMN_PRODUCT_ISAVAILABLE2, product.getIsAvailable2());
        values.put(COLUMN_PRODUCT_COUNT, product.getCount());
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_VALUE, product.getValue());
        values.put(COLUMN_PRODUCT_DESCRIPTION, product.getDis());
        values.put(COLUMN_PRODUCT_IMAGEURL, product.getImgResource());
        // updating row
        db.update(TABLE_BASKET, values, COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
    }
    public void updtProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_PRICE1, product.getPrice1());
        values.put(COLUMN_PRODUCT_ISAVAILABLE1, product.getIsAvailable1());
        values.put(COLUMN_PRODUCT_PRICE2, product.getPrice2());
        values.put(COLUMN_PRODUCT_ISAVAILABLE2, product.getIsAvailable2());
        values.put(COLUMN_PRODUCT_COUNT, product.getCount());
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_VALUE, product.getValue());
        values.put(COLUMN_PRODUCT_DESCRIPTION, product.getDis());
        values.put(COLUMN_PRODUCT_IMAGEURL, product.getImgResource());
        // updating row
        db.update(TABLE_PRODUCT, values, COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
    }

}