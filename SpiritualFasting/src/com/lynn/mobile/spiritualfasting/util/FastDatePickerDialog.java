package com.lynn.mobile.spiritualfasting.util;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class FastDatePickerDialog extends DatePickerDialog {

	public FastDatePickerDialog(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		int minYear = calendar.get(Calendar.YEAR);
	    int minMonth = calendar.get(Calendar.MONTH);
	    int minDay = calendar.get(Calendar.DAY_OF_MONTH);
	    
        Calendar newDate = calendar;
        newDate.set(year, month, day);

        if (calendar.after(newDate)) {
            view.init(minYear, minMonth, minDay - 1, this);
        }
    }
}
