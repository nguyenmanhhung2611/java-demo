package jp.co.transcosmos.dm3.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

	
	/**
	 * �w�肳�ꂽ���t������ɁA�w�肳�ꂽ���������Z���A���̌��ʂ���t�I�u�W�F�N�g�ŕ��A����B<br/>
	 * ���t������̏����́AStringUtils �Őݒ肳��Ă���f�t�H���g�������ݒ肳���B<br/>
	 * <br/>
	 * @param dateStr�@���Z�ΏۂƂȂ���t������
	 * @param days ���Z����
	 * 
	 * @return ���Z���ꂽ���t�^�I�u�W�F�N�g
	 */
	public static Date addDays(String dateStr, int days) throws ParseException {
		return addDays(StringUtils.stringToDate(dateStr), days);
	}
	
	/**
	 * �w�肳�ꂽ���t������ɁA�w�肳�ꂽ���������Z���A���̌��ʂ���t�I�u�W�F�N�g�ŕ��A����B<br/>
	 * ���t������̏����́AdateFmt �p�����[�^�Ŏw�肳�ꂽ��������g�p����B<br/>
	 * <br/>
	 * @param dateStr�@���Z�ΏۂƂȂ���t������
	 * @param dateFmt�@���Z�ΏۂƂȂ���t������̏���
	 * @param days ���Z����
	 * 
	 * @return ���Z���ꂽ���t�^�I�u�W�F�N�g
	 */
	public static Date addDays(String dateStr, String dateFmt, int days) throws ParseException {
		return addDays(StringUtils.stringToDate(dateStr, dateFmt), days);
	}
	
	/**
	 * �w�肳�ꂽ���t�^�ɁA�w�肳�ꂽ���������Z����B<br/>
	 * <br/>
	 * @param date�@���Z�ΏۂƂȂ���t�^
	 * @param days ���Z����
	 * 
	 * @return ���Z���ꂽ���t�^�I�u�W�F�N�g
	 */
	public static Date addDays(Date date, int days){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();

	}
	
}
