package vn.com.phamtruongit.appmystore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ActivityReadBarcode extends  AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initScannerView();
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void handleResult(Result result) {

        Intent intent=new Intent();
        intent.putExtra("data",result.getText());
        setResult(RESULT_OK, intent);
        finish();


    }

    private void initScannerView() {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        ArrayList<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        mScannerView.setFormats(formats);

        mScannerView.setResultHandler(this);

        mScannerView.startCamera();
    }
}
