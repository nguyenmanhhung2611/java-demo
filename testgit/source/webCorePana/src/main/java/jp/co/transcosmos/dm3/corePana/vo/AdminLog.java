package jp.co.transcosmos.dm3.corePana.vo;

import java.util.Date;

/**
 * �Ǘ��T�C�g���O.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Tanaka Shinichi        2015.08.11  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class AdminLog {
	/** �Ǘ��T�C�g���OID */
	private String adminLogId;
	/** �Ǘ��҃��[�U�[ID */
	private String adminUserId;
	/** ���b�Z�[�W */
	private String msg;
	/** �@�\CD */
	private String functionCd;
	/** �o�^�� */
	private Date insDate;


	public String getAdminLogId() {
		return adminLogId;
	}

	public void setAdminLogId(String adminLogId) {
		this.adminLogId = adminLogId;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getFunctionCd() {
		return functionCd;
	}

	public void setFunctionCd(String functionCd) {
		this.functionCd = functionCd;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
