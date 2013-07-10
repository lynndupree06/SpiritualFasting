package com.lynn.spiritualfasting.database;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynn.spiritualfasting.model.Fast;
import com.lynn.spiritualfasting.model.Recipe;
import com.lynn.spiritualfasting.model.YourFast;

public class RecipeDB extends DatabaseHandler<Recipe> {

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_INSTR = "instructions";
    private static final String KEY_TIME = "prepTime";
    private static final String KEY_SIZE = "servingSize";
    private static final String KEY_IMG = "imageURL";
    private static final String KEY_YOUR_FAST_ID = "yourFastId";
	
	public RecipeDB(Context context) {
		super(context);
		TABLE = "recipes";
		CREATE_TABLE = "CREATE TABLE " + TABLE + "("
	            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	            + KEY_NAME + " TEXT,"
				+ KEY_INSTR + " TEXT,"
				+ KEY_TIME + " INTEGER,"
				+ KEY_SIZE + " INTEGER,"
				+ KEY_IMG + " TEXT"
				+ KEY_YOUR_FAST_ID + " INTEGER)";
	}

	public long addItem(Recipe recipe) {
		// Gets the data repository in write mode
		SQLiteDatabase db = this.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(KEY_ID, recipe.getId());
		values.put(KEY_NAME, recipe.getName());
		values.put(KEY_INSTR, recipe.getInstructions());
		values.put(KEY_TIME, recipe.getPreperationTime());
		values.put(KEY_SIZE, recipe.getServingSize());
		values.put(KEY_IMG, recipe.getImage());

		// Insert the new row, returning the primary key value of the new row
		long newRowId = db.insert(TABLE, KEY_ID, values);
		return newRowId;
	}

	public int getItemCount() {
		SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
		
		String sql = "SELECT * FROM " + TABLE;
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor.moveToFirst()) {
            do {
                count++;
            } while (cursor.moveToNext());
        }
		
		return count;
	}

	public Recipe getItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
              
	    String[] selectionArgs = { String.valueOf(id) };
	    String sql = "SELECT * FROM " + TABLE 
				+ " LEFT JOIN yourFasts ON " + TABLE + "." + KEY_YOUR_FAST_ID + " = yourFasts.id "
        		+ " LEFT JOIN fasts ON yourFasts." + YourFastDB.KEY_FAST_ID + " = fasts.id"
        		+ " WHERE id = ?";
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		
		Recipe recipe = null;
		
		if(cursor.moveToFirst()) {
			String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
			String instructions = cursor.getString(cursor.getColumnIndexOrThrow(KEY_INSTR));
			int prepTime = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TIME));
			int servingSize = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SIZE));
			String imgURL = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMG));
			String start = cursor.getString(cursor.getColumnIndexOrThrow(YourFastDB.KEY_START));
			String end = cursor.getString(cursor.getColumnIndexOrThrow(YourFastDB.KEY_END));
			String fastName = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_NAME));
			int length = cursor.getInt(cursor.getColumnIndexOrThrow(FastDB.KEY_LENGTH));
			String url = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_URL));
			
			Timestamp startDate = null, endDate = null;
			
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.US);
				startDate = new Timestamp(sdf.parse(start).getTime());
				endDate = new Timestamp(sdf.parse(end).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//TODO: Get ingredients
			String[] ingredients = new String[]{};
			
			Fast fast = new Fast(fastName, length, url);
			YourFast yourFast = new YourFast(fast, startDate, endDate);
			recipe = new Recipe(id, name, instructions, ingredients, 
					prepTime, servingSize, imgURL, yourFast);
		}
		
		return recipe;
	}

	public List<Recipe> getAllItems() {
		List<Recipe> recipeList = new ArrayList<Recipe>();
		
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
            	String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
    			String instructions = cursor.getString(cursor.getColumnIndexOrThrow(KEY_INSTR));
    			int prepTime = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TIME));
    			int servingSize = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SIZE));
    			String imgURL = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMG));
    			String start = cursor.getString(cursor.getColumnIndexOrThrow(YourFastDB.KEY_START));
    			String end = cursor.getString(cursor.getColumnIndexOrThrow(YourFastDB.KEY_END));
    			String fastName = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_NAME));
    			int length = cursor.getInt(cursor.getColumnIndexOrThrow(FastDB.KEY_LENGTH));
    			String url = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_URL));
    			
    			Timestamp startDate = null, endDate = null;
    			
    			try {
    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.US);
    				startDate = new Timestamp(sdf.parse(start).getTime());
    				endDate = new Timestamp(sdf.parse(end).getTime());
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    			
    			//TODO: Get ingredients
    			String[] ingredients = new String[]{};
    			
    			Fast fast = new Fast(fastName, length, url);
    			YourFast yourFast = new YourFast(fast, startDate, endDate);
        		Recipe recipe = new Recipe(id, name, instructions, ingredients, 
        				prepTime, servingSize, imgURL, yourFast);
        		
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return recipeList;
	}

	public int updateItem(Recipe recipe) {
		SQLiteDatabase db = this.getReadableDatabase();

		// New value for one column
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, recipe.getName());
		values.put(KEY_INSTR, recipe.getInstructions());
		values.put(KEY_SIZE, recipe.getServingSize());
		values.put(KEY_TIME, recipe.getPreperationTime());
		values.put(KEY_IMG, recipe.getImage());

		// Which row to update, based on the ID
		String selection = KEY_ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(recipe.getId()) };

		int result = db.update(TABLE, values, selection, selectionArgs);
		return result;
	}

	public int deleteItem(Recipe recipe) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		// Define 'where' part of query.
		String selection = KEY_ID + " LIKE ?";
		
		// Specify arguments in placeholder order.
		String[] selectionArgs = { String.valueOf(recipe.getId()) };
		
		int result = db.delete(TABLE, selection, selectionArgs);
		return result;
	}

	@Override
	protected boolean itemExists(Recipe item, SQLiteDatabase db) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void init(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
}
