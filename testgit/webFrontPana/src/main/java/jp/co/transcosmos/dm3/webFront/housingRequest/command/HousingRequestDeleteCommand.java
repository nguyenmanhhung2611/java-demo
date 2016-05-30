package jp.co.transcosmos.dm3.webFront.housingRequest.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingRequest;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * 物件リクエスト入力画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   焦		  2015.04.22    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingRequestDeleteCommand implements Command {

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 物件情報用 Model オブジェクト */
	private HousingRequestManage housingRequestManage;

	/**
	 * 物件リクエスト情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param housingRequestManage
	 *            物件リクエスト情報用 Model オブジェクト
	 */
	public void setHousingRequestManage(HousingRequestManage housingRequestManage) {
		this.housingRequestManage = housingRequestManage;
	}

	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/** 物件情報用 Model オブジェクト */
	private PanaCommonManage panaCommonManage;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
	}

	/**
	 * 物件リクエスト入力画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);

		// ページ処理用のフォームオブジェクトを作成
		PanaHousingRequestForm housingRequestForm = factory
				.createPanaHousingRequestForm(request);

		// ログインユーザーの情報を取得
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(
			    request, response);
		// ユーザIDを取得
		String userId = "";
		if (loginUser != null) {
			userId = loginUser.getUserId().toString();
			model.put("loginFlg", 0);
		}else{
			userId = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response).getUserId().toString();
			model.put("loginFlg", 1);
		}

		model.put("housingRequestForm", housingRequestForm);

		// 伝達情報の物件リクエストID（housing_request_id）
		if(StringUtils.isEmpty(housingRequestForm.getHousingRequestId())){
			throw new RuntimeException("物件リクエストIDが指定されていません.");
		}

		// 物件リクエスト情報
		List<HousingRequest> requestList = new ArrayList<HousingRequest>();
		HousingRequest requestInfo = new PanaHousingRequest();
		String housingRequestId = housingRequestForm.getHousingRequestId();

		requestList = this.housingRequestManage.searchRequest(userId);
		int searchCount = 0;
		for(int i=0;i<requestList.size();i++){
			if(!StringUtils.isEmpty(housingRequestId)){
				if(housingRequestId.equals(requestList.get(i).getHousingRequestInfo().getHousingRequestId())){
					requestInfo = requestList.get(i);
					searchCount++;
				}
			}
		}

		if(searchCount == 0){
			return new ModelAndView("404");
		}

		// Form へ初期値を設定する。
		if(requestList != null && requestList.size()>0){
			housingRequestForm.setDefaultData(requestInfo);
		}

		// 都道府県リストの設定
		String prefName = this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd());
		model.put("prefName", prefName);


		// 取得したデータをレンダリング層へ渡す
		return new ModelAndView("success", model);
	}

}
