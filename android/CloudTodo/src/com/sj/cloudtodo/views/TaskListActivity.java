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
import com.sj.cloudtodo.db.DataStore;
import com.sj.cloudtodo.model.Task;
import com.sj.cloudtodo.ui.adapters.TasksListAdapter;
import com.sj.cloudtodo.views.AddTaskDialog.AddTaskDialogListener;

public class TaskListActivity  extends FragmentActivity implements AddTaskDialogListener {
	
	private List<Task> tasks;
	private TasksListAdapter adapter ;
	private ListView list;
	
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
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loadTaskList();
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
	
	
	/*
	 * Loads tasks into the list based on the selected filter
	 */
	private void loadTaskList() {
		
		DataStore d = new DataStore(this);
		tasks.clear();
		
		String[] strings = getResources().getStringArray(R.array.action_list);
		
		int selectedFilter = getActionBar().getSelectedNavigationIndex();
		
		if ( "Today".equalsIgnoreCase(strings[selectedFilter]) ) {
			tasks.addAll(d.getTasksDueToday());
			tasks.addAll(d.getTasksOverDue());
		}
		else if ( "All".equalsIgnoreCase(strings[selectedFilter]) ) {
			tasks.addAll(d.getAllTasks());
		}
		else if ( "Tomorrow".equalsIgnoreCase(strings[selectedFilter]) ) {
			tasks.addAll(d.getTasksDueTomorrow());
		}
		else if ( "Unplanned".equalsIgnoreCase(strings[selectedFilter]) ) {
			tasks.addAll(d.getTasksDueTomorrow());
		}
		
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu_activity_task_list, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		
		switch ( item.getItemId()) {
		
		case R.id.menu_context_activity_tasklist_delete :
			DataStore d = new DataStore(this);
			Task task = (Task)list.getItemAtPosition(info.position);
			d.deleteTask(task);
			tasks.remove(task);
			adapter.notifyDataSetChanged();
			
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}
}
