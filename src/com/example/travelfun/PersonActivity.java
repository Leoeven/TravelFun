package com.example.travelfun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class PersonActivity extends Activity {
	
    private LinearLayout bar_home,bar_add,bar_person;
    private Button editBtn,exitBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		
		bar_home = (LinearLayout)findViewById(R.id.bar_home);
		bar_add = (LinearLayout)findViewById(R.id.bar_add);
		bar_person = (LinearLayout)findViewById(R.id.bar_person);
		
		bar_home.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonActivity.this,HomeActivity.class);
				startActivity(intent);
			}
			
		});
		bar_add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonActivity.this,AddActivity.class);
				startActivity(intent);
			}
			
		});
		bar_person.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonActivity.this,PersonActivity.class);
				startActivity(intent);
			}
			
		});
		
		editBtn = (Button)findViewById(R.id.editBtn);
		exitBtn = (Button)findViewById(R.id.exitBtn);
		
		editBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonActivity.this,PersonEditActivity.class);
                startActivity(intent);				
			}
			
		});
		
		exitBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonActivity.this,MainActivity.class);
                startActivity(intent);				
			}
			
		});
		
	}

}