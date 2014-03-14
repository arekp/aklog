package com.arekp.aklog.database;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.arekp.aklog.RaportBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ParseException;
import android.util.Log;

public class RaportDbAdapter {
	 private static final String DEBUG_TAG = "SqLiteTodoManager";
	 
	    private static final int DB_VERSION =3 ;
	    private static final String DB_NAME = "databaseAkLog.db";
	    private static final String DB_TODO_TABLE = "raport";
	 
	    public static final String KEY_ID = "_id";
	    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	    public static final int ID_COLUMN = 0;
	    
	    public static final String KEY_FREQ = "frequency";
	    public static final String FREQ_OPTIONS = "REAL";
	    public static final int FREQ_COLUMN = 1;
	    
	    public static final String KEY_MODE = "mode";
	    public static final String MODE_OPTIONS = "TEXT NOT NULL";
	    public static final int MODE_COLUMN = 2;
	 
	    public static final String KEY_DATA = "data";
	    public static final String DATA_OPTIONS = "TEXT";
	    public static final int DATA_COLUMN = 3;
	    
	    public static final String KEY_CALLSIGN = "callsign";
	    public static final String CALLSIGN_OPTIONS = "TEXT";
	    public static final int CALLSIGN_COLUMN = 4;
	    
	    public static final String KEY_RS = "rs";
	    public static final String RS_OPTIONS = "INTEGER DEFAULT 0";
	    public static final int RS_COLUMN = 5;
	    
	    public static final String KEY_RT = "rt";
	    public static final String RT_OPTIONS = "INTEGER DEFAULT 0";
	    public static final int RT_COLUMN = 6;
	    
	    public static final String KEY_NOTE = "note";
	    public static final String NOTE_OPTIONS = "TEXT";
	    public static final int NOTE_COLUMN = 7;
	    
	    public static final String KEY_STATUS = "status";
	    public static final String STATUS_OPTIONS = "INTEGER";
	    public static final int STATUS_COLUMN = 8;
	    
	    private static final String DB_CREATE_TODO_TABLE =
	            "CREATE TABLE " + DB_TODO_TABLE + "( " +
	            KEY_ID + " " + ID_OPTIONS + ", " +
	            KEY_FREQ + " " + FREQ_OPTIONS + ", " +
	            KEY_MODE + " " + MODE_OPTIONS + ", " +
	            KEY_DATA + " " + DATA_OPTIONS + ", " +
	            KEY_CALLSIGN + " " + CALLSIGN_OPTIONS + ", " +
	            KEY_RS + " " + RS_OPTIONS + ", " +
	            KEY_RT + " " + RT_OPTIONS + ", " +
	            KEY_NOTE + " " + NOTE_OPTIONS +", " +
	             KEY_STATUS + " " + STATUS_OPTIONS +
	            ");";
	    private static final String DROP_TODO_TABLE =
	            "DROP TABLE IF EXISTS " + DB_TODO_TABLE;
	 
	    private SQLiteDatabase db;
	    private Context context;
	    private DatabaseHelper dbHelper;
	 
	    private static class DatabaseHelper extends SQLiteOpenHelper {
	        public DatabaseHelper(Context context, String name,
	                CursorFactory factory, int version) {
	            super(context, name, factory, version);
	        }
	 
	        @Override
	        public void onCreate(SQLiteDatabase db) {
	            db.execSQL(DB_CREATE_TODO_TABLE);
	 
	            Log.d(DEBUG_TAG, "Database creating...");
	            Log.d(DEBUG_TAG, "Table " + DB_TODO_TABLE + " ver." + DB_VERSION + " created");
	        }
	 
	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            db.execSQL(DROP_TODO_TABLE);
	 
	            Log.d(DEBUG_TAG, "Database updating...");
	            Log.d(DEBUG_TAG, "Table " + DB_TODO_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
	            Log.d(DEBUG_TAG, "All data is lost.");
	 
	            onCreate(db);
	        }
	    }
	 
	    public RaportDbAdapter(Context context) {
	        this.context = context;
	    }
	 
	    public RaportDbAdapter open(){
	        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
	        try {
	            db = dbHelper.getWritableDatabase();
	        } catch (SQLException e) {
	            db = dbHelper.getReadableDatabase();
	        }
	        return this;
	    }
	 
	    public void close() {
	        dbHelper.close();
	    }
	 
