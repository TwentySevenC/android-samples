package com.example.javastatepatterndemo;
/**
 * A Candy Machine. Use java state pattern
 * Five state : No dollar, Has dollar, Sold, Sold out, Win.
 * Three operation : Insert dollar  eject dollar   candy go
 * 
 *              Insert dollar       eject dollar        candy go          (inner translations)
 *                                 
 *  no dollar    has dollar              --               --                
 *                                 
 *  has dollar      --               no dollar           sold                            
 *   
 *  sold            --                   --               --               no dollar(candy > 0)/sold out (candy < 0)
 * 
 *  win             --                   --               --               no dollar(candy > 0)/sold out (candy < 0)
 * 
 *  sold out        --                   --               --
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CandyMachineActivity extends Activity {
	
	private State mNoDollarState;
	private State mHasDollarState;
	private State mSoldState;
	private State mSoldoutState;
	private State mWinState;
	
	private State mCurrentState;
	private int mCount;
	
	private Button mBtnInsertDollar;
	private Button mBtnEjectDollar;
	private Button mBtnCandyGo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_candy_go);
		
		mCount = 10;
		
		//initialize state
		initState();
		
		mBtnInsertDollar = (Button)findViewById(R.id.insert_dollar);
		mBtnEjectDollar = (Button)findViewById(R.id.eject_dollar);
		mBtnCandyGo = (Button)findViewById(R.id.candy_out);
		
		mBtnInsertDollar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentState.insertDollar();
			}
		});
		
		
		mBtnEjectDollar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentState.ejectDollar();
			}
		});
		
		mBtnCandyGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentState.candyGo();
			}
		});
		
	}
	
	
	void showToast(int id){
		Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
	}
	
	
	void doGoOutCandy(){
		if(mCount > 0){
			mCount = mCount - 1;
		}
	}
	
	
	void initState(){
		mNoDollarState = new NoDollarState(this);
		mHasDollarState = new HasDollarState(this);
		mSoldoutState = new SoldoutState(this);
		mSoldState = new SoldState(this);
		mWinState = new WinState(this);
		
		mCurrentState = mNoDollarState;
	}
	
	
	//----------------------------

	public State getCurrentState() {
		return mCurrentState;
	}


	public State getNoDollarState() {
		return mNoDollarState;
	}


	public State getHasDollarState() {
		return mHasDollarState;
	}


	public State getSoldState() {
		return mSoldState;
	}


	public State getSoldoutState() {
		return mSoldoutState;
	}


	public State getWinState() {
		return mWinState;
	}


	public void setCurrentState(State currentState) {
		this.mCurrentState = currentState;
	}


	public int getCount() {
		return mCount;
	}
	
	
	
}
