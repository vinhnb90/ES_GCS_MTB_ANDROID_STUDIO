package esolutions.com.gcs_svn_old.esgcs.printer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import vn.edu.hcmut.cce.BabyPrinter;
import vn.edu.hcmut.cce.BluetoothPrinterService;
import android.annotation.SuppressLint;
import android.content.Context;

import esolutions.com.gcs_svn_old.com.camera.utility.Billing;
import esolutions.com.gcs_svn_old.com.camera.utility.Common;
import esolutions.com.gcs_svn_old.com.camera.utility.row_gcs;

@SuppressLint("DefaultLocale")
public class InThongBao {
	int SOKYTU = 32;
	Context ct;
	Billing bll;
	Common comm;

	public InThongBao() {
		bll = new Billing();
		comm = new Common();
	}

	/**
	 * In thông báo
	 * 
	 * @param row
	 * @param ct
	 */
	public void PrintMsg(row_gcs row, Context ct) {
		this.ct = ct;
		ArrayList<String> lstStr = CreateStringTamTinhTien_Khong_Dau(row);
		Print(lstStr);
	}
	
	public void PrintMsgBaBy(row_gcs row, Context ct, BluetoothPrinterService bs) {
		this.ct = ct;
		// ArrayList<String> lstStr = CreateStringTamTinhTien(row);
//		ArrayList<String> lstStr = CreateStringTamTinhTien_SonLa(row);
//		ArrayList<String> lstStr = CreateStringTamTinhTien_BacKan(row);

		ArrayList<String> lstStr = CreateStringTamTinhTien(row);
		Print(lstStr, bs);
	}

	private String FormatNumber(int number) {
		String strFormat = "";
		String txtNumber = number + "";
		for (int i = txtNumber.length(); i >= 0; i -= 3) {
			if (i - 3 <= 0) {
				strFormat = txtNumber.substring(0, i) + " " + strFormat;
				break;
			}
			strFormat = txtNumber.substring(i - 3, i) + " " + strFormat;
		}
		return strFormat.substring(0, strFormat.length() - 1);
	}

	private String Chu(String gNumber) {
		if (gNumber == null || gNumber.trim().length() == 0)
			return "";

		String result = "";
		int iNumber = Integer.parseInt(gNumber);

		switch (iNumber) {
		case 0:
			result = "không";
			break;
		case 1:
			result = "một";
			break;
		case 2:
			result = "hai";
			break;
		case 3:
			result = "ba";
			break;
		case 4:
			result = "bốn";
			break;
		case 5:
			result = "năm";
			break;
		case 6:
			result = "sáu";
			break;
		case 7:
			result = "bảy";
			break;
		case 8:
			result = "tám";
			break;
		case 9:
			result = "chín";
			break;
		default:
			result = "";
			break;
		}
		return result;
	}

	private String Donvi(String so) {
		if (so == null || so.trim().isEmpty())
			return "";

		String Kdonvi = "";

		if (so.equals("1"))
			Kdonvi = "";
		if (so.equals("2"))
			Kdonvi = "nghìn";
		if (so.equals("3"))
			Kdonvi = "triệu";
		if (so.equals("4"))
			Kdonvi = "tỷ";
		if (so.equals("5"))
			Kdonvi = "nghìn tỷ";
		if (so.equals("6"))
			Kdonvi = "triệu tỷ";
		if (so.equals("7"))
			Kdonvi = "tỷ tỷ";

		return Kdonvi;
	}

