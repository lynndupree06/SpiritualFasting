package com.lynn.mobile.spiritualfasting;

import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.fragments.*;
import com.lynn.mobile.spiritualfasting.util.FragmentNames;
import com.lynn.mobile.spiritualfasting.R;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.main_layout);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		setupMenu();
		setupDatabase();
		
		if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            HomeFragment firstFragment = new HomeFragment();
            
            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());
            
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment, 
                    		FragmentNames.HOME_FRAGMENT.getVaule()).commit();
        }
	}

	private void setupDatabase() {
		FastDB fastDB = new FastDB(getApplicationContext());
		SQLiteDatabase db = fastDB.getReadableDatabase();
		fastDB.init(db);
		fastDB.close();
	}
}
