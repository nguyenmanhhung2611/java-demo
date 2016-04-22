package ctcav.com.demo.post_pre_contruct;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerPostPreContructService {
	private String message;
	
	// Set, get properties
	public String getMessage() {
	  return message;
	}

	public void setMessage(String message) {
	  this.message = message;
	}
	
	@PostConstruct
	public void initIt() throws Exception {
	  System.out.println("Init method after properties are set : " + message);
	}
	
	@PreDestroy
	public void cleanUp() throws Exception {
	  System.out.println("Spring Container is destroy! Customer clean up");
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		CustomerPostPreContructService cus = (CustomerPostPreContructService) context.getBean("customerPostPreContructService");
		System.out.println(cus.getMessage());
		cus.cleanUp();
	}
}
