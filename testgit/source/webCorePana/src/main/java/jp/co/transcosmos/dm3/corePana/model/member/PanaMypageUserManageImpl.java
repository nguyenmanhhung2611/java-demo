package jp.co.transcosmos.dm3.corePana.model.member;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.HousingManageImpl;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserManageImpl;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.vo.PasswordRemind;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberInfoForm;
import jp.co.transcosmos.dm3.corePana.model.mypage.PanaMypageUserInterface;
import jp.co.transcosmos.dm3.corePana.vo.MemberQuestion;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

/**
 * 会員情報用 Model クラス.
 * <p>
 * <pre>
 * 担当者         修正日      修正内容
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.15	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public class PanaMypageUserManageImpl extends MypageUserManageImpl {

	private static final Log log = LogFactory.getLog(HousingManageImpl.class);

	/** マイページアンケート情報用 DAO */
	private DAO<MemberQuestion> memberQuestionDAO;

	/** パスワード問合せ情報用 DAO */
	private DAO<PasswordRemind> passwordRemindDAOChild;

	/**
	 * パスワード問合せ情報用 DAO を設定する。<br/>
	 * <br/>
	 * @param passwordRemindDAO パスワード問合せ情報用 DAO
	 */
	public void setPasswordRemindDAOChild(DAO<PasswordRemind> passwordRemindDAOChild) {
		this.passwordRemindDAOChild = passwordRemindDAOChild;
	}

	/**
	 * マイページアンケート情報の検索・更新に使用する DAO を設定する。<br/>
	 * <br/>
	 * @param memberQuestionDao マイページアンケート情報の検索・更新DAO
	 */
	public void setMemberQuestionDAO(DAO<MemberQuestion> memberQuestionDAO) {
		this.memberQuestionDAO = memberQuestionDAO;
	}

	/**
	 * パラメータで渡された Form の情報でマイページアンケートを新規登録する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * <br/>
	 * @param inputForm マイページユーザーの入力値を格納した Form オブジェクト
	 *
	 * @return addUserId と同じ値
	 *
 	 * @exception DuplicateException ユーザーID、アンケート番号、選択質問CD の重複
	 */
	public String addMemberQuestion(MemberInfoForm inputForm, String addUserId)
			throws DuplicateException {

		List<MemberQuestion> list = new ArrayList<>();			// マイページアンケート情報登録用

		if (inputForm.getQuestionId() != null ) {
			for (String questionId : inputForm.getQuestionId()){

				if (!StringValidateUtil.isEmpty(questionId)) {

					// バリーオブジェクトを生成して値を設定する。
					MemberQuestion memberQuestion = new MemberQuestion();
					memberQuestion.setUserId(addUserId);
					memberQuestion.setCategoryNo(PanaCommonConstant.COMMON_CATEGORY_NO);
					memberQuestion.setQuestionId(questionId);
					if ("008".equals(questionId)) {
						memberQuestion.setEtcAnswer(inputForm.getEtcAnswer1());
					}
					if ("009".equals(questionId)) {
						memberQuestion.setEtcAnswer(inputForm.getEtcAnswer2());
					}
					if ("010".equals(questionId)) {
						memberQuestion.setEtcAnswer(inputForm.getEtcAnswer3());
					}

					list.add(memberQuestion);
				}
			}

			if (list.size() > 0) {
				try {
					// 選択質問CD が設定されている場合はレコードを追加する。
					this.memberQuestionDAO.insert(list.toArray(new MemberQuestion[list.size()]));
				} catch (DataIntegrityViolationException e) {
					// 追加中に親レコードが削除された場合、NotFoundException の例外をスローする。
					// 例えば、物件の削除処理と競合した場合など..。
					log.warn(e.getMessage(), e);
					throw new NotFoundException();
				}
			}
		}

		return addUserId;
	}

	/**
	 * パラメータで渡された Form の情報でマイページアンケート情報を更新する。<br/>
	 * マイページアンケート情報は DELETE & INSERT で一括更新する。<br/>
	 * バリデーション等のチェック処理は呼び出し元で予め実施しておく事。<br/>
	 * MemberInfoForm の userId プロパティに設定された値を主キー値として更新する。<br/>
	 * <br/>
	 * ※基本仕様では、マイページアンケート情報を表示用アイコンとして登録する。
	 * <br/>
	 * @param inputForm マイページアンケート情報の入力値を格納した Form オブジェクト
	 *
	 */

	public void updateMemberQuestion(MemberInfoForm inputForm, String userId){
		// 既存マイページアンケート情報を削除する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		this.memberQuestionDAO.deleteByFilter(criteria);

		addMemberQuestion(inputForm, userId);
	}

	/**
	 * ユーザーID （主キー値）に該当するマイページアンケート情報を復帰する。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param userId　情報取得対象となるマイページアンケート情報
	 * @return 取得したマイページアンケート情報
	 */
	public List<MemberQuestion> searchMemberQuestionPk(String userId) {

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		// マイページアンケート情報を取得
		List<MemberQuestion> memberQuestionList =  this.memberQuestionDAO.selectByFilter(criteria);

		return memberQuestionList;
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
		PanaMypageUserInterface memberInfo = (PanaMypageUserInterface) buildMemberInfo();


    	// フォームの入力値をバリーオブジェクトに設定する。
    	inputForm.copyToMemberInfo(memberInfo);

    	// ユーザーID を設定
    	memberInfo.setUserId(addUserId);

    	// タイムスタンプ情報を設定する。
    	Date sysDate = new Date();
		// ログイン失敗回数
		memberInfo.setFailCnt(0);
        // フロントサイトから登録した場合は登録経路=WEB、有効区分=有効
        if ("front".equals(((MemberInfoForm) inputForm).getProjectFlg())) {
			// 登録経路
			memberInfo.setEntryRoute(PanaCommonConstant.ENTRY_ROUTE_001);
			// ロックフラグ
			memberInfo.setLockFlg(PanaCommonConstant.LOCK_FLG_0);
        }
    	memberInfo.setInsDate(sysDate);
    	memberInfo.setInsUserId(editUserId);
    	memberInfo.setUpdDate(sysDate);
    	memberInfo.setUpdUserId(editUserId);


		// マイページ会員情報を登録
		try {
			this.memberInfoDAO.insert(new MypageUserInterface[]{memberInfo});

			// マイページアンケート情報を更新する
			MemberInfoForm form = (MemberInfoForm)inputForm;
			updateMemberQuestion(form, addUserId);

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
		super.updateMyPageUser(inputForm, updUserId, editUserId);

		// マイページアンケート情報を更新する
		MemberInfoForm form = (MemberInfoForm)inputForm;
		updateMemberQuestion(form, updUserId);
	}

	/**
	 * 問合せ登録日<br/>
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
	public boolean dateCheck(String remindId)
			throws DuplicateException, NotFoundException {

		// 入力されたお問合せID を取得する。
		// 取得条件は、お問合せID がリクエストパラメータと一致し、未処理、有効期限内の物。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("remindId", remindId);
		criteria.addWhereClause("commitFlg", "0");


		List<PasswordRemind> remind = this.passwordRemindDAOChild.selectByFilter(criteria);

		// 該当データが取得出来ない場合、例外をスローする。
		if (remind.size() == 0){
			throw new NotFoundException();
		}

		Date insDate = remind.get(0).getInsDate();

		// システム日期
		Date now = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		long nowTime = cal.getTimeInMillis();

		long insTime = 0;

		cal.setTime(insDate);
		insTime = cal.getTimeInMillis();

		// 1日間以内（システム日付 - 最終更新日<= 1日）計算
		long betweenDays = (nowTime - insTime) / (1000 * 3600 * 24);

		if(betweenDays>0){
			return false;
		}
		return true;
	}

	/**
	 * ユーザーのメール<br/>
	 * <br/>
	 *
	 * @throws DuplicateException
	 * @throws NotFoundException
	 */
	public String getResultMail(String remindId)
			throws DuplicateException, NotFoundException {

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("remindId", remindId);

		List<PasswordRemind> remindInfo = this.passwordRemindDAOChild.selectByFilter(criteria);
		JoinResult userInfo = searchMyPageUserPk(remindInfo.get(0).getUserId());

		return ((jp.co.transcosmos.dm3.corePana.vo.MemberInfo)userInfo.getItems().get("memberInfo")).getEmail();
	}
}
