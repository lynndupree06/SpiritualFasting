package com.lynn.spiritualfasting.fragments;

import java.util.List;

import com.actionbarsherlock.app.SherlockListFragment;
import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.database.FastDB;
import com.lynn.spiritualfasting.model.Fast;
import com.lynn.spiritualfasting.util.FastListAdapter;
import com.lynn.spiritualfasting.util.Resources;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
	    
	    ListAdapter adapter = new FastListAdapter(getSherlockActivity(), R.layout.types_of_fast_list_item, fasts);
		setListAdapter(adapter);
	    
	    return rootView;
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		TypesofFastsDetailFragment detailFragment = new TypesofFastsDetailFragment();
		getSherlockActivity().getIntent().putExtra(Resources.FAST_ID, (int)id);
		detailFragment.setArguments(getSherlockActivity().getIntent().getExtras());
		
		FragmentTransaction transaction = getSherlockActivity().getSupportFragmentManager().beginTransaction();
		transaction.replace(((ViewGroup)getView().getParent()).getId(), detailFragment);
		transaction.addToBackStack(null);
		transaction.commit();
    }
}
