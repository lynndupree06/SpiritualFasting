package com.lynn.mobile.spiritualfasting.model;

public class Scripture {

	private int id;
	private int day;
	private String scripture;
	private int fastId;
	private String url;
	
	public Scripture(int day, String scripture, int fastId, String url) {
		this(0, day, scripture, fastId, url);
	}

	public Scripture(int id, int day, String scripture, int fastId, String url) {
		setId(id);
		setDay(day);
		setScripture(scripture);
		setFastId(fastId);
		setUrl(url);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getScripture() {
		return scripture;
	}

	public void setScripture(String scripture) {
		this.scripture = scripture;
	}

	public int getFastId() {
		return fastId;
	}

	public void setFastId(int fastId) {
		this.fastId = fastId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
