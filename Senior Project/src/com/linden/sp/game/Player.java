package com.linden.sp.game;

import com.linden.sp.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Player {
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
		//get images
		//rabbit = BitmapFactory.decodeResource(res, R.drawable.rabbit);
		
		//change player to selected car
		playerImage = rabbit;
		
		
		
		//starting location
		initialX = x-playerImage.getWidth() / 2;
		initialY= (y*2) - playerImage.getHeight();
		
		//starting speed
		xSpeed = 0;
		
		//Starting health
		//add in variable max health dependent on car
		playerHealth = 100;
		
		
	}
	
	//draw player on screen
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(playerImage,initialX,initialY,null);
	}
}
