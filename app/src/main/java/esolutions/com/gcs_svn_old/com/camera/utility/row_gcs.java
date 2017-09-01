package esolutions.com.gcs_svn_old.com.camera.utility;

import java.util.LinkedHashMap;

import android.os.Parcel;
import android.os.Parcelable;

public class row_gcs implements Parcelable {

	private String MA_NVGCS;
	private String MA_KHANG;
	private String MA_DDO;
	private String MA_DVIQLY;
	private String MA_GC;
	private String MA_QUYEN;
	private String MA_TRAM;
	private String BOCSO_ID;
	private String LOAI_BCS;
	private String LOAI_CS;
	private String TEN_KHANG;
	private String DIA_CHI;
	private String MA_NN;
	private String SO_HO;
	private String MA_CTO;
	private String SERY_CTO;
	private String HSN;
	private String CS_CU;
	private String TTR_CU;
	private String SL_CU;
	private String SL_TTIEP;
	private String NGAY_CU;
	private String CS_MOI;
	private String TTR_MOI;
	private String SL_MOI;
	private String CHUOI_GIA;
	private String KY;
	private String THANG;
	private String NAM;
	private String NGAY_MOI;
	private String NGUOI_GCS;
	private String SL_THAO;
	private String KIMUA_CSPK;
	private String MA_COT;
	private String CGPVTHD;
	private String HTHUC_TBAO_DK;
	private String DTHOAI_SMS;
	private String EMAIL;
	private String THOI_GIAN;
	private double X;
	private double Y;
	private String SO_TIEN;
	private String HTHUC_TBAO_TH;
	private String TTHAI_DBO;
	private String DU_PHONG;
	private String GHICHU;
	private String TT_KHAC;
	private String ID_SQLITE;
	private String SLUONG_1;
	private String SLUONG_2;
	private String SLUONG_3;
	private String SO_HOM;
	private String PMAX;
	private String NGAY_PMAX;

	public row_gcs(String mA_NVGCS, String mA_KHANG, String mA_DDO,
			String mA_DVIQLY, String mA_GC, String mA_QUYEN, String mA_TRAM,
			String bOCSO_ID, String lOAI_BCS, String lOAI_CS, String tEN_KHANG,
			String dIA_CHI, String mA_NN, String sO_HO, String mA_CTO,
			String sERY_CTO, String hSN, String cS_CU, String tTR_CU,
			String sL_CU, String sL_TTIEP, String nGAY_CU, String cS_MOI,
			String tTR_MOI, String sL_MOI, String cHUOI_GIA, String kY,
			String tHANG, String nAM, String nGAY_MOI, String nGUOI_GCS,
			String sL_THAO, String kIMUA_CSPK, String mA_COT, String cGPVTHD,
			String hTHUC_TBAO_DK, String dTHOAI_SMS, String eMAIL,
			String tHOI_GIAN, double x, double y, String sO_TIEN,
			String hTHUC_TBAO_TH, String tTHAI_DBO, String dU_PHONG, 
			String GHICHU, String TT_KHAC, String ID_SQLITE,
			String SLUONG_1, String SLUONG_2, String SLUONG_3,String SO_HOM, String PMAX, String NGAY_PMAX) {
		super();
		MA_NVGCS = mA_NVGCS;
		MA_KHANG = mA_KHANG;
		MA_DDO = mA_DDO;
		MA_DVIQLY = mA_DVIQLY;
		MA_GC = mA_GC;
		MA_QUYEN = mA_QUYEN;
		MA_TRAM = mA_TRAM;
		BOCSO_ID = bOCSO_ID;
		LOAI_BCS = lOAI_BCS;
		LOAI_CS = lOAI_CS;
		TEN_KHANG = tEN_KHANG;
		DIA_CHI = dIA_CHI;
		MA_NN = mA_NN;
		SO_HO = sO_HO;
		MA_CTO = mA_CTO;
		SERY_CTO = sERY_CTO;
		HSN = hSN;
		CS_CU = cS_CU;
		TTR_CU = tTR_CU;
		SL_CU = sL_CU;
		SL_TTIEP = sL_TTIEP;
		NGAY_CU = nGAY_CU;
		CS_MOI = cS_MOI;
		TTR_MOI = tTR_MOI;
		SL_MOI = sL_MOI;
		CHUOI_GIA = cHUOI_GIA;
		KY = kY;
		THANG = tHANG;
		NAM = nAM;
		NGAY_MOI = nGAY_MOI;
		NGUOI_GCS = nGUOI_GCS;
		SL_THAO = sL_THAO;
		KIMUA_CSPK = kIMUA_CSPK;
		MA_COT = mA_COT;
		CGPVTHD = cGPVTHD;
		HTHUC_TBAO_DK = hTHUC_TBAO_DK;
		DTHOAI_SMS = dTHOAI_SMS;
		EMAIL = eMAIL;
		THOI_GIAN = tHOI_GIAN;
		X = x;
		Y = y;
		SO_TIEN = sO_TIEN;
		HTHUC_TBAO_TH = hTHUC_TBAO_TH;
		TTHAI_DBO = tTHAI_DBO;
		DU_PHONG = dU_PHONG;
		this.GHICHU = GHICHU;
		this.TT_KHAC = TT_KHAC;
		this.ID_SQLITE = ID_SQLITE;
		this.SLUONG_1 = SLUONG_1;
		this.SLUONG_2 = SLUONG_2;
		this.SLUONG_3 = SLUONG_3;
		this.SO_HOM = SO_HOM;
		this.PMAX = PMAX;
		this.NGAY_PMAX = NGAY_PMAX;
	}

