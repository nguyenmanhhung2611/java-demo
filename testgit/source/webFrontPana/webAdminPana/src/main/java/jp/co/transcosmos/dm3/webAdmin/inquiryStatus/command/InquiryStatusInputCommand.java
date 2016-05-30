package jp.co.transcosmos.dm3.webAdmin.inquiryStatus.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.InquiryInfo;
import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryStatusForm;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お問合せ情報入力画面
 *
 * ・リクエストパラメータ（検索画面で入力した検索条件、表示ページ位置等）の受取を行う。
 * ・リクエストパラメータ（inquiryId）に該当するお問合せ情報、マイページ会員情報を取得し、画面
 *      表示する。
 *
 * 【リクエストパラメータ（command） が "back"の場合】
 *     ・リクエストパラメータで渡された入力値を入力画面に表示する。　（入力確認画面から復帰したケース。）
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"notFound" : 該当データが存在しない場合
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.04.08	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class InquiryStatusInputCommand implements Command {

	/** 物件問合せメンテナンスを行う Model オブジェクト */
	private PanaHousingInquiryManageImpl panaInquiryManager;
	/** 査定問合せメンテナンスを行う Model オブジェクト */
	private InquiryManage assessmentInquiryManager;
	/** 汎用問合せメンテナンスを行う Model オブジェクト */
	private InquiryManage generalInquiryManager;

	/**
	 * 物件問合せメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaInquiryManager
	 *            物件問合せメンテナンスの model オブジェクト
	 */
	public void setPanaInquiryManager(
			PanaHousingInquiryManageImpl panaInquiryManager) {
		this.panaInquiryManager = panaInquiryManager;
	}

	/**
	 * 査定問合せメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param assessmentInquiryManager
	 *            査定問合せメンテナンスの model オブジェクト
	 */
	public void setAssessmentInquiryManager(InquiryManage assessmentInquiryManager) {
		this.assessmentInquiryManager = assessmentInquiryManager;
	}

	/**
	 * 汎用問合せメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param generalInquiryManager
	 *            汎用問合せメンテナンスの model オブジェクト
	 */
	public void setGeneralInquiryManager(InquiryManage generalInquiryManager) {
		this.generalInquiryManager = generalInquiryManager;
	}

	/**
	 * お問合せ情報入力画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = createModel(request);
		PanaInquiryStatusForm inputForm = (PanaInquiryStatusForm) model.get("inputForm");

		// 事前チェック
		 String inquiryType = check(inputForm);

		// 問合せオブジェクトを作成して、問合せ情報を取得する。
		InquiryInfo inquiryInfo = getInquiryInfo(inputForm, inquiryType);

		// 該当するデータが存在しない場合、該当無し画面を表示する。
		if (inquiryInfo == null) {
			return new ModelAndView("notFound", model);
		}

		// modelにお問合せ情報を格納する
		putModel(inquiryInfo, model, inquiryType, inputForm);

		// command パラメータが "back" の場合、入力確認画面からの復帰なので、ＤＢから初期値を取得しない。
		// （リクエストパラメータから取得した値を使用する。）
		String command = inputForm.getCommand();
		if (command != null && "back".equals(command)) {

			return new ModelAndView("success", model);
		}

		// 取得した値を入力用 Form へ格納する。 （入力値の初期値用）
		inputForm.setDefaultData(inquiryInfo, inquiryType);

		return new ModelAndView("success", model);
	}

	/**
	 * 事前チェック:お問合せID（inquiryId）により、お問合せヘッダテーブルの読込する。<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);
		PanaInquiryStatusForm inputForm = factory.createPanaInquiryStatusForm(request);
		PanaInquirySearchForm searchForm = factory.createPanaInquirySearchForm(request);

		model.put("inputForm", inputForm);
		model.put("searchForm", searchForm);

		return model;
	}

	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            inputForm
	 * @throws Exception
	 */
	protected String check(PanaInquiryStatusForm inputForm) throws Exception {

		// 処理対象となる主キー値がパラメータで渡されていない場合、例外をスローする。
		if (StringValidateUtil.isEmpty(inputForm.getInquiryId())) {
			throw new RuntimeException("お問合せIDが指定されていません.");
		}

		// 事前チェック:お問合せID（inquiryId）により、お問合せヘッダテーブルの読込
		InquiryHeader inquiryHeader = panaInquiryManager.searchInquiryHeaderPk(inputForm.getInquiryId());

		// データの存在しない場合。
		if (inquiryHeader.getInquiryId() == null) {
			throw new NotFoundException();
		}

		return inquiryHeader.getInquiryType();
	}

	/**
	 * お問合せ情報を取得する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            inputForm
	 * @throws Exception
	 */
	protected InquiryInfo getInquiryInfo(PanaInquiryStatusForm inputForm, String inquiryType) throws Exception {

		// 問合せオブジェクトを作成する
		InquiryInfo inquiryInfo = new InquiryInfo();

		if (PanaCommonConstant.INQUIRY_TYPE_HOUSING.equals(inquiryType)) {
			// 物件問合せ情報を取得する
			InquiryInterface inquiryHousing = this.panaInquiryManager.searchHousingInquiryPk(inputForm.getInquiryId());

			return (InquiryInfo)inquiryHousing;

		} else if (PanaCommonConstant.INQUIRY_TYPE_GENERAL.equals(inquiryType)) {
			// 汎用問合せ情報を取得する
			InquiryInterface	inquiryGeneral = generalInquiryManager.searchInquiryPk(inputForm.getInquiryId());

			return (InquiryInfo)inquiryGeneral;

		} else if (PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT.equals(inquiryType)) {
			// 査定問合せ情報を取得する
			InquiryInterface	inquiryAssessment = assessmentInquiryManager.searchInquiryPk(inputForm.getInquiryId());

			return (InquiryInfo)inquiryAssessment;

		}

		return inquiryInfo;
	}

	/**
	 * 取得したお問合せ情報をmodel オブジェクトに格納する。<br/>
	 * <br/>
	 *
	 * @param model
	 *            model オブジェクト
	 * @param inquiryInfo
	 *            お問合せ情報
	 */
	protected void putModel(InquiryInfo inquiryInfo, Map<String, Object> model, String inquiryType, PanaInquiryStatusForm inputForm) {

		if (inquiryType.equalsIgnoreCase(PanaCommonConstant.INQUIRY_TYPE_HOUSING)) {

			// 取得した物件問合せ情報をmodel へ格納する。
			inputForm.setHousingData(inquiryInfo, model);

		} else if (inquiryType.equalsIgnoreCase(PanaCommonConstant.INQUIRY_TYPE_GENERAL)) {

			// 取得した汎用問合せ情報をmodel へ格納する。
			inputForm.setGeneralData(inquiryInfo, model);

		} else if (inquiryType.equalsIgnoreCase(PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT)) {

			// 取得した査定情報をmodel へ格納する。
			inputForm.setAssessmentData(inquiryInfo, model);
		}
	}
}
