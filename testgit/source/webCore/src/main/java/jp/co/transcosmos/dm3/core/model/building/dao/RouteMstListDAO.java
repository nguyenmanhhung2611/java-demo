package jp.co.transcosmos.dm3.core.model.building.dao;

import java.util.List;

import jp.co.transcosmos.dm3.core.vo.RouteMst;

public interface RouteMstListDAO {

	/**
	 * �H���}�X�^�擾
	 * �@group by�œ������H����������
	 */
	public List<RouteMst> listRouteMst(Object[] params);
}
