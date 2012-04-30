package com.linden.sp.game;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.linden.sp.R;

public class Item {
		
		// Starting locations
		private float itemX;
		private float itemY;
		
		// Speed
		private double itemSpeedY;
		
		// Random location for
		private static Random random = new Random();
		
		private int itemDrawable;
		private int damage;
		private Bitmap itemImage;
		
		//Civilian car images
		private final int[] items = {
				R.drawable.wrench
		};
		
		public Item (Resources res){

			// Set the bitmap to Civilian Car Image
			setImageAttributes();
			itemImage = BitmapFactory.decodeResource(res,itemDrawable);
			
			
			
			// Get horizontal starting location (between bounds, accounting for Civilian Car image width and keeping within the 4 lanes)
			int randomX=random.nextInt(5);
			//starting location
			if (randomX<=1){
				itemX = gameView.leftBound;
			}
			else if (randomX>1 && randomX<=2){
				itemX = gameView.leftBound + Civilian.laneWidth;
			}
			else if (randomX>2 && randomX<=3){
				itemX = gameView.leftBound + Civilian.laneWidth*2;
			}
			else if (randomX>3 && randomX<=4){
				itemX = gameView.rightBound-Civilian.laneWidth;
			}

			itemY = 0 - itemImage.getHeight();
			
			//set starting speed
			speedY();
			
		}
		
		public void draw(Canvas canvas){
			canvas.drawBitmap(itemImage, itemX, itemY, null);
		}
		
		private void speedY(){
			//game looping speed slowed down by a percentage multiplied by level speed modifier
			itemSpeedY= (double) Engine.gameLoopSpeed * .25;
			itemSpeedY= (double) Engine.levelSpeedMult*itemSpeedY;
		}
		
		public void animate (long elapsedTime){
			//update speed
			speedY();
			
			//animate
			itemY+= itemSpeedY * (elapsedTime / 5f);
			
		}
		//set random obstacle and attributes based off image
		private void setImageAttributes(){
			int randomImage = random.nextInt(items.length);
			
			itemDrawable = items[randomImage];
			
			//check image and apply effects
			if (randomImage == 0){
				damage=-25;
			}
			else if (randomImage == 5){
				
			}
			
			
			
			
		}
		public int getDamage(){
			return damage;
		}
		
		public boolean checkBounds(){
			//remove image if it is no longer on the screen
			if (itemY- itemImage.getHeight() >= gameView.bottomBound){
				return true;
			}
			return false;
		}
		
		public int [] getLocation() {
			// Set up array for 2D plane
			int[] location = new int [2];
			
			location[0] = (int) itemX;
			location[1] = (int) itemY;
			
			//return location
			return location;
		}

		public int [][] getBounds(){
			// 4 corners x,y for each corner
			int[][] bounds = new int [4][2];
			
			//Fill array clockwise (starting with Top Left)
			bounds[0][0] = (int) itemX;
			bounds[0][1] = (int) itemY;
			
			bounds[1][0] = (int) ((int) itemX + (int) itemImage.getWidth());
			bounds[1][1] = (int) itemY;
			
			bounds[2][0] = (int) ((int) itemX + (int) itemImage.getWidth());
			bounds[2][1] = (int) ((int) itemY + (int) itemImage.getHeight());	
			
			bounds[3][0] = (int) itemX;
			bounds[3][1] = (int) ((int) itemY + (int) itemImage.getHeight());	
			
			return bounds;
		}
		public int[] getSize(){
			int[] playerSize = new int [2];
			
			playerSize[0] = (int) itemImage.getWidth();
			playerSize[1] = (int) itemImage.getHeight();
			
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
