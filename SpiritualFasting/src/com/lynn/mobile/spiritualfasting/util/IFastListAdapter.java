package com.lynn.mobile.spiritualfasting.util;

import java.util.List;

import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class IFastListAdapter<T> extends ArrayAdapter<T> {
	
	protected List<T> Items;
	protected int ResourceId;
	protected LayoutInflater Inflater;

	public IFastListAdapter(Context context, int resourceId, List<T> objects) {
		super(context, resourceId);
		ResourceId = resourceId;
		Inflater = LayoutInflater.from(context);
		Items = objects;
	}
	
	@Override
	public View getView (int position, View convertView, ViewGroup parent)
	{
		convertView = (ViewGroup)Inflater.inflate (ResourceId, null);
		TextView name = (TextView)convertView.findViewById (R.id.type_of_fast_title);
		name.setText(((Fast) Items.get(position)).getName()); 
		return convertView;
	}

	@Override
	public T getItem (int position)
	{
		return Items.get(position);
	}
	
	@Override
	public long getItemId (int position)
	{
		if(position == 0)
			return 0;
		else
			return (position - 1);
	}
	
	@Override
	public int getCount() {
		return Items.size();
	}
}
