package jp.co.transcosmos.dm3.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

	/** デフォルト日付書式 */
	private static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

	
	
	/**
	 * デフォルト日付書式を使用して文字列を日付型へ変換する。<br/>
	 * <br/>
	 * @param dateStr 日付文字列
	 * 
	 * @return 変換された日付型
	 * 
	 * @throws ParseException 
	 */
	public static Date stringToDate(String dateStr) throws ParseException {
		return stringToDate(dateStr, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 指定された日付書式を使用して文字列を日付型へ変換する。<br/>
	 * <br/>
	 * @param dateStr 日付文字列
	 * @param dateFmt 日付書式文字列
	 * 
	 * @return 変換された日付型
	 * 
	 * @throws ParseException 
	 */
	public static Date stringToDate(String dateStr, String dateFmt) throws ParseException {

		if (dateStr == null) return null;

		SimpleDateFormat format = new SimpleDateFormat(dateFmt);
		return format.parse(dateStr);
	}

	
	
	/**
	 * 日付型からデフォルトの日付書式文字列へ変換する。<br/>
	 * <br/>
	 * @param date 変換対象日付型
	 * @return 変換された文字列
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DEFAULT_DATE_FORMAT);
	}
	
	
	
	/**
	 * 日付型から指定された日付書式文字列へ変換する。<br/>
	 * <br/>
	 * @param date 変換対象日付型
	 * @param dateFmt 指定された書式文字列
	 * @return 変換された文字列
	 */
	public static String dateToString(Date date, String dateFmt) {

		if (date == null) return null;

		SimpleDateFormat format = new SimpleDateFormat(dateFmt);
		return format.format(date);
	}
	
	
	
	/**
	 * 接頭語を付加する。<br>
	 * ただし、結合対象文字列が空の場合は空文字列を復帰する。<br>
	 * <br>
	 * @param target 結合対象文字列
	 * @param prefix 接頭語
	 * @return 結合された文字列
	 */
	public static String addPrefix(Object target, String prefix){
		if (target == null) return "";
		if (StringValidateUtil.isEmpty(target.toString())) return "";
		return prefix.toString() + target;
	}
	
	
	
	/**
	 * 接尾語を付加する。<br>
	 * ただし、結合対象文字列が空の場合は空文字列を復帰する。<br>
	 * <br>
	 * @param target 結合対象文字列
	 * @param suffix 接尾語
	 * @return 結合された文字列
	 */
	public static String addSuffix(Object target, String suffix){ 
		if (target == null) return "";
		if (StringValidateUtil.isEmpty(target.toString())) return "";
		return target.toString() + suffix;
	}

}
