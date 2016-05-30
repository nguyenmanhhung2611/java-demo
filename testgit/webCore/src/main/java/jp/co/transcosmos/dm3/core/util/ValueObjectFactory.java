package jp.co.transcosmos.dm3.core.util;

import java.lang.reflect.Array;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * バリーオブジェクトのインスタンスを取得する Factory クラス.
 * <p>
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	新規作成
 * H.Mizuno		2015.03.12	リフレクション対応に仕様を変更
 * </pre>
 * <p>
 * 注意事項<br/>
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 * 
 */
public class ValueObjectFactory {

	private static final Log log = LogFactory.getLog(ValueObjectFactory.class);
	
	/** VO を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "valueObjectFactory";

	/** VO のパッケージ階層 */
	protected String packageName = "jp.co.transcosmos.dm3.core.vo";



	/**
	 * ValueObjectFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 valueObjectFactory で定義された ValueObjectFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、valueObjectFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * 
	 * @return ValueObjectFactory、または継承して拡張したクラスのインスタンス
	 */
	public static ValueObjectFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (ValueObjectFactory)springContext.getBean(ValueObjectFactory.FACTORY_BEAN_ID);
	}

	
	
	/**
	 * 指定されたクラス名（シンプル名）に該当するバリーオブジェクトのインスタンスを復帰する。<br/>
	 * <br/>
	 * バリーオブジェクトを拡張した場合、このメソッドをオーバーライドして拡張したクラスを復帰する様にカスタマイズする事。<br/>
	 * <br/>
	 * @param shortClassName　 クラス名（シンプル名）
	 * 
	 * @return　バリーオブジェクトのインスタンス
	 * 
	 */
	public Object getValueObject(String shortClassName) {
		return buildValueObject(shortClassName);
	}



	/**
	 * 指定されたクラス名（シンプル名）に該当するバリーオブジェクトのインスタンスを復帰する。<br/>
	 * 指定された件数分の配列オブジェクトを復帰する。<br/>
	 * <br/>
	 * バリーオブジェクトを拡張した場合、このメソッドをオーバーライドして拡張したクラスを復帰する様にカスタマイズする事。<br/>
	 * <br/>
	 * @param shortClassName　 クラス名（シンプル名）
	 * @param cnt　配列の数
	 * 
	 * @return　バリーオブジェクトのインスタンスの配列
	 * 
	 */
	public Object[] getValueObject(String shortClassName, int cnt) {

		// バリーオブジェクトを生成し、インスタンスを取得する。
		Object target = buildValueObject(shortClassName);

		// そのインスタンスのクラスから配列をリフレクションする。
		Object[] objects = (Object[])Array.newInstance(target.getClass(), cnt);

		// 配列の中にリフレクションしたインスタンスを設定する。
		for (int i=0; i < cnt; ++i){
			try {
				objects[i] = target.getClass().newInstance();

			} catch (InstantiationException | IllegalAccessException e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage());
			}
		}
		return objects;
	}



	/**
	 * バリーオブジェクトのインスタンスを生成する、実質的な処理<br/>
	 * <br/>
	 * @param shortClassName クラス名（シンプル名）
	 * 
	 * @return 指定されたクラスのインスタンス
	 */
	protected Object buildValueObject(String shortClassName){

		try {
			// クラス名からインスタンスを生成する。
			Class<?> cls = Class.forName(this.packageName + "." + shortClassName);
			Object vo = cls.newInstance();

			// Default 値を設定
			setDefaultValue(vo);

			return vo;

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
		
	}



	/**
	 * 特定の初期値を設定すべき Value オブジェクトかをチェックする。<br/>
	 * 該当する Value オブジェクトの場合、初期化処理を実行する。<br/>
	 * <br/>
	 * @param vo Value オブジェクト
	 */
	protected void setDefaultValue(Object vo){
		
		if (vo instanceof AdminLoginInfo){
			initAdminLoginInfo((AdminLoginInfo)vo);
		}
	}
	
	
	
	/**
	 * 管理者ログインID情報の初期化処理<br/>
	 * <br/>
	 * @param adminLoginInfo 管理者ログインID情報の Value オブジェクト
	 */
	protected void initAdminLoginInfo(AdminLoginInfo adminLoginInfo){
		// DB 定義側でデフォルト設定していても、バリーオブジェクトの値が null の場合、例外が発生する。
		// よって、DB 定義側でデフォルト設定している場合、このタイミングで初期値を設定しておく。
		adminLoginInfo.setFailCnt(0);
	}
	
}
