package esolutions.com.gcs_svn_old.com.AiBall_motion_detect;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.ArrayAdapter;

public class WifiPickerActivity extends Activity {
	ArrayAdapter<String> _accessPointsArrayAdapter;
	ArrayList<String> _ssids = new ArrayList<String>();
	/**
	 * @see Activity#onCreate(Bundle)
	 */
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//        setContentView(R.layout.wifi_picker);
//
//        _accessPointsArrayAdapter = new ArrayAdapter<String>(this, R.layout.access_point_list_item);
//		ListView apList = (ListView)findViewById(R.id.AccessPointListView);
//		apList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//		apList.setAdapter(_accessPointsArrayAdapter);
//		apList.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int pos, long id) {
//				String ssid = parent.getItemAtPosition(pos).toString();
//				WifiManager w = (WifiManager) WifiPickerActivity.this.getSystemService(Context.WIFI_SERVICE); 
//
//				List<WifiConfiguration> wcs = w.getConfiguredNetworks();
//				String checkSSID = '\"' + ssid + '\"';
//
//				SharedPreferences settings = getSharedPreferences(PublicDefine.PREFS_NAME, 0);
//				SharedPreferences.Editor editor = settings.edit();
//				editor.putString("ssid", ssid);
//
//				// Commit the edits!
//				editor.commit();
//
//				boolean foundExisting = false;
//				for(WifiConfiguration wc : wcs) {
//					if(wc.SSID.equals(checkSSID)) {
//						w.enableNetwork(wc.networkId, true);
//						foundExisting = true;
//					}
//				}
//
//				if(!foundExisting) {
//					WifiConfiguration newWC = new WifiConfiguration();
//					newWC.hiddenSSID = false;
//					newWC.SSID = checkSSID;
//					newWC.status = WifiConfiguration.Status.ENABLED;
//					// the following is the settings
//					// that found to be working for ai-ball
//					newWC.hiddenSSID = false;
//					newWC.allowedGroupCiphers.set(0);
//					newWC.allowedGroupCiphers.set(1);
//					newWC.allowedGroupCiphers.set(2);
//					newWC.allowedGroupCiphers.set(3);
//					newWC.allowedKeyManagement.set(0);
//					newWC.allowedPairwiseCiphers.set(1);
//					newWC.allowedPairwiseCiphers.set(3);
//					newWC.allowedProtocols.set(0);
//					newWC.allowedProtocols.set(1);
//
//					int res = w.addNetwork(newWC);
//					w.enableNetwork(res, true);
//				}		
//				finish();
//			}
//		});
//
//
//		IntentFilter i = new IntentFilter(); 
//		i.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION); 
//		registerReceiver(new BroadcastReceiver() { 
//			public void onReceive(Context c, Intent i) { 
//				// Code to execute when SCAN_RESULTS_AVAILABLE_ACTION 
//				// event occurs 
//				WifiManager w = (WifiManager) c.getSystemService(Context.WIFI_SERVICE); 
//				List<ScanResult> results = w.getScanResults(); // Returns a <list> of scanResults 
//				for(ScanResult result : results) {
//					if(!_ssids.contains(result.SSID)) {
//						_accessPointsArrayAdapter.add(result.SSID);
//						_ssids.add(result.SSID);
//					}
//				}
//			} 
//		}, i); 
//		// Now you can call this and it should execute the broadcastReceiver's onReceive() 
//		WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE); 
//		boolean a = wm.startScan(); 
//	}
//
//	/**
//	 * @see android.app.Activity#onStart()
//	 */
//	@Override
//	protected void onStart() {
//		super.onStart();
//		// TODO Put your code here
//	}
}
