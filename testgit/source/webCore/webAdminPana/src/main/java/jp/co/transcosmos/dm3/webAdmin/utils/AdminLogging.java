package jp.co.transcosmos.dm3.webAdmin.utils;

import java.util.Date;

import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.corePana.vo.AdminLog;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.utils.CommonLogging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * �Ǘ��T�C�g���O�o�̓N���X 
 * ���O�e�[�u���ւ̏o�͂�Ή�����B
 *�@�g�p����ꍇ�́ASpring �� DI �R���e�i�ŃV���O���g���Œ�`���鎖�B
 *�@�i���ڃC���X�^���X�������ꍇ��Ascope ���v���g�^�C�v�̏ꍇ�́A�}���`�X���b�h���œ���ۏ؂��Ȃ��B�j
 * 
 */
public class AdminLogging implements CommonLogging {

    private static final Log log = LogFactory.getLog(AdminLogging.class);
    
    /** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;

	/** ���O�e�[�u��DAO */
	protected DAO<AdminLog> logTableDao;
	
	/**
	 * ���O�o�͏���<br>
	 * <br>
	 * @param msg �o�̓��b�Z�[�W
	 * @return true=����I���Afalse=�G���[
	*/
	public boolean write(String msg) {
		return logWriter(msg, null, null);
	}
	
    /**
	 * ���O�o�͏���<br>
	 * <br>
	 * @param msg �o�̓��b�Z�[�W
	 * @param adminUserId �Ǘ��҃��[�U�[ID
	 * @param functionCd �@�\�R�[�h
	 * @return true=����I���Afalse=�G���[
	 */
	public boolean write(String msg, String adminUserId, String functionCd) {
		return logWriter(msg, adminUserId, functionCd);
	}
	
	/**
	 * ���O�o�͏���<br>
	 * <br>
	 * @param msg �o�̓��b�Z�[�W
	 * @param adminUserId �Ǘ��҃��[�U�[ID
	 * @param functionCd �@�\�R�[�h
	 * @return true=����I���Afalse=�G���[
	 */
	private boolean logWriter(String msg, String adminUserId, String functionCd){
		// ���O�e�[�u���̃I�u�W�F�N�g
		AdminLog logTableVo = buildLogTableVo(msg, adminUserId, functionCd);

		// ���O�e�[�u���ւ̏�������
		this.logTableDao.insert(new AdminLog[] { logTableVo });
		return true;
	}
	
	/**
	 * ���O�I�u�W�F�N�g���쐬
	 * @param msg �o�̓��b�Z�[�W
	 * @param adminUserId �Ǘ��҃��[�U�[ID
	 * @param functionCd �@�\�R�[�h
	 * @return ���O�I�u�W�F�N�g
	 */
	protected AdminLog buildLogTableVo(String msg, String adminUserId, String functionCd) {
		AdminLog vo = (AdminLog)this.valueObjectFactory.getValueObject("AdminLog");
		
		vo.setMsg(msg);
		vo.setInsDate(new Date());
		vo.setAdminUserId(adminUserId);
		vo.setFunctionCd(functionCd);
		return vo;
	}

	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	public void setLogTableDao(DAO<AdminLog> logTableDao) {
		this.logTableDao = logTableDao;
	}
}
