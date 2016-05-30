package jp.co.transcosmos.dm3.webAdmin.memberInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.csv.CsvValueConverter;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;


/**
 * 会員一覧CSV 出力時の変換処理.
 * <p>
 * <pre>
 * 担当者		  修正日		修正内容
 * -------------- ----------- -----------------------------------------------------
 * tangtianyun	  2015.04.20	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class CsvConverter implements CsvValueConverter {

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	/** 管理者ログインＩＤ情報 情報用 DAO*/
	protected DAO<AdminLoginInfo> adminLoginInfoDAO;

	/**
     * 共通コード変換オブジェクトを設定する。<br/>
     * <br/>
     * @param codeLookupManager
     */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
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
	 * @param targetValue CSV 出力値
	 * @param fieldName CSV 出力値のフィールド名（CsvConfig#dbColumns の値）
	 * @return　変換した値
	 */
    @Override
    public Object convert(String columnName, Object value, Object thisOne) {
    	// 登録経路 の場合、codelookup で定義されている文字列に変換する。
        if (columnName.equals("memberInfo.entryRoute") && value != null) {
            // 登録経路 の場合、codelookup で定義されている文字列に変換する。
            return this.codeLookupManager.lookupValueWithDefault("entryRoute", (String)value, (String)value);
        }
        // 流入経路 の場合、codelookup で定義されている文字列に変換する。
        if (columnName.equals("memberInfo.refCd") && value != null) {
            // 流入経路 の場合、codelookup で定義されている文字列に変換する。
            return this.codeLookupManager.lookupValueWithDefault("refCd", (String)value, (String)value);
        }
        // 居住状態 の場合、codelookup で定義されている文字列に変換する。
        if (columnName.equals("memberInfo.residentFlg") && value != null) {
            // 居住状態 の場合、codelookup で定義されている文字列に変換する。
            return this.codeLookupManager.lookupValueWithDefault("residentFlg", (String)value, (String)value);
        }
        // メール配信希望 の場合、codelookup で定義されている文字列に変換する。
        if (columnName.equals("memberInfo.mailSendFlg") && value != null) {
            // メール配信希望 の場合、codelookup で定義されている文字列に変換する。
            return this.codeLookupManager.lookupValueWithDefault("mailSendFlg", (String)value, (String)value);
        }
    	// 住所2の場合、住所2を住所・市区町村名＋住所・町名番地その他に変換する。
    	if (columnName.equals("memberInfo.address") && value != null) {
    		JoinResult result = (JoinResult)thisOne;
    		MemberInfo memberInfo = (MemberInfo)result.getItems().get("memberInfo");
    		String addressOther = "";
    		if (!StringValidateUtil.isEmpty(memberInfo.getAddressOther())) {
    			addressOther = memberInfo.getAddressOther();
    		}
    		return value + addressOther;
    	}
        // 新規登録日 の場合、日付をyyyy/MM/ddに変換する。
 		if (columnName.equals("memberInfo.insDate") && value != null) {
 			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 			return sdf.format((Date)value);
 		}
        // 新規登録者 の場合、ユーザー名に変換する。
 		if (columnName.equals("memberInfo.insUserId") && value != null) {
    		JoinResult result = (JoinResult)thisOne;
    		MemberInfo memberInfo = (MemberInfo)result.getItems().get("memberInfo");
    		if (memberInfo.getUserId().equals(value)) {
    			return "本人";
    		} else {
     			AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(value);
     			return adminLoginInfo == null? null: adminLoginInfo.getUserName();
    		}
 		}

 		// 最終更新日 の場合、日付をyyyy/MM/dd HH:mm:ssに変換する。
 		if (columnName.equals("memberInfo.updDate") && value != null) {
 			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 			return sdf.format((Date)value);
 		}
        // 最終更新者 の場合、ユーザー名に変換する。
 		if (columnName.equals("memberInfo.updUserId") && value != null) {
    		JoinResult result = (JoinResult)thisOne;
    		MemberInfo memberInfo = (MemberInfo)result.getItems().get("memberInfo");
    		if (memberInfo.getUserId().equals(value)) {
    			return "本人";
    		} else {
     			AdminLoginInfo adminLoginInfo = this.adminLoginInfoDAO.selectByPK(value);
     			return adminLoginInfo == null? null: adminLoginInfo.getUserName();
    		}
 		}

        // 変換不要な場合は元の値を復帰
        return value;
    }

}
