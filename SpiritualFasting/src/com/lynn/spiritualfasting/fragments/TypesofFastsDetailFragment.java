package com.lynn.spiritualfasting.fragments;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.database.FastDB;
import com.lynn.spiritualfasting.util.Resources;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class TypesofFastsDetailFragment extends SherlockFragment implements ActionBar.TabListener {

	private int fastId;
	private String fastName;
	private ActionBar actionBar;

	public TypesofFastsDetailFragment() {
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        
        actionBar = getSherlockActivity().getSupportActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        actionBar.addTab(actionBar.newTab()
                .setText("Details")
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab()
                .setText("Restrictions")
                .setTabListener(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.types_of_fasts_detail_fragment, container, false);
        
        FastDB db = new FastDB(getSherlockActivity());
        fastId = getArguments().getInt(Resources.FAST_ID);
        
        fastName = db.getItem(fastId).getName();
        String url = db.getItem(fastId).getUrl();
        
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
    			CreateFastFragment fragment = new CreateFastFragment();
    			FragmentManager manager = getActivity().getSupportFragmentManager();
				FragmentTransaction transaction = manager.beginTransaction();
				getActivity().getIntent().putExtra(Resources.FAST_NAME, fastName);
				fragment.setArguments(getActivity().getIntent().getExtras());
				transaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
				transaction.addToBackStack(null);
				transaction.commit();
    			return true; 
		    default:
		        return super.onOptionsItemSelected(item);
		}
    }
    
    @Override
    public void onDetach() {
    	super.onDetach();
    	actionBar.removeAllTabs();
    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
