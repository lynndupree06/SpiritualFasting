package com.lynn.mobile.spiritualfasting;

import com.lynn.mobile.spiritualfasting.fragments.*;
import com.lynn.mobile.spiritualfasting.util.FragmentNames;
import com.lynn.mobile.spiritualfasting.R;

import android.os.Bundle;

public class YourFastsListActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.your_fasts_layout);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    	setupMenu();
//		setSlidingActionBarEnabled(false);
		
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
