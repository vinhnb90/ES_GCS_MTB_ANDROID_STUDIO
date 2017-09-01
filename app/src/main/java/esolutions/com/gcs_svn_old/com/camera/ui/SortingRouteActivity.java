package esolutions.com.gcs_svn_old.com.camera.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.ConstantVariables;
import esolutions.com.gcs_svn_old.com.camera.utility.SQLiteConnection;

@SuppressLint("DefaultLocale")
public class SortingRouteActivity extends RootActivity {
	Common comm = null;
	
	String[] LoTrinhTag = {"SERY_CTO", "STT_ORG", "STT_ROUTE","LOAI_BCS"};
	String[] sorting_route_col_name = {"STT_ROUTE", "MA_GC", "MA_COT"};
	int[] sorting_route_col_id = {R.id.tv_STT,R.id.tv_MA_GC,R.id.tv_MA_COT };
	int selectedIdx;
	boolean isSaved;
	String ext_pattern = "_[0-9]{4,5}.[Xx][Mm][Ll]"; //_41011.Xml

	LinkedHashMap<String, String> mapSo;
	LinkedHashMap<String, String> mapSo2;
	LinkedHashMap<String, String> selectedRow = null;
	ArrayList<LinkedHashMap<String, String>> lsvDataSortingRoute = null; // chứa danh sách cto đầy đủ của sổ
	ArrayList<LinkedHashMap<String, String>> lsvDataSortingRoute2 = null;// chứa danh sách cto hiện trên list sắp xếp
	
	File CurrentFile ;
	ListView lsvSortingRoute ;
	SortingRouteAdapter srAdapter;
	SortingRouteAdapter srAdapter2;
	public static String[] colArr2 = { "MA_NVGCS", "MA_KHANG", "MA_DDO",
		"MA_DVIQLY", "MA_GC", "MA_QUYEN", "MA_TRAM", "BOCSO_ID",
		"LOAI_BCS", "LOAI_CS", "TEN_KHANG", "DIA_CHI", "MA_NN", "SO_HO",
		"MA_CTO", "SERY_CTO", "HSN", "CS_CU", "TTR_CU", "SL_CU",
		"SL_TTIEP", "NGAY_CU", "CS_MOI", "TTR_MOI", "SL_MOI", "CHUOI_GIA",
		"KY", "THANG", "NAM", "NGAY_MOI", "NGUOI_GCS", "SL_THAO",
		"KIMUA_CSPK", "MA_COT", "CGPVTHD", "HTHUC_TBAO_DK", "DTHOAI_SMS",
		"EMAIL", "THOI_GIAN", "X", "Y", "SO_TIEN", "HTHUC_TBAO_TH", "TENKHANG_RUTGON",
		"TTHAI_DBO", "DU_PHONG", "TEN_FILE", "GHICHU", "TT_KHAC", "ID_SQLITE" };
	
	//textview trên lưới
	View currView ;
	TextView currTvSTT ;
	TextView currTv_MA_GC ;
	TextView currTv_MA_COT ;
	
	//textview thông tin
	TextView tv_SERY_CTO;
	TextView tv_MA_TRAM;
	TextView tv_MA_GC;
	TextView tv_MA_COT;
	TextView tv_TEN_KH;
	TextView tv_DIA_CHI;
	TextView tv_LOAI_BCS;
	EditText etxtSTT;
	
	Spinner spnChonSo;
	Button btnChangePosition;
	Button btnSaveRoute;
	Button btnSortingRoute;
	Button btnPrevPosition;
	
	public static int Selected_STT = -1;
	int idxPrev = -1;
//	int index = 0;
//	int index2 = 0;
	int index_save_select = 0;
	int index_save_input = 0;
	int stt = 1;
	SQLiteConnection connection;
	int size;
	String FileName;

