package com.linden.sp;
import com.linden.sp.game.Engine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;



public class Career extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//no notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//set view CarSelect.xml
		setContentView(R.layout.career);
		
		
		
		//Grid layout
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));
		

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// Create intent & bundle data from Career to Engine
				Intent intent = new Intent(Career.this, Engine.class);
				Bundle bundle = new Bundle();
	
				// send which level data to new activity
				bundle.putInt("level", (position+1));
				intent.putExtras(bundle);
				startActivity(intent);
				
				
			}
			
		});

		
	}

}
