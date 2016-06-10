package jp.co.transcosmos.dm3.core.model.building.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * 建物情報メンテナンスの入力パラメータ受取り用フォーム
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	新規作成
 *
 * 注意事項
 * フレームワークが提供する Validateable インターフェースは実装していない。
 * バリデーション実行時のパラメータが通常と異なるので注意する事。
 *
 * </pre>
 */
public class BuildingForm implements Validateable {

	private static final Log log = LogFactory.getLog(BuildingForm.class);
	
	/** command パラメータ */
	private String command;
	
	/** システム建物CD */
	private String sysBuildingCd;
	/** 建物番号 */
	private String buildingCd;
	/** 物件種類CD */
	private String housingKindCd;
	/** 建物構造CD */
	private String structCd;
	/** 表示用建物名 */
	private String displayBuildingName;
	/** 表示用建物名ふりがな */
	private String displayBuildingNameKana;
	/** 所在地・郵便番号 */
	private String zip;
	/** 所在地・都道府県CD */
	private String prefCd;
	/** 所在地・都道府県名 */
	private String prefName;
	/** 所在地・市区町村CD */
	private String addressCd;
	/** 所在地・市区町村名 */
	private String addressName;
	/** 所在地・町名番地 */
	private String addressOther1;
	/** 所在地・建物名その他  */
	private String addressOther2;
	/** 竣工年月 */
	private String compDate;
	/** 竣工旬 */
	private String compTenDays;
	/** 総階数 */
	private String totalFloors;
	
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	/**
	 * 継承用<br/>
	 * <br/>
	 */
	protected BuildingForm() {
		super();
	}
	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected BuildingForm(LengthValidationUtils lengthUtils) {
		super();
		this.lengthUtils = lengthUtils;
	}
	
	
	/**
	 * command パラメータを取得する。<br/>
	 * <br/>
	 * @return command パラメータ
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * command パラメータを設定する。<br/>
	 * <br/>
	 * @param command command パラメータ
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * システム建物CDを取得する。<br/>
	 * <br/>
	 * @return システム建物CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}
	/**
	 * システム建物CDを設定する。<br/>
	 * <br/>
	 * @param sysBuildingCd システム建物CD
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}
	/**
	 * 建物番号を取得する。<br/>
	 * <br/>
	 * @return 建物番号
	 */
	public String getBuildingCd() {
		return buildingCd;
	}
	/**
	 * 建物番号を設定する。<br/>
	 * <br/>
	 * @param buildingCd 建物番号
	 */
	public void setBuildingCd(String buildingCd) {
		this.buildingCd = buildingCd;
	}
	/**
	 * 物件種類CDを取得する。<br/>
	 * <br/>
	 * @return 物件種類CD
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}
	/**
	 * 物件種類CDを設定する。<br/>
	 * <br/>
	 * @param housingKindCd 物件種類CD
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}
	/**
	 * 表示用建物名を取得する。<br/>
	 * <br/>
	 * @return 表示用建物名
	 */
	public String getDisplayBuildingName() {
		return displayBuildingName;
	}
	/**
	 * 表示用建物名を設定する。<br/>
	 * <br/>
	 * @param displayBuildingName 表示用建物名
	 */
	public void setDisplayBuildingName(String displayBuildingName) {
		this.displayBuildingName = displayBuildingName;
	}
	/**
	 * 表示用建物名ふりがなを取得する。<br/>
	 * <br/>
	 * @return 表示用建物名ふりがな
	 */
	public String getDisplayBuildingNameKana() {
		return displayBuildingNameKana;
	}
	/**
	 * 表示用建物名ふりがなを設定する。<br/>
	 * <br/>
	 * @param displayBuildingNameKana 表示用建物名ふりがな
	 */
	public void setDisplayBuildingNameKana(String displayBuildingNameKana) {
		this.displayBuildingNameKana = displayBuildingNameKana;
	}
	/**
	 * 所在地・郵便番号を取得する。<br/>
	 * <br/>
	 * @return 所在地・郵便番号
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * 所在地・郵便番号を設定する。<br/>
	 * <br/>
	 * @param zip 所在地・郵便番号
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * 所在地・都道府県CDを取得する。<br/>
	 * <br/>
	 * @return 所在地・都道府県CD
	 */
	public String getPrefCd() {
		return prefCd;
	}
	/**
	 * 所在地・都道府県CDを設定する。<br/>
	 * <br/>
	 * @param prefCd 所在地・都道府県CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}
	/**
	 * 所在地・都道府県名を取得する。<br/>
	 * <br/>
	 * @return 所在地・都道府県名
	 */
	public String getPrefName() {
		return prefName;
	}
	/**
	 * 所在地・都道府県名を設定する。<br/>
	 * <br/>
	 * @param prefCd 所在地・都道府県名
	 */
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}
	/**
	 * 所在地・市区町村CDを取得する。<br/>
	 * <br/>
	 * @return 所在地・市区町村CD
	 */
	public String getAddressCd() {
		return addressCd;
	}
	/**
	 * 所在地・市区町村CDを設定する。<br/>
	 * <br/>
	 * @param addressCd 所在地・市区町村CD
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}
	/**
	 * 所在地・市区町村名を取得する。<br/>
	 * <br/>
	 * @return 所在地・市区町村名
	 */
	public String getAddressName() {
		return addressName;
	}
	/**
	 * 所在地・市区町村名を設定する。<br/>
	 * <br/>
	 * @param addressName 所在地・市区町村名
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	/**
	 * 所在地・町名番地を取得する。<br/>
	 * <br/>
	 * @return 所在地・町名番地
	 */
	public String getAddressOther1() {
		return addressOther1;
	}
	/**
	 * 所在地・町名番地を設定する。<br/>
	 * <br/>
	 * @param addressOther1 所在地・町名番地
	 */
	public void setAddressOther1(String addressOther1) {
		this.addressOther1 = addressOther1;
	}
	/**
	 * 所在地・建物名その他を取得する。<br/>
	 * <br/>
	 * @return 所在地・所在地・建物名その他
	 */
	public String getAddressOther2() {
		return addressOther2;
	}
	/**
	 * 所在地・建物名その他を設定する。<br/>
	 * <br/>
	 * @param addressOther2 所在地・建物名その他
	 */
	public void setAddressOther2(String addressOther2) {
		this.addressOther2 = addressOther2;
	}
	/**
	 * 建物構造CDを取得する。<br/>
	 * <br/>
	 * @return 建物構造CD
	 */
	public String getStructCd() {
		return structCd;
	}
	/**
	 * 建物構造CDを設定する。<br/>
	 * <br/>
	 * @param structCd 建物構造CD
	 */
	public void setStructCd(String structCd) {
		this.structCd = structCd;
	}
	/**
	 * 竣工年月を取得する。<br/>
	 * <br/>
	 * @return 竣工年月
	 */
	public String getCompDate() {
		return compDate;
	}
	/**
	 * 竣工年月を設定する。<br/>
	 * <br/>
	 * @param compDate 竣工年月
	 */
	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}
	/**
	 * 竣工旬を取得する。<br/>
	 * <br/>
	 * @return 竣工旬
	 */
	public String getCompTenDays() {
		return compTenDays;
	}
	/**
	 * 竣工旬を設定する。<br/>
	 * <br/>
	 * @param compTenDays 竣工旬
	 */
	public void setCompTenDays(String compTenDays) {
		this.compTenDays = compTenDays;
	}
	/**
	 * 総階数を取得する。<br/>
	 * <br/>
	 * @return 総階数
	 */
	public String getTotalFloors() {
		return totalFloors;
	}
	/**
	 * 総階数を設定する。<br/>
	 * <br/>
	 * @param totalFloors 総階数
	 */
	public void setTotalFloors(String totalFloors) {
		this.totalFloors = totalFloors;
	}
	
	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */
	public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();
        
        // 建物番号入力チェック
        validBuildingCd(errors);
        // 表示用建物名入力チェック
        validDisplayBuildingName(errors);
        // 表示用建物名ふりがな入力チェック
        validDisplayBuildingNameKana(errors);
        // 所在地・郵便番号入力チェック
        validZip(errors);
        // 所在地・都道府県入力チェック
        validPrefCd(errors);
        // 所在地・町名番地入力チェック
        validAddressOther1(errors);
        // 所在地・建物名その他入力チェック
        validAddressOther2(errors);
        // 竣工年月入力チェック
        validCompDate(errors);
        // 竣工旬入力チェック
        validCompTenDays(errors);
        // 総階数入力チェック
        validTotalFloors(errors);
        return (startSize == errors.size());
	}
	
	/**
	 * 建物番号 バリデーション<br/>
	 * ・桁数チェック
	 * ・半角英数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validBuildingCd(List<ValidationFailure> errors) {
        // 建物番号入力チェック
        ValidationChain valid = new ValidationChain("buildingInfo.input.buildingCd",this.buildingCd);
        // 桁数チェック
        int len = this.lengthUtils.getLength("buildingInfo.input.buildingCd", 20);
        valid.addValidation(new MaxLengthValidation(len));
        // 半角英数チェック
        valid.addValidation(new AlphanumericOnlyValidation());
        valid.validate(errors);

	}
	
	/**
	 * 表示用建物名 バリデーション<br/>
	 * ・必須チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validDisplayBuildingName(List<ValidationFailure> errors) {
		// 表示用建物名入力チェック
		ValidationChain valid = new ValidationChain(
				"buildingInfo.input.displayBuildingName",
				this.displayBuildingName);
		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 桁数チェック
		int len = this.lengthUtils.getLength(
				"buildingInfo.input.displayBuildingName", 40);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);
	}
	
	/**
	 * 表示用建物名ふりがな バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validDisplayBuildingNameKana(List<ValidationFailure> errors) {
		// 表示用建物名ふりがな入力チェック
		ValidationChain valid = new ValidationChain(
				"buildingInfo.input.displayBuildingNameKana",
				this.displayBuildingName);
		// 桁数チェック
		int len = this.lengthUtils.getLength(
				"buildingInfo.input.displayBuildingNameKana", 80);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);
	}

	/**
	 * 所在地・郵便番号 バリデーション<br/>
	 * ・桁数チェック
	 * ・数値チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validZip(List<ValidationFailure> errors) {
		// 所在地・郵便番号入力チェック
		ValidationChain valid = new ValidationChain("buildingInfo.input.zip", this.zip);
		// 桁数チェック
		int len = this.lengthUtils.getLength("buildingInfo.input.zip", 7);
		
		valid.addValidation(new MaxLengthValidation(len));
		// 数値チェック
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 * 所在地・都道府県 バリデーション<br/>
	 * ・必須チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validPrefCd(List<ValidationFailure> errors) {
		// 所在地・郵便番号入力チェック
		ValidationChain valid = new ValidationChain(
				"buildingInfo.input.prefCd",
				this.prefCd);
		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		valid.validate(errors);
	}
	
	/**
	 * 所在地・町名番地 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validAddressOther1(List<ValidationFailure> errors) {
		// 所在地・町名番地入力チェック
		ValidationChain valid = new ValidationChain("buildingInfo.input.addressOther1", this.addressOther1);
		// 桁数チェック
		int len = this.lengthUtils.getLength("buildingInfo.input.addressOther1", 50);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);
	}
	
	/**
	 * 所在地・建物名その他 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validAddressOther2(List<ValidationFailure> errors) {
		// 所在地・建物名その他入力チェック
		ValidationChain valid = new ValidationChain("buildingInfo.input.addressOther2", this.addressOther2);
		// 桁数チェック
		int len = this.lengthUtils.getLength("buildingInfo.input.addressOther2", 50);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);
	}
	
	/**
	 * 所在地・竣工年月 バリデーション<br/>
	 * ・竣工旬が入力された場合は必須
	 * ・日付書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validCompDate(List<ValidationFailure> errors) {

		// 所在地・竣工年月入力チェック
		ValidationChain valid = new ValidationChain("buildingInfo.input.compDate", this.compDate);

		// 竣工旬が入力された場合は必須
		if (!StringValidateUtil.isEmpty(this.compTenDays)){
			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// 日付書式チェック
		valid.addValidation(new ValidDateValidation("yyyy/MM"));
		valid.validate(errors);
	}

	/**
	 * 竣工旬 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validCompTenDays(List<ValidationFailure> errors) {
		// 竣工旬入力チェック
		ValidationChain valid = new ValidationChain("buildingInfo.input.compTenDays", this.compTenDays);
		// 桁数チェック
		int len = this.lengthUtils.getLength("buildingInfo.input.compTenDays", 10);
		valid.addValidation(new MaxLengthValidation(len));
		valid.validate(errors);
	}
	
	/**
	 * 総階数 バリデーション<br/>
	 * ・桁数チェック
	 * ・数値チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validTotalFloors(List<ValidationFailure> errors) {
		// 総階数入力チェック
		ValidationChain valid = new ValidationChain("buildingInfo.input.totalFloors", this.totalFloors);
		// 桁数チェック
		int len = this.lengthUtils.getLength("buildingInfo.input.totalFloors", 3);
		valid.addValidation(new MaxLengthValidation(len));
		// 数値チェック
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 * 引数で渡された建物基本情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param building 値を設定する建物基本情報のバリーオブジェクト
	 * 
	 */
	public void copyToBuildingInfo(BuildingInfo building, String editUserId) {

		// システム建物CDを設定
		if (!StringValidateUtil.isEmpty(this.sysBuildingCd)) {
			building.setSysBuildingCd(this.sysBuildingCd);
		}
		
		// 建物番号を設定	
		building.setBuildingCd(this.buildingCd);

		// 物件種類CDを設定
		building.setHousingKindCd(this.housingKindCd);
		
		// 建物構造CDを設定
		building.setStructCd(this.structCd);
		
		// 表示用建物名を設定
		building.setDisplayBuildingName(this.displayBuildingName);
		
		// 表示用建物名ふりがなを設定
		building.setDisplayBuildingNameKana(this.displayBuildingNameKana);
		
		// 所在地・郵便番号を設定
		building.setZip(this.zip);
		
		// 所在地・都道府県CDを設定
		building.setPrefCd(this.prefCd);
		
		// 所在地・市区町村CDを設定
		building.setAddressCd(this.addressCd);
		
		// 所在地・市区町村名を設定
		building.setAddressName(this.addressName);
		
		// 所在地・町名番地を設定
		building.setAddressOther1(this.addressOther1);
		
		// 所在地・建物名その他を設定
		building.setAddressOther2(this.addressOther2);
		
		// 竣工年月を設定 （日は 01固定）
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		if (!StringValidateUtil.isEmpty(this.compDate)) {
			try {
				building.setCompDate(sdf.parse(this.compDate + "/01"));
			} catch (ParseException e) {
				// バリデーションで安全性を担保しているので、このタイミングでエラーが発生する事は無いが、
				// 一応、警告をログ出力する。
				log.warn("date format error. (" + this.compDate + ")");
				building.setCompDate(null);
			}
		} else {
			building.setCompDate(null);
		}

		
		// 竣工旬を設定
		building.setCompTenDays(this.compTenDays);
		
		// 総階数を設定
		if (!StringValidateUtil.isEmpty(this.totalFloors)) {
			building.setTotalFloors(Integer.valueOf(this.totalFloors));
		} else {
			building.setTotalFloors(null);
		}

		// 更新日付を設定
		building.setUpdDate(new Date());;

		// 更新担当者を設定
		building.setUpdUserId(editUserId);

	}
	
	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 * @param building Building　を実装した、建物情報管理用バリーオブジェクト
	 * @param prefMst PrefMst　を実装した、都道府県マスタ管理用バリーオブジェクト
	 * 
	 */
	public void setDefaultData(BuildingInfo building, PrefMst prefMst) {

		// システム建物CD
		this.sysBuildingCd = building.getSysBuildingCd();
		// 建物番号 を設定
		this.buildingCd = building.getBuildingCd();
		// 物件種類CD
		this.housingKindCd = building.getHousingKindCd();
		// 建物構造CD
		this.structCd = building.getStructCd();
		// 表示用建物名
		this.displayBuildingName = building.getDisplayBuildingName();
		// 表示用建物名ふりがな 
		this.displayBuildingNameKana = building.getDisplayBuildingNameKana();
		// 所在地・郵便番号
		this.zip = building.getZip();
		// 所在地・都道府県CD
		this.prefCd = building.getPrefCd();
		// 所在地・都道府県名
		this.prefName = prefMst.getPrefName();
		// 所在地・市区町村CD
		this.addressCd = building.getAddressCd();
		// 所在地・市区町村名
		this.addressName = building.getAddressName();
		// 所在地・町名番地
		this.addressOther1 = building.getAddressOther1();
		// 所在地・建物名その他
		this.addressOther2 = building.getAddressOther2();
		// 竣工年月
		if (building.getCompDate() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM"); 
			this.compDate = formatter.format(building.getCompDate());
		}
		// 竣工旬
		this.compTenDays = building.getCompTenDays();
		// 総階数
		if (building.getTotalFloors() != null) {
			this.totalFloors = String.valueOf(building.getTotalFloors());
		}
		
	}
}
