package vn.com.phamtruongit.appmystore;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.com.phamtruongit.appmystore.data.TypeProduct;
import vn.com.phamtruongit.appmystore.fragment.FragmentHome;
import vn.com.phamtruongit.appmystore.fragment.FragmentProduct;
import vn.com.phamtruongit.appmystore.fragment.FragmentTypeProduct;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    private boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            fragment = new FragmentHome();
            loadFrament(fragment);
        }
         updateApp();
    }

    private void updateApp() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_update_app);
        dialog.setCancelable(false);
        dialog.show();
        TextView tvGoto = dialog.findViewById(R.id.tvGoto);
        TextView tvAfter = dialog.findViewById(R.id.tvAfter);
        tvAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }

        });
    }

    private void loadFrament(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_type_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_menu) {
            if (check) {
                Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_add_loaisp);
                dialog.setCancelable(false);
                dialog.show();
                EditText edName = dialog.findViewById(R.id.edTenLoaiSP);
                TextView tv_Them = dialog.findViewById(R.id.btn_them);
                TextView tv_Huy = dialog.findViewById(R.id.btn_huy);
                tv_Them.setOnClickListener(v -> {
                    String name = edName.getText().toString();
                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    TypeProduct typeProduct = new TypeProduct();
                    typeProduct.name = name;
                    ApplicationMyStore.db.typeProductDao().insertTypeProduct(typeProduct);
                    dialog.dismiss();
                    fragment = new FragmentTypeProduct();
                    loadFrament(fragment);
                });
            } else {
                Intent intent = new Intent(MainActivity.this, ActivityAddProduct.class);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragment = new FragmentHome();
            loadFrament(fragment);
        } else if (id == R.id.nav_gallery) {
            check = true;
            fragment = new FragmentTypeProduct();
            loadFrament(fragment);
        } else if (id == R.id.nav_slideshow) {
            check = false;
            fragment = new FragmentProduct();
            loadFrament(fragment);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            ShareCompat.IntentBuilder.from(MainActivity.this)
                    .setType("text/plain")
                    .setChooserTitle("Share application " + getResources().getString(R.string.app_name))
                    .setText("https://play.google.com/store/apps/details?id=" + getPackageName())
                    .startChooser();
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"phamvantruongit@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Write title");
            intent.putExtra(Intent.EXTRA_TEXT, "Write some things");
            startActivity(Intent.createChooser(intent, ""));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
