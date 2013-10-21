package com.sj.cloudtodo.views;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sj.cloudtodo.R;
import com.sj.cloudtodo.common.CloudTodo;
import com.sj.cloudtodo.db.DataStore;
import com.sj.cloudtodo.model.MonthlyOptionDTO;
import com.sj.cloudtodo.model.Recurrance;
import com.sj.cloudtodo.model.Task;
import com.sj.cloudtodo.views.DayListDialog.DayListDialogListener;
import com.sj.cloudtodo.views.MonthlyRepeatDialog.MonthlyRepeatDialogListener;
import com.sj.cloudtodo.views.RepeatFrequencyDialog.RepeatFrequencyDialogListener;

public class RepeatActivity extends Activity implements 
				RepeatFrequencyDialogListener, 
				OnDateSetListener,
				DayListDialogListener,
				MonthlyRepeatDialogListener {
	
	private boolean isCurrentDatePickerStartDate ;
	
	private Recurrance recurrance;
	private Task task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_repeat);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			
			this.task = (Task) extras.get("task");
			this.recurrance = (Recurrance) extras.get("recurrance");
			
			if ( this.recurrance == null ) {
				Log.i(CloudTodo.tag, "No recurrance for this task");
				recurrance = new Recurrance();
				recurrance.setFrequency(Recurrance.RECURRANCE_NEVER);
				recurrance.setTaskId(task.getId());
			}
		} 

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
		
		refreshUI();
	}
	
	@Override
	public void OnFrequencySelected(String frequency) {
		
		removeCurrentFrequencyUI();
		
		LinearLayout layoutFrequency = (LinearLayout) findViewById(R.id.layoutRepeatFrequency);
		TextView txtSecondaryText = (TextView) layoutFrequency.findViewById(R.id.txtSecondaryText);
		txtSecondaryText.setText(frequency);
		this.recurrance.setFrequency(frequency);
		
		inflateSelectedFrequencyUI(frequency);
		
	}
	
	private void removeCurrentFrequencyUI( ) {
		
		ViewGroup mainLayout = (ViewGroup) findViewById(R.id.repeatfrequencymain);

		if ( Recurrance.RECURRANCE_DAILY.equalsIgnoreCase(this.recurrance.getFrequency())) {
			//TODO
			
			LinearLayout layoutRepeatStartDate = (LinearLayout) findViewById(R.id.layoutRepeatStartDate);
			LinearLayout layoutRepeatEndDate = (LinearLayout) findViewById(R.id.layoutRepeatEndDate);
			
			layoutRepeatStartDate.setVisibility(View.INVISIBLE);
			layoutRepeatEndDate.setVisibility(View.INVISIBLE);
		}
		else if ( Recurrance.RECURRANCE_WEEKLY.equalsIgnoreCase(this.recurrance.getFrequency())) {
			LinearLayout layoutRepeatStartDate = (LinearLayout) findViewById(R.id.layoutRepeatStartDate);
			LinearLayout layoutRepeatEndDate = (LinearLayout) findViewById(R.id.layoutRepeatEndDate);
			
			layoutRepeatStartDate.setVisibility(View.INVISIBLE);
			layoutRepeatEndDate.setVisibility(View.INVISIBLE);
			
			ViewGroup vWeekly = (ViewGroup) mainLayout.findViewById(R.id.layoutRepeatFrequencyWeekly);
			
			if ( vWeekly !=null )
				mainLayout.removeView( vWeekly );
		}
		else if ( Recurrance.RECURRANCE_MONTHLY.equalsIgnoreCase(this.recurrance.getFrequency())) {
			
			LinearLayout layoutRepeatStartDate = (LinearLayout) findViewById(R.id.layoutRepeatStartDate);
			LinearLayout layoutRepeatEndDate = (LinearLayout) findViewById(R.id.layoutRepeatEndDate);
			
			layoutRepeatStartDate.setVisibility(View.INVISIBLE);
			layoutRepeatEndDate.setVisibility(View.INVISIBLE);
			
			ViewGroup vMonthly = (ViewGroup) mainLayout.findViewById(R.id.layoutRepeatFrequencyMonthly);
			
			if ( vMonthly !=null )
				mainLayout.removeView( vMonthly );
		}
	}
	
	private void inflateSelectedFrequencyUI ( String frequency ) {
		
		ViewGroup mainLayout = (ViewGroup) findViewById(R.id.repeatfrequencymain);
		
		if ( Recurrance.RECURRANCE_DAILY.equalsIgnoreCase(frequency)) {
			LinearLayout layoutRepeatStartDate = (LinearLayout) findViewById(R.id.layoutRepeatStartDate);
			LinearLayout layoutRepeatEndDate = (LinearLayout) findViewById(R.id.layoutRepeatEndDate);
			
			layoutRepeatStartDate.setVisibility(View.VISIBLE);
			layoutRepeatEndDate.setVisibility(View.VISIBLE);
		}
		else if ( Recurrance.RECURRANCE_WEEKLY.equalsIgnoreCase(frequency)) {
			
			LinearLayout layoutRepeatStartDate = (LinearLayout) findViewById(R.id.layoutRepeatStartDate);
			LinearLayout layoutRepeatEndDate = (LinearLayout) findViewById(R.id.layoutRepeatEndDate);
			
			layoutRepeatStartDate.setVisibility(View.VISIBLE);
			layoutRepeatEndDate.setVisibility(View.VISIBLE);
			
			View v = getLayoutInflater().inflate(R.layout.view_repeat_weekly, mainLayout);
			
			LinearLayout layoutRepeatFrequencyWeekly = (LinearLayout) v.findViewById(R.id.layoutRepeatFrequencyWeekly);
			layoutRepeatFrequencyWeekly.setOnClickListener( new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DialogFragment dialogFragment = new DayListDialog();
					dialogFragment.show(getFragmentManager(),"DayListDialog");
				}
			});
		}
		
		else if ( Recurrance.RECURRANCE_MONTHLY.equalsIgnoreCase(frequency)) {
			
			LinearLayout layoutRepeatStartDate = (LinearLayout) findViewById(R.id.layoutRepeatStartDate);
			LinearLayout layoutRepeatEndDate = (LinearLayout) findViewById(R.id.layoutRepeatEndDate);
			
			layoutRepeatStartDate.setVisibility(View.VISIBLE);
			layoutRepeatEndDate.setVisibility(View.VISIBLE);
			
			View v = getLayoutInflater().inflate(R.layout.view_repeat_monthly, mainLayout);
			
			LinearLayout layoutRepeatFrequencyMonthly = (LinearLayout) v.findViewById(R.id.layoutRepeatFrequencyMonthly);
			layoutRepeatFrequencyMonthly.setOnClickListener( new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DialogFragment dialogFragment = new MonthlyRepeatDialog();
					dialogFragment.show(getFragmentManager(),"MonthlyRepeatDialog");
				}
			});
		}
		
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
		String date = String.format("%d-%02d-%02d",year,  (1+monthOfYear), dayOfMonth );
		
		if ( isCurrentDatePickerStartDate ) {
			LinearLayout layoutDueDate = (LinearLayout) findViewById(R.id.layoutRepeatStartDate);
			TextView textDueDate = (TextView) layoutDueDate.findViewById(R.id.txtSecondaryText);
			textDueDate.setText(date);
			try{
				this.recurrance.setStartDate(CloudTodo.stringToDate(date));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			LinearLayout layoutDueDate = (LinearLayout) findViewById(R.id.layoutRepeatEndDate);
			TextView textDueDate = (TextView) layoutDueDate.findViewById(R.id.txtSecondaryText);
			textDueDate.setText(date);
			
			try{
				this.recurrance.setEndDate(CloudTodo.stringToDate(date));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void OnDayListSelected( List<String> days) {
		LinearLayout layoutRepeatFrequencyWeekly = (LinearLayout) findViewById(R.id.layoutRepeatFrequencyWeekly);
		TextView textDays = (TextView ) layoutRepeatFrequencyWeekly.findViewById(R.id.txtSecondaryText);
		
		String daysString = "";
		
		if ( days.size() == 0 ) daysString = "None";
		
		for ( int i=0; i<days.size(); i++ ) {
			daysString+=days.get(i);
			
			if ( i != days.size()-1 ) daysString+=", ";  
		}
		textDays.setText(daysString);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_task_recurrance, menu);
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if ( item.getItemId() == R.id.menu_activity_task_recurrance_save) {
			
			if ( ! Recurrance.RECURRANCE_NEVER.equalsIgnoreCase(this.recurrance.getFrequency()) ) {
				DataStore d = new DataStore(this);
				if (recurrance.getId()==-1)
					d.saveRecurrance(this.recurrance);
				else
					d.updateRecurrance(recurrance);
			}
			
			finish();
		}
		
		return true;
	}
	
	private void refreshUI() {
		LinearLayout layoutDueDate = (LinearLayout) findViewById(R.id.layoutRepeatStartDate);
		TextView textDueDate = (TextView) layoutDueDate.findViewById(R.id.txtSecondaryText);
		textDueDate.setText(CloudTodo.dateToString(recurrance.getStartDate()));
		
		layoutDueDate = (LinearLayout) findViewById(R.id.layoutRepeatEndDate);
		textDueDate = (TextView) layoutDueDate.findViewById(R.id.txtSecondaryText);
		textDueDate.setText(CloudTodo.dateToString(recurrance.getEndDate()));
		
		LinearLayout layoutFrequency = (LinearLayout) findViewById(R.id.layoutRepeatFrequency);
		TextView txtSecondaryText = (TextView) layoutFrequency.findViewById(R.id.txtSecondaryText);
		txtSecondaryText.setText(recurrance.getFrequency());
	}

	@Override
	public void OnMonthlyOptionSelected( MonthlyOptionDTO option ) {
		
		String displayText = "";
		if ( option.getSelectedOption() == MonthlyOptionDTO.MONTHLY_OPTION_DAY ) {
			displayText = "On day " + option.getDay();
		}
		else if ( option.getSelectedOption() == MonthlyOptionDTO.MONTHLY_OPTION_FIRST_DAY ) {
			displayText = "First day";
		}
		else if ( option.getSelectedOption() == MonthlyOptionDTO.MONTHLY_OPTION_LAST_DAY ) {
			displayText = "Last day";
		}
		LinearLayout layoutRepeatFrequencyMonthly = (LinearLayout) findViewById(R.id.layoutRepeatFrequencyMonthly);
		TextView txtSecondaryText = (TextView)layoutRepeatFrequencyMonthly.findViewById(R.id.txtSecondaryText);
		txtSecondaryText.setText( displayText );
		
	}

}
