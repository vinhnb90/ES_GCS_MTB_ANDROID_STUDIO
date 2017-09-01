package esolutions.com.gcs_svn_old.com.camera.entity;

public class EntityLog {

	private int ID;
	private String TEN_FILE;
	private String MA_QUYEN;
	private String MA_CTO;
	private String SERY_CTO;
	private String LOAI_BCS;
	private String CHI_SO;
	private String NGAY_SUA;
	private int LOAI;
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTEN_FILE() {
		return TEN_FILE;
	}

	public void setTEN_FILE(String tEN_FILE) {
		TEN_FILE = tEN_FILE;
	}

	public String getMA_QUYEN() {
		return MA_QUYEN;
	}

	public void setMA_QUYEN(String mA_QUYEN) {
		MA_QUYEN = mA_QUYEN;
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

	public String getLOAI_BCS() {
		return LOAI_BCS;
	}

	public void setLOAI_BCS(String lOAI_BCS) {
		LOAI_BCS = lOAI_BCS;
	}

	public String getCHI_SO() {
		return CHI_SO;
	}

	public void setCHI_SO(String cHI_SO) {
		CHI_SO = cHI_SO;
	}

	public String getNGAY_SUA() {
		return NGAY_SUA;
	}

	public void setNGAY_SUA(String nGAY_SUA) {
		NGAY_SUA = nGAY_SUA;
	}

	public int getLOAI() {
		return LOAI;
	}

	public void setLOAI(int lOAI) {
		LOAI = lOAI;
	}

	public EntityLog(){
		
	}
	
	public EntityLog(int ID, String TEN_FILE, String MA_QUYEN, String MA_CTO, String SERY_CTO, String LOAI_BCS, String CHI_SO, String NGAY_SUA, int LOAI){
		this.TEN_FILE = TEN_FILE;
		this.MA_QUYEN = MA_QUYEN;
		this.MA_CTO = MA_CTO;
		this.SERY_CTO = SERY_CTO;
		this.LOAI_BCS = LOAI_BCS;
		this.CHI_SO = CHI_SO;
		this.NGAY_SUA = NGAY_SUA;
		this.LOAI = LOAI;
	}
	
}
