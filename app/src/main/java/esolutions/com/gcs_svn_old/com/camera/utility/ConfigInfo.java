package esolutions.com.gcs_svn_old.com.camera.utility;

public class ConfigInfo {
	private boolean WarningEnable;
	private boolean WarningEnable2;
	private boolean WarningEnable3;
	private int VuotDinhMuc;
	private int VuotDinhMuc2;
	private int DuoiDinhMuc;
	private int DuoiDinhMuc2;
	private boolean AutosaveEnable;
	private int AutosaveMinutes;
	private String IP_SERVICE; ;
	private String Serial;
	private String ColumnsOrder;
	private boolean DinhDang;
	private String DVIQLY;
	
	public ConfigInfo() {
		super();
		WarningEnable = false;
		WarningEnable2 = false;
		WarningEnable3 = false;
		// WarningThreshold = 0;
		VuotDinhMuc = 200;
		VuotDinhMuc2 = 0;
		DuoiDinhMuc = 100;
		DuoiDinhMuc2 = 0;
		AutosaveEnable = false;
		AutosaveMinutes = 10;
		IP_SERVICE = "";
		Serial = "";
		ColumnsOrder = "";
		DinhDang = false;
		DVIQLY = "";
	}

	public ConfigInfo(boolean warningEnable3, boolean warningEnable, boolean warningEnable2, int vuotDinhMuc, int duoiDinhMuc, int vuotDinhMuc2, int duoiDinhMuc2,
			boolean autosaveEnable, int autosaveMinutes, String IP_SERVICE, String Serial, String ColumnsOrder, boolean DinhDang,String DVIQLY) {
		super();
		this.WarningEnable = warningEnable;
		this.WarningEnable2 = warningEnable2;
		this.WarningEnable3 = warningEnable3;

		this.VuotDinhMuc = vuotDinhMuc;
		this.VuotDinhMuc2 = vuotDinhMuc2;
		this.DuoiDinhMuc = duoiDinhMuc;
		this.DuoiDinhMuc2 = duoiDinhMuc2;
		//WarningThreshold = warningThreshold;
		
		this.AutosaveEnable = autosaveEnable;
		this.AutosaveMinutes = autosaveMinutes;
		this.IP_SERVICE = IP_SERVICE;
		this.Serial = Serial;
		this.ColumnsOrder = ColumnsOrder;
		this.DinhDang = DinhDang;
		this.DVIQLY = DVIQLY;
	}


	public String getDVIQLY() {
		return DVIQLY;
	}

	public void setDVIQLY(String dVIQLY) {
		DVIQLY = dVIQLY;
	}

	public boolean isWarningEnable3() {
		return WarningEnable3;
	}

	public void setWarningEnable3(boolean warningEnable3) {
		WarningEnable3 = warningEnable3;
	}

	public boolean isWarningEnable() {
		return WarningEnable;
	}

	public void setWarningEnable(boolean warningEnable) {
		WarningEnable = warningEnable;
	}

	public int getVuotDinhMuc() {
		return VuotDinhMuc;
	}

	public void setVuotDinhMuc(int vuotDinhMuc) {
		VuotDinhMuc = vuotDinhMuc;
	}

	public int getDuoiDinhMuc() {
		return DuoiDinhMuc;
	}

	public void setDuoiDinhMuc(int duoiDinhMuc) {
		DuoiDinhMuc = duoiDinhMuc;
	}

	public boolean isAutosaveEnable() {
		return AutosaveEnable;
	}

	public void setAutosaveEnable(boolean autosaveEnable) {
		AutosaveEnable = autosaveEnable;
	}

	public int getAutosaveMinutes() {
		return AutosaveMinutes;
	}

	public void setAutosaveMinutes(int autosaveMinutes) {
		AutosaveMinutes = autosaveMinutes;
	}

	public String getIP_SERVICE() {
		return IP_SERVICE;
	}

	public void setIP_SERVICE(String iP_SERVICE) {
		IP_SERVICE = iP_SERVICE;
	}
	
	public String getSerial() {
		return Serial;
	}

	public void setSerial(String serial) {
		Serial = serial;
	}

	public String getColumnsOrder() {
		return ColumnsOrder;
	}

	public void setColumnsOrder(String columnsOrder) {
		ColumnsOrder = columnsOrder;
	}

	public boolean isWarningEnable2() {
		return WarningEnable2;
	}

	public void setWarningEnable2(boolean warningEnable2) {
		WarningEnable2 = warningEnable2;
	}

	public int getVuotDinhMuc2() {
		return VuotDinhMuc2;
	}

	public void setVuotDinhMuc2(int vuotDinhMuc2) {
		VuotDinhMuc2 = vuotDinhMuc2;
	}

	public int getDuoiDinhMuc2() {
		return DuoiDinhMuc2;
	}

	public void setDuoiDinhMuc2(int duoiDinhMuc2) {
		DuoiDinhMuc2 = duoiDinhMuc2;
	}

	public boolean getDinhDang() {
		return DinhDang;
	}

	public void setDinhDang(boolean dinhDang) {
		DinhDang = dinhDang;
	}

}
