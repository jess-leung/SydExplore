package au.com.sydexplore;

import au.com.sydexplore.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		// Create new thread 
		Thread splashThread = new Thread() {

			@Override
			public void run() {
				try {
					super.run();
					sleep(4000);  //Delay main screen by 4 seconds 
				} catch (Exception e) {

				} finally {
					// Start new intent to go to main activity
					Intent i = new Intent(SplashScreen.this,
							MainActivity.class);
					startActivity(i);
					finish();
				}
			}
		};
		// Start splash thread
		splashThread.start();
	}

}
