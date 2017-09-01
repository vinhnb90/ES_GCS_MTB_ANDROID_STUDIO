package esolutions.com.gcs_svn_old.com.camera.utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import esolutions.com.gcs_svn_old.com.camera.entity.EntityGCS;
import esolutions.com.gcs_svn_old.com.camera.entity.EntityIndex;
import esolutions.com.gcs_svn_old.com.camera.entity.EntityLog;
import esolutions.com.gcs_svn_old.com.camera.entity.EntityLogDelete;
import esolutions.com.gcs_svn_old.com.camera.entity.EntityRoute;
import esolutions.com.gcs_svn_old.com.camera.entity.EntitySo;

public class SQLiteConnection extends SQLiteOpenHelper {

	private String DATABASE_NAME = null;//"ESGCS.s3db"
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME_INDEX = "gcsindex";
	private static final String TABLE_NAME_ROUTE = "GCS_LO_TRINH";
	private static final String CREATE_TABLE_ROUTE = "CREATE TABLE [GCS_LO_TRINH] ("
            + "[ID_ROUTE] INTEGER  PRIMARY KEY AUTOINCREMENT NULL," + "[SERY_CTO] VARCHAR(50)  NULL,"
            + "[STT_ORG] INTEGER  NULL," + "[STT_ROUTE] INTEGER  NULL,"
            + "[LOAI_BCS] VARCHAR(50)  NULL," + "[TEN_FILE] VARCHAR(200)  NULL)";
	private static final String CREATE_TABLE_INDEX = "CREATE TABLE "
			+ TABLE_NAME_INDEX
			+ "(_id integer primary key autoincrement, so text, vitri integer)";
	private static final String TABLE_NAME_SO = "GCS_SO_NVGCS";
	private static final String CREATE_TABLE_SO = "CREATE TABLE "
			+ TABLE_NAME_SO + "(_id integer primary key autoincrement,"
			+ "MA_DVIQLY text," + "MA_NVGCS text," + "TEN_SOGCS text,"
			+ "MA_DQL text)";
	private static final String TABLE_NAME_CHISO = "GCS_CHISO_HHU";
	private static final String CREATE_TABLE_CHISO = "CREATE TABLE "
			+ TABLE_NAME_CHISO + "(ID INTEGER ,"
			+ "MA_NVGCS TEXT," + "MA_KHANG TEXT," + "MA_DDO TEXT,"
			+ "MA_DVIQLY TEXT," + "MA_GC TEXT," + "MA_QUYEN TEXT,"
			+ "MA_TRAM TEXT," + "BOCSO_ID TEXT," + "LOAI_BCS TEXT,"
			+ "LOAI_CS TEXT," + "TEN_KHANG TEXT," + "DIA_CHI TEXT,"
			+ "MA_NN TEXT," + "SO_HO INTEGER," + "MA_CTO TEXT,"
			+ "SERY_CTO TEXT," + "HSN INTEGER," + "CS_CU FLOAT,"
			+ "TTR_CU TEXT," + "SL_CU FLOAT," + "SL_TTIEP INTEGER,"
			+ "NGAY_CU TEXT," + "CS_MOI FLOAT," + "TTR_MOI TEXT,"
			+ "SL_MOI FLOAT," + "CHUOI_GIA TEXT," + "KY INTEGER,"
			+ "THANG INTEGER," + "NAM INTEGER," + "NGAY_MOI TEXT,"
			+ "NGUOI_GCS TEXT," + "SL_THAO FLOAT," + "KIMUA_CSPK INTEGER,"
			+ "MA_COT TEXT," + "CGPVTHD TEXT," + "HTHUC_TBAO_DK TEXT,"
			+ "DTHOAI_SMS TEXT," + "EMAIL TEXT," + "THOI_GIAN TEXT,"
			+ "X FLOAT," + "Y FLOAT," + "Z FLOAT," + "SO_TIEN FLOAT,"
			+ "HTHUC_TBAO_TH TEXT," + "TENKHANG_RUTGON TEXT,"
			+ "TTHAI_DBO INTEGER," + "DU_PHONG TEXT," + "TEN_FILE TEXT,"
			+ "GHICHU TEXT,"+ "[TT_KHAC] TEXT  NULL,"+ "[ID_SQLITE] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "[SLUONG_1] TEXT  NULL," + "[SLUONG_2] TEXT  NULL," + "[SLUONG_3] TEXT  NULL," + "[SO_HOM] TEXT  NULL,"
			+ "[CHU_KY] BLOB NULL," + "[HINH_ANH] BLOB NULL," + "[PMAX] FLOAT NULL," + "[NGAY_PMAX] TEXT NULL," + "[STR_CHECK_DSOAT] TEXT NULL)";
	private static final String TABLE_NAME_CUSTOMER = "GCS_CUSTOMER";
	@SuppressWarnings("unused")
	private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE "
			+ TABLE_NAME_CUSTOMER
			+ "(id integer primary key autoincrement, noi_dung text)";
	
	private static final String TABLE_NAME_LOG_DELETE = "GCS_LOG_DELETE";
	private static final String CREATE_TABLE_LOG_DELETE = "CREATE TABLE " + TABLE_NAME_LOG_DELETE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "MA_QUYEN TEXT," + "SERY_CTO TEXT," + "CS_XOA TEXT," + "NGAY_XOA TEXT," + "LY_DO TEXT)";
	
	private static final String TABLE_NAME_LOG_CS = "GCS_LOG";
	private static final String CREATE_TABLE_LOG_CS = "CREATE TABLE " + TABLE_NAME_LOG_CS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "TEN_FILE TEXT," + "MA_QUYEN TEXT," + "MA_CTO TEXT," + "SERY_CTO TEXT," + "LOAI_BCS TEXT," + "CHI_SO TEXT," + "NGAY_SUA TEXT," + "LOAI INTEGER)";
	
	private String db = null;//Environment.getExternalStorageDirectory() + "/ESGCS/DB/"
//	private static String data = Environment.getExternalStorageDirectory() + "/ESGCS/DB/";
	private int count;

	private SQLiteDatabase database;
	Common comm = new Common();
	
	/**
	 * Tạo bảng GCS_CHISO_HHU nếu chưa có
	 * @return
	 */
	public String CreateTableSqlite_GCS_CHISO_HHU()
    {
        String sql_check = "SELECT name FROM sqlite_master WHERE name='GCS_CHISO_HHU'";
        
    	database = this.getReadableDatabase();
    	Cursor table_exist = database.rawQuery(sql_check, null);
		if(table_exist != null && table_exist.moveToFirst()){
			return "exist";
		}
		database = this.getWritableDatabase();
        database.execSQL(CREATE_TABLE_CHISO);
        return null;
    }

	/**
	 * Tạo bảng GCS_SO_NVGCS nếu chưa có
	 * @return
	 */
    public String CreateTableSqlite_GCS_SO_NVGCS()
    {
        String sql_check = "SELECT name FROM sqlite_master WHERE name='GCS_SO_NVGCS'";
        
    	database = this.getReadableDatabase();
    	Cursor table_exist = database.rawQuery(sql_check, null);
        if (table_exist != null && table_exist.moveToFirst())
        {
            return "exist";
        }
        database = this.getWritableDatabase();
        database.execSQL(CREATE_TABLE_SO);
        return null;
        
    }

    /**
	 * Tạo bảng gcsindex nếu chưa có
	 * @return
	 */
    public String CreateTableSqlite_gcsindex()
    {
        String sql_check = "SELECT name FROM sqlite_master WHERE name='gcsindex'";
        
    	database = this.getReadableDatabase();
    	Cursor table_exist = database.rawQuery(sql_check, null);
        if (table_exist != null && table_exist.moveToFirst())
        {
            return "exist";
        }
        database = this.getWritableDatabase();
        database.execSQL(CREATE_TABLE_INDEX);
        return null;
    }

    /**
	 * Tạo bảng GCS_LO_TRINH nếu chưa có
	 * @return
	 */
    public String CreateTableSqlite_GCS_LO_TRINH()
    {
    	String sql_check = "SELECT name FROM sqlite_master WHERE name='GCS_LO_TRINH'";
        
    	database = this.getReadableDatabase();
    	Cursor table_exist = database.rawQuery(sql_check, null);
        if (table_exist != null && table_exist.moveToFirst())
        {
            return "exist";
        }
        database = this.getWritableDatabase();
        database.execSQL(CREATE_TABLE_ROUTE);
        return null;
    }
	
    public String CreateTableSqlite_GCS_LOG_DELETE()
    {
        String sql_check = "SELECT name FROM sqlite_master WHERE name='GCS_LOG_DELETE'";
        
    	database = this.getReadableDatabase();
    	Cursor table_exist = database.rawQuery(sql_check, null);
		if(table_exist != null && table_exist.moveToFirst()){
			return "exist";
		}
		database = this.getWritableDatabase();
        database.execSQL(CREATE_TABLE_LOG_DELETE);
        return null;
    }
    
    public String CreateTableSqlite_GCS_LOG()
    {
        String sql_check = "SELECT name FROM sqlite_master WHERE name='GCS_LOG'";
        
    	database = this.getReadableDatabase();
    	Cursor table_exist = database.rawQuery(sql_check, null);
		if(table_exist != null && table_exist.moveToFirst()){
			return "exist";
		}
		database = this.getWritableDatabase();
        database.execSQL(CREATE_TABLE_LOG_CS);
        return null;
    }
    
