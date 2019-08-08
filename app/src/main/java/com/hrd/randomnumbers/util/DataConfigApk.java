package com.hrd.randomnumbers.util;

import android.content.Context;
import android.content.SharedPreferences;

public class DataConfigApk {
    private SharedPreferences configPre;

    public DataConfigApk(Context context) {
        configPre = context.getSharedPreferences("RandomNumber", Context.MODE_PRIVATE);
    }

    public void setData_LayoutList(boolean tmp){
        SharedPreferences.Editor editor = configPre.edit();
        editor.putBoolean("Horizontal", tmp);
        editor.commit();
    }

    public boolean getData_LayoutList(){
        return configPre.getBoolean("Horizontal", true);
    }

    public void setData_NumMin(String tmp){
        SharedPreferences.Editor editor = configPre.edit();
        editor.putString("NumMin", tmp);
        editor.commit();
    }

    public String getData_NumMin(){
        return configPre.getString("NumMin", "1");
    }

    public void setData_NumMax(String tmp){
        SharedPreferences.Editor editor = configPre.edit();
        editor.putString("NumMax", tmp);
        editor.commit();
    }

    public String getData_NumMax(){
        return configPre.getString("NumMax", "100");
    }
}
