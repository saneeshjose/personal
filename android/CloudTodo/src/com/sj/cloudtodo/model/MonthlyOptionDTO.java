package com.sj.cloudtodo.model;

public class MonthlyOptionDTO {
	
	public static int MONTHLY_OPTION_DAY = 1;
	public static int MONTHLY_OPTION_FIRST_DAY = 2;
	public static int MONTHLY_OPTION_LAST_DAY = 3;
	
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
