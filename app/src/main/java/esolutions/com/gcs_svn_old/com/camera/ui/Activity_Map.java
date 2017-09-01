package esolutions.com.gcs_svn_old.com.camera.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.GPSTracker;

public class Activity_Map extends FragmentActivity implements LocationListener{

//	private GoogleMap mMap;
//	private LatLng posObject;
//	
//	private double lat = 0d;
//	private double lon = 0d;
//	private String title = "";
//	private int view = 0;
//	public static GPSTracker gps;
//	public static LatLng latLng;
//	
//	private ImageButton ibtView;
//	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		try{
//			setContentView(R.layout.activity_map);
//			if (android.os.Build.VERSION.SDK_INT > 9) {
//			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//			    StrictMode.setThreadPolicy(policy);
//			}
//			getActionBar().hide();
//			gps = new GPSTracker(this);
//			ibtView = (ImageButton)findViewById(R.id.ibtView);
//			//Khá»Ÿi táº¡o biáº¿n google map
//			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
//			//khá»Ÿi táº¡o biáº¿n váº½ Ä‘Æ°á»�ng Ä‘i
//			//Láº¥y giÃ¡ trá»‹ kinh Ä‘á»“ vÃ  vÄ© Ä‘á»™ nháº­n Ä‘Æ°á»£c
//			Intent intent = getIntent();
//			Bundle b = intent.getBundleExtra("POS");
//			title = b.getString("TITLE");
//			lat = b.getDouble("LAT");
//			lon = b.getDouble("LONG");
//			posObject = new LatLng(lat, lon);
//			//LÆ°u tá»�a Ä‘á»™ cá»§a Ä‘iá»ƒm nháº­n Ä‘Æ°á»£c
//	
//			//Di chuyá»ƒn tá»›i Ä‘iá»ƒm trÃªn
//			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posObject, 16));
//			//Ä�Ã¡nh dáº¥u Ä‘iá»ƒm nháº­n Ä‘Æ°á»£c
//			mMap.addMarker(new MarkerOptions().position(posObject).title(title));
//			
//			ibtView.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					if(view == 0){
//						mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//						view = 1;
//					} else if(view == 1) {
//						mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//						view = 2;
//					} else if(view == 2) {
//						mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//						view = 3;
//					} else if(view == 3) {
//						mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//						view = 0;
//					}
//				}
//			});
//		} catch(Exception ex){
//			ex.toString();
//			Common.ShowToast(Activity_Map.this, "Lỗi không xem được bản đồ", Toast.LENGTH_LONG, size());
//			this.finish();
//		}
//	}
//
////	@Override
////	protected void onNewIntent(Intent intent) {
////		if (intent.getBooleanExtra("finish", false)) {
////			finish();
////		}
	}
//
//	/**
//	 * Láº¥y Ä‘á»™ rá»™ng thiáº¿t bá»‹
//	 * 
//	 */
//	@SuppressWarnings("deprecation")
//	private int size() {
//		Display d = getWindowManager().getDefaultDisplay();
//		return d.getWidth() - 65;
//	}
//	
//	@Override
//	public void onLocationChanged(Location location) {
//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//        // Creating a LatLng object for the current location
//        LatLng latLng = new LatLng(latitude, longitude);
//        // Showing the current location in Google Map
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        // Zoom in the Google Map
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
//        posObject = new LatLng(lat, lon);
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//		
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//		
//	}

@Override
public void onLocationChanged(Location location) {
	// TODO Auto-generated method stub
	
}

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	// TODO Auto-generated method stub
	
}

@Override
public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
	
}

@Override
public void onProviderDisabled(String provider) {
	// TODO Auto-generated method stub
	
}
	
}
