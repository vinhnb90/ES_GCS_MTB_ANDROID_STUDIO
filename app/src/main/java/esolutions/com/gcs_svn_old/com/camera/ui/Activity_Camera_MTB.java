package esolutions.com.gcs_svn_old.com.camera.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.LruCache;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.meetme.android.horizontallistview.HorizontalListView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.entity.EntityGCS;
import esolutions.com.gcs_svn_old.com.camera.entity.EntityIndex;
import esolutions.com.gcs_svn_old.com.camera.simplemjpeg.ShowCamera;
import esolutions.com.gcs_svn_old.com.camera.utility.AppLocationService;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.ConfigInfo;
import esolutions.com.gcs_svn_old.com.camera.utility.ConstantVariables;
import esolutions.com.gcs_svn_old.com.camera.utility.GPSTracker;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;
import esolutions.com.gcs_svn_old.com.camera.utility.row_gcs;
import esolutions.com.gcs_svn_old.com.es.zoomimage.ImageViewTouch;
import esolutions.com.gcs_svn_old.com.meetme.android.horizontallistview.CustomArrayAdapterCameraMTB;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import esolutions.com.gcs_svn_old.esgcs.printer.InThongBao;

@SuppressWarnings("deprecation")
public class Activity_Camera_MTB extends Activity {

	private Spinner spTieuChi, spTrangThai;
	public EditText etCSMoi, etPmax;
	public EditText etTimKiem;
    private HorizontalListView hlvSimpleList;
//	public MjpegView mv;
	private Button btnLoad, btnDelete, btnView, btnCapture, btnChange;
	private Button btnChucNang, btnSoKhac, btnAnHienCamera, btnGhi;
	private Button etNgayPmax, btnXoa;
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0,
	btnDot, btnClear;
	private Button btnQ, btnW, btnE, btnR, btnT, btnY, btnU, btnI, btnO, btnP,
	btnA, btnS, btnD, btnF, btnG, btnH, btnJ, btnK, btnL, btnZ, btnX,
	btnC, btnV, btnB, btnN, btnM, btnClear2, btnUpper, btnNumber,
	btnPhay, btnSpace, btnCham, btnNext, btnNgoacDong;
	private ImageButton ibtScanner, btnDong;
	private LinearLayout lnCamera, lnButtonCamera, lnButtonCloseCamera, lnListCustom;
	private RelativeLayout rlCamera;
    private ImageViewTouch ivViewImage;
    public ListView lvCustomer = null;
    
	private View viewControlNumber;
	private View viewControlText;
	private LayoutInflater controlInflater = null;
	private TextView tvDaGhi;
	
    private Common comm;
    private SQLiteConnection connection;
    private GPSTracker gps;
    
    private String fileName = "";
	private String maso = "";
	private String kieu_hthi = "ALL";
	public String TEN_FILE;
	
	public static int selected_index = 0;
	public static int pos_so = 0;
	
	public static int h = 55;
	public static int top = 70;
	public static int bottom = top + h;
	
	private ArrayList<LinkedHashMap<String, String>> mSimpleListValues = new ArrayList<LinkedHashMap<String,String>>();
	public AdapterCameraMTB adapter = null;
	public static ArrayList<LinkedHashMap<String, String>> arrCustomer = new ArrayList<LinkedHashMap<String,String>>();
	public static ArrayList<LinkedHashMap<String, String>> arrCustomer_Filter = new ArrayList<LinkedHashMap<String,String>>();
	private THTTBTAdapter adapter_ttbt = null;
	private THCSBTAdapter adapter_slbt = null;
	private KyNhanAdapter adapter_kxn = null;
	private ArrayAdapter<String> dataAdapter;
	
	ThumbnailCache mCache;
	AppLocationService appLocationService;
	private Dialog alertDialog;
	private int checkChangeCamera = 0;
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_camera_mtb);
			connection = new SQLiteConnection(this.getApplicationContext(), "ESGCS.s3db", Environment.getExternalStorageDirectory() + Common.DBFolderPath);
			comm = new Common();
			gps = new GPSTracker(this.getApplicationContext());
			appLocationService = new AppLocationService(this.getApplicationContext());
			
			// Pick cache size based on memory class of device
			final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			final int memoryClassBytes = am.getMemoryClass() * 1024 * 1024;
			mCache = new ThumbnailCache(memoryClassBytes / 2);
			
			getActionBar().hide();
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			Intent intent = getIntent();
			Bundle b = intent.getBundleExtra("maso");
			maso = b.getString("SO");
			kieu_hthi = b.getString("KIEU_HTHI");
			fileName = maso.split(":")[0].toString();
			
			if(Common.pos_change_camera != -1)
				selected_index = Common.pos_change_camera;
			else
				selected_index = getPos();
			
			initComponent();
			ivViewImage.setVisibility(View.GONE);
			eventButton();
			setupSimpleList();
			showCamera();
			(new AsyncTaskCameraMTB(Activity_Camera_MTB.this, kieu_hthi)).execute(fileName);
			setKeyboard();
			hideSoftKeyboard();
			handleKeyboard();
			eventEdittext();
			enableKeyboard(View.GONE);
			CreateSpnTieuChi();
			CreateSpnTrangThai();
			
			etCSMoi.requestFocus();
			etCSMoi.selectAll();
			
			createPhotoFolder(fileName);
//			RefWatcher refWatcher = MyApp.getRefWatcher(this);
//			refWatcher.watch(this);
		} catch(Exception ex) {
			comm.msbox("onCreate error", ex.toString(), Activity_Camera_MTB.this);
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		if (level >= TRIM_MEMORY_MODERATE) { // 60
			mCache.evictAll();
		} else if (level >= TRIM_MEMORY_BACKGROUND) { // 40
			mCache.trimToSize(mCache.size() / 2);
		}
	}
	
	private static void unbindDrawable(Drawable d){
	    if(d!=null){
	        d.setCallback(null);
	    }
	}
	
	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		unbindDrawable(ivViewImage.getBackground());
	}
	
	public static void readLog() {
		try {
			Process process = Runtime.getRuntime().exec("logcat -d");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder log = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				log.append(line);
			}
		} catch (IOException e) {
		}
	}
	
	private void createPhotoFolder(String fileName) {
		try{
			TEN_FILE = connection.getTenFileByMaQuyen(fileName);
			TEN_FILE = TEN_FILE.toLowerCase().contains(".xml")?TEN_FILE.substring(0, TEN_FILE.length() - 4):TEN_FILE;
			File photo = new File(Environment.getExternalStorageDirectory() + Common.programPath + "/Photo/" + TEN_FILE);
			if (!photo.exists()) {
				photo.mkdirs();
				if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
					try{
		        		Activity_Camera_MTB.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/ESGCS")));
		        	} catch(Exception ex) {
		        		Activity_Camera_MTB.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/ESGCS")));
		        	}
				}
			}
		} catch(Exception ex) {
			comm.msbox("createPhotoFolder error", ex.toString(), Activity_Camera_MTB.this);
		}
	}

	private void checkGPS(){
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
	
	@Override
	protected void onResume() {
		try{
			checkGPS();
			super.onResume();
			
//			if(Common.state_show_camera == 0) {
//				btnLoad.setEnabled(false);
//				btnDelete.setEnabled(false);
//				btnView.setEnabled(false);
//				btnCapture.setEnabled(false);
//			}
			
			CreateEventTimKiem();
		} catch(Exception ex) {
			comm.msbox("onResume error", ex.toString(), Activity_Camera_MTB.this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		if(connection != null)
			connection.close();
		if(checkChangeCamera == 0){
			File photo = new File(Environment.getExternalStorageDirectory() + Common.programPath + "/Photo/" + TEN_FILE);
			String[] allFilesBackup = photo.list();
			if(allFilesBackup.length == 0){
				photo.delete();
			}
		}
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		Common.pos_tab = 0;
		Common.pos_change_camera = -1;
		Common.state_show_camera = 0;
		pos_so = 0;
		try{
			if(lnButtonCamera.isShown()){
				Common.isErrorCamera = false;
				this.finish();
			} else {
				Common.state_show_camera = 1;
				lnButtonCamera.setVisibility(View.VISIBLE);
				lnListCustom.setVisibility(View.VISIBLE);
				lnButtonCloseCamera.setVisibility(View.GONE);
			}
		} catch(Exception ex){
			Common.isErrorCamera = false;
			this.finish();
		}
	}

	// ------------------- CÁC PHƯƠNG THỨC KHỞI TẠO --------------------
	private void initComponent(){
		try{
			spTieuChi = (Spinner)findViewById(R.id.spTieuChi);
			spTrangThai = (Spinner)findViewById(R.id.spTrangThai);
			etTimKiem = (EditText)findViewById(R.id.etTimKiem);
			etCSMoi = (EditText)findViewById(R.id.etCSMoi);
			etPmax = (EditText)findViewById(R.id.etPmax);
            hlvSimpleList = (HorizontalListView) findViewById(R.id.hlvSimpleList);
			ivViewImage = (ImageViewTouch) findViewById(R.id.ivViewImage);
			btnLoad = (Button)findViewById(R.id.btnLoad);
			btnDelete = (Button)findViewById(R.id.btnDelete);
			btnView = (Button)findViewById(R.id.btnView);
			btnCapture = (Button)findViewById(R.id.btnCapture);
			btnChange = (Button)findViewById(R.id.btnChange);
			btnChucNang = (Button)findViewById(R.id.btnChucNang);
			btnSoKhac = (Button)findViewById(R.id.btnSoKhac);
			btnAnHienCamera = (Button)findViewById(R.id.btnAnHienCamera);
			btnGhi = (Button)findViewById(R.id.btnGhi);
			etNgayPmax = (Button)findViewById(R.id.etNgayPmax);
			btnXoa = (Button)findViewById(R.id.btnXoa);
			ibtScanner = (ImageButton)findViewById(R.id.ibtScanner);
			btnDong = (ImageButton)findViewById(R.id.btnDong);
			lnCamera = (LinearLayout)findViewById(R.id.lnCamera);
			lnButtonCamera = (LinearLayout)findViewById(R.id.lnButtonCamera);
			lnButtonCloseCamera = (LinearLayout)findViewById(R.id.lnButtonCloseCamera);
			lnListCustom = (LinearLayout)findViewById(R.id.lnListCustom);
			tvDaGhi = (TextView)findViewById(R.id.tvDaGhi);
			preview = (FrameLayout) findViewById(R.id.camera_preview);
			rlCamera = (RelativeLayout) findViewById(R.id.rlCamera);
		} catch(Exception ex){
			comm.msbox("Lỗi", "Lỗi khởi tạo đối tượng", Activity_Camera_MTB.this);
		}
	}
	
	private void eventButton(){
		try{
			btnLoad.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try{
						ivViewImage.setVisibility(View.GONE);
						rlCamera.setVisibility(View.VISIBLE);
						cameraObject.startPreview();
						Common.state_show_image = 0;
						btnCapture.setEnabled(true);
//						if(Common.CAMERA_TYPE.equals("AIBALL")){
//							mv.setVisibility(View.VISIBLE);
//							Common.state_show_image = 0;
//							new DoRead().execute(URL);
//							new StandByCamera().execute(ConstantVariables.URL_RESUME);
//							btnCapture.setEnabled(true);
//						}
					} catch(Exception ex) {
						ex.toString();
					}
				}
			});
			
			btnDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try{
						String selectedFilePath = Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + TEN_FILE + "/" + fileName + "_" + adapter.getItem(selected_index).get("MA_CTO") + "_" + adapter.getItem(selected_index).get("NAM") + "_" + adapter.getItem(selected_index).get("THANG") + "_" + adapter.getItem(selected_index).get("KY") + "_" + adapter.getItem(selected_index).get("MA_DDO") + "_" + adapter.getItem(selected_index).get("LOAI_BCS") + ".jpg";
						File file = new File(selectedFilePath);
						boolean deleted = file.delete();
						if(deleted){
							comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Đã xóa hình ảnh", Toast.LENGTH_SHORT);
							setImage();
						} else {
							comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không xóa được hình ảnh", Toast.LENGTH_LONG);
						}
					} catch(Exception ex) {
						comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Có lỗi khi xóa hình ảnh", Toast.LENGTH_LONG);
					}
				}
			});
			
			btnView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					lnButtonCamera.setVisibility(View.GONE);
					lnListCustom.setVisibility(View.GONE);
					lnButtonCloseCamera.setVisibility(View.VISIBLE);
					
					String filePath = Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + TEN_FILE + "/" + fileName + "_" + adapter.getItem(selected_index).get("MA_CTO") + "_" + adapter.getItem(selected_index).get("NAM") + "_" + adapter.getItem(selected_index).get("THANG") + "_" + adapter.getItem(selected_index).get("KY") + "_" + adapter.getItem(selected_index).get("MA_DDO") + "_" + adapter.getItem(selected_index).get("LOAI_BCS") + ".jpg";
					Bitmap bmImage = BitmapFactory.decodeFile(filePath);
					ivViewImage.setImageBitmapReset(bmImage, 0, true);
				}
			});
			
			btnCapture.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					cameraObject.takePicture(null, null, capturedIt);
