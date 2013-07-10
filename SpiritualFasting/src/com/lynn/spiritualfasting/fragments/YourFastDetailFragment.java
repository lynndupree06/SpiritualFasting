package com.lynn.spiritualfasting.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.database.FastDB;
import com.lynn.spiritualfasting.model.Fast;
import com.lynn.spiritualfasting.util.Resources;

/**
 * A fragment representing a single Fast detail screen. This fragment is either
 * contained in a {@link YourFastsListActivity} in two-pane mode (on tablets) or a
 * {@link FastDetailActivity} on handsets.
 */
public class YourFastDetailFragment extends SherlockFragment implements OnNavigationListener {

	private int yourFastId;

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
		final String fastName = getArguments().getString(Resources.FAST_NAME);
		String progress = getArguments().getString("progress");
        getSherlockActivity().setTitle(fastName);
		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		
		TextView title = (TextView) rootView.findViewById(R.id.fast_activity_title);
		title.setText(progress);
		
		TextView todaysDate = (TextView) rootView.findViewById(R.id.fast_activity_subtitle);
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
		todaysDate.setText(sdf.format(Calendar.getInstance().getTime()));
		
		Button addJournalEntry = (Button) rootView.findViewById(R.id.journal_entry_button);
        addJournalEntry.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JournalEntryFragment fragment = new JournalEntryFragment();
				FragmentTransaction transaction = getSherlockActivity().getSupportFragmentManager().beginTransaction();
				getSherlockActivity().getIntent().putExtra(Resources.YOUR_FAST_ID, yourFastId);
				fragment.setArguments(getSherlockActivity().getIntent().getExtras());
				transaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
        
        Button fastDetail = (Button) rootView.findViewById(R.id.fast_detail_button);
        fastDetail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TypesofFastsDetailFragment fragment = new TypesofFastsDetailFragment();
				FragmentTransaction transaction = getSherlockActivity().getSupportFragmentManager().beginTransaction();
				
				FastDB fastDb = new FastDB(getSherlockActivity());
				Fast fast = fastDb.getItemByName(fastName);
				
				getSherlockActivity().getIntent().putExtra(Resources.FAST_ID, fast.getId());
				fragment.setArguments(getSherlockActivity().getIntent().getExtras());
				transaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});

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
}
