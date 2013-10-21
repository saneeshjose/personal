package com.sj.cloudtodo.model;

public class MonthlyOptionDTO {
	
	public static int MONTHLY_OPTION_DAY = 0;
	public static int MONTHLY_OPTION_FIRST_DAY = 0;
	public static int MONTHLY_OPTION_LAST_DAY = 0;
	
	private int selectedOption;
	private int day;
	
	public int getSelectedOption() {
		return selectedOption;
	}
	public void setSelectedOption(int selectedOption) {
		this.selectedOption = selectedOption;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
}
