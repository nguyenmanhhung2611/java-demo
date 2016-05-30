package jp.co.transcosmos.dm3.core.model.housing.form;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ArrayMemberValidation;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.DecimalValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * 物件基本情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class HousingForm implements Validateable {

	/** システム物件CD */
	private String sysHousingCd;
	/** 物件番号 */
	private String housingCd;
	/** 表示用物件名 */
	private String displayHousingName;
	/** 表示用物件名（カナ） */
	private String displayHousingNameKana;
	/** 部屋番号 */
	private String roomNo;
	/** システム建物CD */
	private String sysBuildingCd;
	/** 賃料・価格 */
	private String price;
	/** 管理費 */
	private String upkeep;
	/** 共益費　*/
	private String commonAreaFee;
	/** 修繕積立費 */
	private String menteFee;
	/** 敷金 */
	private String secDeposit;
	/** 敷金単位 */
	private String secDepositCrs;
	/** 保証金 */
	private String bondChrg;
	/** 保証金単位 */
	private String bondChrgCrs;
	/** 敷引礼金区分 */
	private String depositDiv;
	/** 敷引礼金額 */
	private String deposit;
	/** 敷引礼金単位 */
	private String depositCrs;
	/** 間取CD */
	private String layoutCd;
	/** 間取詳細コメント */
	private String layoutComment;
	/** 物件の階数 */
	private String floorNo;
	/** 物件の階数コメント */
	private String floorNoNote;
	/** 土地面積 */
	private String landArea;
	/** 土地面積_補足 */
	private String landAreaMemo;
	/** 専有面積 */
	private String personalArea;
	/** 専有面積_補足 */
	private String personalAreaMemo;
	/** 入居状態フラグ */
	private String moveinFlg;
	/** 駐車場の状況 */
	private String parkingSituation;
	/** 駐車場空の有無 */
	private String parkingEmpExist;
	/** 表示用駐車場情報 */
	private String displayParkingInfo;
	/** 窓の向き */
	private String windowDirection;
	/** 基本情報コメント */
	private String basicComment;

	// アイコン情報 は、内部的に自動生成されるので入力情報ではない。

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	
	
	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected HousingForm() {
		super();
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param　codeLookupManager　共通コード変換処理
	 */
	protected HousingForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	

	/**
	 * 引数で渡された物件基本情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param housingInfo 物件基本情報
	 */
	public void copyToHousingInfo(HousingInfo housingInfo){

		// システム物件CD は、自動採番するか、housingInfo 側で予め設定されている値を使用するので
		// Form の値は設定しない。
		
		housingInfo.setHousingCd(this.housingCd);								// 物件番号
		housingInfo.setDisplayHousingName(this.displayHousingName);				// 表示用物件名
		housingInfo.setDisplayHousingNameKana(this.displayHousingNameKana);		// 表示用物件名ふりがな
		housingInfo.setRoomNo(this.roomNo);										// 部屋番号
		housingInfo.setSysBuildingCd(this.sysBuildingCd);						// システム建物CD

		if (!StringValidateUtil.isEmpty(this.price)) {							// 賃料/価格
			housingInfo.setPrice(Long.valueOf(this.price));
		} else {
			housingInfo.setPrice(null);
		}

		if (!StringValidateUtil.isEmpty(this.upkeep)) {							// 管理費
			housingInfo.setUpkeep(Long.valueOf(this.upkeep));
		} else {
			housingInfo.setUpkeep(null);
		}

		if (!StringValidateUtil.isEmpty(this.commonAreaFee)) {					// 共益費
			housingInfo.setCommonAreaFee(Long.valueOf(this.commonAreaFee));
		} else {
			housingInfo.setCommonAreaFee(null);
		}

		if (!StringValidateUtil.isEmpty(this.menteFee)) {						// 修繕積立費
			housingInfo.setMenteFee(Long.valueOf(this.menteFee));
		} else {
			housingInfo.setMenteFee(null);
		}

		if (!StringValidateUtil.isEmpty(this.secDeposit)) {						// 敷金
			housingInfo.setSecDeposit(new BigDecimal(this.secDeposit));
		} else {
			housingInfo.setSecDeposit(null);
		}

		housingInfo.setSecDepositCrs(this.secDepositCrs);						// 敷金単位

		if (!StringValidateUtil.isEmpty(this.bondChrg)) {						// 保証金
			housingInfo.setBondChrg(new BigDecimal(this.bondChrg));
		} else {
			housingInfo.setBondChrg(null);
		}

		housingInfo.setBondChrgCrs(this.bondChrgCrs);							// 保証金単位
		housingInfo.setDepositDiv(this.depositDiv);								// 敷引礼金区分

		if (!StringValidateUtil.isEmpty(this.deposit)) {						// 敷引礼金額
			housingInfo.setDeposit(new BigDecimal(this.deposit));
		} else {
			housingInfo.setDeposit(null);
		}

		housingInfo.setDepositCrs(this.depositCrs);								// 敷引礼金単位
		housingInfo.setLayoutCd(this.layoutCd);									// 間取CD
		housingInfo.setLayoutComment(this.layoutComment);						// 間取詳細コメント

		if (!StringValidateUtil.isEmpty(this.floorNo)) {						// 物件の階数
			housingInfo.setFloorNo(Integer.valueOf(this.floorNo));
		} else {
			housingInfo.setFloorNo(null);
		}

		housingInfo.setFloorNoNote(this.floorNoNote);							// 物件の階数コメント

		if (!StringValidateUtil.isEmpty(this.landArea)) {						// 土地面積
			housingInfo.setLandArea(new BigDecimal(this.landArea));
		} else {
			housingInfo.setLandArea(null);
		}

		housingInfo.setLandAreaMemo(this.landAreaMemo);							// 土地面積_補足

		if (!StringValidateUtil.isEmpty(this.personalArea)) {					// 専有面積
			housingInfo.setPersonalArea(new BigDecimal(this.personalArea));
		} else {
			housingInfo.setPersonalArea(null);
		}

		housingInfo.setPersonalAreaMemo(this.personalAreaMemo);					// 専有面積_補足
		housingInfo.setMoveinFlg(this.moveinFlg);								// 入居状態フラグ
		housingInfo.setParkingSituation(this.parkingSituation);					// 駐車場の状況
		housingInfo.setParkingEmpExist(this.parkingEmpExist);					// 駐車場空の有無
		housingInfo.setDisplayParkingInfo(this.displayParkingInfo);				// 表示用駐車場情報
		housingInfo.setWindowDirection(this.windowDirection);					// 窓の向き

		// アイコン情報は、設備情報側から自動更新するので入力項目ではない。

		housingInfo.setBasicComment(this.basicComment);							// 基本情報コメント
		
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

		// 物件番号
		validHousingCd(errors);
		// 表示用物件名
		validDisplayHousingName(errors);
		// 表示用物件名（カナ）
		validDisplayHousingNameKana(errors);
		// 部屋番号
		validRoomNo(errors);
		// システム建物CD
		validSysBuildingCd(errors);
		// 賃料・価格
		validPrice(errors);
		// 管理費
		validUpkeep(errors);
		// 共益費
		validCommonAreaFee(errors);
		// 修繕積立費
		validMenteFee(errors);
		// 敷金
		validSecDeposit(errors);
		// 敷金単位
		validSecDepositCrs(errors);
		// 保証金
		validBondChrg(errors);
		// 保証金単位
		validBondChrgCrs(errors);
		// 敷引礼金区分
		validDepositDiv(errors);
		// 敷引礼金額
		validDeposit(errors);
		// 敷引礼金単位
		validDepositCrs(errors);
		// 間取CD
		validLayoutCd(errors);
		// 間取詳細コメント
		validLayoutComment(errors);
		// 物件の階数
		validFloorNo(errors);
		// 物件の階数コメント
		validFloorNoNote(errors);
		// 土地面積
		validLandArea(errors);
		// 土地面積_補足
		validLandAreaMemo(errors);
		// 専有面積
		validPersonalArea(errors);
		// 専有面積_補足
		validPersonalAreaMemo(errors);
		// 入居状態フラグ
		validMoveinFlg(errors);
		// 駐車場の状況
		validParkingSituation(errors);
		// 駐車場空の有無
		validParkingEmpExist(errors);
		// 表示用駐車場情報
		validDisplayParkingInfo(errors);
		// 窓の向き
		validWindowDirection(errors);
		// 基本情報コメント
		validBasicComment(errors);
		
		return (startSize == errors.size());

	}



	/**
	 * 物件番号のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 *   <li>半角英数記号チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validHousingCd(List<ValidationFailure> errors) {
		String label = "housing.input.housingCd";
		ValidationChain valid = new ValidationChain(label, this.housingCd);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)));
		// 半角英数記号チェック
		valid.addValidation(new AsciiOnlyValidation());

		valid.validate(errors);
	}

	/**
	 * 表示用物件名のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>必須チェック</li>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validDisplayHousingName(List<ValidationFailure> errors) {
		String label = "housing.input.displayHousingName";
		ValidationChain valid = new ValidationChain(label, this.displayHousingName);

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}

	/**
	 * 表示用物件名（カナ）のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>必須チェック</li>
	 *   <li>最大文字列長チェック</li>
	 *   <li>全角カタカナチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validDisplayHousingNameKana(List<ValidationFailure> errors){
		String label = "housing.input.displayHousingNameKana";
		ValidationChain valid = new ValidationChain(label, this.displayHousingNameKana);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 80)));
		// 全角カタカナチェック
		valid.addValidation(new ZenkakuKanaValidator());

		valid.validate(errors);
	}

	/**
	 * 部屋番号のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 *   <li>半角英数記号チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validRoomNo(List<ValidationFailure> errors){
		String label = "housing.input.roomNo";
		ValidationChain valid = new ValidationChain(label, this.roomNo);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 5)));
		// 半角英数記号チェック
		valid.addValidation(new AsciiOnlyValidation());

		valid.validate(errors);
	}

	/**
	 * システム建物CDのバリデーション<br/>
	 * <br/>
	 * @param errors エラーメッセージリスト
	 */
	protected void validSysBuildingCd(List<ValidationFailure> errors){
		// Command 側で存在チェックを行うので特になし。
	}

	/**
	 * 賃料・価格のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 *   <li>半角数字チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validPrice(List<ValidationFailure> errors){
		String label = "housing.input.price";
		ValidationChain valid = new ValidationChain(label, this.price);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// 半角数字チェック
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * 管理費のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 *   <li>半角数字チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validUpkeep(List<ValidationFailure> errors){
		String label = "housing.input.upkeep";
		ValidationChain valid = new ValidationChain(label, this.upkeep);
		
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// 半角数字チェック
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * 共益費のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 *   <li>半角数字チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validCommonAreaFee(List<ValidationFailure> errors){
		String label = "housing.input.commonAreaFee";
		ValidationChain valid = new ValidationChain(label, this.commonAreaFee);
		
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// 半角数字チェック
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * 修繕積立費のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 *   <li>半角数字チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validMenteFee(List<ValidationFailure> errors){
		String label = "housing.input.menteFee";
		ValidationChain valid = new ValidationChain(label, this.menteFee);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// 半角数字チェック
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * 敷金のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>敷金単位の入力があった場合は必須チェック</li>
	 *   <li>数値チェック</li>
	 *   <li>整数部、小数部桁数チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validSecDeposit(List<ValidationFailure> errors){
		String label = "housing.input.secDeposit";
		ValidationChain valid = new ValidationChain(label, this.secDeposit);

		// 敷金単位の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.secDepositCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// 数値チェック
		valid.addValidation(new NumberValidation());
		// 整数部、小数部桁数チェック
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * 敷金単位のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>敷金の入力があった場合は必須チェック</li>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validSecDepositCrs(List<ValidationFailure> errors){
		String label = "housing.input.secDepositCrs";
		ValidationChain valid = new ValidationChain(label, this.secDepositCrs);

		// 敷金の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.secDeposit)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));
		
		valid.validate(errors);
	}

	/**
	 * 保証金のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>保証金単位の入力があった場合は必須チェック</li>
	 *   <li>数値チェック</li>
	 *   <li>整数部、小数部桁数チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validBondChrg(List<ValidationFailure> errors){
		String label = "housing.input.bondChrg";
		ValidationChain valid = new ValidationChain(label, this.bondChrg);
		
		// 保証金単位の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.bondChrgCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// 数値チェック
		valid.addValidation(new NumberValidation());
		// 整数部、小数部桁数チェック
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * 保証金単位のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>保証金の入力があった場合は必須チェック</li>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validBondChrgCrs(List<ValidationFailure> errors){
		String label = "housing.input.bondChrgCrs";
		ValidationChain valid = new ValidationChain(label, this.bondChrgCrs);

		// 保証金の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.bondChrg)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));
		
		valid.validate(errors);
	}

	/**
	 * 敷引礼金区分のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validDepositDiv(List<ValidationFailure> errors){
		String label = "housing.input.depositDiv";
		ValidationChain valid = new ValidationChain(label, this.depositDiv);

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "deposit"));

		valid.validate(errors);
	}

	/**
	 * 敷引礼金額のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>敷引礼金単位の入力があった場合は必須チェック</li>
	 *   <li>数値チェック</li>
	 *   <li>整数部、小数部桁数チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validDeposit(List<ValidationFailure> errors){
		String label = "housing.input.deposit";
		ValidationChain valid = new ValidationChain(label, this.deposit);

		// 敷引礼金単位の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.depositCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// 数値チェック
		valid.addValidation(new NumberValidation());
		// 整数部、小数部桁数チェック
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * 敷引礼金単位のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>敷引礼金額の入力があった場合は必須チェック</li>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validDepositCrs(List<ValidationFailure> errors){
		String label = "housing.input.depositCrs";
		ValidationChain valid = new ValidationChain(label, this.depositCrs);

		// 敷引礼金額の入力があった場合は必須チェック
		if (StringValidateUtil.isEmpty(this.deposit)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));

		valid.validate(errors);
	}

	/**
	 * 間取CD のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validLayoutCd(List<ValidationFailure> errors){
		String label = "housing.input.layoutCd";
		ValidationChain valid = new ValidationChain(label, this.layoutCd);

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "layout"));
		
		valid.validate(errors);
	}

	/**
	 * 間取詳細コメントのバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validLayoutComment(List<ValidationFailure> errors){
		String label = "housing.input.layoutComment";
		ValidationChain valid = new ValidationChain(label, this.layoutComment);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 200)));

		valid.validate(errors);
	}

	/**
	 * 物件の階数のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 *   <li>半角数字チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validFloorNo(List<ValidationFailure> errors){
		String label = "housing.input.floorNo";
		ValidationChain valid = new ValidationChain(label, this.floorNo);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 3)));
		// 半角数字チェック
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * 物件の階数コメントのバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validFloorNoNote(List<ValidationFailure> errors){
		String label = "housing.input.floorNoNote";
		ValidationChain valid = new ValidationChain(label, this.floorNoNote);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 10)));

		valid.validate(errors);
	}

	/**
	 * 土地面積のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>数値チェック</li>
	 *   <li>整数部、小数部桁数チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validLandArea(List<ValidationFailure> errors){
		String label = "housing.input.landArea";
		ValidationChain valid = new ValidationChain(label, this.landArea);

		// 数値チェック
		valid.addValidation(new NumberValidation());
		// 整数部、小数部桁数チェック
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * 土地面積_補足のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validLandAreaMemo(List<ValidationFailure> errors){
		String label = "housing.input.landAreaMemo";
		ValidationChain valid = new ValidationChain(label, this.landAreaMemo);
		
		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 10)));
		
		valid.validate(errors);
	}

	/**
	 * 専有面積のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>数値チェック</li>
	 *   <li>整数部、小数部桁数チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validPersonalArea(List<ValidationFailure> errors){
		String label = "housing.input.personalArea";
		ValidationChain valid = new ValidationChain(label, this.personalArea);

		// 数値チェック
		valid.addValidation(new NumberValidation());
		// 整数部、小数部桁数チェック
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * 専有面積_補足のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validPersonalAreaMemo(List<ValidationFailure> errors){
		String label = "housing.input.personalArea";
		ValidationChain valid = new ValidationChain(label, this.personalArea);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 10)));

		valid.validate(errors);
	}

	/**
	 * 入居状態フラグのバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validMoveinFlg(List<ValidationFailure> errors){
		String label = "housing.input.moveinFlg";
		ValidationChain valid = new ValidationChain(label, this.moveinFlg);

		// パターンチェック
		valid.addValidation(new ArrayMemberValidation(new String[]{"0","1"}));

		valid.validate(errors);
	}

	/**
	 * 駐車場の状況のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validParkingSituation(List<ValidationFailure> errors){
		String label = "housing.input.parkingSituation";
		ValidationChain valid = new ValidationChain(label, this.parkingSituation);

		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "parkingSituation"));

		valid.validate(errors);
	}

	// 
	/**
	 * 駐車場空の有無のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validParkingEmpExist(List<ValidationFailure> errors){
		String label = "housing.input.parkingEmpExist";
		ValidationChain valid = new ValidationChain(label, this.parkingEmpExist);
		
		// パターンチェック
		valid.addValidation(new ArrayMemberValidation(new String[]{"0","1"}));

		valid.validate(errors);
	}

	/**
	 * 表示用駐車場情報のバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validDisplayParkingInfo(List<ValidationFailure> errors){
		String label = "housing.input.displayParkingInfo";
		ValidationChain valid = new ValidationChain(label, this.displayParkingInfo);

		// 最大桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}

	/**
	 * 窓の向きのバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>パターンチェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validWindowDirection(List<ValidationFailure> errors){
		String label = "housing.input.windowDirection";
		ValidationChain valid = new ValidationChain(label, this.windowDirection);
		
		// パターンチェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "direction"));

		valid.validate(errors);
	}

	/**
	 * 基本情報コメントのバリデーション<br/>
	 * <br/>
	 * <ul>
	 *   <li>最大文字列長チェック</li>
	 * </ul>
	 * @param errors エラーメッセージリスト
	 */
	protected void validBasicComment(List<ValidationFailure> errors){
		String label = "housing.input.basicComment";
		ValidationChain valid = new ValidationChain(label, this.basicComment);

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
	 * 物件番号を取得する。<br/>
	 * <br/>
	 * @return 物件番号
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * 物件番号を設定する。<br/>
	 * <br/>
	 * @param housingCd 物件番号
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	 * 表示用物件名を取得する。<br/>
	 * <br/>
	 * @return 表示用物件名
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * 表示用物件名を設定する。<br/>
	 * <br/>
	 * @param displayHousingName 表示用物件名
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * 表示用物件名（カナ）を取得する。<br/>
	 * <br/>
	 * @return 表示用物件名（カナ）
	 */
	public String getDisplayHousingNameKana() {
		return displayHousingNameKana;
	}

	/**
	 * 表示用物件名（カナ）を設定する。<br/>
	 * <br/>
	 * @param displayHousingNameKana 表示用物件名（カナ）
	 */
	public void setDisplayHousingNameKana(String displayHousingNameKana) {
		this.displayHousingNameKana = displayHousingNameKana;
	}

	/**
	 * 部屋番号を取得する。<br/>
	 * <br/>
	 * @return 部屋番号
	 */
	public String getRoomNo() {
		return roomNo;
	}

	/**
	 * 部屋番号を設定する。<br/>
	 * <br/>
	 * @param roomNo 部屋番号
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	/**
	 * システム建物CD 取得する。<br/>
	 * <br/>
	 * @return　システム建物CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * システム建物CD 設定する。<br/>
	 * <br/>
	 * @param sysBuildingCd　システム建物CD
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * 賃料・価格を取得する。<br/>
	 * <br/>
	 * @return 賃料・価格
	 */
	public String getPrice() {
		return price;
	}
	
	/**
	 * 賃料・価格を設定する。<br/>
	 * <br/>
	 * @param price 賃料・価格
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * 管理費を取得する。<br/>
	 * <br/>
	 * @return 管理費
	 */
	public String getUpkeep() {
		return upkeep;
	}

	/**
	 * 管理費を設定する。<br/>
	 * <br/>
	 * @param upkeep 管理費
	 */
	public void setUpkeep(String upkeep) {
		this.upkeep = upkeep;
	}
	
	/**
	 * 共益費を取得する。<br/>
	 * <br/>
	 * @return 共益費
	 */
	public String getCommonAreaFee() {
		return commonAreaFee;
	}
	
	/**
	 * 共益費を設定する。<br/>
	 * <br/>
	 * @param commonAreaFee　共益費
	 */
	public void setCommonAreaFee(String commonAreaFee) {
		this.commonAreaFee = commonAreaFee;
	}

	/**
	 * 修繕積立費を設定する。<br/>
	 * <br/>
	 * @return 修繕積立費
	 */
	public String getMenteFee() {
		return menteFee;
	}

	/**
	 * 修繕積立費を設定する。<br/>
	 * <br/>
	 * @param menteFee 修繕積立費
	 */
	public void setMenteFee(String menteFee) {
		this.menteFee = menteFee;
	}

	/**
	 * 敷金を取得する。<br/>
	 * <br/>
	 * @return 敷金
	 */
	public String getSecDeposit() {
		return secDeposit;
	}

	/**
	 * 敷金を設定する。<br/>
	 * <br/>
	 * @param secDeposit
	 */
	public void setSecDeposit(String secDeposit) {
		this.secDeposit = secDeposit;
	}

	/**
	 * 敷金単位を取得する。<br/>
	 * <br/>
	 * @return 敷金単位
	 */
	public String getSecDepositCrs() {
		return secDepositCrs;
	}

	/**
	 * 敷金単位を設定する。<br/>
	 * <br/>
	 * @param secDepositCrs 敷金単位
	 */
	public void setSecDepositCrs(String secDepositCrs) {
		this.secDepositCrs = secDepositCrs;
	}

	/**
	 * 保証金を取得する。<br/>
	 * <br/>
	 * @return 保証金
	 */
	public String getBondChrg() {
		return bondChrg;
	}

	/**
	 *  保証金を設定する。<br/>
	 *  <br/>
	 * @param bondChrg 保証金
	 */
	public void setBondChrg(String bondChrg) {
		this.bondChrg = bondChrg;
	}
	
	/**
	 * 保証金単位を取得する。<br/>
	 * <br/>
	 * @return 保証金単位
	 */
	public String getBondChrgCrs() {
		return bondChrgCrs;
	}

	/**
	 * 保証金単位を設定する。<br/>
	 * <br/>
	 * @param bondChrgCrs 保証金単位
	 */
	public void setBondChrgCrs(String bondChrgCrs) {
		this.bondChrgCrs = bondChrgCrs;
	}

	/**
	 * 敷引礼金区分を取得する。<br/>
	 * <br/>
	 * @return 敷引礼金区分
	 */
	public String getDepositDiv() {
		return depositDiv;
	}

	/**
	 * 敷引礼金区分を設定する。<br/>
	 * <br/>
	 * @param depositDiv　敷引礼金区分
	 */
	public void setDepositDiv(String depositDiv) {
		this.depositDiv = depositDiv;
	}

	/**
	 * 敷引礼金額を取得する。<br/>
	 * <br/>
	 * @return 敷引礼金額
	 */
	public String getDeposit() {
		return deposit;
	}

	/**
	 * 敷引礼金額を設定する。<br/>
	 * <br/>
	 * @param deposit　敷引礼金額
	 */
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	/**
	 * 敷引礼金単位を取得する。<br/>
	 * <br/>
	 * @return 敷引礼金単位
	 */
	public String getDepositCrs() {
		return depositCrs;
	}

	/**
	 * 敷引礼金単位を設定する。<br/>
	 * <br/>
	 * @param depositCrs 敷引礼金単位
	 */
	public void setDepositCrs(String depositCrs) {
		this.depositCrs = depositCrs;
	}
	
	/**
	 * 間取CDを取得する。<br/>
	 * <br/>
	 * @return 間取CD
	 */
	public String getLayoutCd() {
		return layoutCd;
	}

	/**
	 * 間取CDを設定する。<br/>
	 * @param layoutCd　間取CD
	 */
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}

	/**
	 * 間取詳細コメントを取得する。<br/>
	 * <br/>
	 * @return 間取詳細コメント
	 */
	public String getLayoutComment() {
		return layoutComment;
	}

	/**
	 * 間取詳細コメントを設定する。<br/>
	 * <br/>
	 * @param layoutComment 間取詳細コメント
	 */
	public void setLayoutComment(String layoutComment) {
		this.layoutComment = layoutComment;
	}

	/**
	 * 物件の階数を取得する。<br/>
	 * <br/>
	 * @return 物件の階数
	 */
	public String getFloorNo() {
		return floorNo;
	}

	/**
	 * 物件の階数を設定する。<br/>
	 * <br/>
	 * @param floorNo　物件の階数
	 */
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	/**
	 * 物件の階数コメントを取得する。<br/>
	 * <br/>
	 * @return 物件の階数コメント
	 */
	public String getFloorNoNote() {
		return floorNoNote;
	}

	/**
	 * 物件の階数コメントを取得する。<br/>
	 * <br/>
	 * @param floorNoNote 物件の階数コメント
	 */
	public void setFloorNoNote(String floorNoNote) {
		this.floorNoNote = floorNoNote;
	}
	
	/**
	 * 土地面積を取得する。<br/>
	 * <br/>
	 * @return 土地面積
	 */
	public String getLandArea() {
		return landArea;
	}

	/**
	 * 土地面積を取得する。<br/>
	 * <br/>
	 * @param landArea 土地面積
	 */
	public void setLandArea(String landArea) {
		this.landArea = landArea;
	}
	
	/**
	 * 土地面積_補足を取得する。<br/>
	 * <br/>
	 * @return 土地面積_補足
	 */
	public String getLandAreaMemo() {
		return landAreaMemo;
	}

	/**
	 * 土地面積_補足を設定する。<br/>
	 * <br/>
	 * @param landAreaMemo　土地面積_補足
	 */
	public void setLandAreaMemo(String landAreaMemo) {
		this.landAreaMemo = landAreaMemo;
	}

	/**
	 * 専有面積を取得する。<br/>
	 * <br/>
	 * @return 専有面積
	 */
	public String getPersonalArea() {
		return personalArea;
	}

	/**
	 * 専有面積を設定する。<br/>
	 * <br/>
	 * @param personalArea　専有面積
	 */
	public void setPersonalArea(String personalArea) {
		this.personalArea = personalArea;
	}
	
	/**
	 * 専有面積_補足を取得する。<br/>
	 * <br/>
	 * @return 専有面積_補足
	 */
	public String getPersonalAreaMemo() {
		return personalAreaMemo;
	}

	/**
	 * 専有面積_補足を取得する。<br/>
	 * <br/>
	 * @param personalAreaMemo 専有面積_補足
	 */
	public void setPersonalAreaMemo(String personalAreaMemo) {
		this.personalAreaMemo = personalAreaMemo;
	}

	/**
	 * 入居状態フラグを取得する。<br/>
	 * <br/>
	 * @return 入居状態フラグ
	 */
	public String getMoveinFlg() {
		return moveinFlg;
	}

	/**
	 * 入居状態フラグを取得する。<br/>
	 * <br/>
	 * @param moveinFlg 入居状態フラグ
	 */
	public void setMoveinFlg(String moveinFlg) {
		this.moveinFlg = moveinFlg;
	}

	/**
	 * 駐車場の状況を取得する。<br/>
	 * <br/>
	 * @return 駐車場の状況
	 */
	public String getParkingSituation() {
		return parkingSituation;
	}

	/**
	 * 駐車場の状況を設定する。<br/>
	 * <br/>
	 * @param parkingSituation　駐車場の状況
	 */
	public void setParkingSituation(String parkingSituation) {
		this.parkingSituation = parkingSituation;
	}

	/**
	 * 駐車場空の有無を取得する。<br/>
	 * <br/>
	 * @return 駐車場空の有無
	 */
	public String getParkingEmpExist() {
		return parkingEmpExist;
	}

	/**
	 * 駐車場空の有無を設定する。<br/>
	 * <br/>
	 * @param parkingEmpExist 駐車場空の有無
	 */
	public void setParkingEmpExist(String parkingEmpExist) {
		this.parkingEmpExist = parkingEmpExist;
	}

	/**
	 * 表示用駐車場情報を取得する。<br/>
	 * <br/>
	 * @return 表示用駐車場情報
	 */
	public String getDisplayParkingInfo() {
		return displayParkingInfo;
	}

	/**
	 * 表示用駐車場情報を設定する。<br/>
	 * <br/>
	 * @param displayParkingInfo 表示用駐車場情報
	 */
	public void setDisplayParkingInfo(String displayParkingInfo) {
		this.displayParkingInfo = displayParkingInfo;
	}
	
	/**
	 * 窓の向きを取得する。<br/>
	 * <br/>
	 * @return 窓の向き
	 */
	public String getWindowDirection() {
		return windowDirection;
	}
	
	/**
	 * 窓の向きを設定する。<br/>
	 * <br/>
	 * @param windowDirection　窓の向き
	 */
	public void setWindowDirection(String windowDirection) {
		this.windowDirection = windowDirection;
	}

	/**
	 * 基本情報コメントを取得する。<br/>
	 * <br/>
	 * @return 基本情報コメント
	 */
	public String getBasicComment() {
		return basicComment;
	}

	/**
	 * 基本情報コメントを設定する。<br/>
	 * <br/>
	 * @param basicComment　基本情報コメント
	 */
	public void setBasicComment(String basicComment) {
		this.basicComment = basicComment;
	}

}
