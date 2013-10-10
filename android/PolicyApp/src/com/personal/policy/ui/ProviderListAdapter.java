package com.personal.policy.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.personal.policy.Provider;
import com.personal.policy.R;

public class ProviderListAdapter extends ArrayAdapter<Provider> {
	
	public ProviderListAdapter(Context context, int textViewResourceId,
			List<Provider> objects) {
		super(context, textViewResourceId, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = convertView;
		
		if ( v == null ) v = inflator.inflate(R.layout.provider_list_item, null);
		
		TextView txtName = (TextView)v.findViewById(R.id.txt_provider_name);
		txtName.setText( getItem(position).getName() );
		
		
		TextView txtAddress = (TextView)v.findViewById(R.id.txt_provider_address);
		txtAddress.setText(getItem(position).getAddress());
		
		TextView txtPhone = (TextView)v.findViewById(R.id.txt_provider_phone);
		txtPhone.setText(getItem(position).getPhone());
		
		
		return v;
		
	}
	
}
