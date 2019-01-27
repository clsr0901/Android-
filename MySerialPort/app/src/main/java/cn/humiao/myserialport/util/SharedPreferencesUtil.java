package cn.humiao.myserialport.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chenliangsen on 2018/7/11.
 */

public class SharedPreferencesUtil {
    public static void setIntegerVaule(Context context, String name, Integer vaule){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        sp.edit().putInt(name, vaule).commit();

    }

    public static Integer getIntegerVaule(Context context, String name){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        Integer vaule = sp.getInt(name, 0);
        return vaule;
    }
    public static Integer getIntegerVauleByDefault(Context context, String name, int defalut){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        Integer vaule = sp.getInt(name, defalut);
        return vaule;
    }

    public static void setBooleanVaule(Context context, String name, boolean vaule){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        sp.edit().putBoolean(name, vaule).commit();

    }

    public static boolean getBooleanVaule(Context context, String name){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        boolean vaule = sp.getBoolean(name, false);
        return vaule;
    }

    public static void setStringVaule(Context context, String name, String vaule){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        sp.edit().putString(name, vaule).commit();

    }

    public static String getStringVaule(Context context, String name, String defautVaule){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        String vaule = sp.getString(name, defautVaule);
        return vaule;
    }
}
