package com.sj.cloudtodo.views;

import com.sj.cloudtodo.model.Recurrance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class RepeatFrequencyDialog extends DialogFragment {
	
	private RepeatFrequencyDialogListener listenerCallback;
	
	public interface RepeatFrequencyDialogListener {
		public void OnFrequencySelected( String frequency );
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try{
			listenerCallback = (RepeatFrequencyDialogListener) activity;
		}
		catch(ClassCastException e) {
			throw new ClassCastException ( activity.toString() +" must implement RepeatFrequencyDialogListener!");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Choose a frequency");
		final CharSequence[] items = new CharSequence[]{
											Recurrance.RECURRANCE_NEVER,
											Recurrance.RECURRANCE_DAILY, 
											Recurrance.RECURRANCE_WEEKLY, 
											Recurrance.RECURRANCE_MONTHLY, 
											Recurrance.RECURRANCE_YEARLY, 
										};
		
		builder.setItems(items, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listenerCallback.OnFrequencySelected(items[which].toString());
			}
		});
		
		AlertDialog dialog = builder.create();
		
		return dialog;
	}

}
