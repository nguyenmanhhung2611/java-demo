package jp.co.transcosmos.dm3.core.model;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.feature.form.FeatureSearchForm;
import jp.co.transcosmos.dm3.core.vo.FeaturePageInfo;


/**
 * 特集の情報を管理する Model クラス用インターフェース.
 * <p>
 * 特集の情報を操作する model クラスはこのインターフェースを実装する事。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.16	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public interface FeatureManage {

	
	/**
	 * 指定されたグループＩＤで特集情報のリストを取得する。<br/>
	 * <br/>
	 * @param featureGroupId 特集グループID
	 * 
	 * @return　特集ページ情報のリストオブジェクト
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public List<FeaturePageInfo> searchFeature(String featureGroupId) throws Exception;
	
	
	
	/**
	 * 指定された検索条件（特集ID、およびページ位置）で特集に該当する物件の情報を取得する。<br/>
	 * <br/>
	 * @param searchForm 特集の検索条件（特集ID、ページ位置）
	 * 
	 * @return 該当件数
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchHousing (FeatureSearchForm searchForm) throws Exception;
		
}