//					try{
//						String ID_SQLITE = adapter.getItem(selected_index).get("ID_SQLITE");
//						(new captureImage(Activity_Camera_MTB.this)).execute(ID_SQLITE);
//					} catch(Exception ex) {
//						ex.toString();
//						comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "lỗi định dạng: " + ex.toString(), Toast.LENGTH_LONG);
//					}
				}
			});
			
			btnChange.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!maso.equals("")){
						checkChangeCamera = 1;
						Bundle b = new Bundle();
						b.putString("SO", maso);
						b.putString("KIEU_HTHI", kieu_hthi);
						
						Intent gcs_act = new Intent(Activity_Camera_MTB.this, Activity_Camera.class);
						gcs_act.putExtra("maso", b);
						startActivity(gcs_act);
						Activity_Camera_MTB.this.finish();
					} else {
						comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không chuyển được camera", Toast.LENGTH_LONG);
					}
				}
			});
			
			btnChucNang.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try{
						if(ibtScanner.isShown())
							showDialogMenu(ConstantVariables.MENU_PAGER_2);
						else
							showDialogMenu(ConstantVariables.MENU_PAGER);
					} catch(Exception ex) {
						
					}
				}
			});
			
			btnSoKhac.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showDialogChonSo();
				}
			});
			
			btnAnHienCamera.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(lnCamera.isShown()){
						lnCamera.setVisibility(View.GONE);
						btnAnHienCamera.setText("Hiện Camera");
					} else {
						lnCamera.setVisibility(View.VISIBLE);
						btnAnHienCamera.setText("Ẩn Camera");
					}
				}
			});
			
			btnGhi.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try{
						LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
						if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
							AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Camera_MTB.this);
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
						} else {
						if(!lnButtonCamera.isShown()){
							Common.state_show_camera = 1;
							lnButtonCamera.setVisibility(View.VISIBLE);
							lnListCustom.setVisibility(View.VISIBLE);
							lnButtonCloseCamera.setVisibility(View.GONE);
						}
						saveCustomerIsSelected();
						}
					}catch(Exception ex) {
						comm.msbox("Lỗi", "Lỗi dữ liệu", Activity_Camera_MTB.this);
					}
				}
			});
			
			btnXoa.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try{
						String selected = String.valueOf(spTieuChi.getSelectedItem());
						if (selected.equals("Chưa ghi")) {
							String col_selected = GetColNameFromString(String.valueOf(spTieuChi.getSelectedItem()));
							CharSequence cs = "Chưa ghi";
//							selected_index = 0;
							(new AsyncTaskCameraFilt(Activity_Camera_MTB.this)).execute(col_selected, "" + cs);
						} else if (selected.equals("Đã ghi")) {
							String col_selected = GetColNameFromString(String.valueOf(spTieuChi.getSelectedItem()));
							CharSequence cs = "Đã ghi";
//							selected_index = 0;
							(new AsyncTaskCameraFilt(Activity_Camera_MTB.this)).execute(col_selected, "" + cs);
						} else {
							etTimKiem.setText("");
						}

					} catch(Exception ex) {
						ex.toString();
					}
				}
			});
			
			etNgayPmax.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showTimePickerDialog();
				}
			});
	
			ibtScanner.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showBlueToothSettingBarcode();
				}
			});
			
			btnDong.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(lnButtonCloseCamera.isShown()){
						Common.state_show_camera = 1;
						lnButtonCamera.setVisibility(View.VISIBLE);
						lnListCustom.setVisibility(View.VISIBLE);
						lnButtonCloseCamera.setVisibility(View.GONE);
					}
				}
			});
			
		} catch(Exception ex){
			comm.msbox("Lỗi", "Lỗi khởi tạo sự kiện", Activity_Camera_MTB.this);
		}
	}
	
	private void setupSimpleList() {
		mSimpleListValues.clear();
		final String[] ma_so_gcs = maso.split(":");
		for (String str : ma_so_gcs) {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("so", str);
			mSimpleListValues.add(map);
		}
		final CustomArrayAdapterCameraMTB adapter = new CustomArrayAdapterCameraMTB(this.getApplicationContext(), R.layout.custom_data_view, mSimpleListValues);
        hlvSimpleList.setAdapter(adapter);
        
        if(ma_so_gcs.length > 1){
        	hlvSimpleList.setSelection(Common.pos_tab);
        	etTimKiem.setText("");
			pos_so = Common.pos_tab;
			adapter.notifyDataSetChanged();
			fileName = mSimpleListValues.get(Common.pos_tab).get("so");
			if(Common.pos_change_camera != -1)
				selected_index = Common.pos_change_camera;
			else
				selected_index = getPos();
        }
        	
        hlvSimpleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try{
					Common.pos_tab = position;
					etTimKiem.setText("");
					pos_so = position;
					adapter.notifyDataSetChanged();
					fileName = mSimpleListValues.get(position).get("so");
					selected_index = getPos();
					(new AsyncTaskCameraMTB(Activity_Camera_MTB.this, kieu_hthi)).execute(fileName);
					createPhotoFolder(fileName);
				} catch(Exception ex) {
					ex.toString();
				}
			}
		});
        
        if(ma_so_gcs.length <= 1){
        	hlvSimpleList.setVisibility(View.GONE);
        }
    }

	/**
	 * Tạo spinner tiêu chí
	 * 
	 */
	private void CreateSpnTieuChi() {
		try {
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getApplicationContext(),
					R.layout.listview_single_choice, ConstantVariables.TIEU_CHI_TIM_KIEM);
			dataAdapter.setDropDownViewResource(R.layout.listview_single_choice);
			spTieuChi.setAdapter(dataAdapter);

			spTieuChi.post(new Runnable() {

				@Override
				public void run() {
					spTieuChi.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(
										AdapterView<?> parent,
										View selectedView, int position, long id) {
									try{
										String Selected = spTieuChi.getSelectedItem().toString();
										if (Selected.equals("STT")
												|| Selected.equals("Số C.Tơ")
												|| Selected.equals("CS cũ")
												|| Selected.equals("SL cũ")
												|| Selected.equals("SL mới")) {
											etTimKiem.setEnabled(true);
											etTimKiem.setInputType(InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_FLAG_DECIMAL);
										} else {
											etTimKiem.setEnabled(true);
											etTimKiem.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
										}
										if (Selected.equals("Chưa ghi")) {
											etTimKiem.setText("Chưa ghi");
											etTimKiem.setEnabled(false);
											btnXoa.setText("Tìm");
										} else if (Selected.equals("Đã ghi")) {
											etTimKiem.setText("Đã ghi");
											etTimKiem.setEnabled(false);
											btnXoa.setText("Tìm");
										} else if (!btnXoa.getText().equals("Xoa")) {
											etTimKiem.setText("");
											btnXoa.setText("Xóa");
										}
										etTimKiem.requestFocus();
									} catch(Exception ex) {
										comm.msbox("Lỗi", ex.toString(), Activity_Camera_MTB.this);
									}
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> parent) {

								}

							});
				}
			});

		} catch (Exception ex) {
			comm.msbox("Lỗi", "Lỗi tạo tiêu chí tìm kiếm", Activity_Camera_MTB.this);
		}

	}

	/**
	 * Tạo spinner trạng thái
	 * 
	 */
	private void CreateSpnTrangThai() {
		try {
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getApplicationContext(),
					R.layout.listview_single_choice, ConstantVariables.ARR_TRANGTHAI);
			dataAdapter.setDropDownViewResource(R.layout.listview_single_choice);
			spTrangThai.setAdapter(dataAdapter);
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Lỗi tạo danh mục trạng thái", Activity_Camera_MTB.this);
		}

	}

	/*private void CreateDialogCanhBao(int color, String canhbao,
			final String ID_SQLITE, final Float CS_MOI, final Float SL_MOI,
			final String SO_CTO) {
				try {

			final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_canhbao);
			dialog.getWindow().setLayout(
					android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			ImageButton btnDong = (ImageButton) dialog.findViewById(R.id.btnDong);
			Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
			Button btnOK = (Button) dialog.findViewById(R.id.btnOk);
			LinearLayout lnTittle = (LinearLayout) dialog.findViewById(R.id.lnTitle);
			TextView tvCanhBao = (TextView) dialog.findViewById(R.id.tvCanhBao);

			tvCanhBao.setText(canhbao);
			lnTittle.setBackgroundColor(color);

			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			btnOK.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CanhBaoChenhLechTongSLCto3Pha(1, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					dialog.dismiss();
				}
			});

			btnDong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			dialog.show();
			dialog.getWindow().setGravity(Gravity.BOTTOM);
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Thông báo bị lỗi", this);
		}
	}
*/
	// ------------------- CÁC HÀM XỬ LÝ HỘP THOẠI ---------------------


	/** Cảnh báo sản lượng bất thường
	 */

	private void CreateDialogCanhBao(int color, String canhbao,
									 final String ID_SQLITE, final Float CS_MOI, final Float SL_MOI,
									 final String tinh_trang_moi, final String LOAI_BCS,
									 final String SO_CTO) {
		try {
			final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_canhbao);
			dialog.getWindow().setLayout(
					android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			ImageButton btnDong = (ImageButton) dialog.findViewById(R.id.btnDong);
			Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
			Button btnOK = (Button) dialog.findViewById(R.id.btnOk);
			LinearLayout lnTittle = (LinearLayout) dialog.findViewById(R.id.lnTitle);
			TextView tvCanhBao = (TextView) dialog.findViewById(R.id.tvCanhBao);

			tvCanhBao.setText(canhbao);
			lnTittle.setBackgroundColor(color);

			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			btnOK.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CanhBaoChenhLechTongSLCto3Pha(1, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					dialog.dismiss();
				}
			});

			btnDong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			dialog.show();
			dialog.getWindow().setGravity(Gravity.BOTTOM);
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Thông báo bị lỗi", this);
		}
	}
	/** Cảnh báo xác nhận vượt quá nhiều sản lượng
	 */
	private void CreateDialogCanhBaoXacNhan(int color, String canhbao,
			final String ID_SQLITE, final Float CS_MOI, final Float SL_MOI,
			final String tinh_trang_moi, final String LOAI_BCS,
			final String SO_CTO) {
		try {
			final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_canhbao_xacnhan);
			dialog.getWindow().setLayout(
					android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			dialog.setCanceledOnTouchOutside(false);

			ImageButton btnDong = (ImageButton) dialog.findViewById(R.id.btnDong);
			Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
			Button btnOK = (Button) dialog.findViewById(R.id.btnOk);
			LinearLayout lnTittle = (LinearLayout) dialog.findViewById(R.id.lnTitle);
			TextView tvCanhBao = (TextView) dialog.findViewById(R.id.tvCanhBao);
			
			tvCanhBao.setText(canhbao);
			lnTittle.setBackgroundColor(color);
			
			btnCancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			
			btnOK.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CanhBaoChenhLechTongSLCto3Pha(1, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					dialog.dismiss();
				}
			});
			
			btnDong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			dialog.show();
			dialog.getWindow().setGravity(Gravity.BOTTOM);
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Thông báo bị lỗi", this);
		}
	}
	
	// ------------------- CÁC HÀM XỬ LÝ CHỨC NĂNG ---------------------
	/**
	 * Tạo sự kiện khi nhập chuỗi lọc
	 * 
	 */
	public void CreateEventTimKiem() {

		etTimKiem.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int start, int before, int after) {
				try {
					String col_selected = GetColNameFromString(String.valueOf(spTieuChi.getSelectedItem()));
					if(col_selected.equals("CHUA_GHI") || col_selected.equals("DA_GHI")){
						return;
					} else if (col_selected.length() > 0 ) {
						(new AsyncTaskCameraFiltMTB(Activity_Camera_MTB.this)).execute(col_selected, "" + cs);
					}
//					if(etTimKiem.getText().toString().equals("")){
//						Activity_Camera.this.lvCustomer.setSelection(selected_index);
//					}
				} catch (Exception ex) {
					comm.msbox("Lọc dữ liệu", "Lọc dữ liệu thất bại", Activity_Camera_MTB.this);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
			}

			@Override
			public void afterTextChanged(Editable et) {
				try{
					if(etTimKiem.getText().toString().equals("")){
						selected_index = getPos();
						Activity_Camera_MTB.this.lvCustomer.setSelection(selected_index);
						setDataOnEditText(selected_index, 0);
						etCSMoi.requestFocus();
						etCSMoi.selectAll();
						showHidePmax(selected_index);
					}
				} catch(Exception ex) {
//					comm.msbox("Lỗi", "afterTextChanged: " + ex.toString(), Activity_Camera.this);
				}
			}
		});

	}
	
	public void findDataOnListview(final ArrayList<LinkedHashMap<String, String>> ListViewData, final String col_selected, final CharSequence constraint) {
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
					try {
						Common Common = new Common();
						ArrayList<LinkedHashMap<String, String>> filt = new ArrayList<LinkedHashMap<String,String>>();
						for (LinkedHashMap<String, String> map : ListViewData) {
							String val = null;
							// nếu ko phải tìm các kh chưa ghi và đã ghi
							if (!col_selected.equals("CHUA_GHI") && !col_selected.equals("DA_GHI")) {
								val = map.get(col_selected);
								if(val != null ){
									val = val.toLowerCase();
								}
							}
							// kiểm tra theo tên ko dấu và có dấu
							if (col_selected.equals("TEN_KHANG") || col_selected.equals("DIA_CHI")) {
								String ten_bo_dau = Common.VietnameseTrim(val);
								if (val.toLowerCase().contains(constraint) || ten_bo_dau.toLowerCase().contains(
												Common.VietnameseTrim(constraint.toString().toLowerCase()))) {
									filt.add(map);
								}
							} else if (col_selected.equals("CHUA_GHI")) {
								String valTTR_MOI = map.get("TTR_MOI");          
								String valCS_MOI = map.get("CS_MOI");
								if ((valTTR_MOI == null || valTTR_MOI.trim().equals(""))
										&& (valCS_MOI == null || valCS_MOI.trim().length() == 0 || Float.parseFloat(valCS_MOI) == 0)) {
									filt.add(map);
								}
							} else if (col_selected.equals("DA_GHI")) {
								String valTTR_MOI = map.get("TTR_MOI");          
								String valCS_MOI = map.get("CS_MOI");
								if ((valTTR_MOI != null && !valTTR_MOI.trim().equals(""))
										|| (valCS_MOI != null && valCS_MOI.trim().length() != 0 && Float.parseFloat(valCS_MOI) != 0)) {
									filt.add(map);
								}
							} else if (val != null && val.contains(constraint)) {
								filt.add(map);
							};
						}
						
						if(!etTimKiem.getText().toString().equals("") && filt.size() == 0){
							comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không có khách hàng thỏa mãn nội dung tìm kiếm", Toast.LENGTH_LONG);
							return;
						}
						adapter = new AdapterCameraMTB(Activity_Camera_MTB.this.getApplicationContext(), filt, R.layout.listview_gcs);
						adapter.setDropDownViewResource(R.layout.listview_single_choice);
	
						lvCustomer.setAdapter(adapter);
					} catch (Exception ex) {
						comm.msbox("Lỗi", "Không lấy được dữ liệu tìm kiếm", Activity_Camera_MTB.this);
					}
//				}
//			}).start();
	}
	
	@SuppressWarnings("unchecked")
	private int setListViewLogDelete(ListView lsvGCS,
			ArrayList<LinkedHashMap<String, String>> ListViewData,
			String fileName) {
		try {
			int stt = 0;
			int count_report = 0;
			Cursor c = connection.getDataLogByMaQuyen(fileName);
			if (c.moveToFirst()) {
				do {
					stt++;
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("STT", "" + stt);
					map.put("MA_QUYEN", fileName);
					map.put("SERY_CTO", c.getString(2));
					map.put("LY_DO", c.getString(5));
					map.put("NGAY_XOA", c.getString(4));
					map.put("CS_XOA", c.getString(3));
					ListViewData.add(map);
					count_report++;
				} while (c.moveToNext());
			}
			AdapterLogDelete adapter = new AdapterLogDelete(this.getApplicationContext(),(ArrayList<LinkedHashMap<String, String>>) ListViewData.clone(), R.layout.listview_log_delete);
			lsvGCS.setAdapter(adapter);
			return count_report;
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được dữ liệu khách hàng đã xóa", this);
			return 0;
		}
	}
	
	private void showDialogMenu(String [] menu) {
		try {
			final Dialog dialog = new Dialog(this);

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_menu);
			dialog.getWindow().setLayout(
					android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			final ListView lvMenu = (ListView) dialog.findViewById(R.id.lvMenu);
			MenuAdapter2 mAdapter = new MenuAdapter2(this.getApplicationContext(), R.id.tvMenu, menu);
			lvMenu.setAdapter(mAdapter);
			
			lvMenu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					switch (pos) {
					case 0: // Chi tiết khách hàng
						ShowDialogChiTiet(GetDataByID(adapter.getItem(selected_index).get("ID_SQLITE")));
						break;
					case 1: // Thêm ghi chú
						showDialogAddNote(GetDataByID(adapter.getItem(selected_index).get("ID_SQLITE")));
						break;
					case 2: // Xóa chỉ số
						if(Common.PHIEN_BAN.equals("SL")){
							showDialogLogDelete(adapter.getItem(selected_index).get("ID_SQLITE"));
						} else {
							clearCSM(adapter.getItem(selected_index).get("ID_SQLITE"));
						}
						break;
						
					case 3: // Xem danh sách cto đã xóa chỉ số
						if(Common.PHIEN_BAN.equals("SL")){
							CreateDialogLogDelete(fileName);
						} else {
							comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Chức năng chỉ sử dụng cho Điện lực Sơn La", Toast.LENGTH_SHORT);
						}
						break;
					case 4: // In danh sách công tơ có sản lượng bất thường
						CreateDialog_SLBT(fileName);
						break;
					case 5: // In danh sách công tơ có trạng thái bất thường
						CreateDialog_TTBT(fileName);
						break;
					case 6: // Danh sách công tơ có sản lượng vượt quá 200%
						CreateDialog_KXN(fileName);
						break;
					case 7: // Thêm khách hàng phát triển mới
						CreateDialogNewCustomer();
						break;
					case 8: // Xem bản đồ
						showMap(adapter.getItem(selected_index).get("ID_SQLITE"));
						break;
					case 9: // Xem toàn bộ vị trí trên bản đồ
						showAllMap();
						break;
					case 10: // Tạm tính tiền điện
						ShowTamTinhTien(GetDataByID(adapter.getItem(selected_index).get("ID_SQLITE")));
						break;
					case 11: // quét mã vạch
						if(!ibtScanner.isShown()){
							ibtScanner.setVisibility(View.VISIBLE);
						} else {
							ibtScanner.setVisibility(View.GONE);
						}
						dialog.dismiss();
						break;
					case 12: // In thông báo
						try {
							row_gcs rGCS = GetDataByID(adapter.getItem(selected_index).get("ID_SQLITE"));
							if((rGCS.getCS_MOI() != null && Float.parseFloat(rGCS.getCS_MOI()) != 0) || (rGCS.getTTR_MOI() != null && !rGCS.getTTR_MOI().equals(""))){
								(new InThongBao()).PrintMsg(rGCS, Activity_Camera_MTB.this.getApplicationContext());
							} else {
								comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Khách hàng chưa có chỉ số", 2000);
							}
						} catch (Exception ex) {
							comm.msbox("Thông báo", "Không thể in thông báo", Activity_Camera_MTB.this);
						}
						break;
					case 13: // Chọn máy in
						ShowActivityConnPrinter();
						break;
					}
					dialog.dismiss();
				}
			});

			dialog.show();
		} catch(Exception ex) {
			
		}
	}
	
	/** Khởi tạo giá trị cho entity
	 */
	private row_gcs GetDataByID(String id) {
		try {
			EntityGCS e = connection.getDataByMAGC(id);
			row_gcs gcs = new row_gcs(e.getMA_NVGCS(), e.getMA_KHANG(),
					e.getMA_DDO(), e.getMA_DVIQLY(), e.getMA_GC(),
					e.getMA_QUYEN(), e.getMA_TRAM(), e.getBOCSO_ID(),
					e.getLOAI_BCS(), e.getLOAI_CS(), e.getTEN_KHANG(),
					e.getDIA_CHI(), e.getMA_NN(), e.getSO_HO(), e.getMA_CTO(),
					e.getSERY_CTO(), e.getHSN(), e.getCS_CU(), e.getTTR_CU(),
					e.getSL_CU(), e.getSL_TTIEP(), e.getNGAY_CU(),
					e.getCS_MOI(), e.getTTR_MOI(), e.getSL_MOI(),
					e.getCHUOI_GIA(), e.getKY(), e.getTHANG(), e.getNAM(),
					e.getNGAY_MOI(), e.getNGUOI_GCS(), e.getSL_THAO(),
					e.getKIMUA_CSPK(), e.getMA_COT(), e.getCGPVTHD(),
					e.getHTHUC_TBAO_DK(), e.getDTHOAI_SMS(), e.getEMAIL(),
					e.getTHOI_GIAN(), e.getX(), e.getY(), e.getSO_TIEN(),
					e.getHTHUC_TBAO_TH(), e.getTTHAI_DBO(), e.getDU_PHONG(),
					e.getGHICHU(), e.getTT_KHAC(), e.getID_SQLITE(),
					e.getSLUONG_1(), e.getSLUONG_2(), e.getSLUONG_3(),
					e.getSO_HOM(), e.getPMAX(), e.getNGAY_PMAX());
			return gcs;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Show dialog thông tin chi tiết của công tơ
	 */
	@SuppressLint("DefaultLocale") @SuppressWarnings("unused")
	private void ShowDialogChiTiet(row_gcs gcs_data) {
		try {
			final Dialog dialog = new Dialog(this);

			Float TongSLHC = 0F;
			Float TongSLVC = 0F;
			Float sl_thao = 0F;
			Float sl_moi = 0F;
			int SoTTBatThuong = 0;
			int daghi = 0;
			String loai_bcs;

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_gcs_chi_tiet);
			dialog.getWindow().setLayout(
					android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			ImageButton btnCloseDialog = (ImageButton) dialog.findViewById(R.id.btnClose);

			for (int i = 0; i < arrCustomer.size(); i++) {
				LinkedHashMap<String, String> row = arrCustomer.get(i);
				loai_bcs = row.get("LOAI_BCS").toUpperCase();
				sl_thao = Float.parseFloat(row.get("SL_THAO"));
				sl_moi = Float.parseFloat(row.get("SL_MOI"));

				if (("BT,CD,TD,KT").contains(loai_bcs)) {
					TongSLHC += sl_moi + sl_thao;
				} else if (("VC").contains(loai_bcs)) {
					TongSLVC += sl_moi + sl_thao;
				} else if (("SG").contains(loai_bcs)) {

				}
				String s1 = row.get("TTR_MOI");

				if (row.get("TTR_MOI") != null && row.get("TTR_MOI").trim().length() > 0) {
					SoTTBatThuong++;
				}
				try {
					if (comm.isSavedRow(row.get("TTR_MOI"), row.get("CS_MOI"))) {
						daghi++;
					}
				} catch (Exception ex) {
				}
			}

			btnCloseDialog.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			TextView tv_Title = (TextView) dialog.findViewById(R.id.tv_Title);
			TextView tv_TitleTongKet = (TextView) dialog.findViewById(R.id.tv_TitleTongKet);
			TextView tv_TongSLHC = (TextView) dialog.findViewById(R.id.tv_TongSLHC);
			TextView tv_TongSLVC = (TextView) dialog.findViewById(R.id.tv_TongSLVC);
			TextView tv_TiLeNhap = (TextView) dialog.findViewById(R.id.tv_TiLeNhap);
			TextView tv_TTBatThuong = (TextView) dialog.findViewById(R.id.tv_TTBatThuong);
			TextView tv_TenKH = (TextView) dialog.findViewById(R.id.tv_TenKH);
			TextView tv_MaKH = (TextView) dialog.findViewById(R.id.tv_MaKH);
			TextView tv_DiaChi = (TextView) dialog.findViewById(R.id.tv_DiaChi);
			TextView tv_MaDiemDo = (TextView) dialog.findViewById(R.id.tv_MaDiemDo);
			TextView tv_BCS = (TextView) dialog.findViewById(R.id.tv_BCS);
			TextView tv_MaGC = (TextView) dialog.findViewById(R.id.tv_MaGC);
			TextView tv_MaTram = (TextView) dialog.findViewById(R.id.tv_MaTram);
			TextView tv_MaNN = (TextView) dialog.findViewById(R.id.tv_MaNN);
			TextView tv_MaCot = (TextView) dialog.findViewById(R.id.tv_MaCot);
			TextView tv_SoHo = (TextView) dialog.findViewById(R.id.tv_SoHo);
			TextView tv_SoCTo = (TextView) dialog.findViewById(R.id.tv_SoCTo);
			TextView tv_HSN = (TextView) dialog.findViewById(R.id.tv_HSN);
			TextView tv_SLThao = (TextView) dialog.findViewById(R.id.tv_SLThao);
			TextView tv_CSCu = (TextView) dialog.findViewById(R.id.tv_CSCu);
			TextView tv_SLCu = (TextView) dialog.findViewById(R.id.tv_SLCu);
			TextView tv_CSMoi = (TextView) dialog.findViewById(R.id.tv_CSMoi);
			TextView tv_SLMoi = (TextView) dialog.findViewById(R.id.tv_SLMoi);
			TextView tv_ChuoiGia = (TextView) dialog.findViewById(R.id.tv_ChuoiGia);
			TextView tv_Ghichu = (TextView) dialog.findViewById(R.id.tv_Ghichu);
			TextView tv_Pmax = (TextView) dialog.findViewById(R.id.tv_Pmax);
			TextView tv_Ngay_Pmax = (TextView) dialog.findViewById(R.id.tv_Ngay_Pmax);
			CheckBox chkbchkbCSPK = (CheckBox) dialog.findViewById(R.id.chkbCSPK);
			

//			int idxEnd = fileName.lastIndexOf("_");
//			idxEnd = idxEnd == -1 ? fileName.lastIndexOf(".xml") : idxEnd;
//			String shortName = fileName.substring(0, idxEnd);
			tv_Title.setText("Quyển: " + fileName);
			tv_TitleTongKet.setText("");
			tv_TitleTongKet.setVisibility(View.GONE);

			tv_TongSLHC.setText(TongSLHC + "");
			tv_TongSLVC.setText(TongSLVC + "");
			tv_TiLeNhap.setText(daghi + "/" + connection.checkDataGCS(fileName));
			tv_TTBatThuong.setText(SoTTBatThuong + "/" + connection.checkDataGCS(fileName));

			tv_TenKH.setText(gcs_data.getTEN_KHANG());
			tv_MaKH.setText(gcs_data.getMA_KHANG());
			tv_DiaChi.setText(gcs_data.getDIA_CHI());
			tv_MaDiemDo.setText(gcs_data.getMA_DDO());
			tv_BCS.setText(gcs_data.getLOAI_BCS());
			tv_MaGC.setText(gcs_data.getMA_GC());
			tv_MaTram.setText(gcs_data.getMA_TRAM());
			tv_MaNN.setText(gcs_data.getMA_NN());
			tv_MaCot.setText(gcs_data.getMA_COT());
			tv_SoHo.setText(gcs_data.getSO_HO());
			tv_SoCTo.setText(gcs_data.getSERY_CTO());
			tv_HSN.setText(gcs_data.getHSN());
			tv_SLThao.setText(gcs_data.getSL_THAO());
			tv_CSCu.setText(gcs_data.getCS_CU());
			tv_SLCu.setText(gcs_data.getSL_CU());
			tv_CSMoi.setText(gcs_data.getCS_MOI());
			tv_SLMoi.setText(gcs_data.getSL_MOI());
			tv_ChuoiGia.setText(gcs_data.getCHUOI_GIA());
			tv_Ghichu.setText(gcs_data.getGHICHU());
			chkbchkbCSPK.setChecked(Boolean.parseBoolean(gcs_data.getKIMUA_CSPK()));
			tv_Pmax.setText(gcs_data.getPMAX());
			if(gcs_data.getNGAY_PMAX().equals("01/01/1000 00:00:00")){
				tv_Ngay_Pmax.setText("");
			} else {
				tv_Ngay_Pmax.setText(gcs_data.getNGAY_PMAX());
			}

			dialog.show();
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Không xem đươc chi tiết: ", Activity_Camera_MTB.this);
		}

	}
	
	/** Show dialog thêm ghi chú
	 */
	private void showDialogAddNote(row_gcs gcs_data) {
		try {
			final Dialog dialog = new Dialog(this);

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_ghichu);
			dialog.getWindow().setLayout(
					android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);

			final EditText etGhichu = (EditText) dialog.findViewById(R.id.etGhichu);
			Button btnGhichu = (Button) dialog.findViewById(R.id.btnGhichu);
			Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
			ImageButton ibtDong_gc = (ImageButton) dialog.findViewById(R.id.ibtDong_gc);

			ibtDong_gc.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			etGhichu.setText(gcs_data.getGHICHU());

			btnGhichu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String ghichu = etGhichu.getText().toString();
					if (connection.updateNoteGCS(adapter.getItem(selected_index).get("ID_SQLITE"), ghichu) != -1) {
						comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Đã thêm ghi chú", Toast.LENGTH_LONG);
						adapter.notifyDataSetChanged();
						lvCustomer.invalidate();
					} else {
						comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không thêm được ghi chú", Toast.LENGTH_LONG);
					}
					dialog.dismiss();
				}
			});

			btnHuy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					etGhichu.setText("");
				}
			});

			dialog.show();
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Không thêm đươc ghi chú", Activity_Camera_MTB.this);
		}
	}
	
	/** Xóa chỉ số mới
	 * 
	 */
	@SuppressLint("SimpleDateFormat")
	private void clearCSM(String ID_SQLITE){
		try{
			// cập nhật thời gian lúc ghi
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String THOI_GIAN = sdf.format(new Date());
			// cập nhật vị trí GPS
			double latitude = 0;
			double longitude = 0;
			if (gps.canGetLocation()) {
				latitude = gps.getLatitude();
				longitude = gps.getLongitude();
			} else {
				
			}
			String X = latitude + "";
			String Y = longitude + "";
			String TTHAI_DBO = "1";
			String LOAI_BCS = connection.getBCSById(ID_SQLITE);
			Cursor c = connection.getDataCS(ID_SQLITE);
			if(c.moveToFirst()){
				if(comm.isSavedRow(c.getString(1), c.getString(0))){
					tvDaGhi.setText((Integer.parseInt(tvDaGhi.getText().toString().split("/")[0]) - 1) + "/" + tvDaGhi.getText().toString().split("/")[1]);
				}
			}
			String STR_CHECK_DSOAT = "";
			if(!LOAI_BCS.equals("KT")){
				if (connection.updateDataGCS2(ID_SQLITE, "0", "", "0", THOI_GIAN, X, Y, TTHAI_DBO, "", "", "", STR_CHECK_DSOAT) != -1) {
					spTrangThai.setSelection(0);
					etCSMoi.setText("0");
					etCSMoi.selectAll();
//					setDataListview(fileName);
					reLoadData(selected_index, "0", "0", "", "0", "");
					etPmax.setText("");
					etNgayPmax.setText("Ngày thực hiện");
					etNgayPmax.setTextColor(Color.GRAY);
				} else {
					comm.ShowToast(this.getApplicationContext(), "Không xóa được chỉ số", Toast.LENGTH_LONG);
				}
			} else {
				if (connection.updateDataGCS(ID_SQLITE, "0", "", "0", THOI_GIAN, X, Y, TTHAI_DBO, "", STR_CHECK_DSOAT) != -1) {
					spTrangThai.setSelection(0);
					etCSMoi.setText("0");
					etCSMoi.selectAll();
//					setDataListview(fileName);
					reLoadData(selected_index, "0", "0", "", "0", "");
				} else {
					comm.ShowToast(this.getApplicationContext(), "Không xóa được chỉ số", Toast.LENGTH_LONG);
				}
			}
			
		} catch(Exception ex){
			
		}
	}
	
	/** Tạo dialog tổng hợp sổ có chỉ số bất thường
	 * 
	 * 
	 */
	private void CreateDialog_SLBT(final String fileName) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_csbtct);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		final ArrayList<LinkedHashMap<String, String>> ListViewData = new ArrayList<LinkedHashMap<String, String>>();

		try {
			int count_report = 0;
			ListView lsvGCS = (ListView) dialog.findViewById(R.id.lsvGCS_csbt);
			count_report = setListViewSLBT(lsvGCS, ListViewData, fileName);

			ImageButton btnDong_csbt = (ImageButton) dialog.findViewById(R.id.btnDong_csbtct);
			btnDong_csbt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			lsvGCS.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					try {
						ShowDialogChiTiet(GetDataByID(adapter_slbt.getItem(pos).get("ID_SQLITE")));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			if (count_report == 0) {
				comm.msbox("Thông báo", "Không có công tơ nào có chỉ số bất thường", Activity_Camera_MTB.this);
			} else {
				dialog.show();
			}
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được danh sách sản lượng bất thường", Activity_Camera_MTB.this);
		}
	}

	/**
	 * Tạo dialog tổng hợp công tơ có tình trạng bất thường
	 * 
	 */
	private void CreateDialog_TTBT(String fileName) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_ttbtct);
		dialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		final ArrayList<LinkedHashMap<String, String>> ListViewData = new ArrayList<LinkedHashMap<String, String>>();

		try {
			int count_report = 0;
			ListView lsvGCS = (ListView) dialog.findViewById(R.id.lsvGCS_ttbt);
			count_report = setListViewTTBT(lsvGCS, ListViewData, fileName);

			ImageButton btnDong_csbt = (ImageButton) dialog.findViewById(R.id.btnDong_ttbt);
			btnDong_csbt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			lsvGCS.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					try {
						ShowDialogChiTiet(GetDataByID(adapter_ttbt.getItem(pos).get("ID_SQLITE")));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			if (count_report == 0) {
				comm.msbox("Thông báo", "Không có công tơ nào có trạng thái bất thường", Activity_Camera_MTB.this);
			} else {
				dialog.show();
			}
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được dữ liệu 5", Activity_Camera_MTB.this);
		}
	}
	
	/** Tạo dialog danh sách khách hàng yêu cầu ký xác nhận
	 */
	private void CreateDialog_KXN(String fileName) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_kxn);
		dialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		final ArrayList<LinkedHashMap<String, String>> ListViewData = new ArrayList<LinkedHashMap<String, String>>();

		try {
			int count_report = 0;
			ListView lsvGCS = (ListView) dialog.findViewById(R.id.lsvGCS_csbt);
			count_report = setListViewKXN(lsvGCS, ListViewData, fileName);

			ImageButton btnDong_csbt = (ImageButton) dialog.findViewById(R.id.btnDong_csbtct);
			btnDong_csbt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			lsvGCS.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					try {
						ShowDialogChiTiet(GetDataByID(adapter_kxn.getItem(pos).get("ID_SQLITE")));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			if (count_report == 0) {
				comm.msbox("Thông báo", "Không có công tơ nào có yêu cầu ký xác nhận", Activity_Camera_MTB.this);
			} else {
				dialog.show();
				count_report = 0;
			}
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được dữ liệu", Activity_Camera_MTB.this);
		}
	}
	
	/**
	 * Tạo dialog thêm khách hàng mới
	 * 
	 */
	private void CreateDialogNewCustomer() {
		try {
			final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_new_customer);
			dialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			final ListView lvNewCustomer = (ListView) dialog.findViewById(R.id.lvNewCustomer);
			loadDataCustomer(lvNewCustomer);

			lvNewCustomer.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					ArrayList<LinkedHashMap<String, String>> arr_cus = connection.getListCustomer();
					LinkedHashMap<String, String> map_cus = arr_cus.get(pos);
					showDialogAddCustomer(lvNewCustomer, map_cus.get("NOI_DUNG"), Integer.parseInt(map_cus.get("ID")));
				}

			});

			ImageButton ibtDong = (ImageButton) dialog.findViewById(R.id.ibtDong);
			ibtDong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			
			Button btnThem = (Button) dialog.findViewById(R.id.btnNew);
			btnThem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showDialogAddCustomer(lvNewCustomer, "", 0);
				}
			});
			
			Button btnXoa = (Button) dialog.findViewById(R.id.btnDelete);
			btnXoa.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try{
						ArrayList<LinkedHashMap<String, String>> arr_cus = connection.getListCustomer();
						if(Common.lst_pos.size() > 0){
							int count = 0;
							List<String> lst_ID = new ArrayList<String>();
							for (String string_pos : Common.lst_pos) {
								int pos = Integer.parseInt(string_pos);
								lst_ID.add(arr_cus.get(pos).get("ID"));
							}
							for (String string_ID : lst_ID) {
								int ID = Integer.parseInt(string_ID);
								if(connection.deleteDataCustomerByID(ID) != -1){
									count++;
								}
							}
							loadDataCustomer(lvNewCustomer);
							comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Đã xóa " + count + " khách hàng", Toast.LENGTH_LONG);
							Common.lst_pos.clear();
						} else {
							comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Bạn phải chọn khách hàng cần xóa", Toast.LENGTH_LONG);
						}
					} catch(Exception ex) {
						comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Lỗi khi xóa khách hàng", Toast.LENGTH_LONG);
					}
				}
			});

			dialog.show();
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Không mở được danh sách khách hàng phát triển mới", Activity_Camera_MTB.this);
		}
	}
	
	/**Show dialog add new customer
	 * 
	 */
	private void showDialogAddCustomer(final ListView lvNewCustomer, String noi_dung, final int ID) {
		try {
			final Dialog dialog = new Dialog(this);

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_ghichu);
			dialog.getWindow().setLayout(
					android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);

			final EditText etGhichu = (EditText) dialog.findViewById(R.id.etGhichu);
			Button btnGhichu = (Button) dialog.findViewById(R.id.btnGhichu);
			Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
			ImageButton ibtDong_gc = (ImageButton) dialog.findViewById(R.id.ibtDong_gc);
			TextView tvTittle = (TextView) dialog.findViewById(R.id.tvTitle);

			tvTittle.setText("Thêm khách hàng phát triển mới");
			ibtDong_gc.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			etGhichu.setText(noi_dung);
			btnGhichu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(!etGhichu.getText().toString().equals("")){
						if(ID == 0){// insert
							if(connection.insertDataCustomer(etGhichu.getText().toString()) != -1){
								loadDataCustomer(lvNewCustomer);
								comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Thêm thành công khách hàng mới", Toast.LENGTH_LONG);
							} else {
								comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không thêm được khách hàng mới", Toast.LENGTH_LONG);
							}
						} else {// update
							if(connection.updateDataCustomer(ID, etGhichu.getText().toString()) != -1){
								loadDataCustomer(lvNewCustomer);
								comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Sửa thành công khách hàng mới", Toast.LENGTH_LONG);
							} else {
								comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không sửa được nội dung khách hàng mới", Toast.LENGTH_LONG);
							}
						}
						dialog.dismiss();
					} else {
						comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Bạn phải nhập nội dung", Toast.LENGTH_LONG);
					}
				}
			});

			btnHuy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					etGhichu.setText("");
				}
			});

			dialog.show();
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Không thêm đươc ghi chú", Activity_Camera_MTB.this);
		}
	}
	
	/** Tạm tính tiền điện
	 * 
	 */
	@SuppressLint("NewApi") private void ShowTamTinhTien(row_gcs rGCS){
		try{
			int SOKYTU = 32;
			InThongBao in_tb = new InThongBao();
			ArrayList<String> lstTinhTien = null;
			if(!Common.PHIEN_BAN.equals("SL")){
				lstTinhTien = in_tb.CreateStringTamTinhTien(rGCS);
			} else {
				lstTinhTien = in_tb.CreateStringTamTinhTien_SonLa(rGCS);
			}
			ArrayList<String> listText = new ArrayList<String>();
	        for (String text : lstTinhTien)
	        {
	        	ArrayList<String> listTemp = in_tb.TachChuList(text, SOKYTU);
	            for (String listItem : listTemp)
	            {
	                listText.add(listItem);
	            }
	        }
	        
	        String strPrint = "";
	        for (String listTextItem : listText)
	        {
	            if (listTextItem != null && !listTextItem.trim().isEmpty()) 
	            	strPrint += String.format("%-"+SOKYTU+"s", listTextItem) + "\n";
	        }
			comm.msbox("Tiền tạm tính", strPrint, Activity_Camera_MTB.this);
		} catch(Exception ex) {
			comm.msbox("Thông báo", "Bạn chưa có file biểu giá", Activity_Camera_MTB.this);
		}
	}
	
	/**Load dữ liệu vào list khách hàng phát triển mới
	 * 
	 */
	private void loadDataCustomer(ListView lvNewCustomer){
		try{
			ArrayList<LinkedHashMap<String,String>> arr_customer = new ArrayList<LinkedHashMap<String,String>>();
			Cursor c = connection.getAllDataCustomer();
			int stt = 0;
			if(c.moveToFirst()){
				do{
					stt++;
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("STT", "" + stt);
					map.put("ID", c.getString(0));
					map.put("NOI_DUNG", c.getString(1));
					arr_customer.add(map);
				} while(c.moveToNext());
			}
			CustomerAdapter mAdapter = new CustomerAdapter(Activity_Camera_MTB.this.getApplicationContext(), arr_customer, R.id.tvSTT);
			lvNewCustomer.setAdapter(mAdapter);
		} catch(Exception ex) {
			comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không lấy được danh sách khách hàng phát triển mới", Toast.LENGTH_LONG);
		}
	}
	
	@SuppressWarnings("unchecked")
	private int setListViewKXN(ListView lsvGCS,
			ArrayList<LinkedHashMap<String, String>> ListViewData,
			String fileName) {
		try {
			int stt = 0;
			int count_report = 0;
			Cursor c = connection.getAllDataGCS(fileName);
			if (c.moveToFirst()) {
				do {
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("TEN_SO", fileName);
					for (int j = 1; j < ConstantVariables.COLARR.length - 4; j++) {
						map.put(ConstantVariables.COLARR[j], c.getString(j));
					}

					float SLChenhLech = 0;
					float SL_Cu = Float.parseFloat(map.get("SL_CU"));
					float SL_Moi = Float.parseFloat(map.get("SL_MOI"));
					float SL_Thao = Float.parseFloat(map.get("SL_THAO"));
					if (SL_Cu > 0) {
						SLChenhLech = (float) (Math.abs(SL_Moi - SL_Cu) + SL_Thao)/ (float) SL_Cu;
					}
					if(comm.isSavedRow(map.get("TTR_MOI"), map.get("CS_MOI"))){
//						if (Common.cfgInfo.isWarningEnable3()) {
							if(SL_Cu >= 400){
								if (SL_Moi > SL_Cu && SLChenhLech*100 >= 200) {
									map.put("STT", (stt + 1) + "");
									stt++;
									ListViewData.add(map);
									count_report++;
								}
							}
//						}
					}
				} while (c.moveToNext());
			}
			adapter_kxn = new KyNhanAdapter(this.getApplicationContext(),(ArrayList<LinkedHashMap<String, String>>) ListViewData.clone(), R.layout.listview_kynhan);
			lsvGCS.setAdapter(adapter_kxn);
			return count_report;
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được dữ liệu", this);
			return 0;
		}
	}
	
	/** Lấy danh sách công tơ có trạng thái bất thường
	 */
	@SuppressWarnings("unchecked")
	private int setListViewTTBT(ListView lsvGCS,
			ArrayList<LinkedHashMap<String, String>> ListViewData,
			String fileName) {
		try {
			int count_report = 0;
			Cursor c = connection.getAllDataGCS(fileName);
			if (c.moveToFirst()) {
				do {
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("TEN_SO", fileName);
					for (int j = 1; j < ConstantVariables.COLARR.length - 4; j++) {
						map.put(ConstantVariables.COLARR[j], c.getString(j));
					}

					String TTR_MOI = map.get("TTR_MOI");

					if (!TTR_MOI.equals("")) {
//						map.put("STT", (stt + 1) + "");
						map.put("STT", getSTTByIDSQLite(c.getString(50)));
						ListViewData.add(map);
						count_report++;
					}
				} while (c.moveToNext());
			}
			adapter_ttbt = new THTTBTAdapter(this.getApplicationContext(),
					(ArrayList<LinkedHashMap<String, String>>) ListViewData
							.clone(), R.layout.listview_thttbt);
			lsvGCS.setAdapter(adapter_ttbt);
			return count_report;
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được danh sách công tơ trạng thái bất thường", this);
			return 0;
		}
	}
	
	/** Lấy danh sách công tơ có sản lượng bất thường
	 */
	@SuppressWarnings("unchecked")
	private int setListViewSLBT(ListView lsvGCS,
			ArrayList<LinkedHashMap<String, String>> ListViewData,
			String fileName) {
		try {
			int count_report = 0;
			Cursor c = connection.getAllDataGCS(fileName);
			if (c.moveToFirst()) {
				do {
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("TEN_SO", fileName);
					for (int j = 1; j < ConstantVariables.COLARR.length - 4; j++) {
						map.put(ConstantVariables.COLARR[j], c.getString(j));
					}

					float SLChenhLech = 0;
					float SL_Cu = Float.parseFloat(map.get("SL_CU"));
					float SL_Moi = Float.parseFloat(map.get("SL_MOI"));
					float SL_Thao = Float.parseFloat(map.get("SL_THAO"));
					if (SL_Cu > 0) {
						if (SL_Moi + SL_Thao >= SL_Cu) {
							SLChenhLech = (float) (SL_Moi - SL_Cu + SL_Thao) / (float) SL_Cu;
						} else {
							SLChenhLech = (float) (SL_Cu - SL_Moi - SL_Thao) / (float) SL_Cu;
						}
					}
					if(comm.isSavedRow(map.get("TTR_MOI"), map.get("CS_MOI"))){
						if (Common.cfgInfo.isWarningEnable()) {
							if ((SL_Moi > SL_Cu && SLChenhLech >= ((float) Common.cfgInfo.getVuotDinhMuc() / 100))
									|| (SL_Moi < SL_Cu && SLChenhLech >= ((float) Common.cfgInfo.getDuoiDinhMuc() / 100))) {
//								map.put("STT", (stt + 1) + "");
								map.put("STT", getSTTByIDSQLite(c.getString(50)));
								ListViewData.add(map);
								count_report++;
							}
						} else if (Common.cfgInfo.isWarningEnable2()) {
							SLChenhLech = Math.abs(SL_Moi - SL_Cu) + SL_Thao;
							if ((SL_Moi > SL_Cu && SLChenhLech >= (float) Common.cfgInfo.getVuotDinhMuc2())
									|| (SL_Moi < SL_Cu && SLChenhLech >= (float) Common.cfgInfo.getDuoiDinhMuc2())) {
//								map.put("STT", (stt + 1) + "");
								map.put("STT", getSTTByIDSQLite(c.getString(50)));
								ListViewData.add(map);
								count_report++;
							}
						} else if (Common.cfgInfo.isWarningEnable3()) {
							if (SL_Cu >= 400 && SL_Moi > SL_Cu && SLChenhLech*100 >= 200) {
//								map.put("STT", (stt + 1) + "");
								map.put("STT", getSTTByIDSQLite(c.getString(50)));
								ListViewData.add(map);
								count_report++;
							}
						}
					}
				} while (c.moveToNext());
			}
			adapter_slbt = new THCSBTAdapter(this.getApplicationContext(),(ArrayList<LinkedHashMap<String, String>>) ListViewData.clone(), R.layout.listview_thcsbt);
			lsvGCS.setAdapter(adapter_slbt);
			return count_report;
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được dữ liệu 1", this);
			return 0;
		}
	}
	
	private String getSTTByIDSQLite(String ID_SQLITE){
		try{
			for (LinkedHashMap<String, String> map : arrCustomer) {
				if(map.get("ID_SQLITE").equals(ID_SQLITE)){
					return map.get("STT");
				}
			}
			return "0";
		} catch(Exception ex) {
			ex.toString();
			return "0";
		}
	}
	
	/** Hiển thị bản đồ
	 */
	private void showMap(String ID_SQLITE) {
		try{
			row_gcs row = GetDataByID(ID_SQLITE);
			if(row.getX() != 0 && row.getY() != 0){
				Bundle b = new Bundle();
				b.putDouble("LAT", row.getX());
				b.putDouble("LONG", row.getY());
				b.putString("TITLE", row.getTEN_KHANG());
				Intent intent = new Intent(Activity_Camera_MTB.this, Activity_Map.class);
				intent.putExtra("POS", b);
				Activity_Camera_MTB.this.startActivity(intent);
			} else {
				comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Chưa cập nhật vị trí ghi chỉ số", Toast.LENGTH_LONG);
			}
		} catch(Exception ex) {
			comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không mở được bản đồ", Toast.LENGTH_LONG);
		}
	}
	
	/** Hiển thị bản đồ
	 */
	private void showAllMap() {
		try{
			Bundle b = new Bundle();
			b.putString("FILE_NAME", fileName);
			Intent intent = new Intent(Activity_Camera_MTB.this, Activity_Maps.class);
			intent.putExtra("POS", b);
			Activity_Camera_MTB.this.startActivity(intent);
		} catch(Exception ex) {
			comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không mở được bản đồ", Toast.LENGTH_LONG);
		}
	}
	
	/** Chọn máy in
	 * 
	 */
	private void ShowActivityConnPrinter() {
		Intent intent = new Intent(Activity_Camera_MTB.this, ConnectBtDeviceActivity.class);
		startActivity(intent);
	}
	
	/**Tạo dialog mở sổ khác
	 * 
	 */
	private void showDialogChonSo() {
		try {
			final Dialog dialog = new Dialog(this);

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_chonso);
			dialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			ImageButton btnDongSo = (ImageButton) dialog.findViewById(R.id.btnDongSo);
			btnDongSo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			ListView lvChonso = (ListView) dialog.findViewById(R.id.lvChonso);

//			mapSo = new LinkedHashMap<String, String>();
			List<String> list = new ArrayList<String>();
			try{
				Cursor c = connection.getAllDataSoInData();
				if(c.moveToFirst()){
					do{
						if(!maso.contains(c.getString(0))){
							list.add(c.getString(0));
						}
					} while(c.moveToNext());
				}
			} catch(Exception ex){
				comm.msbox("Thông báo", "Không mở được sổ khác", Activity_Camera_MTB.this);
				return;
			}
			dataAdapter = new ArrayAdapter<String>(Activity_Camera_MTB.this.getApplicationContext(), R.layout.listview_simple, list);
			lvChonso.setAdapter(dataAdapter);

			lvChonso.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					try{
						etTimKiem.setText("");
						spTieuChi.setSelection(0);
						if(maso.split(":").length > 1){
							String so = parent.getItemAtPosition(pos).toString();
							StringBuilder arr_so = new StringBuilder();
							for (String s : maso.split(":")) {
								if(s.equals(fileName)){
									arr_so.append(so + ":");
									fileName = so;
								} else {
									arr_so.append(s + ":");
								}
							}
							maso = arr_so.toString();
							setupSimpleList();
							selected_index = getPos();
						} else {
							fileName = parent.getItemAtPosition(pos).toString();
							maso = fileName + ":";
							selected_index = getPos();
						}
						(new AsyncTaskCameraMTB(Activity_Camera_MTB.this, kieu_hthi)).execute(fileName);
						createPhotoFolder(fileName);
						dialog.dismiss();
					} catch(Exception ex) {
						comm.msbox("Thông báo", ex.toString(), Activity_Camera_MTB.this);
						ex.toString();
					}
				}
			});
			dialog.show();
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Không mở được sổ khác", Activity_Camera_MTB.this);
		}
	}
	
	public void showTimePickerDialog() {
		try{
			final Dialog dialog = new Dialog(this);
	
			dialog.setContentView(R.layout.date_time_layout);
			
			final DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker1);
			final TimePicker tp = (TimePicker) dialog.findViewById(R.id.timePicker1);
			Button btOK = (Button) dialog.findViewById(R.id.btOK);
			final TextView tvNgay = (TextView) dialog.findViewById(R.id.tvNgay);
			String BCS = adapter.getItem(selected_index).get("LOAI_BCS");
			if(BCS.equals("BT")){
				dialog.setTitle("Chọn ngày PMAX");
			} else {
				dialog.setTitle("Chọn ngày H1.PMAX");
			}
//			tp.setIs24HourView(true);
//			String datetime = adapter.getItem(pager.getCurrentItem()).get("NGAY_PMAX");
			String datetime = etNgayPmax.getText().toString();
			if(datetime.equals("Ngày thực hiện")){
				datetime = "";
			}
			if(datetime != null && !datetime.equals("")){
				String date = datetime.split(" ")[0].toString();
				String time = datetime.split(" ")[1].toString();
				tvNgay.setText(date.split("/")[0].toString() + "/" + date.split("/")[1].toString() + "/" + date.split("/")[2].toString());
				dp.init(Integer.parseInt(date.split("/")[2].toString()), Integer.parseInt(date.split("/")[0].toString()) - 1, Integer.parseInt(date.split("/")[1].toString()), new OnDateChangedListener(){
					
				     @Override
				     public void onDateChanged(DatePicker view, 
				       int year, int monthOfYear,int dayOfMonth) {
				    	 tvNgay.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
				     }});
				
				tp.setCurrentHour(Integer.parseInt(time.split(":")[0].toString()));
				tp.setCurrentMinute(Integer.parseInt(time.split(":")[1].toString()));
			} else {
				Calendar now = Calendar.getInstance();
				tvNgay.setText((now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DAY_OF_MONTH) + "/" + now.get(Calendar.YEAR));
				dp.init(
					    now.get(Calendar.YEAR), 
					    now.get(Calendar.MONTH), 
					    now.get(Calendar.DAY_OF_MONTH), 
					    new OnDateChangedListener(){
	
					     @Override 
					     public void onDateChanged(DatePicker view, 
					       int year, int monthOfYear,int dayOfMonth) {
					    	 tvNgay.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
					     }});
			
				tp.setCurrentHour(now.get(Calendar.HOUR));
				tp.setCurrentMinute(now.get(Calendar.MINUTE));
			}
			
			btOK.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					etNgayPmax.setText((dp.getMonth() + 1) + "/" + dp.getDayOfMonth() + "/" + dp.getYear() + " " + (tp.getCurrentHour()<12?tp.getCurrentHour():tp.getCurrentHour()-12) + ":" + (tp.getCurrentMinute().toString().length()==1?"0" + tp.getCurrentMinute():tp.getCurrentMinute()) + ":00" + " " + (tp.getCurrentHour()<12?"AM":"PM"));
					etNgayPmax.setTextColor(Color.BLACK);
					dialog.dismiss();
				}
			});
			
			dialog.show();
		} catch(Exception ex) {
			comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không chọn được ngày", 2000);
		}
	}
	
	/** Ghi log khi xóa
	 */
	private void showDialogLogDelete(final String ID_SQLITE){
		try{
			final Dialog dialog = new Dialog(this);

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_ghichu);
			dialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			dialog.setCanceledOnTouchOutside(false);
			TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
			ImageButton ibtDong_gc = (ImageButton) dialog.findViewById(R.id.ibtDong_gc);
			final EditText etGhichu = (EditText) dialog.findViewById(R.id.etGhichu);
			Button btnGhichu = (Button) dialog.findViewById(R.id.btnGhichu);
			Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
			
			tvTitle.setText("Lý do xóa chỉ số");
			ibtDong_gc.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			
			btnGhichu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!etGhichu.getText().toString().trim().equals("")){
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						String MA_QUYEN = "";
						String SERY_CTO = "";
						String NGAY_XOA = "";
						String CS_XOA = "";
						String LY_DO = "";

						MA_QUYEN = fileName;
						SERY_CTO = connection.getNoCtoById(ID_SQLITE);
						CS_XOA = connection.getCsMoiById(ID_SQLITE);
						NGAY_XOA = sdf.format(new Date());
						LY_DO = etGhichu.getText().toString().trim();
						if(!MA_QUYEN.equals("") && !SERY_CTO.equals("") && ! NGAY_XOA.equals("")){
							if(connection.insertLogXoa(MA_QUYEN, SERY_CTO, CS_XOA, NGAY_XOA, LY_DO) != -1){
								clearCSM(ID_SQLITE);
							} else {
								comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không xóa được chỉ số do không thể lưu vết khách hàng đã xóa", Toast.LENGTH_LONG);
							}
						} else {
							comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không lấy được đủ thông tin khách hàng", Toast.LENGTH_LONG);
						}
						dialog.dismiss();
					} else {
						comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Bạn phải nhập lý do vì sao xóa", Toast.LENGTH_LONG);
					}
				}
			});
			
			btnHuy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					etGhichu.setText("");
				}
			});

			dialog.show();
		} catch(Exception ex) {
			ex.toString();
		}
	}
	
	/** Tạo dialog tổng hợp công tơ đã xóa
	 * 
	 * 
	 */
	private void CreateDialogLogDelete(final String fileName) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_log_delete);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		final ArrayList<LinkedHashMap<String, String>> ListViewData = new ArrayList<LinkedHashMap<String, String>>();

		try {
			int count_report = 0;
			ListView lsvGCS = (ListView) dialog.findViewById(R.id.lsvLog);
			count_report = setListViewLogDelete(lsvGCS, ListViewData, fileName);

			ImageButton btnDong = (ImageButton) dialog.findViewById(R.id.btnDong);
			btnDong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			lsvGCS.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
//					try {
//						ShowDialogChiTiet(GetDataByID(adapter_slbt.getItem(pos).get("ID_SQLITE")));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
				}
			});
			if (count_report == 0) {
				comm.msbox("Thông báo", "Không có công tơ nào đã xóa chỉ số", Activity_Camera_MTB.this);
			} else {
				dialog.show();
			}
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được danh sách sản lượng bất thường", Activity_Camera_MTB.this);
		}
	}
	
	// ------------------- CÁC HÀM XỬ LÝ CHỨC NĂNG GHI -----------------
	/**
	 * Công thức tính sản lượng
	 */
	private Float TinhSanLuong(Float CS_Cu, Float CS_Moi, Float HSN, String TT_Moi) {
		Float SL = 0F;
		// nếu là công tơ qua vòng
		if (CS_Moi < CS_Cu || (TT_Moi != null && TT_Moi.equals("Q"))) {
			SL = Float.parseFloat(((Math.pow(10, (CS_Cu.intValue() + "").length()) - CS_Cu + CS_Moi) * HSN) + "");
			SL = Float.parseFloat(String.format(Locale.ENGLISH, "%.3f", SL));
		} else if (CS_Moi > CS_Cu) {
			float hs = Float.parseFloat(String.format(Locale.ENGLISH, "%.3f", CS_Moi - CS_Cu));
			SL = hs * HSN;
			SL = Float.parseFloat(String.format(Locale.ENGLISH, "%.3f", SL));
		} else {
			SL = 0F;
		}

		return SL;
	}
	
	/**
	 * Cảnh báo chênh lệch sản lượng
	 */
	private void CanhBaoChenhLechSL(String ID_SQLITE, Float SL_CU, Float SL_THAO, Float SL_MOI, Float CS_MOI, String tinh_trang_moi, String LOAI_BCS, String SO_CTO) {
		try{
			float SLChenhLech = 0;
			if (SL_CU > 0) {
				if(SL_MOI + SL_THAO > SL_CU){
					SLChenhLech = (float) (SL_MOI - SL_CU + SL_THAO) / (float) SL_CU;
				} else {
					SLChenhLech = (float) (SL_CU - SL_MOI - SL_THAO) / (float) SL_CU;
				}
			}
			
			if (Common.cfgInfo.isWarningEnable3()) {
				if(SL_CU >= 400){
					if (SL_MOI + SL_THAO > SL_CU && SLChenhLech*100 >= 200) {
						CreateDialogCanhBaoXacNhan(Color.parseColor("#FF0000"), "Sản lượng vượt quá " + SLChenhLech*100
								+ "%.\nYêu cầu ký xác nhận với khách hàng" + " \nBạn có muốn lưu?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else {
						CanhBaoChenhLechTongSLCto3Pha(0, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					}
				} else {
					CanhBaoChenhLechTongSLCto3Pha(0, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
				}
			} else if (Common.cfgInfo.isWarningEnable()) {// Cảnh báo theo %
				if (SL_MOI + SL_THAO > SL_CU
						&& SLChenhLech*100 >= ((float) Common.cfgInfo.getVuotDinhMuc())) {
					if(SLChenhLech*100 < 50){
						CreateDialogCanhBao(Color.parseColor("#005789"), "Sản lượng mới vượt quá "
								+ Common.round(SLChenhLech*100, 2) + "% so với mức " + Common.cfgInfo.getVuotDinhMuc() 
								+ "% đã đặt trong cấu hình tương ứng với " + (SL_MOI - SL_CU + SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(SLChenhLech*100 >= 50 && SLChenhLech*100 < 100){
						CreateDialogCanhBao(Color.parseColor("#FFCC00"), "Sản lượng mới vượt quá "
								+ Common.round(SLChenhLech*100, 2) + "% so với mức " + Common.cfgInfo.getVuotDinhMuc() 
								+ "% đã đặt trong cấu hình tương ứng với " + (SL_MOI - SL_CU + SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(SLChenhLech*100 >= 100){
						CreateDialogCanhBao(Color.parseColor("#FF0000"), "Sản lượng mới vượt quá "
								+ Common.round(SLChenhLech*100, 2) + "% so với mức " + Common.cfgInfo.getVuotDinhMuc() 
								+ "% đã đặt trong cấu hình tương ứng với " + (SL_MOI - SL_CU + SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					}
				} else if (SL_MOI + SL_THAO < SL_CU
						&& SLChenhLech*100 >= ((float) Common.cfgInfo.getDuoiDinhMuc())) {
					if(SLChenhLech*100 < 50){
						CreateDialogCanhBao(Color.parseColor("#005789"), "Sản lượng mới dưới ngưỡng định mức "
								+ Common.round(SLChenhLech*100, 2) + "% so với mức " + Common.cfgInfo.getDuoiDinhMuc() 
								+ "% đã đặt trong cấu hình tương ứng với " + (SL_CU - SL_MOI - SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(SLChenhLech*100 >= 50 && SLChenhLech*100 < 100){
						CreateDialogCanhBao(Color.parseColor("#FFCC00"), "Sản lượng mới dưới ngưỡng định mức "
								+ Common.round(SLChenhLech*100, 2) + "% so với mức " + Common.cfgInfo.getDuoiDinhMuc() 
								+ "% đã đặt trong cấu hình tương ứng với " + (SL_CU - SL_MOI - SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(SLChenhLech*100 >= 100){
						CreateDialogCanhBao(Color.parseColor("#FF0000"), "Sản lượng mới dưới ngưỡng định mức "
								+ Common.round(SLChenhLech*100, 2) + "% so với mức " + Common.cfgInfo.getDuoiDinhMuc() 
								+ "% đã đặt trong cấu hình tương ứng với " + (SL_CU - SL_MOI - SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					}
				} else {
					CanhBaoChenhLechTongSLCto3Pha(0, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
				}
			} else if (Common.cfgInfo.isWarningEnable2()) {
				if (SL_MOI + SL_THAO >= SL_CU) {
					SLChenhLech = SL_MOI - SL_CU + SL_THAO;
				} else {
					SLChenhLech = SL_CU - SL_MOI - SL_THAO;
				}
				String chenhLechTren = "" + Math.abs((SLChenhLech - (float) Common.cfgInfo.getVuotDinhMuc2()));
				String chenhLechDuoi = "" + Math.abs((SLChenhLech - (float) Common.cfgInfo.getDuoiDinhMuc2()));
				
				if (SL_MOI + SL_THAO > SL_CU
						&& SLChenhLech > (float) Common.cfgInfo.getVuotDinhMuc2()) {
					if(Float.parseFloat(chenhLechTren) < 100){
						CreateDialogCanhBao(Color.parseColor("#005789"), "Sản lượng mới vượt quá định mức cho phép. "
								+ chenhLechTren + " kw\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(Float.parseFloat(chenhLechTren) >= 100 && Float.parseFloat(chenhLechTren) < 150){
						CreateDialogCanhBao(Color.parseColor("#FFCC00"), "Sản lượng mới vượt quá định mức cho phép. "
								+ chenhLechTren + " kw\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(Float.parseFloat(chenhLechTren) >= 150){
						CreateDialogCanhBao(Color.parseColor("#FF0000"), "Sản lượng mới vượt quá định mức cho phép. "
								+ chenhLechTren + " kw\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					}
				} else if (SL_MOI + SL_THAO < SL_CU && SLChenhLech > (float) Common.cfgInfo.getDuoiDinhMuc2()) {
					if(Float.parseFloat(chenhLechDuoi) < 100){
						CreateDialogCanhBao(Color.parseColor("#005789"), "Sản lượng mới dưới định mức cho phép. " 
								+ chenhLechDuoi + " kw\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(Float.parseFloat(chenhLechDuoi) >= 100 && Float.parseFloat(chenhLechDuoi) < 150){
						CreateDialogCanhBao(Color.parseColor("#FFCC00"), "Sản lượng mới dưới định mức cho phép. " 
								+ chenhLechDuoi + " kw\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(Float.parseFloat(chenhLechDuoi) >= 150){
						CreateDialogCanhBao(Color.parseColor("#FF0000"), "Sản lượng mới dưới định mức cho phép. " 
								+ chenhLechDuoi + " kw\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					}
				} else {
					CanhBaoChenhLechTongSLCto3Pha(0, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
				}
			} else {
				if (SL_MOI + SL_THAO > SL_CU
						&& SLChenhLech*100 >= 30) {
					if(SLChenhLech*100 < 50){
						CreateDialogCanhBao(Color.parseColor("#005789"), "Sản lượng mới vượt quá " + SLChenhLech*100 + "% so với mức " 
								+ "30% mặc định tương ứng với " + (Math.abs(SL_MOI - SL_CU) + SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(SLChenhLech*100 >= 50 && SLChenhLech*100 < 100){
						CreateDialogCanhBao(Color.parseColor("#FFCC00"), "Sản lượng mới vượt quá " + SLChenhLech*100 + "% so với mức " 
								+ "30% mặc định tương ứng với " + (Math.abs(SL_MOI - SL_CU) + SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(SLChenhLech*100 >= 100){
						CreateDialogCanhBao(Color.parseColor("#FF0000"), "Sản lượng mới vượt quá " + SLChenhLech*100 + "% so với mức "
								+ "30% mặc định tương ứng với " + (Math.abs(SL_MOI - SL_CU) + SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					}
				} else if (SL_MOI + SL_THAO < SL_CU
						&& SLChenhLech*100 >= ((float) Common.cfgInfo.getDuoiDinhMuc())) {
					if(SLChenhLech*100 < 50){
						CreateDialogCanhBao(Color.parseColor("#005789"), "Sản lượng mới dưới ngưỡng " + SLChenhLech*100 + "% so với mức " 
								+ "30% mặc định tương ứng với " + (SL_CU - SL_MOI - SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(SLChenhLech*100 >= 50 && SLChenhLech*100 < 100){
						CreateDialogCanhBao(Color.parseColor("#FFCC00"), "Sản lượng mới dưới ngưỡng " + SLChenhLech*100 + "% so với mức " 
								+ "30% mặc định tương ứng với " + (SL_CU - SL_MOI - SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					} else if(SLChenhLech*100 >= 100){
						CreateDialogCanhBao(Color.parseColor("#FF0000"), "Sản lượng mới dưới ngưỡng " + SLChenhLech*100 + "% so với mức " 
								+ "30% mặc định tương ứng với " + (SL_CU - SL_MOI - SL_THAO) + "kw/h\nBạn có muốn lưu ?", ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
					}
				} else {
					CanhBaoChenhLechTongSLCto3Pha(0, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS, SO_CTO);
				}
			}
		} catch(Exception ex) {
			comm.msbox("", ex.toString(), this);
		}
	}
	
	/**Kiểm tra file cấu hình đã tồn tại chưa
	 * 
	 */
//	private void CheckFileConfigExist() {
//		try {
//			String filePath = Environment.getExternalStorageDirectory()
//					+ Common.programPath + Common.cfgFileName;
//			// create a File object for the parent directory
//			File programDirectory = new File(
//					Environment.getExternalStorageDirectory()
//							+ Common.programPath);
//			if (!programDirectory.exists()) {
//				programDirectory.mkdirs(); // tạo thư mục chứa dữ liệu
//			}
//			
//			//kiểm tra folder DB đã tồn tại hay chưa
//			File dbDir = new File(Environment.getExternalStorageDirectory()
//					+ Common.programPath + "/DB");
//			if (!dbDir.exists()) {
//				dbDir.mkdirs();
//			}
//			
//			//kiểm tra folder upload đã tồn tại hay chưa
//			File uploadDir = new File(Environment.getExternalStorageDirectory()
//					+ Common.programPath + "/Upload");
//			if (!uploadDir.exists()) {
//				uploadDir.mkdirs();
//			}
//
//			//kiểm tra folder download đã tồn tại hay chưa
//			File downloadDir = new File(Environment.getExternalStorageDirectory()
//							+ Common.programPath + "/Download");
//			if (!downloadDir.exists()) {
//				downloadDir.mkdirs();
//			}
//			
//			//kiểm tra folder backup đã có chưa, nếu chưa thì tạo mới
//			File backupDir = new File(
//					Environment.getExternalStorageDirectory()
//							+ Common.programPath + "/Backup");
//			if (!backupDir.exists()) {
//				backupDir.mkdirs();
//			}
//			
//			//kiểm tra folder lộ trình đã có chưa, nếu chưa thì tạo mới
//			File routeDir = new File(
//					Environment.getExternalStorageDirectory()
//							+ Common.programPath + "/LoTrinh");
//			if (!routeDir.exists()) {
//				routeDir.mkdirs();
//			}
//			
//			//kiểm tra folder lộ trình đã có chưa, nếu chưa thì tạo mới
//			File tempDir = new File(
//					Environment.getExternalStorageDirectory()
//							+ Common.programPath + "/Temp");
//			if (!tempDir.exists()) {
//				tempDir.mkdirs();
//			}
//
//			File cfgFile = new File(filePath);
//			if (cfgFile.exists()) {
//				Common.cfgInfo = comm.GetFileConfig();
//				if(Common.cfgInfo == null){
//					Common.cfgInfo = new ConfigInfo();
//				}
//				// Lấy lại thông tin file cấu hình
//				createFileConfig(Common.cfgInfo);
//			} else {
//				// tao file cau hinh mac dinh
//				Common.cfgInfo = new ConfigInfo();
//				comm.CreateFileConfig(Common.cfgInfo, cfgFile, "");
//
//			}
////			if(!Common.cfgInfo.getDinhDang()){
////	        	ivCropImage.setVisibility(View.GONE);
////	        } else {
////	        	if(Common.state_show_camera == 1){
////	        		ivCropImage.setVisibility(View.VISIBLE);
////	        	}
////	        }
//		} catch (Exception ex) {
//			comm.msbox("Thông báo", "File cấu hình không đúng", Activity_Camera.this);
//		}
//	}
	
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
	/**
	 * Cảnh báo chênh lệch tổng sản lượng công tơ 3 pha
	 * 
	 */
	private void CanhBaoChenhLechTongSLCto3Pha(final int tinh_trang_qua_sl, final String ID_SQLITE, final float CS_MOI, final float SL_MOI, final String tinh_trang_moi, final String LOAI_BCS, String SO_CTO) {
		try{
			int sai_so_cto_tong = 2;
			int dem = 0;
			if(LOAI_BCS.equals("KT")){
				SaveToSelectedRow(tinh_trang_qua_sl, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS);
			} else {
				float sl_sg = 0f;
				float sl_tong = 0f;
				Cursor c = connection.getDataSameSoCto(SO_CTO);
				if(c.moveToFirst()){
					do{
						if(c.getString(9).equals("SG")){
							if(LOAI_BCS.equals("SG")){
								sl_sg = SL_MOI;
							} else {
								sl_sg = Float.parseFloat(c.getString(25));
							}
							if(Float.parseFloat(c.getString(25)) != 0){
								dem++;
							}
						} else if(c.getString(9).equals("BT")){
							if(LOAI_BCS.equals("BT")){
								sl_tong += SL_MOI;
							} else {
								sl_tong += Float.parseFloat(c.getString(25));
							}
							if(Float.parseFloat(c.getString(25)) != 0){
								dem++;
							}
						} else if(c.getString(9).equals("CD")){
							if(LOAI_BCS.equals("CD")){
								sl_tong += SL_MOI;
							} else {
								sl_tong += Float.parseFloat(c.getString(25));
							}
							if(Float.parseFloat(c.getString(25)) != 0){
								dem++;
							}
						} else if(c.getString(9).equals("TD")){
							if(LOAI_BCS.equals("TD")){
								sl_tong += SL_MOI;
							} else {
								sl_tong += Float.parseFloat(c.getString(25));
							}
							if(Float.parseFloat(c.getString(25)) != 0){
								dem++;
							}
						}
					} while(c.moveToNext());
					if(dem < 3){
						SaveToSelectedRow(tinh_trang_qua_sl, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS);
					} else {
						if(dem == 4){
							if(Math.abs(sl_sg - sl_tong) > sai_so_cto_tong){
								new AlertDialog.Builder(this)
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle("Thông báo")
								.setMessage("Tổng sản lượng công tơ 3 pha chênh lệch nhiều. \nBạn có muốn lưu ?")
								.setPositiveButton("Đồng ý",
										new DialogInterface.OnClickListener() {
		
											@Override
											public void onClick(DialogInterface dialog,
													int which) {
												SaveToSelectedRow(tinh_trang_qua_sl, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS);
											}
		
										}).setNegativeButton("Không", null).show()
								.getWindow().setGravity(Gravity.BOTTOM);
							} else {
								SaveToSelectedRow(tinh_trang_qua_sl, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS);
							}
						} else {
							if(connection.getSlmoiById(ID_SQLITE) == 0){
								if(Math.abs(sl_sg - sl_tong) > sai_so_cto_tong){
									new AlertDialog.Builder(this)
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setTitle("Thông báo")
									.setMessage("Tổng sản lượng công tơ 3 pha chênh lệch nhiều. \nBạn có muốn lưu ?")
									.setPositiveButton("Đồng ý",
											new DialogInterface.OnClickListener() {
			
												@Override
												public void onClick(DialogInterface dialog,
														int which) {
													SaveToSelectedRow(tinh_trang_qua_sl, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS);
												}
			
											}).setNegativeButton("Không", null).show()
									.getWindow().setGravity(Gravity.BOTTOM);
								} else {
									SaveToSelectedRow(tinh_trang_qua_sl, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS);
								}
							} else {
								SaveToSelectedRow(tinh_trang_qua_sl, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS);
							}
						}
					}
				} else {
					if(LOAI_BCS.equals("VC"))
						SaveToSelectedRow(tinh_trang_qua_sl, ID_SQLITE, CS_MOI, SL_MOI, tinh_trang_moi, LOAI_BCS);
				}
			}
		} catch(Exception ex) {
			comm.ShowToast(this.getApplicationContext(), "Lỗi so sánh sản lượng công tơ 3 pha", Toast.LENGTH_LONG);
		}
	}

	/**
	 * Ghi dữ liệu của dòng được chọn
	 */
	@SuppressLint("SimpleDateFormat")
	Bitmap bm = null;
	String PMAX = "";
	String NGAY_PMAX = "";
	private void SaveToSelectedRow(final int tinh_trang_qua_sl, final String ID_SQLITE, float cs_moi, float sl_moi, String tinh_trang_moi, final String LOAI_BCS) {
		try{
			// cập nhật vị trí GPS
			double latitude = 0;
			double longitude = 0;
			
			if (Common.isNetworkOnline(this.getApplicationContext())) {
				if (gps.canGetLocation()) {
					latitude = gps.getLatitude();
					longitude = gps.getLongitude();
				}
			} else {
				Location gpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
				
				if (gpsLocation != null) {
					latitude = gpsLocation.getLatitude();
					longitude = gpsLocation.getLongitude();
				} else {
					comm.ShowToast(getApplicationContext(), "Không lấy được vị trí", Toast.LENGTH_SHORT);
				}
			}
			
			// cập nhật thời gian lúc ghi
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String currentDateandTime = sdf.format(new Date());
			
			// lưu dữ liệu
			final String CS_MOI = Common.ConvertFloatToString(cs_moi, ConstantVariables.FORMAT_DECIMAL_PATTERN);
			final String TTR_MOI = tinh_trang_moi;
			final String SL_MOI = Common.ConvertFloatToString(sl_moi, ConstantVariables.FORMAT_DECIMAL_PATTERN);
			final String THOI_GIAN = currentDateandTime;
			final String X = latitude + "";
			final String Y = longitude + "";
			final String TTHAI_DBO = "1";
			if(!SL_MOI.equals("") && Float.parseFloat(SL_MOI) >= 0){
				if(etPmax.getText().toString().equals("")){
					PMAX = "";
				} else {
					PMAX = Common.ConvertFloatToString(Float.parseFloat(etPmax.getText().toString()), ConstantVariables.FORMAT_DECIMAL_PATTERN);
				}
				NGAY_PMAX = etNgayPmax.getText().toString();
				if(NGAY_PMAX.equals("Ngày thực hiện")){
					NGAY_PMAX = "";
				}
				if((LOAI_BCS.equals("BT") || LOAI_BCS.equals("CD")) && (PMAX.equals("") || NGAY_PMAX.equals(""))){
					comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Bạn cần nhập PMAX và ngày PMAX nếu là khách hàng cơ quan", 2000);
					PMAX = "";
					NGAY_PMAX = "";
				}
				
//				new Thread(){
//
//					@Override
//					public void run() {
//						runOnUiThread(new Runnable() {
//							
//							@Override
//							public void run() {
								Cursor c = connection.getDataCS(ID_SQLITE);
								if(c.moveToFirst()){
									if(!comm.isSavedRow(c.getString(1), c.getString(0))){
										tvDaGhi.setText((Integer.parseInt(tvDaGhi.getText().toString().split("/")[0]) + 1) + "/" + tvDaGhi.getText().toString().split("/")[1]);
									}
								}
								
								bm = createBitMap(Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + TEN_FILE + "/" + fileName + "_" + adapter.getItem(selected_index).get("MA_CTO") + "_" + adapter.getItem(selected_index).get("NAM") + "_" + adapter.getItem(selected_index).get("THANG") + "_" + adapter.getItem(selected_index).get("KY") + "_" + adapter.getItem(selected_index).get("MA_DDO") + "_" + adapter.getItem(selected_index).get("LOAI_BCS") + ".jpg");
								if(bm != null){
									bm = drawCSMoiToBitmap(Activity_Camera_MTB.this, bm, CS_MOI);
									if(saveImageToFile(bm)){
										comm.scanFile(Activity_Camera_MTB.this.getApplicationContext(), new String[] {Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + fileName + "_" + adapter.getItem(selected_index).get("MA_CTO") + "_" + adapter.getItem(selected_index).get("LOAI_BCS") + "_" + adapter.getItem(selected_index).get("NAM") + "-" + adapter.getItem(selected_index).get("THANG") + "-" + adapter.getItem(selected_index).get("KY") + ".jpg"});
									}
								}
								
								if(LOAI_BCS.equals("CD")){
									getPmax(PMAX, NGAY_PMAX, "" + (Float.parseFloat(ID_SQLITE) - 1));
									PMAX = "";
									NGAY_PMAX = "";
								}
								String STR_CHECK_DSOAT = "";
								if(!LOAI_BCS.equals("KT")){
									if (connection.updateDataGCS2(ID_SQLITE, CS_MOI, TTR_MOI, SL_MOI, THOI_GIAN, X, Y, TTHAI_DBO, "" + tinh_trang_qua_sl, PMAX, NGAY_PMAX, STR_CHECK_DSOAT) != -1) {
										reLoadData(selected_index, CS_MOI, SL_MOI, TTR_MOI, PMAX, NGAY_PMAX);
										if(LOAI_BCS.equals("CD")){
											Cursor c_pmax = connection.getPmax(adapter.getItem(selected_index - 1).get("ID_SQLITE"));
											if(c_pmax.moveToFirst()){
												String BT_PMAX = c_pmax.getString(0);
												String BT_NGAY_PMAX = c_pmax.getString(1);
												adapter.getItem(selected_index - 1).put("PMAX", BT_PMAX);
												adapter.getItem(selected_index - 1).put("NGAY_PMAX", BT_NGAY_PMAX);
											}
										}
										if(selected_index < adapter.getCount() - 1){
											selected_index++;
										}
										lvCustomer.setSelection(selected_index);
										// Lưu vị trí khách hàng đang ghi
										try {
											if (etTimKiem.getText().toString().equals("")) {
												connection.updateDataIndex(fileName, selected_index);
											}
										} catch (Exception ex) {
											comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không lưu được vị trí đang ghi", Toast.LENGTH_SHORT);
										}
										setDataOnEditText(selected_index, 1);
										etCSMoi.requestFocus();
										etCSMoi.selectAll();
										showHidePmax(selected_index);
									} else {
										comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Ghi dữ liệu thất bại", Toast.LENGTH_SHORT);
									}
								} else {
									if (connection.updateDataGCS(ID_SQLITE, CS_MOI, TTR_MOI, SL_MOI, THOI_GIAN, X, Y, TTHAI_DBO, "" + tinh_trang_qua_sl, STR_CHECK_DSOAT) != -1) {
										reLoadData(selected_index, CS_MOI, SL_MOI, TTR_MOI, PMAX, NGAY_PMAX);
										if(selected_index < adapter.getCount() - 1){
											selected_index++;
										}
										lvCustomer.setSelection(selected_index);
										// Lưu vị trí khách hàng đang ghi
										try {
											if (etTimKiem.getText().toString().equals("")) {
												connection.updateDataIndex(fileName, selected_index);
											}
										} catch (Exception ex) {
											comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không lưu được vị trí đang ghi", Toast.LENGTH_SHORT);
										}
										setDataOnEditText(selected_index, 2);
										etCSMoi.requestFocus();
										etCSMoi.selectAll();
										showHidePmax(selected_index);
									} else {
										comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Ghi dữ liệu thất bại", Toast.LENGTH_SHORT);
									}
								}
//								if(Common.state_show_camera == 1){
									setImage();
//								}
//							}
//						});
//					}
//					
//				}.start();
				Common.pos_change_camera = selected_index;
			} else {
				comm.msbox("Error", "Có lỗi khi tính sản lượng\nVui lòng chọn lại khách hàng và ghi lại chỉ số", Activity_Camera_MTB.this);
			}
		} catch(Exception ex) {
			comm.ShowToast(this.getApplicationContext(), "Lỗi ghi dữ liệu", Toast.LENGTH_SHORT);
		}
	}
	
	public Bitmap createBitMap(String path){
	    File file = new File(path);
	    Bitmap bitmap = null;
	    if(file.exists())
	    	bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
	    return bitmap;
	}
	
	/** Lưu khách hàng đang được chọn
	 * 
	 */
	@SuppressLint("NewApi") private void saveCustomerIsSelected(){
		try{
			// Lấy trạng thái công tơ
			String trang_thai_cong_to = ConstantVariables.ARR_CONTENT_TRANGTHAI[spTrangThai.getSelectedItemPosition()];
			if (etCSMoi.getText().toString().trim().isEmpty() && (spTrangThai.getSelectedItemPosition() == 0
				|| trang_thai_cong_to.equals("Q") || trang_thai_cong_to.equals("G") || trang_thai_cong_to.equals("T")
				|| trang_thai_cong_to.equals("C") || trang_thai_cong_to.equals("K") || trang_thai_cong_to.equals("H"))) {
				Common.ShowToast(this, "Chưa nhập chỉ số", Toast.LENGTH_SHORT, Common.size(Activity_Camera_MTB.this));
				etCSMoi.requestFocus();
			} else {
				// Lấy thông tin
				String ID_SQLITE = adapter.getItem(selected_index).get("ID_SQLITE");
				EntityGCS eGCS = connection.getDataByMAGC(ID_SQLITE);
				float CS_CU = Float.parseFloat(eGCS.getCS_CU());
				float CS_MOI = 0f;
				float SL_CU = Float.parseFloat(eGCS.getSL_CU());
				float SL_MOI = 0f;
				float SL_THAO = Float.parseFloat(eGCS.getSL_THAO());
				float HSN = Float.parseFloat(eGCS.getHSN());
				String LOAI_BCS = eGCS.getLOAI_BCS();
				String SO_CTO = eGCS.getSERY_CTO();
				
				if(trang_thai_cong_to.equals("U")){
					CS_MOI = CS_CU;
					SL_MOI = 0f;
				} else if(trang_thai_cong_to.equals("Q")){
					CS_MOI = Float.parseFloat(etCSMoi.getText().toString());
					SL_MOI = TinhSanLuong(CS_CU, CS_MOI, HSN, trang_thai_cong_to);
				} else if(trang_thai_cong_to.equals("C")){
					CS_MOI = Float.parseFloat(etCSMoi.getText().toString());
					if(CS_MOI < CS_CU){
						comm.ShowToast(this.getApplicationContext(), "Chỉ số mới nhỏ hơn chỉ số cũ\nYêu cầu nhập lại chỉ số (C)", Toast.LENGTH_LONG);
						return;
					} else {
						SL_MOI = TinhSanLuong(CS_CU, CS_MOI, HSN, trang_thai_cong_to);
					}
				} else if(trang_thai_cong_to.equals("K")){
					CS_MOI = Float.parseFloat(etCSMoi.getText().toString());
					if(CS_MOI < CS_CU){
						comm.ShowToast(this.getApplicationContext(), "Chỉ số mới nhỏ hơn chỉ số cũ\nYêu cầu nhập lại chỉ số (K)", Toast.LENGTH_LONG);
						return;
					} else {
						SL_MOI = TinhSanLuong(CS_CU, CS_MOI, HSN, trang_thai_cong_to);
					}
				} else if(trang_thai_cong_to.equals("V")){
					CS_MOI = CS_CU;
					SL_MOI = 0f;
				} else if(trang_thai_cong_to.equals("M")){
					CS_MOI = CS_CU;
					SL_MOI = 0f;
				} else if(trang_thai_cong_to.equals("T")){
					CS_MOI = Float.parseFloat(etCSMoi.getText().toString());
					if(CS_MOI < CS_CU){
						comm.ShowToast(this.getApplicationContext(), "Chỉ số mới nhỏ hơn chỉ số cũ\nYêu cầu nhập lại chỉ số (T)", Toast.LENGTH_LONG);
						return;
					} else {
						SL_MOI = TinhSanLuong(CS_CU, CS_MOI, HSN, trang_thai_cong_to);
					}
				} else if(trang_thai_cong_to.equals("L")){
					CS_MOI = CS_CU;
					SL_MOI = 0f;
				} else if(trang_thai_cong_to.equals("X")){
					CS_MOI = CS_CU;
					SL_MOI = 0f;
				} else if(trang_thai_cong_to.equals("H")){
					CS_MOI = Float.parseFloat(etCSMoi.getText().toString());
					if(CS_MOI < CS_CU){
						comm.ShowToast(this.getApplicationContext(), "Chỉ số mới nhỏ hơn chỉ số cũ\nYêu cầu nhập lại chỉ số (H)", Toast.LENGTH_LONG);
						return;
					} else {
						SL_MOI = TinhSanLuong(CS_CU, CS_MOI, HSN, trang_thai_cong_to);
					}
				} else if(trang_thai_cong_to.equals("D")){
					CS_MOI = CS_CU;
					SL_MOI = 0f;
				} else if(trang_thai_cong_to.equals("G")){
					CS_MOI = Float.parseFloat(etCSMoi.getText().toString());
					if(CS_MOI < CS_CU){
						comm.ShowToast(this.getApplicationContext(), "Chỉ số mới nhỏ hơn chỉ số cũ\nYêu cầu nhập lại chỉ số (G)", Toast.LENGTH_LONG);
						return;
					} else {
						SL_MOI = TinhSanLuong(CS_CU, CS_MOI, HSN, trang_thai_cong_to);
					}
				} else if(trang_thai_cong_to.equals("")){
					CS_MOI = Float.parseFloat(etCSMoi.getText().toString());
					if(CS_MOI >= CS_CU){
						SL_MOI = TinhSanLuong(CS_CU, CS_MOI, HSN, trang_thai_cong_to);
					} else {
						comm.ShowToast(this.getApplicationContext(), "Chỉ số mới nhỏ hơn chỉ số cũ\nBạn phải chọn trạng thái mới có thể ghi (0)", Toast.LENGTH_LONG);
						return;
					}
				}
				if(trang_thai_cong_to.equals("U") || trang_thai_cong_to.equals("V")
						 || trang_thai_cong_to.equals("M") || trang_thai_cong_to.equals("L")
						 || trang_thai_cong_to.equals("X") || trang_thai_cong_to.equals("D")){
					SaveToSelectedRow(0, ID_SQLITE, CS_MOI, SL_MOI, trang_thai_cong_to, LOAI_BCS);
				} else if(trang_thai_cong_to.equals("T") || trang_thai_cong_to.equals("G")
						|| trang_thai_cong_to.equals("C") || trang_thai_cong_to.equals("K")
						|| trang_thai_cong_to.equals("H")){
					if(CS_MOI >= CS_CU){
						CanhBaoChenhLechSL(ID_SQLITE, SL_CU, SL_THAO, SL_MOI, CS_MOI, trang_thai_cong_to, LOAI_BCS, SO_CTO);
					}
				} else if(trang_thai_cong_to.equals("Q")){
					final String ID_SQLITE1 = ID_SQLITE;
					final Float SL_CU1 = SL_CU;
					final Float SL_THAO1 = SL_THAO;
					final Float SL_MOI1 = SL_MOI;
					final Float CS_MOI1 = CS_MOI;
					final String trang_thai_cong_to1 = trang_thai_cong_to;
					final String LOAI_BCS1 = LOAI_BCS;
					final String SO_CTO1 = SO_CTO;
					if(CS_MOI < CS_CU){
						CanhBaoChenhLechSL(ID_SQLITE, SL_CU, SL_THAO, SL_MOI, CS_MOI, trang_thai_cong_to, LOAI_BCS, SO_CTO);
					} else {
						new AlertDialog.Builder(Activity_Camera_MTB.this)
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle("Thông báo").setMessage("Công tơ qua vòng. \nBạn có muốn lưu?")
								.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										CanhBaoChenhLechSL(ID_SQLITE1, SL_CU1, SL_THAO1, SL_MOI1, CS_MOI1, trang_thai_cong_to1, LOAI_BCS1, SO_CTO1);
									}
								}).setNegativeButton("Không", null)
								.show().getWindow().setGravity(Gravity.BOTTOM);
					}
				} else {
					if(CS_MOI >= CS_CU){
						CanhBaoChenhLechSL(ID_SQLITE, SL_CU, SL_THAO, SL_MOI, CS_MOI, trang_thai_cong_to, LOAI_BCS, SO_CTO);
					} else {
						comm.ShowToast(this.getApplicationContext(), "Chỉ số mới nhỏ hơn chỉ số cũ\nBạn phải chọn trạng thái mới có thể ghi (1)", Toast.LENGTH_LONG);
						return;
					}
				}
			}
		} catch(Exception ex) {
			comm.ShowToast(this.getApplicationContext(), "Có lỗi xảy ra khi ghi chỉ số", Toast.LENGTH_LONG);
		}
	}
	
	private void getPmax(String H1_PMAX, String NGAY_H1_PMAX, String ID_SQLITE){
		try{
			EntityGCS entity = connection.getDataByMAGC(ID_SQLITE);
			String PMAX_TT = entity.getPMAX();
			String NGAY_PMAX_TT = entity.getNGAY_PMAX();
			if(!NGAY_PMAX_TT.equals("")){
				int ngay = Integer.parseInt(NGAY_PMAX_TT.split(" ")[0].toString().split("/")[1].toString());
				int thang = Integer.parseInt(NGAY_PMAX_TT.split(" ")[0].toString().split("/")[0].toString());
				int nam = Integer.parseInt(NGAY_PMAX_TT.split(" ")[0].toString().split("/")[2].toString());
				
				if(NGAY_H1_PMAX.equals("")){
					// TH1 - Từ ngày 10/12/2014 đên hết tháng 12/2014
					if(nam == 2014){
						if((thang == 12 && ngay < 10) || thang < 12){
							connection.updateDataGCS(ID_SQLITE, "", "");
						}
					}
				} else {
					int ngay_h1 = Integer.parseInt(NGAY_H1_PMAX.split(" ")[0].toString().split("/")[1].toString());
					int thang_h1 = Integer.parseInt(NGAY_H1_PMAX.split(" ")[0].toString().split("/")[0].toString());
					int nam_h1 = Integer.parseInt(NGAY_H1_PMAX.split(" ")[0].toString().split("/")[2].toString());
					// TH1 - Từ ngày 10/12/2014 đên hết tháng 12/2014
					if(nam == 2014){
						if((thang == 12 && ngay < 10) || thang < 12){
							connection.updateDataGCS(ID_SQLITE, "", "");
						}
					}
					// TH2 - Trước ngày 10/1/2015
					if(nam == 2015 && thang == 1 && ngay < 10){
						if(Float.parseFloat(PMAX_TT) <= 40){
							if(Float.parseFloat(H1_PMAX) > 40){
								if(nam_h1 == 2014 && thang_h1 == 12 && ngay_h1 >= 10 || nam_h1 > 2014){
									connection.updateDataGCS(ID_SQLITE, H1_PMAX, NGAY_H1_PMAX);
								}
								if(nam_h1 == 2014 && thang_h1 == 12 && ngay_h1 < 10){
									
								}
							} else {
								if(Float.parseFloat(H1_PMAX) > Float.parseFloat(PMAX_TT)){
									if(nam_h1 == 2014 && thang_h1 == 12 && ngay_h1 >= 10 || nam_h1 > 2014){
										connection.updateDataGCS(ID_SQLITE, H1_PMAX, NGAY_H1_PMAX);
									}
								} else {
									
								}
							}
						}
					}
					// TH3 - Sau ngày 10/1/2015
					if((nam == 2015 && thang == 1 && ngay >= 10) || (nam == 2015 && thang > 1) || nam > 2015){
						if(Float.parseFloat(PMAX_TT) <= 40){
							if(Float.parseFloat(H1_PMAX) > 40){
								if(nam_h1 == 2014 && thang_h1 == 12 && ngay_h1 >= 10 || nam_h1 > 2014){
									connection.updateDataGCS(ID_SQLITE, H1_PMAX, NGAY_H1_PMAX);
								}
								if(nam_h1 == 2014 && thang_h1 == 12 && ngay_h1 < 10){
									
								}
							} else {
								if(nam_h1 == 2014 && thang_h1 == 12 && ngay_h1 >= 10 || nam_h1 > 2014){
									if(Float.parseFloat(H1_PMAX) <= Float.parseFloat(PMAX_TT)){
										
									} else {
										connection.updateDataGCS(ID_SQLITE, H1_PMAX, NGAY_H1_PMAX);
									}
								}
								if(nam_h1 == 2014 && thang_h1 == 12 && ngay_h1 < 10){
									
								}
							}
						}
					}
				}
			} else {
				comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Bạn chưa nhập PMAX", 2000);
			}
		} catch(Exception ex) {
			comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Lỗi nhập PMAX", 2000);
		}
	}
	
	// ------------------- CÁC HÀM XỬ LÝ BÀN PHÍM ----------------------
	/**
	 * Ẩn bàn phím
	 * 
	 */
	@SuppressLint("NewApi") public void hideSoftKeyboard() {
		etTimKiem.setInputType(InputType.TYPE_NULL);
		etCSMoi.setInputType(InputType.TYPE_NULL);
		etPmax.setInputType(InputType.TYPE_NULL);
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			etTimKiem.setRawInputType(InputType.TYPE_CLASS_TEXT);
			etTimKiem.setTextIsSelectable(true);
			etCSMoi.setRawInputType(InputType.TYPE_CLASS_TEXT);
			etCSMoi.setTextIsSelectable(true);
			etPmax.setRawInputType(InputType.TYPE_CLASS_TEXT);
			etPmax.setTextIsSelectable(true);
		}
	}

	/**
	 * Xứ lý sự kiện chuyển phím khi chạm vào edittext
	 * 
	 */
	private void eventEdittext() {
		etTimKiem.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				viewControlNumber.setVisibility(View.GONE);
				enableKeyboard(View.VISIBLE);
			}
		});

		etTimKiem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewControlNumber.setVisibility(View.GONE);
				enableKeyboard(View.VISIBLE);
			}
		});

		etCSMoi.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				viewControlNumber.setVisibility(View.VISIBLE);
				enableKeyboard(View.GONE);
			}
		});

		etCSMoi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewControlNumber.setVisibility(View.VISIBLE);
				enableKeyboard(View.GONE);
			}
		});
		
		etPmax.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				viewControlNumber.setVisibility(View.VISIBLE);
				enableKeyboard(View.GONE);
			}
		});

		etPmax.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewControlNumber.setVisibility(View.VISIBLE);
				enableKeyboard(View.GONE);
			}
		});
		
	}

	/**
	 * Set bàn phím
	 * 
	 */
	private void setKeyboard() {
		try {
			controlInflater = LayoutInflater.from(getApplicationContext());
			viewControlNumber = controlInflater.inflate(R.layout.keyboard_number, null);
			viewControlText = controlInflater.inflate(R.layout.keyboard_text, null);
			LayoutParams layoutParamsControl = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			this.addContentView(viewControlText, layoutParamsControl);
			this.addContentView(viewControlNumber, layoutParamsControl);
		} catch (Exception ex) {

		}
	}

	/**
	 * Thêm text khi nhấn vào bàn phím
	 * 
	 */
	private void insertText(String s, EditText et) {
		try {
			if(etPmax.isFocused()){
				String pmax = etPmax.getText().toString();
//				if(!pmax.equals("")){
					if(pmax.contains(".")){
						if(pmax.replace(".", ",").split(",").length > 1) {
							if(pmax.replace(".", ",").split(",")[1].toString().length() < 2){
								int start = et.getSelectionStart();
								int end = et.getSelectionEnd();
								et.setText(et.getText().toString().substring(0, start)
										+ s
										+ et.getText().toString()
												.substring(end, et.getText().toString().length()));
								et.setSelection(start + 1);
							}
						} else {
							int start = et.getSelectionStart();
							int end = et.getSelectionEnd();
							et.setText(et.getText().toString().substring(0, start)
									+ s
									+ et.getText().toString()
											.substring(end, et.getText().toString().length()));
							et.setSelection(start + 1);
						}
					} else {
						int start = et.getSelectionStart();
						int end = et.getSelectionEnd();
						et.setText(et.getText().toString().substring(0, start)
								+ s
								+ et.getText().toString()
										.substring(end, et.getText().toString().length()));
						et.setSelection(start + 1);
					}
//				}
			} else {
				int start = et.getSelectionStart();
				int end = et.getSelectionEnd();
				et.setText(et.getText().toString().substring(0, start)
						+ s
						+ et.getText().toString()
								.substring(end, et.getText().toString().length()));
				et.setSelection(start + 1);
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * Xóa ký tự
	 * 
	 */
	private void deleteText(EditText et) {
		try {
			int start = et.getSelectionStart();
			int end = et.getSelectionEnd();
			if (start > 0) {
				if (start == end) {
					et.setText(et.getText().toString().substring(0, start - 1)
							+ et.getText().toString().substring(end, et.getText().toString().length()));
					et.setSelection(start - 1);
				} else {
					et.setText(et.getText().toString().substring(0, start)
							+ et.getText().toString().substring(end, et.getText().toString().length()));
					et.setSelection(start);
				}
			} else {
				if (start != end) {
					et.setText(et.getText().toString().substring(0, start)
							+ et.getText().toString().substring(end, et.getText().toString().length()));
					et.setSelection(start);
				}
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * Ẩn hiện bàn phím chữ
	 * 
	 */
	private void enableKeyboard(int type) {
		btnQ.setVisibility(type);
		btnW.setVisibility(type);
		btnE.setVisibility(type);
		btnR.setVisibility(type);
		btnT.setVisibility(type);
		btnY.setVisibility(type);
		btnU.setVisibility(type);
		btnI.setVisibility(type);
		btnO.setVisibility(type);
		btnP.setVisibility(type);
		btnA.setVisibility(type);
		btnS.setVisibility(type);
		btnD.setVisibility(type);
		btnF.setVisibility(type);
		btnG.setVisibility(type);
		btnH.setVisibility(type);
		btnJ.setVisibility(type);
		btnK.setVisibility(type);
		btnL.setVisibility(type);
		btnZ.setVisibility(type);
		btnX.setVisibility(type);
		btnC.setVisibility(type);
		btnV.setVisibility(type);
		btnB.setVisibility(type);
		btnN.setVisibility(type);
		btnM.setVisibility(type);
		btnClear2.setVisibility(type);
		btnUpper.setVisibility(type);
		btnNumber.setVisibility(type);
		btnSpace.setVisibility(type);
		btnNext.setVisibility(type);
		btnPhay.setVisibility(type);
		btnCham.setVisibility(type);
		btnNgoacDong.setVisibility(type);
	}

	/**
	 * Xử lý sự kiện bàn phím
	 * 
	 */
	private void handleKeyboard() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);
		btn6 = (Button) findViewById(R.id.btn6);
		btn7 = (Button) findViewById(R.id.btn7);
		btn8 = (Button) findViewById(R.id.btn8);
		btn9 = (Button) findViewById(R.id.btn9);
		btn0 = (Button) findViewById(R.id.btn0);
		btnDot = (Button) findViewById(R.id.btnDot);
		btnClear = (Button) findViewById(R.id.btnClear);

		btnQ = (Button) findViewById(R.id.btnQ);
		btnW = (Button) findViewById(R.id.btnW);
		btnE = (Button) findViewById(R.id.btnE);
		btnR = (Button) findViewById(R.id.btnR);
		btnT = (Button) findViewById(R.id.btnT);
		btnY = (Button) findViewById(R.id.btnY);
		btnU = (Button) findViewById(R.id.btnU);
		btnI = (Button) findViewById(R.id.btnI);
		btnO = (Button) findViewById(R.id.btnO);
		btnP = (Button) findViewById(R.id.btnP);
		btnA = (Button) findViewById(R.id.btnA);
		btnS = (Button) findViewById(R.id.btnS);
		btnD = (Button) findViewById(R.id.btnD);
		btnF = (Button) findViewById(R.id.btnF);
		btnG = (Button) findViewById(R.id.btnG);
		btnH = (Button) findViewById(R.id.btnH);
		btnJ = (Button) findViewById(R.id.btnJ);
		btnK = (Button) findViewById(R.id.btnK);
		btnL = (Button) findViewById(R.id.btnL);
		btnZ = (Button) findViewById(R.id.btnZ);
		btnX = (Button) findViewById(R.id.btnX);
		btnC = (Button) findViewById(R.id.btnC);
		btnV = (Button) findViewById(R.id.btnV);
		btnB = (Button) findViewById(R.id.btnB);
		btnN = (Button) findViewById(R.id.btnN);
		btnM = (Button) findViewById(R.id.btnM);
		btnClear2 = (Button) findViewById(R.id.btnClear2);
		btnUpper = (Button) findViewById(R.id.btnUpper);
		btnNumber = (Button) findViewById(R.id.btnNumber);
		btnSpace = (Button) findViewById(R.id.btnSpace);
		btnNext = (Button) findViewById(R.id.btnNext);
		btnPhay = (Button) findViewById(R.id.btnPhay);
		btnCham = (Button) findViewById(R.id.btnCham);
		btnNgoacDong = (Button) findViewById(R.id.btnNgoacDong);

		// Xử lý bàn phím số
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					insertText("1", etCSMoi);
				else if(etPmax.isFocused())
					insertText("1", etPmax);
			}
		});

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					insertText("2", etCSMoi);
				else if(etPmax.isFocused())
					insertText("2", etPmax);
			}
		});

		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					insertText("3", etCSMoi);
				else if(etPmax.isFocused())
					insertText("3", etPmax);
			}
		});

		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					insertText("4", etCSMoi);
				else if(etPmax.isFocused())
					insertText("4", etPmax);
			}
		});

		btn5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					insertText("5", etCSMoi);
				else if(etPmax.isFocused())
					insertText("5", etPmax);
			}
		});

		btn6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					insertText("6", etCSMoi);
				else if(etPmax.isFocused())
					insertText("6", etPmax);
			}
		});

		btn7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					insertText("7", etCSMoi);
				else if(etPmax.isFocused())
					insertText("7", etPmax);
			}
		});

		btn8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					insertText("8", etCSMoi);
				else if(etPmax.isFocused())
					insertText("8", etPmax);
			}
		});

		btn9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					insertText("9", etCSMoi);
				else if(etPmax.isFocused())
					insertText("9", etPmax);
			}
		});

		btn0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					insertText("0", etCSMoi);
				else if(etPmax.isFocused())
					insertText("0", etPmax);
			}
		});

		btnDot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					if(!etCSMoi.getText().toString().contains(".")){
						insertText(".", etCSMoi);
					}
