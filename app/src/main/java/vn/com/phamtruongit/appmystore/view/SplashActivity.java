package vn.com.phamtruongit.appmystore.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.com.phamtruongit.appmystore.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);//Left to right
                finish();

            }
        },1500);
    }
}
