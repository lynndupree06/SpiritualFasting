package com.lynn.mobile.spiritualfasting;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.view.KeyEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.lynn.mobile.spiritualfasting.navigation.MenuListAdapter;
import com.lynn.mobile.spiritualfasting.navigation.OnMenuItemClickListener;
import com.lynn.mobile.spiritualfasting.util.FastDatePickerDialog;
import com.lynn.mobile.spiritualfasting.R;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

@SuppressLint("NewApi")
public class BaseActivity extends SlidingFragmentActivity {
	private static final int DATE_DIALOG_ID = 0;
	protected SlidingMenu menu;
	private int year;
	private int month;
	private int day;
	
	public void setupMenu() {
		setBehindContentView(R.layout.navigation_menu_layout);
		menu = getSlidingMenu();
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeEnabled(true);
        menu.setFadeDegree(0.35f);
        menu.setMenu(R.layout.navigation_list_layout);
		
		final ListView listView = (ListView) findViewById(R.id.navigation_list);
		String[] values = getResources().getStringArray(R.array.navigation);
		
		ListAdapter adapter = new MenuListAdapter<String>(this, R.layout.navigation_menu_item, values);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnMenuItemClickListener(this, menu));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case android.R.id.home:
		        menu.toggle();
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ( keyCode == KeyEvent.KEYCODE_MENU ) {
	        menu.toggle();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@SuppressLint("NewApi")
	@Override
	protected Dialog onCreateDialog(int id) {
		final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        
		switch (id) {
			case DATE_DIALOG_ID:
				FastDatePickerDialog picker = new FastDatePickerDialog(this, CreateFastActivity.datePickerListener, 
                        year, month,day);
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					picker.getDatePicker().setMinDate(calendar.getTime().getTime());
				}
				
				
			    return picker;
		}
		return null;
	}
}
