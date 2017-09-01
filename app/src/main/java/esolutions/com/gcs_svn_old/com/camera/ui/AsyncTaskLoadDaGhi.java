package esolutions.com.gcs_svn_old.com.camera.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.TextView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;

public class AsyncTaskLoadDaGhi extends AsyncTask<String, LinkedHashMap<String, String>, ArrayList<LinkedHashMap<String, String>>>{

	SQLiteConnection connection;
	public Activity activityParent;
	public TextView tvDaGhi = null;
	public TextView tvMaQuyen = null;
	public int daghi;
	
	Common comm = new Common();
	
	public static String[] colArr = { "MA_NVGCS", "MA_KHANG", "MA_DDO",
		"MA_DVIQLY", "MA_GC", "MA_QUYEN", "MA_TRAM", "BOCSO_ID",
		"LOAI_BCS", "LOAI_CS", "TEN_KHANG", "DIA_CHI", "MA_NN", "SO_HO",
		"MA_CTO", "SERY_CTO", "HSN", "CS_CU", "TTR_CU", "SL_CU",
		"SL_TTIEP", "NGAY_CU", "CS_MOI", "TTR_MOI", "SL_MOI", "CHUOI_GIA",
		"KY", "THANG", "NAM", "NGAY_MOI", "NGUOI_GCS", "SL_THAO",
		"KIMUA_CSPK", "MA_COT", "CGPVTHD", "HTHUC_TBAO_DK", "DTHOAI_SMS",
		"EMAIL", "THOI_GIAN", "X", "Y", "SO_TIEN", "HTHUC_TBAO_TH",
		"TENKHANG_RUTGON", "TTHAI_DBO", "DU_PHONG", "TEN_FILE", "GHICHU",
		"TT_KHAC", "ID_SQLITE", "SLUONG_1", "SLUONG_2", "SLUONG_3", "SO_HOM" };
	
	public AsyncTaskLoadDaGhi(Activity activityParent){
		try{
			this.activityParent = activityParent;
			connection = new SQLiteConnection(activityParent, "ESGCS.s3db", Environment.getExternalStorageDirectory() + Common.DBFolderPath);
		} catch(Exception ex){
			ex.toString();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ArrayList<LinkedHashMap<String, String>> doInBackground(String... fileName) {
		try{
			daghi = 0;
			int stt = 0;
			Float dtt = 0F;
			String BCS = "";
			ArrayList<LinkedHashMap<String, String>> arrCustomer = new ArrayList<LinkedHashMap<String,String>>();
			Cursor c_lotrinh = connection.getDataRouteByFileName(comm.convertFile(fileName[0], connection));
			Cursor c = connection.getAllDataGCSsoft(fileName[0]);
			if(!c.moveToFirst() || !c_lotrinh.moveToFirst()){
				c = connection.getAllDataGCS(fileName[0]);
			}
			if (c.moveToFirst()) {
				do {
					stt++;
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					
					for (int i = 0; i < colArr.length; i++) {
						map.put(colArr[i], c.getString(i + 1));
					}
					try{
						map.put("PMAX", c.getString(57));
						map.put("NGAY_PMAX", c.getString(58));
					} catch(Exception ex) {
						ex.toString();
					}
					// tạo cột ĐTT
					dtt = (Float.parseFloat(map.get("SL_MOI")) + Float.parseFloat(map.get("SL_THAO")));
					String dientt = String.valueOf(dtt);
					try {
						if (Integer.parseInt(dientt.replace(".", ",").split(",")[1]) > 0)
							map.put("DTT", String.format(Locale.ENGLISH, "%.3f", dtt));
						else
							map.put("DTT", String.format(Locale.ENGLISH, "%.0f", dtt));
					} catch (Exception ex) {
						map.put("DTT", String.format(Locale.ENGLISH, "%.0f", dtt));
					}
	
					if (c.getString(9).equals("SG")) {
						map.put("STT", "" + (stt - 3));
						arrCustomer.add(arrCustomer.size() - 3, map);
					} else if(c.getString(9).equals("VC")){
						if(!BCS.equals("KT")){
							map.put("STT", "" + (stt - 3));
							arrCustomer.add(arrCustomer.size() - 3, map);
						} else {
							map.put("STT", "" + stt);
							arrCustomer.add(map);
						}
					} else if(c.getString(9).equals("KT")){
						map.put("STT", "" + stt);
						arrCustomer.add(map);
					} else {
						map.put("STT", "" + (stt + 2));
						arrCustomer.add(map);
					}
					publishProgress(map);
					BCS = c.getString(9);
					if(comm.isSavedRow(c.getString(24), c.getString(23))){
						daghi++;
					}
				} while (c.moveToNext());
			}
			return arrCustomer;
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
			tvDaGhi = (TextView)activityParent.findViewById(R.id.tvDaGhi);
			tvMaQuyen = (TextView)activityParent.findViewById(R.id.tvMaQuyen);
			
//			tvMaQuyen.setText(result.get(0).get("MA_QUYEN"));
			tvDaGhi.setText(daghi + "/" + result.size());
			
		} catch(Exception ex) {
			ex.toString();
		}
	}

	@Override
	protected void onProgressUpdate(LinkedHashMap<String, String>... values) {
		super.onProgressUpdate(values);
	}

}
