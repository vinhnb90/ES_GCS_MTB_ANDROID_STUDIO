package esolutions.com.gcs_svn_old.com.camera.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.AsyncCallWS;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;

public class QuanLySoActivity extends RootActivity {
	
	boolean result = false;
	int count_check = 0;
	ArrayAdapter<String> dataAdapter;
	Spinner spnChonSo;
	ListView lvChonSo;
	CheckBox chkbchonnhieuso;
	LinkedHashMap<String, String> mapSo;
	Common comm = new Common();
	Intent gcs_act;
	int size;
	String trangthai = "";
	SQLiteConnection connection;
	org.w3c.dom.Document doc;
	
	AsyncCallWS acws;
	List<String> list_so;// lấy danh sách sổ đang có trên máy
	Button btnRestoreData ;
	CheckBox chkCheckAll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			setContentView(R.layout.activity_quan_ly_so);
			getActionBar().hide();
			chkCheckAll = (CheckBox) findViewById(R.id.chkCheckAll);
			lvChonSo = (ListView) findViewById(R.id.lvChonSo1);
			btnRestoreData = (Button) findViewById(R.id.btnRestoreData);
			btnRestoreData.setVisibility(View.GONE);
			
			chkCheckAll.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int num_so = lvChonSo.getCount();
					if(chkCheckAll.isChecked()){
						// chọn tất cả sổ trong list
						for(int i = 0; i<=num_so; i++){
					        lvChonSo.setItemChecked(i, true);
					    }
					} else {
						// chọn tất cả sổ trong list
						for(int i = 0; i<=num_so; i++){
					        lvChonSo.setItemChecked(i, false);
					    }
					}
				}
			});
			
		}catch(Exception e){
			comm.msbox("Thông báo", e.getMessage(), QuanLySoActivity.this);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume(); // Always call the superclass method first
		
		if(acws == null && Common.cfgInfo.getIP_SERVICE() != null && Common.cfgInfo.getIP_SERVICE().length() > 0)
			acws = new AsyncCallWS();
		String result = comm.CheckDbExist(QuanLySoActivity.this, "ESGCS.s3db", Environment.getExternalStorageDirectory() +Common.DBFolderPath);
		if(result == "EXIST"){
			connection = null;
			connection = new SQLiteConnection(QuanLySoActivity.this, "ESGCS.s3db", Environment.getExternalStorageDirectory() +Common.DBFolderPath);
		}
//		comm.LoadFolder(this);
		
		spnChonSo_GetData(lvChonSo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quan_ly_so, menu);
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

	/**
	 * Lấy dữ liệu đổ vào spinner chọn sổ
	 */
	@SuppressWarnings("static-access")
	private void spnChonSo_GetData(ListView lvChonSo) {

		File file_db = new File(Environment.getExternalStorageDirectory() +Common.DBFolderPath + "/ESGCS.s3db");
		if(file_db.exists()){
			// lấy danh sách sổ đang có trên máy
			list_so = connection.GetAllSo();
		}else{
			list_so = new ArrayList<String>();
		}
		
		java.util.Collections.sort(list_so);

		try {
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(QuanLySoActivity.this, android.R.layout.simple_list_item_multiple_choice, list_so);
			lvChonSo.setChoiceMode(lvChonSo.CHOICE_MODE_MULTIPLE);
			lvChonSo.setAdapter(dataAdapter);
				
		} catch (Exception ex) {
			comm.msbox("Thông báo", "Chưa có dữ liệu trong thư mục", QuanLySoActivity.this);
		}
	}
		
	public void btnDelData_Click(View view) {
		try {
			final String[] selected_item = spnChonSo_GetSelectedItem(lvChonSo);
			String str_list_so = "\n";
			final ArrayList<String> arr_list_quyen = new ArrayList<String>();
			for(String item : selected_item){
				str_list_so += item+"\n";
				arr_list_quyen.add(item.contains("_")?item.split("_")[0].toString():item.replace(".", ",").split(",")[0].toString());
			}
			
			new AlertDialog.Builder(QuanLySoActivity.this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Lấy sổ")
			.setMessage("Danh sách sổ muốn xóa:\n"+str_list_so+"\nBạn có muốn xóa ?")
			.setPositiveButton("Đồng ý",
				new DialogInterface.OnClickListener() {
					@SuppressLint("SimpleDateFormat")
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						try{
							//-> create backup
							comm.CreateBackup(null);
							
							File file_db = new File(Environment.getExternalStorageDirectory() +Common.DBFolderPath + "/ESGCS.s3db");
							if(file_db.exists()){//PhotoFolderPath
								File file_photo = new File(Environment.getExternalStorageDirectory() + Common.PhotoFolderPath);
								String[] allFilesPhoto = file_photo.list();
					            for (int i = 0; i < allFilesPhoto.length; i++) {
					            	String ma_quyen = allFilesPhoto[i].split("_")[0].toString();
					            	if(arr_list_quyen.contains(ma_quyen)){
					            		File file_pt = new File( Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + allFilesPhoto[i]);
					            		if(file_pt.exists()){
					            			file_pt.delete();
					            		}
					            	}
								}
								connection.GetWritableDatabase();
								connection.BeginTransaction();
								
								//->Xóa sổ trên máy
								connection.delete_array_so(selected_item);
								
								connection.SetTransactionSuccessful();
							}
							spnChonSo_GetData(lvChonSo);
							comm.msbox("Xóa sổ", "Xóa sổ thành công", QuanLySoActivity.this);
						}
						catch(Exception e){
							comm.msbox("Xóa sổ", "Xóa sổ thất bại", QuanLySoActivity.this);
						}
						finally {
							connection.EndTransaction();										
					    }
					}

				})
			.setNegativeButton("Không", null)
			.show().getWindow().setGravity(Gravity.BOTTOM);
		} catch(Exception ex) {
			comm.msbox("Xóa sổ", "Xóa sổ thất bại", QuanLySoActivity.this);
		}
	}
	
	public void btnRestoreData_Click(View view) {
		try {
			
			comm.msbox("Thông báo", "Phục hồi", QuanLySoActivity.this);
			spnChonSo_GetData(lvChonSo);
		} catch(Exception ex) {
			
		}
	}
	
	public void btnBack_Click(View view) {
		QuanLySoActivity.this.finish();
	}
	
	/**
	 * Lấy sổ được chọn trong danh sách
	 * @param lvChonSo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String[] spnChonSo_GetSelectedItem(ListView lvChonSo){
		List<String> list_selected = new ArrayList<String>();
		ArrayAdapter<String> dataAdapter = (ArrayAdapter<String>) lvChonSo.getAdapter();
		SparseBooleanArray ck = lvChonSo.getCheckedItemPositions();
		for (int i = 0; i < lvChonSo.getCount(); i++) {
			if(ck.get(i)){
				list_selected.add(dataAdapter.getItem(i));
			}
		}
		return list_selected.toArray(new String[0]);
	}

}
