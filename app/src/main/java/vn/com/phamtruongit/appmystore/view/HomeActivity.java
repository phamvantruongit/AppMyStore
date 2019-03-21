package vn.com.phamtruongit.appmystore.view;

import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.view.BaseActivity;

public class HomeActivity extends BaseActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }
}
