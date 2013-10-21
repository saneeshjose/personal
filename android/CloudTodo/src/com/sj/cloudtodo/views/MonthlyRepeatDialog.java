package com.sj.cloudtodo.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.sj.cloudtodo.R;
import com.sj.cloudtodo.model.MonthlyOptionDTO;

public class MonthlyRepeatDialog extends DialogFragment {
	
	private MonthlyRepeatDialogListener listenerCallback;
	private View monthlydialogView;
	private MonthlyOptionDTO monthlyOption ;
	
	public interface MonthlyRepeatDialogListener {
		public void OnMonthlyOptionSelected( MonthlyOptionDTO option );
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try{
			listenerCallback = (MonthlyRepeatDialogListener) activity;
		}
		catch(ClassCastException e) {
			throw new ClassCastException ( activity.toString() +" must implement MonthlyRepeatDialogListener!");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		monthlyOption = new MonthlyOptionDTO();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Choose Days");
		builder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				boolean isOnDay = ((RadioButton) monthlydialogView.findViewById(R.id.rdOnDay)).isChecked();
				boolean isOnLastDay = ((RadioButton) monthlydialogView.findViewById(R.id.rdOnLastDay)).isChecked();
				boolean isOnFirstDay = ((RadioButton) monthlydialogView.findViewById(R.id.rdOnFirstDay)).isChecked();
				
				if ( isOnDay ) {
					monthlyOption.setSelectedOption(MonthlyOptionDTO.MONTHLY_OPTION_DAY);
					EditText editDay =  (EditText)  monthlydialogView.findViewById(R.id.editDay);
					monthlyOption.setDay(Integer.parseInt(editDay.getText().toString()));
				}
				else if ( isOnFirstDay ) {
					monthlyOption.setSelectedOption(MonthlyOptionDTO.MONTHLY_OPTION_FIRST_DAY);
				}
				else if ( isOnLastDay ) {
					monthlyOption.setSelectedOption(MonthlyOptionDTO.MONTHLY_OPTION_LAST_DAY);
				}
				
				listenerCallback.OnMonthlyOptionSelected( monthlyOption );
			}
		});
		
		builder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		monthlydialogView = inflater.inflate(R.layout.dialog_monthly_repeat, null);
		builder.setView(monthlydialogView);
		
		final RadioButton rd = (RadioButton ) monthlydialogView.findViewById(R.id.rdOnDay);
		rd.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRadioButtonClicked(rd);
			}
		});
		
		final RadioButton rdOnFirstDay= (RadioButton ) monthlydialogView.findViewById(R.id.rdOnFirstDay);
		rdOnFirstDay.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRadioButtonClicked(rdOnFirstDay);
			}
		});
		
		final RadioButton rdOnLastDay = (RadioButton ) monthlydialogView.findViewById(R.id.rdOnLastDay);
		rdOnLastDay.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRadioButtonClicked(rdOnLastDay);
			}
		});
		
		return builder.create();
	}
	
	public void onRadioButtonClicked( View v) {
		
		switch ( v.getId() ) {
		
		case R.id.rdOnFirstDay:
			((RadioButton) monthlydialogView.findViewById(R.id.rdOnDay)).setChecked(false);
			((RadioButton) monthlydialogView.findViewById(R.id.rdOnLastDay)).setChecked(false);
			break;
			
		case R.id.rdOnLastDay:
			((RadioButton) monthlydialogView.findViewById(R.id.rdOnDay)).setChecked(false);
			((RadioButton) monthlydialogView.findViewById(R.id.rdOnFirstDay)).setChecked(false);
			break;
		
		case R.id.rdOnDay:
			((RadioButton) monthlydialogView.findViewById(R.id.rdOnFirstDay)).setChecked(false);
			((RadioButton) monthlydialogView.findViewById(R.id.rdOnLastDay)).setChecked(false);
			
			break;
			
		}
	}

}
