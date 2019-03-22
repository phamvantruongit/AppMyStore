package vn.com.phamtruongit.appmystore.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.com.phamtruongit.appmystore.R;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    Unbinder unbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        unbinder=  ButterKnife.bind(this);
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        onCreate();


    }

    abstract void onCreate();


    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();

    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent;
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_home) {
            intent= new Intent(this, HomeActivity.class);
            startActivity(intent);

        } else if (itemId == R.id.navigation_category) {
            intent= new Intent(this, CategoryActivity.class);
            startActivity(intent);

        } else if (itemId == R.id.navigation_products) {
            intent= new Intent(this, ProductActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navigation_more) {
            intent= new Intent(this, MoreActivity.class);
            startActivity(intent);

        }
        finish();

        return true;
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    protected abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
               finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
