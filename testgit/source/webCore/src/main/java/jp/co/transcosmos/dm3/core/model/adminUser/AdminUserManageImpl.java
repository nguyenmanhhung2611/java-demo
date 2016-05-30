package jp.co.transcosmos.dm3.core.model.adminUser;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.vo.AdminRoleInfo;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.login.v3.LockSupportAuth;
import jp.co.transcosmos.dm3.transaction.RequestScopeDataSource;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * 管理ユーザーメンテナンス用 Model クラス.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.03	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 * 
 */
public class AdminUserManageImpl implements AdminUserManage {

	private static final Log log = LogFactory.getLog(AdminUserManageImpl.class);

	/** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;
	
	/** 管理ユーザー検索用 DAO */
	protected DAO<JoinResult> adminUserListDAO;

	/** 管理ユーザー情報更新用 DAO */
	protected DAO<AdminUserInterface> adminLoginInfoDAO;
	
	/** 管理ユーザーロール情報更新用 DAO */
	protected DAO<AdminUserRoleInterface> adminRoleInfoDAO;
	
	/** 共通パラメーターオブジェクト */
	protected CommonParameters commonParameters;

	/** 認証処理クラス */
	protected LockSupportAuth authentication;

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
	 * 管理ユーザー検索用 DAO の設定<br>
	 * <br>
	 * @param adminUserListDAO 管理ユーザー検索用 DAO
	 */
	public void setAdminUserListDAO(DAO<JoinResult> adminUserListDAO) {
		this.adminUserListDAO = adminUserListDAO;
	}

