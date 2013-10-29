package com.lynn.mobile.spiritualfasting;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.model.Fast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.EditText;

public class CreateFastTypeActivity extends BaseActivity {
	
	private EditText name;
	private EditText duration;
	private EditText purpose;
	private EditText description;
	private EditText restrictions;

	@Override
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView (R.layout.create_fast_type_layout);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		setupMenu();
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		name = (EditText) findViewById(R.id.add_fast_type_name);
		duration = (EditText) findViewById(R.id.add_fast_type_duration);
		purpose = (EditText) findViewById(R.id.add_fast_type_purpose);
		description = (EditText) findViewById(R.id.add_fast_type_description);
		restrictions = (EditText) findViewById(R.id.add_fast_type_restrictions);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.create_fast_type_menu, menu);
		return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.create_fast_type:
    			addNewFastType();
    			return true;
    		case R.id.cancel_fast_type:
    			finish();
    			return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
    }

	private void addNewFastType() {
		String fastName = name.getText().toString();
		String fastPurpose = purpose.getText().toString();
		int fastDuration = !duration.getText().toString().equals("")
				? Integer.valueOf(duration.getText().toString()) : 0;
		
		if(!fastName.equals("") && !fastPurpose.equals("") && fastDuration > 0) {
			Fast newFastType = new Fast(fastName, fastPurpose, fastDuration, 
					"custom_fast.html", true);
		
			FastDB db = new FastDB(this);
			db.addItem(newFastType);
			db.close();
			finish();
		} else {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Some of the required fields are missing.");
	    
			String message = "";
			if(fastName.equals(""))
				message += "Please enter the name of the fast.\n";
			if(fastPurpose.equals("")) 
				message += "Please enter the purpose of the fast.\n";
			if(fastDuration == 0)
				message += "Please enter duration for the fast.\n";
			
			alert.setMessage(message); 

			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			});
	    
			AlertDialog alertDialog = alert.create();
			alertDialog.show();
		}
	}
}
