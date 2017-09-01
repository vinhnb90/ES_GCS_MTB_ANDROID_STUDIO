package esolutions.com.gcs_svn_old.com.camera.utility;


import esolutions.com.gcs_svn_old.R;
import esolutions.com.gcs_svn_old.R.drawable;

public class ConstantVariables {

	public static final String[] TTGCS_COLNAME = new String[] { "Quyen", "DiemDo", "DaGhi",	"ChuaGhi" };
	public static final int[] TTGCS_COLID = new int[] { R.id.tv_Quyen, R.id.tv_DiemDo, R.id.tv_DaGhi, R.id.tv_ChuaGhi };
	public static final String[] THSL_COLNAME = new String[] { "Quyen", "DiemDo", "SLHC", "SLVC" };
	public static final int[] THSL_COLID = new int[] { R.id.tv_Quyen, R.id.tv_DiemDo, R.id.tv_SLHC, R.id.tv_SLVC };
	public static final String[] COLARR = {"ID_ROUTE", "MA_NVGCS", "MA_KHANG", "MA_DDO",
		"MA_DVIQLY", "MA_GC", "MA_QUYEN", "MA_TRAM", "BOCSO_ID",
		"LOAI_BCS", "LOAI_CS", "TEN_KHANG", "DIA_CHI", "MA_NN", "SO_HO",
		"MA_CTO", "SERY_CTO", "HSN", "CS_CU", "TTR_CU", "SL_CU",
		"SL_TTIEP", "NGAY_CU", "CS_MOI", "TTR_MOI", "SL_MOI", "CHUOI_GIA",
		"KY", "THANG", "NAM", "NGAY_MOI", "NGUOI_GCS", "SL_THAO",
		"KIMUA_CSPK", "MA_COT", "CGPVTHD", "HTHUC_TBAO_DK", "DTHOAI_SMS",
		"EMAIL", "THOI_GIAN", "X", "Y", "SO_TIEN", "HTHUC_TBAO_TH", "TENKHANG_RUTGON",
		"TTHAI_DBO", "DU_PHONG", "TEN_FILE", "GHICHU", "TT_KHAC", "ID_SQLITE",
		"SLUONG_1","SLUONG_2","SLUONG_3","SO_HOM", "CHU_KY", "HINH_ANH", "PMAX", "NGAY_PMAX" };
	public static String[] MENU_PAGER = { "Xem chi tiết", "Thêm ghi chú", "Xóa chỉ số mới","Công tơ đã xóa chỉ số",
		"Công tơ sản lượng bất thường", "Công tơ trạng thái bất thường", "Công tơ vượt quá sản lượng 200%",
		"Thêm khách hàng phát triển mới", "Xem bản đồ", "Xem toàn bộ vị trí", "Tạm tính tiền điện", "Bật chế độ quét mã vạch", "In thông báo",
		"Chọn máy in"};
	public static String[] MENU_PAGER_2 = { "Xem chi tiết", "Thêm ghi chú", "Xóa chỉ số mới", "Công tơ đã xóa chỉ số",
		"Công tơ sản lượng bất thường", "Công tơ trạng thái bất thường", "Công tơ vượt quá sản lượng 200%",
		"Thêm khách hàng phát triển mới", "Xem bản đồ", "Xem toàn bộ vị trí", "Tạm tính tiền điện", "Tắt chế độ quét mã vạch", "In thông báo",
		"Chọn máy in"};
	public static final int[] ICON_PAGER = { drawable.gcs_view, drawable.gcs_add,
		drawable.gcs_delete, drawable.gcs_delete, drawable.gcs_csbt, drawable.gcs_status, drawable.gcs_warning, 
		drawable.gcs_new_customer, drawable.gcs_map, drawable.gcs_map, drawable.gcs_cal, 
		drawable.gcs_scanbacode, drawable.gcs_printer, drawable.gcs_select };
	public static final String[] ARR_CONTENT_TRANGTHAI = new String[] { "", "U", "Q", "C", "K", "V", "M", "T", "L", "X", "H", "D", "G" };
	public static final String[] ARR_TRANGTHAI = new String[] { "Trạng thái c.tơ",
			"U - Không dùng", "Q - Qua vòng", "C - Cháy", "K - Kẹt",
			"V - Vắng nhà", "M - Mất", "T - CS khách hàng báo", "L - Quá số",
			"X - Có thay đổi", "H - Hỏng", "D - Bị cắt điện",
			"G - Bị rời vị trí" };
	public static final String[] TIEU_CHI_TIM_KIEM = new String[] { "Mã cột",
		"Chưa ghi", "Đã ghi", "Chưa chụp", "Số C.Tơ", "STT", "Tên KH", "Mã GC", "Mã KH", "CS cũ",
		"SL cũ", "SL mới", "Địa chỉ" };
	public static final String[] COL_TIMKIEM = new String[] { "MA_COT", "CHUA_GHI", "DA_GHI",
		"CHUA_CHUP", "SERY_CTO", "STT", "TEN_KHANG", "MA_GC", "MA_KHANG", "CS_CU",
		"SL_CU", "SL_MOI", "DIA_CHI" };
	
	public static final boolean DEBUG = false;
	public static final String CODE_CHANGE_VERSION = "es_version";
	public static final String CODE_CHANGE_CAMERA = "es_change_camera";
	public static final String CODE_LOGIN = "es_login";
	public static final String CODE_REGISTER = "es_register";
	public static final String CODE_CHANGE_DATE = "es_change_date";
	public static final String CODE_REGISTER2 = "esr";
	public static final String CODE_CHANGE_NGAY_PMAX = "doi_ngay_pmax";
	public static final String CODE_LOAD_FOLDER = "lf";
	public static final String CODE_LOOKUP = "es_lookup";
	public static final String LANG = "eng";
	public static final String KEY = "698423b50cadf4e548536f39c10b3aa3";//45683968
	public static final int NO_SELECTED_COLOR = 0xFFFFFFFF;
	public static final int SELECTED_COLOR = 0xff00ffff;
	public static final String NAMESPACE = "http://tempuri.org/";
	public static final String FORMAT_DECIMAL_PATTERN = "#.###";
	public static final int REQUEST_SETTINGS = 0;
	public static final String URL_PAUSE = "http://192.168.2.1/?action=command&command=video_pause";
	public static final String URL_RESUME = "http://192.168.2.1/?action=command&command=video_resume";
	public static final String URL_STATUS = "http://192.168.2.1?action=command&command=video_get_status";
	
}
