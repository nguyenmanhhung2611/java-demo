package jp.co.transcosmos.dm3.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

	
	/**
	 * 指定された日付文字列に、指定された日数を加算し、その結果を日付オブジェクトで復帰する。<br/>
	 * 日付文字列の書式は、StringUtils で設定されているデフォルト書式が設定される。<br/>
	 * <br/>
	 * @param dateStr　加算対象となる日付文字列
	 * @param days 加算日数
	 * 
	 * @return 加算された日付型オブジェクト
	 */
	public static Date addDays(String dateStr, int days) throws ParseException {
		return addDays(StringUtils.stringToDate(dateStr), days);
	}
	
	/**
	 * 指定された日付文字列に、指定された日数を加算し、その結果を日付オブジェクトで復帰する。<br/>
	 * 日付文字列の書式は、dateFmt パラメータで指定された文字列を使用する。<br/>
	 * <br/>
	 * @param dateStr　加算対象となる日付文字列
	 * @param dateFmt　加算対象となる日付文字列の書式
	 * @param days 加算日数
	 * 
	 * @return 加算された日付型オブジェクト
	 */
	public static Date addDays(String dateStr, String dateFmt, int days) throws ParseException {
		return addDays(StringUtils.stringToDate(dateStr, dateFmt), days);
	}
	
	/**
	 * 指定された日付型に、指定された日数を加算する。<br/>
	 * <br/>
	 * @param date　加算対象となる日付型
	 * @param days 加算日数
	 * 
	 * @return 加算された日付型オブジェクト
	 */
	public static Date addDays(Date date, int days){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();

	}
	
}
