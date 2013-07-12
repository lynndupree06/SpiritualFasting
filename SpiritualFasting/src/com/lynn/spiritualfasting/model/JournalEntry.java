package com.lynn.spiritualfasting.model;

import java.sql.Timestamp;
import java.util.Calendar;

public class JournalEntry {
	public int id;
	public Timestamp lastUpdated;
	public String entry;
	private YourFast fast;
	private int day;
	
	public JournalEntry(String entry, YourFast fast, int day) {
		this(0, entry, fast, day, new Timestamp(Calendar.getInstance().getTime().getTime()));
	}
	
	public JournalEntry(int id, String entry, YourFast fast, int day, Timestamp date) {
		setId(id);
		setEntry(entry);
		setYourFast(fast);
		setDay(day);
		setLastUpdated(date);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated(Timestamp date) {
		this.lastUpdated = date;
	}
	
	public String getEntry() {
		return entry;
	}
	
	public void setEntry(String entry) {
		this.entry = entry;
	}

	public YourFast getYourFast() {
		return fast;
	}

	public void setYourFast(YourFast fast) {
		this.fast = fast;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
