package jp.co.transcosmos.dm3.webFront.information.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * マイページ画面
 * リクエストパラメータで渡された物件詳細のバリデーションを行い、物件詳細画面を表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ     2015.04.13  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class InformationDetailedCommand implements Command {

	/** お知らせメンテナンスを行う Model オブジェクト */
	private InformationManage informationManager;

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/** マイページユーザーの情報管理用 Model */
	private MypageUserManage mypageUserManager;

	/**
	 * お知らせメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param informationManager お知らせメンテナンスの model オブジェクト
	 */
	public void setInformationManager(InformationManage informationManager) {
		this.informationManager = informationManager;
	}
	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * マイページユーザーの情報管理用 Model（core） を設定する。<br/>
	 * <br/>
	 *
	 * @param mypageUserManager
	 *            マイページユーザーの情報管理用 Model（core）
	 */
	public void setMypageUserManager(MypageUserManage mypageUserManager) {
		this.mypageUserManager = mypageUserManager;
	}

	/**
	 * マイページ画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);

		// 共通パラメータ情報
		model.put("commonParameters", this.commonParameters);

		// フロント側のユーザ情報
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
		String loginUserId = (String)loginUser.getUserId();
		// お知らせ番号
		String informationNo = request.getParameter("informationNo");
		if(StringUtils.isEmpty(informationNo)) {
			throw new RuntimeException("お知らせ番号が指定されていません.");
		}

		// お客様へのお知らせの取得
		Information information = this.informationManager.searchMyPageInformationPk(informationNo, loginUserId);

		if (information == null) {
			return new ModelAndView("404");
		}
		// マイページユーザー情報の取得
		JoinResult joinResult = this.mypageUserManager.searchMyPageUserPk(loginUserId);
		MemberInfo memberInfo = (MemberInfo)joinResult.getItems().get("memberInfo");

		// 処理実行を行う。
		model.put("information", information);
		model.put("memberInfo", memberInfo);

		return new ModelAndView("success", model);
	}

	/**
	 * リクエストパラメータから outPutForm オブジェクトを作成する。<br/>
	 * 生成した outPutForm オブジェクトは Map に格納して復帰する。<br/>
	 * key = フォームクラス名（パッケージなし）、Value = フォームオブジェクト <br/>
	 *
	 * @param request
	 *            HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();
		return model;
	}
}
