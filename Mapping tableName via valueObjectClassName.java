=== Issue ===
<bean id="recentlyInfoDAO" class="jp.co.transcosmos.dm3.dao.ReflectingDAO">
	<property name="dataSource" ref="requestScopeDS" />
	<property name="valueObjectClassName"
	  value="jp.co.transcosmos.dm3.corePana.vo.RecentlyInfo" />
	<property name="pkFields" value="sysHousingCd,userId" />
	<property name="emptyStringsToNull" value="true" />
	<property name="useAutonumberColumns" value="false"/>
</bean>

== Research ==
ReflectingDAO.java
protected String[][] assignFieldNameToColumnNameMappings(Map<String,String> suggestedMappings) {

== Explain ==
<bean id="recentlyInfoDAO" class="jp.co.transcosmos.dm3.dao.ReflectingDAO">
<property name="valueObjectClassName" value="jp.co.transcosmos.dm3.corePana.vo.RecentlyInfo" />

-> tableName: recently_info


================================================
Mapping tableName via class name
~~~
Input: ListMethod.class
Output: list_method
~~~

public class ListMethod { }

public class Test {

	public static void main(String[] args) {
		String classNameWithoutPackage = getClassNameWithoutPackage(ListMethod.class);
		System.out.println(mappingPolicyFieldNameToColumnName(classNameWithoutPackage));
	}

	protected static String mappingPolicyFieldNameToColumnName(String fieldName) {
		String mappedTo = lmcToUnderscore(fieldName);
		/*if (this.capitalizeFieldNames) {
			mappedTo = mappedTo.toUpperCase();
		}*/
		return mappedTo;
	}

	public static String lmcToUnderscore(String fieldName) {
		// Pass 1 - convert consecutive upper case chars to first + last only
		// upper case
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

	public static String getClassNameWithoutPackage(Class<?> beanClass) {
		String className = null;
		if ((beanClass.getPackage() != null) && !beanClass.getPackage().getName().equals("")) {
			className = beanClass.getName().substring(beanClass.getPackage().getName().length() + 1);
		} else {
			className = beanClass.getName();
		}
		int dollarPos = className.lastIndexOf('$');
		if (dollarPos >= 0) {
			className = className.substring(dollarPos + 1);
		}
		return className;
	}
}







