package com.lynn.spiritualfasting.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.database.JournalEntryDB;
import com.lynn.spiritualfasting.util.Resources;

public class JournalEntryFragment extends SherlockFragment {
	private EditText journalEntry;

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

		getSherlockActivity().setTitle(R.string.title_journal_entry);
		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		
		int yourFastId = getArguments().getInt(Resources.YOUR_FAST_ID);
		journalEntry = (EditText) rootView.findViewById(R.id.journal_entry_text);
		
		return rootView;
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.journal_entry_menu, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.update_entry:
    			JournalEntryDB db = new JournalEntryDB(getSherlockActivity());
    			
//    			String entry = journalEntry.getText().toString();
//    			Timestamp date = new Timestamp(Calendar.getInstance().getTime().getTime());
//    			JournalEntry newEntry = new JournalEntry(id, entry, fast, date);
    			
    			getActivity().getSupportFragmentManager().popBackStack();
    			return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
    }
}
