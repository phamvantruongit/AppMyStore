package vn.com.phamtruongit.appmystore.view;

import vn.com.phamtruongit.appmystore.R;

public  class ProductActivity  extends BaseActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_products;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_products;
    }
}