package jp.co.transcosmos.dm3.adminCore.information;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * ���m�点CSV �o�͎��̕ϊ�����.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.31	�V�K�쐬
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
		
		// �o�^���� �̏ꍇ�A���t��yyyy/MM/dd HH:mm:ss�ɕϊ�����B
		if (columnName.equals("information.insDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format((Date)value);
		}
		
		// ��� �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
		if (columnName.equals("information.informationType")) {
			return this.codeLookupManager.lookupValueWithDefault("information_type", (String)value, (String)value);
		}
		
		// ���J�Ώۋ敪 �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
		if (columnName.equals("information.dspFlg")) {
			return this.codeLookupManager.lookupValueWithDefault("information_dspFlg", (String)value, (String)value);
		}
		
		// �\�����ԁiFROM) �̏ꍇ�A���t��yyyy/MM/dd�ɕϊ�����B
		if (columnName.equals("information.startDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format((Date)value);
		}
		
		// �\�����ԁiTO) �̏ꍇ�A���t��yyyy/MM/dd�ɕϊ�����B
		if (columnName.equals("information.endDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format((Date)value);
		}
		
		// �ŏI�X�V�� �̏ꍇ�A���t��yyyy/MM/dd HH:mm:ss�ɕϊ�����B
		if (columnName.equals("information.updDate") && value != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format((Date)value);
		}
		
		// �ϊ��s�v�ȏꍇ�͌��̒l�𕜋A
		return value;
	}

}
