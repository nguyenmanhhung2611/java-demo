package main;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class Main {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		/* Một số method quan trọng trong Reflection liên quan tới Class.
		 * Ví dụ ghi ra các thông tin cơ bản của class như tên class, package, modifier, ..*/ 
		
		// Lấy ra đối tượng 'Class' mô tả class ShowClassInfo
		Class<Main> main = Main.class;
		
		// Ghi ra tên class + package name
		System.out.println(main.getName());
		
		// Lấy ra tên class
		System.out.println(main.getSimpleName());
		
		// Thông tin package
		Package p = main.getPackage();
		System.out.println(p.getName());
		
		// Modifier
		int modifiers = main.getModifiers();
		
		System.out.println("is public? " + Modifier.isPublic(modifiers));
		System.out.println("is interface? " + Modifier.isInterface(modifiers));
		System.out.println("is final? " + Modifier.isFinal(modifiers));
		System.out.println("is abstract? " + Modifier.isAbstract(modifiers));
		
		
		// =====
		System.out.println("===========");
		Class<Cat> catClass = Cat.class;
		
		// Lấy ra lớp cha cuả class cat
		Class<?> parrentCat = catClass.getSuperclass();
		System.out.println(parrentCat.getSimpleName());
		
		// Lấy ra mảng các class mô tả các interface mà class Cat thi hành
		Class<?>[] interfaceClasses = catClass.getInterfaces();
		for (Class<?> interfaceClass : interfaceClasses) {
			System.out.println("Interface: " + interfaceClass.getSimpleName());
		}
		
		// Lấy ra danh sách các cấu tử của class Cat
		System.out.println("======= Contructors =======");
		Constructor<?>[] constructors = catClass.getConstructors();
		for (Constructor<?> constructor : constructors) {
			System.out.println("Constructor: " + constructor.getName());
			
			// Lấy ra thông tin kiểu tham số của cấu tử
			Class<?>[] paramClasses = constructor.getParameterTypes();
			for (Class<?> paramClass : paramClasses) {
				System.out.println("Param: " + paramClass.getName());
			}
			System.out.println("=======");
		}
		
		// Lấy ra danh sách các method public của class Cat
		// Bao gồm cả các method thừa kế từ class cha và các interface
		System.out.println("======= Methods =======");
		Method[] methods = catClass.getMethods();
		for (Method method : methods) {
			System.out.println("Method: " + method.getName());
		}
		
		// Lấy ra danh sách các field public
		// Bao gồm cả các method thừa kế từ class cha và các interface
		System.out.println("======= Fields =======");
		Field[] fields = catClass.getFields();
		for (Field field : fields) {
			System.out.println("Field: " + field.getName());
		}
		
		// ======
		/* Lấy ra một  cấu tử với các tham số chỉ định trước. Và ghi ra thông tin về cấu tử (constructor) này.*/
		
		System.out.println("===========");
		// Lấy ra cấu tử có tham số String, int cỉa class Cat
		Constructor<?> constructor = catClass.getConstructor(String.class, int.class);
		
		// Lấy ra thông tin kiểu tham số của cấu tử
		Class<?>[] paramClasses = constructor.getParameterTypes();
		for (Class<?> paramClass : paramClasses) {
			System.out.println("Param: " + paramClass.getName());
		}
		
		// Khởi tao đối tượng Cat theo cách thông thường
		Cat tom = new Cat("Tom", 3);
		System.out.println("Cat 1: " + tom.getName() + ", age: " + tom.getAge());
		
		// Khởi tạo đối tượng dùng reflect
		Cat tom2 = (Cat) constructor.newInstance("Tom2", 2);
		System.out.println("Cat 2: " + tom2.getName() + ", age: " + tom2.getAge());
		
		// ======
		/*Lấy ra Field với tên chỉ định sẵn.*/
		
		System.out.println("===========");
		// Lấy ra field có tên 'NUMBER_OF_LEGS':
		Field field = catClass.getField("NUMBER_OF_LEGS");
		Class<?> fieldType = field.getType();
		System.out.println("Field type: " + fieldType.getSimpleName());
		
		Field ageField = catClass.getField("age");
		Cat tom3 = new Cat("Tom3", 5);
		
		// Lấy ra giá trị của trường age bằng reflect
		Integer age = (Integer) ageField.get(tom3);
		System.out.println("ageField: " + age);
		
		// Set giá trị mới
		ageField.set(tom3, 7);
		System.out.println("New age: " + tom3.getAge());
		
		// ======
		/* Lấy ra một method với tên, các tham số chỉ định trước. Ghi ra thông tin về method này, như kiểu trả về, danh sách các tham số,...*/
		System.out.println("============");
		
		// Lấy ra đối tượng method mô tả method getAge
		Method getAgeMethod = catClass.getMethod("getAge");
		
		// Kiểu trả về của method getAge
		Class<?> returnType = getAgeMethod.getReturnType();
		System.out.println("Return type of getAge: " + returnType.getSimpleName());
		
		// Gọi method getAge theo cách reflect = tom3.getAge()
		int age1 = (int) getAgeMethod.invoke(tom3);
		System.out.println("Age: " + age1);
		
		// Lất ra đối tượng mô tả method setAge(int) của class Cat
		Method setAgeMethod = catClass.getMethod("setAge", int.class);
		
		// Gọi setAge(int) theo cách reflect = setAge(5)
		setAgeMethod.invoke(tom3, 5);
		System.out.println("New age: " + tom3.getAge());
		
		// ======
		System.out.println("============");
		/* Liệt kê ra các public setter method, và các public getter method của class.*/
		// Lấy ra danh sách public method
		Method[] method1s = catClass.getMethods();
		for (Method method1 : method1s) {
			System.out.println("Method: " + method1.getName());
			System.out.println("is setter: " + isSetter(method1));
			System.out.println("is getter: " + isGetter(method1));
		}
		
		// ======
		/*Truy cập vào các private method, field*/
		System.out.println("============");
		
		// Class.getField(String) chỉ lấy được các trường public.
        // Sử dụng Class.getDeclaredField(String):
        // Lấy ra đối tượng Field mô tả trường name của class Cat.
        // (Trường khi báo trong class này).
		Field private_nameField = catClass.getDeclaredField("name");
		
		// Cho phép để truy cập vào các trường private.
        // Nếu không sẽ bị ngoại lệ IllegalAccessException
		private_nameField.setAccessible(true);
		
		Cat tom4 = new Cat("Tom4");
		
		String fieldvalue = (String) private_nameField.get(tom4);
		System.out.println("Value field name: " + fieldvalue);
		
		// Set name mới
		private_nameField.set(tom4, "Tom4 cat");
		System.out.println("New name: " + tom4.getName());
		
		// ======
		/*Truy cập vào private method*/
		System.out.println("============");
		
		// Class.getMethod(String) chỉ lấy được các method public.
        // Sử dụng Class.getDeclaredMethod(String):
        // Lấy ra đối tượng Method mô tả method setName(String) của class Cat.
        // (Phương thức khai báo trong class).
		Method private_setNameMethod = catClass.getDeclaredMethod("setName", String.class);
		
		// Cho phép để truy cập vào các method private.
        // Nếu không sẽ bị ngoại lệ IllegalAccessException
		private_setNameMethod.setAccessible(true);
		
		Cat tom5 = new Cat("tom5");
		
		// Gọi private method
		private_setNameMethod.invoke(tom5, "Tom5 cat");
		System.out.println("New name: "+ tom5.getName());
	}
	
	private static boolean isGetter(Method method) {
		if ( !method.getName().startsWith("get") ) return false;
		if ( method.getParameterTypes().length != 0 ) return false;
		if ( void.class.equals(method.getReturnType()) ) return false;
		return true;
	}
	
	private static boolean isSetter(Method method) {
		if ( !method.getName().startsWith("set") ) return false;
		if ( method.getParameterTypes().length != -1 ) return false;
		return true;
	}

}
