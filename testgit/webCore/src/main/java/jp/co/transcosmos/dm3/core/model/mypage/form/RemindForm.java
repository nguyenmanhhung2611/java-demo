package jp.co.transcosmos.dm3.core.model.mypage.form;

import java.util.List;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * マイページ会員のパスワード問合せ、および、パスワード変更用フォーム.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.31	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class RemindForm implements Validateable {

	/** メールアドレス */
	private String email;



	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * 
	 */
	protected RemindForm(){
		super();
	}

	
	
	/**
	 * 入力されたメールアドレスが存在するかをチェックする検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、マイページ会員情報のテーブルとして、MemberInfo 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 主キーとなる検索条件オブジェクト
	 */
	public DAOCriteria buildMailCriteria(){
		// 検索条件オブジェクトを取得
		DAOCriteria criteria = new DAOCriteria();

		// ユーザー管理をしているテーブルのメールアドレスをチェックする。 
		criteria.addWhereClause("email", this.email);

		return criteria;
	}

	
	
	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */

	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();
		
		// TODO 入力ミスの問題もあるので、メールアドレスの整合性チェックのみは実装する事。

		return (startSize == errors.size());
	}



	/**
	 * メールアドレスを取得する。<br/>
	 * <br/>
	 * @return メールアドレス
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * メールアドレスを設定する。<br/>
	 * <br/>
	 * @param email メールアドレス （問合せ登録用）
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
