package vn.com.phamtruongit.appmystore.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

@Entity
public class Product implements Parcelable  {

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

    public Product() {
    }

    protected Product(Parcel in) {
        id = in.readInt();
        code = in.readString();
        name = in.readString();
        size = in.readString();
        quantity = in.readInt();
        price_in = in.readDouble();
        price_out = in.readDouble();
        date = in.readString();
        id_type_product = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(size);
        dest.writeInt(quantity);
        dest.writeDouble(price_in);
        dest.writeDouble(price_out);
        dest.writeString(date);
        dest.writeInt(id_type_product);
    }

}
