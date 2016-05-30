package jp.co.transcosmos.dm3.webFront.inquiryHousing.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryHousingForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 物件のお問い合わせ完了画面
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
public class InquiryHousingCompCommand implements Command {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panaCommonManage;

	/** 物件問合せメンテナンスを行う Model オブジェクト */
	private PanaHousingInquiryManageImpl panaInquiryManager;

	/** 物件情報を行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 新規通知メールテンプレート */
	private ReplacingMail sendInquiryHousingMail;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

	/** 物件画像情報DAO */
	private DAO<HousingImageInfo> HousingImageInfoDAO;

	/** Panasonic用ファイル処理関連共通Util */
	private PanaFileUtil fileUtil;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
	}

	/**
	 * 物件問合せメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaInquiryManager
	 *            物件問合せメンテナンスの model オブジェクト
	 */
	public void setPanaInquiryManager(PanaHousingInquiryManageImpl panaInquiryManager) {
		this.panaInquiryManager = panaInquiryManager;
	}

	/**
	 * 物件情報を行う Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
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

	/**
	 * @param sendInquiryHousingMail
	 *            セットする sendInquiryHousingMail
	 */
	public void setSendInquiryHousingMail(ReplacingMail sendInquiryHousingMail) {
		this.sendInquiryHousingMail = sendInquiryHousingMail;
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

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		PanaInquiryHousingForm inputForm = (PanaInquiryHousingForm) model.get("inputForm");

		String command = inputForm.getCommand();
		if (command !=null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
		}

		// ログインユーザーの情報を取得
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
		String mypageUserId = null;
		String logFlg = "0";
		// loginUserがNULLの場合、非ログイン状態となる。
		if (loginUser == null) {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);
			logFlg = "1";
		} else {
			mypageUserId = (String)loginUser.getUserId();
		}
		// ユーザIDを取得
		String editUserId = (String)loginUser.getUserId();

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {

			// バリデーションエラーあり
			model.put("errors", errors);

			if(errors != null){
				// 都道府県リストの設定
				List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
				model.put("prefMstList", prefMstList);

				// 物件のお問い合わせ情報の取得
				Housing housing = panaHousingManager.searchHousingPk(inputForm.getSysHousingCd()[0]);
				// データの存在しない場合。
				if (housing == null) {
					return new ModelAndView("404", model);
				}

				// 閲覧権限があり、且つ、表示順、画像タイプ、枝番で昇順ソート後の画像を取得
				List<HousingImageInfo> housingImageInfoList = getHousingImageInfoList(housing, logFlg);

				// Form へ初期値を設定する。
				if ( housing != null ) {
					inputForm.setDefaultDataHousing(housing, commonParameters, housingImageInfoList, model, fileUtil);
				}
				model.put("inputForm", inputForm);
			}

			return new ModelAndView("input", model);
		}

		// 物件お問い合わせのDB登録処理
		String inquiryId = this.panaInquiryManager.addInquiry(inputForm, mypageUserId, editUserId);

		//お問い合わせ番号を設定
		inputForm.setInquiryId(inquiryId);
		//物件番号を設定
		inputForm.setHousingCd(inputForm.getSysHousingCd()[0]);

		// メールテンプレートで使用するパラメータを設定する。
		this.sendInquiryHousingMail.setParameter("inputForm",inputForm);
		this.sendInquiryHousingMail.setParameter("commonParameters",CommonParameters.getInstance(request));

		// メール送信
		this.sendInquiryHousingMail.send();

		model.put("inputForm", inputForm);

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
		if(logFlg == "1"){
			criteria.addWhereClause("roleId", PanaCommonConstant.ROLE_ID_PUBLIC);
		}
		criteria.addOrderByClause("sortOrder");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");

		List<HousingImageInfo> housingImageInfoList = this.HousingImageInfoDAO.selectByFilter(criteria);

		return housingImageInfoList;
	}
}
