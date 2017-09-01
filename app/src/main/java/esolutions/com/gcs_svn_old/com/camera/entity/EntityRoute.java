package esolutions.com.gcs_svn_old.com.camera.entity;

public class EntityRoute {

	private int ID_ROUTE;
	private int STT_ORG;
	private int STT_ROUTE;
	private String LOAI_BCS;
	private String TEN_FILE;
	public int getID_ROUTE() {
		return ID_ROUTE;
	}
	public void setID_ROUTE(int iD_ROUTE) {
		ID_ROUTE = iD_ROUTE;
	}
	public int getSTT_ORG() {
		return STT_ORG;
	}
	public void setSTT_ORG(int sTT_ORG) {
		STT_ORG = sTT_ORG;
	}
	public int getSTT_ROUTE() {
		return STT_ROUTE;
	}
	public void setSTT_ROUTE(int sTT_ROUTE) {
		STT_ROUTE = sTT_ROUTE;
	}
	public String getLOAI_BCS() {
		return LOAI_BCS;
	}
	public void setLOAI_BCS(String lOAI_BCS) {
		LOAI_BCS = lOAI_BCS;
	}
	public String getTEN_FILE() {
		return TEN_FILE;
	}
	public void setTEN_FILE(String tEN_FILE) {
		TEN_FILE = tEN_FILE;
	}
	
	public EntityRoute(){
		
	}
	
	public EntityRoute(int STT_ORG, int STT_ROUTE, String LOAI_BCS, String TEN_FILE){
		this.STT_ORG = STT_ORG;
		this.STT_ROUTE = STT_ROUTE;
		this.LOAI_BCS = LOAI_BCS;
		this.TEN_FILE = TEN_FILE;
	}
	
	public EntityRoute(int ID_ROUTE, int STT_ORG, int STT_ROUTE, String LOAI_BCS, String TEN_FILE){
		this.ID_ROUTE = ID_ROUTE;
		this.STT_ORG = STT_ORG;
		this.STT_ROUTE = STT_ROUTE;
		this.LOAI_BCS = LOAI_BCS;
		this.TEN_FILE = TEN_FILE;
	}
}
