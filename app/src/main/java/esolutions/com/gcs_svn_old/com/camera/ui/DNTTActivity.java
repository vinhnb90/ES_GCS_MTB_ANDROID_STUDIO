package esolutions.com.gcs_svn_old.com.camera.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.ConstantVariables;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;

@SuppressLint("NewApi")
public class DNTTActivity extends RootActivity {

	private String[] dntt_colName = new String[] { "STT", "Tram", /*"Quyen",*/
			"DaGhi", "DNTThu", "InputDN", "DNTThat" };
	private int[] dntt_colID = new int[] { R.id.tv_STT, R.id.tv_Tram,
			/*R.id.tv_Quyen,*/ R.id.tv_DaGhi, R.id.tv_DNTThu, R.id.tv_InputDN,
			R.id.tv_DNTThat };
	private int selectedIdx = -1;
	private LinkedHashMap<String, String> selectedRow;
	private ArrayList<LinkedHashMap<String, String>> ListViewData;
	ListAdapter adapter;
	SQLiteConnection connection;
	Common comm;
	
	public static int Selected_STT = 1;

	private EditText et_InputDN;

	private ListView lsvDNTT;
	private Button btnTinhDNTT;

	// các control của dòng hiện tại
	//private View currView;
	private TextView currTvDTThu;
	//private TextView currTvDTThat;
	private TextView currTvInputDN;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		comm = new Common();
		setContentView(R.layout.activity_dntt);
		// Show the Up button in the action bar.
		// setupActionBar();
		getActionBar().hide();
		if(comm.checkDB()){
			connection = new SQLiteConnection(this, "ESGCS.s3db", Environment.getExternalStorageDirectory() +Common.DBFolderPath);
			init_data();
		}

