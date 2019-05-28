package com.chetan.stackoverflow.ui.auth;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.chetan.stackoverflow.R;
import com.chetan.stackoverflow.utils.Constants;
import com.chetan.stackoverflow.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity";

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProviderFactory providerFactory;

    private AuthViewModel viewModel;

    private WebView webView;
    private Button button;
    private FrameLayout container;
    private ConstraintLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Log.d(TAG, "onCreate: auth activity");

        ImageView imageView = findViewById(R.id.imageView_logo);

        webView = findViewById(R.id.web_container);
        button = findViewById(R.id.button_login);
        container = findViewById(R.id.container);
        mainContainer = findViewById(R.id.main_container);

        requestManager.load(logo).into(imageView);

        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button clicked");
                mainContainer.setVisibility(View.GONE);
                webview();

            }
        });

    }


    private void webview() {
        webView.setWebContentsDebuggingEnabled(true);

        webView.getSettings().setUserAgentString("stack_android_app");

        // Cookie manager for the webview
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);

        webView.setWebViewClient(new MyCustomWebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.setWebChromeClient(new MyCustomChromeClient());

        webView.loadUrl("https://stackoverflow.com/oauth/dialog" +
                "?client_id=" + 14910 +
                "&scope=" + "read_inbox" +
                "&redirect_uri=" + "https://stackexchange.com/oauth/login_success");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "onPageStarted: Ok");
            }

            String authCode;

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished: url:" + url);
                if (url.contains("access_token=")) {
                    String strAccessToken = getAccessToken(url);

                    Log.d("Acess Token", strAccessToken);
                    if (!strAccessToken.equals("0")) {
                        Toast.makeText(getApplicationContext(), "Authenticated Successfully", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onPageFinished: authenticated");
                    }
                } else if (url.contains("error=access_denied")) {
                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onPageFinished: access denied");
                } else {
                    Log.d(TAG, "onPageFinished: does not contain access token");
                }
            }
        });
    }

    private String getAccessToken(String url) {
        int i = 0;
        final String[] split = url.split("#|&|=");
        for (String values : split) {
            if (i == 2) {
                //AppConstants.savePreferences(this,"AcessToken",values);
                Log.d(TAG, "getAccessToken: token:" + values);
                return values;
            }
            i++;
        }
        return null;
    }


    private class MyCustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            Log.d(TAG, "onPageStarted: LOADING STARTED ...");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String host = Uri.parse(url).getHost();
            Log.d(TAG, host);
            //Toast.makeText(MainActivity.this, host,
            //Toast.LENGTH_SHORT).show();
            if (host.equals(Constants.DOMAIN)) {
                // This is my webView site, so do not override; let my WebView load
                // the page
                if (webView != null) {
                    webView.setVisibility(View.GONE);
                    container.removeView(webView);
                    webView = null;
                }
                return false;
            }

            if (host.contains("m.facebook.com") || host.contains("facebook.co")
                    || host.contains("google.co")
                    || host.contains("www.facebook.com")
                    || host.contains(".google.com")
                    || host.contains(".google.co")
                    || host.contains("accounts.google.com")
                    || host.contains("accounts.google.co.in")
                    || host.contains("www.accounts.google.com")
                    || host.contains("www.twitter.com")
                    || host.contains("secure.payu.in")
                    || host.contains("https://secure.payu.in")
                    || host.contains("oauth.googleusercontent.com")
                    || host.contains("content.googleapis.com")
                    || host.contains("ssl.gstatic.com")) {
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch
            // another Activity that handles URLs
            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            //startActivity(intent);
            //return true;
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            Log.d(TAG, "onPageFinished: LOADING...");
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            Log.d("onReceivedSslError", "onReceivedSslError");
            // super.onReceivedSslError(view, handler, error);
        }
    }


    private class MyCustomChromeClient extends WebChromeClient {

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            webView = new WebView(AuthActivity.this);
            webView.setVerticalScrollBarEnabled(true);
            webView.setHorizontalScrollBarEnabled(false);
            webView.setWebViewClient(new MyCustomWebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            container.addView(webView);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(webView);
            resultMsg.sendToTarget();

            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            super.onProgressChanged(view, newProgress);

        }

        @Override
        public void onCloseWindow(WebView window) {
            Log.d("onCloseWindow", "called");
        }

    }
}
