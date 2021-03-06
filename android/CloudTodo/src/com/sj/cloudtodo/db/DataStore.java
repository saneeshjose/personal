package com.sj.cloudtodo.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sj.cloudtodo.common.CloudTodo;
import com.sj.cloudtodo.model.Recurrance;
import com.sj.cloudtodo.model.Task;
import com.sj.cloudtodo.model.User;

public class DataStore {
	
	private Context context;
	private DatabaseHelper helper;
	private final String SHARED_PREF_USER = "user.info";
	private final String STR_USER_EMAIL = "user.info.email";
	
	private final String STR_USER_ID = "user.info.id";
	private final String STR_USER_NAME = "user.info.name";
	
	public DataStore( Context context) {
		this.context = context;
		this.helper = new DatabaseHelper(context);
	}
	
	public void deleteTask( Task task) {
		
		String sqlDeleteTasks = "DELETE FROM CLOUDTODO_TASKS WHERE ID=" + task.getId();
		String sqlDeleteRecurrance = "DELETE FROM CLOUDTODO_RECURRANCE WHERE TASK_ID_FK=" + task.getId();
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sqlDeleteTasks);
		db.execSQL(sqlDeleteRecurrance);
		db.close();
	}
	
	public List<Task> getAllTasks() {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		String []cols = new String[]{"ID, TASK, DUE_DATE, PRIORITY, STATUS"};
		Cursor c = db.query("CLOUDTODO_TASKS", cols , null, null, null, null, "STATUS" );
		
		List<Task> taskList = resultSetToTaskList(c);
		
		c.close();
		db.close();
		return taskList;
	}
	
	public Recurrance getRecurrance(Task task) {
		
		Recurrance recurrance = new Recurrance();
		
		SQLiteDatabase db = helper.getReadableDatabase();
		String []cols = new String[]{"ID, TASK_ID_FK, START_DATE, END_DATE, FREQUENCY"};
		Cursor c = db.query("CLOUDTODO_RECURRANCE", cols , "TASK_ID_FK="+task.getId(), null, null, null,null );
	
		try {
			if ( !c.moveToNext() ) return null;
			
			recurrance.setId(c.getInt(0));
			recurrance.setTaskId(c.getInt(1));
			recurrance.setStartDate(CloudTodo.stringToDate(c.getString(2)));
			recurrance.setEndDate(CloudTodo.stringToDate(c.getString(3)));
			recurrance.setFrequency(c.getString(4));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			c.close();
			db.close();
		}
		
		return recurrance;
	}
	
	public List<Task> getTasksDueToday() {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		String []cols = new String[]{"ID, TASK, DUE_DATE, PRIORITY, STATUS"};
		Cursor c = db.query("CLOUDTODO_TASKS", cols , "due_date = date(\'now\',\'localtime\')", null, null, null, "STATUS" );
		
		List<Task> taskList = resultSetToTaskList(c);
		
		c.close();
		db.close();
		return taskList;
	}
	
	public List<Task> getTasksDueTomorrow() {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		String []cols = new String[]{"ID, TASK, DUE_DATE, PRIORITY, STATUS"};
		Cursor c = db.query("CLOUDTODO_TASKS", cols , "due_date = date(\'now\',\'localtime\', \'+1 day\')", null, null, null, "STATUS" );
		
		List<Task> taskList = resultSetToTaskList(c);
		
		c.close();
		db.close();
		return taskList;
	}
	
	public List<Task> getTasksNoDueDate() {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		String []cols = new String[]{"ID, TASK, DUE_DATE, PRIORITY, STATUS"};
		Cursor c = db.query("CLOUDTODO_TASKS", cols , "due_date = date(\'now\',\'localtime\', \'+1 day\')", null, null, null, "STATUS" );
		
		List<Task> taskList = resultSetToTaskList(c);
		
		c.close();
		db.close();
		return taskList;
	}
	
	public List<Task> getTasksOverDue() {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		String []cols = new String[]{"ID, TASK, DUE_DATE, PRIORITY, STATUS"};
		Cursor c = db.query("CLOUDTODO_TASKS", cols , "( julianday(strftime('%Y-%m-%d','now'),'localtime')  -  julianday( strftime('%Y-%m-%d', due_date),'localtime')  ) >0 and status!=1", null, null, null, "STATUS" );
		
		List<Task> taskList = resultSetToTaskList(c);
		
		c.close();
		db.close();
		return taskList;
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
	
	private List<Task> resultSetToTaskList( Cursor c) {
		
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
			tlist.add(task);
		}
		
		return tlist;
	}
	
	public void saveRecurrance ( Recurrance r ) {
		
		String sql = "INSERT INTO CLOUDTODO_RECURRANCE ( TASK_ID_FK, START_DATE, END_DATE, FREQUENCY ) VALUES ("+
						r.getTaskId()+"," +
						"'"+CloudTodo.dateToString(r.getStartDate())+"',"+
						"'"+CloudTodo.dateToString(r.getEndDate())+"',"+
						"'"+r.getFrequency()+"'"+
						")";
		
		Log.i(CloudTodo.tag, sql);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
						
	}
	
	public void saveTask ( Task task ) {
		
		String sql="INSERT INTO CLOUDTODO_TASKS (TASK, DUE_DATE, PRIORITY, STATUS) VALUES (" +
				"'"+task.getTask()+"'," 
				+"'"+CloudTodo.dateToString(task.getDueDate())+"',"
				+task.getPriority()+","
				+task.getStatus()+
				")";
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
	}
	
	public void saveUserId( String userId) {
		SharedPreferences s = context.getSharedPreferences(SHARED_PREF_USER, 0);
		Editor e = s.edit();
		e.putString(STR_USER_ID, userId);
		e.commit();
	}
	
	public void saveUserInfo( User user ) {
		SharedPreferences s = context.getSharedPreferences(SHARED_PREF_USER, 0);
		Editor e = s.edit();
		e.putString(STR_USER_EMAIL, user.getEmail());
		e.putString(STR_USER_NAME, user.getName());
		e.commit();
	}
	
	public void updateRecurrance ( Recurrance r ) {
		Log.e(CloudTodo.tag, "Yet to implement!");
	}
	
	public void updateTask ( Task task ) {
		
		Log.i(CloudTodo.tag, "Uppdating task " + task.getId());
		
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
}
