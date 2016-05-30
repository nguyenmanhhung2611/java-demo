package jp.co.transcosmos.dm3.webFront.assessment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;

/**
 * <pre>
 * 郵便番号により、住所を検索する。
 *
 * 【復帰する View 名】
 *	・"success" : 正常終了
 *
 * 担当者	   修正日	  修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢	   2015.04.27  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class GetAddressByZipCommand implements Command {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 検索パラメータ受信
		String zip = PanaStringUtils.changeToHankakuNumber(request.getParameter("zip"));

		 // 市区町村マスタを取得する
		String[] zipMst = panamCommonManager.getZipToAddress(zip);

		if("0".equals(zipMst[0])){
			zipMst[2] = panamCommonManager.getAddressName(zipMst[2]);
		}

		return new ModelAndView("success", "zipMst", zipMst);
	}

}
