package com.linden.sp;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;
import android.view.WindowManager;

public class Settings extends PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
				
		//no notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
		
		//set view to settings
		addPreferencesFromResource(R.xml.prefs);
	}

}
