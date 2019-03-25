package vn.com.phamtruongit.appmystore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.List;

import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.data.Product;
import vn.com.phamtruongit.appmystore.data.interfaces.ItemAdapter;


public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    List<Product> listMain;
    IOnClick iOnClick;
    boolean check;
    //Database database;
    Context context;
    ItemAdapter itemAdapter;


    public SanPhamAdapter(List<Product> list ,ItemAdapter itemAdapter){
        listMain=list;
        this.itemAdapter=itemAdapter;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sanpham, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.tvTenSP.setText(listMain.get(position).name);
        viewHolder.tvDanhMuc.setText(listMain.get(position).category);
//        if(check){
//            viewHolder.ckbox.setVisibility(View.VISIBLE);
//            if(listMain.get(position).getStatus().equals("1")){
//                viewHolder.ckbox.setChecked(true);
//            }
//        }

//        if (listMain.get(position).size.length()>0) {
//            viewHolder.tv_Size.setVisibility(View.VISIBLE);
//            viewHolder.tvSize.setVisibility(View.VISIBLE);
//            viewHolder.tvSize.setText(listMain.get(position).size);
//        }

        viewHolder.itemView.setOnClickListener(v->{
            itemAdapter.itemAdapter(listMain.get(position),position);
        });

        viewHolder.tvSLSP.setText(listMain.get(position).quantity+"");
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        double gia= listMain.get(position).price_out;
        viewHolder.tvGia.setText(decimalFormat.format(gia) +" VND");


        viewHolder.ckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //database.updateStatus(listMain.get(position).getId(),"1");
                    iOnClick.iOnClick(listMain.get(position),position);
                }else {
                   //database.updateStatus(listMain.get(position).getId(),"0");
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return listMain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP, tvTTSP, tvSLSP, tvSize, tv_Size, tvGia,tvDanhMuc;
        CheckBox ckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tv_tensp);
            tvTTSP = itemView.findViewById(R.id.tv_TTSanPham);
            tvSLSP = itemView.findViewById(R.id.tvSLSP);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvSize = itemView.findViewById(R.id.tvSizeSP);
            tv_Size = itemView.findViewById(R.id.tv_SizeSP);
            ckbox=itemView.findViewById(R.id.ckbox);
            tvDanhMuc=itemView.findViewById(R.id.tvDanhMuc);
        }
    }

    public interface  IOnClick{
        void iOnClick(Product sanPham, int position);
    }


}
