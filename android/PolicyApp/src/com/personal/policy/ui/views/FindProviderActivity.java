package com.personal.policy.ui.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.personal.policy.Provider;
import com.personal.policy.R;
import com.personal.policy.net.NetUtils;
import com.personal.policy.ui.ProviderListAdapter;

public class FindProviderActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.find_provider);
		
		final List<Provider> providers = new ArrayList<Provider>();
		final ProviderListAdapter adapter = new ProviderListAdapter(this, R.layout.provider_list_item, providers );
		final ListView list = (ListView) findViewById(R.id.provider_list);
		list.setAdapter(adapter);
		
		Button b = (Button) findViewById(R.id.btnSearch);
		
		b.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText txtProviderName = (EditText) findViewById(R.id.provider_name);
				EditText txtProviderLocation = (EditText) findViewById(R.id.provider_location);
				
				String name = txtProviderName.getText().toString();
				String location = txtProviderLocation.getText().toString();
				
				try {
					providers.clear();
					providers.addAll( new NetUtils().getProviders(name, location));
					adapter.notifyDataSetChanged();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
				
		
		
	}

}
