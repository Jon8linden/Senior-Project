package com.linden.sp.game;

import com.linden.sp.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Player{

	//player image
	private static Bitmap playerImage;
	
	//x and y starting locations
	private static float xPlayerPosition;
	private static float yPlayerPosition;
	
	private int playerHealth;
	private int handling;
	private static int breakingPower;
	private static int speed;
	private static int acceleration;
	
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
		//career 
		if (Engine.level == 1){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.rabbit);
			//health (this should probably be resistance so everyone has 100 health)
			playerHealth = 200;
			//Accelerometer multiplier
			handling = 2;
			//top speed (how fast items/civilians move)
			Engine.levelSpeedMult=.1;
			//spawn chance
			Engine.obstacleChance= 40000;
			setSpeed(1);
			//acceleration (add this to menu screen)
			setAcceleration(2);
			//breakingPower = number; implement later
			setBreakingPower(3);
		}
		else if (Engine.level == 2){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.gallerydelsol);
			playerHealth = 100;
			handling = 3;
			Engine.levelSpeedMult= .15;
			setSpeed(1);
			setAcceleration(3);
			setBreakingPower(3);
		}
		else if (Engine.level == 3){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.jeep);
			playerHealth = 200;
			handling = 2;
			setSpeed(2);
			setAcceleration(3);
			setBreakingPower(2);
		}
		else if (Engine.level == 4){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.blacktruck);
			playerHealth = 600;
			handling = 2;
			setSpeed(2);
			setAcceleration(4);
			setBreakingPower(2);
		}
		else if (Engine.level == 5){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.gamebmw);
			playerHealth = 500;
			handling = 4;
			setSpeed(4);
			setAcceleration(5);
			setBreakingPower(4);
		} 
		else if (Engine.level == 6){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.porschetrimed);
			playerHealth = 400;
			handling = 5;
			setSpeed(5);
			setAcceleration(7);
			setBreakingPower(6);
		}
		else if (Engine.level == 7){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.stiplayer600);
			playerHealth = 500;
			handling = 6;
			Engine.levelSpeedMult=1;
			setSpeed(6);
			setAcceleration(6);
			setBreakingPower(5);
		}
		//Survival
		else if (Engine.selectedCar == 0){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.rabbit600);
			playerHealth = 100;
			handling = 2;
			
			//top speed (how fast items/civilians move)
			Engine.levelSpeedMult=.1;
			//spawn chance
			Engine.obstacleChance= 40000;
			
			setSpeed(1);
			setAcceleration(2);
			setBreakingPower(3);
		}
		else if (Engine.selectedCar == 1){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.gallerydelsol);
			playerHealth = 100;
			handling = 3;
			setSpeed(1);
			setAcceleration(2);
			setBreakingPower(3);
		}
		else if (Engine.selectedCar == 2){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.jeep600);
			playerHealth = 200; 
			handling = 2;
			setSpeed(2);
			setAcceleration(3);
			setBreakingPower(2);
		}
		else if (Engine.selectedCar == 3){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.blacktruck);
			playerHealth = 600;
			handling = 2;
			setSpeed(2);
			setAcceleration(4);
			setBreakingPower(2);
		}
		else if (Engine.selectedCar == 4){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.gamebmw);
			playerHealth = 500;
			handling = 4;
			
			//top speed (how fast items/civilians move)
			Engine.levelSpeedMult=.5;
			//spawn chance
			Engine.obstacleChance= 35000;
			
			setSpeed(4);
			setAcceleration(5);
			setBreakingPower(4);
		} 
		else if (Engine.selectedCar == 5){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.porschetrimed);
			playerHealth = 400;
			handling = 5;
			setSpeed(5);
			setAcceleration(7);
			setBreakingPower(6);
		}
		else if (Engine.selectedCar == 6){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.stiplayer600); 
			playerHealth = 500;
			handling = 6;
			
			//top speed (how fast items/civilians move)
			Engine.levelSpeedMult=1;
			//spawn chance
			Engine.obstacleChance= 1000;
			
			setSpeed(6);
			setAcceleration(6);
			setBreakingPower(9);
		}
		
		
		
	}
	
	//draw player on screen
	public void doDraw(Canvas canvas){
		//Log.d("Made it", "Player doDraw");
		canvas.drawBitmap(playerImage,xPlayerPosition,yPlayerPosition,null);
	}

	//move player left and right
	public void animation(long totalTime) {
		//change speed based off of accelerometer gravity
		//handling mod = .9
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
	public void setSpeed(int speed) {
		this.speed = speed;
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

}
