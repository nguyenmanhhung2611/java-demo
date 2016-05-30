package jp.co.trancosmos.dm3.corePana.model.news.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * お知らせ情報の検索パラメータ、および、画面コントロールパラメータ受取り用フォーム
 * 検索条件となるリクエストパラメータの取得や、ＤＢ検索オブジェクトの生成を行う。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * zhang		2015.04.21	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class PanaNewsSearchForm extends NewsSearchForm implements Validateable {

	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
	 * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意
	 * する事。<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();
		super.validate(errors);

		return (startSize == errors.size());

	}

	/**
	 * 検索条件オブジェクトを作成する。<br/>
	 * PagingListForm に実装されている、ページ処理用の検索条件生成処理を拡張し、受け取ったリクエスト
	 * パラメータによる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、管理ユーザー情報のテーブルとして、News 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 検索条件オブジェクト
	 */
	@Override
	public DAOCriteria buildCriteria(){

		DAOCriteria criteria = super.buildCriteria();
		criteria.addOrderByClause("newsId", false);

        return criteria;

	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 *
	 * @param lengthUtils
	 *            レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	PanaNewsSearchForm(LengthValidationUtils lengthUtils) {
		super(lengthUtils);

	}
}
