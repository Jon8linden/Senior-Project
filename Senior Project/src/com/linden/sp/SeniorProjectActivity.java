package com.linden.sp;

import android.app.Activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SeniorProjectActivity extends Activity {
    /** Called when the activity is first created. */
    Splash animation;
    
    //set splash screen to on
	static boolean splashScreen = true;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		//Set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		//no notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//Set view splash.xml
		animation = new Splash(this);
		setContentView(animation);
		
		//MediaPlayer splashSound = MediaPlayer.create(this, R.raw.);
		//splashSound.start();

    }
	
}