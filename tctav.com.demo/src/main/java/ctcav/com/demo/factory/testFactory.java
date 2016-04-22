package ctcav.com.demo.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testFactory {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		
		// List
		CustomerFactory customList1 = (CustomerFactory) context.getBean("customerListFactoryBean1");
		CustomerFactory customList2 = (CustomerFactory) context.getBean("customerListFactoryBean2");
		System.out.println(customList1.toString());
		System.out.println(customList2.toString());
		
		// Set
		CustomerFactory customSet1 = (CustomerFactory) context.getBean("customerSetFactoryBean1");
		CustomerFactory customSet2 = (CustomerFactory) context.getBean("customerSetFactoryBean2");
		System.out.println(customSet1.toString());
		System.out.println(customSet2.toString());
		
		// Map
		CustomerFactory customMap1 = (CustomerFactory) context.getBean("customerMapFactoryBean1");
		CustomerFactory customMap2 = (CustomerFactory) context.getBean("customerMapFactoryBean2");
		System.out.println(customMap1.toString());
		System.out.println(customMap2.toString());
	}
}
