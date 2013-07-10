package com.lynn.spiritualfasting;

import com.lynn.spiritualfasting.fragments.*;
import com.lynn.spiritualfasting.util.FragmentNames;
import com.lynn.spiritualfasting.util.Resources;

import android.os.Bundle;

public class YourFastsActivity extends BaseActivity {
	
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
            
            if(getIntent().getExtras() != null 
            		&& getIntent().getExtras().getString(Resources.FAST_NAME) != null
            		&& getIntent().getExtras().getInt(Resources.YOUR_FAST_ID) != 0) {
            	
            	YourFastDetailFragment firstFragment = new YourFastDetailFragment();
                firstFragment.setArguments(getIntent().getExtras());
            	getSupportFragmentManager().beginTransaction()
                	.add(R.id.your_fasts_fragment_container, firstFragment, 
                		FragmentNames.YOUR_FRAGMENT.getVaule()).commit();
            } else {
            	YourFastsListFragment firstFragment = new YourFastsListFragment();
                firstFragment.setArguments(getIntent().getExtras());
	            getSupportFragmentManager().beginTransaction()
	                    .add(R.id.your_fasts_fragment_container, firstFragment, 
	                    		FragmentNames.YOUR_FRAGMENT.getVaule()).commit();
            }
        }
	}
}
