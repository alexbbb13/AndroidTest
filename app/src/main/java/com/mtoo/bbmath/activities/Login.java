package com.mtoo.bbmath.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.mtoo.bbmath.R;

public class Login extends ActionBarActivity {

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

		Spinner sp = (Spinner) findViewById(R.id.sp_user_name);
		// Create an ArrayAdapter using the data.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.login_spinner_selected_text, temp_users); // ToDo: Load from database cursor

		// Specify the layout to use when the list of choices appears.
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner.
		sp.setAdapter(adapter);
		sp.setSelection(4);                            // ToDo: Set to previous user from database
		ImageButton ib = (ImageButton) findViewById(R.id.ib_user_drop);

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

}
