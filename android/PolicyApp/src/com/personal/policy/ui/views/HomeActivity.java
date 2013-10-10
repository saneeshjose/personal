package com.personal.policy.ui.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.personal.policy.R;
import com.personal.policy.ui.ImageTextViewAdapter;

public class HomeActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.home);
		
		GridView grid = (GridView) findViewById(R.id.homeGrid);
		
		final List<String> menu_items = new ArrayList<String>();
		menu_items.add( getString( R.string.home_my_policy ));
		menu_items.add( getString( R.string.home_my_claims ));
		menu_items.add( getString( R.string.home_find_provider ));
		menu_items.add( getString( R.string.home_contact));
		menu_items.add( getString( R.string.home_call));
		
		ImageTextViewAdapter adapter = new ImageTextViewAdapter(this, R.layout.home_grid_item, menu_items);
		grid.setAdapter( adapter);
		
		grid.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            handleSelection(menu_items.get(position));
	        }
	    });
	}
	
	private void handleSelection ( String selection ) {
		if ( getString(R.string.home_my_policy).equals(selection)) {
			Intent i = new Intent ( this, MyPolicyActivity.class);
			startActivity(i);
		}
		else if ( getString(R.string.home_my_claims).equals(selection)) {
			Intent i = new Intent ( this, MyClaimsActivity.class);
			startActivity(i);
		}
		else if ( getString(R.string.home_find_provider).equals(selection)) {
			Intent i = new Intent ( this, FindProviderActivity.class);
			startActivity(i);
		}
		else if ( getString(R.string.home_contact).equals(selection)) {
			email();
		}
		else if ( getString(R.string.home_call).equals(selection)) {
			initiateCall();
		}
	}
	
	private void initiateCall() {
		Intent myintent = new Intent(Intent.ACTION_CALL);
		myintent.setData(Uri.parse("tel:" + getString(R.string.home_call_number)));
		myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(myintent);
	}
	
	private void email() {
		/* Create the Intent */
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

		/* Fill it with Data */
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"wellpoint_mobile@wellpoint.com"});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Email from mobile app");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

		/* Send it off to the Activity-Chooser */
		startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	}
}
