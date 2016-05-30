package jp.co.transcosmos.dm3.core.model.building.form;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.BuildingManageImpl;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.DecimalValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * 建物ランドマーク情報の入力パラメータ受取り用フォーム
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.17	新規作成
 *
 * 注意事項
 * バリデーション実行時のパラメータが通常と異なるので注意する事。
 *
 * </pre>
 */
public class BuildingLandmarkForm implements Validateable {
	
	/** command パラメータ */
	private String command;
	
	/** システム建物CD */
	private String sysBuildingCd;
	/** 建物番号 */
	private String buildingCd;
	/** 建物名称 */
	private String displayBuildingName;
	/** 枝番 */
	private String divNo[];
	/** ランドマークの種類 */
	private String landmarkType[];
	/** ランドマークからの手段 */
	private String wayFromLandmark[];
	/** 表示順 */
	private String sortOrder[];
	/** ランドマーク名 */
	private String landmarkName[];
	/** ランドマークからの徒歩時間 */
	private String timeFromLandmark[];
	/** ランドマークからの距離 */
	private String distanceFromLandmark[];
	
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;
	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	
	/**
	 * 継承用<br/>
	 * <br/>
	 */
	protected BuildingLandmarkForm() {
		super();
	}
	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected BuildingLandmarkForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
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
	 * 枝番を取得する。<br/>
	 * <br/>
	 * @return 枝番
	 */
	public String[] getDivNo() {
		return divNo;
	}

	/**
	 * 枝番を設定する。<br/>
	 * <br/>
	 * @param divNo 枝番
	 */
	public void setDivNo(String[] divNo) {
		this.divNo = divNo;
	}

	/**
	 * ランドマークの種類を取得する。<br/>
	 * <br/>
	 * @return ランドマークの種類
	 */
	public String[] getLandmarkType() {
		return landmarkType;
	}

	/**
	 * ランドマークの種類を設定する。<br/>
	 * <br/>
	 * @param landmarkType ランドマークの種類
	 */
	public void setLandmarkType(String[] landmarkType) {
		this.landmarkType = landmarkType;
	}

	/**
	 * ランドマークからの手段を取得する。<br/>
	 * <br/>
	 * @return ランドマークからの手段
	 */
	public String[] getWayFromLandmark() {
		return wayFromLandmark;
	}

	/**
	 * ランドマークからの手段を設定する。<br/>
	 * <br/>
	 * @param wayFromLandmark ランドマークからの手段
	 */
	public void setWayFromLandmark(String[] wayFromLandmark) {
		this.wayFromLandmark = wayFromLandmark;
	}

	/**
	 * 表示順を取得する。<br/>
	 * <br/>
	 * @return 表示順
	 */
	public String[] getSortOrder() {
		return sortOrder;
	}

