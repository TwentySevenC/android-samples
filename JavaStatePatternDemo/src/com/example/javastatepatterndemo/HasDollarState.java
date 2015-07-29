package com.example.javastatepatterndemo;

import java.util.Random;

public class HasDollarState implements State {
	private CandyMachineActivity mCandyMachine;
	private Random mRandom = new Random(System.currentTimeMillis());
	
	public HasDollarState(CandyMachineActivity candyMachine) {
		mCandyMachine = candyMachine;
	}

	@Override
	public void insertDollar() {
		mCandyMachine.showToast(R.string.hasdollar_state_insert_dollar);
	}

	@Override
	public void ejectDollar() {
		
		mCandyMachine.showToast(R.string.hasdollar_state_eject_dollar);
		mCandyMachine.setCurrentState(mCandyMachine.getNoDollarState());

	}

	@Override
	public void candyGo() {
		mCandyMachine.showToast(R.string.hasdollar_state_candy_go);
		
		if(mRandom.nextInt(10) == 0){
//			mCandyMachine.setCurrentState(mCandyMachine.getWinState());
			mCandyMachine.getWinState().candyGo();
		}else{
			
//			mCandyMachine.setCurrentState(mCandyMachine.getSoldState());
			mCandyMachine.getSoldState().candyGo();
		}
	}

}
