package com.leo.android.graphicstransitiondrawable;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.TransitionDrawable;
import android.view.Menu;
import android.widget.ImageView;

public class TransitonDrawableActivity extends Activity {

	private ImageView mImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transiton_drawable);
		mImageView = (ImageView)findViewById(R.id.transiton_image);
		TransitionDrawable drawable = (TransitionDrawable) mImageView.getDrawable();
		drawable.setCrossFadeEnabled(true);
		drawable.startTransition(5000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transiton_drawable, menu);
		return true;
	}

}
