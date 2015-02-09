package com.mtoo.bbmath.activities;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.mtoo.bbmath.R;
import com.mtoo.bbmath.helpers.DatabaseHandler;


public class Login extends ActionBarActivity {

	private Spinner sp;
	private DatabaseHandler db;
	private List<UserWithId> spinnerList = new ArrayList<UserWithId>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// ZeroEntry in spinner has to be Add New Uwer text.
		String ZeroEntry = getString(R.string.login_AddNewUser_prompt);
		String[] temp_users = {ZeroEntry, "Adam", "Andrew", "Ben",
				"Howard", "Jane", "Kalis", "Ramelyn", "Weston", "Zach"};

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		sp = (Spinner) findViewById(R.id.sp_user_name);
		
		// Initializing database and loading users list
		db = new DatabaseHandler(getApplicationContext());
		// Create an ArrayAdapter using the data.
		UserWithId addNew= new UserWithId();
		addNew.setName("Add new user");
		addNew.setId(0);
		spinnerList.add(addNew);
		List<UserWithId> allUsersFromDb=db.getAllUsers();
		sort(allUsersFromDb);
		spinnerList.addAll(allUsersFromDb);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.login_spinner_selected_text, getNames(spinnerList)); // ToDo: Load from database cursor

		// Specify the layout to use when the list of choices appears.
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner.
		sp.setAdapter(adapter);
		sp.setSelection(searchIdPosition(spinnerList,db.getLastUserId()));    // ToDo: Set to previous user from database
		sp.setClickable(false);
		sp.setOnTouchListener(new OnTouchListener(){
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	        	if(sp.getSelectedItemPosition()==0){
	    			Intent intent = new Intent(Login.this, AddUser.class);
	    			startActivity(intent);
	    		} else {
	    			UserWithId selectedUser =  spinnerList.get(sp.getSelectedItemPosition());
	    			db.setLastUserId(selectedUser.getId());
	    			Intent intent = new Intent(Login.this, ActivitySelect.class);
	    			intent.putExtra("username", selectedUser.getName());
	    			startActivity(intent);
	    		}
	    	    return false;
	        }
	    });
		ImageButton ib = (ImageButton) findViewById(R.id.ib_user_drop);
		ib.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        sp.performClick();
		    }
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void sort(List<UserWithId> list){
		Collections.sort(list,new Comparator<UserWithId>(){
			   @Override
			   public int compare(final UserWithId object1,UserWithId object2) {
				   return  object1.getName().compareTo(object2.getName());
			     }
			 });
	}
	
	private List<String> getNames(List<UserWithId> list){
		List<String> names=new ArrayList<String>();
		for(UserWithId user:list) names.add(user.getName());
		return names;
	}
	
	private int searchIdPosition(List<UserWithId> list, int id){
		int found=0;
		for(UserWithId user:list) if(user.getId()==id) found=list.indexOf(user);
		return found;
	}
}
