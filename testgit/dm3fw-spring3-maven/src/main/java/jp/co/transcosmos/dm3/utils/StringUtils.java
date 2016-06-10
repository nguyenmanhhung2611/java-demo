package jp.co.transcosmos.dm3.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

	/** �f�t�H���g���t���� */
	private static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

	
	
	/**
	 * �f�t�H���g���t�������g�p���ĕ��������t�^�֕ϊ�����B<br/>
	 * <br/>
	 * @param dateStr ���t������
	 * 
	 * @return �ϊ����ꂽ���t�^
	 * 
	 * @throws ParseException 
	 */
	public static Date stringToDate(String dateStr) throws ParseException {
		return stringToDate(dateStr, DEFAULT_DATE_FORMAT);
	}

	/**
	 * �w�肳�ꂽ���t�������g�p���ĕ��������t�^�֕ϊ�����B<br/>
	 * <br/>
	 * @param dateStr ���t������
	 * @param dateFmt ���t����������
	 * 
	 * @return �ϊ����ꂽ���t�^
	 * 
	 * @throws ParseException 
	 */
	public static Date stringToDate(String dateStr, String dateFmt) throws ParseException {

		if (dateStr == null) return null;

		SimpleDateFormat format = new SimpleDateFormat(dateFmt);
		return format.parse(dateStr);
	}

	
	
	/**
	 * ���t�^����f�t�H���g�̓��t����������֕ϊ�����B<br/>
	 * <br/>
	 * @param date �ϊ��Ώۓ��t�^
	 * @return �ϊ����ꂽ������
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DEFAULT_DATE_FORMAT);
	}
	
	
	
	/**
	 * ���t�^����w�肳�ꂽ���t����������֕ϊ�����B<br/>
	 * <br/>
	 * @param date �ϊ��Ώۓ��t�^
	 * @param dateFmt �w�肳�ꂽ����������
	 * @return �ϊ����ꂽ������
	 */
	public static String dateToString(Date date, String dateFmt) {

		if (date == null) return null;

		SimpleDateFormat format = new SimpleDateFormat(dateFmt);
		return format.format(date);
	}
	
	
	
	/**
	 * �ړ����t������B<br>
	 * �������A�����Ώە����񂪋�̏ꍇ�͋󕶎���𕜋A����B<br>
	 * <br>
	 * @param target �����Ώە�����
	 * @param prefix �ړ���
	 * @return �������ꂽ������
	 */
	public static String addPrefix(Object target, String prefix){
		if (target == null) return "";
		if (StringValidateUtil.isEmpty(target.toString())) return "";
		return prefix.toString() + target;
	}
	
	
	
	/**
	 * �ڔ����t������B<br>
	 * �������A�����Ώە����񂪋�̏ꍇ�͋󕶎���𕜋A����B<br>
	 * <br>
	 * @param target �����Ώە�����
	 * @param suffix �ڔ���
	 * @return �������ꂽ������
	 */
	public static String addSuffix(Object target, String suffix){ 
		if (target == null) return "";
		if (StringValidateUtil.isEmpty(target.toString())) return "";
		return target.toString() + suffix;
	}

}
