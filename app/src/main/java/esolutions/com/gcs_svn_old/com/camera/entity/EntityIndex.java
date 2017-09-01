package esolutions.com.gcs_svn_old.com.camera.entity;

public class EntityIndex {
	
	private int _id;
	private String so;
	private int index;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getSo() {
		return so;
	}
	public void setSo(String so) {
		this.so = so;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public EntityIndex(){
		
	}
	
	public EntityIndex(int index){
		this.index = index;
	}
	
	public EntityIndex(String so, int index){
		this.so = so;
		this.index = index;
	}
	
	public EntityIndex(int _id, String so, int index){
		this._id = _id;
		this.so = so;
		this.index = index;
	}
}
