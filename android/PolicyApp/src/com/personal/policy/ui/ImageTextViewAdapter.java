package com.personal.policy.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.personal.policy.R;

public class ImageTextViewAdapter extends ArrayAdapter<String> {
	
	public ImageTextViewAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = convertView;
		
		if ( v == null ) v = inflator.inflate(R.layout.home_grid_item, null);
		
		String label = getItem(position);
		TextView txtStock = (TextView)v.findViewById(R.id.txtLabel);
		txtStock.setText(label);
		
		ImageView img = (ImageView ) v.findViewById(R.id.imgBtn);
		if ( label.equals( getContext().getString(R.string.home_my_policy) ) ) {
			img.setImageResource(R.drawable.form_green_edit);
		}
		else if ( label.equals( getContext().getString(R.string.home_my_claims) ) ) {
			img.setImageResource(R.drawable.dollar_currency_sign);
		}
		else if ( label.equals( getContext().getString(R.string.home_find_provider) ) ) {
			img.setImageResource(R.drawable.search);
		}
		else if ( label.equals( getContext().getString(R.string.home_contact) ) ) {
			img.setImageResource(R.drawable.contact_us);
		}
		else if ( label.equals( getContext().getString(R.string.home_about) ) ) {
			img.setImageResource(R.drawable.icon);
		}
		else if ( label.equals( getContext().getString(R.string.home_call) ) ) {
			img.setImageResource(R.drawable.call_us);
		}
		
		return v;
		
	}
	
}
