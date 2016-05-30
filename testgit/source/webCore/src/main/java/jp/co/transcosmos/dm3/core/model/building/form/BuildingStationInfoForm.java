package jp.co.transcosmos.dm3.core.model.building.form;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.building.BuildingManageImpl;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.dao.JoinResult;
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
 * 建物情報メンテナンスの入力パラメータ受取り用フォーム
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.09	新規作成
 *
 * 注意事項
 * バリデーション実行時のパラメータが通常と異なるので注意する事。
 *
 * </pre>
 */
public class BuildingStationInfoForm implements Validateable {
	
	/** command パラメータ */
	private String command;
	
	/** システム建物CD */
	private String sysBuildingCd;
	/** 所在地・都道府県CD */
	private String prefCd;
	/** 枝番 */
	private String divNo[];
	/** 表示順 */
	private String sortOrder[];
	/** 代表路線CD */
	private String defaultRouteCd[];
	/** 駅CD */
	private String stationCd[];
	/** 最寄り駅からの手段 */
	private String wayFromStation[];
	/** 駅からの距離 */
	private String distanceFromStation[];
	/** 駅からの徒歩時間 */
	private String timeFromStation[];
	/** バス会社名 */
	private String busCompany[];
	/** バスの所要時間 */
	private String busRequiredTime[];
	/** バス停名 */
	private String busStopName[];
	/** バス停からの徒歩時間 */
	private String timeFromBusStop[];
	/** 路線名 */
	private String routeName[];
	/** 駅名 */
	private String stationName[];
	
	
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	
	/**
	 * 継承用<br/>
	 * <br/>
	 */
	protected BuildingStationInfoForm(){
		super();
	}
	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected BuildingStationInfoForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
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
	 * 代表路線CDを取得する。<br/>
	 * <br/>
	 * @return 代表路線CD
	 */
	public String[] getDefaultRouteCd() {
		return defaultRouteCd;
	}
	/**
	 * 代表路線CDを設定する。<br/>
	 * <br/>
	 * @param defaultRouteCd 代表路線CD
	 */
	public void setDefaultRouteCd(String[] defaultRouteCd) {
		this.defaultRouteCd = defaultRouteCd;
	}
	/**
	 * 駅CDを取得する。<br/>
	 * <br/>
	 * @return 駅CD
	 */
	public String[] getStationCd() {
		return stationCd;
	}
	/**
	 * 駅CDを設定する。<br/>
	 * <br/>
	 * @param stationCd 駅CD
	 */
	public void setStationCd(String[] stationCd) {
		this.stationCd = stationCd;
	}


	/**
	 * 最寄り駅からの手段を取得する。<br/>
	 * <br/>
	 * @return 最寄り駅からの手段
	 */
	public String[] getWayFromStation() {
		return wayFromStation;
	}


	/**
	 * 最寄り駅からの手段を設定する。<br/>
	 * <br/>
	 * @param wayFromStation 最寄り駅からの手段
	 */
	public void setWayFromStation(String[] wayFromStation) {
		this.wayFromStation = wayFromStation;
	}


	/**
	 * 駅からの距離を取得する。<br/>
	 * <br/>
	 * @return 駅からの距離
	 */
	public String[] getDistanceFromStation() {
		return distanceFromStation;
	}


	/**
	 * 駅からの距離を設定する。<br/>
	 * <br/>
	 * @param distanceFromStation 駅からの距離
	 */
	public void setDistanceFromStation(String[] distanceFromStation) {
		this.distanceFromStation = distanceFromStation;
	}


	/**
	 * 駅からの徒歩時間を取得する。<br/>
	 * <br/>
	 * @return 駅からの徒歩時間
	 */
	public String[] getTimeFromStation() {
		return timeFromStation;
	}


	/**
	 * 駅からの徒歩時間を設定する。<br/>
	 * <br/>
	 * @param timeFromStation 駅からの徒歩時間
	 */
	public void setTimeFromStation(String[] timeFromStation) {
		this.timeFromStation = timeFromStation;
	}


	/**
	 * バス会社名を取得する。<br/>
	 * <br/>
	 * @return バス会社名
	 */
	public String[] getBusCompany() {
		return busCompany;
	}


	/**
	 * バス会社名を設定する。<br/>
	 * <br/>
	 * @param busCompany バス会社名
	 */
	public void setBusCompany(String[] busCompany) {
		this.busCompany = busCompany;
	}


	/**
	 * バスの所要時間を取得する。<br/>
	 * <br/>
	 * @return バスの所要時間
	 */
	public String[] getBusRequiredTime() {
		return busRequiredTime;
	}


