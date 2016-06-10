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
 * 路線マスタ取得用ＤＡＯクラス.
 * <p>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu	   2015.03.09	新規作成
 *
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスの SQL にはベンダー固有の処理が含まれている。<br/>
 * デフォルトは ORACLE用 なので、MySQL で使用する場合は、dbBender プロパティに
 * mysql を設定する事。<br/>
 *
 */
public class PanaRouteMstListDAOImpl implements  PanaRouteMstListDAO {
	/** データソース */
	private DataSource dataSource;

	/**
	 * データソースを取得する。<br/>
	 * <br/>
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 路線マスタ取得
	 * 　group byで同じ線路を合併する
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
	 * 路線マスタ取得
	 * 　group byで同じ線路を合併する
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
	 * 鉄道会社マスタ取得
	 * 　group byで同じ線路を合併する
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
