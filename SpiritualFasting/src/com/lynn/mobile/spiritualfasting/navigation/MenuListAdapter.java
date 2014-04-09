package com.lynn.mobile.spiritualfasting.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lynn.mobile.spiritualfasting.R;

public class MenuListAdapter<T> extends ArrayAdapter<T>
{
	private int ResourceId;
	private LayoutInflater Inflater;
	private Context Ctx;

	public T[] MenuItems;
	public Context AdapterContext;
	private Typeface font;

	public MenuListAdapter (Context context, int resourceId, T[] objects)
	{
		super(context, resourceId);
		ResourceId = resourceId;
		Inflater = LayoutInflater.from(context);
		Ctx = context;
		MenuItems = objects;
		font = Typeface.createFromAsset(Ctx.getAssets(), "fonts/fontawesome-webfont.ttf");
	}
	
	@Override
	public T getItem (int position)
	{
		return MenuItems[position];
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
	public View getView (int position, View convertView, ViewGroup parent)
	{		
		convertView = (ViewGroup)Inflater.inflate (ResourceId, null);

		TextView txtName = (TextView)convertView.findViewById (R.id.menu_item_text);
		txtName.setText((String)MenuItems [position]);
		
		TextView txtImage = (TextView)convertView.findViewById(R.id.menu_item_image);
		txtImage.setTypeface(font);
		
		switch (position) {
			case 0: 
				txtImage.setText(resources().getString(R.string.icon_home));
				break;
			case 1: 
				txtImage.setText(resources().getString(R.string.icon_your_fasts));
				break;
			case 2: 
				txtImage.setText(resources().getString(R.string.icon_types));
				break;
			case 3:
				txtImage.setText(resources().getString(R.string.icon_why));
				break;
			case 4:
				txtImage.setText(resources().getString(R.string.icon_info));
				break;
			case 5:
				txtImage.setText(resources().getString(R.string.icon_terms));
				break;
			case 6:
				txtImage.setText(resources().getString(R.string.icon_share));
				break;	
		}

		return convertView;
	}

	private Resources resources() {
		return Ctx.getResources();
	}
	
	@Override
	public int getCount() {
		return MenuItems.length;
	}
}
