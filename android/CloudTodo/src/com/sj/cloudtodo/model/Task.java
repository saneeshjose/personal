package com.sj.cloudtodo.model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
		return status==1;
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