//				if(etPmax.isFocused())
//					if(!etPmax.getText().toString().contains(".")){
//						insertText(".", etPmax);
//					}
			}
		});

		btnClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(etCSMoi.isFocused())
					deleteText(etCSMoi);
				else if(etPmax.isFocused())
					deleteText(etPmax);
			}
		});

		btnClear.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if(etCSMoi.isFocused())
					etCSMoi.setText("");
				else if(etPmax.isFocused())
					etPmax.setText("");
				return false;
			}
		});

		// Xử lý bàn phím chữ
		btnQ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnQ.getText().toString(), etTimKiem);
			}
		});

		btnW.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnW.getText().toString(), etTimKiem);
			}
		});

		btnE.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnE.getText().toString(), etTimKiem);
			}
		});

		btnR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnR.getText().toString(), etTimKiem);
			}
		});

		btnT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnT.getText().toString(), etTimKiem);
			}
		});

		btnY.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnY.getText().toString(), etTimKiem);
			}
		});

		btnU.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnU.getText().toString(), etTimKiem);
			}
		});

		btnI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnI.getText().toString(), etTimKiem);
			}
		});

		btnO.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnO.getText().toString(), etTimKiem);
			}
		});

		btnP.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnP.getText().toString(), etTimKiem);
			}
		});

		btnA.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnA.getText().toString(), etTimKiem);
			}
		});

		btnS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnS.getText().toString(), etTimKiem);
			}
		});

		btnD.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnD.getText().toString(), etTimKiem);
			}
		});

		btnF.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnF.getText().toString(), etTimKiem);
			}
		});

		btnG.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnG.getText().toString(), etTimKiem);
			}
		});

		btnH.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnH.getText().toString(), etTimKiem);
			}
		});

		btnJ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnJ.getText().toString(), etTimKiem);
			}
		});

		btnK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnK.getText().toString(), etTimKiem);
			}
		});

		btnL.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnL.getText().toString(), etTimKiem);
			}
		});

		btnZ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnZ.getText().toString(), etTimKiem);
			}
		});

		btnX.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnX.getText().toString(), etTimKiem);
			}
		});

		btnC.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnC.getText().toString(), etTimKiem);
			}
		});

		btnV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnV.getText().toString(), etTimKiem);
			}
		});

		btnB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnB.getText().toString(), etTimKiem);
			}
		});

		btnN.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnN.getText().toString(), etTimKiem);
			}
		});

		btnM.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnM.getText().toString(), etTimKiem);
			}
		});

		btnCham.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnCham.getText().toString(), etTimKiem);
			}
		});

		btnPhay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnPhay.getText().toString(), etTimKiem);
			}
		});

		btnSpace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(" ", etTimKiem);
			}
		});

		btnClear2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteText(etTimKiem);
			}
		});

		btnClear2.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				etTimKiem.setText("");
				return false;
			}
		});

		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		btnNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btnQ.getText().toString().equals("q")
						|| btnQ.getText().toString().equals("Q")) {
					setNumberOrTextKeyboard(true);
				} else {
					setNumberOrTextKeyboard(false);
				}
			}
		});

		btnUpper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btnQ.getText().toString().equals("q")
						|| btnQ.getText().toString().equals("Q")) {
					upperText();
				} else {
					upperNumber();
				}
			}
		});

		btnNgoacDong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				insertText(btnNgoacDong.getText().toString(), etTimKiem);
			}
		});
	}

	/**
	 * Thay đổi kiểu bàn phím chữ hay số
	 * 
	 * @param state
	 *            nếu state = true thì chuyển sang bàn phím số và ngược lại
	 */
	private void setNumberOrTextKeyboard(boolean state) {
		if (state) {
			btnNgoacDong.setVisibility(View.VISIBLE);

			btnQ.setText("1");
			btnW.setText("2");
			btnE.setText("3");
			btnR.setText("4");
			btnT.setText("5");
			btnY.setText("6");
			btnU.setText("7");
			btnI.setText("8");
			btnO.setText("9");
			btnP.setText("0");

			btnA.setText("@");
			btnS.setText("#");
			btnD.setText("$");
			btnF.setText("%");
			btnG.setText("&");
			btnH.setText("*");
			btnJ.setText("-");
			btnK.setText("+");
			btnL.setText("(");

			btnZ.setText("!");
			btnX.setText("\"");
			btnC.setText("'");
			btnV.setText(":");
			btnB.setText(";");
			btnN.setText("/");
			btnM.setText("?");

			btnNumber.setText("ABC");
		} else {
			btnNgoacDong.setVisibility(View.GONE);

			btnQ.setText("q");
			btnW.setText("w");
			btnE.setText("e");
			btnR.setText("r");
			btnT.setText("t");
			btnY.setText("y");
			btnU.setText("u");
			btnI.setText("i");
			btnO.setText("o");
			btnP.setText("p");

			btnA.setText("a");
			btnS.setText("s");
			btnD.setText("d");
			btnF.setText("f");
			btnG.setText("g");
			btnH.setText("h");
			btnJ.setText("j");
			btnK.setText("k");
			btnL.setText("l");

			btnZ.setText("z");
			btnX.setText("x");
			btnC.setText("c");
			btnV.setText("v");
			btnB.setText("b");
			btnN.setText("n");
			btnM.setText("m");

			btnNumber.setText("123");
		}
	}

	/**
	 * Sự kiện chuyển chữ hoa chữ thường
	 * 
	 */
	private void upperText() {
		if (btnQ.getText().toString().equals("q")) {
			btnUpper.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_upper, 0);

			btnQ.setText("Q");
			btnW.setText("W");
			btnE.setText("E");
			btnR.setText("R");
			btnT.setText("T");
			btnY.setText("Y");
			btnU.setText("U");
			btnI.setText("I");
			btnO.setText("O");
			btnP.setText("P");

			btnA.setText("A");
			btnS.setText("S");
			btnD.setText("D");
			btnF.setText("F");
			btnG.setText("G");
			btnH.setText("H");
			btnJ.setText("J");
			btnK.setText("K");
			btnL.setText("L");

			btnZ.setText("Z");
			btnX.setText("X");
			btnC.setText("C");
			btnV.setText("V");
			btnB.setText("B");
			btnN.setText("N");
			btnM.setText("M");
		} else {
			btnUpper.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_lower, 0);

			btnQ.setText("q");
			btnW.setText("w");
			btnE.setText("e");
			btnR.setText("r");
			btnT.setText("t");
			btnY.setText("y");
			btnU.setText("u");
			btnI.setText("i");
			btnO.setText("o");
			btnP.setText("p");

			btnA.setText("a");
			btnS.setText("s");
			btnD.setText("d");
			btnF.setText("f");
			btnG.setText("g");
			btnH.setText("h");
			btnJ.setText("j");
			btnK.setText("k");
			btnL.setText("l");

			btnZ.setText("z");
			btnX.setText("x");
			btnC.setText("c");
			btnV.setText("v");
			btnB.setText("b");
			btnN.setText("n");
			btnM.setText("m");
		}
	}

	/**
	 * Sự kiển chuyển số và ký tự
	 * 
	 */
	private void upperNumber() {
		if (btnQ.getText().toString().equals("1")) {
			btnUpper.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_upper, 0);

			btnQ.setText("~");
			btnW.setText("`");
			btnE.setText("|");
			btnR.setText("•");
			btnT.setText("√");
			btnY.setText("π");
			btnU.setText("÷");
			btnI.setText("×");
			btnO.setText("{");
			btnP.setText("}");

			btnA.setText("→");
			btnS.setText("£");
			btnD.setText("ç");
			btnF.setText("€");
			btnG.setText("°");
			btnH.setText("ˆ");
			btnJ.setText("_");
			btnK.setText("=");
			btnL.setText("[");
			btnNgoacDong.setText("]");

			btnZ.setText("™");
			btnX.setText("®");
			btnC.setText("©");
			btnV.setText("¶");
			btnB.setText("\\");
			btnN.setText("<");
			btnM.setText(">");
		} else {
			btnUpper.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_lower, 0);

			btnQ.setText("1");
			btnW.setText("2");
			btnE.setText("3");
			btnR.setText("4");
			btnT.setText("5");
			btnY.setText("6");
			btnU.setText("7");
			btnI.setText("8");
			btnO.setText("9");
			btnP.setText("0");

			btnA.setText("@");
			btnS.setText("#");
			btnD.setText("$");
			btnF.setText("%");
			btnG.setText("&");
			btnH.setText("*");
			btnJ.setText("-");
			btnK.setText("+");
			btnL.setText("(");

			btnZ.setText("!");
			btnX.setText("\"");
			btnC.setText("'");
			btnV.setText(":");
			btnB.setText(";");
			btnN.setText("/");
			btnM.setText("?");
		}
	}
	
	// ------------------- CÁC HÀM XỬ LÝ CAMERA ------------------------
    Bitmap bitmap = null;
