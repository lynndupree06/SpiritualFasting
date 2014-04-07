package com.lynn.mobile.spiritualfasting.model;

public class Fast {
	private int id;
	private String name;
	private String purpose;
	private int length;
	private String url;
	private boolean custom;
	private String background;
	private String details;
	
	public Fast(int id, String name, String purpose, int length, String url, boolean isCustom) {
		this(id, name, purpose, length, url, isCustom, "", "");
	}
	
	public Fast(String name, String purpose, int length, String url, boolean isCustom) {
		this(0, name, purpose, length, url, isCustom, "", "");
	}
	
	public Fast(String name, String purpose, int length, String url, 
			boolean isCustom, String background, String details) {
		this(0, name, purpose, length, url, isCustom, background, details);
	}

	public Fast(int id, String name, String purpose, int length, 
			String url, boolean isCustom, String background, String details) {
		this.setId(id);
		this.setName(name);
		this.setPurpose(purpose);
		this.setLength(length);
		this.setUrl(url);
		this.setCustom(isCustom);
		this.setBackground(background);
		this.setDetails(details);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
