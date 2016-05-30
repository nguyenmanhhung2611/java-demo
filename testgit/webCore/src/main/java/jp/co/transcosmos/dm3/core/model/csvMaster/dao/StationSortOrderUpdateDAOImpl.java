package jp.co.transcosmos.dm3.core.model.csvMaster.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 駅マスタ取り込み並び順更新ＤＡＯクラス.
 * <p>
 *  駅マスタ取り込み機能で登録されるテーブルの並び順を更新する。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * 阿部　奈津江	2007.01.25	新規作成
 * H.Mizuno		2015.02.19	MySQL 対応
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスの SQL にはベンダー固有の処理が含まれている。<br/>
 * デフォルトは ORACLE用 なので、MySQL で使用する場合は、dbBender プロパティに
 * mysql を設定する事。<br/>
 * 
 */
public class StationSortOrderUpdateDAOImpl implements StationSortOrderUpdateDAO {

	/** データソース */
	protected DataSource dataSource;

	/** 使用する DB ベンダー名　oracle or mysql （デフォルト oracle）  */
	protected String dbBender = "oracle";



	/**
	 * データソースを取得する。<br/>
	 * <br/>
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 使用する DB ペンだー名を設定する。<br/>
	 * <br/>
	 * @param dbBender oracle or mysql （デフォルト oracle）
	 */
	public void setDbBender(String dbBender) {
		this.dbBender = dbBender;
	}



	/**
	 * 鉄道会社マスタの並び順全件更新
	 * 　並び順＋鉄道会社CD順に並べたものに対して
	 * 　rownumを利用して1～順につけた並び順で更新する
	 * 　※歯抜けになっていた並び順は詰まる
	*/
	@Override
	public void updateRrMstSortOrder(Object[] objUpdData) {
	
		JdbcTemplate template = new JdbcTemplate(dataSource);

		String rownum = "ROWNUM";

		// MySQL の場合、ROWNUM 擬似列が存在しないので、相関クエリで代替する。
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
	 * 路線マスタの並び順全件更新
	 * 　並び順＋路線CD順に並べたものに対して
	 * 　rownumを利用して1～順につけた並び順で更新する
	 * 　※歯抜けになっていた並び順は詰まる
	*/
	@Override
	public void updateRouteMstSortOrder(Object[] objUpdData) {
	
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		String rownum = "ROWNUM";

		// MySQL の場合、ROWNUM 擬似列が存在しないので、相関クエリで代替する。
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
