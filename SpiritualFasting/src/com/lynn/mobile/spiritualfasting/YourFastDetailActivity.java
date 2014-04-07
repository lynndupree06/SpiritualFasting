package com.lynn.mobile.spiritualfasting;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.database.YourFastDB;
import com.lynn.mobile.spiritualfasting.fragments.ExitViewDialogFragment;
import com.lynn.mobile.spiritualfasting.fragments.YourFastDetailFragment;
import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.model.YourFast;
import com.lynn.mobile.spiritualfasting.util.Resources;
import com.lynn.mobile.spiritualfasting.util.ScreenSlidePagerAdapter;
import com.lynn.mobile.spiritualfasting.R;
import com.slidingmenu.lib.SlidingMenu;

public class YourFastDetailActivity extends BaseActivity {
	private String fastName;
	protected int num_pages;
	protected int dayInProgress;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	private ImageButton previousBtn;
	private ImageButton nextBtn;
	private int yourFastId;
	private List<SherlockFragment> fragments;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.your_fast_detail_layout);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		fastName = getIntent().getExtras().getString(Resources.FAST_NAME);
		yourFastId = getIntent().getExtras().getInt(Resources.YOUR_FAST_ID);
		
		setTitle(fastName);
		setBehindContentView(R.layout.navigation_menu_layout);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		
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
    		updateAdapter();
    		getmPager().setCurrentItem(dayInProgress);
    		
    		getmPager().setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
    		    @Override
    		    public void onPageSelected(int position) {
    	    		getmPager().setCurrentItem(position);
    		    }
    		});
    		
    		fastDb.close();
    		wireButtons();
//    		getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        }
	}

	public void updateAdapter() {
		YourFastDB yourFastDb = new YourFastDB(this);
		YourFast yourFast = yourFastDb.getItem(yourFastId);
		
		fragments = getFragments(yourFast.getStartDate(), yourFast.getEndDate());
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), 
				fragments);
		getmPager().setAdapter(mPagerAdapter);
		yourFastDb.close();
	}
	
	private OnBackStackChangedListener getListener() {
		OnBackStackChangedListener result = new OnBackStackChangedListener()
        {
            public void onBackStackChanged() 
            {   
//            	finish();
//                int day = getIntent().getExtras().getInt(Resources.DAY);
//                YourFastDetailFragment currentFragment = (YourFastDetailFragment)fragments.get(day - 1);
//                currentFragment.onFragmentResume();
//                getmPager().invalidate();
                //updateAdapter();
//                getmPager().getAdapter().notifyDataSetChanged();                 
//                getmPager().invalidate();
//                getmPager().refreshDrawableState();
            }
        };

        return result;
	}

	/**
	 * Setup list of fragments to put in the view pager.
	 * @param startDate Date that the fast starts.
	 * @return List of fragments.
	 */
	private List<SherlockFragment> getFragments(Timestamp startDate, Timestamp endDate){
		  List<SherlockFragment> fList = new ArrayList<SherlockFragment>();
		  Timestamp today = new Timestamp(Calendar.getInstance().getTime().getTime());
		  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  String todaysDate = simpleDateFormat.format(new Date(today.getTime()));
		  String fastStartDate = simpleDateFormat.format(new Date(startDate.getTime()));

		  if((startDate.before(today) || fastStartDate.equals(todaysDate)) && !endDate.before(today)) {
			  for(int i = 1; i <= num_pages; i++) {
				  getIntent().putExtra(Resources.PROGRESS, "Day " + i + " of " + num_pages);
				  getIntent().putExtra(Resources.DAY, i);
				  fList.add(YourFastDetailFragment.newInstance(getIntent().getExtras()));
			  }
				
			  if(fastStartDate.equals(todaysDate)) {
				  dayInProgress = 0; 
			  } else {
				  long diffTime = today.getTime() - startDate.getTime();
				  long diffDays = diffTime / (1000 * 60 * 60 * 24);
				  dayInProgress = (int)diffDays;
			  }
		  } else {
			  fList.add(YourFastDetailFragment.newInstance(getIntent().getExtras()));
		  }
		  
		  return fList;
	}
	
	/**
	 * Method to set up listeners for all of the buttons in the 
	 * YourFastDetailActivity.
	 */
	public void wireButtons() {
        previousBtn = (ImageButton) findViewById(R.id.previous);
		previousBtn.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	dayInProgress = mPager.getCurrentItem();
		    	dayInProgress = ((dayInProgress - 1) >= 0) ? (dayInProgress - 1) : 0;
		        mPager.setCurrentItem(dayInProgress);
		    }
		});
		nextBtn = (ImageButton) findViewById(R.id.next);
		nextBtn.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	dayInProgress = mPager.getCurrentItem();
		    	dayInProgress = ((dayInProgress + 1) < num_pages) ? (dayInProgress + 1) : num_pages - 1;
		    	getmPager().setCurrentItem(dayInProgress);
		    }
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case android.R.id.home:
		    	if(getSupportFragmentManager().findFragmentByTag(Resources.JOURNAL_FRAGMENT) != null) {
		    		ExitViewDialogFragment dialog = new ExitViewDialogFragment();
		        	dialog.show(getSupportFragmentManager(), null);
		    	} else {
			    	if(!getSupportFragmentManager().popBackStackImmediate())
			    		finish();
			    	
			    	toggleNavigationButtons(View.VISIBLE);
		    	}
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed() {
		if(getSupportFragmentManager().findFragmentByTag(Resources.JOURNAL_FRAGMENT) != null) {
    		ExitViewDialogFragment dialog = new ExitViewDialogFragment();
        	dialog.show(getSupportFragmentManager(), null);
    	} else {
	    	if(getSupportFragmentManager().popBackStackImmediate()) {
	    		toggleNavigationButtons(View.VISIBLE);
	    	} else {
		    	finish();
	    	}
    	}
	}
	
	public ViewPager getmPager() {
		return mPager;
	}

	public void setmPager(ViewPager mPager) {
		this.mPager = mPager;
	}

	/**
	 * Add/Remove buttons in the view.
	 * @param visibility The type of visibility.
	 */
	public void toggleNavigationButtons(int visibility) {
		previousBtn.setVisibility(visibility);
		nextBtn.setVisibility(visibility);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ( keyCode == KeyEvent.KEYCODE_MENU ) {
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
