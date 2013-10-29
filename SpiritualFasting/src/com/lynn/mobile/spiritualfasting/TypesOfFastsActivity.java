package com.lynn.mobile.spiritualfasting;

import com.lynn.mobile.spiritualfasting.R;
import com.lynn.mobile.spiritualfasting.fragments.TypesofFastsListFragment;
import com.lynn.mobile.spiritualfasting.util.FragmentNames;

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
