package jp.co.transcosmos.dm3.core.displayAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.utils.ReflectionUtils;


/**
 * DisplayAdapter の抽象クラス.
 * バリーオブジェクトから取得した値を表示用に加工する Adapter の抽象クラス。<br>
 * このクラスを継承して作成する DisplayAdapter は、委譲先のメソッドをオーバーライドしたい場合、
 * 委譲先の getter と同名で、引数に T を渡すメソッドを追加する。<br>
 * <br>
 * 例えば、委譲先が、PrefMst クラスで、getPrefName() メソッドが存在する場合で、<br>
 * DisplayAdapter の getDisplayValue(prefMst, "prefName") を実行すると、<br>
 * DisplayAdapter　に、getPrefName(PrefMst prefMst)　が存在すればそのメソッド
 * が使用され、無ければ PrefMst の　getPrerName() が使用される。<br>
 * <br>
 * キャッシュを有効に機能させる為、DI コンテナで Bean 定義する時は、シングルトンで定義する事。<br> 
 * @param <T>
 * @param <T> 委譲先バリーオブジェクトの型
 */
public abstract class DisplayAdapter {

	private static final Log log = LogFactory.getLog(DisplayAdapter.class);
	

	/** getter メソッドキャッシュ */
	private ConcurrentMap<String, DisplayMethodCache> methodCache
			= new ConcurrentHashMap<>();



	/**
	 * 表示用加工処理<br>
	 * 表示加工に使用されるメソッドは、指定されたフィールド名を getter 形式のメソッド名に変換し、
	 * 引数として委譲先バリーオブジェクトを持つ構造である必要がある。<br>
	 * <br>
	 * <ol>
	 * <li>自身のクラスに該当するメソッドが存在するかをチェックする。</li>
	 * <li>存在する場合は自身のメソッドを使用して復帰する。（変換された結果を復帰）</li>
	 * <li>存在しない場合は委譲先のメソッドを使用して復帰する。（変換しない結果を復帰）</li>
	 * </ol>
	 * @param <T>
	 * @param fieldName 取得対象フィールド名
	 * @return　表示用オブジェクト
	 * @throws IllegalArgumentException 不正な引数でメソッドを実行 
	 * @throws IllegalAccessException 不正なメソッドアクセス
	 * @throws NoSuchMethodException メソッドが存在しない
	 */
	public <T> Object getDisplayValue(T targetVo, String fieldName)
			throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException {

		// getter キャッシュのキーを取得 （委譲先クラス名 + "#" + フィールド名
		String key = targetVo.getClass().getName() + "#" + fieldName;

		DisplayMethodCache methodAdapter = methodCache.computeIfAbsent(key, k -> {
			// キャッシュに存在しない場合、メソッド作成してキャッシュに格納する。
			try {
				// メソッドがキャッシュに無い場合、DisplayAdapter のメソッドを取得する。
				Method method = getAdapterMethod(fieldName, targetVo.getClass());
				return new DisplayMethodCache(method, false);

			} catch (NoSuchMethodException e) {
				// メソッドが DisplayAdapter に存在しない場合、委譲先のバリーオブジェクトからメソッドを取得する。
				try {
					Method method = ReflectionUtils.getGetterMethod(targetVo.getClass(), fieldName);
					return new DisplayMethodCache(method, true);
				} catch (Exception e1) {
					// 委譲先にもメソッドが存在しない場合はそのまま例外をスローする。
					throw new RuntimeException("field name " + fieldName + " is not found.");
				}
			}
		});


		// 取得したメソッドを実行
		try {
			if (methodAdapter.isTargetValueObject()){
				return methodAdapter.getMethod().invoke(targetVo);
			} else {
				return methodAdapter.getMethod().invoke(this, targetVo);
			}
		} catch (InvocationTargetException e){
			Throwable cause = e.getCause();
			log.error(cause.getMessage(), cause);
			throw new RuntimeException(fieldName + " execute error " + cause.getMessage());
		}
	}



	/**
	 * DisplayAdapter に定義された実行可能なメソッドを取得する。<br>
	 * <br>
	 * @param fieldName フィールド名
	 * @param voClass 委譲先バリーオブジェクトクラス
	 * @return 取得した Method オブジェクト
	 * @throws NoSuchMethodException　該当するメソッドが見つからない
	 */
	private Method getAdapterMethod(String fieldName, Class<?> voClass)
			throws NoSuchMethodException {

		Class<?>[] paramTypes = new Class[1];
		
		// バリーオブジェクトが継承されている可能性があるので、引数のバリーオブジェクトの型では実行可能な
		// メソッドが見つけられない場合がる。
		// その場合はバリーオブジェクトの親クラスを再帰的にたどり、実行可能なメソッドを検出する。
		Class<?> thisParamType = voClass;
		while ((thisParamType != null) && !thisParamType.equals(Object.class)){
			paramTypes[0] = thisParamType;
			try {
				return ReflectionUtils.getGetterMethod(this.getClass(), fieldName, paramTypes);
			} catch (NoSuchMethodException e) {
				thisParamType = thisParamType.getSuperclass();
			}
		}

		throw new NoSuchMethodException();
	}

}
