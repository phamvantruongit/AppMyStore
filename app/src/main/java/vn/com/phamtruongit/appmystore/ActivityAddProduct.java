package vn.com.phamtruongit.appmystore;

import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.phamtruongit.appmystore.data.Product;

public class ActivityAddProduct extends AppCompatActivity {
    @BindView(R.id.edMaVach)
    EditText edMaVach;
    @BindView(R.id.edTenSP)
    EditText edTenSP;
    @BindView(R.id.edGiaNhap)
    EditText edGiaNhap;
    @BindView(R.id.edGiaBan)
    EditText edGiaBan;
    @BindView(R.id.edSoLuong)
    EditText edSoLuong;
    @BindView(R.id.edSize)
    EditText edSize;
    @BindView(R.id.tvTaoMaVach)
    TextView tvTaoMaVach;
    @BindView(R.id.tvQuetMaVach)
    TextView tvQuetMaVach;
    @BindView(R.id.btnThemMoi)
    Button btnThemMoi;
    @BindView(R.id.btnHuyBo)
    Button btnHuyBo;
    @BindView(R.id.nice_spinner)
    NiceSpinner nice_spinner;

    private String code="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        List<String> list=new ArrayList<>();
        list.add(new String("Quần "));
        list.add(new String("Giầy dép"));
        list.add(new String("Quần "));
        list.add(new String("Giầy dép"));
        list.add(new String("Quần "));
        list.add(new String("Giầy dép"));
        list.add(new String("Quần "));
        list.add(new String("Giầy dép áo sơ mi"));
        nice_spinner.attachDataSource(list);
        nice_spinner.setSelectedIndex(3);

        btnThemMoi.setOnClickListener(v->{
            String mavach=edMaVach.getText().toString();
            String tensp=edTenSP.getText().toString();
            String gianhap=edGiaNhap.getText().toString();
            String giaban=edGiaBan.getText().toString();
            String soluong=edSoLuong.getText().toString();
            String size=edSize.getText().toString();

            if(TextUtils.isEmpty(tensp)||TextUtils.isEmpty(gianhap)||TextUtils.isEmpty(giaban)||TextUtils.isEmpty(soluong)){
                Toast.makeText(this, "Nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
                return;
            }
            int GiaNhap= Integer.parseInt(gianhap);
            int GiaBan= Integer.parseInt(giaban);
            if(GiaBan<GiaNhap){
                Toast.makeText(this, "Giá bán phải >= giá nhập", Toast.LENGTH_SHORT).show();
                return;
            }


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date nowtime = new Date();
            String strnowtime = sdf.format(nowtime);


            Product product=new Product();
            if(!TextUtils.isEmpty(mavach)){
                product.code=mavach;
            }
            product.name=tensp;
            product.price_in=GiaNhap;
            product.price_out=GiaBan;
            product.quantity= Integer.parseInt(soluong);
            if(!TextUtils.isEmpty(size)){
                product.size=size;
            }
           product.date=strnowtime;
           product.id_type_product=1;
           ApplicationMyStore.db.productDao().insertProduct(product);





        });


        tvTaoMaVach.setOnClickListener(v->{
            for (int j=0;j < 1;j++)

            {     Random rand = new Random();
                code= String.valueOf(rand.nextInt()).replace("-","");
                int i=0;
                while (code.length()<10){
                    code+=i++;
                }
                edMaVach.setText(code);
            }
        });

    }
}
