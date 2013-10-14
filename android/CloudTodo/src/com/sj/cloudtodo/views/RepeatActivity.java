package com.sj.cloudtodo.views;

import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sj.cloudtodo.R;
import com.sj.cloudtodo.views.DayListDialog.DayListDialogListener;
import com.sj.cloudtodo.views.RepeatFrequencyDialog.RepeatFrequencyDialogListener;

public class RepeatActivity extends Activity implements RepeatFrequencyDialogListener, OnDateSetListener,DayListDialogListener {
	
	private boolean isCurrentDatePickerStartDate ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_repeat);
		
		LinearLayout layoutFrequency = (LinearLayout) findViewById(R.id.layoutRepeatFrequency);
		layoutFrequency.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment dialogFragment = new RepeatFrequencyDialog();
				dialogFragment.show(getFragmentManager(),"RepeatDialog");
			}
		});
		
		
		LinearLayout layoutRepeatStartDate = (LinearLayout) findViewById(R.id.layoutRepeatStartDate);
		layoutRepeatStartDate.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Date today = new Date();
				isCurrentDatePickerStartDate = true;
				DatePickerDialog d = new DatePickerDialog(RepeatActivity.this, RepeatActivity.this, 1900+ today.getYear(), today.getMonth(), today.getDate());
				d.show();
			}
		});
		
		LinearLayout layoutRepeatEndDate = (LinearLayout) findViewById(R.id.layoutRepeatEndDate);
		layoutRepeatEndDate.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Date today = new Date();
				isCurrentDatePickerStartDate = false;
				DatePickerDialog d = new DatePickerDialog(RepeatActivity.this, RepeatActivity.this, 1900+ today.getYear(), today.getMonth(), today.getDate());
				d.show();
			}
		});
		
	}
	
	@Override
	public void OnFrequencySelected(String frequency) {
		
		LinearLayout layoutFrequency = (LinearLayout) findViewById(R.id.layoutRepeatFrequency);
		TextView txtSecondaryText = (TextView) layoutFrequency.findViewById(R.id.txtSecondaryText);
		txtSecondaryText.setText(frequency);
		
		inflateSelectedFrequencyUI(frequency);
		
	}
	
	private void inflateSelectedFrequencyUI ( String frequency ) {
		
		ViewGroup mainLayout = (ViewGroup) findViewById(R.id.repeatfrequencymain);
		
		if ( "Daily".equalsIgnoreCase(frequency)) {
			
			ViewGroup vWeekly = (ViewGroup) mainLayout.findViewById(R.id.layoutRepeatFrequencyWeekly);
			
			if ( vWeekly !=null )
				mainLayout.removeView( vWeekly );
		}
		else if ( "Weekly".equalsIgnoreCase(frequency)) {
			View v = getLayoutInflater().inflate(R.layout.view_repeat_weekly, mainLayout);
			
			LinearLayout layoutRepeatFrequencyWeekly = (LinearLayout) v.findViewById(R.id.layoutRepeatFrequencyWeekly);
			layoutRepeatFrequencyWeekly.setOnClickListener( new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DialogFragment dialogFragment = new DayListDialog();
					dialogFragment.show(getFragmentManager(),"DayListDialog");
				}
			});
			
//			TextView txtPrimary = (TextView) v.findViewById(R.id.txtPrimaryText);
//			txtPrimary.setText("On Days");
			
//			TextView txtSecondary = (TextView) v.findViewById(R.id.txtSecondaryText);
//			txtSecondary.setText("None");
		}
		
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
		if ( isCurrentDatePickerStartDate ) {
			LinearLayout layoutDueDate = (LinearLayout) findViewById(R.id.layoutRepeatStartDate);
			TextView textDueDate = (TextView) layoutDueDate.findViewById(R.id.txtSecondaryText);
			String date = String.format("%d-%02d-%02d",year,  (1+monthOfYear), dayOfMonth );
			textDueDate.setText(date);
		}
		else {
			LinearLayout layoutDueDate = (LinearLayout) findViewById(R.id.layoutRepeatEndDate);
			TextView textDueDate = (TextView) layoutDueDate.findViewById(R.id.txtSecondaryText);
			String date = String.format("%d-%02d-%02d",year,  (1+monthOfYear), dayOfMonth );
			textDueDate.setText(date);
		}
		
	}

	@Override
	public void OnDayListSelected(String[] days) {
		
	}

}
