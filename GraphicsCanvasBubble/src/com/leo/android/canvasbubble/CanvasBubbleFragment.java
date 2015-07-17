package com.leo.android.canvasbubble;

import java.util.Random;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
/**
 * 
 * @author lj
 * 
 * generate a bubble moving on the screen.
 * create a customer view extends View
 */
public class CanvasBubbleFragment extends Fragment {
	private static final String TAG = "CanvasBubbleFragment";
	private BubbleView mBubbleView;
	private FrameLayout mLayout;
	private Thread mThread;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_canvas_bubble, container, false);
		mLayout = (FrameLayout) view.findViewById(R.id.frame);
		mLayout.setBackgroundColor(Color.GRAY);
		
		//convert a drawable to a bitmap
		Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.b128);
		mBubbleView = new BubbleView(getActivity(), bitmap);
		
		//add a BubbleView to the layout.
		mLayout.addView(mBubbleView);
		
		//create a new thread 
		mThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//if the bubble move on the screen, re-draw. 
				while(mBubbleView.move()){
					Log.i(TAG, "Bubble move.");
					mBubbleView.postInvalidate();
					try {
						Thread.sleep(1000);   /*sleep one second*/
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						Log.e(TAG, "Failed to interrupt.");
					}
				}
			}
		});
		mThread.start();
		
		return view;
	}
	
	//create a customer view
	private class BubbleView extends View {
		private static final int STEP = 50;
		private Coords mCurrent;
		private Paint mPaint;
		final private Coords mDxDy;
		final private DisplayMetrics mDisplayMetrics;
		final private int mDisplayWidth;
		final private int mDisplayHeight;
		final private float mBitmapWidthAndHeight, mBitmapWidthAndHeightAdj;
		private Bitmap mBitmap;

		public BubbleView(Context context, Bitmap bitmap) {
			super(context);
//			mBitmapWidthAndHeight 
//				= getActivity().getResources().getDimension(R.dimen.image_width);
			mBitmapWidthAndHeight = bitmap.getHeight();
			mBitmapWidthAndHeightAdj = mBitmapWidthAndHeight + 20;
			mBitmap = bitmap;
			
			//get the screen dimension.
			mDisplayMetrics = new DisplayMetrics();
			getActivity().getWindowManager()
						.getDefaultDisplay()
						.getMetrics(mDisplayMetrics);
			mDisplayHeight = mDisplayMetrics.heightPixels;
			mDisplayWidth = mDisplayMetrics.widthPixels;
			
			//generate a random coords for BubbleView.
			Random r = new Random();
			float x = (float)r.nextInt(mDisplayWidth);
			float y = (float)r.nextInt(mDisplayWidth);
			mCurrent = new Coords(x, y);
			
			//generate a move-step for BubbleView
			float dx = (float)r.nextInt(mDisplayWidth)/mDisplayWidth;
			float dy = (float)r.nextInt(mDisplayHeight)/mDisplayHeight;
			Log.i(TAG, "dx:" + dx + " dy: " + dy);
			
			dx *= r.nextInt(2) == 1 ? STEP : -1 * STEP;
			dy *= r.nextInt(2) == 1 ? STEP : -1 * STEP;
			
			mDxDy = new Coords(dx, dy);
			Log.i(TAG, "dxdy: " + mDxDy);
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
		}
		
		
		//override onDraw() method, re-draw the bitmap
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
//			Coords temp = mCurrent.getCoords();
			Log.i(TAG, "onDraw: " + mCurrent);
			canvas.drawBitmap(mBitmap, mCurrent.getX(), mCurrent.getY(), mPaint);
		}
		
		//
		public boolean move(){
			mCurrent = mCurrent.move(mDxDy);
			//if the BubbleView moves out of screen, return false.
			if(mCurrent.getX() < 0 - mBitmapWidthAndHeightAdj
					|| mCurrent.getX() > mDisplayWidth + mBitmapWidthAndHeightAdj
					|| mCurrent.getY() < 0 - mBitmapWidthAndHeightAdj
					|| mCurrent.getY() > mDisplayHeight + mBitmapWidthAndHeightAdj){
				return false;
			}
			return true;
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		
	}
	
	
	
}
