package jp.co.transcosmos.dm3.core.displayAdapter;

public class Executer {

	public static <T> Object getValue(DisplayAdapter adapter, T vo, String fieldName)
			throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException{

		return adapter.getDisplayValue(vo, fieldName);
	}
}
