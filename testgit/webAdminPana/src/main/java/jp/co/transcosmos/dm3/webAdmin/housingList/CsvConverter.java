package jp.co.transcosmos.dm3.webAdmin.housingList;

import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;


/**
 * �����ꗗCSV �o�͎��̕ϊ�����.
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

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	/**
     * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param codeLookupManager
     */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}



	/**
	 * CSV �o�͒l�̕ϊ�����<br/>
	 * <br/>
	 * @param targetValue CSV �o�͒l
	 * @param fieldName CSV �o�͒l�̃t�B�[���h���iCsvConfig#dbColumns �̒l�j
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
