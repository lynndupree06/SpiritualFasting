package com.lynn.mobile.spiritualfasting;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.database.YourFastDB;
import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.model.YourFast;
import com.lynn.mobile.spiritualfasting.util.Resources;
import com.lynn.mobile.spiritualfasting.R;

public class CreateFastActivity extends BaseActivity {
	
	private static TextView startDate;
	private Spinner fastType;
	private LinearLayout fastDurationLayout;
    private static int day;
    private static int month;
    private static int year;

    private static final int DATE_PICKER_ID = 0;
    private static final int DURATION_PICKER_ID = 1;
    private YourFastDB db;
	private ArrayList<String> list;
	private LinearLayout fastNameLayout;
	private EditText fastName;
	private EditText fastDuration;

    @Override
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView (R.layout.create_fast_form_layout);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		setupMenu();
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		list = new ArrayList<String>();
		FastDB db = new FastDB(this);
		List<Fast> fasts = db.getAllItems();
		
		list.add("Select a fast...");
		for(Fast f : fasts) 
			list.add(f.getName());
		
		list.add("Custom Fast");
		
		fastNameLayout = (LinearLayout) findViewById(R.id.fast_name_layout);
		fastName = (EditText) findViewById(R.id.start_fast_name);
		fastDurationLayout = (LinearLayout) findViewById(R.id.fast_duration_layout);
		fastDuration = (EditText) findViewById(R.id.start_fast_duration);
		
		fastDuration.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				showDialog(DURATION_PICKER_ID);
			}
		});
		
		String fastName = "";
		fastType = (Spinner) findViewById(R.id.type_of_fast_spinner);
		fastType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
					int position, long id) {
				if(parentView.getSelectedItem().toString().startsWith("Custom Fast")) {
					fastNameLayout.setVisibility(View.VISIBLE);
					fastDurationLayout.setVisibility(View.VISIBLE);
				} else {
					fastNameLayout.setVisibility(View.INVISIBLE);
					fastDurationLayout.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_dropdown_item, list);
		fastType.setAdapter(spinnerArrayAdapter);
		
		if(getIntent().getExtras() != null) {
			fastName = getIntent().getExtras().getString(Resources.FAST_NAME);
			if(!fastName.equals(""))
				fastType.setSelection(list.indexOf(fastName));
		}	
		
		startDate = (TextView) findViewById(R.id.start_fast_date);
        startDate.setOnClickListener(new OnClickListener() {			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(DATE_PICKER_ID);
			}
		});
	}
    
    public static DatePickerDialog.OnDateSetListener datePickerListener 
    	= new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			String monthPrefix = (month < 9) ? "0" : "";
			String dayPrefix = (day < 9) ? "0" : "";
			
			// set selected date into textview
			startDate.setText(new StringBuilder().append(monthPrefix).append(month + 1)
			   .append("/").append(dayPrefix).append(day).append("/").append(year)
			   .append(" "));
		}
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.create_fasts_menu, menu);
		return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.start_fast:
    			createNewFast();
    			return true;
    		case R.id.cancel_fast:
    			finish();
    			return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
    }

	public void createNewFast() {
		String name = fastType.getSelectedItem().toString();
		String date = startDate.getText().toString().trim();
		Timestamp today = new Timestamp(Calendar.getInstance().getTime().getTime());
		
		Timestamp start = null;
		String todaysDate = null;
		try {

			if(!date.equals("")) {
				start = new Timestamp(new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime());
				todaysDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date (today.getTime())).trim();
			} else {
				throw new Exception();
			}
			
			if(!name.equals("Select a fast...") && !date.equals("") 
					&& (start.after(today) || date.equals(todaysDate))) {
				
				FastDB fastDb = new FastDB(this);
				if(name.equals("Custom Fast")) {
					if(!fastName.equals("") && !fastDuration.equals("")) {
						name = fastName.getText().toString();
						int duration = Integer.valueOf(fastDuration.getText().toString());
						fastDb.addItem(new Fast(name, "", duration, "custom_fast.html", true));
					} else {
						throw new Exception();
					}
				}
				
				Fast fast = fastDb.getItemByName(name);
					
				Calendar c = Calendar.getInstance();
				c.setTime(start);
				c.add(Calendar.DATE, fast.getLength());
				Timestamp end = new Timestamp(c.getTime().getTime());
					
				YourFast newFast = new YourFast(fast, start, end);
				db = new YourFastDB(this);
				long newFastId = db.addItem(newFast);
				db.close();
	
				Intent intent = new Intent(this, YourFastDetailActivity.class);
				String fastStartDate  = new SimpleDateFormat("yyyy-MM-dd").format(new Date(newFast.getStartDate().getTime()));
					
				if(fastStartDate.equals(todaysDate)) {
					intent.putExtra(Resources.PROGRESS, "Day 1 of " + newFast.getFast().getLength());
					intent.putExtra(Resources.DAY, 1);
				} else {
					long diffTime = newFast.getStartDate().getTime() - Calendar.getInstance().getTime().getTime();
					long diffDays = diffTime / (1000 * 60 * 60 * 24);
					String dayText = (diffDays + 1 > 1) ? " days" : " day";
					intent.putExtra(Resources.PROGRESS, "Set to start in " + (diffDays + 1) + dayText);
					intent.putExtra(Resources.DAY, 0);
				}
					
				intent.putExtra(Resources.FAST_NAME, fast.getName());
				intent.putExtra(Resources.YOUR_FAST_ID, (int)newFastId);
				this.startActivity(intent);
					
				if(!this.getTitle().equals(this.getString(R.string.app_name)))
					this.finish();
			} else {
				throw new Exception();
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Some of the required fields are missing.");
	    
			String message = "";
			if(name.startsWith("Select a fast"))
				message += "Please select the type of fast.\n";
			if(date.equals("") || (start != null && start.before(today))) 
				message += "Please select a valid start date.\n";
			if(name.startsWith("Custom Fast") && fastName.getText().toString().equals(""))
				message += "Please enter a valid name for the fast.\n";
			if(name.startsWith("Custom Fast") && fastDuration.getText().toString().equals(""))
				message += "Please enter a valid duration for the fast.\n";
			
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
