package esolutions.com.gcs_svn_old.com.camera.entity;

public class EntitySo {
	
	private int _ID;
	private String MA_DVIQLY;
	private String MA_NVGCS;
	private String TEN_SOGCS;
	private String MA_DQL;
	
	public int get_ID() {
		return _ID;
	}

	public void set_ID(int _ID) {
		this._ID = _ID;
	}

	public String getMA_DVIQLY() {
		return MA_DVIQLY;
	}

	public void setMA_DVIQLY(String mA_DVIQLY) {
		MA_DVIQLY = mA_DVIQLY;
	}

	public String getMA_NVGCS() {
		return MA_NVGCS;
	}

	public void setMA_NVGCS(String mA_NVGCS) {
		MA_NVGCS = mA_NVGCS;
	}

	public String getTEN_SOGCS() {
		return TEN_SOGCS;
	}

	public void setTEN_SOGCS(String tEN_SOGCS) {
		TEN_SOGCS = tEN_SOGCS;
	}

	public String getMA_DQL() {
		return MA_DQL;
	}

	public void setMA_DQL(String mA_DQL) {
		MA_DQL = mA_DQL;
	}

	public EntitySo(){
		
	}
	
	public EntitySo(int _ID, String MA_DVIQLY, String MA_NVGCS, String TEN_SOGCS, String MA_DQL){
		this._ID = _ID;
		this.MA_DVIQLY = MA_DVIQLY;
		this.MA_NVGCS = MA_NVGCS;
		this.TEN_SOGCS = TEN_SOGCS;
		this.MA_DQL = MA_DQL;
	}
}