	private String Tach(String tach3) {
		String Ktach = "";
		if (tach3.equals("000"))
			return "";
		if (tach3.length() == 3) {
			String tr = tach3.trim().substring(0, 1).toString().trim();
			String ch = tach3.trim().substring(1, 2).toString().trim();
			String dv = tach3.trim().substring(2, 3).toString().trim();
			if (tr.equals("0") && ch.equals("0"))
				Ktach = " không trăm lẻ " + Chu(dv.toString().trim()) + " ";
			if (!tr.equals("0") && ch.equals("0") && dv.equals("0"))
				Ktach = Chu(tr.toString().trim()).trim() + " trăm ";
			if (!tr.equals("0") && ch.equals("0") && !dv.equals("0"))
				Ktach = Chu(tr.toString().trim()).trim() + " trăm lẻ "
						+ Chu(dv.trim()).trim() + " ";
			if (tr.equals("0") && Integer.parseInt(ch) > 1
					&& Integer.parseInt(dv) > 0 && !dv.equals("5"))
				Ktach = " không trăm " + Chu(ch.trim()).trim() + " mươi "
						+ Chu(dv.trim()).trim() + " ";
			if (tr.equals("0") && Integer.parseInt(ch) > 1 && dv.equals("0"))
				Ktach = " không trăm " + Chu(ch.trim()).trim() + " mươi ";
			if (tr.equals("0") && Integer.parseInt(ch) > 1 && dv.equals("5"))
				Ktach = " không trăm " + Chu(ch.trim()).trim() + " mươi lăm ";
			if (tr.equals("0") && ch.equals("1") && Integer.parseInt(dv) > 0
					&& !dv.equals("5"))
				Ktach = " không trăm mười " + Chu(dv.trim()).trim() + " ";
			if (tr.equals("0") && ch.equals("1") && dv.equals("0"))
				Ktach = " không trăm mười ";
			if (tr.equals("0") && ch.equals("1") && dv.equals("5"))
				Ktach = " không trăm mười lăm ";
			if (Integer.parseInt(tr) > 0 && Integer.parseInt(ch) > 1
					&& Integer.parseInt(dv) > 0 && !dv.equals("5"))
				Ktach = Chu(tr.trim()).trim() + " trăm "
						+ Chu(ch.trim()).trim() + " mươi "
						+ Chu(dv.trim()).trim() + " ";
			if (Integer.parseInt(tr) > 0 && Integer.parseInt(ch) > 1
					&& dv.equals("0"))
				Ktach = Chu(tr.trim()).trim() + " trăm "
						+ Chu(ch.trim()).trim() + " mươi ";
			if (Integer.parseInt(tr) > 0 && Integer.parseInt(ch) > 1
					&& dv.equals("5"))
				Ktach = Chu(tr.trim()).trim() + " trăm "
						+ Chu(ch.trim()).trim() + " mươi lăm ";
			if (Integer.parseInt(tr) > 0 && ch.equals("1")
					&& Integer.parseInt(dv) > 0 && !dv.equals("5"))
				Ktach = Chu(tr.trim()).trim() + " trăm mười "
						+ Chu(dv.trim()).trim() + " ";

			if (Integer.parseInt(tr) > 0 && ch.equals("1") && dv.equals("0"))
				Ktach = Chu(tr.trim()).trim() + " trăm mười ";
			if (Integer.parseInt(tr) > 0 && ch.equals("1") && dv.equals("5"))
				Ktach = Chu(tr.trim()).trim() + " trăm mười lăm ";

		}

		return Ktach;

	}

	@SuppressLint("DefaultLocale")
	public String So_chu(int gNum) {
		if (gNum == 0)
			return "Không đồng";

		String lso_chu = "";
		String tach_mod = "";
		String tach_conlai = "";
		int Num = Math.round(gNum);
		String gN = String.valueOf(Num);
		int m = Integer.parseInt(String.valueOf(gN.length() / 3));
		int mod = gN.length() - m * 3;
		String dau = "[+]";

		// Dau [+ , - ]
		if (gNum < 0)
			dau = "[-]";
		dau = "";

		// Tach hang lon nhat
		if (mod == 1)
			tach_mod = "00" + String.valueOf(Num).trim().substring(0, 1);
		if (mod == 2)
			tach_mod = "0" + String.valueOf(Num).trim().substring(0, 2);
		if (mod == 0)
			tach_mod = "000";
		// Tach hang con lai sau mod :
		if (String.valueOf(Num).length() > 2)
			tach_conlai = String.valueOf(Num).trim()
					.substring(mod, String.valueOf(Num).length()).trim();

		// /don vi hang mod
		int im = m + 1;
		if (mod > 0)
			lso_chu = Tach(tach_mod).toString().trim() + " "
					+ Donvi(String.valueOf(im).trim());
		// / Tach 3 trong tach_conlai

		int i = m;
		int _m = m;
		int j = 1;
		String tach3 = "";
		String tach3_ = "";

		while (i > 0) {
			tach3 = tach_conlai.trim().substring(0, 3).trim();
			tach3_ = tach3;
			lso_chu = lso_chu.trim() + " " + Tach(tach3.trim()).trim();
			m = _m + 1 - j;
			if (!tach3_.equals("000"))
				lso_chu = lso_chu.trim() + " "
						+ Donvi(String.valueOf(m).trim()).trim();
			tach_conlai = tach_conlai.trim().substring(3,
					tach_conlai.trim().length());

			i = i - 1;
			j = j + 1;
		}
		if (lso_chu.trim().substring(0, 1).equals("k"))
			lso_chu = lso_chu.trim().substring(10, lso_chu.trim().length())
					.trim();
		if (lso_chu.trim().substring(0, 1).equals("l"))
			lso_chu = lso_chu.trim().substring(2, lso_chu.trim().length())
					.trim();
		if (lso_chu.trim().length() > 0)
			lso_chu = dau.trim()
					+ " "
					+ lso_chu.trim().substring(0, 1).trim().toUpperCase()
					+ lso_chu.trim().substring(1, lso_chu.trim().length())
							.trim() + " đồng chẵn.";

		return lso_chu.toString().trim();

	}

