package jp.co.transcosmos.dm3.corePana.model.building.dao;

import java.util.List;

import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;

public interface PanaRouteMstListDAO {

	/**
	 * �H���}�X�^�擾
	 * �@group by�œ������H����������
	 */
	public List<RouteMst> listRouteMst(Object[] params);

	public List<RouteMst> listRouteMst2(Object[] params);

	/**
	 * �S����Ѓ}�X�^�擾
	 * �@group by�œ������H����������
	 */
	public List<RrMst> listRrMst(Object[] params);
}
