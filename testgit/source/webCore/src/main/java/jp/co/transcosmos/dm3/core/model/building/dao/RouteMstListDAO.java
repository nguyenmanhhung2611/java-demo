package jp.co.transcosmos.dm3.core.model.building.dao;

import java.util.List;

import jp.co.transcosmos.dm3.core.vo.RouteMst;

public interface RouteMstListDAO {

	/**
	 * 路線マスタ取得
	 * 　group byで同じ線路を合併する
	 */
	public List<RouteMst> listRouteMst(Object[] params);
}