	@SuppressWarnings("unused")
	public String[] TachChu(String strBangChu, int intSoKiTu) {
		String[] strBangChuLine = new String[8];
		int Int_Cot = 0;
		int Int_Dong = 1;
		String strBangchu_1 = strBangChu;
		int k = 0;
		int i = 0;
		int j = 0;
		// int_dong la số dòng của chuỗi tiền
		// Int_cot là số cột hoá đơn của phần in chữ (toi da bao nhieu ki tu)
		Int_Cot = intSoKiTu;

		j = 1;
		if (strBangChu.length() < Int_Cot) {
			strBangChuLine[j] = strBangchu_1;
			return strBangChuLine;

		}
		while (k <= strBangChu.length()) {
			for (i = Int_Cot; i >= 1; i += -1) {
				if (strBangchu_1.substring(i, i + 1) == " ") {
					break; // TODO: might not be correct. Was : Exit For
				}
			}
			strBangChuLine[j] = strBangchu_1.substring(0, i);
			strBangchu_1 = strBangchu_1.substring(i + 1);
			j += 1;
			k += i + 1;
			if (strBangchu_1.length() < Int_Cot) {
				strBangChuLine[j] = strBangchu_1;
				break; // TODO: might not be correct. Was : Exit Do
			}
		}
		return strBangChuLine;
	}

	@SuppressWarnings("unused")
	public ArrayList<String> TachChuList(String strBangChu, int intSoKiTu) {
		ArrayList<String> strBangChuLine = new ArrayList<String>();
		int Int_Cot = 0;
		int Int_Dong = 1;
		String strBangchu_1 = strBangChu;
		int k = 0;
		int i = 0;
		int j = 0;
		// int_dong la số dòng của chuỗi tiền
		// Int_cot là số cột hoá đơn của phần in chữ (toi da bao nhieu ki tu)
		Int_Cot = intSoKiTu;

		j = 1;
		if (strBangChu.length() < Int_Cot) {
			strBangChuLine.add(strBangchu_1);
			return strBangChuLine;

		}

		while (k <= strBangChu.length()) {

			for (i = Int_Cot - 1; i >= 1; i += -1) {
				if (strBangchu_1.substring(i, i + 1).equals(" ")) {
					break; // TODO: might not be correct. Was : Exit For
				}
			}

			strBangChuLine.add(strBangchu_1.substring(0, i + 1));
			strBangchu_1 = strBangchu_1.substring(i + 1);
			j += 1;
			k += i + 1;
			if (strBangchu_1.length() < Int_Cot) {
				strBangChuLine.add(strBangchu_1);
				break; // TODO: might not be correct. Was : Exit Do
			}

		}

		return strBangChuLine;
	}

	private String TextAlignCenter(String txt) {
		int num_space = (SOKYTU - txt.length() > 0 ? SOKYTU - txt.length() : 0);
		num_space = num_space / 2 + num_space % 2;
		String str_space = "";
		for (int i = 0; i < num_space; i++) {
			str_space += " ";
		}
		return str_space + txt;
	}

