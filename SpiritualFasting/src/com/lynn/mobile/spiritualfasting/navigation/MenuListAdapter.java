package com.lynn.mobile.spiritualfasting.navigation;

import android.content.Context;
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

	public MenuListAdapter (Context context, int resourceId, T[] objects)
	{
		super(context, resourceId);
		ResourceId = resourceId;
		Inflater = LayoutInflater.from(context);
		Ctx = context;
		MenuItems = objects;
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

		ImageView menu_image = (ImageView)convertView.findViewById (R.id.menu_item_image);
		String uri = "";

		int imageResource;
		Drawable image = null;
		
		switch (position) {
			case 0: 
				uri = "drawable/ic_menu_home"; 
				imageResource = Ctx.getResources().getIdentifier(uri, null, "android");
				image = Ctx.getResources().getDrawable(imageResource);
				break;
			case 1: 
				uri = "drawable/ic_menu_star";
				imageResource = Ctx.getResources().getIdentifier(uri, null, "android");
				image = Ctx.getResources().getDrawable(imageResource);
				break;
			case 2: 
				uri = "drawable/ic_menu_goto";
				imageResource = Ctx.getResources().getIdentifier(uri, null, "android");
				image = Ctx.getResources().getDrawable(imageResource);
				break;
			case 3:
				uri = "drawable/ic_menu_help";
				imageResource = Ctx.getResources().getIdentifier(uri, null, "android");
				image = Ctx.getResources().getDrawable(imageResource);
				break;
			case 4:
				uri = "drawable/ic_menu_info_details";
				imageResource = Ctx.getResources().getIdentifier(uri, null, "android");
				image = Ctx.getResources().getDrawable(imageResource);
				break;
		}

		menu_image.setImageDrawable(image);

		return convertView;
	}
	
	@Override
	public int getCount() {
		return MenuItems.length;
	}
}
