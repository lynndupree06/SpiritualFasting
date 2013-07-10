package com.lynn.spiritualfasting.fragments;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lynn.spiritualfasting.R;
import com.lynn.spiritualfasting.YourFastsActivity;
import com.lynn.spiritualfasting.database.FastDB;
import com.lynn.spiritualfasting.database.YourFastDB;
import com.lynn.spiritualfasting.model.Fast;
import com.lynn.spiritualfasting.model.YourFast;
import com.lynn.spiritualfasting.util.Resources;

public class CreateFastFragment extends SherlockFragment {
	
	private static TextView startDate;
    private static int day;
    private static int month;
    private static int year;
	private Spinner fastType;

    private static final int DATE_PICKER_ID = 0;
    private YourFastDB db;
	private ArrayList<String> list;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.create_fast_form_layout,
				container, false);

		getSherlockActivity().setTitle(R.string.title_activity_start_fast);
		getSherlockActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		list = new ArrayList<String>();
		String[] fastTypesList = getResources().getStringArray(R.array.types_of_fasts);
		
		for(String name : fastTypesList) 
			list.add(name);
		
		String fastName = "";
		fastType = (Spinner) rootView.findViewById(R.id.type_of_fast_spinner);
		
		if(getArguments() != null) {
			fastName = getArguments().getString(Resources.FAST_NAME);
			if(!fastName.equals(""))
				fastType.setSelection(list.indexOf(fastName));
		}	
		
		startDate = (TextView) rootView.findViewById(R.id.start_fast_date);
        startDate.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				getActivity().showDialog(DATE_PICKER_ID);
			}
		});
		
		
		return rootView;
	}
    
    public static DatePickerDialog.OnDateSetListener datePickerListener 
    	= new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			// set selected date into textview
			startDate.setText(new StringBuilder().append(month + 1)
			   .append("/").append(day).append("/").append(year)
			   .append(" "));
		}
    };
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.create_fasts_menu, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.start_fast:
    			createNewFast();
    			return true;
    		case R.id.cancel_fast:
    			getSherlockActivity().getSupportFragmentManager().popBackStack();
    			return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
    }

	public void createNewFast() {
		String name = fastType.getSelectedItem().toString();
		String date = startDate.getText().toString();

		SherlockFragmentActivity activity = getSherlockActivity();
		if(!name.equals("Select a fast...") && !date.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
			
			try {	
				Timestamp start = new Timestamp(sdf.parse(date).getTime()); 
				
				FastDB fastDb = new FastDB(activity);
				Fast fast = fastDb.getItemByName(name);
				
				Calendar c = Calendar.getInstance();
				c.setTime(start);
				c.add(Calendar.DATE, fast.getLength());
				Timestamp end = new Timestamp(c.getTime().getTime());
				
				YourFast newFast = new YourFast(fast, start, end);
				db = new YourFastDB(activity);
				db.addItem(newFast);

				Intent intent = new Intent(activity, YourFastsActivity.class);
				Timestamp today = new Timestamp(Calendar.getInstance().getTime().getTime());
				if(newFast.getStartDate().equals(today)) {
					intent.putExtra(Resources.PROGRESS, 
							"Day 1 of " + newFast.getFast().getLength());
				} else {
					intent.putExtra(Resources.PROGRESS,
							"Set to start on: " + sdf.format(newFast.getStartDate()));
				}
				
				intent.putExtra(Resources.FAST_NAME, fast.getName());
				intent.putExtra(Resources.YOUR_FAST_ID, fast.getId());
				activity.startActivity(intent);
				
				if(!activity.getTitle().equals(activity.getString(R.string.app_name)))
					activity.finish();
				
			}  catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		    alert.setTitle("Some of the required fields are missing.");
		    
		    String message = "";
		    if(name.equals("Select a fast..."))
		    	message += "Please select the type of fast.";
		    if(date.equals("")) 
		    	message += "\nPlease select the start date.";
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
