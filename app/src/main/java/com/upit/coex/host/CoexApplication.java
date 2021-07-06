package com.upit.coex.host;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.logger.L;
import com.google.gson.Gson;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CoexApplication extends Application {

    private static CoexApplication sCoex;
    private Gson mGSon;

    public static CoexApplication self() {
        return sCoex;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sCoex = this;

        boolean isDebuggable =  ( 0 != ( getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE ) );
        if (!isDebuggable){
            // Những trường hợp không rõ exception
            Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException (Thread thread, Throwable e) {
                    handleUncaughtException (thread, e);
                }
            });
        }

    }

    public void handleUncaughtException (Thread thread, Throwable e)
    {
        e.printStackTrace();
        L.d("COEX plication","uncaught exception"+e.getMessage()+"k");
        System.exit(1); // kill off the crashed app
    }

    public Gson getGSon() {
       if (!CoexOptional.getInstance().setObject(mGSon).checkNull()){
           synchronized (CoexApplication.class){
               mGSon = new Gson();
           }
       }
        return mGSon;
    }
}
