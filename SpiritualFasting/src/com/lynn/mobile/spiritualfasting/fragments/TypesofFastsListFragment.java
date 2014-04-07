package com.lynn.mobile.spiritualfasting.fragments;

import java.util.List;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lynn.mobile.spiritualfasting.CreateFastTypeActivity;
import com.lynn.mobile.spiritualfasting.TypesOfFastsDetailActivity;
import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.util.FastListAdapter;
import com.lynn.mobile.spiritualfasting.util.Resources;
import com.lynn.mobile.spiritualfasting.R;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TypesofFastsListFragment extends SherlockListFragment {

	private FastListAdapter adapter;

	public TypesofFastsListFragment() { }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.types_of_fasts_list_fragment,
				container, false);
		
		getSherlockActivity().setTitle(R.string.title_types_of_fasts);
		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		
		updateAdapter();
	    
	    return rootView;
	}

	public void updateAdapter() {
		int types = getArguments().getInt(Resources.FAST_TYPE);
		FastDB db = new FastDB(getSherlockActivity());
	    List<Fast> fasts = db.getCustomOrOriginalFasts(types);
	    db.close();
	    
	    adapter = new FastListAdapter(getSherlockActivity(), R.layout.types_of_fast_list_item, fasts);
		setListAdapter(adapter);
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getSherlockActivity(), TypesOfFastsDetailActivity.class);
		intent.putExtra(Resources.FAST_ID, (int)id);
		getSherlockActivity().startActivity(intent);
    }
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.types_of_fasts_list_menu, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.add_fast:
    			Intent intent = new Intent(getSherlockActivity(), CreateFastTypeActivity.class);
    			getSherlockActivity().startActivity(intent);
				updateAdapter();
    			if(adapter != null) {
    				adapter.notifyDataSetChanged();
    			}
    			return true; 
		    default:
		        return super.onOptionsItemSelected(item);
		}
    }
    
    @Override
	public void onResume() {
		super.onResume();

		updateAdapter();
		if(adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}
}
