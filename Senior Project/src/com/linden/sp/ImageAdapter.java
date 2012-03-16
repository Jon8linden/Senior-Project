package com.linden.sp;
import com.linden.sp.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;



public class ImageAdapter extends BaseAdapter{
	private Context setContext;
	
	
	public ImageAdapter(Context set){
		setContext = set;

	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        ImageView grid;
        if (convertView == null){
            grid = new ImageView(setContext);
            grid.setLayoutParams(new GridView.LayoutParams(150, 150));
            grid.setScaleType(ImageView.ScaleType.FIT_CENTER);
            
            //space around level images
            grid.setPadding(5, 5, 5, 5);
        }
        else {
            grid = (ImageView) convertView;
        }

        grid.setImageResource(Images[position]);
        return grid;
    }
	
	//level thumbnails
	private Integer[] Images = {R.drawable.lvl1, R.drawable.lvl2, R.drawable.lvl3, 
			R.drawable.lvl4, R.drawable.icon,R.drawable.icon,R.drawable.icon,
			R.drawable.icon};

	public int getCount() {
		return Images.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}



}

