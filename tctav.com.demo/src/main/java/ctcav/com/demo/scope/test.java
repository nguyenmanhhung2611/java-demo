package ctcav.com.demo.scope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
	public static void main(String[] args) {
		//ApplicationContext context = new AnnotationConfigApplicationContext(CustomerService.class);
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		
		CustomerService customer1 = (CustomerService) context.getBean("customerService");
		customer1.setMessage("customer 1");
		System.out.println(customer1.toString());
		
		CustomerService customer2 = (CustomerService) context.getBean("customerService");
		System.out.println(customer2.toString());
		
		/*System.out.println("=========");
		Object a = new Object();
		CustomerService aaa = new CustomerService();
		System.out.println(a.toString()+ " || " + aaa.toString()+ " || " + a.hashCode() + " || " + aaa.hashCode() + " || " + customer1.hashCode());
		Object a = new Object();
		System.out.println(a.hashCode());
		a = new Object();
		System.out.println(a.hashCode());*/
	}
}
