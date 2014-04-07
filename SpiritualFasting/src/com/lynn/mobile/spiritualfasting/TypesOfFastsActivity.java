package com.lynn.mobile.spiritualfasting;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.lynn.mobile.spiritualfasting.R;
import com.lynn.mobile.spiritualfasting.fragments.TypesofFastsListFragment;
import com.lynn.mobile.spiritualfasting.util.FragmentNames;
import com.lynn.mobile.spiritualfasting.util.Resources;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class TypesOfFastsActivity extends BaseActivity {

	String[] actions = new String[] { "Standard", "Custom" };
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.types_of_fasts_layout);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		setupMenu();
		
		/** Create an array adapter to populate dropdownlist */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), 
        		R.layout.spinner_selector_text_layout, actions);
 
        /** Enabling dropdown list navigation for the action bar */
        getSherlock().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
 
        /** Defining Navigation listener */
        ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {
 
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            	switch(itemPosition) {
            		case 0: 
            			getIntent().putExtra(Resources.FAST_TYPE, itemPosition);
            			startFragment();
            			return true;
            		case 1: 
            			getIntent().putExtra(Resources.FAST_TYPE, itemPosition);
            			startFragment();
            			return true;
            	}
                
            	return false;
            }
        };
 
        /** Setting dropdown items and item navigation listener for the actionbar */
        getSherlock().getActionBar().setListNavigationCallbacks(adapter, navigationListener);
    
		
		if (findViewById(R.id.types_of_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            
            getIntent().putExtra(Resources.FAST_TYPE, 0);
            startFragment();
        }
	}

	public void startFragment() {
		TypesofFastsListFragment firstFragment = new TypesofFastsListFragment();
		firstFragment.setArguments(getIntent().getExtras());
		
		getSupportFragmentManager().beginTransaction()
		        .replace(R.id.types_of_fragment_container, firstFragment, 
		        		FragmentNames.TYPES_FRAGMENT.getVaule()).commit();
	}
}
