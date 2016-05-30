package jp.co.transcosmos.dm3.core.model.housing.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * �����摜�t�@�C���������p DAO �̎����N���X.
 * ���A����摜�t�@�C�����ɂ͊g���q�A����уt�@�C���p�X�̏��͊܂܂Ȃ��B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.06	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class HousingFileNameDAOImpl implements HousingFileNameDAO {

	/** �f�[�^�\�[�X */
	protected DataSource dataSource;

	/** �V�[�P���X����l���擾����ۂɎg�p���� SQL �� */
	protected String sequenceNextvalExpression;



	/**
	 * �f�[�^�\�[�X���擾����B<br/>
	 * <br/>
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * �V�[�P���X����l���擾����ۂɎg�p���� SQL ����ݒ肷��B<br/>
	 * <br/>
	 * @param sequenceNextvalExpression �V�[�P���X����l���擾����ۂɎg�p���� SQL ��
	 */
	public void setSequenceNextvalExpression(String sequenceNextvalExpression) {
		this.sequenceNextvalExpression = sequenceNextvalExpression;
	}



	/**
	 * �����摜�t�@�C�������V�[�P���X����擾���ĕ��A����B<br/>
	 * �g���q�A����уt�@�C���p�X�̏��͕��A����f�[�^�Ɋ܂܂Ȃ��B<br/>
	 * <br/>
	 * @return �摜�t�@�C�����@�i�g���q�A�p�X�Ȃ��j
	 */
	@Override
	public String createFileName() {

		JdbcTemplate template = new JdbcTemplate(this.dataSource);

        List<String> list = template.query(this.sequenceNextvalExpression, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultset, int rowNum) throws SQLException {
				return resultset.getString(1);
			}});

		return list.get(0);
	}

}
