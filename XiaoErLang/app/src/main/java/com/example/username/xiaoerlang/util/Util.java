package com.example.username.xiaoerlang.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hanlu.feng on 2/14/2017.
 */

public class Util {
    public static void showToast(Context mContext , String info){

        Toast.makeText(mContext, info, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context mContext , int info){
        String mtext =   mContext.getResources().getString(info);
        showToast(mContext,mtext);
    }
}
