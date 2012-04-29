package com.linden.sp;

import com.linden.sp.game.Engine;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Finish extends Activity{
	
	boolean win;
	boolean careerTime;
	int score;
	int carsHit;
	int level;
	int LevelGoalCars;
	int timeTaken;
	int levelGoalTime;
	int difficulty;
	//boolean survival;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//display finish.xml 
		setContentView(R.layout.finish);
		
		//get data from bundle
		Bundle bundle = getIntent().getExtras();
		win = bundle.getBoolean("won", false);
		careerTime = bundle.getBoolean("careerTime", false);
		timeTaken = bundle.getInt("time", 0);
		carsHit=bundle.getInt("carsHit", 0);
		LevelGoalCars = bundle.getInt("levelGoalCars",0);
		level = bundle.getInt("level",0);
		score = bundle.getInt("score",0);
		difficulty = bundle.getInt("difficulty", 0);
		
		View viewWon = (View) findViewById (R.id.viewWon);
    	View viewLost = (View) findViewById (R.id.viewLost);
    	View viewSurvial = (View) findViewById(R.id.viewSurvival);
    	TextView titleText = (TextView) findViewById (R.id.txtfinishedTitle);
    	
    	TextView scoreText = (TextView) findViewById (R.id.txtScoreNum);
		
    	scoreText.setText(""+score);
    	
    	//survival view
		if(difficulty>0){
			viewSurvial.setVisibility(View.VISIBLE);
			viewLost.setVisibility(View.GONE);
			viewWon.setVisibility(View.GONE);
			
			titleText.setText("You Died!");
			
    	}
		//Career Win
			
    	else if(win){
			viewWon.setVisibility(View.VISIBLE);
			viewLost.setVisibility(View.GONE);
			viewSurvial.setVisibility(View.GONE);
			
	    	// Set the title's text
	    	titleText.setText("You Won!");
		}
		//Career loose
	    else {
	    	// Show the right view
	    	viewWon.setVisibility(View.GONE);
	    	viewLost.setVisibility(View.VISIBLE);
	    	viewSurvial.setVisibility(View.GONE);
	    	
	    	// Set the title's text
	    	titleText.setText("You Lost");
	    }
	    
		Button btn_NextLevel = (Button) findViewById (R.id.btnNextLvl);
		btn_NextLevel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Create new intent
	        	Intent intent = new Intent(Finish.this, Engine.class);
	        	
	        	// Create new bundle
	            Bundle bundle = new Bundle();
	            bundle.putInt("level", level);
	            bundle.putBoolean("career", true);
	            // Add the level data to the bundle and add the bundle to the intent
	            intent.putExtras(bundle);
	            
	            // Start the actual activity
	            startActivity(intent);

				// Kill this activity
				finish();
			}
		});
		//play again
		Button btn_Again = (Button) findViewById (R.id.btnAgain);
		btn_Again.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Create new intent
	        	Intent intent = new Intent(Finish.this, Engine.class);
	        	
	        	// Create new bundle
	            Bundle bundle = new Bundle();
	            
	            // add data
	            bundle.putInt("level", level);
	            bundle.putBoolean("survival", true);
	            bundle.putInt("difficulty", difficulty);
	            intent.putExtras(bundle);
	            
	            // Start the actual activity
	            startActivity(intent);

				// Kill this activity
				finish();
			}
		});
		
		
		//Retry button
		Button btn_Retry = (Button) findViewById (R.id.btnRetry);
		btn_Retry.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Create new intent
	        	Intent intent = new Intent(Finish.this, Engine.class);
	        	
	        	// Create new bundle
	            Bundle bundle = new Bundle();
	            
	            // Add the level data to the bundle and add the bundle to the intent
	    		bundle.putInt("level", level-1);
	    		bundle.putBoolean("career", true);
	            intent.putExtras(bundle);
	            
	            // Start the actual activity
	            startActivity(intent);

				// Kill this activity
				finish();
			}
		});
		
		
		// Level select button
		Button btn_MainMenu = (Button) findViewById (R.id.btnQuit);
		btn_MainMenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Kill this activity
				finish();
			}
		});
		
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}


}
