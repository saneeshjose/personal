package com.personal.policy.ui;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.personal.policy.Claim;
import com.personal.policy.R;

public class ClaimDetailListAdapter extends ArrayAdapter<Claim> {
	
	public ClaimDetailListAdapter(Context context, int textViewResourceId,
			List<Claim> objects) {
		super(context, textViewResourceId, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = convertView;
		
		if ( v == null ) v = inflator.inflate(R.layout.claim_detail_list_item, null);
		
		TextView txtDate = (TextView)v.findViewById(R.id.txt_claim_Date);
		Date claimDate = getItem(position).getClaimDate();
		String dateString = (claimDate.getMonth()+1)+"/"+claimDate.getDate()+"/"+(claimDate.getYear()+1900);
		txtDate.setText( dateString );
		
		
		TextView txtAmount = (TextView)v.findViewById(R.id.txt_claim_amount);
		txtAmount.setText(getItem(position).getAmount()+"");
		
		TextView txtStatus = (TextView)v.findViewById(R.id.txt_claim_status);
		txtStatus.setText(getItem(position).getClaimStatus());
		
		TextView txtApproved = (TextView)v.findViewById(R.id.txt_claim_approved_amount);
		txtApproved.setText( getItem(position).getApprovedAmount()+"");
		
		return v;
		
	}
	
}
