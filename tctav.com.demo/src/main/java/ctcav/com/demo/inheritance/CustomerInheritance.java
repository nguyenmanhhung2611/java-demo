package ctcav.com.demo.inheritance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerInheritance {
	
	private int type;
	private String action;
	private String country;

	// set, get properties
	public void initMethod() throws Exception {
		System.out.println("========== Init method ctcav.com.demo.inheritance.CustomerInheritance");
		System.out.println("======================================================================");
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return this.type + " || " + this.action + " || "  + this.country;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		CustomerInheritance cus = (CustomerInheritance) context.getBean("customerInheritance");
		System.out.println(cus.toString());
	}
}
