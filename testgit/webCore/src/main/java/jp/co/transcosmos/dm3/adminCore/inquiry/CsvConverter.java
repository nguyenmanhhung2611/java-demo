package jp.co.transcosmos.dm3.adminCore.inquiry;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * ���⍇��CSV �o�͎��̕ϊ�����.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.10	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class CsvConverter implements CsvValueConverter {

	protected CodeLookupManager codeLookupManager;
	
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * CSV �o�͒l�̕ϊ�����<br/>
	 * <br/>
	 * @param columnName CSV �o�͒l�̃t�B�[���h���iCsvConfig#dbColumns �̒l�j
	 * @param value ���̃J�����̏o�͒l
	 * @param thisOne CSV �s�f�[�^ �iValue �I�u�W�F�N�g�A�܂��́AJoinResult�j
	 * 
	 * @return�@�ϊ������l
	 */
	@Override
	public Object convert(String columnName, Object value, Object thisOne) {
		
		// �₢���킹��� �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
		if (columnName.equals("inquiryHeader.inquiryType")) {
			return this.codeLookupManager.lookupValueWithDefault("inquiry_type", (String)value, (String)value);
		}

		// ���⍇������ �̏ꍇ�A���t��yyyy/MM/dd�ɕϊ�����B
		if (columnName.equals("inquiryHeader.inquiryDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format((Date)value);
		}
		
		// ���⍇����� �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
		if (columnName.equals("inquiryDtlInfo.inquiryDtlType")) {
			return this.codeLookupManager.lookupValueWithDefault("inquiry_dtlType", (String)value, (String)value);
		}
		
		// �ŏI�X�V�� �̏ꍇ�A���t��yyyy/MM/dd HH:mm:ss�ɕϊ�����B
		if (columnName.equals("inquiryHeader.updDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format((Date)value);
		}
		
		// �ϊ��s�v�ȏꍇ�͌��̒l�𕜋A
		return value;
	}

}
