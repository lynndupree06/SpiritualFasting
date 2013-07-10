package com.lynn.spiritualfasting.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PartialFast extends YourFast {

	List<Recipe> recipes;
	
	public PartialFast(Fast fast, Timestamp start, Timestamp end) {
		super(fast, start, end);
		recipes = new ArrayList<Recipe>();
	}

}
