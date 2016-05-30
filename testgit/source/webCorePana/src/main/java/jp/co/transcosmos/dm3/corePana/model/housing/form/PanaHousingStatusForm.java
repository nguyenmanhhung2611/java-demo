package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.util.List;

import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * リフォーム情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 *
 * <pre>
 * 担当者      修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS     2015.03.10  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaHousingStatusForm {

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	PanaHousingStatusForm(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * デフルトコンストラクター。<br/>
	 * <br/>
	 *
	 */
	PanaHousingStatusForm() {
		super();
	}

	/** 公開区分 */
	private String hiddenFlg;

	/** ステータス */
	private String statusCd;

	/** 会員番号 */
	private String userId;

	/** 氏名 */
	private String memberName;

	/** 電話番号 */
	private String tel;

	/** メールアドレス */
	private String email;

	/** 備考 */
	private String note;

	/** 備考(表示用） */
	private String showNote;

	/** システム物件CD */
	private String sysHousingCd;

	/** システムリフォームCD */
	private String sysReformCd;

	/** イベントフラグ */
	private String command;

	/** システム建物番号 */
	private String sysBuildingCd;

	/** 物件CD */
	private String housingCd;

	/** 物件名称 */
	private String displayHousingName;

	/** Readonlyフラグ */
	private String readonlyFlg;

	/** 物件種別コード */
	private String housingKindCd;

	/**
	 * 公開区分 を取得する。<br/>
	 * <br/>
	 *
	 * @return 公開区分
	 */
	public String getHiddenFlg() {
		return hiddenFlg;
	}

	/**
	 * 公開区分 を設定する。<br/>
	 * <br/>
	 *
	 * @param hiddenFlg
	 */
	public void setHiddenFlg(String hiddenFlg) {
		this.hiddenFlg = hiddenFlg;
	}

	/**
	 * ステータス を取得する。<br/>
	 * <br/>
	 *
	 * @return ステータス
	 */
	public String getStatusCd() {
		return statusCd;
	}

	/**
	 * ステータス を設定する。<br/>
	 * <br/>
	 *
	 * @param statusCd
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * 会員番号 を取得する。<br/>
	 * <br/>
	 *
	 * @return 会員番号
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 会員番号 を設定する。<br/>
	 * <br/>
	 *
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 氏名 を取得する。<br/>
	 * <br/>
	 *
	 * @return 氏名
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * 氏名 を設定する。<br/>
	 * <br/>
	 *
	 * @param memberName
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * 電話番号 を取得する。<br/>
	 * <br/>
	 *
	 * @return 電話番号
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 電話番号 を設定する。<br/>
	 * <br/>
	 *
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * メールアドレス を取得する。<br/>
	 * <br/>
	 *
	 * @return メールアドレス
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * メールアドレス を設定する。<br/>
	 * <br/>
	 *
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 備考 を取得する。<br/>
	 * <br/>
	 *
	 * @return 備考
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 備考 を設定する。<br/>
	 * <br/>
	 *
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 備考（表示用） を取得する。<br/>
	 * <br/>
	 *
	 * @return 備考
	 */
	public String getShowNote() {
		return showNote;
	}

	/**
	 * 備考（表示用） を設定する。<br/>
	 * <br/>
	 *
	 * @param note
	 */
	public void setShowNote(String showNote) {
		this.showNote = showNote;
	}

	/**
	 * システムリフォームCD を取得する。<br/>
	 * <br/>
	 *
	 * @return システムリフォームCD
	 */
	public String getSysReformCd() {
		return sysReformCd;
	}

	/**
	 * システムリフォームCD を設定する。<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 */
	public void setSysReformCd(String sysReformCd) {
		this.sysReformCd = sysReformCd;
	}

	/**
	 * イベントフラグ を取得する。<br/>
	 * <br/>
	 *
	 * @return イベントフラグ
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * イベントフラグ を設定する。<br/>
	 * <br/>
	 *
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * システム建物番号 を取得する。<br/>
	 * <br/>
	 *
	 * @return システム建物番号
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * システム建物番号 を設定する。<br/>
	 * <br/>
	 *
	 * @param sysBuildingCd
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * システム物件CD を取得する。<br/>
	 * <br/>
	 *
	 * @return システム物件CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * システム物件CD を設定する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * 物件名称 を取得する。<br/>
	 * <br/>
	 *
	 * @return 物件名称
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * 物件名称 を設定する。<br/>
	 * <br/>
	 *
	 * @param displayHousingName
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * 物件番号 を取得する。<br/>
	 * <br/>
	 *
	 * @return 物件番号
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * 物件番号 を設定する。<br/>
	 * <br/>
	 *
	 * @param housingCd
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	public String getReadonlyFlg() {
		return readonlyFlg;
	}

	public void setReadonlyFlg(String readonlyFlg) {
		this.readonlyFlg = readonlyFlg;
	}

	public String getHousingKindCd() {
		return housingKindCd;
	}

	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
	 * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意 する事。<br/>
	 * <br/>
	 *
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 * @param isAdmin
	 *            ログインユーザ権限がadminか判定結果
	 * @return 正常時 true、エラー時 false
	 */
	public boolean validateUpd(List<ValidationFailure> errors, Boolean isAdmin, HousingStatusInfo housingStatusInfo) {
		int startSize = errors.size();


		// 公開区分入力チェック
		ValidationChain hiddenFlg = new ValidationChain(
				"housingBrowse.input.hiddenFlg", getHiddenFlg());
		// 必須チェック
		hiddenFlg.addValidation(new NullOrEmptyCheckValidation());
		// パターンチェック
		hiddenFlg.addValidation(new CodeLookupValidation(
				this.codeLookupManager, "hiddenFlg"));
		hiddenFlg.validate(errors);

		// ステータス
		ValidationChain statusCd = new ValidationChain(
				"housingBrowse.input.statusCd", getStatusCd());
		// 必須チェック
		statusCd.addValidation(new NullOrEmptyCheckValidation());
		// パターンチェック
		statusCd.addValidation(new CodeLookupValidation(this.codeLookupManager,
				"statusCd"));
		statusCd.validate(errors);

		// 備考
		ValidationChain note = new ValidationChain("housingBrowse.input.note",
				getNote());
		// 桁数チェック
		note.addValidation(new MinLengthValidation(0));
		note.addValidation(new MaxLengthValidation(200));
		note.validate(errors);

		// 権限チェック
		if (housingStatusInfo.getHiddenFlg() != null) {
			if (!isAdmin
					&& !housingStatusInfo.getHiddenFlg().equals(getHiddenFlg())) {
				ValidationFailure vf = new ValidationFailure("role", "公開区分",
						null, null);
				errors.add(vf);
			}
		}

		return (startSize == errors.size());
	}

	// バリーオブジェクトを設定
	public void copyToHousingStatusInfo(HousingStatusInfo housingstatusinfo) {
		// システム物件CD
		housingstatusinfo.setSysHousingCd(setString(getSysHousingCd(),
				housingstatusinfo.getSysHousingCd()));
		// 非公開フラグ
		housingstatusinfo.setHiddenFlg(setString(getHiddenFlg(),
				housingstatusinfo.getHiddenFlg()));
		// ステータスCD
		housingstatusinfo.setStatusCd(setString(getStatusCd(),
				housingstatusinfo.getStatusCd()));
		// ユーザーID
		housingstatusinfo.setUserId(setString(getUserId(),
				housingstatusinfo.getUserId()));
		if ("".equals(getUserId())) {
			housingstatusinfo.setUserId(getUserId());
		}
		// 備考
		housingstatusinfo.setNote(getNote());
	}

	// String型の値設定
	private String setString(String input, String nullValue) {
		if (input == null || "".equals(input)) {
			return nullValue;
		}

		return input;
	}

	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
	 * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意 する事。<br/>
	 * <br/>
	 *
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 * @param isAdmin
	 *            ログインユーザ権限がadminか判定結果
	 * @return 正常時 true、エラー時 false
	 */
	public boolean validateIns(List<ValidationFailure> errors, Boolean isAdmin) {
		int startSize = errors.size();

		// 物件名称入力チェック
		ValidationChain displayHousingName = new ValidationChain(
				"housingStatus.input.displayHousingName", getDisplayHousingName());
		// 必須チェック
		displayHousingName.addValidation(new NullOrEmptyCheckValidation());
		// 桁数チェック
		displayHousingName.addValidation(new MinLengthValidation(0));
		displayHousingName.addValidation(new MaxLengthValidation(25));
		displayHousingName.validate(errors);

		// 物件種別入力チェック
		ValidationChain housingKindCd = new ValidationChain(
				"housingStatus.input.housingKindCd", getHousingKindCd());
		// 必須チェック
		housingKindCd.addValidation(new NullOrEmptyCheckValidation());
		// パターンチェック
		housingKindCd.addValidation(new CodeLookupValidation(
				this.codeLookupManager, "buildingInfo_housingKindCd"));
		housingKindCd.validate(errors);

		// 公開区分入力チェック
		ValidationChain hiddenFlg = new ValidationChain(
				"housingStatus.input.hiddenFlg", getHiddenFlg());
		// 必須チェック
		hiddenFlg.addValidation(new NullOrEmptyCheckValidation());
		// パターンチェック
		hiddenFlg.addValidation(new CodeLookupValidation(
				this.codeLookupManager, "hiddenFlg"));
		hiddenFlg.validate(errors);

		// ステータス
		ValidationChain statusCd = new ValidationChain(
				"housingStatus.input.statusCd", getStatusCd());
		// 必須チェック
		statusCd.addValidation(new NullOrEmptyCheckValidation());
		// パターンチェック
		statusCd.addValidation(new CodeLookupValidation(this.codeLookupManager,
				"statusCd"));
		statusCd.validate(errors);

		// 備考
		ValidationChain note = new ValidationChain("housingStatus.input.note",
				getNote());
		// 桁数チェック
		note.addValidation(new MinLengthValidation(0));
		note.addValidation(new MaxLengthValidation(200));
		note.validate(errors);

		// 権限チェック
		if (!isAdmin && PanaCommonConstant.HIDDEN_FLG_PUBLIC.equals(getHiddenFlg())) {
			ValidationFailure vf = new ValidationFailure("role", "公開区分", null,
					null);
			errors.add(vf);
		}

		return (startSize == errors.size());
	}

	/**
	 * 物件ステータス（新規）画面格納<br/>
	 * <br/>
	 *
	 * @param userRole
	 *            入力値が格納された ユーザー情報 オブジェクト
	 */
	public void setDefaultData(UserRoleSet userRole) {
		// 管理権限ユーザの場合
		if (userRole.hasRole("admin")) {
			this.readonlyFlg = "0";
		} else {
			// 管理権限ユーザ以外の場合
			// Readonlyで表示する
			this.readonlyFlg = "1";
		}

		if (StringValidateUtil.isEmpty(this.getCommand())) {
			// 公開区分
			this.setHiddenFlg(PanaCommonConstant.HIDDEN_FLG_PRIVATE);
			// 物件種別
			this.setHousingKindCd(PanaCommonConstant.HOUSING_KIND_CD_MANSION);
		}

		// command
		this.setCommand("insert");
	}

	/**
	 * ステータス編集の格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された Housing オブジェクト
	 * @param userRole
	 *            入力値が格納された ユーザー情報 オブジェクト
	 * @param memberInfo
	 *            入力値が格納された マイページ会員 オブジェクト
	 */
	public void setDefaultData(PanaHousing housing, UserRoleSet userRole,
			MemberInfo memberInfo) {

		// 物件ステータス情報の取得
		JoinResult housingResult = housing.getHousingInfo();
		HousingStatusInfo housingStatusInfo = (HousingStatusInfo) housingResult
				.getItems().get("housingStatusInfo");

		// 公開区分
		this.setHiddenFlg(housingStatusInfo.getHiddenFlg());

		// ステータス
		this.setStatusCd(housingStatusInfo.getStatusCd());
		// 会員番号
		this.setUserId(housingStatusInfo.getUserId());

		// 管理権限ユーザの場合
		if (userRole.hasRole("admin")) {
			this.readonlyFlg = "0";
		} else {
			// 管理権限ユーザ以外の場合
			// Readonlyで表示する
			this.readonlyFlg = "1";
		}
		// 氏名
		StringBuffer sbr = new StringBuffer();
		if (!StringValidateUtil.isEmpty(memberInfo.getMemberLname())) {
			sbr.append(memberInfo.getMemberLname());
		}
		if (!StringValidateUtil.isEmpty(memberInfo.getMemberFname())) {
			sbr.append(memberInfo.getMemberFname());
		}
		this.setMemberName(sbr.toString());

		// 電話番号
		this.setTel(memberInfo.getTel());
		// メールアドレス
		this.setEmail(memberInfo.getEmail());
		// 備考（管理者用）
		this.setNote(housingStatusInfo.getNote());
	}
}
