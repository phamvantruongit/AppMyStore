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


    @Query("SELECT *  FROM Product  WHERE  name LIKE :name ")
    List<Product> searhProduct(String name);

    @Query("SELECT *  FROM Product  WHERE  code LIKE :code ")
    Product searhProductBarcode(String code);


    @Query("DELETE FROM Product where id in (:id)")
    void deleteProduct(int id);


    @Query("UPDATE Product SET code = :code , name=:name, size=:size,quantity=:quantity, price_in=:price_in,price_out=:price_out ,date=:date, id_type_product=:id_type_product WHERE id =:id")
    void updateProduct(String code , String name , String size , int quantity , double price_in , double price_out ,String date , int id_type_product ,int id);
}