	public String getDTHOAI_SMS() {
		return DTHOAI_SMS;
	}

	public void setDTHOAI_SMS(String dTHOAI_SMS) {
		DTHOAI_SMS = dTHOAI_SMS;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getCGPVTHD() {
		return CGPVTHD;
	}

	public void setCGPVTHD(String cGPVTHD) {
		CGPVTHD = cGPVTHD;
	}

	public String getHTHUC_TBAO_DK() {
		return HTHUC_TBAO_DK;
	}

	public void setHTHUC_TBAO_DK(String hTHUC_TBAO_DK) {
		HTHUC_TBAO_DK = hTHUC_TBAO_DK;
	}

	public String getTHOI_GIAN() {
		return THOI_GIAN;
	}

	public void setTHOI_GIAN(String tHOI_GIAN) {
		THOI_GIAN = tHOI_GIAN;
	}

	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}

	public String getSO_TIEN() {
		return SO_TIEN;
	}

	public void setSO_TIEN(String sO_TIEN) {
		SO_TIEN = sO_TIEN;
	}

	public String getHTHUC_TBAO_TH() {
		return HTHUC_TBAO_TH;
	}

	public void setHTHUC_TBAO_TH(String hTHUC_TBAO_TH) {
		HTHUC_TBAO_TH = hTHUC_TBAO_TH;
	}

	public String getTTHAI_DBO() {
		return TTHAI_DBO;
	}

	public void setTTHAI_DBO(String tTHAI_DBO) {
		TTHAI_DBO = tTHAI_DBO;
	}

	public String getDU_PHONG() {
		return DU_PHONG;
	}

	public void setDU_PHONG(String dU_PHONG) {
		DU_PHONG = dU_PHONG;
	}

	public String getMA_COT() {
		return MA_COT;
	}

	public void setMA_COT(String mA_COT) {
		MA_COT = mA_COT;
	}

	public String getMA_NVGCS() {
		return MA_NVGCS;
	}

	public void setMA_NVGCS(String mA_NVGCS) {
		MA_NVGCS = mA_NVGCS;
	}

	public String getMA_KHANG() {
		return MA_KHANG;
	}

	public void setMA_KHANG(String mA_KHANG) {
		MA_KHANG = mA_KHANG;
	}

	public String getMA_DDO() {
		return MA_DDO;
	}

	public void setMA_DDO(String mA_DDO) {
		MA_DDO = mA_DDO;
	}

	public String getMA_DVIQLY() {
		return MA_DVIQLY;
	}

