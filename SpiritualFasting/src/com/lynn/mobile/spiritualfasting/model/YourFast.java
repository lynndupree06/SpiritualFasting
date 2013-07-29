package com.lynn.mobile.spiritualfasting.model;

import java.sql.Timestamp;

public class YourFast {
	
	private Fast fast;
	private Timestamp startDate;
	private Timestamp endDate;
	private Integer id;
	
	private String[] entries;
	
	public YourFast(Fast fast, Timestamp start, Timestamp end) {
		this(null, fast, start, end, new String[]{});
	}
	
	public YourFast(Integer id, Fast fast, Timestamp start, Timestamp end) {
		this(id, fast, start, end, new String[]{});
	}
	
	public YourFast(Integer id, Fast fast, Timestamp start, Timestamp end,
			String[] entries) {
		setId(id);
		setFast(fast);
		setStartDate(start);
		setEndDate(end);
		setEntries(entries);
	}

	public Timestamp getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	
	public Timestamp getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String[] getEntries() {
		return entries;
	}

	public void setEntries(String[] entries) {
		this.entries = entries;
	}

	public Fast getFast() {
		return fast;
	}
	
	public void setFast(Fast fast) {
		this.fast = fast;
	}
}