	public void GetWritableDatabase(){
		database = this.getWritableDatabase();
	}
	
	/**
	 * Mở transaction
	 * @return
	 */
	public void BeginTransaction(){
		database.beginTransaction();
	}
	
	/**
	 * Lưu transaction
	 * @return
	 */
	public void SetTransactionSuccessful(){
		database.setTransactionSuccessful();
	}
	
	/**
	 * Đóng transaction
	 * @return
	 */
	public void EndTransaction(){
		database.endTransaction();
	}
	
	public class DbErrorHandler implements DatabaseErrorHandler {
		@Override
		public void onCorruption(SQLiteDatabase dbObj) {
			// TODO Auto-generated method stub
		}
	}
	public class SQLLOpenHelper extends SQLiteOpenHelper {

		public SQLLOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
			super(context, name, factory, version, errorHandler);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}

	private void DbConnect(Context context, String db_fullname){
		try{
			// Kiểm tra file có bị lỗi ko
			SQLLOpenHelper dbHelper = new SQLLOpenHelper(context, db_fullname, null, 1, new DbErrorHandler());
			database = dbHelper.getWritableDatabase();
			database.close();
			
			// Mở db
			SQLiteDatabase.openDatabase(db_fullname, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
			
		}catch(Exception ex){
			boolean backup_result = comm.CreateBackup("MALFORM");
			if(backup_result){
				SQLiteDatabase.openOrCreateDatabase(db_fullname, null);		
			}
		}
	}
	
	public SQLiteConnection(Context context, String db_name, String db_folder) {
		super(context, db_folder +"/"+ db_name, null, DATABASE_VERSION);
		DATABASE_NAME = db_name;
		db = db_folder;
		DbConnect(context, db +"/"+ DATABASE_NAME);
//		try{
//			SQLiteDatabase.openDatabase(db +"/"+ DATABASE_NAME, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
//		}catch(Exception ex){
//			boolean backup_result = Common.CreateBackup("MALFORM");
//			if(backup_result){
//				SQLiteDatabase.openOrCreateDatabase(db +"/"+ DATABASE_NAME, null);		
//			}
//		}
//		SQLiteDatabase.openOrCreateDatabase(db +"/"+ DATABASE_NAME, null);  //openOrCreateDatabase(db +"/"+ DATABASE_NAME, null); 
//		SQLiteDatabase.openDatabase(db +"/"+ DATABASE_NAME, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
	}
	
//	public SQLiteConnection(Context context) {
//		super(context, data + "ESGCS.s3db", null, DATABASE_VERSION);
//		DbConnect(context, data + "ESGCS.s3db");
//		try{
//			SQLiteDatabase.openDatabase(data + "ESGCS.s3db", null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
//		}catch(Exception ex){
//			boolean backup_result = Common.CreateBackup("ERROR");
//			if(backup_result){
//				SQLiteDatabase.openOrCreateDatabase(data + "ESGCS.s3db", null);	
//			}
//		}
		
//	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		 db.execSQL(CREATE_TABLE_INDEX);
//		 db.execSQL(CREATE_TABLE_SO);
//		 db.execSQL(CREATE_TABLE_CHISO);
//		 db.execSQL(CREATE_TABLE_ROUTE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("DROP TABLE IF EXITST " + CREATE_TABLE_INDEX);
//		db.execSQL("DROP TABLE IF EXITST " + CREATE_TABLE_SO);
//		db.execSQL("DROP TABLE IF EXITST " + CREATE_TABLE_CHISO);
//		db.execSQL("DROP TABLE IF EXITST " + CREATE_TABLE_ROUTE);
		onCreate(db);
	}

	public static String getTableNameIndex() {
		return TABLE_NAME_INDEX;
	}

	public static String getTableNameSo() {
		return TABLE_NAME_SO;
	}

	public static String getTableNameChiso() {
		return TABLE_NAME_CHISO;
	}

