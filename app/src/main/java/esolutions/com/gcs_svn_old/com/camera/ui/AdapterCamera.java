package esolutions.com.gcs_svn_old.com.camera.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.ConstantVariables;

@SuppressLint("DefaultLocale")
public class AdapterCamera extends ArrayAdapter<LinkedHashMap<String, String>>{

	private final Context context;
	@SuppressWarnings("unused")
	private List<LinkedHashMap<String, String>> filteredList = null;
	private ArrayList<LinkedHashMap<String, String>> filtered;
	private Filter filter;
	public String filter_col = null;
	Common comm = new Common();

	public AdapterCamera(Context context, 
			ArrayList<LinkedHashMap<String, String>> list_view, int resource) {

		super(context, R.layout.listview_camera, list_view);
		this.context = context;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressWarnings("deprecation")
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = inflater.inflate(R.layout.listview_camera, null);

		try{
			LinkedHashMap<String, String> val = getItem(position);
			TextView tvTenKH = (TextView)convertView.findViewById(R.id.tvTenKH);
			TextView tvSoCto = (TextView)convertView.findViewById(R.id.tvSoCto);
			TextView tvCScu = (TextView)convertView.findViewById(R.id.tvCScu);
			TextView tvMaGC = (TextView)convertView.findViewById(R.id.tvMaGC);
			TextView tvCSmoi = (TextView)convertView.findViewById(R.id.tvCSmoi);
			TextView tvMaTram = (TextView)convertView.findViewById(R.id.tvMaTram);
			TextView tvSLcu = (TextView)convertView.findViewById(R.id.tvSLcu);
			TextView tvSLthao = (TextView)convertView.findViewById(R.id.tvSLthao);
			TextView tvSLmoi = (TextView)convertView.findViewById(R.id.tvSLmoi);
			TextView tvMaCot = (TextView)convertView.findViewById(R.id.tvMaCot);
			TextView tvDienTT = (TextView)convertView.findViewById(R.id.tvDienTT);
			TextView tvBoCS = (TextView)convertView.findViewById(R.id.tvBoCS);
			TextView tvTTRmoi = (TextView)convertView.findViewById(R.id.tvTTRmoi);
			TextView tvDiaChi = (TextView)convertView.findViewById(R.id.tvDiaChi);
			TextView tvSTT = (TextView)convertView.findViewById(R.id.tvSTT);
//			LinearLayout lnWidth = (LinearLayout)convertView.findViewById(R.id.lnWidth);
//			lnWidth.getLayoutParams().width = Activity_Camera.size + 65;
			
			tvTenKH.setText(val.get("TEN_KHANG"));
			tvSoCto.setText(val.get("SERY_CTO"));
			tvCScu.setText(val.get("CS_CU"));
			tvMaGC.setText(val.get("MA_GC"));
			tvCSmoi.setText(val.get("CS_MOI"));
			tvMaTram.setText(val.get("MA_TRAM"));
			tvSLcu.setText(val.get("SL_CU"));
			tvSLthao.setText(val.get("SL_THAO"));
			tvSLmoi.setText(val.get("SL_MOI"));
			tvMaCot.setText(val.get("MA_COT"));
			tvDienTT.setText(val.get("DTT"));
			tvBoCS.setText(val.get("LOAI_BCS"));
			tvTTRmoi.setText(val.get("TTR_MOI"));
			tvDiaChi.setText(val.get("DIA_CHI"));
			tvSTT.setText(val.get("STT"));
			
			if(position == Activity_Camera.selected_index){
				int sdk = android.os.Build.VERSION.SDK_INT;
				if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					convertView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.border_listitem_gcs_cyan));
				} else {
					convertView.setBackground(getContext().getResources().getDrawable(R.drawable.border_listitem_gcs_cyan));
				}
//				convertView.setBackgroundColor(Color.CYAN);
			} else {
				if(comm.isSavedRow(val.get("TTR_MOI"), val.get("CS_MOI"))){
					convertView.setBackgroundColor(Color.parseColor("#999999"));
				}
				else{
					convertView.setBackgroundColor(ConstantVariables.NO_SELECTED_COLOR);
				}
			}
		} catch(Exception ex) {
			comm.msbox("Lỗi", "Lỗi hiển thị dữ liệu", getContext());
		}
		
		return convertView;
	}

