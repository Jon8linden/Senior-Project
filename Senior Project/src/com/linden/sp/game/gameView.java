package com.linden.sp.game;


import com.linden.sp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class gameView extends SurfaceView implements SurfaceHolder.Callback{
	
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
	
	public Player player;
	

	public gameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		viewThread = new GameViewThread(this);
		
		//load our bitmap
		gasBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.white_flag);
				
		// load break button
		breakBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.breakpeddle);
		

	}
	
	//draw to screen
	public void doDraw (Canvas canvas){
		//Log.d("Made it", "GameView doDraw");
		canvas.drawColor(Color.GRAY);
		
        // Draw character
		player.doDraw(canvas);
		//gas button location
		gasButtonX = (int) gasBitmap.getWidth();
    	gasButtonY = (getHeight()) - gasBitmap.getHeight();

    	//break button location
    	breakButtonX = (int) getWidth() - breakBitmap.getWidth();
    	breakButtonY = (int) (getHeight()) - breakBitmap.getHeight();


    	//draw buttons
   		canvas.drawBitmap(gasBitmap, gasButtonX, gasButtonY, null);
   		canvas.drawBitmap(breakBitmap, breakButtonX, breakButtonY, null);
		
	}
	
	
	public void surfaceCreated(SurfaceHolder holder){
		//Log.d("Made it", "GameView surfaceCreated");
		gameWidth = this.getWidth();
		gameHeight = this.getHeight();
		
		//setup boundaries
		
		//create player
		player = new Player(getResources(), (int) gameWidth/2, (int) gameHeight/2);
    	
        if (!viewThread.isAlive()) {
            viewThread = new GameViewThread(this);
            viewThread.setRun(true);
            viewThread.start();
        }
        
        // notify engine that the surface has been created
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
		
	}


	public Indicators clickScreen(float x, float y) {
		//break button rect box
		Rect breakButton = new Rect(breakButtonX, breakButtonY, breakButtonX+breakBitmap.getWidth(), breakButtonY+breakBitmap.getHeight());
		
		//flag button rect box
		Rect gas = new Rect(gasButtonX, gasButtonY, gasButtonX+gasBitmap.getWidth(), gasButtonY+gasBitmap.getHeight());
		
		//check to see where on the canvas the user pressed
		if (gas.contains((int) x, (int)y)) {
			invalidate();
			return Indicators.gasPeddle;
		
		} 
		
		//check to see if break was pressed
		else if (breakButton.contains((int) x, (int)y)) {
			return Indicators.breakPeddle;
		
		} 
		
		//did not touch a peddle
		else {
			return Indicators.fail;
		}
		
	}
	
}
