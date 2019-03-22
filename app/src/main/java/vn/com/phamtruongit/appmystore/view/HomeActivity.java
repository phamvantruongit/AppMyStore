package vn.com.phamtruongit.appmystore.view;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import vn.com.phamtruongit.appmystore.ApplicationMyStore;
import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.data.Product;

public class HomeActivity extends BaseActivity {
    //https://www.journaldev.com/12478/android-searchview-example-tutorial
    @BindView(R.id.searchview)
    SearchView searchview;

    @BindView(R.id.rv_product)
    RecyclerView rv_product;

    RecyclerView.LayoutManager layoutManager;

    @Override
    void onCreate() {


        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Product product=ApplicationMyStore.db.productDao().searhProduct(query.trim());
                if(product!=null){
                    Log.d("Product",product.name + product.code +"--");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }

    private void updateApp() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_update_app);
        dialog.setCancelable(false);
        dialog.show();
        TextView tvGoto = dialog.findViewById(R.id.tvGoto);
        TextView tvAfter = dialog.findViewById(R.id.tvAfter);


        tvAfter.setOnClickListener(v -> {
            dialog.dismiss();
        });

        tvGoto.setOnClickListener(v -> {
            dialog.dismiss();
            String url;

            try {
                getPackageManager().getPackageInfo("com.android.vending", 0);
                url = "market://details?id=" + getPackageName();
            } catch (final Exception e) {
                url = "https://play.google.com/store/apps/details?id=" + getPackageName();
            }

            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            startActivity(intent);
        });


//        if (id == R.id.nav_share) {
//            ShareCompat.IntentBuilder.from(MainActivity.this)
//                    .setType("text/plain")
//                    .setChooserTitle("Share application " + getResources().getString(R.string.app_name))
//                    .setText("https://play.google.com/store/apps/details?id=" + getPackageName())
//                    .startChooser();
//        } else if (id == R.id.nav_send) {
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("plain/text");
//            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"phamvantruongit@gmail.com"});
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Write title");
//            intent.putExtra(Intent.EXTRA_TEXT, "Write some things");
//            startActivity(Intent.createChooser(intent, ""));
//        }
    }


    public void OpenQrcode(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED && checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED && checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
                return;
            } else {
                Intent intent = new Intent(this, QRCodeScannerActivity.class);
                startActivityForResult(intent, 100);
            }

        }
        Intent intent = new Intent(this, QRCodeScannerActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, QRCodeScannerActivity.class);
                startActivityForResult(intent, 100);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
