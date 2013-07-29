package com.lynn.mobile.spiritualfasting.database;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DatabaseHandler<T> extends SQLiteOpenHelper {

	protected static final int DATABASE_VERSION = 1; // Database Version
    protected static final String DATABASE_NAME = "spiritual_fasting.db"; // Database Name
    
    protected String TABLE = "";
    protected String CREATE_TABLE = "";
    protected String CREATE_TABLE_FAST = "CREATE TABLE fasts("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT," 
            + "description TEXT,"
            + "length INTEGER,"
            + "url TEXT)";
    
    protected String CREATE_TABLE_YOUR_FAST = "CREATE TABLE yourFasts("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "fastId INTGER," 
            + "startDate TIMESTAMP,"
            + "endDate TIMESTAMP)";
    
    protected String CREATE_TABLE_JOURNAL = "CREATE TABLE journalEntries("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "entry TEXT, "
            + "yourFastId INTEGER, " 
            + "lastUpdated TIMESTAMP,"
            + "dayInFast INTEGER)";
    
    protected String CREATE_TABLE_RECIPE = "CREATE TABLE recipes("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT,"
			+ "instructions TEXT,"
			+ "preperationTime INTEGER,"
			+ "servingSize INTEGER,"
			+ "imgUrl TEXT"
			+ "yourFastId INTEGER)";
    
    protected String CREATE_TABLE_SCRIPTURES = "CREATE TABLE scriptures("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "day INTEGER,"
			+ "scripture TEXT,"
			+ "fastId INTEGER, "
			+ "url TEXT)";
    
    public abstract long addItem(T item);
	public abstract int getItemCount();
	public abstract T getItem(int id);
	public abstract List<T> getAllItems();
	public abstract int updateItem(T item);
	public abstract int deleteItem(T item);
	protected abstract boolean itemExists(T item, SQLiteDatabase db);
    protected abstract void init(SQLiteDatabase db);
    
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAST);
        db.execSQL(CREATE_TABLE_YOUR_FAST);
        db.execSQL(CREATE_TABLE_JOURNAL);
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_SCRIPTURES);
        init(db);
    }
    
	// Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
 
        // Create tables again
        onCreate(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    
    
}
