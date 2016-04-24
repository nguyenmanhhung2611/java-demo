package tctav.com.hibernate.model;

public class UserModel {
	private int id;
	private String name;
	private String passwork;

	public UserModel(int id) {
		this.id = id;
	}
	
	public UserModel(String name, String passwork) {
		this.name = name;
		this.passwork = passwork;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPasswork() {
		return passwork;
	}
	public void setPasswork(String passwork) {
		this.passwork = passwork;
	}
	
}
