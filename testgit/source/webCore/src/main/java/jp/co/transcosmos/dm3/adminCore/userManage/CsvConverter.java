package jp.co.transcosmos.dm3.adminCore.userManage;

import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;


/**
 * �Ǘ����[�U�[CSV �o�͎��̕ϊ�����.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.05	�V�K�쐬
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

		// �J�������iCsvConfig �́AdbColumns �Ŏw�肵���l�j�����[��ID �̏ꍇ
		if (columnName.equals("adminRoleInfo.roleId")) {
			// ���[��ID �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
			return this.codeLookupManager.lookupValueWithDefault("roleId", (String)value, (String)value);
		} 


		// �ϊ��s�v�ȏꍇ�͌��̒l�𕜋A
		return value;
	}

}
