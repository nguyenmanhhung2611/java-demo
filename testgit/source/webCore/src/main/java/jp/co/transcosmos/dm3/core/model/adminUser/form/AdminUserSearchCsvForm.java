package jp.co.transcosmos.dm3.core.model.adminUser.form;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * 管理ユーザー CSV 出力の検索パラメータ受取り用フォーム.
 * <p>
 * 管理ユーザー検索用の Form を継承して作成した CSV 出力用の Form クラス。<br/>
 * buildCriteria() 実行時、PagingListForm　の buildCriteria() を処理しない。
 * よって、全データが取得対象となる。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	Shamaison を参考に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 検索対象となる全データが Form に格納されるので、CSV 出力以外では使用しない事。
 * 
 */
public class AdminUserSearchCsvForm extends AdminUserSearchForm {

	
	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected AdminUserSearchCsvForm() {
		super();
	}
	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected AdminUserSearchCsvForm(LengthValidationUtils lengthUtils) {
		super(lengthUtils);
	}



	/**
	 * 検索条件オブジェクトを作成する。<br/>
	 * CSV 出力用なのでページ処理を除外した検索条件を生成する。<br/>
	 * <br/>
	 * @return 検索条件オブジェクト
	 */
	@Override
	public DAOCriteria buildCriteria(){
		return buildCriteria(new DAOCriteria());
	}

}
