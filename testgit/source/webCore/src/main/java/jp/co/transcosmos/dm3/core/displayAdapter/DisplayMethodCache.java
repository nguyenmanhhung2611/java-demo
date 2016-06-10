package jp.co.transcosmos.dm3.core.displayAdapter;

import java.lang.reflect.Method;

/**
 * DisplayAdapter のキャッシュオブジェクト.
 * <br>
 * @author H.Mizuno
 *
 */
class DisplayMethodCache {

	/** リフレクションして取得したメソッド */
	private Method method;

	/** true の場合、リフレクション先は委譲先バリーオブジェクト */
	private boolean isTargetValueObject;

	
	
	/**
	 * コンストラクタ<br>
	 * <br>
	 * @param method 実行するメソッド
	 * @param isTargetValueObject 委譲先バリーオブジェクトを使用する場合は true を設定する。
	 */
	DisplayMethodCache(Method method, boolean isTargetValueObject){
		this.method = method;
		this.isTargetValueObject = isTargetValueObject;
	}

	
	/**
	 * リフレクションで実行するメソッドを取得する。<br>
	 * <br>
	 * @return 実行するメソッド
	 */
	public Method getMethod() {
		return method;
	}

	
	/**
	 * リフレクションするオブジェクトを判定する。<br>
	 * <br>
	 * @return true が復帰された場合、委譲先バリーオブジェクトを使用する。
	 */
	public boolean isTargetValueObject() {
		return isTargetValueObject;
	}

}
