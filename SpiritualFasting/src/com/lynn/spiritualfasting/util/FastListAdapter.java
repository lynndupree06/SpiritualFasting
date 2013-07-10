package com.lynn.spiritualfasting.util;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.database.FastDB;
import com.lynn.spiritualfasting.model.Fast;

public class FastListAdapter extends IFastListAdapter<Fast> {

	public FastListAdapter(Context context, int resourceId, List<Fast> objects) {
		super(context, resourceId, objects);
	}
	
	@Override
	public View getView (int position, View convertView, ViewGroup parent)
	{
		convertView = (ViewGroup)Inflater.inflate (ResourceId, null);
		TextView name = (TextView)convertView.findViewById (R.id.type_of_fast_title);
		name.setText(Items.get(position).getName()); 
		return convertView;
	}

	@Override
	public long getItemId (int position)
	{
		FastDB db = new FastDB(getContext());
		Fast fast = db.getItemByName(Items.get(position).getName());
		return fast.getId();
	}
}
