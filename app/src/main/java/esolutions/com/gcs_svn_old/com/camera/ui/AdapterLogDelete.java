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

public class AdapterLogDelete extends ArrayAdapter<LinkedHashMap<String, String>>  {

	private final Context context;
	@SuppressWarnings("unused")
	private ArrayList<LinkedHashMap<String, String>> filtered;
	public String filter_col = null;

	public AdapterLogDelete(Context context,
			ArrayList<LinkedHashMap<String, String>> list_view, int resource) {
		super(context, R.layout.listview_log_delete, list_view);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflater.inflate(R.layout.listview_log_delete, null);
		}
		 
		LinkedHashMap<String, String> val = getItem(position);
		TextView tv_STT = (TextView)convertView.findViewById(R.id.tv_Stt);
		TextView tv_MaQuyen = (TextView)convertView.findViewById(R.id.tv_MaQuyen);
		TextView tv_SeryCto = (TextView)convertView.findViewById(R.id.tv_SeryCto);
		TextView tv_LyDo = (TextView)convertView.findViewById(R.id.tv_LyDo);
		TextView tv_CsXoa = (TextView)convertView.findViewById(R.id.tv_CsXoa);
		TextView tv_Ngay = (TextView)convertView.findViewById(R.id.tv_Ngay);
		
		tv_STT.setText(val.get("STT"));
		tv_MaQuyen.setText(val.get("MA_QUYEN"));
		tv_SeryCto.setText(val.get("SERY_CTO"));
		tv_LyDo.setText(val.get("LY_DO"));
		tv_CsXoa.setText(val.get("CS_XOA"));
		tv_Ngay.setText(val.get("NGAY_XOA"));

		return convertView;
	}
}