	/**
	 * 表示順を設定する。<br/>
	 * <br/>
	 * @param sortOrder 表示順
	 */
	public void setSortOrder(String[] sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * ランドマーク名を取得する。<br/>
	 * <br/>
	 * @return ランドマーク名
	 */
	public String[] getLandmarkName() {
		return landmarkName;
	}

	/**
	 * ランドマーク名を設定する。<br/>
	 * <br/>
	 * @param landmarkName ランドマーク名
	 */
	public void setLandmarkName(String[] landmarkName) {
		this.landmarkName = landmarkName;
	}

	/**
	 * ランドマークからの徒歩時間を取得する。<br/>
	 * <br/>
	 * @return ランドマークからの徒歩時間
	 */
	public String[] getTimeFromLandmark() {
		return timeFromLandmark;
	}

	/**
	 * ランドマークからの徒歩時間を設定する。<br/>
	 * <br/>
	 * @param timeFromLandmark ランドマークからの徒歩時間
	 */
	public void setTimeFromLandmark(String[] timeFromLandmark) {
		this.timeFromLandmark = timeFromLandmark;
	}

	/**
	 * ランドマークからの距離を取得する。<br/>
	 * <br/>
	 * @return ランドマークからの距離
	 */
	public String[] getDistanceFromLandmark() {
		return distanceFromLandmark;
	}

	/**
	 * ランドマークからの距離を設定する。<br/>
	 * <br/>
	 * @param distanceFromLandmark ランドマークからの距離
	 */
	public void setDistanceFromLandmark(String[] distanceFromLandmark) {
		this.distanceFromLandmark = distanceFromLandmark;
	}
	
	/**
	 * 引数で渡された建物ランドマーク情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param buildingLandmark[] 値を設定する建物ランドマーク情報のバリーオブジェクト
	 * @param length 建物ランドマークのレコード数
	 * 
	 */
	public void copyToBuildingLandmark(BuildingLandmark[] buildingLandmarks, int length) {

		// 処理しているvoの番号
		int voIndex = 0;
		for (int i = 0; i < this.landmarkType.length; i++) {
			// 地域名を入力していない場合、登録しない
			if(StringValidateUtil.isEmpty(this.landmarkName[i])) {
				continue;
			}
			buildingLandmarks[voIndex].setSysBuildingCd(this.sysBuildingCd);
			buildingLandmarks[voIndex].setDivNo(voIndex + 1);
			buildingLandmarks[voIndex].setLandmarkType(this.landmarkType[i]);
			buildingLandmarks[voIndex].setWayFromLandmark(this.wayFromLandmark[i]);
			if (!StringValidateUtil.isEmpty(this.sortOrder[i])) {
				buildingLandmarks[voIndex].setSortOrder(Integer.valueOf(this.sortOrder[i]));
			}
			buildingLandmarks[voIndex].setLandmarkName(this.landmarkName[i]);
			if (!StringValidateUtil.isEmpty(this.timeFromLandmark[i])) {
				buildingLandmarks[voIndex].setTimeFromLandmark(Integer.valueOf(this.timeFromLandmark[i]));
			}
			if (!StringValidateUtil.isEmpty(this.distanceFromLandmark[i])) {
				buildingLandmarks[voIndex].setDistanceFromLandmark(new BigDecimal(this.distanceFromLandmark[i]));
			}
			
			// 全て入力した行は処理終了
			if (voIndex == length) {
				break;
			}
			voIndex++;
			
		}
		
	}

	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 * @param building Building　取得した建物情報リスト
	 * 
	 */
	public void setDefaultData(Building building) {
		
		// 地域情報リストを取得する
		List<BuildingLandmark> buildingLandmarkList = building.getBuildingLandmarkList();
		
        // 物件番号を設定する
		this.buildingCd = ((BuildingInfo) building.getBuildingInfo()
				.getItems().get(BuildingManageImpl.BUILDING_INFO_ALIA))
				.getBuildingCd();
		// 物件名称を設定する
		this.displayBuildingName = ((BuildingInfo) building.getBuildingInfo()
				.getItems().get(BuildingManageImpl.BUILDING_INFO_ALIA))
				.getDisplayBuildingName();
		// 配列初期化
		creatBuildingStationInfoForm(buildingLandmarkList.size());
		for(int i = 0;i<buildingLandmarkList.size();i ++) {
			BuildingLandmark buildingLandmark = buildingLandmarkList.get(i);
			// 枝番
			if (buildingLandmark.getDivNo() != null) {
				this.divNo[i] = String.valueOf(buildingLandmark.getDivNo());
			}
			// ランドマークの種類
			this.landmarkType[i] = buildingLandmark.getLandmarkType();
			// ランドマークからの手段
			this.wayFromLandmark[i] = buildingLandmark.getWayFromLandmark();
			// 表示順
			if (buildingLandmark.getSortOrder() != null) {
				this.sortOrder[i] = String.valueOf(buildingLandmark.getSortOrder());
			}
			// ランドマーク名
			this.landmarkName[i] = buildingLandmark.getLandmarkName();
			// ランドマークからの徒歩時間
			if (buildingLandmark.getTimeFromLandmark() != null) {
				this.timeFromLandmark[i] = String.valueOf(buildingLandmark.getTimeFromLandmark());
			}
			// ランドマークからの距離
			if (buildingLandmark.getDistanceFromLandmark() != null) {
				this.distanceFromLandmark[i] = buildingLandmark.getDistanceFromLandmark().toString();
			}
		}

	}
	
	/**
	 * 渡されたサイズで Formの配列を設定する。<br/>
	 * <br/>
	 * @param size int　配列のサイズ
	 * 
	 */
	protected void creatBuildingStationInfoForm(int size) {
		this.divNo = new String[size];
		this.landmarkType = new String[size];
		this.wayFromLandmark = new String[size];
		this.sortOrder = new String[size];
		this.landmarkName = new String[size];
		this.timeFromLandmark = new String[size];
		this.distanceFromLandmark = new String[size];
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
	 * 建物名称を取得する。<br/>
	 * <br/>
	 * @return 建物名称
	 */
	public String getDisplayBuildingName() {
		return displayBuildingName;
	}

	/**
	 * 建物名称を設定する。<br/>
	 * <br/>
	 * @param displayBuildingName 建物名称
	 */
	public void setDisplayBuildingName(String displayBuildingName) {
		this.displayBuildingName = displayBuildingName;
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
        
		for (int i = 0; i < landmarkType.length; i++) {
	        // ランドマーク名入力チェック
			validLandmarkName(errors, i);
	        // ランドマークからの手段入力チェック
			validWayFromLandmark(errors, i);
	        // ランドマークからの徒歩時間
			validTimeFromLandmark(errors, i);
	        // ランドマークからの距離入力チェック
			validDistanceFromLandmark(errors, i);
	        // 表示順入力チェック
			validSortOrder(errors, i);
		}
        
        return (startSize == errors.size());
	}
	
	/**
	 * ランドマーク名 バリデーション<br/>
	 * ・必須チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validLandmarkName(List<ValidationFailure> errors, int i) {
        // ランドマーク名 入力チェック
        ValidationChain valid = new ValidationChain("buildingLandmark.input.landmarkName", this.landmarkName[i]);
		// 必須チェック
		if (!StringValidateUtil.isEmpty(this.wayFromLandmark[i])
				|| !StringValidateUtil.isEmpty(this.timeFromLandmark[i])
				|| !StringValidateUtil.isEmpty(this.distanceFromLandmark[i])
				|| !StringValidateUtil.isEmpty(this.sortOrder[i])) {
			valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(), i + 1));
		}
        // 桁数チェック
        int len = this.lengthUtils.getLength("buildingLandmark.input.landmarkName", 80);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        valid.validate(errors);
	}

	/**
	 * ランドマークからの手段 バリデーション<br/>
	 * ・必須チェック
	 * ・パターンチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validWayFromLandmark(List<ValidationFailure> errors, int i) {
        // ランドマークからの手段 入力チェック
        ValidationChain valid = new ValidationChain("buildingLandmark.input.wayFromLandmark", this.wayFromLandmark[i]);
		// 必須チェック
		if (!StringValidateUtil.isEmpty(this.landmarkName[i])
				|| !StringValidateUtil.isEmpty(this.timeFromLandmark[i])
				|| !StringValidateUtil.isEmpty(this.distanceFromLandmark[i])
				|| !StringValidateUtil.isEmpty(this.sortOrder[i])) {
			valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(), i + 1));
	        // パターンチェック
	        valid.addValidation(new CodeLookupValidation(this.codeLookupManager,"buildingStationInfo_wayFromStation"));
		}
        valid.validate(errors);
	}
	
	/**
	 * ランドマークからの徒歩時間 バリデーション<br/>
	 * ・桁数チェック
	 * ・整数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validTimeFromLandmark(List<ValidationFailure> errors, int i) {
        // ランドマークからの徒歩時間 入力チェック
        ValidationChain valid = new ValidationChain("buildingLandmark.input.timeFromLandmark", this.timeFromLandmark[i]);
        // 桁数チェック
        int len = this.lengthUtils.getLength("buildingLandmark.input.timeFromLandmark", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // 整数チェック
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
       
	}
	
	/**
	 * ランドマークからの距離 バリデーション<br/>
	 * ・DECIMALチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validDistanceFromLandmark(List<ValidationFailure> errors, int i) {
        // ランドマークからの距離 入力チェック
        ValidationChain valid = new ValidationChain("buildingLandmark.input.distanceFromLandmark", this.distanceFromLandmark[i]);
        // DECIMALチェック
        int num = this.lengthUtils.getLength("buildingLandmark.input.distanceFromLandmarkNum", 7);
        int dec = this.lengthUtils.getLength("buildingLandmark.input.distanceFromLandmarkDec", 2);
        valid.addValidation(new LineAdapter(new DecimalValidation(num, dec), i + 1));
        valid.validate(errors);
        
	}
	
	/**
	 * 表示順入力 バリデーション<br/>
	 * ・桁数チェック
	 * ・整数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validSortOrder(List<ValidationFailure> errors, int i) {
        // 表示順入力 入力チェック
        ValidationChain valid = new ValidationChain("buildingLandmark.input.sortOrder", this.sortOrder[i]);
        // 桁数チェック
        int len = this.lengthUtils.getLength("buildingLandmark.input.sortOrder", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // 整数チェック
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
       
	}
	
}
