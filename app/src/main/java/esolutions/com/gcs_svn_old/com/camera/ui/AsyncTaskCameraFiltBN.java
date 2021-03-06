package esolutions.com.gcs_svn_old.com.camera.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;

public class AsyncTaskCameraFiltBN extends AsyncTask<String, LinkedHashMap<String, String>, ArrayList<LinkedHashMap<String, String>>>{

	SQLiteConnection connection;
	public Activity activityParent;
	
	Common comm = new Common();
	
	public AsyncTaskCameraFiltBN(Activity activityParent){
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
			for (LinkedHashMap<String, String> map : Activity_Camera_BN.arrCustomer) {
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
			Activity_Camera_BN.arrCustomer_Filter = result;
			((Activity_Camera_BN) activityParent).adapter = new AdapterCameraBN(activityParent, result, R.layout.listview_gcs);
			((Activity_Camera_BN) activityParent).lvCustomer.setAdapter(((Activity_Camera_BN) activityParent).adapter);
			if(!Activity_Camera_BN.etTimKiem.getText().toString().equals("")){
				Activity_Camera_BN.selected_index = 0;
				((Activity_Camera_BN) activityParent).lvCustomer.setSelection(Activity_Camera_BN.selected_index);
				((Activity_Camera_BN) activityParent).setDataOnEditText(Activity_Camera_BN.selected_index, 5);
				((Activity_Camera_BN) activityParent).showHidePmax(Activity_Camera_BN.selected_index);
			} else {
				((Activity_Camera_BN) activityParent).setDataOnEditText(Activity_Camera_BN.selected_index, 6);
				((Activity_Camera_BN) activityParent).showHidePmax(Activity_Camera_BN.selected_index);
				Activity_Camera_BN.selected_index = ((Activity_Camera_BN) activityParent).getPos();
				((Activity_Camera_BN) activityParent).lvCustomer.setSelection(Activity_Camera_BN.selected_index);
			}
//			((Activity_Camera_BN) activityParent).lvCustomer.setOnItemClickListener(new OnItemClickListener() {
//	
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						
//						int position, long id) {
//					Activity_Camera_BN.selected_index = position;
//					((Activity_Camera_BN) activityParent).adapter.notifyDataSetChanged();
//					((Activity_Camera_BN) activityParent).lvCustomer.invalidate();
//					((Activity_Camera_BN) activityParent).setImage();
//					((Activity_Camera_BN) activityParent).setDataOnEditText(Activity_Camera_BN.selected_index);
//					((Activity_Camera_BN) activityParent).showHidePmax(Activity_Camera_BN.selected_index);
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
