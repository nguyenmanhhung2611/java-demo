package jp.co.transcosmos.dm3.core.model.building.dao;

import java.util.List;

import jp.co.transcosmos.dm3.core.vo.StationMst;

public interface StationMstListDAO {

	/**
	 * 駅マスタ取得
	 * 　group byで同じ駅を合併する
	 */
	public List<StationMst> listStationMst(Object[] params);
}
