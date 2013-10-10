package com.personal.policy.ui.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.personal.policy.Policy;
import com.personal.policy.R;
import com.personal.policy.User;
import com.personal.policy.db.DataStore;
import com.personal.policy.net.NetUtils;
import com.personal.policy.ui.KeyValueListAdapter;
import com.personal.policy.ui.KeyValueListItem;

public class MyClaimsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.myclaims);
		
		final NetUtils net = new NetUtils();
		
		Policy p=null;
		try {
			List<Policy> policies = net.getPolicies(new DataStore(this).getUserId());
			
			p = policies.get(0);
		}
		catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		ListView list = (ListView) findViewById(R.id.dependent_list);
		
		List<KeyValueListItem> dependents = new ArrayList<KeyValueListItem>();
		
		DataStore dataStore = new DataStore( this );
		User user = dataStore.getUserInfo();
		dependents.add(new KeyValueListItem(user.getName(), ">>"));
		
		for ( String dependent : p.getDependents()) {
			dependents.add( new KeyValueListItem(dependent, ">>"));
		}
		
		KeyValueListAdapter adapter = new KeyValueListAdapter(this, R.layout.mypolicy_list_item, dependents);
		list.setAdapter( adapter );
		
		list.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				TextView txtDependent = (TextView) view.findViewById(R.id.txtLabel);
				Intent i = new Intent(MyClaimsActivity.this, ClaimDetailActivity.class);
				i.putExtra("dependent", txtDependent.getText());
				startActivity(i);
			}
		});
	}

}
