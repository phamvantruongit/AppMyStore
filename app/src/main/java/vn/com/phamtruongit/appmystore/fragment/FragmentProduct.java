package vn.com.phamtruongit.appmystore.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import vn.com.phamtruongit.appmystore.ActivityAddProduct;
import vn.com.phamtruongit.appmystore.ApplicationMyStore;
import vn.com.phamtruongit.appmystore.MainActivity;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.adapter.SanPhamAdapter;
import vn.com.phamtruongit.appmystore.data.Product;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProduct extends BaseFragment  {
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    SanPhamAdapter sanPhamAdapter;
    RecyclerView.LayoutManager layoutManager;
    public FragmentProduct() {
    }

    @Override
    public int layout() {
        return R.layout.fragment_product;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        List<Product> productList= ApplicationMyStore.db.productDao().getListProduct();
        sanPhamAdapter=new SanPhamAdapter(productList);
        layoutManager=new LinearLayoutManager(getContext());
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setAdapter(sanPhamAdapter);
        sanPhamAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_product,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_menu){
            Intent intent=new Intent(getActivity(), ActivityAddProduct.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
