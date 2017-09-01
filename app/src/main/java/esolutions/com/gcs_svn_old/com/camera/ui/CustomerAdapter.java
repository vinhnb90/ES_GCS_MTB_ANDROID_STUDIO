package esolutions.com.gcs_svn_old.com.camera.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;

public class CustomerAdapter extends ArrayAdapter<LinkedHashMap<String, String>>  {

	private final Context context;
	Common comm = new Common();
	
	public String filter_col = null;

	public CustomerAdapter(Context context,
			ArrayList<LinkedHashMap<String, String>> list_view, int resource) {
		super(context, R.layout.listview_thcsbt, list_view);
		this.context = context;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflater.inflate(R.layout.listview_customer, null);
		}
		LinkedHashMap<String, String> val = getItem(position);
		TextView tvSTT = (TextView)convertView.findViewById(R.id.tvSTT);
		TextView tvNoiDung = (TextView)convertView.findViewById(R.id.tvNoiDung);
		CheckBox cbCheck = (CheckBox)convertView.findViewById(R.id.cbCheck);
		
		tvSTT.setText(val.get("STT"));
		tvNoiDung.setText(val.get("NOI_DUNG"));

		cbCheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!Common.lst_pos.contains("" + position)){
					Common.lst_pos.add("" + position);
				} else {
					Common.lst_pos.remove("" + position);
				}
			}
		});
		return convertView;
	}
}
