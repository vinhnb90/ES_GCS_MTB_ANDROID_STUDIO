package esolutions.com.gcs_svn_old.com.camera.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.GPSTracker;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;

public class Activity_Maps extends FragmentActivity implements LocationListener{

//	private GoogleMap mMap;
//	private LatLng posObject;
//	public static SQLiteConnection connection;
//	
//	private double lat = 0d;
//	private double lon = 0d;
//	private String title = "";
//	private String fileName = "";
//	private int view = 0;
//	public static GPSTracker gps;
//	public static LatLng latLng;
//	
//	private ImageButton ibtView;
//	
//	@SuppressLint("NewApi")
//	@Override
//	protected void onCreate(Bundle arg0) {
//		super.onCreate(arg0);
//		try{
//			setContentView(R.layout.activity_map);
//			connection = new SQLiteConnection(this, "ESGCS.s3db",
//					Environment.getExternalStorageDirectory()
//							+ Common.DBFolderPath);
//			if (android.os.Build.VERSION.SDK_INT > 9) {
//			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//			    StrictMode.setThreadPolicy(policy);
//			}
//			getActionBar().hide();
//			gps = new GPSTracker(this);
//			ibtView = (ImageButton)findViewById(R.id.ibtView);
//			//Khởi tạo biến google map
//			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
//			//khởi tạo biến vẽ đường đi
//			//Lấy giá trị kinh đồ và vĩ độ nhận được
//			Intent intent = getIntent();
//			Bundle b = intent.getBundleExtra("POS");
//			fileName = b.getString("FILE_NAME");
////			title = b.getString("TITLE");
////			lat = Double.parseDouble(b.getString("LAT"));
////			lon = Double.parseDouble(b.getString("LONG"));
////			posObject = new LatLng(lat, lon);
//			Cursor c = connection.getAllLatLon(fileName);
//			if(c.moveToFirst()){
//				do {
//					title = c.getString(0);
////					String sLat = c.getString(1);
////					String sLon = c.getString(2);
////					lat = Double.parseDouble(sLat);
////					lon = Double.parseDouble(sLon);
//					lat = c.getDouble(1);
//					lon = c.getDouble(2);
//					if(lat != 0 && lon != 0){
//						posObject = new LatLng(lat, lon);
//						//Đánh dấu điểm nhận được
//						mMap.addMarker(new MarkerOptions().position(posObject).title(title));
//					}
//				} while (c.moveToNext());
//				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posObject, 16));
//			}
//			//Lưu tọa độ của điểm nhận được
//	
//			//Di chuyển tới điểm trên
//			if(lat != 0 && lon != 0){
//				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posObject, 16));
//			}
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
//			Common.ShowToast(Activity_Maps.this, "Lỗi không xem được bản đồ", Toast.LENGTH_LONG, size());
//			this.finish();
//		}
//	}
//
////	@Override
////	protected void onNewIntent(Intent intent) {
////		if (intent.getBooleanExtra("finish", false)) {
////			finish();
////		}
////	}
//
//	/**
//	 * Lấy độ rộng thiết bị
//	 * 
//	 */
//	@SuppressWarnings("deprecation")
//	private int size() {
//		Display d = getWindowManager().getDefaultDisplay();
//		return d.getWidth() - 65;
//	}
	
	@Override
	public void onLocationChanged(Location location) {
//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//        // Creating a LatLng object for the current location
//        LatLng latLng = new LatLng(latitude, longitude);
//        // Showing the current location in Google Map
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        // Zoom in the Google Map
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
//        posObject = new LatLng(lat, lon);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}
	
}
