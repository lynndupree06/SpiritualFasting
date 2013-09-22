package com.lynn.mobile.spiritualfasting.navigation;

import com.lynn.mobile.spiritualfasting.AboutActivity;
import com.lynn.mobile.spiritualfasting.MainActivity;
import com.lynn.mobile.spiritualfasting.TermsOfUseActivity;
import com.lynn.mobile.spiritualfasting.TypesOfFastsActivity;
import com.lynn.mobile.spiritualfasting.WhyActivity;
import com.lynn.mobile.spiritualfasting.YourFastsListActivity;
import com.lynn.mobile.spiritualfasting.R;
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
	    		  context.startActivity(intent);
	    		  break;
	    	  case 1:
	    		  intent = new Intent(context, YourFastsListActivity.class);
	    		  context.startActivity(intent);
	    		  break;
	    	  case 2:
	    		  intent = new Intent(context, TypesOfFastsActivity.class);
	    		  context.startActivity(intent);
	    		  break;
	    	  case 3:
	    		  intent = new Intent(context, WhyActivity.class);
	    		  context.startActivity(intent);
	    		  break;
	    	  case 4:
	    		  intent = new Intent(context, AboutActivity.class);
	    		  context.startActivity(intent);
	    		  break;
	    	  case 5:
	    		  intent = new Intent(context, TermsOfUseActivity.class);
	    		  context.startActivity(intent);
	    		  break;
	    	  case 6:
	    		  intent = new Intent(Intent.ACTION_SEND);
                  intent.setType("text/plain");
				  String url = "http://bit.ly/spiritualFasting";
                  intent.putExtra(Intent.EXTRA_TEXT, "Start a spiritual fast today with this app: " + url);
                  intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this app!");
                  context.startActivity(Intent.createChooser(intent, "Share"));
				  break;
  	  	}
  	  	
		
		if(!context.getTitle().equals(context.getString(R.string.app_name)))
			context.finish();
		
  	  	menu.toggle();
    }
}
