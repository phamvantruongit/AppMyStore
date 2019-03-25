package vn.com.phamtruongit.appmystore.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM Product where id_type_product  in (:id) ")
    List<Product> getListProduct(int id);

    @Query("UPDATE Product SET code =(:code) ,name = (:name) ,size =(:size) ,price_in= (:price_in) ,price_out= (:price_out) ,category =(:category) ,quantity =(:quantity) WHERE id in (:id)")
    void updateProduct(int id ,String name ,String code , String size , int quantity , double price_in , double price_out ,String category );

    @Query("DELETE FROM Product where id in (:id)")
    void deleteProduct(int id);
}
