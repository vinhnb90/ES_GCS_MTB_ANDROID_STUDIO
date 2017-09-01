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

public class THTTBTAdapter extends ArrayAdapter<LinkedHashMap<String, String>>  {

	private final Context context;
	@SuppressWarnings("unused")
	private ArrayList<LinkedHashMap<String, String>> filtered;
	public String filter_col = null;

	public THTTBTAdapter(Context context,
			ArrayList<LinkedHashMap<String, String>> list_view, int resource) {
		super(context, R.layout.listview_thttbt, list_view);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listview_thttbt, null);
		}
		
		LinkedHashMap<String, String> val = getItem(position);
		TextView tv_STT_ttbt = (TextView)convertView.findViewById(R.id.tv_STT_ttbt);
		TextView tv_TenSo_ttbt = (TextView)convertView.findViewById(R.id.tv_TenSo_ttbt);
		TextView tv_HoTenKH_ttbt = (TextView)convertView.findViewById(R.id.tv_HoTenKH_ttbt);
		TextView tv_SO_CTO_ttbt = (TextView)convertView.findViewById(R.id.tv_SO_CTO_ttbt);
		TextView tv_CS_MOI_ttbt = (TextView)convertView.findViewById(R.id.tv_CS_MOI_ttbt);
		TextView tv_TT_MOI_ttbt = (TextView)convertView.findViewById(R.id.tv_TT_MOI_ttbt);
		TextView tv_DIA_CHI_ttbt = (TextView)convertView.findViewById(R.id.tv_DIA_CHI_ttbt);
		TextView tv_GHI_CHU_ttbt = (TextView)convertView.findViewById(R.id.tv_GHI_CHU_ttbt);
		
		tv_STT_ttbt.setText(val.get("STT"));
		tv_TenSo_ttbt.setText(val.get("TEN_SO"));
		tv_HoTenKH_ttbt.setText(val.get("TEN_KHANG"));
		tv_SO_CTO_ttbt.setText(val.get("SERY_CTO"));
		tv_CS_MOI_ttbt.setText(val.get("CS_MOI"));
		tv_TT_MOI_ttbt.setText(val.get("TTR_MOI"));
		tv_DIA_CHI_ttbt.setText(val.get("DIA_CHI"));
		tv_GHI_CHU_ttbt.setText(val.get("GHICHU"));

		return convertView;
	}
}
