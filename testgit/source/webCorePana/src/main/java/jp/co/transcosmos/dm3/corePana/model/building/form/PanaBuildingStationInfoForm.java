package jp.co.transcosmos.dm3.corePana.model.building.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
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
 * fan         2015.03.11  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */

public class PanaBuildingStationInfoForm extends BuildingStationInfoForm implements Validateable {
    /** 共通コード変換処理 */
    private CodeLookupManager codeLookupManager;

	/**
     * 継承用<br/>
     * <br/>
     */
    protected PanaBuildingStationInfoForm(){
        super();
    }

    /**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
     */
    protected PanaBuildingStationInfoForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
        super();
        this.lengthUtils = lengthUtils;
        this.codeLookupManager = codeLookupManager;
    }

	/**
	 * 引数で渡された最寄り駅情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param information 値を設定するお知らせ情報のバリーオブジェクト
	 * @param length 最寄り駅のレコード数
	 *
	 */
	@Override
	public void copyToBuildingStationInfo(
			BuildingStationInfo[] buildingStationInfos, int length) {
		// 処理しているvoの番号
		int voIndex = 0;
		// 画面行数でルールする
		for (int i = 0;i < this.getDefaultRouteCd().length; i++) {
			// 路線を入力していない場合、登録しない
			if(StringValidateUtil.isEmpty(this.getDefaultRouteCd()[i])) {
				continue;
			}
			buildingStationInfos[voIndex].setSysBuildingCd(this.getSysBuildingCd());
			buildingStationInfos[voIndex].setDivNo(voIndex + 1);
			buildingStationInfos[voIndex].setDefaultRouteCd(this.getDefaultRouteCd()[i]);
			buildingStationInfos[voIndex].setDefaultRouteName(this.getRouteName()[i]);
			buildingStationInfos[voIndex].setStationCd(this.getStationCd()[i]);
			buildingStationInfos[voIndex].setStationName(this.getStationName()[i]);
			buildingStationInfos[voIndex].setBusCompany(this.getBusCompany()[i]);
			buildingStationInfos[voIndex].setSortOrder(i+1);
			if (!StringValidateUtil.isEmpty(this.getTimeFromBusStop()[voIndex])) {
				buildingStationInfos[voIndex].setTimeFromBusStop(Integer.valueOf(this.getTimeFromBusStop()[i]));
			}
			// 全て入力した行は処理終了
			if (voIndex == length) {
				break;
			}
			voIndex++;
		}
	}
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();
		return (startSize == errors.size());
	}
}
