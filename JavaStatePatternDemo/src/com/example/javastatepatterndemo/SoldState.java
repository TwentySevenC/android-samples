package com.example.javastatepatterndemo;

public class SoldState implements State {
	private CandyMachineActivity mCandyMachine;
	
	public SoldState(CandyMachineActivity candyMachine) {
		mCandyMachine = candyMachine;
	}

	@Override
	public void insertDollar() {
		mCandyMachine.showToast(R.string.sold_state_insert_dollar);
	}

	@Override
	public void ejectDollar() {
		mCandyMachine.showToast(R.string.sold_state_eject_dollar);
	}

	@Override
	public void candyGo() {
		mCandyMachine.showToast(R.string.sold_state_candy_do);
		mCandyMachine.doGoOutCandy();
		
		if(mCandyMachine.getCount() > 0){
			
			mCandyMachine.setCurrentState(mCandyMachine.getNoDollarState());
			
		}else{
			
			mCandyMachine.setCurrentState(mCandyMachine.getSoldoutState());
			
		}
	}

}
