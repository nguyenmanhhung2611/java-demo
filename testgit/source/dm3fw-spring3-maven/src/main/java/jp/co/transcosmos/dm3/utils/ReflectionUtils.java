/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Encapsulates some of the commonly use functions for doing reflection, so we can 
 * optimise (or cache results) later.
 * 
 * Note that this class was largely inspired by the Generator-runtime framework's
 * ReflectionUtils class, and while the implementations are quite different, it's 
 * clear that the source code is only copyrightable, not original as such.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: ReflectionUtils.java,v 1.7 2012/08/01 09:28:36 tanaka Exp $
 */
public class ReflectionUtils {
    
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    
    private static String buildGetterName(String name, boolean isBoolean) {
        if ((name == null) || name.equals("")) {
            return null;
        } else {
            return (isBoolean ? "is" : "get") + name.substring(0, 1).toUpperCase() + name.substring(1);
        }
    }
    
    public static String buildSetterName(String name) {
        if ((name == null) || name.equals("")) {
            return null;
        } else {
            return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        }
    }
    
    /**
     * This is where the default naming policy is defined. Override this to provide 
     * custom naming policies.
     * Changes abcDef -> abc_def, abcDEF -> abc_def, abcDEFGhi -> abc_def_ghi
     * 
     * Tries to simulate the logical conversion for java bean naming
     */
    public static String lmcToUnderscore(String fieldName) {
        // Pass 1 - convert consecutive upper case chars to first + last only upper case
        StringBuffer out = new StringBuffer();
        for (int n = 0; n < fieldName.length(); n++) {
            if (!Character.isUpperCase(fieldName.charAt(n))) {
                out.append(fieldName.charAt(n));
            } else if ((n < fieldName.length() - 1) && !Character.isUpperCase(fieldName.charAt(n + 1))) {
                out.append(fieldName.charAt(n));
            } else if ((n > 0) && !Character.isUpperCase(fieldName.charAt(n - 1))) {
                out.append(fieldName.charAt(n));
            } else {
                out.append(Character.toLowerCase(fieldName.charAt(n)));
            }
        }
        
        // Pass 2 - add an underscore between lower to upper transitions
        for (int n = 0; n < out.length() - 1; n++) {
            boolean isUpper = Character.isUpperCase(out.charAt(n));
            if (isUpper) {
                out.setCharAt(n, Character.toLowerCase(out.charAt(n)));
            } else if (Character.isUpperCase(out.charAt(n + 1))) {
                out.insert(n + 1, "_");
                n++;
            }
            
        }
        return out.toString();
    }
    
    /**
     * Convert underscores to be in LMC format
     */
    public static String underscoreToLmc(String underscoreForm) {
        int underscorePos = underscoreForm.indexOf('_');
        if (underscorePos == -1) {
            return underscoreForm;
        } else if (underscorePos == underscoreForm.length() - 1) {
            return underscoreForm.substring(0, underscorePos);
        } else {
            int nextCharPos = underscorePos + 1;
            while (!Character.isLetterOrDigit(underscoreForm.charAt(nextCharPos))) {
                nextCharPos++;
                if (nextCharPos == underscoreForm.length()) {
                    return underscoreForm.substring(0, underscorePos);
                }
            }
            return underscoreForm.substring(0, underscorePos) + 
                    Character.toUpperCase(underscoreForm.charAt(nextCharPos)) +
                    underscoreToLmc(underscoreForm.substring(nextCharPos + 1));
        }
    }

    public static String getClassNameWithoutPackage(Class<?> beanClass) {
        String className = null; 
        if ((beanClass.getPackage() != null) && !beanClass.getPackage().getName().equals("")) {
            className =  beanClass.getName().substring(beanClass.getPackage().getName().length() + 1);
        } else {
            className = beanClass.getName();
        }
        int dollarPos = className.lastIndexOf('$');
        if (dollarPos >= 0) {
            className = className.substring(dollarPos + 1);
        }
        return className;
    }

    public static String lowerCaseFirstChar(String text) {
        if (text == null) {
            return null;
        } else if (text.length() > 1) {
            return text.substring(0,1).toLowerCase() + text.substring(1);
        } else {
            return text.toLowerCase();
        }
    }
    public static String upperCaseFirstChar(String text) {
        if (text == null) {
            return null;
        } else if (text.length() > 1) {
            return text.substring(0,1).toUpperCase() + text.substring(1);
        } else {
            return text.toUpperCase();
        }
    }

// 2015.07.06 H.Mizuno ディスプレイアダプター用に、getter メソッドを取得する機能を追加 start
/*
    public static Class<?> getFieldTypeByGetter(Class<?> beanClass, String fieldName) 
            throws NoSuchMethodException {
        try {
            Method methGetter = beanClass.getMethod(buildGetterName(fieldName, false), EMPTY_CLASS_ARRAY);
            return methGetter.getReturnType();
        } catch (NoSuchMethodException err) {
            try {
                Method methGetter = beanClass.getMethod(buildGetterName(fieldName, true), EMPTY_CLASS_ARRAY);
                return methGetter.getReturnType();
            } catch (NoSuchMethodException err2) {
                throw err;                
            }
        }
    }
*/
    public static Class<?> getFieldTypeByGetter(Class<?> beanClass, String fieldName) 
            throws NoSuchMethodException {
    	return getGetterMethod(beanClass, fieldName).getReturnType();
    }

