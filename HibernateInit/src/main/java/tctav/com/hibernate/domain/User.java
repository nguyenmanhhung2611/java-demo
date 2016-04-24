package tctav.com.hibernate.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity  // NOTE: Hasn't import org.hibernate.annotations.Entity; It should be import javax.persistence.Entity
@Table(name = "user")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	
	@Column(name = "passwork")
	private String passwork;

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
