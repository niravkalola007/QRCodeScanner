package com.example.android.qrcodescanner;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class MainActivity extends ActionBarActivity implements ZBarScannerView.ResultHandler{
    private ZBarScannerView mScannerView;
    private TextView txtQRCode;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScannerView= (ZBarScannerView) findViewById(R.id.scanner);
        txtQRCode= (TextView) findViewById(R.id.txtQRCode);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("HOME");
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
//        Toast.makeText(this, "Contents = " + rawResult.getContents() +
//                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();
        mScannerView.startCamera();
        txtQRCode.setText(rawResult.getContents());
    }

}
