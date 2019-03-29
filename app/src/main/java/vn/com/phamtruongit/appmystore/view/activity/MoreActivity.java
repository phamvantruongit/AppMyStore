package vn.com.phamtruongit.appmystore.view.activity;

import android.content.Intent;
import android.widget.TextView;
import butterknife.BindView;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.view.activity.ActivityAddProduct;
import vn.com.phamtruongit.appmystore.view.activity.BaseActivity;


public class MoreActivity extends BaseActivity {

    @BindView(R.id.tvThemSP)
    TextView tvThemSP;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_more;
    }


    @Override
    void onCreate() {
       tvThemSP.setOnClickListener(v->{
           Intent intent=new Intent(this,ActivityAddProduct.class);
           startActivity(intent);

       });
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_more;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
