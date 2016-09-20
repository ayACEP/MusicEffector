package org.ls.musiceffector.app;

import android.app.Application;
import android.content.Intent;

import org.ls.musiceffector.activity.EffectorService;

/**
 * Created by LS on 2016/9/18.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intent serviceIntent = new Intent(this, EffectorService.class);
        startService(serviceIntent);
    }
}
