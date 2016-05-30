package jp.co.transcosmos.dm3.corePana.model.inquiry;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiry;
import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiryManageImpl;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryHeaderInfo;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.vo.InquiryHousing;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.inquiry.dao.HousingInquiryDAO;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryHousingForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * 物件問合せ model の実装クラス.
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
public class PanaHousingInquiryManageImpl extends HousingInquiryManageImpl {

	/** 問合せヘッダ情報用DAO */
	private DAO<InquiryHeader> inquiryHeaderDAO;

	/** 問合せアンケート情報用 DAO */
	private DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO;

	/** 物件問合せ model の実装クラス. */
	protected GeneralInquiryManageImpl generalInquiryManager;

	/** 物件問合せ model の実装クラス. */
	protected AssessmentInquiryManageImpl assessmentInquiryManager;

	/** 都道府県マスタ用 DAO */
	private DAO<PrefMst> prefMstDAO;

	/** 問合情報メンテナンスの model */
	protected HousingInquiryDAO housingInquiryDAO;

	/**
	 *  問合せヘッダ情報用 DAO を設定する。<br/>
	 * <br/>
	 * @param inquiryHeaderDAO 問合せヘッダ情報用 DAO
	 */
	public void setInquiryHeaderDAO(DAO<InquiryHeader> inquiryHeaderDAO) {
		this.inquiryHeaderDAO = inquiryHeaderDAO;
	}

	/**
	 * @param generalInquiryManage セットする generalInquiryManage
	 */
	public void setGeneralInquiryManager(GeneralInquiryManageImpl generalInquiryManager) {
		this.generalInquiryManager = generalInquiryManager;
	}

