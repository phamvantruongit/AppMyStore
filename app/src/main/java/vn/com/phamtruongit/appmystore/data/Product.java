package vn.com.phamtruongit.appmystore.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "code")
    public String code;

    @ColumnInfo(name = "name")
    public String name;

   @ColumnInfo(name = "size")
    public String size;

   @ColumnInfo(name = "quantity")
    public int quantity;

   @ColumnInfo(name = "price_in")
    public double price_in;

   @ColumnInfo(name = "price_out")
    public double price_out;

   @ColumnInfo(name = "date")
    public String date;

   @ColumnInfo(name = "id_type_product")
    public int id_type_product;




}
