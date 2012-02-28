package com.linden.sp.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class gameView extends SurfaceView implements SurfaceHolder.Callback{
	
	int gameWidth;
	int gameHeight;
	
	//view thread
	private GameViewThread viewThread;
	
	public Player player;
	

	public gameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		viewThread = new GameViewThread(this);
	}
	
	//draw to screen
	public void doDraw (Canvas canvas){
		//Log.d("Made it", "GameView doDraw");
		canvas.drawColor(Color.GRAY);
		
        // Draw character
		player.doDraw(canvas);
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
	
}
