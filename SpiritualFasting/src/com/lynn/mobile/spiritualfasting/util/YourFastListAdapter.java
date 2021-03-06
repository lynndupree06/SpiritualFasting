package com.lynn.mobile.spiritualfasting.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.lynn.mobile.spiritualfasting.R;
import com.lynn.mobile.spiritualfasting.extensibility.CustomTextView;
import com.lynn.mobile.spiritualfasting.model.YourFast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class YourFastListAdapter extends IFastListAdapter<YourFast> {
	private SparseBooleanArray mSelectedItemsIds;
	private Typeface font;
	private Context context;
	
	public YourFastListAdapter(Context context, int resourceId, List<YourFast> objects) {
		super(context, resourceId, objects);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		font = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
	}

	@Override
	public View getView (int position, View convertView, ViewGroup parent)
	{
		convertView = Inflater.inflate (ResourceId, null);
		YourFast yourFast = Items.get(position);
		
		CustomTextView name = (CustomTextView)convertView.findViewById (R.id.type_of_fast_title);
		name.setTypeface(font);
		name.setText(context.getResources().getString(R.string.icon_calendar) + " " + yourFast.getFast().getName());

        CustomTextView progress = (CustomTextView)convertView.findViewById (R.id.progress_subtitle);
		ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progress_bar);
		Date date = Calendar.getInstance().getTime();
        Timestamp dateToday = new Timestamp(date.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
		Timestamp startDate = yourFast.getStartDate();
		Timestamp endDate = yourFast.getEndDate();
		
		if(startDate.before(dateToday)) {
			if(endDate.before(dateToday)) {
				progress.setText("Ended on: " + sdf.format(endDate.getTime()));
				progressBar.setProgress(100);
			} else {
				long diffTime = Calendar.getInstance().getTime().getTime() - startDate.getTime();
				long diffDays = diffTime / (1000 * 60 * 60 * 24);
				progress.setText("Day " + (diffDays + 1) + " of " + yourFast.getFast().getLength());
				progressBar.setMax(yourFast.getFast().getLength());
				progressBar.setProgress((int)(diffDays + 1));
			}
		} else {
			long diffTime = startDate.getTime() - Calendar.getInstance().getTime().getTime();
			long diffDays = diffTime / (1000 * 60 * 60 * 24);
			String dayText = (diffDays + 1 > 1) ? " days" : " day";
			progress.setText("Set to start in " + (diffDays + 1) + dayText);
			progressBar.setProgress(0);
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
