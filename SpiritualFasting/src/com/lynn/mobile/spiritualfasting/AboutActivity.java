package com.lynn.mobile.spiritualfasting;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.webview_layout);
		setupMenu();
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

		WebView webView = (WebView) findViewById(R.id.webView);
		webView.loadUrl("file:///android_asset/about.html");
	}
}
