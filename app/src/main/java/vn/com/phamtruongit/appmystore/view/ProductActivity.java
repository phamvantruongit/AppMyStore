package vn.com.phamtruongit.appmystore.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import vn.com.phamtruongit.appmystore.ApplicationMyStore;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.adapter.SanPhamAdapter;
import vn.com.phamtruongit.appmystore.data.Product;

public  class ProductActivity  extends BaseActivity {
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    SanPhamAdapter sanPhamAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    void onCreate() {
       tvTitleApp.setText(getResources().getText(R.string.title_products));
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Product> productList= ApplicationMyStore.db.productDao().getListProduct();
        sanPhamAdapter=new SanPhamAdapter(productList);
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

}