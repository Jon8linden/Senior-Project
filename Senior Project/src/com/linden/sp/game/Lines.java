package com.linden.sp.game;

import java.util.Random;

import com.linden.sp.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class Lines {

	
	// Starting locations
	private float ccX;
	private float ccY;
	
	//place lines in lanes counter
	public int counter;
	// Speed
	private double ccSpeedY;
	
	// Random location for CC
	private static Random random = new Random();
	
	private int linesDrawable;
	private Bitmap Image;
	static int laneWidth;
	
	//dashed lines
	private final int[] lines = {
			R.drawable.dashedline
	};
	
	
	public Lines (Resources res){
		// Set the bitmap
		setImageAttributes();
		Image = BitmapFactory.decodeResource(res,linesDrawable);
		
		laneWidth= Engine.laneWidth;
		
		//starting location
		if (Engine.lineLaneCounter==1){
			ccX = gameView.leftBound + laneWidth*2;
			
			Log.i("Line ", "Counter " + counter);
		} 
		//between lane 4 and 5
		else if(Engine.lineLaneCounter == 2){
			
			ccX = gameView.rightBound-Engine.laneWidth;

		}
		//between lane one and two
		else{
			ccX = gameView.leftBound + laneWidth;

		}


		ccY = 0 - Image.getHeight();
		
		//set starting speed
		speedY();
		
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(Image, ccX, ccY, null);
	}
	
	private void speedY(){
		//game looping speed slowed down by a percentage multiplied by level speed modifier
		ccSpeedY= (double) Engine.gameLoopSpeed;
		ccSpeedY= (double) Engine.levelSpeedMult*ccSpeedY;
	}
	
	public void animate (long elapsedTime){
		//update speed
		speedY();
		
		//animate
		ccY+= ccSpeedY * (elapsedTime / 5f);
		
	}
	//set random obstacle and attributes based off image
	private void setImageAttributes(){
		int randomImage = random.nextInt(lines.length);
		
		linesDrawable = lines[randomImage];
		
		
		
		
	}
	
	public boolean checkBounds(){
		//remove image if it is no longer on the screen
		if (ccY- Image.getHeight() >= gameView.bottomBound){
			return true;
		}
		return false;
	}
	
	public int [] getLocation() {
		// Set up array for 2D plane
		int[] location = new int [2];
		
		location[0] = (int) ccX;
		location[1] = (int) ccY;
		
		//return location
		return location;
	}

	public int[] getSize(){
		int[] playerSize = new int [2];
		
		playerSize[0] = (int) Image.getWidth();
		playerSize[1] = (int) Image.getHeight();
		
		return playerSize;
		
	}
	
	
	
}
