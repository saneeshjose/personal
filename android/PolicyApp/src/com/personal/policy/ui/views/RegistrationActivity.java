package com.personal.policy.ui.views;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.personal.policy.ErrorMessage;
import com.personal.policy.R;
import com.personal.policy.ResponseMessage;
import com.personal.policy.net.NetUtils;
import com.personal.policy.net.PolicyJSONUtils;

public class RegistrationActivity extends Activity {
	
	private ProgressDialog dialog ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register);
		
		Button b = ( Button)  findViewById(R.id.btnSignUp);
		
		b.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				final String fullName = ((EditText) findViewById(R.id.txtRegisterName)).getText().toString();
				final String email = ((EditText) findViewById(R.id.txtRegisterEmail)).getText().toString();
				final String password = ((EditText) findViewById(R.id.txtRegisterPassword)).getText().toString();
				final String subscriberId = ((EditText) findViewById(R.id.txtRegisterSubsID)).getText().toString();
				final String ssn = ((EditText) findViewById(R.id.txtRegisterSSN)).getText().toString();
				
				dialog = ProgressDialog.show(RegistrationActivity.this, "", "Signing Up...");
				
				
				Thread t = new Thread() {
					
					@Override
					public void run() {
						
						try {
							String userkey = signUp(fullName, email, password,subscriberId,ssn);
							System.out.println("Logged in as user '"+userkey+"'");
							dialog.dismiss();

							Intent i = new Intent ( RegistrationActivity.this, HomeActivity.class);
							startActivity(i);
						}
						catch(Exception e) {
							dialog.dismiss();
							e.printStackTrace();
							final String err = e.getMessage();
							
							runOnUiThread( new Runnable () {
								public void run() {
									showAlert("Sign up failed... " + err);
								};
							});
							
						}
						
					}
					
				};
				t.start();
				
			}
		});
	}
	
	/*
	 * Sign up and return user id
	 */
	private String signUp( String fullname, String email, String password, String subscriberId, String ssn) throws Exception {
		
		NetUtils n = new NetUtils();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("fullname", fullname);
		params.put("email", email);
		params.put("password", password);
		params.put("subscriberId", subscriberId);
		params.put("ssn", ssn);
		
		params.put("action", "signup");
		
		String response = n.postAuthRequest(params);
		System.out.println(response);
		
		if ( PolicyJSONUtils.isJSONError(response) ) {
			ErrorMessage e = PolicyJSONUtils.parseError(response);
			throw new Exception (e.getError());
		} else {
			ResponseMessage m = PolicyJSONUtils.parseResponse(response);
			return m.getResponse();
		}
		
	}
	
	private void showAlert ( String text ) {
		Builder builder = new Builder(this);
		AlertDialog alert = builder.create();
		alert.setTitle("Alert");
		alert.setMessage(text);
		alert.show();
	}

}
