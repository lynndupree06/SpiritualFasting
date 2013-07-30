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
import com.lynn.mobile.spiritualfasting.model.JournalEntry;
import com.lynn.mobile.spiritualfasting.model.YourFast;

public class JournalEntryDB extends DatabaseHandler<JournalEntry> {

    private static final String KEY_ID = "id";
    private static final String KEY_ENTRY = "entry";
    private static final String KEY_YOUR_FAST_ID = "yourFastId";
    private static final String KEY_DATE = "lastUpdated";
	private static final String KEY_DAY = "dayInFast";
	
	public JournalEntryDB(Context context) {
		super(context);
		TABLE = "journalEntries";
		CREATE_TABLE = "CREATE TABLE " + TABLE + "("
	            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	            + KEY_ENTRY + " TEXT, "
	            + KEY_YOUR_FAST_ID + " INTEGER, " 
	            + KEY_DATE + " TIMESTAMP,"
	            + KEY_DAY + " INTEGER)";
	}

	public long addItem(JournalEntry journalEntry) {
		// Gets the data repository in write mode
		SQLiteDatabase db = this.getWritableDatabase();
		long newRowId = 0;
		
//		if(!itemExists(journalEntry, db)) {
			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.putNull(KEY_ID);
			values.put(KEY_YOUR_FAST_ID, journalEntry.getYourFast().getId());
			values.put(KEY_ENTRY, journalEntry.getEntry());
			values.put(KEY_DAY, journalEntry.getDay());
			values.put(KEY_DATE, journalEntry.getLastUpdated().getTime());
	
			// Insert the new row, returning the primary key value of the new row
			newRowId = db.insert(TABLE, KEY_ID, values);
//		}
		
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

	public JournalEntry getItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		JournalEntry newJournalEntry = null;
		
	    String[] selectionArgs = { String.valueOf(id) };
		String sql = "SELECT * FROM " + TABLE 
				+ " LEFT JOIN yourFasts ON " + TABLE + "." + KEY_YOUR_FAST_ID + " = yourFasts.id "
        		+ " LEFT JOIN fasts ON yourFasts." + YourFastDB.KEY_FAST_ID + " = fasts.id"
        		+ " WHERE " + TABLE + ".id = ?";
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		
		if(cursor.moveToFirst()) {
			String entry = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ENTRY));
			int entryDay = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_DAY));
			String entryLastUpdated = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE));
			String start = cursor.getString(cursor.getColumnIndexOrThrow(YourFastDB.KEY_START));
			String end = cursor.getString(cursor.getColumnIndexOrThrow(YourFastDB.KEY_END));
			String name = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_NAME));
			String desc = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_DESC));
			int length = cursor.getInt(cursor.getColumnIndexOrThrow(FastDB.KEY_LENGTH));
			String url = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_URL));
			
			Timestamp startDate = null, endDate = null, date = null;
				
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.US);
				startDate = new Timestamp(sdf.parse(start).getTime());
				endDate = new Timestamp(sdf.parse(end).getTime());
				date = new Timestamp(sdf.parse(entryLastUpdated).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Fast fast = new Fast(name, desc, length, url);
			YourFast yourFast = new YourFast(fast, startDate, endDate);
			newJournalEntry = new JournalEntry(id, entry, yourFast, entryDay, date);
		}
		
		return newJournalEntry;
	}

	public List<JournalEntry> getAllItems() {
		List<JournalEntry> fastList = new ArrayList<JournalEntry>();
		
        // Select All Query
        String selectQuery = "SELECT " + TABLE + ".id AS JournalEntryID, "
        		+ KEY_ENTRY + ", " + KEY_DAY + ", " + KEY_DATE + ", " 
        		+ "yourFasts.id, " + YourFastDB.KEY_START + ", " + YourFastDB.KEY_END + ", "
        		+ FastDB.KEY_NAME + ", " + FastDB.KEY_DESC + ", " 
        		+ FastDB.KEY_LENGTH + ", " + FastDB.KEY_URL
        		+ " FROM " + TABLE
        		+ " LEFT JOIN yourFasts ON " + TABLE + "." + KEY_YOUR_FAST_ID + " = yourFasts.id "
        		+ " LEFT JOIN fasts ON yourFasts." + YourFastDB.KEY_FAST_ID + " = fasts.id";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	int id = cursor.getInt(cursor.getColumnIndexOrThrow("JournalEntryID"));
            	String entry = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ENTRY));
    			int entryDay = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_DAY));
    			String entryDate = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE));
    			int yourFastId = cursor.getInt(cursor.getColumnIndexOrThrow(YourFastDB.KEY_ID));
    			String start = cursor.getString(cursor.getColumnIndexOrThrow(YourFastDB.KEY_START));
    			String end = cursor.getString(cursor.getColumnIndexOrThrow(YourFastDB.KEY_END));
    			String name = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_NAME));
    			String desc = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_DESC));
    			int length = cursor.getInt(cursor.getColumnIndexOrThrow(FastDB.KEY_LENGTH));
    			String url = cursor.getString(cursor.getColumnIndexOrThrow(FastDB.KEY_URL));
    			
    			Timestamp startDate = null, endDate = null, date = null;
    				
    			try {
    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.US);
    				startDate = new Timestamp(sdf.parse(start).getTime());
    				endDate = new Timestamp(sdf.parse(end).getTime());
    				date = new Timestamp(sdf.parse(entryDate).getTime());
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    			
    			Fast fast = new Fast(name, desc, length, url);
    			YourFast yourFast = new YourFast(yourFastId, fast, startDate, endDate);
                JournalEntry journalEntry = new JournalEntry(id, entry, yourFast, entryDay, date);
                fastList.add(journalEntry);
            } while (cursor.moveToNext());
        }
     
        return fastList;
	}

	public int updateItem(JournalEntry journalEntry) {
		SQLiteDatabase db = this.getReadableDatabase();

		// New value for one column
		ContentValues values = new ContentValues();
		values.put(KEY_ENTRY, journalEntry.getEntry());

		// Which row to update, based on the ID
		String selection = KEY_ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(journalEntry.getId()) };

		int result = db.update(TABLE, values, selection, selectionArgs);
		return result;
	}

	public int deleteItem(JournalEntry journalEntry) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		// Define 'where' part of query.
		String selection = KEY_ID + " LIKE ?";
		
		// Specify arguments in placeholder order.
		String[] selectionArgs = { String.valueOf(journalEntry.getId()) };
		
		int result = db.delete(TABLE, selection, selectionArgs);
		return result;
	}

	protected boolean itemExists(JournalEntry entry, SQLiteDatabase db) {
	    String[] selectionArgs = { String.valueOf(entry.getDay()), 
	    		String.valueOf(entry.getYourFast().getId()) };
		String sql = "SELECT * FROM " + TABLE + " WHERE " 
	    		+ KEY_DAY + " = ? AND "
	    		+ KEY_YOUR_FAST_ID + " = ?";
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		
		return cursor.moveToFirst();
	}

	@Override
	protected void init(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
}
