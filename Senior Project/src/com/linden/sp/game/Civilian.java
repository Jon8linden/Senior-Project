package com.linden.sp.game;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.linden.sp.R;

public class Civilian {
	
	//cc means Civilian Car
	
	// Starting locations
	private float ccX;
	private float ccY;
	
	// Speed
	private double ccSpeedY;
	
	// Random location for CC
	private static Random random = new Random();
	
	private int ccDrawable;
	private int damage;
	private Bitmap ccImage;
	
	//Civilian car images
	private final int[] civilianCars = {
		R.drawable.stirear,
		R.drawable.icon
			
			
	};
	
	public Civilian (Resources res){
		
		
		// Set the bitmap to Civilian Car Image
		ccImage = BitmapFactory.decodeResource(res,ccDrawable);
		
		
		// Get horizontal starting location (between bounds, accounting for Civilian Car image width)
		float randomX = random.nextInt((int) (gameView.rightBound - gameView.leftBound - ccImage.getWidth())) + gameView.leftBound;
		
		//starting location
		ccX = randomX;
		ccY = 0 - ccImage.getHeight();
		
		//set speed
		speedY();
		
	}
	
	private void speedY(){
		ccSpeedY= (double) Engine.gameLoopSpeed;
	}
	
	public void animate (long elapsedTime){
		speedY();
		
		//animate
		ccY+= ccSpeedY * (elapsedTime / 5f);
		
	}
	
	private void setImage(){
		int randomImage = random.nextInt(civilianCars.length);
		
		ccDrawable = civilianCars[randomImage];
		
		//check image and apply effects
		if (randomImage == 0){
			// effects
		}
		else if (randomImage == 1){
			
		}
		
		
		
		
	}
	
	public int [] getLocation() {
		// Set up array for 2D plane
		int[] location = new int [2];
		
		location[0] = (int) ccX;
		location[1] = (int) ccY;
		
		//return location
		return location;
	}
	
}