	// Xử lý bảng lưu sổ
	public long insertDataSo(String MA_DVIQLY, String MA_NVGCS,
			String TEN_SOGCS, String MA_DQL) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("MA_DVIQLY", MA_DVIQLY);
		values.put("MA_NVGCS", MA_NVGCS);
		values.put("TEN_SOGCS", TEN_SOGCS);
		values.put("MA_DQL", MA_DQL);
		return database.insert(TABLE_NAME_SO, null, values);
	}

	public long updateDataSo(int _ID, String MA_DVIQLY, String MA_NVGCS,
			String TEN_SOGCS, String MA_DQL) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("MA_DVIQLY", MA_DVIQLY);
		values.put("MA_NVGCS", MA_NVGCS);
		values.put("TEN_SOGCS", TEN_SOGCS);
		values.put("MA_DQL", MA_DQL);
		return database.update(TABLE_NAME_SO, values, "_id=?",
				new String[] { "" + _ID });
	}
	
	public long updateDataTenSo(int _ID, String TEN_SOGCS) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("TEN_SOGCS", TEN_SOGCS);
		return database.update(TABLE_NAME_SO, values, "_id=?", new String[] { "" + _ID });
	}

	public long deleteAllDataSo() {
		database = this.getWritableDatabase();
		return database.delete(TABLE_NAME_SO, null, null);
	}
	
	public Cursor getAllDataSo() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_SO;		
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getDataSo(String so1, String so2) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT TEN_SOGCS FROM " + TABLE_NAME_SO + " WHERE TEN_SOGCS <> '" + so1 + "' AND TEN_SOGCS <> '" + so2 + "'";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getDataSo(String so1) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT TEN_SOGCS FROM " + TABLE_NAME_SO + " WHERE TEN_SOGCS <> '" + so1 + "'";
		return database.rawQuery(strQuery, null);
	}
	public Cursor getDataSo(String[] so) {
		String strSo = "";
		for(String s : so){
			strSo += ",'"+s+"'";
		}
		strSo = strSo.substring(1);
		database = this.getReadableDatabase();
		String strQuery = "SELECT TEN_SOGCS FROM " + TABLE_NAME_SO + " WHERE TEN_SOGCS NOT IN (" + strSo + ")";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getDataSoInData(String[] so) {
		String strSo = "";
		for(String s : so){
			strSo += ",'"+s+"'";
		}
		strSo = strSo.substring(1);
		database = this.getReadableDatabase();
		String strQuery = "SELECT DISTINCT(MA_QUYEN) FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN NOT IN (" + strSo + ")";
		return database.rawQuery(strQuery, null);
	}
	
	public boolean getMaDvi(){
		database = this.getReadableDatabase();
		String strQuery = "SELECT distinct MA_DVIQLY FROM "
				+ TABLE_NAME_SO
				+ " where MA_DVIQLY not like 'PD%' and MA_DVIQLY not like 'PM%' and "
				+ "MA_DVIQLY not like 'PA25%' and MA_DVIQLY not like 'PA15%' and " 
				+ "MA_DVIQLY not like 'PA22%' and MA_DVIQLY not like 'PA26%' and "
				+ "MA_DVIQLY not like 'PA18%' and MA_DVIQLY not like 'PA12%' and "
				+ "MA_DVIQLY not like 'PA23%' and MA_DVIQLY not like 'PA20%' and "
				+ "MA_DVIQLY not like 'PA02%' and MA_DVIQLY not like 'PA0508%' and "
				+ "MA_DVIQLY not like 'PA29%' and MA_DVIQLY not like 'PA0508%' and "
				+ "MA_DVIQLY not like 'PH%'";// 
		Cursor c = database.rawQuery(strQuery, null);
		if(c.getCount() > 0){
			return false;
		} else {
			return true;
		}
	}
	
	public boolean getMaDviLC(){
		database = this.getReadableDatabase();
		String strQuery = "SELECT distinct MA_DVIQLY FROM " + TABLE_NAME_SO + " where MA_DVIQLY not like '%PA29%'";
		Cursor c = database.rawQuery(strQuery, null);
		if(c.getCount() > 0){
			return false;
		} else {
			return true;
		}
	}

	public EntitySo getDataSo(int _ID) {
		database = this.getReadableDatabase();
		Cursor c = database.query(TABLE_NAME_INDEX, new String[] { "_id",
				"MA_DVIQLY", "MA_NVGCS", "TEN_SOGCS", "MA_DQL" }, "_id=?",
				new String[] { "" + _ID }, null, null, null, null);
		if (c != null)
			c.moveToFirst();
		EntitySo e = new EntitySo(Integer.parseInt(c.getString(0)),
				c.getString(1), c.getString(2), c.getString(3),
				c.getString(4));
		return e;
	}

	public int checkDataSo() {
		try {
			database = this.getReadableDatabase();
			String strQuery = "SELECT COUNT(*) FROM " + TABLE_NAME_SO;
			Cursor c = database.rawQuery(strQuery, null);
			count = 0;
			if (c.moveToFirst()) {
				do {
					count = c.getInt(0);
				} while (c.moveToNext());
			}
			return count;
		} catch (Exception ex) {
			return count;
		}
	}

	// Xử lý bảng ghi chỉ số
	public long insertDataGCS(String MA_NVGCS, String MA_KHANG, String MA_DDO,
			String MA_DVIQLY, String MA_GC, String MA_QUYEN, String MA_TRAM,
			String BOCSO_ID, String LOAI_BCS, String LOAI_CS, String TEN_KHANG,
			String DIA_CHI, String MA_NN, String SO_HO, String MA_CTO,
			String SERY_CTO, String HSN, String CS_CU, String TTR_CU,
			String SL_CU, String SL_TTIEP, String NGAY_CU, String CS_MOI,
			String TTR_MOI, String SL_MOI, String CHUOI_GIA, String KY,
			String THANG, String NAM, String NGAY_MOI, String NGUOI_GCS,
			String SL_THAO, String KIMUA_CSPK, String MA_COT, String CGPVTHD,
			String HTHUC_TBAO_DK, String DTHOAI_SMS, String EMAIL,
			String THOI_GIAN, String X, String Y, String SO_TIEN,
			String HTHUC_TBAO_TH, String TENKHANG_RUTGON, String TTHAI_DBO,
			String DU_PHONG, String TEN_FILE, String GHICHU, String TT_KHAC, String ID_SQLITE,
			String SLUONG_1, String  SLUONG_2,String SLUONG_3,String SO_HOM, String STR_CHECK_DSOAT) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("MA_NVGCS", MA_NVGCS);
		values.put("MA_KHANG", MA_KHANG);
		values.put("MA_DDO", MA_DDO);
		values.put("MA_DVIQLY", MA_DVIQLY);
		values.put("MA_GC", MA_GC);
		values.put("MA_QUYEN", MA_QUYEN);
		values.put("MA_TRAM", MA_TRAM);
		values.put("BOCSO_ID", BOCSO_ID);
		values.put("LOAI_BCS", LOAI_BCS);
		values.put("LOAI_CS", LOAI_CS);
		values.put("TEN_KHANG", TEN_KHANG);
		values.put("DIA_CHI", DIA_CHI);
		values.put("MA_NN", MA_NN);
		values.put("SO_HO", SO_HO);
		values.put("MA_CTO", MA_CTO);
		values.put("SERY_CTO", SERY_CTO);
		values.put("HSN", HSN);
		values.put("CS_CU", CS_CU);
		values.put("TTR_CU", TTR_CU);
		values.put("SL_CU", SL_CU);
		values.put("SL_TTIEP", SL_TTIEP);
		values.put("NGAY_CU", NGAY_CU);
		values.put("CS_MOI", CS_MOI);
		values.put("TTR_MOI", TTR_MOI);
		values.put("SL_MOI", SL_MOI);
		values.put("CHUOI_GIA", CHUOI_GIA);
		values.put("KY", KY);
		values.put("THANG", THANG);
		values.put("NAM", NAM);
		values.put("NGAY_MOI", NGAY_MOI);
		values.put("NGUOI_GCS", NGUOI_GCS);
		values.put("SL_THAO", SL_THAO);
		values.put("KIMUA_CSPK", KIMUA_CSPK);
		values.put("MA_COT", MA_COT);
		values.put("HTHUC_TBAO_DK", HTHUC_TBAO_DK);
		values.put("DTHOAI_SMS", DTHOAI_SMS);
		values.put("EMAIL", EMAIL);
		values.put("THOI_GIAN", THOI_GIAN);
		values.put("X", X);
		values.put("Y", Y);
		values.put("SO_TIEN", SO_TIEN);
		values.put("HTHUC_TBAO_TH", HTHUC_TBAO_TH);
		values.put("TENKHANG_RUTGON", TENKHANG_RUTGON);
		values.put("TTHAI_DBO", TTHAI_DBO);
		values.put("DU_PHONG", DU_PHONG);
		values.put("TEN_FILE", TEN_FILE);
		values.put("GHICHU", GHICHU);
		values.put("TT_KHAC", TT_KHAC);
		values.put("ID_SQLITE", ID_SQLITE);
		values.put("SLUONG_1", SLUONG_1);
		values.put("SLUONG_2", SLUONG_2);
		values.put("SLUONG_3", SLUONG_3);
		values.put("SO_HOM", SO_HOM);
		values.put("STR_CHECK_DSOAT", STR_CHECK_DSOAT);
		return database.insert(TABLE_NAME_CHISO, null, values);
	}

	public long updateAllDataGCS(int ID, String MA_NVGCS, String MA_KHANG,
			String MA_DDO, String MA_DVIQLY, String MA_GC, String MA_QUYEN,
			String MA_TRAM, String BOCSO_ID, String LOAI_BCS, String LOAI_CS,
			String TEN_KHANG, String DIA_CHI, String MA_NN, String SO_HO,
			String MA_CTO, String SERY_CTO, String HSN, String CS_CU,
			String TTR_CU, String SL_CU, String SL_TTIEP, String NGAY_CU,
			String CS_MOI, String TTR_MOI, String SL_MOI, String CHUOI_GIA,
			String KY, String THANG, String NAM, String NGAY_MOI,
			String NGUOI_GCS, String SL_THAO, String KIMUA_CSPK, String MA_COT,
			String CGPVTHD, String HTHUC_TBAO_DK, String DTHOAI_SMS,
			String EMAIL, String THOI_GIAN, String X, String Y, String SO_TIEN,
			String HTHUC_TBAO_TH, String TENKHANG_RUTGON, String TTHAI_DBO,
			String DU_PHONG, String TEN_FILE, String GHICHU, String TT_KHAC,
			String ID_SQLITE, String SLUONG_1,String SLUONG_2,String SLUONG_3,String SO_HOM, String STR_CHECK_DSOAT) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("MA_NVGCS", MA_NVGCS);
		values.put("MA_KHANG", MA_KHANG);
		values.put("MA_DDO", MA_DDO);
		values.put("MA_DVIQLY", MA_DVIQLY);
		values.put("MA_GC", MA_GC);
		values.put("MA_QUYEN", MA_QUYEN);
		values.put("MA_TRAM", MA_TRAM);
		values.put("BOCSO_ID", BOCSO_ID);
		values.put("LOAI_BCS", LOAI_BCS);
		values.put("LOAI_CS", LOAI_CS);
		values.put("TEN_KHANG", TEN_KHANG);
		values.put("DIA_CHI", DIA_CHI);
		values.put("MA_NN", MA_NN);
		values.put("SO_HO", SO_HO);
		values.put("MA_CTO", MA_CTO);
		values.put("SERY_CTO", SERY_CTO);
		values.put("HSN", HSN);
		values.put("CS_CU", CS_CU);
		values.put("TTR_CU", TTR_CU);
		values.put("SL_CU", SL_CU);
		values.put("SL_TTIEP", SL_TTIEP);
		values.put("NGAY_CU", NGAY_CU);
		values.put("CS_MOI", CS_MOI);
		values.put("TTR_MOI", TTR_MOI);
		values.put("SL_MOI", SL_MOI);
		values.put("CHUOI_GIA", CHUOI_GIA);
		values.put("KY", KY);
		values.put("THANG", THANG);
		values.put("NAM", NAM);
		values.put("NGAY_MOI", NGAY_MOI);
		values.put("NGUOI_GCS", NGUOI_GCS);
		values.put("SL_THAO", SL_THAO);
		values.put("KIMUA_CSPK", KIMUA_CSPK);
		values.put("MA_COT", MA_COT);
		values.put("HTHUC_TBAO_DK", HTHUC_TBAO_DK);
		values.put("DTHOAI_SMS", DTHOAI_SMS);
		values.put("EMAIL", EMAIL);
		values.put("THOI_GIAN", THOI_GIAN);
		values.put("X", X);
		values.put("Y", Y);
