package jp.co.transcosmos.dm3.corePana.model.building.form;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * 建物ランドマーク情報の入力パラメータ受取り用フォーム
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 劉艶東		2015.03.31	新規作成
 *
 * 注意事項
 * バリデーション実行時のパラメータが通常と異なるので注意する事。
 *
 * </pre>
 */
public class PanaBuildingLandmarkForm extends BuildingLandmarkForm {
    /**
     * 継承用<br/>
     * <br/>
     */
    PanaBuildingLandmarkForm() {
        super();
    }

    /**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
     */
    PanaBuildingLandmarkForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
        super();
        this.lengthUtils = lengthUtils;
        this.codeLookupManager = codeLookupManager;
    }


    /** システム物件CD */
    private String sysHousingCd;
    /** 物件番号 */
    private String housingCd;
    /** 物件名称 */
    private String displayHousingName;

    /**
	 * @return sysHousingCd
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * @param sysHousingCd セットする sysHousingCd
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * @return housingCd
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * @param housingCd セットする housingCd
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	 * @return displayHousingName
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * @param displayHousingName セットする displayHousingName
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
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

		for (int i = 0; i < this.getLandmarkType().length; i++) {
	        // ランドマークの種類入力チェック
			validLandmarkType(errors, i);
	        // ランドマーク名入力チェック
			validLandmarkName(errors, i);
	        // ランドマークからの距離入力チェック
			validDistanceFromLandmark(errors, i);
			// ランドマーク名とランドマークからの距離の関連チェック
			validLandmarkNameAndDistance(errors, i);
		}

        return (startSize == errors.size());
	}

	/**
	 * ランドマークの種類入力チェック<br/>
	 * ・ ランドマークの種類のパターンチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validLandmarkType(List<ValidationFailure> errors, int i) {
        // 規模入力チェック
        ValidationChain valid = new ValidationChain("buildingLandmark.input.landmarkType",
        		this.getLandmarkType()[i]);
        // パターンチェック
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "buildingLandmark_landmarkType"));
        valid.validate(errors);

	}

	/**
	 * ランドマーク名とランドマークからの距離の関連チェック<br/>
	 * ・ランドマークからの距離を入力した場合、ランドマーク名を入力していない場合
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validLandmarkNameAndDistance(List<ValidationFailure> errors,
			int i) {
		String label1 = this.codeLookupManager.lookupValue("buildingLandmark_landmarkType", this.getLandmarkType()[i])  + "までの距離";
		String label2 =  this.codeLookupManager.lookupValue("buildingLandmark_landmarkType", this.getLandmarkType()[i])  + "名";

		if (!StringValidateUtil.isEmpty(this.getDistanceFromLandmark()[i]) &&
				StringValidateUtil.isEmpty(this.getLandmarkName()[i])) {
        	errors.add(new ValidationFailure("landMarkError", label1, label2, null));
		}

	}

	/**
	 * ランドマーク名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	@Override
	protected void validLandmarkName(List<ValidationFailure> errors, int i) {
        // ランドマーク名 入力チェック
		String label = this.codeLookupManager.lookupValue("buildingLandmark_landmarkType", this.getLandmarkType()[i])  + "名_値";
        ValidationChain valid = new ValidationChain(label, this.getLandmarkName()[i]);
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(50));
        valid.validate(errors);
	}


	/**
	 * ランドマークからの距離 バリデーション<br/>
	 * ・DECIMALチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	@Override
	protected void validDistanceFromLandmark(List<ValidationFailure> errors, int i) {
		int errorSize = errors.size();
        // ランドマークからの距離 入力チェック
		String label = this.codeLookupManager.lookupValue("buildingLandmark_landmarkType", this.getLandmarkType()[i]) + "_距離";
        ValidationChain valid = new ValidationChain(label, this.getDistanceFromLandmark()[i]);
        // 半角数字チェック
        valid.addValidation(new NumericValidation());
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(5));
        valid.validate(errors);
        if (errorSize == errors.size()) {
        	if (PanaStringUtils.toInteger(this.getDistanceFromLandmark()[i]) != null &&
        			PanaStringUtils.toInteger(this.getDistanceFromLandmark()[i]) <= 0) {
            	errors.add(new ValidationFailure("landMarkDistanceError", label,  null, null));
        	}
        }

	}



	/**
	 * 引数で渡された建物ランドマーク情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param buildingLandmark[] 値を設定する建物ランドマーク情報のバリーオブジェクト
	 * @param length 建物ランドマークのレコード数
	 *
	 */
	@Override
	public void copyToBuildingLandmark(BuildingLandmark[] buildingLandmarks, int length) {

		// 処理しているvoの番号
		int voIndex = 0;
		for (int i = 0; i < this.getLandmarkType().length; i++) {
			// 地域名を入力していない場合、登録しない
			if(StringValidateUtil.isEmpty(this.getLandmarkName()[i])) {
				continue;
			}
			buildingLandmarks[voIndex].setSysBuildingCd(this.getSysBuildingCd());
			buildingLandmarks[voIndex].setDivNo(voIndex + 1);
			buildingLandmarks[voIndex].setLandmarkType(this.getLandmarkType()[i]);
			buildingLandmarks[voIndex].setWayFromLandmark("3");
			buildingLandmarks[voIndex].setSortOrder(null);
			buildingLandmarks[voIndex].setLandmarkName(this.getLandmarkName()[i]);
			buildingLandmarks[voIndex].setTimeFromLandmark(null);
			if (!StringValidateUtil.isEmpty(this.getDistanceFromLandmark()[i])) {
				buildingLandmarks[voIndex].setDistanceFromLandmark(new BigDecimal(this.getDistanceFromLandmark()[i]));
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
	 * ランドマークからの徒歩時間は再計算とする。
	 * <br/>
	 * @param building Building　取得した建物情報リスト
	 *
	 */
	@Override
	public void setDefaultData(Building building) {
		super.setDefaultData(building);
		// 地域情報リストを取得する
		List<BuildingLandmark> buildingLandmarkList = building.getBuildingLandmarkList();

		for(int i = 0;i<buildingLandmarkList.size();i ++) {
			BuildingLandmark buildingLandmark = buildingLandmarkList.get(i);
			// ランドマークからの徒歩時間
			if (buildingLandmark.getDistanceFromLandmark() != null) {
				this.getDistanceFromLandmark()[i] = String.valueOf(buildingLandmark.getDistanceFromLandmark().intValue());
				BigDecimal time = PanaCalcUtil.calcLandMarkTime(buildingLandmark.getDistanceFromLandmark());
				if (time != null) {
					this.getTimeFromLandmark()[i] = String.valueOf(time);
				}
			} else {
				this.getTimeFromLandmark()[i] = null;
			}
		}

	}
}
