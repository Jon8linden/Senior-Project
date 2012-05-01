package com.linden.sp.game;

import com.linden.sp.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.Toast;

public class Player{

	//player image
	private static Bitmap playerImage;
	
	//x and y starting locations
	private static float xPlayerPosition;
	private static float yPlayerPosition;
	
	static int playerHealth=100;		//number set so instant loose does not occur when game is loading
	private int handling;
	private static int breakingPower;
	private static int speed;
	private static int acceleration;
	private static int careerFinish=1; 	//number set so instant win does not occur when game is loading

	public static int scoreMult=1;
	
	// X and Y speeds
	private int xSpeed;
	private int ySpeed;
	
	public Player(Resources res, int x, int y){
		//super (contextPlayer);

		//change image
		changePlayerImage(res);

		//starting location (center bottom)
		xPlayerPosition = x-playerImage.getWidth() / 2;
		yPlayerPosition= (y*2) - playerImage.getHeight();
		
		//starting speed
		xSpeed = 0;
		
		
	}
	
	//determine which car to place as player image
	public void changePlayerImage(Resources res){
		if (Engine.level == 1){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.rabbit600);
			//health (this should probably be resistance so everyone has 100 health)
			playerHealth = 200;
			//Accelerometer multiplier
			handling = 2;
			//top speed (how fast items/civilians move)
			Engine.levelSpeedMult=.1;
			//acceleration (add this to menu screen)
			setAcceleration(2);
			//breakingPower = number; implement later
			setBreakingPower(3);
			//max number of CC and Cops
			Engine.maxCC = 3;
			Engine.maxCops = 0;
			//spawn delay must be changed due to speed of cars
			Engine.spawnDelay=100;
			//finish condition destroy cars and how many
			Engine.careerDestroy = true;
			setCareerFinish(25);
			//delay for lane lines
			Engine.lineDelay=10;
			
		}
		else if (Engine.level == 2){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.delsol600);
			playerHealth = 100;
			handling = 3;
			Engine.levelSpeedMult= .15;
			setAcceleration(3);
			setBreakingPower(3);
			Engine.maxCC = 3;
			Engine.maxCops = 0;
			Engine.spawnDelay=100;
			
