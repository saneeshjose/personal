package com.sj.cloudtodo.views;

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
import android.widget.TextView;

import com.sj.cloudtodo.R;
import com.sj.cloudtodo.db.DataStore;
import com.sj.cloudtodo.model.User;
import com.sj.cloudtodo.net.NetUtils;

public class LoginActivity extends Activity {
	
	private ProgressDialog dialog ;
	
	private NetUtils utils = new NetUtils();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);
		
		Button btnSignIn = (Button) findViewById(R.id.btnLogin);
		btnSignIn.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String username = ((EditText) findViewById(R.id.txtUser)).getText().toString();
				final String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();
				dialog = ProgressDialog.show(LoginActivity.this, "", "Signing In...");
				
				Thread t = new Thread() {
					@Override
					public void run() {
						try {
							String userId = authenticate(username, password);
							
							runOnUiThread( new Runnable () {
								public void run() {
									dialog.setMessage("Loading user information...");
								};
							});
							
							User user = utils.getUserInfo(userId);
							DataStore d = new DataStore(LoginActivity.this);
							d.saveUserId(userId);
							d.saveUserInfo(user);
							
							dialog.dismiss();

							Intent i = new Intent ( LoginActivity.this, HomeActivity.class);
							startActivity(i);
							LoginActivity.this.finish();
						}
						catch(Exception e) {
							e.printStackTrace();
							dialog.dismiss();
							System.out.println("Sign in failed");
							
							runOnUiThread( new Runnable () {
								public void run() {
									showAlert("Signin failed...");
								};
							});
							
						}
					}
				};
				
				t.start();
			}
		});
		
		TextView txtRegister = (TextView) findViewById(R.id.txtRegister);
		txtRegister.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
				startActivity(i);
				
			}
		});
	}
	
	private String authenticate ( String email, String password ) throws Exception {
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("action", "signin");
		parameters.put("email", email);
		parameters.put("password", password);
		
		String response = utils.postAuthRequest(parameters);
		return response;
	}
	
	private void showAlert ( String text ) {
		Builder builder = new Builder(this);
		AlertDialog alert = builder.create();
		alert.setTitle("Alert");
		alert.setMessage(text);
		alert.show();
	}

}
