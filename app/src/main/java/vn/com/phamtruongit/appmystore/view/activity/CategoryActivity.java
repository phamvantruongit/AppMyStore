package vn.com.phamtruongit.appmystore.view.activity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import vn.com.phamtruongit.appmystore.ApplicationMyStore;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.adapter.AdapterLoaiSP;
import vn.com.phamtruongit.appmystore.data.TypeProduct;
import vn.com.phamtruongit.appmystore.view.Interface.OnClickItem;
import vn.com.phamtruongit.appmystore.view.activity.BaseActivity;


public class CategoryActivity extends BaseActivity implements OnClickItem {

    AdapterLoaiSP adapterLoaiSP;
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    RecyclerView.LayoutManager layoutManager;
    List<TypeProduct> productList;

    @Override
    void onCreate() {
        getData();

    }

    private void getData() {
        layoutManager = new LinearLayoutManager(this);
        productList = ApplicationMyStore.db.typeProductDao().getListTypeProduct();
        adapterLoaiSP = new AdapterLoaiSP(this, productList, this);
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setAdapter(adapterLoaiSP);
        adapterLoaiSP.notifyDataSetChanged();
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

            showDialog(null);
        }

        return true;
    }

    private void showDialog(TypeProduct typeProducts) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_add_loaisp);
        dialog.setCancelable(false);
        dialog.show();
        EditText edName = dialog.findViewById(R.id.edTenLoaiSP);
        TextView tv_Them = dialog.findViewById(R.id.btn_them);
        TextView tv_Huy = dialog.findViewById(R.id.btn_huy);

        if (typeProducts != null) {
            edName.setText(typeProducts.name);
        }

        tv_Them.setOnClickListener(v -> {
            String name = edName.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
                return;
            }
            TypeProduct typeProduct = new TypeProduct();
            typeProduct.name = name;
            if (typeProducts != null)
                ApplicationMyStore.db.typeProductDao().insertTypeProduct(typeProduct);
            else
                ApplicationMyStore.db.typeProductDao().insertTypeProduct(typeProduct);
            dialog.dismiss();
            getData();
        });

        tv_Huy.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    @Override
    public void onClichItem(Object object ,int position) {
        TypeProduct typeProduct = (TypeProduct) object;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_dialog);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }


        dialog.findViewById(R.id.tvHuy).setOnClickListener(v-> {
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tvThemSP).setOnClickListener(v->{
            dialog.dismiss();
            showDialog(null);
        });


        dialog.findViewById(R.id.tvSuaSP).setOnClickListener(v->{
            dialog.dismiss();
            showDialog(typeProduct);
        });

        dialog.findViewById(R.id.tvXoaSP).setOnClickListener(v->{
            dialog.dismiss();
        });
    }
}
