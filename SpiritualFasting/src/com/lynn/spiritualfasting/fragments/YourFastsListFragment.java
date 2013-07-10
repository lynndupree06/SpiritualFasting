package com.lynn.spiritualfasting.fragments;

import java.util.List;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.database.YourFastDB;
import com.lynn.spiritualfasting.model.YourFast;
import com.lynn.spiritualfasting.util.Resources;
import com.lynn.spiritualfasting.util.YourFastListAdapter;

public class YourFastsListFragment extends SherlockListFragment implements ActionMode.Callback {
	protected ActionMode mActionMode;
	private YourFastListAdapter adapter;
	private List<YourFast> fasts;
	
	public YourFastsListFragment() { }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.your_fast_list_fragment,
				container, false);
		
		getSherlockActivity().setTitle(R.string.title_your_fasts_list);
		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); 
		
		YourFastDB db = new YourFastDB(getSherlockActivity());
	    fasts = db.getAllItems();
	    
	    if(fasts.size() != 0) {
		    adapter = new YourFastListAdapter(getSherlockActivity(), R.layout.your_fast_list_item, fasts);
		    setListAdapter(adapter);
	    } else {
	    	TextView emptyList = (TextView) rootView.findViewById(R.id.no_fasts);
	    	emptyList.setText(R.string.no_current_fasts_text);
	    }
	    
	    db.close(); 
	    return rootView;
	} 
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
	        @Override
	        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
	        	mActionMode = getSherlockActivity().startActionMode(YourFastsListFragment.this);
	        	onListItemCheck(position);
	            return true;
	        }
	    });
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		if(mActionMode == null) {
			FragmentActivity activity = getSherlockActivity();
			YourFastDB db = new YourFastDB(activity);
			YourFastDetailFragment detailFragment = new YourFastDetailFragment();
	
			YourFast item = db.getItem((int)id);
			activity.getIntent().putExtra(Resources.YOUR_FAST_ID, (int)id);
			activity.getIntent().putExtra(Resources.FAST_NAME, item.getFast().getName());
			
			TextView progress = (TextView)v.findViewById (R.id.progress_subtitle);
			activity.getIntent().putExtra(Resources.PROGRESS, progress.getText());
			
			detailFragment.setArguments(activity.getIntent().getExtras());
			
			FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
			transaction.replace(((ViewGroup)getView().getParent()).getId(), detailFragment);
			transaction.addToBackStack(null);
			transaction.commit();
			db.close();
		} else {
	        onListItemCheck(position); 
		}
    }
	
	private void onListItemCheck(int position) {
		YourFastListAdapter adapter = (YourFastListAdapter) getListAdapter();
		adapter.toggleSelection(position);
		boolean hasCheckedItems = adapter.getSelectedCount() > 0;        
		 
		if (hasCheckedItems && mActionMode == null)
		    // there are some selected items, start the actionMode
		    mActionMode = getSherlockActivity().startActionMode(this);
		else if (!hasCheckedItems && mActionMode != null)
		    // there no selected items, finish the actionMode
		    mActionMode.finish();
		         
		 
		if(mActionMode != null)
		    mActionMode.setTitle(String.valueOf(adapter.getSelectedCount()) + " selected");
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.your_fasts_list_menu, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	FragmentTransaction transaction = getSherlockActivity().getSupportFragmentManager().beginTransaction();
    	
    	switch (item.getItemId()) {
    		case R.id.add_fast:
    			CreateFastFragment createFragment = new CreateFastFragment();
				transaction.replace(R.id.fragment_container, createFragment);
				transaction.addToBackStack(null);
				transaction.commit();
    			return true;
    		case R.id.edit_fast:
    			mActionMode = getSherlockActivity().startActionMode(this);
    			return true;
		    default: 
		        return super.onOptionsItemSelected(item);
		}
    }

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.edit_your_fasts_list_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.delete_fast:
				List<YourFast> deletedFasts = adapter.getSelectedItems();
				YourFastDB db = new YourFastDB(getSherlockActivity());
				for(YourFast f : deletedFasts) {
					db.deleteItem(f);
					fasts.remove(f);
					adapter.remove(f);
				}

				adapter.notifyDataSetChanged();
				mActionMode.finish();
				db.close();
				return true;
		    default:
		        return true;
		}
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		mActionMode = null;
		if(adapter != null && adapter.getSelectedCount() > 0) {
			for(int i = 0; i < adapter.getCount(); i++) {
				adapter.selectView(i, false);
			}
		}
	}
}