//	Camera camera;
//	SurfaceView surfaceView;
//	SurfaceHolder surfaceHolder;
//	boolean previewing = false;
    private Camera cameraObject;
//	private Camera.Parameters parameter;
	private FrameLayout preview;
	private ShowCamera showCamera;
	
    public void showCamera(){
    	try{
    		cameraObject = isCameraAvailiable();
    		showCamera = new ShowCamera(this, cameraObject);
//    		parameter = cameraObject.getParameters();
    		setDisplayOrientation(cameraObject, 90);

    		// auto focus
    		preview.addView(showCamera);
    		setCameraFocus(myAutoFocusCallback);
    	} catch(Exception ex) {
    		comm.ShowToast(Activity_Camera_MTB.this, "Error show camera: " + ex.toString(), Toast.LENGTH_LONG);
    	}
    }
    
	/**
	 * Open camera
	 * 
	 * @return
	 */
	public static Camera isCameraAvailiable() {
		Camera object = null;
		try {
			object = Camera.open();
		} catch (Exception e) {
		}
		return object;
	}
	
	/**
	 * Xoay khung nhìn hình ảnh
	 * 
	 * @param camera
	 *            : Camera
	 * @param angle
	 *            : góc xoay
	 */
	protected void setDisplayOrientation(Camera camera, int angle) {
		Method downPolymorphic;
		try {
			downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
			if (downPolymorphic != null)
				downPolymorphic.invoke(camera, new Object[] { angle });
		} catch (Exception e1) {

		}
	}
	
	/**
	 * auto focus
	 * 
	 */
	AutoFocusCallback myAutoFocusCallback = new AutoFocusCallback() {

		@Override
		public void onAutoFocus(boolean arg0, Camera arg1) {
			btnCapture.setEnabled(true);
		}
	};
	
	/**
	 * Tự động focus
	 * 
	 * @param autoFocus
	 *            : AutoFocusCallback
	 */
	@SuppressWarnings("static-access")
	public void setCameraFocus(AutoFocusCallback autoFocus) {
		if (cameraObject.getParameters().getFocusMode().equals(cameraObject.getParameters().FOCUS_MODE_AUTO) || cameraObject.getParameters().getFocusMode().equals(cameraObject.getParameters().FOCUS_MODE_MACRO)) {
			cameraObject.autoFocus(autoFocus);
		}
	}
	
	public Bitmap rotateImage(int angle, Bitmap bitmapSrc) {
	    Matrix matrix = new Matrix();
	    matrix.postRotate(angle);
	    return Bitmap.createBitmap(bitmapSrc, 0, 0, 
	        bitmapSrc.getWidth(), bitmapSrc.getHeight(), matrix, true);
	}
	
	private PictureCallback capturedIt = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			try{
				String ID_SQLITE = adapter.getItem(selected_index).get("ID_SQLITE");
				Cursor c = connection.getDataForImage(ID_SQLITE);
				Bitmap bitmap = null;
				if(c.moveToFirst()){
					bitmap = drawTextToBitmap(Activity_Camera_MTB.this, rotateImage(90, Common.decodeBase64Byte(data)), c.getString(0), c.getString(2), c.getString(3), c.getString(1), c.getString(4));
				}
				if(saveImageToFile(bitmap)){
					setImage();
	            } else {
	            	runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							comm.ShowToast(Activity_Camera_MTB.this, "Chưa lưu được hình ảnh", Toast.LENGTH_LONG);
						}
					});
	            }
				camera.startPreview();
			} catch(final Exception ex) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						comm.ShowToast(Activity_Camera_MTB.this, "Error take picture: " + ex.toString(), Toast.LENGTH_LONG);
					}
				});
			}
		}
	};
	
    /** Vẽ chữ lên ảnh
	 */
	public Bitmap drawTextToBitmap(Context gContext, Bitmap bitmap, String name, String ma_dd, String so_cto, String cs_moi, String chuoi_gia) {
		Bitmap.Config bitmapConfig = bitmap.getConfig();
		if (bitmapConfig == null) {
			bitmapConfig = Bitmap.Config.ARGB_8888;
		}
		bitmap = bitmap.copy(bitmapConfig, true);

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setTextSize(bitmap.getHeight()/25);
		
		Paint paint_rec = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_rec.setColor(Color.BLACK);
		
		// Xử lý cắt chuỗi tên xuống dòng
		String[] arrName = name.trim().split(" ");
		String name1 = "";
		String name2 = "";
		for (String sName : arrName) {
			Rect bounds_cut = new Rect();
			paint.getTextBounds(name1 + sName + " ", 0, (name1 + sName + " ").length(), bounds_cut);
			if(bounds_cut.width() < 2*bitmap.getWidth()/3){
				name1 += sName + " ";
			} else {
				name2 += sName + " ";
			}
		}
		
		name = name1.trim();
		
		Rect bounds1 = new Rect();
		paint.getTextBounds(name, 0, name.length(), bounds1);
		int x1 = 10;
		int y1 = bounds1.height();
		int y1_2 = 2*bounds1.height();
		
		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), conf); // bitmap.getHeight() + 5 * bounds1.height() + 30
		Canvas c = new Canvas(bmp);
		// Vẽ ảnh lên khung mới có viền đen
		c.drawBitmap(bitmap, 0, 0, paint_rec);
		// Vẽ tên
		c.drawRect(0, 0, bmp.getWidth(), 2 * bounds1.height() + 15, paint_rec);
		c.drawRect(0, bmp.getHeight() - 2 * bounds1.height() - 15, bmp.getWidth(), bmp.getHeight(), paint_rec);
		c.drawText(name, x1, y1, paint);
		if(!name2.equals(""))
			c.drawText(name2, x1, y1_2, paint);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String currentDateandTime = sdf.format(new Date());
		
		Rect bounds0 = new Rect();
		paint.getTextBounds(currentDateandTime, 0, currentDateandTime.length(), bounds0);
		c.drawRect(0, 2 * bounds1.height() + 15, bmp.getWidth(), 3*bounds1.height() + 15, paint_rec);//bounds0.width() + 15
		
		int x5 = 10;
		int y5 = 2 * bounds1.height() + 30;
		c.drawText(currentDateandTime, x5, y5, paint);
		
		Rect bounds6 = new Rect();
		paint.getTextBounds(chuoi_gia, 0, chuoi_gia.length(), bounds6);
		int x6 = 5;//bmp.getWidth() - 10 - bounds6.width()
		int y6 = bmp.getHeight() - 20 - bounds6.height();
		c.drawText(chuoi_gia, x6, y6, paint);
		
		Rect bounds2 = new Rect();
		paint.getTextBounds(ma_dd, 0, ma_dd.length(), bounds2);
		int x2 = 10;
		int y2 = bmp.getHeight() - 10;
		c.drawText(ma_dd, x2, y2, paint);
		
		Rect bounds3 = new Rect();
		paint.getTextBounds(so_cto, 0, so_cto.length(), bounds3);
		int x3 = bmp.getWidth() - 10 - bounds3.width();
		int y3 = bmp.getHeight() - 10;
		c.drawText(so_cto, x3, y3, paint);
		
		// Vẽ CS_MOI lên góc trên bên phải
		Rect bounds4 = new Rect();
		paint.getTextBounds(cs_moi, 0, cs_moi.length(), bounds4);
		int x4 = bmp.getWidth() - 10 - bounds4.width();
		int y4 = bounds4.height() + 8;
		c.drawText(cs_moi, x4, y4, paint);
		
		return bmp;
	}
	
	/** Vẽ chữ lên ảnh
	 */
	public Bitmap drawCSMoiToBitmap(Context gContext, Bitmap bitmap, String cs_moi) {
		Bitmap.Config bitmapConfig = bitmap.getConfig();
		if (bitmapConfig == null) {
			bitmapConfig = Bitmap.Config.ARGB_8888;
		}
		bitmap = bitmap.copy(bitmapConfig, true);

		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setTextSize(bitmap.getHeight()/25);
		
		Paint paint_rec = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_rec.setColor(Color.BLACK);
		
		Rect bounds_width = new Rect();
		paint.getTextBounds("0000000", 0, "0000000".length(), bounds_width);
		
		Rect bounds = new Rect();
		paint.getTextBounds(cs_moi, 0, cs_moi.length(), bounds);
		int x = bitmap.getWidth() - 10 - bounds.width();
		int y = bounds.height() + 8;
		canvas.drawRect(bitmap.getWidth() - bounds_width.width(), 0, bitmap.getWidth(), bounds.height() + 15, paint_rec);
		canvas.drawText(cs_moi, x, y, paint);

		return bitmap;
	}
	
    /** Lưu hình ảnh
	 */
	private boolean saveImageToFile(Bitmap bmp_result){
		try{
			if(bmp_result != null){
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				bmp_result.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
				File f = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + TEN_FILE + "/" + fileName + "_" + adapter.getItem(selected_index).get("MA_CTO") + "_" + adapter.getItem(selected_index).get("NAM") + "_" + adapter.getItem(selected_index).get("THANG") + "_" + adapter.getItem(selected_index).get("KY") + "_" + adapter.getItem(selected_index).get("MA_DDO") + "_" + adapter.getItem(selected_index).get("LOAI_BCS") + ".jpg");
				if(f.exists()){
					f.delete();
				}
				f.createNewFile();
				FileOutputStream fo = new FileOutputStream(f);
				fo.write(bytes.toByteArray());
				fo.close();
				comm.scanFile(Activity_Camera_MTB.this.getApplicationContext(), new String[] {Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + TEN_FILE + "/" + fileName + "_" + adapter.getItem(selected_index).get("MA_CTO") + "_" + adapter.getItem(selected_index).get("NAM") + "_" + adapter.getItem(selected_index).get("THANG") + "_" + adapter.getItem(selected_index).get("KY") + "_" + adapter.getItem(selected_index).get("MA_DDO") + "_" + adapter.getItem(selected_index).get("LOAI_BCS") + ".jpg"});
				return true;
			} else {
				return false;
			}
		} catch(Exception ex) {
			comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "Không chụp được hình ảnh", Toast.LENGTH_LONG);
			return false;
		}
	}
	
	/** Hiển thị camera hay hình ảnh đã chụp
     * 
     */
    public void setImage(){
		try{
			if(selected_index >= adapter.getCount()){
				selected_index = 0;
				connection.updateDataIndex(fileName, selected_index);
				lvCustomer.setSelection(selected_index);
			}
			String filePath = Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + TEN_FILE + "/" + fileName + "_" + adapter.getItem(selected_index).get("MA_CTO") + "_" + adapter.getItem(selected_index).get("NAM") + "_" + adapter.getItem(selected_index).get("THANG") + "_" + adapter.getItem(selected_index).get("KY") + "_" + adapter.getItem(selected_index).get("MA_DDO") + "_" + adapter.getItem(selected_index).get("LOAI_BCS") + ".jpg";
			Bitmap bmGoc = BitmapFactory.decodeFile(filePath);
			if(bmGoc != null){
				Bitmap bmImage = Bitmap.createScaledBitmap(bmGoc, bmGoc.getWidth()/2, bmGoc.getHeight()/2, false);
				Common.state_show_image = 1;
				ivViewImage.setVisibility(View.VISIBLE);
				btnDelete.setEnabled(true);
				if(Common.CAMERA_TYPE.equals("AIBALL")){
					if(preview!=null){
						cameraObject.stopPreview();
					}
					rlCamera.setVisibility(View.GONE);
					btnView.setEnabled(true);
					btnCapture.setEnabled(false);
				}
				ivViewImage.setImageBitmapReset(bmImage, 0, true);
//				ivViewImage.zoomToCenter(1.3f);
			} else {
				Common.state_show_image = 0;
				ivViewImage.setVisibility(View.GONE);
				btnDelete.setEnabled(false);
				if(Common.CAMERA_TYPE.equals("AIBALL")){// && (Common.getSSID(Activity_Camera.this).substring(0, 3).toLowerCase().equals("evn") || !Common.PHIEN_BAN.equals("HN"))
					if(!preview.isShown()){
						rlCamera.setVisibility(View.VISIBLE);
						cameraObject.startPreview();
					}
					btnView.setEnabled(false);
//					if(Common.state_show_camera == 1){
						btnCapture.setEnabled(true);
//					}
				}
				ivViewImage.setImageBitmapReset(null, 0, true);
			}
		} catch(Exception ex) {
			comm.ShowToast(Activity_Camera_MTB.this.getApplicationContext(), "setImage error:\n" + ex.toString(), Toast.LENGTH_LONG);
		}
	}
	
	// ------------------- CÁC HÀM XỬ LÝ KHÁC --------------------------
	/** Lấy vị trí đang ghi
	 * @return
	 */
	public int getPos() {
		try {
			EntityIndex ei = connection.getDataIndex(fileName);
			return ei.getIndex();
		} catch (Exception ex) {
			return 0;
		}
	}

	/** Load lại dữ liệ trong adapter khi có thay đổi
	 */
	private void reLoadData(int pos, String CS_MOI, String SL_MOI, String TTR_MOI, String PMAX, String NGAY_PMAX){
		try{
			adapter.getItem(pos).put("CS_MOI", CS_MOI);
			adapter.getItem(pos).put("SL_MOI", SL_MOI);
			adapter.getItem(pos).put("TTR_MOI", TTR_MOI);
			adapter.getItem(pos).put("PMAX", PMAX);
			adapter.getItem(pos).put("NGAY_PMAX", NGAY_PMAX);
			adapter.notifyDataSetChanged();
//			loadDaGhi();
		} catch(Exception ex) {
			comm.msbox("Lỗi", "Lỗi làm mới dữ liệu vừa ghi", Activity_Camera_MTB.this);
		}
	}
	
	/** Ẩn hiện thông tin PMAX
	 */
	public void showHidePmax(int position){
		try {
			String BCS = adapter.getItem(position).get("LOAI_BCS");
			if (BCS.equals("BT") || BCS.equals("CD")) {
				etPmax.setVisibility(View.VISIBLE);
				etNgayPmax.setVisibility(View.VISIBLE);
				if (BCS.equals("BT")) {
					etPmax.setHint("Pmax");
				} else {
					etPmax.setHint("H1.Pmax");
				}
			} else {
				etPmax.setVisibility(View.GONE);
				etNgayPmax.setVisibility(View.GONE);
			}
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Lỗi ẩn hiện thông tin PMAX", Activity_Camera_MTB.this);
		}
	}
	
	/**
	 * set dữ liệu vào edittext (cs_moi, ghi_chu, ttr_moi)
	 * 
	 */
	public void setDataOnEditText(int pos, int state) {
		try{
			Cursor c = connection.setDataEditText(adapter.getItem(selected_index).get("ID_SQLITE"));
			String TTR_MOI = "";
			String CS_MOI = "";
			String PMAX = "";
			String NGAY_PMAX = "";
			if (c.moveToFirst()) {
				do {
					CS_MOI = c.getString(0);
					TTR_MOI = c.getString(1);
					PMAX = c.getString(3);
					NGAY_PMAX = c.getString(4);
				} while (c.moveToNext());
			}
			if(NGAY_PMAX.equals("01/01/1000 00:00:00")){
				NGAY_PMAX = "";
			}
			GetPosition_spnTrangThai(TTR_MOI);
			etCSMoi.setText(CS_MOI);
			etCSMoi.selectAll();
			etPmax.setText(PMAX);
			etNgayPmax.setText(NGAY_PMAX);
		} catch(Exception ex) {
			Cursor c = connection.setDataEditText2(adapter.getItem(selected_index).get("ID_SQLITE"));
			String TTR_MOI = "";
			String CS_MOI = "";
			if (c.moveToFirst()) {
				do {
					CS_MOI = c.getString(0);
					TTR_MOI = c.getString(1);
				} while (c.moveToNext());
			}
			GetPosition_spnTrangThai(TTR_MOI);
			etCSMoi.setText(CS_MOI);
			etCSMoi.selectAll();
		}
	}
	
	/**
	 * Lấy trạng thái của công tơ
	 * 
	 */
	public void GetPosition_spnTrangThai(String TT_MOI) {
		for (int i = 0; i < ConstantVariables.ARR_TRANGTHAI.length; i++) {
			if (TT_MOI != null
					&& TT_MOI.equals(ConstantVariables.ARR_TRANGTHAI[i].split("-")[0].trim())) {
				spTrangThai.setSelection(i);
				return;
			} else {
				spTrangThai.setSelection(0);
			}
		}
	}

	/**
	 * Lấy tên cột từ chuỗi
	 * 
	 * @param selected
	 * @return tên cột
	 */
	private String GetColNameFromString(String selected) {
		for (int i = 0; i < ConstantVariables.TIEU_CHI_TIM_KIEM.length; i++) {
			if (selected != null && selected.equals(ConstantVariables.TIEU_CHI_TIM_KIEM[i])) {
				return ConstantVariables.COL_TIMKIEM[i];
			}
		}
		return "";
	}

	/** Show dialog kết nối bluetooth máy quét mã vạch
	 * 
	 */
	private void showBlueToothSettingBarcode(){
		try{
			final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
			if (!bluetooth.isEnabled()){
				bluetooth.enable();
			}
		
			final Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.bluetooth.BluetoothSettings");
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent);
		} catch(Exception ex) {
			ex.toString();
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
