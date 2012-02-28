package com.linden.sp.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread{
	private gameView view;
	private SurfaceHolder holder;
	private long totalTimeRunning;
	private long startTime;
	
	//set run to false on start
	private boolean running = false;
	
	public GameViewThread(gameView gview){
		view = gview;
		holder = view.getHolder();
	}
	
	public void setRun(boolean run){
		running = run;
	}
	
	public void run() {
		//Log.d("Made it", "GameViewThread");
		Canvas canvas = null;
		startTime = System.currentTimeMillis();
		
		while (running) {
			canvas = holder.lockCanvas();
			
			if (canvas != null){
            	//moves player
				view.animation(totalTimeRunning);
				view.doDraw(canvas);
				
				holder.unlockCanvasAndPost(canvas);
				totalTimeRunning = System.currentTimeMillis()-startTime;
			}
			
			startTime = System.currentTimeMillis();
		}
	}
}
