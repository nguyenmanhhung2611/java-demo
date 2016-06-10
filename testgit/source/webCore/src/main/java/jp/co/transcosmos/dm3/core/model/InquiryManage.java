package jp.co.transcosmos.dm3.core.model;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;


/**
 * 問合せ情報を管理する Model クラス用インターフェース.
 * <p>
 * 問合せ情報を操作する model クラスはこのインターフェースを実装する事。<br/>
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
public interface InquiryManage {

	/**
	 * パラメータで渡された Form の情報で物件問合せ情報をを新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 物件問合せの入力値を格納した Form オブジェクト
	 * @param mypageUserId マイページのユーザーID （マイページログイン時に設定。　それ以外は null）
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 採番されたお問い合わせID
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public String addInquiry(InquiryForm inputForm, String mypageUserId, String editUserId)
			throws Exception;

	

	/**
	 * 問合せ情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、問合せヘッダ情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * ※管理画面からの利用を前提としているので、フロントから使用する場合はセキュリティに注意する事。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * 
	 * @return 該当件数
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchInquiry(InquirySearchForm searchForm) throws Exception;
	
	
	
	/**
	 * リクエストパラメータで渡された問合せID （主キー値）に該当する物件問合せ情報を復帰する。<br/>
	 * InquirySearchForm の inquiryId プロパティに設定された値を主キー値として情報を取得する。
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param searchForm　検索結果となる JoinResult
	 * 
	 * @return　DB から取得した物件問合せのバリーオブジェクト
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public InquiryInterface searchInquiryPk(String inquiryId) throws Exception;

	
	
	/**
	 * リクエストパラメータで渡された問合せID （主キー値）に該当する問合せ情報の対応ステータスを更新する。<br/>
	 * InquirySearchForm の inquiryId プロパティに設定された値を主キー値として対応ステータスを更新する。<br/>
	 * 更新日、更新者、対応ステータス以外は更新しないので、Form に設定しない事。（設定しても値は無視される。）<br/>
	 * <br/>
	 * @param inputForm 対応ステータスの入力値を格納した Form オブジェクト
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
 	 * @exception NotFoundException 更新対象なし
	 */
	public void updateInquiryStatus(InquiryStatusForm inputForm, String editUserId)
			throws Exception, NotFoundException;

	
	public InquiryForm createForm();
	
	public InquiryForm createForm(HttpServletRequest request);
	
	
}
