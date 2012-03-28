package com.linden.sp;

import com.linden.sp.R;
import com.linden.sp.ImageAdapterCarSelect;
import com.linden.sp.game.Engine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class CarSelect extends Activity{
boolean flag = false;
int carNumber;
int difficulty;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//no notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		//set view CarSelect.xml
		setContentView(R.layout.carselect);
		
		//need to take in level difficulty value	
		Log.d("difficulty ", " "+ difficulty);
		
		//OK button setup
		Button btnOK = (Button) findViewById (R.id.btnOK);
		btnOK.setOnClickListener(new View.OnClickListener() {
		


			public void onClick(View v) {
				if (flag){
					Intent intent = new Intent(CarSelect.this, Engine.class);
					Bundle bundle = new Bundle();
		
					// send which level and difficulty data to new activity
					bundle.putInt("car", (carNumber));
					bundle.putInt("difficulty", (difficulty));
					intent.putExtras(bundle);
					startActivity(intent);
				}
				else{
					Toast.makeText(CarSelect.this, "Select a car first", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		
		
		//car select setup	
		Gallery gallery = (Gallery) findViewById(R.id.carselect);
	    gallery.setAdapter(new ImageAdapterCarSelect(this));

	    gallery.setOnItemClickListener(new OnItemClickListener() {
	           public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	   flag = true;
	        	   TextView SelectCar = (TextView)findViewById (R.id.selectedCar);
	        	   TextView Health = (TextView)findViewById (R.id.Health);
	        	   TextView Speed = (TextView)findViewById (R.id.Speed);
	        	   TextView Handling = (TextView)findViewById (R.id.Handling);
	        	   TextView Special = (TextView)findViewById (R.id.Special);
	        	   
	        	   //Rabbit 
	        	   if (position == 0 ){
	        		   
	        		 //set labels
	        		   SelectCar.setText("Rabbit");
	        		   Health.setText("2");
	        		   Speed.setText("1");
	        		   Handling.setText("2");
	        		   Special.setText(" ");
	        		   
	        		   //car selected
	        		   carNumber=position;
	        	   }
	        	   
	        	   //Del Sol
	        	   else if  (position == 1 ){
	        		   
	        		 //set labels
	        		   SelectCar.setText("Del Sol");
	        		   Health.setText("1");
	        		   Speed.setText("1");
	        		   Handling.setText("3");
	        		   Special.setText(" ");
	        		   
	        		 //car selected
	        		   carNumber=position;
	        	   }
	        	   
	        	   //Jeep
	        	   else if (position == 2 ){
	        		   
	        		 //set labels
	        		   SelectCar.setText("Jeep");
	        		   Health.setText("2");
	        		   Speed.setText("2");
	        		   Handling.setText("2");
	        		   Special.setText(" ");
	        		   
	        		 //car selected
	        		   carNumber=position;
	        	   }
	        	   
	        	   //Pickup
	        	   else if (position == 3 ){
	        		   
	        		 //set labels
	        		   SelectCar.setText("Pickup");
	        		   Health.setText("6");
	        		   Speed.setText("2");
	        		   Handling.setText("2");
	        		   Special.setText(" ");
	        		   
	        		 //car selected
	        		   carNumber=position;
	        	   }
	        	   
	        	   //BMW
	        	   else if (position == 4 ){
	        		   
	        		 //set labels
	        		   SelectCar.setText("BMW");
	        		   Health.setText("5");
	        		   Speed.setText("4");
	        		   Handling.setText("4");
	        		   //1.5% point mod
	        		   Special.setText("Rich Mans car");
	        		   
	        		 //car selected
	        		   carNumber=position;
	        	   }
	        	   //911
	        	   else if (position == 5 ){
	        		   
	        		 //set labels
	        		   SelectCar.setText("911");
	        		   Health.setText("4");
	        		   Speed.setText("5");
	        		   Handling.setText("5");
	        		   Special.setText(" ");
	        		   
	        		 //car selected
	        		   carNumber=position;
	        	   }
	        	   
	        	   //STI
	        	   else if (position == 6 ){
	        		   
	        		   //set labels
	        		   SelectCar.setText("STI");
	        		   Health.setText("5");
	        		   Speed.setText("6");
	        		   Handling.setText("6");
	        		   Special.setText(" ");
	        		   
	        		 //car selected
	        		   carNumber=position;
	        	   }
	        	   
	        	   
	        	   
	            }

	  
	        });
	};
}