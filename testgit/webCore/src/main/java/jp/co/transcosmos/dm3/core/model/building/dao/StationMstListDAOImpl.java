package jp.co.transcosmos.dm3.core.model.building.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import jp.co.transcosmos.dm3.core.vo.StationMst;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * 駅マスタ取得用ＤＡＯクラス.
 * <p>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu	   2015.03.10	新規作成
 * 
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスの SQL にはベンダー固有の処理が含まれている。<br/>
 * デフォルトは ORACLE用 なので、MySQL で使用する場合は、dbBender プロパティに
 * mysql を設定する事。<br/>
 * 
 */
public class StationMstListDAOImpl implements  StationMstListDAO {
	
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
	 * 駅マスタ取得
	 * 　group byで同じ駅を合併する
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
