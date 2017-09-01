package esolutions.com.gcs_svn_old.com.camera.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.ChonSo;

public class Adapter_Chon_So extends BaseAdapter{

	private Context context;
	private List<ChonSo> list;
	private LayoutInflater inflater;
	private TextView tvChonso;
	@SuppressWarnings("unused")
	private CheckBox cbChonso;
	
	public Adapter_Chon_So(Context context, List<ChonSo> list) {
		this.list = list;
		this.context = context;
	}

	@SuppressWarnings("static-access")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(view == null){
			inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.listview_chonso, null);
		}
		tvChonso = (TextView)view.findViewById(R.id.tvChonso);
		cbChonso = (CheckBox)view.findViewById(R.id.cbChonso);
		
		final ChonSo cs = list.get(position);
		tvChonso.setText(cs.getMaso());
		return view;
	}
	
	public void setData(List<ChonSo> list){
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int pos) {
		return list.get(pos);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
}
