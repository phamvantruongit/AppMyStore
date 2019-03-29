package vn.com.phamtruongit.appmystore.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.List;
import java.util.StringTokenizer;

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
        sanPhamAdapter=new SanPhamAdapter(productList,this,false);
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
    public void onClichItem(Object object ,int postion) {
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
            ApplicationMyStore.db.productDao().deleteProduct(product.id);
            sanPhamAdapter.notifyItemRemoved(postion);
            dialog.dismiss();
        });
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