package com.lynn.spiritualfasting;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.lynn.spiritualfasting.database.FastDB;
import com.lynn.spiritualfasting.database.JournalEntryDB;
import com.lynn.spiritualfasting.fragments.JournalEntryFragment;
import com.lynn.spiritualfasting.fragments.YourFastDetailFragment;
import com.lynn.spiritualfasting.model.Fast;
import com.lynn.spiritualfasting.model.JournalEntry;
import com.lynn.spiritualfasting.util.Resources;
import com.lynn.spiritualfasting.util.ScreenSlidePagerAdapter;
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
	private Button addJournalEntry;
	private Button fastDetail;
	
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
    		List<SherlockFragment> fragments = getFragments();
    		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), 
    				fragments);
    		getmPager().setAdapter(mPagerAdapter);
    		
    		getmPager().setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
    		    @Override
    		    public void onPageSelected(int position) {
    	    		getmPager().setCurrentItem(position);
    		    }
    		});
    		
    		fastDb.close();
    		wireButtons();
        }
	}
	
	/**
	 * Setup list of fragments to put in the view pager.
	 * @return list of fragments.
	 */
	private List<SherlockFragment> getFragments(){
		  List<SherlockFragment> fList = new ArrayList<SherlockFragment>();
		 
		  for(int i = 1; i <= num_pages; i++) {
			  getIntent().putExtra(Resources.PROGRESS, "Day " + i + " of " + num_pages);
			  getIntent().putExtra(Resources.DAY, i);
			  fList.add(YourFastDetailFragment.newInstance(getIntent().getExtras()));
		  }
		 
		  return fList;
	}
	
	/**
	 * Method to set up listeners for all of the buttons in the 
	 * YourFastDetailActivity.
	 */
	public void wireButtons() {
		
		addJournalEntry = (Button) findViewById(R.id.journal_entry_button);
		addJournalEntry.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JournalEntryDB db = new JournalEntryDB(YourFastDetailActivity.this);
				List<JournalEntry> entries = db.getAllItems();
				getIntent().putExtra(Resources.ENTRY_ID, 0);
				
				for(JournalEntry e : entries) {
					if(e.getYourFast().getId() == yourFastId &&
							e.getDay() == getmPager().getCurrentItem()) {
						getIntent().putExtra(Resources.ENTRY_ID, e.getId());
					}
				}
				
				JournalEntryFragment fragment = new JournalEntryFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				getIntent().putExtra(Resources.YOUR_FAST_ID, yourFastId);
				fragment.setArguments(getIntent().getExtras()); 
				transaction.replace(R.id.your_fast_detail_fragment_container, fragment);
				transaction.addToBackStack(null);
				transaction.commit();
				toggleNavigationButtons(View.INVISIBLE);
			}
		});

        fastDetail = (Button) findViewById(R.id.fast_detail_button);
        fastDetail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(YourFastDetailActivity.this, TypesOfFastsDetailActivity.class);
				FastDB fastDb = new FastDB(YourFastDetailActivity.this);
				Fast fast = fastDb.getItemByName(fastName);
				
				intent.putExtra(Resources.FAST_ID, fast.getId());
				startActivity(intent);
				fastDb.close();
			}
		});
        
        previousBtn = (ImageButton) findViewById(R.id.previous);
		previousBtn.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	dayInProgress = ((dayInProgress - 1) >= 0) ? (dayInProgress - 1) : 0;
		        mPager.setCurrentItem(dayInProgress);
		    }
		});
		nextBtn = (ImageButton) findViewById(R.id.next);
		nextBtn.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	dayInProgress = ((dayInProgress + 1) < num_pages) ? (dayInProgress + 1) : num_pages - 1;
		    	getmPager().setCurrentItem(dayInProgress);
		    }
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case android.R.id.home:
		    	if(!getSupportFragmentManager().popBackStackImmediate())
		    		finish();
		    	
		    	toggleNavigationButtons(View.VISIBLE);
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

	/**
	 * Add/Remove buttons in the view.
	 * @param visibility The type of visibility.
	 */
	public void toggleNavigationButtons(int visibility) {
		previousBtn.setVisibility(visibility);
		nextBtn.setVisibility(visibility);
		addJournalEntry.setVisibility(visibility);
		fastDetail.setVisibility(visibility);
	}
}
