package com.leo.android.audiovideocamera;

import android.app.Fragment;

public class CameraActivity extends SingleFragmentActivity {


	@Override
	protected Fragment getFragment() {
		// TODO Auto-generated method stub
		return new CameraFragment();
	}


}
