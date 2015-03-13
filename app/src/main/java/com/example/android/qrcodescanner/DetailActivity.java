package com.example.android.qrcodescanner;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class DetailActivity extends ActionBarActivity {

    private ImageView comment,clipboard,share;
    String qrValue;
    private TextView qr;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        comment= (ImageView) findViewById(R.id.comment);
        clipboard= (ImageView) findViewById(R.id.clipboard);
        share= (ImageView) findViewById(R.id.share);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processMessage();
            }
        });

        clipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCopy();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processShare();
            }
        });
        Intent i=getIntent();
        qrValue=i.getStringExtra("qr_value");
        qr= (TextView) findViewById(R.id.qrValue);
        qr.setText(qrValue);
        adView = (AdView) findViewById(R.id.advertiseView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(DetailActivity.this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called before the activity is destroyed. */
    @Override
    public void onDestroy() {
        // Destroy the AdView.
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void processMessage() {

        Intent intentsms = new Intent( Intent.ACTION_VIEW, Uri.parse("sms:" + "") );
        intentsms.putExtra( "sms_body",qrValue);
        startActivity(intentsms);

    }

    private void processShare() {

        Intent iShare = new Intent(Intent.ACTION_SEND);
        iShare.setType("text/plain");
        iShare.putExtra(Intent.EXTRA_TEXT,qrValue);
        startActivity(Intent.createChooser(iShare, "Share with"));

    }

    private void processCopy() {

        MyClipboardManager manager = new MyClipboardManager();
        manager.copyToClipboard(DetailActivity.this,qrValue);
        Toast.makeText(this, "Copied To ClipBoard", Toast.LENGTH_SHORT).show();

    }

}
