package com.sj.cloudtodo.model;

import java.io.Serializable;
import java.util.Date;

public class Recurrance implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int taskId;
	
	private Date startDate;
	private Date endDate;
	private String frequency;
	
	public static final String RECURRANCE_NEVER ="Never";
	public static final String RECURRANCE_DAILY ="Daily"; 
	public static final String RECURRANCE_WEEKLY ="Weekly"; 
	public static final String RECURRANCE_MONTHLY ="Monthly";
	public static final String RECURRANCE_YEARLY ="Yearly"; 
	
	public Recurrance() {
		this.frequency = Recurrance.RECURRANCE_NEVER;
		// New object which is not in the db
		this.id = -1;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public int getTaskId() {
		return taskId;
	}
	
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}