		init_control();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dntt, menu);
		return true;
	}

	private void init_data() {
		ListViewData = new ArrayList<LinkedHashMap<String, String>>();

		try {
			ListViewData = LayTongDNTT_Quyen(ListViewData, 1);
			
			if (!ListViewData.isEmpty()) {
				adapter = new DNTTAdapter(this, ListViewData, R.layout.listview_dntt, dntt_colName, dntt_colID);
				ListView lsvDNTT = (ListView) DNTTActivity.this.findViewById(R.id.lsvDNTT);
				lsvDNTT.setAdapter(adapter);
			}

		} catch (Exception ex) {
			comm.msbox("Lỗi", "Không lấy được dữ liệu", this);
		}
	}

	private void init_control() {
		try{
			et_InputDN = (EditText) this.findViewById(R.id.et_InputDN);
			btnTinhDNTT = (Button) this.findViewById(R.id.btnTinhDNTT);
			ImageButton ibtDongDntt = (ImageButton)findViewById(R.id.ibtDongDntt);
			ibtDongDntt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DNTTActivity.this.finish();
				}
			});
			lsvDNTT = (ListView) this.findViewById(R.id.lsvDNTT);
			// gán sự kiện khi click vào 1 dòng
			lsvDNTT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (DNTTActivity.this.selectedIdx < 0) {
						SetEnableView(true);
					} else {
						// Bỏ selected item cũ
						for (int i = 0; i < parent.getChildCount(); i++) {
							View old_view = parent.getChildAt(i);
	//						if (((TextView) old_view.findViewById(R.id.tv_STT))
	//								.getText().toString().equals(DNTTActivity.Selected_STT)) {
								old_view.setBackgroundColor(ConstantVariables.NO_SELECTED_COLOR);
	//						}
						}
					}
	
					// Lấy thông tin dòng được chọn
					SelectedItem(position, view);
	
				}
			});
			
			SelectedItem(Selected_STT-1, null);
		}
		catch(Exception ex){
			
		}
	}

	private void SetEnableView(boolean isEnable) {
		et_InputDN.setEnabled(isEnable);
		btnTinhDNTT.setEnabled(isEnable);
	}

	private void SelectedItem(int position, View view) {
		if(adapter == null || adapter.isEmpty()){
			return;
		}
		if (view == null && Selected_STT > 0) {
			view = adapter.getView(Selected_STT - 1, null, null);
		}
		
		this.selectedIdx = position;
		DNTTActivity.Selected_STT = Integer.parseInt(((TextView) view.findViewById(R.id.tv_STT))
				.getText().toString());
		selectedRow = ListViewData.get(Selected_STT - 1);
		view.setBackgroundColor(ConstantVariables.SELECTED_COLOR);

		//currView = view;
		currTvDTThu = (TextView) view.findViewById(R.id.tv_DNTThu);
		currTvInputDN = (TextView) view.findViewById(R.id.tv_InputDN);
		//currTvDTThat = (TextView) view.findViewById(R.id.tv_DNTThat);

		// Lấy stt và set bgrcolor selected item
		

		et_InputDN.setText(currTvInputDN.getText());
		et_InputDN.requestFocus();
		et_InputDN.selectAll();

	}

	private ArrayList<LinkedHashMap<String, String>> LayTongDNTT_Quyen(ArrayList<LinkedHashMap<String, String>> ListViewData, int STT_begin) {
		int stt = STT_begin;
		String ma_tram = null;
		List<String> mts = new ArrayList<String>();
		int so_diem_do = 0;
		int da_ghi = 0;
		Double sum_dntt = 0d;
		Cursor c = connection.getAllDataMaTram();
		if(c.moveToFirst()){
			do{
//				if(!mts.contains(c.getString(0))){
					mts.add(c.getString(0));
//				}
			} while(c.moveToNext());
		}
		for (String mt : mts) {
			sum_dntt = 0d;
			ma_tram = mt;
			da_ghi = 0;
			Cursor c2 = connection.getAllDataGCSbyTram(mt);
			so_diem_do = c2.getCount();
			if(c2.moveToFirst()){
				do{
					String bcs = "";
					Float dtt = 0F;
					Float sl_moi = 0F;
					Float sl_thao = 0F;
					if(comm.isSavedRow(c2.getString(24), c2.getString(23))){
						da_ghi++;
					}
					bcs = c2.getString(9);
					sl_moi = Float.parseFloat(c2.getString(25));
					sl_thao = Float.parseFloat(c2.getString(32));
					if(("KT,BT,CD,TD").contains(bcs)){
						dtt = sl_moi + sl_thao;
						sum_dntt += dtt;	
					}
				} while(c2.moveToNext());
			}
			LinkedHashMap<String, String> mapTram = null;
			for(int i = 0; i < ListViewData.size(); i++){
				if(ListViewData.get(i).get("LOAI").equals("TRAM") && ListViewData.get(i).get("Tram").equals(ma_tram)){
					mapTram = ListViewData.get(i);
					break;
				}
			}
			
			//nếu chưa có thông tin trạm thì thêm vào
			if(mapTram == null){
				mapTram = new LinkedHashMap<String, String>();
				mapTram.put("STT", stt + "");
				stt++;
				mapTram.put("Tram", ma_tram);
				mapTram.put("DaGhi", da_ghi+"");
				mapTram.put("SoDiemDo", so_diem_do+"");
				mapTram.put("DNTThu", String.format(Locale.ENGLISH, "%.0f",sum_dntt) );
				mapTram.put("InputDN", "");
				mapTram.put("DNTThat_Percent", "");
				mapTram.put("DNTThat", "");
				mapTram.put("LOAI", "TRAM");

				ListViewData.add(mapTram);
			} else {
				//  nếu đã có thông tin trạm thì cộng thêm dtt của quyển vào cho trạm
				Double dttTram = Double.parseDouble(mapTram.get("DNTThu")) + sum_dntt;
				int da_ghi_tram = Integer.parseInt(mapTram.get("DaGhi")) + da_ghi ;
				int so_diem_do_tram = Integer.parseInt(mapTram.get("SoDiemDo")) +so_diem_do;
				mapTram.put("DNTThu", String.format(Locale.ENGLISH, "%.0f",dttTram));
				mapTram.put("DaGhi", da_ghi_tram +"");
				mapTram.put("SoDiemDo",  so_diem_do_tram+"");
			}
		}
		
		return ListViewData;
	}

	public void btnTinhDNTT_Click(View view) {
		try {
			String inputDN = this.et_InputDN.getText().toString();
			if (inputDN.length() == 0) {
				comm.msbox("Thông báo", "Chưa nhập điện năng đầu nguồn", this);
				return;
			}
			Double dn_dau_nguon = Double.parseDouble(inputDN);
			Double dien_ton_that = dn_dau_nguon
					- Double.parseDouble(currTvDTThu.getText().toString());
			double dien_ton_that_percent = ((double)dien_ton_that/(double)dn_dau_nguon) * 100;
			
			String dx = String.format(Locale.ENGLISH, "%.3f", dien_ton_that_percent); //Common.ConvertDoubleToString(dien_ton_that_percent,Common.FORMAT_DECIMAL_PATTERN) ;//String.format(Locale.ENGLISH, "%.3f",dien_ton_that_percent)
			dien_ton_that_percent = Double.valueOf(dx);
			
			selectedRow.put("InputDN", inputDN);
			selectedRow.put("DNTThat_Percent", String.format(Locale.ENGLISH, "%.3f", dien_ton_that_percent)); //Common.ConvertDoubleToString(dien_ton_that_percent,Common.FORMAT_DECIMAL_PATTERN) );//String.format(Locale.ENGLISH, "%.3f", dien_ton_that_percent)
			selectedRow.put("DNTThat", String.format(Locale.ENGLISH, "%.0f", dien_ton_that));
			((BaseAdapter) lsvDNTT.getAdapter()).notifyDataSetChanged();

		} catch (Exception ex) {
			comm.msbox("Thông báo", "Không thể tính điện năng tổn thất", this);
		}

	}
}
