package com.example.javastatepatterndemo;

public class WinState implements State {
	private CandyMachineActivity mCandyMachine;
	
	public WinState(CandyMachineActivity candyMachine) {
		mCandyMachine = candyMachine;
	}
	
	@Override
	public void insertDollar() {
		mCandyMachine.showToast(R.string.win_state_insert_dollar);

	}

	@Override
	public void ejectDollar() {
		mCandyMachine.showToast(R.string.win_state_eject_dollar);

	}

	@Override
	public void candyGo() {
		
		mCandyMachine.showToast(R.string.win_candy);
		mCandyMachine.doGoOutCandy();
		
		if(mCandyMachine.getCount() > 0){
			
			mCandyMachine.doGoOutCandy();
			if(mCandyMachine.getCount() > 0){
				
				mCandyMachine.setCurrentState(mCandyMachine.getNoDollarState());
			}else{
				
				mCandyMachine.setCurrentState(mCandyMachine.getSoldoutState());
			}
			
		}else{
			mCandyMachine.setCurrentState(mCandyMachine.getSoldoutState());
		}
	}

}
