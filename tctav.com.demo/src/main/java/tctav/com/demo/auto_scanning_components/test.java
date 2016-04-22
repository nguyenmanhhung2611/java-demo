package tctav.com.demo.auto_scanning_components;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("auto_scanning_components.xml");
		CustomerService cusSer = (CustomerService) context.getBean("customerService");
		System.out.println(cusSer);
	}
}
