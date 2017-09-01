package esolutions.com.gcs_svn_old.com.camera.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

public class Billing {
	public static final String[] col_HoaDon_Arr = { "MA_DDO", "TGBD", "SAN_LUONG", "DON_GIA", "SO_TIEN"};
	public static final String[] col_Gia_Arr = {"DINH_MUC","DON_GIA","MA_NGIA"};
	public static ArrayList<LinkedHashMap<String, String>> list_gia;
	public static final String PriceFileName = "shbt.xml";
	
	Common comm;
	
	ArrayList<LinkedHashMap<String, String>> list_hd;
	
	Context ct;
	
	
	public Billing(){
		comm = new Common();
		if(list_gia==null){
			list_gia = GetDataPriceFromFile(new File(Environment.getExternalStorageDirectory()+Common.programPath+PriceFileName));
		}
	}

	@SuppressLint("SimpleDateFormat")
	public ArrayList<LinkedHashMap<String, String>> DoBilling(LinkedHashMap<String, String> lhm_row, Context ct ){
		this.ct = ct;
		String strChuoiGia;
		int slKT = 0;
		list_hd = new ArrayList<LinkedHashMap<String,String>>();
		try{
			strChuoiGia = lhm_row.get("CHUOI_GIA").toString().trim();

			int sl_moi = (lhm_row.get("SL_MOI") != null && lhm_row.get("SL_MOI").length() > 0 ? Integer.parseInt(lhm_row.get("SL_MOI").toString()) : 0 );
			int sl_thao = (lhm_row.get("SL_THAO") != null && lhm_row.get("SL_THAO").length() > 0 ? Integer.parseInt(lhm_row.get("SL_THAO").toString()) : 0 );
					
			if(!"VC,SG".contains(lhm_row.get("LOAI_BCS").toString())){
				if(strChuoiGia.equals("KT: 100%*SHBT-A")){
//					Calendar calendar_DK = comm.ConvertStringToCalendar(lhm_row.get("NGAY_CU"), "MM/dd/yyyy");
//					Calendar calendar_CK = comm.ConvertStringToCalendar(lhm_row.get("NGAY_MOI"), "MM/dd/yyyy");
										
//					int NoDayInMonthDK = calendar_DK.getActualMaximum(Calendar.DAY_OF_MONTH);
//					if(1 + GetDateDiff(calendar_DK, calendar_CK) == NoDayInMonthDK){
						short so_ho = (lhm_row.get("SO_HO") != null && lhm_row.get("SO_HO").length() > 0 ? Short.parseShort(lhm_row.get("SO_HO").toString()) : 1);
						SHBTKT(lhm_row.get("MA_DDO"), so_ho, sl_moi+sl_thao, lhm_row.get("CHUOI_GIA"));
//                    }
				}
				else if(strChuoiGia.equals("BT: 100%*SHBT-A; CD: 100%*SHBT-A; TD: 100%*SHBT-A")){
					Calendar calendar_DK = comm.ConvertStringToCalendar(lhm_row.get("NGAY_CU"), "MM/dd/yyyy");
					Calendar calendar_CK = comm.ConvertStringToCalendar(lhm_row.get("NGAY_MOI"), "MM/dd/yyyy");
					int NoDayInMonthDK = calendar_DK.getActualMaximum(Calendar.DAY_OF_MONTH);

					if(1 + GetDateDiff(calendar_DK, calendar_CK) == NoDayInMonthDK){
						
						if( lhm_row.get("MA_DDO").toString().equals("TD")) {//bcs cuoi cung
							short so_ho = (lhm_row.get("SO_HO") != null && lhm_row.get("SO_HO").length() > 0 ? Short.parseShort(lhm_row.get("SO_HO").toString()) : 1);
                            slKT = slKT + sl_moi+sl_thao;
                            SHBTKT(lhm_row.get("MA_DDO"), so_ho, slKT, lhm_row.get("CHUOI_GIA"));
						}
						else if( (lhm_row.get("MA_DDO").toString().equals("BT")) ){
                            slKT = sl_moi+sl_thao;
						}
                        else{
                            slKT = slKT + sl_moi+sl_thao;
                        }
					}	   

				}
				else if (strChuoiGia.startsWith("BT: 100%") && !strChuoiGia.contains("SH")) {
	                if (strChuoiGia.split(";").length == 3) {
	                    SXKDHC(lhm_row.get("MA_DDO"), sl_moi+sl_thao, lhm_row.get("CHUOI_GIA"), lhm_row.get("LOAI_BCS"));
	                }
				}
				else if(strChuoiGia.startsWith("KT: 100%") && !strChuoiGia.contains("SH")){
                    SXKDHC(lhm_row.get("MA_DDO"), sl_moi+sl_thao, lhm_row.get("CHUOI_GIA"), lhm_row.get("LOAI_BCS"));
				}
			}
				
			return list_hd;
			
			
		}catch(Exception ex){
			return null;
		}
	}
	
	private int GetDateDiff(Calendar calStart , Calendar calEnd){
		int date_diff = 0;
		if(calEnd.get(Calendar.DAY_OF_YEAR) < calStart.get(Calendar.DAY_OF_YEAR) && calEnd.get(Calendar.YEAR) > calStart.get(Calendar.YEAR)){
			date_diff = calStart.getActualMaximum(Calendar.DAY_OF_YEAR) - calStart.get(Calendar.DAY_OF_YEAR) + calEnd.get(Calendar.DAY_OF_YEAR);
		}
		else{
			date_diff = calStart.get(Calendar.DAY_OF_YEAR) - calEnd.get(Calendar.DAY_OF_YEAR);
		}
		return date_diff;
	}
	
