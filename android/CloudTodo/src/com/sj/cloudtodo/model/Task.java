package com.sj.cloudtodo.model;

import java.io.Serializable;
import java.util.Date;

import com.sj.cloudtodo.common.CloudTodo;

public class Task implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int STATUS_COMPLETE=1;
	public static final int STATUS_INCOMPLETE=0;
	private Date dueDate;
	private int id;
	private int priority;
	private Recurrance recurrance;
	private boolean recurring;
	private int status;
	private String task;
	
	public Date getDueDate() {
		return dueDate;
	}

	public int getId() {
		return id;
	}

	public int getPriority() {
		return priority;
	}

	public Recurrance getRecurrance() {
		return recurrance;
	}

	public int getStatus() {
		return status;
	}
	
	public String getTask() {
		return task;
	}

	public boolean isComplete() {
		return status==STATUS_COMPLETE;
	}

	public boolean isOverDue() {
		
		if ( dueDate == null )
			return false;
		try{
			//Remove date portion to enable comparison
			Date today = CloudTodo.stringToDate( CloudTodo.dateToString(new Date()) );
			
			if ( (status!=1) && (dueDate.compareTo(today)<0)  ) {
				return true;
			}
			else
				return false;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isRecurring() {
		return recurring;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public void setRecurrance(Recurrance recurrance) {
		this.recurrance = recurrance;
	}
	
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setTask(String task) {
		this.task = task;
	}
	
	public String toString() {
		String s = "Task :  " + task +"\n"
					+ "Due : " + getDueDate()+"\n"
					+ "Complete : " + isComplete()+"\n" 
					+ "Priority : " + getPriority()
					;
		return s;
	}
	
}
