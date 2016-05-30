package jp.co.transcosmos.dm3.core.model.housing.form;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ArrayMemberValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.DecimalValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * 物件詳細情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 */
public class HousingDtlForm implements Validateable {

	private static final Log log = LogFactory.getLog(HousingDtlForm.class);
	
	/** システム物件CD */
	private String sysHousingCd;
	/** 用途地域CD */
	private String usedAreaCd;
	/** 取引形態区分 */
	private String transactTypeDiv;
	/** 表示用契約期間 */
	private String displayContractTerm;
	/** 土地権利 */
	private String landRight;
	/** 入居可能時期フラグ */
	private String moveinTiming;
	/** 入居可能時期 */
	private String moveinTimingDay;
	/** 入居可能時期コメント */
	private String moveinNote;
	/** 表示用入居可能時期 */
	private String displayMoveinTiming;
	/** 表示用入居諸条件 */
	private String displayMoveinProviso;
	/** 管理形態・方式 */
	private String upkeepType;
	/** 管理会社 */
	private String upkeepCorp;
	/** 更新料 */
	private String renewChrg;
	/** 更新料単位 */
	private String renewChrgCrs;
	/** 更新料名 */
	private String renewChrgName;
	/** 更新手数料 */
	private String renewDue;
	/** 更新手数料単位 */
	private String renewDueCrs;
	/** 仲介手数料 */
	private String brokerageChrg;
	/** 仲介手数料単位 */
	private String brokerageChrgCrs;
	/** 鍵交換料 */
	private String changeKeyChrg;
	/** 損保有無 */
	private String insurExist;
	/** 損保料金 */
	private String insurChrg;
	/** 損保年数 */
	private String insurTerm;
	/** 損保認定ランク */
	private String insurLank;
	/** 諸費用 */
	private String otherChrg;
	/** 接道状況 */
	private String contactRoad;
	/** 接道方向/幅員 */
	private String contactRoadDir;
	/** 私道負担 */
	private String privateRoad;
	/** バルコニー面積 */
	private String balconyArea;
	/** 特記事項 */
	private String specialInstruction;
	/** 詳細コメント */
	private String dtlComment;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;



	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected HousingDtlForm() {
		
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param　codeLookupManager　共通コード変換処理
	 */
	protected HousingDtlForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	
	
	/**
	 * 引数で渡された物件詳細情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param housingDtlInfo 物件詳細情報
	 * @throws ParseException 
	 */
	public void copyToHousingDtlInfo(HousingDtlInfo housingDtlInfo) {

		// 日付型の入力書式は、"yyyy/MM/dd"
		
		housingDtlInfo.setSysHousingCd(this.sysHousingCd);						// システム物件CD
		housingDtlInfo.setUsedAreaCd(this.usedAreaCd);							// 用途地域CD
		housingDtlInfo.setTransactTypeDiv(this.transactTypeDiv);				// 取引形態区分
		housingDtlInfo.setDisplayContractTerm(this.displayContractTerm);		// 表示用契約期間
		housingDtlInfo.setLandRight(this.landRight);							// 土地権利
		housingDtlInfo.setMoveinTiming(this.moveinTiming);						// 入居可能時期フラグ

		try {																	// 入居可能時期
			Date wkMoveinTimingDay = StringUtils.stringToDate(this.moveinTimingDay, "yyyy/MM/dd");
			housingDtlInfo.setMoveinTimingDay(wkMoveinTimingDay);
		} catch (ParseException e) {
			// バリデーションで安全性を担保しているので、このタイミングでエラーが発生する事は無いが、
			// 一応、警告をログ出力する。
			log.warn("moveinTimingDay format error. (" + this.moveinTimingDay + ")");
			housingDtlInfo.setMoveinTimingDay(null);
		}

		housingDtlInfo.setMoveinNote(this.moveinNote);							// 入居可能時期コメント
		housingDtlInfo.setDisplayMoveinTiming(this.displayMoveinTiming);		// 表示用入居可能時期
		housingDtlInfo.setDisplayMoveinProviso(this.displayMoveinProviso);		// 表示用入居諸条件
		housingDtlInfo.setUpkeepType(this.upkeepType);							// 管理形態・方式
		housingDtlInfo.setUpkeepCorp(this.upkeepCorp);							// 管理会社
		
		if (!StringValidateUtil.isEmpty(this.renewChrg)) {						// 更新料
			housingDtlInfo.setRenewChrg(new BigDecimal(this.renewChrg));
		} else {
			housingDtlInfo.setRenewChrg(null);
		}

		housingDtlInfo.setRenewChrgCrs(this.renewChrgCrs);						// 更新料単位
		housingDtlInfo.setRenewChrgName(this.renewChrgName);					// 更新料名

		if (!StringValidateUtil.isEmpty(this.renewDue)) {						// 更新手数料
			housingDtlInfo.setRenewDue(new BigDecimal(this.renewDue));
		} else {
			housingDtlInfo.setRenewDue(null);
		}

		housingDtlInfo.setRenewDueCrs(this.renewDueCrs);						// 更新手数料単位

		if (!StringValidateUtil.isEmpty(this.brokerageChrg)) {					// 仲介手数料
			housingDtlInfo.setBrokerageChrg(new BigDecimal(this.brokerageChrg));
		} else {
			housingDtlInfo.setBrokerageChrg(null);
		}

		housingDtlInfo.setBrokerageChrgCrs(this.brokerageChrgCrs);				// 仲介手数料単位

		if (!StringValidateUtil.isEmpty(this.changeKeyChrg)) {					// 鍵交換料
			housingDtlInfo.setChangeKeyChrg(new BigDecimal(this.changeKeyChrg));
		} else {
			housingDtlInfo.setChangeKeyChrg(null);
		}

		housingDtlInfo.setInsurExist(this.insurExist);							// 損保有無

		if (!StringValidateUtil.isEmpty(this.insurChrg)) {						// 損保料金
			housingDtlInfo.setInsurChrg(Long.valueOf(this.insurChrg));
		} else {
			housingDtlInfo.setInsurChrg(null);
		}

		if (!StringValidateUtil.isEmpty(this.insurTerm)) {						// 損保年数
			housingDtlInfo.setInsurTerm(Integer.valueOf(this.insurTerm));
		} else {
			housingDtlInfo.setInsurTerm(null);
		}

		housingDtlInfo.setInsurLank(this.insurLank);							// 損保認定ランク

		if (!StringValidateUtil.isEmpty(this.otherChrg)){						// 諸費用
			housingDtlInfo.setOtherChrg(Long.valueOf(this.otherChrg));
		} else {
			housingDtlInfo.setOtherChrg(null);
		}

		housingDtlInfo.setContactRoad(this.contactRoad);						// 接道状況
		housingDtlInfo.setContactRoadDir(this.contactRoadDir);					// 接道方向/幅員
		housingDtlInfo.setPrivateRoad(this.privateRoad);						// 私道負担

		if (!StringValidateUtil.isEmpty(this.balconyArea)){						// バルコニー面積
			housingDtlInfo.setBalconyArea(new BigDecimal(this.balconyArea));
		} else {
			housingDtlInfo.setBalconyArea(null);
		}

		housingDtlInfo.setSpecialInstruction(this.specialInstruction);			// 特記事項
		housingDtlInfo.setDtlComment(this.dtlComment);							// 詳細コメント

	}
	
	
	
	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();

		// システム物件CD のバリデーションは、Command 側で対応するので From 側では実装しない。
		// （更新時にパラメータが欠落している場合や、該当データが無い場合は例外をスローする。）

		// 用途地域CD
		validUsedAreaCd(errors);
		// 取引形態区分
		validTransactTypeDiv(errors);
		// 表示用契約期間
		validDisplayContractTerm(errors);
		// 土地権利
		validLandRight(errors);
		// 入居可能時期フラグ
		validMoveinTiming(errors);
		// 入居可能時期
		validMoveinTimingDay(errors);
		// 入居可能時期コメント
		validMoveinNote(errors);
		// 表示用入居可能時期
		validDisplayMoveinTiming(errors);
		// 表示用入居諸条件
		validDisplayMoveinProviso(errors);
		// 管理形態・方式
		validUpkeepType(errors);
		// 管理会社
		validUpkeepCorp(errors);
		// 更新料
		validRenewChrg(errors);
		// 更新料単位
		validRenewChrgCrs(errors);
		// 更新料名
		validRenewChrgName(errors);
		// 更新手数料
		validRenewDue(errors);
		// 更新手数料単位
		validRenewDueCrs(errors);
		// 仲介手数料
		validBrokerageChrg(errors);
		// 仲介手数料単位
		validBrokerageChrgCrs(errors);
		// 鍵交換料
		validChangeKeyChrg(errors);
		// 損保有無
		validInsurExist(errors);
		// 損保料金
		validInsurChrg(errors);
		// 損保年数
		validInsurTerm(errors);
		// 損保認定ランク
		validInsurLank(errors);
		// 諸費用
		validOtherChrg(errors);
		// 接道状況
		validContactRoad(errors);
		// 接道方向/幅員
		validContactRoadDir(errors);
		// 私道負担
		validPrivateRoad(errors);
		// バルコニー面積
		validBalconyArea(errors);
		// 特記事項
		validSpecialInstruction(errors);
		// 詳細コメント
		validDtlComment(errors);

		return (startSize == errors.size());
	}
	
