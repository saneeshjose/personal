package com.sj.cloudtodo.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.sj.cloudtodo.R;

public class AddTaskDialog extends DialogFragment {
	
	private DialogListener mListener;
	
	private String taskName;
	
	public String getTaskName() {
		return taskName;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddTaskDialogListener");
        }
    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle(R.string.str_add_task_dialog_title);
		builder.setPositiveButton(R.string.str_add_task_dialog_ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText edit = (EditText) AddTaskDialog.this.getDialog().findViewById(R.id.edit_add_task_dialog_task);
				if ( edit == null ) {
					Log.e("Dialog", "edit_add_task_dialog_task is not found!");
					return;
				}
				taskName = edit.getText().toString();
				AddTaskDialog.this.getDialog().dismiss();
				mListener.onDialogPositiveClick(AddTaskDialog.this);
			}
		});
		
		builder.setNegativeButton(R.string.str_add_task_dialog_cancel, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.onDialogNegativeClick( AddTaskDialog.this );
				AddTaskDialog.this.getDialog().cancel();
			}
		});
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.dialog_add_task, null));
		return builder.create();
	}
}
