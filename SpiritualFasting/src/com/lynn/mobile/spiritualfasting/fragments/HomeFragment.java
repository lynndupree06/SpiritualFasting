package com.lynn.mobile.spiritualfasting.fragments;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;
import com.lynn.mobile.spiritualfasting.CreateFastActivity;
import com.lynn.mobile.spiritualfasting.YourFastDetailActivity;
import com.lynn.mobile.spiritualfasting.database.YourFastDB;
import com.lynn.mobile.spiritualfasting.model.YourFast;
import com.lynn.mobile.spiritualfasting.util.Resources;
import com.lynn.mobile.spiritualfasting.util.YourFastListAdapter;
import com.lynn.mobile.spiritualfasting.R;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends SherlockFragment {

	private YourFastListAdapter adapter;
	private ListView listView;

	public HomeFragment() { }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.home_layout,
				container, false);
		
		getSherlockActivity().setTitle(R.string.app_name);
		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		
		WebView webView = (WebView) rootView.findViewById(R.id.welcome_webView);
		webView.loadUrl("file:///android_asset/welcome.html");
		
		Button startButton = (Button) rootView.findViewById(R.id.start_fast_button);
		startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getSherlockActivity(), CreateFastActivity.class);
				getSherlockActivity().startActivity(intent);
			}
		});
		
		listView = (ListView) rootView.findViewById(R.id.your_fasts_list);
		setListAdapter();
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				FragmentActivity activity = getSherlockActivity();
				YourFastDB db = new YourFastDB(activity);
				Intent intent = new Intent(activity, YourFastDetailActivity.class);
		
				YourFast item = db.getItem((int)id);
				intent.putExtra(Resources.YOUR_FAST_ID, (int)id);
				intent.putExtra(Resources.FAST_NAME, item.getFast().getName());
				
				TextView progress = (TextView)v.findViewById (R.id.progress_bar);
				intent.putExtra(Resources.PROGRESS, progress.getText());
				
				long diffTime = Calendar.getInstance().getTime().getTime() - item.getStartDate().getTime();
				long diffDays = diffTime / (1000 * 60 * 60 * 24);
				intent.putExtra(Resources.DAY, (int)(diffDays + 1));

				db.close();
				activity.startActivity(intent);
			}
		});
	    
		return rootView;
	}

	public void setListAdapter() {
		YourFastDB db = new YourFastDB(getSherlockActivity());
	    List<YourFast> fasts = db.getAllItems();
	    List<YourFast> newList = new ArrayList<YourFast>();
	    
	    // Get fasts that are current in progress
	    Timestamp today = new Timestamp(Calendar.getInstance().getTime().getTime());
	    for(YourFast f : fasts) {
	    	if(f.getStartDate().before(today) && f.getEndDate().after(today))
	    		newList.add(f);
	    }
	    
	    adapter = new YourFastListAdapter(getSherlockActivity(), R.layout.your_fast_list_item, newList);
	    listView.setAdapter(adapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(adapter != null) {
			setListAdapter();
			adapter.notifyDataSetChanged();
		}
	}
}
