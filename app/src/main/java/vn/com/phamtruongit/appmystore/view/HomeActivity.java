package vn.com.phamtruongit.appmystore.view;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import vn.com.phamtruongit.appmystore.R;
import vn.com.phamtruongit.appmystore.view.BaseActivity;

public class HomeActivity extends BaseActivity {
    @Override
    void onCreate() {
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


}
