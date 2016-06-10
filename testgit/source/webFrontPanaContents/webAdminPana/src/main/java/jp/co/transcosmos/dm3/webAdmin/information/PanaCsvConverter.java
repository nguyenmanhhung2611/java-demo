package jp.co.transcosmos.dm3.webAdmin.information;

import jp.co.transcosmos.dm3.adminCore.information.CsvConverter;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.corePana.vo.Information;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * お知らせCSV 出力時の変換処理.
 * <p>
 *
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.04.21	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaCsvConverter extends CsvConverter implements CsvValueConverter {

	/** 管理者ログインＩＤ情報 情報用 DAO*/
	protected DAO<AdminLoginInfo> adminLoginInfoDAO;


	/**
	 * @param adminLoginInfoDAO セットする adminLoginInfoDAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}


	/**
	 * CSV 出力値の変換処理<br/>
	 * <br/>
	 *
	 * @param columnName
	 *            CSV 出力値のフィールド名（CsvConfig#dbColumns の値）
	 * @param value
	 *            そのカラムの出力値
	 * @param thisOne
	 *            CSV 行データ （Value オブジェクト、または、JoinResult）
	 *
	 * @return　変換した値
	 */
	@Override
	public Object convert(String columnName, Object value, Object thisOne) {

		// 対象会員 の場合、会員名（姓）+　会員名（名）
		// 退会した場合、特定会員向けのお知らせについては、氏名に「退会済み」と記載
		if (columnName.equals("memberInfo.memberLname")) {
			JoinResult result = (JoinResult) thisOne;
			Information information = (Information) result.getItems().get(
					"information");
			MemberInfo memberInfo = (MemberInfo) result.getItems().get(
					"memberInfo");

			if (information.getDspFlg().equals("0")) {
				value = "サイトTOP";
			} else if (information.getDspFlg().equals("1")) {
				value = "全員";
			} else if (information.getDspFlg().equals("2")) {
				if (!StringValidateUtil.isEmpty(memberInfo.getMemberLname())) {
					value = memberInfo.getMemberLname() + " "
							+ memberInfo.getMemberFname();
				} else {
					value = "退会済み";
				}
			}

			return value;
		}

		// メール送信フラグ 0：未送信、1：送信
		if (columnName.equals("information.sendFlg")) {
			if (value != null) {
				if ("0".equals(value)) {
					value = "未送信";
				} else if ("1".equals(value)) {
					value = "送信";
				}
			}

			return value;
		}

		// 最終更新者：ユーザー名称
		if (columnName.equals("information.updUserId")) {
			JoinResult result = (JoinResult) thisOne;
			AdminLoginInfo adminLoginInfo = (AdminLoginInfo) result.getItems()
					.get("adminLoginInfo");

			return adminLoginInfo.getUserName();
		}

        // 新規登録者 の場合、ユーザー名に変換する。
 		if (columnName.equals("information.insUserId") && value != null) {
 			AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(value);
 			return adminLoginInfo == null? null: adminLoginInfo.getUserName();
 		}

		return super.convert(columnName, value, thisOne);
	}

}
