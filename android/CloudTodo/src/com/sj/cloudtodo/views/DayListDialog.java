package com.sj.cloudtodo.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;

public class DayListDialog extends DialogFragment {
	
	private DayListDialogListener listenerCallback;
	
	public interface DayListDialogListener {
		public void OnDayListSelected( String []days );
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try{
			listenerCallback = (DayListDialogListener) activity;
		}
		catch(ClassCastException e) {
			throw new ClassCastException ( activity.toString() +" must implement DayListDialogListener!");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Choose Days");
		builder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listenerCallback.OnDayListSelected(new String[]{});
			}
		});
		
		builder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		final CharSequence[] items = new CharSequence[]{"Monday","Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// TODO Auto-generated method stub
			}
		});
		
		
		AlertDialog dialog = builder.create();
		
		return dialog;
	}

}
