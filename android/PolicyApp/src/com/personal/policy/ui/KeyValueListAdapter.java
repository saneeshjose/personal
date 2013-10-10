package com.personal.policy.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.personal.policy.R;

public class KeyValueListAdapter extends ArrayAdapter<KeyValueListItem> {
	
	public KeyValueListAdapter(Context context, int textViewResourceId,
			List<KeyValueListItem> objects) {
		super(context, textViewResourceId, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = convertView;
		
		if ( v == null ) v = inflator.inflate(R.layout.mypolicy_list_item, null);
		
		TextView txtLabel = (TextView)v.findViewById(R.id.txtLabel);
		txtLabel.setText(getItem(position).getKey());
		
		TextView txtValue = (TextView)v.findViewById(R.id.txtValue);
		txtValue.setText(getItem(position).getValue());
		
		return v;
		
	}
	
}
