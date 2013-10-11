package com.sj.cloudtodo.ui.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sj.cloudtodo.R;
import com.sj.cloudtodo.db.DataStore;
import com.sj.cloudtodo.model.Task;

public class TasksListAdapter extends ArrayAdapter<Task> {
	
	public TasksListAdapter(Context context, int textViewResourceId,
			List<Task> objects) {
		super(context, textViewResourceId, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = convertView;
		final int p = position;
		
		if ( v == null ) v = inflator.inflate(R.layout.list_item_tasks, null);
		
		final Task task = getItem(position);
		
		TextView txtName = (TextView)v.findViewById(R.id.text_list_item_tasks);
		txtName.setText( task.getTask() );
		
		if ( task.isOverDue() ) {
			txtName.setTextColor(Color.RED);
		}
		else
			txtName.setTextColor(Color.WHITE);
		
		final CheckBox checkCompleted = (CheckBox) v.findViewById(R.id.check_task_list_item);
		checkCompleted.setChecked(task.isComplete());		
		checkCompleted.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				DataStore dataStore = new DataStore(getContext());
				if ( checkCompleted.isChecked() ) 
					task.setStatus(Task.STATUS_COMPLETE);
				else
					task.setStatus(Task.STATUS_INCOMPLETE);
				
				dataStore.updateTask(task);
			}
		});
		
//		ImageView imgDelete = (ImageView) v.findViewById(android.R.id.img_list_item_tasks_delete);
//		imgDelete.setOnClickListener( new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				TasksListAdapter.this.remove(TasksListAdapter.this.getItem(p));
//				notifyDataSetChanged();
//			}
//		});
		
		return v;
		
	}
	
}
