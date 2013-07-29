package com.lynn.spiritualfasting.test.database;

import java.util.GregorianCalendar;

import android.test.ActivityInstrumentationTestCase2;

import com.lynn.mobile.spiritualfasting.MainActivity;
import com.lynn.mobile.spiritualfasting.database.JournalEntryDB;
import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.model.JournalEntry;

public class JournalEntryTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private JournalEntry entry;
	private JournalEntryDB db;
	private int id;
	private String entryText;
	private GregorianCalendar day;
	private Fast fast;

	public JournalEntryTest() {
		super("com.lynn.spiritualentrying", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		id = 1;
		entryText = "This is my journal entry.";
		day = new GregorianCalendar(2013, 4, 3, 8, 0);
		fast = new Fast(1, "Daniel Fast", 21);
		
		entry = new JournalEntry(id, entryText, fast, day);
		db = new JournalEntryDB(getActivity());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAddJournalEntry() {
		db.addItem(entry);
		JournalEntry actual = db.getItem(entry.getId());
		
		assertEquals(entry.getId(), actual.getId());
		assertEquals(entry.getEntry(), actual.getEntry());
		assertEquals(entry.getFast(), actual.getFast());
		assertEquals(entry.getLastUpdated(), actual.getLastUpdated());
		
		db.deleteItem(entry);
	}

	public void testGetJournalEntryById() {
		db.addItem(entry);
		int actual = db.getItem(id).getId();
		assertEquals(id, actual);
		db.deleteItem(entry);
	}
	
	public void testGetAllJournalEntrys() {
		GregorianCalendar date1 = new GregorianCalendar(2013, 4, 3, 8, 0);
		GregorianCalendar date2 = new GregorianCalendar(2013, 4, 10, 9, 0);
		
		JournalEntry entry1 = new JournalEntry(1, "entry 1", fast, date1);
		JournalEntry entry2 = new JournalEntry(2, "entry 2", fast, date2);
		
		db.addItem(entry1);
		db.addItem(entry2);
		
		assertEquals(2, db.getAllItems().size());
		db.deleteItem(entry1);
		db.deleteItem(entry2);
	}
	
	public void testDeleteJournalEntry() {
		db.addItem(entry);
		assertTrue(db.deleteItem(entry) >= 1);
	}
	
	public void testUpdateJournalEntry() {
		db.addItem(entry);
		JournalEntry currentJournalEntry = db.getItem(id);
		currentJournalEntry.setEntry("Partial JournalEntry");
		
		assertTrue(db.updateItem(currentJournalEntry) >= 1);
		assertEquals("Partial JournalEntry", db.getItem(id).getEntry());
		db.deleteItem(entry);
	}
}
