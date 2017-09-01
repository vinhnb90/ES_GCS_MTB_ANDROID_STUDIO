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
public class DNTTAdapter extends ArrayAdapter<LinkedHashMap<String, String>> {

	private final Context context;
	public String filter_col = null;

	public DNTTAdapter(Context context,
			ArrayList<LinkedHashMap<String, String>> list_view, int resource,
			String[] from, int[] to) {
		// super(context, list_view, resource, from, to);

		super(context, R.layout.listview_dntt, list_view);

		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// View rowView = inflater.inflate(R.layout.listview_gcs, parent,
			// false);
			//View rowView = inflater.inflate(R.layout.listview_dntt, null);
			convertView = inflater.inflate(R.layout.listview_dntt, null);
		}
		LinkedHashMap<String, String> val = getItem(position);
		ChangeValOfRow(convertView, val);

		// Set màu
		if (val.get("STT").equals(DNTTActivity.Selected_STT+""))
			convertView.setBackgroundColor(ConstantVariables.SELECTED_COLOR);
		else
			convertView.setBackgroundColor(ConstantVariables.NO_SELECTED_COLOR);

		return convertView;

	}

	private void ChangeValOfRow(View rowView, LinkedHashMap<String, String> val) {
		String DNTThat_Percent = val.get("DNTThat_Percent");
		
		// Lấy các control của dòng
		TextView tv_STT = (TextView) rowView.findViewById(R.id.tv_STT);
		TextView tv_Tram = (TextView) rowView.findViewById(R.id.tv_Tram);
		//TextView tv_Quyen = (TextView) rowView.findViewById(R.id.tv_Quyen);
		TextView tv_DaGhi = (TextView) rowView.findViewById(R.id.tv_DaGhi);
		TextView tv_DNTThu = (TextView) rowView.findViewById(R.id.tv_DNTThu);
		TextView tv_InputDN = (TextView) rowView.findViewById(R.id.tv_InputDN);
		TextView tv_DNTThat_Percent = (TextView) rowView.findViewById(R.id.tv_DNTThat_Percent);
		TextView tv_DNTThat = (TextView) rowView.findViewById(R.id.tv_DNTThat);

		// Set dữ liệu cho các control của dòng
		tv_STT.setText(val.get("STT"));
		tv_Tram.setText(val.get("Tram"));
//		tv_Quyen.setText(val.get("Quyen"));
		tv_DaGhi.setText(val.get("DaGhi") + " / "+val.get("SoDiemDo"));//val.get("DaGhi")
		tv_DNTThu.setText(val.get("DNTThu"));
		tv_InputDN.setText(val.get("InputDN"));
		tv_DNTThat_Percent.setText( DNTThat_Percent.length() == 0 ? "" : DNTThat_Percent+ "%");
		tv_DNTThat.setText(val.get("DNTThat"));
	}


}
