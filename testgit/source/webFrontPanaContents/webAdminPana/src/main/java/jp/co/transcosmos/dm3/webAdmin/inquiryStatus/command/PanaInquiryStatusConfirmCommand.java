package jp.co.transcosmos.dm3.webAdmin.inquiryStatus.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.inquiry.command.InquiryStatusConfirmCommand;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.InquiryInfo;
import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryStatusForm;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お問合せ情報入力確認画面
 * リクエストパラメータで渡されたお問合せ情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.04.09	新規作成
 *
 * 注意事項
 *
 * </pre>
*/
public class PanaInquiryStatusConfirmCommand extends InquiryStatusConfirmCommand implements Command {

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
	 * お問合せ情報入力確認画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = super.createModel(request);
		PanaInquiryStatusForm inputForm = (PanaInquiryStatusForm) model.get("inputForm");

		// 事前チェック
		 String inquiryType = check(inputForm);

		ModelAndView modelAndView = super.handleRequest(request, response);
		model = modelAndView.getModel();

        // 問合せオブジェクトを作成して、問合せ情報を取得する。
 		InquiryInfo inquiryInfo = getInquiryInfo(inputForm, inquiryType);

 		// 該当するデータが存在しない場合、該当無し画面を表示する。
 		if (inquiryInfo == null) {
 			return new ModelAndView("notFound", model);
 		}

 		// modelにお問合せ情報を格納する
 		putModel(inquiryInfo, model, inquiryType, inputForm);

 		// 対応内容を設定
 		inputForm.setShowAnswerText(PanaStringUtils.encodeHtml(inputForm.getAnswerText()));
 		model.put("inputForm", inputForm);

        return modelAndView;
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
