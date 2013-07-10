package com.lynn.spiritualfasting.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.model.YourFast;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class YourFastListAdapter extends IFastListAdapter<YourFast> {
	private SparseBooleanArray mSelectedItemsIds;
	
	public YourFastListAdapter(Context context, int resourceId, List<YourFast> objects) {
		super(context, resourceId, objects);
		mSelectedItemsIds = new SparseBooleanArray();
	}

	@Override
	public View getView (int position, View convertView, ViewGroup parent)
	{
		convertView = (ViewGroup)Inflater.inflate (ResourceId, null);
		YourFast yourFast = Items.get(position);
		
		TextView name = (TextView)convertView.findViewById (R.id.type_of_fast_title);
		name.setText(yourFast.getFast().getName()); 
	
		TextView progress = (TextView)convertView.findViewById (R.id.progress_subtitle);
		Date date = Calendar.getInstance().getTime();
        Timestamp dateToday = new Timestamp(date.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
		Timestamp startDate = yourFast.getStartDate();
		Timestamp endDate = yourFast.getEndDate();
		
		if(startDate.before(dateToday)) {
			if(endDate.before(dateToday)) {
				progress.setText("Ended on: " + sdf.format(endDate.getTime()));
			} else {
				long diffTime = Calendar.getInstance().getTime().getTime() - startDate.getTime();
				long diffDays = diffTime / (1000 * 60 * 60 * 24);
				progress.setText("Day " + (diffDays + 1) + " of " + yourFast.getFast().getLength());
			}
		} else {
			progress.setText("Set to start on: " + sdf.format(startDate.getTime()));
		}

		convertView.setBackgroundColor(mSelectedItemsIds.get(position)? 0x9934B5E4: Color.TRANSPARENT);         
		
		return convertView;
	}
	
	@Override
	public long getItemId (int position)
	{
		return Items.get(position).getId();
	}
	
	public void toggleSelection(int position)
	{
	    selectView(position, !mSelectedItemsIds.get(position));
	}
	
	public void selectView(int position, boolean value)
	{
	    if(value)
	        mSelectedItemsIds.put(position, value);
	    else
	        mSelectedItemsIds.delete(position);
	             
	    notifyDataSetChanged();
	}
	
	public int getSelectedCount() {
	    return mSelectedItemsIds.size();// mSelectedCount;
	}
	
	public List<YourFast> getSelectedItems() {
		List<YourFast> selectedItems = new ArrayList<YourFast>();
		for(int i = 0; i < Items.size(); i++) {
			if(mSelectedItemsIds.get(i)) {
				selectedItems.add(Items.get(i));
			}
		}
		
		return selectedItems;
	}
}
