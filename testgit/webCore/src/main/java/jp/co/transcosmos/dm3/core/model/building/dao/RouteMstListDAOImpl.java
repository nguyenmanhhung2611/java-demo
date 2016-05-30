package jp.co.transcosmos.dm3.core.model.building.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.vo.RouteMst;

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
public class RouteMstListDAOImpl implements  RouteMstListDAO {
	/** データソース */
	protected DataSource dataSource;

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
