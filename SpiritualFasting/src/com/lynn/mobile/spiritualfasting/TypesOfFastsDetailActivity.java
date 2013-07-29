package com.lynn.mobile.spiritualfasting;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.lynn.mobile.spiritualfasting.fragments.TypesofFastsDetailFragment;
import com.lynn.mobile.spiritualfasting.fragments.TypesofFastsDetailRestrictionFragment;
import com.lynn.mobile.spiritualfasting.R;

public class TypesOfFastsDetailActivity extends BaseActivity implements ActionBar.TabListener {

	private static final String BACKGROUND = "Background";
	private static final String DETAILS = "Details";
	private ActionBar actionBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.types_of_fasts_layout);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		setupMenu();
//		setSlidingActionBarEnabled(false);
		
		actionBar = getSupportActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        actionBar.addTab(actionBar.newTab()
                .setText(BACKGROUND)
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab()
                .setText(DETAILS)
                .setTabListener(this));
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		SherlockFragment fragment = null;
		
		if(tab.getText().equals(BACKGROUND)) {
			fragment = new TypesofFastsDetailFragment();
		} else if (tab.getText().equals(DETAILS)) {
			fragment = new TypesofFastsDetailRestrictionFragment();
		}
		
		if(fragment != null) {
			fragment.setArguments(getIntent().getExtras());
			ft.replace(R.id.types_of_fragment_container, fragment);
		}
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
