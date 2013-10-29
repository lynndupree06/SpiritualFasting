package com.lynn.mobile.spiritualfasting.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import com.lynn.mobile.spiritualfasting.CreateFastActivity;
import com.lynn.mobile.spiritualfasting.CreateFastTypeActivity;
import com.lynn.mobile.spiritualfasting.database.FastDB;
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
        
        fastName = db.getItem(fastId).getName();
        String url = db.getItem(fastId).getUrl();
        db.close();
        
        getSherlockActivity().setTitle(fastName);
		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        
        WebView webView = (WebView) rootView.findViewById(R.id.types_of_fast_detail_webview);
		webView.loadUrl("file:///android_asset/types_of_fasts/" + url);
		
        return rootView;
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.types_of_fasts_detail_menu, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.start_current_fast:
    			Intent intent = new Intent(getSherlockActivity(), CreateFastActivity.class);
    			intent.putExtra(Resources.FAST_NAME, fastName);
				getSherlockActivity().startActivity(intent);
    			return true; 
		    default:
		        return super.onOptionsItemSelected(item);
		}
    }
}
