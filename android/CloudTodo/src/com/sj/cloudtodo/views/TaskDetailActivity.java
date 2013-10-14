package com.sj.cloudtodo.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sj.cloudtodo.R;
import com.sj.cloudtodo.common.CloudTodo;
import com.sj.cloudtodo.db.DataStore;
import com.sj.cloudtodo.model.Task;

public class TaskDetailActivity extends FragmentActivity implements OnDateSetListener {
	
	private Task task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_task_detail);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			task = (Task) extras.get("task");
			if (task == null)
				return;
			
			TextView txtTask = (TextView ) findViewById(R.id.editTask);
			txtTask.setText(task.getTask());
		}
		else
			Log.e("CloudTodo", "No extras..");
		
		LinearLayout layoutDueDate = (LinearLayout) findViewById(R.id.layoutDueDate);
		if ( task.getDueDate()!=null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			TextView textDueDate = (TextView) layoutDueDate.findViewById(R.id.txtSecondaryText);
			textDueDate.setText(dateFormat.format(task.getDueDate()) );
		}
		
		layoutDueDate.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Date today = new Date();
				
				DatePickerDialog d = new DatePickerDialog(TaskDetailActivity.this, TaskDetailActivity.this, 1900+ today.getYear(), today.getMonth(), today.getDate());
				d.show();
			}
		});
		
		LinearLayout layoutRepeats = (LinearLayout) findViewById(R.id.layoutRepeats);
		layoutRepeats.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent ( TaskDetailActivity.this, RepeatActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_task_detail, menu);
        return true;
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		LinearLayout layoutDueDate = (LinearLayout) findViewById(R.id.layoutDueDate);
		TextView textDueDate = (TextView) layoutDueDate.findViewById(R.id.txtSecondaryText);
		String date = String.format("%d-%02d-%02d",year,  (1+monthOfYear), dayOfMonth );
		textDueDate.setText(date);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if ( item.getItemId() == R.id.menu_activity_task_detail_save ) {
			
			EditText textTask = (EditText) findViewById(R.id.editTask);
			task.setTask(textTask.getText().toString());
			
			LinearLayout layoutDueDate = (LinearLayout) findViewById(R.id.layoutDueDate);
			TextView textDueDate = (TextView) layoutDueDate.findViewById(R.id.txtSecondaryText);
			
			try{
				Date date = CloudTodo.stringToDate(textDueDate.getText().toString());
				task.setDueDate(date);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			DataStore dataStore = new DataStore(this);
			dataStore.updateTask(task);
			finish();
		}
		else if ( item.getItemId() == R.id.menu_activity_task_detail_delete ) {
			DataStore dataStore = new DataStore(this);
			dataStore.deleteTask(task);
			finish();
		}
		return true;
		
	}
	
}
