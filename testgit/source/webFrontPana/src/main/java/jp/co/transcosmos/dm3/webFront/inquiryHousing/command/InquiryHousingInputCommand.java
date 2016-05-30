package jp.co.transcosmos.dm3.webFront.inquiryHousing.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryHousingForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.servlet.ModelAndView;

/**
 * 物件のお問い合わせ入力画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ    2015.04.23   新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class InquiryHousingInputCommand implements Command {

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 物件情報を行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** マイページユーザーの情報を管理する Model オブジェクト */
	private MypageUserManage mypageUserManage;

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panaCommonManage;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

	/** 物件画像情報DAO */
	private DAO<HousingImageInfo> HousingImageInfoDAO;

	/** Panasonic用ファイル処理関連共通Util */
	private PanaFileUtil fileUtil;

	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * 物件情報を行う Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            物件情報を行う Model オブジェクト
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * マイページユーザーの情報を管理する Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param MypageUserManage マイページユーザーの情報を管理する Model オブジェクト
	 */
	public void setMypageUserManage(MypageUserManage mypageUserManage) {
		this.mypageUserManage = mypageUserManage;
	}

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
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
	 * 物件画像情報DAOを設定する。<br/>
	 * <br/>
	 * @param housingImageInfoDAO 物件画像情報DAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		HousingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 *Panasonic用ファイル処理関連共通Utilを設定する。<br/>
	 * <br/>
	 * @param fileUtil Panasonic用ファイル処理関連共通Util
	 */
	public void setFileUtil(PanaFileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}

	/**
	 * 物件のお問い合わせ入力画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		PanaInquiryHousingForm inputForm = (PanaInquiryHousingForm) model.get("inputForm");

		// 処理対象となる主キー値がパラメータで渡されていない場合、例外をスローする。
		if (inputForm.getSysHousingCd() == null || inputForm.getSysHousingCd()[0] == "") {
			throw new RuntimeException("システム物件CDが指定されていません.");
		}

		// ログインユーザーの情報を取得
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// loginUserがNULLの場合、非ログイン状態となる。
		String logFlg = "1";
		if (loginUser == null) {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);
			logFlg = "0";
		}

		// 物件のお問い合わせ情報の取得
		Housing housing = panaHousingManager.searchHousingPk(inputForm.getSysHousingCd()[0]);
		// データの存在しない場合。
		if (housing == null) {
			return new ModelAndView("404", model);
		}
		// 閲覧権限があり、且つ、表示順、画像タイプ、枝番で昇順ソート後の画像を取得
		List<HousingImageInfo> housingImageInfoList = getHousingImageInfoList(housing, logFlg);
		// Form へ初期値を設定する。
		if (housing != null) {
			inputForm.setDefaultDataHousing(housing, commonParameters, housingImageInfoList, model, fileUtil);
		}
		model.put("inputForm", inputForm);

		// 都道府県リストの設定
		List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
		model.put("prefMstList", prefMstList);

		// command パラメータが "back" の場合、入力確認画面からの復帰なので、ＤＢから初期値を取得しない。
		// （リクエストパラメータから取得した値を使用する。）
		String command = inputForm.getCommand();
		if (command != null && "back".equals(command)) {

			return new ModelAndView("success", model);
		}

		if ("1".equals(logFlg)) {
			// ログインした状態の場合
			// 画面初期化表示:マイページ会員情報を取得して、設定
			getUserInfo(loginUser, inputForm);
		}

		// お問合せ内容種別CD
		String[] inquiryDtlType = {"001"};
		inputForm.getInquiryHeaderForm().setInquiryDtlType(inquiryDtlType);

		// 取得したデータをレンダリング層へ渡す
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

		Map<String, Object> model = new HashMap<String, Object>();

		// ページ処理用のフォームオブジェクトを作成
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);

		PanaInquiryHousingForm inputForm = factory.createPanaInquiryHousingForm(request);
		InquiryHeaderForm inquiryHeaderForm = factory.createInquiryHeaderForm(request);
		inputForm.setCommonInquiryForm(inquiryHeaderForm);

		model.put("inputForm", inputForm);

		return model;
	}

	/**
	 * 画面初期化表示:マイページ会員情報を取得して、設定する。<br/>
	 * <br/>
	 *
	 * @param loginUser
	 *            loginUser
	 * @throws Exception
	 */
	private void getUserInfo(LoginUser loginUser, PanaInquiryHousingForm inputForm) throws Exception {

		String userId = null;
		JoinResult userInfo = null;
		if (loginUser != null) {
			userId = String.valueOf(loginUser.getUserId());
			userInfo = mypageUserManage.searchMyPageUserPk(userId);
		}
		// Form へ初期値を設定する。
		inputForm.setDefaultDataUserInfo(userInfo, inputForm.getInquiryHeaderForm());
	}

	/**
	 *閲覧権限があり、且つ、表示順、画像タイプ、枝番で昇順ソート後の画像を取得する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            inputForm
	 * @throws Exception
	 */
	private List<HousingImageInfo> getHousingImageInfoList(Housing housing, String logFlg) {

		// 閲覧権限があり、且つ、表示順、画像タイプ、枝番で昇順ソート後の1番目の画像を表示
		// 物件基本情報を取得する。
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
		criteria.addWhereClause("roleId", null, DAOCriteria.IS_NULL, true);
		if ("0".equals(logFlg)){
			criteria.addWhereClause("roleId", PanaCommonConstant.ROLE_ID_PUBLIC);
		}
		criteria.addOrderByClause("sortOrder");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");

		List<HousingImageInfo> housingImageInfoList = this.HousingImageInfoDAO.selectByFilter(criteria);

		return housingImageInfoList;
	}
}
