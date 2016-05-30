package jp.co.transcosmos.dm3.corePana.model.information;

import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * <pre>
 * お知らせメンテナンス用 Model クラス
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.05	新規作成
 *
 * 注意事項
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。
 *
 * </pre>
 */
public class PanaInformationManageImpl extends InformationManageImpl {

	/**
	 * マイページ TOP に表示するお知らせ情報取得に使用する検索条件を生成する。<br/>
	 * 公開対象区分 = 「全本会員」、または、「個人」が取得対象になるが、「個人」の場合、お知らせ公開先情報
	 * のユーザーID が引数と一致する必要がある。<br/>
	 * また、システム日付が公開期間中であるお知らせ情報が取得対象となる。<br/>
	 * <br/>
	 * @return マイページ TOP に表示する、お知らせ情報のリスト
	 */
	@Override
	protected DAOCriteria buildMyPageInformationCriteria(String userId) {
		DAOCriteria mainCriteria = super.buildMyPageInformationCriteria(userId);
		mainCriteria.addOrderByClause("insDate",false);
		return mainCriteria;
	}
}
