package com.lynn.spiritualfasting.database;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynn.spiritualfasting.model.Scripture;

public class ScriptureDB extends DatabaseHandler<Scripture> {

	protected static final String KEY_ID = "id";
	protected static final String KEY_DAY = "day";
	protected static final String KEY_SCRIPTURE = "scripture";
	protected static final String KEY_FAST_ID = "fastId";
	protected static final String KEY_URL = "url";

	public ScriptureDB(Context context) {
		super(context);
		TABLE = "scriptures";
	}

	@Override
	public long addItem(Scripture item) {
		SQLiteDatabase db = this.getWritableDatabase();
		long newRowId = 0;
		
		if(!itemExists(item, db)) {
			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.putNull(KEY_ID);
			values.put(KEY_DAY, item.getDay());
			values.put(KEY_SCRIPTURE, item.getScripture());
			values.put(KEY_FAST_ID, item.getFastId());
	
			// Insert the new row, returning the primary key value of the new row
			newRowId = db.insert(TABLE, KEY_ID, values);
		}
		
		return newRowId;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Scripture getItem(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Scripture> getAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateItem(Scripture item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteItem(Scripture item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean itemExists(Scripture item, SQLiteDatabase db) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void init(SQLiteDatabase db) {
		
	}

	public Scripture getItemByUniqueId(int day, int fastId) {
		SQLiteDatabase db = this.getReadableDatabase();
		Scripture scripture = null;
		
	    String[] selectionArgs = { String.valueOf(day), String.valueOf(fastId) };
		String sql = "SELECT * FROM " + TABLE + " WHERE day = ? AND fastId = ?";
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		
		if(cursor.moveToFirst()) {
			int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
			String scriptureText = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SCRIPTURE));
			String url = cursor.getString(cursor.getColumnIndexOrThrow(KEY_URL));
			
			scripture = new Scripture(itemId, day, scriptureText, fastId, url);
		}
		
		return scripture;
	}

}
