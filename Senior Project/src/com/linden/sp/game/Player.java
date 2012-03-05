package com.linden.sp.game;

import com.linden.sp.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class Player{
	//player image
	private Bitmap playerImage;
	private Bitmap rabbit;
	
	//x and y starting locations
	private float initialX;
	private float initialY;
	
	private int playerHealth;
	
	// X and Y speeds
	private int xSpeed;
	private int ySpeed;
	
	public Player(Resources res, int x, int y){
		//super (contextPlayer);
		//get image
		rabbit = BitmapFactory.decodeResource(res, R.drawable.rabbitsmall);
		
		//change player to selected car
		playerImage = rabbit;

		//starting location
		initialX = x-playerImage.getWidth() / 2;
		initialY= (y*2) - playerImage.getHeight();
		
		//starting speed
		xSpeed = 0;
		
		
	}
	
	//draw player on screen
	public void doDraw(Canvas canvas){
		//Log.d("Made it", "Player doDraw");
		//canvas.drawBitmap(playerImage,100,100,null);
		canvas.drawBitmap(playerImage,initialX,initialY,null);
	}

	//move player left and right
	public void animation(long totalTime) {
		//change speed based off of accelerometer gravity
		xSpeed = (int) (Engine.gravity[1] * .9);
		
		//apply speed to xCordinates
		initialX += xSpeed * (totalTime / 5f);
		
		//change yCordinates
		ySpeed = (int) (Engine.yDirection);
		
		initialY= ySpeed ;
		
		//check bounds
		
		
		
	}
	//check bounds
	
	//get position of player
	//get player dimensions 
}
