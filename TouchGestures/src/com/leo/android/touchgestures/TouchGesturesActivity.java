package com.leo.android.touchgestures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
/**
 * 
 * @author lj
 *	detect customer gestures
 */
public class TouchGesturesActivity extends Activity {
	private static final String NEXT = "Next";
	private static final String YES = "Yes";
	private static final String NO = "No";
	private static final String PREV = "Prev";
	private RelativeLayout mLayout;
	private FrameLayout mFrame;
	private GestureLibrary mLibrary;
	private GestureOverlayView mGestureOverlayView;
	private int mFirstColor = 0xffff0000, mBgColor = Color.GRAY;
	LinkedList<Integer> mColorList = new LinkedList<Integer>();
	Random mRandom = new Random();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_gestures);
		
		mGestureOverlayView = (GestureOverlayView)findViewById(R.id.gesture_view);
		mLayout = (RelativeLayout)findViewById(R.id.layout_container);
		mLayout.setBackgroundColor(mBgColor);
		mFrame = (FrameLayout)findViewById(R.id.frame);
		mFrame.setBackgroundColor(mFirstColor);
		
		//use GestureLibraries's fromRawResource() method get gestures file
		mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		//
		if(!mLibrary.load()){
			finish();   /*load gestures, if failed, finish activity*/
		}
		

		
		mGestureOverlayView.addOnGesturePerformedListener(new OnGesturePerformedListener() {
			
			@Override
			public void onGesturePerformed(GestureOverlayView view, Gesture gesture) {
				// TODO Auto-generated method stub
				//recognize gestures, the return value is a key-value list; key-gesture name value- score
				ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
				
				if(predictions.size() > 0){
					String name = predictions.get(0).name;
					double score = predictions.get(0).score;
					if(score > 2.0){
						if(name.equals(PREV)){
							if(mColorList.size() > 0){
								mFrame.setBackgroundColor(mColorList.remove());
							}
//							Toast.makeText(getApplicationContext(), PREV, Toast.LENGTH_SHORT).show();
						}else if(name.equals(NEXT)){
							mColorList.add(((ColorDrawable)mFrame.getBackground()).getColor());
							int color = mRandom.nextInt(0xffffff)|0xff000000;
							mFrame.setBackgroundColor(color);
//							Toast.makeText(getApplicationContext(), NEXT, Toast.LENGTH_SHORT).show();
						}else if(name.equals(YES)){
							mLayout.setBackgroundColor(((ColorDrawable)mFrame.getBackground()).getColor());
//							Toast.makeText(getApplicationContext(), YES, Toast.LENGTH_SHORT).show();
						}else if(name.equals(NO)){
							mLayout.setBackgroundColor(mBgColor);
//							Toast.makeText(getApplicationContext(), NO, Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
						}
					}
				}else{
					Toast.makeText(getApplicationContext(), "No prediction.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.touch_gestures, menu);
		return true;
	}

}