	public ArrayList<String> CreateStringTamTinhTien(row_gcs row) {

		Calendar calendar = Calendar.getInstance();

		ArrayList<String> lstStr = new ArrayList<String>();

		int tong_tien = 0;
		int tong_dtt = 0;
		int tien_sau_thue = 0;

		Calendar calendar_DK = comm.ConvertStringToCalendar(row.getNGAY_CU(), "MM/dd/yyyy");
		Calendar calendar_CK = comm.ConvertStringToCalendar(row.getNGAY_MOI(), "MM/dd/yyyy");// yyyy-MM-dd
		LinkedHashMap<String, String> lhm_row = row.ConvertToLinkedHashMap();
		ArrayList<LinkedHashMap<String, String>> list_hoa_don = new ArrayList<LinkedHashMap<String,String>>();
		list_hoa_don = (new Billing()).DoBilling(lhm_row, ct);

		lstStr.add(TextAlignCenter("Công ty Điện lực Lai Châu"));// Công ty Điện lực
		lstStr.add(TextAlignCenter(Common.arr_TenDvi_LaiChau[Common.posDonVi(row.getMA_DVIQLY())]));
		lstStr.add(TextAlignCenter("THÔNG BÁO TIỀN ĐIỆN"));
		lstStr.add("Tên KH : " + row.getTEN_KHANG());
		lstStr.add("Địa chỉ: " + row.getDIA_CHI());
		lstStr.add("Mã KH: " + row.getMA_KHANG());
		lstStr.add("Số công tơ: " + row.getSERY_CTO() + "  Số hộ: "
				+ row.getSO_HO());
		lstStr.add("Mã cột: " + row.getMA_COT());
		lstStr.add("Mã trạm: " + row.getMA_TRAM());
		lstStr.add("Mã sổ: " + row.getMA_QUYEN());
		lstStr.add("Thông báo tháng " + (calendar_CK.get(Calendar.MONTH) + 1) + "/" + calendar_CK.get(Calendar.YEAR)
				+ "(từ " + calendar_DK.get(Calendar.DAY_OF_MONTH) + "/"
				+ (calendar_DK.get(Calendar.MONTH) + 1) + "/"
				+ calendar_DK.get(Calendar.YEAR) + " đến "
				+ calendar_CK.get(Calendar.DAY_OF_MONTH) + "/"
				+ (calendar_CK.get(Calendar.MONTH) + 1) + "/"
				+ calendar_CK.get(Calendar.YEAR) + ")");
		lstStr.add("ĐNTT: " + Integer.parseInt(row.getSL_MOI())
				+ Integer.parseInt(row.getSL_THAO()));
		lstStr.add("CSĐK: " + Integer.parseInt(row.getCS_CU()) + " CSCK: "
				+ Integer.parseInt(row.getCS_MOI()));

		// common.msbox("Debug", "456 - "+lhm_row.get("SERY_CTO"), ct);

		lstStr.add("-----------------------------");
		lstStr.add("|ĐNTT | Đơn giá| Thành tiền  |");
		lstStr.add("|----------------------------|");

		// String[] ctiet_dntt = {"1234","4565","7896"};
		// String[] ctiet_gia = {"4567","5678","6783"};
		// String[] ctiet_tien = {"777","888","9999"};
		if(list_hoa_don != null) {
			for (int i = 0; i < list_hoa_don.size(); i++) {
				// lstStr.add(String.format("|%5s|%8s|%13s|",
				// FormatNumber(Integer.parseInt(ctiet_dntt[i])),
				// FormatNumber(Integer.parseInt(ctiet_gia[i])),
				// FormatNumber(Integer.parseInt(ctiet_dntt[i]) *
				// Integer.parseInt(ctiet_gia[i])))); // tạm để ctiet_tien =
				// ctiet_dntt*ctiet_gia
	
				LinkedHashMap<String, String> row_hoa_don = list_hoa_don.get(i);
				int dtt = (row_hoa_don.get("SAN_LUONG") != null
						&& row_hoa_don.get("SAN_LUONG").length() > 0 ? Integer
						.parseInt(row_hoa_don.get("SAN_LUONG")) : 0);
				int don_gia = (row_hoa_don.get("DON_GIA") != null
						&& row_hoa_don.get("DON_GIA").length() > 0 ? Integer
						.parseInt(row_hoa_don.get("DON_GIA")) : 0);
				int so_tien = (row_hoa_don.get("SO_TIEN") != null
						&& row_hoa_don.get("SO_TIEN").length() > 0 ? Integer
						.parseInt(row_hoa_don.get("SO_TIEN")) : 0);
	
				lstStr.add(String.format("|%5s|%8s|%13s|", FormatNumber(dtt),
						FormatNumber(don_gia), FormatNumber(so_tien)));
	
				tong_tien += so_tien;
				tong_dtt += dtt;
			}
		}

		lstStr.add("|----------------------------|");
		lstStr.add(String.format("| %-13s|%13s|", FormatNumber(tong_dtt)
				+ " kWh", FormatNumber(tong_tien) + ""));
		lstStr.add("|----------------------------|");
		lstStr.add(String.format("| %-13s|%13s|", "Thuế GTGT",
				FormatNumber(tong_tien / 10) + ""));
		lstStr.add("|----------------------------|");

		tien_sau_thue = (tong_tien + tong_tien / 10);
		lstStr.add("Tổng tiền: " + FormatNumber(tien_sau_thue) + " VNĐ");
		lstStr.add("Bằng chữ: " + So_chu(tien_sau_thue));
		lstStr.add("Lai Châu, ngày " + calendar.get(Calendar.DAY_OF_MONTH)
				+ " tháng " + (calendar.get(Calendar.MONTH) + 1) + " năm "
				+ calendar.get(Calendar.YEAR));
		lstStr.add("------------------------------");
		lstStr.add("\n\n");

		return lstStr;
	}

