package esolutions.com.gcs_svn_old.com.camera.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import esolutions.com.gcs_svn_old.esgcs.printer.ESC_Bt_Printer;
@SuppressLint("NewApi")
public class Common {
	// Mã code chương trình
	public static String PHIEN_BAN = "HN";//Hà Nội
//	public static String PHIEN_BAN = "BN";//Bắc Ninh
//	public static String PHIEN_BAN = "LC";//Lai châu
//	public static String PHIEN_BAN = "SL";// Sơn La
	public static String CAMERA_TYPE = "AIBALL";// camera cho Hà Nội
	public static int CAMERA_AIBALL_RUN = 0;// Tình trạng hoạt động của camera aiball
//	public String CAMERA_TYPE = "QX10"; // camera phục vụ nghiên cứu
//	public String CAMERA_TYPE = "AS20";// camera cho Sơn La
//	public static int tt_dinh_dang = 0;
	public static int state_show_camera = 0;
	public static int state_show_image = 0;
	public static String CS_MOI;
	public static String SL_MOI = "0";
	public static boolean state_cs = false;
	public static int trang_thai_hien_csmoi = 0;
	public static int cur_pos = 0;
//	public static final String EXTRA_MESSAGE = "EXT_MSG";
//	public static final String EXTRA_MESSAGE1 = "EXT_MSG1";
//	public static final String EXTRA_MESSAGE2 = "EXT_MSG2";
	public static int count_data = 0;
	public static int pos_change_camera = -1;
	public static int pos_tab = 0;
	
	public static final String UploadFolderPath = "/ESGCS/Upload";
	public static final String DBFolderPath = "/ESGCS/DB";
	public static final String PhotoFolderPath = "/ESGCS/Photo";
	public static final String DownloadFolderPath = "/ESGCS/Download";
	public static final String BackupFolderPath = "/ESGCS/Backup";
	public static final String RouteFolderPath = "/ESGCS/LoTrinh";
	public static final String TempFolderPath = "/ESGCS/Temp";
	public static final String FormatFolderPath = "/ESGCS/Format";
	public static final String TessFolderPath = "/ESGCS/Format/tessdata/";
	public static final String programPath = "/ESGCS/";
	public static final String cfgFileName = "ESGCS.cfg";
	public static ConfigInfo cfgInfo;
	public static boolean isSoMoi;
	public static String MA_DVIQLY = null;
	public static String MA_NVGCS = null;
	public static String MA_DQL = null;
	public static String IMEI = null;
	public static ESC_Bt_Printer printer = null;
	public static String SO1;
	public static int count;
	public static int state_chonso;
	public static ArrayList<String> lst_pos = new ArrayList<String>();
	public static boolean isErrorCamera = false;
	
	public static String[] arr_MaDvi_LaiChau = {"PA2908", "PA2905", "PA2901", "PA2902", "PA2904", "PA2906", 
		"PA2907", "PA2909"};
	public static String[] arr_TenDvi_LaiChau = {"Điện lực Thành Phố Lai Châu", "Điện lực Tam Đường", 
		"Điện lực Tân Uyên", "Điện lực Than Uyên", "Điện lực Phong Thổ", "Điện lực Sìn Hồ", 
		"Điện lực Nậm Nhùn", "Điện lực Nậm Nhùn"};

	public static String sPath = "";

	public Common() {
		if (printer == null) {
			printer = new ESC_Bt_Printer("00:12:F3:19:49:CD");//
		}
	}

	private static final String[] VietnameseSigns = new String[] {
			"aAeEoOuUiIdDyY", "áàạảãâấầậẩẫăắằặẳẵ", "�?ÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴ",
			"éèẹẻẽêế�?ệểễ", "ÉÈẸẺẼÊẾỀỆỂỄ", "óò�?�?õôốồộổỗơớ�?ợởỡ",
			"ÓÒỌỎÕÔ�?ỒỘỔỖƠỚỜỢỞỠ", "úùụủũưứừựửữ", "ÚÙỤỦŨƯỨỪỰỬỮ", "íìịỉĩ",
			"�?ÌỊỈĨ", "đ", "�?", "ýỳỵỷỹ", "�?ỲỴỶỸ" };

	private String[] tagName = new String[] { "WarningEnable3", "WarningEnable",
			"WarningEnable2", "VuotMuc", "DuoiDinhMuc", "VuotMuc2",
			"DuoiDinhMuc2", "AutosaveEnable", "AutosaveMinutes", "UrlWS","Serial",
			"ColOrder", "DinhDang","DVIQLY" };

