package com.droidmaniac.datacore;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by DroidManiac on 11/11/2015.
 */
public class ContactApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "kSmGfEoB14e5kL23GGWiCpBNObuxMVfgl6yiVLec", "5btQ4kTfrd08lMUrONroEMKrG7xwKHVsuoiJwTTa");
    }
}
