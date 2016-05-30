package jp.co.transcosmos.dm3.corePana.model.building.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
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
 * fan         2015.03.11  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */

public class PanaBuildingForm extends BuildingForm implements Validateable {
	/** 総階数 */
	private String totalFloorsTxt;
    /**
     * 継承用<br/>
     * <br/>
     */
    protected PanaBuildingForm() {
        super();
    }

    /**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
     */
    protected PanaBuildingForm(LengthValidationUtils lengthUtils) {
        super();
        this.lengthUtils = lengthUtils;
    }

    /**
	 * @return totalFloorsTxt
	 */
	public String getTotalFloorsTxt() {
		return totalFloorsTxt;
	}

	/**
	 * @param totalFloorsTxt セットする totalFloorsTxt
	 */
	public void setTotalFloorsTxt(String totalFloorsTxt) {
		this.totalFloorsTxt = totalFloorsTxt;
	}

	/**
     * 引数で渡された建物基本情報のバリーオブジェクトにフォームの値を設定する。<br/>
     * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
     * <br/>
     * @param building 値を設定する建物基本情報のバリーオブジェクト
     *
     */
	@Override
    public void copyToBuildingInfo(BuildingInfo buildingInfo, String editUserId) {

		BuildingInfo  building= buildingInfo;
        // システム建物CDを設定
        if (!StringValidateUtil.isEmpty(formAndVo(this.getSysBuildingCd(),buildingInfo.getSysBuildingCd()))) {
            building.setSysBuildingCd(formAndVo(this.getSysBuildingCd(),buildingInfo.getSysBuildingCd()));
        }

        // 建物番号を設定
        building.setBuildingCd(formAndVo(this.getBuildingCd(),buildingInfo.getBuildingCd()));

        // 物件種類CDを設定
        building.setHousingKindCd(formAndVo(this.getHousingKindCd(),buildingInfo.getHousingKindCd()));

        // 所在地・郵便番号を設定
        building.setZip(formAndVo(this.getZip(),buildingInfo.getZip()));

        // 所在地・都道府県CDを設定
        building.setPrefCd(formAndVo(this.getPrefCd(),buildingInfo.getPrefCd()));

        // 所在地・市区町村CDを設定
        building.setAddressCd(formAndVo(this.getAddressCd(),buildingInfo.getAddressCd()));

        // 所在地・市区町村名を設定
        building.setAddressName(formAndVo(this.getAddressName(),buildingInfo.getAddressName()));

        // 所在地・町名番地を設定
        building.setAddressOther1(formAndVo(this.getAddressOther1(),buildingInfo.getAddressOther1()));

        // 所在地・建物名その他を設定
        building.setAddressOther2(formAndVo(this.getAddressOther2(),buildingInfo.getAddressOther2()));

        // 竣工年月を設定
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (!StringValidateUtil.isEmpty(this.getCompDate())) {
            try {
                building.setCompDate(sdf.parse(this.getCompDate() + "01"));
            } catch (ParseException e) {
                building.setCompDate(null);
            }
        }
        // 総階数を設定
        if(this.getTotalFloors() != null || building.getTotalFloors()!=null)
        {
        	building.setTotalFloors(PanaStringUtils.toInteger(formAndVo(this.getTotalFloors(), PanaStringUtils.toString(building.getTotalFloors()))));
        }

        // 更新日付を設定
        building.setUpdDate(new Date());

        // 更新担当者を設定
        building.setUpdUserId(editUserId);

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
