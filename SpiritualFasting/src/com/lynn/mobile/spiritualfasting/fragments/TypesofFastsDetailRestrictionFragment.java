package com.lynn.mobile.spiritualfasting.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.util.Resources;
import com.lynn.mobile.spiritualfasting.R;

public class TypesofFastsDetailRestrictionFragment extends TypesofFastsDetailFragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.types_of_fasts_detail_fragment, container, false);

		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        
        FastDB db = new FastDB(getSherlockActivity());
        fastId = getArguments().getInt(Resources.FAST_ID);
        
        Fast currentFast = db.getItem(fastId);
		fastName = currentFast.getName();
        String url = currentFast.getUrl();
        db.close();
        
        WebView webView = (WebView) rootView.findViewById(R.id.types_of_fast_detail_webview);
		
		if(!url.startsWith("custom_fast")) {
			webView.loadUrl("file:///android_asset/types_of_fasts/purpose_" + url);
        } else {
        	String page = "<html><head><link rel='stylesheet' type='text/css' href='file:///android_asset/css/style.css'/></head><body>" +
					currentFast.getDetails() +
					"</body></html>";
		            
		    webView.loadDataWithBaseURL("x-data://base", page, "text/html", "UTF-8", null);
        }
		
        return rootView;
    }
}
