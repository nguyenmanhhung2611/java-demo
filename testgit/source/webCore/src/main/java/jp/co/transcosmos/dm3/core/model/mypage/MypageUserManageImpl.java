package jp.co.transcosmos.dm3.core.model.mypage;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.RemindForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.PasswordRemind;
import jp.co.transcosmos.dm3.core.vo.UserInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.transaction.RequestScopeDataSource;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

public class MypageUserManageImpl implements MypageUserManage {

	private static final Log log = LogFactory.getLog(MypageUserManageImpl.class);
	
	/** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;

	/** マイページ会員検索用 DAO */
	protected DAO<JoinResult> memberListDAO;

	/** ユーザーID 情報用 DAO*/
	protected DAO<UserInfo> userInfoDAO;

	/** マイページ会員情報用 DAO */
	protected DAO<MypageUserInterface> memberInfoDAO;

	/** パスワード問合せ情報用 DAO */
	protected DAO<PasswordRemind> passwordRemindDAO;
	
	/** UUID が重複した場合のリトライ回数 */
	protected int maxRetry = 100;

	/** パスワード問合せの有効期限 */
	protected int maxEntryDay = 10;

	/** マイページ会員情報用 の Form ファクトリー */
	protected MypageUserFormFactory mypageFormFactory;

	/** 共通パラメーターオブジェクト */
	protected CommonParameters commonParameters;

	/**
	 * RequestScopeDataSource の Bean ID 名<br/>
	 * 手動でトランザクションを制御する場合、このプロパティに設定されている Bean ID で処理する。<br/>
	 * 通常は、CommonsParameter に設定されている値を使用するが、このプロパティに値が設定
	 * されている場合、そちらの値を優先する。<br/>
	 */
	protected String requestScopeDataSourceId = null;

	
	
	/**
	 * バリーオブジェクトのインスタンスを生成するファクトリーを設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory バリーオブジェクトのファクトリー
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * マイページ会員情報検索用 DAO を設定する。<br/>
	 * <br/>
	 * @param memberListDAO マイページ会員情報検索用 DAO
	 */
	public void setMemberListDAO(DAO<JoinResult> memberListDAO) {
		this.memberListDAO = memberListDAO;
	}

