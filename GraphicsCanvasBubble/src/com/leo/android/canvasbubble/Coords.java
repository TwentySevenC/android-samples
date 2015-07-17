package com.leo.android.canvasbubble;

public class Coords {
	private float mX;
	private float mY;
	
	public Coords(float x, float y) {
		mX = x;
		mY = y;
	}
	
	public float getX() {
		return mX;
	}

	public float getY() {
		return mY;
	}

	synchronized public Coords move(Coords dxdy){
		return new Coords(mX + dxdy.mX, mY + dxdy.mY);
	}
	
	synchronized public Coords getCoords(){
		return new Coords(mX, mY);
	}
	
	public String toString(){
		return "[" + mX + ", " + mY + "]";
	}
}