	/**
	 * @param assessmentInquiryManage セットする assessmentInquiryManage
	 */
	public void setAssessmentInquiryManager(AssessmentInquiryManageImpl assessmentInquiryManager) {
		this.assessmentInquiryManager = assessmentInquiryManager;
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
	 *  問合せアンケート情報用DAO を設定する。<br/>
	 * <br/>
	 * @param inquiryHousingQuestionDAO 問合せアンケート情報用 DAO
	 */
	public void setInquiryHousingQuestionDAO(DAO<InquiryHousingQuestion> inquiryHousingQuestionDAO) {
		this.inquiryHousingQuestionDAO = inquiryHousingQuestionDAO;
	}

	/**
	 * 問合情報メンテナンスの modelを設定する。<br/>
	 * <br/>
	 * @param housingInquiryDAO セットする housingInquiryDAO
	 */
	public void setHousingInquiryDAO(HousingInquiryDAO housingInquiryDAO) {
		this.housingInquiryDAO = housingInquiryDAO;
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
	@Override
	public String addInquiry(InquiryForm inputForm, String mypageUserId, String editUserId)
			throws Exception {

		String inquiryId = null;

		// 問合せヘッダの登録処理を実行
		inquiryId = super.addInquiry(inputForm, mypageUserId, editUserId);

		// 問合せアンケート情報作成
		InquiryHousingQuestion[] inquiryHousingQuestions = null;
		int ansCnt = 0;
		if (((PanaInquiryHousingForm)inputForm).getAnsCd() != null && ((PanaInquiryHousingForm)inputForm).getAnsCd().length > 0) {

			ansCnt += ((PanaInquiryHousingForm)inputForm).getAnsCd().length;
		}
		if (ansCnt > 0){
			inquiryHousingQuestions = (InquiryHousingQuestion[]) this.valueObjectFactory.getValueObject("InquiryHousingQuestion", ansCnt);

			// フォームの入力値をバリーオブジェクトに設定する。
			((PanaInquiryHousingForm)inputForm).copyToInquiryHousingQuestion(inquiryHousingQuestions);

			// 取得したお問合せIDを主キー値に設定する。
	    	for (InquiryHousingQuestion inquiryHousingQuestion : inquiryHousingQuestions) {
	    		inquiryHousingQuestion.setInquiryId(inquiryId);
			}

	    	// 問合せアンケート情報登録
			this.inquiryHousingQuestionDAO.insert(inquiryHousingQuestions);
		}

		return inquiryId;
	}

	/**
	 * 問合せ情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、問合せヘッダ情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * ※管理画面からの利用を前提としているので、フロントから使用する場合はセキュリティに注意する事。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 *
	 * @return 該当件数
	 *
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public int searchInquiry(InquirySearchForm searchForm) throws Exception {
		// 検索処理
		List<JoinResult> inquiryList = null;
		try {
			inquiryList = this.housingInquiryDAO.housingInquirySearch((PanaInquirySearchForm)searchForm);
		} catch (NotEnoughRowsException err) {
			// 範囲外のページが設定された場合、再検索
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			searchForm.setSelectedPage(pageNo);
			inquiryList = this.housingInquiryDAO.housingInquirySearch((PanaInquirySearchForm)searchForm);
		}

		searchForm.setRows(inquiryList);

		return inquiryList.size();
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
	public InquiryInfo searchHousingInquiryPk(String inquiryId) throws Exception {

		InquiryInfo inquiryInfo = new InquiryInfo();

		InquiryInterface inquiryHousing = super.searchInquiryPk(inquiryId);

		// もし問合せヘッダの情報が無い場合、戻り値をnull設定して処理を中断する。
		if (inquiryHousing.getInquiryHeaderInfo() == null) return null;

		// 問合せオブジェクトへ設定する。
		inquiryInfo.setInquiryHeaderInfo(inquiryHousing.getInquiryHeaderInfo());
		inquiryInfo.setHousings(((HousingInquiry)inquiryHousing).getHousings());

		//問合者住所・都道府県CDより都道府県マスタから都道府県名を取得して問合せオブジェクトへ設定する。
		InquiryHeader inquiryHeader =  (InquiryHeader)inquiryHousing.getInquiryHeaderInfo().getInquiryHeader();
		if(!StringValidateUtil.isEmpty(inquiryHeader.getPrefCd())){
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("prefCd",inquiryHeader.getPrefCd(), DAOCriteria.EQUALS, false);

			List<PrefMst> prefMst = this.prefMstDAO.selectByFilter(criteria);
			if (prefMst.size() > 0) {
				inquiryInfo.setPrefMst(prefMst.get(0));
			}
		}

		// 問合せヘッダの情報が取得できた場合は、問合せアンケート情報を取得して問合せオブジェクトへ設定する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		List<InquiryHousingQuestion> inquiryHousingQuestion = this.inquiryHousingQuestionDAO.selectByFilter(criteria);
		if (inquiryHousingQuestion.size() > 0) {
			inquiryInfo.setInquiryHousingQuestion(inquiryHousingQuestion.toArray(
					new InquiryHousingQuestion[inquiryHousingQuestion.size()]));
		}

		// 問合せヘッダの情報が取得できた場合は、物件問合せ情報を取得して問合せオブジェクトへ設定する。
		DAOCriteria criteriaHousing = new DAOCriteria();
		criteriaHousing.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		List<InquiryHousing> inquiryHousings = this.inquiryHousingDAO.selectByFilter(criteriaHousing);
		if (inquiryHousings.size() > 0) {
			inquiryInfo.setInquiryHousing((jp.co.transcosmos.dm3.corePana.vo.InquiryHousing)inquiryHousings.get(0));
		}

		return inquiryInfo;
	}

	/**
	 * リクエストパラメータで渡された問合せID （主キー値）に該当する問合せヘッダ情報を復帰する。<br/>
	 * InquirySearchForm の inquiryId プロパティに設定された値を主キー値として情報を取得する。
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param inquiryId　取得対象お問合せID
	 *
	 * @return　DB から取得した問合せヘッダのバリーオブジェクト
	 *
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public InquiryHeader searchInquiryHeaderPk(String inquiryId) throws Exception {

		// 問合せヘッダ情報のインスタンスを生成する。
		InquiryHeader inquiryHeader = new InquiryHeader();

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		// 問合せヘッダ情報を取得
		List<InquiryHeader> inquiryHeaders = this.inquiryHeaderDAO.selectByFilter(criteria);

		//問合せヘッダ情報 を設定
		if (inquiryHeaders.size() > 0) {
			inquiryHeader = inquiryHeaders.get(0);
		}

		return inquiryHeader;
	}



	/**
	 * パラメータで渡された Form の情報で物件一覧情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * PanaHousingSearchForm の sysBuildingCd プロパティに設定された値を主キー値として削除する。
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            削除条件の格納オブジェクト
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public void delInquiryAll(String inquiryId) throws Exception {
		// お問合せヘッダ情報を削除する条件を生成する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);

		// お問合せヘッダ情報を削除する
		this.inquiryHeaderDAO.deleteByFilter(criteria);
	}

	/**
	 * CSV出力情報を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された searchForm パラメータの値で検索条件を生成し、CSV出力情報を検索する。<br/>
	 * 検索結果は searchForm オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件、および、検索結果の格納オブジェクト
	 *
	 * @return 該当件数
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public void searchCsvHousing(PanaInquirySearchForm searchForm,HttpServletResponse response)throws Exception {

		this.housingInquiryDAO.housingInquirySearch(searchForm,response, this, this.generalInquiryManager, this.assessmentInquiryManager);
	}


	/**
	 * 物件問合せ情報のインスタンスを生成する。<br/>
	 * 生成したインスタンスには、問合せヘッダの情報を設定する。<br/>
	 * もし、HousingInquiry を拡張した場合はこのメソッドをオーバーライドする事。<br/>
	 * <br/>
	 * @param header お問合せヘッダ
	 * @return　物件問合せ情報のインスタンス
	 */
	protected HousingInquiry createHousingInquiry(InquiryHeaderInfo header) {
		HousingInquiry inquiry = new HousingInquiry();
		inquiry.setInquiryHeaderInfo(header);
		return inquiry;
	}

}