	public static int posDonVi(String MA_DVIQLY) {
		for(int i = 0; i < arr_MaDvi_LaiChau.length; i++) {
			if(MA_DVIQLY.equals(arr_MaDvi_LaiChau[i])){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * �?ọc file
	 * 
	 * @param fileName
	 * @return
	 */
	public String readTextFile(String fileName) {
		String returnValue = "";
		FileReader file;
		String line = "";
		try {
			file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			try {
				while ((line = reader.readLine()) != null) {
					returnValue += line + "\n";
				}
			} finally {
				reader.close();
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		return returnValue;

	}

	/**
	 * Ghi file
	 * 
	 * @param fileName
	 *            tên file
	 * @param s
	 *            chuỗi nội dung
	 */
	public void writeTextFile(String fileName, String s) {
		FileWriter output;
		try {
			output = new FileWriter(fileName);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write(s);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Kiểm tra mã đăng ky�?
	 * 
	 * @param context
	 * @param srl
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public boolean CheckLicense(Context context, String srl) {
		String seri = srl;
		if (seri == null)
			seri = cfgInfo.getSerial();

		// chuỗi serial sau khi giải mã gồm mã dvi , ngày hết hạn, imei
		// Lấy serial
		try {
			if (seri == null || seri.length() == 0) {
				
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new File(Environment
						.getExternalStorageDirectory()
						+ Common.programPath
						+ Common.cfgFileName));

				NodeList nodeList = doc.getElementsByTagName("Config");

				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element elm = (Element) node;

						NodeList nodelst4 = elm.getElementsByTagName("Serial");
						if (nodelst4.item(0) != null) {
							Element sub_elm4 = (Element) nodelst4.item(0);
							seri = sub_elm4.getTextContent();
						}
					}
				}
			}
			
			// giải mã serial để lấy ngày hết hạn
			security sc = new security();
			String info = sc.decrypt_Base64(seri);
			@SuppressWarnings("unused")
			String MaDVI = null;
			String strExpireDate = null;
			String IMEI = null;
	//		String subStr;
			String[] infos = info.split("_"); 
			if (info != null && info.length() > 0) {
				
	//			MaDVI = info.substring(0, info.indexOf("_"));
	//			subStr = info.substring(info.indexOf("_") + 1);
	//			strExpireDate = subStr.substring(0, subStr.indexOf("_"));
	//			subStr = subStr.substring(subStr.indexOf("_") + 1);
	//			IMEI = subStr;
				MaDVI = infos[0];
				strExpireDate = infos[1];
				IMEI = infos[2];
			}
	
			if (IMEI != null && IMEI.length() > 0 && IMEI.equals(GetIMEI(context))) {
	
				Calendar c = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
				Date ExpireDate;
				try {
					ExpireDate = df.parse(strExpireDate);
				} catch (ParseException e) {
					ExpireDate = null;
				}
	
				if (ExpireDate.after(c.getTime())) {
					// common.MaDVI = MaDVI;
					if (!seri.equals(cfgInfo.getSerial())) {
						cfgInfo.setSerial(seri);
						CreateFileConfig(cfgInfo,
								new File(Environment.getExternalStorageDirectory()
										+ programPath + cfgFileName), seri);
					}
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		} 
		

		return false;
	}

	/**
	 * Lâ�?y IMEI hoặc SN của ma�?y
	 * 
	 * @param context
	 * @return
	 */
	public String GetIMEI(Context context) {
		try {
			TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			String IMEI = tManager.getDeviceId();
			if (IMEI == null) {
				Class<?> c = Class.forName("android.os.SystemProperties");
				Method get = c.getMethod("get", String.class);
				IMEI = (String) get.invoke(c, "ro.serialno");
			}

			return IMEI;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Tạo file config
	 * 
	 * @param cfg
	 * @param cfgFile
	 * @param strSerial
	 * @return
	 */
	public boolean CreateFileConfig(ConfigInfo cfg, File cfgFile,
			String strSerial) {

		if (strSerial == null)
			strSerial = cfgInfo.getSerial();

		FileOutputStream fos;
		XmlSerializer serializer = Xml.newSerializer();
		String[] tagValue = new String[] {
				String.valueOf(cfg.isWarningEnable3()),
				String.valueOf(cfg.isWarningEnable()),
				String.valueOf(cfg.isWarningEnable2()),
				String.valueOf(cfg.getVuotDinhMuc()),
				String.valueOf(cfg.getDuoiDinhMuc()),
				String.valueOf(cfg.getVuotDinhMuc2()),
				String.valueOf(cfg.getDuoiDinhMuc2()),
				String.valueOf(cfg.isAutosaveEnable()),
				String.valueOf(cfg.getAutosaveMinutes()),
				cfg.getIP_SERVICE(),
				strSerial,
				cfg.getColumnsOrder(),
				String.valueOf(cfg.getDinhDang()),
				cfg.getDVIQLY()};
		try {
			cfgFile.createNewFile();
			fos = new FileOutputStream(cfgFile, false);

			serializer.setOutput(fos, "UTF-8");
			serializer.startDocument(null, Boolean.valueOf(true));
			serializer.setFeature(
					"http://xmlpull.org/v1/doc/features.html#indent-output",
					true);
			serializer.startTag(null, "Config");

			for (int i = 0; i < tagName.length; i++) {
				serializer.startTag(null, tagName[i]);
				serializer.text(tagValue[i]);
				serializer.endTag(null, tagName[i]);
			}

			serializer.endTag(null, "Config");
			serializer.endDocument();

			serializer.flush();

			fos.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Lấy cấu hình từ file config
	 * 
	 * @return
	 */
	public ConfigInfo GetFileConfig() {
		ConfigInfo cfgInfo = new ConfigInfo();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(Environment
					.getExternalStorageDirectory()
					+ Common.programPath
					+ Common.cfgFileName));

			NodeList nodeList = doc.getElementsByTagName("Config");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elm = (Element) node;

					NodeList nodelst11 = elm
							.getElementsByTagName("WarningEnable3");
					if (nodelst11 != null && nodelst11.item(0) != null) {
						Element sub_elm = (Element) nodelst11.item(0);
						cfgInfo.setWarningEnable3(Boolean.valueOf(sub_elm
								.getTextContent()));
					}
					
					NodeList nodelst = elm
							.getElementsByTagName("WarningEnable");
					if (nodelst != null && nodelst.item(0) != null) {
						Element sub_elm = (Element) nodelst.item(0);
						cfgInfo.setWarningEnable(Boolean.valueOf(sub_elm
								.getTextContent()));
					}

					NodeList nodelst8 = elm
							.getElementsByTagName("WarningEnable2");
					if (nodelst8 != null && nodelst8.item(0) != null) {
						Element sub_elm8 = (Element) nodelst8.item(0);
						cfgInfo.setWarningEnable2(Boolean.valueOf(sub_elm8
								.getTextContent()));
					}

					NodeList nodelst1 = elm.getElementsByTagName("VuotMuc");
					if (nodelst1 != null && nodelst1.item(0) != null) {
						Element sub_elm1 = (Element) nodelst1.item(0);
						cfgInfo.setVuotDinhMuc(Integer.valueOf(sub_elm1
								.getTextContent()));
					}

					NodeList nodelst2 = elm.getElementsByTagName("DuoiDinhMuc");
					if (nodelst2 != null && nodelst2.item(0) != null) {
						Element sub_elm2 = (Element) nodelst2.item(0);
						cfgInfo.setDuoiDinhMuc(Integer.valueOf(sub_elm2
								.getTextContent()));
					}
					NodeList nodelst9 = elm.getElementsByTagName("VuotMuc2");
					if (nodelst9 != null && nodelst9.item(0) != null) {
						Element sub_elm9 = (Element) nodelst9.item(0);
						cfgInfo.setVuotDinhMuc2(Integer.valueOf(sub_elm9
								.getTextContent()));
					}

					NodeList nodelst10 = elm
							.getElementsByTagName("DuoiDinhMuc2");
					if (nodelst10 != null && nodelst10.item(0) != null) {
						Element sub_elm10 = (Element) nodelst10.item(0);
						cfgInfo.setDuoiDinhMuc2(Integer.valueOf(sub_elm10
								.getTextContent()));
					}

					NodeList nodelst3 = elm
							.getElementsByTagName("AutosaveEnable");
					if (nodelst3 != null && nodelst3.item(0) != null) {
						Element sub_elm3 = (Element) nodelst3.item(0);
						cfgInfo.setAutosaveEnable(Boolean.valueOf(sub_elm3
								.getTextContent()));
					}

					NodeList nodelst4 = elm
							.getElementsByTagName("AutosaveMinutes");
					if (nodelst4 != null && nodelst4.item(0) != null) {
						Element sub_elm4 = (Element) nodelst4.item(0);
						cfgInfo.setAutosaveMinutes(Integer.valueOf(sub_elm4
								.getTextContent()));
					}

					NodeList nodelst5 = elm.getElementsByTagName("Serial");
					if (nodelst5.item(0) != null) {
						Element sub_elm5 = (Element) nodelst5.item(0);
						cfgInfo.setSerial(sub_elm5.getTextContent());
					}

					NodeList nodelst6 = elm.getElementsByTagName("UrlWS");
					if (nodelst6.item(0) != null) {
						Element sub_elm6 = (Element) nodelst6.item(0);
						cfgInfo.setIP_SERVICE(sub_elm6.getTextContent());
					}

					NodeList nodelst7 = elm.getElementsByTagName("ColOrder");
					if (nodelst7.item(0) != null) {
						Element sub_elm7 = (Element) nodelst7.item(0);
						cfgInfo.setColumnsOrder(sub_elm7.getTextContent());
					}
					
					NodeList nodelst12 = elm.getElementsByTagName("DinhDang");
					if (nodelst12 != null && nodelst12.item(0) != null) {
						Element sub_elm12 = (Element) nodelst12.item(0);
						cfgInfo.setDinhDang(Boolean.valueOf(sub_elm12.getTextContent()));
					}
					NodeList nodelst13 = elm.getElementsByTagName("DVIQLY");
					if (nodelst13 != null && nodelst13.item(0) != null) {
						Element sub_elm13 = (Element) nodelst13.item(0);
						cfgInfo.setDVIQLY(sub_elm13.getTextContent());
					}

				}
			}
		} catch (SAXException e) {
			cfgInfo = null;
		} catch (IOException e) {
			cfgInfo = null;
		} catch (ParserConfigurationException e1) {
			cfgInfo = null;
		}
		return cfgInfo;
	}

	/**
	 * Bỏ dấu tiếng Việt
	 * 
	 * @param str
	 * @return
	 */
	public String VietnameseTrim(String str) {
		for (int i = 1; i < VietnameseSigns.length; i++) {
			for (int j = 0; j < VietnameseSigns[i].length(); j++)
				str = str.replace(VietnameseSigns[i].charAt(j),
						VietnameseSigns[0].charAt(i - 1));
		}
		return str;
	}

	/**
	 * Mã hóa file bằng mã Base64
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String FileToBase64(String path) throws IOException {
		byte[] bytes = FileToByteArray(path);
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	/**
	 * Chuyển file thành mảng byte
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public byte[] FileToByteArray(String path) throws IOException {
		File f = new File(path);
		byte[] data = new byte[(int) f.length()];
		FileInputStream fis = new FileInputStream(f);
		fis.read(data);
		fis.close();
		return data;
	}

	/**
	 * Chuyển mảng byte thành File
	 * 
	 * @param bloc
	 * @param filePath
	 */
	public void ConvertByteToFile(byte[] bloc, String filePath) {
		File photo = new File(filePath);

		if (photo.exists()) {
			photo.delete();
		}

		try {
			FileOutputStream fos = new FileOutputStream(photo.getPath());

			fos.write(bloc);
			fos.close();
		} catch (IOException e) {
			// String s = e.getMessage();
		}
	}

	/**
	 * Lâ�?y danh sa�?ch tên file
	 * 
	 * @param FolderPath
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public String[] GetListFileName(String FolderPath) {
		String[] arrFileNames;

		try {

			File sdCardRoot = Environment.getExternalStorageDirectory();

			// Tạo thư mục nếu chưa có
			File programDirectory = new File(
					Environment.getExternalStorageDirectory() + FolderPath);

			if (!programDirectory.exists()) {
				programDirectory.mkdirs(); // tạo thư mục chứa dữ liệu
			}

			// lay so trong thu muc

			File dir = new File(sdCardRoot, FolderPath);
			if (dir.listFiles().length == 0) {
				// msbox("Thông báo", "Không có dữ liệu");
				return new String[0];
			} else {
				int count = 0;
				arrFileNames = new String[dir.listFiles().length];
				for (File f : dir.listFiles()) {
					if (f.isFile()) {
						String fileName = f.getName();
						// Kiểm tra phần mở rộng của file
						String fileExtension = fileName.substring(
								fileName.length() - 4, fileName.length());
						if (fileExtension.toLowerCase().equals(".xml")) {
							// cho vao array
							arrFileNames[count++] = fileName;
						} else {
							continue;
						}
					}
				}

				return arrFileNames;
			}
		} catch (Exception ex) {
			return new String[0];
		}

	}

	/**
	 * B�? đuôi .xml trong tên file
	 * 
	 * @param fileName
	 * @return
	 */
	public String GetShortFileName(String fileName) {
		int idxEnd = fileName.lastIndexOf("_");
		idxEnd = idxEnd == -1 ? fileName.lastIndexOf(".xml") : idxEnd;
		return fileName.substring(0, idxEnd);
	}

	/**
	 * Kiểm tra dòng đã gcs
	 * 
	 * @param elm
	 * @return
	 */
	public boolean isSavedRow(Element elm) {

		Element col_elem_TTR_MOI = (Element) elm
				.getElementsByTagName("TTR_MOI").item(0);
		Element col_elem_CS_MOI = (Element) elm.getElementsByTagName("CS_MOI")
				.item(0);

		if ((col_elem_TTR_MOI != null
				&& col_elem_TTR_MOI.getTextContent() != null && !col_elem_TTR_MOI
				.getTextContent().trim().equals(""))
				|| (col_elem_CS_MOI != null
						&& col_elem_CS_MOI.getTextContent() != null && Float
						.parseFloat(col_elem_CS_MOI.getTextContent().trim()) > 0)) {
			return true;
		}
		return false;
	}

	/**
	 * Lấy vị trí biểu giá công tơ 3 fa
	 * @param loai_bcs
	 * @return
	 */
	public static int LayViTriBieuGiaCto3Fa(String loai_bcs){
		int bcs = -1;
		
		if(loai_bcs.equals("BT")){
			bcs = 0;
		}
		else if(loai_bcs.equals("CD")){
			bcs = 1;
		}
		else if(loai_bcs.equals("TD")){
			bcs = 2;
		}
		else if(loai_bcs.equals("SG")){
			bcs = 3;
		}
		else if(loai_bcs.equals("VC")){
			bcs = 4;
		}
		
		return bcs;
	}
	
	public static boolean backupToSDcard(Context ctx){
		try{
			String SDcardDirection = System.getenv("SECONDARY_STORAGE");
			File str_file_dst = new File(SDcardDirection + "/ESGCS_BACKUP");
			File[] files = str_file_dst.listFiles();
			
			if(files != null){
				if(files.length > 9){
					Arrays.sort(files, filecomparator);
					for (int i = 0; i < files.length - 9; i++) {
						if(files[i].exists()){
							files[i].delete();
						}
					}
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");
				String currentDateandTime = sdf.format(new Date());
				
				File file_src = new File(Environment.getExternalStorageDirectory() + DBFolderPath + "/ESGCS.s3db");
				if(!file_src.exists()){
					return true;
				}
				File str_file_dst_2 = null;
				
				str_file_dst_2 = new File(SDcardDirection + "/ESGCS_BACKUP" + "/ESGCS_" + currentDateandTime + ".s3db");
				
//				String[] allFilesDb = {str_file_dst_2.getPath()};
//				scanFile(ctx, allFilesDb);
				return CopyFile(file_src, str_file_dst_2);
			} else {
				return false;
			}
		} catch(Exception ex) {
			Log.e("backup error", ex.toString());
			return false;
		}
	}
	
	/**
	 * Copy file bằng stream
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public boolean copy(File src, File dst) {
		try {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);

			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Copy file bằng channel
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static boolean CopyFile(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		boolean r = false;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			r = true;
		} catch (Exception ex) {
			r = false;
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
		return r;
	}

	/**
	 * Xo�?a file
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void DeleteFile(String filePath) throws IOException {
		File fDel = new File(filePath);
		if (fDel.exists()) {
			fDel.delete();
		}
	}

	/**
	 * �?ổi tên file
	 * 
	 * @param oldFilePath
	 * @param newFilePath
	 * @throws IOException
	 */
	public void RenameFile(String oldFilePath, String newFilePath)
			throws IOException {
		File fRename = new File(oldFilePath);
		File fNewName = new File(newFilePath);
		if (fRename.exists()) {
			fRename.renameTo(fNewName);
		}
	}

	/**
	 * Show dialog thông ba�?o
	 * @param title
	 * @param msg
	 * @param context
	 * @param font_type (set null nếu muốn hiện kiểu chữ mặc định)
	 * @return
	 */
	public static AlertDialog msbox(String title, String msg, Context context, Typeface font_type) {
		TextView content = new TextView(context);
        content.setText(msg);
        content.setTextSize(Common.GetTextSizeByScreen(context));
        content.setPadding(10, 30, 10, 0);
        if(font_type != null)
        	content.setTypeface(font_type);
        
		AlertDialog ad = new AlertDialog.Builder(context)
				.setTitle(title)
//				.setMessage(msg)
		        .setView(content)
				.setPositiveButton("Đóng",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// To do
							}
						}).setCancelable(true).create();
		Window window = ad.getWindow();
		window.setGravity(Gravity.BOTTOM);
		ad.show();
		return ad;
	}
	/**
	 * Show dialog thông ba�?o
	 * @param title
	 * @param msg
	 * @param context
	 * @return
	 */
	public AlertDialog msbox(String title, String msg, Context context){
		AlertDialog ad = msbox(title,msg,context,null);
		return ad;
	}

	public static void ShowToast(Context context, String content, int duration, int size) {
		Toast toast = Toast.makeText(context, content, duration);
		LinearLayout toastLayout = (LinearLayout) toast.getView();
		TextView tvToast = (TextView) toastLayout.getChildAt(0);
		tvToast.setTextSize(26);
		toastLayout.setBackgroundColor(Color.parseColor("#990000"));//Color.parseColor("#00FFFF")
		tvToast.setTextColor(Color.WHITE);
		tvToast.setLayoutParams(new LinearLayout.LayoutParams(size, LayoutParams.WRAP_CONTENT));
		tvToast.setGravity(Gravity.CENTER);
		// toast.setGravity(Gravity.TOP, 0, 100);
		toast.show();
	}
	
	public void ShowToast(Context context, String content, int duration) {
		Toast toast = Toast.makeText(context, content, duration);
		LinearLayout toastLayout = (LinearLayout) toast.getView();
		TextView tvToast = (TextView) toastLayout.getChildAt(0);
		tvToast.setTextSize(26);
		toastLayout.setBackgroundColor(Color.parseColor("#990000"));//Color.parseColor("#00FFFF")
		tvToast.setTextColor(Color.WHITE);
		tvToast.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tvToast.setGravity(Gravity.CENTER);
		// toast.setGravity(Gravity.TOP, 0, 100);
		toast.show();
	}

	/**
	 * Chuyển string sang kiểu date
	 * 
	 * @param strDate
	 * @param strDateFormat
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public Calendar ConvertStringToCalendar(String strDate, String strDateFormat) {
		try {
			Calendar cld = new GregorianCalendar();
			SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
			Date date = dateFormat.parse(strDate);
			cld.setTime(date);
			return cld;
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Lâ�?y cỡ chữ theo ki�?ch thươ�?c màn hình
	 * 
	 * @param context
	 * @return
	 */
	public static int GetTextSizeByScreen(Context context) {
		int text_size = 15; // normal size (4 inches)
		int screen_size = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);
		// set cỡ chữ theo từng cỡ màn hình

		// nê�?u là màn hình 4 inches
		if (screen_size <= Configuration.SCREENLAYOUT_SIZE_NORMAL) {
			screen_size = 15;
		}
		// nê�?u là màn hình 7 inches hoặc 10 inches
		else { //if (screen_size == Configuration.SCREENLAYOUT_SIZE_LARGE || screen_size == Configuration.SCREENLAYOUT_SIZE_XLARGE)
			text_size = 20;
		}
		return text_size;
	}

	/**
	 * Chuyển Float -> String
	 * @param num
	 * @param pattern
	 * @return
	 */
	public static String ConvertFloatToString(Float num, String pattern) {
		DecimalFormatSymbols Symbols = new DecimalFormatSymbols(Locale.ENGLISH);
		DecimalFormat df = new DecimalFormat(pattern, Symbols);
		return df.format(num);
	}

	/**
	 * Chuyển Double -> String
	 * @param num
	 * @param pattern
	 * @return
	 */
	public static String ConvertDoubleToString(Double num, String pattern) {
		DecimalFormatSymbols Symbols = new DecimalFormatSymbols(Locale.ENGLISH);
		DecimalFormat df = new DecimalFormat(pattern, Symbols);
		return df.format(num);
	}

	/**
	 * Load lại folder để hiển thị những sổ bị ẩn trên máy
	 * @param ctx
	 */
	public void LoadFolder(final Context ctx) {
//		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            // only for KITKAT and newer versions
			// scan folder ESGCS
			String[] allFiles = new String [] {Environment.getExternalStorageDirectory() + "/ESGCS/ESGCS.cfg"};
			scanFile(ctx, allFiles);
            //scan folder DB
            File file_db = new File(Environment.getExternalStorageDirectory() + "/ESGCS/DB");
            String[] allFilesDb = file_db.list();
            for (int i = 0; i < allFilesDb.length; i++) {
            	allFilesDb[i] = Environment.getExternalStorageDirectory() + "/ESGCS/DB/" + allFilesDb[i];
			}
            if(allFilesDb != null)
            	scanFile(ctx, allFilesDb);
            //scan folder Backup
            File file_backup = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Backup");
            String[] allFilesBackup = file_backup.list();
            for (int i = 0; i < allFilesBackup.length; i++) {
            	allFilesBackup[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Backup/" + allFilesBackup[i];
			}
            if(allFilesBackup != null)
            	scanFile(ctx, allFilesBackup);
            //scan folder Temp
            File file_temp = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Temp");
            String[] allFilesTemp = file_temp.list();
            for (int i = 0; i < allFilesTemp.length; i++) {
            	allFilesTemp[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Temp/" + allFilesTemp[i];
			}
            if(allFilesTemp != null)
            	scanFile(ctx, allFilesTemp);
            //scan folder Photo
            File file_photo = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Photo");
            String[] allFilesPhoto = file_photo.list();
            for (int i = 0; i < allFilesPhoto.length; i++) {
            	allFilesPhoto[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + allFilesPhoto[i];
            	File f = new File(allFilesPhoto[i]);
            	String[] allFilesPhoto2 = f.list();
            	for (int j = 0; j < allFilesPhoto2.length; j++) {
            		allFilesPhoto2[j] = allFilesPhoto[i] + "/" + allFilesPhoto2[j];
				}
            	if(allFilesPhoto2 != null)
                	scanFile(ctx, allFilesPhoto2);
			}
            
            //scan folder format
            File file_format = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Format/tessdata");
            String[] allFilesFormat = file_format.list();
            for (int i = 0; i < allFilesFormat.length; i++) {
            	allFilesFormat[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Format/tessdata/" + allFilesFormat[i];
			}
            if(allFilesFormat != null)
            	scanFile(ctx, allFilesFormat);
//        } else {
//        	try{
//        		ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/ESGCS")));
//        	} catch(Exception ex) {
//        		ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/ESGCS")));
//        	}
//        }
	}
	
	/**
	 * Load lại folder để hiển thị những sổ bị ẩn trên máy
	 * @param ctx
	 */
	public void LoadFolder2(final Context ctx) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            // only for KITKAT and newer versions
			// scan folder ESGCS
			String[] allFiles = new String [] {Environment.getExternalStorageDirectory() + "/ESGCS/ESGCS.cfg"};
			scanFile(ctx, allFiles);
            //scan folder DB
            File file_db = new File(Environment.getExternalStorageDirectory() + "/ESGCS/DB");
            String[] allFilesDb = file_db.list();
            for (int i = 0; i < allFilesDb.length; i++) {
            	allFilesDb[i] = Environment.getExternalStorageDirectory() + "/ESGCS/DB/" + allFilesDb[i];
			}
            if(allFilesDb != null)
            	scanFile(ctx, allFilesDb);
            //scan folder Backup
            File file_backup = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Backup");
            String[] allFilesBackup = file_backup.list();
            for (int i = 0; i < allFilesBackup.length; i++) {
            	allFilesBackup[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Backup/" + allFilesBackup[i];
			}
            if(allFilesBackup != null)
            	scanFile(ctx, allFilesBackup);
            //scan folder Temp
            File file_temp = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Temp");
            String[] allFilesTemp = file_temp.list();
            for (int i = 0; i < allFilesTemp.length; i++) {
            	allFilesTemp[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Temp/" + allFilesTemp[i];
			}
            if(allFilesTemp != null)
            	scanFile(ctx, allFilesTemp);
            //scan folder format
            File file_format = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Format/tessdata");
            String[] allFilesFormat = file_format.list();
            for (int i = 0; i < allFilesFormat.length; i++) {
            	allFilesFormat[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Format/tessdata/" + allFilesFormat[i];
			}
            if(allFilesFormat != null)
            	scanFile(ctx, allFilesFormat);
        } else {
        	try{
        		ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/ESGCS")));
        	} catch(Exception ex) {
        		ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/ESGCS")));
        	}
        }
	}
	
	public void LoadFolderPhoto(final ContextWrapper ctx) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			try{
	            File file_db = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Photo/");
	            String[] allFilesPhoto = file_db.list();
	            for (int i = 0; i < allFilesPhoto.length; i++) {
	            	allFilesPhoto[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + allFilesPhoto[i];
	            	String[] allFilesPhoto2 = new File(allFilesPhoto[i]).list();
	            	for (int j = 0; j < allFilesPhoto2.length; j++) {
	            		allFilesPhoto2[j] = allFilesPhoto[i] + allFilesPhoto2[j];
//	            		new SingleMediaScanner(ctx, new File(allFilesPhoto2[j]));
	            	}
	            	if(allFilesPhoto2 != null){
	                	scanFile2(ctx, allFilesPhoto2);
	            	}
				}
			} catch(Exception ex) {
				ShowToast(ctx, "Lỗi: " + ex.toString(), Toast.LENGTH_LONG);
			}
        } else {
        	try{
        		ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/ESGCS/Photo/")));
        	} catch(Exception ex) {
        		ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/ESGCS/Photo/")));
        	}
        }
	}
	
	public void loadFile(final ContextWrapper ctx){
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            File file_db = new File(Environment.getExternalStorageDirectory() + "");
            String[] allFilesDb = file_db.list();
            for (int i = 0; i < allFilesDb.length; i++) {
            	allFilesDb[i] = Environment.getExternalStorageDirectory() + "/" + allFilesDb[i];
			}
            if(allFilesDb != null)
            	scanFile(ctx, allFilesDb);
        } else {
        	ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "")));
        }
	}
	
	public void scanFile(Context ctx, String[] allFiles) {
		MediaScannerConnection.scanFile(ctx, allFiles, null, new MediaScannerConnection.OnScanCompletedListener()
                {
                    public void onScanCompleted(String path, Uri uri)
                    {
                        //Log.d("ExternalStorage", "Scanned " + path + ":");
                        //Log.d("ExternalStorage", "uri=" + uri);
                    }
                });
	}
	
	public void scanFile2(Context ctx, String[] allFiles) {
		MediaScannerConnection.scanFile(ctx, allFiles, new String[] { "image/jpeg" }, null);
	}

	/**
	 * Kiểm tra công tơ đã ghi chưa
	 * @param TTR_MOI
	 * @param CS_MOI
	 * @return
	 */
	public boolean isSavedRow(String TTR_MOI, String CS_MOI) {

		if ((TTR_MOI != null && TTR_MOI.trim().length() > 0)
				|| (CS_MOI != null && CS_MOI.trim().length() > 0 && (CS_MOI != null && Float
						.parseFloat(CS_MOI.trim()) > 0))) {
			return true;
		}
		return false;
	}

	/**
	 * Mã hóa AES
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	@SuppressLint("TrulyRandom")
	public static void encrypt() throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException {
		// Here you read the cleartext.
		File extStore = Environment.getExternalStorageDirectory();
		FileInputStream fis = new FileInputStream(extStore + "/ESGCS");
		// This stream write the encrypted text. This stream will be wrapped by
		// another stream.
		FileOutputStream fos = new FileOutputStream(extStore + "/ESGCS");

		// Length is 16 byte
		SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(),
				"AES");
		// Create cipher
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, sks);
		// Wrap the output stream
		CipherOutputStream cos = new CipherOutputStream(fos, cipher);
		// Write bytes
		int b;
		byte[] d = new byte[8];
		while ((b = fis.read(d)) != -1) {
			cos.write(d, 0, b);
		}
		// Flush and close streams.
		cos.flush();
		cos.close();
		fis.close();
	}

	/**
	 * Giải mã AES
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public static void decrypt() throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException {

		File extStore = Environment.getExternalStorageDirectory();
		FileInputStream fis = new FileInputStream(extStore + "/encrypted");

		FileOutputStream fos = new FileOutputStream(extStore + "/decrypted");
		SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(),
				"AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, sks);
		CipherInputStream cis = new CipherInputStream(fis, cipher);
		int b;
		byte[] d = new byte[8];
		while ((b = cis.read(d)) != -1) {
			fos.write(d, 0, b);
		}
		fos.flush();
		fos.close();
		cis.close();
	}

	/**
	 * Thêm đuôi xml nếu chưa có
	 * @param fileName
	 * @return
	 */
	public static String convertFileName(String fileName) {
		String s = fileName.substring(fileName.length() - 4);
		if (!s.equals(".xml")) {
			fileName += ".xml";
		}
		return fileName;
	}

	/**
	 * Convert array -> String
	 * @param array
	 * @return
	 */
	public String ConvertArray2String(String[] array){
		if(array == null || array.length == 0){
			return null;
		}
		String str = "";
		for(String item : array){
			str += item+",";
		}
		str = str.substring(0, str.lastIndexOf(","));
		return str;
	}

	public List<String> GetSameItems(String[] arr1, String[] arr2){
		List<String> list = new ArrayList<String>();
		String[] arr_temp = arr2;
		for(String item_arr_1 : arr1){
			for(String item_arr_2 : arr_temp){
				String item1 = item_arr_1.toLowerCase(Locale.ENGLISH).trim();
				String item2 = item_arr_2.toLowerCase(Locale.ENGLISH).trim();
				if(item1.equals(item2)){
					list.add(item_arr_1);
					break;
				}
			}
		}
		
		return list;
	}
	
	/**
	 * Tạo backup 
	 */
	@SuppressLint("SimpleDateFormat")
	public boolean CreateBackup(String filename_suffix){
		// Chuyển th�?i gian hiện tại thành string
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String currentDateandTime = sdf.format(new Date());
		
		//-> copy db vào folder backup 
		File file_src = new File(Environment.getExternalStorageDirectory() + DBFolderPath + "/ESGCS.s3db");
		if(!file_src.exists()){
			return true;
		}
		File str_file_dst = null;
		if(filename_suffix != null){
			filename_suffix = "_"+filename_suffix.trim();
		}else{
			filename_suffix = "";
		}
		
		str_file_dst = new File(Environment.getExternalStorageDirectory() + BackupFolderPath + "/ESGCS_"+currentDateandTime+filename_suffix+".s3db");
		
//		str_file_dst = new File(Environment.getExternalStorageDirectory() +common.BackupFolderPath + "/ESGCS_"+currentDateandTime+".s3db");
		
		return copy(file_src, str_file_dst);
	}

	public void deleteBackup(){
		try{
			File str_file_dst = new File(Environment.getExternalStorageDirectory() + BackupFolderPath);
			File[] files = str_file_dst.listFiles();
			
			if(files.length > 9){
				Arrays.sort(files, filecomparator);
				for (int i = 0; i < files.length - 9; i++) {
					if(files[i].exists()){
						files[i].delete();
					}
				}
			}
			
//			Calendar c = Calendar.getInstance();
//			int cmonth = c.get(Calendar.MONTH) + 1;
//			
//			String [] ls_file = str_file_dst.list();
//			for (int i = 0; i < ls_file.length; i++) {
//				String fname = ls_file[i].split("_")[ls_file.length - 2].toString().substring(4, 6);
//				int dmonth = Integer.parseInt(fname);
//				if(dmonth != cmonth){
//					File fNameDelete = new File(Environment.getExternalStorageDirectory() + BackupFolderPath + "/" + ls_file[i]);
//					if(fNameDelete.exists()){
//						fNameDelete.delete();
//					}
//				}
//			}
		} catch(Exception ex) {
			ex.toString();
		}
	}
	
	public static Comparator<? super File> filecomparator = new Comparator<File>(){
		  
		  public int compare(File file1, File file2) {

		   if(file1.isDirectory()){
			   if (file2.isDirectory()){
//				    return String.valueOf(file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
				   
					Date dCreate1 = new Date(file1.lastModified());
					Date dCreate2 = new Date(file2.lastModified());
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					String newFormat1 = formatter.format(dCreate1);
					String newFormat2 = formatter.format(dCreate2);
				   return String.valueOf(newFormat1.toLowerCase()).compareTo(newFormat2.toLowerCase());
			   }else{
				   return -1;
			   }
		   } else {
			   if (file2.isDirectory()){
				   return 1;
			   } else {
//				    return String.valueOf(file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
				   
					Date dCreate1 = new Date(file1.lastModified());
					Date dCreate2 = new Date(file2.lastModified());
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					String newFormat1 = formatter.format(dCreate1);
					String newFormat2 = formatter.format(dCreate2);
					return String.valueOf(newFormat1.toLowerCase()).compareTo(newFormat2.toLowerCase());
			   }
		   }
		  }
	};
	/**
	 *  Lấy khoảng cách chuổi
	 * @param name
	 * @param size
	 * @return
	 */
	public String distance(String name, float size){
		Paint paint = new Paint();
		float w = paint.measureText(name);
		float len = size - w;
		String str_length = " ";
		while(paint.measureText(str_length) < len){
			str_length += " ";
		}
		return str_length;
	}
	
	/**
	 * Convert array string -> list<String>
	 * @param array
	 * @return
	 */
	public List<String> ConvertArrayString2List(String[] array){
		List<String> list = new ArrayList<String>();
		for(String item : array){
			list.add(item);
		}
		return list;
	}
	
	/**
	 * Kiểm tra db đã có chưa. Nếu chưa có thì tạo mới
	 * @param context
	 * @param db_name
	 * @param db_folder
	 * @return
	 */
	public String CheckDbExist(Context context,String db_name, String db_folder){
		try{
//			 kiểm tra file db đã có chưa
			SQLiteConnection db_conn = new SQLiteConnection(context,db_name, db_folder);
			db_conn.CreateTableSqlite_GCS_CHISO_HHU();
			db_conn.CreateTableSqlite_GCS_SO_NVGCS();
			db_conn.CreateTableSqlite_gcsindex();
			db_conn.CreateTableSqlite_GCS_LO_TRINH();
			db_conn.CreateTableSqlite_GCS_LOG_DELETE();
			db_conn.CreateTableSqlite_GCS_LOG();
			db_conn.close();
			return "EXIST";
		}
		catch(Exception ex){
			return ex.getMessage();
		}
	}
			 
	public boolean checkDB(){
		String filePath = Environment.getExternalStorageDirectory() + Common.DBFolderPath + "/ESGCS.s3db";
		File cfgFile = new File(filePath);
		if(cfgFile.exists()){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Lấy kích thước màn hình
	 * @param act
	 * @return
	 */
	public Point GetScreenSize(Activity act){
		Display d = act.getWindowManager().getDefaultDisplay();
		Point point = new Point();
		d.getSize(point);
		return point;
	}
	
	/**
	 * Lấy chi�?u ngang của màn hình
	 * @param act
	 * @return
	 */
	public int GetScreenSizeXShowToast(Activity act){
		Point point = GetScreenSize(act);
		int size = point.x - 65;
		return size;
	}
	
	/**
	 * Show/hide tạm th�?i bàn phi�?m ảo
	 * 
	 * @param show
	 */
	public static void VisibleKeyBoard(Context context, boolean show) {

		if (show) {
			// inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
			// .getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			            
		} else {
			// inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
			// .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
			
		}
	}
	
	/**
	 * Lấy loại kích thước màn hình
	 * @param context
	 * @return
	 */
	public static int GetScreenSizeType(Context context){
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);
	}
	
	public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd;
    }
	
	/**
	 * Chuyển mảng byte thành Bitmap
	 */
	public static Bitmap decodeBase64(String input) {
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

	/**
	 * Chuyển mảng byte thành Bitmap
	 * 
	 */
	public static Bitmap decodeBase64Byte(byte[] decodedByte) {
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

	/**
	 * Chuyển Bitmap thành mảng byte
	 * 
	 */
	public static String encodeTobase64(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
		return imageEncoded;
	}
	
	public static byte[] encodeTobase64Byte(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		return b;
	}
	
//	public static String convertFile(String fileName, SQLiteConnection connection){
//		try{
//			String file = "";
//			Cursor c = connection.getFileDataRoute();
//			if(c.moveToFirst()){
//				do {
//					if(c.getString(0).split("_")[0].equals(fileName)){
//						file = c.getString(0);
//					}
//				} while (c.moveToNext());
//			}
//			return file;
//		} catch(Exception ex) {
//			ex.toString();
//			return fileName;
//		}
//	}
	
	public static void zip(String[] _files, String zipFileName) {
		try {
			final int BUFFER = 2048;
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(zipFileName);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			byte data[] = new byte[BUFFER];

			for (int i = 0; i < _files.length; i++) {
				Log.v("Compress", "Adding: " + _files[i]);
				FileInputStream fi = new FileInputStream(_files[i]);
				origin = new BufferedInputStream(fi, BUFFER);

				ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
				out.putNextEntry(entry);
				int count;

				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	private static void zipSubFolder(ZipOutputStream out, File folder,
//	        int basePathLength) throws IOException {
//
//	    final int BUFFER = 2048;
//
//	    File[] fileList = folder.listFiles();
//	    BufferedInputStream origin = null;
//	    for (File file : fileList) {
//	        if (file.isDirectory()) {
//	            zipSubFolder(out, file, basePathLength);
//	        } else {
//	            byte data[] = new byte[BUFFER];
//	            String unmodifiedFilePath = file.getPath();
//	            String relativePath = unmodifiedFilePath
//	                    .substring(basePathLength);
//	            Log.i("ZIP SUBFOLDER", "Relative Path : " + relativePath);
//	            FileInputStream fi = new FileInputStream(unmodifiedFilePath);
//	            origin = new BufferedInputStream(fi, BUFFER);
//	            ZipEntry entry = new ZipEntry(relativePath);
//	            out.putNextEntry(entry);
//	            int count;
//	            while ((count = origin.read(data, 0, BUFFER)) != -1) {
//	                out.write(data, 0, count);
//	            }
//	            origin.close();
//	        }
//	    }
//	}
	
	public void scanZipPhotoFiles(ArrayList<String> arr_selected){
		try{
			File file_photo = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Photo");
	        String[] allFilesPhoto = file_photo.list();
	        ArrayList<String> arr = new ArrayList<String>();
	        
	        for (int i = 0; i < allFilesPhoto.length; i++) {
	        	if(arr_selected.contains(allFilesPhoto[i].split("_")[0].toString())){
	        		allFilesPhoto[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + allFilesPhoto[i];
	        		arr.add(allFilesPhoto[i]);
	        	}
			}
	        
	        for (int i = 0; i < arr.size(); i++) {
	        	File f = new File(arr.get(i));
	        	String[] allFiles = f.list();
	        	for (int j = 0; j < allFiles.length; j++) {
	        		allFiles[j] = arr.get(i) + "/" + allFiles[j];
				}
	        	zip(allFiles, arr.get(i) + ".zip");
			}
	        
	        arr.clear();
	        String[] allFilesPhoto2 = file_photo.list();
	        for (int i = 0; i < allFilesPhoto2.length; i++) {
	        	if(allFilesPhoto2[i].contains(".zip")){
	        		allFilesPhoto2[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + allFilesPhoto2[i];
	        		arr.add(allFilesPhoto2[i]);
	        	}
	        }
	        
	        arr.add(Environment.getExternalStorageDirectory() + Common.DBFolderPath + "/ESGCS.s3db");
	        allFilesPhoto2 = arr.toArray(new String[arr.size()]);
			zip(allFilesPhoto2, Environment.getExternalStorageDirectory() + "/ESGCS/ESGCS.zip");
		} catch(Exception ex) {
			ex.toString();
		}
	}
	
	public static void scanZipFiles(ArrayList<String> arr_selected){
		try{
			File file_photo = new File(Environment.getExternalStorageDirectory() + "/ESGCS/Photo");
	        String[] allFilesPhoto = file_photo.list();
	        ArrayList<String> arr = new ArrayList<String>();
	        
	        for (int i = 0; i < allFilesPhoto.length; i++) {
	        	if(arr_selected.contains(allFilesPhoto[i].split("_")[0].toString())){
	        		allFilesPhoto[i] = Environment.getExternalStorageDirectory() + "/ESGCS/Photo/" + allFilesPhoto[i];
	        		arr.add(allFilesPhoto[i]);
	        	}
			}
	        arr.add(Environment.getExternalStorageDirectory() + Common.DBFolderPath + "/ESGCS.s3db");
	        allFilesPhoto = arr.toArray(new String[arr.size()]);
			zip(allFilesPhoto, Environment.getExternalStorageDirectory() + "/ESGCS/ESGCS.zip");
		} catch(Exception ex) {
			ex.toString();
		}
	}

	@SuppressWarnings("deprecation")
	public static int size(Activity ac) {
		Display d = ac.getWindowManager().getDefaultDisplay();
		return d.getWidth() - 65;
	}

	public String convertFile(String fileName, SQLiteConnection connection){
		try{
			String file = "";
			Cursor c = connection.getFileDataRoute();
			if(c.moveToFirst()){
				do {
					if(c.getString(0).split("_")[0].equals(fileName)){
						file = c.getString(0);
					}
				} while (c.moveToNext());
			}
			return file;
		} catch(Exception ex) {
			ex.toString();
			return fileName;
		}
	}

	public static Double getInch(Activity ctx){
		try{
			DisplayMetrics dm = new DisplayMetrics();
			ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int widthPixels = dm.widthPixels;
			int heightPixels = dm.heightPixels;
		    double x = Math.pow(widthPixels/dm.xdpi,2);
		    double y = Math.pow(heightPixels/dm.ydpi,2);
		    double screenInches = Math.sqrt(x+y);
		    return screenInches;
		} catch(Exception ex) {
			ex.toString();
			return -1d;
		}
	}
	
	public static String getSSID(Context context){
		try{
			@SuppressWarnings("static-access")
			WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			String ssid = wifiInfo.getSSID();
			return ssid;
		} catch(Exception ex) {
			return "";
		}
	}
	
	public static void clearApplicationData(Context ctx)
	{
	    File cache = ctx.getCacheDir();
	    File appDir = new File(cache.getParent());
	    if (appDir.exists()) {
	        String[] children = appDir.list();
	        for (String s : children) {
	            if (!s.equals("lib")) {
	                boolean delete = deleteDir(new File(appDir, s));
//	                Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
	                if(delete){
//	                	delete = delete;
	                }
	            }
	        }
	    }
	}

	public static boolean deleteDir(File dir) 
	{
	    if (dir != null && dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    return dir.delete();
	}
	
	public static long getMemory(Context ctx, int state){
		try{
			MemoryInfo mi = new MemoryInfo();
			@SuppressWarnings("static-access")
			ActivityManager activityManager = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
			activityManager.getMemoryInfo(mi);
			long availableMegs = mi.availMem / 1048576L;

			//Percentage can be calculated for API 16+
			long percentAvail = mi.availMem / mi.totalMem;
			if(state == 0){
				return availableMegs;
			} else {
				return percentAvail;
			}
			
		} catch(Exception ex){
			return 0L;
		}
	}
	
	public static long getUsedMemorySize(int state) {

	    long freeSize = 0L;
	    long totalSize = 0L;
	    long usedSize = -1L;
	    try {
	        Runtime info = Runtime.getRuntime();
	        freeSize = info.freeMemory();
	        totalSize = info.totalMemory();
	        usedSize = totalSize - freeSize;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    if(state == 0)
	    	return freeSize;
	    else if(state == 1)
	    	return totalSize;
	    else
	    	return usedSize;
	}
	
//	static int A, R, G, B;
//    static int pixel;
    
	/** Chuyển ảnh đen trắng
	 * @param src
	 * @return
	 */
	public static Bitmap createBlackAndWhite(final Bitmap src, final int pro) {
	    final int width = src.getWidth();
	    final int height = src.getHeight();
	    // create output bitmap
	    final Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
	    // color information
	    int A, R, G, B;
	    int pixel;

	    // scan through all pixels
	    for (int x = 0; x < width; ++x) {
	        for (int y = 0; y < height; ++y) {
	            // get pixel color
	            pixel = src.getPixel(x, y);
	            A = Color.alpha(pixel);
	            R = Color.red(pixel);
	            G = Color.green(pixel);
	            B = Color.blue(pixel);
	            int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

	            // use 128 as threshold, above -> white, below -> black
	            if (gray > pro)//128 
	                gray = 0;
	            else
	                gray = 255;
	            // set new pixel color to output bitmap
//	            int color = Color.argb(A, R, G, B);
	            int color = Color.argb(A, gray, gray, gray);
	            bmOut.setPixel(x, y, color);//Color.argb(A, gray, gray, gray)
	        }
	    }
	    return bmOut;
	}

	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}
	
	public static Boolean checkSDCard() {
		Boolean isSDPresent = Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED);
		return isSDPresent;
	}

//	private static boolean isExternalStorageReadOnly() {
//		String extStorageState = Environment.getExternalStorageState();
//		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
//			return true;
//		}
//		return false;
//	}
//
//	private static boolean isExternalStorageAvailable() {
//		String extStorageState = Environment.getExternalStorageState();
//		if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
//			return true;
//		}
//		return false;
//	}
		 
	public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
	        boolean filter) {
	    float ratio = Math.min(
	            (float) maxImageSize / realImage.getWidth(),
	            (float) maxImageSize / realImage.getHeight());
	    int width = Math.round((float) ratio * realImage.getWidth());
	    int height = Math.round((float) ratio * realImage.getHeight());

	    Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
	            height, filter);
	    return newBitmap;
	}

	public static boolean isNetworkOnline(Context context) {
		boolean status = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null
					&& netInfo.getState() == NetworkInfo.State.CONNECTED) {
				status = true;
			} else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo != null
						&& netInfo.getState() == NetworkInfo.State.CONNECTED)
					status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return status;

	}

	public static Bitmap drawableToBitmap (Drawable drawable) {
	    Bitmap bitmap = null;

	    if (drawable instanceof BitmapDrawable) {
	        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
	        if(bitmapDrawable.getBitmap() != null) {
	            return bitmapDrawable.getBitmap();
	        }
	    }

	    if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
	        bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
	    } else {
	        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
	    }

	    Canvas canvas = new Canvas(bitmap);
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);
	    return bitmap;
	}

	public static void moveFile(String inputPath, String inputFile, String outputPath) {
		InputStream in = null;
		OutputStream out = null;
		try {

			// create output directory if it doesn't exist
			File dir = new File(outputPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			in = new FileInputStream(inputPath + inputFile);
			out = new FileOutputStream(outputPath + inputFile);

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.close();
			in = null;

			// write the output file
			out.flush();
			out.close();
			out = null;

			// delete the original file
			new File(inputPath + inputFile).delete();

		}

		catch (FileNotFoundException fnfe1) {
			Log.e("tag", fnfe1.getMessage());
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}

	}

	//region date time utils

	public enum DATE_TIME_TYPE {
		HHmmss,
		yyyyMMdd,
		yyyyMMddHHmmssSSS,
		MMyyyy,
		ddMMyyyy,
		ddMMyyyyHHmmss,
		FULL;

		@Override
		public String toString() {
			if (this == HHmmss)
				return "HHmmss";
			if (this == yyyyMMdd)
				return "yyyyMMdd";
			if (this == yyyyMMddHHmmssSSS)
				return "yyyyMMddHHmmssSSS";
			if (this == MMyyyy)
				return "MM/yyyy";
			if (this == ddMMyyyy)
				return "dd/MM/yyyy";
			if (this == ddMMyyyyHHmmss)
				return "dd/MM/yyyy HH:mm:ss";
			if (this == FULL)
				return "yyyy-MM-dd HH:mm:ss";
			return super.toString();
		}
	}

	public static String getDateTimeNow(Common.DATE_TIME_TYPE formatDate) {
		SimpleDateFormat df = new SimpleDateFormat(formatDate.toString());
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df.format(Calendar.getInstance().getTime());
	}

	public static String convertDateToDate(String time, DATE_TIME_TYPE typeDefault, DATE_TIME_TYPE typeConvert) {
		if (time == null || time.trim().isEmpty())
			return "";

		if (typeDefault != DATE_TIME_TYPE.FULL) {
			time = time.replaceAll("-", "");
			for (int i = time.length(); i <= 17; i++) {
				time += "0";
			}
		}

		long longDate = convertDateToLong(time, typeDefault);

		String dateByDefaultType = convertLongToDate(longDate, typeConvert);
		return dateByDefaultType;
	}

	public static long convertDateToLong(String date, DATE_TIME_TYPE typeDefault) {
		SimpleDateFormat formatter = new SimpleDateFormat(typeDefault.toString());
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date dateParse;
		try {
			dateParse = (Date) formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}

		return dateParse.getTime();
	}

	public static String convertLongToDate(long time, Common.DATE_TIME_TYPE format) {
		if (time < 0)
			return null;
		if (format == null)
			return null;

		SimpleDateFormat df2 = new SimpleDateFormat(format.toString());
		df2.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date(time);
		return df2.format(date);
	}
	//endregion
}

