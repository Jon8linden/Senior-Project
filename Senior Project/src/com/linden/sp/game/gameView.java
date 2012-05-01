package com.linden.sp.game;


import java.util.ArrayList;
import java.util.Iterator;

import com.linden.sp.R;
import com.linden.sp.R.color;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class gameView extends SurfaceView implements SurfaceHolder.Callback{
	
	public static float leftBound;
	public static float rightBound;
	public static float topBound;
	public static int bottomBound;
	int gameWidth;
	int gameHeight;
	
	 
	//button placements
	private int gasButtonX;
	private int gasButtonY;
	private int breakButtonX;
	private int breakButtonY;
	
	//image bitmaps
	private Bitmap gasBitmap = null;
	private Bitmap breakBitmap = null;
	
	//view thread
	private GameViewThread viewThread;
	
	public ArrayList<Civilian> obstacleElements = new ArrayList<Civilian>();
	public ArrayList<Cops> copElement = new ArrayList<Cops>();
	public ArrayList<Item> itemElement = new ArrayList<Item>();
	public Player player;
	

	public gameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		viewThread = new GameViewThread(this);
		
		//load gas button
		gasBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gas2);
				
		// load break button
		breakBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brakebutton);
		

	}
	
	//draw to screen
	public void doDraw (Canvas canvas){
		// Draw Road
		canvas.drawColor(Color.GRAY);
		
   		// Draw Grass
   		Paint pGreen = new Paint();
   		pGreen.setStyle(Paint.Style.FILL);
   		pGreen.setColor(0xFF005e20);
   		
   		Rect leftGrass = new Rect();
   		leftGrass.set(0,0, (int) leftBound, canvas.getHeight());
   		canvas.drawRect(leftGrass, pGreen);
   		
   		Rect rightGrass = new Rect();
   		rightGrass.set((int) rightBound,0, canvas.getWidth(), canvas.getHeight());
   		canvas.drawRect(rightGrass, pGreen);
   		
   		// Set up text (color size location)
   		Paint pText = new Paint();
   		pText.setColor(Color.BLACK);
   		pText.setTextSize((float) 30 * getContext().getResources().getDisplayMetrics().density);
   		pText.setTextAlign(Align.CENTER);
   		
   		// Draw Health
   		canvas.drawText("Health", (leftBound/2), (canvas.getHeight()/10), pText);
   		canvas.drawText(""+Player.playerHealth, (leftBound/2), (canvas.getHeight()/10) + pText.getTextSize(), pText);
   		
   		if(Engine.career){
   			// Remaining Time display
   			if(Player.getCareerFinish() <0){
   	   			canvas.drawText("Time", rightBound + (leftBound/2) , (canvas.getHeight()/10), pText);
   	   			canvas.drawText(""+Engine.remainingTime, rightBound +(leftBound/2), (canvas.getHeight()/10) + (pText.getTextSize()), pText);

   			}
   			else{
   			// Draw Number of cars hit
   			canvas.drawText("Cars Hit", rightBound + (leftBound/2) , (canvas.getHeight()/10), pText);
   			canvas.drawText(""+Engine.carsHit, rightBound +(leftBound/2), (canvas.getHeight()/10) + (pText.getTextSize()), pText);
   			}
   		}
   		else if (Engine.survival){
   			// Draw Score
   			canvas.drawText("Score", rightBound + (leftBound/2) , (canvas.getHeight()/10), pText);
   			canvas.drawText(""+Engine.score, rightBound +(leftBound/2), (canvas.getHeight()/10) + pText.getTextSize(), pText);
   		}
        // Draw Character
		player.doDraw(canvas);

		// Gas button location
		gasButtonX = (int) getWidth() - breakBitmap.getWidth();
    	gasButtonY = (getHeight()) - gasBitmap.getHeight();
 
    	// Break button location
    	breakButtonX = 0;
    	breakButtonY = (int) (getHeight()) - breakBitmap.getHeight();


    	// Draw Buttons
   		canvas.drawBitmap(gasBitmap, gasButtonX, gasButtonY, null);
   		canvas.drawBitmap(breakBitmap, breakButtonX, breakButtonY, null);
   		
		// Draw obstructions
		synchronized (obstacleElements) {
			if (obstacleElements.size() > 0) {
	            for (Iterator<Civilian> it = obstacleElements.iterator(); it.hasNext();) {
	            	it.next().draw(canvas);
	            }
			}
        }
		// Draw Cops
		synchronized (copElement) {
			if (copElement.size() > 0) {
	            for (Iterator<Cops> copIt = copElement.iterator(); copIt.hasNext();) {
	            	copIt.next().draw(canvas);
	            }
			}
        }
		
		// Draw Items
		synchronized (itemElement) {
			if (itemElement.size() > 0) {
	            for (Iterator<Item> it = itemElement.iterator(); it.hasNext();) {
	            	it.next().draw(canvas);
	            }
			}
        }
   		
	}
	

	public void surfaceCreated(SurfaceHolder holder){
		//Log.d("Made it", "GameView surfaceCreated");
		gameWidth = this.getWidth();
		gameHeight = this.getHeight();
		
		//setup boundaries
		rightBound = gameWidth-(gameWidth/5);
		leftBound = (gameWidth/5);
		
		topBound = 0;
		bottomBound= gameHeight;
		
		
		//create player
		player = new Player(getResources(), (int) gameWidth/2, (int) gameHeight/2);
    	
        if (!viewThread.isAlive()) {
            viewThread = new GameViewThread(this);
            viewThread.setRun(true);
            viewThread.start();
        }
        
        //notify engine that the surface has been created
        Engine.surfaceCreated();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
        gameWidth = width;
        gameHeight = height;
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		//kill viewThread
		if (viewThread.isAlive()){
			viewThread.setRun(false);
		}
		
	}
	
	public void animation (long totalTime){
		player.animation(totalTime);
		
		// Move the civilians down
    	synchronized (obstacleElements) {
    		if (obstacleElements.size() > 0) {
	            for (Iterator<Civilian> it = obstacleElements.iterator(); it.hasNext();) {
	            	it.next().animate(totalTime);
	            }
    		}
    	}
    	
		// Move the Cops down
    	synchronized (copElement) {
    		if (copElement.size() > 0) {
	            for (Iterator<Cops> it = copElement.iterator(); it.hasNext();) {
	            	it.next().animate(totalTime);
	            }
    		}
    	}
    	
		// Move the items down
    	synchronized (itemElement) {
    		if (itemElement.size() > 0) {
	            for (Iterator<Item> it = itemElement.iterator(); it.hasNext();) {
	            	it.next().animate(totalTime);
	            }
    		}
    	}
		
	}


	public int clickScreen(float x, float y) {
		//break button rect box
		Rect breakButton = new Rect(breakButtonX, breakButtonY, breakButtonX+breakBitmap.getWidth(), breakButtonY+breakBitmap.getHeight());
		
		//gas button rect box
		Rect gas = new Rect(gasButtonX, gasButtonY, gasButtonX+gasBitmap.getWidth(), gasButtonY+gasBitmap.getHeight());
		
		//check to see where on the canvas the user pressed
		if (gas.contains((int) x, (int)y)) {
			invalidate();
			return 1;
		
		} 
		
		//check to see if break was pressed
		else if (breakButton.contains((int) x, (int)y)) {
			return -1;
		
		} 
		
		//did not touch a peddle
		else {
			return 0;
		}
		
	}
	
}
