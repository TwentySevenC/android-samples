package com.example.javastatepatterndemo;

public class NoDollarState implements State {
	private CandyMachineActivity mCandyMachine;
	
	public NoDollarState(CandyMachineActivity candyMachine) {
		mCandyMachine = candyMachine;
	}
	
	@Override
	public void insertDollar() {
		mCandyMachine.showToast(R.string.nodollar_state_insert_dollar);
		
		mCandyMachine.setCurrentState(mCandyMachine.getHasDollarState());
	}

	@Override
	public void ejectDollar() {
		
		mCandyMachine.showToast(R.string.nodollar_state_eject_dollar);
	}

	@Override
	public void candyGo() {
		mCandyMachine.showToast(R.string.nodollar_state_candy_go);

	}

}
