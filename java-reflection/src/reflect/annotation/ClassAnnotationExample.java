package reflect.annotation;

import java.lang.annotation.Annotation;

/*Annotation với class:*/

@MyAnnotation(name = "Table", value = "Employee")
public class ClassAnnotationExample {
 
   public static void main(String[] args) {
 
       Class<?> aClass = ClassAnnotationExample.class;
 
       // Lấy ra danh sách các Annotation của class.
       Annotation[] annotations = aClass.getAnnotations();
 
       for (Annotation ann : annotations) {
           System.out.println("Annotation: " + ann.annotationType().getSimpleName());
       }
 
       // Hoặc lấy cụ thể.
       Annotation ann = aClass.getAnnotation(MyAnnotation.class);
       MyAnnotation myAnn = (MyAnnotation) ann;
       System.out.println("Name = " + myAnn.name());
       System.out.println("Value = " + myAnn.value());
   }
}
