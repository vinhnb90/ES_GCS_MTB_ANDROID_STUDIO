package esolutions.com.gcs_svn_old.com.camera.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import esolutions.com.gcs_svn_old.R;

public class KyNhanAdapter extends ArrayAdapter<LinkedHashMap<String, String>>  {

	private final Context context;
	@SuppressWarnings("unused")
	private ArrayList<LinkedHashMap<String, String>> filtered;
	public String filter_col = null;

	public KyNhanAdapter(Context context,
			ArrayList<LinkedHashMap<String, String>> list_view, int resource) {
		super(context, R.layout.listview_kynhan, list_view);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflater.inflate(R.layout.listview_kynhan, null);
			 
			LinkedHashMap<String, String> val = getItem(position);
			TextView tv_STT_csbt = (TextView)convertView.findViewById(R.id.tv_STT_csbt);
			TextView tv_TenSo_csbt = (TextView)convertView.findViewById(R.id.tv_TenSo_csbt);
			TextView tv_HoTenKH_csbt = (TextView)convertView.findViewById(R.id.tv_HoTenKH_csbt);
			TextView tv_SO_CTO_csbt = (TextView)convertView.findViewById(R.id.tv_SO_CTO_csbt);
			TextView tv_CS_CU_csbt = (TextView)convertView.findViewById(R.id.tv_CS_CU_csbt);
			TextView tv_CS_MOI_csbt = (TextView)convertView.findViewById(R.id.tv_CS_MOI_csbt);
			TextView tv_SL_CU_csbt = (TextView)convertView.findViewById(R.id.tv_SL_CU_csbt);
			TextView tv_SL_MOI_csbt = (TextView)convertView.findViewById(R.id.tv_SL_MOI_csbt);
			TextView tv_DIA_CHI_csbt = (TextView)convertView.findViewById(R.id.tv_DIA_CHI_csbt);
			TextView tv_GHI_CHU_csbt = (TextView)convertView.findViewById(R.id.tv_GHI_CHU_csbt);
//			ImageView iv_Chu_Ky = (ImageView)convertView.findViewById(R.id.iv_Chu_Ky);
			
			tv_STT_csbt.setText(val.get("STT"));
			tv_TenSo_csbt.setText(val.get("TEN_SO"));
			tv_HoTenKH_csbt.setText(val.get("TEN_KHANG"));
			tv_SO_CTO_csbt.setText(val.get("SERY_CTO"));
			tv_CS_CU_csbt.setText(val.get("CS_CU"));
			tv_CS_MOI_csbt.setText(val.get("CS_MOI"));
			tv_SL_CU_csbt.setText(val.get("SL_CU"));
			tv_SL_MOI_csbt.setText(val.get("SL_MOI"));
			tv_DIA_CHI_csbt.setText(val.get("DIA_CHI"));
			tv_GHI_CHU_csbt.setText(val.get("GHICHU"));
//			String ck = val.get("CHU_KY");
//			iv_Chu_Ky.setImageBitmap(Common.decodeBase64(ck));
			return convertView;
		} catch(Exception ex) {
			return null;
		}
	}
}
