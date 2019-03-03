package vn.com.phamtruongit.appmystore.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
@Database(entities = {TypeProduct.class,Product.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TypeProductDao typeProductDao();
    public abstract ProductDao productDao();
}
