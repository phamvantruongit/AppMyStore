package vn.com.phamtruongit.appmystore;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.squareup.leakcanary.LeakCanary;

import vn.com.phamtruongit.appmystore.data.AppDatabase;

public class ApplicationMyStore extends Application {
    public static AppDatabase db;

    public AppDatabase createDatabaseUser() {
        if (db == null) {
            return   db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-userEntity").allowMainThreadQueries().build();
        }
        return db;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        createDatabaseUser();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }
}