	/**
	 * 管理ユーザー情報更新用 DAO の設定<br/>
	 * <br/>
	 * @param adminLoginInfoDAO 管理ユーザー更新用 DAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminUserInterface> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}

	/**
	 * 管理ユーザーロール情報更新用 DAO の設定<br/>
	 * ロール情報をユーザー情報で管理する場合、この DAO は設定しない。
	 * <br/>
	 * @param adminRoleInfoDAO 管理ユーザー更新用 DAO
	 */
	public void setAdminRoleInfoDAO(DAO<AdminUserRoleInterface> adminRoleInfoDAO) {
		this.adminRoleInfoDAO = adminRoleInfoDAO;
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
	 * 認証処理クラスを設定する。<br/>
	 * <br/>
	 * @param authentication ロック処理をサポートしている認証処理クラス
	 */
	public void setAuthentication(LockSupportAuth authentication) {
		this.authentication = authentication;
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
	 * 管理ユーザーの追加を行う<br/>
	 * もし、ユーザー情報とロール情報を同一テーブルで管理する場合、adminRoleInfoDAO プロパティには
	 * null を設定する事。<br/>
	 * 管理ユーザーID は自動採番されるので、AdminUserForm の userId プロパティには値を設定しない事。<br/>
	 * ※設定しても問題ないが、登録処理では使用されない。<br/>
	 * <br/>
	 * @param inputForm 管理ユーザーの入力値を格納した Form オブジェクト
 	 * @param editUserId ログインユーザーＩＤ（更新情報用）
	 * @return 採番された管理者ユーザーID
	 * 
	 * @throws DuplicateException 登録するログインID が重複した場合
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@Override
	public String addAdminUser(AdminUserForm inputForm, String editUserId)
			throws DuplicateException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
    	// 新規登録処理の場合、入力フォームの値を設定するバリーオブジェクトを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）
    	AdminUserInterface adminUser = buildAdminUserInfo();

    	// フォームの入力値をバリーオブジェクトに設定する。
    	inputForm.copyToAdminUserInfo(adminUser);

    	// タイムスタンプ情報を設定する。
    	Date sysDate = new Date();
		adminUser.setInsDate(sysDate);
		adminUser.setInsUserId(editUserId);
    	adminUser.setUpdDate(sysDate);
		adminUser.setUpdUserId(editUserId);


		// 管理ユーザー情報を登録
		try {
			this.adminLoginInfoDAO.insert(new AdminUserInterface[]{adminUser});

		} catch (DuplicateKeyException e){
			// 管理ユーザーの登録では、ログインID の入力によってキー重複エラーが発生する
			// 場合がある。　その場合、ロールバックして例外をスローする。
			// 本来は、ログインID 以外の一意制約エラーは例外として扱うべきだが、判定方法が
			// ＤＢベンダーに依存するので、そこまで細かく判定しない。
			manualRollback();
			throw new DuplicateException();
		}


		// ロール情報用の DAO が存在しない場合（ユーザー管理情報でロールID を管理している場合）、
		// ロール管理のテーブルは存在しないのでレコードの追加は行わない。
		if (this.adminRoleInfoDAO != null){
	    	AdminUserRoleInterface adminRoles = buildAdminRoleInfo();
			inputForm.copyToAdminRoleInfo(adminRoles);
			// 新規登録時は、Form 内に userId が設定されていないので採番した値を自身で設定する必要がある。
			adminRoles.setUserId((String)adminUser.getUserId());
			this.adminRoleInfoDAO.insert(new AdminUserRoleInterface[]{adminRoles});
		}

		return (String)adminUser.getUserId();
	}

	
	
	/**
	 * 管理ユーザーの更新を行う<br/>
	 * もし、ユーザー情報とロール情報を同一テーブルで管理する場合、adminRoleInfoDAO プロパティには
	 * null を設定する事。<br/>
	 * 主キーとなる更新条件は、AdminUserForm の buildPkCriteria() が復帰する検索条件を使
	 * 用する。
	 * <br/>
	 * @param inputForm 管理ユーザーの入力値を格納した Form オブジェクト
 	 * @param editUserId ログインユーザーＩＤ（更新情報用）
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@Override
	public void updateAdminUser(AdminUserForm inputForm, String editUserId)
			throws DuplicateException, NotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException{

    	// 更新処理の場合、更新対象データを取得する。
        DAOCriteria criteria = inputForm.buildPkCriteria();

        // 管理ユーザー情報を取得
        List<JoinResult> userInfo = this.adminUserListDAO.selectByFilter(criteria);

        // 該当するデータが存在しない場合は UPDATE を実行できないので、例外をスローする。
        // （例えば、管理ユーザーの検索後に別のセッションから取得対象ユーザーを削除した場合など）
		if (userInfo == null || userInfo.size() == 0 ) {
        	throw new NotFoundException();
		}


        // 管理ユーザー情報を取得し、入力した値で上書きする。
    	// ※ パスワードの場合、入力された場合のみ上書きされる。
    	AdminUserInterface adminUser
    		= (AdminUserInterface) userInfo.get(0).getItems().get(this.commonParameters.getAdminUserDbAlias()); 
    	inputForm.copyToAdminUserInfo(adminUser);
    	AdminUserRoleInterface adminRoles = buildAdminRoleInfo();
    	inputForm.copyToAdminRoleInfo(adminRoles);

    	// タイムスタンプ情報を設定する。
		adminUser.setUpdDate(new Date());
		adminUser.setUpdUserId(editUserId);


    	try {
    		// 管理ユーザー情報を更新
        	// 現時点では厳格な競合コントロールは行っていない。　（システムとしての制限事項）
        	// 将来、楽観的ロックによる競合管理を実装する可能性がある。
    		this.adminLoginInfoDAO.update(new AdminUserInterface[]{adminUser});

    	} catch (DuplicateKeyException e) {
			// 管理ユーザーの登録では、ログインID の入力によってキー重複エラーが発生する
			// 場合がある。　その場合、ロールバックして例外をスローする。
			// 本来は、ログインID 以外の一意制約エラーは例外として扱うべきだが、判定方法が
			// ＤＢベンダーに依存するので、そこまで細かく判定しない。
			manualRollback();
			throw new DuplicateException();
    	}


		// ロール情報用の DAO が存在しない場合（ユーザー管理情報でロールID を管理している場合）、
		// ロール管理のテーブルは存在しないのでレコードの追加は行わない。
		if (this.adminRoleInfoDAO != null){
			// ロール情報は、複数件ある事を想定し、Delete And Insert で更新する。
			// 管理者ログインID をキーとして削除した後に、ロール情報を追加する。
			this.adminRoleInfoDAO.deleteByFilter(inputForm.buildPkCriteria());
			this.adminRoleInfoDAO.insert(new AdminUserRoleInterface[]{adminRoles});
		}

	}

	
	
	/**
	 * 管理ユーザーの削除を行う<br/>
	 * 主キーとなる削除条件は、inputForm の buildPkCriteria() が復帰する検索条件
	 * を使用する。<br/>
	 * また、削除対象レコードが存在しない場合でも正常終了として扱う事。<br/>
	 * <br/>
	 * @param inputForm 削除対象となる管理ユーザーの主キー値を格納した Form オブジェクト
	 */
	@Override
	public void delAdminUser(AdminUserForm inputForm){

		// 管理ユーザーの主キー値を削除条件としたオブジェクトを生成
		DAOCriteria criteria = inputForm.buildPkCriteria();

		// 管理ユーザーロール情報を削除する。
		this.adminRoleInfoDAO.deleteByFilter(criteria);

		// 管理ユーザー情報を削除する。
		this.adminLoginInfoDAO.deleteByFilter(criteria);

	}



	/**
	 * アカウントロックステータスを変更する。<br/>
	 * <br/>
	 * @param inputForm 変更対象となる管理ユーザーの主キー値を格納した Form オブジェクト
	 * @param locked false = 通常モードに設定、true = ロックモードに設定
 	 * @param editUserId ログインユーザーＩＤ（更新情報用）
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	@Override
	public void changeLockStatus(AdminUserForm inputForm, boolean locked, String editUserId)
			throws NotFoundException {

    	// ロック対象となる管理ユーザー情報を取得する。
        DAOCriteria criteria = inputForm.buildPkCriteria();
        List<AdminUserInterface> userInfo = this.adminLoginInfoDAO.selectByFilter(criteria);

        // 該当するデータが存在しない場合は更新処理ができないので、例外をスローする。
        // （例えば、管理ユーザーの検索後に別のセッションから取得対象ユーザーを削除した場合など）
		if (userInfo == null || userInfo.size() == 0 ) {
        	throw new NotFoundException();
		}

		if (locked){
			// ロックモードに設定
			this.authentication.changeToLock(userInfo.get(0));
		} else {
			// 通常モードに設定
			this.authentication.changeToUnlock(userInfo.get(0));
		}

		// タイムスタンプを更新
		UpdateExpression[] expression = inputForm.buildTimestampUpdateExpression(editUserId);
        this.adminLoginInfoDAO.updateByCriteria(criteria, expression);

	}


	
	/**
	 * ロック状態の判定<br/>
	 * フレームワークのロックステータスチェックへ処理を委譲する。<br/>
	 * <br/>
	 * @param targetUser チェック対象ユーザー情報
	 * @return true = ロック中、false = 正常
	 */
	@Override
	public boolean isLocked(AdminUserInterface targetUser) {
		return this.authentication.isLocked(targetUser);
	}


	
	/**
	 * ログインID が未使用かをチェックする。<br/>
	 * フォームに設定されているログインID が未使用かをチェックする。<br/>
	 * もし、ユーザーＩＤ が設定されている場合、そのユーザーID を除外してチェックする。<br/>
	 * <br/>
	 * @param inputForm チェック対象となる入力値
	 *  
	 * @return true = 利用可、false = 利用不可
	 * 
	 */
	@Override
	public boolean isFreeLoginId(AdminUserForm inputForm) {

		// Form からチェックに必要な検索条件を生成して検索する。
		DAOCriteria criteria = inputForm.buildFreeLoginIdCriteria();
		List<AdminUserInterface> list = this.adminLoginInfoDAO.selectByFilter(criteria);

		if (list.size() == 0) {
			return true;
			
		} else {
			return false;
		}
	}

	
	
	/**
	 * 管理ユーザーを検索し、結果リストを復帰する。<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、管理ユーザーを検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @return 該当件数
	 */
	@Override
	public int searchAdminUser(AdminUserSearchForm searchForm){
		
        // 管理ユーザーを検索する条件を生成する。
        DAOCriteria criteria = searchForm.buildCriteria();

        // 管理ユーザーの検索
        List<JoinResult> userList;
        try {
        	userList = this.adminUserListDAO.selectByFilter(criteria);

        } catch(NotEnoughRowsException err) {
 
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			userList = this.adminUserListDAO.selectByFilter(criteria);
        }

        searchForm.setRows(userList);

        return userList.size();

	}



	/**
	 * リクエストパラメータで渡されたユーザーID （主キー値）に該当する管理ユーザー情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param searchForm　検索結果となる JoinResult
	 * @return 取得した管理ユーザー情報
	 */
	@Override
	public JoinResult searchAdminUserPk(AdminUserSearchForm searchForm) {

        // 管理ユーザー情報を取得する為の主キーを対象とした検索条件を生成する。
        DAOCriteria criteria = searchForm.buildPkCriteria();

        // 管理ユーザー情報を取得
        List<JoinResult> userInfo = this.adminUserListDAO.selectByFilter(criteria);

		if (userInfo == null || userInfo.size() == 0 ) {
			return null;
		}
		
		return userInfo.get(0);
	}



	/**
	 * パスワードの変更処理を行う。<br/>
	 * 主キーとなる更新条件は、AdminUserForm の buildPkCriteria() が復帰する検索条件
	 * を使用する。<br/>
	 * <br/>
	 * @param inputForm パスワード変更の入力情報
	 * @param updUserId 更新対象ユーザーID
 	 * @param editUserId ログインユーザーＩＤ（更新情報用）
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	@Override
	public void changePassword(PwdChangeForm inputForm, String updUserId, String editUserId)
			throws NotFoundException {

        // 管理ユーザー情報を取得する為の主キーを対象とした検索条件を生成する。
        DAOCriteria criteria = inputForm.buildPkCriteria(updUserId);

        // パスワード更新用の UpdateExpression を生成する。
        UpdateExpression[] expression = inputForm.buildPwdUpdateExpression(editUserId);

        if (this.adminLoginInfoDAO.updateByCriteria(criteria, expression) == 0){
        	// 通常、更新対象無しの場合は無視しているが、パスワード変更の場合、セキュリティ的な
        	// 影響もあるので更新できている事を確認する。
        	throw new NotFoundException();
        }
	}



	/**
	 * 管理ユーザー用のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * <br/>
	 * @return AdminUserInterface を実装した管理ユーザーオブジェクト
	 */
	protected AdminUserInterface buildAdminUserInfo() {

		// 重要
		// もし管理ユーザーテーブルを AdminLoginInfo 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		return (AdminUserInterface) this.valueObjectFactory.getValueObject("AdminLoginInfo"); 
	}



	/**
	 * 管理ユーザーのロール情報のバリーオブジェクトを作成するファクトリーメソッド<br/>
	 * バリーオブジェクトを拡張した場合はこのメソッドをオーバーライドする事。<br/>
	 * <br/>
	 * @return ロール情報バリーオブジェクト
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	protected AdminUserRoleInterface buildAdminRoleInfo()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		// 重要
		// もし管理ユーザーロールテーブルを AdminRoleInfo 以外のオブジェクトに変更した場合、
		// このメソッドを適切なバリーオブジェクトを生成する様にオーバーライドする事。

		// 特に、ロールID を管理ユーザー情報で管理する場合、ロール情報テーブルが存在しない
		// ので、null を復帰する様にオーバーライドする必要がある。

		return (AdminRoleInfo) this.valueObjectFactory.getValueObject("AdminRoleInfo");
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
