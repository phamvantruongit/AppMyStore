package vn.com.phamtruongit.appmystore.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.view.BaseActivity;

public class MoreActivity extends BaseActivity  {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_more;
    }


    @Override
    void onCreate() {

    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_more;
    }

}
