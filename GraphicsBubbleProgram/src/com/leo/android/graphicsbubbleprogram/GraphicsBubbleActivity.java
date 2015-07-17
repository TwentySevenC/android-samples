package com.leo.android.graphicsbubbleprogram;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
/**
 * 
 * @author lj
 *	use java code not xml to create a imageView
 */
public class GraphicsBubbleActivity extends Activity {
	private RelativeLayout mLayout;
	private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_bubble);
        
        mLayout = (RelativeLayout)findViewById(R.id.frame);
        mImageView = new ImageView(this);
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.b128));
        
        //get the dimension 
        int width = (int)getResources().getDimension(R.dimen.bubble_width);
        int height = (int)getResources().getDimension(R.dimen.bubble_height);
        //set params
        RelativeLayout.LayoutParams params = 
        		new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        
        mImageView.setLayoutParams(params);
        mLayout.addView(mImageView);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graphics_bubble, menu);
        return true;
    }
    
}
