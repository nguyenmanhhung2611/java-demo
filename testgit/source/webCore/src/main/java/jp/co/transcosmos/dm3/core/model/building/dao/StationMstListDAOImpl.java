package jp.co.transcosmos.dm3.core.model.building.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.vo.StationMst;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * �w�}�X�^�擾�p�c�`�n�N���X.
 * <p>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu	   2015.03.10	�V�K�쐬
 * 
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�� SQL �ɂ̓x���_�[�ŗL�̏������܂܂�Ă���B<br/>
 * �f�t�H���g�� ORACLE�p �Ȃ̂ŁAMySQL �Ŏg�p����ꍇ�́AdbBender �v���p�e�B��
 * mysql ��ݒ肷�鎖�B<br/>
 * 
 */
public class StationMstListDAOImpl implements  StationMstListDAO {
	
	/** �f�[�^�\�[�X */
	protected DataSource dataSource;
	
	/**
	 * �f�[�^�\�[�X���擾����B<br/>
	 * <br/>
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * �w�}�X�^�擾
	 * �@group by�œ����w����������
	 */
	@Override
	public List<StationMst> listStationMst(Object[] params) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "SELECT stationMst.station_cd AS stationCd, stationMst.station_name AS stationName, stationRouteInfo.sort_order AS sortOrder "
				+ "FROM station_mst stationMst "
				+ "LEFT JOIN station_route_info stationRouteInfo ON stationMst.station_cd = stationRouteInfo.station_cd "
				+ "LEFT JOIN route_mst routeMst ON stationRouteInfo.route_cd = routeMst.route_cd "
				+ "WHERE stationMst.pref_cd = ? "
				+ "AND   routeMst.route_cd = ? "
				+ "GROUP BY stationMst.station_cd,stationMst.station_name "
				+ "ORDER BY stationRouteInfo.sort_order ASC "
				;
		return template.query(sql, params,  new StationMstRowMapper());
	}
	
	class StationMstRowMapper implements RowMapper<StationMst> {
		@Override
		public StationMst mapRow(ResultSet result, int rowNum)
				throws SQLException {
			StationMst stationMst = new StationMst();
			stationMst.setStationCd(result.getString("stationCd"));
			stationMst.setStationName(result.getString("stationName"));
			return stationMst;
		}
	}
}
