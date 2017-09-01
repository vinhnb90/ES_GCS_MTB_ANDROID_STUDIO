package esolutions.com.gcs_svn_old.com.camera.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.ConfigInfo;
import esolutions.com.gcs_svn_old.com.camera.utility.ConstantVariables;

public class Activity_Config extends RootActivity {

	TextView tv_valVuotMuc;
	TextView tv_valDuoiDinhMuc;
	TextView tv_valTGTuDongLuu;
	SeekBar sbVuotMuc;
	SeekBar sbDuoiDinhMuc;
	SeekBar sbTGTuDongLuu;
	CheckBox chkbCanhBaoChenhLechSL, chkbCanhBaoChenhLechSL2, chkbCanhBaoDungNhieu;
	CheckBox chkbTuDongLuu, ckDinhDang;
	EditText etxt_URL_WS, etVuotMuc, etDuoiDinhMuc, etDvi_qly, etKey;
	Button btShow;
	LinearLayout lnCanhBao;

	String license = "";
	int k = 5;
	int size;

	Common comm = new Common();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_cau_hinh);
			// Show the Up button in the action bar.
			// setupActionBar();
			getActionBar().hide();
			Point point = comm.GetScreenSize(Activity_Config.this);
			size = point.x - 65;
	
			tv_valVuotMuc = (TextView) findViewById(R.id.tv_valVuotMuc);
			tv_valDuoiDinhMuc = (TextView) findViewById(R.id.tv_valDuoiDinhMuc);
			tv_valTGTuDongLuu = (TextView) findViewById(R.id.tv_valTGTuDongLuu);
			sbVuotMuc = (SeekBar) findViewById(R.id.sbVuotMuc);
			sbDuoiDinhMuc = (SeekBar) findViewById(R.id.sbDuoiDinhMuc);
			etVuotMuc = (EditText) findViewById(R.id.etVuotMuc);
			etDuoiDinhMuc = (EditText) findViewById(R.id.etDuoiDinhMuc);
			sbTGTuDongLuu = (SeekBar) findViewById(R.id.sbTuDongLuu);
			chkbCanhBaoChenhLechSL = (CheckBox) findViewById(R.id.chkbCanhBaoChenhLechSL);
			chkbCanhBaoChenhLechSL2 = (CheckBox) findViewById(R.id.chkbCanhBaoChenhLechSL2);
			chkbCanhBaoDungNhieu = (CheckBox) findViewById(R.id.chkbCanhBaoDungNhieu);
			chkbTuDongLuu = (CheckBox) findViewById(R.id.chkbTuDongLuu);
			ckDinhDang = (CheckBox) findViewById(R.id.ckDinhDang);
			etxt_URL_WS = (EditText) findViewById(R.id.etxt_URL_WS);
			etDvi_qly = (EditText) findViewById(R.id.etxt_DienLuc);
			etKey = (EditText) findViewById(R.id.etKey);
			btShow = (Button) findViewById(R.id.btShow);
			lnCanhBao = (LinearLayout) findViewById(R.id.lnCanhBao);
			
			if(Common.PHIEN_BAN.equals("BN")){
				lnCanhBao.setVisibility(View.GONE);
				btShow.setVisibility(View.VISIBLE);
				etKey.setVisibility(View.VISIBLE);
			}
			
			btShow.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String KEY = Common.MD5(etKey.getText().toString().trim());
					if(KEY.equals(ConstantVariables.KEY) || etKey.getText().toString().trim().equals(ConstantVariables.KEY)){
						lnCanhBao.setVisibility(View.VISIBLE);
						btShow.setVisibility(View.GONE);
						etKey.setVisibility(View.GONE);
					}
				}
			});
			
			// get value config
			tv_valVuotMuc.setText(String.valueOf((Common.cfgInfo.getVuotDinhMuc()) + "%"));
			tv_valDuoiDinhMuc.setText(String.valueOf((Common.cfgInfo.getDuoiDinhMuc()) + "%"));
			tv_valTGTuDongLuu.setText(String.valueOf(((Common.cfgInfo.getAutosaveMinutes())) + "'"));
			
			sbVuotMuc.setEnabled(Common.cfgInfo.isWarningEnable());
			etVuotMuc.setEnabled(Common.cfgInfo.isWarningEnable2());
			sbVuotMuc.setProgress((Common.cfgInfo.getVuotDinhMuc()) / k - 1);
			etVuotMuc.setText(String.valueOf(Common.cfgInfo.getVuotDinhMuc2()));
			sbDuoiDinhMuc.setEnabled(Common.cfgInfo.isWarningEnable());
			etDuoiDinhMuc.setEnabled(Common.cfgInfo.isWarningEnable2());
			sbDuoiDinhMuc.setProgress((Common.cfgInfo.getDuoiDinhMuc()) / k - 1);
			etDuoiDinhMuc.setText(String.valueOf(Common.cfgInfo.getDuoiDinhMuc2()));
			sbTGTuDongLuu.setEnabled(Common.cfgInfo.isAutosaveEnable());
			sbTGTuDongLuu.setProgress(Common.cfgInfo.getAutosaveMinutes() - 10);
			chkbCanhBaoDungNhieu.setChecked(Common.cfgInfo.isWarningEnable3());
			chkbCanhBaoChenhLechSL.setChecked(Common.cfgInfo.isWarningEnable());
			chkbCanhBaoChenhLechSL2.setChecked(Common.cfgInfo.isWarningEnable2());
			chkbTuDongLuu.setChecked(Common.cfgInfo.isAutosaveEnable());
			etxt_URL_WS.setText(Common.cfgInfo.getIP_SERVICE());
			ckDinhDang.setChecked(Common.cfgInfo.getDinhDang());
			etDvi_qly.setText(Common.cfgInfo.getDVIQLY());
			sbVuotMuc.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					tv_valVuotMuc.setText(((progress + 1) * k) + "%");
				}
	
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}
	
				public void onStopTrackingTouch(SeekBar seekBar) {
	
				}
			});
	
			sbDuoiDinhMuc.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					tv_valDuoiDinhMuc.setText(((progress + 1) * k) + "%");
				}
	
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}
	
				public void onStopTrackingTouch(SeekBar seekBar) {
	
				}
			});
	
			sbTGTuDongLuu.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					tv_valTGTuDongLuu.setText(((progress + 10)) + "'");
				}
	
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO
				}
	
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO
				}
			});
		} catch(Exception ex) {
			ex.toString();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cau_hinh, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void btnOrderColumns_Click(View view){
//		Intent act = new Intent(getBaseContext(), OrderColumnsActivity.class);
//		startActivity(act);
	}
	
	public void btnBack_Click(View view) {
//		Intent main_act = new Intent(getBaseContext(), MainActivity.class);
//		startActivity(main_act);
		this.finish();
	}
	
	public void btnBackup_Click(View view) {
		createDialogBackup();
	}

	public void chkbCanhBaoDungNhieu_Click(View view) {
		if(chkbCanhBaoDungNhieu.isChecked()){
			chkbCanhBaoChenhLechSL.setChecked(!chkbCanhBaoDungNhieu.isChecked());
			chkbCanhBaoChenhLechSL2.setChecked(!chkbCanhBaoDungNhieu.isChecked());
			etVuotMuc.setEnabled(chkbCanhBaoChenhLechSL2.isChecked());
			etDuoiDinhMuc.setEnabled(chkbCanhBaoChenhLechSL2.isChecked());
			sbVuotMuc.setEnabled(chkbCanhBaoChenhLechSL.isChecked());
			sbDuoiDinhMuc.setEnabled(chkbCanhBaoChenhLechSL.isChecked());
		}
	}
	
	public void chkbCanhBaoChenhLechSL_Click(View view) {
		sbVuotMuc.setEnabled(chkbCanhBaoChenhLechSL.isChecked());
		sbDuoiDinhMuc.setEnabled(chkbCanhBaoChenhLechSL.isChecked());
		if(chkbCanhBaoChenhLechSL.isChecked()){
			chkbCanhBaoChenhLechSL2.setChecked(!chkbCanhBaoChenhLechSL.isChecked());
			chkbCanhBaoDungNhieu.setChecked(!chkbCanhBaoChenhLechSL.isChecked());
			etVuotMuc.setEnabled(chkbCanhBaoChenhLechSL2.isChecked());
			etDuoiDinhMuc.setEnabled(chkbCanhBaoChenhLechSL2.isChecked());
		}
	}
	
	public void chkbCanhBaoChenhLechSL2_Click(View view) {
		etVuotMuc.setEnabled(chkbCanhBaoChenhLechSL2.isChecked());
		etDuoiDinhMuc.setEnabled(chkbCanhBaoChenhLechSL2.isChecked());
		if(chkbCanhBaoChenhLechSL2.isChecked()){
			chkbCanhBaoChenhLechSL.setChecked(!chkbCanhBaoChenhLechSL2.isChecked());
			chkbCanhBaoDungNhieu.setChecked(!chkbCanhBaoChenhLechSL2.isChecked());
			sbVuotMuc.setEnabled(chkbCanhBaoChenhLechSL.isChecked());
			sbDuoiDinhMuc.setEnabled(chkbCanhBaoChenhLechSL.isChecked());
		}
	}

	public void chkbTuDongLuu_Click(View view) {
		sbTGTuDongLuu.setEnabled(chkbTuDongLuu.isChecked());
	}

	public void btnSave_Click(View view) {
//		Common comm = new Common();
		try {
			if(chkbCanhBaoChenhLechSL2.isChecked()){
				if(!etVuotMuc.getText().toString().equals("") && !etDuoiDinhMuc.getText().toString().equals("")){
					String col_order = Common.cfgInfo.getColumnsOrder();
					
					ConfigInfo cfgInfo = new ConfigInfo(
							chkbCanhBaoDungNhieu.isChecked(),
							chkbCanhBaoChenhLechSL.isChecked(),
							chkbCanhBaoChenhLechSL2.isChecked(),
							(sbVuotMuc.getProgress() + 1) * k ,
							(sbDuoiDinhMuc.getProgress() + 1) * k ,
							Integer.parseInt(etVuotMuc.getText().toString().trim()),
							Integer.parseInt(etDuoiDinhMuc.getText().toString().trim()),
							chkbTuDongLuu.isChecked(), sbTGTuDongLuu.getProgress() + 10,
							etxt_URL_WS.getText().toString(), 
							null, 
							col_order,
							ckDinhDang.isChecked(),
							etDvi_qly.getText().toString().trim());
					String filePath = Environment.getExternalStorageDirectory()
							+ Common.programPath + Common.cfgFileName;
					File cfgFile = new File(filePath);

					if (comm.CreateFileConfig(cfgInfo, cfgFile, null)) {
						Common.cfgInfo = new ConfigInfo(
								chkbCanhBaoDungNhieu.isChecked(),
								chkbCanhBaoChenhLechSL.isChecked(),
								chkbCanhBaoChenhLechSL2.isChecked(),
								(sbVuotMuc.getProgress() + 1) * k ,
								(sbDuoiDinhMuc.getProgress() + 1) * k ,
								Integer.parseInt(etVuotMuc.getText().toString().trim()),
								Integer.parseInt(etDuoiDinhMuc.getText().toString().trim()),
								chkbTuDongLuu.isChecked(),
								sbTGTuDongLuu.getProgress() + 10,
								etxt_URL_WS.getText().toString(),
								Common.cfgInfo.getSerial(),
								col_order,
								ckDinhDang.isChecked(),
								etDvi_qly.getText().toString().trim());

						Common.ShowToast(getBaseContext(),"Cài đặt cấu hình thành công", Toast.LENGTH_LONG, size);
					} else {
						new AlertDialog.Builder(this).setTitle("Lỗi")
								.setMessage("Không lưu được cấu hình")
								.setPositiveButton("OK", null).setCancelable(true)
								.create().show();
					}
				} else {
					Common.ShowToast(getBaseContext(), "Bạn phải nhập giá trị sản lượng", Toast.LENGTH_LONG, size);
				}
			} else {
				String col_order = Common.cfgInfo.getColumnsOrder();
				
				ConfigInfo cfgInfo = new ConfigInfo(
						chkbCanhBaoDungNhieu.isChecked(),
						chkbCanhBaoChenhLechSL.isChecked(),
						chkbCanhBaoChenhLechSL2.isChecked(),
						(sbVuotMuc.getProgress() + 1) * k ,
						(sbDuoiDinhMuc.getProgress() + 1) * k ,
						0,
						0,
						chkbTuDongLuu.isChecked(),
						sbTGTuDongLuu.getProgress() + 10,
						etxt_URL_WS.getText().toString(), 
						null, 
						col_order,
						ckDinhDang.isChecked(),
						etDvi_qly.getText().toString().trim());
				String filePath = Environment.getExternalStorageDirectory()
						+ Common.programPath + Common.cfgFileName;
				File cfgFile = new File(filePath);

				if (comm.CreateFileConfig(cfgInfo, cfgFile, null)) {
					Common.cfgInfo = new ConfigInfo(
							chkbCanhBaoDungNhieu.isChecked(),
							chkbCanhBaoChenhLechSL.isChecked(),
							chkbCanhBaoChenhLechSL2.isChecked(),
							(sbVuotMuc.getProgress() + 1) * k ,
							(sbDuoiDinhMuc.getProgress() + 1) * k ,
							0,
							0,
							chkbTuDongLuu.isChecked(),
							sbTGTuDongLuu.getProgress() + 10,
							etxt_URL_WS.getText().toString(),
							Common.cfgInfo.getSerial(),
							col_order,
							ckDinhDang.isChecked(),
							etDvi_qly.getText().toString().trim());

					Common.ShowToast(getBaseContext(),"Cài đặt cấu hình thành công", Toast.LENGTH_LONG, size);
				} else {
					new AlertDialog.Builder(this).setTitle("Lỗi")
							.setMessage("Không lưu được cấu hình")
							.setPositiveButton("OK", null).setCancelable(true)
							.create().show();
				}
			}
		} catch (Exception ex) {
			comm.msbox("Lỗi", "Cài đặt cấu hình không thành công",getApplicationContext());
		}

	}

	ArrayList<LinkedHashMap<String, String>> arrBackup;
	private void setListBackup(ListView lvBackup){
		try{
			String path = Environment.getExternalStorageDirectory() + "/ESGCS/Backup/";
			File fBackup = new File(path);
			arrBackup = new ArrayList<LinkedHashMap<String,String>>();
			
			File[] files = fBackup.listFiles();
			Arrays.sort(files, Common.filecomparator);
			
			for (int i = 0; i < files.length; i++) {
				File fBK = files[i];
				String name = fBK.getName();//Name
				long size = fBK.length()/1024;// Size (KB)
				Date dCreate = new Date(fBK.lastModified());
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		        String newFormat = formatter.format(dCreate);
				
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				map.put("NAME", name);
				map.put("SIZE", "" + size);
				map.put("DATE", newFormat);
				arrBackup.add(map);
			}
			
//			for (int i = 0; i < bkName.length; i++) {
//				File fBK = new File(path + bkName[i]);
//				String name = fBK.getName();//Name
//				long size = fBK.length()/1024;// Size (KB)
//				Date dCreate = new Date(fBK.lastModified());
//				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		        String newFormat = formatter.format(dCreate);
//				
//				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
//				map.put("NAME", name);
//				map.put("SIZE", "" + size);
//				map.put("DATE", newFormat);
//				arrBackup.add(map);
//			}
			
			Adapter_Backup adapter = new Adapter_Backup(Activity_Config.this, R.layout.listview_backup, arrBackup);
			lvBackup.setAdapter(adapter);
			
		} catch(Exception ex) {
			dialogBackup.dismiss();
			Common.ShowToast(Activity_Config.this, "Error setListBackup: " + ex.toString(), Toast.LENGTH_LONG, size);
		}
	}

	public static Dialog dialogBackup;
	
	private void createDialogBackup(){
		try{
			dialogBackup = new Dialog(Activity_Config.this);
			dialogBackup.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogBackup.setContentView(R.layout.dialog_backup);
			dialogBackup.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
//			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//			Button btBackup = (Button) dialog.findViewById(R.id.btBackup);
			ListView lvBackup = (ListView) dialogBackup.findViewById(R.id.lvBackup);
			
			setListBackup(lvBackup);
			
			dialogBackup.show();
		} catch(Exception ex) {
			ex.toString();
		}
	}
	
}
