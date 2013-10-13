package com.sj.cloudtodo.views;

import android.support.v4.app.DialogFragment;

public interface DialogListener {
	
	public void onDialogNegativeClick(DialogFragment dialog);
    public void onDialogPositiveClick(DialogFragment dialog);
}
