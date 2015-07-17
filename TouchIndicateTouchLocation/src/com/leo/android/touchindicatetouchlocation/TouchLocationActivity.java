package com.leo.android.touchindicatetouchlocation;

import android.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;

public class TouchLocationActivity extends SingleFragmentActivity {
	private static final String TAG = "TouchLocationActivity";

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onTouchEvent");
		return super.onTouchEvent(event);
	}

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new TouchLocationFragment();
	}


}
