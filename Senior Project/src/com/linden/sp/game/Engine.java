package com.linden.sp.game;

import android.app.Activity;
import android.os.Bundle;

public class Engine extends Activity{

	// Declare main panel
	gameView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// Set to gameView
        view = new gameView(this);
        setContentView(view);
	}

}
