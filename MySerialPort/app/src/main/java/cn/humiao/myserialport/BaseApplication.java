package cn.humiao.myserialport;

import android.app.Application;
import android.content.Context;

import com.nanchen.crashmanager.CrashApplication;
import com.nanchen.crashmanager.UncaughtExceptionHandlerImpl;

public class BaseApplication extends CrashApplication {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 设置崩溃后自动重启 APP
        UncaughtExceptionHandlerImpl.getInstance().init(this, BuildConfig.DEBUG, true, 0, MainActivity.class);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Context getAppContext() {
        return instance;
    }

}