//		values.put("Z", 0);
		values.put("SO_TIEN", SO_TIEN);
		values.put("HTHUC_TBAO_TH", HTHUC_TBAO_TH);
		values.put("TENKHANG_RUTGON", TENKHANG_RUTGON);
		values.put("TTHAI_DBO", TTHAI_DBO);
		values.put("DU_PHONG", DU_PHONG);
		values.put("TEN_FILE", TEN_FILE);
		values.put("GHICHU", GHICHU);
		values.put("TT_KHAC", TT_KHAC);
		values.put("ID_SQLITE", ID_SQLITE);
		values.put("SLUONG_1", SLUONG_1);
		values.put("SLUONG_2", SLUONG_2);
		values.put("SLUONG_3", SLUONG_3);
		values.put("SO_HOM", SO_HOM);
		values.put("STR_CHECK_DSOAT", STR_CHECK_DSOAT);
		return database.update(TABLE_NAME_CHISO, values, "ID=?",
				new String[] { "" + ID });
	}

	public long updateDataGCS(String ID_SQLITE, String CS_MOI, String TTR_MOI, String SL_MOI, 
			String THOI_GIAN, String X, String Y, String TTHAI_DBO, String TT_KHAC, String STR_CHECK_DSOAT) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("CS_MOI", CS_MOI);
		values.put("TTR_MOI", TTR_MOI);
		values.put("SL_MOI", SL_MOI);
		values.put("THOI_GIAN", THOI_GIAN);
		values.put("X", X);
		values.put("Y", Y);
		values.put("TTHAI_DBO", TTHAI_DBO);
		values.put("TT_KHAC", TT_KHAC);
		values.put("STR_CHECK_DSOAT", STR_CHECK_DSOAT);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long updateDataGCS(String ID_SQLITE, String CS_MOI, String TTR_MOI, String SL_MOI, 
			String THOI_GIAN, String X, String Y, String TTHAI_DBO, String TT_KHAC) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("CS_MOI", CS_MOI);
		values.put("TTR_MOI", TTR_MOI);
		values.put("SL_MOI", SL_MOI);
		values.put("THOI_GIAN", THOI_GIAN);
		values.put("X", X);
		values.put("Y", Y);
		values.put("TTHAI_DBO", TTHAI_DBO);
		values.put("TT_KHAC", TT_KHAC);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long updateDataGCS2(String ID_SQLITE, String CS_MOI, String TTR_MOI, String SL_MOI, 
			String THOI_GIAN, String X, String Y, String TTHAI_DBO, String TT_KHAC, String PMAX, 
			String NGAY_PMAX, String STR_CHECK_DSOAT) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("CS_MOI", CS_MOI);
		values.put("TTR_MOI", TTR_MOI);
		values.put("SL_MOI", SL_MOI);
		values.put("THOI_GIAN", THOI_GIAN);
		values.put("X", X);
		values.put("Y", Y);
		values.put("TTHAI_DBO", TTHAI_DBO);
		values.put("TT_KHAC", TT_KHAC);
		values.put("PMAX", PMAX);
		values.put("NGAY_PMAX", NGAY_PMAX);
		values.put("STR_CHECK_DSOAT", STR_CHECK_DSOAT);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long updateDataGCS3(String ID_SQLITE, String CS_MOI, String TTR_MOI, String SL_MOI, 
			String THOI_GIAN, String X, String Y, String TTHAI_DBO, String TT_KHAC, String PMAX, 
			String NGAY_PMAX) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("CS_MOI", CS_MOI);
		values.put("TTR_MOI", TTR_MOI);
		values.put("SL_MOI", SL_MOI);
		values.put("THOI_GIAN", THOI_GIAN);
		values.put("X", X);
		values.put("Y", Y);
		values.put("TTHAI_DBO", TTHAI_DBO);
		values.put("TT_KHAC", TT_KHAC);
		values.put("PMAX", PMAX);
		values.put("NGAY_PMAX", NGAY_PMAX);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long updateDataGCSPage(String ID_SQLITE, String CS_MOI, String TTR_MOI, String SL_MOI, 
			String THOI_GIAN, String X, String Y, String TTHAI_DBO, String GHICHU, String STR_CHECK_DSOAT) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("CS_MOI", CS_MOI);
		values.put("TTR_MOI", TTR_MOI);
		values.put("SL_MOI", SL_MOI);
		values.put("THOI_GIAN", THOI_GIAN);
		values.put("X", X);
		values.put("Y", Y);
		values.put("TTHAI_DBO", TTHAI_DBO);
		values.put("GHICHU", GHICHU);
		values.put("STR_CHECK_DSOAT", STR_CHECK_DSOAT);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long updateTimeGCS(String ID_SQLITE, String THOI_GIAN) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("THOI_GIAN", THOI_GIAN);
		return database.update(TABLE_NAME_CHISO, values, "ID=?", new String[] {ID_SQLITE });
	}
	
	public long updateTTDsoatGCS() {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("STR_CHECK_DSOAT", "CHUA_DOI_SOAT");
		return database.update(TABLE_NAME_CHISO, values, null, null);
	}
	
	public long clearDataGCS(String ID_SQLITE, String CS_MOI, String TTR_MOI, String SL_MOI, String PMAX, String NGAY_PMAX, String STR_CHECK_DSOAT) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("CS_MOI", CS_MOI);
		values.put("TTR_MOI", TTR_MOI);
		values.put("SL_MOI", SL_MOI);
		values.put("PMAX", PMAX);
		values.put("NGAY_PMAX", NGAY_PMAX);
		values.put("STR_CHECK_DSOAT", STR_CHECK_DSOAT);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long clearDataGCS(String ID_SQLITE, String CS_MOI, String TTR_MOI, String SL_MOI, String STR_CHECK_DSOAT) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("CS_MOI", CS_MOI);
		values.put("TTR_MOI", TTR_MOI);
		values.put("SL_MOI", SL_MOI);
		values.put("STR_CHECK_DSOAT", STR_CHECK_DSOAT);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long updateDataGCS(String ID_SQLITE, String PMAX, String NGAY_PMAX) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("PMAX", PMAX);
		values.put("NGAY_PMAX", NGAY_PMAX);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long updateNoteGCS(String ID_SQLITE, String GHICHU){
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("GHICHU", GHICHU);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long updateNgayPmax(String ID_SQLITE, String NGAY_PMAX){
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("NGAY_PMAX", NGAY_PMAX);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long updateImageGCS(String ID_SQLITE, byte[] image){
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("HINH_ANH", image);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public long updateDataDSoat(String ID_SQLITE, String STR_CHECK_DSOAT) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("STR_CHECK_DSOAT", STR_CHECK_DSOAT);
		return database.update(TABLE_NAME_CHISO, values, "ID_SQLITE=?", new String[] { ID_SQLITE });
	}
	
	public String getDSoat(String ID_SQLITE) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT STR_CHECK_DSOAT FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		if(c.moveToFirst()){
			return c.getString(0);
		}
		return "";
	}
	
	public long deleteAllDataGCS() {
		database = this.getWritableDatabase();
		return database.delete(TABLE_NAME_CHISO, null, null);
	}

	public Cursor setDataEditText(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT CS_MOI,TTR_MOI,GHICHU,PMAX,NGAY_PMAX FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		return c;
	}
	
	public Cursor getDataCS(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT CS_MOI,TTR_MOI FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		return c;
	}
	
	public Cursor getDataForImage(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT TEN_KHANG, CS_MOI, MA_DDO, SERY_CTO, CHUOI_GIA FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		return c;
	}
	
	public Cursor setDataEditText2(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT CS_MOI,TTR_MOI,GHICHU FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		return c;
	}
	
	public Cursor getIDByMaQuyen(String MA_QUYEN){
		database = this.getReadableDatabase();
		String strQuery = "SELECT ID_SQLITE FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN = '" + MA_QUYEN + "'";
		Cursor c = database.rawQuery(strQuery, null);
		return c;
	}
	
	public float getSlmoiById(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT SL_MOI FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		float sl_moi = 0f;
		if(c.moveToFirst()){
			sl_moi = Float.parseFloat(c.getString(0));
		}
		return sl_moi;
	}
	
	public String getBCSById(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT LOAI_BCS FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		String loai_bcs = "";
		if(c.moveToFirst()){
			loai_bcs = c.getString(0);
		}
		return loai_bcs;
	}
	
	public String getDateById(String ID_SQLITE){
		try{
			database = this.getReadableDatabase();
			String strQuery = "SELECT THOI_GIAN FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
			Cursor c = database.rawQuery(strQuery, null);
			String thoi_gian = "";
			if(c.moveToFirst()){
				thoi_gian = c.getString(0).split(" ")[1].toString();
			}
			return thoi_gian;
		} catch(Exception ex){
			return "09:00:00";
		}
	}
	
	public String getNoCtoById(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT SERY_CTO FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		String sery_cto = "";
		if(c.moveToFirst()){
			sery_cto = c.getString(0);
		}
		return sery_cto;
	}
	
	public String getCsMoiById(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT CS_MOI FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		String cs_moi = "";
		if(c.moveToFirst()){
			cs_moi = c.getString(0);
		}
		return cs_moi;
	}
	
	public String getTtrMoiById(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT TTR_MOI FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		String ttr_moi = "";
		if(c.moveToFirst()){
			ttr_moi = c.getString(0);
		}
		return ttr_moi;
	}
	
	public Cursor getDataSameSoCto(String SERY_CTO){
		database = this.getReadableDatabase();
		//SELECT * FROM GCS_CHISO_HHU WHERE SERY_CTO = '00686042' AND LOAI_BCS != 'VC'
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE SERY_CTO = '" + SERY_CTO + "' AND LOAI_BCS <> 'VC'";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataSoInData() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT DISTINCT(MA_QUYEN) FROM " + TABLE_NAME_CHISO;		
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataNgayPmaxGCS() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT ID_SQLITE, NGAY_PMAX FROM " + TABLE_NAME_CHISO;		
		return database.rawQuery(strQuery, null);
	}
	
	public String getAllDataSoInData2(String ma_quyen) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT DISTINCT(TEN_FILE) FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN ='" + ma_quyen + "'";		
		Cursor c = database.rawQuery(strQuery, null);
		String so = "";
		if(c.moveToFirst()){
			so = c.getString(0);
		}
		return so;
	}
	
	public Cursor getAllDataSoOtherData(String quyen) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT DISTINCT(MA_QUYEN) FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN <> '" + quyen + "'";		
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllLatLon(String MA_QUYEN) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT TEN_KHANG, X, Y FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN = '" + MA_QUYEN + "'";		
		return database.rawQuery(strQuery, null);
	}
	
	public String getTenFileByMaQuyen(String MA_QUYEN) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT DISTINCT TEN_FILE FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN = '" + MA_QUYEN + "'";		
		Cursor c =  database.rawQuery(strQuery, null);
		if(c.moveToFirst()){
			return c.getString(0);
		}
		return "";
	}
	
	public int getIndexFirstDataGCS(String fileName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "'";
		Cursor c = database.rawQuery(strQuery, null);
		int idx = 0;
		if(c.moveToFirst()){
			idx = Integer.parseInt(c.getString(0));
		}
		return idx;
	}
	
	public int countByID(String MA_CTO) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT COUNT(*) FROM " + TABLE_NAME_CHISO + " WHERE MA_CTO = '" + MA_CTO + "'";		
		Cursor c =  database.rawQuery(strQuery, null);
		if(c.moveToFirst()){
			return c.getInt(0);
		}
		return 0;
	}
	
	public int getIndexPageDataGCS(String fileName, String tieu_chi, String cs) {
		database = this.getReadableDatabase();
		String strQuery = null;
		if(tieu_chi.equals("Mã cột")){
			strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND MA_COT LIKE '%" + cs + "%'";
		}
		if(tieu_chi.equals("Số C.Tơ")){
			strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND SERY_CTO LIKE '%" + cs + "%'";
		}
		if(tieu_chi.equals("Tên KH")){
			strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND TEN_KHANG LIKE '%" + cs + "%'";
		}
		if(tieu_chi.equals("Mã GC")){
			strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND MA_GC LIKE '%" + cs + "%'";
		}
		if(tieu_chi.equals("Mã KH")){
			strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND MA_KHANG LIKE '%" + cs + "%'";
		}
		if(tieu_chi.equals("CS cũ")){
			strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND CS_CU LIKE '%" + cs + "%'";
		}
		if(tieu_chi.equals("SL cũ")){
			strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND SL_CU LIKE '%" + cs + "%'";
		}
		if(tieu_chi.equals("SL mới")){
			strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND SL_MOI LIKE '%" + cs + "%'";
		}
		if(tieu_chi.equals("Địa chỉ")){
			strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND DIA_CHI LIKE '%" + cs + "%'";
		}
		if(tieu_chi.equals("Chưa ghi")){
			strQuery = "SELECT ROWID as row FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND CS_MOI = '0' OR TTR_MOI = ''";
		}
		Cursor c = database.rawQuery(strQuery, null);
		int idx = 0;
		if(c.moveToFirst()){
			idx = Integer.parseInt(c.getString(0));
		}
		return idx;
	}
	