	@SuppressLint("NewApi") @SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sorting_route);
		getActionBar().hide();
		comm = new Common();
		if(comm.checkDB()){
			connection = new SQLiteConnection(this, "ESGCS.s3db", Environment.getExternalStorageDirectory() +Common.DBFolderPath);
		}
		Display d = getWindowManager().getDefaultDisplay();
		size = d.getWidth() - 65;
		
		comm = new Common();
		isSaved = true;
		selectedIdx = -1;

		initViewObj();
		spnChonSo_GetData();
	}
	
	/**Khởi tạo các control
	 * @author TruongPV
	 */
	private void initViewObj(){
		//khởi tạo các control trên panel thông tin
		tv_SERY_CTO = (TextView) findViewById(R.id.tv_SERY_CTO);
		tv_MA_TRAM= (TextView) findViewById(R.id.tv_MA_TRAM);
		tv_MA_GC= (TextView) findViewById(R.id.tv_MA_GC);
		tv_MA_COT= (TextView) findViewById(R.id.tv_MA_COT);
		tv_TEN_KH= (TextView) findViewById(R.id.tv_TEN_KH);
		tv_DIA_CHI= (TextView) findViewById(R.id.tv_DIA_CHI);
		tv_LOAI_BCS= (TextView) findViewById(R.id.tv_LOAI_BCS);
		etxtSTT = (EditText) findViewById(R.id.etxtSTT);
		
		//khởi tạo các control khác
		spnChonSo = (Spinner) findViewById(R.id.spnChonSo);
		lsvSortingRoute = (ListView) findViewById(R.id.lsvSortingRoute);
		btnChangePosition = (Button) findViewById(R.id.btnChangePosition);
		btnSaveRoute = (Button) findViewById(R.id.btnSaveRoute);
		btnSortingRoute = (Button) findViewById(R.id.btnSortingRoute);
		btnPrevPosition = (Button) findViewById(R.id.btnPrevPosition);
		
		SetEnableView(false);
	}
	
	
	
	/**Sự kiện cho nút xóa lộ trình
	 * 
	 * @param view
	 */
	public void btnSortingRoute_Click(View view) {
		try{
			String fileName = mapSo.get(String.valueOf(spnChonSo.getSelectedItem()));
			fileName = comm.convertFile(fileName, connection);
//			String shortFileName = ReplaceStringByPattern(fileName.toUpperCase(Locale.ENGLISH),ext_pattern,"");
			// && connection.deleteDataRoute(shortFileName) != -1
			if(connection.deleteDataRoute(fileName) != -1){
				Common.ShowToast(SortingRouteActivity.this, "Xóa lộ trình thành công", Toast.LENGTH_LONG, size);
				stt = 1;
				LoadListViewSortingRoute();
			} else {
				Common.ShowToast(SortingRouteActivity.this, "Xóa lộ trình không thành công", Toast.LENGTH_LONG, size);
			}
		} catch(Exception ex) {
			ex.toString();
		}
	}
	
	/**Sự kiện cho nút chọn sổ
	 * 
	 * @param view
	 */
	public void btnChooseFile_Click(View view){
		if(comm.checkDB()){
			try{
				stt = 1;
				if(!isSaved){
					new AlertDialog.Builder(SortingRouteActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("Thông báo").setMessage("Chưa lưu sắp xếp cho sổ hiện tại. \nBạn có muốn lưu?")
					.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
	//								String FileNameSelected = mapSo.get(String.valueOf(spnChonSo.getSelectedItem()));
//									ArrayList<LinkedHashMap<String, String>> list = lsvDataSortingRoute;
									if(CreateSortingRouteFile(FileName, lsvDataSortingRoute)){
										comm.msbox("Thông báo", "Lưu sắp xếp lộ trình thành công", SortingRouteActivity.this);
										LoadListViewSortingRoute();
									} else {
										comm.msbox("Thông báo", "Lưu sắp xếp không thành công", SortingRouteActivity.this);
									}
								}
							}).setNegativeButton("Không", new DialogInterface.OnClickListener() {
	
								@Override
								public void onClick(DialogInterface dialog, int which) {
									isSaved = true;
									LoadListViewSortingRoute();
								}
							}).show();
				}else{
					LoadListViewSortingRoute();
				}
			}catch(Exception ex){
				comm.msbox("Thông báo", "Không thể chọn file + " + ex.toString(), SortingRouteActivity.this);
			}
		}
	}
	
	/**Sự kiện cho nút đổi vị trí công tơ
	 * 
	 * @param view
	 */
	public void btnChangePosition_Click(View view){
		try{
			String strSTT = etxtSTT.getText().toString().trim();
			int idx_from = -1;
			int idx_to = -1;
			if(strSTT.length() == 0){
				comm.msbox("Thông báo", "Chưa nhập vị trí cần chuyển", SortingRouteActivity.this);
				return;
			}
			int idx_new = Integer.parseInt(strSTT) - 1; // vị trí mới trên listview sắp xếp
			if(idx_new + 1> lsvDataSortingRoute2.size()){
				comm.msbox("Thông báo", "Vị trí phải nhỏ hơn tổng số công tơ", SortingRouteActivity.this);
				return;
			}
			if(idx_new + 1 == SortingRouteActivity.Selected_STT){
				return;
			}

			// lấy ra sery của cto đang chọn
			String selected_sery = lsvDataSortingRoute2.get(selectedIdx).get("SERY_CTO").toString();
						
			// lấy ra tất cả bộ chỉ số của cto
			ArrayList<LinkedHashMap<String, String>> lsvDataTemp = new ArrayList<LinkedHashMap<String, String>>();
			for(int i = 0 ; i < lsvDataSortingRoute.size(); i++){
				if(selected_sery.equals(lsvDataSortingRoute.get(i).get("SERY_CTO"))){
					lsvDataTemp.add(lsvDataSortingRoute.get(i)) ;
				}
			}
			
			if(lsvDataTemp.size() > 0){
				// lấy ra vị trí hiện tại của cto trên sổ
				idx_from = Integer.parseInt(lsvDataTemp.get(0).get("STT_ROUTE").toString()) - 1;
				// lấy ra vị trí đích của cto trên sổ
				idx_to = GetIdxBCSOfCto(lsvDataSortingRoute2.get(idx_new).get("SERY_CTO").toString(), "first"); //Integer.parseInt(lsvDataSortingRoute2.get(new_position_stt-1).get("STT_ROUTE").toString()) - 1;
			
				// chuyển cto được chọn thế chỗ của cto đích
				// vị trí đích < vị trí ban đầu
				int so_bcs = lsvDataTemp.size();
				if(idx_to < idx_from){
					for(int n = 0 ; n < so_bcs; n++){
						idx_from = Integer.parseInt(lsvDataTemp.get(n).get("STT_ROUTE").toString()) - 1;
						move(idx_from , idx_to + n);
					}
				}
				// vị trí đích > vị trí ban đầu
				else if(idx_to > idx_from){
					idx_to = GetIdxBCSOfCto(lsvDataSortingRoute2.get(idx_new).get("SERY_CTO").toString(), "last");
					for(int m = 0; m < so_bcs ; m++){
						idx_from = Integer.parseInt(lsvDataTemp.get(0).get("STT_ROUTE").toString()) - 1;
						move(idx_from , idx_to);
					}
				}

				// đổi vị trí trên lưới
				move2(selectedIdx, idx_new);
			}
			isSaved = false;
		}catch(Exception e){
			comm.msbox("Thông báo", "Vị trí nhập vào không hợp lệ ", SortingRouteActivity.this);
		}
		
	}
	
	/**
	 * Lấy index của bộ chỉ số đầu tiên của cto trên sổ
	 * @param sery_cto
	 * @return
	 */
	private int GetIdxBCSOfCto(String sery_cto, String idx_type){
		int first_idx = -1;
		int last_idx = -1;
		int count = 0;
		for(int i = 0 ; i < lsvDataSortingRoute.size(); i++){
			if(sery_cto.equals(lsvDataSortingRoute.get(i).get("SERY_CTO"))){
				
				// nếu chưa lấy đc vị trí đầu
				if(first_idx == -1){
					first_idx = Integer.parseInt(lsvDataSortingRoute.get(i).get("STT_ROUTE")) - 1; // lưu vị trí đầu
				}
				
				// nếu chỉ lấy vị trí đầu
				if(idx_type.equals("first")){
					break;
				}
				
				last_idx = Integer.parseInt(lsvDataSortingRoute.get(i).get("STT_ROUTE")) - 1; // lưu vị trí cuối
				count++;
				
				// nếu lấy được bộ chỉ số cuối của cto thì thoát
				if(count >= 5){
					break;
				}
			}
		}
		if(idx_type.equals("first"))
			return first_idx;
		else
			return last_idx;
	}

	/**Chuyển vị trí trên list hiển thị
	 */
	private void move2(int idx_from, int idx_to ){
		View new_position_view = srAdapter2.getView( idx_to, null, null);
		SortListWhenChangePosition2(idx_from, idx_to);
		
		// cập nhật giá trị trên listview
		((BaseAdapter) lsvSortingRoute.getAdapter()).notifyDataSetChanged();
		lsvSortingRoute.invalidateViews();
		ListViewSortingRouteSelectedItem(idx_to, new_position_view, true);
	}
	
	/**Chuyển vị trí trên file sẽ lưu
	 */
	private void move(int idx_from, int idx_to ){
//		new_position_view = srAdapter.getView( idx_to, null, null);
		SortListWhenChangePosition(idx_from, idx_to );
	}
	
	/**Sự kiện cho nút chuyển đến vị trí trước đó
	 * 
	 * @param view
	 */
	public void btnPrevPosition_Click(View view){
		try
		{
			if(idxPrev == -1){
				return;
			}
			int idxTemp = idxPrev;
			idxPrev = selectedIdx;
			selectedIdx=idxTemp;

			View prev_view = srAdapter2.getView( selectedIdx, null, null);
			ListViewSortingRouteSelectedItem(selectedIdx, prev_view, true);
		}catch(Exception ex){
			comm.msbox("Thông báo", "Vị trí trước đó không hợp lệ", SortingRouteActivity.this);
		}
	}
	
	/**Sự kiện cho nút lưu sắp xếp
	 * 
	 * @param view
	 */
	public void btnSaveRoute_Click(View view) {
		try{
			String FileNameSelected = mapSo.get(String.valueOf(spnChonSo.getSelectedItem()));
			if(CreateSortingRouteFile(FileNameSelected, lsvDataSortingRoute)){
				comm.msbox("Thông báo", "Lưu sắp xếp lộ trình thành công", SortingRouteActivity.this);
			}else{
				comm.msbox("Thông báo", "Lưu sắp xếp không thành công", SortingRouteActivity.this);
			}
		}catch(Exception ex){
			comm.msbox("Thông báo", "Không thể lưu sắp xếp", SortingRouteActivity.this);
		}
	}

	/**Load dữ liệu của sổ lên listview
	 *  
	 */
	@SuppressWarnings("unchecked")
	private void LoadListViewSortingRoute(){
		FileName = mapSo.get(String.valueOf(spnChonSo.getSelectedItem()));
		lsvDataSortingRoute = new ArrayList<LinkedHashMap<String,String>>();
		lsvDataSortingRoute2 = new ArrayList<LinkedHashMap<String,String>>();
		lsvDataSortingRoute = GetGCSDataFromFile();
		lsvDataSortingRoute2 = GetGCSDataFromFile2();
		srAdapter = new SortingRouteAdapter(this, (ArrayList<LinkedHashMap<String, String>>) lsvDataSortingRoute.clone(), R.layout.listview_sorting_route, sorting_route_col_name, sorting_route_col_id);
		srAdapter2 = new SortingRouteAdapter(this, (ArrayList<LinkedHashMap<String, String>>) lsvDataSortingRoute2.clone(), R.layout.listview_sorting_route, sorting_route_col_name, sorting_route_col_id);

		lsvSortingRoute.setAdapter(srAdapter2);

		// gán sự kiện khi click vào 1 dòng
		lsvSortingRoute.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (SortingRouteActivity.this.selectedIdx < 0) {
					SetEnableView(true);
				} else {
					// Bỏ selected item cũ
					for (int i = 0; i < parent.getChildCount(); i++) {
						View old_view = parent.getChildAt(i);
						old_view.setBackgroundColor(ConstantVariables.NO_SELECTED_COLOR);
					}
				}
				ListViewSortingRouteSelectedItem(position, view, false);
			}
		});

		SetEnableView(true);
		idxPrev = -1;
		selectedIdx = 0;
		Selected_STT = 1;
		View new_position_view = lsvSortingRoute.getAdapter().getView(selectedIdx, null, null);
		ListViewSortingRouteSelectedItem(selectedIdx, new_position_view, true);
	}
	
	/**Tạo file sắp xếp lộ trình*/
	private boolean CreateSortingRouteFile(String fileName, ArrayList<LinkedHashMap<String,String>> gcs_data) {
		
//		String shortFileName = ReplaceStringByPattern(fileName.toUpperCase(Locale.ENGLISH),ext_pattern,"");
		String shortFileName = fileName;
//		Common.ShowToast(SortingRouteActivity.this, fileName, Toast.LENGTH_LONG, 500);
		try {
			connection.deleteDataRoute(fileName);
			connection.deleteDataRoute(shortFileName);
//			if(!connection.checkDataExist(common.convertFileName(fileName))){
			for (int i = 0; i < gcs_data.size(); i++) {
				connection.insertLoTrinh(gcs_data.get(i).get("SERY_CTO"), Integer.parseInt(gcs_data.get(i).get("STT_ORG")), 
						Integer.parseInt(gcs_data.get(i).get("STT_ROUTE")), gcs_data.get(i).get("LOAI_BCS"), 
						shortFileName);
			}
//			} else {
//				for (int i = 0; i < gcs_data.size(); i++) {
//					connection.updateLoTrinh(Integer.parseInt(gcs_data.get(i).get("ID_ROUTE")),gcs_data.get(i).get("SERY_CTO"), Integer.parseInt(gcs_data.get(i).get("STT_ORG")), Integer.parseInt(gcs_data.get(i).get("STT_ROUTE")), gcs_data.get(i).get("LOAI_BCS"), gcs_data.get(i).get("TEN_FILE"));
//				}
//			}
			isSaved = true;
			return true;
		} catch (Exception e) {
			isSaved = false;
			return false;
		}
	}
	
	/**Lấy dữ liệu đổ vào combobox chọn sổ
	 * 
	 */
	public void spnChonSo_GetData() {
		if(comm.checkDB()){
			mapSo = new LinkedHashMap<String, String>();
			mapSo2 = new LinkedHashMap<String, String>();
			try {
				
				List<String> list = new ArrayList<String>();
	
				Cursor c = connection.getAllDataSoInData();
				if(c.moveToFirst()){
					do{
						String fileName = c.getString(0);
						String shortName = c.getString(0);
//						String fileName = Common.convertFileName(c.getString(3));
//						String shortName = fileName.substring(0,fileName.length() - 4);
						mapSo.put(shortName, fileName);
						list.add(shortName);
					} while(c.moveToNext());
				}
				
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
				spnChonSo.setAdapter(dataAdapter);
			} catch (Exception ex) {
				comm.msbox("Thông báo", "Không lấy được dữ liệu", SortingRouteActivity.this);
			}
		}
	}
	
	/**Lấy dữ liệu trong sổ để dùng sắp xếp lộ trình GCS
	 * @return
	 */
	public ArrayList<LinkedHashMap<String,String>> GetGCSDataFromFile(){
		// Khoi tao Array chứa các row
		ArrayList<LinkedHashMap<String, String>> ListViewData = new ArrayList<LinkedHashMap<String, String>>();
		try {
			String FileNameSelected = mapSo.get(String.valueOf(spnChonSo.getSelectedItem()));
			Cursor c_lotrinh = connection.getDataRouteByFileName(comm.convertFile(FileNameSelected, connection));
			Cursor c = connection.getAllDataGCSsoft(FileNameSelected);
			if(!c.moveToFirst() || !c_lotrinh.moveToFirst()){
				c = connection.getAllDataGCS(FileNameSelected);
			}
			int i = 0;
			ArrayList<String> arr_ID = new ArrayList<String>();
			if(c.moveToFirst()){
				do{
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					// tạo cột STT_ORG
					map.put("STT_ORG", (i + 1) + "");
					map.put("STT_ROUTE", (i + 1) + "");
					for (int j = 1; j < ConstantVariables.COLARR.length - 4; j++) {
						map.put(ConstantVariables.COLARR[j], c.getString(j));
					}
					ListViewData.add(map);
					arr_ID.add(c.getString(50));
					i++;
				} while(c.moveToNext());
			}
			Cursor c2 = connection.getAllDataGCS(FileNameSelected);
			if(c2.moveToFirst()){
				if(c.getCount() != c2.getCount()){
					do {
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						if(!arr_ID.contains(c2.getString(50))){
							map.put("STT_ORG", (i + 1) + "");
							map.put("STT_ROUTE", (i + 1) + "");
							for (int j = 1; j < ConstantVariables.COLARR.length - 4; j++) {
								map.put(ConstantVariables.COLARR[j], c2.getString(j));
							}
							ListViewData.add(map);
							i++;
						}
					} while(c2.moveToNext());
				}
			}
			return ListViewData;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**Lấy dữ liệu trong sổ để dùng sắp xếp lộ trình GCS
	 * @return
	 */
	public ArrayList<LinkedHashMap<String,String>> GetGCSDataFromFile2(){
		// Khoi tao Array chứa các row
		ArrayList<LinkedHashMap<String, String>> ListViewData = new ArrayList<LinkedHashMap<String, String>>();
		try {
			String FileNameSelected = mapSo.get(String.valueOf(spnChonSo.getSelectedItem()));
			Cursor c_lotrinh = connection.getDataRouteByFileName(comm.convertFile(FileNameSelected, connection));
			Cursor c = connection.getAllDataGCSsoft(FileNameSelected);
			if(!c.moveToFirst() || !c_lotrinh.moveToFirst()){
				c = connection.getAllDataGCS(FileNameSelected);
			}
			int i = 0;
			ArrayList<String> arr_ID = new ArrayList<String>();
			if(c.moveToFirst()){
				do{
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	
					map.put("STT", (i + 1) + "");
					
					for (int j = 1; j < ConstantVariables.COLARR.length - 4; j++) {
						map.put(ConstantVariables.COLARR[j], c.getString(j));
					}
					arr_ID.add(c.getString(50));
					String BCS_END = "";
					String so_cto = map.get("SERY_CTO");
					int dem = connection.getCountDataGCSBySery(so_cto, FileNameSelected);
					
					if(dem == 1) {
						map.put("STT_ORG", stt + "");
						map.put("STT_ROUTE", stt + "");
						ListViewData.add(map);
						stt++;
					} else if(dem == 2){
						if(map.get("LOAI_BCS").equals("VC")){
							map.put("STT_ORG", stt + "");
							map.put("STT_ROUTE", stt + "");
							ListViewData.add(map);
							stt++;
						}
					} else {
						Cursor c1 = connection.getAllDataGCSBySery(so_cto);
						if(c1.moveToFirst()){
							BCS_END = c1.getString(0);
						}
						if(map.get("LOAI_BCS").equals("KT") || map.get("LOAI_BCS").equals(BCS_END)){
							map.put("STT_ORG", stt + "");
							map.put("STT_ROUTE", stt + "");
							ListViewData.add(map);
							stt++;
						}
					}
					
					i++;
				} while(c.moveToNext());
			}
			Cursor c2 = connection.getAllDataGCS(FileNameSelected);
			if(c2.moveToFirst()){
				if(c.getCount() != c2.getCount()){
					do {
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						if(!arr_ID.contains(c2.getString(50))){
							map.put("STT", (i + 1) + "");
							
							for (int j = 1; j < ConstantVariables.COLARR.length - 4; j++) {
								map.put(ConstantVariables.COLARR[j], c2.getString(j));
							}
							String BCS_END = "";
							String so_cto = map.get("SERY_CTO");
							int dem = connection.getCountDataGCSBySery(so_cto, FileNameSelected);
							
							if(dem == 1) {
								map.put("STT_ORG", stt + "");
								map.put("STT_ROUTE", stt + "");
								ListViewData.add(map);
								stt++;
							} else if(dem == 2){
								if(map.get("LOAI_BCS").equals("VC")){
									map.put("STT_ORG", stt + "");
									map.put("STT_ROUTE", stt + "");
									ListViewData.add(map);
									stt++;
								}
							} else {
								Cursor c1 = connection.getAllDataGCSBySery(so_cto);
								if(c1.moveToFirst()){
									BCS_END = c1.getString(0);
								}
								if(map.get("LOAI_BCS").equals("KT") || map.get("LOAI_BCS").equals(BCS_END)){
									map.put("STT_ORG", stt + "");
									map.put("STT_ROUTE", stt + "");
									ListViewData.add(map);
									stt++;
								}
							}
							
							i++;
						}
					} while(c2.moveToNext());
				}
			}
			return ListViewData;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**Lấy thông tin dòng được chọn
	 * 
	 * @param position vị trí dòng được chọn
	 * @param view dòng được chọn
	 * @param isUp xác định muốn chuyển dòng được chọn xuất hiện ở vị trí thứ 2
	 */
	private void ListViewSortingRouteSelectedItem(int position, View view, boolean isUp){

		if(isUp){
			// check item
			lsvSortingRoute.setItemChecked(position, true);
	
			// move view (chuyển dòng trước dòng hiện tại lên đầu)
			lsvSortingRoute.setSelection(position - 1 >= 0 ? position - 1: 0);
		}
		
		if (view == null && Selected_STT > 0) {
			view = srAdapter.getView(Selected_STT - 1, null, null);
		}

		idxPrev = selectedIdx;
		SortingRouteActivity.this.selectedIdx = position;
		SortingRouteActivity.Selected_STT = Integer.parseInt(((TextView) view
				.findViewById(R.id.tv_STT)).getText().toString());
		
		try{
			selectedRow = lsvDataSortingRoute2.get(SortingRouteActivity.Selected_STT - 1);
		} catch(Exception ex){
			SortingRouteActivity.Selected_STT = 1;
			selectedRow = lsvDataSortingRoute2.get(SortingRouteActivity.Selected_STT - 1);
		}

		view.setBackgroundColor(ConstantVariables.SELECTED_COLOR);

		currView = view;
		currTvSTT = (TextView) view.findViewById(R.id.tv_STT);
		currTv_MA_GC = (TextView) view.findViewById(R.id.tv_MA_GC);
		currTv_MA_COT = (TextView) view.findViewById(R.id.tv_MA_COT);
		
		tv_SERY_CTO.setText(selectedRow.get("SERY_CTO"));
		tv_MA_TRAM.setText(selectedRow.get("MA_TRAM"));
		tv_MA_GC.setText(selectedRow.get("MA_GC"));
		tv_MA_COT.setText(selectedRow.get("MA_COT"));
		tv_TEN_KH.setText(selectedRow.get("TEN_KHANG"));
		tv_DIA_CHI.setText(selectedRow.get("DIA_CHI"));
		tv_LOAI_BCS.setText(selectedRow.get("LOAI_BCS"));
		
		etxtSTT.setText(currTvSTT.getText());
		etxtSTT.requestFocus();
		etxtSTT.selectAll();
	}
	
	/**enable/disable 1 số control 
	 * 
	 * @param status
	 */
	private void SetEnableView(boolean status){
		etxtSTT.setEnabled(status);
		btnSaveRoute.setEnabled(status);
		btnSortingRoute.setEnabled(status);
		btnChangePosition.setEnabled(status);
		btnPrevPosition.setEnabled(status);
	}
	
	/**Sắp xếp list lsvDataSortingRoute khi thay đổi vị trí
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void SortListWhenChangePosition(int from, int to) {

		if (from == to)
			return;

		LinkedHashMap<String,String> item_temp = (LinkedHashMap<String, String>) lsvDataSortingRoute.get(from).clone();

		// chuyển item từ dưới lên trên
		if (from > to) {
			// chuyển các item phía dưới vị trí đích của item được chọn xuống dưới
			for (int i = from; i > to; i--) {
				SwapItemList(lsvDataSortingRoute.get(i), lsvDataSortingRoute.get(i-1));
			}
		}
		// chuyển item được chọn từ trên xuống dưới
		else if (from < to) {

			// chuyển các item phía trên vị trí đích của item được chọn lên trên
			for (int i = from; i < to; i++) {
				SwapItemList(lsvDataSortingRoute.get(i), lsvDataSortingRoute.get(i+1));
			}
		}

		// chuyển item được chọn đến vị trí đích
		SwapItemList(lsvDataSortingRoute.get(to), item_temp);
	}
	
	/**Sắp xếp list lsvDataSortingRoute khi thay đổi vị trí
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void SortListWhenChangePosition2(int from, int to) {

		if (from == to)
			return;

		LinkedHashMap<String,String> item_temp = (LinkedHashMap<String, String>) lsvDataSortingRoute2.get(from).clone();

		// chuyển item từ dưới lên trên
		if (from > to) {
			// chuyển các item phía dưới vị trí đích của item được chọn xuống dưới
			for (int i = from; i > to; i--) {
				SwapItemList(lsvDataSortingRoute2.get(i), lsvDataSortingRoute2.get(i-1));
			}
		}
		// chuyển item được chọn từ trên xuống dưới
		else if (from < to) {

			// chuyển các item phía trên vị trí đích của item được chọn lên trên
			for (int i = from; i < to; i++) {
				SwapItemList(lsvDataSortingRoute2.get(i), lsvDataSortingRoute2.get(i+1));
			}
		}

		// chuyển item được chọn đến vị trí đích
		SwapItemList(lsvDataSortingRoute2.get(to), item_temp);
	}
	
	/**Đổi vị trí giữa 2 item list
	 * 
	 * @param item1
	 * @param item2
	 */
	@SuppressWarnings("unchecked")
	private void SwapItemList(LinkedHashMap<String, String> item1, LinkedHashMap<String, String> item2 ){
		LinkedHashMap<String, String> tempData = (LinkedHashMap<String, String>) item1.clone();
		for(int i = 1; i < ConstantVariables.COLARR.length - 24; i++){
			if(item1.containsKey(ConstantVariables.COLARR[i]) && !ConstantVariables.COLARR[i].equals("STT_ROUTE")){
				item1.put(ConstantVariables.COLARR[i], item2.get(ConstantVariables.COLARR[i]) == null ? null : item2.get(ConstantVariables.COLARR[i]).toString());
				item2.put(ConstantVariables.COLARR[i], tempData.get(ConstantVariables.COLARR[i]) == null ? null : tempData.get(ConstantVariables.COLARR[i]).toString());
			}
		}
	}

//	private String ReplaceStringByPattern(String strData, String strRegEx, String strReplace){
//	    String retVal = strData.replaceFirst(strRegEx, strReplace);
//	    return retVal;
//	}
	
	
	
	
	
	
	
	
	
}
