package com.linden.sp.game;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Engine extends Activity implements SensorEventListener, OnTouchListener{

	// main game view panel
	gameView gameView;
	
	//threads
	Thread engineThread;
		//add music thread
	
	//game looping speed
	final static int gameLoopSpeed = 5*Player.getSpeed();
	
	//start up game variables
	static Boolean surfaceCreated = false;
	private Boolean engineRunning = false;
	static int totalRunTime = 0;
	public static int level;
	public static int selectedCar;
	public static int difficulty;
	
	
	
	//gameOver
	private int gravityDirection;
	
	
	//Sensor management variables for accelerometer
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	
	
	public static float[] gravity = new float[3];
	public static float[] linear_acceleration = new float[3];
	
	public static int yDirection;
	
	
	public Engine(){
		super();
		//set intial game variables
		
	}
	
	public void runGame(){
		//check to see what to start
		//if music is off dont start
		//items
		//score and check if win condition
		totalRunTime();
		
		
	}
	
	
	private void totalRunTime() {
		totalRunTime++;
		//double actualTimeRunning = (double) (totalRunTime *gameSpeed)/1000;
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//no notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//get data from bundle
		
	    //keep phone from sleeping
	    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
	    
        //get data from bundle 
        Bundle bundle = getIntent().getExtras();
        //set to 0 so it does not effect survival mode 
        level = bundle.getInt("level",0);
        
        selectedCar = bundle.getInt("car",1);
        difficulty = bundle.getInt("difficulty",1);
        
		// Set to gameView
        gameView = new gameView(this);
        setContentView(gameView);
	    

       // boolean career = bundle.getBoolean("careerMode", true);
        
        //Log.d("Engine", "level " + level);
        
        //Determines mode
        //career mode

        //set up preferences 
        //preferences = PreferenceManager.getDefaultSharedPreferences(getBaseCon());
        
        
	    //Accelerometer Sensor
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    
        gravity[1] = 0;
        linear_acceleration[1] = 0;
        
        //sets where the listener is for touch (gameView)
        gameView.setOnTouchListener(this);
	    
	}


	private void setLevel(int level) {
		
		if (level == 1){
			//rabbit
			//Accelerometer modifier
			//game speed
			//point modifier
			//chance of items
			//chance of obstructions
			//finish condition score = # or timer
			
		}
		else if (level == 2){
			//truck
		}
		else if (level == 3){
			//bmw
		}
		else if (level == 4){
			//sti
		}
		
	}
	

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
		engineRunning = false;
	}

	@Override
	protected void onResume() {
		Log.d("Made it", "Engine onResume");
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
		
		//check to see if game ended
		
		//resume game and start engine thread
		engineRunning = true;
		engineThread.start();
	}

	//start engine
	@Override
	protected void onStart() {
		Log.d("Made it", "Engine onStart");
		super.onStart();
		//create thread
		
		engineThread = new Thread(new Runnable(){
			

			public void run() {
				while (engineRunning){
					runGame();
				}
				try{
					Thread.sleep(gameLoopSpeed);
				} catch (Throwable t){
					
				}
				
				
			}
			
			
		});
	}

	public static void surfaceCreated() {
		surfaceCreated = true;
		
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	public void onSensorChanged(SensorEvent event) {
 
		final float alpha = (float) 0.8;
		
        // Get display rotation
        Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        
        // Account for devices different default orientations
        if (rotation != 0) {
	        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
	        linear_acceleration[1] = event.values[1] - gravity[1];
        }
        else {
        	gravity[1] = alpha * gravity[1] + (1 - alpha) * (event.values[0] * -1);
	        linear_acceleration[1] = event.values[0] - gravity[1];
        }
        
        //Move player left/right based on the accelerometer
        if (surfaceCreated && engineRunning) {
        	
	        if (gravity[1] >= -1 && gravity[1] <= 1 && gravityDirection != 0) {
	        	gravityDirection = 0;
	        }
	        
	        else if (gravity[1] > 1 && gravityDirection != 1) {
	        	gravityDirection = 1;
	        }
	        
	        else if (gravity[1] < -1 && gravityDirection != -1) {
	        	gravityDirection = -1;
	        }
        }
	}
	
	//check for button press
	public boolean onTouch(View v, MotionEvent event) {
		int x = gameView.clickScreen(event.getX(), event.getY());
			//while (event.getAction() != MotionEvent.ACTION_UP) {
		
					//while (gameView.clickScreen(event.getX(), event.getY())) {
					if (x == 1){
						//move image up 
						//if (yDirection < getHeight){ do this} else {nothing}
						yDirection=yDirection-1;
						Log.d("Made it", ""+yDirection);
					}
					else if(x==-1){
						//move image down
						//if (yDirection > getHeight){ do this} else {nothing}
						yDirection=yDirection+1;
						
						Log.d("Made it", ""+yDirection);

					}
					else{
						yDirection=0;
					}

			//}
			return true;
			

	}
	
	
}
