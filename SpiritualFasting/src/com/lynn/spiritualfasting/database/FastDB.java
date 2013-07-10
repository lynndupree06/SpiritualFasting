package com.lynn.spiritualfasting.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynn.spiritualfasting.model.Fast;

public class FastDB extends DatabaseHandler<Fast> {

	protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_LENGTH = "length";
    protected static final String KEY_URL = "url";
	
	public FastDB(Context context) {
		super(context);
		TABLE = "fasts";
		CREATE_TABLE = "CREATE TABLE " + TABLE + "("
	            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	            + KEY_NAME + " TEXT," 
	            + KEY_LENGTH + " INTEGER,"
	            + KEY_URL + " TEXT)";
	}

	protected void init(SQLiteDatabase db) {
//		Fast fast = new Fast("Corporate Fasting", 10, "corporate_fast.html");
//		addItem(fast, db);
//		
//		fast = new Fast("Full Fast", 0, "full_fast.html");
//		addItem(fast, db);
		
		Fast fast = new Fast("The Daniel Fast", 21, "daniel_fast.html");
		addItem(fast, db);
		
//		fast = new Fast("Fasting to Experience Gods Power", 10, "gods_power_fast.html");
//		addItem(fast, db);
		
		fast = new Fast("John the Baptist Fast", 10, "john_baptist_fast.html");
		addItem(fast, db);
		
//		fast = new Fast("The Normal Fast", 10, "normal_fast.html");
//		addItem(fast, db);
//		
//		fast = new Fast("Partial Fast", 10, "partial_fast.html");
//		addItem(fast, db);
		
		fast = new Fast("Paul's Fast", 10, "pauls_fast.html");
		addItem(fast, db);
		
//		fast = new Fast("Radical Fast", 10, "radical_fast.html");
//		addItem(fast, db);
		
		fast = new Fast("Samuel Fast", 10, "samuel_fast.html");
		addItem(fast, db);
	}
	
	public void addItem(Fast fast, SQLiteDatabase db) {
		if(!itemExists(fast, db)) {
			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.putNull(KEY_ID);
			values.put(KEY_NAME, fast.getName());
			values.put(KEY_LENGTH, fast.getLength());
			values.put(KEY_URL, fast.getUrl());
	
			// Insert the new row, returning the primary key value of the new row
			db.insert(TABLE, KEY_ID, values);
		}
	}

	public long addItem(Fast fast) {
		// Gets the data repository in write mode
		SQLiteDatabase db = this.getWritableDatabase();
		long newRowId = 0;
		
		if(!itemExists(fast, db)) {
			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.putNull(KEY_ID);
			values.put(KEY_NAME, fast.getName());
			values.put(KEY_LENGTH, fast.getLength());
			values.put(KEY_URL, fast.getUrl());
	
			// Insert the new row, returning the primary key value of the new row
			newRowId = db.insert(TABLE, KEY_ID, values);
		}
		
		return newRowId;
	}

	protected boolean itemExists(Fast fast, SQLiteDatabase db) {
	    String[] selectionArgs = { fast.getName() };
		String sql = "SELECT " + KEY_ID + " FROM " + TABLE + " WHERE name = ?";
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		
		return cursor.moveToFirst();
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

	public Fast getItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
              
	    String[] selectionArgs = { String.valueOf(id) };
		String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		Fast newFast = null;
		
		if(cursor.moveToFirst()) {
			String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
			int length = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_LENGTH));
			String url = cursor.getString(cursor.getColumnIndexOrThrow(KEY_URL));
			newFast = new Fast((int) id, name, length, url);
		}
		
		return newFast;
	}

	public Fast getItemByName(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		Fast newFast = null;
		
	    String[] selectionArgs = { name };
		String sql = "SELECT * FROM " + TABLE + " WHERE name = ?";
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		
		if(cursor.moveToFirst()) {
			int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
			int length = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_LENGTH));
			String url = cursor.getString(cursor.getColumnIndexOrThrow(KEY_URL));
			
			newFast = new Fast(itemId, name, length, url);
		}
		return newFast;
	}

	public List<Fast> getAllItems() {
		List<Fast> fastList = new ArrayList<Fast>();
		
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
    			int length = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_LENGTH));
    			String url = cursor.getString(cursor.getColumnIndexOrThrow(KEY_URL));
                
                Fast fast = new Fast(id, name, length, url);
                fastList.add(fast);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return fastList;
	}

	public int updateItem(Fast fast) {
		SQLiteDatabase db = this.getReadableDatabase();

		// New value for one column
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, fast.getName());
		values.put(KEY_LENGTH, fast.getLength());
		values.put(KEY_URL, fast.getUrl());

		// Which row to update, based on the ID
		String selection = KEY_ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(fast.getId()) };

		int result = db.update(TABLE, values, selection, selectionArgs);
		return result;
	}

	public int deleteItem(Fast fast) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		// Define 'where' part of query.
		String selection = KEY_ID + " LIKE ?";
		
		// Specify arguments in placeholder order.
		String[] selectionArgs = { String.valueOf(fast.getId()) };
		
		int result = db.delete(TABLE, selection, selectionArgs);
		return result;
	}
}