	public void setMA_DVIQLY(String mA_DVIQLY) {
		MA_DVIQLY = mA_DVIQLY;
	}

	public String getMA_GC() {
		return MA_GC;
	}

	public void setMA_GC(String mA_GC) {
		MA_GC = mA_GC;
	}

	public String getMA_QUYEN() {
		return MA_QUYEN;
	}

	public void setMA_QUYEN(String mA_QUYEN) {
		MA_QUYEN = mA_QUYEN;
	}

	public String getMA_TRAM() {
		return MA_TRAM;
	}

	public void setMA_TRAM(String mA_TRAM) {
		MA_TRAM = mA_TRAM;
	}

	public String getBOCSO_ID() {
		return BOCSO_ID;
	}

	public void setBOCSO_ID(String bOCSO_ID) {
		BOCSO_ID = bOCSO_ID;
	}

	public String getLOAI_BCS() {
		return LOAI_BCS;
	}

	public void setLOAI_BCS(String lOAI_BCS) {
		LOAI_BCS = lOAI_BCS;
	}

	public String getLOAI_CS() {
		return LOAI_CS;
	}

	public void setLOAI_CS(String lOAI_CS) {
		LOAI_CS = lOAI_CS;
	}

	public String getTEN_KHANG() {
		return TEN_KHANG;
	}

	public void setTEN_KHANG(String tEN_KHANG) {
		TEN_KHANG = tEN_KHANG;
	}

	public String getDIA_CHI() {
		return DIA_CHI;
	}

	public void setDIA_CHI(String dIA_CHI) {
		DIA_CHI = dIA_CHI;
	}

	public String getMA_NN() {
		return MA_NN;
	}

	public void setMA_NN(String mA_NN) {
		MA_NN = mA_NN;
	}

	public String getSO_HO() {
		return SO_HO;
	}

	public void setSO_HO(String sO_HO) {
		SO_HO = sO_HO;
	}

	public String getMA_CTO() {
		return MA_CTO;
	}

	public void setMA_CTO(String mA_CTO) {
		MA_CTO = mA_CTO;
	}

	public String getSERY_CTO() {
		return SERY_CTO;
	}

	public void setSERY_CTO(String sERY_CTO) {
		SERY_CTO = sERY_CTO;
	}

	public String getHSN() {
		return HSN;
	}

	public void setHSN(String hSN) {
		HSN = hSN;
	}

	public String getCS_CU() {
		return CS_CU;
	}

	public void setCS_CU(String cS_CU) {
		CS_CU = cS_CU;
	}

	public String getTTR_CU() {
		return TTR_CU;
	}

	public void setTTR_CU(String tTR_CU) {
		TTR_CU = tTR_CU;
	}

	public String getSL_CU() {
		return SL_CU;
	}

	public void setSL_CU(String sL_CU) {
		SL_CU = sL_CU;
	}

	public String getSL_TTIEP() {
		return SL_TTIEP;
	}

	public void setSL_TTIEP(String sL_TTIEP) {
		SL_TTIEP = sL_TTIEP;
	}

	public String getNGAY_CU() {
		return NGAY_CU;
	}

	public void setNGAY_CU(String nGAY_CU) {
		NGAY_CU = nGAY_CU;
	}

	public String getCS_MOI() {
		return CS_MOI;
	}

	public void setCS_MOI(String cS_MOI) {
		CS_MOI = cS_MOI;
	}

	public String getTTR_MOI() {
		return TTR_MOI;
	}

	public void setTTR_MOI(String tTR_MOI) {
		TTR_MOI = tTR_MOI;
	}

	public String getSL_MOI() {
		return SL_MOI;
	}

	public void setSL_MOI(String sL_MOI) {
		SL_MOI = sL_MOI;
	}

	public String getCHUOI_GIA() {
		return CHUOI_GIA;
	}

	public void setCHUOI_GIA(String cHUOI_GIA) {
		CHUOI_GIA = cHUOI_GIA;
	}

	public String getKY() {
		return KY;
	}

	public void setKY(String kY) {
		KY = kY;
	}

