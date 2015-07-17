package com.leo.android.graphicsshapedrawxml;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 
 * @author lj
 * use xml to create two shapeDrawable
 */
public class GraphicsShapeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_shape);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graphics_shape, menu);
        return true;
    }
    
}
