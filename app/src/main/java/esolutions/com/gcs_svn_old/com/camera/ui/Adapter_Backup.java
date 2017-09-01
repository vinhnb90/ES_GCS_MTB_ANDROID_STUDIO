package esolutions.com.gcs_svn_old.com.camera.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class Adapter_Backup extends ArrayAdapter<LinkedHashMap<String, String>> {

	private final Context context;
	public String filter_col = null;
	Common comm = new Common();

	public Adapter_Backup(Context context, int resource, ArrayList<LinkedHashMap<String, String>> objects) {
		super(context, resource, objects);
		this.context = context;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// Lấy dòng trên listview
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.listview_backup, null);
		LinkedHashMap<String, String> val = getItem(position);

		TextView tvSTT = (TextView)convertView.findViewById(R.id.tvSTT);
		TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
		TextView tvDate = (TextView)convertView.findViewById(R.id.tvDate);
		TextView tvSize = (TextView)convertView.findViewById(R.id.tvSize);
		ImageButton ibDownload = (ImageButton)convertView.findViewById(R.id.ibDownload);
		
		tvSTT.setText("" + (position + 1));
		tvName.setText(val.get("NAME"));
		tvDate.setText(val.get("DATE"));
		tvSize.setText(val.get("SIZE") + " (KB)");

		ibDownload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
					String cDate = sdf.format(new Date());
					// Rename
					File fOld = new File(Environment.getExternalStorageDirectory() + "/ESGCS/DB/ESGCS.s3db");
					File fNew = new File(Environment.getExternalStorageDirectory() + "/ESGCS/DB/ESGCS_old" + cDate + ".s3db");
					if(fOld.exists()) {
						fOld.renameTo(fNew);
					}
					
					LinkedHashMap<String, String> map = getItem(position);
					File fBackup = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Backup/" + map.get("NAME"));
					if(fBackup.exists()){
						comm.copy(fBackup, fOld);
					}
					
					comm.msbox("Thông báo", "Bạn vừa lấy lại một file dữ liệu được backup lúc " + map.get("DATE"), context);
					Activity_Config.dialogBackup.dismiss();
				} catch(Exception ex) {
					ex.toString();
				}
			}
		});
		
		return convertView;

	}
	
}
