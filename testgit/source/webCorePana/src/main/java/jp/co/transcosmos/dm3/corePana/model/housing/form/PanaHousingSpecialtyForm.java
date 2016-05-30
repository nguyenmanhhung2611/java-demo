package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * 設備情報用フォーム.
 * <p>
 *
 * <pre>
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong  2015.04.09  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaHousingSpecialtyForm extends HousingEquipForm {
    /**
     * コンストラクター<br/>
     * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
     * <br/>
     */
    PanaHousingSpecialtyForm() {
        super();
    }

    /** 命令フラグ */
    private String command;
    /** 物件種類CD */
    private String housingKindCd;
    /** 物件番号 */
    private String housingCd;
    /** 物件名称 */
    private String displayHousingName;
    /** 設備マスタの設備CD */
    private String[] mstEquipCd;
    /** 設備名称 */
    private String[] equipName;

    /**
     * @return command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command セットする command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return housingKindCd
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * @param housingKindCd セットする housingKindCd
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
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
     * @return mstEquipCd
     */
    public String[] getMstEquipCd() {
        return mstEquipCd;
    }

    /**
     * @param mstEquipCd セットする mstEquipCd
     */
    public void setMstEquipCd(String[] mstEquipCd) {
        this.mstEquipCd = mstEquipCd;
    }

    /**
     * @return equipName
     */
    public String[] getEquipName() {
        return equipName;
    }

    /**
     * @param equipName セットする equipName
     */
    public void setEquipName(String[] equipName) {
        this.equipName = equipName;
    }

    /**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 *
	 * @param housing 物件情報オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setDefaultData(Housing housing, List<JoinResult> equipList) throws Exception {
		// 物件基本情報を取得する。
		HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo().getItems().get("housingInfo"));

		// 物件番号をセットする。
		this.setHousingCd(housingInfo.getHousingCd());
		// 表示用物件名をセットする。
		this.setDisplayHousingName(housingInfo.getDisplayHousingName());

		// 物件設備情報を取得する。
		Map<String, jp.co.transcosmos.dm3.core.vo.EquipMst> housingEquipMap = housing.getHousingEquipInfos();
		// 物件設備CD
		String[] housingEquipCd = new String[housingEquipMap.size()];
		int i = 0;
		for (String key : housingEquipMap.keySet()) {
			housingEquipCd[i] = housingEquipMap.get(key).getEquipCd();
			i++;
		}
		// 物件設備CDをセットする。
		this.setEquipCd(housingEquipCd);

		int len = equipList.size();
		// マスタの設備CD
		String[] mstEquipCd = new String[len];
		// 設備名称
		String[] equipName = new String[len];
		for (int j = 0; j < len; j++) {
			mstEquipCd[j] = ((EquipMst)equipList.get(j).getItems().get("equipMst")).getEquipCd();
			equipName[j] = ((EquipMst)equipList.get(j).getItems().get("equipMst")).getEquipName();
		}
		// マスタの設備CDをセットする。
		this.setMstEquipCd(mstEquipCd);
		// 設備名称をセットする。
		this.setEquipName(equipName);
	}
}