	/**
	 * ユーザーID 情報用 DAO<br/>
	 * <br/>
	 * @param userInfoDAO ユーザーID 情報用 DAO
	 */
	public void setUserInfoDAO(DAO<UserInfo> userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
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
	 * パスワード問合せ情報用 DAO を設定する。<br/>
	 * <br/>
	 * @param passwordRemindDAO パスワード問合せ情報用 DAO
	 */
	public void setPasswordRemindDAO(DAO<PasswordRemind> passwordRemindDAO) {
		this.passwordRemindDAO = passwordRemindDAO;
	}

	/**
	 * お問合せ情報の主キーとして使用する UUID が、重複した場合のリトライ回数を設定する。<br/>
	 * 初期値は 100 回なので、通常は変更する必要はない。<br/>
	 * <br/>
	 * @param maxRetry リトライ回数
	 */
	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	/**
	 * お問合せ情報の有効日数を設定する。<br/>
	 * 初期値は 10日で、値を変えたい場合にこのプロパティを設定する。<br/>
	 * <br/>
	 * @param maxEntryDay 有効日数
	 */
	public void setMaxEntryDay(int maxEntryDay) {
		this.maxEntryDay = maxEntryDay;
	}

	/**
	 * マイページ会員情報用 の Form ファクトリーを設定する。<br/>
	 * <br/>
	 * @param mypageFormFactory マイページ会員情報用 の Form ファクトリー
	 */
	public void setMypageFormFactory(MypageUserFormFactory mypageFormFactory) {
		this.mypageFormFactory = mypageFormFactory;
	}

	/**
	 * RequestScopeDataSource の Bean ID 名を設定する。<br/>
	 * 通常、commonParameters で設定されている値を使用するので、このプロパティを使用する事はない。<br/>
	 * <br/>
	 * @param requestScopeDataSourceId Bean ID
	 */
	public void setRequestScopeDataSourceId(String requestScopeDataSourceId) {
		this.requestScopeDataSourceId = requestScopeDataSourceId;
	}

	/**
	 * 共通パラメーターオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメーターオブジェクト
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	

	
	/**
	 * ユーザーID を採番し、ユーザーID 情報へ追加する。<br/>
	 * フロントサイトの場合、この機能は匿名認証のフィルターから使用される。<br/>
	 * 管理機能からマイページ会員を登録する場合、addMyPageUser() を使用する前にこのメソッドを
	 * 実行してユーザーID を採番する必要がある。<br/>
	 * <br/>
	 * @param editUserId 更新時のタイムスタンプとなるユーザーID　（フロント側の場合、null）
	 * 
	 * @return 追加されたログインユーザーオブジェクト
	 * 
	 */
	@Override
	public LoginUser addLoginID(String editUserId){

    	// ユーザーID 採番用のバリーオブジェクトのインスタンスを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）
    	UserInfo userInfo = buildUserInfo();

    	// UserInfo をバイパスする様なカスタマイズを想定し、もし　buildUserInfo()
    	// がインスタンスを復帰しない場合はユーザーID を採番せずに null を復帰する。<br/>
    	if (userInfo == null) return null;


    	// バリーオブジェクトに値を設定する。 （設定が必要なのは登録日のみ。）
    	userInfo.setInsDate(new Date());

    	//　ユーザーID を採番し、ユーザーID を復帰
		this.userInfoDAO.insert(new UserInfo[]{userInfo});
		return userInfo;
	}



	/**
	 * パラメータで渡された Form の情報と指定されたユーザーIDでマイページユーザーを新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * フロント側からマイページ登録する場合、ログインID は既に採番されているので、addUserId にはその値を指定する。<br/>
	 * 管理機能から使用する場合は、addLoginID() を使用してログインID を採番しておく事。<br/>
	 * また、フロント側からマイページ登録する場合、editUserId は、addUserId と同じ値を設定する。<br/>
	 * <br/>
	 * @param inputForm マイページユーザーの入力値を格納した Form オブジェクト
	 * @param addUserId マイページ追加対象となるユーザーID
	 * @param editUserId 更新時のタイムスタンプとなるユーザーID
	 * 
	 * @return addUserId と同じ値
	 * 
 	 * @exception DuplicateException メールアドレス（ログインID） の重複
	 */
	@Override
	public String addMyPageUser(MypageUserForm inputForm, String addUserId, String editUserId)
			throws DuplicateException {

    	// 新規登録処理の場合、入力フォームの値を設定するバリーオブジェクトを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）
    	MypageUserInterface memberInfo = buildMemberInfo();


    	// フォームの入力値をバリーオブジェクトに設定する。
    	inputForm.copyToMemberInfo(memberInfo);

    	// ユーザーID を設定
    	memberInfo.setUserId(addUserId);
    	
    	// タイムスタンプ情報を設定する。
    	Date sysDate = new Date();
    	memberInfo.setInsDate(sysDate);
    	memberInfo.setInsUserId(editUserId);
    	memberInfo.setUpdDate(sysDate);
    	memberInfo.setUpdUserId(editUserId);

    	
		// マイページ会員情報を登録
		try {
			this.memberInfoDAO.insert(new MypageUserInterface[]{memberInfo});

		} catch (DuplicateKeyException e){
			// マイページ会員の登録では、メールアドレス（ログインID） の入力によってキー重複エラーが発生する
			// 場合がある。　その場合、ロールバックして例外をスローする。
			// 本来は、メールアドレス（ログインID） 以外の一意制約エラーは例外として扱うべきだが、判定方法が
			// ＤＢベンダーに依存するので、そこまで細かく判定しない。
			manualRollback();
			throw new DuplicateException();
		} catch (Exception e){
			e.printStackTrace();
		}

		return addUserId;
	}

	
	
	/**
	 * マイページ会員の更新を行う<br/>
	 * 主キーとなる更新条件は、MypageUserForm の buildPkCriteria() が復帰する検索条件を使
	 * 用する。
	 * <br/>
	 * @param inputForm マイページユーザーの入力値を格納した Form オブジェクト
	 * @param addUserId マイページ更新対象となるユーザーID
	 * @param editUserId 更新時のタイムスタンプとなるユーザーID
	 * 
	 * @throws DuplicateException 
	 * @throws NotFoundException 
	 */
	@Override
	public void updateMyPageUser(MypageUserForm inputForm, String updUserId, String editUserId)
			throws DuplicateException, NotFoundException {

    	// 更新処理の場合、更新対象データを取得する。
        DAOCriteria criteria = inputForm.buildPkCriteria(updUserId);

        // マイページ会員情報を取得
        List<JoinResult> userInfo = this.memberListDAO.selectByFilter(criteria);

        // 該当するデータが存在しない場合は UPDATE を実行できないので、例外をスローする。
        // （例えば、マイページ会員の検索後に別のセッションから取得対象ユーザーを削除した場合など）
		if (userInfo == null || userInfo.size() == 0 ) {
        	throw new NotFoundException();
		}


        // マイページ会員情報を取得し、入力した値で上書きする。
    	// ※ パスワードの場合、入力された場合のみ上書きされる。
    	MypageUserInterface mypageUser
    		= (MypageUserInterface) userInfo.get(0).getItems().get(this.commonParameters.getMemberDbAlias()); 
    	inputForm.copyToMemberInfo(mypageUser);

    	// タイムスタンプ情報を設定する。
    	mypageUser.setUpdDate(new Date());
    	mypageUser.setUpdUserId(editUserId);


    	try {
    		// マイページ会員情報を更新
        	// 現時点では厳格な競合コントロールは行っていない。　（システムとしての制限事項）
        	// 将来、楽観的ロックによる競合管理を実装する可能性がある。
    		this.memberInfoDAO.update(new MypageUserInterface[]{mypageUser});

    	} catch (DuplicateKeyException e) {
			// マイページ会員情報の登録では、メールアドレス（ログインID） の入力によってキー重複エラーが発生する
			// 場合がある。　その場合、ロールバックして例外をスローする。
			// 本来は、メールアドレス以外の一意制約エラーは例外として扱うべきだが、判定方法が
			// ＤＢベンダーに依存するので、そこまで細かく判定しない。
			manualRollback();
			throw new DuplicateException();
    	}
	}

	
	
	/**
	 * マイページ会員の削除を行う<br/>
	 * 主キーとなる削除条件は、inputForm の buildPkCriteria() が復帰する検索条件
	 * を使用する。<br/>
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * <br/>
	 * @param userId 削除対象となるマイページ会員の主キー値
	 */
	@Override
	public void delMyPageUser(String userId) {

		// note
		// このメソッドはフロント側からも使用される可能性がある。
		// その場合、MypageUserSearchForm　を使用させると、userId をリクエストパラメータ
		// でやり取りされる可能性があり、セキュリティ的にリスクが発生する。
		// また、マイページ会員情報はカスタマイズによってはテーブル自体が MemberInfo でない
		// 可能性があり、物理名を model 側で把握できない。
		// よって、model 側で Form のインスタンスをビルドして処理を委譲する。

		MypageUserSearchForm searchForm = this.mypageFormFactory.createMypageUserSearchForm();
		searchForm.setUserId(userId);

		// マイページ会員情報の主キー値を削除条件としたオブジェクトを生成
		DAOCriteria criteria = searchForm.buildPkCriteria();

		// マイページ会員情報をを削除する。
		// ※退会後も、マイページ会員以外のユーザーに提供している機能があるので、ログインID 情報の削除は
		// 行わない。
		this.memberInfoDAO.deleteByFilter(criteria);

	}



	/**
	 * メールアドレス（ログインID） が未使用かをチェックする。<br/>
	 * フォームに設定されているメールアドレス（ログインID） が未使用かをチェックする。<br/>
	 * もし、ユーザーＩＤ が引数で渡された場合、そのユーザーID を除外してチェックする。<br/>
	 * <br/>
	 * @param inputForm チェック対象となる入力値
	 * @param userId チェック対象となるマイページユーザーのユーザーID （新規登録時は null）
	 *  
	 * @return true = 利用可、false = 利用不可
	 * 
	 */
	@Override
	public boolean isFreeLoginId(MypageUserForm inputForm, String userId) {

		// Form からチェックに必要な検索条件を生成して検索する。
		DAOCriteria criteria = inputForm.buildFreeLoginIdCriteria(userId);
		List<MypageUserInterface> list = this.memberInfoDAO.selectByFilter(criteria);

		if (list.size() == 0) {
			return true;

		} else {
			return false;
		}
	}



	/**
	 * マイページ会員を検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、マイページ会員を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @return 該当件数
	 */
	@Override
	public int searchMyPageUser(MypageUserSearchForm searchForm) {

        // マイページ会員を検索する条件を生成する。
        DAOCriteria criteria = searchForm.buildCriteria();

        // 管理ユーザーの検索
        List<JoinResult> userList;
        try {
        	userList = this.memberListDAO.selectByFilter(criteria);

        } catch(NotEnoughRowsException err) {
 
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			userList = this.memberListDAO.selectByFilter(criteria);
        }

        searchForm.setRows(userList);
        
        return userList.size();

	}



	/**
	 * ユーザーID （主キー値）に該当するマイページ会員情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param userId　情報取得対象となるマイページ会員情報
	 * @return 取得したマイページ会員情報
	 */
	@Override
	public JoinResult searchMyPageUserPk(String userId) {

		// note
		// このメソッドはフロント側からも使用される可能性がある。
		// その場合、MypageUserSearchForm　を使用させると、userId をリクエストパラメータ
		// でやり取りされる可能性があり、セキュリティ的にリスクが発生する。
		// また、マイページ会員情報はカスタマイズによってはテーブル自体が MemberInfo でない
		// 可能性があり、物理名を model 側で把握できない。
		// よって、model 側で Form のインスタンスをビルドして処理を委譲する。

		MypageUserSearchForm searchForm = this.mypageFormFactory.createMypageUserSearchForm();
		searchForm.setUserId(userId);

		// Form に処理を委譲し、主キーの検索条件を生成する。
		DAOCriteria criteria = searchForm.buildPkCriteria();

        // マイページ会員情報を取得
        List<JoinResult> userInfo = this.memberListDAO.selectByFilter(criteria);

		if (userInfo == null || userInfo.size() == 0 ) {
			return null;
		}

		return userInfo.get(0);
	}



	/**
	 * パスワード変更の登録処理を行う。<br/>
	 * 指定されたメールアドレスの妥当性を確認し、問題がなければパスワード問合せ情報を DB に登録
	 * する。　正常に処理が行えた場合、採番したお問い合わせID （UUID）を復帰する。<br/>
	 * ※トランザクションの管理は呼出元で制御する事。<br/>
	 *   （その為、メール送信はこのメソッド内で実装しない。）<br/>
	 * <br/>
	 * @param inputForm パスワード問合せの対象となる入力情報
	 * @param entryUserId 匿名認証時に払い出されているユーザーID
	 * 
	 * @return 正常終了時 UUID、メアドが存在しない場合など、エラー発生時は null
	 * 
 	 * @exception NotFoundException 更新対象なし
	 *
	*/
	@Override
	public String addPasswordChangeRequest(RemindForm inputForm, String entryUserId)
		throws NotFoundException {

		// 入力されたメールアドレスが存在するかをチェックする。
		DAOCriteria criteria = inputForm.buildMailCriteria();
		List<MypageUserInterface> member = this.memberInfoDAO.selectByFilter(criteria);

		// 該当データが存在しなかった場合は例外をスローする。
		if (member.size() == 0){
			throw new NotFoundException();
		};

		
		// パスワード問合せのバリーオブジェクトへ値を設定する。
		PasswordRemind passwordRemind = buildPasswordRemind(member.get(0), entryUserId);

		// 戻り値となる UUID （お問合せID）
		// ＤＢへの登録が正常終了した場合のみ値が設定される。
		String returnId = null;

		// 万が一、UUID が重複した場合、リトライするので、閾値回数分登録を繰り返す。
		for (int i=0; i<this.maxRetry; ++i){

			//　UUID の採番
			// UUID Ver 1 は使用しない事。　（サーバーの IP が抜かれる可能性がある。）
			passwordRemind.setRemindId(UUID.randomUUID().toString());

			// 登録処理
			try {
				this.passwordRemindDAO.insert(new PasswordRemind[]{passwordRemind});
				returnId = passwordRemind.getRemindId();
				break;

			} catch (DuplicateKeyException e) {
				// もし、キー重複が発生した場合は新たな UUID でリトライする。
				// ※確立的には非常に低い...。
				continue;
			}
		}


		if (returnId == null) {
			// もし閾値に達して採番できない場合は例外をスローする。
			// UUID は重複する事自体が稀なので、100 回繰り返しても重複する場合は別の問題が
			// 考えられる。
			throw new RuntimeException ("UUID retry count is max.");
		}

		// 採番したお問合せID （UUID）を復帰
		return returnId;
	}



	/**
	 * パスワードの変更処理を行う。<br/>
	 * パスワードの入力確認など、一般的なバリデーションは呼出し元で実施しておく事。<br/>
	 * リクエストパラメータで渡された UUID が期限切れの場合、もしくは、該当レコードがパスワード問合せ
	 * 情報に存在しない場合は例外をスローする。<br/>
	 * パスワードの更新時に使用する主キーの値は、パスワード問合せ情報から取得する事。<br/>
	 * <br/>
	 * @param inputForm 新しいパスワードの入力値が格納された Form
	 * @param entryUserId 匿名認証時に払い出されているユーザーID
	 * 
 	 * @exception NotFoundException 更新対象なし
	 */
	@Override
	public void changePassword(PwdChangeForm inputForm, String entryUserId)
			throws NotFoundException {

		// 入力されたお問合せID を取得する。
		// 取得条件は、お問合せID がリクエストパラメータと一致し、未処理、有効期限内の物。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("remindId", inputForm.getRemindId());
		criteria.addWhereClause("commitFlg", "0");

		Date limitDate = DateUtils.addDays(new Date(), -1 * this.maxEntryDay);
		criteria.addWhereClause("insDate", limitDate, DAOCriteria.GREATER_THAN_EQUALS);

		List<PasswordRemind> remind = this.passwordRemindDAO.selectByFilter(criteria);
		
		// 該当データが取得出来ない場合、例外をスローする。
		if (remind.size() == 0){
			throw new NotFoundException();
		}

		// note
		// 当初は、該当データ無しと、期限切れを別に扱おうと思ったが、セキュリティ的に餌を与える
		// 事になるので、どちらも同等に扱う事にした。


		// マイページ会員を取得する為の主キーを対象とした検索条件を生成する。
		String userId = remind.get(0).getUserId();
        criteria = inputForm.buildPkCriteria(userId);

        // パスワードを更新する為の　UpdateExpression　を生成して更新する。
        UpdateExpression[] expression = inputForm.buildPwdUpdateExpression(entryUserId);
        this.memberInfoDAO.updateByCriteria(criteria, expression);


        // 問合せ情報のステータスを更新する為の主キー条件を生成する。
		criteria = new DAOCriteria();
		criteria.addWhereClause("remindId", inputForm.getRemindId());

        // 問合せ情報のステータス更新用の UpdateExpression　を生成して更新する。
		expression = new UpdateExpression[] {new UpdateValue("commitFlg", "1"),
											 new UpdateValue("updDate", new Date()),
											 new UpdateValue("updUserId", entryUserId)};

        this.passwordRemindDAO.updateByCriteria(criteria, expression);

        // 一度採番された UUID を永久欠番にする為、パスワード問合せ情報は消去しない。

	}



	/**
	 * ユーザーID 用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return ユーザーID オブジェクト
	 */
	protected UserInfo buildUserInfo() {

		// 重要
		// このシステムではマイページユーザーの情報は、ログインID 情報に存在する事を前提としている。
		// 個別カスタマイズ等でログインID 情報を排除する場合は、このメソッドの戻り値が null になる
		// 様にオーバーライドする必要がある。
		// このメソッドの戻り値が null の場合、管理機能はログインID情報の更新を行わない。
		// ※DB の制約削除や、参照系機能の DAO を修正するなど、それなりの対応は必要。

		return (UserInfo) this.valueObjectFactory.getValueObject("UserInfo"); 
	}



	/**
	 * マイページユーザー用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return MypageUserInterface を実装した管理ユーザーオブジェクト
	 */
	protected MypageUserInterface buildMemberInfo() {

		// 重要
		// もし管理ユーザーテーブルを AdminLoginInfo 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		return (MypageUserInterface) this.valueObjectFactory.getValueObject("MemberInfo"); 
	}

	

	/**
	 * パスワード問合せ用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @param member 該当となる会員情報
	 * @param editUserId 登録者ID （匿名認証時に払い出されたID）
	 * 
	 * @return PasswordRemind パスワード問合せ情報
	 */
	protected PasswordRemind buildPasswordRemind(MypageUserInterface member, String editUserId){
		//　ファクトリーを使用してバリーオブジェクトを生成する。
		PasswordRemind passwordRemind = (PasswordRemind) valueObjectFactory.getValueObject("PasswordRemind");

		// パスワード問合せ情報は、入力値をＤＢに保存しないので、UUID 以外の値の設定もおこなってしまう。

		// ユーザーID
		passwordRemind.setUserId((String)member.getUserId());
		// 変更確定フラグ （未確定固定）
		passwordRemind.setCommitFlg("0");
		// 問合せ登録日
		passwordRemind.setInsDate(new Date());

		// 問合せ登録者
		// 匿名認証時に何かしらの UserID は払い出されているので、その値を設定しておく
		passwordRemind.setInsUserId(editUserId);

		return passwordRemind;
	}

	
	

	/**
	 * 手動でロールバック処理を行う。<br/>
	 * <br/>
	 */
	protected void manualRollback(){

		// 共通パラメータオブジェクトから値を取得する。
		String beanId = this.commonParameters.getRequestScopeDataSourceId();
		// もし、このクラスのプロパティに値が設定されていた場合、そちらの値を優先する。
		if (!StringValidateUtil.isEmpty(this.requestScopeDataSourceId)){
			beanId = this.requestScopeDataSourceId;
		}

		// ロールバック処理
		RequestScopeDataSource.closeCurrentTransaction(beanId, true);
	}

}
