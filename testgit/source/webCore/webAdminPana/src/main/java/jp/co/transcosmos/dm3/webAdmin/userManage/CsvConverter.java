package jp.co.transcosmos.dm3.webAdmin.userManage;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;


/**
 * �Ǘ����[�U�[CSV �o�͎��̕ϊ�����.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����		2015/04/21	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class CsvConverter implements CsvValueConverter {

	private CodeLookupManager codeLookupManager;

	private int failCntToLock = 5;

	/** �Ǘ��҃��O�C���h�c��� ���p DAO*/
	protected DAO<AdminLoginInfo> adminLoginInfoDAO;

	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}



	public void setFailCntToLock(int failCntToLock) {
		this.failCntToLock = failCntToLock;
	}



	/**
	 * @param adminLoginInfoDAO �Z�b�g���� adminLoginInfoDAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
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

		// �J�������iCsvConfig �́AdbColumns �Ŏw�肵���l�j
		if (columnName.equals("adminLoginInfo.lastLogin")) {

			// �ŏI���O�C���� �̏ꍇ�A�uYYYY/MM/DD hh:mm�v�f�[�^�`���ɕϊ�����B
			if (value != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				return sdf.format((Date)value);
			}
			return value;

		} else if (columnName.equals("adminRoleInfo.roleId")) {

			// ���[��ID �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
			return this.codeLookupManager.lookupValueWithDefault("roleId", (String)value, (String)value);

		} else if (columnName.equals("adminLoginInfo.failCnt")) {

			// �A�J�E���g���b�N��� �̏ꍇ�A
			// ���O�C�����s�񐔂�5�ȏ�@�ˁ@�u���b�N���v
			// ��L�ȊO�@�ˁ@�u�A�����b�N�v
			if (value != null && (int)value >= this.failCntToLock) {
				return "���b�N��";
			}
			return "�A�����b�N";

		}

        // �V�K�o�^�� �̏ꍇ�A���t��yyyy/MM/dd�ɕϊ�����B
 		if (columnName.equals("adminLoginInfo.insDate") && value != null) {
 			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 			return sdf.format((Date)value);
 		}
        // �V�K�o�^�� �̏ꍇ�A���[�U�[���ɕϊ�����B
 		if (columnName.equals("adminLoginInfo.insUserId") && value != null) {
 			AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(value);
 			return adminLoginInfo == null? null: adminLoginInfo.getUserName();
 		}

 		// �ŏI�X�V�� �̏ꍇ�A���t��yyyy/MM/dd HH:mm:ss�ɕϊ�����B
 		if (columnName.equals("adminLoginInfo.updDate") && value != null) {
 			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 			return sdf.format((Date)value);
 		}

        // �ŏI�X�V�� �̏ꍇ�A���[�U�[���ɕϊ�����B
 		if (columnName.equals("adminLoginInfo.updUserId") && value != null) {
 			AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(value);
 			return adminLoginInfo == null? null: adminLoginInfo.getUserName();
 		}

		// �ϊ��s�v�ȏꍇ�͌��̒l�𕜋A
		return value;
	}

}