	/**
	 * 用途地域CD のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validUsedAreaCd(List<ValidationFailure> errors){
		String label = "housingDtl.input.usedAreaCd";
		ValidationChain valid = new ValidationChain(label, this.usedAreaCd);

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "usedArea"));

		valid.validate(errors);
	}
	
	/**
	 * 取引形態区分 のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validTransactTypeDiv(List<ValidationFailure> errors){
		String label = "housingDtl.input.transactTypeDiv";
		ValidationChain valid = new ValidationChain(label, this.transactTypeDiv);
		
		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "transactTypeDiv"));

		valid.validate(errors);
	}

	/**
	 * 表示用契約期間 のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validDisplayContractTerm(List<ValidationFailure> errors){
		String label = "housingDtl.input.displayContractTerm";
		ValidationChain valid = new ValidationChain(label, this.displayContractTerm);
		
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 100)));

		valid.validate(errors);
	}

	/**
	 * 土地権利のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validLandRight(List<ValidationFailure> errors){
		String label = "housingDtl.input.landRight";
		ValidationChain valid = new ValidationChain(label, this.landRight);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 100)));

		valid.validate(errors);
	}

	/**
	 * 入居可能時期フラグのバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validMoveinTiming(List<ValidationFailure> errors){
		String label = "housingDtl.input.moveinTiming";
		ValidationChain valid = new ValidationChain(label, this.moveinTiming);

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "moveinTiming"));

		valid.validate(errors);
	}

	/**
	 * 入居可能時期のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>日付チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validMoveinTimingDay(List<ValidationFailure> errors){
		String label = "housingDtl.input.moveinTimingDay";
		ValidationChain valid = new ValidationChain(label, this.moveinTimingDay);

		// 日付チェック
		valid.addValidation(new ValidDateValidation("yyyy/MM/dd"));

		valid.validate(errors);
	}

	/**
	 * 入居可能時期コメントのバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validMoveinNote(List<ValidationFailure> errors){
		String label = "housingDtl.input.moveinNote";
		ValidationChain valid = new ValidationChain(label, this.moveinNote);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)));

		valid.validate(errors);
	}

	/**
	 * 表示用入居可能時期のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validDisplayMoveinTiming(List<ValidationFailure> errors){
		String label = "housingDtl.input.displayMoveinTiming";
		ValidationChain valid = new ValidationChain(label, this.displayMoveinTiming);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 18)));

		valid.validate(errors);
	}

	/**
	 * 表示用入居諸条件のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validDisplayMoveinProviso(List<ValidationFailure> errors){
		String label = "housingDtl.input.displayMoveinProviso";
		ValidationChain valid = new ValidationChain(label, this.displayMoveinProviso);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 300)));

		valid.validate(errors);
	}

	/**
	 * 管理形態・方式のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validUpkeepType(List<ValidationFailure> errors){
		String label = "housingDtl.input.upkeepType";
		ValidationChain valid = new ValidationChain(label, this.upkeepType);
		
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)));

		valid.validate(errors);
	}

	/**
	 * 管理会社のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validUpkeepCorp(List<ValidationFailure> errors){
		String label = "housingDtl.input.upkeepCorp";
		ValidationChain valid = new ValidationChain(label, this.upkeepCorp);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)));

		valid.validate(errors);
	}

	/**
	 * 更新料のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>更新料単位の入力があった場合は必須チェック</li>
	 *   <li>数値チェック</li>
	 *   <li>整数部、小数部桁数チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validRenewChrg(List<ValidationFailure> errors){
		String label = "housingDtl.input.renewChrg";
		ValidationChain valid = new ValidationChain(label, this.renewChrg);

		// 更新料単位の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.renewChrgCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// 数値チェック
		valid.addValidation(new NumberValidation());
		// 整数部、小数部桁数チェック
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * 更新料単位のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>更新料の入力があった場合は必須チェック</li>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validRenewChrgCrs(List<ValidationFailure> errors){
		String label = "housingDtl.input.renewChrgCrs";
		ValidationChain valid = new ValidationChain(label, this.renewChrgCrs);

		// 更新料の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.renewChrg)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));

		valid.validate(errors);
	}

	/**
	 * 更新料名のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validRenewChrgName(List<ValidationFailure> errors){
		String label = "housingDtl.input.renewChrgName";
		ValidationChain valid = new ValidationChain(label, this.renewChrgName);
		
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

		valid.validate(errors);
	}

	/**
	 * 更新手数料のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>更新手数料単位の入力があった場合は必須チェック</li>
	 *   <li>数値チェック</li>
	 *   <li>整数部、小数部桁数チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validRenewDue(List<ValidationFailure> errors){
		String label = "housingDtl.input.renewDue";
		ValidationChain valid = new ValidationChain(label, this.renewDue);
		
		// 更新手数料単位の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.renewDueCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// 数値チェック
		valid.addValidation(new NumberValidation());
		// 整数部、小数部桁数チェック
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * 更新手数料単位のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>更新手数料の入力があった場合は必須チェック</li>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validRenewDueCrs(List<ValidationFailure> errors){
		String label = "housingDtl.input.renewDueCrs";
		ValidationChain valid = new ValidationChain(label, this.renewDueCrs);

		// 更新手数料の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.renewDue)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));

		valid.validate(errors);
	}

	/**
	 * 仲介手数料のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>仲介手数料単位の入力があった場合は必須チェック</li>
	 *   <li>数値チェック</li>
	 *   <li>整数部、小数部桁数チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validBrokerageChrg(List<ValidationFailure> errors){
		String label = "housingDtl.input.brokerageChrg";
		ValidationChain valid = new ValidationChain(label, this.brokerageChrg);

		// 仲介手数料単位の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.brokerageChrgCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// 数値チェック
		valid.addValidation(new NumberValidation());
		// 整数部、小数部桁数チェック
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * 仲介手数料単位のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>仲介手数料の入力があった場合は必須チェック</li>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validBrokerageChrgCrs(List<ValidationFailure> errors){
		String label = "housingDtl.input.brokerageChrgCrs";
		ValidationChain valid = new ValidationChain(label, this.brokerageChrgCrs);
		
		// 更新手数料の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.brokerageChrg)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));

		valid.validate(errors);
	}

	/**
	 * 鍵交換料のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>数値チェック</li>
	 *   <li>整数部、小数部桁数チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validChangeKeyChrg(List<ValidationFailure> errors){
		String label = "housingDtl.input.changeKeyChrg";
		ValidationChain valid = new ValidationChain(label, this.changeKeyChrg);
		
		// 数値チェック
		valid.addValidation(new NumberValidation());
		// 整数部、小数部桁数チェック
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * 損保有無のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validInsurExist(List<ValidationFailure> errors){
		String label = "housingDtl.input.insurExist";
		ValidationChain valid = new ValidationChain(label, this.insurExist);

		// パターンチェック
		valid.addValidation(new ArrayMemberValidation(new String[]{"0","1"}));

		valid.validate(errors);
	}

	/**
	 * 損保料金のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 *   <li>半角数字チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validInsurChrg(List<ValidationFailure> errors){
		String label = "housingDtl.input.insurChrg";
		ValidationChain valid = new ValidationChain(label, this.insurChrg);
		
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// 半角数字チェック
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * 損保年数のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 *   <li>半角数字チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validInsurTerm(List<ValidationFailure> errors){
		String label = "housingDtl.input.insurTerm";
		ValidationChain valid = new ValidationChain(label, this.insurTerm);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 3)));
		// 半角数字チェック
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}
	
	/**
	 * 損保認定ランクのバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validInsurLank(List<ValidationFailure> errors){
		String label = "housingDtl.input.insurLank";
		ValidationChain valid = new ValidationChain(label, this.insurLank);

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "insurLank"));

		valid.validate(errors);
	}

	/**
	 * 諸費用のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 *   <li>半角数字チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validOtherChrg(List<ValidationFailure> errors){
		String label = "housingDtl.input.otherChrg";
		ValidationChain valid = new ValidationChain(label, this.otherChrg);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// 半角数字チェック
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * 接道状況のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validContactRoad(List<ValidationFailure> errors){
		String label = "housingDtl.input.contactRoad";
		ValidationChain valid = new ValidationChain(label, this.contactRoad);
		
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * 接道方向/幅員のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validContactRoadDir(List<ValidationFailure> errors){
		String label = "housingDtl.input.contactRoadDir";
		ValidationChain valid = new ValidationChain(label, this.contactRoadDir);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));
		
		valid.validate(errors);
	}

	/**
	 * 私道負担のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validPrivateRoad(List<ValidationFailure> errors){
		String label = "housingDtl.input.privateRoad";
		ValidationChain valid = new ValidationChain(label, this.privateRoad);
		
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));
		
		valid.validate(errors);
	}

	/**
	 * バルコニー面積のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>数値チェック</li>
	 *   <li>整数部、小数部桁数チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validBalconyArea(List<ValidationFailure> errors){
		String label = "housingDtl.input.balconyArea";
		ValidationChain valid = new ValidationChain(label, this.balconyArea);

		// 数値チェック
		valid.addValidation(new NumberValidation());
		// 整数部、小数部桁数チェック
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * 特記事項のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validSpecialInstruction(List<ValidationFailure> errors){
		String label = "housingDtl.input.specialInstruction";
		ValidationChain valid = new ValidationChain(label, this.specialInstruction);
		
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 100)));

		valid.validate(errors);
	}

	/**
	 * 詳細コメントのバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validDtlComment(List<ValidationFailure> errors){
		String label = "housingDtl.input.dtlComment";
		ValidationChain valid = new ValidationChain(label, this.dtlComment);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 1000)));

		valid.validate(errors);
	}



	/**
	 * システム物件CD を取得する。<br/>
	 * <br/>
	 * @return システム物件CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}
	
	/**
	 * システム物件CD を設定する。<br/>
	 * <br/>
	 * @param sysHousingCd システム物件CD
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * 用途地域CD を取得する。<br/>
	 * <br/>
	 * @return　用途地域CD
	 */
	public String getUsedAreaCd() {
		return usedAreaCd;
	}

