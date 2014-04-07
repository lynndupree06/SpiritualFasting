package com.lynn.mobile.spiritualfasting.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import com.lynn.mobile.spiritualfasting.CreateFastActivity;
import com.lynn.mobile.spiritualfasting.CreateFastTypeActivity;
import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.util.Resources;
import com.lynn.mobile.spiritualfasting.R;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class TypesofFastsDetailFragment extends SherlockFragment {

	protected int fastId;
	protected String fastName;

	public TypesofFastsDetailFragment() {
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.types_of_fasts_detail_fragment, container, false);
        
        FastDB db = new FastDB(getSherlockActivity());
        fastId = getArguments().getInt(Resources.FAST_ID);
        
        Fast currentFast = db.getItem(fastId);
		fastName = currentFast.getName();
        String url = currentFast.getUrl();
        db.close();
        
        getSherlockActivity().setTitle(fastName);
		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        
        WebView webView = (WebView) rootView.findViewById(R.id.types_of_fast_detail_webview);
		
        if(!url.startsWith("custom_fast")) {
        	webView.loadUrl("file:///android_asset/types_of_fasts/" + url);
        } else {
        	String page = "<html><head><link rel='stylesheet' type='text/css' href='file:///android_asset/css/style.css'/></head><body>" +
					currentFast.getBackground() +
					"</body></html>";
		            
		    webView.loadDataWithBaseURL("x-data://base", page, "text/html", "UTF-8", null);
        }
		
        return rootView;
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
    	super.onCreateOptionsMenu(menu, inflater);
    	
    	FastDB db = new FastDB(getSherlockActivity());
        fastId = getArguments().getInt(Resources.FAST_ID);
    	Fast currentFast = db.getItem(fastId);
		boolean isCustom = currentFast.isCustom();
		db.close();
		
    	if(!isCustom) { 
    		inflater.inflate(R.menu.types_of_fasts_detail_menu, menu);
    	} else {
    		inflater.inflate(R.menu.types_of_fasts_custom_detail_menu, menu);
    	}
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
		switch (item.getItemId()) {
    		case R.id.start_current_fast:
    			intent = new Intent(getSherlockActivity(), CreateFastActivity.class);
    			intent.putExtra(Resources.FAST_NAME, fastName);
				getSherlockActivity().startActivity(intent);
    			return true;
    		case R.id.edit_fast_details:
    			intent = new Intent(getSherlockActivity(), CreateFastTypeActivity.class);
    			intent.putExtra(Resources.FAST_ID, fastId);
    			getSherlockActivity().startActivity(intent);
    			getSherlockActivity().finish();
		    default:
		        return super.onOptionsItemSelected(item);
		}
    }
}