	private void SHBTKT(String strMaDDo , short iSoHo, int iDienTThu, String strChuoiGia){
		
		int iBT, iSLConLai, iSLBacThang;
		ArrayList<LinkedHashMap<String,String>> list_row = new ArrayList<LinkedHashMap<String,String>>();
		LinkedHashMap<String,String> lhm_row_CT ;
		if(strChuoiGia.endsWith("A")){
			for(LinkedHashMap<String, String> lhm_gia : list_gia){
				if(lhm_gia.get("MA_NGIA").equals("A")){
					list_row.add(lhm_gia);
				}
			}
		}
		else{
			for(LinkedHashMap<String, String> lhm_gia : list_gia){
				if(lhm_gia.get("MA_NGIA").equals("N")){
					list_row.add(lhm_gia);
				}
			}
		}
		
		iSLConLai = iDienTThu;
		iBT = 0;
		while (iSLConLai > 0){
			int dinh_muc = (list_row.get(iBT).get("DINH_MUC") != null && list_row.get(iBT).get("DINH_MUC").length() > 0 ? Integer.parseInt(list_row.get(iBT).get("DINH_MUC")): 0) ;
			int dinh_muc_so_ho = dinh_muc * iSoHo;
			if( dinh_muc_so_ho < iSLConLai){
				if (dinh_muc == 0){
					iSLBacThang = iSLConLai;
				}
				else{
					iSLBacThang = dinh_muc_so_ho;
				}
			}
		    else{
		        iSLBacThang = iSLConLai;
		    }
		    
		    lhm_row_CT = new LinkedHashMap<String, String>();
		    int don_gia = list_row.get(iBT).get("DON_GIA") != null && list_row.get(iBT).get("DON_GIA").length() > 0 ? Integer.parseInt(list_row.get(iBT).get("DON_GIA")) : 0;
		    //gan ket qua cho lhm_row_CT
		    lhm_row_CT.put(col_HoaDon_Arr[0],strMaDDo) ;
		    lhm_row_CT.put(col_HoaDon_Arr[1], "KT") ;
		    lhm_row_CT.put(col_HoaDon_Arr[2], iSLBacThang + "") ;
		    lhm_row_CT.put(col_HoaDon_Arr[3], don_gia + "") ;
		    lhm_row_CT.put(col_HoaDon_Arr[4], (iSLBacThang * don_gia) + "") ;
		    //xong ket qua
		    list_hd.add(lhm_row_CT);
		    iBT = iBT + 1;
		    iSLConLai = iSLConLai - iSLBacThang;
		}
	}

	private void SXKDHC(String strMaDDo, int iDienTThu, String strChuoiGia, String strBCS){
		int iDonGia;
		LinkedHashMap<String, String> lhm_row_CT = new LinkedHashMap<String, String>();
		String strChuoiGia1;
		
	    if (strBCS.equals("BT") )
	        strChuoiGia1 = strChuoiGia.split(";")[0];
        else if( strBCS.equals("CD") )
	        strChuoiGia1 = strChuoiGia.split(";")[1];
	    else if (strBCS.equals("TD") )
	        strChuoiGia1 = strChuoiGia.split(";")[2];
	    else
	        strChuoiGia1 = strChuoiGia.split(";")[0];

	    strChuoiGia1 = strChuoiGia1.split("-")[0];
	    iDonGia = (strChuoiGia1.split("[*]")[1].length() > 0 ? Integer.parseInt(strChuoiGia1.split("[*]")[1]) : 0);
	    //gan ket qua cho dr
	    lhm_row_CT.put(col_HoaDon_Arr[0],strMaDDo) ;
		lhm_row_CT.put(col_HoaDon_Arr[1],strBCS);
		lhm_row_CT.put(col_HoaDon_Arr[2],iDienTThu+"");
		lhm_row_CT.put(col_HoaDon_Arr[3],iDonGia+"");
		lhm_row_CT.put(col_HoaDon_Arr[4],(iDienTThu * iDonGia)+"");
	    //xong ket qua
	    list_hd.add(lhm_row_CT);
	}

	private ArrayList<LinkedHashMap<String, String>> GetDataPriceFromFile(File file_price){
		if(!file_price.exists()){
			return null;
		}
		// Khoi tao Array chứa các row
		ArrayList<LinkedHashMap<String, String>> List_Data = new ArrayList<LinkedHashMap<String, String>>();
		try {
			// Đổ dữ liệu cho Array
			DocumentBuilderFactory dbf = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(file_price);
	
			NodeList nodeList = doc.getElementsByTagName("DATA_RECORD");
	
			for (int i = 0; i < nodeList.getLength(); i++) {
	
				Node node = nodeList.item(i);
	
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elm = (Element) node;
	
					NodeList[] col_list = new NodeList[Billing.col_Gia_Arr.length];
					Element[] col_elem = new Element[Billing.col_Gia_Arr.length];
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	
					for (int j = 0; j < Billing.col_Gia_Arr.length; j++) {
						col_list[j] = elm.getElementsByTagName(Billing.col_Gia_Arr[j]);
						if (col_list[j].item(0) == null) {
							map.put(Billing.col_Gia_Arr[j], null);
							continue;
						}
						col_elem[j] = (Element) col_list[j].item(0);
	
						map.put(Billing.col_Gia_Arr[j], col_elem[j].getTextContent());
					}
					List_Data.add(map);
				}
			}
			return List_Data;
		} catch (Exception e) {
			return null;
		}
				
	}


	
}
