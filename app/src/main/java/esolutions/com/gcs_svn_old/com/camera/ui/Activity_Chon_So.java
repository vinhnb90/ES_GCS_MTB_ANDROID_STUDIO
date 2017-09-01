package esolutions.com.gcs_svn_old.com.camera.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.LruCache;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.ConfigInfo;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
@SuppressLint("NewApi")
public class Activity_Chon_So extends Activity{

	private Button btnGCS, btnGCSAll, btnQuanLySo;
	private RadioGroup rgCamera;
	private ListView lvChonSo;
	@SuppressWarnings("unused")
	private RadioButton rbAiBall, rbMTB;
	
	static int count_check = 0;
	
	private Common comm = new Common();
	
	private SQLiteConnection connection;
	private Dialog alertDialog;
	
	private ArrayAdapter<String> dataAdapter;
//	protected MyApp mMyApp;
	ThumbnailCache mCache;
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mMyApp = (MyApp)this.getApplicationContext();
		
		// Pick cache size based on memory class of device
		final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final int memoryClassBytes = am.getMemoryClass() * 1024 * 1024;
		mCache = new ThumbnailCache(memoryClassBytes / 2);
					
		setContentView(R.layout.activity_chon_so);
		getActionBar().hide();
		
		initComponent();
		eventButton();
//		RefWatcher refWatcher = MyApp.getRefWatcher(this);
//		refWatcher.watch(this);
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		if (level >= TRIM_MEMORY_MODERATE) { // 60
			mCache.evictAll();
		} else if (level >= TRIM_MEMORY_BACKGROUND) { // 40
			mCache.trimToSize(mCache.size() / 2);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		mMyApp.setCurrentActivity(this);
//		comm.LoadFolder(this);
		lvChonSo.clearChoices();
		list_So = new ArrayList<String>();
		
		String result = comm.CheckDbExist(Activity_Chon_So.this.getApplicationContext(), "ESGCS.s3db", Environment.getExternalStorageDirectory() +Common.DBFolderPath);
		if(result == "EXIST"){
			connection = null;
			connection = new SQLiteConnection(Activity_Chon_So.this.getApplicationContext(), "ESGCS.s3db", Environment.getExternalStorageDirectory() +Common.DBFolderPath);
			spnChonSo_GetData();
		}
		
		if(Common.PHIEN_BAN.equals("HN")){
			LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
			if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Kích hoạt GPS");
				builder.setMessage("Bạn phải kích hoạt GPS để tiếp tục sử dụng chương trình\nKích hoạt ở chế độ chính xác cao để thu thập tọa độ chính xác nhất");
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialogInterface, int i) {
								Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(intent);
							}
						});
				alertDialog = builder.create();
				alertDialog.setCanceledOnTouchOutside(false);
				alertDialog.setCancelable(false);
				alertDialog.show();
			}
		}
//		if(!Common.PHIEN_BAN.equals("HN")){
//			rgCamera.setVisibility(View.GONE);
//		}
	}

	@Override
	protected void onPause() {
//		clearReferences();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
//		clearReferences();
		if(connection != null)
			connection.close();
		super.onDestroy();
	}

