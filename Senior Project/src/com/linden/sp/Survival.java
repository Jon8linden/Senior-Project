package com.linden.sp;

import com.linden.sp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Survival extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//no notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//set view CarSelect.xml
		setContentView(R.layout.survival);
		
		//Easy
		Button btnEasy = (Button) findViewById (R.id.btnEasy);
		btnEasy.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//startActivity(new Intent("android.intent.action.CARSELECT"));
				Intent intent = new Intent("android.intent.action.CARSELECT");
				Bundle bundle = new Bundle();
				
				bundle.putInt("survival", 1);
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		});
		
		
		//Medium
		Button btnMedium = (Button) findViewById (R.id.btnMedium);
		btnMedium.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent("android.intent.action.CARSELECT");
				Bundle bundle = new Bundle();
				
				bundle.putInt("survival", 2);
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		});		
		//Hard
		Button btnHard = (Button) findViewById (R.id.btnHard);
		btnHard.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent("android.intent.action.CARSELECT");
				Bundle bundle = new Bundle();
				
				bundle.putInt("survival", 3);
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		});		
	
		
	}

}
