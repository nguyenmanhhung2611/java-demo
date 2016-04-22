package tctav.com.demo.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FileNameGenerator {
	
	private String name;
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		FileNameGenerator fileNameGenerator1 = (FileNameGenerator) context.getBean("FileNameGenerator1");
		FileNameGenerator fileNameGenerator2 = (FileNameGenerator) context.getBean("FileNameGenerator2");
		FileNameGenerator fileNameGenerator3 = (FileNameGenerator) context.getBean("FileNameGenerator3");
		
		System.out.println("1. " + fileNameGenerator1.getName() + " || " + fileNameGenerator1.getType());
		System.out.println("2. " + fileNameGenerator2.getName() + " || " + fileNameGenerator2.getType());
		System.out.println("3. " + fileNameGenerator3.getName() + " || " + fileNameGenerator3.getType());
	}
	
}
