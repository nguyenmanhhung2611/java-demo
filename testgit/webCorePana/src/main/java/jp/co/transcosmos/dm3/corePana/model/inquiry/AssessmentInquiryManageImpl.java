package jp.co.transcosmos.dm3.corePana.model.inquiry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryManageUtils;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentInputForm;
import jp.co.transcosmos.dm3.corePana.vo.InquiryAssessment;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * 査定問合せ model の実装クラス.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.04.02	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class AssessmentInquiryManageImpl implements InquiryManage{

	/** Value オブジェクトの Factory */
	private ValueObjectFactory valueObjectFactory;

	/** 問合せヘッダ用共通処理 */
	private InquiryManageUtils inquiryManageUtils;

	/** 査定情報用 DAO */
	private DAO<InquiryAssessment> inquiryAssessmentDAO;

	/** 問合せアンケート情報用 DAO */
	private DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO;

	/** 査定問合情報取得用 DAO */
	private DAO<JoinResult> assessmentInquiryDAO;

	/** 都道府県マスタ用 DAO */
	private DAO<PrefMst> prefMstDAO;

	/**
	 * Value オブジェクトの Factory を設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory Value オブジェクトの Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * 問合せヘッダ用共通処理を設定する。<br/>
	 * <br/>
	 * @param inquiryManageUtils 問合せヘッダ用共通処理
	 */
	public void setInquiryManageUtils(InquiryManageUtils inquiryManageUtils) {
		this.inquiryManageUtils = inquiryManageUtils;
	}

	/**
	 * 査定問合情報取得用 DAO を設定する。<br/>
	 * <br/>
	 * @param assessmentInquiryDAO 査定問合情報取得用 DAO
	 */
	public void setInquiryAssessmentDAO(DAO<InquiryAssessment> inquiryAssessmentDAO) {
		this.inquiryAssessmentDAO = inquiryAssessmentDAO;
	}

	/**
	 * @param inquiryHousingQuestionDAO セットする inquiryHousingQuestionDAO
	 */
	public void setInquiryHousingQuestionDAO(DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO) {
		this.inquiryHousingQuestionDAO = inquiryHousingQuestionDAO;
	}

	/**
	 * 査定問合情報取得用 DAO を設定する。<br/>
	 * <br/>
	 * @param assessmentInquiryDAO 査定問合情報取得用 DAO
	 */
	public void setAssessmentInquiryDAO(DAO<JoinResult> assessmentInquiryDAO) {
		this.assessmentInquiryDAO = assessmentInquiryDAO;
	}

	/**
	 *  都道府県マスタ用 DAO を設定する。<br/>
	 * <br/>
	 * @param prefMstDAO 都道府県マスタ用 DAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	/**
	 * パラメータで渡された Form の情報で査定情報をを新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 査定情報の入力値を格納した Form オブジェクト
	 * @param mypageUserId マイページのユーザーID （マイページログイン時に設定。　それ以外は null）
	 * @param editUserId ログインユーザーID （更新情報用）
	 *
	 * @return 採番されたお問い合わせID
	 *
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public String addInquiry(InquiryForm inputForm, String mypageUserId,
			String editUserId) throws Exception {

		// 問合せヘッダの登録処理を実行
		String inquiryId = this.inquiryManageUtils.addInquiry(
				inputForm.getInquiryHeaderForm(), PanaCommonConstant.INQUIRY_TYPE_ASSESSMENT, mypageUserId, editUserId);

		// 新規登録処理の場合、入力フォームの値を設定するバリーオブジェクトを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）

		// 査定情報作成
		InquiryAssessment inquiryAssessment = (InquiryAssessment) this.valueObjectFactory.getValueObject("InquiryAssessment");

		// フォームの入力値をバリーオブジェクトに設定する。
		((AssessmentInputForm)inputForm).copyToInquiryAssessment(inquiryAssessment);

		// 取得したお問合せIDを主キー値に設定する。
    	inquiryAssessment.setInquiryId(inquiryId);

    	// 査定情報登録
    	this.inquiryAssessmentDAO.insert(new InquiryAssessment[] { inquiryAssessment });


    	// 問合せアンケート情報作成
		InquiryHousingQuestion[] inquiryHousingQuestions = null;
		int ansCnt = 0;
		if (((AssessmentInputForm)inputForm).getAnsCd() != null && ((AssessmentInputForm)inputForm).getAnsCd().length > 0) {

			ansCnt += ((AssessmentInputForm)inputForm).getAnsCd().length;
		}
		if (ansCnt > 0)
		{
			inquiryHousingQuestions = (InquiryHousingQuestion[]) this.valueObjectFactory.getValueObject("InquiryHousingQuestion", ansCnt);

			// フォームの入力値をバリーオブジェクトに設定する。
			((AssessmentInputForm)inputForm).copyToInquiryHousingQuestion(inquiryHousingQuestions);

			// 取得したお問合せIDを主キー値に設定する。
	    	for (InquiryHousingQuestion inquiryHousingQuestion : inquiryHousingQuestions) {
	    		inquiryHousingQuestion.setInquiryId(inquiryId);
			}

	    	// 問合せアンケート情報登録
			this.inquiryHousingQuestionDAO.insert(inquiryHousingQuestions);
		}

		return inquiryId;
	}

	@Override
	public int searchInquiry(InquirySearchForm searchForm) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	/**
	 * リクエストパラメータで渡された問合せID （主キー値）に該当する査定問合情報を復帰する。<br/>
	 * InquirySearchForm の inquiryId プロパティに設定された値を主キー値として情報を取得する。
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param inquiryId　取得対象お問合せID
	 *
	 * @return　DB から取得した査定問合情報のバリーオブジェクト
	 *
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public InquiryInfo searchInquiryPk(String inquiryId) throws Exception {

		InquiryInfo inquiryInfo = new InquiryInfo();

		// 査定問合情報を取得して問合せオブジェクトへ設定する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		List<JoinResult> assessmentInquiry = this.assessmentInquiryDAO.selectByFilter(criteria);
		if (assessmentInquiry.size() > 0) {
			inquiryInfo.setAssessmentInquiry(assessmentInquiry);
		}

		//問合者住所・都道府県CDより都道府県マスタから都道府県名を取得して問合せオブジェクトへ設定する。
		InquiryAssessment inquiryAssessment =  (InquiryAssessment)assessmentInquiry.get(0).getItems().get("inquiryAssessment");
		if (!StringValidateUtil.isEmpty(inquiryAssessment.getPrefCd())) {
			DAOCriteria criteriaPrefMst = new DAOCriteria();
			criteriaPrefMst.addWhereClause("prefCd",inquiryAssessment.getPrefCd(), DAOCriteria.EQUALS, false);

			List<PrefMst> prefMst = this.prefMstDAO.selectByFilter(criteriaPrefMst);
			if (prefMst.size() > 0) {
				inquiryInfo.setPrefMst(prefMst.get(0));
			}
		}

		return inquiryInfo;
	}

	@Override
	public void updateInquiryStatus(InquiryStatusForm inputForm,
			String editUserId) throws Exception, NotFoundException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public InquiryForm createForm() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public InquiryForm createForm(HttpServletRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
