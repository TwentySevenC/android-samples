/**
 * 
 */
package com.leo.android.audiovideocamera;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author lj
 *
 */
public abstract class SingleFragmentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.fragment_activity);
		
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);
		
		
		/**
		 * if fragment is null, create a new fragment and add it into activity
		 */
		if(null == fragment){
			fragment = getFragment();
			fm.beginTransaction()
			  .add(R.id.fragment_container, fragment)
			  .commit();
		}
	}
	
	/**
	 * use factory-method pattern to create a fragment, 
	 * this activity need not know what the fragment that added to it actually is.
	 * 
	 * @return  a kind of fragment
	 */
	protected abstract Fragment getFragment();

}