	@SuppressLint("SimpleDateFormat")
	public ArrayList<String> CreateStringTamTinhTien_SonLa(row_gcs row) {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 3);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		ArrayList<String> lstStr = new ArrayList<String>();

		int tong_tien = 0; // chèn tạm số tiền , khi nào tính được tiền thì sửa
							// lại
		int tong_dtt = 0;
		int tien_sau_thue = 0;
		String ngay_cu = row.getNGAY_CU();
		String ngay_moi = row.getNGAY_MOI();
		Calendar calendar_DK = comm.ConvertStringToCalendar(ngay_cu,
				"MM/dd/yyyy HH:mm:ss a");
		Calendar calendar_CK = comm.ConvertStringToCalendar(ngay_moi,
				"MM/dd/yyyy HH:mm:ss a");// yyyy-MM-dd
		String ngay_dky = calendar_DK.get(Calendar.DAY_OF_MONTH) + "/" + (calendar_DK.get(Calendar.MONTH) + 1) + "/" + calendar_DK.get(Calendar.YEAR);
		String ngay_cky = calendar_CK.get(Calendar.DAY_OF_MONTH) + "/" + (calendar_CK.get(Calendar.MONTH) + 1) + "/" + calendar_CK.get(Calendar.YEAR);
//		String ngay_dky = sdf.format(calendar_DK.getTime());
//		String ngay_cky = sdf.format(calendar_CK.getTime());
		LinkedHashMap<String, String> lhm_row = row.ConvertToLinkedHashMap();
		ArrayList<LinkedHashMap<String, String>> list_hoa_don = (new Billing())
				.DoBilling(lhm_row, ct);

		lstStr.add(TextAlignCenter("Công ty Điện Lực Lai Châu"));//Công ty Điện lực
//		lstStr.add(TextAlignCenter("Công ty Điện lực Bắc Kạn"));// Công ty Điện lực
		lstStr.add(TextAlignCenter(Common.cfgInfo.getDVIQLY().toString().trim()));
		lstStr.add(TextAlignCenter("ĐT:0229799004"));
		lstStr.add(TextAlignCenter("THÔNG BÁO CHỈ SỐ"));
		lstStr.add(TextAlignCenter("VÀ TẠM TÍNH TIỀN ĐIỆN"));
		lstStr.add(TextAlignCenter("(từ " + ngay_dky + " đến " + ngay_cky + ")"));
		lstStr.add("Tên KH : " + row.getTEN_KHANG());
		lstStr.add("Địa chỉ: " + row.getDIA_CHI());
		lstStr.add("Mã KH: " + row.getMA_KHANG());
		lstStr.add("Số công tơ: " + row.getSERY_CTO() + "  Số hộ: "
				+ row.getSO_HO());
		lstStr.add("Thông báo tháng " + row.getTHANG() + "/" + row.getNAM());
		lstStr.add("CSĐK: " + Integer.parseInt(row.getCS_CU()) + " CSCK: "
				+ Integer.parseInt(row.getCS_MOI()));

		lstStr.add("------------------------------");
		lstStr.add("|ĐNTT | Đơn giá| Thành tiền  |");
		lstStr.add("|----------------------------|");

