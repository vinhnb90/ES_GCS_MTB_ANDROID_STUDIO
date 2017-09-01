package esolutions.com.gcs_svn_old.com.camera.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.ConstantVariables;

@SuppressLint("DefaultLocale")
public class SortingRouteAdapter extends ArrayAdapter<LinkedHashMap<String, String>> {

	private final Context context;

	public SortingRouteAdapter(Context context,
			ArrayList<LinkedHashMap<String, String>> list_view, int resource,
			String[] from, int[] to) {
		// super(context, list_view, resource, from, to);

		super(context, R.layout.listview_sorting_route, list_view);

		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Lấy dòng trên listview
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listview_sorting_route, null);
		}
		LinkedHashMap<String, String> val = getItem(position);
		ChangeValOfRow(convertView, val);

		// Set màu
		if (val.get("STT_ROUTE").equals(SortingRouteActivity.Selected_STT + ""))
			convertView.setBackgroundColor(ConstantVariables.SELECTED_COLOR);
		else
			convertView.setBackgroundColor(ConstantVariables.NO_SELECTED_COLOR);

		return convertView;

	}

	private void ChangeValOfRow(View rowView, LinkedHashMap<String, String> val) {
		
		// Lấy các control của dòng
		TextView tv_STT = (TextView) rowView.findViewById(R.id.tv_STT);
		TextView tv_MA_GC = (TextView) rowView.findViewById(R.id.tv_MA_GC);
		TextView tv_MA_COT = (TextView) rowView.findViewById(R.id.tv_MA_COT);
		TextView tv_LOAI_BCS = (TextView) rowView.findViewById(R.id.tv_LOAI_BCS);
		
		// Set dữ liệu cho các control của dòng
		tv_STT.setText(val.get("STT_ROUTE"));
		tv_MA_GC.setText(val.get("SERY_CTO"));
		tv_MA_COT.setText(val.get("MA_COT"));
		tv_LOAI_BCS.setText(val.get("LOAI_BCS"));
		
	}

}