	    public long insertRaport(RaportBean rap) {
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	
	        ContentValues newRaportValues = new ContentValues();
	        newRaportValues.put(KEY_FREQ, rap.getFrequency());
	        newRaportValues.put(KEY_MODE, rap.getMode());
	        newRaportValues.put(KEY_DATA, dateFormat.format(rap.getData()));
	        newRaportValues.put(KEY_CALLSIGN, rap.getCallsign());
	        newRaportValues.put(KEY_RS, rap.getRs());
	        newRaportValues.put(KEY_RT, rap.getRt());
	        newRaportValues.put(KEY_NOTE, rap.getNote());
	        return db.insert(DB_TODO_TABLE, null, newRaportValues);
	    }
	
	    public boolean updateRaport(RaportBean rap) {
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        long id = rap.getId();
	        String where = KEY_ID + "=" + id;
	        Log.d("raportAdapter_updateTodoStatus",where);
	        ContentValues updateTodoValues = new ContentValues();
	        updateTodoValues.put(KEY_FREQ, rap.getFrequency());
	        updateTodoValues.put(KEY_MODE, rap.getMode());
	        updateTodoValues.put(KEY_DATA, dateFormat.format(rap.getData()));
	        updateTodoValues.put(KEY_CALLSIGN, rap.getCallsign());
	        updateTodoValues.put(KEY_RS, rap.getRs());
	        updateTodoValues.put(KEY_RT, rap.getRt());
	        updateTodoValues.put(KEY_NOTE, rap.getNote());
	        return db.update(DB_TODO_TABLE, updateTodoValues, where, null) > 0;
	    }
	 
	    public boolean updateTodoStatus(long id,Boolean status) {
	        String where = KEY_ID + "=" + id;
     int statusTask = status ? 1 : 0;
	        Log.d("raportAdapter_updateTodoStatus",where+" "+status.toString());
	        ContentValues updateTodoValues = new ContentValues();
	        //updateTodoValues.put(KEY_FREQ, description);
	        updateTodoValues.put(KEY_STATUS, statusTask);
	        return db.update(DB_TODO_TABLE, updateTodoValues, where, null) > 0;
	    }
	 
	    public void updateAllOffStatus() {
	        ContentValues updateTodoValues = new ContentValues();
	        updateTodoValues.put(KEY_STATUS, 0);
	        Log.d("raportAdapter_updateTodoStatus","Wszystko na 0 ");
	       db.update(DB_TODO_TABLE, updateTodoValues, null, null);
	    }
	    public boolean deleteTodo(long id){
	        String where = KEY_ID + "=" + id;
	        return db.delete(DB_TODO_TABLE, where, null) > 0;
	    }
	 
	    public Cursor getAllReports() {
	        String[] columns = {KEY_ID,KEY_FREQ, KEY_MODE,KEY_DATA,KEY_CALLSIGN,KEY_RS,KEY_RT,KEY_NOTE};
	        return db.query(DB_TODO_TABLE, columns, null, null, null, null, null);
	    }
	 
	public RaportBean getReport(long id) {
		String[] columns = { KEY_ID, KEY_FREQ, KEY_MODE, KEY_DATA,
				KEY_CALLSIGN, KEY_RS, KEY_RT, KEY_NOTE };
		String where = KEY_ID + "=" + id;
		Cursor cursor = db.query(DB_TODO_TABLE, columns, where, null, null,
				null, null);
		RaportBean rap = null;
		if (cursor != null && cursor.moveToFirst()) {
			// boolean completed = cursor.getInt(COMPLETED_COLUMN) > 0 ? true :
			// false;
			Float frequency = cursor.getFloat(FREQ_COLUMN);
			String mode = cursor.getString(MODE_COLUMN);
			Date data=null;
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				 data = sdf.parse(cursor.getString(DATA_COLUMN));
				}catch(ParseException ex){

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//Date data = cursor.getString(DATA_COLUMN);
			String callsign = cursor.getString(CALLSIGN_COLUMN);
			Integer rs = cursor.getInt(RS_COLUMN);
			Integer rt = cursor.getInt(RT_COLUMN);
			String note = cursor.getString(NOTE_COLUMN);
			rap = new RaportBean(id, frequency, mode, data, callsign, rs, rt,note);
		}
		return rap;
	}
}
