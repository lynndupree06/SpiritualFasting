package com.lynn.mobile.spiritualfasting.fragments;

import java.util.List;

import com.actionbarsherlock.app.SherlockListFragment;
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
import android.widget.ListAdapter;
import android.widget.ListView;

public class TypesofFastsListFragment extends SherlockListFragment {

	public TypesofFastsListFragment() { }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.types_of_fasts_list_fragment,
				container, false);
		
		getSherlockActivity().setTitle(R.string.title_types_of_fasts);
		getSherlockActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		
		FastDB db = new FastDB(getSherlockActivity());
	    List<Fast> fasts = db.getAllItems();
	    db.close();
	    
	    ListAdapter adapter = new FastListAdapter(getSherlockActivity(), R.layout.types_of_fast_list_item, fasts);
		setListAdapter(adapter);
	    
	    return rootView;
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getSherlockActivity(), TypesOfFastsDetailActivity.class);
		intent.putExtra(Resources.FAST_ID, (int)id);
		getSherlockActivity().startActivity(intent);
    }
}