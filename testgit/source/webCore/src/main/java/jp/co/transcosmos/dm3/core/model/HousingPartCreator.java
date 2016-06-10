package jp.co.transcosmos.dm3.core.model;

import jp.co.transcosmos.dm3.core.model.housing.Housing;


/**
 * こだわり条件を作成するクラスのインターフェース.
 * <p>
 * こだわり条件を作成するクラスは、このインターフェースを実装する事。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.09	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public interface HousingPartCreator {

	/**
	 * 物件 model の proxy は、引数として 実行した model のメソッド名を引き渡す。<br/>
	 * このメソッドの戻り値が false の場合、createPart() の実行をキャンセルする。<br/> 
	 * このメソッドの戻り値が true の場合、createPart() を実行する。<br/>
	 * <br/>
	 * @return 実行したくない model のメソッド名の場合、false を復帰する。
	 */
	public boolean isExecuteMethod(String methodName);

	
	/**
	 * こだわり条件の作成処理を実装する。<br/>
	 * <br/>
	 * @param housing こだわり条件を作成する物件ボブジェクト
	 * @throws Exception 実装先がスローする任意の例外
	 */
	public void createPart(Housing housing) throws Exception;
}
