package com.lynn.spiritualfasting.test.database;

import android.test.ActivityInstrumentationTestCase2;

import com.lynn.mobile.spiritualfasting.MainActivity;
import com.lynn.mobile.spiritualfasting.database.FastDB;
import com.lynn.mobile.spiritualfasting.model.Fast;

public class FastTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Fast fast;
	private FastDB db;
	private int id;
	private String name;
	private int length;

	public FastTest() {
		super("com.lynn.spiritualfasting", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		id = 1;
		name = "Daniel Fast";
		length = 21;
		
		fast = new Fast(id, name, length);
		db = new FastDB(getActivity());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAddFast() {
		db.addItem(fast);
		Fast actual = db.getItem(fast.getId());
		
		assertEquals(fast.getId(), actual.getId());
		assertEquals(fast.getName(), actual.getName());
		assertEquals(fast.getLength(), actual.getLength());
		
		db.deleteItem(fast);
	}

	public void testGetFastById() {
		db.addItem(fast);
		int actual = db.getItem(id).getId();
		assertEquals(id, actual);
		db.deleteItem(fast);
	}
	
	public void testGetFastByName() {
		db.addItem(fast);
		String actual = db.getItemByName(name).getName();
		assertEquals(name, actual);
		db.deleteItem(fast);
	}
	
	public void testGetAllFasts() {
		Fast fast1 = new Fast(1, "Daniel Fast", 21);
		Fast fast2 = new Fast(2, "Partial Fast", 3);
		
		db.addItem(fast1);
		db.addItem(fast2);
		
		assertEquals(2, db.getAllItems().size());
		db.deleteItem(fast1);
		db.deleteItem(fast2);
	}
	
	public void testDeleteFast() {
		db.addItem(fast);
		assertTrue(db.deleteItem(fast) >= 1);
	}
	
	public void testUpdateFast() {
		db.addItem(fast);
		Fast currentFast = db.getItem(id);
		currentFast.setName("Partial Fast");
		currentFast.setLength(3);
		
		db.updateItem(currentFast);
		assertEquals("Partial Fast", db.getItem(id).getName());
		assertEquals(3, db.getItem(id).getLength());
		db.deleteItem(fast);
	}
}
