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
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryGeneralForm;
import jp.co.transcosmos.dm3.corePana.vo.InquiryGeneral;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * 汎用問合せ model の実装クラス.
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
public class GeneralInquiryManageImpl implements InquiryManage {

	/** Value オブジェクトの Factory */
	private ValueObjectFactory valueObjectFactory;

	/** 汎用問合せ情報取得用 DAO */
	private DAO<JoinResult> generalInquiryDAO;

	/** 汎用問合せ情報用 DAO */
	private DAO<InquiryGeneral> inquiryGeneralDAO;

	/** 問合せアンケート情報用 DAO */
	private DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO;

	/** 問合せヘッダ用共通処理 */
	private InquiryManageUtils inquiryManageUtils;

	/**
	 * 汎用問合せ情報取得用 DAO を設定する。<br/>
	 * <br/>
	 * @param generalInquiryDAO 汎用問合せ情報取得用 DAO
	 */
	public void setGeneralInquiryDAO(DAO<JoinResult> generalInquiryDAO) {
		this.generalInquiryDAO = generalInquiryDAO;
	}

	/**
	 * @param inquiryGeneralDAO セットする inquiryGeneralDAO
	 */
	public void setInquiryGeneralDAO(DAO<InquiryGeneral> inquiryGeneralDAO) {
		this.inquiryGeneralDAO = inquiryGeneralDAO;
	}


	/**
	 * @param inquiryManageUtils セットする inquiryManageUtils
	 */
	public void setInquiryManageUtils(InquiryManageUtils inquiryManageUtils) {
		this.inquiryManageUtils = inquiryManageUtils;
	}

	/**
	 * @param inquiryHousingQuestionDAO セットする inquiryHousingQuestionDAO
	 */
	public void setInquiryHousingQuestionDAO(
			DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO) {
		this.inquiryHousingQuestionDAO = inquiryHousingQuestionDAO;
	}

	@Override
	public String addInquiry(InquiryForm inputForm, String mypageUserId,
			String editUserId) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int searchInquiry(InquirySearchForm searchForm) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	/**
	 * Value オブジェクトの Factory を設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory Value オブジェクトの Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}
	/**
	 * リクエストパラメータで渡された問合せID （主キー値）に該当する物件問合せ情報を復帰する。<br/>
	 * InquirySearchForm の inquiryId プロパティに設定された値を主キー値として情報を取得する。
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param inquiryId　取得対象お問合せID
	 *
	 * @return　DB から取得した物件問合せのバリーオブジェクト
	 *
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public InquiryInfo searchInquiryPk(String inquiryId) throws Exception {

		// 査定問合情報を取得して問合せオブジェクトへ設定する。
		InquiryInfo inquiryInfo = new InquiryInfo();

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		List<JoinResult> generalInquiry = this.generalInquiryDAO.selectByFilter(criteria);
		if (generalInquiry.size() > 0) {
			inquiryInfo.setGeneralInquiry(generalInquiry);
	}

		return inquiryInfo;
	}

	/**
	 * パラメータで渡された Form の情報で物件問合せ情報をを新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 物件問合せの入力値を格納した Form オブジェクト
	 * @param mypageUserId マイページのユーザーID （マイページログイン時に設定。　それ以外は null）
	 * @param editUserId ログインユーザーID （更新情報用）
	 *
	 * @return 採番されたお問い合わせID
	 *
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */

	public String addInquiry(PanaInquiryGeneralForm inputForm, String mypageUserId, String editUserId)
			throws Exception {

		String inquiryId = null;

		// 問合せヘッダの登録処理を実行
		inquiryId = this.inquiryManageUtils.addInquiry(
				inputForm.getInquiryHeaderForm(), PanaCommonConstant.INQUIRY_TYPE_GENERAL, mypageUserId, editUserId);
		inputForm.setInquiryId(inquiryId);

		// 新規登録処理の場合、入力フォームの値を設定するバリーオブジェクトを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）
		InquiryGeneral[] inquiryGeneral = new InquiryGeneral[]{ (InquiryGeneral) this.valueObjectFactory.getValueObject("InquiryGeneral")};

		// フォームの入力値をバリーオブジェクトに設定する。
		inputForm.copyToInquiryGeneral(inquiryGeneral, inquiryId);
		// 汎用問合せ情報を登録
		this.inquiryGeneralDAO.insert(inquiryGeneral);

		// フォームの入力値をバリーオブジェクトに設定する。
		if (inputForm.getAnsCd() != null) {
			InquiryHousingQuestion[] inquiryHousingQuestions = new InquiryHousingQuestion[inputForm.getAnsCd().length];
			inputForm.copyToInquiryHousingQuestion(inquiryHousingQuestions, inquiryId);
			// 問合せアンケートを登録
			this.inquiryHousingQuestionDAO.insert(inquiryHousingQuestions);
		}

		return inquiryId;
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
