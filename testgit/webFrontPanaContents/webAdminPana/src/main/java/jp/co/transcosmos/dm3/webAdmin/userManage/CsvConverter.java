package jp.co.transcosmos.dm3.webAdmin.userManage;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;


/**
 * 管理ユーザーCSV 出力時の変換処理.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢		2015/04/21	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class CsvConverter implements CsvValueConverter {

	private CodeLookupManager codeLookupManager;

	private int failCntToLock = 5;

	/** 管理者ログインＩＤ情報 情報用 DAO*/
	protected DAO<AdminLoginInfo> adminLoginInfoDAO;

	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}



	public void setFailCntToLock(int failCntToLock) {
		this.failCntToLock = failCntToLock;
	}



	/**
	 * @param adminLoginInfoDAO セットする adminLoginInfoDAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}



	/**
	 * CSV 出力値の変換処理<br/>
	 * <br/>
	 * @param columnName CSV 出力値のフィールド名（CsvConfig#dbColumns の値）
	 * @param value そのカラムの出力値
	 * @param thisOne CSV 行データ （Value オブジェクト、または、JoinResult）
	 *
	 * @return　変換した値
	 */
	@Override
	public Object convert(String columnName, Object value, Object thisOne) {

		// カラム名（CsvConfig の、dbColumns で指定した値）
		if (columnName.equals("adminLoginInfo.lastLogin")) {

			// 最終ログイン日 の場合、「YYYY/MM/DD hh:mm」データ形式に変換する。
			if (value != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				return sdf.format((Date)value);
			}
			return value;

		} else if (columnName.equals("adminRoleInfo.roleId")) {

			// ロールID の場合、codelookup で定義されている文字列に変換する。
			return this.codeLookupManager.lookupValueWithDefault("roleId", (String)value, (String)value);

		} else if (columnName.equals("adminLoginInfo.failCnt")) {

			// アカウントロック状態 の場合、
			// ログイン失敗回数が5以上　⇒　「ロック中」
			// 上記以外　⇒　「アンロック」
			if (value != null && (int)value >= this.failCntToLock) {
				return "ロック中";
			}
			return "アンロック";

		}

        // 新規登録日 の場合、日付をyyyy/MM/ddに変換する。
 		if (columnName.equals("adminLoginInfo.insDate") && value != null) {
 			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 			return sdf.format((Date)value);
 		}
        // 新規登録者 の場合、ユーザー名に変換する。
 		if (columnName.equals("adminLoginInfo.insUserId") && value != null) {
 			AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(value);
 			return adminLoginInfo == null? null: adminLoginInfo.getUserName();
 		}

 		// 最終更新日 の場合、日付をyyyy/MM/dd HH:mm:ssに変換する。
 		if (columnName.equals("adminLoginInfo.updDate") && value != null) {
 			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 			return sdf.format((Date)value);
 		}

        // 最終更新者 の場合、ユーザー名に変換する。
 		if (columnName.equals("adminLoginInfo.updUserId") && value != null) {
 			AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(value);
 			return adminLoginInfo == null? null: adminLoginInfo.getUserName();
 		}

		// 変換不要な場合は元の値を復帰
		return value;
	}

}
