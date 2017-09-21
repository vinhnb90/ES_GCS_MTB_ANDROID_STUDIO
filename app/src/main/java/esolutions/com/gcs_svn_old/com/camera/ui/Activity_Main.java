package esolutions.com.gcs_svn_old.com.camera.ui;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.ParserConfigurationException;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.entity.EntityGCS;
import esolutions.com.gcs_svn_old.com.camera.utility.AsyncCallWS;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.ConfigInfo;
import esolutions.com.gcs_svn_old.com.camera.utility.ConstantVariables;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;
import esolutions.com.gcs_svn_old.com.camera.utility.row_gcs;
import esolutions.com.gcs_svn_old.com.camera.utility.security;

@TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
public class Activity_Main extends Activity implements
		ScanFragment.ScanProgressCallbacks {

	// public static final String[] ttgcs_colName = new String[] { "Quyen",
	// "DiemDo", "DaGhi", "ChuaGhi" };
	// public static final int[] ttgcs_colID = new int[] { R.id.tv_Quyen,
	// R.id.tv_DiemDo, R.id.tv_DaGhi, R.id.tv_ChuaGhi };
	// public static final String[] thsl_colName = new String[] { "Quyen",
	// "DiemDo", "SLHC", "SLVC" };
	// public static final int[] thsl_colID = new int[] { R.id.tv_Quyen,
	// R.id.tv_DiemDo, R.id.tv_SLHC, R.id.tv_SLVC };

	private Button btnRegister, btnLogin, btnLogout, btnTransferData,
			btnCauHinh, btnSortingRoute, btnGCS, btnBCTH;
	private ImageView ivLogoApp;
	private TextView tvVersion;
	private ListView lsvTTGCS;
	private List<String> fileName;
	private ArrayList<LinkedHashMap<String, String>> ListViewData2 = null;
	private ListView lvChonSo;
	private Button btnGetData;
	private Button btnSendData;
	private RadioButton rbSendData;
	private RadioButton rbGetData;

	private THCSBTAdapter adapter;
	private THTTBTAdapter adapter2;

	// public static final String[] ConstantVariables.COLARR = { "MA_NVGCS",
	// "MA_KHANG", "MA_DDO",
	// "MA_DVIQLY", "MA_GC", "MA_QUYEN", "MA_TRAM", "BOCSO_ID",
	// "LOAI_BCS", "LOAI_CS", "TEN_KHANG", "DIA_CHI", "MA_NN", "SO_HO",
	// "MA_CTO", "SERY_CTO", "HSN", "CS_CU", "TTR_CU", "SL_CU",
	// "SL_TTIEP", "NGAY_CU", "CS_MOI", "TTR_MOI", "SL_MOI", "CHUOI_GIA",
	// "KY", "THANG", "NAM", "NGAY_MOI", "NGUOI_GCS", "SL_THAO",
	// "KIMUA_CSPK", "MA_COT", "CGPVTHD", "HTHUC_TBAO_DK", "DTHOAI_SMS",
	// "EMAIL", "THOI_GIAN", "X", "Y", "SO_TIEN", "HTHUC_TBAO_TH",
	// "TENKHANG_RUTGON",
	// "TTHAI_DBO", "DU_PHONG", "TEN_FILE", "GHICHU", "TT_KHAC", "ID_SQLITE" };

	private Common comm;

	private String fn = "";
	private int count_report = 0;
	private int numRowFilled = 0;

	private String[] arr_so_server; // lấy danh sách sổ có thể lấy về từ server

	private List<String> list_so;// lấy danh sách sổ đang có trên máy
	private List<String> same_items; // chứa danh sách sổ cùng có ở trên sv và
										// mobile

	private SQLiteConnection connection;
	private Dialog alertDialog;

	// protected MyApp mMyApp;
	ThumbnailCache mCache;
	private ScanFragment mScanFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			// mMyApp = (MyApp)this.getApplicationContext();

			// Pick cache size based on memory class of device
			final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			final int memoryClassBytes = am.getMemoryClass() * 1024 * 1024;
			mCache = new ThumbnailCache(memoryClassBytes / 2);

			comm = new Common();
			initComponent();
			eventButton();

			if (Common.PHIEN_BAN.equals("LC")) {
				// String emei =
				// comm.GetIMEI(Activity_Main.this.getApplicationContext());
				// if(!emei.equals("863363025464981")){//863363025464981 -
				// 354013063915065
				// comm.ShowToast(Activity_Main.this.getApplicationContext(),
				// "EMEI " + emei +
				// " của bạn chưa được đăng ký sử dụng.\nVui lòng liên hệ E-Solutions để được cấp quyền.",
				// Toast.LENGTH_LONG);
				// Activity_Main.this.finish();
				// }
				ivLogoApp.setImageDrawable(getResources().getDrawable(
						R.drawable.remote_metering1));
				setTitle(getResources().getString(R.string.app_name_lc));
			}
			tvVersion.append(" - IMEI: "
					+ comm.GetIMEI(Activity_Main.this.getApplicationContext())
					+ " - " + Common.PHIEN_BAN);
			CheckLisence();
			// comm.LoadFolder(this.getApplicationContext());

			if (!comm.checkDB()) {
				comm.ShowToast(Activity_Main.this.getApplicationContext(),
						"Chưa có dữ liệu trong máy", Toast.LENGTH_LONG);
				btnLogout.setVisibility(View.GONE);
				btnTransferData.setVisibility(View.GONE);
			} else {
				try {
					// Common.deleteBackup();
					// comm.CreateBackup(null);
					connection = new SQLiteConnection(
							Activity_Main.this.getApplicationContext(),
							"ESGCS.s3db",
							Environment.getExternalStorageDirectory()
									+ Common.DBFolderPath);

					if (Common.PHIEN_BAN.equals("LC")) {
						if (!connection.getMaDviLC()) {
							comm.ShowToast(
									Activity_Main.this.getApplicationContext(),
									"Phiên bản chỉ dùng cho Điện lực Lai Châu",
									Toast.LENGTH_LONG);
							// this.finish();
						}
					} else {
						if (!connection.getMaDvi()) {
							comm.ShowToast(
									Activity_Main.this.getApplicationContext(),
									"Đơn vị bạn chưa được cấp quyền sử dụng chương trình",
									Toast.LENGTH_LONG);
							this.finish();
						}
					}

					// gps = new GPSTracker(Activity_Main.this);
					Common.IMEI = comm.GetIMEI(this.getApplicationContext());

					if (Common.MA_NVGCS == null) {
						btnLogin.setVisibility(View.VISIBLE);
						btnLogout.setVisibility(View.GONE);
						btnTransferData.setVisibility(View.GONE);
					} else {
						btnLogin.setVisibility(View.GONE);
					}

					fileName = new ArrayList<String>();
				} catch (Exception ex) {
					comm.msbox("err", ex.toString(), this);
				}
			}

			// comm.LoadFolder2(this.getApplicationContext());

			// RefWatcher refWatcher = MyApp.getRefWatcher(this);
			// refWatcher.watch(this);
		} catch (Exception ex) {
			Log.e("error", ex.toString());
		}
	}

	@android.support.annotation.RequiresApi(api = VERSION_CODES.JELLY_BEAN_MR1)
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
		try {
			super.onResume();

			// FragmentManager fm = getFragmentManager();
			// mScanFragment = (ScanFragment) fm.findFragmentByTag("scan");
			// if (mScanFragment == null) {
			// mScanFragment = new ScanFragment();
			// fm.beginTransaction().add(mScanFragment, "scan").commit();
			// }
			//
			// File path = new File(Environment.getExternalStorageDirectory() +
			// "/ESGCS/");
			// mScanFragment.startScan(path.getCanonicalFile(), false);

			// mMyApp.setCurrentActivity(this);
			String result = comm.CheckDbExist(
					Activity_Main.this.getApplicationContext(), "ESGCS.s3db",
					Environment.getExternalStorageDirectory()
							+ Common.DBFolderPath);
			if (result.equals("EXIST")) {
				connection.close();
				connection = null;
				connection = new SQLiteConnection(
						Activity_Main.this.getApplicationContext(),
						"ESGCS.s3db", Environment.getExternalStorageDirectory()
								+ Common.DBFolderPath);
			}
			btnLogin.setVisibility(View.VISIBLE);
			if (Common.PHIEN_BAN.equals("HN")) {
				turnGPSOn();
				LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
				if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
						|| !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Kích hoạt GPS");
					builder.setMessage("Bạn phải kích hoạt GPS để tiếp tục sử dụng chương trình\nKích hoạt ở chế độ chính xác cao để thu thập tọa độ chính xác nhất");
					builder.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialogInterface, int i) {
									Intent intent = new Intent(
											Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(intent);
								}
							});
					alertDialog = builder.create();
					alertDialog.setCanceledOnTouchOutside(false);
					alertDialog.setCancelable(false);
					alertDialog.show();
				}
			}

		} catch (Exception ex) {
			ex.toString();
		}
	}

	private static void unbindDrawable(Drawable d) {
		if (d != null) {
			d.setCallback(null);
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		unbindDrawable(ivLogoApp.getBackground());
	}

	@Override
	public void onBackPressed() {
		try {
			if (!alertDialog.isShowing())
				Activity_Main.this.finish();
		} catch (Exception ex) {
			ex.toString();
			Activity_Main.this.finish();
		}
	}

	@Override
	protected void onPause() {
		// clearReferences();
		comm.LoadFolder(this.getApplicationContext());
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// clearReferences();
		if (connection != null)
			connection.close();
		super.onDestroy();
	}

	// private void clearReferences(){
	// Activity currActivity = mMyApp.getCurrentActivity();
	// if (this.equals(currActivity))
	// mMyApp.setCurrentActivity(null);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_cmd) {
			createDialogCMD();
		} else if (id == R.id.action_del_lotrinh) {
			if (connection.deleteAllDataLoTrinh() != -1) {
				// comm.ShowToast(Activity_Main.this.getApplicationContext(),
				// "Đã xóa hết lộ trình", 2000,
				// Common.size(Activity_Main.this));
				comm.ShowToast(Activity_Main.this.getApplicationContext(),
						"Đã xóa hết lộ trình", Toast.LENGTH_LONG);
			}
		} else if (id == R.id.action_exit) {
			Activity_Main.this.finish();
		} else if (id == R.id.action_load) {
			// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// Intent mediaScanIntent = new
			// Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			// File f = new File("file://" +
			// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
			// Uri contentUri = Uri.fromFile(f);
			// mediaScanIntent.setData(contentUri);
			// this.sendBroadcast(mediaScanIntent);
			// } else {
			// sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
			// Uri.parse("file://" +
			// Environment.getExternalStorageDirectory())));
			// }

			comm.LoadFolderPhoto(Activity_Main.this);
			comm.ShowToast(Activity_Main.this.getApplicationContext(),
					"Đã load lại thư mục photo", Toast.LENGTH_LONG);
		} else {
			super.onOptionsItemSelected(item);
		}
		return true;
	}

	// ---------------------- KHỞI TẠO ---------------------------
	private void initComponent() {
		try {
			btnRegister = (Button) findViewById(R.id.btnRegister);
			btnLogin = (Button) findViewById(R.id.btnLogin);
			btnLogout = (Button) findViewById(R.id.btnLogout);
			btnTransferData = (Button) findViewById(R.id.btnTransferData);
			btnCauHinh = (Button) findViewById(R.id.btnCauHinh);
			btnSortingRoute = (Button) findViewById(R.id.btnSortingRoute);
			btnGCS = (Button) findViewById(R.id.btnGCS);
			btnBCTH = (Button) findViewById(R.id.btnBCTH);
			ivLogoApp = (ImageView) findViewById(R.id.ivLogoApp);
			tvVersion = (TextView) findViewById(R.id.tvVersion);
		} catch (Exception ex) {
			// comm.ShowToast(Activity_Main.this.getApplicationContext(),
			// "Error: " + ex.toString(), Toast.LENGTH_LONG,
			// Common.size(Activity_Main.this));
			comm.ShowToast(Activity_Main.this.getApplicationContext(),
					"Error: " + ex.toString(), Toast.LENGTH_LONG);
		}
	}

	private void eventButton() {
		try {
			btnRegister.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					final Dialog dialog = new Dialog(Activity_Main.this);

					dialog.setTitle("Đăng ký chương trình");
					dialog.setContentView(R.layout.dialog_register);

					final EditText etxtSerial = (EditText) dialog
							.findViewById(R.id.etxtSerial);
					Button btnInputSerial = (Button) dialog
							.findViewById(R.id.btnInputSerial);

					btnInputSerial.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							String strSerial = etxtSerial.getText().toString();
							if (comm.CheckLicense(
									Activity_Main.this.getApplicationContext(),
									strSerial)) {
								comm.msbox("Đăng ký", "Đăng ký thành công",
										Activity_Main.this);
								Common.cfgInfo.setSerial(etxtSerial.getText()
										.toString());
								File cfgFile = new File(Environment
										.getExternalStorageDirectory()
										+ Common.programPath
										+ Common.cfgFileName);

								comm.CreateFileConfig(Common.cfgInfo, cfgFile,
										strSerial);

								btnRegister.setVisibility(View.GONE);
								dialog.dismiss();

							} else {
								comm.msbox("Đăng ký", "Mã đăng ký không đúng",
										Activity_Main.this);
							}
						}
					});

					dialog.show();
				}
			});

			btnLogin.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CreateDialog_Login();
				}
			});

			btnLogout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Common.MA_NVGCS = null;
						btnLogin.setVisibility(View.VISIBLE);
						btnLogout.setVisibility(View.GONE);
						btnTransferData.setVisibility(View.GONE);
						ivLogoApp.getLayoutParams().height = convertPXtoDP(350);
					} catch (Exception ex) {
						comm.msbox("Đăng xuất", "Đăng xuất thất bại",
								Activity_Main.this);
					}
				}
			});

			btnTransferData.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CreateDialog_TransferData();
				}
			});

			btnCauHinh.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent cau_hinh_act = new Intent(Activity_Main.this,
							Activity_Config.class);
					startActivity(cau_hinh_act);
				}
			});

			btnSortingRoute.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent Sorting_route_Act = new Intent(Activity_Main.this,
							SortingRouteActivity.class);
					startActivity(Sorting_route_Act);
				}
			});

			btnGCS.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivity(new Intent(Activity_Main.this,
							Activity_Chon_So.class));
					Activity_Main.this.finish();
				}
			});

			btnBCTH.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CreateDialog_BCTH();
				}
			});
		} catch (Exception ex) {
			comm.ShowToast(Activity_Main.this.getApplicationContext(),
					"Error: " + ex.toString(), Toast.LENGTH_LONG);
		}
	}

	// ---------------------- XỬ LÝ HỘP THOẠI --------------------
	/**
	 * Tạo dialog báo cáo tổng hợp
	 * 
	 */
	private void CreateDialog_BCTH() {
		try {
			// custom dialog
			final Dialog dialog_bcth = new Dialog(this);

			dialog_bcth.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog_bcth.setContentView(R.layout.dialog_bcth);

			Button btnTongHop = (Button) dialog_bcth
					.findViewById(R.id.btnTongHop);
			// RadioGroup rgBC = (RadioGroup)
			// dialog_bcth.findViewById(R.id.rgBC);
			final RadioButton rTTGCS = (RadioButton) dialog_bcth
					.findViewById(R.id.rTTGCS);
			final RadioButton rTHSo = (RadioButton) dialog_bcth
					.findViewById(R.id.rTHSo);
			final RadioButton rTHCSBT = (RadioButton) dialog_bcth
					.findViewById(R.id.rTHCSBT);
			final RadioButton rTHTTBT = (RadioButton) dialog_bcth
					.findViewById(R.id.rTHTTBT);
			final RadioButton rDNTT = (RadioButton) dialog_bcth
					.findViewById(R.id.rDNTT);

			btnTongHop.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (rTTGCS.isChecked()) {
						CreateDialog_TTGCS();
					} else if (rTHSo.isChecked()) {
						CreateDialog_THSL();
					} else if (rTHCSBT.isChecked()) {
						CreateDialog_ChonSo(0);
					} else if (rTHTTBT.isChecked()) {
						CreateDialog_ChonSo(1);
					} else if (rDNTT.isChecked()) {
						CreateDialog_DNTT();
					}
					// dialog_bcth.dismiss();
				}
			});

			ImageButton btnDong = (ImageButton) dialog_bcth
					.findViewById(R.id.btnDongbcth);
			btnDong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog_bcth.dismiss();
				}
			});
			dialog_bcth.show();
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Không lấy được dữ liệu",
					Activity_Main.this);
		}
	}

	/**
	 * Gọi activity điện năng tổn thất
	 * 
	 */
	private void CreateDialog_DNTT() {
		Intent DNTT_Act = new Intent(Activity_Main.this, DNTTActivity.class);
		startActivity(DNTT_Act);
	}

	/**
	 * tạo dialog trạng thái ghi chỉ số
	 * 
	 */
	@SuppressLint("DefaultLocale")
	private void CreateDialog_TTGCS() {

		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_ttgcs);

		ArrayList<LinkedHashMap<String, String>> ListViewData = new ArrayList<LinkedHashMap<String, String>>();
		ImageButton ibtDongttgcs = (ImageButton) dialog
				.findViewById(R.id.ibtDongttgcs);
		ibtDongttgcs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		if (comm.checkDB()) {
			try {
				List<String> fileNames = new ArrayList<String>();
				Cursor c = connection.getAllDataSoInData();
				if (c.moveToFirst()) {
					do {
						fileNames.add(c.getString(0));// lấy tên sổ trong db
					} while (c.moveToNext());
				}
				ListViewData = LayTrangThaiGCS(fileNames, ListViewData);

				if (!ListViewData.isEmpty()) {
					ListAdapter adapter = new SimpleAdapter(
							this.getApplicationContext(), ListViewData,
							R.layout.listview_ttgcs,
							ConstantVariables.TTGCS_COLNAME,
							ConstantVariables.TTGCS_COLID);
					ListView lsvTTGCS = (ListView) dialog
							.findViewById(R.id.lsvTTGCS);
					lsvTTGCS.setAdapter(adapter);
				}
				dialog.show();

			} catch (Exception ex) {
				comm.msbox("Lỗi", "Không lấy được dữ liệu", Activity_Main.this);
			}
		}
	}

	/**
	 * Tạo dialog tổng hợp sản lượng
	 * 
	 */
	private void CreateDialog_THSL() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_thsl);

		ArrayList<LinkedHashMap<String, String>> ListViewData = new ArrayList<LinkedHashMap<String, String>>();
		ImageButton ibtDongttgcs = (ImageButton) dialog
				.findViewById(R.id.ibtDongthsl);
		ibtDongttgcs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		if (comm.checkDB()) {
			try {
				List<String> fileNames = new ArrayList<String>();
				Cursor c = connection.getAllDataSoInData();
				if (c.moveToFirst()) {
					do {
						fileNames.add(c.getString(0));// lấy tên sổ trong db
					} while (c.moveToNext());
				}
				ListViewData = LayTongSL_Quyen(fileNames, ListViewData);
				if (!ListViewData.isEmpty()) {
					ListAdapter adapter = new SimpleAdapter(
							this.getApplicationContext(), ListViewData,
							R.layout.listview_thsl,
							ConstantVariables.THSL_COLNAME,
							ConstantVariables.THSL_COLID);
					ListView lsvTTGCS = (ListView) dialog
							.findViewById(R.id.lsvTHSL);
					lsvTTGCS.setAdapter(adapter);
				}
				dialog.show();

			} catch (Exception ex) {
				comm.msbox("Lỗi", "Không lấy được dữ liệu", Activity_Main.this);
			}
		}
	}

	/**
	 * Tạo dialog tổng hợp sổ có chỉ số bất thường
	 * 
	 */
	private void CreateDialog_ChonSo(final int state) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_csbt);

		ArrayList<String> ListViewData = new ArrayList<String>();
		if (comm.checkDB()) {
			try {
				try {
					Cursor c = connection.getAllDataSoInData();
					if (c.moveToFirst()) {
						do {
							String fileName = c.getString(0);
							ListViewData.add(fileName);
						} while (c.moveToNext());
					}
				} catch (Exception ex) {
					// comm.ShowToast(Activity_Main.this.getApplicationContext(),
					// "Không lấy được dữ liệu", Toast.LENGTH_LONG,
					// Common.size(Activity_Main.this));
					comm.ShowToast(Activity_Main.this.getApplicationContext(),
							"Không lấy được dữ liệu", Toast.LENGTH_LONG);
				}

				if (!ListViewData.isEmpty()) {
					final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							this.getApplicationContext(),
							R.layout.listview_multichoice, ListViewData);
					lsvTTGCS = (ListView) dialog.findViewById(R.id.lsvChonSo);
					lsvTTGCS.setAdapter(adapter);

					Button btnTongHop_csbt = (Button) dialog
							.findViewById(R.id.btnTongHop_csbt);
					btnTongHop_csbt.setOnClickListener(new OnClickListener() {
						@SuppressLint("NewApi")
						@Override
						public void onClick(View v) {
							fileName.clear();
							if (lsvTTGCS.getCheckedItemCount() > 0) {
								for (int i = 0; i < lsvTTGCS.getCount(); i++) {
									SparseBooleanArray ck = lsvTTGCS
											.getCheckedItemPositions();
									if (ck.get(i)) {
										fileName.add(adapter.getItem(i));
									}
								}
								if (state == 0) {
									CreateDialog_CSBT(fileName);
								} else {
									CreateDialog_TTBT(fileName);
								}
							} else {
								// comm.ShowToast(Activity_Main.this.getApplicationContext(),
								// "Bạn phải chọn sổ", 2000,
								// Common.size(Activity_Main.this));
								comm.ShowToast(Activity_Main.this
										.getApplicationContext(),
										"Bạn phải chọn sổ", 2000);
							}
						}
					});

					ImageButton ibtDong_csbt = (ImageButton) dialog
							.findViewById(R.id.ibtDong_csbt);
					ibtDong_csbt.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
						}
					});
				}
				dialog.show();

			} catch (Exception ex) {
				comm.msbox("Lỗi", "Không lấy được dữ liệu", Activity_Main.this);
			}
		}
	}

	/**
	 * Tạo dialog tổng hợp sổ có tình trạng bất thường
	 * 
	 */
	private void CreateDialog_TTBT(List<String> fileName) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_ttbtct);
		final ArrayList<LinkedHashMap<String, String>> ListViewData = new ArrayList<LinkedHashMap<String, String>>();

		try {
			ListView lsvGCS = (ListView) dialog.findViewById(R.id.lsvGCS_ttbt);
			setListView2(lsvGCS, ListViewData, fileName);

			ImageButton btnDong_csbt = (ImageButton) dialog
					.findViewById(R.id.btnDong_ttbt);
			btnDong_csbt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					Activity_Main.this.fileName.clear();
				}
			});

			lsvGCS.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					try {
						fn = ListViewData.get(pos).get("TEN_SO");
						setList(fn);
						CreateDialog_ChiTiet(GetDataByID2(ListViewData.get(pos)
								.get("ID_SQLITE")), fn);
						// CreateDialog_ChiTiet(GetDataByID2(Integer.parseInt(ListViewData.get(pos).get("STT"))
						// - 1), fn);
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					}
				}
			});

			if (count_report == 0) {
				comm.msbox("Thông báo",
						"Không có công tơ nào có trạng thái bất thường",
						Activity_Main.this);
			} else {
				dialog.show();
				count_report = 0;
			}
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được dữ liệu", Activity_Main.this);
		}
	}

	/**
	 * Tạo dialog chi tiết
	 * 
	 * @param gcs_data
	 * @param fileName
	 */
	private void CreateDialog_ChiTiet(row_gcs gcs_data, String fileName) {
		try {
			// custom dialog
			final Dialog dialog = new Dialog(this);

			Float TongSLHC = 0F;
			Float TongSLVC = 0F;
			Float sl_thao = 0F;
			Float sl_moi = 0F;
			int SoTTBatThuong = 0;
			String loai_bcs;

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_gcs_chi_tiet);
			dialog.getWindow().setLayout(
					android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);

			ImageButton btnCloseDialog = (ImageButton) dialog
					.findViewById(R.id.btnClose);

			for (int i = 0; i < ListViewData2.size(); i++) {
				LinkedHashMap<String, String> row = ListViewData2.get(i);
				loai_bcs = row.get("LOAI_BCS").toUpperCase(Locale.ENGLISH);
				sl_thao = Float.parseFloat(row.get("SL_THAO"));
				sl_moi = Float.parseFloat(row.get("SL_MOI"));

				if (("BT,CD,TD,KT").contains(loai_bcs)) {
					TongSLHC += sl_moi + sl_thao;
				} else if (("VC").contains(loai_bcs)) {
					TongSLVC += sl_moi + sl_thao;
				} else if (("SG").contains(loai_bcs)) {

				}

				if (row.get("TTR_MOI") != null
						&& row.get("TTR_MOI").trim().length() > 0) {
					SoTTBatThuong++;
				}
			}

			btnCloseDialog.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			TextView tv_Title = (TextView) dialog.findViewById(R.id.tv_Title);
			TextView tv_TitleTongKet = (TextView) dialog
					.findViewById(R.id.tv_TitleTongKet);
			TextView tv_TongSLHC = (TextView) dialog
					.findViewById(R.id.tv_TongSLHC);
			TextView tv_TongSLVC = (TextView) dialog
					.findViewById(R.id.tv_TongSLVC);
			TextView tv_TiLeNhap = (TextView) dialog
					.findViewById(R.id.tv_TiLeNhap);
			TextView tv_TTBatThuong = (TextView) dialog
					.findViewById(R.id.tv_TTBatThuong);
			TextView tv_TenKH = (TextView) dialog.findViewById(R.id.tv_TenKH);
			TextView tv_MaKH = (TextView) dialog.findViewById(R.id.tv_MaKH);
			TextView tv_DiaChi = (TextView) dialog.findViewById(R.id.tv_DiaChi);
			TextView tv_MaDiemDo = (TextView) dialog
					.findViewById(R.id.tv_MaDiemDo);
			TextView tv_BCS = (TextView) dialog.findViewById(R.id.tv_BCS);
			TextView tv_MaGC = (TextView) dialog.findViewById(R.id.tv_MaGC);
			TextView tv_MaTram = (TextView) dialog.findViewById(R.id.tv_MaTram);
			TextView tv_MaNN = (TextView) dialog.findViewById(R.id.tv_MaNN);
			// TextView tv_MaCot = (TextView)
			// dialog.findViewById(R.id.tv_MaCot);
			TextView tv_SoHo = (TextView) dialog.findViewById(R.id.tv_SoHo);
			TextView tv_SoCTo = (TextView) dialog.findViewById(R.id.tv_SoCTo);
			TextView tv_HSN = (TextView) dialog.findViewById(R.id.tv_HSN);
			TextView tv_SLThao = (TextView) dialog.findViewById(R.id.tv_SLThao);
			TextView tv_CSCu = (TextView) dialog.findViewById(R.id.tv_CSCu);
			TextView tv_SLCu = (TextView) dialog.findViewById(R.id.tv_SLCu);
			TextView tv_CSMoi = (TextView) dialog.findViewById(R.id.tv_CSMoi);
			TextView tv_SLMoi = (TextView) dialog.findViewById(R.id.tv_SLMoi);
			TextView tv_ChuoiGia = (TextView) dialog
					.findViewById(R.id.tv_ChuoiGia);
			TextView tv_Ghichu = (TextView) dialog.findViewById(R.id.tv_Ghichu);
			CheckBox chkbchkbCSPK = (CheckBox) dialog
					.findViewById(R.id.chkbCSPK);
			TextView tv_Pmax = (TextView) dialog.findViewById(R.id.tv_Pmax);
			TextView tv_Ngay_Pmax = (TextView) dialog
					.findViewById(R.id.tv_Ngay_Pmax);

			// int idxEnd = fileName.lastIndexOf("_");
			// idxEnd = idxEnd == -1 ? fileName.lastIndexOf(".xml") : idxEnd;
			// String shortName = fileName.substring(0, idxEnd);
			tv_Title.setText("Quyển: " + fileName);
			tv_TitleTongKet.setText(fileName);
			tv_TitleTongKet.setVisibility(View.GONE);

			tv_TongSLHC.setText(TongSLHC + "");
			tv_TongSLVC.setText(TongSLVC + "");
			tv_TiLeNhap.setText(numRowFilled + "/" + ListViewData2.size());
			numRowFilled = 0;
			tv_TTBatThuong.setText(SoTTBatThuong + "/" + ListViewData2.size());

			tv_TenKH.setText(gcs_data.getTEN_KHANG());
			tv_MaKH.setText(gcs_data.getMA_KHANG());
			tv_DiaChi.setText(gcs_data.getDIA_CHI());
			tv_MaDiemDo.setText(gcs_data.getMA_DDO());
			tv_BCS.setText(gcs_data.getLOAI_BCS());
			tv_MaGC.setText(gcs_data.getMA_GC());
			tv_MaTram.setText(gcs_data.getMA_TRAM());
			tv_MaNN.setText(gcs_data.getMA_NN());
			// tv_MaCot.setText(gcs_data.getMA_COT());
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
			chkbchkbCSPK.setChecked(Boolean.parseBoolean(gcs_data
					.getKIMUA_CSPK()));
			tv_Pmax.setText(gcs_data.getPMAX());
			tv_Ngay_Pmax.setText(gcs_data.getNGAY_PMAX());

			dialog.show();
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Không xem đươc chi tiết",
					Activity_Main.this);
		}
	}

	/**
	 * Tạo dialog tổng hợp sổ có chỉ số bất thường
	 * 
	 */
	private void CreateDialog_CSBT(final List<String> fileName) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_csbtct);
		final ArrayList<LinkedHashMap<String, String>> ListViewData = new ArrayList<LinkedHashMap<String, String>>();

		try {
			ListView lsvGCS = (ListView) dialog.findViewById(R.id.lsvGCS_csbt);
			setListView(lsvGCS, ListViewData, fileName);

			ImageButton btnDong_csbt = (ImageButton) dialog
					.findViewById(R.id.btnDong_csbtct);
			btnDong_csbt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					Activity_Main.this.fileName.clear();
				}
			});

			lsvGCS.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					try {
						fn = ListViewData.get(pos).get("TEN_SO");
						setList(fn);// selectedRow.get("ID_SQLITE"))
						LinkedHashMap<String, String> map = ListViewData
								.get(pos);
						String ID = map.get("ID_SQLITE");
						CreateDialog_ChiTiet(GetDataByID2(ID), fn);
						// CreateDialog_ChiTiet(GetDataByID2(Integer.parseInt(ListViewData.get(pos).get("STT"))
						// - 1), fn);
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					}
				}
			});
			if (count_report == 0) {
				comm.msbox("Thông báo",
						"Không có công tơ nào có chỉ số bất thường",
						Activity_Main.this);
			} else {
				dialog.show();
				count_report = 0;
			}
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được dữ liệu", Activity_Main.this);
		}
	}

	/**
	 * Tạo dialog đăng nhập
	 * 
	 */
	private void CreateDialog_Login() {
		try {
			// custom dialog
			final Dialog dialog_login = new Dialog(this);

			dialog_login.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog_login.setContentView(R.layout.dialog_login);

			Button btnLoginPopup = (Button) dialog_login
					.findViewById(R.id.btnLogin);
			ImageButton ibtDong = (ImageButton) dialog_login
					.findViewById(R.id.ibtDong);
			ibtDong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog_login.dismiss();
				}
			});
			final TextView etxtUsername = (TextView) dialog_login
					.findViewById(R.id.etxtUsername);
			final TextView etxtPassword = (TextView) dialog_login
					.findViewById(R.id.etxtPassword);

			btnLoginPopup.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						String username = etxtUsername.getText().toString()
								.trim();
						String password = etxtPassword.getText().toString()
								.trim();
						if (username.length() > 0 && password.length() > 0) {
							// gọi service kiểm tra đăng nhập
							String result = (new AsyncCallWS())
									.WS_USER_LOGIN_CALL(username, password);
							// result = "SUCCESS";
							if (result.equals("SUCCESS")) {
								Common.MA_NVGCS = username;
								// common.MA_NVGCS =
								btnLogin.setVisibility(View.GONE);
								btnLogout.setVisibility(View.VISIBLE);
								btnTransferData.setVisibility(View.VISIBLE);
								comm.msbox("Đăng nhập", "Đăng nhập thành công",
										Activity_Main.this);

								if (comm.GetScreenSize(Activity_Main.this).x < 600)
									ivLogoApp.getLayoutParams().height = convertPXtoDP(150);
								else
									ivLogoApp.getLayoutParams().height = convertPXtoDP(300);

								dialog_login.dismiss();
							} else {
								Common.MA_NVGCS = null;
								btnLogin.setVisibility(View.VISIBLE);
								btnLogout.setVisibility(View.GONE);
								btnTransferData.setVisibility(View.GONE);
								comm.msbox("Đăng nhập", result,
										Activity_Main.this);
							}

						} else {
							Common.MA_NVGCS = null;
							btnLogin.setVisibility(View.VISIBLE);
							btnLogout.setVisibility(View.GONE);
							btnTransferData.setVisibility(View.GONE);
							comm.msbox("Đăng nhập",
									"Tên đăng nhập hoặc mật khẩu không hợp lệ",
									Activity_Main.this);
						}
					} catch (Exception ex) {
						comm.msbox("Đăng nhập", "Đăng nhập thất bại",
								Activity_Main.this);
					}
				}
			});

			dialog_login.show();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	/**
	 * Tạo dialog đồng bộ dữ liệu
	 * 
	 */
	private void CreateDialog_TransferData() {
		// custom dialog
		final Dialog dialog_transfer_data = new Dialog(this);

		dialog_transfer_data.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_transfer_data.setContentView(R.layout.dialog_transfer_data);

		lvChonSo = (ListView) dialog_transfer_data.findViewById(R.id.lvChonSo);

		final CheckBox chkCheckAll = (CheckBox) dialog_transfer_data
				.findViewById(R.id.chkCheckAll);

		btnGetData = (Button) dialog_transfer_data
				.findViewById(R.id.btnGetData);
		btnSendData = (Button) dialog_transfer_data
				.findViewById(R.id.btnSendData);

		rbSendData = (RadioButton) dialog_transfer_data
				.findViewById(R.id.rbSendData);
		rbGetData = (RadioButton) dialog_transfer_data
				.findViewById(R.id.rbGetData);

		btnSendData.setVisibility(View.GONE);
		btnGetData.setVisibility(View.GONE);

		btnGetData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// TODO lấy sổ được chọn từ listview và tải về từ sv
					// arr_so_server =
					// acws.WS_GET_TEN_FILE_OF_NV_CALL(common.MA_DVIQLY,
					// common.MA_NVGCS);
					File file_db = new File(Environment
							.getExternalStorageDirectory()
							+ Common.DBFolderPath + "/ESGCS.s3db");
					// kiểm tra db nếu chưa có thì tạo mới
					RecreateConnSqlite("ESGCS.s3db",
							Environment.getExternalStorageDirectory()
									+ Common.DBFolderPath);

					// Lấy danh sách các sổ được chọn lấy từ server
					final String[] array_selected = spnChonSo_GetSelectedItem(lvChonSo);

					if (file_db.exists()) {
						// lấy danh sách sổ đang có trên máy
						list_so = connection.GetAllSo();
						same_items = comm.GetSameItems(array_selected,
								(String[]) list_so.toArray(new String[0]));
					} else {
						list_so = new ArrayList<String>();
						same_items = null;
					}

					// kiểm tra sổ tải về có trùng với sổ trên máy ko
					// nếu có sổ trùng:
					if (same_items != null && same_items.size() > 0) {
						String str_list_so = "\n";
						for (String item : same_items) {
							str_list_so += item + "\n";
						}
						// -> hỏi xóa sổ cũ
						new AlertDialog.Builder(Activity_Main.this)
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle("Lấy sổ")
								.setMessage(
										"Sổ lấy về bị trùng:\n"
												+ str_list_so
												+ "\nBạn có muốn xóa sổ cũ trên máy để tải sổ mới về ?")
								.setPositiveButton("Đồng ý",
										new DialogInterface.OnClickListener() {
											@SuppressLint("SimpleDateFormat")
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {

												try {
													// ->Backup dữ liệu: copy db
													// hiện tại vào folder
													// backup
													comm.CreateBackup(null);

													// -> tải về
													String result = (new AsyncCallWS())
															.WS_DOWNLOAD_SO_GCS_CALL(
																	Common.MA_DVIQLY,
																	Common.MA_NVGCS,
																	array_selected,
																	"ESGCS.zip");

													// nếu ko có file tải về thì
													// thông báo tải về thất bại
													// File file = new
													// File(Environment.getExternalStorageDirectory()
													// +Common.TempFolderPath +
													// "/ESGCS.zip");
													File fileDST = new File(
															Environment
																	.getExternalStorageDirectory()
																	+ Common.PhotoFolderPath
																	+ "/ESGCS.zip");
													if (!fileDST.exists()) {
														throw new Exception(
																"Tải về thất bại");
													} else {
														unpackZip(
																"ESGCS.zip",
																Environment
																		.getExternalStorageDirectory()
																		+ Common.PhotoFolderPath
																		+ "/");
														fileDST.delete();
														File fPhotos = new File(
																Environment
																		.getExternalStorageDirectory()
																		+ Common.PhotoFolderPath);
														String[] sFile = fPhotos
																.list();
														for (String sF : sFile) {
															File fDBPhoto = new File(
																	Environment
																			.getExternalStorageDirectory()
																			+ Common.PhotoFolderPath
																			+ "/"
																			+ sF);
															if (fDBPhoto
																	.isFile()) {
																File fDBTemp = new File(
																		Environment
																				.getExternalStorageDirectory()
																				+ Common.TempFolderPath
																				+ "/ESGCS.s3db");
																comm.copy(
																		fDBPhoto,
																		fDBTemp);
																fDBPhoto.delete();
															}
														}
													}

													File file_db = new File(
															Environment
																	.getExternalStorageDirectory()
																	+ Common.DBFolderPath
																	+ "/ESGCS.s3db");
													if (file_db.exists()) {
														SQLiteConnection temp_conn = new SQLiteConnection(
																Activity_Main.this
																		.getApplicationContext(),
																"ESGCS.s3db",
																Environment
																		.getExternalStorageDirectory()
																		+ Common.TempFolderPath);

														connection
																.GetWritableDatabase();
														connection
																.BeginTransaction();

														// ->Xóa sổ bị trùng
														// trên máy
														connection
																.delete_array_so((String[]) same_items
																		.toArray(new String[0]));

														// -> Lấy dữ liệu trong
														// db vừa tải từ server
														// cho vào ArrayList
														ArrayList<LinkedHashMap<String, String>> list = temp_conn
																.ConvertDataFromSqliteToArrayList("SELECT * FROM GCS_CHISO_HHU"); // TODO
																																	// ĐỔI
																																	// LẠI
																																	// CONNECNT
																																	// TỚI
																																	// db
																																	// tạm

														// -> chèn sổ mới
														connection
																.insert_arraylist_to_db(
																		array_selected,
																		list,
																		Common.MA_DQL);

														connection
																.SetTransactionSuccessful();
													} else {
														// nếu trên máy chưa có
														// db thì copy db từ
														// folder temp luôn
														File file_src = new File(
																Environment
																		.getExternalStorageDirectory()
																		+ Common.TempFolderPath
																		+ "/ESGCS.s3db");
														comm.copy(file_src,
																file_db);
													}
													comm.msbox("Nhận sổ",
															result,
															Activity_Main.this);
												} catch (Exception e) {
													comm.msbox("Nhận sổ",
															"Nhận sổ thất bại",
															Activity_Main.this);
												} finally {
													connection.EndTransaction();
												}
											}

										}).setNegativeButton("Không", null)
								.show().getWindow().setGravity(Gravity.BOTTOM);
					}
					// ko trùng: tải về luôn
					else {
						String result = (new AsyncCallWS())
								.WS_DOWNLOAD_SO_GCS_CALL(Common.MA_DVIQLY,
										Common.MA_NVGCS, array_selected,
										"ESGCS.zip");
						// nếu ko có file tải về thì thông báo tải về thất bại
						File fileDST = new File(Environment
								.getExternalStorageDirectory()
								+ Common.PhotoFolderPath + "/ESGCS.zip");
						if (!fileDST.exists()) {
							throw new Exception("Tải về thất bại");
						} else {
							unpackZip("ESGCS.zip",
									Environment.getExternalStorageDirectory()
											+ Common.PhotoFolderPath + "/");
							fileDST.delete();
							File fPhotos = new File(Environment
									.getExternalStorageDirectory()
									+ Common.PhotoFolderPath);
							String[] sFile = fPhotos.list();
							for (String sF : sFile) {
								File fDBPhoto = new File(Environment
										.getExternalStorageDirectory()
										+ Common.PhotoFolderPath + "/" + sF);
								if (fDBPhoto.isFile()) {
									File fDBTemp = new File(Environment
											.getExternalStorageDirectory()
											+ Common.TempFolderPath
											+ "/ESGCS.s3db");
									comm.copy(fDBPhoto, fDBTemp);
									fDBPhoto.delete();
								}
							}
						}

						if (file_db.exists()) {
							SQLiteConnection temp_conn = new SQLiteConnection(
									Activity_Main.this.getApplicationContext(),
									"ESGCS.s3db", Environment
											.getExternalStorageDirectory()
											+ Common.TempFolderPath);

							try {
								connection.BeginTransaction();

								// -> Lấy dữ liệu trong db vừa tải từ server cho
								// vào ArrayList
								ArrayList<LinkedHashMap<String, String>> list = temp_conn
										.ConvertDataFromSqliteToArrayList("SELECT * FROM GCS_CHISO_HHU"); // TODO
																											// ĐỔI
																											// LẠI
																											// CONNECNT
																											// TỚI
																											// db
																											// tạm

								// -> chèn sổ mới
								connection.insert_arraylist_to_db(
										array_selected, list, Common.MA_DQL);

								connection.SetTransactionSuccessful();
							} catch (Exception e) {
								throw new Exception();
							} finally {
								connection.EndTransaction();
							}
						} else {
							// nếu trên máy chưa có db thì copy db từ folder
							// temp luôn
							File file_src = new File(Environment
									.getExternalStorageDirectory()
									+ Common.TempFolderPath + "/ESGCS.s3db");
							comm.copy(file_src, file_db);
						}
						comm.msbox("Nhận sổ", result, Activity_Main.this);
					}

					comm.LoadFolder2(Activity_Main.this.getApplicationContext());

				} catch (Exception ex) {
					comm.msbox("Nhận sổ", "Nhận sổ thất bại",
							Activity_Main.this);
				}
			}
		});

		btnSendData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// TODO lấy sổ được chọn từ listview và tải lên sv
					// AsyncCallWS acws = new AsyncCallWS();
					// String dirPath = sdCardPath + "/ESGCS/DB";
					// String[] arrFileTransfer =
					// acws.WS_GET_TEN_FILE_OF_NV_CALL(
					// common.MA_DVIQLY, common.MA_NVGCS); // danh sách sổ có
					// thể cập nhật lên server

					// Lấy danh sách các sổ được chọn đẩy lên server
					String[] array_selected = spnChonSo_GetSelectedItem(lvChonSo);
					ArrayList<String> arr_selected = new ArrayList<String>();
					for (String s : array_selected) {
						arr_selected.add(s.contains("_") ? s.split("_")[0]
								.toString() : s.replace(".", ",").split(",")[0]
								.toString());
					}
					comm.scanZipPhotoFiles(arr_selected);

					String result = (new AsyncCallWS()).WS_UPLOAD_SO_GCS_CALL(
							Common.MA_DVIQLY, Common.MA_NVGCS, array_selected);
					if (result.contains("ERROR")) {
						comm.msbox(
								"Gửi sổ",
								"Gửi dữ liệu thất bại.\nĐợi 5 phút sau ấn gửi dữ liệu lại.",
								Activity_Main.this);
					} else {
						// Xóa file
						File file = new File(Environment
								.getExternalStorageDirectory()
								+ "/ESGCS/ESGCS.zip");
						if (file.exists()) {
							file.delete();
						}
						File file2 = new File(Environment
								.getExternalStorageDirectory()
								+ "/ESGCS/Photo/");
						String[] listF = file2.list();
						for (int i = 0; i < listF.length; i++) {
							if (listF[i].contains(".zip")) {
								File file3 = new File(Environment
										.getExternalStorageDirectory()
										+ "/ESGCS/Photo/" + listF[i]);
								if (file3.exists()) {
									file3.delete();
								}
							}
						}
						comm.msbox("Gửi sổ", result, Activity_Main.this);
					}
				} catch (Exception ex) {
					comm.msbox("Đồng bộ sổ", "Gửi dữ liệu thất bại",
							Activity_Main.this);
				}
			}
		});

		rbSendData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					SyncAct(dialog_transfer_data, "SEND_DATA");
				} catch (Exception e) {
					// comm.ShowToast(Activity_Main.this.getApplicationContext(),"Lấy danh sách tải lên thất bại",
					// Toast.LENGTH_LONG, Common.size(Activity_Main.this));
					comm.ShowToast(Activity_Main.this.getApplicationContext(),
							"Lấy danh sách tải lên thất bại", Toast.LENGTH_LONG);
				}
			}
		});

		rbGetData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					SyncAct(dialog_transfer_data, "GET_DATA");
				} catch (Exception e) {
					// comm.ShowToast(Activity_Main.this.getApplicationContext(),"Lấy danh sách tải về thất bại",
					// Toast.LENGTH_LONG, Common.size(Activity_Main.this));
					comm.ShowToast(Activity_Main.this.getApplicationContext(),
							"Lấy danh sách tải về thất bại", Toast.LENGTH_LONG);
				}
			}
		});

		chkCheckAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int num_so = lvChonSo.getCount();
				if (chkCheckAll.isChecked()) {
					// chọn tất cả sổ trong list
					for (int i = 0; i <= num_so; i++) {
						lvChonSo.setItemChecked(i, true);
					}
				} else {
					// chọn tất cả sổ trong list
					for (int i = 0; i <= num_so; i++) {
						lvChonSo.setItemChecked(i, false);
					}
				}
			}
		});

		dialog_transfer_data.show();
	}

	public static void unzip(String zipFile, String location)
			throws IOException {
		try {
			File f = new File(location);
			if (!f.isDirectory()) {
				f.mkdirs();
			}
			ZipInputStream zin = new ZipInputStream(
					new FileInputStream(zipFile));
			try {
				ZipEntry ze = null;
				while ((ze = zin.getNextEntry()) != null) {
					String path = location + ze.getName();

					if (ze.isDirectory()) {
						File unzipFile = new File(path);
						if (!unzipFile.isDirectory()) {
							unzipFile.mkdirs();
						}
					} else {
						FileOutputStream fout = new FileOutputStream(path,
								false);
						try {
							for (int c = zin.read(); c != -1; c = zin.read()) {
								fout.write(c);
							}
							zin.closeEntry();
						} finally {
							fout.close();
						}
					}
				}
			} finally {
				zin.close();
			}
		} catch (Exception e) {

		}
	}

	private boolean unpackZip(String zipname, String path) {
		InputStream is;
		ZipInputStream zis;
		try {
			String filename;
			is = new FileInputStream(path + zipname);
			zis = new ZipInputStream(new BufferedInputStream(is));
			ZipEntry ze;
			byte[] buffer = new byte[1024];
			int count;

			while ((ze = zis.getNextEntry()) != null) {
				// zapis do souboru
				filename = ze.getName();

				// Need to create directories if not exists, or
				// it will generate an Exception...
				if (ze.isDirectory()) {
					File fmd = new File(path + filename);
					fmd.mkdirs();
					continue;
				}

				FileOutputStream fout = new FileOutputStream(path + filename);

				// cteni zipu a zapis
				while ((count = zis.read(buffer)) != -1) {
					fout.write(buffer, 0, count);
				}

				fout.close();
				zis.closeEntry();
			}

			zis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@SuppressLint("InlinedApi")
	private void createDialogLookup() {
		try {
			final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_lookup);
			dialog.getWindow().setLayout(
					android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			ImageButton btnDong = (ImageButton) dialog
					.findViewById(R.id.ibtDong);
			final TextView tvLookUp = (TextView) dialog
					.findViewById(R.id.tvLookUp);

			tvLookUp.append("- es_lookup : Mở bảng tra cứu mã code\n");
			tvLookUp.append("- es_version=hn : Chuyển sang phiên bản dùng chung\n");
			tvLookUp.append("- es_register=1 : Đăng ký sử dụng chương trình 1 năm\n");
			tvLookUp.append("- es_change_camera=aiball : Chuyển sang dùng camera AI-BALL\n");
			tvLookUp.append("- doi_ngay_pmax : Đổi định dạng ngày Pmax\n");

			btnDong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			dialog.show();
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Không mở được bảng tra cứu", this);
		}
	}

	private void changeFormatDataPmax() {
		try {
			Cursor c = connection.getAllDataNgayPmaxGCS();
			if (c.moveToFirst()) {
				do {
					String id = c.getString(0);
					String ngay_pmax = c.getString(1);
					if (ngay_pmax != null && !ngay_pmax.equals("")) {
						String[] arr_ngay = ngay_pmax.split(" ");
						if (arr_ngay.length == 2) {
							String date = arr_ngay[0].toString().trim();
							String time = arr_ngay[1].toString().trim();
							date = date.split("/")[1].toString() + "/"
									+ date.split("/")[0].toString() + "/"
									+ date.split("/")[2].toString();
							int h = Integer.parseInt(time.split(":")[0]
									.toString());
							int m = Integer.parseInt(time.split(":")[1]
									.toString());
							int s = Integer.parseInt(time.split(":")[2]
									.toString());
							time = (h < 12 ? "0" + h : "0" + (h - 12)) + ":"
									+ (m < 10 ? "0" + m : m) + ":"
									+ (s < 10 ? "0" + s : s)
									+ (h < 12 ? " AM" : " PM");
							String datetime = date + " " + time;
							connection.updateNgayPmax(id, datetime);
						}
					}
				} while (c.moveToNext());
			}
		} catch (Exception ex) {
			comm.msbox("LỖI", "Lỗi: " + ex.toString(), Activity_Main.this);
		}
	}

	// ---------------------- KHỞI TẠO DỮ LIỆU
	// ----------------------------------
	/**
	 * Lấy dữ liệu trạng thái gcs theo từng quyển
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	private ArrayList<LinkedHashMap<String, String>> LayTrangThaiGCS(
			List<String> fileNames,
			ArrayList<LinkedHashMap<String, String>> ListViewData) {

		if (comm.checkDB()) {
			for (String fileName : fileNames) {
				int so_diem_do = 0;
				int da_ghi = 0;
				Cursor c = connection.getAllDataGCS(fileName);
				so_diem_do = c.getCount();
				String shortName = fileName;
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				if (c.moveToFirst()) {
					do {
						if (comm.isSavedRow(c.getString(24), c.getString(23))) {
							da_ghi++;
						}
					} while (c.moveToNext());
				}
				map.put("Quyen", shortName);
				map.put("DiemDo", so_diem_do + "");
				map.put("DaGhi", da_ghi + "");
				map.put("ChuaGhi", (so_diem_do - da_ghi) + "");
				ListViewData.add(map);
			}
		}

		return ListViewData;
	}

	/**
	 * Lấy dữ liệu dòng theo id
	 * 
	 * @param id
	 * @return
	 */
	private row_gcs GetDataByID2(String id) {// selectedRow.get("ID_SQLITE"))
		EntityGCS e = connection.getDataByMAGC(id);
		row_gcs gcs = new row_gcs(e.getMA_NVGCS(), e.getMA_KHANG(),
				e.getMA_DDO(), e.getMA_DVIQLY(), e.getMA_GC(), e.getMA_QUYEN(),
				e.getMA_TRAM(), e.getBOCSO_ID(), e.getLOAI_BCS(),
				e.getLOAI_CS(), e.getTEN_KHANG(), e.getDIA_CHI(), e.getMA_NN(),
				e.getSO_HO(), e.getMA_CTO(), e.getSERY_CTO(), e.getHSN(),
				e.getCS_CU(), e.getTTR_CU(), e.getSL_CU(), e.getSL_TTIEP(),
				e.getNGAY_CU(), e.getCS_MOI(), e.getTTR_MOI(), e.getSL_MOI(),
				e.getCHUOI_GIA(), e.getKY(), e.getTHANG(), e.getNAM(),
				e.getNGAY_MOI(), e.getNGUOI_GCS(), e.getSL_THAO(),
				e.getKIMUA_CSPK(), e.getMA_COT(), e.getCGPVTHD(),
				e.getHTHUC_TBAO_DK(), e.getDTHOAI_SMS(), e.getEMAIL(),
				e.getTHOI_GIAN(), e.getX(), e.getY(), e.getSO_TIEN(),
				e.getHTHUC_TBAO_TH(), e.getTTHAI_DBO(), e.getDU_PHONG(),
				e.getGHICHU(), e.getTT_KHAC(), e.getID_SQLITE(),
				e.getSLUONG_1(), e.getSLUONG_2(), e.getSLUONG_3(),
				e.getSO_HOM(), e.getPMAX(), e.getPMAX());
		return gcs;
	}

	private void setList(String fileName) throws ParserConfigurationException {
		Float dtt = 0F;
		// Khoi tao Array chứa các row
		ListViewData2 = new ArrayList<LinkedHashMap<String, String>>();
		boolean isLicense = comm.CheckLicense(this, null);
		// boolean isLicense = true;
		if (comm.checkDB()) {
			try {
				Cursor c = connection.getAllDataGCS(fileName);
				int index = 0;
				if (c.moveToFirst()) {
					do {
						if (!isLicense && index == 50) {
							break;
						}
						index++;
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("STT", "" + index);
						for (int i = 1; i < ConstantVariables.COLARR.length - 8; i++) {
							map.put(ConstantVariables.COLARR[i], c.getString(i));
						}

						// tạo cột ĐTT
						dtt = (Float.parseFloat(map.get("SL_MOI")) + Float
								.parseFloat(map.get("SL_THAO")));
						map.put("DTT",
								String.format(Locale.ENGLISH, "%.2f", dtt));

						// đếm số bản ghi đã lưu
						// if (Float.parseFloat(map.get("CS_MOI")) != 0 &&
						// map.get("CS_MOI") != null ||
						// !map.get("CS_MOI").equals("") && map.get("CS_MOI") !=
						// null) {
						if (comm.isSavedRow(map.get("TTR_MOI"),
								map.get("CS_MOI"))) {
							numRowFilled++;
						}
						ListViewData2.add(map);
					} while (c.moveToNext());
				}
			} catch (Exception ex) {
				comm.msbox("Lỗi", "Không lấy được dữ liệu 6",
						Activity_Main.this);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setListView2(ListView lsvGCS,
			ArrayList<LinkedHashMap<String, String>> ListViewData,
			List<String> fileNames) {
		if (comm.checkDB()) {
			try {
				for (String fileName : fileNames) {
					int stt = 0;
					// String s = fileName.substring(fileName.length()-4);
					// if(!s.equals(".xml")){
					// fileName = fileName + ".xml";
					// }
					Cursor c = connection.getAllDataGCS(fileName);
					if (c.moveToFirst()) {
						do {
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							map.put("TEN_SO", fileName);
							for (int j = 1; j < ConstantVariables.COLARR.length - 8; j++) {
								map.put(ConstantVariables.COLARR[j],
										c.getString(j));
								// }
							}

							String TTR_MOI = map.get("TTR_MOI");

							if (!TTR_MOI.equals("")) {
								map.put("STT", (stt + 1) + "");
								stt++;
								ListViewData.add(map);
								count_report++;
							}
						} while (c.moveToNext());
					}
				}

				adapter2 = new THTTBTAdapter(this.getApplicationContext(),
						(ArrayList<LinkedHashMap<String, String>>) ListViewData
								.clone(), R.layout.listview_thttbt);
				lsvGCS.setAdapter(adapter2);

			} catch (Exception ex) {
				comm.msbox("Lỗi", "Không lấy được dữ liệu", this);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setListView(ListView lsvGCS,
			ArrayList<LinkedHashMap<String, String>> ListViewData,
			List<String> fileNames) {
		try {
			if (comm.checkDB()) {
				for (String fileName : fileNames) {
					int stt = 0;
					// String s = fileName.substring(fileName.length()-4);
					// if(!s.equals(".xml")){
					// fileName = fileName + ".xml";
					// }
					Cursor c = connection.getAllDataGCS(fileName);
					if (c.moveToFirst()) {
						do {
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							map.put("TEN_SO", fileName);
							for (int j = 1; j < ConstantVariables.COLARR.length - 8; j++) {
								map.put(ConstantVariables.COLARR[j],
										c.getString(j));
								// }
							}

							float SLChenhLech = 0;
							float SL_Cu = Float.parseFloat(map.get("SL_CU"));
							float SL_Moi = Float.parseFloat(map.get("SL_MOI"));
							float SL_Thao = Float
									.parseFloat(map.get("SL_THAO"));
							if (SL_Cu > 0) {
								if (SL_Moi + SL_Thao >= SL_Cu) {
									SLChenhLech = (float) (SL_Moi - SL_Cu + SL_Thao)
											/ (float) SL_Cu;
								} else {
									SLChenhLech = (float) (SL_Cu - SL_Moi - SL_Thao)
											/ (float) SL_Cu;
								}
							}
							// if (Float.parseFloat(map.get("CS_MOI")) != 0 ||
							// map.get("TTR_MOI") != null) {
							if (comm.isSavedRow(map.get("TTR_MOI"),
									map.get("CS_MOI"))) {
								if (Common.cfgInfo.isWarningEnable()) {
									if ((SL_Moi > SL_Cu && SLChenhLech >= ((float) Common.cfgInfo
											.getVuotDinhMuc() / 100))
											|| (SL_Moi < SL_Cu && SLChenhLech >= ((float) Common.cfgInfo
													.getDuoiDinhMuc() / 100))) {
										map.put("STT", (stt + 1) + "");
										stt++;
										ListViewData.add(map);
										count_report++;
									}
								} else if (Common.cfgInfo.isWarningEnable2()) {
									SLChenhLech *= (float) SL_Cu;
									if ((SL_Moi > SL_Cu && SLChenhLech >= (float) Common.cfgInfo
											.getVuotDinhMuc2())
											|| (SL_Moi < SL_Cu && SLChenhLech >= (float) Common.cfgInfo
													.getDuoiDinhMuc2())) {
										map.put("STT", (stt + 1) + "");
										stt++;
										ListViewData.add(map);
										count_report++;
									}
								}
							}
						} while (c.moveToNext());
					}
				}
				adapter = new THCSBTAdapter(this.getApplicationContext(),
						(ArrayList<LinkedHashMap<String, String>>) ListViewData
								.clone(), R.layout.listview_thcsbt);
				lsvGCS.setAdapter(adapter);
			}
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được dữ liệu", this);
		}
	}

	/**
	 * Lấy dữ liệu tổng sản lượng theo quyển
	 * 
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	private ArrayList<LinkedHashMap<String, String>> LayTongSL_Quyen(
			List<String> fileNames,
			ArrayList<LinkedHashMap<String, String>> ListViewData) {

		if (comm.checkDB()) {
			for (String fileName : fileNames) {
				int so_diem_do = 0;
				float slhc = 0;
				float slvc = 0;
				// String s = fileName.substring(fileName.length()-4);
				// if(!s.equals(".xml")){
				// fileName = fileName + ".xml";
				// }
				Cursor c = connection.getAllDataGCS(fileName);
				so_diem_do = c.getCount();
				// String shortName = Common.GetShortFileName(fileName);
				String shortName = fileName;
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				if (c.moveToFirst()) {
					do {
						float sl_moi = Float.parseFloat(c.getString(25));
						float sl_thao = Float.parseFloat(c.getString(32));
						;
						String bcs = c.getString(9);
						if (("VC").contains(bcs)) {
							slvc += sl_moi + sl_thao;
						} else if (("BT,CD,TD,KT").contains(bcs)) {
							slhc += sl_moi + sl_thao;
						}
					} while (c.moveToNext());
				}
				map.put("Quyen", shortName);
				map.put("DiemDo", so_diem_do + "");
				map.put("SLHC",
						String.format(Locale.ENGLISH, "%.0f",
								(Float.parseFloat(slhc + ""))));
				map.put("SLVC",
						String.format(Locale.ENGLISH, "%.0f",
								(Float.parseFloat(slvc + ""))));
				ListViewData.add(map);
			}
		}
		return ListViewData;
	}

	// ---------------------- TẠO VÀ KIỂM TRA CẤU HÌNH
	// ---------------------------
	/**
	 * Kiểm tra bản quyền chương trình
	 * 
	 */
	private void CheckLisence() {
		btnRegister = (Button) findViewById(R.id.btnRegister);

		CheckFileConfigExist();

		if (comm.CheckLicense(this.getApplicationContext(), null)) {
			btnRegister.setVisibility(View.GONE);
		} else {
			btnRegister.setVisibility(View.VISIBLE);
			// comm.ShowToast(Activity_Main.this.getApplicationContext(),
			// "Bạn đang sử dụng phiên bản thử nghiệm. Liên hệ Esolutions để đăng ký",
			// Toast.LENGTH_LONG, Common.size(Activity_Main.this));
			comm.ShowToast(
					Activity_Main.this.getApplicationContext(),
					"Bạn đang sử dụng phiên bản thử nghiệm. Liên hệ Esolutions để đăng ký",
					Toast.LENGTH_LONG);
		}
	}

	public void copyFileToSDCard(String fileFrom) {
		AssetManager is = this.getAssets();
		InputStream fis;
		try {

			fis = is.open(fileFrom);
			FileOutputStream fos;
			// if (!APP_FILE_PATH.exists()) {
			// APP_FILE_PATH.mkdirs();
			// }
			fos = new FileOutputStream(new File(
					Environment.getExternalStorageDirectory()
							+ Common.DBFolderPath, fileFrom));
			byte[] b = new byte[8];
			int i;
			while ((i = fis.read(b)) != -1) {
				fos.write(b, 0, i);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public boolean copyFile(String from, String to) {
		try {
			int byteread = 0;
			File oldfile = new File(from);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(from);
				FileOutputStream fs = new FileOutputStream(to);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Kiểm tra file cấu hình đã tồn tại chưa
	 * 
	 */
	private void CheckFileConfigExist() {
		try {
			try {
				String SDcardDirection = System.getenv("SECONDARY_STORAGE");
				String[] listSDcardDirection = SDcardDirection.split(":");
				for (String s : listSDcardDirection) {
					File storageDir = new File(s + "/ESGCS_BACKUP");
					if (!storageDir.exists()) {
						storageDir.mkdir();
					}
				}
			} catch (Exception ex) {
				ex.toString();
			}

			String filePath = Environment.getExternalStorageDirectory()
					+ Common.programPath + Common.cfgFileName;
			File programDirectory = new File(
					Environment.getExternalStorageDirectory()
							+ Common.programPath);
			if (!programDirectory.exists()) {
				programDirectory.mkdirs(); // tạo thư mục chứa dữ liệu
			}

			// kiểm tra folder DB đã tồn tại hay chưa
			File dbDir = new File(Environment.getExternalStorageDirectory()
					+ Common.programPath + "/DB");
			if (!dbDir.exists()) {
				dbDir.mkdirs();
			}

			// kiểm tra folder backup đã có chưa, nếu chưa thì tạo mới
			File backupDir = new File(Environment.getExternalStorageDirectory()
					+ Common.programPath + "/Backup");
			if (!backupDir.exists()) {
				backupDir.mkdirs();
			}

			// kiểm tra folder lộ trình đã có chưa, nếu chưa thì tạo
			// mới
			File tempDir = new File(Environment.getExternalStorageDirectory()
					+ Common.programPath + "/Temp");
			if (!tempDir.exists()) {
				tempDir.mkdirs();
			}

			// kiểm tra folder định dạng đã có chưa, nếu chưa thì tạo mới
			File format = new File(Environment.getExternalStorageDirectory()
					+ Common.programPath + "/Format");
			if (!format.exists()) {
				format.mkdirs();
			}

			// kiểm tra folder định dạng đã có chưa, nếu chưa thì tạo mới
			File photo = new File(Environment.getExternalStorageDirectory()
					+ Common.programPath + "/Photo");
			if (!photo.exists()) {
				photo.mkdirs();
			}

			// kiểm tra folder định dạng đã có chưa, nếu chưa thì tạo mới
			File tessdata = new File(Environment.getExternalStorageDirectory()
					+ Common.programPath + "/Format/tessdata");
			if (!tessdata.exists()) {
				tessdata.mkdirs();
			}

			// //kiểm tra file test
			File tess = new File(Environment.getExternalStorageDirectory()
					+ Common.programPath + "/Format/tessdata/test");
			if (!tess.exists()) {
				tess.mkdirs();
			}

			copyDataFormat();

			File cfgFile = new File(filePath);
			if (cfgFile.exists()) {
				Common.cfgInfo = comm.GetFileConfig();
				if (Common.cfgInfo == null) {
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
			comm.msbox("Thông báo",
					"File cấu hình không đúng: \n" + ex.toString(),
					Activity_Main.this);
		}
	}

	/**
	 * Lấy lại thông tin file cấu hình
	 * 
	 */
	public void createFileConfig(ConfigInfo cfg) {
		try {
			ConfigInfo cfgInfo = new ConfigInfo(cfg.isWarningEnable3(),
					cfg.isWarningEnable(), cfg.isWarningEnable2(),
					cfg.getVuotDinhMuc(), cfg.getDuoiDinhMuc(),
					cfg.getVuotDinhMuc2(), cfg.getDuoiDinhMuc2(),
					cfg.isAutosaveEnable(), cfg.getAutosaveMinutes(),
					cfg.getIP_SERVICE(), null, cfg.getColumnsOrder(),
					cfg.getDinhDang(), cfg.getDVIQLY());

			String filePath = Environment.getExternalStorageDirectory()
					+ Common.programPath + Common.cfgFileName;
			File cfgFile = new File(filePath);

			comm.CreateFileConfig(cfgInfo, cfgFile, null);
		} catch (Exception ex) {

		}
	}

	/**
	 * Copy file nhận dạng vào máy
	 * 
	 */
	private void copyDataFormat() {
		try {
			if (!(new File(Common.TessFolderPath + ConstantVariables.LANG
					+ ".traineddata")).exists()) {
				try {
					AssetManager assetManager = getAssets();
					InputStream in = assetManager.open("tessdata/"
							+ ConstantVariables.LANG + ".traineddata");
					File outFile = new File(
							Environment.getExternalStorageDirectory(),
							"/ESGCS/Format/tessdata/" + ConstantVariables.LANG
									+ ".traineddata");
					if (!outFile.exists()) {
						OutputStream out = new FileOutputStream(outFile);
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						in.close();
						out.close();
						comm.LoadFolder2(Activity_Main.this
								.getApplicationContext());
					}
				} catch (IOException e) {
					e.toString();
				}
			}
		} catch (Exception ex) {
			ex.toString();
		}
	}

	// --------------------- CÁC HÀM XỬ LÝ KHÁC
	// ---------------------------------
	public int convertPXtoDP(int size) {
		Resources resources = Activity_Main.this.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return (int) (size * (metrics.densityDpi / 160f));
	}

	/***
	 * Tạo lại instance SQLiteConnection nếu chưa có
	 * 
	 * @param db_name
	 * @param db_path
	 */
	private void RecreateConnSqlite(String db_name, String db_path) {
		if (connection != null) {
			connection.close();
		}
		connection = null;
		connection = new SQLiteConnection(
				Activity_Main.this.getApplicationContext(), db_name, db_path);
	}

	/**
	 * Lấy sổ được chọn trong danh sách
	 * 
	 * @param lvChonSo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String[] spnChonSo_GetSelectedItem(ListView lvChonSo) {
		List<String> list_selected = new ArrayList<String>();
		ArrayAdapter<String> dataAdapter = (ArrayAdapter<String>) lvChonSo
				.getAdapter();
		SparseBooleanArray ck = lvChonSo.getCheckedItemPositions();
		for (int i = 0; i < lvChonSo.getCount(); i++) {
			if (ck.get(i)) {
				list_selected.add(dataAdapter.getItem(i));
			}
		}
		return list_selected.toArray(new String[0]);
	}

	/**
	 * Ẩn hiện view sau khi gửi/nhận dữ liệu
	 * 
	 * @param dialog_transfer_data
	 * @param Action
	 */
	private void SyncAct(Dialog dialog_transfer_data, String Action) {
		CheckBox chkCheckAll = (CheckBox) dialog_transfer_data
				.findViewById(R.id.chkCheckAll);
		chkCheckAll.setChecked(false);

		RecreateConnSqlite("ESGCS.s3db",
				Environment.getExternalStorageDirectory() + Common.DBFolderPath);

		if (Action.contains("SEND_DATA")) {
			rbGetData.setChecked(false);
			rbSendData.setChecked(true);
			btnSendData.setVisibility(View.VISIBLE);
			btnGetData.setVisibility(View.GONE);
			spnChonSo_GetData(lvChonSo, "SEND_DATA");
		} else {// (rbGetData.isChecked())
			rbGetData.setChecked(true);
			rbSendData.setChecked(false);
			btnSendData.setVisibility(View.GONE);
			btnGetData.setVisibility(View.VISIBLE);
			spnChonSo_GetData(lvChonSo, "GET_DATA");
		}

		// load lại folder chứa file để hiện thị file bị ẩn
		comm.LoadFolder2(Activity_Main.this.getApplicationContext());
	}

	/**
	 * Lấy dữ liệu đổ vào spinner chọn sổ
	 */
	private void spnChonSo_GetData(ListView lvChonSo, String Action) {
		arr_so_server = (new AsyncCallWS()).WS_GET_TEN_FILE_OF_NV_CALL(
				Common.MA_DVIQLY, Common.MA_NVGCS);
		File file_db = new File(Environment.getExternalStorageDirectory()
				+ Common.DBFolderPath + "/ESGCS.s3db");
		List<String> lvdata = null;
		if (file_db.exists()) {
			// lấy danh sách sổ đang có trên máy
			list_so = connection.GetAllSo();
			same_items = comm.GetSameItems(arr_so_server,
					(String[]) list_so.toArray(new String[0]));
		} else {
			list_so = new ArrayList<String>();
			same_items = null;
		}

		if (Action.contains("GET_DATA")) {
			lvdata = comm.ConvertArrayString2List(arr_so_server);
		} else {
			lvdata = same_items;
		}

		java.util.Collections.sort(lvdata);
		try {
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
					Activity_Main.this.getApplicationContext(),
					R.layout.listview_multichoice, lvdata);
			lvChonSo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			lvChonSo.setAdapter(dataAdapter);

		} catch (Exception ex) {
			comm.msbox("Thông báo", "Chưa có dữ liệu trong thư mục",
					Activity_Main.this);
		}
	}

	@SuppressLint("InlinedApi")
	private void createDialogCMD() {
		try {
			final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_hack);
			dialog.getWindow().setLayout(
					android.app.ActionBar.LayoutParams.MATCH_PARENT,
					android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			ImageButton btnDong = (ImageButton) dialog
					.findViewById(R.id.ibtDong);
			final EditText etGhiChu = (EditText) dialog
					.findViewById(R.id.etGhiChu);
			Button btnThem = (Button) dialog.findViewById(R.id.btnThem);
			Button btnXoa = (Button) dialog.findViewById(R.id.btnXoa);

			btnDong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			btnThem.setOnClickListener(new OnClickListener() {

				@SuppressLint("DefaultLocale")
				@Override
				public void onClick(View v) {
					try {
						String code = etGhiChu.getText().toString();
						if (code.contains("=")) {// mã có giá trị
							// Đổi phiên bản
							if (code.split("=")[0]
									.trim()
									.toString()
									.toLowerCase()
									.equals(ConstantVariables.CODE_CHANGE_VERSION
											.toLowerCase())) {
								if (code.split("=")[1].trim().toString()
										.toLowerCase().equals("hn")) {
									Common.PHIEN_BAN = "HN";
									comm.ShowToast(
											Activity_Main.this
													.getApplicationContext(),
											"Bạn vừa chuyển sang phiên bản Hà Nội",
											2000);
									Activity_Main.this.finish();
									startActivity(new Intent(
											Activity_Main.this,
											Activity_Main.class));
								} else if (code.split("=")[1].trim().toString()
										.toLowerCase().equals("lc")) {
									Common.PHIEN_BAN = "LC";
									comm.ShowToast(
											Activity_Main.this
													.getApplicationContext(),
											"Bạn vừa chuyển sang phiên bản cho Lai Châu",
											2000);
									Activity_Main.this.finish();
									startActivity(new Intent(
											Activity_Main.this,
											Activity_Main.class));
								} else if (code.split("=")[1].trim().toString()
										.toLowerCase().equals("bn")) {
									Common.PHIEN_BAN = "BN";
									comm.ShowToast(
											Activity_Main.this
													.getApplicationContext(),
											"Bạn vừa chuyển sang phiên bản cho Bắc Ninh",
											2000);
									Activity_Main.this.finish();
									startActivity(new Intent(
											Activity_Main.this,
											Activity_Main.class));
								} else if (code.split("=")[1].trim().toString()
										.toLowerCase().equals("sl")) {
									Common.PHIEN_BAN = "SL";
									comm.ShowToast(
											Activity_Main.this
													.getApplicationContext(),
											"Bạn vừa chuyển sang phiên bản cho Sơn La",
											2000);
									Activity_Main.this.finish();
									startActivity(new Intent(
											Activity_Main.this,
											Activity_Main.class));
								}
								// Đổi camera
							} else if (code.split("=")[0]
									.trim()
									.toString()
									.toLowerCase()
									.equals(ConstantVariables.CODE_CHANGE_CAMERA
											.toLowerCase())) {
								if (code.split("=")[1].trim().toString()
										.toLowerCase().equals("aiball")) {
									Common.CAMERA_TYPE = "AIBALL";
									comm.ShowToast(
											Activity_Main.this
													.getApplicationContext(),
											"Bạn vừa chuyển sang phiên bản dùng camera AI-BALL",
											Toast.LENGTH_LONG);
								} else if (code.split("=")[1].trim().toString()
										.toLowerCase().equals("qx10")) {
									Common.CAMERA_TYPE = "QX10";
									comm.ShowToast(
											Activity_Main.this
													.getApplicationContext(),
											"Bạn vừa chuyển sang phiên bản dùng camera QX10",
											Toast.LENGTH_LONG);
								} else if (code.split("=")[1].trim().toString()
										.toLowerCase().equals("as20")) {
									Common.CAMERA_TYPE = "AS20";
									comm.ShowToast(
											Activity_Main.this
													.getApplicationContext(),
											"Bạn vừa chuyển sang phiên bản dùng camera AS20",
											Toast.LENGTH_LONG);
								}
								// Đăng ký chương trình
							} else if (code.split("=")[0]
									.trim()
									.toString()
									.toLowerCase()
									.equals(ConstantVariables.CODE_REGISTER
											.toLowerCase())
									|| code.split("=")[0]
											.trim()
											.toString()
											.toLowerCase()
											.equals(ConstantVariables.CODE_REGISTER2
													.toLowerCase())) {
								int nam = Integer.parseInt(code.split("=")[1]
										.trim().toString().toLowerCase());
								Calendar cal = Calendar.getInstance(TimeZone
										.getDefault());
								Date currentLocalTime = cal.getTime();
								DateFormat date = new SimpleDateFormat(
										"dd/MM/yyyy");
								date.setTimeZone(TimeZone.getDefault());
								String localTime = date
										.format(currentLocalTime);

								String MA_DVI = "PD";
								String NGAY = localTime.split("/")[0]
										+ "/"
										+ localTime.split("/")[1]
										+ "/"
										+ String.valueOf(Integer
												.parseInt(localTime.split("/")[2]
														.toString())
												+ nam);
								String IMEI = comm.GetIMEI(Activity_Main.this);
								String KEY = MA_DVI + "_" + NGAY + "_" + IMEI;
								security sc = new security();
								String key = sc.encrypt_Base64(KEY);

								Common.cfgInfo = comm.GetFileConfig();
								boolean warningEnable3 = Common.cfgInfo
										.isWarningEnable3();
								boolean warningEnable = Common.cfgInfo
										.isWarningEnable();
								boolean warningEnable2 = Common.cfgInfo
										.isWarningEnable2();
								int vuotDinhMuc = Common.cfgInfo
										.getVuotDinhMuc();
								int duoiDinhMuc = Common.cfgInfo
										.getDuoiDinhMuc();
								int vuotDinhMuc2 = Common.cfgInfo
										.getVuotDinhMuc2();
								int duoiDinhMuc2 = Common.cfgInfo
										.getDuoiDinhMuc2();
								boolean autosaveEnable = Common.cfgInfo
										.isAutosaveEnable();
								int autosaveMinutes = Common.cfgInfo
										.getAutosaveMinutes();
								String ColumnsOrder = Common.cfgInfo
										.getColumnsOrder();
								String IP_SERVICE = Common.cfgInfo
										.getIP_SERVICE();
								boolean DinhDang = Common.cfgInfo.getDinhDang();
								String Dien_luc = Common.cfgInfo.getDVIQLY();

								ConfigInfo cfgInfo = new ConfigInfo(
										warningEnable3, warningEnable,
										warningEnable2, vuotDinhMuc,
										duoiDinhMuc, vuotDinhMuc2,
										duoiDinhMuc2, autosaveEnable,
										autosaveMinutes, IP_SERVICE, key,
										ColumnsOrder, DinhDang, Dien_luc);

								String filePath = Environment
										.getExternalStorageDirectory()
										+ Common.programPath
										+ Common.cfgFileName;
								File cfgFile = new File(filePath);

								if (comm.CreateFileConfig(cfgInfo, cfgFile, key)) {
									Activity_Main.this.finish();
									startActivity(new Intent(
											Activity_Main.this,
											Activity_Main.class));
									comm.ShowToast(Activity_Main.this
											.getApplicationContext(),
											"Đăng ký thành công",
											Toast.LENGTH_LONG);
								}
							} else if (code.split("=")[0]
									.trim()
									.toString()
									.toLowerCase()
									.equals(ConstantVariables.CODE_CHANGE_DATE
											.toLowerCase())) {
								File file_photo = new File(Environment
										.getExternalStorageDirectory()
										+ "/ESGCS/Photo");
								String[] allFilesPhoto = file_photo.list();
								for (String photo : allFilesPhoto) {
									Bitmap bm = createBitMap(Environment
											.getExternalStorageDirectory()
											+ "/ESGCS/Photo/" + photo);
									if (bm != null) {
										Bitmap resized = Bitmap
												.createScaledBitmap(bm, 640,
														585, true);
										bm = drawNgayToBitmap(
												Activity_Main.this
														.getApplicationContext(),
												resized, code.split("=")[1]
														.trim().toString());
										if (saveImageToFile(bm, photo)) {
											comm.scanFile(
													Activity_Main.this,
													new String[] { Environment
															.getExternalStorageDirectory()
															+ "/ESGCS/Photo/"
															+ photo });
										}
									}
								}
							}
						} else {// mã không có giá trị
							if (code.equals(ConstantVariables.CODE_LOOKUP)) {
								// show bảng tra cứu
								createDialogLookup();
							}
							if (code.equals(ConstantVariables.CODE_CHANGE_NGAY_PMAX)) {
								// Đổi định dạng ngày Pmax
								changeFormatDataPmax();
							}
							if (code.equals(ConstantVariables.CODE_LOAD_FOLDER)) {
								// Load toàn bộ folder
								comm.LoadFolder2(Activity_Main.this
										.getApplicationContext());
								comm.ShowToast(Activity_Main.this
										.getApplicationContext(),
										"Đã quét tất cả các thư mục",
										Toast.LENGTH_SHORT);
							}
						}
						dialog.dismiss();
					} catch (Exception ex) {
						dialog.dismiss();
					}
				}
			});

			btnXoa.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					etGhiChu.setText("");
				}
			});

			dialog.show();
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Không có hình ảnh", this);
		}
	}

	private void turnGPSOn() {
		try {
			@SuppressWarnings("deprecation")
			String provider = Settings.Secure.getString(getContentResolver(),
					Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

			if (!provider.contains("gps")) { // if gps is disabled
				final Intent poke = new Intent();
				poke.setClassName("com.android.settings",
						"com.android.settings.widget.SettingsAppWidgetProvider");
				poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
				poke.setData(Uri.parse("3"));
				this.sendBroadcast(poke);
			}
		} catch (Exception ex) {
			Log.e("error", ex.toString());
		}
	}

	public void turnGPSOn2() {
		try {
			Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
			intent.putExtra("enabled", true);
			this.sendBroadcast(intent);

			@SuppressWarnings("deprecation")
			String provider = Settings.Secure.getString(getContentResolver(),
					Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			if (!provider.contains("gps")) { // if gps is disabled
				final Intent poke = new Intent();
				poke.setClassName("com.android.settings",
						"com.android.settings.widget.SettingsAppWidgetProvider");
				poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
				poke.setData(Uri.parse("3"));
				this.sendBroadcast(poke);

			}
		} catch (Exception ex) {
			ex.toString();
		}
	}

	// private boolean canToggleGPS() {
	// PackageManager pacman = getPackageManager();
	// PackageInfo pacInfo = null;
	//
	// try {
	// pacInfo = pacman.getPackageInfo("com.android.settings",
	// PackageManager.GET_RECEIVERS);
	// } catch (NameNotFoundException e) {
	// return false; //package not found
	// }
	//
	// if(pacInfo != null){
	// for(ActivityInfo actInfo : pacInfo.receivers){
	// //test if recevier is exported. if so, we can toggle GPS.
	// if(actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider")
	// && actInfo.exported){
	// return true;
	// }
	// }
	// }
	//
	// return false; //default
	// }

	@SuppressWarnings("deprecation")
	public void turnGPSOn(Context cxt) {
		String provider = Settings.Secure.getString(cxt.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!provider.contains("gps")
				&& Build.VERSION.SDK_INT < VERSION_CODES.KITKAT) {
			Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
			intent.putExtra("enabled", true);
			cxt.sendBroadcast(intent);
		}

		if (!provider.contains("gps")) { // if gps is disabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			cxt.sendBroadcast(poke);
		}
	}

	public Bitmap drawNgayToBitmap(Context gContext, Bitmap bitmap,
			String currentDateandTime) {
		// Resources resources = gContext.getResources();
		// float scale = resources.getDisplayMetrics().density;

		Bitmap.Config bitmapConfig = bitmap.getConfig();
		if (bitmapConfig == null) {
			bitmapConfig = Bitmap.Config.ARGB_8888;
		}
		bitmap = bitmap.copy(bitmapConfig, true);

		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setTextSize(bitmap.getHeight() / 24);

		Paint paint_rec = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_rec.setColor(Color.BLACK);

		Rect bounds_width = new Rect();
		paint.getTextBounds(currentDateandTime, 0, currentDateandTime.length(),
				bounds_width);

		// Rect bounds = new Rect();
		// paint.getTextBounds(currentDateandTime, 0,
		// currentDateandTime.length(), bounds);
		// int x = 10;
		// int y = bitmap.getHeight() - bitmap.getHeight()/9;
		// canvas.drawRect(0, bitmap.getHeight() - bitmap.getHeight()/9 + 2,
		// 5*bitmap.getWidth()/22, bitmap.getHeight() - bitmap.getHeight()/9 -
		// bitmap.getHeight()/12, paint_rec);
		// canvas.drawText(currentDateandTime, x, y, paint);

		Rect bounds = new Rect();
		paint.getTextBounds(currentDateandTime, 0, currentDateandTime.length(),
				bounds);
		int x = 10;
		int y = bitmap.getHeight() - 25 - 2 * bounds.height();
		canvas.drawRect(0, bitmap.getHeight() - 3 * bounds.height() - 26,
				bounds.width() + 15, bitmap.getHeight() - 2 * bounds.height()
						- 15, paint_rec);
		canvas.drawText(currentDateandTime, x, y, paint);

		return bitmap;
	}

	public Bitmap createBitMap(String path) {
		File file = new File(path);
		Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		return bitmap;
	}

	private boolean saveImageToFile(Bitmap bmp_result, String fileName) {
		try {
			if (bmp_result != null) {
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				bmp_result.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
				File f = new File(Environment.getExternalStorageDirectory()
						+ "/ESGCS/Photo/" + fileName);
				if (f.exists()) {
					f.delete();
				}
				f.createNewFile();
				FileOutputStream fo = new FileOutputStream(f);
				fo.write(bytes.toByteArray());
				fo.close();
				comm.scanFile(
						Activity_Main.this,
						new String[] { Environment
								.getExternalStorageDirectory()
								+ "/ESGCS/Photo/" + fileName });
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			// comm.ShowToast(Activity_Main.this.getApplicationContext(),
			// "Không chụp được hình ảnh", Toast.LENGTH_LONG,
			// Common.size(Activity_Main.this));
			comm.ShowToast(Activity_Main.this.getApplicationContext(),
					"Không chụp được hình ảnh", Toast.LENGTH_LONG);
			return false;
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

	@Override
	public void updateProgressNum(int progressNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProgressText(UIStringGenerator progressText) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDebugMessages(UIStringGenerator debugMessages) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePath(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStartButtonEnabled(boolean startButtonEnabled) {
		// TODO Auto-generated method stub

	}

	@Override
	public void signalFinished() {
		// TODO Auto-generated method stub

	}
}
