package com.linden.sp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Splash extends View{

	Bitmap firstCar;
	Bitmap copCar;
	Bitmap logo;
	Bitmap splashBackground;
	Bitmap bMapScaled;

	//set up x y coordinates 
	int xC1=-800;
	int xC2=-1800;
	int xLogo;
	boolean start=true;
	
	
	int yCar=150;
	int yLogo=0;
	
	//variable locations on the screen
	int middle;
	int bottom;
	int adder=0;
	Paint paint = new Paint();
	 
	
	public Splash(Context contextSplash) {
		super(contextSplash);

		
		firstCar = BitmapFactory.decodeResource(getResources(), R.drawable.smallsti);
		copCar = BitmapFactory.decodeResource(getResources(), R.drawable.smallcop);
		logo = BitmapFactory.decodeResource(getResources(), R.drawable.smalllogo);
		
		//splashBackground = BitmapFactory.decodeResource(getResources(), R.drawable.splashbackground);
		//bMapScaled = Bitmap.createScaledBitmap(splashBackground, 900, 500, false);
		//mBitmap = Bitmap.createScaledBitmap(image, sketchBoard.getWidth(), sketchBoard.getHeight(), true)
		//getResizedBitmap(splashBackground, getHeight(), getWidth());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		//draw background in location (0,0)
		//splashBackground(0, 0, canvas);

		//find middle of the screen
		middle = canvas.getHeight()/2;
		xLogo = canvas.getWidth()/5;
		


		//move first car to right
		if (xC1 < canvas.getWidth()){
			//draw blue sti
			drawFirstCar(xC1,yCar, canvas);
			xC1 = xC1+25;
			invalidate();
		}
		
		//move second car to right
		else if (xC2 < canvas.getWidth()){
			drawCopCar(xC2, yCar, canvas);
			xC2 = xC2+25;
			invalidate();
		}
		
		//bring logo to center of the screen
		else if (adder < middle){			
			drawLogo(xLogo, yLogo, canvas);
			adder = adder + 2;
			invalidate();
		} 
		
		//move on to main menu
		else{
			copCar.recycle();
			logo.recycle();
			firstCar.recycle();
			
			
			Intent intent = new Intent(getContext(), MainMenu.class);
            ((Activity)getContext()).startActivity(intent);
		}

	}
	private void drawLogo(int x, int y, Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(logo, x, y, paint);
	}

	private void drawCopCar(int x, int y, Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(copCar, x, y, paint);
	}

	private void drawFirstCar(int x, int y, Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(firstCar, x, y, paint);
	}

}
