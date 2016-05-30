package jp.co.transcosmos.dm3.core.model;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * お知らせ情報を管理する Model クラス用インターフェース.
 * <p>
 * お知らせ情報を情報を操作する model クラスはこのインターフェースを実装する事。<br/>
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
public interface NewsManage {

	/**
	 * パラメータで渡された Form の情報でお知らせ情報を新規追加する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * お知らせ番号 は自動採番されるので、InformationForm の informationNo プロパティには値を設定しない事。<br/>
	 * <br/>
	 * @param inputForm お知らせ情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return お知らせ番号
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public String addInformation(NewsForm inputForm, String editUserId)
			throws Exception;



	/**
	 * パラメータで渡された Form の情報でお知らせ情報を更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * InformationForm の informationNo プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * @param inputForm お知らせ情報の入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 0 = 正常終了、-2 = 更新対象なし
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	public void updateInformation(NewsForm inputForm, String editUserId)
			throws Exception, NotFoundException;


	
	/**
	 * パラメータで渡された Form の情報でお知らせ情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * InformationSearchForm の informationNo プロパティに設定された値を主キー値として削除する。
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * <br/>
	 * @param inputForm お知らせ情報の検索値（削除対象となる informationNo）を格納した Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
    public void delNews(NewsForm inputForm) throws Exception;


	/**
	 * お知らせ情報を検索し、結果リストを復帰する。（管理画面用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、お知らせ情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * 検索条件として、公開対象区分が渡された場合、以下のデータを検索対象とする。<br/>
	 * 
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * 
	 * @return 該当件数
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public int searchAdminNews(NewsSearchForm searchForm) throws Exception;
	
	
	
	/**
	 * リクエストパラメータで渡された「お知らせ番号」 （主キー値）に該当するお知らせ情報を復帰する。（サイト TOP 用）<br/>
	 * 公開対象区分 = 「仮を含む全会員」が取得対象になる。<br/>
	 * また、システム日付が公開期間中であるお知らせ情報が取得対象となる。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param informationNo 取得対象となるお知らせ番号
	 * 
	 * @return　お知らせ情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public News searchTopNewsPk(String newsId)
			throws Exception;

}
