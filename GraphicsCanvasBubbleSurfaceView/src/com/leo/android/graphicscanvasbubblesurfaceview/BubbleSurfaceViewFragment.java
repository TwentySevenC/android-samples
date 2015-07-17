package com.leo.android.graphicscanvasbubblesurfaceview;

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
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 
 * @author lj
 *
 *	this android sample introduces how to use safaceView
 *	a bubble appear to the screen randomly, move and rotate ,then disappear when we can't see it
 */
public class BubbleSurfaceViewFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = "BubbleSurfaceViewFragment";
	private BubbleView mBubbleView;
	private RelativeLayout mLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_bubble_surface_view, container, false);
		mLayout = (RelativeLayout)view.findViewById(R.id.frame);
		//use BitmapFactory get the drawable
		mBubbleView = new BubbleView(getActivity(), BitmapFactory.decodeResource(getResources(), R.drawable.b128));
		//remember to add customer view into the container view
		mLayout.addView(mBubbleView);
		return view;
	}
	
	//define customer views
	private class BubbleView extends SurfaceView implements SurfaceHolder.Callback{
		private final DisplayMetrics mDisplayMetrics;
		private final int mDisplayWidth, mDisplayHeight;
		private final float mImageHeightAndWidth, mImageHeightAndWidthAdj;
		
		private final SurfaceHolder mSurfaceHolder;
		private Thread mDrawingThread;
		private float mX, mY, mDx, mDy, mRotate;
		private final Paint mPaint;
		private final Bitmap mBitmap;
		
		private static final int STEP = 1;
		private static final float ROTATE = 1.0F;
		

		public BubbleView(Context context, Bitmap bitmap) {
			super(context);
			
			mImageHeightAndWidth = getActivity().getResources().getDimension(R.dimen.image_height);
			mImageHeightAndWidthAdj = mImageHeightAndWidth / 2 ;
			
			//create scale bitmap 
			mBitmap = Bitmap.createScaledBitmap(bitmap, (int)mImageHeightAndWidth, (int)mImageHeightAndWidth, false);
			
			//get the dimension of the screen 
			mDisplayMetrics = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
			mDisplayHeight = mDisplayMetrics.heightPixels;
			mDisplayWidth = mDisplayMetrics.widthPixels;
			
			
			//generate a coord and the dx,dy.
			Random random = new Random();
			mX = (float)random.nextInt(mDisplayWidth);
			mY = (float)random.nextInt(mDisplayHeight);
			
			mDx = (float)random.nextInt(mDisplayWidth)/mDisplayWidth;
			mDx *= random.nextInt(2) == 1 ? STEP : -1 * STEP;
			mDy = (float)random.nextInt(mDisplayHeight)/mDisplayHeight;
			mDx *= random.nextInt(2) == 1 ? STEP : -1 * STEP;
			mRotate = 1.0f;
			
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			
			//get the surface hodler
			mSurfaceHolder = getHolder();
			mSurfaceHolder.addCallback(this);
		}
		
		private void drawBubble(Canvas canvas){
			/*
			 * Note: On each pass you retrieve the Canvas from the SurfaceHolder, 
			 * the previous state of the Canvas will be retained. In order to properly 
			 * animate your graphics, you must re-paint the entire surface. For example, 
			 * you can clear the previous state of the Canvas by filling in a color with 
			 * drawColor() or setting a background image with drawBitmap(). Otherwise, 
			 * you will see traces of the drawings you previously performed.
			 */
			canvas.drawColor(Color.DKGRAY);
			
			canvas.rotate(mRotate, mX + mImageHeightAndWidthAdj, mY + mImageHeightAndWidthAdj);
			canvas.drawBitmap(mBitmap, mX, mY, mPaint);
			
		}
		
		//Calculate new coords, if the bubble exceed the screen, return false, otherwise, return true.
		private boolean move(){
			mX += mDx;
			mY += mDy;
			mRotate += ROTATE;
			
			//
			if(mX < 0 - mImageHeightAndWidth 
					|| mX > mDisplayWidth + mImageHeightAndWidth
					|| mY < 0 - mImageHeightAndWidth
					|| mY > mDisplayHeight + mImageHeightAndWidth){
				return false;
			}
			return true;
		}

		/**
		 * Three lifecycle methods within SurfaceHolder.Callback.
		 */
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			mDrawingThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Canvas canvas = null;
					while(move() && !Thread.currentThread().isInterrupted()){
						canvas = mSurfaceHolder.lockCanvas();    /*lockCanvas() get a canvas.*/
						if(canvas != null){
							drawBubble(canvas);
							mSurfaceHolder.unlockCanvasAndPost(canvas);  /*unlock the canvas after re-draw*/
						}
					}
					
				}
			});
			mDrawingThread.start();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			if(mDrawingThread != null){
				mDrawingThread.interrupt();    /*interrupt the thread when safaceView destroyed*/
			}
		}
		
	}
	
}
