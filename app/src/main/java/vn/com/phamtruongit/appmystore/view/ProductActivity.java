package vn.com.phamtruongit.appmystore.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import butterknife.BindView;
import vn.com.phamtruongit.appmystore.ApplicationMyStore;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.adapter.SanPhamAdapter;
import vn.com.phamtruongit.appmystore.data.Product;
import vn.com.phamtruongit.appmystore.view.Interface.OnClickItem;

public  class ProductActivity  extends BaseActivity implements OnClickItem {
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    SanPhamAdapter sanPhamAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    void onCreate() {
       //tvTitleApp.setText(getResources().getText(R.string.title_products));
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Product> productList= ApplicationMyStore.db.productDao().getListProduct();
        sanPhamAdapter=new SanPhamAdapter(productList,this);
        layoutManager=new LinearLayoutManager(this);
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setAdapter(sanPhamAdapter);
        sanPhamAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_products;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_products;
    }

    @Override
    public void onClichItem(Object object) {
        Product product= (Product) object;
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
            Intent intent=new Intent(ProductActivity.this,ActivityAddProduct.class);
            intent.putExtra("sendData",false);
            startActivity(intent);

        });


        dialog.findViewById(R.id.tvSuaSP).setOnClickListener(v->{
            dialog.dismiss();
            Intent intent=new Intent(ProductActivity.this,ActivityAddProduct.class);
            intent.putExtra("sendData",true);
            Bundle bundle=new Bundle();
            bundle.putParcelable("product",product);
            intent.putExtra("product",bundle);
            startActivity(intent);

        });

        dialog.findViewById(R.id.tvXoaSP).setOnClickListener(v->{
            dialog.dismiss();
        });
    }
}