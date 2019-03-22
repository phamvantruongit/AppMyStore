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
import vn.com.phamtruongit.appmystore.view.Interface.OnClickItem;


public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    List<Product> listMain;
    IOnClick iOnClick;
    boolean check;
    //Database database;
    Context context;
    OnClickItem onClickItem;

    public SanPhamAdapter(List<Product> list ,OnClickItem item){
        listMain=list;

       // database=new Database(context);
       onClickItem=item;
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

        viewHolder.tvSLSP.setText(listMain.get(position).quantity+"");
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        double gia= listMain.get(position).price_out;
        viewHolder.tvGia.setText(decimalFormat.format(gia) +"VND");


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
        viewHolder.itemView.setOnClickListener(v -> {
            onClickItem.onClichItem(listMain.get(position));
        });


    }


    @Override
    public int getItemCount() {
        return listMain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP, tvTTSP, tvSLSP, tvSize, tv_Size, tvGia;
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
        }
    }

    public interface  IOnClick{
        void iOnClick(Product sanPham, int position);
    }


}
