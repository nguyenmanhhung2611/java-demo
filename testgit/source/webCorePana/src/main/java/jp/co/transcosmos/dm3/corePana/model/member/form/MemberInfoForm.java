package jp.co.transcosmos.dm3.corePana.model.member.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.corePana.model.mypage.PanaMypageUserInterface;
import jp.co.transcosmos.dm3.corePana.util.PanaCommonUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.validation.SameValueValidation;
import jp.co.transcosmos.dm3.corePana.vo.MemberQuestion;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.LengthValidator;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * マイページ会員情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者         修正日     修正内容
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun   2015.04.17  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class MemberInfoForm extends MypageUserForm {

	/** 住所・郵便番号 */
	private String zip;
	/** 住所・都道府県CD */
	private String prefCd;
	/** 住所・都道府県 */
	private String prefName;
	/** 住所・市区町村名 */
	private String address;
	/** 住所・町名番地その他 */
	private String addressOther;
	/** 電話番号 */
	private String tel;
	/** FAX */
	private String fax;
	/** メール配信希望 */
	private String mailSendFlg;
	/** 居住状態 */
	private String residentFlg;
	/** 希望地域・都道府県CD */
	private String hopePrefCd;
	/** 希望地域・都道府県 */
	private String hopePrefName;
	/** 希望地域・市区町村 */
	private String hopeAddress;
	/** 登録経路 */
	private String entryRoute;
	/** プロモCD */
	private String promoCd;
	/** 流入元CD */
	private String refCd;
	/** ロックフラグ */
	private String lockFlg;
	/** 最終ログイン日 */
	private String lastLogin;
	/** 登録日 */
	private String insDate;
	/** 最終更新日 */
	private String updDate;
	/** アンケート */
	private String[] questionId;
	/** アンケート内容1 */
	private String etcAnswer1;
	/** アンケート内容2 */
	private String etcAnswer2;
	/** アンケート内容3 */
	private String etcAnswer3;
	/** 機能フラグ */
	private String projectFlg;

	private String redirectKey;

	/**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     */
    MemberInfoForm() {
        super();
    }

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	MemberInfoForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager, CommonParameters commonParameters) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
		this.commonParameters = commonParameters;
	}


	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 * @param mypageUser MypageUserInterface　を実装した、マイページ会員情報用バリーオブジェクト
	 */
	@Override
	public void setDefaultData(MypageUserInterface mypageUser){
		super.setDefaultData(mypageUser);
		PanaMypageUserInterface memberInfo = (PanaMypageUserInterface)mypageUser;
		//日時フォーマット
		DateFormat format =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (memberInfo != null) {
			PanaCommonUtil.copyProperties(this, memberInfo);
			if (!StringValidateUtil.isEmpty(this.getLastLogin())){
				this.setLastLogin(format.format(memberInfo.getLastLogin()));
			}
			if (memberInfo.getInsDate() != null) {
				this.setInsDate(format.format(memberInfo.getInsDate()));
			}
			if (memberInfo.getUpdDate() != null) {
				this.setUpdDate(format.format(memberInfo.getUpdDate()));
			}
		}

	}

	/**
	 * マイページアンケート情報から Form へ初期値を設定する。<br/>
	 * <br/>
	 * @param memberQuestionList マイページアンケート情報
	 */
	public void setMemberQuestion(List<MemberQuestion> memberQuestionList){
		if (memberQuestionList != null && memberQuestionList.size() > 0) {
			int len = memberQuestionList.size();
			// アンケート番号、選択質問CD
			String[] questionId = new String[len];
			for (int i = 0; i < len; i++) {
				questionId[i] = memberQuestionList.get(i).getQuestionId();
				if ("008".equals(memberQuestionList.get(i).getQuestionId())) {
					this.setEtcAnswer1(memberQuestionList.get(i).getEtcAnswer());
				}
				if ("009".equals(memberQuestionList.get(i).getQuestionId())) {
					this.setEtcAnswer2(memberQuestionList.get(i).getEtcAnswer());
				}
				if ("010".equals(memberQuestionList.get(i).getQuestionId())) {
					this.setEtcAnswer3(memberQuestionList.get(i).getEtcAnswer());
				}
			}
			this.setQuestionId(questionId);
		}
	}
	/**
	 * 引数で渡されたマイページ会員のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @return MypageUserInterface を実装したマイページ会員オブジェクト
	 */
	@Override
	public void copyToMemberInfo(MypageUserInterface mypageUser){
		super.copyToMemberInfo(mypageUser);
		jp.co.transcosmos.dm3.corePana.model.mypage.PanaMypageUserInterface memberInfo =
				(jp.co.transcosmos.dm3.corePana.model.mypage.PanaMypageUserInterface) mypageUser;

		// 住所・郵便番号を設定
		memberInfo.setZip(this.getZip());
		// 住所・都道府県CDを設定
		memberInfo.setPrefCd(this.getPrefCd());
		// 住所・市区町村名を設定
		memberInfo.setAddress(this.getAddress());
		// 住所・市区町村名を設定
		memberInfo.setAddressOther(this.getAddressOther());
		// 電話番号
		memberInfo.setTel(this.getTel());
		// FAX
		memberInfo.setFax(this.getFax());
		// メール配信希望
		memberInfo.setMailSendFlg(this.getMailSendFlg());
		// 居住状態
		memberInfo.setResidentFlg(this.getResidentFlg());
		// 希望地域・都道府県
		memberInfo.setHopePrefCd(this.getHopePrefCd());
		// 希望地域・市区町村
		memberInfo.setHopeAddress(this.getHopeAddress());
        // 管理サイトから登録した場合
        if (!"front".equals(this.projectFlg)) {
    		// 登録経路
    		memberInfo.setEntryRoute(this.getEntryRoute());
    		// ロックフラグ
    		memberInfo.setLockFlg(this.getLockFlg());
        }
		// プロモCD
		memberInfo.setPromoCd(this.getPromoCd());
		// 流入元CD
		memberInfo.setRefCd(this.getRefCd());

	}

	/**
	 * 住所・郵便番号を取得する。<br/>
	 * <br/>
	 * @return 住所・郵便番号
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * 住所・郵便番号を設定する。<br/>
	 * <br/>
	 * @param zip 住所・郵便番号
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * 住所・都道府県CDを取得する。<br/>
	 * <br/>
	 * @return 住所・都道府県CD
	 */
	public String getPrefCd() {
		return prefCd;
	}

	/**
	 * 住所・都道府県CDを設定する。<br/>
	 * <br/>
	 * @param prefCd 住所・都道府県CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * 住所・市区町村名を取得する。<br/>
	 * <br/>
	 * @return 住所・市区町村名
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 住所・市区町村名を設定する。<br/>
	 * <br/>
	 * @param address 住所・市区町村名
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 住所・町名番地その他を取得する。<br/>
	 * <br/>
	 * @return 住所・町名番地その他
	 */
	public String getAddressOther() {
		return addressOther;
	}

	/**
	 * 住所・町名番地その他を設定する。<br/>
	 * <br/>
	 * @param addressOther 住所・町名番地その他
	 */
	public void setAddressOther(String addressOther) {
		this.addressOther = addressOther;
	}

	/**
	 * 電話番号を取得する。<br/>
	 * <br/>
	 * @return 電話番号
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 電話番号を設定する。<br/>
	 * <br/>
	 * @param tel 電話番号
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * FAXを取得する。<br/>
	 * <br/>
	 * @return FAX
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * FAXを設定する。<br/>
	 * <br/>
	 * @param fax FAX
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * メール配信希望を取得する。<br/>
	 * <br/>
	 * @return メール配信希望
	 */
	public String getMailSendFlg() {
		return mailSendFlg;
	}

	/**
	 * メール配信希望を設定する。<br/>
	 * <br/>
	 * @param mailSendFlg メール配信希望
	 */
	public void setMailSendFlg(String mailSendFlg) {
		this.mailSendFlg = mailSendFlg;
	}

	/**
	 * 居住状態を取得する。<br/>
	 * <br/>
	 * @return 居住状態
	 */
	public String getResidentFlg() {
		return residentFlg;
	}

	/**
	 * 居住状態を設定する。<br/>
	 * <br/>
	 * @param residentFlg 居住状態
	 */
	public void setResidentFlg(String residentFlg) {
		this.residentFlg = residentFlg;
	}

	/**
	 * 希望地域・都道府県CDを取得する。<br/>
	 * <br/>
	 * @return 希望地域・都道府県CD
	 */
	public String getHopePrefCd() {
		return hopePrefCd;
	}

	/**
	 * 希望地域・都道府県CDを設定する。<br/>
	 * <br/>
	 * @param hopePrefCd 希望地域・都道府県CD
	 */
	public void setHopePrefCd(String hopePrefCd) {
		this.hopePrefCd = hopePrefCd;
	}

	/**
	 * 希望地域・市区町村を取得する。<br/>
	 * <br/>
	 * @return 希望地域・市区町村
	 */
	public String getHopeAddress() {
		return hopeAddress;
	}

	/**
	 * 希望地域・市区町村を設定する。<br/>
	 * <br/>
	 * @param hopeAddress 希望地域・市区町村
	 */
	public void setHopeAddress(String hopeAddress) {
		this.hopeAddress = hopeAddress;
	}

	/**
	 * 登録経路を取得する。<br/>
	 * <br/>
	 * @return 登録経路
	 */
	public String getEntryRoute() {
		return entryRoute;
	}

	/**
	 * 登録経路を設定する。<br/>
	 * <br/>
	 * @param entryRoute 登録経路
	 */
	public void setEntryRoute(String entryRoute) {
		this.entryRoute = entryRoute;
	}

	/**
	 * プロモCDを取得する。<br/>
	 * <br/>
	 * @return プロモCD
	 */
	public String getPromoCd() {
		return promoCd;
	}

	/**
	 * プロモCDを設定する。<br/>
	 * <br/>
	 * @param promoCd プロモCD
	 */
	public void setPromoCd(String promoCd) {
		this.promoCd = promoCd;
	}

	/**
	 * ロックフラグを取得する。<br/>
	 * <br/>
	 * @return ロックフラグ
	 */
	public String getLockFlg() {
		return lockFlg;
	}

	/**
	 * ロックフラグを設定する。<br/>
	 * <br/>
	 * @param lockFlg ロックフラグ
	 */
	public void setLockFlg(String lockFlg) {
		this.lockFlg = lockFlg;
	}

	/**
	 * 最終ログイン日を取得する。<br/>
	 * <br/>
	 * @return 最終ログイン日
	 */
	public String getLastLogin() {
		return lastLogin;
	}

	/**
	 * 最終ログイン日を設定する。<br/>
	 * <br/>
	 * @param lastLogin 最終ログイン日
	 */
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * 登録日を取得する。<br/>
	 * <br/>
	 * @return 登録日
	 */
	public String getInsDate() {
		return insDate;
	}

	/**
	 * 登録日を設定する。<br/>
	 * <br/>
	 * @param insDate 登録日
	 */
	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}

	/**
	 * 最終更新日を取得する。<br/>
	 * <br/>
	 * @return 最終更新日
	 */
	public String getUpdDate() {
		return updDate;
	}

	/**
	 * 最終更新日を設定する。<br/>
	 * <br/>
	 * @param updDate 最終更新日
	 */
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	/**
	 * アンケートを取得する。<br/>
	 * <br/>
	 * @return アンケート
	 */
	public String[] getQuestionId() {
		return questionId;
	}

	/**
	 * アンケートを設定する。<br/>
	 * <br/>
	 * @param questionId アンケート
	 */
	public void setQuestionId(String[] questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return etcAnswer1
	 */
	public String getEtcAnswer1() {
		return etcAnswer1;
	}

	/**
	 * @param etcAnswer1 セットする etcAnswer1
	 */
	public void setEtcAnswer1(String etcAnswer1) {
		this.etcAnswer1 = etcAnswer1;
	}

	/**
	 * @return etcAnswer2
	 */
	public String getEtcAnswer2() {
		return etcAnswer2;
	}

	/**
	 * @param etcAnswer2 セットする etcAnswer2
	 */
	public void setEtcAnswer2(String etcAnswer2) {
		this.etcAnswer2 = etcAnswer2;
	}

	/**
	 * @return etcAnswer3
	 */
	public String getEtcAnswer3() {
		return etcAnswer3;
	}

	/**
	 * @param etcAnswer3 セットする etcAnswer3
	 */
	public void setEtcAnswer3(String etcAnswer3) {
		this.etcAnswer3 = etcAnswer3;
	}

	/**
	 * 住所・都道府県を取得する。<br/>
	 * <br/>
	 * @return 住所・都道府県
	 */
	public String getPrefName() {
		return prefName;
	}

	/**
	 * 住所・都道府県を設定する。<br/>
	 * <br/>
	 * @param prefName 住所・都道府県
	 */
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	/**
	 * 希望地域・都道府県を取得する。<br/>
	 * <br/>
	 * @return 希望地域・都道府県
	 */
	public String getHopePrefName() {
		return hopePrefName;
	}

	/**
	 * 希望地域・都道府県を設定する。<br/>
	 * <br/>
	 * @param hopePrefName 希望地域・都道府県
	 */
	public void setHopePrefName(String hopePrefName) {
		this.hopePrefName = hopePrefName;
	}

	/**
	 * 流入元CDを取得する。<br/>
	 * <br/>
	 * @return 流入元CD
	 */
	public String getRefCd() {
		return refCd;
	}

	/**
	 * 流入元CDを設定する。<br/>
	 * <br/>
	 * @param refCd 流入元CD
	 */
	public void setRefCd(String refCd) {
		this.refCd = refCd;
	}

	/**
	 * 機能フラグを取得する。<br/>
	 * <br/>
	 * @return 機能フラグ
	 */
	public String getProjectFlg() {
		return projectFlg;
	}

	/**
	 * 機能フラグを設定する。<br/>
	 * <br/>
	 * @param projectFlg 機能フラグ
	 */
	public void setProjectFlg(String projectFlg) {
		this.projectFlg = projectFlg;
	}

	public String getRedirectKey() {
		return redirectKey;
	}

	public void setRedirectKey(String redirectKey) {
		this.redirectKey = redirectKey;
	}

	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
	 * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意
	 * する事。<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors, String mode) {
        int startSize = errors.size();

        // 住所検索処理のチェック
        if ("getAddress".equals(mode)){
            // 住所・郵便番号入力チェック
            // 必須チェック
			if(StringValidateUtil.isEmpty(this.getZip())){
				ValidationFailure vf = new ValidationFailure(
                        "housingInfoZipNotInput", "", "", null);
                errors.add(vf);
			} else {
	            ValidationChain zip = new ValidationChain("mypage.input.zip", this.getZip());
	            // 桁数チェック
	            zip.addValidation(new LengthValidator(7));
	            // 半角数字チェック
	            zip.addValidation(new NumericValidation());
	            zip.validate(errors);
			}
    		return (startSize == errors.size());
        }

        super.validate(errors, mode);

        // フロント側の入力チェック
        if ("front".equals(this.getProjectFlg())) {
        	// Form 値の自動変換をする
        	setAutoChange();

            // 住所検索処理のチェック
            if ("getAddress".equals(mode)){
                // 郵便番号入力チェック
                ValidationChain zip = new ValidationChain("member.input.zip", this.getZip());
                // 必須チェック
                zip.addValidation(new NullOrEmptyCheckValidation());
                // 桁数チェック
                zip.addValidation(new LengthValidator(7));
                // 半角数字チェック
                zip.addValidation(new NumericValidation());
                zip.validate(errors);
        		return (startSize == errors.size());
            }

        	return (startSize == frontValidate(errors, mode));
        }

        // 住所・郵便番号入力チェック
        ValidationChain zip = new ValidationChain("mypage.input.zip", this.getZip());
        // 必須チェック
        zip.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        zip.addValidation(new LengthValidator(7));
        // 半角数字チェック
        zip.addValidation(new NumericValidation());
        zip.validate(errors);

        // 住所(都道府県)入力チェック
        ValidationChain prefCd = new ValidationChain("mypage.input.prefCd", this.getPrefCd());
        // 必須チェック
        prefCd.addValidation(new NullOrEmptyCheckValidation());
        prefCd.validate(errors);

        // 住所(市区町村・番地)入力チェック
        ValidationChain address = new ValidationChain("mypage.input.address", this.getAddress());
        // 必須チェック
        address.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        address.addValidation(new MaxLengthValidation(50));
        address.validate(errors);

        // 住所(建物名)入力チェック
        ValidationChain addressOther = new ValidationChain("mypage.input.addressOther", this.getAddressOther());
        // 桁数チェック
        addressOther.addValidation(new MaxLengthValidation(30));
        addressOther.validate(errors);

        // 居住形態入力チェック
        ValidationChain residentFlg = new ValidationChain("mypage.input.residentFlg", this.getResidentFlg());
        // パターンチェック
        residentFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "residentFlg"));
        residentFlg.validate(errors);

        // 物件希望地域(都道府県)入力チェック
        ValidationChain hopePrefCd = new ValidationChain("mypage.input.hopePrefCd", this.getHopePrefCd());
        // 必須チェック
        hopePrefCd.addValidation(new NullOrEmptyCheckValidation());
        hopePrefCd.validate(errors);

        // 物件希望地域(市区町村)入力チェック
        ValidationChain hopeAddress = new ValidationChain("mypage.input.hopeAddress", this.getHopeAddress());
        // 桁数チェック
        hopeAddress.addValidation(new MaxLengthValidation(50));
        hopeAddress.validate(errors);

        // 電話番号入力チェック
        ValidationChain tel = new ValidationChain("mypage.input.tel", this.getTel());
        // 必須チェック
        tel.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        tel.addValidation(new MaxLengthValidation(11));
        // 半角数字チェック
        tel.addValidation(new NumericValidation());
        tel.validate(errors);

        // FAX番号入力チェック
        ValidationChain fax = new ValidationChain("mypage.input.fax", this.getFax());
        // 桁数チェック
        fax.addValidation(new MaxLengthValidation(11));
        // 半角数字チェック
        fax.addValidation(new NumericValidation());
        fax.validate(errors);

        // メール配信入力チェック
        ValidationChain mailSendFlg = new ValidationChain("mypage.input.mailSendFlg", this.getMailSendFlg());
        // 必須チェック
        mailSendFlg.addValidation(new NullOrEmptyCheckValidation());
        // パターンチェック
        mailSendFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "mailSendFlg"));
        mailSendFlg.validate(errors);

        // アンケート入力チェック
		if (this.getQuestionId() != null) {
			for (String questionId : this.getQuestionId()) {
				ValidationChain valid = new ValidationChain("mypage.input.questionId", questionId);
				// パターン入力チェック
				valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "ans_all"));
				valid.validate(errors);
			}
		}

        // アンケート内容入力チェック
        if (this.getQuestionId() != null) {
        	for (String questionId : this.getQuestionId()) {
        		if ("008".equals(questionId)) {
        	        // アンケート内容1入力チェック
        	        ValidationChain etcAnswer1 = new ValidationChain("mypage.input.etcAnswer1", this.getEtcAnswer1());
        	        // 桁数チェック
        	        etcAnswer1.addValidation(new MaxLengthValidation(50));
        	        etcAnswer1.validate(errors);
        		}
        		if ("009".equals(questionId)) {
        	        // アンケート内容2入力チェック
        	        ValidationChain etcAnswer2 = new ValidationChain("mypage.input.etcAnswer2", this.getEtcAnswer2());
        	        // 桁数チェック
        	        etcAnswer2.addValidation(new MaxLengthValidation(50));
        	        etcAnswer2.validate(errors);
        		}
        		if ("010".equals(questionId)) {
        	        // アンケート内容3入力チェック
        	        ValidationChain etcAnswer3 = new ValidationChain("mypage.input.etcAnswer3", this.getEtcAnswer3());
        	        // 桁数チェック
        	        etcAnswer3.addValidation(new MaxLengthValidation(50));
        	        etcAnswer3.validate(errors);
        		}
        	}
        }

        // アンケートチェックボックスとアンケート内容の制御チェック
        List<String> list = new ArrayList<String>();
        if (this.getQuestionId() != null) {
        	list =  Arrays.asList(this.getQuestionId());
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer1()) && !list.contains("008")) {
        	errors.add(new ValidationFailure("mustInput1", "アンケート内容1",  "パナソニックショップ", null));
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer2()) && !list.contains("009")) {
        	errors.add(new ValidationFailure("mustInput1", "アンケート内容2",  "その他", null));
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer3()) && !list.contains("010")) {
        	errors.add(new ValidationFailure("mustInput1", "アンケート内容3",  "プロモコード", null));
        }

        // 登録経路入力チェック
        ValidationChain entryRoute = new ValidationChain("mypage.input.entryRoute", this.getEntryRoute());
        // 必須チェック
        entryRoute.addValidation(new NullOrEmptyCheckValidation());
        // パターンチェック
        entryRoute.addValidation(new CodeLookupValidation(this.codeLookupManager, "entryRoute"));
        entryRoute.validate(errors);


        // 有効区分入力チェック
        ValidationChain lockFlg = new ValidationChain("mypage.input.lockFlg", this.getLockFlg());
        // 必須チェック
        lockFlg.addValidation(new NullOrEmptyCheckValidation());
        // パターンチェック
        lockFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "lockFlg"));
        lockFlg.validate(errors);

		return (startSize == errors.size());
	}

	/**
	 * フロント側バリデーションを実行。<br/>
	 * <br/>
	 * @return errorsSize チェックエラー個数
	 */
	private int frontValidate (List<ValidationFailure> errors, String mode) {

        // 郵便番号入力チェック
        ValidationChain zip = new ValidationChain("member.input.zip", this.getZip());
        // 必須チェック
        zip.addValidation(new NullOrEmptyCheckValidation());
		// 桁数チェック
        zip.addValidation(new LengthValidator(this.lengthUtils.getLength("member.input.zip", 7)));
        // 半角数字チェック
        zip.addValidation(new NumericValidation());
        zip.validate(errors);

        // 都道府県名_お客様情報入力チェック
        ValidationChain prefCd = new ValidationChain("member.input.prefCd", this.getPrefCd());
        // 必須チェック
        prefCd.addValidation(new NullOrEmptyCheckValidation());
        prefCd.validate(errors);

        // 市区町村番地入力チェック
        ValidationChain address = new ValidationChain("member.input.address", this.getAddress());
        // 必須チェック
        address.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        address.addValidation(new MaxLengthValidation(50));
        address.validate(errors);

        // 建物名入力チェック
        ValidationChain addressOther = new ValidationChain("member.input.addressOther", this.getAddressOther());
        // 桁数チェック
        addressOther.addValidation(new MaxLengthValidation(30));
        addressOther.validate(errors);

        // 電話番号入力チェック
        ValidationChain tel = new ValidationChain("member.input.tel", this.getTel());
        // 必須チェック
        tel.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        tel.addValidation(new MaxLengthValidation(11));
        // 半角数字チェック
        tel.addValidation(new NumericValidation());
        tel.validate(errors);

        // 居住形態入力チェック
        ValidationChain residentFlg = new ValidationChain("member.input.residentFlg", this.getResidentFlg());
        // パターンチェック
        residentFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "residentFlg"));
        residentFlg.validate(errors);

        // 都道府県名_物件希望地域
        ValidationChain hopePrefCd = new ValidationChain("member.input.hopePrefCd", this.getHopePrefCd());
        // 必須チェック
        hopePrefCd.addValidation(new NullOrEmptyCheckValidation());
        hopePrefCd.validate(errors);

        // 物件希望地域(市区町村)入力チェック
        ValidationChain hopeAddress = new ValidationChain("member.input.hopeAddress", this.getHopeAddress());
        // 桁数チェック
        hopeAddress.addValidation(new MaxLengthValidation(50));
        hopeAddress.validate(errors);

        // メール配信入力チェック
        ValidationChain mailSendFlg = new ValidationChain("member.input.mailSendFlg", this.getMailSendFlg());
        // 必須チェック
        mailSendFlg.addValidation(new NullOrEmptyCheckValidation());
        // パターンチェック
        mailSendFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "mailSendFlg"));
        mailSendFlg.validate(errors);

        if (this.questionId != null) {

        	for (int i = 0; i < this.questionId.length; i++) {
                // メール配信入力チェック
                ValidationChain questionId = new ValidationChain("member.input.ansCd", this.questionId[i]);
                // パターンチェック
            	questionId.addValidation(new CodeLookupValidation(this.codeLookupManager, "ans_all"));
            	questionId.validate(errors);
        	}


            // アンケート1内容1入力チェック
            validetcAnswer(errors, "008", "member.input.etcAnswer1", this.etcAnswer1);

            // アンケート1内容2入力チェック
            validetcAnswer(errors, "009", "member.input.etcAnswer2", this.etcAnswer2);

            // アンケート1内容3入力チェック
            validetcAnswer(errors, "010", "member.input.etcAnswer3", this.etcAnswer3);

        }

        // アンケートチェックボックスとアンケート内容の制御チェック
        List<String> list = new ArrayList<String>();
        if (this.getQuestionId() != null) {
        	list =  Arrays.asList(this.getQuestionId());
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer1()) && !list.contains("008")) {
        	errors.add(new ValidationFailure("mustInput1", "アンケート内容1",  "パナソニックショップ", null));
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer2()) && !list.contains("009")) {
        	errors.add(new ValidationFailure("mustInput1", "アンケート内容2",  "その他", null));
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer3()) && !list.contains("010")) {
        	errors.add(new ValidationFailure("mustInput1", "アンケート内容3",  "プロモコード", null));
        }

        return errors.size();
	}

	/**
	 * アンケート内容 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param chkbox アンケート選択チェックボックス
	 * @param label 画面項目のラベル
	 * @param answer 画面項目の内容
	 */
	private void validetcAnswer(List<ValidationFailure> errors, String chkbox, String label, String answer) {

		boolean isChk = false;

		for (int i = 0; i < this.questionId.length; i++) {
			if (chkbox.equals(this.questionId[i])) {
				isChk = true;
			}
		}

		if (isChk) {
			ValidationChain valid = new ValidationChain(label,answer);
	        // 桁数チェック
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("member.input.etcAnswer", 50)));

			valid.validate(errors);
		}
	}


	/**
	 * パスワード バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>最小桁数チェック</li>
	 * <li>最大桁数チェック</li>
	 * <li>新パスワードの半角英数記号文字チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @param mode 処理モード ("insert" or "update")
	 */
	@Override
	protected void validPassword(List<ValidationFailure> errors, String mode) {
		String label = "mypage.input.password";
        ValidationChain valPassword = new ValidationChain(label, this.getPassword());

        if (mode.equals("insert")) {
        	// 新規登録時は必須チェックを行う。
        	// 更新登録時のパスワード入力は任意となり、入力された場合のみパスワードを更新する。
        	valPassword.addValidation(new NullOrEmptyCheckValidation());						// 必須チェック
        }

        // パスワードの入力があった場合、パスワードのバリデーションを行う
        if (!StringValidateUtil.isEmpty(this.getPassword())){

        	// 最小桁数チェック
        	valPassword.addValidation(new MinLengthValidation(this.commonParameters.getMinMypagePwdLength()));

        	// 最大桁数チェック
        	valPassword.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));

        	// パスワード強度チェックのバリデーションオブジェクトが取得できた場合、パスワード強度のバリデーションを実行する。
            Validation pwdValidation = createPwdValidation();
        	if (pwdValidation != null){
        		valPassword.addValidation(pwdValidation);
        	}
        }
        valPassword.validate(errors);
	}

	/**
	 * パスワード確認 バリデーション<br/>
	 * パスワードの入力があった場合のみ、以下のバリデーションを実施する<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>パスワードと、確認入力の照合チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	@Override
	protected void validRePassword(List<ValidationFailure> errors) {

		// パスワードの入力があった場合、パスワード確認のバリデーションを行う
        if (!StringValidateUtil.isEmpty(this.getPassword())){

        	String label = "mypage.input.rePassword";
            ValidationChain valNewRePwd = new ValidationChain(label, this.getPasswordChk());

            // 必須チェック
            valNewRePwd.addValidation(new NullOrEmptyCheckValidation());

            valNewRePwd.validate(errors);

            ValidationChain valPwd = new ValidationChain("mypage.input.password", this.getPassword());

            // パスワードと、確認入力の照合チェック
            String cmplabel = this.codeLookupManager.lookupValue("errorLabels", "mypage.input.rePassword");
            valPwd.addValidation(new SameValueValidation(cmplabel, this.getPasswordChk()));

            valPwd.validate(errors);
        }
	}

	/**
	 * Form 値の自動変換をする。<br/>
	 * <br/>
	 */
	private void setAutoChange() {
		// 全角数字から半角数字への自動変換
		this.setZip(PanaStringUtils.changeToHankakuNumber(this.zip));
		this.setEmail(PanaStringUtils.changeToHankakuNumber(this.getEmail()));
		this.setTel(PanaStringUtils.changeToHankakuNumber(this.tel));
		this.setPrefCd(PanaStringUtils.changeToHankakuNumber(this.prefCd));
		this.setHopePrefCd(PanaStringUtils.changeToHankakuNumber(this.hopePrefCd));
	}
}
