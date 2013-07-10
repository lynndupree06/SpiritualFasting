package com.lynn.spiritualfasting.model;

import java.sql.Timestamp;

public class JournalEntry {
	public int id;
	public Timestamp date;
	public String entry;
	private YourFast fast;
	
	public JournalEntry(int id, String entry, YourFast fast, Timestamp date) {
		setId(id);
		setEntry(entry);
		setYourFast(fast);
		setDate(date);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
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
}
