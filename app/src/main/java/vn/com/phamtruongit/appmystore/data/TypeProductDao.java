package vn.com.phamtruongit.appmystore.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TypeProductDao {
    @Insert
    void insertTypeProduct(TypeProduct product);

    @Query("SELECT * FROM TypeProduct ")
    List<TypeProduct> getListTypeProduct();



}
