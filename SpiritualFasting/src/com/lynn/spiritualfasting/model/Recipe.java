package com.lynn.spiritualfasting.model;

public class Recipe {
	
	private Integer id;
	private String name;
	private String[] ingredients;
	private String instructions;
	private int preperationTime;
	private int servingSize;
	private String image;
	private YourFast fast;
	
	public Recipe(int id, String name, String instructions) {
		this(id, name, instructions, new String[]{}, 
				Integer.valueOf(null), Integer.valueOf(null), new String(), null);
	}
	
	public Recipe(int id, String name, String instructions,
			String[] ingredients, Integer prepTime, Integer servingSize,
			String imageURL, YourFast fast) {
		setId(id);
		setName(name);
		setIngredients(ingredients);
		setInstructions(instructions);
		setPreperationTime(prepTime);
		setServingSize(servingSize);
		setImage(imageURL);
		setFast(fast);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String[] getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}
	
	public String getInstructions() {
		return instructions;
	}
	
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public int getPreperationTime() {
		return preperationTime;
	}
	
	public void setPreperationTime(int preperationTime) {
		this.preperationTime = preperationTime;
	}
	
	public int getServingSize() {
		return servingSize;
	}
	
	public void setServingSize(int servingSize) {
		this.servingSize = servingSize;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public YourFast getFast() {
		return fast;
	}

	public void setFast(YourFast fast) {
		this.fast = fast;
	}
}
