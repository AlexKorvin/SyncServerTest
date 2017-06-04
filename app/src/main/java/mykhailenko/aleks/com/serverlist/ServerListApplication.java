package mykhailenko.aleks.com.serverlist;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ServerListApplication extends Application {

    private static ServerListApplication instance;

    public static synchronized ServerListApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

}
