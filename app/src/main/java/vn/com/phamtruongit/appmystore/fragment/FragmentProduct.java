package vn.com.phamtruongit.appmystore.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.com.phamtruongit.appmystore.ActivityAddProduct;
import vn.com.phamtruongit.appmystore.ApplicationMyStore;
import vn.com.phamtruongit.appmystore.MainActivity;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.adapter.SanPhamAdapter;
import vn.com.phamtruongit.appmystore.data.Product;
import vn.com.phamtruongit.appmystore.data.TypeProduct;
import vn.com.phamtruongit.appmystore.data.interfaces.ItemAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProduct extends BaseFragment implements ItemAdapter {
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @BindView(R.id.nice_spinner)
    NiceSpinner niceSpinner;
    SanPhamAdapter sanPhamAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<String> list;
    List<Product> productList;
    private int id_type_product;
    @Override
    public void getData() {
        list=new ArrayList<>();
        List<TypeProduct> listTypeProduct=ApplicationMyStore.db.typeProductDao().getListTypeProduct();
        for(TypeProduct typeProduct : listTypeProduct){
            list.add(typeProduct.name);
        }
    }

    @Override
    public int layout() {
        return R.layout.fragment_product;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(list.size()>0){
            niceSpinner.attachDataSource(list);
            niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    position++;
                    id_type_product=position;
                    productList = ApplicationMyStore.db.productDao().getListProduct(id_type_product);
                    getDataProduct();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }



    }



    public void getDataProduct(){
        sanPhamAdapter = new SanPhamAdapter(productList, this);
        layoutManager = new LinearLayoutManager(getContext());
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setAdapter(sanPhamAdapter);
        sanPhamAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        productList = ApplicationMyStore.db.productDao().getListProduct(1);
        getDataProduct();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_product, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu) {
            Intent intent = new Intent(getActivity(), ActivityAddProduct.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemAdapter(Object object ,int position) {
        Product product = (Product) object;
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_dialog);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialog.findViewById(R.id.tvHuy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.tvThemSP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), ActivityAddProduct.class);
                intent.putExtra("id_loaisp", product.id_type_product);
                intent.putExtra("editProduct", false);
                startActivityForResult(intent, 100);
            }
        });

        dialog.findViewById(R.id.tvSuaSP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), ActivityAddProduct.class);
                intent.putExtra("editProduct", true);
                Bundle bundle = new Bundle();
                bundle.putParcelable("product" ,product);
                intent.putExtra("product", bundle);
                startActivityForResult(intent, 101);
            }
        });
        dialog.findViewById(R.id.tvXoaSP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationMyStore.db.productDao().deleteProduct(product.id);
                dialog.dismiss();
                productList.remove(position);
                sanPhamAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Xoa san pham thanh cong", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
