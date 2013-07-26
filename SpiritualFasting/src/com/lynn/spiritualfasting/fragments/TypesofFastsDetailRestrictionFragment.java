package com.lynn.spiritualfasting.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.database.FastDB;
import com.lynn.spiritualfasting.util.Resources;

public class TypesofFastsDetailRestrictionFragment extends TypesofFastsDetailFragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.types_of_fasts_detail_fragment, container, false);

		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        
        FastDB db = new FastDB(getSherlockActivity());
        fastId = getArguments().getInt(Resources.FAST_ID);
        
        fastName = db.getItem(fastId).getName();
        String url = db.getItem(fastId).getUrl();
        db.close();
        
        WebView webView = (WebView) rootView.findViewById(R.id.types_of_fast_detail_webview);
		webView.loadUrl("file:///android_asset/types_of_fasts/purpose_" + url);
		
        return rootView;
    }
}
