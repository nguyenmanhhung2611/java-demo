package jp.co.transcosmos.dm3.webAdmin.memberInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;


/**
 * ����ꗗCSV �o�͎��̕ϊ�����.
 * <p>
 * <pre>
 * �S����		  �C����		�C�����e
 * -------------- ----------- -----------------------------------------------------
 * tangtianyun	  2015.04.20	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class CsvConverter implements CsvValueConverter {

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	/** �Ǘ��҃��O�C���h�c��� ���p DAO*/
	protected DAO<AdminLoginInfo> adminLoginInfoDAO;

	/**
     * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param codeLookupManager
     */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
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
	 * @param targetValue CSV �o�͒l
	 * @param fieldName CSV �o�͒l�̃t�B�[���h���iCsvConfig#dbColumns �̒l�j
	 * @return�@�ϊ������l
	 */
    @Override
    public Object convert(String columnName, Object value, Object thisOne) {
    	// �o�^�o�H �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
        if (columnName.equals("memberInfo.entryRoute") && value != null) {
            // �o�^�o�H �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
            return this.codeLookupManager.lookupValueWithDefault("entryRoute", (String)value, (String)value);
        }
        // �����o�H �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
        if (columnName.equals("memberInfo.refCd") && value != null) {
            // �����o�H �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
            return this.codeLookupManager.lookupValueWithDefault("refCd", (String)value, (String)value);
        }
        // ���Z��� �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
        if (columnName.equals("memberInfo.residentFlg") && value != null) {
            // ���Z��� �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
            return this.codeLookupManager.lookupValueWithDefault("residentFlg", (String)value, (String)value);
        }
        // ���[���z�M��] �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
        if (columnName.equals("memberInfo.mailSendFlg") && value != null) {
            // ���[���z�M��] �̏ꍇ�Acodelookup �Œ�`����Ă��镶����ɕϊ�����B
            return this.codeLookupManager.lookupValueWithDefault("mailSendFlg", (String)value, (String)value);
        }
    	// �Z��2�̏ꍇ�A�Z��2���Z���E�s�撬�����{�Z���E�����Ԓn���̑��ɕϊ�����B
    	if (columnName.equals("memberInfo.address") && value != null) {
    		JoinResult result = (JoinResult)thisOne;
    		MemberInfo memberInfo = (MemberInfo)result.getItems().get("memberInfo");
    		String addressOther = "";
    		if (!StringValidateUtil.isEmpty(memberInfo.getAddressOther())) {
    			addressOther = memberInfo.getAddressOther();
    		}
    		return value + addressOther;
    	}
        // �V�K�o�^�� �̏ꍇ�A���t��yyyy/MM/dd�ɕϊ�����B
 		if (columnName.equals("memberInfo.insDate") && value != null) {
 			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 			return sdf.format((Date)value);
 		}
        // �V�K�o�^�� �̏ꍇ�A���[�U�[���ɕϊ�����B
 		if (columnName.equals("memberInfo.insUserId") && value != null) {
    		JoinResult result = (JoinResult)thisOne;
    		MemberInfo memberInfo = (MemberInfo)result.getItems().get("memberInfo");
    		if (memberInfo.getUserId().equals(value)) {
    			return "�{�l";
    		} else {
     			AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(value);
     			return adminLoginInfo == null? null: adminLoginInfo.getUserName();
    		}
 		}

 		// �ŏI�X�V�� �̏ꍇ�A���t��yyyy/MM/dd HH:mm:ss�ɕϊ�����B
 		if (columnName.equals("memberInfo.updDate") && value != null) {
 			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 			return sdf.format((Date)value);
 		}
        // �ŏI�X�V�� �̏ꍇ�A���[�U�[���ɕϊ�����B
 		if (columnName.equals("memberInfo.updUserId") && value != null) {
    		JoinResult result = (JoinResult)thisOne;
    		MemberInfo memberInfo = (MemberInfo)result.getItems().get("memberInfo");
    		if (memberInfo.getUserId().equals(value)) {
    			return "�{�l";
    		} else {
     			AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(value);
     			return adminLoginInfo == null? null: adminLoginInfo.getUserName();
    		}
 		}

        // �ϊ��s�v�ȏꍇ�͌��̒l�𕜋A
        return value;
    }

}
