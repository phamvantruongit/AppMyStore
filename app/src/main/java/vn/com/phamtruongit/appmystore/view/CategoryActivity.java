package vn.com.phamtruongit.appmystore.view;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.com.phamtruongit.appmystore.ActivityAddProduct;
import vn.com.phamtruongit.appmystore.ApplicationMyStore;
import vn.com.phamtruongit.appmystore.MainActivity;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.data.TypeProduct;
import vn.com.phamtruongit.appmystore.fragment.FragmentTypeProduct;


public class CategoryActivity extends BaseActivity {
    @Override
    void onCreate() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_category;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_category;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_type_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_menu) {

            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_add_loaisp);
            dialog.setCancelable(false);
            dialog.show();
            EditText edName = dialog.findViewById(R.id.edTenLoaiSP);
            TextView tv_Them = dialog.findViewById(R.id.btn_them);
            TextView tv_Huy = dialog.findViewById(R.id.btn_huy);
            tv_Them.setOnClickListener(v -> {
                String name = edName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                TypeProduct typeProduct = new TypeProduct();
                typeProduct.name = name;
                ApplicationMyStore.db.typeProductDao().insertTypeProduct(typeProduct);
                dialog.dismiss();
            });

            tv_Huy.setOnClickListener(v->{
                dialog.dismiss();
            });

        }

        return true;
    }
}