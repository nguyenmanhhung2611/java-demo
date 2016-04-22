package ctcav.com.demo.scope;

//@Service
//@Scope("prototype")
public class CustomerService {
	
	private String message;
	
	public CustomerService() {
		
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return this.message;
	}
	
	
}
