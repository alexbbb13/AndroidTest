package com.mtoo.bbmath.helpers;

import java.util.ArrayList;
import java.util.List;
 

import com.mtoo.bbmath.activities.UserWithId;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
  
    // Database Name
    private static final String DATABASE_NAME = "bbMath";
  
    // Labels table name
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_PROGRAM_SETTINGS = "Program_Settings";
  
    // Labels Table Columns names
    private static final String KEY_ID = "user_id";
    private static final String KEY_NAME = "name";
    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final String KEY_PASSWORD = "password";
    
    private static final String KEY_LAST_USER = "last_user";
    private static final String VALUE_LAST_USER = "value";
    private static final int LAST_USER_COLUMN_INDEX = 1;
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
  
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
    	/*
    	 * for testing
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER  ," + KEY_NAME + " TEXT ," + KEY_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_PROGRAM_SETTINGS + "("
                + "key TEXT , value TEXT)";
        db.execSQL(CREATE_SETTINGS_TABLE);
        String FILL_SETTINGS_TABLE = "INSERT INTO " + TABLE_PROGRAM_SETTINGS + "( key , value ) values( 'db_version' , '1' )";
        db.execSQL(FILL_SETTINGS_TABLE);
        FILL_SETTINGS_TABLE = "INSERT INTO " + TABLE_PROGRAM_SETTINGS + "(key,value) values('last_user','0')";
        db.execSQL(FILL_SETTINGS_TABLE);
        FILL_SETTINGS_TABLE = "INSERT INTO " + TABLE_USERS + "(user_id,name) values( 1,'Alex')";
        db.execSQL(FILL_SETTINGS_TABLE);
        FILL_SETTINGS_TABLE = "INSERT INTO " + TABLE_USERS + "(user_id,name) values( 2,'Zed')";
        db.execSQL(FILL_SETTINGS_TABLE);
        FILL_SETTINGS_TABLE = "INSERT INTO " + TABLE_USERS + "(user_id,name) values( 3,'Keys')";
        db.execSQL(FILL_SETTINGS_TABLE);
        */
    }
  
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
  
        // Create tables again
        onCreate(db);
    }
     
    /**
     * Inserting new user into users table
     * */
    public void insertUser(String user){
        SQLiteDatabase db = this.getWritableDatabase();
         
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user);
          
        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }
     
    /**
     * Getting all users
     * returns List<String> of users
     * */
    public List<UserWithId> getAllUsers(){
        List<UserWithId> users = new ArrayList<UserWithId>();
         
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
      
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	UserWithId user= new UserWithId();
            	user.setId(cursor.getInt(ID_INDEX));
            	user.setName(cursor.getString(NAME_INDEX));
                users.add(user);
            } while (cursor.moveToNext());
        }
         
        // closing connection
        cursor.close();
        db.close();
         
        // returning users
        return users;
    }
    
    public int getLastUserId(){
    	
        // Select Last User Query
        String selectQuery = "SELECT * FROM " + TABLE_PROGRAM_SETTINGS+" WHERE key='"+KEY_LAST_USER+"'";
        int lastUserId=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	lastUserId=cursor.getInt(LAST_USER_COLUMN_INDEX);
            } while (cursor.moveToNext());
        }
         
        // closing connection
        cursor.close();
        db.close();
         
        // returning result
        return lastUserId;
    }
    
    public void setLastUserId(int id){
     	SQLiteDatabase db = this.getWritableDatabase();
     	String updateQuery = "UPDATE " + TABLE_PROGRAM_SETTINGS+" SET value="+id+" WHERE key=\""+KEY_LAST_USER+"\"";
     	db.execSQL(updateQuery);
        db.close(); // Closing database connection
    
    }
    
    public int getUserId(String username){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS+" WHERE "+KEY_NAME+"="+username;
        int userId=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(ID_INDEX);
            } while (cursor.moveToNext());
        }
         
        // closing connection
        cursor.close();
        db.close();
         
        return userId;
    }
}