package com.lynn.spiritualfasting;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.view.MenuItem;
import com.lynn.spiritualfasting.database.FastDB;
import com.lynn.spiritualfasting.model.Fast;
import com.lynn.spiritualfasting.util.Resources;
import com.lynn.spiritualfasting.util.ScreenSlidePagerAdapter;

public class YourFastDetailActivity extends BaseActivity {
	private String fastName;
	protected int num_pages;
	protected int dayInProgress;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	private Button previousBtn;
	private Button nextBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.your_fast_detail_layout);
		
		fastName = getIntent().getExtras().getString(Resources.FAST_NAME);
		dayInProgress = getIntent().getExtras().getInt(Resources.DAY);
		
		setTitle(fastName);
		setBehindContentView(R.layout.navigation_menu_layout);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		
		if (findViewById(R.id.your_fast_detail_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            
            FastDB fastDb = new FastDB(this);
    		Fast fast = fastDb.getItemByName(fastName);
    		num_pages = fast.getLength();
    		
    		setmPager((ViewPager) findViewById(R.id.pager));
    		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), 
    				num_pages, getIntent().getExtras());
    		getmPager().setAdapter(mPagerAdapter);
    		getmPager().setCurrentItem(dayInProgress - 1);
    		getmPager().setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
    		    @Override
    		    public void onPageSelected(int position) {
    	    		getmPager().setCurrentItem(position);
    		    }
    		});
    		
    		fastDb.close();
    		
    		previousBtn = (Button) findViewById(R.id.previous);
    		previousBtn.setOnClickListener(new OnClickListener() {
    		    public void onClick(View v) {
    		    	dayInProgress = ((dayInProgress - 1) > 0) ? (dayInProgress - 1) : 1;
    		        mPager.setCurrentItem(dayInProgress - 1);
    		    }
    		});
    		nextBtn = (Button) findViewById(R.id.next);
    		nextBtn.setOnClickListener(new OnClickListener() {
    		    public void onClick(View v) {
    		    	dayInProgress = ((dayInProgress + 1) <= num_pages) ? (dayInProgress + 1) : num_pages;
    		    	getmPager().setCurrentItem(dayInProgress - 1);
    		    }
    		});
        }
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case android.R.id.home:
		    	if(!getSupportFragmentManager().popBackStackImmediate())
		    		finish();
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed() {
    	if(getSupportFragmentManager().popBackStackImmediate()) {
    		toggleNavigationButtons(View.VISIBLE);
    	} else {
	    	finish();
    	}
	}

	public ViewPager getmPager() {
		return mPager;
	}

	public void setmPager(ViewPager mPager) {
		this.mPager = mPager;
	}

	public void toggleNavigationButtons(int visibility) {
		previousBtn.setVisibility(visibility);
		nextBtn.setVisibility(visibility);
	}
}