//	private void ChangeValOfRow(View rowView, LinkedHashMap<String, String> val) {
//		// Lấy các control của dòng
//		for(int i = 0 ; i < Common.gcs_cols_pager.gcs_tbl_data.length; i++){
//			TextView tv = (TextView) rowView.findViewById(Common.gcs_cols_pager.gcs_tbl_data[i].tv_container_id);
//			tv.setText(val.get(Common.gcs_cols_pager.gcs_tbl_data[i].DataName));
//		}
//	}
	
	@Override
	public Filter getFilter() {
		if (filter == null)
			filter = new GCS_Filter();
		return filter;
	}

	private class GCS_Filter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// NOTE: this function is *always* called from a background thread,
			// and not the UI thread.
			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if (constraint != null && constraint.toString().length() > 0) {
				ArrayList<LinkedHashMap<String, String>> filt = new ArrayList<LinkedHashMap<String, String>>();

				for (int i = 0, l = Activity_Camera.arrCustomer.size(); i < l; i++) {
					LinkedHashMap<String, String> lst = Activity_Camera.arrCustomer.get(i);

					if (AdapterCamera.this.filter_col != null) {
						String val = null;
						// nếu ko phải tìm các kh chưa ghi
						if (!AdapterCamera.this.filter_col.equals("CHUA_GHI") && !AdapterCamera.this.filter_col.equals("DA_GHI")) {
							val = lst.get(AdapterCamera.this.filter_col);
							if(val != null ){
								val = val.toLowerCase();
							}
						}
						// kiểm tra theo tên ko dấu và có dấu
						if (AdapterCamera.this.filter_col.equals("TEN_KHANG") || AdapterCamera.this.filter_col.equals("DIA_CHI")) {
							String ten_bo_dau = comm.VietnameseTrim(val);
							if (val.toLowerCase().contains(constraint) || ten_bo_dau.toLowerCase().contains(
									comm.VietnameseTrim(constraint.toString().toLowerCase()))) {
								filt.add(lst);
							}
						} else if (AdapterCamera.this.filter_col.equals("CHUA_GHI")) {
							String valTTR_MOI = lst.get("TTR_MOI");          
							String valCS_MOI = lst.get("CS_MOI");
							if ((valTTR_MOI == null || valTTR_MOI.trim().equals(""))
									&& (valCS_MOI == null
											|| valCS_MOI.trim().length() == 0 || Float.parseFloat(valCS_MOI) == 0)) {
								filt.add(lst);
							}
						} else if (val != null && val.contains(constraint)) {
							filt.add(lst);
						}
					}
				}
				result.count = filt.size();
				result.values = filt;
				Activity_Camera.arrCustomer_Filter = filt;
				if(constraint.toString().equals("")){
					Activity_Camera.arrCustomer_Filter = Activity_Camera.arrCustomer;
				}
			} else {

				synchronized (this) {
					result.values = Activity_Camera.arrCustomer;
					result.count = Activity_Camera.arrCustomer.size();
				}

			}
			notifyDataSetInvalidated();
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			try{
				// NOTE: this function is *always* called from the UI thread.
				AdapterCamera.this.filtered = (ArrayList<LinkedHashMap<String, String>>) results.values;
				AdapterCamera.this.notifyDataSetChanged();
				clear();
				if(AdapterCamera.this.filtered == null)
					AdapterCamera.this.filtered = Activity_Camera.arrCustomer_Filter;
				for (int i = 0, l = AdapterCamera.this.filtered.size(); i < l; i++)
					AdapterCamera.this.add(AdapterCamera.this.filtered.get(i));
				notifyDataSetInvalidated();
			} catch(Exception ex) {
				ex.toString();
//				try{
//					AdapterPager.this.notifyDataSetChanged();
//					clear();
//					int l = Activity_Camera.arrCustomer_Filter.size();
//					for (int i = 0; i < l; i++)
//						AdapterPager.this.add(Activity_Camera.arrCustomer_Filter.get(i));
//					AdapterPager.this.notifyDataSetInvalidated();
//				} catch(Exception ex2) {
//					ex2.toString();
//				}
			}
		}
	}

}
