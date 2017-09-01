package esolutions.com.gcs_svn_old.com.camera.simplemjpeg;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.define.Define;
import esolutions.com.gcs_svn_old.multi_media.AviRecord;
import esolutions.com.gcs_svn_old.multi_media.IAudioSink;
import esolutions.com.gcs_svn_old.multi_media.IVideoSink;
import esolutions.com.gcs_svn_old.multi_media.PCMPlayer;
import esolutions.com.gcs_svn_old.multi_media.VideoStreamer;

public class SettingCamera extends Activity
//        implements IVideoSink, IAudioSink
{

	Button btnConnect, btnSettings;
	EditText etIP, etCommand;
	TextView text_wifi_ssid;
	
	int width = 640;
	int height = 480;
	
	String ip_ad = "192.168.2.1:80";
	String ip_command = "?action=stream";
	
	Common comm = new Common();
//	String ip_ad = "192.168.0.93:8080";
//	String ip_command = "?action=stream";

	/*//region Khởi tạo tham số gậy Camera|
	private TextView mTvStatus;
	private ImageView mIvLoadVideoArea, mIvCaptureImage;
	private Button btConnect, btCapture;
	private static final int REQUEST_WIFI_SETTING = 1008;
	private boolean SNAPSHOT;
	private int BUFFER_LOAD_CAMERA = 1229000;
	private static String sIP = null, sPASS_WORD = null, sUSER_NAME = null;

	private int THRES_HOLD = 20;

	private int CHANGE = 3;

	private VideoStreamer mStreamingVideoRecorder;

	private PCMPlayer mPCMPlayer;

	private boolean ENABLE_AUDIO = false;

	private ThumbnailCache mCache = new ThumbnailCache(64);

	final byte[] EXIF_header = {
			(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE1, 0x00, 0x5A, 0x45, 0x78, 0x69
			, 0x66, 0x00, 0x00, 0x4D, 0x4D, 0x00, 0x2A
			, 0x00, 0x00, 0x00, 0x08, 0x00, 0x02, 0x01, 0x32, 0x00, 0x02
			, 0x00, 0x00, 0x00, 0x14, 0x00, 0x00, 0x00, 0x2A, (byte) 0x87, 0x69
			, 0x00, 0x04, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x3E
			, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x32, 0x30
			, 0x31, 0x30, 0x3A, 0x31, 0x32, 0x3A, 0x32, 0x34, 0x20, 0x31
			, 0x36, 0x3A, 0x30, 0x38, 0x3A, 0x32, 0x34, 0x00, 0x00, 0x01
			, (byte) 0x90, 0x03, 0x00, 0x02, 0x00, 0x00, 0x00, 0x14, 0x00, 0x00
			, 0x00, 0x2A, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

	static int FRAME_INDEX = 0;
	static byte[] FRAME_1 = null;
	static int FRAME_INDEX_LAST_MOTION = -120;
	static int FRAME_COUNT = 0;
	static int DIFF_FRAME = 120;
	static int MOTION = 0;

	static int INDEX = 0;
	private boolean RECORD_IN_PROGRESS = false;
	private AviRecord mAviRecord;
	private long MAX_SIZE;
	private ByteBuffer mBbRefBf;
	private ByteBuffer mBbDiffBf;
	private byte[] mARefBytes;
	private byte[] mADiffBytes;
	private byte[] mARefStore;
	private byte[] mADiffStore;
	private int MOTION_LEVEL = 0;
	static boolean FLAG = false;

	private final String TAG = "Debug";

	private String name_cut = "";

	private static String sPath = "";


    @Override
    public void onPCM(byte[] pcmData) {

    }

    @Override
    public void onFrame(byte[] frame, byte[] pcm) {
        final Bitmap bitmapOnFrame = BitmapFactory.decodeByteArray(frame, 0, frame.length);

        INDEX++;
        INDEX = INDEX % 8;
        if (RECORD_IN_PROGRESS) {
            if (mAviRecord.getCurrentRecordSize() >= MAX_SIZE) {
                mIvLoadVideoArea.post(new Runnable() {
                    public void run() {
                    }
                });
                RECORD_IN_PROGRESS = false;
                return;
            }

            if (mStreamingVideoRecorder.getResetFlag() == 1)
                mAviRecord.resetCounter();

            if (mBbRefBf == null || mBbDiffBf == null)
                MOTION = 0;
            else if ((INDEX == 5)
                    && (Math.abs(FRAME_COUNT - FRAME_INDEX_LAST_MOTION) > DIFF_FRAME)) {
                //only check if _frameindex is greater than previous _frameindex_motiondetect by 10s = 170 frames
                mBbRefBf.rewind();
                mBbDiffBf.rewind();
                mARefBytes = mBbRefBf.array();
                mADiffBytes = mBbDiffBf.array();

//				int choice=0;
                MOTION_LEVEL = 0;
                FLAG = false;

                for (int i = 0; i < BUFFER_LOAD_CAMERA; i += 24) {
//					int chosen = choice%3;
//					int diff = Math.abs(mARefBytes[i+chosen] - mADiffBytes[i+chosen]);
//					if ( diff > 100 )
                    MOTION_LEVEL++;
//					choice++;
                }

                Log.i(TAG, "THRE_SHOLD - " + THRES_HOLD + " CHANGE - " + CHANGE
                        + " MOTION_LEVEL " + MOTION_LEVEL);

                if (THRES_HOLD > 30000)
                    CHANGE = 4;
                else if (THRES_HOLD == 0) {
                    CHANGE = 5;
                    FRAME_INDEX_LAST_MOTION = FRAME_COUNT;
                    FLAG = true;
                    mAviRecord.stop();
                    mAviRecord.start();
                } else if (MOTION_LEVEL > THRES_HOLD) {
                    *//**
                     * Using Ai-ball, start recording video now
                     *//*
                    CHANGE = 1;
                    FRAME_INDEX_LAST_MOTION = FRAME_COUNT;
                    FLAG = true;
                    mAviRecord.stop();
                    mAviRecord.start();
                } else {
                    CHANGE = 0;
//					mAviRecord.stop();
                }
            }

            // TODO
            Log.i(TAG, "FLAG " + FLAG);

            if (FLAG) {
                //motion > threshold)
                if (pcm.length > 0) {
                    if (mAviRecord.getRecordFlag() == Define.RUN) {
                        mAviRecord.getAudio(pcm, pcm.length, mStreamingVideoRecorder.getResetAudioBufferCount());
                    }
                }

                if (mAviRecord.getRecordFlag() == Define.RUN) {
                    FRAME_1 = new byte[frame.length + 92];

                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss.");
                    byte[] to_be_inserted_bytes = formatter.format(date).getBytes();
                    System.arraycopy(to_be_inserted_bytes, 0, EXIF_header, 54, 20);

                    System.arraycopy(EXIF_header, 0, FRAME_1, 0, 94);
                    System.arraycopy(frame, 2, FRAME_1, 94, frame.length - 2);
                    mAviRecord.getImage(FRAME_1, FRAME_1.length, FRAME_INDEX);

                    FRAME_INDEX++;
                }
            } else {
            }
            FRAME_COUNT++;
        } else {
            btCapture.setEnabled(true);
            FRAME_INDEX = 0;
        }

        if (SNAPSHOT) {
            SNAPSHOT = false;
            saveImage(frame);
            finish();
            onDestroy();
        }

        mIvLoadVideoArea.post(new Runnable() {
            public void run() {
                mIvLoadVideoArea.setImageBitmap(bitmapOnFrame);
            }
        });

        if (pcm != null) {
            mPCMPlayer.writePCM(pcm);
        }
    }

    private void saveImage(byte[] frame) {
        try {
            BufferedOutputStream bos = null;
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/anhCongTo");
            myDir.mkdirs();

//			String fname = sTask.getMaKhachHang() + "_" + sTask.getNgayPhanCong() + ".jpg";
            long date = System.currentTimeMillis();
            String fname = date + ".jpg";
            File file = new File(myDir, fname);
            if (file.exists()) file.delete();
            file.createNewFile();
            sPath = root + "/anhCongTo/" + fname;

            String fileName = file.getPath();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bmRoot = BitmapFactory.decodeByteArray(frame, 0, frame.length, options);

            bos = new BufferedOutputStream(new FileOutputStream(fileName));
//			bmRoot = drawTextToBitmap(
//					bmRoot,
//					"Khách hàng: " + sTask.getTenKhachHang(),
//					"Số Công tơ: " + sTask.getSoCongTo(),
//					"Ngày Phúc Tra: " + sTask.getNgayPhucTra(),
//					"Mã Điểm Đo :" + sTask.getMaDiemDo());
            bmRoot = drawTextToBitmap(
                    bmRoot,
                    "Khách hàng: " ,
                    "Số Công tơ: " ,
                    "Ngày Phúc Tra: " ,
                    "Mã Điểm Đo :" );

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmRoot.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageByte = baos.toByteArray();

            bos.write(imageByte);
//            bos.write(Common.encodeTobase64Byte(bmRoot));
            bos.close();
            Bitmap congTo = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

            mIvCaptureImage.setImageBitmap(congTo);
//            mIvLoadVideoArea.post(new Runnable() {
//                public void run() {
//                    mIvLoadVideoArea.setImageBitmap(bitmapOnFrame);
//                }
//            });
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public static class ThumbnailCache extends LruCache<Long, Bitmap> {
		public ThumbnailCache(int maxSizeBytes) {
			super(maxSizeBytes);
		}

		@Override
		protected int sizeOf(Long key, Bitmap value) {
			return value.getByteCount();
		}
	}
	//endregion*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_setting);

		btnConnect = (Button) findViewById(R.id.btnConnect_main);
		btnSettings = (Button) findViewById(R.id.btnSettings);
		etIP = (EditText) findViewById(R.id.etIP);
		etCommand = (EditText) findViewById(R.id.etCommand);
		text_wifi_ssid = (TextView) findViewById(R.id.text_wifi_ssid);


		/*mIvCaptureImage = (ImageView) findViewById(R.id.iv_capture_image);
		mIvLoadVideoArea= (ImageView) findViewById(R.id.iv_load_video_area);
		btnConnect = (Button) findViewById(R.id.btnConnect_main);
		mTvStatus = (TextView) findViewById(R.id.tv_record_status_main);

		btnSettings = (Button) findViewById(R.id.btnSettings);
		etIP = (EditText) findViewById(R.id.etIP);
		etCommand = (EditText) findViewById(R.id.etCommand);
		text_wifi_ssid = (TextView) findViewById(R.id.text_wifi_ssid);

*/
		Bundle extras = getIntent().getExtras();
		
		if(extras != null){
        	width = extras.getInt("width", width);
        	height = extras.getInt("height", height);
			
        	ip_ad = extras.getString("ip_ad1", ip_ad);
        	ip_command = extras.getString("ip_command");
    		
        	etIP.setText(String.valueOf(ip_ad));
        	etCommand.setText(String.valueOf(ip_command));
        }

     /*   //region Gậy camera
        btConnect = (Button) findViewById(R.id.btConnect);
        btCapture= (Button) findViewById(R.id.btCapture);
        sIP = "http://192.168.2.1";
        sUSER_NAME = "";
        sPASS_WORD = "";
        mARefStore = new byte[BUFFER_LOAD_CAMERA];
        mADiffStore = new byte[BUFFER_LOAD_CAMERA];
        mARefBytes = new byte[BUFFER_LOAD_CAMERA];
        mADiffBytes = new byte[BUFFER_LOAD_CAMERA];


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGotoWiFiSetting();
            }
        });

        btCapture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SNAPSHOT = true;
            }
        });
        setThresHold();
        try {
            Log.d(TAG, "onCreate: " + sIP);
            mStreamingVideoRecorder = new VideoStreamer(new URL(sIP + "/?action=appletvastream"), sUSER_NAME, sPASS_WORD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        mPCMPlayer = new PCMPlayer();

        ENABLE_AUDIO = false;
        mStreamingVideoRecorder.enableAudio(ENABLE_AUDIO);
        mStreamingVideoRecorder.addVideoSink(this);

        final Handler changeText = new Handler();

        changeText.post(new Runnable() {
            @Override
            public void run() {
                if (CHANGE == 1) {
                    CHANGE = 3;
                    mTvStatus.postInvalidate();
                    mTvStatus.setText(getString(R.string.record));

                } else if (CHANGE == 0) {
                    CHANGE = 3;
                    mTvStatus.postInvalidate();
                    mTvStatus.setText(getString(R.string.detectMotion));

                } else if (CHANGE == 4) {

                    mTvStatus.postInvalidate();
                    mTvStatus.setText(getString(R.string.disabled));
                } else if (CHANGE == 5) {

                    mTvStatus.postInvalidate();
                    mTvStatus.setText(getString(R.string.always));
                }

                changeText.postDelayed(this, 2000);
            }
        });

        //endregion*/

		btnConnect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickGotoWiFiSetting();
			}
		});
		
		btnSettings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					Intent intent = new Intent();
    				intent.putExtra("width", width);
    				intent.putExtra("height", height);
    				intent.putExtra("ip_ad", ip_ad);
    				intent.putExtra("ip_command", ip_command);
    				Common.state_show_camera = 1;
    	        
    				setResult(RESULT_OK, intent);
    				finish();
				} catch(Exception ex) {
					ex.toString();
				}
			}
		});
	}

   /* private void setThresHold() {
        THRES_HOLD = 5 * 10;

        if (THRES_HOLD == 0)
            CHANGE = 5;
        else if (THRES_HOLD == 300) {
            THRES_HOLD += 30000;
            CHANGE = 4;
        } else {
            THRES_HOLD += 1850;
        }
    }*/

    @Override
	protected void onResume() {
		WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);
		if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String htmlLabel = String.format("SSID: <b>%s</b>", wifiInfo.getSSID());
            text_wifi_ssid.setText(Html.fromHtml(htmlLabel));
        } else {
        	text_wifi_ssid.setText(R.string.msg_wifi_disconnect);
        }
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		try{
			Intent intent = new Intent();
			intent.putExtra("width", width);
			intent.putExtra("height", height);
			intent.putExtra("ip_ad", ip_ad);
			intent.putExtra("ip_command", ip_command);
			Common.state_show_camera = 1;
        
			setResult(RESULT_OK, intent);
			finish();
		} catch(Exception ex) {
			ex.toString();
		}
	}

	/** Open wifi setting
	 * 
	 */
	public void onClickGotoWiFiSetting() {
		try{
	        String pac = "com.android.settings";
	        Intent i = new Intent();
	
	        i.setClassName(pac, pac + ".wifi.p2p.WifiP2pSettings");
	        try {
//	            startActivity(i);
	            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
	        } catch (ActivityNotFoundException e) {
	            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH+1) {
	                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
	            } else {
	                i.setClassName(pac, pac + ".wifi.WifiSettings");
	                try {
	                    startActivity(i);
	                } catch (ActivityNotFoundException e2) {
	                    
	                }
	            }
	        }
		} catch(Exception ex) {
			ex.toString();
		}
    }



    //region Method Gậy Camera


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    /*    try {
            if (requestCode == REQUEST_WIFI_SETTING) {
                Intent it = new Intent(SettingCamera.this, SettingCamera.class);
                startActivity(it);
                SettingCamera.this.finish();
            }
        } catch (Exception ex) {
            Toast.makeText(SettingCamera.this, "\"Lỗi quét mã vạch\\n\" + ex.toString()", Toast.LENGTH_SHORT).show();
//            Common.showAlertDialogGreen(GcsRecordActivity.this, "Lỗi", Color.RED, "Lỗi quét mã vạch\n" + ex.toString(), Color.WHITE, "OK", Color.WHITE);
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();

     /*   FRAME_INDEX = 0;
        DIFF_FRAME = 120;
        MOTION = 0;
        FRAME_INDEX_LAST_MOTION = -120;
        FRAME_COUNT = 0;
        //_detectdifference = new DetectDifference();


        Thread a = new Thread(mStreamingVideoRecorder);
        a.start();
        new Thread(mPCMPlayer).start();*/

    }

    @Override
    protected void onStop() {
        super.onStop();
//        INDEX = 0;
//        MOTION = 0;
//        FRAME_COUNT = 0;
//        FRAME_INDEX_LAST_MOTION = -120;
//        FRAME_1 = null;
//        FLAG = false;
//        try {
//            mStreamingVideoRecorder.stop();
//
//            mPCMPlayer.stop();
//        } catch (Exception e) {
//            //todo: error handling
//        }
//        //_detectdifference.stop();
//        FRAME_INDEX = 0;
//
//        if (RECORD_IN_PROGRESS) {
//            RECORD_IN_PROGRESS = false;
//            mAviRecord.setRecordFlag(Define.STOP);
//        }
    }