	/**
	 * バスの所要時間を設定する。<br/>
	 * <br/>
	 * @param busRequiredTime バスの所要時間
	 */
	public void setBusRequiredTime(String[] busRequiredTime) {
		this.busRequiredTime = busRequiredTime;
	}


	/**
	 * バス停名を取得する。<br/>
	 * <br/>
	 * @return バス停名
	 */
	public String[] getBusStopName() {
		return busStopName;
	}


	/**
	 * バス停名を設定する。<br/>
	 * <br/>
	 * @param busStopName バス停名
	 */
	public void setBusStopName(String[] busStopName) {
		this.busStopName = busStopName;
	}


	/**
	 * バス停からの徒歩時間を取得する。<br/>
	 * <br/>
	 * @return バス停からの徒歩時間
	 */
	public String[] getTimeFromBusStop() {
		return timeFromBusStop;
	}


	/**
	 * バス停からの徒歩時間を設定する。<br/>
	 * <br/>
	 * @param timeFromBusStop バス停からの徒歩時間
	 */
	public void setTimeFromBusStop(String[] timeFromBusStop) {
		this.timeFromBusStop = timeFromBusStop;
	}


	/**
	 * 路線名を取得する。<br/>
	 * <br/>
	 * @return 路線名
	 */
	public String[] getRouteName() {
		return routeName;
	}


	/**
	 * 路線名を設定する。<br/>
	 * <br/>
	 * @param routeName 路線名
	 */
	public void setRouteName(String[] routeName) {
		this.routeName = routeName;
	}


	/**
	 * 駅名を取得する。<br/>
	 * <br/>
	 * @return 駅名
	 */
	public String[] getStationName() {
		return stationName;
	}


	/**
	 * 駅名を設定する。<br/>
	 * <br/>
	 * @param stationName 駅名
	 */
	public void setStationName(String[] stationName) {
		this.stationName = stationName;
	}

