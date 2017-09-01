package esolutions.com.gcs_svn_old.com.camera.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;

public class AsyncTaskCameraFilt extends AsyncTask<String, LinkedHashMap<String, String>, ArrayList<LinkedHashMap<String, String>>>{

	SQLiteConnection connection;
	public Activity activityParent;
	
	Common comm = new Common();
	
	public AsyncTaskCameraFilt(Activity activityParent){
		try{
			this.activityParent = activityParent;
			connection = new SQLiteConnection(activityParent, "ESGCS.s3db", Environment.getExternalStorageDirectory() + Common.DBFolderPath);
		} catch(Exception ex){
			ex.toString();
		}
	}
	
	@Override
	protected ArrayList<LinkedHashMap<String, String>> doInBackground(String... para) {
		try{
			ArrayList<LinkedHashMap<String, String>> filt = new ArrayList<LinkedHashMap<String,String>>();
			for (LinkedHashMap<String, String> map : Activity_Camera.arrCustomer) {
				String val = null;
				// nếu ko phải tìm các kh chưa ghi và đã ghi
				if (!para[0].equals("CHUA_GHI") && !para[0].equals("DA_GHI")) {
					val = map.get(para[0]);
					if(val != null ){
						val = val.toLowerCase();
					}
				}
				// kiểm tra theo tên ko dấu và có dấu
				if (para[0].equals("TEN_KHANG") || para[0].equals("DIA_CHI")) {
					String ten_bo_dau = comm.VietnameseTrim(val);
					if (val.toLowerCase().contains(para[1]) || ten_bo_dau.toLowerCase().contains(
									comm.VietnameseTrim(para[1].toString().toLowerCase()))) {
						filt.add(map);
					}
				} else if (para[0].equals("CHUA_GHI")) {
					String valTTR_MOI = map.get("TTR_MOI");          
					String valCS_MOI = map.get("CS_MOI");
					if ((valTTR_MOI == null || valTTR_MOI.trim().equals(""))
							&& (valCS_MOI == null || valCS_MOI.trim().length() == 0 || Float.parseFloat(valCS_MOI) == 0)) {
						filt.add(map);
					}
				} else if (para[0].equals("DA_GHI")) {
					String valTTR_MOI = map.get("TTR_MOI");          
					String valCS_MOI = map.get("CS_MOI");
					if ((valTTR_MOI != null && !valTTR_MOI.trim().equals(""))
							|| (valCS_MOI != null && valCS_MOI.trim().length() != 0 && Float.parseFloat(valCS_MOI) != 0)) {
						filt.add(map);
					}
				} else if (para[0].equals("CHUA_CHUP")) {
					String TEN_FILE = map.get("TEN_FILE").toLowerCase().contains(".xml")?map.get("TEN_FILE").substring(0, map.get("TEN_FILE").length() - 4):map.get("TEN_FILE");
					String fileName = map.get("MA_QUYEN");
					String MA_CTO = map.get("MA_CTO");
					String NAM = map.get("NAM");
					String THANG = map.get("THANG");
					String KY = map.get("KY");
					String MA_DDO = map.get("MA_DDO");
					String LOAI_BCS = map.get("LOAI_BCS");
					String filePath = Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" 
						+ TEN_FILE + "/" + fileName + "_" + MA_CTO + "_" + NAM + "_" 
						+ THANG + "_" + KY + "_" + MA_DDO + "_" + LOAI_BCS + ".jpg";
					Log.e("ERRORRRRRR", filePath);
					File fImage = new File(filePath);
					if(!fImage.exists()){
						filt.add(map);
					}
				} else if (val != null && val.contains(para[1])) {
					filt.add(map);
				};
			}
			return filt;
		} catch(Exception ex) {
			return null;
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(ArrayList<LinkedHashMap<String, String>> result) {
		super.onPostExecute(result);
		try{
			Activity_Camera.arrCustomer_Filter = result;
			((Activity_Camera) activityParent).adapter = new AdapterCamera(activityParent, result, R.layout.listview_gcs);
			((Activity_Camera) activityParent).lvCustomer.setAdapter(((Activity_Camera) activityParent).adapter);
			if(!((Activity_Camera) activityParent).etTimKiem.getText().toString().equals("")){
				Activity_Camera.selected_index = 0;
				((Activity_Camera) activityParent).lvCustomer.setSelection(Activity_Camera.selected_index);
				((Activity_Camera) activityParent).setDataOnEditText(Activity_Camera.selected_index, 5);
				((Activity_Camera) activityParent).showHidePmax(Activity_Camera.selected_index);
			} else {
				((Activity_Camera) activityParent).setDataOnEditText(Activity_Camera.selected_index, 6);
				((Activity_Camera) activityParent).showHidePmax(Activity_Camera.selected_index);
				Activity_Camera.selected_index = ((Activity_Camera) activityParent).getPos();
				((Activity_Camera) activityParent).lvCustomer.setSelection(Activity_Camera.selected_index);
			}
//			((Activity_Camera) activityParent).lvCustomer.setOnItemClickListener(new OnItemClickListener() {
//	
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						
//						int position, long id) {
//					Activity_Camera.selected_index = position;
//					((Activity_Camera) activityParent).adapter.notifyDataSetChanged();
//					((Activity_Camera) activityParent).lvCustomer.invalidate();
//					((Activity_Camera) activityParent).setImage();
//					((Activity_Camera) activityParent).setDataOnEditText(Activity_Camera.selected_index);
//					((Activity_Camera) activityParent).showHidePmax(Activity_Camera.selected_index);
//				}
//			});
		} catch(Exception ex) {
			ex.toString();
		}
	}

	@Override
	protected void onProgressUpdate(LinkedHashMap<String, String>... values) {
		super.onProgressUpdate(values);
	}

}
