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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import vn.com.phamtruongit.appmystore.ActivityAddProduct;
import vn.com.phamtruongit.appmystore.ApplicationMyStore;
import vn.com.phamtruongit.appmystore.MainActivity;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.adapter.AdapterLoaiSP;
import vn.com.phamtruongit.appmystore.data.TypeProduct;
import vn.com.phamtruongit.appmystore.data.interfaces.ItemAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTypeProduct extends BaseFragment implements ItemAdapter {
   MainActivity mainActivity;
   AdapterLoaiSP adapterLoaiSP;
   @BindView(R.id.rv_product)
    RecyclerView rvProduct;
   RecyclerView.LayoutManager layoutManager;
   List<TypeProduct> productList;

    public FragmentTypeProduct() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager=new LinearLayoutManager(getActivity());
    }

    @Override
    public void getData() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productList= ApplicationMyStore.db.typeProductDao().getListTypeProduct();
        adapterLoaiSP=new AdapterLoaiSP(getActivity(),productList,this);
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setAdapter(adapterLoaiSP);
        adapterLoaiSP.notifyDataSetChanged();
    }

    @Override
    public int layout() {
        return R.layout.fragment_type_product;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_type_product,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId()==R.id.action_menu) {
           Dialog dialog = new Dialog(getActivity());
           dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
           dialog.setContentView(R.layout.popup_add_loaisp);
           dialog.show();
           EditText edName = dialog.findViewById(R.id.edTenLoaiSP);
           TextView tv_Them = dialog.findViewById(R.id.btn_them);
           TextView tv_Huy = dialog.findViewById(R.id.btn_huy);
           tv_Them.setOnClickListener(v -> {
               String name = edName.getText().toString();
               if (TextUtils.isEmpty(name)) {
                   Toast.makeText(getActivity(), "Enter name", Toast.LENGTH_SHORT).show();
                   return;
               }
               TypeProduct typeProduct = new TypeProduct();
               typeProduct.name = name;
               ApplicationMyStore.db.typeProductDao().insertTypeProduct(typeProduct);
               dialog.dismiss();

           });
       }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void itemAdapter(Object object ,int position) {
        TypeProduct typeProduct= (TypeProduct) object;
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
                Intent intent=new Intent(getActivity(),ActivityAddProduct.class);
                intent.putExtra("id_loaisp",typeProduct.id);
                startActivityForResult(intent,100);
            }
        });

        dialog.findViewById(R.id.tvSuaSP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(getActivity(),ActivityAddProduct.class);
                intent.putExtra("id_loaisp",typeProduct.id);
                intent.putExtra("editSP",true);
                Bundle bundle=new Bundle();
                //bundle.putParcelable("sanpham",sanPham);
                intent.putExtra("sanpham",bundle);
                startActivityForResult(intent,101);
            }
        });
        dialog.findViewById(R.id.tvXoaSP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                database.xoaSanPham(sanPham.getId());
//                listSanPham.remove(position);
//                sanPhamAdapter.notifyDataSetChanged();
                //Toast.makeText(SanPhamActivity.this, "Xoa san pham thanh cong", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
