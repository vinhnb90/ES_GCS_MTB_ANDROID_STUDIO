package esolutions.com.gcs_svn_old.com.camera.entity;

public class EntityLogDelete {

	private int ID;
	private String MA_QUYEN;
	private String SERY_CTO;
	private String CS_XOA;
	private String NGAY_XOA;
	private String LY_DO;
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getMA_QUYEN() {
		return MA_QUYEN;
	}

	public void setMA_QUYEN(String mA_QUYEN) {
		MA_QUYEN = mA_QUYEN;
	}

	public String getSERY_CTO() {
		return SERY_CTO;
	}

	public void setSERY_CTO(String sERY_CTO) {
		SERY_CTO = sERY_CTO;
	}

	public String getCS_XOA() {
		return CS_XOA;
	}

	public void setCS_XOA(String cS_XOA) {
		CS_XOA = cS_XOA;
	}

	public String getNGAY_XOA() {
		return NGAY_XOA;
	}

	public void setNGAY_XOA(String nGAY_XOA) {
		NGAY_XOA = nGAY_XOA;
	}

	public String getLY_DO() {
		return LY_DO;
	}

	public void setLY_DO(String lY_DO) {
		LY_DO = lY_DO;
	}

	public EntityLogDelete(){
		
	}
	
	public EntityLogDelete(String MA_QUYEN, String SERY_CTO, String CS_XOA, String NGAY_XOA, String LY_DO){
		this.MA_QUYEN = MA_QUYEN;
		this.SERY_CTO = SERY_CTO;
		this.CS_XOA = CS_XOA;
		this.NGAY_XOA = NGAY_XOA;
		this.LY_DO = LY_DO;
	}
	
	public EntityLogDelete(int ID, String MA_QUYEN, String SERY_CTO, String CS_XOA, String NGAY_XOA, String LY_DO){
		this.ID = ID;
		this.MA_QUYEN = MA_QUYEN;
		this.SERY_CTO = SERY_CTO;
		this.CS_XOA = CS_XOA;
		this.NGAY_XOA = NGAY_XOA;
		this.LY_DO = LY_DO;
	}
}
