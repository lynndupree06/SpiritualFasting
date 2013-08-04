package com.lynn.mobile.spiritualfasting.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.model.Scripture;

public class FastDB extends DatabaseHandler<Fast> {

	protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_DESC = "description";
    protected static final String KEY_LENGTH = "length";
    protected static final String KEY_URL = "url";
	private Context context;
	
	private static final String TABLE_SCRIPTURES = "scriptures";
	
	public FastDB(Context context) {
		super(context);
		this.context = context;
		TABLE = "fasts";
		CREATE_TABLE = "CREATE TABLE " + TABLE + "("
	            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	            + KEY_NAME + " TEXT," 
	            + KEY_DESC + " TEXT,"
	            + KEY_LENGTH + " INTEGER,"
	            + KEY_URL + " TEXT)";
	}

	public void init(SQLiteDatabase db) {
//		Fast fast = new Fast("The Disciple’s Fast", "Breaking Addiction", 7, "disciple_fast.html");
//		addItem(fast, db);
//		
//		fast = new Fast("The Ezra Fast", "Solving Problems", 5, "ezra_fast.html");
//		addItem(fast, db);
//		
//		fast = new Fast("The Elijah Fast", "Solving Emotional Problems", 5, "elijah_fast.html");
//		addItem(fast, db);
//		
//		fast = new Fast("The Widow’s Fast", "Humanitarian Needs", 5, "widow_fast.html");
//		addItem(fast, db);
//		
//		fast = new Fast("The Saint Paul’s Fast", "Decision Making", 5, "saint_paul_fast.html");
//		addItem(fast, db);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE);
		db.execSQL(CREATE_TABLE_FAST);
		
		Fast fast = new Fast("The Daniel Fast", "Health & Healing", 21, "daniel_fast.html");
		addItem(fast, db);
		
		fast = new Fast("The Samuel Fast", "Evangelism & Revival", 6, "samuel_fast.html");
		addItem(fast, db);
		
		fast = new Fast("The John the Baptist Fast", "For Testimony", 5, "john_baptist_fast.html");
		addItem(fast, db);
		
//		fast = new Fast("The Esther Fast", "Protection From the Evil One", 5, "esther_fast.html");
//		addItem(fast, db);
	}
	
	private void addScriptures(String fast, int fastId, SQLiteDatabase db) {
		AssetManager assetManager = context.getAssets();
		InputStream input;
		try {
			input = assetManager.open("scriptures/" + fast.replace("html", "txt"));

			int size = input.available();
		    byte[] buffer = new byte[size];
		    input.read(buffer);
		    input.close();

		    String text = new String(buffer);
		    String[] list = text.split("\n");
		          
		    int idx = 1;
		    for(String i : list) {
		    	String url = idx + "_" + fast;
		    	Scripture scripture = new Scripture(idx, i, fastId, url);
		    	addScripture(scripture, db);
		    	idx++;
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addScripture(Scripture scripture, SQLiteDatabase db) {
		long newRowId = 0;
		
		ContentValues values = new ContentValues();
		values.putNull(ScriptureDB.KEY_ID);
		values.put(ScriptureDB.KEY_DAY, scripture.getDay());
		values.put(ScriptureDB.KEY_SCRIPTURE, scripture.getScripture());
		values.put(ScriptureDB.KEY_FAST_ID, scripture.getFastId());
		values.put(ScriptureDB.KEY_URL, scripture.getUrl());
	
		// Insert the new row, returning the primary key value of the new row
		newRowId = db.insert(TABLE_SCRIPTURES, KEY_ID, values);
	}

	public void addItem(Fast fast, SQLiteDatabase db) {
		if(!itemExists(fast, db)) {
			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.putNull(KEY_ID);
			values.put(KEY_NAME, fast.getName());
			values.put(KEY_DESC, fast.getDescription());
			values.put(KEY_LENGTH, fast.getLength());
			values.put(KEY_URL, fast.getUrl());
	
			// Insert the new row, returning the primary key value of the new row
			long fastId = db.insert(TABLE, KEY_ID, values);
			addScriptures(fast.getUrl(), (int)fastId, db);
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
			values.put(KEY_DESC, fast.getDescription());
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
			String desc = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESC));
			int length = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_LENGTH));
			String url = cursor.getString(cursor.getColumnIndexOrThrow(KEY_URL));
			newFast = new Fast((int) id, name, desc, length, url);
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
			String desc = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESC));
			int length = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_LENGTH));
			String url = cursor.getString(cursor.getColumnIndexOrThrow(KEY_URL));
			
			newFast = new Fast(itemId, name, desc, length, url);
		}
		return newFast;
	}

	public List<Fast> getAllItems() {
		List<Fast> fastList = new ArrayList<Fast>();
		
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE 
        		+ " ORDER BY " + KEY_NAME + " ASC";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
    			String desc = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESC));
    			int length = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_LENGTH));
    			String url = cursor.getString(cursor.getColumnIndexOrThrow(KEY_URL));
                
                Fast fast = new Fast(id, name, desc, length, url);
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
		values.put(KEY_DESC, fast.getDescription());
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
