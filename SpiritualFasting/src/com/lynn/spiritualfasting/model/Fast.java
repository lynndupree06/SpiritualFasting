package com.lynn.spiritualfasting.model;

public class Fast {
	private int id;
	private String name;
	private int length;
	private String url;
	
	public Fast(String name, int length, String url) {
		this(0, name, length, url);
	}

	public Fast(int id, String name, int length, String url) {
		this.id = id;
		this.name = name;
		this.length = length;
		this.setUrl(url);
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
}
