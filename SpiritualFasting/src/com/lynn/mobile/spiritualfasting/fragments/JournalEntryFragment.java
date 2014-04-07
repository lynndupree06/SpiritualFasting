package com.lynn.mobile.spiritualfasting.fragments;

import java.sql.Timestamp;
import java.util.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lynn.mobile.spiritualfasting.YourFastDetailActivity;
import com.lynn.mobile.spiritualfasting.database.JournalEntryDB;
import com.lynn.mobile.spiritualfasting.database.YourFastDB;
import com.lynn.mobile.spiritualfasting.model.JournalEntry;
import com.lynn.mobile.spiritualfasting.util.Resources;
import com.lynn.mobile.spiritualfasting.R;

public class JournalEntryFragment extends SherlockFragment {
	private static EditText journalEntry;
	private static int yourFastId;
	private static int entryId;
	private static int day;

	public JournalEntryFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.journal_entry_fragment,
				container, false);
		
		yourFastId = getArguments().getInt(Resources.YOUR_FAST_ID);
		entryId = getArguments().getInt(Resources.ENTRY_ID);
		journalEntry = (EditText) rootView.findViewById(R.id.journal_entry_text);
		day = getArguments().getInt(Resources.DAY);
		
		JournalEntryDB db = new JournalEntryDB(getSherlockActivity());
		JournalEntry entry = db.getEntryByFastAndDay(yourFastId, day);
		
		if(entry != null)
			journalEntry.setText(entry.getEntry());
		
		return rootView;
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
    	super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.journal_entry_menu, menu);
    }
    
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.update_entry:
    			saveJournalEntry(getSherlockActivity());
    			return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
    }

	public static void saveJournalEntry(SherlockFragmentActivity sherlockActivity) {
		JournalEntryDB db = new JournalEntryDB(sherlockActivity);
		YourFastDB yourFastDb = new YourFastDB(sherlockActivity);
		
		String entry = journalEntry.getText().toString();
		Timestamp date = new Timestamp(Calendar.getInstance().getTime().getTime());
		YourFastDetailActivity activity = (YourFastDetailActivity) sherlockActivity;
		day = activity.getmPager().getCurrentItem() + 1;
		JournalEntry newEntry = new JournalEntry(entryId, entry, yourFastDb.getItem(yourFastId), day, date);
		
		if(entryId != 0) {
			db.updateItem(newEntry);
		} else {
			db.addItem(newEntry);
		}
		db.close();
		yourFastDb.close();

		((YourFastDetailActivity)sherlockActivity).toggleNavigationButtons(View.VISIBLE);
		sherlockActivity.getSupportFragmentManager().popBackStack();
	}
}
