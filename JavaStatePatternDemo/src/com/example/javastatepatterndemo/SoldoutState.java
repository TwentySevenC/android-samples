package com.example.javastatepatterndemo;

public class SoldoutState implements State {
	private CandyMachineActivity mCandyMachine;
	
	public SoldoutState(CandyMachineActivity candyMachine) {
		mCandyMachine = candyMachine;
	}
	
	@Override
	public void insertDollar() {
		mCandyMachine.showToast(R.string.soldout_state_insert_dollar);
	}	

	@Override
	public void ejectDollar() {
		mCandyMachine.showToast(R.string.soldout_state_eject_dollar);
	}

	@Override
	public void candyGo() {
		mCandyMachine.showToast(R.string.soldout_state_candy_go);
	}

}
