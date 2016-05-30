package jp.co.transcosmos.dm3.webAdmin.utils;

import java.util.Date;

import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.corePana.vo.AdminLog;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.utils.CommonLogging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 管理サイトログ出力クラス 
 * ログテーブルへの出力を対応する。
 *　使用する場合は、Spring の DI コンテナでシングルトンで定義する事。
 *　（直接インスタンス化した場合や、scope がプロトタイプの場合は、マルチスレッド環境で動作保証しない。）
 * 
 */
public class AdminLogging implements CommonLogging {

    private static final Log log = LogFactory.getLog(AdminLogging.class);
    
    /** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;

	/** ログテーブルDAO */
	protected DAO<AdminLog> logTableDao;
	
	/**
	 * ログ出力処理<br>
	 * <br>
	 * @param msg 出力メッセージ
	 * @return true=正常終了、false=エラー
	*/
	public boolean write(String msg) {
		return logWriter(msg, null, null);
	}
	
    /**
	 * ログ出力処理<br>
	 * <br>
	 * @param msg 出力メッセージ
	 * @param adminUserId 管理者ユーザーID
	 * @param functionCd 機能コード
	 * @return true=正常終了、false=エラー
	 */
	public boolean write(String msg, String adminUserId, String functionCd) {
		return logWriter(msg, adminUserId, functionCd);
	}
	
	/**
	 * ログ出力処理<br>
	 * <br>
	 * @param msg 出力メッセージ
	 * @param adminUserId 管理者ユーザーID
	 * @param functionCd 機能コード
	 * @return true=正常終了、false=エラー
	 */
	private boolean logWriter(String msg, String adminUserId, String functionCd){
		// ログテーブルのオブジェクト
		AdminLog logTableVo = buildLogTableVo(msg, adminUserId, functionCd);

		// ログテーブルへの書き込み
		this.logTableDao.insert(new AdminLog[] { logTableVo });
		return true;
	}
	
	/**
	 * ログオブジェクトを作成
	 * @param msg 出力メッセージ
	 * @param adminUserId 管理者ユーザーID
	 * @param functionCd 機能コード
	 * @return ログオブジェクト
	 */
	protected AdminLog buildLogTableVo(String msg, String adminUserId, String functionCd) {
		AdminLog vo = (AdminLog)this.valueObjectFactory.getValueObject("AdminLog");
		
		vo.setMsg(msg);
		vo.setInsDate(new Date());
		vo.setAdminUserId(adminUserId);
		vo.setFunctionCd(functionCd);
		return vo;
	}

	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	public void setLogTableDao(DAO<AdminLog> logTableDao) {
		this.logTableDao = logTableDao;
	}
}
