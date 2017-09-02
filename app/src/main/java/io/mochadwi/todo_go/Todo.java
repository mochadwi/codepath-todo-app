package io.mochadwi.todo_go;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mochadwi on 9/2/17.
 */

public class Todo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Realm.init(this);
//
//        final RealmConfiguration configuration = new RealmConfiguration.Builder().name("sample.realm").schemaVersion(2).build();
//        Realm.setDefaultConfiguration(configuration);
//        Realm.getInstance(configuration);
    }

    @Override
    public void onTerminate() {
//        Realm.getDefaultInstance().close();
        super.onTerminate();
    }
}