	public String getTHANG() {
		return THANG;
	}

	public void setTHANG(String tHANG) {
		THANG = tHANG;
	}

	public String getNAM() {
		return NAM;
	}

	public void setNAM(String nAM) {
		NAM = nAM;
	}

	public String getNGAY_MOI() {
		return NGAY_MOI;
	}

	public void setNGAY_MOI(String nGAY_MOI) {
		NGAY_MOI = nGAY_MOI;
	}

	public String getNGUOI_GCS() {
		return NGUOI_GCS;
	}

	public void setNGUOI_GCS(String nGUOI_GCS) {
		NGUOI_GCS = nGUOI_GCS;
	}

	public String getSL_THAO() {
		return SL_THAO;
	}

	public void setSL_THAO(String sL_THAO) {
		SL_THAO = sL_THAO;
	}

	public String getKIMUA_CSPK() {
		return KIMUA_CSPK;
	}

	public void setKIMUA_CSPK(String kIMUA_CSPK) {
		KIMUA_CSPK = kIMUA_CSPK;
	}

	public static Creator<row_gcs> getCreator() {
		return CREATOR;
	}
	
	public String getGHICHU() {
		return GHICHU;
	}

	public void setGHICHU(String gHICHU) {
		GHICHU = gHICHU;
	}

	public String getTT_KHAC() {
		return TT_KHAC;
	}

	public void setTT_KHAC(String tT_KHAC) {
		TT_KHAC = tT_KHAC;
	}

	public String getID_SQLITE() {
		return ID_SQLITE;
	}

	public void setID_SQLITE(String ID_SQLITE) {
		this.ID_SQLITE = ID_SQLITE;
	}

	public String getSLUONG_1() {
		return SLUONG_1;
	}

	public void setSLUONG_1(String sLUONG_1) {
		SLUONG_1 = sLUONG_1;
	}

	public String getSLUONG_2() {
		return SLUONG_2;
	}

	public void setSLUONG_2(String sLUONG_2) {
		SLUONG_2 = sLUONG_2;
	}

	public String getSLUONG_3() {
		return SLUONG_3;
	}

	public void setSLUONG_3(String sLUONG_3) {
		SLUONG_3 = sLUONG_3;
	}

	public String getSO_HOM() {
		return SO_HOM;
	}

	public void setSO_HOM(String sO_HOM) {
		SO_HOM = sO_HOM;
	}

	public String getPMAX() {
		return PMAX;
	}

	public void setPMAX(String pMAX) {
		PMAX = pMAX;
	}

	public String getNGAY_PMAX() {
		return NGAY_PMAX;
	}

	public void setNGAY_PMAX(String nGAY_PMAX) {
		NGAY_PMAX = nGAY_PMAX;
	}

