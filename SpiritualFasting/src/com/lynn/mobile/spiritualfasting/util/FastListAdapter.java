package com.lynn.mobile.spiritualfasting.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lynn.mobile.spiritualfasting.R;
import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.extensibility.CustomTextView;
import com.lynn.mobile.spiritualfasting.model.Fast;

import java.util.List;

public class FastListAdapter extends IFastListAdapter<Fast> {

	public FastListAdapter(Context context, int resourceId, List<Fast> objects) {
		super(context, resourceId, objects);
	}
	
	@Override
	public View getView (int position, View convertView, ViewGroup parent)
	{
		convertView = (ViewGroup)Inflater.inflate (ResourceId, null);
		TextView name = (CustomTextView)convertView.findViewById (R.id.type_of_fast_title);
		TextView description = (CustomTextView)convertView.findViewById(R.id.type_of_fast_description);
		name.setText(Items.get(position).getName()); 
		description.setText(Items.get(position).getPurpose());
		return convertView;
	}

	@Override
	public long getItemId (int position)
	{
		FastDB db = new FastDB(getContext());
		Fast fast = db.getItemByName(Items.get(position).getName());
		db.close();
		return fast.getId();
	}
}
