package com.lynn.spiritualfasting;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.KeyEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.lynn.spiritualfasting.fragments.CreateFastFragment;
import com.lynn.spiritualfasting.navigation.MenuListAdapter;
import com.lynn.spiritualfasting.navigation.OnMenuItemClickListener;
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
		Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        
		switch (id) {
			case DATE_DIALOG_ID:
				DatePickerDialog picker = new DatePickerDialog(this, CreateFastFragment.datePickerListener, 
                        year, month,day);
				picker.getDatePicker().setMinDate(c.getTime().getTime());
			    return picker;
		}
		return null;
	}
}