    public static Method getGetterMethod(Class<?> beanClass, String fieldName) 
    		throws NoSuchMethodException {
    	
    	return getGetterMethod(beanClass, fieldName, EMPTY_CLASS_ARRAY);
	}
    
    public static Method getGetterMethod(Class<?> beanClass, String fieldName, Class<?>[] paramTypes) 
            throws NoSuchMethodException {
        try {
            return beanClass.getMethod(buildGetterName(fieldName, false), paramTypes);
        } catch (NoSuchMethodException err) {
        	return beanClass.getMethod(buildGetterName(fieldName, true), paramTypes);
        }
    }
// 2015.07.06 H.Mizuno ディスプレイアダプター用に、getter メソッドを取得する機能を追加 end
    
    public static Object getFieldValueByGetter(Object bean, String fieldName) 
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            Method methGetter = bean.getClass().getMethod(buildGetterName(fieldName, false), EMPTY_CLASS_ARRAY);
            return methGetter.invoke(bean, EMPTY_OBJECT_ARRAY);
        } catch (NoSuchMethodException err) {
            try {
                Method methGetter = bean.getClass().getMethod(buildGetterName(fieldName, true), EMPTY_CLASS_ARRAY);
                return methGetter.invoke(bean, EMPTY_OBJECT_ARRAY);
            } catch (NoSuchMethodException err2) {
                throw err;                
            }
        }
    }

    public static void setFieldValueBySetter(Object bean, String fieldName, Object value) 
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method methSetter = bean.getClass().getMethod(buildSetterName(fieldName), 
                new Class[] {getFieldTypeByGetter(bean.getClass(), fieldName)});
        methSetter.invoke(bean, new Object[] {value});
    }

    public static String[] getAllFieldNamesByGetters(Class<?> beanClass) {
        Class<?> thisClass = beanClass;
        List<String> outFields = null;
        while ((thisClass != null) && !thisClass.equals(Object.class)) {
            Field allFields[] = thisClass.getDeclaredFields();
            for (int n = 0; n < allFields.length; n++) {
                Field field = allFields[n];
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers)) {
                    try {
                        Class<?> fieldType = field.getType();
                        Method methGetter = thisClass.getMethod(buildGetterName(field.getName(),
                                fieldType.isAssignableFrom(Boolean.class) ||
                                fieldType.equals(Boolean.TYPE)), EMPTY_CLASS_ARRAY);
                        modifiers = methGetter.getModifiers();
                        if (!Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
                            if (outFields == null) {
                                outFields = new ArrayList<String>();
                            }
                            outFields.add(field.getName());
                        }
                    } catch (NoSuchMethodException err) {}
                }
            }
            thisClass = thisClass.getSuperclass();
        }
        return outFields == null ? new String[0] : 
            (String []) outFields.toArray(new String[outFields.size()]);
    }

// 2015.06.16 H.Mizuno GROUP BY 対応 start
    public static LinkedHashMap<String, Annotation[]> getAllFieldNamesAndAnnotationsByGetters(Class<?> beanClass) {
    	Class<?> thisClass = beanClass;
    	LinkedHashMap<String, Annotation[]> outFields = new LinkedHashMap<>();

        while ((thisClass != null) && !thisClass.equals(Object.class)) {
            Field allFields[] = thisClass.getDeclaredFields();
            for (int n = 0; n < allFields.length; n++) {

            	Field field = allFields[n];
                int modifiers = field.getModifiers();
                Annotation[] annontatins = field.getAnnotations();

                if (!Modifier.isStatic(modifiers)) {
                    try {
                        Class<?> fieldType = field.getType();
                        Method methGetter = thisClass.getMethod(buildGetterName(field.getName(),
                                fieldType.isAssignableFrom(Boolean.class) ||
                                fieldType.equals(Boolean.TYPE)), EMPTY_CLASS_ARRAY);
                        modifiers = methGetter.getModifiers();
                        if (!Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
                        	outFields.put(field.getName(), annontatins);
                        }
                    } catch (NoSuchMethodException err) {}
                }
            }
            thisClass = thisClass.getSuperclass();
        }
        return outFields;
    }
// 2015.06.16 H.Mizuno GROUP BY 対応 end
    
    /**
     * Reflects to get a static member attribute from a class (for getting constants)
     */
    public static Object getStaticAttribute(String name, Class<?> beanClass) {
        return getStaticAttribute(name, beanClass, Object.class);
    }

    @SuppressWarnings("unchecked")
    public static <E> E getStaticAttribute(String name, Class<?> beanClass, Class<E> type) {
        Class<?> thisClass = beanClass;
        while ((thisClass != null) && !thisClass.equals(Object.class)) {
            try {
                Field field = thisClass.getDeclaredField(name);
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) && 
                        Modifier.isPublic(modifiers) &&
                        field.getType().equals(type)) {
                    return (E) field.get(null);
                }
            } catch (Throwable err) {
                // skip and continue
            }
            thisClass = thisClass.getSuperclass();
        }
        return null;
    } 
}
