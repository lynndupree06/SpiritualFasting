package com.lynn.mobile.spiritualfasting.database;

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

import com.lynn.mobile.spiritualfasting.model.Fast;
import com.lynn.mobile.spiritualfasting.model.YourFast;

public class YourFastDB extends DatabaseHandler<YourFast> {

	protected static final String KEY_ID = "id";
    protected static final String KEY_FAST_ID = "fastId";
	protected static final String KEY_START = "startDate";
	protected static final String KEY_END = "endDate";
	
	public YourFastDB(Context context) {
		super(context);
		TABLE = "yourFasts";
		CREATE_TABLE = "CREATE TABLE " + TABLE + "("
	            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	            + KEY_FAST_ID + " INTGER," 
	            + KEY_START + " TIMESTAMP,"
	            + KEY_END + " TIMESTAMP)";
	}

	public long addItem(YourFast fast) {
		// Gets the data repository in write mode
		SQLiteDatabase db = this.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.putNull(KEY_ID);
		values.put(KEY_FAST_ID, fast.getFast().getId());
		values.put(KEY_START, fast.getStartDate().toString());
		values.put(KEY_END, fast.getEndDate().toString());

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

	public YourFast getItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
              
	    String[] selectionArgs = { String.valueOf(id) };
	    String sql = "SELECT * FROM " + TABLE
				+ " LEFT JOIN fasts ON yourFasts." + YourFastDB.KEY_FAST_ID + " = fasts.id"
				+ " WHERE yourFasts.id = ?";
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		
		YourFast newFast = null;
		if(cursor.moveToFirst()) {
			int fastId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_FAST_ID));
			String start = cursor.getString(cursor.getColumnIndexOrThrow(KEY_START));
			String end = cursor.getString(cursor.getColumnIndexOrThrow(KEY_END));
			String name = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_NAME));
			String desc = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_DESC));
			int length = cursor.getInt(cursor.getColumnIndexOrThrow(FastDB.KEY_LENGTH));
			String url = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_URL));
			boolean custom = cursor.getInt(cursor.getColumnIndexOrThrow(FastDB.KEY_CUSTOM)) > 0;
	
			Timestamp startDate = null, endDate = null;
			
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.US);
				startDate = new Timestamp(sdf.parse(start).getTime());
				endDate = new Timestamp(sdf.parse(end).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Fast fast = new Fast(fastId, name, desc, length, url, custom);			
			newFast = new YourFast((Integer) id, fast, startDate, endDate);
		}
		return newFast;
	}

	public List<YourFast> getAllItems() {
		List<YourFast> fastList = new ArrayList<YourFast>();
		
        // Select All Query
        String selectQuery = "SELECT yourFasts.id AS YourFastsID, "
        		+ KEY_FAST_ID + ", " + KEY_START + ", " + KEY_END + ", " 
        		+ FastDB.KEY_NAME + ", " + FastDB.KEY_DESC + ", " 
        		+ FastDB.KEY_LENGTH + ", " + FastDB.KEY_URL + ", "
        		+ FastDB.KEY_CUSTOM + " FROM " + TABLE
				+ " LEFT JOIN fasts ON yourFasts." + YourFastDB.KEY_FAST_ID + " = fasts.id";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	int id = cursor.getInt(cursor.getColumnIndexOrThrow("YourFastsID"));
            	int fastId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_FAST_ID));
    			String start = cursor.getString(cursor.getColumnIndexOrThrow(KEY_START));
    			String end = cursor.getString(cursor.getColumnIndexOrThrow(KEY_END));
    			String name = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_NAME));
    			String desc = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_DESC));
    			int length = cursor.getInt(cursor.getColumnIndexOrThrow(FastDB.KEY_LENGTH));
    			String url = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_URL));
    			boolean custom = cursor.getInt(cursor.getColumnIndexOrThrow(FastDB.KEY_CUSTOM)) > 0;
    	
    			Timestamp startDate = null, endDate = null;
    			
    			try {
    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.US);
    				startDate = new Timestamp(sdf.parse(start).getTime());
    				endDate = new Timestamp(sdf.parse(end).getTime());
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}

    			Fast fast = new Fast(fastId, name, desc, length, url, custom);			
        		YourFast yourFast = new YourFast((Integer) id, fast, startDate, endDate);
                fastList.add(yourFast);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return fastList;
	}

	public int updateItem(YourFast fast) {
		SQLiteDatabase db = this.getReadableDatabase();

		// New value for one column
		ContentValues values = new ContentValues();
		values.put(KEY_FAST_ID, fast.getFast().getId());
		values.put(KEY_START, fast.getStartDate().toString());
		values.put(KEY_END, fast.getEndDate().toString());

		// Which row to update, based on the ID
		String selection = KEY_ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(fast.getId()) };

		int result = db.update(TABLE, values, selection, selectionArgs);
		return result;
	}

	public int deleteItem(YourFast fast) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		// Define 'where' part of query.
		String selection = KEY_ID + " LIKE ?";
		
		// Specify arguments in placeholder order.
		String[] selectionArgs = { String.valueOf(fast.getId()) };
		
		int result = db.delete(TABLE, selection, selectionArgs);
		return result;
	}

	@Override
	protected boolean itemExists(YourFast item, SQLiteDatabase db) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void init(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
}
