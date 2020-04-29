package com.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import im.vector.R;

public class SettingsWebActivity extends AppCompatActivity {

    private String url = "";

    private WebView webView;
    SharedPreferences settings;


    Activity activity;
    private ProgressDialog progDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_web);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
        }
        setUrl();
        activity = this;
        progDailog = ProgressDialog.show(activity, "Loading", "Please wait...", true);
        progDailog.setCancelable(false);
        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });
        webView.loadUrl(url);
    }

    private void setUrl() {
        Bundle b = getIntent().getExtras();
        url = b.getString("Bundle");
        if (url.equalsIgnoreCase("Why")) {
            url = "https://viido.it";
            setTitle("Why Viido ?");
        } else if (url.equalsIgnoreCase("Credit")) {
            String userName = settings.getString("Username", "");
            String password = settings.getString("Password", "");
            url = "https://billing.viido.it/crm/customer/mobile_payment.php?pr_login=" + userName + "&pr_password=" + password + "&mobiledone=submit_log";
            setTitle("Buy Credit");
        } else if (url.equalsIgnoreCase("Topup")) {
            String userName = settings.getString("Username", "");
            String password = settings.getString("Password", "");
            url = "https://billing.viido.it/crm/customer/billing_mobile_app.php?pr_login=" + userName + "&pr_password=" + password + "&mobiledone=submit_log";
            setTitle("Topup");
        }
    }

    public void setTitle(String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (Exception e) {
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
