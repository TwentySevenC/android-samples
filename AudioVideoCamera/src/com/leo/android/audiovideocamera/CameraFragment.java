package com.leo.android.audiovideocamera;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CameraFragment extends Fragment {
	private static final String TAG = "CameraFragment";
	private Button mBtnCamera;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_camera, container, false);
		mBtnCamera = (Button)view.findViewById(R.id.take_picture);
		return view;
	}

}
