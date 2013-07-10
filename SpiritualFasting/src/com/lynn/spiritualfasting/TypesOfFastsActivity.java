package com.lynn.spiritualfasting;

import com.lynn.spiritualfasting.fragments.*;
import com.lynn.spiritualfasting.util.FragmentNames;

import android.os.Bundle;

public class TypesOfFastsActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.types_of_fasts_layout);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		setupMenu();
		
		if (findViewById(R.id.types_of_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            TypesofFastsListFragment firstFragment = new TypesofFastsListFragment();
            firstFragment.setArguments(getIntent().getExtras());
            
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.types_of_fragment_container, firstFragment, 
                    		FragmentNames.TYPES_FRAGMENT.getVaule()).commit();
        }
	}
}
