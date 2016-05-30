package jp.co.transcosmos.dm3.webAdmin.information;

import jp.co.transcosmos.dm3.adminCore.information.CsvConverter;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.corePana.vo.Information;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * ���m�点CSV �o�͎��̕ϊ�����.
 * <p>
 *
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.04.21	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaCsvConverter extends CsvConverter implements CsvValueConverter {

	/** �Ǘ��҃��O�C���h�c��� ���p DAO*/
	protected DAO<AdminLoginInfo> adminLoginInfoDAO;


	/**
	 * @param adminLoginInfoDAO �Z�b�g���� adminLoginInfoDAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}


	/**
	 * CSV �o�͒l�̕ϊ�����<br/>
	 * <br/>
	 *
	 * @param columnName
	 *            CSV �o�͒l�̃t�B�[���h���iCsvConfig#dbColumns �̒l�j
	 * @param value
	 *            ���̃J�����̏o�͒l
	 * @param thisOne
	 *            CSV �s�f�[�^ �iValue �I�u�W�F�N�g�A�܂��́AJoinResult�j
	 *
	 * @return�@�ϊ������l
	 */
	@Override
	public Object convert(String columnName, Object value, Object thisOne) {

		// �Ώۉ�� �̏ꍇ�A������i���j+�@������i���j
		// �މ���ꍇ�A�����������̂��m�点�ɂ��ẮA�����Ɂu�މ�ς݁v�ƋL��
		if (columnName.equals("memberInfo.memberLname")) {
			JoinResult result = (JoinResult) thisOne;
			Information information = (Information) result.getItems().get(
					"information");
			MemberInfo memberInfo = (MemberInfo) result.getItems().get(
					"memberInfo");

			if (information.getDspFlg().equals("0")) {
				value = "�T�C�gTOP";
			} else if (information.getDspFlg().equals("1")) {
				value = "�S��";
			} else if (information.getDspFlg().equals("2")) {
				if (!StringValidateUtil.isEmpty(memberInfo.getMemberLname())) {
					value = memberInfo.getMemberLname() + " "
							+ memberInfo.getMemberFname();
				} else {
					value = "�މ�ς�";
				}
			}

			return value;
		}

		// ���[�����M�t���O 0�F�����M�A1�F���M
		if (columnName.equals("information.sendFlg")) {
			if (value != null) {
				if ("0".equals(value)) {
					value = "�����M";
				} else if ("1".equals(value)) {
					value = "���M";
				}
			}

			return value;
		}

		// �ŏI�X�V�ҁF���[�U�[����
		if (columnName.equals("information.updUserId")) {
			JoinResult result = (JoinResult) thisOne;
			AdminLoginInfo adminLoginInfo = (AdminLoginInfo) result.getItems()
					.get("adminLoginInfo");

			return adminLoginInfo.getUserName();
		}

        // �V�K�o�^�� �̏ꍇ�A���[�U�[���ɕϊ�����B
 		if (columnName.equals("information.insUserId") && value != null) {
 			AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(value);
 			return adminLoginInfo == null? null: adminLoginInfo.getUserName();
 		}

		return super.convert(columnName, value, thisOne);
	}

}
