package jp.co.transcosmos.dm3.core.model.inquiry;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 問合せヘッダユーティリティ.
 * 追合せヘッダに対する各種処理を行う。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.02	新規作成
 * H.Mizuno		2015.04.30	複数お問合せ種別の対応に問題があるので修正
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class InquiryManageUtils {
	
	private static final Log log = LogFactory.getLog(InquiryManageUtils.class);
	
	/** Value オブジェクトの Factory */
	protected ValueObjectFactory valueObjectFactory;
	
	/** 問合せヘッダ情報用DAO */
	protected DAO<InquiryHeader> inquiryHeaderDAO;
	
	/** お問合せ内容種別情報用DAO */
	protected DAO<InquiryDtlInfo> inquiryDtlInfoDAO;
	
	/** お問合せ情報一覧取得用 DAO */
	protected DAO<JoinResult> inquiryListDAO;
	
	/** マイページ会員情報用 DAO */
	protected DAO<MypageUserInterface> memberInfoDAO;
	
	/**
	 * Value オブジェクトの Factory を設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory Value オブジェクトの Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}
	
	/**
	 * 問合せヘッダ情報用DAO を設定する。<br/>
	 * <br/>
	 * @param inquiryHeaderDAO 問合せヘッダ情報用 DAO
	 */
	public void setInquiryHeaderDAO(DAO<InquiryHeader> inquiryHeaderDAO) {
		this.inquiryHeaderDAO = inquiryHeaderDAO;
	}
	
	/**
	 * お問合せ内容種別情報用DAO を設定する。<br/>
	 * <br/>
	 * @param inquiryDtlInfoDAO お問合せ内容種別情報用 DAO
	 */
	public void setInquiryDtlInfoDAO(DAO<InquiryDtlInfo> inquiryDtlInfoDAO) {
		this.inquiryDtlInfoDAO = inquiryDtlInfoDAO;
	}
	
	/**
	 *  お問合せ情報一覧取得用DAO を設定する。<br/>
	 * <br/>
	 * @param inquiryListDAO  お問合せ情報一覧取得用 DAO
	 */
	public void setInquiryListDAO(DAO<JoinResult> inquiryListDAO) {
		this.inquiryListDAO = inquiryListDAO;
	}
	
	/**
	 * マイページ会員情報の検索・更新に使用する DAO を設定する。<br/>
	 * <br/>
	 * @param memberInfoDAO マイページ会員情報の検索・更新DAO
	 */
	public void setMemberInfoDAO(DAO<MypageUserInterface> memberInfoDAO) {
		this.memberInfoDAO = memberInfoDAO;
	}

	/**
	 * パラメータで渡された Form の情報で問合せ情報をを新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm 物件問合せの入力値を格納した Form オブジェクト
	 * @param inquiryType お問合せ区分 
	 * @param mypageUserId マイページのユーザーID （マイページログイン時に設定。　それ以外は null）
	 * @param editUserId ログインユーザーID （更新情報用）
	 * 
	 * @return 採番されたお問い合わせID
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public String addInquiry(InquiryHeaderForm inputForm,
			String inquiryType,
			String mypageUserId,
			String editUserId)
			throws Exception {

		// 新規登録処理の場合、入力フォームの値を設定するバリーオブジェクトを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）
		InquiryHeader inquiryHeader = (InquiryHeader) this.valueObjectFactory.getValueObject("InquiryHeader");

// 2015.04.30 H.Mizuno 複数お問合せ内容種別への問題を修正 start
//		InquiryDtlInfo[] inquiryDtlInfos
//				= new InquiryDtlInfo[] {(InquiryDtlInfo) this.valueObjectFactory.getValueObject("InquiryDtlInfo")};
		InquiryDtlInfo[] inquiryDtlInfos = null;
		if (inputForm.getInquiryDtlType() != null && inputForm.getInquiryDtlType().length > 0){
			inquiryDtlInfos = (InquiryDtlInfo[]) this.valueObjectFactory.getValueObject("InquiryDtlInfo", inputForm.getInquiryDtlType().length);
		}
// 2015.04.30 H.Mizuno 複数お問合せ内容種別への問題を修正 end

		// 更新担当者を設定
		String strUserId = null;
		if (!StringValidateUtil.isEmpty(mypageUserId)) {
			strUserId = mypageUserId;
// 2015.05.11 H.Mizuno マイページユーザーの場合、ユーザーID を設定する処理を追加 start
			inquiryHeader.setUserId(mypageUserId);
// 2015.05.11 H.Mizuno マイページユーザーの場合、ユーザーID を設定する処理を追加 end
		} else {
			strUserId = editUserId;
		}

    	// フォームの入力値をバリーオブジェクトに設定する。
    	inputForm.copyToInquiryHeader(inquiryHeader, strUserId);
		
    	// お問合せ区分、お問い合わせ日時、対応ステータスを設定する
    	inquiryHeader.setInquiryType(inquiryType);
    	inquiryHeader.setInquiryDate(inquiryHeader.getUpdDate());
    	inquiryHeader.setAnswerStatus("0");
    	
    	// 新規登用のタイムスタンプ情報を設定する。 （更新日の設定情報を転記）
    	inquiryHeader.setInsDate(inquiryHeader.getUpdDate());
    	inquiryHeader.setInsUserId(strUserId);

		// 取得した主キー値で問合せヘッダ情報を登録
		this.inquiryHeaderDAO.insert(new InquiryHeader[] { inquiryHeader });


		// 入力値を設定
// 2015.04.30 H.Mizuno お問合せ内容種別CD の更新方法を変更 start
//
//		inputForm.copyToInquiryDtlInfo(inquiryDtlInfos);
//
//		// 問合せ内容種別情報の主キー値を取得した主キー値に設定する。
//		for (InquiryDtlInfo inquiryDtlInfo : inquiryDtlInfos) {
//			inquiryDtlInfo.setInquiryId(inquiryHeader.getInquiryId());
//		}
//
//		try {
//			this.inquiryDtlInfoDAO.insert(inquiryDtlInfos);
//		} catch (DataIntegrityViolationException e) {
//			throw new NotFoundException();
//		}
//
		// お問合内容種別CD が渡された場合のみ更新処理を行う。
		if (inquiryDtlInfos != null){
			// お問合内容種別CD を Form から設定する。
			inputForm.copyToInquiryDtlInfo(inquiryDtlInfos);

			// 同じ内容種別CD が送信されるとキー重複エラーが発生するので、登録した種別CD は退避しておく。
			// この Set オブジェクトに登録したお問合せ種別CD の場合は追加処理をキャンセルする。
			Set<String> cdChkSet = new HashSet<>();

			// 渡されたお問合せ内容種別CD 分、登録を繰り返す。
			for (InquiryDtlInfo inquiryDtlInfo : inquiryDtlInfos) {
				inquiryDtlInfo.setInquiryId(inquiryHeader.getInquiryId());
				
				// 既に登録済のお問合せ内容種別CD の場合、登録をスキップする。
				// また、コード自体が空文字列の場合も登録をスキップする。
				if (!StringValidateUtil.isEmpty(inquiryDtlInfo.getInquiryDtlType()) &&
					!cdChkSet.contains(inquiryDtlInfo.getInquiryDtlType())){

					this.inquiryDtlInfoDAO.insert(new InquiryDtlInfo[]{inquiryDtlInfo});
					cdChkSet.add(inquiryDtlInfo.getInquiryDtlType());
				}
			}

		}
// 2015.04.30 H.Mizuno お問合せ内容種別CD の更新方法を変更 end
		
		return inquiryHeader.getInquiryId();
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
	public int searchInquiry(InquirySearchForm searchForm) throws Exception {
		// 問合せ情報を検索する条件を生成する。
		DAOCriteria criteria = searchForm.buildCriteria();

		// 問合せの検索
		List<JoinResult> inquiryList;
		try {
			inquiryList = this.inquiryListDAO.selectByFilter(criteria);

		} catch (NotEnoughRowsException err) {

			int pageNo = (err.getMaxRowCount() - 1)
					/ searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			inquiryList = this.inquiryListDAO.selectByFilter(criteria);
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
	 * @return　DB から取得した問合せヘッダ情報のバリーオブジェクト
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public InquiryHeaderInfo searchInquiryPk(String inquiryId) throws Exception {
		// 問合せヘッダ情報のインスタンスを生成する。
		InquiryHeaderInfo inquiryHeaderInfo = new InquiryHeaderInfo();
		
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId, DAOCriteria.EQUALS, false);
		
		// 問合せヘッダ情報を取得
		List<InquiryHeader> inquiryHeaders = this.inquiryHeaderDAO.selectByFilter(criteria);
		
		if (inquiryHeaders.size() == 0) return null;
		
		// 問合せヘッダ情報を設定
		inquiryHeaderInfo.setInquiryHeader(inquiryHeaders.get(0));
		
		// お問合せ内容種別情報を取得
		List<InquiryDtlInfo> inquiryDtlInfoList = this.inquiryDtlInfoDAO.selectByFilter(criteria);
		
		// お問合せ内容種別情報を設定
		if (inquiryDtlInfoList.size() > 0) {
			inquiryHeaderInfo.setInquiryDtlInfos(inquiryDtlInfoList.toArray(
					new InquiryDtlInfo[inquiryDtlInfoList.size()]));
		}
		
		// マイページ会員情報の主キー値を検索条件としたオブジェクトを生成
		String userId = inquiryHeaderInfo.getInquiryHeader().getUserId();
		DAOCriteria criteriaMember = new DAOCriteria();
		
		// ユーザーID が指定されている場合、マイページ会員を取得する。
		if (!StringValidateUtil.isEmpty(userId)){
			criteriaMember.addWhereClause("userId", userId, DAOCriteria.EQUALS, true);
			
			// マイページ会員を取得する
			List<MypageUserInterface> member = this.memberInfoDAO.selectByFilter(criteriaMember);
			
			if (member.size() > 0) {
				inquiryHeaderInfo.setMypageUser(member.get(0));
			}			
		}
		
		return inquiryHeaderInfo;
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
	public void updateInquiryStatus(InquiryStatusForm inputForm, String editUserId)
			throws Exception, NotFoundException {
		
    	// 更新処理の場合、更新対象データを取得する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inputForm.getInquiryId());

		List<InquiryHeader> inquiryHeaders = this.inquiryHeaderDAO.selectByFilter(criteria);

        // 該当するデータが存在しない場合は、例外をスローする。
		if (inquiryHeaders == null || inquiryHeaders.size() == 0) {
			throw new NotFoundException();
		}    	

		
        // お問合せヘッダ情報を取得し、入力した値で上書きする。
		InquiryHeader inquiryHeader = inquiryHeaders.get(0);

    	inputForm.copyToInquiryHeader(inquiryHeader, editUserId);

		// お問合せヘッダ情報の更新
		this.inquiryHeaderDAO.update(new InquiryHeader[]{inquiryHeader});
		
	}

}
