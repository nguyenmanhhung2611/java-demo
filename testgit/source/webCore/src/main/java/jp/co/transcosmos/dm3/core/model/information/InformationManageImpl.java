package jp.co.transcosmos.dm3.core.model.information;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.InformationTarget;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.OrCriteria;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * お知らせメンテナンス用 Model クラス
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.05	新規作成
 * H.Mizuno		2015.06.17	お知らせ情報取得時の条件を効率的にオーバーライド出来るようにリファクタリング
 * 
 * 注意事項
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。
 * 
 * </pre>
 */
public class InformationManageImpl implements InformationManage {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);

	/** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;
	
	/** お知らせ情報取得用 DAO */
	protected DAO<JoinResult> informationListDAO;

	/** お知らせ情報更新用 DAO */
	protected DAO<Information> informationDAO;

	/** お知らせ公開先情報更新用 DAO */
	protected DAO<InformationTarget> informationTargetDAO;

	/** お知らせ情報テーブルの別名 */
	public static final String IMFORMATION_ALIA = "information";

	/** お知らせ公開先情報ロールテーブルの別名 */
	public static final String IMFORMATION_TARGET_ALIA = "informationTarget";

	
	
	/**
	 * バリーオブジェクトのインスタンスを生成するファクトリーを設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory バリーオブジェクトのファクトリー
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * お知らせ情報取得用 DAOを取得する。<br/>
	 * <br/>
	 * 
	 * @return お知らせ情報取得用 DAO
	 */
	public void setInformationListDAO(DAO<JoinResult> informationListDAO) {
		this.informationListDAO = informationListDAO;
	}

	/**
	 * お知らせ情報更新用 DAOを取得する。<br/>
	 * <br/>
	 * 
	 * @return お知らせ情報更新用 DAO
	 */
	public void setInformationDAO(DAO<Information> informationDAO) {
		this.informationDAO = informationDAO;
	}

	/**
	 * お知らせ公開先情報更新用 DAOを取得する。<br/>
	 * <br/>
	 * 
	 * @return お知らせ公開先情報更新用 DAO
	 */
	public void setInformationTargetDAO(
			DAO<InformationTarget> informationTargetDAO) {
		this.informationTargetDAO = informationTargetDAO;
	}


	
	/**
	 * パラメータで渡された Form の情報でお知らせ情報を新規追加する。<br/>
	 * お知らせ番号 は自動採番されるので、InformationForm の informationNo プロパティには値を設定しない事。<br/>
	 * <br/>
	 * @param inputForm お知らせ情報の入力値を格納した Form オブジェクト
 	 * @param editUserId ログインユーザーＩＤ（更新情報用）
 	 * 
	 * @return お知らせ番号
	 */
	@Override
	public String addInformation(InformationForm inputForm, String editUserId){

    	// 新規登録処理の場合、入力フォームの値を設定するバリーオブジェクトを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）
		Information information = (Information) this.valueObjectFactory.getValueObject("Information");
		InformationTarget[] informationTargets
				= new InformationTarget[] {(InformationTarget) this.valueObjectFactory.getValueObject("InformationTarget")};


    	// フォームの入力値をバリーオブジェクトに設定する。
    	inputForm.copyToInformation(information, editUserId);
		
    	// 新規登用のタイムスタンプ情報を設定する。 （更新日の設定情報を転記）
    	information.setInsDate(information.getUpdDate());
    	information.setInsUserId(editUserId);


		// 取得した主キー値で管理ユーザー情報を登録
		this.informationDAO.insert(new Information[] { information });


		// 公開対象区分が個人の場合、お知らせ公開先情報のレコードを追加する。
		if ("2".equals(information.getDspFlg())) {
		
			// 入力値を設定
			inputForm.copyToInformationTarget(informationTargets);

			// お知らせ公開先情報の主キー値を取得した主キー値に設定する。
			for (InformationTarget informationTarget : informationTargets) {
				informationTarget.setInformationNo(information.getInformationNo());
			}
			this.informationTargetDAO.insert(informationTargets);
		}

		return information.getInformationNo();
	}



	/**
	 * お知らせの更新を行う<br/>
	 * <br/>
	 * @param inputForm お知らせの入力値を格納した Form オブジェクト
	 * 
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	@Override
	public void updateInformation(InformationForm inputForm, String editUserId)
			throws NotFoundException {

    	// 更新処理の場合、更新対象データを取得する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", inputForm.getInformationNo());

		List<JoinResult> informations = this.informationListDAO.selectByFilter(criteria);

        // 該当するデータが存在しない場合は、例外をスローする。
		if (informations == null || informations.size() == 0) {
			throw new NotFoundException();
		}    	

		
        // お知らせ情報を取得し、入力した値で上書きする。
		Information information
			= (Information) informations.get(0).getItems().get(IMFORMATION_ALIA);

    	inputForm.copyToInformation(information, editUserId);


		// お知らせ公開先情報を更新・削除
		this.informationTargetDAO.deleteByFilter(inputForm.buildPkCriteria());


		// 公開対象区分が個人の場合
		if ("2".equals(information.getDspFlg())) {

			InformationTarget[] informationTargets
				= new InformationTarget[] {(InformationTarget) this.valueObjectFactory.getValueObject("InformationTarget")};

			inputForm.copyToInformationTarget(informationTargets);
			
			// お知らせ公開先情報を追加
			this.informationTargetDAO.insert(informationTargets);
		}
    	
		// お知らせ情報の更新
		this.informationDAO.update(new Information[]{information});

	}

	
	
	/**
	 * パラメータで渡された Form の情報でお知らせ情報を削除する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * InformationSearchForm の informationNo プロパティに設定された値を主キー値として削除する。
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * <br/>
	 * @param inputForm お知らせ情報の検索値（削除対象となる informationNo）を格納した Form オブジェクト
	 */
	@Override
	public void delInformation(InformationForm inputForm) {

		// お知らせ公開先情報を更新・削除
		this.informationTargetDAO.deleteByFilter(inputForm.buildPkCriteria());

		// お知らせ情報の更新
		this.informationDAO.deleteByFilter(inputForm.buildPkCriteria());

	}


	
	/**
	 * サイト TOP に表示するお知らせ情報を取得する。<br/>
	 * <br/>
	 * @see InformationManageImpl#buildTopInformationCriteria()
	 * @return サイト TOP に表示する、お知らせ情報のリスト
	 */
	@Override
	public List<Information> searchTopInformation() {
		// お知らせ情報を取得
		return this.informationDAO.selectByFilter(buildTopInformationCriteria());

	}


	
	/**
	 * サイト TOP に表示するお知らせ情報を取得する検索条件を生成する。<br/>
	 * 公開対象区分 = 「仮を含む全会員」が取得対象になる。<br/>
	 * また、システム日付が公開期間中であるお知らせ情報が取得対象となる。<br/>
	 * <br/>
	 * @return サイト TOP に表示する、お知らせ情報取得用検索オブジェクト
	 */
	protected DAOCriteria buildTopInformationCriteria(){
		
		// お知らせ情報を取得する為の主キーを対象とした検索条件を生成する。
		DAOCriteria criteria = new DAOCriteria();

		// 公開対象区分 = 「仮を含む全会員」
		criteria.addWhereClause("dspFlg", "0");

		// システム日付が公開期間中であるお知らせ情報が取得対象となる
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date sysDate = new Date();
		// startDate IS NUll OR startDate <= システム日付
		OrCriteria startOrCriteria = new OrCriteria();
		startOrCriteria.addWhereClause("startDate", null, DAOCriteria.IS_NULL);
		startOrCriteria.addWhereClause("startDate", sdf.format(sysDate),
				DAOCriteria.LESS_THAN_EQUALS);
		// endDate IS NUll OR endDate >= システム日付
		OrCriteria endOrCriteria = new OrCriteria();
		endOrCriteria.addWhereClause("endDate", null, DAOCriteria.IS_NULL);
		endOrCriteria.addWhereClause("endDate", sdf.format(sysDate),
				DAOCriteria.GREATER_THAN_EQUALS);
		
		criteria.addSubCriteria(startOrCriteria);
		criteria.addSubCriteria(endOrCriteria);
		
		return criteria;
	}
	
	
	
	/**
	 * マイページ TOP に表示するお知らせ情報を取得する。<br/>
	 * <br/>
	 * @see InformationManageImpl#buildMyPageInformationCriteria(String userId)
	 * @return マイページ TOP に表示する、お知らせ情報のリスト
	 */
	@Override
	public List<Information> searchMyPageInformation(String userId) {
		// お知らせ情報を取得
		List<JoinResult> information = this.informationListDAO
				.selectByFilter(buildMyPageInformationCriteria(userId));

		List<Information> result = new ArrayList<>();

		if (information == null || information.size() == 0) {
			return result;
		}

		for (JoinResult a : information){
			result.add((Information) a.getItems().get(IMFORMATION_ALIA));
		}

		return result;
	}



	/**
	 * マイページ TOP に表示するお知らせ情報取得に使用する検索条件を生成する。<br/>
	 * 公開対象区分 = 「全本会員」、または、「個人」が取得対象になるが、「個人」の場合、お知らせ公開先情報
	 * のユーザーID が引数と一致する必要がある。<br/>
	 * また、システム日付が公開期間中であるお知らせ情報が取得対象となる。<br/>
	 * <br/>
	 * @return マイページ TOP に表示する、お知らせ情報のリスト
	 */
	protected DAOCriteria buildMyPageInformationCriteria(String userId) {

		// お知らせ情報を取得する為の主キーを対象とした検索条件を生成する。
		DAOCriteria mainCriteria = new DAOCriteria();
		// 公開対象区分 =「全本会員」、または、「個人」
		OrCriteria dspCriteria = new OrCriteria();
		// 公開対象区分 = 「個人」
		DAOCriteria dspCriteria2 = new DAOCriteria();
		
		// 公開対象区分 = 「全本会員」、または、「個人」
		// 「個人」の場合、お知らせ公開先情報のユーザーID が引数と一致する
		dspCriteria2.addWhereClause("dspFlg", "2");
		dspCriteria2.addWhereClause("userId", userId);
		dspCriteria.addSubCriteria(dspCriteria2);
		dspCriteria.addWhereClause("dspFlg", "1");
		mainCriteria.addSubCriteria(dspCriteria);
		
		// システム日付が公開期間中であるお知らせ情報が取得対象となる
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date sysDate = new Date();
		// startDate IS NUll OR startDate <= システム日付
		OrCriteria startOrCriteria = new OrCriteria();
		startOrCriteria.addWhereClause("startDate", null, DAOCriteria.IS_NULL);
		startOrCriteria.addWhereClause("startDate", sdf.format(sysDate),
				DAOCriteria.LESS_THAN_EQUALS);
		// endDate IS NUll OR endDate >= システム日付
		OrCriteria endOrCriteria = new OrCriteria();
		endOrCriteria.addWhereClause("endDate", null, DAOCriteria.IS_NULL);
		endOrCriteria.addWhereClause("endDate", sdf.format(sysDate),
				DAOCriteria.GREATER_THAN_EQUALS);

		mainCriteria.addSubCriteria(startOrCriteria);
		mainCriteria.addSubCriteria(endOrCriteria);

		return mainCriteria;
	}
	
	
	
	/**
	 * お知らせ情報を検索し、結果リストを復帰する。（管理画面用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、お知らせ情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @return 該当件数
	 */
	@Override
	public int searchAdminInformation(InformationSearchForm searchForm) {

		// お知らせ情報を検索する条件を生成する。
		DAOCriteria criteria = searchForm.buildCriteria();

		// お知らせの検索
		List<JoinResult> informationList;
		try {
			informationList = this.informationListDAO.selectByFilter(criteria);

		} catch (NotEnoughRowsException err) {

			int pageNo = (err.getMaxRowCount() - 1)
					/ searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			informationList = this.informationListDAO.selectByFilter(criteria);
		}

		searchForm.setRows(informationList);

		return informationList.size();
	}



	/**
	 * リクエストパラメータで渡された「お知らせ番号」 （主キー値）に該当するお知らせ情報を復帰する。（サイト TOP 用）<br/>
	 * 公開対象区分 = 「仮を含む全会員」が取得対象になる。<br/>
	 * また、システム日付が公開期間中であるお知らせ情報が取得対象となる。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param informationNo 取得対象となるお知らせ番号
	 * @return　お知らせ情報
	 */
	@Override
	public Information searchTopInformationPk(String informationNo) {

		// お知らせ情報を取得する為の主キーを対象とした検索条件を生成する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", informationNo);

		// 公開対象区分 = 「仮を含む全会員」
		criteria.addWhereClause("dspFlg", "0");
		
		// システム日付が公開期間中であるお知らせ情報が取得対象となる
		Date sysDate = new Date();
		// startDate IS NUll OR startDate <= システム日付
		OrCriteria startOrCriteria = new OrCriteria();
		startOrCriteria.addWhereClause("startDate", null, DAOCriteria.IS_NULL);
		startOrCriteria.addWhereClause("startDate", sysDate,
				DAOCriteria.LESS_THAN_EQUALS);
		// endDate IS NUll OR endDate >= システム日付
		OrCriteria endOrCriteria = new OrCriteria();
		endOrCriteria.addWhereClause("endDate", null, DAOCriteria.IS_NULL);
		endOrCriteria.addWhereClause("endDate", sysDate,
				DAOCriteria.GREATER_THAN_EQUALS);
		
		criteria.addSubCriteria(startOrCriteria);
		criteria.addSubCriteria(endOrCriteria);

		// お知らせ情報を取得
		List<Information> information = this.informationDAO
				.selectByFilter(criteria);

		if (information == null || information.size() == 0) {
			return null;
		}

		return information.get(0);
	}



	/**
	 * リクエストパラメータで渡された「お知らせ番号」 （主キー値）に該当するお知らせ情報を復帰する。（マイページ TOP 用）<br/>
	 * 公開対象区分 = 「全本会員」、または、「個人」が取得対象になるが、「個人」の場合、お知らせ公開先情報
	 * のユーザーID が引数と一致する必要がある。<br/>
	 * また、システム日付が公開期間中であるお知らせ情報が取得対象となる。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param informationNo 取得対象となるお知らせ番号
	 * @param userId マイページユーザーID
	 * 
	 * @return　お知らせ情報
	 */
	@Override
	public Information searchMyPageInformationPk(String informationNo, String userId){

		// お知らせ情報を取得する為の主キーを対象とした検索条件を生成する。
		DAOCriteria mainCriteria = new DAOCriteria();
		mainCriteria.addWhereClause("informationNo", informationNo);
		
		// 公開対象区分 =「全本会員」、または、「個人」
		OrCriteria dspCriteria = new OrCriteria();
		// 公開対象区分 = 「個人」
		DAOCriteria dspCriteria2 = new DAOCriteria();

		// 公開対象区分 = 「全本会員」、または、「個人」
		// 「個人」の場合、お知らせ公開先情報のユーザーID が引数と一致する
		dspCriteria2.addWhereClause("dspFlg", "2");
		dspCriteria2.addWhereClause("userId", userId);
		dspCriteria.addSubCriteria(dspCriteria2);
		dspCriteria.addWhereClause("dspFlg", "1");
		mainCriteria.addSubCriteria(dspCriteria);

		// システム日付が公開期間中であるお知らせ情報が取得対象となる
		Date sysDate = new Date();
		// startDate IS NUll OR startDate <= システム日付
		OrCriteria startOrCriteria = new OrCriteria();
		startOrCriteria.addWhereClause("startDate", null, DAOCriteria.IS_NULL);	
		startOrCriteria.addWhereClause("startDate", sysDate,
				DAOCriteria.LESS_THAN_EQUALS);
		// endDate IS NUll OR endDate >= システム日付
		OrCriteria endOrCriteria = new OrCriteria();
		endOrCriteria.addWhereClause("endDate", null, DAOCriteria.IS_NULL);
		endOrCriteria.addWhereClause("endDate", sysDate,
				DAOCriteria.GREATER_THAN_EQUALS);
		
		mainCriteria.addSubCriteria(startOrCriteria);
		mainCriteria.addSubCriteria(endOrCriteria);

		// お知らせ情報を取得
		List<JoinResult> information = this.informationListDAO
				.selectByFilter(mainCriteria);

		if (information == null || information.size() == 0) {
			return null;
		}
		
		return (Information) information.get(0).getItems().get(IMFORMATION_ALIA);
	}



	/**
	 * リクエストパラメータで渡されたユーザーID （主キー値）に該当するお知らせ情報を復帰する。（管理ページ用）<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param informationNo 取得対象となるお知らせ番号
	 * @return　お知らせ情報、お知らせ公開先情報
	 */
	@Override
	public JoinResult searchAdminInformationPk(String informationNo) {

		// お知らせ情報を取得する為の主キーを対象とした検索条件を生成する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", informationNo);

		// お知らせ情報を取得
		List<JoinResult> information = this.informationListDAO
				.selectByFilter(criteria);

		if (information == null || information.size() == 0) {
			return null;
		}

		return information.get(0);
	}

}
