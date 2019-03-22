package vn.com.phamtruongit.appmystore.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM Product ")
    List<Product> getListProduct();

    //"SELECT * FROM BookContentEntity where id in (:id) "

    @Query("SELECT *  FROM Product  WHERE  name LIKE :name ")
    Product searhProduct(String name);
}
