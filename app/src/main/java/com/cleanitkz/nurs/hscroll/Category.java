package com.cleanitkz.nurs.hscroll;

import java.util.ArrayList;

public class Category {
    public String name;
    public String value;
    public ArrayList<Catalog> catalogs;
    public Category(){}
    public Category(String name, String value){
        this.value = value;
        this.name = name;
        this.catalogs = new ArrayList<Catalog>();
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public void AddCatalog (Catalog ct){
        this.catalogs.add(ct);
    }
    public ArrayList<Catalog> getCatalogs() {
        return catalogs;
    }
    public Catalog getCatalogById (int id)
    {
        return this.catalogs.get(id);
    }
}
