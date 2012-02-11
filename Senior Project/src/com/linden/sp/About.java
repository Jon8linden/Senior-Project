package com.linden.sp;

import com.linden.sp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class About extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//display about.xml 
		setContentView(R.layout.about);
	}
	
}
