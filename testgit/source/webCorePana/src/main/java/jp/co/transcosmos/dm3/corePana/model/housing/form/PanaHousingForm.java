package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * 物件基本情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 *
 * <pre>
 * 担当者      修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan	       2015.03.11  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */

public class PanaHousingForm extends HousingForm implements Validateable {
    /**
     * デフォルトコンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     */
    PanaHousingForm() {
        super();
    }

    /**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
     * @param　codeLookupManager　共通コード変換処理
     */
    PanaHousingForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
        super();
        this.lengthUtils = lengthUtils;
        this.codeLookupManager = codeLookupManager;
    }

    /** 最小徒歩時間 */
    private String minWalkingTime;
    /** リフォーム込価格 */
    private String priceFullMin;
    /** リフォーム込価格 */
    private String priceFullMax;

    /** リフォーム準備中コメント */
    private String reformComment;
	/** 物件リクエスト処理対象フラグ */
	private String requestFlg;

    /**
     * リフォーム込価格 パラメータを取得する。<br/>
     * リフォーム込価格 パラメータの値は、フレームワークの URL マッピングで使用する リフォーム込価格 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 最小徒歩時間 パラメータ
     */
    public String getPriceFullMin() {
		return priceFullMin;
	}

    /**
     * リフォーム込価格 パラメータを設定する。<BR/>
     * リフォーム込価格 パラメータの値は、フレームワークの URL マッピングで使用する リフォーム込価格 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM priceFullMin リフォーム込価格 パラメータ
     */
	public void setPriceFullMin(String priceFullMin) {
		this.priceFullMin = priceFullMin;
	}

	/**
     * リフォーム込価格 パラメータを取得する。<br/>
     * リフォーム込価格 パラメータの値は、フレームワークの URL マッピングで使用する リフォーム込価格 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 最小徒歩時間 パラメータ
     */
	public String getPriceFullMax() {
		return priceFullMax;
	}

	 /**
     * リフォーム込価格 パラメータを設定する。<BR/>
     * リフォーム込価格 パラメータの値は、フレームワークの URL マッピングで使用する リフォーム込価格 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM priceFullMax リフォーム込価格 パラメータ
     */
	public void setPriceFullMax(String priceFullMax) {
		this.priceFullMax = priceFullMax;
	}
	 /**
     * 最小徒歩時間 パラメータを取得する。<br/>
     * 最小徒歩時間 パラメータの値は、フレームワークの URL マッピングで使用する 最小徒歩時間 クラスの切り替えにも使用される。<br/>
     * <br/>
     *
     * @return 最小徒歩時間 パラメータ
     */
	public String getMinWalkingTime() {
        return minWalkingTime;
    }
	 /**
     * 最小徒歩時間 パラメータを設定する。<BR/>
     * 最小徒歩時間 パラメータの値は、フレームワークの URL マッピングで使用する 最小徒歩時間 クラスの切り替えにも使用される。<BR/>
     * <BR/>
     *
     * @PARAM minWalkingTime 最小徒歩時間 パラメータ
     */
    public void setMinWalkingTime(String minWalkingTime) {
        this.minWalkingTime = minWalkingTime;
    }

    /**
	 * @return reformComment
	 */
	public String getReformComment() {
		return reformComment;
	}

	/**
	 * @param reformComment セットする reformComment
	 */
	public void setReformComment(String reformComment) {
		this.reformComment = reformComment;
	}

	/**
	 * 物件リクエスト処理対象フラグを取得する。<br/>
	 * <br/>
	 * @return 物件リクエスト処理対象フラグ
	 */
	public String getRequestFlg() {
		return requestFlg;
	}

	/**
	 * 物件リクエスト処理対象フラグを設定する。<br/>
	 * <br/>
	 * @param basicComment　物件リクエスト処理対象フラグ
	 */
	public void setRequestFlg(String requestFlg) {
		this.requestFlg = requestFlg;
	}


	/**
     * 引数で渡された物件基本情報のバリーオブジェクトにフォームの値を設定する。<br/>
     * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
     * <br/>
     * @return housingInfo 物件基本情報
     */
	@Override
    public void copyToHousingInfo(HousingInfo housingInfo) {
//        super.copyToHousingInfo(housingInfo);

        // システム物件CD は、自動採番するか、housingInfo 側で予め設定されている値を使用するので
 		// Form の値は設定しない。

 		housingInfo.setHousingCd(formAndVo(super.getHousingCd(),housingInfo.getHousingCd()));								// 物件番号
 		housingInfo.setDisplayHousingName(formAndVo(super.getDisplayHousingName(),housingInfo.getDisplayHousingName()));				// 表示用物件名
 		housingInfo.setDisplayHousingNameKana(formAndVo(super.getDisplayHousingNameKana(),housingInfo.getDisplayHousingNameKana()));		// 表示用物件名ふりがな
 		housingInfo.setRoomNo(formAndVo(super.getRoomNo(),housingInfo.getRoomNo()));										// 部屋番号
 		housingInfo.setSysBuildingCd(formAndVo(super.getSysBuildingCd(),housingInfo.getSysBuildingCd()));						// システム建物CD

 		if(super.getPrice() !=null){
 			if (!StringValidateUtil.isEmpty(super.getPrice())) {							// 賃料/価格
 	 			housingInfo.setPrice(Long.valueOf(super.getPrice()));
 	 		} else {
 	 			housingInfo.setPrice(null);
 	 		}
 		}

 		if(super.getUpkeep() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getUpkeep())) {							// 管理費
	 			housingInfo.setUpkeep(Long.valueOf(super.getUpkeep()));
	 		} else {
	 			housingInfo.setUpkeep(null);
	 		}
 		}

 		if(super.getCommonAreaFee() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getCommonAreaFee())) {					// 共益費
	 			housingInfo.setCommonAreaFee(Long.valueOf(super.getCommonAreaFee()));
	 		} else {
	 			housingInfo.setCommonAreaFee(null);
	 		}
 		}

 		if(super.getMenteFee() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getMenteFee())) {						// 修繕積立費
	 			housingInfo.setMenteFee(Long.valueOf(super.getMenteFee()));
	 		} else {
	 			housingInfo.setMenteFee(null);
	 		}
 		}

 		if(super.getSecDeposit() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getSecDeposit())) {						// 敷金
	 			housingInfo.setSecDeposit(new BigDecimal(super.getSecDeposit()));
	 		} else {
	 			housingInfo.setSecDeposit(null);
	 		}
 		}

 		housingInfo.setSecDepositCrs(formAndVo(super.getSecDepositCrs(),housingInfo.getSecDepositCrs()));		// 敷金単位

 		if(super.getBondChrg() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getBondChrg())) {						// 保証金
	 			housingInfo.setBondChrg(new BigDecimal(super.getBondChrg()));
	 		} else {
	 			housingInfo.setBondChrg(null);
	 		}
 		}

 		housingInfo.setBondChrgCrs(formAndVo(super.getBondChrgCrs(),housingInfo.getBondChrgCrs()));		// 保証金単位
 		housingInfo.setDepositDiv(formAndVo(super.getDepositDiv(),housingInfo.getDepositDiv()));		// 敷引礼金区分

 		if(super.getDeposit() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getDeposit())) {						// 敷引礼金額
	 			housingInfo.setDeposit(new BigDecimal(super.getDeposit()));
	 		} else {
	 			housingInfo.setDeposit(null);
	 		}
 		}

 		housingInfo.setDepositCrs(formAndVo(super.getDepositCrs(),housingInfo.getDepositCrs()));			// 敷引礼金単位
 		housingInfo.setLayoutCd(formAndVo(super.getLayoutCd(),housingInfo.getLayoutCd()));					// 間取CD
 		housingInfo.setLayoutComment(formAndVo(super.getLayoutComment(),housingInfo.getLayoutComment()));	// 間取詳細コメント

 		if(super.getFloorNo() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getFloorNo())) {						// 物件の階数
	 			housingInfo.setFloorNo(Integer.valueOf(super.getFloorNo()));
	 		} else {
	 			housingInfo.setFloorNo(null);
	 		}
 		}

 		housingInfo.setFloorNoNote(formAndVo(super.getFloorNoNote(),housingInfo.getFloorNoNote()));			// 物件の階数コメント

 		if(super.getLandArea() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getLandArea())) {						// 土地面積
	 			housingInfo.setLandArea(new BigDecimal(super.getLandArea()));
	 		} else {
	 			housingInfo.setLandArea(null);
	 		}
 		}

 		housingInfo.setLandAreaMemo(formAndVo(super.getLandAreaMemo(),housingInfo.getLandAreaMemo()));		// 土地面積_補足

 		if(super.getPersonalArea() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getPersonalArea())) {					// 専有面積
	 			housingInfo.setPersonalArea(new BigDecimal(super.getPersonalArea()));
	 		} else {
	 			housingInfo.setPersonalArea(null);
	 		}
 		}

 		housingInfo.setPersonalAreaMemo(formAndVo(super.getPersonalAreaMemo(),housingInfo.getPersonalAreaMemo()));			// 専有面積_補足
 		housingInfo.setMoveinFlg(formAndVo(super.getMoveinFlg(),housingInfo.getMoveinFlg()));								// 入居状態フラグ
 		housingInfo.setParkingSituation(formAndVo(super.getParkingSituation(),housingInfo.getParkingSituation()));			// 駐車場の状況
 		housingInfo.setParkingEmpExist(formAndVo(super.getParkingEmpExist(),housingInfo.getParkingEmpExist()));				// 駐車場空の有無
 		housingInfo.setDisplayParkingInfo(formAndVo(super.getDisplayParkingInfo(),housingInfo.getDisplayParkingInfo()));	// 表示用駐車場情報
 		housingInfo.setWindowDirection(formAndVo(super.getWindowDirection(),housingInfo.getWindowDirection()));				// 窓の向き
 		//物件リクエストフラグ
 		((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setRequestFlg(formAndVo(this.getRequestFlg(),((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).getRequestFlg()));

 		// アイコン情報は、設備情報側から自動更新するので入力項目ではない。

 		housingInfo.setBasicComment(formAndVo(super.getBasicComment(),housingInfo.getBasicComment()));						// 基本情報コメント

        if (!StringValidateUtil.isEmpty(this.minWalkingTime)) {
            int minWalkingTime = Integer.valueOf(this.minWalkingTime);
            ((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setMinWalkingTime(minWalkingTime);
        }
        if(!StringValidateUtil.isEmpty(this.priceFullMin)){
        	Long priceFullMin = Long.valueOf(this.priceFullMin);
            ((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setPriceFullMin(priceFullMin);
        }
        if(!StringValidateUtil.isEmpty(this.priceFullMax)){
            Long priceFullMax = Long.valueOf(this.priceFullMax);
            ((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setPriceFullMax(priceFullMax);
        }

        ((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setReformComment(formAndVo(this.getReformComment(),((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).getReformComment()));

        if(!StringValidateUtil.isEmpty(this.getFloorNo())){
        	int floorNo = Integer.valueOf(this.getFloorNo());
        	((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setFloorNo(floorNo);
        }
        if(!StringValidateUtil.isEmpty(this.getUpkeep())){
        	housingInfo.setUpkeep(PanaStringUtils.toLong(this.getUpkeep()));
        }
        if(!StringValidateUtil.isEmpty(this.getMenteFee())){
        	housingInfo.setMenteFee(PanaStringUtils.toLong(this.getMenteFee()));
        }

        //((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setRequestFlg(this.requestFlg);
    }
	/**
     * String型の値を設定する。<br/>
     * <br/>
     * @return String
     */
	public String formAndVo(String form,String vo){
		String val;
		if(form == null && !StringValidateUtil.isEmpty(vo)){
			val=vo;
		}else{
			val=form;
		}
		return val;
	}
    @Override
    public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();
        return (startSize == errors.size());
    }
}
