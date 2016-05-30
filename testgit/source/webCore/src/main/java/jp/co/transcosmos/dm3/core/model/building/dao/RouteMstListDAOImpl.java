package jp.co.transcosmos.dm3.core.model.building.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.vo.RouteMst;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * �H���}�X�^�擾�p�c�`�n�N���X.
 * <p>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu	   2015.03.09	�V�K�쐬
 * 
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�� SQL �ɂ̓x���_�[�ŗL�̏������܂܂�Ă���B<br/>
 * �f�t�H���g�� ORACLE�p �Ȃ̂ŁAMySQL �Ŏg�p����ꍇ�́AdbBender �v���p�e�B��
 * mysql ��ݒ肷�鎖�B<br/>
 * 
 */
public class RouteMstListDAOImpl implements  RouteMstListDAO {
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
	 * �H���}�X�^�擾
	 * �@group by�œ������H����������
	 */
	@Override
	public List<RouteMst> listRouteMst(Object[] params) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "SELECT routeMst.route_cd AS routeCd, routeMst.route_name AS routeName, routeMst.sort_order AS sortOrder "
				+ "FROM route_mst routeMst "
				+ "LEFT JOIN station_route_info stationRouteInfo ON routeMst.route_cd = stationRouteInfo.route_cd "
				+ "LEFT JOIN station_mst stationMst ON stationRouteInfo.station_cd = stationMst.station_cd "
				+ "WHERE stationMst.pref_cd = ? "
				+ "GROUP BY routeMst.route_cd,routeMst.route_name "
				+ "ORDER BY routeMst.sort_order ASC "
				;
		return template.query(sql, params,  new RouteMstRowMapper());
	}
	
	class RouteMstRowMapper implements RowMapper<RouteMst> {
		@Override
		public RouteMst mapRow(ResultSet result, int rowNum)
				throws SQLException {
			RouteMst routeMst = new RouteMst();
			routeMst.setRouteCd(result.getString("routeCd"));
			routeMst.setRouteName(result.getString("routeName"));
			return routeMst;
		}
	}
	
}
