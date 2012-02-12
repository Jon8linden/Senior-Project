package com.linden.sp.game;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class gameView extends SurfaceView{
	int width;
	int height;
	
	
	public Player player;
	
	public gameView(Context context) {
		super(context);
		
	}
	
	public void surfaceCreated(SurfaceHolder holder){
		width = this.getWidth();
		height = this.getHeight();
		
		//create player
		player = new Player(getResources(), (int) width/2, (int) height/2);
	}
}
