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

import java.util.Iterator;
import java.util.Random;


public class Engine extends Activity implements SensorEventListener, OnTouchListener{

	// main game view panel
	gameView gameView;
	
	//threads
	Thread engineThread;
		//add music thread
	
	//game looping speed
	final static int gameLoopSpeed = 20;
	
	//start up game variables
	static Boolean surfaceCreated = false;
	private Boolean engineRunning = false;
	
	static int totalRunTime = 0;
	private int lastObstacleTime = 0;
	private int lastItemTime=0;

	private int numberOfObstructions = 0;
	
	public static int level;
	public static int selectedCar;
	public static int difficulty;

	static double levelSpeedMult = 1;
	static int score;


	//obstacle chance
	private static Random random = new Random();
	static int spawnDelay = 50;
	static int maxObstructions = 5;
	static int iChance=100;
	private int itemSpawnDelay = 50;
	
	Boolean gameOver = false;
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
		
		if(engineRunning){
			if(surfaceCreated){
				obstacle();
			}
			totalRunTime();
		}
		//check to see what to start
		//if music is off dont start
		//items
		
		//score and check if win condition
		//calculateScore();

		
		
	}
	
	
	private void totalRunTime() {
		totalRunTime++;

		// Calculate actual game running time in seconds
		double actualRunningTime = (double)(totalRunTime * gameLoopSpeed) / 1000;
		
		// Log every 5 seconds (real time)
		if ((actualRunningTime % 5.0) == 0) {
			Log.i("Game Time", "Game has been running for " + (int)actualRunningTime + " seconds");
			Log.i("Game Time", "Game running time: " + totalRunTime);
		}
		
	}
	private void obstacle(){
			// place obstacle on the screen if it has not produced an obstacle within the spawn time
			if ((totalRunTime - lastObstacleTime) > spawnDelay && numberOfObstructions < maxObstructions){
				synchronized (gameView.obstacleElements){
					gameView.obstacleElements.add(new Civilian(getResources()));
					//set the time of the last obstacle
					lastObstacleTime = totalRunTime;
					Log.i("obstical made", " : " + totalRunTime);
					numberOfObstructions++;
				}
				
			
		}
		// moves existing civilian cars
		synchronized(gameView.obstacleElements){
			for (Iterator<Civilian> car = gameView.obstacleElements.iterator(); car.hasNext();){

				// Get the current car
            	Civilian currentCar = car.next();
            	
            	// If any of the obstruction elements are out of bounds
            	if (currentCar.checkBounds()){
            		car.remove();
            		numberOfObstructions--;
            		Log.i("Civilian Car ", "Obstruction Destroyed at " + totalRunTime);
            	}
            	
            	else if (currentCar.checkCollision(gameView.player)) {
            		// Remove the item
            		car.remove();
            		numberOfObstructions--;
            		//update health
            		gameView.player.damagePlayer(currentCar.getDamage());
            		//checkHealth
            		//updateScore
            		calculateScore();
            		// Debugging
            		Log.i("Civilian Car ", "Obstruction Destroyed at " + totalRunTime);
            	}
			}
			
		}
		
		
	}
	
	private void item(){
		int  iRandom= random.nextInt(iChance);
		
		if (iRandom == (iChance/7)){
			// place obstacle on the screen if it has not produced an obstacle within the spawn time
			if ((totalRunTime - lastItemTime) > itemSpawnDelay){
				synchronized (gameView.obstacleElements){
					gameView.obstacleElements.add(new Civilian(getResources()));
					//set the time of the last obstacle
					lastItemTime = totalRunTime;
					Log.i("Item made", " : " + totalRunTime);
					
				}
				
			}
		}
		
		
		
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
        Log.d("difficulty ", " "+ difficulty);

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
	
	// calculate score based off time and hitting obstacles
	public void calculateScore(){
		score = score+1 * Player.scoreMult;
		Log.i("calculateScore ", "Current Score " + score);
		
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
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		totalRunTime=0;
		surfaceCreated = false;
		//stop music
	}

	//start engine
	@Override
	protected void onStart() {
		Log.d("Made it", "Engine onStart" + gameLoopSpeed);
		super.onStart();
		//create thread
		
		engineThread = new Thread(new Runnable(){
			

			public void run() {
				
				//while engine is running and game is not over runGame
				while (engineRunning && !gameOver){
					runGame();
				
				try{
					Thread.sleep(gameLoopSpeed);
				} catch (Throwable t){
					
				}
				}
				
				//if (gameOver){finish();}
			}
			
			
		});
	}


	public static void surfaceCreated() {
		surfaceCreated = true;
		
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	//Accelerometer changes left to right controls
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
	
	//check for button press (gas break)
	public boolean onTouch(View v, MotionEvent event) {
		int x = gameView.clickScreen(event.getX(), event.getY());
			//while (event.getAction() != MotionEvent.ACTION_UP) {
			if (event.getAction() == android.view.MotionEvent.ACTION_DOWN){
					
					//gas button
					if (x == 1){
						//move image up 
						yDirection= yDirection -Player.getAcceleration();
					}
					//break button
					else if (x==-1){
						//move image down
						yDirection= yDirection + Player.getBreakingPower();


					}
			}
			else if (event.getAction() == android.view.MotionEvent.ACTION_UP){
					yDirection=0;
			}

			return true;
			

	}
	
	
}
