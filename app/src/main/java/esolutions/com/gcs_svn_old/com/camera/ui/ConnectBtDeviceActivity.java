package esolutions.com.gcs_svn_old.com.camera.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.esgcs.printer.ESC_Bt_Printer;

public class ConnectBtDeviceActivity extends Activity {


	private ListView listDevicesFound;
	private Button btnScanDevice;
	private Button btnPrintTest;
	private TextView stateBluetooth;
	private EditText etxt_MAC;

	private BluetoothAdapter bluetoothAdapter;
	public static ArrayAdapter<String> btArrayAdapter;
	Common comm = new Common();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_connect_bt_device);
	
			btnPrintTest = (Button) findViewById(R.id.btnPrintTest);
			btnScanDevice = (Button) findViewById(R.id.btnScanDevice);
			etxt_MAC = (EditText) findViewById(R.id.etxt_MAC);
			stateBluetooth = (TextView) findViewById(R.id.tvBluetoothState);
			
			bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	
			listDevicesFound = (ListView) findViewById(R.id.lvDevicesFound);
			btArrayAdapter = new ArrayAdapter<String>(ConnectBtDeviceActivity.this, android.R.layout.simple_list_item_1);
			listDevicesFound.setAdapter(btArrayAdapter);
	
			CheckBlueToothState();
	
			btnScanDevice.setOnClickListener(btnScanDeviceOnClickListener);
			btnPrintTest.setOnClickListener(btnPrintTestOnClickListener);
	
			registerReceiver(ActionFoundReceiver, new IntentFilter( BluetoothDevice.ACTION_FOUND));
	
			listDevicesFound .setOnItemClickListener(new AdapterView.OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String addMAC = (String) parent
							.getItemAtPosition(position);
					String[] addMACs = addMAC.split("\n");
					etxt_MAC.setText(addMACs[1]); 
				}
			});
		} catch(Exception ex) {
			ex.toString();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(ActionFoundReceiver);
	}

	private Button.OnClickListener btnScanDeviceOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			btArrayAdapter.clear();
			bluetoothAdapter.startDiscovery();
		}
	};
	
	private Button.OnClickListener btnPrintTestOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			try{
				if(Common.printer == null){
					Common.printer = new ESC_Bt_Printer(etxt_MAC.getText().toString());
//					comm.printer = new ESC_Bt_Printer("00:12:F3:19:49:CD");
				}
				else{
					Common.printer.MAC = etxt_MAC.getText().toString();
//					comm.printer.MAC = "00:12:F3:19:49:CD";
				}
				
//				common.msbox("Debug", "'"+comm.printer.MAC + "' - " +comm.printer.MAC.length()+"\nfix - "+etxt_MAC.getText().toString().length(),
//						ConnectBtDeviceActivity.this);
				
				String test = "Kết nối máy in thành công\nNgày ..... tháng .... năm";
				Common.printer.Print(test);
//				etxt_MAC.setText(Html.fromHtml(str).toString());
			}
			catch(Exception ex){
				comm.msbox("Thông báo", "Không thể in , kiểm tra lại kết nổi, máy in", ConnectBtDeviceActivity.this);
			}
		}
	};
	
	private void CheckBlueToothState() {
		if (Common.printer.mBTAdapter == null) {
			stateBluetooth.setText("Bluetooth không hỗ trợ");
		} else {
			if (Common.printer.mBTAdapter.isEnabled()) {
				if (Common.printer.mBTAdapter.isDiscovering()) {
					stateBluetooth.setText("Đang tìm thiết bị...");
					btnScanDevice.setEnabled(false);
				} else {
					stateBluetooth.setText("Bluetooth đã bật.");
					btnScanDevice.setEnabled(true);
				}
			} else {
				stateBluetooth.setText("Bluetooth đã tắt !");
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, ESC_Bt_Printer.REQUEST_ENABLE_BT);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ESC_Bt_Printer.REQUEST_ENABLE_BT) {
			CheckBlueToothState();
		}
	}

	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				btArrayAdapter.add(device.getName() + "\n"
						+ device.getAddress());
				btArrayAdapter.notifyDataSetChanged();
			}
		}
	};


}