	/**
	 * 用途地域CD を設定する。<br/>
	 * <br/>
	 * @param usedAreaCd 用途地域CD
	 */
	public void setUsedAreaCd(String usedAreaCd) {
		this.usedAreaCd = usedAreaCd;
	}

	/**
	 * 取引形態区分を取得する。<br/>
	 * <br/>
	 * @return 取引形態区分
	 */
	public String getTransactTypeDiv() {
		return transactTypeDiv;
	}

	/**
	 * 取引形態区分を設定する。<br/>
	 * <br/>
	 * @param transactTypeDiv 取引形態区分
	 */
	public void setTransactTypeDiv(String transactTypeDiv) {
		this.transactTypeDiv = transactTypeDiv;
	}

	/**
	 * 表示用契約期間を取得する。<br/>
	 * <br/>
	 * @return 表示用契約期間
	 */
	public String getDisplayContractTerm() {
		return displayContractTerm;
	}

	/**
	 * 表示用契約期間を設定する。<br/>
	 * <br/>
	 * @param displayContractTerm 表示用契約期間
	 */
	public void setDisplayContractTerm(String displayContractTerm) {
		this.displayContractTerm = displayContractTerm;
	}
	
	/**
	 * 土地権利を取得する。<br/>
	 * <br/>
	 * @return 土地権利
	 */
	public String getLandRight() {
		return landRight;
	}

