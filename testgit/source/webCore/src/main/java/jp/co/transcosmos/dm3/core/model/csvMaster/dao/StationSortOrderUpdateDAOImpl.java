package jp.co.transcosmos.dm3.core.model.csvMaster.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * �w�}�X�^��荞�ݕ��я��X�V�c�`�n�N���X.
 * <p>
 *  �w�}�X�^��荞�݋@�\�œo�^�����e�[�u���̕��я����X�V����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �����@�ޒÍ]	2007.01.25	�V�K�쐬
 * H.Mizuno		2015.02.19	MySQL �Ή�
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�� SQL �ɂ̓x���_�[�ŗL�̏������܂܂�Ă���B<br/>
 * �f�t�H���g�� ORACLE�p �Ȃ̂ŁAMySQL �Ŏg�p����ꍇ�́AdbBender �v���p�e�B��
 * mysql ��ݒ肷�鎖�B<br/>
 * 
 */
public class StationSortOrderUpdateDAOImpl implements StationSortOrderUpdateDAO {

	/** �f�[�^�\�[�X */
	protected DataSource dataSource;

	/** �g�p���� DB �x���_�[���@oracle or mysql �i�f�t�H���g oracle�j  */
	protected String dbBender = "oracle";



	/**
	 * �f�[�^�\�[�X���擾����B<br/>
	 * <br/>
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * �g�p���� DB �y�����[����ݒ肷��B<br/>
	 * <br/>
	 * @param dbBender oracle or mysql �i�f�t�H���g oracle�j
	 */
	public void setDbBender(String dbBender) {
		this.dbBender = dbBender;
	}



	/**
	 * �S����Ѓ}�X�^�̕��я��S���X�V
	 * �@���я��{�S�����CD���ɕ��ׂ����̂ɑ΂���
	 * �@rownum�𗘗p����1�`���ɂ������я��ōX�V����
	 * �@���������ɂȂ��Ă������я��͋l�܂�
	*/
	@Override
	public void updateRrMstSortOrder(Object[] objUpdData) {
	
		JdbcTemplate template = new JdbcTemplate(dataSource);

		String rownum = "ROWNUM";

		// MySQL �̏ꍇ�AROWNUM �[���񂪑��݂��Ȃ��̂ŁA���փN�G���ő�ւ���B
		if ("mysql".equals(this.dbBender)) {
			rownum = "(SELECT COUNT(*) FROM " +
					 " (SELECT rr_cd FROM rr_mst ORDER BY sort_order ASC, rr_cd ASC) R2 " +
					 " WHERE R2.rr_cd <= R1.rr_cd) ";
		}
		
		template.update("UPDATE rr_mst mainRrTbl "
						+ "SET mainRrTbl.upd_date = ?, mainRrTbl.upd_user_id = ? "
							+ ", mainRrTbl.sort_order = ( "
								+ "SELECT orderNo "
								+ "FROM ( "
									+ "SELECT " + rownum + " AS orderNo, rr_cd "
									+ "FROM( "
										+ "SELECT rr_cd "
										+ "FROM rr_mst "
										+ "ORDER BY sort_order ASC, rr_cd ASC "
									+ ") R1 "
								+ ") sortRrTbl "
								+ "WHERE sortRrTbl.rr_cd = mainRrTbl.rr_cd "
							+ ")", objUpdData);

	}

	
	
	/**
	 * �H���}�X�^�̕��я��S���X�V
	 * �@���я��{�H��CD���ɕ��ׂ����̂ɑ΂���
	 * �@rownum�𗘗p����1�`���ɂ������я��ōX�V����
	 * �@���������ɂȂ��Ă������я��͋l�܂�
	*/
	@Override
	public void updateRouteMstSortOrder(Object[] objUpdData) {
	
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		String rownum = "ROWNUM";

		// MySQL �̏ꍇ�AROWNUM �[���񂪑��݂��Ȃ��̂ŁA���փN�G���ő�ւ���B
		if ("mysql".equals(this.dbBender)) {
			rownum = "(SELECT COUNT(*) FROM " +
					 " (SELECT route_cd FROM route_mst ORDER BY sort_order ASC, route_cd ASC) R2 " +
					 " WHERE R2.route_cd <= R1.route_cd) ";
		}
		
		template.update("UPDATE route_mst mainRouteTbl "
						+ "SET mainRouteTbl.upd_date = ?, mainRouteTbl.upd_user_id = ? "
							+ ", mainRouteTbl.sort_order = ( "
								+ "SELECT orderNo "
								+ "FROM ( "
									+ "SELECT " + rownum + " AS orderNo, route_cd "
									+ "FROM( "
										+ "SELECT route_cd "
										+ "FROM route_mst "
										+ "ORDER BY sort_order ASC, route_cd ASC "
									+ ") R1 "
								+ ") sortRouteTbl "
								+ "WHERE sortRouteTbl.route_cd = mainRouteTbl.route_cd "
							+ ")", objUpdData);

	}	
}
