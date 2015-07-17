package com.leo.android.audiovideoringtonemanager;

import android.app.Activity;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 
 * @author lj
 * the use of RingtoneManager
 * getDefaultUri(int type)
 * getRingtone(Uri uri)
 */
public class RingtoneManagerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ringtone_manager);
		//get the reference of the ringtone button
		final Button ringtoneButton = (Button)findViewById(R.id.btn_ringtone);
		//set a OnClickListener on the button
		ringtoneButton.setOnClickListener(new RtClickListener());
		
		//get the reference of the notification button
		final Button notificationButton = (Button)findViewById(R.id.btn_notification);
		//set a OnClickListener on the button
		notificationButton.setOnClickListener(new RtClickListener());
		
		//get the reference of the alarm button
		final Button alarmButton = (Button)findViewById(R.id.btn_alarm);
		//set a OnClickListener on the button
		alarmButton.setOnClickListener(new RtClickListener());
		
		
	}
	
	
	//define a OnClickListener to handle three types' ringtone 
	private class RtClickListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.btn_ringtone:
				//gets the Uri for the default ringtone of a particular type
				Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
				//plays the ringtone
				playRingtone(ringtoneUri);
				break;
			case R.id.btn_notification:
				Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				playRingtone(notificationUri);
				break;
			case R.id.btn_alarm:
				Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
				playRingtone(alarmUri);
				break;
				
			default:
				break;
			}
		}
		
	}
	
	/**
	 * play a ringtone
	 * 
	 * @param ringtoneUri Uri for the ringtone of a particular type
	 */
	private void playRingtone(Uri ringtoneUri){
		RingtoneManager.getRingtone(this, ringtoneUri).play();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ringtone_manager, menu);
		return true;
	}

}
