package com.sj.cloudtodo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private final String SQL_DB_SETUP_TASKS = "CREATE TABLE CLOUDTODO_TASKS ( " +
											"ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
											"TASK TEXT, "+
											"DUE_DATE TEXT, "+
											"PRIORITY INTEGER, "+
											"STATUS INTEGER"+
											")";
	
	private final String SQL_DB_SETUP_RECURRANCE = "CREATE TABLE CLOUDTODO_RECURRANCE ( " +
			"ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
			"TASK_ID_FK INTEGER, "+
			"START_DATE TEXT, "+
			"END_DATE TEXT, "+
			"FREQUENCY TEXT"+
			")";

	public DatabaseHelper(Context context) {
		super(context, "cloudtodo.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_DB_SETUP_TASKS);
		db.execSQL(SQL_DB_SETUP_RECURRANCE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.i("CloudTodo", "Upgrading database version from "+oldVersion+" to " + newVersion );
		
		if ( oldVersion == 1 && newVersion == 2 ) {
			db.execSQL("update cloudtodo_tasks set due_date=date(due_date)");
			Log.i("CloudTodo", "Upgrade completed" );
		}
		else
			Log.e("CloudTodo", "Upgrade not possible" );
	}

}
