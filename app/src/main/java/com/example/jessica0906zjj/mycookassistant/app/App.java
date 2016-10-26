package com.example.jessica0906zjj.mycookassistant.app;

import android.app.Activity;
import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;

    public static synchronized App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AVOSCloud.initialize(this,"OPby9zNsW96iR4YbCHVRLl4g-gzGzoHsz", "vsSTWVYCXsGub6Pnkd0goGTN");
        AVOSCloud.setDebugLogEnabled(true);

    }

    public void registerActivity(Activity act){
        if (allActivities == null){
            allActivities = new HashSet<Activity>();
        }
        allActivities.add(act);
    }
    public void unregisterActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    if (act != null && !act.isFinishing())
                        act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
