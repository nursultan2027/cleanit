package com.cleanitkz.nurs.hscroll;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class fHelper {
    private Context context;
    public fHelper(Context context) {
        this.context = context;
    }
    public String readCityFile() {
        return readFile("cityFile");
    }
    public String readWelcomeFile() {
        return readFile("welcomeFile");
    }
    public String readTokenFile() {
        return readFile("tokenInfo");
    }
    public String readAccountFile() {
        return  readFile("AccountInfo");
    }
    public String readFile1(){return readFile("Str1");}
    public String readFile2(){return readFile("Str2");}
    public String readProductVersionFile(){return readFile("version");}
    public String readPriceFile(){return readFile("price");}
    public void writePriceFile(String s) {
        writeFile(s, "price");
    }
    public void writeProductVersionFile(String s) {
        writeFile(s, "version");
    }
    public void writeTokenFile(String s) {
        writeFile(s, "tokenInfo");
    }
    public void writeCityFile(String s) {
        writeFile(s,"cityFile");
    }
    public void wrteWelcomeFile(String s) {writeFile(s, "welcomeFile");}
    public void writeAccountFile(String s) {
        writeFile(s, "AccountInfo");
    }
    public void writeFile1(String s){writeFile(s, "Str1");}
    public void writeFile2(String s){writeFile(s, "Str2");}
    public String readFile(String str) {
        FileInputStream fin = null;
        String text = "";
        try {
            fin = context.openFileInput(str);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            text = new String (bytes);
            return text;
        }
        catch(IOException ex) {
        }
        finally{
            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){
            }
        }
        return text;
    }

    public void writeFile(String s, String fileName) {
        FileOutputStream fos = null;
        try {
            String text = s;
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            fos.write(text.getBytes());
        }
        catch(IOException ex) {

        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

            }
        }
    }
}
