package com.lynn.mobile.spiritualfasting.fragments;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lynn.mobile.spiritualfasting.TypesOfFastsDetailActivity;
import com.lynn.mobile.spiritualfasting.YourFastDetailActivity;
import com.lynn.mobile.spiritualfasting.YourFastsListActivity;
import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.database.JournalEntryDB;
import com.lynn.mobile.spiritualfasting.database.ScriptureDB;
import com.lynn.mobile.spiritualfasting.database.YourFastDB;
import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.model.JournalEntry;
import com.lynn.mobile.spiritualfasting.model.Scripture;
import com.lynn.mobile.spiritualfasting.util.Resources;
import com.lynn.mobile.spiritualfasting.R;

/**
 * A fragment representing a single Fast detail screen. This fragment is either
 * contained in a {@link YourFastsListActivity} in two-pane mode (on tablets) or a
 * {@link FastDetailActivity} on handsets.
 */
public class YourFastDetailFragment extends SherlockFragment implements OnNavigationListener {

	private String fastName;
	private int day;
	private int yourFastId;

	public YourFastDetailFragment() { }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true); 
	} 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.your_fast_detail_fragment,
				container, false);

		fastName = getArguments().getString(Resources.FAST_NAME);
		day = getArguments().getInt(Resources.DAY);
		yourFastId = getArguments().getInt(Resources.YOUR_FAST_ID);
		String progress = getArguments().getString(Resources.PROGRESS);
		
		YourFastDetailActivity activity = (YourFastDetailActivity) getSherlockActivity();
		FastDB fastDb = new FastDB(activity);
		Fast currentFast = fastDb.getItemByName(fastName);
		
		activity.setTitle(fastName);
		activity.setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		YourFastDB yourFastDb = new YourFastDB(getSherlockActivity());
		Timestamp startDate = yourFastDb.getItem(yourFastId).getStartDate();
		
		TextView subtitle = (TextView) rootView.findViewById(R.id.fast_activity_subtitle);
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
		
		WebView scripture = (WebView) rootView.findViewById(R.id.fast_activity_webview);
		
		if(progress.startsWith("Set")) {
			activity.toggleNavigationButtons(View.INVISIBLE);
			scripture.loadUrl("file:///android_asset/not_started.html");
			subtitle.setText(sdf.format(Calendar.getInstance().getTime()));
		} else if (progress.startsWith("Ended")) {
			activity.toggleNavigationButtons(View.INVISIBLE);
			scripture.loadUrl("file:///android_asset/ended.html");
			subtitle.setText(sdf.format(Calendar.getInstance().getTime()));
		} else {
			progress = "Day " + day + " of " + currentFast.getLength();
			ScriptureDB scriptDB = new ScriptureDB(activity);
			String url = currentFast.getUrl();
			
			if(!url.startsWith("custom_fast")) {
				Scripture currentScripture = scriptDB.getItemByUniqueId(day, currentFast.getId());
				if(currentScripture != null)
					url = currentScripture.getUrl();
				
				scripture.loadUrl("file:///android_asset/scriptures/" 
						+ currentFast.getUrl().substring(0, currentFast.getUrl().indexOf(".")) 
						+ "/" + url);
			} else {
				String page = "<html><head><link rel='stylesheet' type='text/css' href='file:///android_asset/css/style.css'/></head><body>" +
						getJournalEntry() +
						"</body></html>";
			            
			    scripture.loadDataWithBaseURL("x-data://base", page,
			                                        "text/html", "UTF-8",
			                                        null);
			}
			
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			c.add(Calendar.DATE, day - 1);
			Timestamp dateForDay  = new Timestamp(c.getTime().getTime());
			
			subtitle.setText(sdf.format(dateForDay));
		}

		TextView title = (TextView) rootView.findViewById(R.id.fast_activity_title);
		title.setText(progress);
		
		yourFastDb.close();
		fastDb.close();
		return rootView;
	}
	
	
	@Override
	public void onDetach() {
		super.onDetach();
//		ActionBar actionBar = getSherlockActivity().getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
    	super.onCreateOptionsMenu(menu, inflater);
    	
    	if(day == 0) { 
    		inflater.inflate(R.menu.your_fast_detail_menu_empty, menu);
    	} else {
    		inflater.inflate(R.menu.your_fast_detail_menu, menu);
    	}
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.add_journal:
    			openJournalFragment();
    			return true;
    		case R.id.fast_detail:
    			showFastDetail();
    			return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
    }

	public void showFastDetail() {
		Intent intent = new Intent(getSherlockActivity(), TypesOfFastsDetailActivity.class);
		FastDB fastDb = new FastDB(getSherlockActivity());
		Fast fast = fastDb.getItemByName(fastName);
		
		intent.putExtra(Resources.FAST_ID, fast.getId());
		startActivity(intent);
		fastDb.close();
	}

	public void openJournalFragment() {
		YourFastDetailActivity activity = (YourFastDetailActivity) getSherlockActivity();
		JournalEntryDB db = new JournalEntryDB(activity);
		List<JournalEntry> entries = db.getAllItems();
		activity.getIntent().putExtra(Resources.ENTRY_ID, 0);
		
		for(JournalEntry e : entries) {
			if(e.getYourFast().getId() == yourFastId &&
					e.getDay() == activity.getmPager().getCurrentItem() + 1) {
				activity.getIntent().putExtra(Resources.ENTRY_ID, e.getId());
				break;
			}
		}
		
		JournalEntryFragment fragment = new JournalEntryFragment();
		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		activity.getIntent().putExtra(Resources.YOUR_FAST_ID, yourFastId);
		fragment.setArguments(activity.getIntent().getExtras()); 
		transaction.replace(R.id.your_fast_detail_fragment_container, fragment, Resources.JOURNAL_FRAGMENT);
		transaction.addToBackStack(null);
		transaction.commit();
		activity.toggleNavigationButtons(View.INVISIBLE);
	}

	public String getJournalEntry() {
		YourFastDetailActivity activity = (YourFastDetailActivity) getSherlockActivity();
		JournalEntryDB db = new JournalEntryDB(activity);
		List<JournalEntry> entries = db.getAllItems();
		
		for(JournalEntry e : entries) {
			if(e.getYourFast().getId() == yourFastId &&
					e.getDay() == activity.getmPager().getCurrentItem() + 1) {
				return e.getEntry();
			}
		}
		
		return "";
	}
	
	public static YourFastDetailFragment newInstance(Bundle bundle) {
		YourFastDetailFragment f = new YourFastDetailFragment();
        f.setArguments(bundle);

        return f;
	}
}
