package com.lynn.spiritualfasting.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.YourFastDetailActivity;
import com.lynn.spiritualfasting.YourFastsListActivity;
import com.lynn.spiritualfasting.database.FastDB;
import com.lynn.spiritualfasting.database.ScriptureDB;
import com.lynn.spiritualfasting.model.Fast;
import com.lynn.spiritualfasting.util.Resources;

/**
 * A fragment representing a single Fast detail screen. This fragment is either
 * contained in a {@link YourFastsListActivity} in two-pane mode (on tablets) or a
 * {@link FastDetailActivity} on handsets.
 */
public class YourFastDetailFragment extends SherlockFragment implements OnNavigationListener {

	private String fastName;
	private int day;

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
		String progress = getArguments().getString(Resources.PROGRESS);
		
		YourFastDetailActivity activity = (YourFastDetailActivity) getSherlockActivity();
		FastDB fastDb = new FastDB(activity);
		Fast currentFast = fastDb.getItemByName(fastName);
		
		activity.setTitle(fastName);
		activity.setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		TextView subtitle = (TextView) rootView.findViewById(R.id.fast_activity_subtitle);
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
		subtitle.setText(sdf.format(Calendar.getInstance().getTime()));
		
		WebView scripture = (WebView) rootView.findViewById(R.id.fast_activity_webview);
		
		if(progress.startsWith("Set")) {
			activity.toggleNavigationButtons(View.INVISIBLE);
			scripture.loadUrl("file:///android_asset/not_started.html");
		} else if (progress.startsWith("Ended")) {
			activity.toggleNavigationButtons(View.INVISIBLE);
			scripture.loadUrl("file:///android_asset/ended.html");
		} else {
			progress = "Day " + day + " of " + currentFast.getLength();
			ScriptureDB scriptDB = new ScriptureDB(activity);
			String url = scriptDB.getItemByUniqueId(day, currentFast.getId()).getUrl();
			scripture.loadUrl("file:///android_asset/scriptures/" 
					+ currentFast.getUrl().substring(0, currentFast.getUrl().indexOf(".")) 
					+ "/" + url);
		}

		TextView title = (TextView) rootView.findViewById(R.id.fast_activity_title);
		title.setText(progress);
		
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

	public static YourFastDetailFragment newInstance(Bundle bundle) {
		YourFastDetailFragment f = new YourFastDetailFragment();
        f.setArguments(bundle);

        return f;
	}
}
