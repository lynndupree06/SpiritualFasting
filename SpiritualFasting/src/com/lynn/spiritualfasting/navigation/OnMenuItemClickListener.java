package com.lynn.spiritualfasting.navigation;

import com.lynn.spiritualfasting.AboutActivity;
import com.lynn.spiritualfasting.MainActivity;
import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.TypesOfFastsActivity;
import com.lynn.spiritualfasting.WhyActivity;
import com.lynn.spiritualfasting.YourFastsActivity;
import com.slidingmenu.lib.SlidingMenu;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class OnMenuItemClickListener implements OnItemClickListener {

	private FragmentActivity context;
	private SlidingMenu menu;
	
	public OnMenuItemClickListener(FragmentActivity context, SlidingMenu menu) {
		this.context = context;
		this.menu = menu;
	}

	@Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
		Intent intent = null;
		
		switch(position) {
	    	  case 0: 
	    		  intent = new Intent(context, MainActivity.class);
	    		  break;
	    	  case 1:
	    		  intent = new Intent(context, YourFastsActivity.class);
	    		  break;
	    	  case 2:
	    		  intent = new Intent(context, TypesOfFastsActivity.class);
	    		  break;
	    	  case 3:
	    		  intent = new Intent(context, WhyActivity.class);
	    		  break;
	    	  case 4:
	    		  intent = new Intent(context, AboutActivity.class);
	    		  break;
  	  	}
  	  	
		context.startActivity(intent);
		
		if(!context.getTitle().equals(context.getString(R.string.app_name)))
			context.finish();
		
  	  	menu.toggle();
    }

}