		for (int i = 0; i < list_hoa_don.size(); i++) {

			LinkedHashMap<String, String> row_hoa_don = list_hoa_don.get(i);
			int dtt = (row_hoa_don.get("SAN_LUONG") != null
					&& row_hoa_don.get("SAN_LUONG").length() > 0 ? Integer
					.parseInt(row_hoa_don.get("SAN_LUONG")) : 0);
			int don_gia = (row_hoa_don.get("DON_GIA") != null
					&& row_hoa_don.get("DON_GIA").length() > 0 ? Integer
					.parseInt(row_hoa_don.get("DON_GIA")) : 0);
			int so_tien = (row_hoa_don.get("SO_TIEN") != null
					&& row_hoa_don.get("SO_TIEN").length() > 0 ? Integer
					.parseInt(row_hoa_don.get("SO_TIEN")) : 0);

			lstStr.add(String.format("|%5s|%8s|%13s|", FormatNumber(dtt),
					FormatNumber(don_gia), FormatNumber(so_tien)));

			tong_tien += so_tien;
			tong_dtt += dtt;
		}

		lstStr.add("|----------------------------|");
		lstStr.add(String.format("| %-13s|%13s|", FormatNumber(tong_dtt)
				+ " kWh", FormatNumber(tong_tien) + ""));
		lstStr.add("|----------------------------|");
		lstStr.add(String.format("| %-13s|%13s|", "Thuế GTGT",
				FormatNumber(tong_tien / 10) + ""));
		lstStr.add("|----------------------------|");
		tien_sau_thue = (tong_tien + tong_tien / 10);
		lstStr.add("Tổng cộng: " + FormatNumber(tien_sau_thue) + " VNĐ");
		lstStr.add("Bằng chữ: " + So_chu(tien_sau_thue));
		lstStr.add("Sơn La,ngày " + calendar.get(Calendar.DAY_OF_MONTH)
				+ " tháng " + (calendar.get(Calendar.MONTH) + 1) + " năm "
				+ calendar.get(Calendar.YEAR));
		lstStr.add("Ngày nộp: " + sdf.format(calendar.getTime()));
		lstStr.add("NV GCS: ...");
		lstStr.add("------------------------------");
		lstStr.add(TextAlignCenter("Thông báo này không có giá trị"));
		lstStr.add(TextAlignCenter("thanh toán"));
		lstStr.add("\n\n");

