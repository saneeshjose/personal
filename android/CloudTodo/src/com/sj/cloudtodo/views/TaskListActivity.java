package com.sj.cloudtodo.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.sj.cloudtodo.R;
import com.sj.cloudtodo.db.DataStore;
import com.sj.cloudtodo.model.Task;
import com.sj.cloudtodo.ui.adapters.TasksListAdapter;
import com.sj.cloudtodo.views.AddTaskDialog.AddTaskDialogListener;

public class TaskListActivity  extends FragmentActivity implements AddTaskDialogListener {
	
	private List<Task> tasks;
	private TasksListAdapter adapter ;
	
	private int REQUEST_MODIFY_TASK_DETAIL = 1001;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tasks = new ArrayList<Task>();
		adapter = new TasksListAdapter(this, R.layout.list_item_tasks, tasks);
		
		setContentView(R.layout.activity_task_list);
		
		ListView list = (ListView) findViewById(R.id.list_tasks);
        list.setAdapter( adapter );
        
        list.setOnItemClickListener( new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position,
        			long id) {
        		Task t = (Task)parent.getItemAtPosition(position);
        		
        		Intent i = new Intent(TaskListActivity.this,
        				TaskDetailActivity.class);
				i.putExtra("task", t);

				startActivityForResult( i, REQUEST_MODIFY_TASK_DETAIL);
  
        	}
		});
        
        list.setOnLongClickListener( new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				return false;
			}
		});
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_list,
                android.R.layout.simple_spinner_dropdown_item);
        
        OnNavigationListener callback = new ActionBar.OnNavigationListener() {
        	
        	String[] strings = getResources().getStringArray(R.array.action_list);
        	
        	@Override
        	public boolean onNavigationItemSelected(int itemPosition,
        			long itemId) {
        		if ( "All".equalsIgnoreCase(strings[itemPosition]) ) {
        			loadTasksAll();
        		}
        		else if ( "Today".equalsIgnoreCase(strings[itemPosition]) ) {
        			loadTasksToday();
        		}
        		else if ( "Tomorrow".equalsIgnoreCase(strings[itemPosition]) ) {
        			loadTasksTomorrow();
        		}
        		return true;
        	}
        };
        
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, callback);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		//TODO?
		loadTasksToday();
//		ActionBar actionBar = getActionBar();
//		System.out.println(actionBar.getSelectedNavigationIndex());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_task_list, menu);
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if ( item.getItemId() == R.id.menu_activity_tasks_add ) {

			DialogFragment newFragment = new AddTaskDialog();
			newFragment.show(getSupportFragmentManager(), "TaskList");
			
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
	
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		String taskName = ((AddTaskDialog) dialog).getTaskName();
		Task task = new Task();
		task.setTask(taskName);
		task.setDueDate(new Date());
		tasks.add(task);
		
		DataStore d = new DataStore(this);
		d.saveTask(task);
		
		adapter.notifyDataSetChanged();
	}
	
	private void loadTasksToday() {
		DataStore d = new DataStore(this);
		tasks.clear();
		tasks.addAll(d.getTasksDueToday());
		adapter.notifyDataSetChanged();
	}
	
	private void loadTasksAll() {
		DataStore d = new DataStore(this);
		tasks.clear();
		tasks.addAll(d.getAllTasks());
		adapter.notifyDataSetChanged();
	}
	
	private void loadTasksTomorrow() {
		DataStore d = new DataStore(this);
		tasks.clear();
		tasks.addAll(d.getTasksDueTomorrow());
		adapter.notifyDataSetChanged();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if ( requestCode == REQUEST_MODIFY_TASK_DETAIL ) {
			if ( resultCode == RESULT_OK ) {
				DataStore d = new DataStore(this);
				Task task = (Task ) data.getExtras().get("task");
				d.updateTask(task);
			}
		}
	}
}
