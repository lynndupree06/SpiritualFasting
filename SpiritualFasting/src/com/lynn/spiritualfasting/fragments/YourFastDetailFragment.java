package com.lynn.spiritualfasting.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.YourFastDetailActivity;
import com.lynn.spiritualfasting.YourFastsListActivity;
import com.lynn.spiritualfasting.database.FastDB;
import com.lynn.spiritualfasting.database.JournalEntryDB;
import com.lynn.spiritualfasting.model.Fast;
import com.lynn.spiritualfasting.model.JournalEntry;
import com.lynn.spiritualfasting.util.Resources;
import com.lynn.spiritualfasting.util.ScreenSlidePagerAdapter;

/**
 * A fragment representing a single Fast detail screen. This fragment is either
 * contained in a {@link YourFastsListActivity} in two-pane mode (on tablets) or a
 * {@link FastDetailActivity} on handsets.
 */
public class YourFastDetailFragment extends SherlockFragment implements OnNavigationListener {

	private int yourFastId;
	private String fastName;
	private int day;
	private int num_days;

	public YourFastDetailFragment() { }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true); 
		ActionBar actionBar = getSherlockActivity().getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	} 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.your_fast_detail_fragment,
				container, false);

		yourFastId = getArguments().getInt(Resources.YOUR_FAST_ID);
		fastName = getArguments().getString(Resources.FAST_NAME);
		day = getArguments().getInt(Resources.DAY);
		
		FastDB fastDb = new FastDB(getSherlockActivity());
		num_days = fastDb.getItemByName(fastName).getLength();
		String progress = "Day " + day + " of " + num_days;
		
		getSherlockActivity().setTitle(fastName);
		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		
		TextView title = (TextView) rootView.findViewById(R.id.fast_activity_title);
		title.setText("Day " + day);
		
		TextView subtitle = (TextView) rootView.findViewById(R.id.fast_activity_subtitle);
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
		subtitle.setText(progress); //sdf.format(Calendar.getInstance().getTime())
		
		String url = fastDb.getItemByName(fastName).getUrl();
		WebView scripture = (WebView) rootView.findViewById(R.id.fast_activity_webview);
		scripture.loadUrl("file:///android_asset/types_of_fasts/" + url);

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
    	super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.your_fast_detail_menu, menu);
    }

	public YourFastDetailFragment newInstance(Bundle bundle) {
		YourFastDetailFragment f = new YourFastDetailFragment();
        f.setArguments(bundle);

        return f;
	}
}