	// write your object's data to the passed-in Parcel
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(MA_NVGCS);
		out.writeString(MA_KHANG);
		out.writeString(MA_DDO);
		out.writeString(MA_DVIQLY);
		out.writeString(MA_GC);
		out.writeString(MA_QUYEN);
		out.writeString(MA_TRAM);
		out.writeString(BOCSO_ID);
		out.writeString(LOAI_BCS);
		out.writeString(LOAI_CS);
		out.writeString(TEN_KHANG);
		out.writeString(DIA_CHI);
		out.writeString(MA_NN);
		out.writeString(SO_HO);
		out.writeString(MA_CTO);
		out.writeString(SERY_CTO);
		out.writeString(HSN);
		out.writeString(CS_CU);
		out.writeString(TTR_CU);
		out.writeString(SL_CU);
		out.writeString(SL_TTIEP);
		out.writeString(NGAY_CU);
		out.writeString(CS_MOI);
		out.writeString(TTR_MOI);
		out.writeString(SL_MOI);
		out.writeString(CHUOI_GIA);
		out.writeString(KY);
		out.writeString(THANG);
		out.writeString(NAM);
		out.writeString(NGAY_MOI);
		out.writeString(NGUOI_GCS);
		out.writeString(SL_THAO);
		out.writeString(KIMUA_CSPK);
		out.writeString(MA_COT);
		out.writeString(GHICHU);
		out.writeString(TT_KHAC);
		out.writeString(ID_SQLITE);
		out.writeString(SLUONG_1);
		out.writeString(SLUONG_3);
		out.writeString(ID_SQLITE);
		out.writeString(SLUONG_1);
		out.writeString(SLUONG_2);
		out.writeString(SLUONG_3);
		out.writeString(SO_HOM);
	}

	public row_gcs() {

	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Creator<row_gcs> CREATOR = new Creator<row_gcs>() {
		@Override
		public row_gcs createFromParcel(Parcel in) {

			return new row_gcs(in);
		}

		@Override
		public row_gcs[] newArray(int size) {
			return new row_gcs[size];
		}
	};

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	private row_gcs(Parcel in) {

		MA_NVGCS = in.readString();
		MA_KHANG = in.readString();
		MA_DDO = in.readString();
		MA_DVIQLY = in.readString();
		MA_GC = in.readString();
		MA_QUYEN = in.readString();
		MA_TRAM = in.readString();
		BOCSO_ID = in.readString();
		LOAI_BCS = in.readString();
		LOAI_CS = in.readString();
		TEN_KHANG = in.readString();
		DIA_CHI = in.readString();
		MA_NN = in.readString();
		SO_HO = in.readString();
		MA_CTO = in.readString();
		SERY_CTO = in.readString();
		HSN = in.readString();
		CS_CU = in.readString();
		TTR_CU = in.readString();
		SL_CU = in.readString();
		SL_TTIEP = in.readString();
		NGAY_CU = in.readString();
		CS_MOI = in.readString();
		TTR_MOI = in.readString();
		SL_MOI = in.readString();
		CHUOI_GIA = in.readString();
		KY = in.readString();
		THANG = in.readString();
		NAM = in.readString();
		NGAY_MOI = in.readString();
		NGUOI_GCS = in.readString();
		SL_THAO = in.readString();
		KIMUA_CSPK = in.readString();
		MA_COT = in.readString();
		GHICHU = in.readString();
		TT_KHAC = in.readString();
		ID_SQLITE = in.readString();
		SLUONG_1 = in.readString();
		SLUONG_2 = in.readString();
		SLUONG_3 = in.readString();
		SO_HOM = in.readString();
	}
	
	public LinkedHashMap<String,String> ConvertToLinkedHashMap(){
		LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
		lhm.put("MA_NVGCS", MA_NVGCS);
		lhm.put("MA_KHANG", MA_KHANG);
		lhm.put("MA_DDO", MA_DDO);
		lhm.put("MA_DVIQLY", MA_DVIQLY);
		lhm.put("MA_GC", MA_GC);
		lhm.put("MA_QUYEN", MA_QUYEN);
		lhm.put("MA_TRAM", MA_TRAM);
		lhm.put("BOCSO_ID", BOCSO_ID);
		lhm.put("LOAI_BCS", LOAI_BCS);
		lhm.put("LOAI_CS", LOAI_CS);
		lhm.put("TEN_KHANG", TEN_KHANG);
		lhm.put("DIA_CHI", DIA_CHI);
		lhm.put("MA_NN", MA_NN);
		lhm.put("SO_HO", SO_HO);
		lhm.put("MA_CTO", MA_CTO);
		lhm.put("SERY_CTO", SERY_CTO);
		lhm.put("HSN", HSN);
		lhm.put("CS_CU", CS_CU);
		lhm.put("TTR_CU", TTR_CU);
		lhm.put("SL_CU", SL_CU);
		lhm.put("SL_TTIEP", SL_TTIEP);
		lhm.put("NGAY_CU", NGAY_CU);
		lhm.put("CS_MOI", CS_MOI);
		lhm.put("TTR_MOI", TTR_MOI);
		lhm.put("SL_MOI", SL_MOI);
		lhm.put("CHUOI_GIA", CHUOI_GIA);
		lhm.put("KY", KY);
		lhm.put("THANG", THANG);
		lhm.put("NAM", NAM);
		lhm.put("NGAY_MOI", NGAY_MOI);
		lhm.put("NGUOI_GCS", NGUOI_GCS);
		lhm.put("SL_THAO", SL_THAO);
		lhm.put("KIMUA_CSPK", KIMUA_CSPK);
		lhm.put("MA_COT", MA_COT);
		lhm.put("CGPVTHD", CGPVTHD);
		lhm.put("HTHUC_TBAO_DK", HTHUC_TBAO_DK);
		lhm.put("DTHOAI_SMS", DTHOAI_SMS);
		lhm.put("EMAIL", EMAIL);
		lhm.put("THOI_GIAN", THOI_GIAN);
		lhm.put("X", "" + X);
		lhm.put("Y", "" + Y);
		lhm.put("SO_TIEN", SO_TIEN);
		lhm.put("HTHUC_TBAO_TH", HTHUC_TBAO_TH);
		lhm.put("TTHAI_DBO", TTHAI_DBO);
		lhm.put("DU_PHONG", DU_PHONG);
		lhm.put("GHICHU", GHICHU);
		lhm.put("TT_KHAC", TT_KHAC);
		lhm.put("ID_SQLITE", ID_SQLITE);
		lhm.put("SLUONG_1", SLUONG_1);
		lhm.put("SLUONG_2", SLUONG_2);
		lhm.put("SLUONG_3", SLUONG_3);
		lhm.put("SO_HOM", SO_HOM);
		lhm.put("PMAX", PMAX);
		lhm.put("NGAY_PMAX", NGAY_PMAX);
		return lhm;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Convert LinkedHashMap to row_gcs
	 * @param lhm
	 * @return
	 */
	public static row_gcs ConvertFromLinkedHashMap(LinkedHashMap<String,String> lhm){
		row_gcs row = null;
		try{
			row = new row_gcs(lhm.get("MA_NVGCS"),
				lhm.get("MA_KHANG"), lhm.get("MA_DDO"),
				lhm.get("MA_DVIQLY"), lhm.get("MA_GC"),
				lhm.get("MA_QUYEN"), lhm.get("MA_TRAM"),
				lhm.get("BOCSO_ID"), lhm.get("LOAI_BCS"),
				lhm.get("LOAI_CS"), lhm.get("TEN_KHANG"),
				lhm.get("DIA_CHI"), lhm.get("MA_NN"),
				lhm.get("SO_HO"), lhm.get("MA_CTO"),
				lhm.get("SERY_CTO"), lhm.get("HSN"),
				lhm.get("CS_CU"), lhm.get("TTR_CU"),
				lhm.get("SL_CU"), lhm.get("SL_TTIEP"),
				lhm.get("NGAY_CU"), lhm.get("CS_MOI"),
				lhm.get("TTR_MOI"), lhm.get("SL_MOI"),
				lhm.get("CHUOI_GIA"), lhm.get("KY"),
				lhm.get("THANG"), lhm.get("NAM"),
				lhm.get("NGAY_MOI"), lhm.get("NGUOI_GCS"),
				lhm.get("SL_THAO"), lhm.get("KIMUA_CSPK"),
				lhm.get("MA_COT"), lhm.get("CGPVTHD"),
				lhm.get("HTHUC_TBAO_DK"),
				lhm.get("DTHOAI_SMS"), lhm.get("EMAIL"),
				lhm.get("THOI_GIAN"), Double.parseDouble(lhm.get("X")),
				Double.parseDouble(lhm.get("Y")), lhm.get("SO_TIEN"),
				lhm.get("HTHUC_TBAO_TH"), lhm.get("TTHAI_DBO"),
				lhm.get("DU_PHONG"), lhm.get("GHICHU"),
				lhm.get("TT_KHAC"), lhm.get("ID_SQLITE"),
				lhm.get("SLUONG_1"), lhm.get("SLUONG_2"),
				lhm.get("SLUONG_3"), lhm.get("SO_HOM"),
				lhm.get("PMAX"), lhm.get("NGAY_PMAX"));
		}
		catch(Exception ex){
			row = null;
		}
		
		return row;
	}
}
