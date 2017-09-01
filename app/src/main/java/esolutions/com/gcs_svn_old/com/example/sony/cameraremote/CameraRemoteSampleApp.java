/*
 * Copyright 2014 Sony Corporation
 */

package esolutions.com.gcs_svn_old.com.example.sony.cameraremote;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.ui.Activity_Camera;
import esolutions.com.gcs_svn_old.com.camera.ui.Activity_Camera_AS20;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.example.sony.cameraremote.ServerDevice.ApiService;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * An Activity class of Device Discovery screen.
 */
public class CameraRemoteSampleApp extends Activity {

    private SimpleSsdpClient mSsdpClient;
 
    private DeviceListAdapter mListAdapter;

    private boolean mActivityActive;

	private String maso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_device_discovery);
        setProgressBarIndeterminateVisibility(false);

        mSsdpClient = new SimpleSsdpClient();
        mListAdapter = new DeviceListAdapter(this);
        
        Intent intent = getIntent();
		Bundle b = intent.getBundleExtra("maso");
		maso = b.getString("SO");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityActive = true;
        ListView listView = (ListView) findViewById(R.id.list_device);
        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	try{
	                ListView listView = (ListView) parent;
	                ServerDevice device = (ServerDevice) listView.getAdapter().getItem(position);
	                launchSampleActivity(device);
            	} catch(Exception ex) {
            		ex.toString();
            	}
            }
        });

        findViewById(R.id.button_search).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                if (!mSsdpClient.isSearching()) {
                    searchDevices();
                    btn.setEnabled(false);
                }
            }
        });

        findViewById(R.id.button_connect).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	WifiManager wifiManager = (WifiManager) CameraRemoteSampleApp.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
				if (!wifiManager.isWifiEnabled()) {
					wifiManager.setWifiEnabled(true);
				}
				
				onClickGotoWiFiSetting();
            }
        });
        
        // Show Wi-Fi SSID.
        TextView textWifiSsid = (TextView) findViewById(R.id.text_wifi_ssid);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String htmlLabel = String.format("SSID: <b>%s</b>", wifiInfo.getSSID());
            textWifiSsid.setText(Html.fromHtml(htmlLabel));
        } else {
            textWifiSsid.setText(R.string.msg_wifi_disconnect);
        }
    }

    /** Open wifi setting
	 * 
	 */
	public void onClickGotoWiFiSetting() {
		try{
	        String pac = "com.android.settings";
	        Intent i = new Intent();
	
	        i.setClassName(pac, pac + ".wifi.p2p.WifiP2pSettings");
	        try {
	            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
	        } catch (ActivityNotFoundException e) {
	            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH+1) {
	                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
	            } else {
	                i.setClassName(pac, pac + ".wifi.WifiSettings");
	                try {
	                    startActivity(i);
	                } catch (ActivityNotFoundException e2) {
	                    
	                }
	            }
	        }
		} catch(Exception ex) {
			ex.toString();
		}
    }
	
    @Override
    protected void onPause() {
        super.onPause();
        mActivityActive = false;
        if (mSsdpClient != null && mSsdpClient.isSearching()) {
            mSsdpClient.cancelSearching();
        }
    }

    /**
     * Start searching supported devices.
     */
    private void searchDevices() {
        mListAdapter.clearDevices();
        setProgressBarIndeterminateVisibility(true);
        mSsdpClient.search(new SimpleSsdpClient.SearchResultHandler() {

            @Override
            public void onDeviceFound(final ServerDevice device) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListAdapter.addDevice(device);
                    }
                });
            }

            @Override
            public void onFinished() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setProgressBarIndeterminateVisibility(false);
                        findViewById(R.id.button_search).setEnabled(true);
                        if (mActivityActive) {
                            Toast.makeText(CameraRemoteSampleApp.this,
                                    R.string.msg_device_search_finish,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onErrorFinished() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setProgressBarIndeterminateVisibility(false);
                        findViewById(R.id.button_search).setEnabled(true);
                        if (mActivityActive) {
                            Toast.makeText(CameraRemoteSampleApp.this,
                                    R.string.msg_error_device_searching,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
	public void onBackPressed() {
    	Common.state_show_camera = 0;
    	Intent intent = new Intent(this, Activity_Camera_AS20.class);
    	startActivity(intent);
		super.onBackPressed();
	}

	/**
     * Launch a SampleCameraActivity.
     * 
     * @param device
     */
    private void launchSampleActivity(ServerDevice device) {
//        MyApp app = (MyApp) getApplication();
//        app.setTargetServerDevice(device);
        Common.state_show_camera = 1;
//        this.finish();
//        Intent intent = new Intent(this, Activity_Camera_AS20.class);
//        startActivity(intent);
        
        Bundle b = new Bundle();
		b.putString("SO", maso);
		Intent gcs_act = new Intent(CameraRemoteSampleApp.this, Activity_Camera_AS20.class);
		gcs_act.putExtra("maso", b);
		startActivity(gcs_act);
		this.finish();
    }

    /**
     * Adapter class for DeviceList
     */
    private static class DeviceListAdapter extends BaseAdapter {

        private final List<ServerDevice> mDeviceList;

        private final LayoutInflater mInflater;

        public DeviceListAdapter(Context context) {
            mDeviceList = new ArrayList<ServerDevice>();
            mInflater = LayoutInflater.from(context);
        }

        public void addDevice(ServerDevice device) {
            mDeviceList.add(device);
            notifyDataSetChanged();
        }

        public void clearDevices() {
            mDeviceList.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mDeviceList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDeviceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textView = (TextView) convertView;
            if (textView == null) {
                textView = (TextView) mInflater.inflate(R.layout.device_list_item, parent, false);
            }
            ServerDevice device = (ServerDevice) getItem(position);
            ApiService apiService = device.getApiService("camera");
            String endpointUrl = null;
            if (apiService != null) {
                endpointUrl = apiService.getEndpointUrl();
            }

            String htmlLabel = String.format("%s ", device.getFriendlyName())
                            + String.format("<br><small>Endpoint URL:  <font color=\"blue\">%s</font></small>", endpointUrl);
            textView.setText(Html.fromHtml(htmlLabel));

            return textView;
        }
    }
}
