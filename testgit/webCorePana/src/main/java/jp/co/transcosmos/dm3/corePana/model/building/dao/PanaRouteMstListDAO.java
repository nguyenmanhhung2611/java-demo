package jp.co.transcosmos.dm3.corePana.model.building.dao;

import java.util.List;

import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;

public interface PanaRouteMstListDAO {

	/**
	 * 路線マスタ取得
	 * 　group byで同じ線路を合併する
	 */
	public List<RouteMst> listRouteMst(Object[] params);

	public List<RouteMst> listRouteMst2(Object[] params);

	/**
	 * 鉄道会社マスタ取得
	 * 　group byで同じ線路を合併する
	 */
	public List<RrMst> listRrMst(Object[] params);
}
