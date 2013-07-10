package com.lynn.spiritualfasting.test.database;

import java.util.List;

import android.test.ActivityInstrumentationTestCase2;

import com.lynn.spiritualfasting.MainActivity;
import com.lynn.spiritualfasting.database.RecipeDB;
import com.lynn.spiritualfasting.model.Recipe;

public class RecipeTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Recipe recipe;
	private RecipeDB db;
	private int id;
	private String name;
	private String[] ingredients;
	private String instructions;
	private int prepTime;
	private int servingSize;
	private String imageURL;

	public RecipeTest() {
		super("com.lynn.spiritualfasting", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		id = 1;
		name = "Recipe 1";
		ingredients = new String [] { "1/2 cup butter", "2 oz water" };
		instructions = "These are the instructions";
		prepTime = 60;
		servingSize = 6;
		imageURL = "/assests/recipe1.jpg";
		
		recipe = new Recipe(id, name, instructions, ingredients, 
				prepTime, servingSize, imageURL);
		db = new RecipeDB(getActivity());
		
		List<Recipe> recipes = db.getAllItems();
		for(Recipe r : recipes) {
			db.deleteItem(r);
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAddRecipe() {
		db.addItem(recipe);
		Recipe actual = db.getItem(recipe.getId());
		
		assertEquals(recipe.getId(), actual.getId());
		assertEquals(name, actual.getName());
		assertEquals(instructions, actual.getInstructions());
		assertEquals(prepTime, actual.getPreperationTime());
		assertEquals(servingSize, actual.getServingSize());
		assertEquals(imageURL, actual.getImage());
		assertEquals(ingredients.length, actual.getIngredients().length);
		
		db.deleteItem(recipe);
	}

	public void testGetRecipeById() {
		db.addItem(recipe);
		int actual = db.getItem(id).getId();
		assertEquals(id, actual);
		db.deleteItem(recipe);
	}
	
	public void testGetAllRecipes() {
		Recipe recipe1 = new Recipe(1, "Recipe 1", "Instructions to the first recipe");
		Recipe recipe2 = new Recipe(2, "Recipe 2", "Instructions to the second recipe");
		
		db.addItem(recipe1);
		db.addItem(recipe2);
		
		assertEquals(2, db.getAllItems().size());
		db.deleteItem(recipe1);
		db.deleteItem(recipe2);
	}
	
	public void testDeleteRecipe() {
		db.addItem(recipe);
		assertTrue(db.deleteItem(recipe) >= 1);
	}
	
	public void testUpdateRecipe() {
		db.addItem(recipe);
		Recipe currentFast = db.getItem(id);
		currentFast.setName("New Recipe");
		currentFast.setInstructions("New instructions");
		currentFast.setPreperationTime(30);
		currentFast.setServingSize(5);
		
		db.updateItem(currentFast);
		
		Recipe actual = db.getItem(id);
		assertEquals("Partial Recipe", actual.getName());
		assertEquals("New instructions", actual.getInstructions());
		assertEquals(30, actual.getPreperationTime());
		assertEquals(5, actual.getServingSize());
		
		db.deleteItem(recipe);
	}
	
	public void testGetRecipeCount() {
		Recipe recipe1 = new Recipe(1, "Recipe 1", "Instructions to the first recipe");
		Recipe recipe2 = new Recipe(2, "Recipe 2", "Instructions to the second recipe");
		
		db.addItem(recipe1);
		db.addItem(recipe2);
		
		assertEquals(2, db.getItemCount());
		db.deleteItem(recipe1);
		db.deleteItem(recipe2);
	}
}