	/**
	 * 土地権利を設定する。<br/>
	 * <br/>
	 * @param landRight 土地権利
	 */
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}

	/**
	 * 入居可能時期フラグを取得する。<br/>
	 * <br/>
	 * @return　入居可能時期フラグ
	 */
	public String getMoveinTiming() {
		return moveinTiming;
	}

	/**
	 * 入居可能時期フラグを設定する。<br/>
	 * <br/>
	 * @param moveinTiming 入居可能時期フラグ
	 */
	public void setMoveinTiming(String moveinTiming) {
		this.moveinTiming = moveinTiming;
	}

	/**
	 * 入居可能時期を取得する。<br/>
	 * <br/>
	 * @return 入居可能時期
	 */
	public String getMoveinTimingDay() {
		return moveinTimingDay;
	}

	/**
	 * 入居可能時期を設定する。<br/>
	 * <br/>
	 * @param moveinTimingDay 入居可能時期
	 */
	public void setMoveinTimingDay(String moveinTimingDay) {
		this.moveinTimingDay = moveinTimingDay;
	}

	/**
	 * 入居可能時期コメントを取得する。<br/>
	 * <br/>
	 * @return 入居可能時期コメント
	 */
	public String getMoveinNote() {
		return moveinNote;
	}

	/**
	 * 入居可能時期コメントを設定する。<br/>
	 * <br/>
	 * @param moveinNote 入居可能時期コメント
	 */
	public void setMoveinNote(String moveinNote) {
		this.moveinNote = moveinNote;
	}

	/**
	 * 表示用入居可能時期を取得する。<br/>
	 * <br/>
	 * @return 表示用入居可能時期
	 */
	public String getDisplayMoveinTiming() {
		return displayMoveinTiming;
	}

	/**
	 * 表示用入居可能時期を設定する。<br/>
	 * <br/>
	 * @param displayMoveinTiming 表示用入居可能時期
	 */
	public void setDisplayMoveinTiming(String displayMoveinTiming) {
		this.displayMoveinTiming = displayMoveinTiming;
	}

	/**
	 * 表示用入居諸条件を取得する。<br/>
	 * <br/>
	 * @return 表示用入居諸条件
	 */
	public String getDisplayMoveinProviso() {
		return displayMoveinProviso;
	}

	/**
	 * 表示用入居諸条件を設定する。<br/>
	 * <br/>
	 * @param displayMoveinProviso　表示用入居諸条件
	 */
	public void setDisplayMoveinProviso(String displayMoveinProviso) {
		this.displayMoveinProviso = displayMoveinProviso;
	}

	/**
	 * 管理形態・方式を取得する。<br/>
	 * <br/>
	 * @return 管理形態・方式
	 */
	public String getUpkeepType() {
		return upkeepType;
	}

	/**
	 * 管理形態・方式を設定する。<br/>
	 * <br/>
	 * @param upkeepType 管理形態・方式
	 */
	public void setUpkeepType(String upkeepType) {
		this.upkeepType = upkeepType;
	}

	/**
	 * 管理会社を取得する。<br/>
	 * <br/>
	 * @return 管理会社
	 */
	public String getUpkeepCorp() {
		return upkeepCorp;
	}

	/**
	 * 管理会社を設定する。<br/>
	 * <br/>
	 * @param upkeepCorp 管理会社
	 */
	public void setUpkeepCorp(String upkeepCorp) {
		this.upkeepCorp = upkeepCorp;
	}

	/**
	 * 更新料を取得する。<br/>
	 * <br/>
	 * @return 更新料
	 */
	public String getRenewChrg() {
		return renewChrg;
	}

	/**
	 * 更新料を設定する。<br/>
	 * <br/>
	 * @param renewChrg 更新料
	 */
	public void setRenewChrg(String renewChrg) {
		this.renewChrg = renewChrg;
	}

	/**
	 * 更新料単位を取得する。<br/>
	 * <br/>
	 * @return 更新料単位
	 */
	public String getRenewChrgCrs() {
		return renewChrgCrs;
	}

	/**
	 * 更新料単位を設定する。<br/>
	 * <br/>
	 * @param renewChrgCrs 更新料単位
	 */
	public void setRenewChrgCrs(String renewChrgCrs) {
		this.renewChrgCrs = renewChrgCrs;
	}

	/**
	 * 更新料名を取得する。<br/>
	 * <br/>
	 * @return 更新料名
	 */
	public String getRenewChrgName() {
		return renewChrgName;
	}

	/**
	 * 更新料名を設定する。<br/>
	 * @param renewChrgName
	 */
	public void setRenewChrgName(String renewChrgName) {
		this.renewChrgName = renewChrgName;
	}

	/**
	 * 更新手数料を取得する。<br/>
	 * <br/>
	 * @return 更新手数料
	 */
	public String getRenewDue() {
		return renewDue;
	}

	/**
	 * 更新手数料を設定する。<br/>
	 * <br/>
	 * @param renewDue 更新手数料
	 */
	public void setRenewDue(String renewDue) {
		this.renewDue = renewDue;
	}

	/**
	 * 更新手数料単位を取得する。<br/>
	 * <br/>
	 * @return 更新手数料単位
	 */
	public String getRenewDueCrs() {
		return renewDueCrs;
	}

	/**
	 * 更新手数料単位を設定する。<br/>
	 * <br/>
	 * @param renewDueCrs 更新手数料単位
	 */
	public void setRenewDueCrs(String renewDueCrs) {
		this.renewDueCrs = renewDueCrs;
	}

	/**
	 * 仲介手数料を取得する。<br/>
	 * <br/>
	 * @return 仲介手数料
	 */
	public String getBrokerageChrg() {
		return brokerageChrg;
	}

	/**
	 * 仲介手数料を設定する。<br/>
	 * <br/>
	 * @param brokerageChrg　仲介手数料
	 */
	public void setBrokerageChrg(String brokerageChrg) {
		this.brokerageChrg = brokerageChrg;
	}

	/**
	 * 仲介手数料単位を取得する。<br/>
	 * <br/>
	 * @return 仲介手数料単位
	 */
	public String getBrokerageChrgCrs() {
		return brokerageChrgCrs;
	}

	/**
	 * 仲介手数料単位を設定する。<br/>
	 * <br/>
	 * @param brokerageChrgCrs 仲介手数料単位
	 */
	public void setBrokerageChrgCrs(String brokerageChrgCrs) {
		this.brokerageChrgCrs = brokerageChrgCrs;
	}

	/**
	 * 鍵交換料を取得する。<br/>
	 * <br/>
	 * @return 鍵交換料
	 */
	public String getChangeKeyChrg() {
		return changeKeyChrg;
	}

	/**
	 * 鍵交換料を設定する。<br/>
	 * <br/>
	 * @param changeKeyChrg 鍵交換料
	 */
	public void setChangeKeyChrg(String changeKeyChrg) {
		this.changeKeyChrg = changeKeyChrg;
	}

	/**
	 * 損保有無を取得する。<br/>
	 * <br/>
	 * @return 損保有無
	 */
	public String getInsurExist() {
		return insurExist;
	}

	/**
	 * 損保有無を設定する。<br/>
	 * <br/>
	 * @param insurExist 損保有無
	 */
	public void setInsurExist(String insurExist) {
		this.insurExist = insurExist;
	}

	/**
	 * 損保料金を取得する。<br/>
	 * <br/>
	 * @return 損保料金
	 */
	public String getInsurChrg() {
		return insurChrg;
	}

	/**
	 * 損保料金を設定する。<br/>
	 * <br/>
	 * @param insurChrg 損保料金
	 */
	public void setInsurChrg(String insurChrg) {
		this.insurChrg = insurChrg;
	}

	/**
	 * 損保年数を取得する。<br/>
	 * <br/>
	 * @return 損保年数
	 */
	public String getInsurTerm() {
		return insurTerm;
	}

	/**
	 * 損保年数を設定する。<br/>
	 * <br/>
	 * @param insurTerm 損保年数
	 */
	public void setInsurTerm(String insurTerm) {
		this.insurTerm = insurTerm;
	}

	/**
	 * 損保認定ランクを取得する。<br/>
	 * <br/>
	 * @return 損保認定ランク
	 */
	public String getInsurLank() {
		return insurLank;
	}

	/**
	 * 損保認定ランクを設定する。<br/>
	 * <br/>
	 * @param insurLank 損保認定ランク
	 */
	public void setInsurLank(String insurLank) {
		this.insurLank = insurLank;
	}

	/**
	 * 諸費用を取得する。<br/>
	 * <br/>
	 * @return 諸費用
	 */
	public String getOtherChrg() {
		return otherChrg;
	}

	/**
	 * 諸費用を設定する。<br/>
	 * <br/>
	 * @param otherChrg 諸費用
	 */
	public void setOtherChrg(String otherChrg) {
		this.otherChrg = otherChrg;
	}

	/**
	 * 接道状況を取得する。<br/>
	 * <br/>
	 * @return 接道状況
	 */
	public String getContactRoad() {
		return contactRoad;
	}

	/**
	 * 接道状況を設定する。<br/>
	 * <br/>
	 * @param contactRoad 接道状況
	 */
	public void setContactRoad(String contactRoad) {
		this.contactRoad = contactRoad;
	}

	/**
	 * 接道方向/幅員を取得する。<br/>
	 * <br/>
	 * @return 接道方向/幅員
	 */
	public String getContactRoadDir() {
		return contactRoadDir;
	}

	/**
	 * 接道方向/幅員を設定する。<br/>
	 * <br/>
	 * @param contactRoadDir 接道方向/幅員
	 */
	public void setContactRoadDir(String contactRoadDir) {
		this.contactRoadDir = contactRoadDir;
	}

	/**
	 * 私道負担を取得する。<br/>
	 * <br/>
	 * @return 私道負担
	 */
	public String getPrivateRoad() {
		return privateRoad;
	}

	/**
	 * 私道負担を設定する。<br/>
	 * <br/>
	 * @param privateRoad 私道負担
	 */
	public void setPrivateRoad(String privateRoad) {
		this.privateRoad = privateRoad;
	}

	/**
	 * バルコニー面積を取得する。<br/>
	 * <br/>
	 * @return バルコニー面積
	 */
	public String getBalconyArea() {
		return balconyArea;
	}

	/**
	 * バルコニー面積を設定する。<br/>
	 * <br/>
	 * @param balconyArea バルコニー面積
	 */
	public void setBalconyArea(String balconyArea) {
		this.balconyArea = balconyArea;
	}

	/**
	 * 特記事項を取得する。<br/>
	 * <br/>
	 * @return 特記事項
	 */
	public String getSpecialInstruction() {
		return specialInstruction;
	}

	/**
	 * 特記事項を取得する。<br/>
	 * <br/>
	 * @param specialInstruction 特記事項
	 */
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	/**
	 * 詳細コメントを取得する。<br/>
	 * <br/>
	 * @return 詳細コメント
	 */
	public String getDtlComment() {
		return dtlComment;
	}

	/**
	 * 詳細コメントを取得する。<br/>
	 * <br/>
	 * @param dtlComment 詳細コメント
	 */
	public void setDtlComment(String dtlComment) {
		this.dtlComment = dtlComment;
	}

}