//    public void onClickSaveImage(View view) {
//
//        if (mIvCaptureImage.getDrawable() == null) {
//            Toast.makeText(SettingCamera.this, "Bạn chưa chụp bất kì ảnh nào!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (sPath.equals("") || sPath.equals(null)) {
//            Toast.makeText(SettingCamera.this, "Lỗi đường dẫn ảnh!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Common.sPath = sPath;
////		Data.sPath = sPath;
//        finish();
//    }
//
//    public Bitmap drawTextToBitmap(Bitmap bitmap, String ten_kh, String so_cto, String ngay_phuc_tra, String ma_diem_do) {
//        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
//        if (bitmapConfig == null) {
//            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
//        }
//        bitmap = bitmap.copy(bitmapConfig, true);
//
////		Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.WHITE);
//        paint.setTextSize(bitmap.getHeight() / 20);
//
//        Paint paint_rec = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint_rec.setColor(Color.BLACK);
//
//        // Xử lý cắt chuỗi tên xuống dòng
//        String[] arrName = ten_kh.trim().split(" ");
//        String name1 = "";
//        String name2 = "";
//        for (String sName : arrName) {
//            Rect bounds_cut = new Rect();
//            paint.getTextBounds(name1, 0, name1.length(), bounds_cut);
//            if (bounds_cut.width() < 3 * bitmap.getWidth() / 4) {
//                name1 += sName + " ";
//            } else {
//                name2 += sName + " ";
//            }
//        }
////		name = name1.trim() + "\n" + name2.trim();
//        ten_kh = name1.trim();
//        name_cut = name1.trim() + "\n" + name2.trim();
//
//        Rect bounds1 = new Rect();
//        paint.getTextBounds(ten_kh, 0, ten_kh.length(), bounds1);
//        int x1 = 10;
//        int y1 = bounds1.height();
//        int y1_2 = 2 * bounds1.height();
//
//        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
//        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() + 5 * bounds1.height() + 30, conf); // this creates a MUTABLE bitmap
//        Canvas c = new Canvas(bmp);
//        // Vẽ tên
//        c.drawRect(0, 0, bmp.getWidth(), 2 * bounds1.height() + 15, paint_rec);
//        c.drawRect(0, bmp.getHeight() - 2 * bounds1.height() - 15, bmp.getWidth(), bmp.getHeight(), paint_rec);
//        c.drawText(ten_kh, x1, y1, paint);
//        if (!name2.equals(""))
//            c.drawText(name2, x1, y1_2, paint);
//        // Vẽ ảnh lên khung mới có viền đen
//        c.drawBitmap(bitmap, 0, 3 * bounds1.height() + 15, paint_rec);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        String currentDateandTime = sdf.format(new Date());
//
//        Rect bounds0 = new Rect();
//        paint.getTextBounds(currentDateandTime, 0, currentDateandTime.length(), bounds0);
//        c.drawRect(0, 2 * bounds1.height() + 15, bounds0.width() + 15, 3 * bounds1.height() + 15, paint_rec);
//
//        int x5 = 10;
//        int y5 = 2 * bounds1.height() + 30;
//        c.drawText(currentDateandTime, x5, y5, paint);
//
////        Rect bounds6 = new Rect();
////        paint.getTextBounds(ngay_phuc_tra, 0, ngay_phuc_tra.length(), bounds6);
////        int x6 = 5;//bmp.getWidth() - 10 - bounds6.width()
////        int y6 = bmp.getHeight() - 20 - bounds6.height();
////        c.drawText(ngay_phuc_tra, x6, y6, paint);
//
//        Rect bounds2 = new Rect();
//        paint.getTextBounds(ma_diem_do, 0, ma_diem_do.length(), bounds2);
//        int x2 = 10;
//        int y2 = bmp.getHeight() - 10;
//        c.drawText(ma_diem_do, x2, y2, paint);
//
//        Rect bounds3 = new Rect();
//        paint.getTextBounds(so_cto, 0, so_cto.length(), bounds3);
//        int x3 = bmp.getWidth() - 10 - bounds3.width();
//        int y3 = bmp.getHeight() - 10;
//        c.drawText(so_cto, x3, y3, paint);
//
////        // Vẽ CS_MOI lên góc trên bên phải
////        Rect bounds4 = new Rect();
////        paint.getTextBounds(so_cto, 0, so_cto.length(), bounds4);
////        int x4 = bmp.getWidth() - 10 - bounds4.width();
////        int y4 = bounds4.height() + 10;
////        c.drawText(so_cto, x4, y4, paint);
//
//        return bmp;
//    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    @Override
//    public void onTrimMemory(int level) {
//        super.onTrimMemory(level);
//        super.onTrimMemory(level);
//        // Giải phóng bộ nhớ giúp tăng hiệu năng hệ thống và
//        // giúp giảm thiểu khả năng bị system kill khi thiếu bộ nhớ.
//        if (level >= TRIM_MEMORY_MODERATE) { // 60
//            // Giải phóng bộ nhớ giúp tăng hiệu năng hệ thống và
//            // giúp giảm thiểu khả năng bị system kill khi thiếu bộ nhớ.
//            mCache.evictAll();
//        } else if (level >= TRIM_MEMORY_BACKGROUND) { // 40
//            // Giải phóng bộ nhớ giúp tăng hiệu năng hệ thống và
//            // giúp giảm thiểu khả năng bị system kill khi thiếu bộ nhớ.
//            mCache.trimToSize(mCache.size() / 2);
//        }
//    }
//
//    @Override
//    public void onInitError(String errorMessage) {
//
//    }
//
//    @Override
//    public void onVideoEnd() {
//
//    }

    //endregion

}