	/**
	 * 引数で渡された最寄り駅情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param information 値を設定するお知らせ情報のバリーオブジェクト
	 * @param length 最寄り駅のレコード数
	 * 
	 */
	public void copyToBuildingStationInfo(
			BuildingStationInfo[] buildingStationInfos, int length) {

		// 処理しているvoの番号
		int voIndex = 0;
		// 画面行数でルールする
		for (int i = 0;i < this.defaultRouteCd.length; i++) {
			// 路線を入力していない場合、登録しない
			if(StringValidateUtil.isEmpty(this.defaultRouteCd[i])) {
				continue;
			}
			buildingStationInfos[voIndex].setSysBuildingCd(this.sysBuildingCd);
			buildingStationInfos[voIndex].setDivNo(voIndex + 1);
			if (!StringValidateUtil.isEmpty(this.sortOrder[i])) {
				buildingStationInfos[voIndex].setSortOrder(Integer.valueOf(this.sortOrder[i]));
			}
			buildingStationInfos[voIndex].setDefaultRouteCd(this.defaultRouteCd[i]);
			buildingStationInfos[voIndex].setDefaultRouteName(this.routeName[i]);
			buildingStationInfos[voIndex].setStationCd(this.stationCd[i]);
			buildingStationInfos[voIndex].setStationName(this.stationName[i]);
			buildingStationInfos[voIndex].setWayFromStation(this.wayFromStation[i]);
			if (!StringValidateUtil.isEmpty(this.distanceFromStation[i])) {
				buildingStationInfos[voIndex].setDistanceFromStation(new BigDecimal(this.distanceFromStation[i]));
			}
			if (!StringValidateUtil.isEmpty(this.timeFromStation[i])) {
				buildingStationInfos[voIndex].setTimeFromStation(Integer.valueOf(this.timeFromStation[i]));
			}
			buildingStationInfos[voIndex].setBusCompany(this.busCompany[i]);
			if (!StringValidateUtil.isEmpty(this.busRequiredTime[i])) {
				buildingStationInfos[voIndex].setBusRequiredTime(Integer.valueOf(this.busRequiredTime[i]));
			}
			buildingStationInfos[voIndex].setBusStopName(this.busStopName[i]);
			if (!StringValidateUtil.isEmpty(this.timeFromBusStop[i])) {
				buildingStationInfos[voIndex].setTimeFromBusStop(Integer.valueOf(this.timeFromBusStop[i]));
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
	 * @param buildingStationInfoList List<JoinResult>　取得した最寄り駅情報リスト
	 * 
	 */
	public void setDefaultData(List<JoinResult> buildingStationInfoList) {

		// 配列初期化
		creatBuildingStationInfoForm(buildingStationInfoList.size());
		for (int i = 0; i < buildingStationInfoList.size(); i++) {
			BuildingStationInfo buildingStationInfo = (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get(BuildingManageImpl.BUILDING_STATION_INFO_ALIA);
			RouteMst routeMst = (RouteMst)buildingStationInfoList.get(i).getItems().get(BuildingManageImpl.ROUTE_MST);
			StationMst stationMst = (StationMst)buildingStationInfoList.get(i).getItems().get(BuildingManageImpl.STATION_MST);

			// 枝番
			if (buildingStationInfo.getDivNo() != null) {
				this.divNo[i] = String.valueOf(buildingStationInfo.getDivNo());
			}
			// 表示順
			if (buildingStationInfo.getSortOrder() != null) {
				this.sortOrder[i] = String.valueOf(buildingStationInfo.getSortOrder());
			}
			
			// 代表路線CD
			this.defaultRouteCd[i] = buildingStationInfo.getDefaultRouteCd();
			// 駅CD
			this.stationCd[i] = buildingStationInfo.getStationCd();
			// 最寄り駅からの手段
			this.wayFromStation[i] = buildingStationInfo.getWayFromStation();
			// 駅からの距離
			if (buildingStationInfo.getDistanceFromStation() != null) {
				this.distanceFromStation[i] = buildingStationInfo.getDistanceFromStation().toString();
			}
			// 駅からの徒歩時間
			if (buildingStationInfo.getTimeFromStation() != null) {
				this.timeFromStation[i] = String.valueOf(buildingStationInfo.getTimeFromStation());
			}
			// バス会社名
			this.busCompany[i] = buildingStationInfo.getBusCompany();
			// 駅からの徒歩時間
			if (buildingStationInfo.getBusRequiredTime() != null) {
				this.busRequiredTime[i] = String.valueOf(buildingStationInfo.getBusRequiredTime());
			}
			// バス停名
			this.busStopName[i] = buildingStationInfo.getBusStopName();
			// 駅からの徒歩時間
			if (buildingStationInfo.getTimeFromBusStop() != null) {
				this.timeFromBusStop[i] = String.valueOf(buildingStationInfo.getTimeFromBusStop());
			}
			// 路線名
			this.routeName[i] = routeMst.getRouteName();
			
			// 駅名
			this.stationName[i] = stationMst.getStationName();
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
		this.defaultRouteCd = new String[size];
		this.stationCd = new String[size];
		this.wayFromStation = new String[size];
		this.distanceFromStation = new String[size];
		this.timeFromStation = new String[size];
		this.busCompany = new String[size];
		this.busRequiredTime = new String[size];
		this.busStopName = new String[size];
		this.timeFromBusStop = new String[size];
		this.routeName = new String[size];
		this.stationName = new String[size];
		this.sortOrder = new String[size];
		
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
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();
        
		for (int i = 0; i < sortOrder.length; i++) {
	        // 路線入力チェック
			validDefaultRouteCd(errors, i);
	        // 駅入力チェック
			validStationCd(errors, i);
	        // 最寄り駅からの手段
			validWayFromStation(errors, i);
	        // 駅からの距離入力チェック
			validDistanceFromStation(errors, i);
	        // 駅からの徒歩時間入力チェック
			validTimeFromStation(errors, i);
	        // バス会社名入力チェック
			validBusCompany(errors, i);
	        // バスの所要時間入力チェック
			validBusRequiredTime(errors, i);
	        // バス停名入力チェック
			validBusStopName(errors, i);
	        // バス停からの徒歩時間入力チェック
			validTimeFromBusStop(errors, i);
	        // 表示順入力チェック
			validSortOrder(errors, i);
		}
        
        return (startSize == errors.size());
	}	
	
	/**
	 * 路線 バリデーション<br/>
	 * ・必須チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validDefaultRouteCd(List<ValidationFailure> errors, int i) {
        // 路線 入力チェック
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.defaultRouteCd", this.defaultRouteCd[i]);
		// 必須チェック
		if (!StringValidateUtil.isEmpty(this.stationCd[i])
				|| !StringValidateUtil.isEmpty(this.wayFromStation[i])
				|| !StringValidateUtil.isEmpty(this.distanceFromStation[i])
				|| !StringValidateUtil.isEmpty(this.timeFromStation[i])
				|| !StringValidateUtil.isEmpty(this.busCompany[i])
				|| !StringValidateUtil.isEmpty(this.busRequiredTime[i])
				|| !StringValidateUtil.isEmpty(this.busStopName[i])
				|| !StringValidateUtil.isEmpty(this.timeFromBusStop[i])
				|| !StringValidateUtil.isEmpty(this.sortOrder[i])) {
			valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(), i + 1));
		}
        valid.validate(errors);
	}
	
	/**
	 * 駅 バリデーション<br/>
	 * ・必須チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validStationCd(List<ValidationFailure> errors, int i) {
        // 駅 入力チェック
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.stationCd", this.stationCd[i]);
		// 必須チェック
		if (!StringValidateUtil.isEmpty(this.defaultRouteCd[i])
				|| !StringValidateUtil.isEmpty(this.wayFromStation[i])
				|| !StringValidateUtil.isEmpty(this.distanceFromStation[i])
				|| !StringValidateUtil.isEmpty(this.timeFromStation[i])
				|| !StringValidateUtil.isEmpty(this.busCompany[i])
				|| !StringValidateUtil.isEmpty(this.busRequiredTime[i])
				|| !StringValidateUtil.isEmpty(this.busStopName[i])
				|| !StringValidateUtil.isEmpty(this.timeFromBusStop[i])
				|| !StringValidateUtil.isEmpty(this.sortOrder[i])) {
			valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(), i + 1));
		}
        valid.validate(errors);
	}

	/**
	 * 駅からの距離 バリデーション<br/>
	 * ・DECIMALチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validDistanceFromStation(List<ValidationFailure> errors, int i) {
        // 建物番号入力チェック
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.distanceFromStation", this.distanceFromStation[i]);
        // DECIMALチェック
        int num = this.lengthUtils.getLength("buildingStationInfo.input.distanceFromStationNum", 7);
        int dec = this.lengthUtils.getLength("buildingStationInfo.input.distanceFromStationDec", 2);
        valid.addValidation(new LineAdapter(new DecimalValidation(num, dec), i + 1));
        valid.validate(errors);
	}
	
	
	/**
	 * 最寄り駅からの手段 バリデーション<br/>
	 * ・パターンチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validWayFromStation(List<ValidationFailure> errors, int i) {
        // 最寄り駅からの手段チェック
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.wayFromStation", this.wayFromStation[i]);
        // パターンチェック
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager,"buildingStationInfo_wayFromStation"));
        valid.validate(errors);
	}
	
	/**
	 * 駅からの徒歩時間 バリデーション<br/>
	 * ・桁数チェック
	 * ・整数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validTimeFromStation(List<ValidationFailure> errors, int i) {
        // 駅からの徒歩時間入力チェック
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.timeFromStation",this.timeFromStation[i]);
        // 桁数チェック
        int len = this.lengthUtils.getLength("buildingStationInfo.input.timeFromStation", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // 整数チェック
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
	}
	
	/**
	 * バス会社名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validBusCompany(List<ValidationFailure> errors, int i) {
        // バス会社名入力チェック
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.busCompany",this.busCompany[i]);
        // 桁数チェック
        int len = this.lengthUtils.getLength("buildingStationInfo.input.busCompany", 40);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        valid.validate(errors);
	}
	
	/**
	 * バスの所要時間 バリデーション<br/>
	 * ・桁数チェック
	 * ・整数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validBusRequiredTime(List<ValidationFailure> errors, int i) {
        // バスの所要時間入力チェック
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.busRequiredTime",this.busRequiredTime[i]);
        // 桁数チェック
        int len = this.lengthUtils.getLength("buildingStationInfo.input.busRequiredTime", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // 整数チェック
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
	}
	
	/**
	 * バス停名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validBusStopName(List<ValidationFailure> errors, int i) {
        // バス停名入力チェック
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.busStopName",this.busStopName[i]);
        // 桁数チェック
        int len = this.lengthUtils.getLength("buildingStationInfo.input.busStopName", 40);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        valid.validate(errors);
	}
	
	/**
	 * バス停からの徒歩時間 バリデーション<br/>
	 * ・桁数チェック
	 * ・整数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validTimeFromBusStop(List<ValidationFailure> errors, int i) {
        // バス停からの徒歩時間入力チェック
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.timeFromBusStop",this.timeFromBusStop[i]);
        // 桁数チェック
        int len = this.lengthUtils.getLength("buildingStationInfo.input.timeFromBusStop", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // 整数チェック
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
	}
	
	/**
	 * 表示順 バリデーション<br/>
	 * ・桁数チェック
	 * ・整数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validSortOrder(List<ValidationFailure> errors, int i) {
        // 表示順入力チェック
        ValidationChain valid = new ValidationChain("buildingStationInfo.input.sortOrder",this.sortOrder[i]);
        // 桁数チェック
        int len = this.lengthUtils.getLength("buildingStationInfo.input.sortOrder", 3);
        valid.addValidation(new LineAdapter(new MaxLengthValidation(len), i + 1));
        // 整数チェック
        valid.addValidation(new LineAdapter(new NumericValidation(), i + 1));
        valid.validate(errors);
	}
}
