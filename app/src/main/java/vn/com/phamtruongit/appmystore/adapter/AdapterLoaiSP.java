package vn.com.phamtruongit.appmystore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.data.TypeProduct;
import vn.com.phamtruongit.appmystore.view.Interface.OnClickItem;

public class AdapterLoaiSP extends RecyclerView.Adapter<AdapterLoaiSP.ViewHolder> {
    Context context;
    List<TypeProduct> listLoaiSP;
    OnClickItem onClickItem;
    public AdapterLoaiSP(Context context, List<TypeProduct> listLoaiSP, OnClickItem onClickItem) {
        this.context = context;
        this.listLoaiSP = listLoaiSP;
        this.onClickItem=onClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_loaisp,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       viewHolder.tv_loaisp.setText(listLoaiSP.get(i).name);
       viewHolder.itemView.setOnClickListener(v->{
           onClickItem.onClichItem(listLoaiSP.get(i),i);
       });
    }

    @Override
    public int getItemCount() {
        return listLoaiSP.size()>0 ? listLoaiSP.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_loaisp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_loaisp=itemView.findViewById(R.id.tv_loaisp);
        }
    }
}
