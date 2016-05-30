package jp.co.transcosmos.dm3.core.model;

import java.io.InputStream;
import java.util.List;

import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * CSV ファイルによるマスタメンテナンス model 用インターフェース.
 * <p/>
 * 駅マスタ、および、住所マスタをメンテナンスする model クラスはこのインターフェースを実装する事。<br/>
 * <p/>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.05	新規作成
 * </pre>
 * <p/>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public interface CsvMasterManage {

	/**
	 * マスタメンテナンス用 CSV の取り込み処理を実装する。<br/>
	 * <br/>
	 * @param inputStream CSV ファイルの InputStream オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 * @param errors エラーオブジェクトのリスト
	 * 
	 * @return 正常時 true、CSV のバリデーションでエラーがある場合 false
	 * 
	 * @exception Exception
	 */
	public boolean csvLoad(InputStream inputStream, String editUserId, List<ValidationFailure> errors)
			throws Exception;
	
}
