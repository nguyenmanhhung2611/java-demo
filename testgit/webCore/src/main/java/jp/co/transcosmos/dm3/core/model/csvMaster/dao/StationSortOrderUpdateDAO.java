package jp.co.transcosmos.dm3.core.model.csvMaster.dao;

public interface StationSortOrderUpdateDAO {

	/**
	 * 鉄道会社マスタの並び順全件更新
	 * 　並び順＋鉄道会社CD順に並べたものに対して
	 * 　rownumを利用して1～順につけた並び順で更新する
	 * 　※歯抜けになっていた並び順は詰まる
	 */
	public void updateRrMstSortOrder(Object[] objUpdData);

	
	
	/**
	 * 路線マスタの並び順全件更新
	 * 　並び順＋路線CD順に並べたものに対して
	 * 　rownumを利用して1～順につけた並び順で更新する
	 * 　※歯抜けになっていた並び順は詰まる
	*/
	public void updateRouteMstSortOrder(Object[] objUpdData);

}
