package jp.co.transcosmos.dm3.core.model.building.dao;

import java.util.List;

import jp.co.transcosmos.dm3.core.vo.StationMst;

public interface StationMstListDAO {

	/**
	 * �w�}�X�^�擾
	 * �@group by�œ����w����������
	 */
	public List<StationMst> listStationMst(Object[] params);
}
