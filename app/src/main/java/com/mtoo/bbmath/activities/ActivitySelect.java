package com.mtoo.bbmath.activities;


import com.mtoo.bbmath.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;



public class ActivitySelect extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
		Bundle bundle = getIntent().getExtras();
		 if(bundle.getString("username")!= null) Toast.makeText(this, "Selected User "+bundle.getString("username"), Toast.LENGTH_SHORT).show();
	}

}
