package com.linden.sp.game;

import com.linden.sp.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Player{
	//player image
	private Bitmap playerImage;
	//private Bitmap rabbit;
	//private Bitmap delSol;
	
	//x and y starting locations
	private float xPlayerPosition;
	private float yPlayerPosition;
	
	private int playerHealth;
	private int handling;
	private static int speed;
	
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
			playerImage = BitmapFactory.decodeResource(res, R.drawable.rabbittrimed);
			playerHealth = 200;
			handling = 2;
			setSpeed(1);
		}
		else if (Engine.level == 2){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.gallerydelsol);
			playerHealth = 100;
			handling = 3;
			setSpeed(1);
		}
		else if (Engine.level == 3){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.jeep);
			playerHealth = 200;
			handling = 2;
			setSpeed(2);
		}
		else if (Engine.level == 4){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.towncop);
			playerHealth = 600;
			handling = 2;
			setSpeed(2);
		}
		else if (Engine.level == 5){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.gamebmw);
			playerHealth = 500;
			handling = 4;
			setSpeed(4);
		} 
		else if (Engine.level == 6){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.porschetrimed);
			playerHealth = 400;
			handling = 5;
			setSpeed(5);
		}
		else if (Engine.level == 7){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.stirear);
			playerHealth = 500;
			handling = 6;
			setSpeed(6);
		}
		//Survival
		else if (Engine.selectedCar == 0){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.rabbittrimed);
			playerHealth = 100;
			handling = 2;
			setSpeed(1);
		}
		else if (Engine.selectedCar == 1){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.gallerydelsol);
			playerHealth = 100;
			handling = 3;
			setSpeed(1);
		}
		else if (Engine.selectedCar == 2){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.jeep);
			playerHealth = 200;
			handling = 2;
			setSpeed(2);
		}
		else if (Engine.selectedCar == 3){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.icon);
			playerHealth = 600;
			handling = 2;
			setSpeed(2);
		}
		else if (Engine.selectedCar == 4){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.gamebmw);
			playerHealth = 500;
			handling = 4;
			setSpeed(4);
		}
		else if (Engine.selectedCar == 5){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.porschetrimed);
			playerHealth = 400;
			handling = 5;
			setSpeed(5);
		}
		else if (Engine.selectedCar == 6){
			playerImage = BitmapFactory.decodeResource(res, R.drawable.stirear);
			playerHealth = 500;
			handling = 6;
			setSpeed(6);
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
	//check bounds
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
			//ySpeed=-ySpeed;
		}
		
		//check to see if player is going below the view
		if (yPlayerPosition + playerImage.getHeight() >= gameView.bottomBound){
			yPlayerPosition = gameView.bottomBound - playerImage.getHeight();
			//ySpeed=-ySpeed;
		}
		
	}
	public static int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	//get position of player
	//get player dimensions 
}
