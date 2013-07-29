package com.lynn.mobile.spiritualfasting.fragments;

import java.util.List;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.lynn.mobile.spiritualfasting.database.JournalEntryDB;
import com.lynn.mobile.spiritualfasting.model.JournalEntry;
import com.lynn.mobile.spiritualfasting.util.Resources;
import com.lynn.mobile.spiritualfasting.R;

public class JournalEntryListFragment extends SherlockListFragment {

	public JournalEntryListFragment() { }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.journal_entry_list_fragment,
				container, false);
		
		getSherlockActivity().setTitle(R.string.title_journal_entry_list);
		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		int yourFastId = getArguments().getInt(Resources.YOUR_FAST_ID);
		
		JournalEntryDB db = new JournalEntryDB(getSherlockActivity());
	    List<JournalEntry> entries = db.getAllItems();
	    
	    String[] list = new String[entries.size()];
	    int idx = 0;
	    for(JournalEntry j : entries) {
	    	if(j.getYourFast().getId() == yourFastId) {
	    		list[idx] = j.getEntry().substring(0, 20);
	    		idx++;
	    	}
	    }
	    
	    if(list.length != 0) {
	    	setListAdapter(new ArrayAdapter<String>(
	                getSherlockActivity(),
	                android.R.layout.simple_list_item_1,
	                android.R.id.text1,
	                list));
	    } else {
	    	TextView emptyList = (TextView) rootView.findViewById(R.id.no_journal_entries);
	    	emptyList.setText(R.string.no_current_journal_entries_text);
	    }
	    
	    return rootView;
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		JournalEntryFragment journalFragment = new JournalEntryFragment();
		
		FragmentTransaction transaction = getSherlockActivity().getSupportFragmentManager().beginTransaction();
		transaction.replace(((ViewGroup)getView().getParent()).getId(), journalFragment);
		transaction.addToBackStack(null);
		transaction.commit();
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
