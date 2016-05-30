package jp.co.transcosmos.dm3.corePana.model.building.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;

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
public class PanaRouteMstListDAOImpl implements  PanaRouteMstListDAO {
	/** �f�[�^�\�[�X */
	private DataSource dataSource;

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
		String sql = "SELECT routeMst.route_cd AS routeCd, CONCAT(rrMst.rr_name,routeMst.route_name_full) AS routeName, routeMst.sort_order AS sortOrder "
				+ "FROM route_mst routeMst "
				+ "LEFT JOIN station_route_info stationRouteInfo ON routeMst.route_cd = stationRouteInfo.route_cd "
				+ "LEFT JOIN station_mst stationMst ON stationRouteInfo.station_cd = stationMst.station_cd "
				+ "LEFT JOIN rr_mst rrMst ON routeMst.rr_cd = rrMst.rr_cd "
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

	/**
	 * �H���}�X�^�擾
	 * �@group by�œ������H����������
	 */
	@Override
	public List<RouteMst> listRouteMstWithGroupBy(Object[] params) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "SELECT DISTINCT  rt.route_cd AS rt_route_cd, rt.route_name AS rt_route_name "
				+ "FROM pref_mst pm "
				+ "INNER JOIN station_mst st ON pm.pref_cd = st.pref_cd "
				+ "INNER JOIN station_route_info sr ON st.station_cd = sr.station_cd "
				+ "INNER JOIN route_mst rt ON sr.route_cd = rt.route_cd "
				+ "WHERE pm.pref_cd = ? "
				+ "ORDER BY rt.sort_order "				
				;
		return template.query(sql, params,  new RouteMstRowMapper2());
	}
	class RouteMstRowMapper2 implements RowMapper<RouteMst> {
		@Override
		public RouteMst mapRow(ResultSet result, int rowNum)
				throws SQLException {

			RouteMst routeMst = new RouteMst();
			routeMst.setRouteCd(result.getString("rt_route_cd"));
			routeMst.setRouteName(result.getString("rt_route_name"));
			return routeMst;
		}
	}

	/**
	 * �S����Ѓ}�X�^�擾
	 * �@group by�œ������H����������
	 */
	@Override
	public List<RrMst> listRrMst(Object[] params) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "SELECT rrMst.rr_cd AS rrCd, rrMst.rr_name AS rrName "
				+ "FROM route_mst routeMst "
				+ "LEFT JOIN station_route_info stationRouteInfo ON routeMst.route_cd = stationRouteInfo.route_cd "
				+ "LEFT JOIN station_mst stationMst ON stationRouteInfo.station_cd = stationMst.station_cd "
				+ "LEFT JOIN rr_mst rrMst ON routeMst.rr_cd = rrMst.rr_cd "
				+ "WHERE stationMst.pref_cd = ? "
				+ "GROUP BY rrMst.rr_cd,rrMst.rr_name "
				+ "ORDER BY rrMst.sort_order ASC "
				;
		return template.query(sql, params,  new RrMstRowMapper());
	}

	class RrMstRowMapper implements RowMapper<RrMst> {
		@Override
		public RrMst mapRow(ResultSet result, int rowNum)
				throws SQLException {
			RrMst rrMst = new RrMst();
			rrMst.setRrCd(result.getString("rrCd"));
			rrMst.setRrName(result.getString("rrName"));
			return rrMst;
		}
	}
}
