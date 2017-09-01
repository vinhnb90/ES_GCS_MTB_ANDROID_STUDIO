package esolutions.com.gcs_svn_old.com.camera.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.ConstantVariables;

public class MenuAdapter2 extends ArrayAdapter<String>{

	private Context context;
	private String[] listMenu;
	
	public MenuAdapter2(Context context, int resource, String[] listMenu) {
		super(context, resource, listMenu);
		this.context = context;
		this.listMenu = listMenu;
	}

	@SuppressWarnings("static-access")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listview_menu, parent, false);
		}
		ImageView ivMenu = (ImageView)convertView.findViewById(R.id.ivMenu);
		TextView tvMenu = (TextView)convertView.findViewById(R.id.tvMenu);
		ivMenu.setImageResource(ConstantVariables.ICON_PAGER[position]);
		tvMenu.setText(listMenu[position]);
		return convertView;
	}

}
