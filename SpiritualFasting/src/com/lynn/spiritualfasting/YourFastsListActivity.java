package com.lynn.spiritualfasting;

import com.lynn.spiritualfasting.fragments.*;
import com.lynn.spiritualfasting.util.FragmentNames;

import android.os.Bundle;

public class YourFastsListActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.your_fasts_layout);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    	setupMenu();
		
		if (findViewById(R.id.your_fasts_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
        		
            YourFastsListFragment firstFragment = new YourFastsListFragment();
            firstFragment.setArguments(getIntent().getExtras());
	        getSupportFragmentManager().beginTransaction()
	        	.add(R.id.your_fasts_fragment_container, firstFragment, 
	        			FragmentNames.YOUR_FRAGMENT.getVaule()).commit();
        }
	}
}