			//Dont hit cars Cant ever hit -1 cars
			Engine.careerSurvive = true;
			setCareerFinish(-1);
			//set survival time
			Engine.maxLevelTime = 30;
			Engine.lineDelay=10;
		}
		else if (Engine.level == 3){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.jeep600);
			playerHealth = 200;
			handling = 2;
			Engine.levelSpeedMult= .25;
			setAcceleration(3);
			setBreakingPower(2);
			Engine.maxCC = 0;
			Engine.maxCops = 3;
			Engine.spawnDelay=50;
			//Dont hit cars Cant ever hit -1 cars
			Engine.careerSurvive = true;
			setCareerFinish(-1);
			//set survival time
			Engine.maxLevelTime = 45;
			Engine.lineDelay=8;
		}
		else if (Engine.level == 4){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.blacktruck);
			playerHealth = 600;
			handling = 2;
			Engine.levelSpeedMult=.25;
			setAcceleration(4);
			setBreakingPower(2);
			Engine.maxCC = 1;
			Engine.maxCops = 2;
			Engine.spawnDelay=50;
			
			//finish condition
			Engine.careerDestroy = true;
			setCareerFinish(60);
			Engine.lineDelay=8;


		}
		else if (Engine.level == 5){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.bmw600);
			playerHealth = 200;
			handling = 4;
			Engine.levelSpeedMult=.35;
			Engine.spawnDelay=40;
			
			setAcceleration(5);
			setBreakingPower(4);
			Engine.maxCC = 1;
			Engine.maxCops = 2;
			setCareerFinish(25);
			Engine.spawnDelay=30;
			//Dont hit cars Cant ever hit -1 cars
			Engine.careerSurvive = true;
			setCareerFinish(-1);
			//set survival time
			Engine.maxLevelTime = 200;
			Engine.lineDelay=3;

		} 
		else if (Engine.level == 6){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.porsche600);
			playerHealth = 400;
			handling = 5;
			Engine.levelSpeedMult=.5;
			setAcceleration(7);
			setBreakingPower(6);
			Engine.maxCC = 1;
			Engine.maxCops = 4;
			setCareerFinish(25);
			
			//finish condition
			Engine.careerDestroy = true;
			setCareerFinish(60);
			Engine.lineDelay=1;
		}
		else if (Engine.level == 7){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.stiplayer600); 
			playerHealth = 500;
			handling = 6;
			Engine.levelSpeedMult=.6;
			Engine.spawnDelay=20;
			setAcceleration(6);
			setBreakingPower(9);
			Engine.maxCC = 1;
			Engine.maxCops = 4;
			setCareerFinish(25);
			//Dont hit cars Cant ever hit -1 cars
			Engine.careerSurvive = true;
			setCareerFinish(-1);
			//set survival time
			Engine.maxLevelTime = 360;
			Engine.lineDelay=1;
		}
		//lane width based off player width
		Engine.laneWidth=playerImage.getWidth();
		
		//update difficulty if survival mode is selected
		Engine.updateDifficulty();
		
		//flag for toast
		Engine.valuesSetFlag = true;
	}
	
	//draw player on screen
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(playerImage,xPlayerPosition,yPlayerPosition,null);
	}

	//move player left and right
	public void animation(long totalTime) {
		//change speed based off of accelerometer gravity and handling of car
		xSpeed = (int) (Engine.gravity[1] * .22 * handling);
		
		//apply speed to xCordinates
		xPlayerPosition += xSpeed * (totalTime / 5f);
		
		//change yCordinates
		ySpeed = (int) (Engine.yDirection);
		
		yPlayerPosition+= ySpeed ;
		
		//check bounds
		checkBounds();
		
		
	}

	//check bounds of the screen
	private void checkBounds(){
		//left and right
		if (xPlayerPosition <= gameView.leftBound){
			xSpeed = -xSpeed;
			xPlayerPosition = gameView.leftBound;
		}
		else if (xPlayerPosition + playerImage.getWidth() >= gameView.rightBound){
			xSpeed = -xSpeed;
			xPlayerPosition = gameView.rightBound - playerImage.getWidth();
		}
		
		
		//check to see if player is going above the view
		if (yPlayerPosition <=0){
			yPlayerPosition=0;
		}
		
		//check to see if player is going below the view
		if (yPlayerPosition + playerImage.getHeight() >= gameView.bottomBound){
			yPlayerPosition = gameView.bottomBound - playerImage.getHeight();
		}
		
	}
	
	public static int[][] getPlayerBounds(){
		// 4 corners x,y for each corner
		int[][] bounds = new int [4][2];
		
		//Fill array clockwise (starting with Top Left)
		bounds[0][0] = (int) xPlayerPosition;
		bounds[0][1] = (int) yPlayerPosition;
		
		bounds[1][0] = (int) ((int) xPlayerPosition + (int) playerImage.getWidth());
		bounds[1][1] = (int) yPlayerPosition;
		
		bounds[2][0] = (int) ((int) xPlayerPosition + (int) playerImage.getWidth());
		bounds[2][1] = (int) ((int) yPlayerPosition + (int) playerImage.getHeight());	
		
		bounds[3][0] = (int) xPlayerPosition;
		bounds[3][1] = (int) ((int) yPlayerPosition + (int) playerImage.getHeight());	
		
		return bounds;
	}
	
	public int[] getPlayerLocation(){
		int[] playerLocation = new int[2];
		
		playerLocation[0] = (int) xPlayerPosition;
		playerLocation[1] = (int) yPlayerPosition;
		return playerLocation;
	}
	public int[] getPlayerSize(){
		int[] playerSize = new int [2];
		
		playerSize[0] = (int) playerImage.getWidth();
		playerSize[1] = (int) playerImage.getHeight();
		
		return playerSize;
		
	}
	public static int getSpeed() {
		return speed;
	}
	public static int getBreakingPower() {
		return breakingPower;
	}
	public void setBreakingPower(int breakingPower) {
		this.breakingPower = breakingPower;
	}
	public static int getAcceleration() {
		return acceleration;
	}
	public static void setAcceleration(int acceleration) {
		Player.acceleration = acceleration;
	}

	public void damagePlayer(int damage) {
		playerHealth= playerHealth-damage;
		Log.i("Hurt player ", "Health is at: " + playerHealth);
	}

	public static int getCareerFinish() {
		return careerFinish;
	}

	public static void setCareerFinish(int careerFinish) {
		Player.careerFinish = careerFinish;
	}

}
