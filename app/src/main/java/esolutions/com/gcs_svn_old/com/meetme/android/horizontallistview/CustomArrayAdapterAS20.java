package esolutions.com.gcs_svn_old.com.meetme.android.horizontallistview;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import esolutions.com.gcs_svn_old.com.camera.ui.Activity_Camera_AS20;
import esolutions.com.gcs_svn_old.R;

/** An array adapter that knows how to render views when given CustomData classes */
public class CustomArrayAdapterAS20 extends ArrayAdapter<LinkedHashMap<String, String>> {
	private final Context context;
	public String filter_col = null;

	public CustomArrayAdapterAS20(Context context, int resource, ArrayList<LinkedHashMap<String, String>> objects) {
		super(context, resource, objects);
		this.context = context;
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// Lấy dòng trên listview
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.custom_data_view, null);
		final LinkedHashMap<String, String> val = getItem(position);

		TextView textView = (TextView)convertView.findViewById(R.id.textView);
		
		textView.setText(val.get("so"));
		
		if(Activity_Camera_AS20.pos_so == position){
			textView.setBackgroundResource(R.drawable.border_listitem_gcs3);
		} else {
			textView.setBackgroundResource(R.drawable.border_listitem_gcs2);
		}
		
		return convertView;

	}
}
