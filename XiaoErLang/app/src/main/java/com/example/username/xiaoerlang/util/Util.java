package com.example.username.xiaoerlang.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by hanlu.feng on 2/14/2017.
 */

public class Util {
    public static String email ="email";
    public static void showToast(Context mContext , String info){

        Toast.makeText(mContext, info, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context mContext , int info){
        String mtext =   mContext.getResources().getString(info);
        showToast(mContext,mtext);
    }

    public static void saveSP(Context context, String keyStr, String value){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(keyStr,value);
        editor.commit();
    }

    public static String getSP(Context context,String keyStr){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        return pref.getString(keyStr,null);
    }
}
