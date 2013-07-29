package com.lynn.spiritualfasting.test.database;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

import android.test.ActivityInstrumentationTestCase2;

import com.lynn.mobile.spiritualfasting.MainActivity;
import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.database.YourFastDB;
import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.model.YourFast;

public class YourFastTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private YourFast yourFast;
	private YourFastDB db;
	private Integer id;
	private Fast fast;
	private Timestamp startDate;
	private Timestamp endDate;

	public YourFastTest() {
		super("com.lynn.spiritualfasting", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		id = 1;
		fast = new Fast(1, "Daniel Fast", 21);
		startDate = new Timestamp(2013, 8, 30, 0, 0, 0, 0);
		endDate = new Timestamp(2013, 9, 25, 0, 0, 0, 0);
		
		yourFast = new YourFast(id, fast, startDate, endDate);
		db = new YourFastDB(getActivity());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAddYourFast() {
		db.addItem(yourFast);
		YourFast actual = db.getItem(yourFast.getId());
		
		assertEquals(yourFast.getId(), actual.getId());
		assertEquals(yourFast.getFast().getId(), actual.getFast().getId());
		assertEquals(yourFast.getStartDate(), actual.getStartDate());
		assertEquals(yourFast.getStartDate(), actual.getEndDate());
		
		db.deleteItem(yourFast);
	}

	public void testGetYourFastById() {
		db.addItem(yourFast);
		Integer actual = db.getItem(id).getId();
		assertEquals(id, actual);
		db.deleteItem(yourFast);
	}
	
	public void testGetAllYourFasts() {
		Timestamp start1 = new Timestamp(2013, 5, 12, 0, 0, 0, 0),
			start2 = new Timestamp(2013, 6, 20, 0, 0, 0, 0),
			end1 = new Timestamp(2013, 5, 30, 0, 0, 0, 0),
			end2 = new Timestamp(2013, 7, 8, 0, 0, 0, 0);
		
		Fast fast1 = new Fast(3, "Fast 1", 5),
			fast2 = new Fast(5, "Fast 2", 3);
		
		YourFast yourFast1 = new YourFast(1, fast1, start1, end1);
		YourFast yourFast2 = new YourFast(2, fast2, start2, end2);
		
		db.addItem(yourFast1);
		db.addItem(yourFast2);
		
		assertEquals(2, db.getAllItems().size());
		db.deleteItem(yourFast1);
		db.deleteItem(yourFast2);
	}
	
	public void testDeleteFast() {
		db.addItem(yourFast);
		assertEquals(1, db.deleteItem(yourFast));
	}
	
	public void testUpdateFast() {
		db.addItem(yourFast);
		YourFast currentFast = db.getItem(id);
		currentFast.setStartDate(new Timestamp(2013, 5, 30, 0, 0, 0, 0));
		currentFast.setEndDate(new Timestamp(2013, 6, 10, 0, 0, 0, 0));
		
		db.updateItem(currentFast);
		YourFast actual = db.getItem(id);
		assertEquals(startDate, actual.getStartDate());
		assertEquals(endDate, actual.getEndDate());
		db.deleteItem(yourFast);
	}
}
