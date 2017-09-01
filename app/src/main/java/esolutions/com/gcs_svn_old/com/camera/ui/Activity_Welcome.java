package esolutions.com.gcs_svn_old.com.camera.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;

public class Activity_Welcome extends Activity {

	Thread t;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		getActionBar().hide();
		ImageView imvLogo = (ImageView) findViewById(R.id.imvLogo);
		if (Common.PHIEN_BAN.equals("LC")) {
			imvLogo.setImageResource(R.drawable.logo_lc);
		}
		
		t =new Thread(){
        	public void run(){
        		try {
        			Thread.sleep(1000);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		} finally {
        			startActivity(new Intent(Activity_Welcome.this, Activity_Main.class));
        		}
        	}
        };
        t.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(t != null)
//			t.interrupt();
			t = null;
	}
	
}
