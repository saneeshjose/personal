package com.sj.cloudtodo.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sj.cloudtodo.common.CloudTodo;
import com.sj.cloudtodo.model.Task;
import com.sj.cloudtodo.model.User;

public class DataStore {
	
	private final String SHARED_PREF_USER = "user.info";
	private final String STR_USER_ID = "user.info.id";
	private final String STR_USER_NAME = "user.info.name";
	private final String STR_USER_EMAIL = "user.info.email";
	
	private Context context;
	private DatabaseHelper helper;
	
	public DataStore( Context context) {
		this.context = context;
		this.helper = new DatabaseHelper(context);
	}
	
	public void saveUserId( String userId) {
		SharedPreferences s = context.getSharedPreferences(SHARED_PREF_USER, 0);
		Editor e = s.edit();
		e.putString(STR_USER_ID, userId);
		e.commit();
	}
	
	public String getUserId () {
		SharedPreferences s = context.getSharedPreferences(SHARED_PREF_USER, 0);
		return s.getString(STR_USER_ID, null);
	}
	
	public User getUserInfo() {
		
		User user = new User();
		
		SharedPreferences s = context.getSharedPreferences(SHARED_PREF_USER, 0);
		
		user.setEmail(s.getString(STR_USER_EMAIL, null));
		user.setName(s.getString(STR_USER_NAME, ""));
		
		return user;
	}
	
	public void saveUserInfo( User user ) {
		SharedPreferences s = context.getSharedPreferences(SHARED_PREF_USER, 0);
		Editor e = s.edit();
		e.putString(STR_USER_EMAIL, user.getEmail());
		e.putString(STR_USER_NAME, user.getName());
		e.commit();
	}
	
	public void saveTask ( Task task ) {
		
		String recurrance_fk = task.isRecurring()?task.getRecurrance().getId():"";
		
		
		String sql="INSERT INTO CLOUDTODO_TASKS (TASK, DUE_DATE, PRIORITY, STATUS, RECURRANCE_FK) VALUES (" +
				"'"+task.getTask()+"'," 
				+"'"+CloudTodo.dateToString(task.getDueDate())+"',"
				+task.getPriority()+","
				+task.getStatus()+","
				+"'"+recurrance_fk+"'"+
				")";
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
	}
	
	public void updateTask ( Task task ) {
		
		System.out.println("Uppdating task " + task.getId());
		
		String sql="UPDATE CLOUDTODO_TASKS SET " +
					"TASK='"+task.getTask()+"', " +
					"DUE_DATE='"+CloudTodo.dateToString(task.getDueDate())+"', " +
					"PRIORITY="+task.getPriority()+", " +
					"STATUS="+task.getStatus()+" " +
					"WHERE ID="+task.getId()+"";
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
	}
	
	public List<Task> getAllTasks() {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		String []cols = new String[]{"ID, TASK, DUE_DATE, PRIORITY, STATUS, RECURRANCE_FK"};
		Cursor c = db.query("CLOUDTODO_TASKS", cols , null, null, null, null, null );
		
		List<Task> tlist = new ArrayList<Task>();
		
		while( c.moveToNext()) {
			Task task = new Task();
			task.setId(c.getInt(0));
			task.setTask(c.getString(1));
			
			String strdate = c.getString(2);
			if ( (strdate != null) && !("null".equalsIgnoreCase(strdate)) ) {
				try{
					task.setDueDate(CloudTodo.stringToDate(strdate));
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
				
			task.setPriority(c.getInt(3));
			task.setStatus(c.getInt(4));
			task.setRecurrance(null);
			tlist.add(task);
			System.out.println(task);
		}
		
		c.close();
		db.close();
		return tlist;
	}
	
	public List<Task> getTasksDueToday() {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		String []cols = new String[]{"ID, TASK, DUE_DATE, PRIORITY, STATUS, RECURRANCE_FK"};
		Cursor c = db.query("CLOUDTODO_TASKS", cols , "due_date = date(\'now\',\'localtime\')", null, null, null, null );
		
		List<Task> tlist = new ArrayList<Task>();
		
		while( c.moveToNext()) {
			Task task = new Task();
			task.setId(c.getInt(0));
			task.setTask(c.getString(1));
			
			String strdate = c.getString(2);
			if ( (strdate != null) && !("null".equalsIgnoreCase(strdate)) ) {
				try{
					task.setDueDate(CloudTodo.stringToDate(strdate));
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
				
			task.setPriority(c.getInt(3));
			task.setStatus(c.getInt(4));
			task.setRecurrance(null);
			tlist.add(task);
			System.out.println(task);
		}
		
		c.close();
		db.close();
		return tlist;
	}
	
	public List<Task> getTasksDueTomorrow() {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		String []cols = new String[]{"ID, TASK, DUE_DATE, PRIORITY, STATUS, RECURRANCE_FK"};
		Cursor c = db.query("CLOUDTODO_TASKS", cols , "due_date = date(\'now\',\'localtime\', \'+1 day\')", null, null, null, null );
		
		List<Task> tlist = new ArrayList<Task>();
		
		while( c.moveToNext()) {
			Task task = new Task();
			task.setId(c.getInt(0));
			task.setTask(c.getString(1));
			
			String strdate = c.getString(2);
			if ( (strdate != null) && !("null".equalsIgnoreCase(strdate)) ) {
				try{
					task.setDueDate(CloudTodo.stringToDate(strdate));
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
				
			task.setPriority(c.getInt(3));
			task.setStatus(c.getInt(4));
			task.setRecurrance(null);
			tlist.add(task);
			System.out.println(task);
		}
		
		c.close();
		db.close();
		return tlist;
	}
	
	public void deleteTasks( List<Task> taskList) {
		
	}
}
