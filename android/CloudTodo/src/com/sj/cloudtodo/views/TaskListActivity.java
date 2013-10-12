package com.sj.cloudtodo.views;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.sj.cloudtodo.R;
import com.sj.cloudtodo.common.CloudTodo;
import com.sj.cloudtodo.db.DataStore;
import com.sj.cloudtodo.model.Task;
import com.sj.cloudtodo.ui.adapters.TasksListAdapter;
import com.sj.cloudtodo.views.AddTaskDialog.AddTaskDialogListener;

public class TaskListActivity  extends FragmentActivity implements AddTaskDialogListener {
	
	private TasksListAdapter adapter ;
	private final String FILTER_ALL = "All";
	private final String FILTER_TODAY = "Today";
	
	private final String FILTER_TOMORROW = "Tomorrow";
	private ListView list;
	private List<Task> tasks;
	
	private void addNewTask ( String taskName ) {
		Task task = new Task();
		task.setTask(taskName);
		
		Date dueDate = new Date();
		
		if ( FILTER_TOMORROW.equalsIgnoreCase( getActiveFilter())) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dueDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			dueDate = cal.getTime();
		}
		
		task.setDueDate(dueDate);
		DataStore d = new DataStore(this);
		d.saveTask(task);
		
		tasks.add(task);
		adapter.notifyDataSetChanged();
	}
	
	private void deleteTask( Task task ) {
		
		DataStore d = new DataStore(this);
		d.deleteTask(task);
		tasks.remove(task);
		adapter.notifyDataSetChanged();
	}
	
	private String getActiveFilter() {
		String[] strings = getResources().getStringArray(R.array.action_list);
		int selectedFilter = getActionBar().getSelectedNavigationIndex();
		return strings[selectedFilter];
	}
	
	/*
	 * Loads tasks into the list based on the selected filter
	 */
	private void loadTaskList() {
		
		DataStore d = new DataStore(this);
		tasks.clear();
		
		String activeFilter = getActiveFilter();
		
		if ( FILTER_TODAY.equalsIgnoreCase(activeFilter) ) {
			tasks.addAll(d.getTasksDueToday());
			tasks.addAll(d.getTasksOverDue());
		}
		else if ( FILTER_ALL.equalsIgnoreCase(activeFilter) ) {
			tasks.addAll(d.getAllTasks());
		}
		else if ( FILTER_TOMORROW.equalsIgnoreCase(activeFilter) ) {
			tasks.addAll(d.getTasksDueTomorrow());
		}
		else {
			Log.e(CloudTodo.tag, "Unknown filter selected!");
		}
		
		adapter.notifyDataSetChanged();
	}
	
	private void moveToTomorrow( Task task ) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		task.setDueDate(cal.getTime());
		
		DataStore d = new DataStore(this);
		d.updateTask(task);
		
		tasks.remove(task);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		Task task = (Task)list.getItemAtPosition(info.position);
		
		switch ( item.getItemId()) {
		
		case R.id.menu_context_activity_tasklist_delete :
			deleteTask(task);
			return true;
			
		case R.id.menu_context_activity_tasklist_tomorrow :
			moveToTomorrow(task);
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tasks = new ArrayList<Task>();
		adapter = new TasksListAdapter(this, R.layout.list_item_tasks, tasks);
		
		setContentView(R.layout.activity_task_list);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		list = (ListView) findViewById(R.id.list_tasks);
        list.setAdapter( adapter );
        registerForContextMenu(list);
        
        list.setOnItemClickListener( new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position,
        			long id) {
        		Task t = (Task)parent.getItemAtPosition(position);
        		
        		Intent i = new Intent(TaskListActivity.this,
        				TaskDetailActivity.class);
				i.putExtra("task", t);

				startActivity(i);
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
        	
        	@Override
        	public boolean onNavigationItemSelected(int itemPosition,
        			long itemId) {
        		loadTaskList();
        		return true;
        	}
        };
        
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, callback);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu_activity_task_list, menu);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_task_list, menu);
        return true;
	}
	
	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
	
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		String taskName = ((AddTaskDialog) dialog).getTaskName();
		addNewTask(taskName);
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
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loadTaskList();
	}
}
