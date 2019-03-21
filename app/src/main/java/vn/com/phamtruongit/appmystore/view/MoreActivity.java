package vn.com.phamtruongit.appmystore.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import vn.com.phamtruongit.appmystore.ActivityAddProduct;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.view.BaseActivity;

public class MoreActivity extends BaseActivity  {

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
