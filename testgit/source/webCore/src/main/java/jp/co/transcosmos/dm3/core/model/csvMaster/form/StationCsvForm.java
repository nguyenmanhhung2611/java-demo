package jp.co.transcosmos.dm3.core.model.csvMaster.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.LengthValidator;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * ヴァル研が提供する駅情報CSV によるマスターデータ更新時に加工済データを管理するフォーム.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.06	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class StationCsvForm implements Validateable {

	/** 鉄道会社CD */
	private String rrCd;
	/** 鉄道会社表示順 （CSV ファイルには存在しない属性） */
	private Integer rrSortOrder;
	/** 路線CD */
	private String routeCd;
	/** 鉄道会社名 */
	private String rrName;
	/** 路線名括弧付き */
	private String routeNameFull;
	/** 路線名括弧付き、鉄道会社付き */
	private String routeNameRr;
	/** 路線表示順  （CSV ファイルには存在しない属性） */
	private Integer routeSortOrder;
	/** 駅CD */
	private String stationCd;
	/** 駅名括弧あり */
	private String stationNameFull;
	/** 都道府県CD */
	private String prefCd;
	/** 停車順1 */
	private String stationRouteDispOrder;
	/** 路線名 */
	private String routeName; 

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	
	
	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected StationCsvForm() {
		super();
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 */
	protected StationCsvForm(LengthValidationUtils lengthUtils){
		super();
		this.lengthUtils = lengthUtils;
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

		// 路線コード　半角英数7桁固定
		validRouteCd(errors);

		// 鉄道会社名　80文字以内
		validRrName(errors);

		// フル路線名　80文字以内
		validRouteNameFull(errors);

		// 鉄道会社付き路線名　80文字以内
		validRouteNameRr(errors);

		// 駅コード　半角英数5桁固定
		validStationCd(errors);

		// 駅名　80文字以内
		validStationNameFull(errors);

		// 駅・路線 並び順
		validStationRouteDispOrder(errors);

		// 都道府県コード　半角数字2桁固定
		validPrefCd(errors);

		return startSize == errors.size();
	}

	/**
	 * 路線コード バリデーション<br/>
	 * ・7桁固定チェック
	 * ・半角英数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validRouteCd(List<ValidationFailure> errors) {
		// 路線コード　半角英数7桁固定
		ValidationChain valid = new ValidationChain("stationCsv.routeCd", this.routeCd);
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("stationCsv.routeCd", 7)));
		valid.addValidation(new AlphanumericOnlyValidation());
		valid.validate(errors);
	}

	/**
	 * 鉄道会社名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validRrName(List<ValidationFailure> errors) {
		// 鉄道会社名　80文字以内
		ValidationChain valid = new ValidationChain("stationCsv.rrName", this.rrName);
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("stationCsv.rrName", 80)));
		valid.validate(errors);
	}

	/**
	 * フル路線名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validRouteNameFull(List<ValidationFailure> errors) {
		// フル路線名　80文字以内
		ValidationChain valid = new ValidationChain("stationCsv.routeNameFull", this.routeNameFull);
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("stationCsv.routeNameFull", 80)));
		valid.validate(errors);
	}

	/**
	 * 鉄道会社付き路線名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validRouteNameRr(List<ValidationFailure> errors) {
		// 鉄道会社付き路線名　80文字以内
		ValidationChain valid = new ValidationChain("stationCsv.routeNameRr", this.routeNameRr);
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("stationCsv.routeNameRr", 80)));
		valid.validate(errors);
	}

	/**
	 * 駅コード バリデーション<br/>
	 * ・5桁固定チェック
	 * ・半角英数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validStationCd(List<ValidationFailure> errors) {
		// 駅コード　半角英数5桁固定
		ValidationChain valid = new ValidationChain("stationCsv.stationCd", this.stationCd);
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("stationCsv.stationCd", 5)));
		valid.addValidation(new AlphanumericOnlyValidation());
		valid.validate(errors);
	}

	/**
	 *  駅名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validStationNameFull(List<ValidationFailure> errors) {
		// 駅名　80文字以内
		ValidationChain valid = new ValidationChain("stationCsv.stationName", this.stationNameFull);
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("stationCsv.stationName", 80)));
		valid.validate(errors);
	}

	/**
	 *  駅・路線 バリデーション<br/>
	 * ・桁数チェック
	 * ・半角数字チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validStationRouteDispOrder(List<ValidationFailure> errors) {
		// 駅・路線 並び順
		ValidationChain valid = new ValidationChain("stationCsv.srSortOrder", this.stationRouteDispOrder);
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("stationCsv.srSortOrder", 5)));
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 *  都道府県コード バリデーション<br/>
	 * ・2桁固定チェック
	 * ・半角数字チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validPrefCd(List<ValidationFailure> errors) {
		// 都道府県コード　半角数字2桁固定
		ValidationChain valid = new ValidationChain("stationCsv.prefCd", this.prefCd);
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("stationCsv.prefCd", 2)));
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}


	/**
	 * 鉄道会社CD を取得する。<br/>
	 * <br/>
	 * @return 鉄道会社CD
	 */
	public String getRrCd() {
		return rrCd;
	}

	/**
	 * 鉄道会社CD を設定する。<br/>
	 * <br/>
	 * @param rrCd
	 */
	public void setRrCd(String rrCd) {
		this.rrCd = rrCd;
	}

	/**
	 * 鉄道会社表示順を取得する。<br/>
	 * <br/>
	 * @return　鉄道会社表示順
	 */
	public Integer getRrSortOrder() {
		return rrSortOrder;
	}

	/**
	 * 鉄道会社表示順を設定する。<br/>
	 * <br/>
	 * @param rrSortOrder　鉄道会社表示順
	 */
	public void setRrSortOrder(Integer rrSortOrder) {
		this.rrSortOrder = rrSortOrder;
	}

	/**
	 * 路線CD を取得する。<br/>
	 * <br/>
	 * @return 路線CD
	 */
	public String getRouteCd() {
		return routeCd;
	}

	/**
	 * 路線CD を設定する。<br/>
	 * @param routeCd 路線CD
	 */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}

	/**
	 * 鉄道会社名を取得する。<br/>
	 * <br/>
	 * @return 鉄道会社名
	 */
	public String getRrName() {
		return rrName;
	}

	/**
	 * 鉄道会社名を設定する。<br/>
	 * <br/>
	 * @param rrName　鉄道会社名
	 */
	public void setRrName(String rrName) {
		this.rrName = rrName;
	}

	/**
	 * 路線名括弧付きを取得する。<br/>
	 * <br/>
	 * @return 路線名括弧付き
	 */
	public String getRouteNameFull() {
		return routeNameFull;
	}

	/**
	 * 路線名括弧付きを設定する。<br/>
	 * <br/>
	 * @param routeNameFull 路線名括弧付き
	 */
	public void setRouteNameFull(String routeNameFull) {
		this.routeNameFull = routeNameFull;
	}

	/**
	 * 「路線名括弧付き、鉄道会社付き」を取得する。<br/>
	 * <br/>
	 * @return 「路線名括弧付き、鉄道会社付き」
	 */
	public String getRouteNameRr() {
		return routeNameRr;
	}

	/**
	 * 「路線名括弧付き、鉄道会社付き」を設定する。<br/>
	 * <br/>
	 * @param routeNameRr 「路線名括弧付き、鉄道会社付き」
	 */
	public void setRouteNameRr(String routeNameRr) {
		this.routeNameRr = routeNameRr;
	}

	/**
	 * 路線表示順を取得する。<br/>
	 * <br/>
	 * @return 路線表示順
	 */
	public Integer getRouteSortOrder() {
		return routeSortOrder;
	}

	/**
	 * 路線表示順を設定する。<br/>
	 * <br/>
	 * @param routeSortOrder 路線表示順
	 */
	public void setRouteSortOrder(Integer routeSortOrder) {
		this.routeSortOrder = routeSortOrder;
	}

	/**
	 * 駅CD を取得する。<br/>
	 * <br/>
	 * @return 駅CD
	 */
	public String getStationCd() {
		return stationCd;
	}

	/**
	 * 駅CD を設定する。<br/>
	 * <br/>
	 * @param stationCd 駅CD
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	/**
	 * 「駅名括弧あり」を取得する。<br/>
	 * <br/>
	 * @return 「駅名括弧あり」
	 */
	public String getStationNameFull() {
		return stationNameFull;
	}

	/**
	 *  「駅名括弧あり」を設定する。<br/>
	 *  <br/>
	 * @param stationNameFull 「駅名括弧あり」
	 */
	public void setStationNameFull(String stationNameFull) {
		this.stationNameFull = stationNameFull;
	}

	/**
	 * 都道府県CD を取得する。<br/>
	 * <br/>
	 * @return 都道府県CD
	 */
	public String getPrefCd() {
		return prefCd;
	}

	/**
	 * 都道府県CD を設定する。<br/>
	 * <br/>
	 * @param prefCd 都道府県CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * 停車順1 を取得する。<br/>
	 * <br/>
	 * @return 停車順1
	 */
	public String getStationRouteDispOrder() {
		return stationRouteDispOrder;
	}

	/**
	 * 停車順1 を設定する。<br/>
	 * <br/>
	 * @param stationRouteDispOrder 停車順1
	 */
	public void setStationRouteDispOrder(String stationRouteDispOrder) {
		this.stationRouteDispOrder = stationRouteDispOrder;
	}

	/**
	 * 路線名を取得する。<br/>
	 * <br/>
	 * @return 路線名
	 */
	public String getRouteName() {
		return routeName;
	}

	/**
	 * 路線名を設定する。<br/>
	 * <br/>
	 * @param routeName 路線名
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

}