//	private void clearReferences(){
//        Activity currActivity = mMyApp.getCurrentActivity();
//        if (this.equals(currActivity))
//            mMyApp.setCurrentActivity(null);
//    }
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(Activity_Chon_So.this, Activity_Main.class));
		super.onBackPressed();
		count_check = 0;
	}

	private void initComponent(){
		try{
			btnGCS = (Button)findViewById(R.id.btnGCS);
			btnGCSAll = (Button)findViewById(R.id.btnGCSAll);
			btnQuanLySo = (Button)findViewById(R.id.btnQuanLySo);
			lvChonSo = (ListView)findViewById(R.id.lvChonSo);
			rbAiBall = (RadioButton)findViewById(R.id.rbAiBall);
			rbMTB = (RadioButton)findViewById(R.id.rbMTB);
			rgCamera = (RadioGroup)findViewById(R.id.rgCamera);
			if(!Common.PHIEN_BAN.equals("HN")){
				btnGCSAll.setVisibility(View.GONE);
				btnGCS.setText("Ghi chỉ số");
				rgCamera.setVisibility(View.GONE);
			}
			if(Common.PHIEN_BAN.equals("LC")){
				rgCamera.setVisibility(View.VISIBLE);
			}
		} catch(Exception ex) {
			comm.ShowToast(Activity_Chon_So.this.getApplicationContext(), "Error: " + ex.toString(), Toast.LENGTH_LONG);
		}
	}
	
	private void eventButton(){
		try{
			btnGCS.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					CheckFileConfigExist();
					if(comm.checkDB()){
						if (lvChonSo.getAdapter().getCount() < 1)
							return true;
						
						count_check = lvChonSo.getCheckedItemCount();
						if(count_check == 0) {
							comm.ShowToast(Activity_Chon_So.this.getApplicationContext(), "Bạn phải chọn sổ trước", Toast.LENGTH_LONG);
							return true;
						}
						
						lvChonSo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
						if(Common.PHIEN_BAN.equals("BN")){
							openGCS(Activity_Camera.class, "ALL");
						} else {
							openGCS(Activity_Camera_BN.class, "ALL");
						}
					}
					return false;
				}
			});
			
			btnGCSAll.setOnClickListener(new View.OnClickListener() {
				
				@SuppressLint("NewApi") @Override
				public void onClick(View v) {
					try{
//						if(connection.updateTTDsoatGCS() != -1){
							CheckFileConfigExist();
							comm.deleteBackup();
							comm.CreateBackup(null);
							if(comm.checkDB()){
								if (lvChonSo.getAdapter().getCount() < 1)
									return;
								
								count_check = lvChonSo.getCheckedItemCount();
								if(count_check == 0) {
									comm.ShowToast(Activity_Chon_So.this.getApplicationContext(), "Bạn phải chọn sổ trước", Toast.LENGTH_LONG);
									return;
								}
								
								lvChonSo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
								if(Common.PHIEN_BAN.equals("BN")){
									openGCS(Activity_Camera_BN.class, "ALL");
								} else {
									if(rbAiBall.isChecked())
										if(Common.PHIEN_BAN.equals("SL")){
											openGCS(Activity_Camera_AS20.class, "ALL");
										} else {
											openGCS(Activity_Camera.class, "ALL");
										}
									else
										openGCS(Activity_Camera_MTB.class, "ALL");
								}
							}
//						}
					} catch(Exception ex) {
						ex.toString();
					}
				}
			});
			
			btnGCS.setOnClickListener(new View.OnClickListener() {
				
				@SuppressLint("NewApi") @Override
				public void onClick(View v) {
					try{
						CheckFileConfigExist();
						comm.deleteBackup();
						comm.CreateBackup(null);
						if(comm.checkDB()){
							if (lvChonSo.getAdapter().getCount() < 1)
								return;
							
							count_check = lvChonSo.getCheckedItemCount();
							if(count_check == 0) {
								comm.ShowToast(Activity_Chon_So.this.getApplicationContext(), "Bạn phải chọn sổ trước", Toast.LENGTH_LONG);
								return;
							}
							
							lvChonSo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
							if(Common.PHIEN_BAN.equals("BN")){
								openGCS(Activity_Camera_BN.class, "ALL");
							} else {
								if(rbAiBall.isChecked())
									if(Common.PHIEN_BAN.equals("SL")){
										openGCS(Activity_Camera_AS20.class, "ALL");
									} else if(Common.PHIEN_BAN.equals("HN")){
										openGCS(Activity_Camera.class, "DSOAT");
									} else {
										openGCS(Activity_Camera.class, "ALL");
									}
								else
									openGCS(Activity_Camera_MTB.class, "DSOAT");
							}
						}
					} catch(Exception ex) {
						ex.toString();
					}
				}
			});
			
			btnQuanLySo.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(comm.checkDB()){
						Intent QLSo_Act = new Intent(Activity_Chon_So.this, QuanLySoActivity.class);
						startActivity(QLSo_Act);
					}
				}
			});
			
			btnQuanLySo.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					CheckFileConfigExist();
					if(comm.checkDB()){
						if (lvChonSo.getAdapter().getCount() < 1)
							return true;
						
						count_check = lvChonSo.getCheckedItemCount();
						if(count_check == 0) {
							comm.ShowToast(Activity_Chon_So.this.getApplicationContext(), "Bạn phải chọn sổ trước", Toast.LENGTH_LONG);
							return true;
						}
						
						lvChonSo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
						openGCS(Activity_Camera_AiBall.class, "ALL");
					}
					return false;
				}
			});
			
		} catch(Exception ex) {
			comm.ShowToast(Activity_Chon_So.this.getApplicationContext(), "Error: " + ex.toString(), Toast.LENGTH_LONG);
		}
	}
	
	List<Integer> ls_pos = new ArrayList<Integer>();
	private void openGCS(Class<?> cls, String type_show) {
		try{
			ArrayList<String> selectedItems = new ArrayList<String>();
			
			for (int i = 0; i < lvChonSo.getCount(); i++) {
				SparseBooleanArray ck = lvChonSo.getCheckedItemPositions();
				if(ck.get(i)){
					selectedItems.add(dataAdapter.getItem(i));
				}
			}
			
			String [] fileName = new String[count_check];
			for (int i = 0; i < count_check; i++) {
				fileName[i] = selectedItems.get(i);
			}
			
//			String s = list_So.get(0);
//			String s = fileName[0].split(" ")[0].toString();
			
			StringBuilder so = new StringBuilder();
			for (String fn : fileName) {
				addSo(fn.split(" ")[0].toString());
				so.append(fn.split(" ")[0].toString() + ":");
			}
			Bundle b = new Bundle();
			b.putString("SO", so.toString());
			b.putString("KIEU_HTHI", type_show);
//			comm.setSO1(s);
			
			Intent gcs_act = new Intent(Activity_Chon_So.this, cls);
			gcs_act.putExtra("maso", b);
			startActivity(gcs_act);
		} catch(Exception ex) {
			comm.ShowToast(Activity_Chon_So.this.getApplicationContext(), "Bạn phải chọn sổ", Toast.LENGTH_LONG);
		}
	}
	
	public void addSo(String so){
		try {
			if(checkSo(so)){// && !comm.PHIEN_BAN.equals("BN")
				connection.insertDataIndex(so, 0);
			}
		} catch(Exception ex) {
			comm.ShowToast(Activity_Chon_So.this.getApplicationContext(), ex.toString(), Toast.LENGTH_LONG);
		}
	}
	
	public boolean checkSo(String so){
		int count = connection.checkDataIndex(so);
		if(count == 0)
			return true;
		else
			return false;
	}
	
	List<String> list_So = new ArrayList<String>();
	/**
	 * Lấy danh sách sổ trên máy
	 */
	public void spnChonSo_GetData() {
		try {
			lvChonSo = (ListView) findViewById(R.id.lvChonSo);
			List<String> list = new ArrayList<String>();

			// Tạo thư mục nếu chưa có
			File programDirectory = new File(Environment.getExternalStorageDirectory() + Common.programPath); // create a File object for the parent directory
			if (!programDirectory.exists()) {
				programDirectory.mkdirs(); // tạo thư mục chứa dữ liệu
			}

			File DBDir = new File(Environment.getExternalStorageDirectory() + Common.DBFolderPath);
			if (!DBDir.exists()) {
				DBDir.mkdirs();
			}

			try{
				Cursor c = connection.getAllDataSoInData();
				if (c.moveToFirst()) {
					do {
						StringBuilder fileName = new StringBuilder(c.getString(0)); 
						Cursor c2 = connection.getAllDataGCSByMaQuyen(fileName.toString());
						int so_diem_do = c2.getCount();
						int da_ghi = 0;
						if (c2.moveToFirst()) {
							do {
								if(comm.isSavedRow(c2.getString(24), c2.getString(23))){
									da_ghi++;
								}
							} while (c2.moveToNext());
						}
						list_So.add(fileName.toString());
						if(da_ghi == so_diem_do){
							fileName.append(distance(fileName.toString(), 180) + "Sổ đã đủ chỉ số");
						} else {
							fileName.append(distance(fileName.toString(), 180) + "Sổ chưa đủ chỉ số");
						}
						list.add(fileName.toString());
					} while (c.moveToNext());
				}
			} catch(Exception ex){
				comm.ShowToast(Activity_Chon_So.this.getApplicationContext(), "Không lấy được dữ liệu", Toast.LENGTH_LONG);
			}

			dataAdapter = new ArrayAdapter<String>(Activity_Chon_So.this.getApplicationContext(), R.layout.listview_multichoice, list);
			lvChonSo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lvChonSo.setAdapter(dataAdapter);
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Chưa có dữ liệu trong thư mục", Activity_Chon_So.this.getApplicationContext());
		}
	}
	
	/**
	 * Lấy khoảng cách chuổi 
	 * @param name
	 * @param size
	 * @return
	 */
	public String distance(String name, float size){
		Paint paint = new Paint();
		float w = paint.measureText(name);
		float len = size - w;
		StringBuilder str_length = new StringBuilder(" ");
		while(paint.measureText(str_length.toString()) < len){
			str_length.append(" ");
		}
		return str_length.toString();
	}
	
	/**Kiểm tra file cấu hình đã tồn tại chưa
	 * 
	 */
	private void CheckFileConfigExist() {
		try {
			String filePath = Environment.getExternalStorageDirectory() + Common.programPath + Common.cfgFileName;
			// create a File object for the parent directory
			File programDirectory = new File(Environment.getExternalStorageDirectory() + Common.programPath);
			if (!programDirectory.exists()) {
				programDirectory.mkdirs(); // tạo thư mục chứa dữ liệu
			}
			
			//kiểm tra folder DB đã tồn tại hay chưa
			File dbDir = new File(Environment.getExternalStorageDirectory() + Common.programPath + "/DB");
			if (!dbDir.exists()) {
				dbDir.mkdirs();
			}
			
			//kiểm tra folder backup đã có chưa, nếu chưa thì tạo mới
			File backupDir = new File(Environment.getExternalStorageDirectory() + Common.programPath + "/Backup");
			if (!backupDir.exists()) {
				backupDir.mkdirs();
			}
			
			//kiểm tra folder lộ trình đã có chưa, nếu chưa thì tạo mới
			File tempDir = new File(Environment.getExternalStorageDirectory() + Common.programPath + "/Temp");
			if (!tempDir.exists()) {
				tempDir.mkdirs();
			}
			
			//kiểm tra folder định dạng đã có chưa, nếu chưa thì tạo mới
			File format = new File(Environment.getExternalStorageDirectory() + Common.programPath + "/Format");
			if (!format.exists()) {
				format.mkdirs();
			}
			
			//kiểm tra folder định dạng đã có chưa, nếu chưa thì tạo mới
			File photo = new File(Environment.getExternalStorageDirectory() + Common.programPath + "/Photo");
			if (!photo.exists()) {
				photo.mkdirs();
			}

			//kiểm tra folder định dạng đã có chưa, nếu chưa thì tạo mới
			File tessdata = new File(Environment.getExternalStorageDirectory() + Common.programPath + "/Format/tessdata");
			if (!tessdata.exists()) {
				tessdata.mkdirs();
			}
			
//			//kiểm tra file test
			File tess = new File(Environment.getExternalStorageDirectory() + Common.programPath + "/Format/tessdata/test");
			if (!tess.exists()) {
				tess.mkdirs();
			}

			File cfgFile = new File(filePath);
			if (cfgFile.exists()) {
				Common.cfgInfo = comm.GetFileConfig();
				if(Common.cfgInfo == null){
					Common.cfgInfo = new ConfigInfo();
				}
				// Lấy lại thông tin file cấu hình
				createFileConfig(Common.cfgInfo);
			} else {
				// tao file cau hinh mac dinh
				Common.cfgInfo = new ConfigInfo();
				comm.CreateFileConfig(Common.cfgInfo, cfgFile, "");

			}
		} catch (Exception ex) {
			comm.msbox("Thông báo", "File cấu hình không đúng", Activity_Chon_So.this.getApplicationContext());
		}
	}
	
	/**Lấy lại thông tin file cấu hình
	 * 
	 */
	public void createFileConfig(ConfigInfo cfg){
		try{
			ConfigInfo cfgInfo = new ConfigInfo(
					cfg.isWarningEnable3(),
					cfg.isWarningEnable(),
					cfg.isWarningEnable2(),
					cfg.getVuotDinhMuc(),
					cfg.getDuoiDinhMuc(),
					cfg.getVuotDinhMuc2(),
					cfg.getDuoiDinhMuc2(),
					cfg.isAutosaveEnable(), cfg.getAutosaveMinutes(),
					cfg.getIP_SERVICE(), 
					null, 
					cfg.getColumnsOrder(),
					cfg.getDinhDang(),
					cfg.getDVIQLY());
			
			String filePath = Environment.getExternalStorageDirectory() + Common.programPath + Common.cfgFileName;
			File cfgFile = new File(filePath);
			
			comm.CreateFileConfig(cfgInfo, cfgFile, null);
		} catch(Exception ex) {
			
		}
	}

	public static class ThumbnailCache extends LruCache<Long, Bitmap> {
		public ThumbnailCache(int maxSizeBytes) {
			super(maxSizeBytes);
		}

		@Override
		protected int sizeOf(Long key, Bitmap value) {
			return value.getByteCount();
		}
	}
	
}
