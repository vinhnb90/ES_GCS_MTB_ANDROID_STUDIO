package esolutions.com.gcs_svn_old.com.camera.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;

public class AsyncTaskCamera extends AsyncTask<String, LinkedHashMap<String, String>, ArrayList<LinkedHashMap<String, String>>>{

	SQLiteConnection connection;
	public Activity activityParent;
//	public ListView ((Activity_Camera) activityParent).lvCustomer = null;
	public TextView tvDaGhi = null;
	public TextView tvMaQuyen = null;
//	AdapterCamera adapter = null;
	public int daghi;
	public int _stt = 0;
	Common comm = new Common();
	private String kieu_hthi = "ALL";
	
	public static String[] colArr = { "MA_NVGCS", "MA_KHANG", "MA_DDO",
		"MA_DVIQLY", "MA_GC", "MA_QUYEN", "MA_TRAM", "BOCSO_ID",
		"LOAI_BCS", "LOAI_CS", "TEN_KHANG", "DIA_CHI", "MA_NN", "SO_HO",
		"MA_CTO", "SERY_CTO", "HSN", "CS_CU", "TTR_CU", "SL_CU",
		"SL_TTIEP", "NGAY_CU", "CS_MOI", "TTR_MOI", "SL_MOI", "CHUOI_GIA",
		"KY", "THANG", "NAM", "NGAY_MOI", "NGUOI_GCS", "SL_THAO",
		"KIMUA_CSPK", "MA_COT", "CGPVTHD", "HTHUC_TBAO_DK", "DTHOAI_SMS",
		"EMAIL", "THOI_GIAN", "X", "Y", "SO_TIEN", "HTHUC_TBAO_TH",
		"TENKHANG_RUTGON", "TTHAI_DBO", "DU_PHONG", "TEN_FILE", "GHICHU",
		"TT_KHAC", "ID_SQLITE", "SLUONG_1", "SLUONG_2", "SLUONG_3", "SO_HOM",
		"CHU_KY", "HINH_ANH", "PMAX", "NGAY_PMAX", "STR_CHECK_DSOAT"};
	
