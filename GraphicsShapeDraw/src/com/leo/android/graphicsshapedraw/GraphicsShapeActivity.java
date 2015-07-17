package com.leo.android.graphicsshapedraw;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
/**
 * 
 * @author lj
 * ShapeDrawable 
 */
public class GraphicsShapeActivity extends Activity {
	private RelativeLayout mLayout;
	private final int alpha = 127;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_shape);
        
        mLayout = (RelativeLayout)findViewById(R.id.frame);
        
        int width = (int)getResources().getDimension(R.dimen.image_width);
        int height = (int)getResources().getDimension(R.dimen.image_height);
        int padding = (int)getResources().getDimension(R.dimen.padding);
        //add a shapedrawable, color cyan 
        ShapeDrawable cyanShapeDrawable = new ShapeDrawable(new OvalShape());
        cyanShapeDrawable.getPaint().setColor(Color.CYAN);
        cyanShapeDrawable.setIntrinsicHeight(height);
        cyanShapeDrawable.setIntrinsicWidth(width);
        cyanShapeDrawable.setAlpha(alpha);
        
        ImageView cyanImageView = new ImageView(this);
        cyanImageView.setImageDrawable(cyanShapeDrawable);
        cyanImageView.setPadding(padding, padding, padding, padding);
//        cyanImageView.setBackgroundColor(Color.CYAN);
        
        RelativeLayout.LayoutParams cyanParams = new RelativeLayout.LayoutParams(width, height);
        cyanParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        cyanParams.addRule(RelativeLayout.CENTER_VERTICAL);
        cyanImageView.setLayoutParams(cyanParams);
        
        mLayout.addView(cyanImageView);
        
        //add another oval 
        ShapeDrawable megantaShapeDrawable = new ShapeDrawable(new OvalShape());
        megantaShapeDrawable.getPaint().setColor(Color.MAGENTA);
        megantaShapeDrawable.setIntrinsicHeight(height);
        megantaShapeDrawable.setIntrinsicWidth(width);
        megantaShapeDrawable.setAlpha(alpha);
        
        ImageView megantaImageView = new ImageView(this);
        megantaImageView.setImageDrawable(megantaShapeDrawable);
        megantaImageView.setPadding(padding, padding, padding, padding);
//        megantaImageView.setBackgroundColor(Color.MAGENTA);
        
        RelativeLayout.LayoutParams megantaParams = new RelativeLayout.LayoutParams(width, height);
        megantaParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        megantaParams.addRule(RelativeLayout.CENTER_VERTICAL);
        megantaImageView.setLayoutParams(megantaParams);
        
        mLayout.addView(megantaImageView);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graphics_shape, menu);
        return true;
    }
    
}
