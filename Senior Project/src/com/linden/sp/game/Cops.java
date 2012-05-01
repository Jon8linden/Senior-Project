package com.linden.sp.game;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.linden.sp.R;

public class Cops {
	
	//cc means Civilian Car
	
	// Starting locations
	private float ccX;
	private float ccY;
	
	// Speed
	private double ccSpeedY;
	
	// Random location for CC
	private static Random random = new Random();
	
	private int copDrawable;
	private int damage;
	private Bitmap copImage;
	
	static int laneWidth;
	
	//cop car images
	private final int[] copCars = {
			R.drawable.towncop600,
			R.drawable.undercovercop600,
			R.drawable.statepolice600
			

	};
	
	
	public Cops (Resources res){
		
		// Set the bitmap to Civilian Car Image
		setImageAttributes();
		copImage = BitmapFactory.decodeResource(res,copDrawable);
		
		laneWidth= Engine.laneWidth;
		
		// Get horizontal starting location (between bounds, accounting for Civilian Car image width and keeping within the 4 lanes)
		int randomX=random.nextInt(5);
		
		
		
		//starting location
		if (randomX<=1){
			ccX = gameView.leftBound;
		}
		else if (randomX>1 && randomX<=2){
			ccX = gameView.leftBound + laneWidth+ Engine.lanePadding;
		}
		else if (randomX>2 && randomX<=3){
			ccX = gameView.leftBound + laneWidth*2 + Engine.lanePadding*2;
		}
		else if (randomX>3 && randomX<=4){
			ccX = gameView.rightBound-laneWidth;
		}

		ccY = 0 - copImage.getHeight();
		
		//set starting speed
		speedY();
		
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(copImage, ccX, ccY, null);
	}
	
	private void speedY(){
		//game looping speed slowed down by a percentage multiplied by level speed modifier
		ccSpeedY= (double) Engine.gameLoopSpeed * .25;
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
		int randomImage = random.nextInt(copCars.length);
		
		copDrawable = copCars[randomImage];
		
		//check image and apply effects
		if (randomImage == 0){
			damage=10;
		}
		else if (randomImage == 1){
			damage = 20;
		}
		else if (randomImage == 2){
			damage = 35;
		}
		
		
		
		
	}
	public int getDamage(){
		return damage;
	}
	
	public boolean checkBounds(){
		//remove image if it is no longer on the screen
		if (ccY- copImage.getHeight() >= gameView.bottomBound){
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

	public int [][] getBounds(){
		// 4 corners x,y for each corner
		int[][] bounds = new int [4][2];
		
		//Fill array clockwise (starting with Top Left)
		bounds[0][0] = (int) ccX;
		bounds[0][1] = (int) ccY;
		
		bounds[1][0] = (int) ((int) ccX + (int) copImage.getWidth());
		bounds[1][1] = (int) ccY;
		
		bounds[2][0] = (int) ((int) ccX + (int) copImage.getWidth());
		bounds[2][1] = (int) ((int) ccY + (int) copImage.getHeight());	
		
		bounds[3][0] = (int) ccX;
		bounds[3][1] = (int) ((int) ccY + (int) copImage.getHeight());	
		
		return bounds;
	}
	public int[] getSize(){
		int[] playerSize = new int [2];
		
		playerSize[0] = (int) copImage.getWidth();
		playerSize[1] = (int) copImage.getHeight();
		
		return playerSize;
		
	}
	
	//check collision
	public boolean checkCollision(Player player){
		//get x,y arrays for car and player
		int[][] obsticalLocation = this.getBounds();
		int[][] playerLocation = Player.getPlayerBounds();
		
		//place rectangle in array location for player
    	Rect obsticalRect = new Rect(
    			obsticalLocation[0][0],
    			obsticalLocation[0][1],
    			obsticalLocation[2][0],
    			obsticalLocation[2][1]
        	);
        	Rect playerRect = new Rect(
        		playerLocation[0][0],
        		playerLocation[0][1],
        		playerLocation[2][0],
        		playerLocation[2][1]
        	);
        	
        	if(Rect.intersects(playerRect, obsticalRect)){
        		return true;
        	}
		
		
		
		
		
		return false;
	}
	
	
}
