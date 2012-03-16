package com.linden.sp;

import com.linden.sp.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapterCarSelect extends BaseAdapter {
    int mGalleryItemBackground;
    private Context setContext;
    
    
    
    
    
    private Integer[] carImages = {
            R.drawable.rabbittrimed,
            R.drawable.gallerydelsol,
            R.drawable.jeep,
            //truck
            R.drawable.icon,
            R.drawable.gallerybmw,
            R.drawable.porschetrimed,
            R.drawable.stiback
            
    };

    public ImageAdapterCarSelect(Context set) {
    	setContext = set;
        
        
        //adds built in borders and spacing
        TypedArray attr = setContext.obtainStyledAttributes(R.styleable.carSelect);
        mGalleryItemBackground = attr.getResourceId(R.styleable.carSelect_android_galleryItemBackground, 0);
        attr.recycle();
    }

    public int getCount() {
        return carImages.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(setContext);

        imageView.setImageResource(carImages[position]);

        
        //sets how big the image is
        imageView.setLayoutParams(new Gallery.LayoutParams(320, 206));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(mGalleryItemBackground);

        return imageView;
    }
}