	public AsyncTaskCamera(Activity activityParent, String kieu_hthi){
		try{
			this.activityParent = activityParent;
			this.kieu_hthi = kieu_hthi;
			Activity_Camera.arrCustomer = new ArrayList<LinkedHashMap<String,String>>();
			connection = new SQLiteConnection(activityParent, "ESGCS.s3db", Environment.getExternalStorageDirectory() + Common.DBFolderPath);
			((Activity_Camera) activityParent).lvCustomer = (ListView)this.activityParent.findViewById(R.id.hlvCustomer);
			((Activity_Camera) activityParent).adapter = new AdapterCamera(activityParent.getApplicationContext(), Activity_Camera.arrCustomer, R.layout.listview_gcs);
			((Activity_Camera) activityParent).lvCustomer.setAdapter(((Activity_Camera) activityParent).adapter);
		} catch(Exception ex){
			Log.e("error init", ex.toString());
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
			Cursor c = kieu_hthi.equals("DSOAT")?connection.getAllDataGCSsoft(fileName[0]):
				connection.getAllDataGCSsoft2(fileName[0]);
			if(!c.moveToFirst() || !c_lotrinh.moveToFirst()){
				c = kieu_hthi.equals("DSOAT")?connection.getAllDataGCS(fileName[0]):
					connection.getAllDataGCS2(fileName[0]);
			}
			if (c.moveToFirst()) {
				do {
					stt++;
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					
					for (int i = 0; i < colArr.length; i++) {
//						map.put(colArr[i], c.getString(i + 1));
						map.put(colArr[i], c.getString(c.getColumnIndex(colArr[i])));
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
						arrCustomer.add(map);
						_stt = 3;
					} else if(c.getString(9).equals("VC")){
						if(BCS.equals("KT") || BCS.equals("BT")){
							map.put("STT", "" + stt);
							arrCustomer.add(map);
							_stt = 0;
						} else {
							map.put("STT", "" + (stt - 3));
							arrCustomer.add(map);
							_stt = 3;
						}
					} else if(c.getString(9).equals("KT")){
						map.put("STT", "" + stt);
						arrCustomer.add(map);
						_stt = 0;
					} else {
						if(connection.countByID(map.get("MA_CTO")) == 2) {
							map.put("STT", "" + stt);
							arrCustomer.add(map);
							_stt = 0;
						} else if(connection.countByID(map.get("MA_CTO")) == 5) {
							map.put("STT", "" + (stt + 2));
							arrCustomer.add(map);
							_stt = 0;
						} else {
							map.put("STT", "" + (stt + 1));
							arrCustomer.add(map);
							_stt = 0;
						}
					}
					
					BCS = c.getString(9);
					if(comm.isSavedRow(c.getString(24), c.getString(23))){
						daghi++;
					}
					LinkedHashMap<String, String > map2 = new LinkedHashMap<String, String>();
					map2.put("stt", "" + _stt);
					publishProgress(map, map2);
				} while (c.moveToNext());
			}
			return arrCustomer;
		} catch(Exception ex) {
			Log.e("error do in background", ex.toString());
			return null;
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(final ArrayList<LinkedHashMap<String, String>> result) {
		super.onPostExecute(result);
		try{
			tvDaGhi = (TextView)activityParent.findViewById(R.id.tvDaGhi);
			tvMaQuyen = (TextView)activityParent.findViewById(R.id.tvMaQuyen);
			
			((Activity_Camera) activityParent).lvCustomer.setSelection(Activity_Camera.selected_index);
			
			tvMaQuyen.setText(result.get(0).get("MA_QUYEN"));
			tvDaGhi.setText(daghi + "/" + result.size());
			
			((Activity_Camera) activityParent).setImage();
			((Activity_Camera) activityParent).setDataOnEditText(Activity_Camera.selected_index, 3);
			((Activity_Camera) activityParent).showHidePmax(Activity_Camera.selected_index);
			((Activity_Camera) activityParent).lvCustomer.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Activity_Camera.selected_index = position;
					((Activity_Camera) activityParent).adapter.notifyDataSetChanged();
					((Activity_Camera) activityParent).lvCustomer.invalidate();
					((Activity_Camera) activityParent).setImage();
					((Activity_Camera) activityParent).setDataOnEditText(Activity_Camera.selected_index, 4);
					((Activity_Camera) activityParent).showHidePmax(Activity_Camera.selected_index);
					Common.pos_change_camera = position;
				}
			});
			((Activity_Camera) activityParent).lvCustomer.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					Activity_Camera.selected_index = 0;
					((Activity_Camera) activityParent).adapter.notifyDataSetChanged();
					((Activity_Camera) activityParent).lvCustomer.invalidate();
					((Activity_Camera) activityParent).setImage();
					((Activity_Camera) activityParent).setDataOnEditText(Activity_Camera.selected_index, 4);
					((Activity_Camera) activityParent).showHidePmax(Activity_Camera.selected_index);
					tvMaQuyen.setText(result.get(0).get("MA_QUYEN"));
					((Activity_Camera) activityParent).lvCustomer.setSelection(0);
					return false;
				}
			});
		} catch(Exception ex) {
			Log.e("error post execute", ex.toString());
		}
	}

	@Override
	protected void onProgressUpdate(LinkedHashMap<String, String>... values) {
		super.onProgressUpdate(values);
		try{
			Activity_Camera.arrCustomer.add(Activity_Camera.arrCustomer.size() - Integer.parseInt(values[1].get("stt")), values[0]);
//			((Activity_Camera) activityParent).adapter.notifyDataSetChanged();
//			((Activity_Camera) activityParent).lvCustomer.invalidate();
			((Activity_Camera) activityParent).adapter = new AdapterCamera(activityParent.getApplicationContext(), Activity_Camera.arrCustomer, R.layout.listview_gcs);
			((Activity_Camera) activityParent).lvCustomer.setAdapter(((Activity_Camera) activityParent).adapter);
		} catch(Exception ex) {
			Log.e("error progress update", ex.toString());
		}
	}

}
