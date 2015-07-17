package com.leo.android.touchindicatetouchlocation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 
 * @author lj
 * 
 * Sample introduce: put a finger on the android phone's or tablet's screen, it will generate a cycle whose color is random.
 * put more fingers on the screen, it will generate more cycles. but the size of cycles becomes smaller and smaller.
 */
public class TouchLocationFragment extends Fragment {
	private static final String TAG = "TouchLocationFragment";
	private static final int MAX_POINT = 20;
	private LinkedList<MarkerView> mInactiveMarkerView;
	private HashMap<Integer, MarkerView> mActiveMarkerView;
	private FrameLayout mFrame;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_touch_location, container, false);
		//get the frameLayout view
		mFrame = (FrameLayout) view.findViewById(R.id.frame);
		initViews();
		//set OnTouchListener...
		mFrame.setOnTouchListener(new OnTouchListener() {
			
			/**
			 * this warning depicts a problem: when a view set a onTouchListener, meanwhile, it set a onClickListener 
			 * the touchEvent cover up the onClickListener.
			 * invoke the performClick method, it will Call this view's OnClickListener, if it is defined
			 * */
			@Override
			public boolean onTouch(View v, MotionEvent event) {  
//				v.performClick();
				int action = event.getActionMasked();
				switch (action) {
				//if a finger point down, generate a MarketView on the screen.
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN: {
					int pointerIndex = event.getActionIndex();
					int pointerId = event.getPointerId(pointerIndex);
					
					//take a markerView from mInacticveMarkerView to mActiveMarkerView, and update the touches in the screen.
					MarkerView markerView = mInactiveMarkerView.remove();
					if(markerView != null){
						mActiveMarkerView.put(pointerId, markerView);
						
						markerView.setX(event.getX(pointerIndex));
						markerView.setY(event.getY(pointerIndex));
						
						updateMarkerView(mActiveMarkerView.size());
						mFrame.addView(markerView);
					}
					return true;
				}
				
				//change the coordination of MarkerViews and redraw the FrameLayout	
				case MotionEvent.ACTION_MOVE: {
					Log.i(TAG, "ACTION_MOVE");
					for(int idx = 0; idx < event.getPointerCount(); idx++){
						int pointerId = event.getPointerId(idx);
						MarkerView markerView = mActiveMarkerView.get(pointerId);
						
						if(null != markerView){
							/*
							 * only if the distance that pointer moves is bigger than a value(such as 2.0), 
							 * the pointer will reset its coords.
							 */
							if(Math.abs(markerView.getX()-event.getX(idx)) > 2.0f 
									|| Math.abs(markerView.getY() - event.getY(idx)) > 2.0f){
								markerView.setX(event.getX(idx));
								markerView.setY(event.getY(idx));
							}
							
							//MarkerView re-draw, not re-draw frame.
							markerView.invalidate();
						}
					}
					return true;
				}	
				//if a finger up, the frame remove the MarkerView, and redraw itself.	
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:{
					int pointerIndex = event.getActionIndex();
					int pointerId = event.getPointerId(pointerIndex);
					
					MarkerView markerView = mActiveMarkerView.remove(pointerId);
					if(null != markerView){
						mInactiveMarkerView.add(markerView);
						updateMarkerView(mActiveMarkerView.size());
						mFrame.removeView(markerView);  /*the method removeView() lead to re-draw ?*/
					}
					return true;
				}
				default:
					return false;
				}
			}
		});
		return view;
	}
	
	/**
	 * define a linkedList containing 20 MarkerViews
	 */
	@SuppressLint("UseSparseArrays")
	private void initViews(){
		mInactiveMarkerView = new LinkedList<MarkerView>();
		mActiveMarkerView = new HashMap<Integer, MarkerView>();
		for(int i = 0; i < MAX_POINT; i++){
			mInactiveMarkerView.add(new MarkerView(getActivity()));
		}
	}
	/*
	 * update the touches number.
	 */
	private void updateMarkerView(int numActive){
		for(MarkerView markerView : mActiveMarkerView.values()){
			markerView.setTouches(numActive);
		}
	}
	
	private class MarkerView extends View {
		private static final float MAX_RADIUS = 400.0f;  /*the max radius*/
		private int mTouches = 0;
		private float mX, mY;
		private final Paint mPaint = new Paint();

		public MarkerView(Context context){
			super(context);
			
			Random random = new Random();  /*set a random color for the paint*/
			mPaint.setStyle(Style.FILL);
			mPaint.setARGB(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
		}
		

		public float getX() {
			return mX;
		}

		public void setX(float x) {
			mX = x;
		}

		public float getY() {
			return mY;
		}

		public void setY(float y) {
			mY = y;
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawCircle(mX, mY, MAX_RADIUS/mTouches, mPaint);
		}
		
		public void setTouches(int touches){
			mTouches = touches;
		}

	
	}

}
