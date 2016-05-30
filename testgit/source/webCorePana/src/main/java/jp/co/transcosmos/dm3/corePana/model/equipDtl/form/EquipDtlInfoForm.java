package jp.co.transcosmos.dm3.corePana.model.equipDtl.form;

import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.core.validation.LineValidationFailure;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;

/**
 * 設備情報用フォーム.
 * <p>
 *
 * <pre>
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.4.14	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class EquipDtlInfoForm implements Validateable {

	/** 項目数量 */
	private final static int _CONT = 30;
	/** システム物件CD */
	private String sysHousingCd;
	/** 物件種類CD */
	private String housingKindCd;
	/** 物件番号 */
	private String housingCd;
	/** 物件名称 */
	private String displayHousingName;
	/** 設備項目 */
	private String[] equipCategory = new String[_CONT];
	/** 設備 */
	private String[] equip = new String[_CONT];
	/** リフォーム */
	private String[] reform = new String[_CONT];
	/** キー番号 */
	private String[] keyCd = new String[_CONT];
	/** 命令フラグ */
	private String command;

	/** 設備項目 */
	private final static String _EQUIP_CATEGORY = "adminEquipName";
	/** 設備 */
	private final static String _EQUIP = "adminEquipInfo";
	/** リフォーム */
	private final static String _REFORM = "adminEquipReform";

	EquipDtlInfoForm() {
		super();
	}

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
	 * @return equipCategory
	 */
	public String[] getEquipCategory() {
		return equipCategory;
	}

	/**
	 * @param equipCategory セットする equipCategory
	 */
	public void setEquipCategory(String[] equipCategory) {
		this.equipCategory = equipCategory;
	}

	/**
	 * @return equip
	 */
	public String[] getEquip() {
		return equip;
	}

	/**
	 * @param equip セットする equip
	 */
	public void setEquip(String[] equip) {
		this.equip = equip;
	}

	/**
	 * @return reform
	 */
	public String[] getReform() {
		return reform;
	}

	/**
	 * @param reform セットする reform
	 */
	public void setReform(String[] reform) {
		this.reform = reform;
	}

	/**
	 * @return keyCd
	 */
	public String[] getKeyCd() {
		return keyCd;
	}

	/**
	 * @param keyCd セットする keyCd
	 */
	public void setKeyCd(String[] keyCd) {
		this.keyCd = keyCd;
	}

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
	 * キー番号設定する<br/>
	 * <br/>
	 *
	 */
	public void init() {
		for (int i = 0; i < _CONT; i++) {
			this.keyCd[i] = String.valueOf(i + 1);
		}
	}

	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * この Form クラスのバリデーションメソッドは、Validateable インターフェースの実装では無いので、
	 * バリデーション実行時の引数が異なるので、複数 Form をまとめてバリデーションする場合などは注意する事。<br/>
	 * <br/>
	 *
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();

		for (int i = 0; i < _CONT; i++) {
			// 設備項目入力チェック
			ValidationChain equipCategory = new ValidationChain("equipDtl.input.equipCategory", this.equipCategory[i]);
			// 桁数チェック
			equipCategory.addValidation(new LineAdapter(new MaxLengthValidation(30), i + 1));
			equipCategory.validate(errors);

			// 設備入力チェック
			ValidationChain equip = new ValidationChain("equipDtl.input.equip", this.equip[i]);
			// 桁数チェック
			equip.addValidation(new LineAdapter(new MaxLengthValidation(30), i + 1));
			equip.validate(errors);

			// 設備項目と設備の関連チェック
			if (!StringUtils.isEmpty(this.equip[i]) && StringUtils.isEmpty(this.equipCategory[i])) {
				ValidationFailure equipVf = new LineValidationFailure(new ValidationFailure("equipCategoryNotNull",
						"設備", "設備項目", new String[] { "入力" }), Integer.valueOf(keyCd[i]));
				errors.add(equipVf);
			}

			// 設備項目とリフォームの関連チェック
			if ("1".equals(this.reform[i]) && StringUtils.isEmpty(this.equipCategory[i])) {
				ValidationFailure reformVf = new LineValidationFailure(new ValidationFailure("equipCategoryNotNull",
						"リフォーム", "設備項目", new String[] { "チェック" }), Integer.valueOf(keyCd[i]));
				errors.add(reformVf);
			}

		}

		return (startSize == errors.size());
	}

	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 *
	 * @param housing 物件情報オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setDefaultData(PanaHousing housing) throws Exception {
		// 物件基本情報を取得する。
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

		// 物件番号をセットする。
		this.housingCd = housingInfo.getHousingCd();

		// 表示用物件名をセットする。
		this.displayHousingName = housingInfo.getDisplayHousingName();

		// 物件拡張属性情報を取得する。
		Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();

		// カテゴリ名に該当する Map を取得する。
		Map<String, String> cateMap = extMap.get("adminEquip");

		if (cateMap == null) {
			return;
		}

		// 設備項目
		String[] equipCategory = new String[_CONT];
		// 設備
		String[] equip = new String[_CONT];
		// リフォーム
		String[] reform = new String[_CONT];

		// 管理者用設備情報の取得
		for (String key : cateMap.keySet()) {
			// Key名
			String keyName = "";

			// Key名番号
			int keyNameNm = 0;

			try {
				// Key名
				keyName = key.substring(0, key.length() - 2);

				// Key名番号
				keyNameNm = Integer.parseInt(key.substring(key.length() - 2));
			} catch (Exception e) {
				throw new Exception("Key名フォーマットが不正です。");
			}

			// Key名が設備項目の場合
			if (_EQUIP_CATEGORY.equals(keyName)) {
				equipCategory[keyNameNm - 1] = (cateMap.get(key));

			} else if (_EQUIP.equals(keyName)) {
				// Key名が設備の場合
				equip[keyNameNm - 1] = (cateMap.get(key));

			} else if (_REFORM.equals(keyName)) {
				// Key名がリフォームの場合
				reform[keyNameNm - 1] = (cateMap.get(key));
			}
		}

		// 設備項目をセットする。
		this.equipCategory = equipCategory;
		// 設備をセットする。
		this.equip = equip;
		// リフォームをセットする。
		this.reform = reform;
	}

}
