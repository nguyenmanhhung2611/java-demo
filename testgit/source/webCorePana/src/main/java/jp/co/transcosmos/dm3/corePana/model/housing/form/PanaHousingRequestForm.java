package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.transcosmos.dm3.adminCore.request.form.HousingRequestForm;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.util.PanaCommonUtil;
import jp.co.transcosmos.dm3.corePana.validation.DataCheckValidation;
import jp.co.transcosmos.dm3.corePana.validation.PriceReformCheckValidation;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;
/**
 * 物件リクエスト入力、確認、完了画面の入力パラメータ受取り用フォーム.
 * <p>
 *
 * <pre>
 * 担当者      修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS     2015.04.22  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */

public class PanaHousingRequestForm extends HousingRequestForm implements Validateable {

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;
	
	private jp.co.transcosmos.dm3.utils.StringUtils DateUtils;

	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	PanaHousingRequestForm(CodeLookupManager codeLookupManager) {
		super();
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	PanaHousingRequestForm() {
		super();
	}

	/** コマンド */
	private String command;

	/** 処理モード */
	private String model;

	/** 中古マンションの予算下限 */
	private String priceLowerMansion;

	/** 中古マンションの予算上限 */
	private String priceUpperMansion;

	/** 中古戸建の予算下限 */
	private String priceLowerHouse;

	/** 中古戸建の予算上限 */
	private String priceUpperHouse;

	/** 中古土地の予算下限 */
	private String priceLowerLand;

	/** 中古土地の予算上限 */
	private String priceUpperLand;

	/** 専有面積下限 */
	private String personalAreaLowerMansion;

	/** 専有面積上限 */
	private String personalAreaUpperMansion;

	/** 中古戸建の建物面積下限 */
	private String buildingAreaLowerHouse;

	/** 中古戸建の建物面積上限 */
	private String buildingAreaUpperHouse;

	/** 中古土地の建物面積下限 */
	private String buildingAreaLowerLand;

	/** 中古土地の建物面積上限 */
	private String buildingAreaUpperLand;

	/** 中古戸建の土地面積下限 */
	private String landAreaLowerHouse;

	/** 中古戸建の土地面積上限 */
	private String landAreaUpperHouse;

	/** 中古土地の土地面積下限 */
	private String landAreaLowerLand;

	/** 中古土地の土地面積上限 */
	private String landAreaUpperLand;

	/** 中古マンションの間取り */
	private String[] layoutCdMansion;

	/** 中古戸建の間取り */
	private String[] layoutCdHouse;

	/** 中古マンションの築年数 */
	private String compDateMansion;

	/** 中古戸建の築年数 */
	private String compDateHouse;

	/** 中古マンションのおすすめのポイント */
	private String[] iconCdMansion;

	/** 中古戸建のおすすめのポイント */
	private String[] iconCdHouse;

	/** 中古マンションのリフォーム価格込みで検索する */
	private String reformCheckMansion;

	/** 中古戸建のリフォーム価格込みで検索する */
	private String reformCheckHouse;

	/** Urlパターン */
	private String urlPattern;
	
	
	private String hopeRequestTest;

	public String getHopeRequestTest() {
		return hopeRequestTest;
	}

	public void setHopeRequestTest(String hopeRequestTest) {
		this.hopeRequestTest = hopeRequestTest;
	}
	
	private String startDate;
	private String endDate;
	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return urlPattern
	 */
	public String getUrlPattern() {
		return urlPattern;
	}

	/**
	 * @param urlPattern
	 *            セットする urlPattern
	 */
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	/**
	 * コメントを取得する。<br/>
	 * コメントの値は、フレームワークの URL マッピングで使用する コメント クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return コメント
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * コメントを設定する。<br/>
	 * コメントの値は、フレームワークの URL マッピングで使用する コメント クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @param command
	 *            コメント
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * 処理モード を取得する。<br/>
	 * 処理モード の値は、フレームワークの URL マッピングで使用する 処理モード クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 処理モード
	 */
	public String getModel() {
		return model;
	}

	/**
	 * 処理モード を設定する。<BR/>
	 * 処理モード の値は、フレームワークの URL マッピングで使用する 処理モード クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM prefCd 処理モード
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 中古マンションの予算下限 を取得する。<br/>
	 * 中古マンションの予算下限 の値は、フレームワークの URL マッピングで使用する 中古マンションの予算下限 クラスの切り替えにも使用される。
	 * <br/>
	 * <br/>
	 *
	 * @return 中古マンションの予算下限
	 */
	public String getPriceLowerMansion() {
		return priceLowerMansion;
	}

	/**
	 * 中古マンションの予算下限 を設定する。<BR/>
	 * 中古マンションの予算下限 の値は、フレームワークの URL マッピングで使用する 中古マンションの予算下限 クラスの切り替えにも使用される。
	 * <BR/>
	 * <BR/>
	 *
	 * @PARAM priceLowerMansion 中古マンションの予算下限
	 */
	public void setPriceLowerMansion(String priceLowerMansion) {
		this.priceLowerMansion = priceLowerMansion;
	}

	/**
	 * 中古マンションの予算上限 を取得する。<br/>
	 * 中古マンションの予算上限 の値は、フレームワークの URL マッピングで使用する 中古マンションの予算上限 クラスの切り替えにも使用される。
	 * <br/>
	 * <br/>
	 *
	 * @return 中古マンションの予算上限
	 */
	public String getPriceUpperMansion() {
		return priceUpperMansion;
	}

	/**
	 * 中古マンションの予算上限 を設定する。<BR/>
	 * 中古マンションの予算上限 の値は、フレームワークの URL マッピングで使用する 中古マンションの予算上限 クラスの切り替えにも使用される。
	 * <BR/>
	 * <BR/>
	 *
	 * @PARAM priceUpperMansion 中古マンションの予算上限
	 */
	public void setPriceUpperMansion(String priceUpperMansion) {
		this.priceUpperMansion = priceUpperMansion;
	}

	/**
	 * 中古戸建の予算下限 を取得する。<br/>
	 * 中古戸建の予算下限 の値は、フレームワークの URL マッピングで使用する 中古戸建の予算下限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古戸建の予算下限
	 */
	public String getPriceLowerHouse() {
		return priceLowerHouse;
	}

	/**
	 * 中古戸建の予算下限 を設定する。<BR/>
	 * 中古戸建の予算下限 の値は、フレームワークの URL マッピングで使用する 中古戸建の予算下限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM priceLowerHouse 中古戸建の予算下限
	 */
	public void setPriceLowerHouse(String priceLowerHouse) {
		this.priceLowerHouse = priceLowerHouse;
	}

	/**
	 * 中古戸建の予算上限 を取得する。<br/>
	 * 中古戸建の予算上限 の値は、フレームワークの URL マッピングで使用する 中古戸建の予算上限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古戸建の予算上限
	 */
	public String getPriceUpperHouse() {
		return priceUpperHouse;
	}

	/**
	 * 中古戸建の予算上限 を設定する。<BR/>
	 * 中古戸建の予算上限 の値は、フレームワークの URL マッピングで使用する 中古戸建の予算上限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM priceUpperHouse 中古戸建の予算上限
	 */
	public void setPriceUpperHouse(String priceUpperHouse) {
		this.priceUpperHouse = priceUpperHouse;
	}

	/**
	 * 中古土地の予算下限 を取得する。<br/>
	 * 中古土地の予算下限 の値は、フレームワークの URL マッピングで使用する 中古土地の予算下限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古土地の予算下限
	 */
	public String getPriceLowerLand() {
		return priceLowerLand;
	}

	/**
	 * 中古土地の予算下限 を設定する。<BR/>
	 * 中古土地の予算下限 の値は、フレームワークの URL マッピングで使用する 中古土地の予算下限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM priceLowerLand 中古土地の予算下限
	 */
	public void setPriceLowerLand(String priceLowerLand) {
		this.priceLowerLand = priceLowerLand;
	}

	/**
	 * 中古土地の予算上限 を取得する。<br/>
	 * 中古土地の予算上限 の値は、フレームワークの URL マッピングで使用する 中古土地の予算上限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古土地の予算上限
	 */
	public String getPriceUpperLand() {
		return priceUpperLand;
	}

	/**
	 * 中古土地の予算上限 を設定する。<BR/>
	 * 中古土地の予算上限 の値は、フレームワークの URL マッピングで使用する 中古土地の予算上限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM priceUpperLand 中古土地の予算上限
	 */
	public void setPriceUpperLand(String priceUpperLand) {
		this.priceUpperLand = priceUpperLand;
	}

	/**
	 * 専有面積下限 を取得する。<br/>
	 * 専有面積下限 の値は、フレームワークの URL マッピングで使用する 専有面積下限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 専有面積下限
	 */
	public String getPersonalAreaLowerMansion() {
		return personalAreaLowerMansion;
	}

	/**
	 * 専有面積下限 を設定する。<BR/>
	 * 専有面積下限 の値は、フレームワークの URL マッピングで使用する 専有面積下限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM personalAreaLowerMansion 専有面積下限
	 */
	public void setPersonalAreaLowerMansion(String personalAreaLowerMansion) {
		this.personalAreaLowerMansion = personalAreaLowerMansion;
	}

	/**
	 * 専有面積上限 を取得する。<br/>
	 * 専有面積上限 の値は、フレームワークの URL マッピングで使用する 専有面積上限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 専有面積上限
	 */
	public String getPersonalAreaUpperMansion() {
		return personalAreaUpperMansion;
	}

	/**
	 * 専有面積上限 を設定する。<BR/>
	 * 専有面積上限 の値は、フレームワークの URL マッピングで使用する 専有面積上限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM personalAreaUpperMansion 専有面積上限
	 */
	public void setPersonalAreaUpperMansion(String personalAreaUpperMansion) {
		this.personalAreaUpperMansion = personalAreaUpperMansion;
	}

	/**
	 * 中古戸建の建物面積下限 を取得する。<br/>
	 * 中古戸建の建物面積下限 の値は、フレームワークの URL マッピングで使用する 中古戸建の建物面積下限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古戸建の建物面積下限
	 */
	public String getBuildingAreaLowerHouse() {
		return buildingAreaLowerHouse;
	}

	/**
	 * 中古戸建の建物面積下限 を設定する。<BR/>
	 * 中古戸建の建物面積下限 の値は、フレームワークの URL マッピングで使用する 中古戸建の建物面積下限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM buildingAreaLowerHouse 中古戸建の建物面積下限
	 */
	public void setBuildingAreaLowerHouse(String buildingAreaLowerHouse) {
		this.buildingAreaLowerHouse = buildingAreaLowerHouse;
	}

	/**
	 * 中古戸建の建物面積上限 を取得する。<br/>
	 * 中古戸建の建物面積上限 の値は、フレームワークの URL マッピングで使用する 中古戸建の建物面積上限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古戸建の建物面積上限
	 */
	public String getBuildingAreaUpperHouse() {
		return buildingAreaUpperHouse;
	}

	/**
	 * 中古戸建の建物面積上限 を設定する。<BR/>
	 * 中古戸建の建物面積上限 の値は、フレームワークの URL マッピングで使用する 中古戸建の建物面積上限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM buildingAreaUpperHouse 中古戸建の建物面積上限
	 */
	public void setBuildingAreaUpperHouse(String buildingAreaUpperHouse) {
		this.buildingAreaUpperHouse = buildingAreaUpperHouse;
	}

	/**
	 * 中古土地の建物面積下限 を取得する。<br/>
	 * 中古土地の建物面積下限 の値は、フレームワークの URL マッピングで使用する 中古土地の建物面積下限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古土地の建物面積下限
	 */
	public String getBuildingAreaLowerLand() {
		return buildingAreaLowerLand;
	}

	/**
	 * 中古土地の建物面積下限 を設定する。<BR/>
	 * 中古土地の建物面積下限 の値は、フレームワークの URL マッピングで使用する 中古土地の建物面積下限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM buildingAreaLowerLand 中古土地の建物面積下限
	 */
	public void setBuildingAreaLowerLand(String buildingAreaLowerLand) {
		this.buildingAreaLowerLand = buildingAreaLowerLand;
	}

	/**
	 * 中古土地の建物面積上限 を取得する。<br/>
	 * 中古土地の建物面積上限 の値は、フレームワークの URL マッピングで使用する 中古土地の建物面積上限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古土地の建物面積上限
	 */
	public String getBuildingAreaUpperLand() {
		return buildingAreaUpperLand;
	}

	/**
	 * 中古土地の建物面積上限 を設定する。<BR/>
	 * 中古土地の建物面積上限 の値は、フレームワークの URL マッピングで使用する 中古土地の建物面積上限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM buildingAreaUpperLand 中古土地の建物面積上限
	 */
	public void setBuildingAreaUpperLand(String buildingAreaUpperLand) {
		this.buildingAreaUpperLand = buildingAreaUpperLand;
	}

	/**
	 * 中古戸建の土地面積下限 を取得する。<br/>
	 * 中古戸建の土地面積下限 の値は、フレームワークの URL マッピングで使用する 中古戸建の土地面積下限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古戸建の土地面積下限
	 */
	public String getLandAreaLowerHouse() {
		return landAreaLowerHouse;
	}

	/**
	 * 中古戸建の土地面積下限 を設定する。<BR/>
	 * 中古戸建の土地面積下限 の値は、フレームワークの URL マッピングで使用する 中古戸建の土地面積下限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM landAreaLowerHouse 中古戸建の土地面積下限
	 */
	public void setLandAreaLowerHouse(String landAreaLowerHouse) {
		this.landAreaLowerHouse = landAreaLowerHouse;
	}

	/**
	 * 中古戸建の土地面積上限 を取得する。<br/>
	 * 中古戸建の土地面積上限 の値は、フレームワークの URL マッピングで使用する 中古戸建の土地面積上限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古戸建の土地面積上限
	 */
	public String getLandAreaUpperHouse() {
		return landAreaUpperHouse;
	}

	/**
	 * 中古戸建の土地面積上限 を設定する。<BR/>
	 * 中古戸建の土地面積上限 の値は、フレームワークの URL マッピングで使用する 中古戸建の土地面積上限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM landAreaUpperHouse 中古戸建の土地面積上限
	 */
	public void setLandAreaUpperHouse(String landAreaUpperHouse) {
		this.landAreaUpperHouse = landAreaUpperHouse;
	}

	/**
	 * 中古土地の土地面積下限 を取得する。<br/>
	 * 中古土地の土地面積下限 の値は、フレームワークの URL マッピングで使用する 中古土地の土地面積下限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古土地の土地面積下限
	 */
	public String getLandAreaLowerLand() {
		return landAreaLowerLand;
	}

	/**
	 * 中古土地の土地面積下限 を設定する。<BR/>
	 * 中古土地の土地面積下限 の値は、フレームワークの URL マッピングで使用する 中古土地の土地面積下限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM landAreaLowerLand 中古土地の土地面積下限
	 */
	public void setLandAreaLowerLand(String landAreaLowerLand) {
		this.landAreaLowerLand = landAreaLowerLand;
	}

	/**
	 * 中古土地の土地面積上限 を取得する。<br/>
	 * 中古土地の土地面積上限 の値は、フレームワークの URL マッピングで使用する 中古土地の土地面積上限 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古土地の土地面積上限
	 */
	public String getLandAreaUpperLand() {
		return landAreaUpperLand;
	}

	/**
	 * 中古土地の土地面積上限 を設定する。<BR/>
	 * 中古土地の土地面積上限 の値は、フレームワークの URL マッピングで使用する 中古土地の土地面積上限 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM landAreaUpperLand 中古土地の土地面積上限
	 */
	public void setLandAreaUpperLand(String landAreaUpperLand) {
		this.landAreaUpperLand = landAreaUpperLand;
	}

	/**
	 * 中古マンションの間取り を取得する。<br/>
	 * 中古マンションの間取り の値は、フレームワークの URL マッピングで使用する 中古マンションの間取り クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古マンションの間取り
	 */
	public String[] getLayoutCdMansion() {
		return layoutCdMansion;
	}

	/**
	 * 中古マンションの間取り を設定する。<BR/>
	 * 中古マンションの間取り の値は、フレームワークの URL マッピングで使用する 中古マンションの間取り クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM layoutCdMansion 中古マンションの間取り
	 */
	public void setLayoutCdMansion(String[] layoutCdMansion) {
		this.layoutCdMansion = layoutCdMansion;
	}

	/**
	 * 中古戸建の間取り を取得する。<br/>
	 * 中古戸建の間取り の値は、フレームワークの URL マッピングで使用する 中古戸建の間取り クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古戸建の間取り
	 */
	public String[] getLayoutCdHouse() {
		return layoutCdHouse;
	}

	/**
	 * 中古戸建の間取り を設定する。<BR/>
	 * 中古戸建の間取り の値は、フレームワークの URL マッピングで使用する 中古戸建の間取り クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM layoutCdHouse 中古戸建の間取り
	 */
	public void setLayoutCdHouse(String[] layoutCdHouse) {
		this.layoutCdHouse = layoutCdHouse;
	}

	/**
	 * 中古マンションの築年数 を取得する。<br/>
	 * 中古マンションの築年数 の値は、フレームワークの URL マッピングで使用する 中古マンションの築年数 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古マンションの築年数
	 */
	public String getCompDateMansion() {
		return compDateMansion;
	}

	/**
	 * 中古マンションの築年数 を設定する。<BR/>
	 * 中古マンションの築年数 の値は、フレームワークの URL マッピングで使用する 中古マンションの築年数 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM compDateMansion 中古マンションの築年数
	 */
	public void setCompDateMansion(String compDateMansion) {
		this.compDateMansion = compDateMansion;
	}

	/**
	 * 中古戸建の築年数 を取得する。<br/>
	 * 中古戸建の築年数 の値は、フレームワークの URL マッピングで使用する 中古戸建の築年数 クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古戸建の築年数
	 */
	public String getCompDateHouse() {
		return compDateHouse;
	}

	/**
	 * 中古戸建の築年数 を設定する。<BR/>
	 * 中古戸建の築年数 の値は、フレームワークの URL マッピングで使用する 中古戸建の築年数 クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM compDateHouse 中古戸建の築年数
	 */
	public void setCompDateHouse(String compDateHouse) {
		this.compDateHouse = compDateHouse;
	}

	/**
	 * 中古マンションのおすすめのポイント を取得する。<br/>
	 * 中古マンションのおすすめのポイント の値は、フレームワークの URL マッピングで使用する 中古マンションのおすすめのポイント
	 * クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古マンションのおすすめのポイント
	 */
	public String[] getIconCdMansion() {
		return iconCdMansion;
	}

	/**
	 * 中古マンションのおすすめのポイント を設定する。<BR/>
	 * 中古マンションのおすすめのポイント の値は、フレームワークの URL マッピングで使用する 中古マンションのおすすめのポイント
	 * クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM iconCdMansion 中古マンションのおすすめのポイント
	 */
	public void setIconCdMansion(String[] iconCdMansion) {
		this.iconCdMansion = iconCdMansion;
	}

	/**
	 * 中古戸建のおすすめのポイント を取得する。<br/>
	 * 中古戸建のおすすめのポイント の値は、フレームワークの URL マッピングで使用する 中古戸建のおすすめのポイント
	 * クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古戸建のおすすめのポイント
	 */
	public String[] getIconCdHouse() {
		return iconCdHouse;
	}

	/**
	 * 中古戸建のおすすめのポイント を設定する。<BR/>
	 * 中古戸建のおすすめのポイント の値は、フレームワークの URL マッピングで使用する 中古戸建のおすすめのポイント
	 * クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM iconCdHouse 中古戸建のおすすめのポイント
	 */
	public void setIconCdHouse(String[] iconCdHouse) {
		this.iconCdHouse = iconCdHouse;
	}

	/**
	 * 中古マンションのリフォーム価格込みで検索する を取得する。<br/>
	 * 中古マンションのリフォーム価格込みで検索する の値は、フレームワークの URL マッピングで使用する 中古マンションのリフォーム価格込みで検索する
	 * クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古マンションのリフォーム価格込みで検索する
	 */
	public String getReformCheckMansion() {
		return reformCheckMansion;
	}

	/**
	 * 中古マンションのリフォーム価格込みで検索する を設定する。<BR/>
	 * 中古マンションのリフォーム価格込みで検索する の値は、フレームワークの URL マッピングで使用する 中古マンションのリフォーム価格込みで検索する
	 * クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM reformCheckMansion 中古マンションのリフォーム価格込みで検索する
	 */
	public void setReformCheckMansion(String reformCheckMansion) {
		this.reformCheckMansion = reformCheckMansion;
	}

	/**
	 * 中古戸建のリフォーム価格込みで検索する を取得する。<br/>
	 * 中古戸建のリフォーム価格込みで検索する の値は、フレームワークの URL マッピングで使用する 中古戸建のリフォーム価格込みで検索する
	 * クラスの切り替えにも使用される。<br/>
	 * <br/>
	 *
	 * @return 中古戸建のリフォーム価格込みで検索する
	 */
	public String getReformCheckHouse() {
		return reformCheckHouse;
	}

	/**
	 * 中古戸建のリフォーム価格込みで検索する を設定する。<BR/>
	 * 中古戸建のリフォーム価格込みで検索する の値は、フレームワークの URL マッピングで使用する 中古戸建のリフォーム価格込みで検索する
	 * クラスの切り替えにも使用される。<BR/>
	 * <BR/>
	 *
	 * @PARAM reformCheckHouse 中古戸建のリフォーム価格込みで検索する
	 */
	public void setReformCheckHouse(String reformCheckHouse) {
		this.reformCheckHouse = reformCheckHouse;
	}

	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 */
	public void setDefaultData(HousingRequest requestInfo) {
		
		
		if(0 != requestInfo.getHousingReqRoutes().size()) {
			this.setRouteCd(requestInfo.getHousingReqRoutes().get(0).getRouteCd());
		}
		if(0 != requestInfo.getHousingReqStations().size()) {
			this.setStationCd(requestInfo.getHousingReqStations().get(0).getStationCd());
		}
		this.setPrefCd(requestInfo.getHousingRequestAreas().get(0).getPrefCd());
		this.setHousingKindCd(requestInfo.getHousingReqKinds().get(0).getHousingKindCd());
		// 賃料/価格・下限値
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceLower())) {
			this.priceLowerMansion = String.valueOf(requestInfo.getHousingRequestInfo().getPriceLower() / 10000);

		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceLower())) {
			this.priceLowerHouse = String.valueOf(requestInfo.getHousingRequestInfo().getPriceLower() / 10000);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceLower())) {
			this.priceLowerLand = String.valueOf(requestInfo.getHousingRequestInfo().getPriceLower() / 10000);
		}
		// 賃料/価格・上限値
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceUpper())) {
			this.priceUpperMansion = String.valueOf(requestInfo.getHousingRequestInfo().getPriceUpper() / 10000);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceUpper())) {
			this.priceUpperHouse = String.valueOf(requestInfo.getHousingRequestInfo().getPriceUpper() / 10000);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceUpper())) {
			this.priceUpperLand = String.valueOf(requestInfo.getHousingRequestInfo().getPriceUpper() / 10000);
		}

		jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo panaHousingRequestInfo =
				(jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo) requestInfo.getHousingRequestInfo();
		
		
		this.setHopeRequestTest(panaHousingRequestInfo.getHopeRequestTest());
		
		String tempStartDate = jp.co.transcosmos.dm3.utils.StringUtils.dateToString(panaHousingRequestInfo.getStartDate(), "dd/MM/yyyy");
		this.setStartDate(tempStartDate);
		
		String tempEndDate = jp.co.transcosmos.dm3.utils.StringUtils.dateToString(panaHousingRequestInfo.getEndDate(), "dd/MM/yyyy");
		this.setEndDate(tempEndDate);
		
		// リフォーム価格込
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			this.reformCheckMansion = panaHousingRequestInfo.getUseReform();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			this.reformCheckHouse = panaHousingRequestInfo.getUseReform();
		}
		// 専有面積・下限値
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getPersonalAreaLower())) {
			this.personalAreaLowerMansion = String.valueOf(panaHousingRequestInfo.getPersonalAreaLower()).substring(0,
					String.valueOf(panaHousingRequestInfo.getPersonalAreaLower()).length() - 3);
		}
		// 専有面積・上限値
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getPersonalAreaUpper())) {
			this.personalAreaUpperMansion = String.valueOf(panaHousingRequestInfo.getPersonalAreaUpper()).substring(0,
					String.valueOf(panaHousingRequestInfo.getPersonalAreaUpper()).length() - 3);
		}
		// 建物面積_下限
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuildingAreaLower())) {
			this.buildingAreaLowerHouse = String.valueOf(panaHousingRequestInfo.getBuildingAreaLower()).substring(0,
					String.valueOf(panaHousingRequestInfo.getBuildingAreaLower()).length() - 3);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuildingAreaLower())) {
			this.buildingAreaLowerLand = String.valueOf(panaHousingRequestInfo.getBuildingAreaLower()).substring(0,
					String.valueOf(panaHousingRequestInfo.getBuildingAreaLower()).length() - 3);
		}
		// 建物面積_上限
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuildingAreaUpper())) {
			this.buildingAreaUpperHouse = String.valueOf(panaHousingRequestInfo.getBuildingAreaUpper()).substring(0,
					String.valueOf(panaHousingRequestInfo.getBuildingAreaUpper()).length() - 3);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuildingAreaUpper())) {
			this.buildingAreaUpperLand = String.valueOf(panaHousingRequestInfo.getBuildingAreaUpper()).substring(0,
					String.valueOf(panaHousingRequestInfo.getBuildingAreaUpper()).length() - 3);
		}
		// 土地面積_下限
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getLandAreaLower())) {
			this.landAreaLowerHouse = String.valueOf(panaHousingRequestInfo.getLandAreaLower()).substring(0,
					String.valueOf(panaHousingRequestInfo.getLandAreaLower()).length() - 3);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getLandAreaLower())) {
			this.landAreaLowerLand = String.valueOf(panaHousingRequestInfo.getLandAreaLower()).substring(0,
					String.valueOf(panaHousingRequestInfo.getLandAreaLower()).length() - 3);
		}
		// 土地面積_上限
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getLandAreaUpper())) {
			this.landAreaUpperHouse = String.valueOf(panaHousingRequestInfo.getLandAreaUpper()).substring(0,
					String.valueOf(panaHousingRequestInfo.getLandAreaUpper()).length() - 3);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getLandAreaUpper())) {
			this.landAreaUpperLand = String.valueOf(panaHousingRequestInfo.getLandAreaUpper()).substring(0,
					String.valueOf(panaHousingRequestInfo.getLandAreaUpper()).length() - 3);
		}
		// 間取CD
		String[] layoutArray = new String[requestInfo.getHousingReqLayouts().size()];
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingReqLayouts())) {
			for (int idx = 0; idx < requestInfo.getHousingReqLayouts().size(); idx++) {
				layoutArray[idx] = requestInfo.getHousingReqLayouts().get(idx).getLayoutCd();
			}
			this.layoutCdMansion = layoutArray;
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingReqLayouts())) {
			for (int idx = 0; idx < requestInfo.getHousingReqLayouts().size(); idx++) {
				layoutArray[idx] = requestInfo.getHousingReqLayouts().get(idx).getLayoutCd();
			}
			this.layoutCdHouse = layoutArray;
		}
		// 築年月
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuiltMonth())) {
			this.compDateMansion = String.valueOf(panaHousingRequestInfo.getBuiltMonth());
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuiltMonth())) {
			this.compDateHouse = String.valueOf(panaHousingRequestInfo.getBuiltMonth());
		}
		// おすすめのポイント こだわり条件CD
		String[] iconArray = new String[requestInfo.getHousingReqParts().size()];
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingReqParts())) {
			for (int idx = 0; idx < requestInfo.getHousingReqParts().size(); idx++) {
				iconArray[idx] = requestInfo.getHousingReqParts().get(idx).getPartSrchCd();
			}
			this.iconCdMansion = iconArray;
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingReqParts())) {
			for (int idx = 0; idx < requestInfo.getHousingReqParts().size(); idx++) {
				iconArray[idx] = requestInfo.getHousingReqParts().get(idx).getPartSrchCd();
			}
			this.iconCdHouse = iconArray;
		}

	}

	/**
	 * バリデーション処理<br/>
	 * リクエストのバリデーションを行う<br/>
	 * <br/>
	 *
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();
		
//		hien.nt create validate for 2 fields combo box and hope request  
		valCombobox(errors);		
		valHopeRequestText(errors);
		valDate(errors);

		// 物件種別入力チェック。
		valHousingKindCd(errors);

		// 都道府県名入力チェック。
		valPrefCd(errors);

		// 予算_下限＞予算_上限の場合
		valPrice(errors);

		// 専有面積_下限＞専有面積_上限の場合
		valPersonalArea(errors);

		// 建物面積_下限＞建物面積_上限の場合
		valBuildingArea(errors);

		// 土地面積_下限＞土地面積_上限の場合
		valLandArea(errors);

		// 「リフォーム価格込みで検索する」をチェックオン場合、予算下限、予算上限両方も選択していない場合、
		valReformCheck(errors);

		// 間取チェック
		valLayoutCheck(errors);

		// 築年数チェック
		valCompDateCheck(errors);

		// おすすめのポイントチェック
		valIconCdCheck(errors);

		return (startSize == errors.size());
	}

	/**
	 * to validate logic StartDate and EndDate
	 * startDate > EndDate --> add Error
	 * startDate || EndDate --> add Error
	 * startDate && EndDate --> add Error
	 * @param errors
	 */
	@SuppressWarnings("static-access")
	private void valDate(List<ValidationFailure> errors) {				
		Date start = null;
		Date end = null;
		
		if(StringUtils.isEmpty(this.getStartDate()) || StringUtils.isEmpty(this.getEndDate())){			
			ValidationFailure valDate = new 
					ValidationFailure("hienDate","housingRequest.input.valDate",null, null);	
			errors.add(valDate);
			System.out.println("case empty");
		}else{		
			try {	
				start= DateUtils.stringToDate(this.getStartDate());
				end = DateUtils.stringToDate(this.getEndDate());
				
				if(start.compareTo(end) > 0)
				{
					ValidationFailure valDate = new ValidationFailure("hienDate","housingRequest.input.valDate",null, null);	
					errors.add(valDate);
				}
			
			} catch (ParseException e)  {			
				ValidationFailure valDate = new 
						ValidationFailure("hienDate","housingRequest.input.valDate",null, null);	
				errors.add(valDate);
			}
		}
	}
	

	/**
	 * 物件種別 バリデーション<br/>
	 * ・必須チェック <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 */
	protected void valHousingKindCd(List<ValidationFailure> errors) {
		// 物件種別入力チェック
		ValidationChain valHousingKindCd = new ValidationChain("housingRequest.input.valHousingKindCd",
				this.getHousingKindCd());
		// 必須チェック
		valHousingKindCd.addValidation(new NullOrEmptyCheckValidation());

		valHousingKindCd.validate(errors);
	}

	protected void valHopeRequestText(List<ValidationFailure> errors) {
		// 物件種別入力チェック
		ValidationChain valHopeRequestText = new ValidationChain("housingRequest.input.valHopeRequestText", this.getHopeRequestTest());
		// 必須チェック
		valHopeRequestText.addValidation(new MaxLengthValidation(1000));
		
		valHopeRequestText.validate(errors);
	}

	/**
	 * 間取チェック バリデーション<br/>
	 * ・code-lookUpチェック <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 */
	protected void valLayoutCheck(List<ValidationFailure> errors) {

		ValidationChain valLayoutCd = null;

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {

			if (this.getLayoutCdMansion() != null) {
				for (int i = 0; i < this.getLayoutCdMansion().length; i++) {
					valLayoutCd = new ValidationChain("housingRequest.input.valLayoutCd", this.getLayoutCdMansion()[i]);

					if (valLayoutCd != null) {

						// code-lookupチェック
						valLayoutCd.addValidation(new CodeLookupValidation(this.codeLookupManager, "layoutCd"));

						valLayoutCd.validate(errors);
					}
				}
			}

		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {

			if (this.getLayoutCdHouse() != null) {
				for (int i = 0; i < this.getLayoutCdHouse().length; i++) {
					valLayoutCd = new ValidationChain("housingRequest.input.valLayoutCd", this.getLayoutCdHouse()[i]);

					if (valLayoutCd != null) {

						// code-lookupチェック
						valLayoutCd.addValidation(new CodeLookupValidation(this.codeLookupManager, "layoutCd"));

						valLayoutCd.validate(errors);
					}
				}
			}

		}

	}

	/**
	 * 築年数チェック バリデーション<br/>
	 * ・code-lookUpチェック <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 */
	protected void valCompDateCheck(List<ValidationFailure> errors) {

		ValidationChain valCompDate = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			valCompDate = new ValidationChain("housingRequest.input.valCompDate", this.getCompDateMansion());
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			valCompDate = new ValidationChain("housingRequest.input.valCompDate", this.getCompDateHouse());
		}

		if (valCompDate != null) {
			// code-lookupチェック
			valCompDate.addValidation(new CodeLookupValidation(this.codeLookupManager, "compDate"));

			valCompDate.validate(errors);
		}

	}

	/**
	 * おすすめのポイントチェック バリデーション<br/>
	 * ・code-lookUpチェック <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 */
	protected void valIconCdCheck(List<ValidationFailure> errors) {

		ValidationChain valIconCd = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (this.getIconCdMansion() != null) {
				for (int i = 0; i < this.getIconCdMansion().length; i++) {
					valIconCd = new ValidationChain("housingRequest.input.valIcon", this.getIconCdMansion()[i]);

					if (valIconCd != null) {

						// code-lookupチェック
						valIconCd.addValidation(
								new CodeLookupValidation(this.codeLookupManager, "recommend_point_icon_list"));

						valIconCd.validate(errors);
					}
				}
			}
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (this.getIconCdHouse() != null) {
				for (int i = 0; i < this.getIconCdHouse().length; i++) {
					valIconCd = new ValidationChain("housingRequest.input.valIcon", this.getIconCdHouse()[i]);

					if (valIconCd != null) {

						// code-lookupチェック
						valIconCd.addValidation(
								new CodeLookupValidation(this.codeLookupManager, "recommend_point_icon_list"));

						valIconCd.validate(errors);
					}
				}
			}
		}

	}

	/**
	 * 都道府県名 バリデーション<br/>
	 * ・必須チェック <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 */
	protected void valPrefCd(List<ValidationFailure> errors) {
		// 都道府県名入力チェック
		ValidationChain valSwitchSelect = new ValidationChain("housingRequest.input.valPrefCd", this.getPrefCd());
		// 桁数チェック
		valSwitchSelect.addValidation(new NullOrEmptyCheckValidation());

		valSwitchSelect.validate(errors);
	}
	
	/**
	 * hien.nt
	 * validate code logic for combo box routeCd and stationCd
	 * 
	 * @param errors
	 */
	protected void valCombobox(List<ValidationFailure> errors) {
		if((StringValidateUtil.isEmpty(this.getRouteCd()) 
						&& StringValidateUtil.isEmpty(this.getStationCd())
						&& StringValidateUtil.isEmpty(this.getHopeRequestTest())
			|| (StringValidateUtil.isEmpty(this.getRouteCd())
						&& !StringValidateUtil.isEmpty(this.getStationCd())	
						&& StringValidateUtil.isEmpty(this.getHopeRequestTest()))
			|| (StringValidateUtil.isEmpty(this.getStationCd())	
						&& StringValidateUtil.isEmpty(this.getHopeRequestTest())))
			){
			// 都道府県の数値チェック
			ValidationFailure valCombobox = new ValidationFailure("hiencombo","housingRequest.input.valCombobox",
					null, null);	
			errors.add(valCombobox);
		} 
	}
	
	/**
	 * 予算 バリデーション<br/>
	 * ・必須チェック <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 */
	protected void valPrice(List<ValidationFailure> errors) {
		// 予算入力チェック
		ValidationChain valPriceLower = null;
		ValidationChain valPriceUpper = null;
		String priceUpper = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			valPriceLower = new ValidationChain("housingRequest.input.valPriceLower", this.getPriceLowerMansion());
			valPriceUpper = new ValidationChain("housingRequest.input.valPriceUpper", this.getPriceUpperMansion());
			priceUpper = this.getPriceUpperMansion();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			valPriceLower = new ValidationChain("housingRequest.input.valPriceLower", this.getPriceLowerHouse());
			valPriceUpper = new ValidationChain("housingRequest.input.valPriceUpper", this.getPriceUpperHouse());
			priceUpper = this.getPriceUpperHouse();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			valPriceLower = new ValidationChain("housingRequest.input.valPriceLower", this.getPriceLowerLand());
			valPriceUpper = new ValidationChain("housingRequest.input.valPriceUpper", this.getPriceUpperLand());
			priceUpper = this.getPriceUpperLand();
		}

		if (valPriceLower != null) {
			// code-lookupチェック
			valPriceLower.addValidation(new CodeLookupValidation(this.codeLookupManager, "price"));

			// 予算_下限＞予算_上限
			valPriceLower.addValidation(new DataCheckValidation(priceUpper, "予算_上限"));
			valPriceLower.validate(errors);
		}
		if (valPriceUpper != null) {
			valPriceUpper.addValidation(new CodeLookupValidation(this.codeLookupManager, "price"));
			valPriceUpper.validate(errors);
		}

	}

	/**
	 * 専有面積 バリデーション<br/>
	 * ・必須チェック <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 */
	protected void valPersonalArea(List<ValidationFailure> errors) {
		// 専有面積入力チェック
		ValidationChain valPersonalAreaLower = null;
		ValidationChain valPersonalAreaUpper = null;
		String areaUpper = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			valPersonalAreaLower = new ValidationChain("housingRequest.input.valPersonalAreaLower",
					this.getPersonalAreaLowerMansion());
			valPersonalAreaUpper = new ValidationChain("housingRequest.input.valPersonalAreaUpper",
					this.getPersonalAreaUpperMansion());
			areaUpper = this.getPersonalAreaUpperMansion();
		}

		if (valPersonalAreaLower != null) {
			// code-lookupチェック
			valPersonalAreaLower.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			// 予算_下限＞予算_上限
			valPersonalAreaLower.addValidation(new DataCheckValidation(areaUpper, "専有面積_上限"));
			valPersonalAreaLower.validate(errors);
		}
		if (valPersonalAreaUpper != null) {
			// code-lookupチェック
			valPersonalAreaUpper.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			valPersonalAreaUpper.validate(errors);
		}

	}

	/**
	 * 建物面積 バリデーション<br/>
	 * ・必須チェック <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 */
	protected void valBuildingArea(List<ValidationFailure> errors) {
		// 建物面積入力チェック
		ValidationChain valBuildingAreaLower = null;
		ValidationChain valBuildingAreaUpper = null;
		String buildingAreaUpper = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			valBuildingAreaLower = new ValidationChain("housingRequest.input.valBuildingAreaLower",
					this.getBuildingAreaLowerHouse());
			valBuildingAreaUpper = new ValidationChain("housingRequest.input.valBuildingAreaUpper",
					this.getBuildingAreaUpperHouse());
			buildingAreaUpper = this.getBuildingAreaUpperHouse();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			valBuildingAreaLower = new ValidationChain("housingRequest.input.valBuildingAreaLower",
					this.getBuildingAreaLowerLand());
			valBuildingAreaUpper = new ValidationChain("housingRequest.input.valBuildingAreaUpper",
					this.getBuildingAreaUpperLand());
			buildingAreaUpper = this.getBuildingAreaUpperLand();
		}

		if (valBuildingAreaLower != null) {
			// code-lookupチェック
			valBuildingAreaLower.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			// 建物面積_下限＞建物面積_上限
			valBuildingAreaLower.addValidation(new DataCheckValidation(buildingAreaUpper, "建物面積_上限"));
			valBuildingAreaLower.validate(errors);
		}
		if (valBuildingAreaUpper != null) {
			// code-lookupチェック
			valBuildingAreaUpper.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			valBuildingAreaUpper.validate(errors);
		}
	}

	/**
	 * 土地面積 バリデーション<br/>
	 * ・必須チェック <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 */
	protected void valLandArea(List<ValidationFailure> errors) {
		// 土地面積入力チェック
		ValidationChain valLandAreaLower = null;
		ValidationChain valLandAreaUpper = null;
		String landAreaUpper = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			valLandAreaLower = new ValidationChain("housingRequest.input.valLandAreaLower",
					this.getLandAreaLowerHouse());
			valLandAreaUpper = new ValidationChain("housingRequest.input.valLandAreaUpper",
					this.getLandAreaUpperHouse());
			landAreaUpper = this.getLandAreaUpperHouse();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			valLandAreaLower = new ValidationChain("housingRequest.input.valLandAreaLower",
					this.getLandAreaLowerLand());
			valLandAreaUpper = new ValidationChain("housingRequest.input.valLandAreaUpper",
					this.getLandAreaUpperLand());
			landAreaUpper = this.getLandAreaUpperLand();
		}

		if (valLandAreaLower != null) {
			// code-lookupチェック
			valLandAreaLower.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			// 土地面積_下限＞土地面積_上限
			valLandAreaLower.addValidation(new DataCheckValidation(landAreaUpper, "土地面積_上限"));
			valLandAreaLower.validate(errors);
		}
		if (valLandAreaUpper != null) {
			// code-lookupチェック
			valLandAreaUpper.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			valLandAreaUpper.validate(errors);
		}
	}

	/**
	 * 「リフォーム価格込みで検索する」 バリデーション<br/>
	 * ・必須チェック <br/>
	 * 
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 */
	protected void valReformCheck(List<ValidationFailure> errors) {
		// 予算入力チェック
		ValidationChain valPriceLower = null;
		String priceUpper = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			valPriceLower = new ValidationChain("housingRequest.input.valPriceLower", this.getPriceLowerMansion());
			priceUpper = this.getPriceUpperMansion();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			valPriceLower = new ValidationChain("housingRequest.input.valPriceLower", this.getPriceLowerHouse());
			priceUpper = this.getPriceUpperHouse();
		}

		if ("1".equals(this.getReformCheckHouse()) || "1".equals(this.getReformCheckMansion())) {

			if (valPriceLower != null) {
				// 予算_下限＞予算_上限
				valPriceLower.addValidation(new PriceReformCheckValidation(priceUpper));
				valPriceLower.validate(errors);
			}
		}
	}

	/**
	 * 引数で渡された物件リクエスト情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 *
	 * @param userId
	 *            ユーザID
	 * @param housingRequestInfo
	 *            物件リクエスト情報
	 */
	@SuppressWarnings("static-access")
	@Override
	public void copyToHousingRequestInfo(String userId, HousingRequestInfo housingRequestInfo) {

		if (StringValidateUtil.isEmpty(userId)) {
			return;
		}

		jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo panaHousingRequestInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo) housingRequestInfo;

		super.copyToHousingRequestInfo(userId, housingRequestInfo);

		// ユーザID
		housingRequestInfo.setUserId(userId);	
		SimpleDateFormat targetFormatToDate = new SimpleDateFormat("dd/MM/yyyy" );
		try {
			panaHousingRequestInfo.setStartDate(targetFormatToDate.parse(this.getStartDate()));
			panaHousingRequestInfo.setEndDate(targetFormatToDate.parse(this.getEndDate()));
			
		} catch (ParseException e) {
			throw new RuntimeException();
		}
		panaHousingRequestInfo.setHopeRequestTest(this.getHopeRequestTest());

		// 賃料/価格・下限値
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceLowerMansion)) {
				housingRequestInfo.setPriceLower(Long.valueOf(this.priceLowerMansion) * 10000);
			} else {
				housingRequestInfo.setPriceLower(null);
			}

		}
		// 賃料/価格・下限値
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceLowerHouse)) {
				housingRequestInfo.setPriceLower(Long.valueOf(this.priceLowerHouse) * 10000);
			} else {
				housingRequestInfo.setPriceLower(null);
			}

		}
		// 賃料/価格・下限値
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceLowerLand)) {
				housingRequestInfo.setPriceLower(Long.valueOf(this.priceLowerLand) * 10000);
			} else {
				housingRequestInfo.setPriceLower(null);
			}

		}
		// 賃料/価格・上限値
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceUpperMansion)) {
				housingRequestInfo.setPriceUpper(Long.valueOf(this.priceUpperMansion) * 10000);
			} else {
				housingRequestInfo.setPriceUpper(null);
			}

		}
		// 賃料/価格・上限値
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceUpperHouse)) {
				housingRequestInfo.setPriceUpper(Long.valueOf(this.priceUpperHouse) * 10000);
			} else {
				housingRequestInfo.setPriceUpper(null);
			}

		}
		// 賃料/価格・上限値
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceUpperLand)) {
				housingRequestInfo.setPriceUpper(Long.valueOf(this.priceUpperLand) * 10000);
			} else {
				housingRequestInfo.setPriceUpper(null);
			}
		}
		// 専有面積・下限値
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.personalAreaLowerMansion)) {
				housingRequestInfo
						.setPersonalAreaLower(BigDecimal.valueOf(Long.valueOf(this.personalAreaLowerMansion)));
			} else {
				housingRequestInfo.setPersonalAreaLower(null);
			}
		}
		// 専有面積・上限値
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.personalAreaUpperMansion)) {
				housingRequestInfo
						.setPersonalAreaUpper(BigDecimal.valueOf(Long.valueOf(this.personalAreaUpperMansion)));
			} else {
				housingRequestInfo.setPersonalAreaUpper(null);
			}
		}
		// 建物面積・下限値
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.buildingAreaLowerHouse)) {
				panaHousingRequestInfo
						.setBuildingAreaLower(BigDecimal.valueOf(Long.valueOf(this.buildingAreaLowerHouse)));
			} else {
				panaHousingRequestInfo.setBuildingAreaLower(null);
			}
		}
		// 建物面積・下限値
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.buildingAreaLowerLand)) {
				panaHousingRequestInfo
						.setBuildingAreaLower(BigDecimal.valueOf(Long.valueOf(this.buildingAreaLowerLand)));
			} else {
				panaHousingRequestInfo.setBuildingAreaLower(null);
			}
		}
		// 建物面積・上限値
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.buildingAreaUpperHouse)) {
				panaHousingRequestInfo
						.setBuildingAreaUpper(BigDecimal.valueOf(Long.valueOf(this.buildingAreaUpperHouse)));
			} else {
				panaHousingRequestInfo.setBuildingAreaUpper(null);
			}
		}
		// 建物面積・上限値
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.buildingAreaUpperLand)) {
				panaHousingRequestInfo
						.setBuildingAreaUpper(BigDecimal.valueOf(Long.valueOf(this.buildingAreaUpperLand)));
			} else {
				panaHousingRequestInfo.setBuildingAreaUpper(null);
			}
		}

		// 土地面積・下限値
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.landAreaLowerHouse)) {
				panaHousingRequestInfo.setLandAreaLower(BigDecimal.valueOf(Long.valueOf(this.landAreaLowerHouse)));
			} else {
				panaHousingRequestInfo.setLandAreaLower(null);
			}
		}
		// 土地面積・下限値
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.landAreaLowerLand)) {
				panaHousingRequestInfo.setLandAreaLower(BigDecimal.valueOf(Long.valueOf(this.landAreaLowerLand)));
			} else {
				panaHousingRequestInfo.setLandAreaLower(null);
			}
		}
		// 土地面積・上限値
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.landAreaUpperHouse)) {
				panaHousingRequestInfo.setLandAreaUpper(BigDecimal.valueOf(Long.valueOf(this.landAreaUpperHouse)));
			} else {
				panaHousingRequestInfo.setLandAreaUpper(null);
			}
		}
		// 土地面積・上限値
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.landAreaUpperLand)) {
				panaHousingRequestInfo.setLandAreaUpper(BigDecimal.valueOf(Long.valueOf(this.landAreaUpperLand)));
			} else {
				panaHousingRequestInfo.setLandAreaUpper(null);
			}
		}

		// 築年数
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.compDateMansion) && !"999".equals(this.compDateMansion)) {
				panaHousingRequestInfo.setBuiltMonth(Integer.valueOf(this.compDateMansion));
			} else {
				panaHousingRequestInfo.setBuiltMonth(null);
			}
		}

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.compDateHouse) && !"999".equals(this.compDateHouse)) {
				panaHousingRequestInfo.setBuiltMonth(Integer.valueOf(this.compDateHouse));
			} else {
				panaHousingRequestInfo.setBuiltMonth(null);
			}
		}

		// リフォーム価格込みで検索する
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			panaHousingRequestInfo.setUseReform(this.reformCheckMansion);
		}

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			panaHousingRequestInfo.setUseReform(this.reformCheckHouse);
		}

		// 都道府県CD
		setPrefCd(this.getPrefCd());
		
		// 物件種類CD
		setHousingKindCd(this.getHousingKindCd());

		// 間取りCD
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			String strLayout = "";
			if (this.layoutCdMansion != null) {
				for (int i = 0; i < this.layoutCdMansion.length; i++) {
					if ("".equals(strLayout)) {
						strLayout = this.layoutCdMansion[i];
					} else {
						strLayout = strLayout + "," + this.layoutCdMansion[i];
					}
				}
				super.setLayoutCd(strLayout);
			}

		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			String strLayout = "";
			if (this.layoutCdHouse != null) {
				for (int i = 0; i < this.layoutCdHouse.length; i++) {
					if ("".equals(strLayout)) {
						strLayout = this.layoutCdHouse[i];
					} else {
						strLayout = strLayout + "," + this.layoutCdHouse[i];
					}
				}
				super.setLayoutCd(strLayout);
			}
		}

		// おすすめのポイントCD
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			String strIconCd = "";
			if (this.iconCdMansion != null) {
				for (int i = 0; i < this.iconCdMansion.length; i++) {
					if ("".equals(strIconCd)) {
						strIconCd = this.iconCdMansion[i];
					} else {
						strIconCd = strIconCd + "," + this.iconCdMansion[i];
					}
				}
				super.setPartSrchCd(strIconCd);
			}
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			String strIconCd = "";
			if (this.iconCdHouse != null) {
				for (int i = 0; i < this.iconCdHouse.length; i++) {
					if ("".equals(strIconCd)) {
						strIconCd = this.iconCdHouse[i];
					} else {
						strIconCd = strIconCd + "," + this.iconCdHouse[i];
					}
				}
				super.setPartSrchCd(strIconCd);
			}
		}

		Date sysDate = new Date();
		housingRequestInfo.setUpdDate(sysDate);
		housingRequestInfo.setUpdUserId(userId);
	}

	@Override
	public void setHousingKindCd(String housingKindCd) {
		// 数値以外の不正なパラメータが入力された場合に例外が発生させるために、数値に変換
		if (!StringValidateUtil.isEmpty(housingKindCd)) {
			Integer.valueOf(housingKindCd);
		}
		super.setHousingKindCd(housingKindCd);
	}

	@Override
	public void setPrefCd(String prefCd) {
		// 数値以外の不正なパラメータが入力された場合に例外が発生させるために、数値に変換
		if (!StringValidateUtil.isEmpty(prefCd)) {
			Integer.valueOf(prefCd);
		}
		super.setPrefCd(prefCd);
	}
		
}

