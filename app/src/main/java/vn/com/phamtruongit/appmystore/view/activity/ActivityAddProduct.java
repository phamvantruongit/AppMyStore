package vn.com.phamtruongit.appmystore.view.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;

import org.angmarch.views.NiceSpinner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.phamtruongit.appmystore.ApplicationMyStore;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.data.Product;
import vn.com.phamtruongit.appmystore.data.TypeProduct;

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
    private int id_type=1;
    Product product=null;

    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        boolean checkData=intent.getBooleanExtra("sendData",false);
        if(checkData){
            Bundle bundle=intent.getBundleExtra("product");
            product=bundle.getParcelable("product");
        }
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        init(product);
    }

    private void init(Product products) {

        if(products!=null){
            edTenSP.setText(products.name);
            edGiaNhap.setText(products.price_in+"");
            edGiaBan.setText(products.price_out+"");
            edSoLuong.setText(products.quantity+"");
            edSize.setText(products.size!=null  ? products.size : "");
        }

        List<String> list=new ArrayList<>();
        List<TypeProduct> typeProductList=ApplicationMyStore.db.typeProductDao().getListTypeProduct();
        for(TypeProduct typeProduct: typeProductList){
             list.add(typeProduct.name);
        }
        if(list.size()>0){
            nice_spinner.attachDataSource(list);
            nice_spinner.addOnItemClickListener((parent, view, position, id) -> {
                position++;
                id_type=position;
            });
        }



        btnThemMoi.setOnClickListener( v-> {
            String mavach=edMaVach.getText().toString();
            String tensp=edTenSP.getText().toString();
            String gianhap=edGiaNhap.getText().toString();
            String giaban=edGiaBan.getText().toString();
            String soluong=edSoLuong.getText().toString();
            String size=edSize.getText().toString();


            edGiaNhap.addTextChangedListener(new NumberTextWatcherForThousand(edGiaNhap));

            NumberTextWatcherForThousand numberTextWatcherForThousand = new NumberTextWatcherForThousand();
            numberTextWatcherForThousand.trimCommaOfString(edGiaNhap.getText().toString());

            edGiaBan.addTextChangedListener(new NumberTextWatcherForThousand(edGiaBan));
            numberTextWatcherForThousand.trimCommaOfString(edGiaBan.getText().toString());


            if(TextUtils.isEmpty(tensp)||TextUtils.isEmpty(gianhap)||TextUtils.isEmpty(giaban)||TextUtils.isEmpty(soluong)){
                Toast.makeText(this, "Nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
                return;
            }

            double GiaNhap= Double.parseDouble(gianhap);
            double GiaBan= Double.parseDouble(giaban);
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
           product.id_type_product=id_type;
           Log.d("ID",id_type+"");
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

        tvQuetMaVach.setOnClickListener(v->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED && checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED && checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
                    return;
                } else {
                    launchBarCodeActivity();
                }

            }
            launchBarCodeActivity();
        });

    }

    private void ads(){
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(getString(R.string.banner_home_footer));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB")
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
        mAdView.loadAd(adRequest);
    }

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchBarCodeActivity();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "error in  scanning", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
            edMaVach.setText(barcode.rawValue);
        }

    }

    public class NumberTextWatcherForThousand implements TextWatcher {

        EditText editText;

        public NumberTextWatcherForThousand() {
        }

        public NumberTextWatcherForThousand(EditText editText) {
            this.editText = editText;


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                editText.removeTextChangedListener(this);
                String value = editText.getText().toString();


                if (value != null && !value.equals("")) {

                    if (value.startsWith(".")) {
                        editText.setText("0.");
                    }
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        editText.setText("");

                    }


                    String str = editText.getText().toString().replaceAll(",", "");
                    if (!value.equals(""))
                        editText.setText(getDecimalFormattedString(str));
                    editText.setSelection(editText.getText().toString().length());
                }
                editText.addTextChangedListener(this);
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
                editText.addTextChangedListener(this);
            }

        }

        public String getDecimalFormattedString(String value) {
            StringTokenizer lst = new StringTokenizer(value, ".");
            String str1 = value;
            String str2 = "";
            if (lst.countTokens() > 1) {
                str1 = lst.nextToken();
                str2 = lst.nextToken();
            }
            String str3 = "";
            int i = 0;
            int j = -1 + str1.length();
            if (str1.charAt(-1 + str1.length()) == '.') {
                j--;
                str3 = ".";
            }
            for (int k = j; ; k--) {
                if (k < 0) {
                    if (str2.length() > 0)
                        str3 = str3 + "." + str2;
                    return str3;
                }
                if (i == 3) {
                    str3 = "," + str3;
                    i = 0;
                }
                str3 = str1.charAt(k) + str3;
                i++;
            }

        }

        public String trimCommaOfString(String string) {
            if (string.contains(",")) {
                return string.replace(",", "");
            } else {
                return string;
            }

        }
    }

}
