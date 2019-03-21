package vn.com.phamtruongit.appmystore.view;

import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.view.BaseActivity;

public class MoreActivity extends BaseActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_more;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_more;
    }
}