//	public int countDSUnCheck(String fileName) {
//		database = this.getReadableDatabase();
//		String strQuery = "SELECT COUNT(*) FROM " + TABLE_NAME_CHISO + " WHERE STR_CHECK_DSOAT <> 'CHECK' AND MA_QUYEN = '" + fileName + "'";
//		Cursor c = database.rawQuery(strQuery, null);
//	}
	
	public Cursor getAllDataGCS(String fileName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE STR_CHECK_DSOAT <> 'CHECK' AND STR_CHECK_DSOAT <> 'CTO_DTU' AND MA_QUYEN = '" + 
		fileName + "'";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataGCS2(String fileName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN = '" + fileName + "'";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataGCSBN(String fileName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE " + fileName + " ORDER BY MA_GC ASC";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataGCSByMaQuyen(String MaQuyen) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN = '" + MaQuyen + "'";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataGCSFilt(String fileName, String tieu_chi, String gia_tri) {
		database = this.getReadableDatabase();
		String strQuery = "";
		if(!gia_tri.equals("") && !tieu_chi.equals("CHUA_GHI")){
			strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "' AND " + tieu_chi + " like '%" + gia_tri + "%'";
		} else {
			strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + fileName + "'";
		}
		
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataGCSsoft(String fileName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM "
				+ TABLE_NAME_CHISO
				+ " a, "
				+ TABLE_NAME_ROUTE
				+ " b WHERE a.SERY_CTO = b.SERY_CTO AND a.LOAI_BCS = b.LOAI_BCS AND " +
				"STR_CHECK_DSOAT <> 'CHECK' AND STR_CHECK_DSOAT <> 'CTO_DTU' AND MA_QUYEN = '" + 
				fileName + "' ORDER BY STT_ROUTE";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataGCSsoft2(String fileName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM "
				+ TABLE_NAME_CHISO
				+ " a, "
				+ TABLE_NAME_ROUTE
				+ " b WHERE a.SERY_CTO = b.SERY_CTO AND a.LOAI_BCS = b.LOAI_BCS AND MA_QUYEN = '" + 
				fileName + "' ORDER BY STT_ROUTE";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getImage(String fileName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT HINH_ANH FROM " + TABLE_NAME_CHISO;
		return database.rawQuery(strQuery, null);
	}
	
	public byte[] getImageByID(String ID_SQLITE) {
		byte[] image = null;
		try{
			database = this.getReadableDatabase();
			String strQuery = "SELECT HINH_ANH FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
			Cursor c = database.rawQuery(strQuery, null);
			if(c.moveToFirst()){
				image = c.getBlob(0);
			}
		} catch(Exception ex){
			ex.toString();
		}
		return image;
	}
	
	public Cursor getAllDataGCSsoftFilt(String fileName, String tieu_chi, String gia_tri) {
		database = this.getReadableDatabase();
		String strQuery = "";
		if(!gia_tri.equals("") && !tieu_chi.equals("CHUA_GHI")){
			strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " a, " + TABLE_NAME_ROUTE + " b WHERE a.SERY_CTO = b.SERY_CTO AND a.LOAI_BCS = b.LOAI_BCS AND a.TEN_FILE = '" + fileName + "' AND a." + tieu_chi + " like '%" + gia_tri + "%' ORDER BY STT_ROUTE";
		} else {
			strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " a, " + TABLE_NAME_ROUTE + " b WHERE a.SERY_CTO = b.SERY_CTO AND a.LOAI_BCS = b.LOAI_BCS AND a.TEN_FILE = '" + fileName + "' ORDER BY STT_ROUTE";
		}
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataGCSbyTram(String ma_tram) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE MA_TRAM = '" + ma_tram + "'";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataGCSBySery(String sery_cto) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT LOAI_BCS FROM " + TABLE_NAME_CHISO + " WHERE SERY_CTO = '" + sery_cto + "' AND LOAI_BCS <> 'VC' AND LOAI_BCS <> 'KT'";
		return database.rawQuery(strQuery, null);
	}
	
	public int getCountDataGCSBySery(String sery_cto, String ten_file) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT COUNT(*) FROM " + TABLE_NAME_CHISO + " WHERE SERY_CTO = '" + sery_cto + "' AND UPPER(TEN_FILE)='" + ten_file.toUpperCase(Locale.ENGLISH) + "'";
		Cursor c = database.rawQuery(strQuery, null);
		int dem = 0;
		if(c.moveToFirst()){
			dem = Integer.parseInt(c.getString(0));
		}
		return dem;
	}
	
	public boolean checkDataGCSBySery(String sery_cto, String BCS) {
		database = this.getReadableDatabase();
		int check = 0;
		boolean ck = false;
		String strQuery = "SELECT CS_MOI, TTR_MOI FROM " + TABLE_NAME_CHISO + " WHERE SERY_CTO = '" + sery_cto + "' AND LOAI_BCS <> 'VC' AND LOAI_BCS <> '" + BCS + "'";
		Cursor c = database.rawQuery(strQuery, null);
		if(c.moveToFirst()){
			 do{
				 if(comm.isSavedRow(c.getString(1), c.getString(0))){
					 check++;
				 }
			 } while(c.moveToNext());
		}
		if(check == 3){
			ck = true;
		}
		return ck;
	}
	
	public EntityGCS getDataByMAGC(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " where ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		if(c != null){ 
			c.moveToFirst();
		}
		
		
		EntityGCS entity = new EntityGCS();
		if(c.getString(0) == null){
			entity.set_ID(-1);
		}else{
			entity.set_ID(Integer.parseInt(c.getString(0)));
		}
		
		entity.setMA_NVGCS(c.getString(1));
		entity.setMA_KHANG(c.getString(2));
		entity.setMA_DDO(c.getString(3));
		entity.setMA_DVIQLY(c.getString(4));
		entity.setMA_GC(c.getString(5));
		entity.setMA_QUYEN(c.getString(6));
		entity.setMA_TRAM(c.getString(7));
		entity.setBOCSO_ID(c.getString(8));
		entity.setLOAI_BCS(c.getString(9));
		entity.setLOAI_CS(c.getString(10));
		entity.setTEN_KHANG(c.getString(11));
		entity.setDIA_CHI(c.getString(12));
		entity.setMA_NN(c.getString(13));
		entity.setSO_HO(c.getString(14));
		entity.setMA_CTO(c.getString(15));
		entity.setSERY_CTO(c.getString(16));
		entity.setHSN(c.getString(17));
		entity.setCS_CU(c.getString(18));
		entity.setTTR_CU(c.getString(19));
		entity.setSL_CU(c.getString(20));
		entity.setSL_TTIEP(c.getString(21));
		entity.setNGAY_CU(c.getString(22));
		entity.setCS_MOI(c.getString(23));
		entity.setTTR_MOI(c.getString(24));
		entity.setSL_MOI(c.getString(25));
		entity.setCHUOI_GIA(c.getString(26));
		entity.setKY(c.getString(27));
		entity.setTHANG(c.getString(28));
		entity.setNAM(c.getString(29));
		entity.setNGAY_MOI(c.getString(30));
		entity.setNGUOI_GCS(c.getString(31));
		entity.setSL_THAO(c.getString(32));
		entity.setKIMUA_CSPK(c.getString(33));
		entity.setMA_COT(c.getString(34));
		entity.setCGPVTHD(c.getString(35));
		entity.setHTHUC_TBAO_DK(c.getString(36));
		entity.setDTHOAI_SMS(c.getString(37));
		entity.setEMAIL(c.getString(38));
		entity.setTHOI_GIAN(c.getString(39));
		entity.setX(c.getDouble(40));
		entity.setY(c.getDouble(41));
		entity.setSO_TIEN(c.getString(42));
		entity.setHTHUC_TBAO_TH(c.getString(43));
		entity.setTENKHANG_RUTGON(c.getString(44));
		entity.setTTHAI_DBO(c.getString(45));
		entity.setDU_PHONG(c.getString(46));
		entity.setTEN_FILE(c.getString(47));
		entity.setGHICHU(c.getString(48));
		entity.setTT_KHAC(c.getString(49));
		entity.setID_SQLITE(c.getString(50));
		entity.setSLUONG_1(c.getString(51));
		entity.setSLUONG_2(c.getString(52));
		entity.setSLUONG_3(c.getString(53));
		entity.setSO_HOM(c.getString(54));
		try{
			entity.setHINH_ANH(c.getBlob(56));
		} catch(Exception ex) {
			entity.setHINH_ANH(null);
		}
		try{
			entity.setPMAX(c.getString(57));
			entity.setNGAY_PMAX(c.getString(58));
		} catch(Exception ex) {
			ex.toString();
		}
		return entity;
	}
	
	public EntityGCS getDataByMAGC_old(String ID_SQLITE){
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " where ID_SQLITE = '" + ID_SQLITE + "'";
		Cursor c = database.rawQuery(strQuery, null);
		if(c != null){ 
			c.moveToFirst();
		}
		
		
		EntityGCS entity = new EntityGCS();
		if(c.getString(0) == null){
			entity.set_ID(-1);
		}else{
			entity.set_ID(Integer.parseInt(c.getString(0)));
		}
		
		entity.setMA_NVGCS(c.getString(1));
		entity.setMA_KHANG(c.getString(2));
		entity.setMA_DDO(c.getString(3));
		entity.setMA_DVIQLY(c.getString(4));
		entity.setMA_GC(c.getString(5));
		entity.setMA_QUYEN(c.getString(6));
		entity.setMA_TRAM(c.getString(7));
		entity.setBOCSO_ID(c.getString(8));
		entity.setLOAI_BCS(c.getString(9));
		entity.setLOAI_CS(c.getString(10));
		entity.setTEN_KHANG(c.getString(11));
		entity.setDIA_CHI(c.getString(12));
		entity.setMA_NN(c.getString(13));
		entity.setSO_HO(c.getString(14));
		entity.setMA_CTO(c.getString(15));
		entity.setSERY_CTO(c.getString(16));
		entity.setHSN(c.getString(17));
		entity.setCS_CU(c.getString(18));
		entity.setTTR_CU(c.getString(19));
		entity.setSL_CU(c.getString(20));
		entity.setSL_TTIEP(c.getString(21));
		entity.setNGAY_CU(c.getString(22));
		entity.setCS_MOI(c.getString(23));
		entity.setTTR_MOI(c.getString(24));
		entity.setSL_MOI(c.getString(25));
		entity.setCHUOI_GIA(c.getString(26));
		entity.setKY(c.getString(27));
		entity.setTHANG(c.getString(28));
		entity.setNAM(c.getString(29));
		entity.setNGAY_MOI(c.getString(30));
		entity.setNGUOI_GCS(c.getString(31));
		entity.setSL_THAO(c.getString(32));
		entity.setKIMUA_CSPK(c.getString(33));
		entity.setMA_COT(c.getString(34));
		entity.setCGPVTHD(c.getString(35));
		entity.setHTHUC_TBAO_DK(c.getString(36));
		entity.setDTHOAI_SMS(c.getString(37));
		entity.setEMAIL(c.getString(38));
		entity.setTHOI_GIAN(c.getString(39));
		entity.setX(c.getDouble(40));
		entity.setY(c.getDouble(41));
		entity.setSO_TIEN(c.getString(42));
		entity.setHTHUC_TBAO_TH(c.getString(43));
		entity.setTENKHANG_RUTGON(c.getString(44));
		entity.setTTHAI_DBO(c.getString(45));
		entity.setDU_PHONG(c.getString(46));
		entity.setTEN_FILE(c.getString(47));
		entity.setGHICHU(c.getString(48));
		entity.setTT_KHAC(c.getString(49));
		entity.setID_SQLITE(c.getString(50));
		entity.setSLUONG_1(c.getString(51));
		entity.setSLUONG_2(c.getString(52));
		entity.setSLUONG_3(c.getString(53));
		entity.setSO_HOM(c.getString(54));
		return entity;
	}
	
	public Cursor getAllData() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO;
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllData(String TableName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO;
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getPmax(String ID_SQLITE) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT PMAX, NGAY_PMAX FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = '" + ID_SQLITE + "'";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getAllDataMaTram() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT DISTINCT MA_TRAM FROM " + TABLE_NAME_CHISO;
		return database.rawQuery(strQuery, null);
	}

	public EntityGCS getDataGCS(int ID) {
		database = this.getReadableDatabase();
		Cursor c = database.query(TABLE_NAME_CHISO, new String[] { "ID",
				"MA_NVGCS", "MA_KHANG", "MA_DDO", "MA_DVIQLY", "MA_GC",
				"MA_QUYEN", "MA_TRAM", "BOCSO_ID", "LOAI_BCS", "LOAI_CS",
				"TEN_KHANG", "DIA_CHI", "MA_NN", "SO_HO", "MA_CTO", "SERY_CTO",
				"HSN", "CS_CU", "TTR_CU", "SL_CU", "SL_TTIEP", "NGAY_CU",
				"CS_MOI", "TTR_MOI", "SL_MOI", "CHUOI_GIA", "KY", "THANG",
				"NAM", "NGAY_MOI", "NGUOI_GCS", "SL_THAO", "KIMUA_CSPK",
				"MA_COT", "CGPVTHD", "HTHUC_TBAO_DK", "DTHOAI_SMS", "EMAIL",
				"THOI_GIAN", "X", "Y", "SO_TIEN", "HTHUC_TBAO_TH",
				"TENKHANG_RUTGON", "TTHAI_DBO", "DU_PHONG", "TEN_FILE",
				"GHICHU", "TT_KHAC", "ID_SQLITE" ,
				"SLUONG_1","SLUONG_2","SLUONG_3","SO_HOM","CHU_KY","HINH_ANH","PMAX","NGAY_PMAX"}, "ID=?", new String[] { "" + ID }, null, null,
				null, null);
		if (c != null)
			c.moveToFirst();
		EntityGCS e = new EntityGCS(Integer.parseInt(c.getString(0)), c.getString(1),
				c.getString(2), c.getString(3), c.getString(4), c.getString(5),
				c.getString(6), c.getString(7), c.getString(8), c.getString(9),
				c.getString(10), c.getString(11), c.getString(12),
				c.getString(13), c.getString(14), c.getString(15),
				c.getString(16), c.getString(17), c.getString(18),
				c.getString(19), c.getString(20), c.getString(21),
				c.getString(22), c.getString(23), c.getString(24),
				c.getString(25), c.getString(26), c.getString(27),
				c.getString(28), c.getString(29), c.getString(30),
				c.getString(31), c.getString(32), c.getString(33),
				c.getString(34), c.getString(35), c.getString(36),
				c.getString(37), c.getString(38), c.getString(39),
				c.getDouble(40), c.getDouble(41), c.getString(42),
				c.getString(43), c.getString(44), c.getString(45),
				c.getString(46), c.getString(47), c.getString(48),
				c.getString(49), c.getString(50), c.getString(51),
				c.getString(52), c.getString(53), c.getString(54),
				c.getBlob(55), c.getBlob(56), c.getString(57), c.getString(58));
		return e;
	}

	public int checkDataGCS(String fileName) {
		try {
			database = this.getReadableDatabase();
			String strQuery = "SELECT COUNT(*) FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN = '" + fileName + "'";
			Cursor c = database.rawQuery(strQuery, null);
			int count = 0;
			if (c.moveToFirst()) {
				do {
					count = c.getInt(0);
				} while (c.moveToNext());
			}
			return count;
		} catch (Exception ex) {
			return 1;
		}
	}
	
	public Cursor getListID(String fileName){
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN = '" + fileName + "'";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getListIDSort(String fileName){
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " a, " + TABLE_NAME_ROUTE + " b WHERE a.SERY_CTO = b.SERY_CTO AND a.LOAI_BCS = b.LOAI_BCS AND a.MA_QUYEN = '" + fileName + "' ORDER BY STT_ROUTE";
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getListIDFilt(String so, String tieu_chi, String gia_tri){
		database = this.getReadableDatabase();
		String strQuery = "";
		if(!gia_tri.equals("") && !tieu_chi.equals("CHUA_GHI")){
			strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + so + "'";
		} else {
			strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + so + "' AND " + tieu_chi + " like '%" + gia_tri + "%'";
		}
		return database.rawQuery(strQuery, null);
	}
	
	public List<String> getList(String so){
		List<String> lstID = new ArrayList<String>();
		database = this.getReadableDatabase();
		String strQuery = "SELECT ID_SQLITE FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + so + "'";
		Cursor c = database.rawQuery(strQuery, null);
		if (c.moveToFirst()) {
			do{
				lstID.add(c.getString(0));
			} while(c.moveToNext());
		}
		return lstID;
	}
	
	public LinkedHashMap<String, String> getDataGCStoMap(int ID_SQLITE){
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CHISO + " WHERE ID_SQLITE = " + ID_SQLITE;
		Cursor c = database.rawQuery(strQuery, null);
		if(c.moveToFirst()){
			map.put("CS_CU", c.getString(18));
			map.put("HSN", c.getString(17));
			map.put("CS_MOI", c.getString(23));
			map.put("SL_CU", c.getString(20));
			map.put("SL_THAO", c.getString(32));
		}
		return map;
	}
	
	public int getTinhTrang(String so){
		try {
			database = this.getReadableDatabase();
			String strQuery = "SELECT COUNT(*) FROM " + TABLE_NAME_CHISO + " WHERE TEN_FILE = '" + so + "' AND cast(CS_MOI as REAL) <> '0' OR TTR_MOI is not null";
			Cursor c = database.rawQuery(strQuery, null);
			int count = 0;
			if (c.moveToFirst()) {
				do {
					count = c.getInt(0);
				} while (c.moveToNext());
			}
			return count;
		} catch (Exception ex) {
			return -1;
		}
	}
	
	public int getDaGhi(String so){
		try {
			database = this.getReadableDatabase();
			String strQuery = "SELECT CS_MOI, TTR_MOI FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN = '" + so + "'";
			Cursor c = database.rawQuery(strQuery, null);
			int count = 0;
			if (c.moveToFirst()) {
				do {
					if(comm.isSavedRow(c.getString(1), c.getString(0))){
						count++;
					}
				} while (c.moveToNext());
			}
			return count;
		} catch(Exception ex){
			return -1;
		}
	}
	
	public int countDataGCS(String so) {
		try {
			database = this.getReadableDatabase();
			String strQuery = "SELECT COUNT(*) FROM " + TABLE_NAME_CHISO + " WHERE MA_QUYEN = '" + so + "'";
			Cursor c = database.rawQuery(strQuery, null);
			int count = 0;
			if (c.moveToFirst()) {
				do {
					count = c.getInt(0);
				} while (c.moveToNext());
			}
			return count;
		} catch (Exception ex) {
			return 1;
		}
	}
	
	// Xử lý bảng chỉ số
	public long insertDataIndex(String so, int index) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("so", so);
		values.put("vitri", index);
		return database.insert(TABLE_NAME_INDEX, null, values);
	}

	public long updateDataIndex(String so, int index) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("vitri", index);
		return database.update(TABLE_NAME_INDEX, values, "so=?",
				new String[] { so.trim() });
	}

	public long clearDataIndex() {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("vitri", 0);
		return database.update(TABLE_NAME_INDEX, values, null, null);
	}

	public long deleteAllDataIndex() {
		database = this.getWritableDatabase();
		return database.delete(TABLE_NAME_INDEX, null, null);
	}

	public Cursor getAllDataIndex() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_INDEX;
		return database.rawQuery(strQuery, null);
	}

	public EntityIndex getDataIndex(String so) {
		database = this.getReadableDatabase();
		Cursor c = database.query(TABLE_NAME_INDEX, new String[] { "so", "vitri" },
				"so=?", new String[] { so.trim() }, null, null, null, null);
		EntityIndex e = new EntityIndex();
		if (c.moveToFirst())
			e = new EntityIndex(c.getString(0), Integer.parseInt(c.getString(1)));
		return e;
	}

	public int checkDataIndex(String so) {
		try {
			database = this.getReadableDatabase();
			String strQuery = "SELECT COUNT(*) FROM " + TABLE_NAME_INDEX
					+ " WHERE so = '" + so.trim() + "'";
			Cursor c = database.rawQuery(strQuery, null);
			int count = 0;
			if (c.moveToFirst()) {
				do {
					count = c.getInt(0);
				} while (c.moveToNext());
			}
			return count;
		} catch (Exception ex) {
			return 1;
		}
	}
	
	// Xử lý bảng thêm kh mới
	public long insertDataCustomer(String noi_dung) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("noi_dung", noi_dung);
		return database.insert(TABLE_NAME_CUSTOMER, null, values);
	}
	
	public long updateDataCustomer(int id, String noi_dung) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("noi_dung", noi_dung);
		return database.update(TABLE_NAME_CUSTOMER, values, "id=?", new String[] { "" + id });
	}
	
	public long deleteAllDataCustomer() {
		database = this.getWritableDatabase();
		return database.delete(TABLE_NAME_CUSTOMER, null, null);
	}
	
	public long deleteDataCustomerByID(int ID) {
		database = this.getWritableDatabase();
		return database.delete(TABLE_NAME_CUSTOMER, "id=?", new String[] { "" + ID });
	}
	
	public Cursor getAllDataCustomer() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CUSTOMER;
		return database.rawQuery(strQuery, null);
	}
	
	public ArrayList<LinkedHashMap<String, String>> getListCustomer(){
		ArrayList<LinkedHashMap<String, String>> lstCus = new ArrayList<LinkedHashMap<String, String>>();
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_CUSTOMER;
		Cursor c = database.rawQuery(strQuery, null);
		if (c.moveToFirst()) {
			do{
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				map.put("ID", c.getString(0));
				map.put("NOI_DUNG", c.getString(1));
				lstCus.add(map);
			} while(c.moveToNext());
		}
		return lstCus;
	}
	
	// Xử lý bảng lộ trình
	public long insertLoTrinh(String SERY_CTO, int STT_ORG, int STT_ROUTE, String LOAI_BCS, String TEN_FILE){
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("SERY_CTO", SERY_CTO);
		values.put("STT_ORG", STT_ORG);
		values.put("STT_ROUTE", STT_ROUTE);
		values.put("LOAI_BCS", LOAI_BCS);
		values.put("TEN_FILE", TEN_FILE);
		return database.insert(TABLE_NAME_ROUTE, null, values);
	}
	
	public long updateLoTrinh(int ID_ROUTE, String SERY_CTO, int STT_ORG, int STT_ROUTE, String LOAI_BCS, String TEN_FILE){
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("SERY_CTO", SERY_CTO);
		values.put("STT_ORG", STT_ORG);
		values.put("STT_ROUTE", STT_ROUTE);
		values.put("LOAI_BCS", LOAI_BCS);
		values.put("TEN_FILE", TEN_FILE);
		return database.update(TABLE_NAME_ROUTE, values, "ID_ROUTE=?", new String[] { "" + ID_ROUTE });
	}
	
	public long deleteDataRoute(String TEN_FILE) {
		database = this.getWritableDatabase();
		return database.delete(TABLE_NAME_ROUTE, "TEN_FILE=?", new String[] { TEN_FILE });
	}
	
	public long deleteAllDataLoTrinh() {
		database = this.getWritableDatabase();
		return database.delete(TABLE_NAME_ROUTE, null, null);
	}
	
	public Cursor getAllDataRoute() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_ROUTE;
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getFileDataRoute() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT DISTINCT(TEN_FILE) FROM " + TABLE_NAME_ROUTE;
		return database.rawQuery(strQuery, null);
	}
	
	public EntityRoute getDataRoute(String so) {
		database = this.getReadableDatabase();
		Cursor c = database.query(TABLE_NAME_INDEX, new String[] { "ID_ROUTE", "SERY_CTO", "STT_ORG", "STT_ROUTE", "LOAI_BCS", "TEN_FILE" },
				"TEN_FILE=?", new String[] { so.trim() }, null, null, null, null);
		if (c != null)
			c.moveToFirst();
		EntityRoute e = new EntityRoute(Integer.parseInt(c.getString(0)), Integer.parseInt(c.getString(1)), Integer.parseInt(c.getString(2)), c.getString(3), c.getString(4));
		return e;
	}
	
	public boolean checkDataExist(String fileName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_ROUTE + " WHERE TEN_FILE = '" + fileName + "'";
		Cursor c = database.rawQuery(strQuery, null);
		return c.moveToFirst();
	}
	
	public int getIDDataRoute(String fileName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT ID_ROUTE FROM " + TABLE_NAME_ROUTE + " WHERE TEN_FILE = '" + fileName + "'";
		Cursor c = database.rawQuery(strQuery, null);
		int id = 0;
		if(c.moveToFirst()){
			id = Integer.parseInt(c.getString(0));
		}
		return id;
	}
	
	public Cursor getDataRouteByFileName(String fileName) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_ROUTE + " WHERE TEN_FILE = '" + fileName + "'";
		Cursor c = database.rawQuery(strQuery, null);
		return c;
	}
	
	// Xử lý bảng ghi log xóa
	public long insertLogXoa(String MA_QUYEN, String SERY_CTO, String CS_XOA, String NGAY_XOA, String LY_DO){
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("MA_QUYEN", MA_QUYEN);
		values.put("SERY_CTO", SERY_CTO);
		values.put("CS_XOA", CS_XOA);
		values.put("NGAY_XOA", NGAY_XOA);
		values.put("LY_DO", LY_DO);
		return database.insert(TABLE_NAME_LOG_DELETE, null, values);
	}
	
	public long updateLogXoa(int ID, String MA_QUYEN, String SERY_CTO, String CS_XOA, String NGAY_XOA, String LY_DO){
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("MA_QUYEN", MA_QUYEN);
		values.put("SERY_CTO", SERY_CTO);
		values.put("CS_XOA", CS_XOA);
		values.put("NGAY_XOA", NGAY_XOA);
		values.put("LY_DO", LY_DO);
		return database.update(TABLE_NAME_LOG_DELETE, values, "ID=?", new String[] { "" + ID });
	}
	
	public long deleteAllDataLog() {
		database = this.getWritableDatabase();
		return database.delete(TABLE_NAME_LOG_DELETE, null, null);
	}
	
	public Cursor getAllDataLog() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_LOG_DELETE;
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getDataLogByMaQuyen(String MA_QUYEN) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_LOG_DELETE + " WHERE MA_QUYEN = '" + MA_QUYEN + "'";
		return database.rawQuery(strQuery, null);
	}
	
	public EntityLogDelete getDataLogByID(int ID) {
		database = this.getReadableDatabase();
		Cursor c = database.query(TABLE_NAME_LOG_DELETE, new String[] { "ID", "MA_QUYEN", "SERY_CTO", "CS_XOA", "NGAY_XOA", "LY_DO" },
				"ID=?", new String[] { ID + "" }, null, null, null, null);
		if (c != null)
			c.moveToFirst();
		EntityLogDelete e = new EntityLogDelete(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
		return e;
	}
	
	// Xử lý bảng ghi log chỉ số cho Bắc Ninh
	public long insertLog(String TEN_FILE, String MA_QUYEN, String MA_CTO, String SERY_CTO, String LOAI_BCS, String CHI_SO, String NGAY_SUA, int LOAI){
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("TEN_FILE", TEN_FILE);
		values.put("MA_QUYEN", MA_QUYEN);
		values.put("MA_CTO", MA_CTO);
		values.put("SERY_CTO", SERY_CTO);
		values.put("LOAI_BCS", LOAI_BCS);
		values.put("CHI_SO", CHI_SO);
		values.put("NGAY_SUA", NGAY_SUA);
		values.put("LOAI", LOAI);
		return database.insert(TABLE_NAME_LOG_CS, null, values);
	}
	
	public long updateLog(int ID, String TEN_FILE, String MA_QUYEN, String MA_CTO, String SERY_CTO, String LOAI_BCS, String CHI_SO, String NGAY_SUA, int LOAI){
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("TEN_FILE", TEN_FILE);
		values.put("MA_QUYEN", MA_QUYEN);
		values.put("MA_CTO", MA_CTO);
		values.put("SERY_CTO", SERY_CTO);
		values.put("LOAI_BCS", LOAI_BCS);
		values.put("CHI_SO", CHI_SO);
		values.put("NGAY_SUA", NGAY_SUA);
		values.put("LOAI", LOAI);
		return database.update(TABLE_NAME_LOG_CS, values, "ID=?", new String[] { "" + ID });
	}
	
	public long deleteAllDataLogCS() {
		database = this.getWritableDatabase();
		return database.delete(TABLE_NAME_LOG_CS, null, null);
	}
	
	public Cursor getAllDataLogCS() {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_LOG_CS;
		return database.rawQuery(strQuery, null);
	}
	
	public Cursor getDataLogCSByMaQuyen(String MA_QUYEN) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + TABLE_NAME_LOG_CS + " WHERE MA_QUYEN = '" + MA_QUYEN + "'";
		return database.rawQuery(strQuery, null);
	}
	
	public int countDataLogCS(String MA_QUYEN, String MA_CTO, String LOAI_BCS) {
		database = this.getReadableDatabase();
		String strQuery = "SELECT COUNT(*) FROM " + TABLE_NAME_LOG_CS + " WHERE MA_QUYEN = '" + MA_QUYEN + "' AND MA_CTO = '" + MA_CTO + "' AND LOAI_BCS = '" + LOAI_BCS + "'";
		Cursor c = database.rawQuery(strQuery, null);
		if(c.moveToFirst()){
			return c.getInt(0);
		}
		return 0;
	}
	
	public EntityLog getDataLogCSByID(int ID) {
		database = this.getReadableDatabase();
		Cursor c = database.query(TABLE_NAME_LOG_CS, new String[] { "ID", "TEN_FILE", "MA_QUYEN", "MA_CTO", "SERY_CTO", "LOAI_BCS", "CHI_SO", "NGAY_SUA", "LOAI" },
				"ID=?", new String[] { ID + "" }, null, null, null, null);
		if (c != null)
			c.moveToFirst();
		EntityLog e = new EntityLog(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getInt(8));
		return e;
	}
	
	// business

	/**
	 * Delete nhiều sổ khỏi db
	 * @param arr_ten_so
	 * @return
	 */
	public long delete_array_so(String[] arr_ten_so ) {
		int result = 0;
		String list_so = "";
		for(String item : arr_ten_so){
			list_so += ",'"+item+"'"; 
		}
		list_so = list_so.trim().substring(1);
		String where = null;
		String[] whereArgs = null;//{list_so}
		
		database = this.getWritableDatabase();
		//
		where = "TEN_FILE IN ("+list_so+")";
		result = database.delete(TABLE_NAME_CHISO, where, whereArgs);
		
		where = "TEN_SOGCS IN ("+list_so+")";
		result = database.delete(TABLE_NAME_SO, where, whereArgs);
		
		where = "so IN ("+list_so+")";
		result = database.delete(TABLE_NAME_INDEX, where, whereArgs);
		
		return result;
	}
	
	/**
	 * Insert tất cả dữ liệu (trong 3 table GCS_CHISO_HHU, GCS_SO_NVGCS, gcsindex) db source vào db destination
	 */
	public String insert_arraylist_to_db(String[] arr_ten_so, ArrayList<LinkedHashMap<String, String>> list, String MA_DQL ) {
		try{
			@SuppressWarnings("unused")
			long result = -1;
			if(database == null || !database.isOpen()){
				return "Chưa kết nối tới cơ sở dữ liệu.";
			}
			
			if(list.size() == 0){
				return "HAVE_NO_DATA";
			}
			
			ContentValues values = new ContentValues();
			
			// chèn từng công tơ của sổ vào bảng GCS_CHISO_HHU
			database = this.getWritableDatabase();
			for(LinkedHashMap<String, String> list_item : list){
				values = new ContentValues();
				for(int i = 1; i < ConstantVariables.COLARR.length; i++){
					String colname = ConstantVariables.COLARR[i];
//				for(String colname : Common.colArr2){
					//bỏ qua cột tự tăng ID_SQLITE
					if(colname.contains("ID_SQLITE")){
						continue;
					}
					values.put(colname, list_item.get(colname));
				}
				String CHECK_DSOAT = list_item.get("STR_CHECK_DSOAT");
				if(CHECK_DSOAT == null || CHECK_DSOAT.equals("")){
					CHECK_DSOAT = "CHUA_DOI_SOAT";
				}
				values.put("STR_CHECK_DSOAT", CHECK_DSOAT);
				result = database.insert(TABLE_NAME_CHISO, null, values);
			}
			
			for(String TEN_FILE : arr_ten_so){
				// chèn vào bảng GCS_SO_NVGCS
				values = new ContentValues();
				values.put("MA_DVIQLY", list.get(0).get("MA_DVIQLY"));
				values.put("MA_NVGCS", list.get(0).get("MA_NVGCS"));
				values.put("TEN_SOGCS", TEN_FILE);
				values.put("MA_DQL", MA_DQL);
				result= database.insert(TABLE_NAME_SO, null, values);
				
				// chèn vào bảng gcsindex
				values = new ContentValues();
				values.put("so", TEN_FILE);
				values.put("vitri", 0);
				result=  database.insert(TABLE_NAME_INDEX, null, values);
			}
			
			return null;
		}
		catch(Exception e){
			return e.getMessage();
		}
	} 
	
	/**
	 * Chuyển dữ liệu từ sqlite -> ArrayList. Chỉ dùng để lấy dữ liệu từ bảng GCS_CHISO_HHU
	 * @param c
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, String>> ConvertDataFromSqliteToArrayList(String strQuery){
		database = this.getReadableDatabase();
		Cursor c = database.rawQuery(strQuery, null);
		ArrayList<LinkedHashMap<String, String>> ListSqliteData = new ArrayList<LinkedHashMap<String, String>>();
		if (c.moveToFirst()) {
			do {
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				for (int j = 1; j < ConstantVariables.COLARR.length; j++) {
					map.put(ConstantVariables.COLARR[j], c.getString(j));
				}
				String CHECK_DSOAT = c.getString(c.getColumnIndex("STR_CHECK_DSOAT"));
				if(CHECK_DSOAT == null || CHECK_DSOAT.equals("")){
					CHECK_DSOAT = "CHUA_DOI_SOAT";
				}
				map.put("STR_CHECK_DSOAT", CHECK_DSOAT);
				ListSqliteData.add(map);
			} while (c.moveToNext());
		}
		return ListSqliteData;
	}
	
	/**
	 * Lấy danh sách sổ trên db
	 * @param connection
	 * @return
	 */
	public List<String> GetAllSo(){
		List<String> list = new ArrayList<String>();
		try{
			Cursor c = getAllDataSo();
			if(c != null ){
				if (c.moveToFirst()) {
					do {
						String fileName = c.getString(3); 
						list.add(fileName);
					} while (c.moveToNext());
				}
			}
		}catch(Exception e){
			list = new ArrayList<String>();
		}
		return list;
	}
	
}