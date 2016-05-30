package jp.co.transcosmos.dm3.core.model.inquiry;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.form.HousingInquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.InquiryHousing;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * 物件問合せ model の実装クラス.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.02	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class HousingInquiryManageImpl implements InquiryManage {

	/** 物件問合せ情報用 DAO */
	protected DAO<InquiryHousing> inquiryHousingDAO;

	/** 問合せヘッダ用共通処理 */
	protected InquiryManageUtils inquiryManageUtils;

	/** 問合せフォームの Factory クラス */
	protected InquiryFormFactory formFactory;

	/** 物件情報メンテナンスの model */
	protected HousingManage housingManager;

	/** Value オブジェクトの Factory */
	protected ValueObjectFactory valueObjectFactory;


	/**
	 * 物件問合せ情報用DAO を設定する。<br/>
	 * <br/>
	 * @param inquiryHousingDAO 物件問合せ情報用 DAO
	 */
	public void setInquiryHousingDAO(DAO<InquiryHousing> inquiryHousingDAO) {
		this.inquiryHousingDAO = inquiryHousingDAO;
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
	 * 問合せフォームの Factory クラスを設定する。<br/>
	 * <br/>
	 * @param formFactory 問合せフォームの Factory クラス
	 */
	public void setFormFactory(InquiryFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	/**
	 * 物件情報メンテナンスの modelを設定する。<br/>
	 * <br/>
	 * @param housingManager 問合せフォームの Factory クラス
	 */
	public void setHousingManager(HousingManage housingManager) {
		this.housingManager = housingManager;
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

		// 問合せヘッダの登録処理を実行
		String inquiryId = this.inquiryManageUtils.addInquiry(
				inputForm.getInquiryHeaderForm(), "00", mypageUserId, editUserId);

		// 物件問合せ情報のValue オブジェクトを生成する。
		InquiryHousing inquiryHousing = (InquiryHousing) this.valueObjectFactory.getValueObject("InquiryHousing");

		// お問い合わせIDを設定する。
		inquiryHousing.setInquiryId(inquiryId);

		// フォームの値を Value オブジェクトへ copy する。
		((HousingInquiryForm) inputForm).copyToInquiryHousing(inquiryHousing);

		// 物件問合せ情報の登録処理を実行
		this.inquiryHousingDAO.insert(new InquiryHousing[]{inquiryHousing});

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

		// 問合せヘッダの検索処理を実行して結果を復帰する。
		// 問合せ一覧は、問合せヘッダで完結する範囲である事が前提となる。
		return this.inquiryManageUtils.searchInquiry(searchForm);
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
	public HousingInquiry searchInquiryPk(String inquiryId) throws Exception {

		// 問合せヘッダの情報を取得して物件問合せオブジェクトへ設定する。
		HousingInquiry inquiry = createHousingInquiry(this.inquiryManageUtils.searchInquiryPk(inquiryId));

		// もし問合せヘッダの情報が無い場合、戻り値をnull設定して処理を中断する。
		if (inquiry.getInquiryHeaderInfo() == null) return null;

		// 問合せヘッダの情報が取得できた場合は、
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);

		// 物件問合せ情報を取得
		List<InquiryHousing> inquiryHousings = this.inquiryHousingDAO.selectByFilter(criteria);

		// 物件情報の model を使用して物件情報を取得する。
		List<Housing> housings = new ArrayList<Housing>();
		Housing housing = null;

		for (InquiryHousing inquiryHousing : inquiryHousings){
			housing = this.housingManager.searchHousingPk(inquiryHousing.getSysHousingCd());

			// 物件情報が取得できた場合は、物件問合せオブジェクトへ設定する。
			if (housing != null) {
				housings.add(housing);
			}
		}

		// 物件問合せ情報を設定
		inquiry.setInquiryHousings(inquiryHousings);

		// 物件情報を設定
		inquiry.setHousings(housings);

		return inquiry;
	}

	/**
	 * リクエストパラメータで渡された問合せID （主キー値）に該当する問合せ情報の対応ステータスを更新する。<br/>
	 * InquirySearchForm の inquiryId プロパティに設定された値を主キー値として対応ステータスを更新する。<br/>
	 * 更新日、更新者、対応ステータス以外は更新しないので、Form に設定しない事。（設定しても値は無視される。）<br/>
	 * <br/>
	 * @param inputForm 対応ステータスの入力値を格納した Form オブジェクト
	 * @param editUserId ログインユーザーID （更新情報用）
	 *
 	 * @exception Exception 実装クラスによりスローされる任意の例外
 	 * @exception NotFoundException 更新対象なし
	 */
	@Override
	public void updateInquiryStatus(InquiryStatusForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// 問合せステータス更新処理を実行する。
		this.inquiryManageUtils.updateInquiryStatus(inputForm, editUserId);
	}



	/**
	 * 空の物件問合せフォームのインスタンスを生成する。<br/>
	 * オブジェクト生成時は、空の問合せヘッダオブジェクトも生成して設定する。<br/>
	 * <bvr/>
	 * @return 空の物件問合せフォームのインスタンス
	 */
	@Override
	public InquiryForm createForm() {
		HousingInquiryForm form = this.formFactory.createHousingInquiryForm();
		form.setCommonInquiryForm(this.formFactory.createInquiryHeaderForm());

		return form;
	}

	/**
	 * 入力した値を設定した物件問い合わせフォームのインスタンスを生成する。<br/>
	 * オブジェクト生成時は、入力値を設定した問合せヘッダオブジェクトも生成して設定する。<br/>
	 */
	@Override
	public InquiryForm createForm(HttpServletRequest request) {
		HousingInquiryForm form = this.formFactory.createHousingInquiryForm(request);
		form.setCommonInquiryForm(this.formFactory.createInquiryHeaderForm(request));

		return form;
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