		return lstStr;
	}
	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public ArrayList<String> CreateStringTamTinhTien_Khong_Dau(row_gcs row) {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 3);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String formattedDate = getDateTime();
		
		ArrayList<String> lstStr = new ArrayList<String>();

		int tong_tien = 0;
		int tong_dtt = 0;
		int tien_sau_thue = 0;
		String ngay_cu = row.getNGAY_CU();
		String ngay_moi = row.getNGAY_MOI();
		Calendar calendar_DK = comm.ConvertStringToCalendar(ngay_cu,
				"MM/dd/yyyy");
		Calendar calendar_CK = comm.ConvertStringToCalendar(ngay_moi,
				"MM/dd/yyyy");// yyyy-MM-dd
		String ngay_dky = sdf.format(calendar_DK.getTime());
		String ngay_cky = sdf.format(calendar_CK.getTime());
		LinkedHashMap<String, String> lhm_row = row.ConvertToLinkedHashMap();
		ArrayList<LinkedHashMap<String, String>> list_hoa_don = (new Billing())
				.DoBilling(lhm_row, ct);
		lstStr.add(TextAlignCenter(Common.cfgInfo.getDVIQLY().toString()));
		lstStr.add(TextAlignCenter("DT:0229799004"));
		lstStr.add(TextAlignCenter("THONG BAO CHI SO"));
		lstStr.add(TextAlignCenter("VA TAM TINH TIEN ÐIEN"));
		lstStr.add(TextAlignCenter("(tu " + ngay_dky + " den " + ngay_cky + ")"));
		lstStr.add("Ten KH : " + comm.VietnameseTrim(row.getTEN_KHANG()));
		lstStr.add("Dia chi: " + comm.VietnameseTrim(row.getDIA_CHI()));
		lstStr.add("Ma KH: " + row.getMA_KHANG());
		lstStr.add("So cong to: " + row.getSERY_CTO() + "  So ho: "
				+ row.getSO_HO());
		lstStr.add("Thong bao thang " + row.getTHANG() + "/" + row.getNAM());
		lstStr.add("CSDK: " + Integer.parseInt(row.getCS_CU()) + " CSCK: "
				+ Integer.parseInt(row.getCS_MOI()));

		lstStr.add("------------------------------");
		lstStr.add("|DNTT | Don gia| Thanh tien  |");
		lstStr.add("|----------------------------|");

		for (int i = 0; i < list_hoa_don.size(); i++) {

			LinkedHashMap<String, String> row_hoa_don = list_hoa_don.get(i);
			int dtt = (row_hoa_don.get("SAN_LUONG") != null
					&& row_hoa_don.get("SAN_LUONG").length() > 0 ? Integer
					.parseInt(row_hoa_don.get("SAN_LUONG")) : 0);
			int don_gia = (row_hoa_don.get("DON_GIA") != null
					&& row_hoa_don.get("DON_GIA").length() > 0 ? Integer
					.parseInt(row_hoa_don.get("DON_GIA")) : 0);
			int so_tien = (row_hoa_don.get("SO_TIEN") != null
					&& row_hoa_don.get("SO_TIEN").length() > 0 ? Integer
					.parseInt(row_hoa_don.get("SO_TIEN")) : 0);

			lstStr.add(String.format("|%5s|%8s|%13s|", FormatNumber(dtt),
					FormatNumber(don_gia), FormatNumber(so_tien)));

			tong_tien += so_tien;
			tong_dtt += dtt;
		}

		lstStr.add("|----------------------------|");
		lstStr.add(String.format("| %-13s|%13s|", FormatNumber(tong_dtt)
				+ " kWh", FormatNumber(tong_tien) + ""));
		lstStr.add("|----------------------------|");
		lstStr.add(String.format("| %-13s|%13s|", "Thue GTGT",
				FormatNumber(tong_tien / 10) + ""));
		lstStr.add("|----------------------------|");
		tien_sau_thue = (tong_tien + tong_tien / 10);
		lstStr.add("Tong cong: " + FormatNumber(tien_sau_thue) + " VND");
		lstStr.add("Bang Chu: " + comm.VietnameseTrim(So_chu(tien_sau_thue)));
		lstStr.add("Ngay " + formattedDate.split("/")[0].toString()
				+ " thang " + formattedDate.split("/")[1].toString() + " nam "
				+ formattedDate.split("/")[2].toString());
		lstStr.add("Ngay nop: " + formattedDate);
		lstStr.add("NV GCS: ...");
		lstStr.add("------------------------------");
		lstStr.add(TextAlignCenter("Thong bao nay khong co gia tri"));
		lstStr.add(TextAlignCenter("thanh toan"));
		lstStr.add("\n\n");

		return lstStr;
	}
	
	public ArrayList<String> CreateStringTamTinhTien_BacKan(row_gcs row) {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 3);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String formattedDate = getDateTime();
		
		ArrayList<String> lstStr = new ArrayList<String>();

		int tong_tien = 0; // chèn tạm số tiền , khi nào tính được tiền thì sửa
							// lại
		int tong_dtt = 0;
		int tien_sau_thue = 0;
		String ngay_cu = row.getNGAY_CU();
		String ngay_moi = row.getNGAY_MOI();
		Calendar calendar_DK = comm.ConvertStringToCalendar(ngay_cu,
				"MM/dd/yyyy");
		Calendar calendar_CK = comm.ConvertStringToCalendar(ngay_moi,
				"MM/dd/yyyy");// yyyy-MM-dd
		String ngay_dky = sdf.format(calendar_DK.getTime());
		String ngay_cky = sdf.format(calendar_CK.getTime());
		LinkedHashMap<String, String> lhm_row = row.ConvertToLinkedHashMap();
		ArrayList<LinkedHashMap<String, String>> list_hoa_don = (new Billing())
				.DoBilling(lhm_row, ct);

		lstStr.add(TextAlignCenter("Công ty Điện lực Bắc Kạn"));// Công ty Điện
																// lực
		lstStr.add(TextAlignCenter(Common.cfgInfo.getDVIQLY().toString().trim()));
		lstStr.add(TextAlignCenter("ĐT:0229799004"));
		lstStr.add(TextAlignCenter("THÔNG BÁO CHỈ SỐ"));
		lstStr.add(TextAlignCenter("VÀ TẠM TÍNH TIỀN ĐIỆN"));
		lstStr.add(TextAlignCenter("(từ " + ngay_dky + " đến " + ngay_cky + ")"));
		lstStr.add("Tên KH : " + row.getTEN_KHANG());
		lstStr.add("Địa chỉ: " + row.getDIA_CHI());
		lstStr.add("Mã KH: " + row.getMA_KHANG());
		lstStr.add("Số công tơ: " + row.getSERY_CTO() + "  Số hộ: "
				+ row.getSO_HO());
		lstStr.add("Thông báo tháng " + row.getTHANG() + "/" + row.getNAM());
		lstStr.add("CSĐK: " + Integer.parseInt(row.getCS_CU()) + " CSCK: "
				+ Integer.parseInt(row.getCS_MOI()));

		lstStr.add("------------------------------");
		lstStr.add("|ĐNTT | Đơn giá| Thành tiền  |");
		lstStr.add("|----------------------------|");

		for (int i = 0; i < list_hoa_don.size(); i++) {

			LinkedHashMap<String, String> row_hoa_don = list_hoa_don.get(i);
			int dtt = (row_hoa_don.get("SAN_LUONG") != null
					&& row_hoa_don.get("SAN_LUONG").length() > 0 ? Integer
					.parseInt(row_hoa_don.get("SAN_LUONG")) : 0);
			int don_gia = (row_hoa_don.get("DON_GIA") != null
					&& row_hoa_don.get("DON_GIA").length() > 0 ? Integer
					.parseInt(row_hoa_don.get("DON_GIA")) : 0);
			int so_tien = (row_hoa_don.get("SO_TIEN") != null
					&& row_hoa_don.get("SO_TIEN").length() > 0 ? Integer
					.parseInt(row_hoa_don.get("SO_TIEN")) : 0);

			lstStr.add(String.format("|%5s|%8s|%13s|", FormatNumber(dtt),
					FormatNumber(don_gia), FormatNumber(so_tien)));

			tong_tien += so_tien;
			tong_dtt += dtt;
		}

		lstStr.add("|----------------------------|");
		lstStr.add(String.format("| %-13s|%13s|", FormatNumber(tong_dtt)
				+ " kWh", FormatNumber(tong_tien) + ""));
		lstStr.add("|----------------------------|");
		lstStr.add(String.format("| %-13s|%13s|", "Thuế GTGT",
				FormatNumber(tong_tien / 10) + ""));
		lstStr.add("|----------------------------|");
		tien_sau_thue = (tong_tien + tong_tien / 10);
		lstStr.add("Tổng cộng: " + FormatNumber(tien_sau_thue) + " VNĐ");
		lstStr.add("Bằng chữ: " + So_chu(tien_sau_thue));
		lstStr.add("Bắc Kạn,ngày " + formattedDate.split("/")[0].toString()
				+ " tháng " + formattedDate.split("/")[1].toString() + " năm "
				+ formattedDate.split("/")[2].toString());
		lstStr.add("Ngày nộp: " + formattedDate);
		lstStr.add("NV GCS: ...");
		lstStr.add("------------------------------");
		lstStr.add(TextAlignCenter("Thông báo này không có giá trị"));
		lstStr.add(TextAlignCenter("thanh toán"));
		lstStr.add("\n\n");

		return lstStr;
	}

	public void Print(ArrayList<String> lstTextInput, BluetoothPrinterService bs) {
		ArrayList<String> listText = new ArrayList<String>();
		for (String text : lstTextInput) {
			ArrayList<String> listTemp = TachChuList(text, SOKYTU);
			for (String listItem : listTemp) {
				listText.add(listItem);
			}
		}

		int dem = 0;
		for (String listTextItem : listText) {
			dem++;
			if (listTextItem != null && !listTextItem.trim().isEmpty())
				BabyPrinter.printVNText(bs, listTextItem + "\n", dem==1||dem==2?"b":"");
		}
		BabyPrinter.lineFeeds(bs);

//		Common.printer.Print(strPrint);
	}
	public void Print(ArrayList<String> lstTextInput) {
		ArrayList<String> listText = new ArrayList<String>();
		for (String text : lstTextInput) {
			ArrayList<String> listTemp = TachChuList(text, SOKYTU);
			for (String listItem : listTemp) {
				listText.add(listItem);
			}
		}

		String strPrint = "";
		for (String listTextItem : listText) {
			if (listTextItem != null && !listTextItem.trim().isEmpty())
				strPrint += String.format("%-" + SOKYTU + "s", listTextItem);
		}

		Common.printer.Print(strPrint);
	}

}
